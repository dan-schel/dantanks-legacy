package com.danschellekens.dantanks.level.actions;

import org.newdawn.slick.Color;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.effects.PotionEffect;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.slick2d.graphics.Artist;

public class RepairKitAction extends UtilityAction {
	float healthRepaired;
	
	public RepairKitAction(Utility utility, AimData aim) {
		super(utility, aim);
		
		this.healthRepaired = 0;
		this.setEndsTurn(false);
	}
	
	@Override
	protected void start(LevelUmpire umpire) {
		PotionEffect effect;
		
		if (this.healthRepaired > 0) {
			firer.health().heal(this.healthRepaired, umpire, firerID, utility.getID());
			effect = new PotionEffect(firer.hitbox().getHitboxCenter(), new Color(1f, 0f, 0f));
		}
		else {
			firer.health().damage(-this.healthRepaired, umpire, firerID, utility.getID());
			effect = new PotionEffect(firer.hitbox().getHitboxCenter(), new Color(0f, 0f, 0f));
		}
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
		return true;
	}

	@Override
	public boolean shouldRun(Level level, TankObj firer) {
		if (this.healthRepaired > 0) {
			return !firer.health().isMax();
		}
		else {
			return true;
		}
	}

	public float getHealthRepaired() {
		return healthRepaired;
	}

	public void setHealthRepaired(float healthRepaired) {
		this.healthRepaired = healthRepaired;
	}
}
