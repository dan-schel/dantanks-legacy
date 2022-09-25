package com.danschellekens.dantanks.level.actions;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AnglePowerAimData;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.effects.ExplosionEffect;
import com.danschellekens.dantanks.level.projectile.ShellProjectile;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.slick2d.graphics.Artist;

public class ShellAction extends UtilityAction {
	Color shellColor;
	float shellSize;
	
	float groundDamageRadius;
	float groundDamageStrength;
	
	float playerDamageRadius;
	float maxPlayerDamage;
	
	ShellProjectile shell;
	
	public ShellAction(Utility utility, AimData aim) {
		super(utility, aim);
		
		shellColor = new Color(0, 0, 0);
		shellSize = 2;
		
		groundDamageRadius = 12;
		groundDamageStrength = 1;
		playerDamageRadius = 48;
		maxPlayerDamage = 20;
	}
	
	@Override
	protected void start(LevelUmpire umpire) {
		AnglePowerAimData anglePower = (AnglePowerAimData) aim;
		shell = new ShellProjectile(
				firer.getBarrelEndPosition(), 
				anglePower.getAngle(), 
				anglePower.getPower(), 
				level.getEnvironment().getWindConstant(), 
				level.getEnvironment().getGravityConstant()
		);
		shell.setColor(this.shellColor);
		shell.setSize(this.shellSize);
	}

	@Override
	public void update() {
		if (shell != null) {
			shell.update();
			
			if (shouldDelete()) { deleteShell(); }
			else if (shouldExplode()) { explodeShell(); }
		}
	}
	boolean shouldDelete() {
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
	boolean shouldExplode() {
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
	void deleteShell() {
		shell = null;
	}
	void explodeShell() {
		level.getEnvironment().explode(shell.getPosition(), groundDamageRadius, groundDamageStrength);
		level.damagePlayers(shell.getPosition(), playerDamageRadius, maxPlayerDamage, firerID, utility.getID());
		
		ExplosionEffect effect = new ExplosionEffect(shell.getPosition(), playerDamageRadius / 2f);
		level.addEffect(effect);
		deleteShell();
	}

	@Override
	public void renderGraphics(Artist artist) {
		if (shell != null) {
			shell.render(artist);
		}
	}
	@Override
	public void renderHitboxes(Artist artist) {
		if (shell != null) {
			artist.push();
			artist.translate(shell.getPosition().getX(), shell.getPosition().getY());
			artist.shape().draw(new Circle(0, 0, 2), new Color(1f, 1f, 1f));
			artist.pop();
		}
	}

	@Override
	public boolean isTurnDone() {
		return shell == null;
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
