package com.danschellekens.dantanks.level.actions;

import org.newdawn.slick.Color;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.effects.PotionEffect;
import com.danschellekens.dantanks.level.shieldObj.ShieldObj;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.slick2d.graphics.Artist;

public class ShieldAction extends UtilityAction {
	final Color color;
	final ShieldObj algorithm;
	
	public ShieldAction(Utility utility, AimData aim, ShieldObj algorithm, Color color) {
		super(utility, aim);
		
		this.color = color;
		this.algorithm = algorithm;
		this.setEndsTurn(false);
	}

	@Override
	public boolean shouldRun(Level level, TankObj firer) {
		if (firer.hasShieldAlgorithm()) {
			String currentShieldID = firer.getShieldAlgorithm().getUtilityID();
			if (currentShieldID.equals(this.utility.getID())) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected void start(LevelUmpire umpire) {
		firer.setShieldAlgorithm(algorithm);
		PotionEffect effect = new PotionEffect(firer.hitbox().getHitboxCenter(), color);
		level.addEffect(effect);
	}

	@Override
	public void update() {
	}

	@Override
	public void renderGraphics(Artist artist) {
	}

	@Override
	public void renderHitboxes(Artist artist) {

	}

	@Override
	public boolean isTurnDone() {
		return true;
	}

	@Override
	public boolean isCompletelyDone() {
		return isTurnDone();
	}
}
