package com.danschellekens.dantanks.level.shieldObj;

import com.danschellekens.slick2d.graphics.Artist;

public abstract class ShieldObj {
	String utilityID;
	public ShieldObj(String utilityID) {
		this.utilityID = utilityID;
	}
	
	public abstract float calculateAmountToBlock(float inputDamage);
	public abstract void update();
	public abstract void renderBehind(Artist artist);
	public abstract void renderAbove(Artist artist);
	public abstract boolean isDone();
	public abstract float getShieldHealthPercentage();
	
	public String getUtilityID() {
		return utilityID;
	}
}
