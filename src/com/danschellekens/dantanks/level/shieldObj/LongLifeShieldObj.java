package com.danschellekens.dantanks.level.shieldObj;

import org.newdawn.slick.Color;

import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;

public class LongLifeShieldObj extends ShieldObj {
	final float damageBlockRatio;
	final Color color;
	final ShieldRenderer renderer;
	
	public LongLifeShieldObj(String utilityID, float damageBlockRatio, Color color) {
		super(utilityID);
		
		this.color = color;
		this.damageBlockRatio = Range.Constrain(damageBlockRatio, 0, 1);
		this.renderer = new ShieldRenderer(color);
	}

	@Override
	public float calculateAmountToBlock(float inputDamage) {
		this.renderer.tookDamage(inputDamage);
		return inputDamage * damageBlockRatio;
	}
	
	@Override
	public void update() {
		renderer.update();
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
		return false;
	}
	
	@Override
	public float getShieldHealthPercentage() {
		return 0f;
	}
}
