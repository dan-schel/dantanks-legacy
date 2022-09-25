package com.danschellekens.dantanks.level.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Color;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AnglePowerAimData;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.effects.ExplosionEffect;
import com.danschellekens.dantanks.level.projectile.ShellProjectile;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.operations.Random;
import com.danschellekens.slick2d.graphics.Artist;

public class FragsAction extends UtilityAction {
	enum Stage { UNSTARTED, PRIMARY_MISSILE, FRAGS, DONE }
	
	Color primaryShellColor;
	float primaryShellSize;
	float primaryShellExplosionEffectSize;
	
	Color fragsColor;
	float fragsSize;
	float fragsGroundDamageRadius;
	float fragsGroundDamageStrength;
	float fragsPlayerDamageRadius;
	float fragsMaxPlayerDamage;
	float fragsFireAngleMin;
	float fragsFireAngleMax;
	float fragsFirePowerMin;
	float fragsFirePowerMax;
	int fragsQuantity;
	
	Stage stage;
	ShellProjectile primaryShell;
	ArrayList<ShellProjectile> frags;
	
	public FragsAction(Utility utility, AimData aim) {
		super(utility, aim);
		
		primaryShellColor = new Color(0, 0, 0);
		primaryShellSize = 3;
		primaryShellExplosionEffectSize = 6;
		
		fragsColor = new Color(0, 0, 0);
		fragsSize = 2;
		fragsGroundDamageRadius = 12;
		fragsGroundDamageStrength = 1;
		fragsPlayerDamageRadius = 48;
		fragsMaxPlayerDamage = 20;
		fragsFireAngleMin = -30;
		fragsFireAngleMax = 30;
		fragsFirePowerMin = 12;
		fragsFirePowerMax = 18;
		fragsQuantity = 5;
		
		stage = Stage.UNSTARTED;
	}
	
	@Override
	protected void start(LevelUmpire umpire) {
		frags = new ArrayList<ShellProjectile>();
		
		AnglePowerAimData anglePower = (AnglePowerAimData) aim;
		primaryShell = new ShellProjectile(
				firer.getBarrelEndPosition(), 
				anglePower.getAngle(), 
				anglePower.getPower(), 
				level.getEnvironment().getWindConstant(), 
				level.getEnvironment().getGravityConstant()
		);
		primaryShell.setColor(primaryShellColor);
		primaryShell.setSize(primaryShellSize);
		
		stage = Stage.PRIMARY_MISSILE;
	}

	@Override
	public void update() {
		if (stage == Stage.PRIMARY_MISSILE) {
			primaryShell.update();
			if (shouldDelete(primaryShell)) { deletePrimaryShell(); }
			else if (shouldExplode(primaryShell)) { explodePrimaryShell(); }
		}
		else if (stage == Stage.FRAGS) {
			Iterator<ShellProjectile> mi = frags.iterator();
			while (mi.hasNext()) {
				ShellProjectile m = mi.next();
				m.update();	
				if (shouldDelete(m)) { deleteFrag(m, mi); }
				else if (shouldExplode(m)) { explodeFrag(m, mi); }
			}
			if (frags.size() == 0) { stage = Stage.DONE; }
		}
	}
	boolean shouldDelete(ShellProjectile shell) {
		if (shell.getPosition().getY() > Level.LEVEL_HEIGHT + 300) {
			return true;
		}
		if (shell.getPosition().getX() < -300) {
			return true;
		}
		if (shell.getPosition().getX() > Level.LEVEL_WIDTH + 300) {
			return true;
		}
		
		return false;
	}
	boolean shouldExplode(ShellProjectile shell) {
		if (level.getGround().getHeightOfX(shell.getPosition().getX()) < shell.getPosition().getY()) {
			return true;
		}
		
		TankObj[] tanks = level.getTanks();
		for (TankObj t : tanks) {
			if (t.hitbox().getHitbox().contains(shell.getPosition().getX(), shell.getPosition().getY())) {
				return true;
			}
		}
		
		return false;
	}
	void deletePrimaryShell() {
		primaryShell = null;
		stage = Stage.FRAGS;
	}
	void explodePrimaryShell() {
		for (int i = 0; i < fragsQuantity; i++) {
			float angle = Random.Float(fragsFireAngleMin, fragsFireAngleMax);
			float power = Random.Float(fragsFirePowerMin, fragsFirePowerMax);
			ShellProjectile frag = new ShellProjectile(
					primaryShell.getPosition(), 
					angle, 
					power,
					level.getEnvironment().getWindConstant(), 
					level.getEnvironment().getGravityConstant()
			);
			frag.setColor(fragsColor);
			frag.setSize(fragsSize);
			frags.add(frag);
		}
		
		ExplosionEffect effect = new ExplosionEffect(primaryShell.getPosition(), primaryShellExplosionEffectSize);
		level.addEffect(effect);
		deletePrimaryShell();
	}
	void deleteFrag(ShellProjectile frag, Iterator<ShellProjectile> iterator) {
		iterator.remove();
	}
	void explodeFrag(ShellProjectile frag, Iterator<ShellProjectile> iterator) {
		level.getEnvironment().explode(frag.getPosition(), fragsGroundDamageRadius, fragsGroundDamageStrength);
		level.damagePlayers(frag.getPosition(), fragsPlayerDamageRadius, fragsMaxPlayerDamage, firerID, utility.getID());
		
		ExplosionEffect effect = new ExplosionEffect(frag.getPosition(), fragsPlayerDamageRadius / 2f);
		level.addEffect(effect);
		deleteFrag(frag, iterator);
	}

