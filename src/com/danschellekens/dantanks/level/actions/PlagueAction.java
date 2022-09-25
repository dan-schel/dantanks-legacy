package com.danschellekens.dantanks.level.actions;

import org.newdawn.slick.Color;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.effects.PotionEffect;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.slick2d.graphics.Artist;

public class PlagueAction extends UtilityAction {
	public PlagueAction(Utility utility, AimData aim) {
		super(utility, aim);
		
		this.setEndsTurn(false);
	}
	
	@Override
	protected void start(LevelUmpire umpire) {
		TankObj[] tanks = level.getTanks();
		
		for (TankObj tank : tanks) {
			tank.health().damage(10000000, umpire, this.firerID, utility.getID());
			PotionEffect effect = new PotionEffect(tank.hitbox().getHitboxCenter(), new Color(0f, 0f, 0f));
			level.addEffect(effect);
		}
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
		return true;
	}
}
