package com.danschellekens.dantanks.level.shieldObj;

import org.newdawn.slick.Color;

import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;

public class DefenceShieldObj extends ShieldObj {
	float damageLeftToStop;
	final float damageBlockRatio;
	final float totalDamageToStop;
	final Color color;
	final ShieldRenderer renderer;
	
	public DefenceShieldObj(String utilityID, float damageToStop, float damageBlockRatio, Color color) {
		super(utilityID);
		
		this.damageLeftToStop = damageToStop;
		this.totalDamageToStop = damageToStop;
		this.damageBlockRatio = Range.Constrain(damageBlockRatio, 0, 1);
		this.color = color;
		this.renderer = new ShieldRenderer(color);
	}

	@Override
	public float calculateAmountToBlock(float inputDamage) {
		this.renderer.tookDamage(inputDamage);
		
		float potentialStoppage = inputDamage * damageBlockRatio;
		float damageStopped = 0;
		
		if (damageLeftToStop >= potentialStoppage) {
			damageStopped = potentialStoppage;
			damageLeftToStop -= potentialStoppage;
		}
		else {
			damageStopped = damageLeftToStop;
			damageLeftToStop = 0;
		}
		
		return damageStopped;
	}

	@Override
	public void update() {
		renderer.update();
		renderer.setShieldHealthPercent(damageLeftToStop / totalDamageToStop);
	}

	@Override
	public void renderBehind(Artist artist) {
		renderer.renderBehind(artist);
	}

	@Override
	public void renderAbove(Artist artist) {
		renderer.renderAbove(artist);
	}

	@Override
	public boolean isDone() {
		return damageLeftToStop <= 0;
	}
	
	@Override
	public float getShieldHealthPercentage() {
		return Range.MapConstrain(damageLeftToStop, 0, totalDamageToStop, 0, 1);
	}
}
