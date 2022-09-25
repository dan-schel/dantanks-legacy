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
import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;

public class MultiShellAction extends UtilityAction {
	Color shellColor;
	float shellSize;
	int shellQuantity;
	float shellSpread;
	
	float groundDamageRadius;
	float groundDamageStrength;
	
	float playerDamageRadius;
	float maxPlayerDamage;
	
	ArrayList<ShellProjectile> shells;
	
	public MultiShellAction(Utility utility, AimData aim) {
		super(utility, aim);
		
		shellColor = new Color(0, 0, 0);
		shellSize = 2;
		shellQuantity = 3;
		shellSpread = 10;
		
		groundDamageRadius = 12;
		groundDamageStrength = 1;
		playerDamageRadius = 48;
		maxPlayerDamage = 20;
	}
	
	@Override
	protected void start(LevelUmpire umpire) {
		shells = new ArrayList<ShellProjectile>();
		
		AnglePowerAimData anglePower = (AnglePowerAimData) aim;
		
		for (int i = 0; i < shellQuantity; i++) {
			float angle = Range.Map(i, 0, shellQuantity - 1, anglePower.getAngle() - shellSpread, anglePower.getAngle() + shellSpread);
			
			ShellProjectile shell = new ShellProjectile(
					firer.getBarrelEndPosition(), 
					angle, 
					anglePower.getPower(),level.getEnvironment().getWindConstant(), 
					level.getEnvironment().getGravityConstant()
			);
			shell.setColor(this.shellColor);
			shell.setSize(this.shellSize);
			shells.add(shell);
		}
	}

	@Override
	public void update() {
		Iterator<ShellProjectile> mi = shells.iterator();
		while (mi.hasNext()) {
			ShellProjectile m = mi.next();
			m.update();	
			if (shouldDelete(m)) { deleteShell(m, mi); }
			else if (shouldExplode(m)) { explodeShell(m, mi); }
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
	void deleteShell(ShellProjectile shell, Iterator<ShellProjectile> iterator) {
		iterator.remove();
	}
	void explodeShell(ShellProjectile shell, Iterator<ShellProjectile> iterator) {
		level.getEnvironment().explode(shell.getPosition(), groundDamageRadius, groundDamageStrength);
		level.damagePlayers(shell.getPosition(), playerDamageRadius, maxPlayerDamage, firerID, utility.getID());
		
		ExplosionEffect effect = new ExplosionEffect(shell.getPosition(), playerDamageRadius / 2f);
		level.addEffect(effect);
		deleteShell(shell, iterator);
	}

	@Override
	public void renderGraphics(Artist artist) {
		for (ShellProjectile m : shells) {
			m.render(artist);
		}
	}
	@Override
	public void renderHitboxes(Artist artist) {
		// Stealthy (couldn't be bothered writing hitbox rendering code.)
	}

	@Override
	public boolean isTurnDone() {
		return shells != null && shells.size() == 0;
	}
	
	@Override
	public boolean isCompletelyDone() {
		return isTurnDone();
	}

	@Override
	public boolean shouldRun(Level level, TankObj firer) {
		return true;
	}

	public Color getShellColor() {
		return shellColor;
	}
	public void setShellColor(Color shellColor) {
		this.shellColor = shellColor;
	}
	public float getShellSize() {
		return shellSize;
	}
	public void setShellSize(float shellSize) {
		this.shellSize = shellSize;
	}
	public int getShellQuantity() {
		return shellQuantity;
	}
	public void setShellQuantity(int shellQuantity) {
		this.shellQuantity = shellQuantity;
	}
	public float getShellSpread() {
		return shellSpread;
	}
	public void setShellSpread(float shellSpread) {
		this.shellSpread = shellSpread;
	}
	public float getGroundDamageRadius() {
		return groundDamageRadius;
	}
	public void setGroundDamageRadius(float groundDamageRadius) {
		this.groundDamageRadius = groundDamageRadius;
	}
	public float getGroundDamageStrength() {
		return groundDamageStrength;
	}
	public void setGroundDamageStrength(float groundDamageStrength) {
		this.groundDamageStrength = groundDamageStrength;
	}
	public float getPlayerDamageRadius() {
		return playerDamageRadius;
	}
	public void setPlayerDamageRadius(float playerDamageRadius) {
		this.playerDamageRadius = playerDamageRadius;
	}
	public float getMaxPlayerDamage() {
		return maxPlayerDamage;
	}
	public void setMaxPlayerDamage(float maxPlayerDamage) {
		this.maxPlayerDamage = maxPlayerDamage;
	}
}
