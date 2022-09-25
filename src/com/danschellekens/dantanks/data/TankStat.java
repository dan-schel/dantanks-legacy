package com.danschellekens.dantanks.data;

import org.newdawn.slick.Image;

import com.danschellekens.dantanks.exceptions.UpgradeException;

public class TankStat {
	final String ID;
	final String displayName;
	final String description;
	final Image icon;
	
	final int[] costs;
	final float[] values;
	
	public TankStat(String ID, String displayName, String description, Image icon, int[] costs, float[] values) throws UpgradeException {
		if (costs.length == 0) { throw new UpgradeException("Cannot create '" + ID + "' stats. There should be at least one entry in the costs and values arrays."); }
		if (costs.length != values.length) { throw new UpgradeException("Cannot create '" + ID + "' stats. Costs and values array should be same length as each other."); }
		
		this.ID = ID;
		this.displayName = displayName;
		this.description = description;
		this.icon = icon;
		
		this.costs = costs;
		this.values = values;
	}
	
	public int getMaxLevel() {
		return costs.length;
	}
	public int getCost(int level) throws UpgradeException {
		if (level > getMaxLevel()) { throw new UpgradeException("Cannot get cost of level '" + level + "'. Stat '" + ID + "' only has " + getMaxLevel() + " level(s)."); }
		if (level < 1) { throw new UpgradeException("Cannot get cost of level '" + level + "'. Level numbers start at 1."); }
		return this.costs[level - 1];
	}
	public float getValue(int level) throws UpgradeException {
		if (level > getMaxLevel()) { throw new UpgradeException("Cannot get value of level '" + level + "'. Stat '" + ID + "' only has " + getMaxLevel() + " level(s)."); }
		if (level < 1) { throw new UpgradeException("Cannot get value of level '" + level + "'. Level numbers start at 1."); }
		return this.values[level - 1];
	}

	public String getID() {
		return ID;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getDescription() {
		return description;
	}
	public int[] getCosts() {
		return costs;
	}
	public float[] getValues() {
		return values;
	}
	public Image getIcon() {
		return icon;
	}
}