	@Override
	public void renderGraphics(Artist artist) {
		if (stage == Stage.PRIMARY_MISSILE) {
			primaryShell.render(artist);
		}
		if (stage == Stage.FRAGS) {
			for (ShellProjectile m : frags) {
				m.render(artist);
			}
		}
	}
	@Override
	public void renderHitboxes(Artist artist) {
		// Stealthy (couldn't be bothered writing hitbox rendering code.)
	}

	@Override
	public boolean isTurnDone() {
		return stage == Stage.DONE;
	}
	
	@Override
	public boolean isCompletelyDone() {
		return isTurnDone();
	}

	@Override
	public boolean shouldRun(Level level, TankObj firer) {
		return true;
	}

	public Color getPrimaryShellColor() {
		return primaryShellColor;
	}
	public void setPrimaryShellColor(Color primaryShellColor) {
		this.primaryShellColor = primaryShellColor;
	}
	public float getPrimaryShellSize() {
		return primaryShellSize;
	}
	public void setPrimaryShellSize(float primaryShellSize) {
		this.primaryShellSize = primaryShellSize;
	}
	public float getPrimaryShellExplosionEffectSize() {
		return primaryShellExplosionEffectSize;
	}
	public void setPrimaryShellExplosionEffectSize(
			float primaryShellExplosionEffectSize) {
		this.primaryShellExplosionEffectSize = primaryShellExplosionEffectSize;
	}
	public Color getFragsColor() {
		return fragsColor;
	}
	public void setFragsColor(Color fragsColor) {
		this.fragsColor = fragsColor;
	}
	public float getFragsSize() {
		return fragsSize;
	}
	public void setFragsSize(float fragsSize) {
		this.fragsSize = fragsSize;
	}
	public float getFragsGroundDamageRadius() {
		return fragsGroundDamageRadius;
	}
	public void setFragsGroundDamageRadius(float fragsGroundDamageRadius) {
		this.fragsGroundDamageRadius = fragsGroundDamageRadius;
	}
	public float getFragsGroundDamageStrength() {
		return fragsGroundDamageStrength;
	}
	public void setFragsGroundDamageStrength(float fragsGroundDamageStrength) {
		this.fragsGroundDamageStrength = fragsGroundDamageStrength;
	}
	public float getFragsPlayerDamageRadius() {
		return fragsPlayerDamageRadius;
	}
	public void setFragsPlayerDamageRadius(float fragsPlayerDamageRadius) {
		this.fragsPlayerDamageRadius = fragsPlayerDamageRadius;
	}
	public float getFragsMaxPlayerDamage() {
		return fragsMaxPlayerDamage;
	}
	public void setFragsMaxPlayerDamage(float fragsMaxPlayerDamage) {
		this.fragsMaxPlayerDamage = fragsMaxPlayerDamage;
	}
	public int getFragsQuantity() {
		return fragsQuantity;
	}
	public void setFragsQuantity(int fragsQuantity) {
		this.fragsQuantity = fragsQuantity;
	}

	public float getFragsFireAngleMin() {
		return fragsFireAngleMin;
	}
	public float getFragsFireAngleMax() {
		return fragsFireAngleMax;
	}
	public void setFragsFireAngle(float fragsFireAngleMin, float fragsFireAngleMax) {
		this.fragsFireAngleMin = fragsFireAngleMin;
		this.fragsFireAngleMax = fragsFireAngleMax;
	}
	
	public float getFragsFirePowerMin() {
		return fragsFirePowerMin;
	}
	public float getFragsFirePowerMax() {
		return fragsFirePowerMax;
	}
	public void setFragsFirePower(float fragsFirePowerMin, float fragsFirePowerMax) {
		this.fragsFirePowerMin = fragsFirePowerMin;
		this.fragsFirePowerMax = fragsFirePowerMax;
	}
}
