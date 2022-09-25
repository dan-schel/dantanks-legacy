package com.danschellekens.dantanks.data;

import com.danschellekens.dantanks.data.values.TankStats;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.operations.Numbers;
import com.danschellekens.operations.Range;

public class Tank {
	public static final float MIN_ANGLE = -90;
	public static final float MAX_ANGLE = 90;
	public static final float MAX_HEALTH_BEFORE_POWER_LOSS = 40;
	
	public static final float DRIVE_SPEED = 1;
	public static final float FUEL_USAGE = 0.2f;
	
	public static final TankStat HEALTH_UPGRADE = TankStats.CreateHealthStat();
	public static final TankStat POWER_UPGRADE = TankStats.CreatePowerStat();
	public static final TankStat FUEL_TANK_UPGRADE = TankStats.CreateFuelTankStat();
	public static final TankStat CLIMB_UPGRADE = TankStats.CreateClimbStat();
	
	final Match match;
	final String playerID;
	
	TankStatLevelManager healthStat;
	TankStatLevelManager powerStat;
	TankStatLevelManager fuelTankStat;
	TankStatLevelManager climbStat;
	
	final TankInventory inventory;
	
	public Tank(String playerID, Match match) {
		this.match = match;
		this.playerID = playerID;
		
		healthStat = new TankStatLevelManager(HEALTH_UPGRADE);
		powerStat = new TankStatLevelManager(POWER_UPGRADE);
		fuelTankStat = new TankStatLevelManager(FUEL_TANK_UPGRADE);
		climbStat = new TankStatLevelManager(CLIMB_UPGRADE);
		
		inventory = new TankInventory(match.getOptions());
	}
	
	public Player getOwner() throws MissingPlayerException {
		Player[] players = match.getPlayers();
		
		for (Player p : players) {
			if (p.getID().equals(this.playerID)) {
				return p;
			}
		}
		
		throw new MissingPlayerException("This Tank's owner cannot be found. No player in the current match has the id '" + this.playerID + "'.");
	}
	
	public float getHealthStatValue() {
		try {
			return HEALTH_UPGRADE.getValue(healthStat.getLevel());
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
	}
	public float getPowerStatValue() {
		try {
			return POWER_UPGRADE.getValue(powerStat.getLevel());
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
	}
	public float getFuelTankStatValue() {
		try {
			return FUEL_TANK_UPGRADE.getValue(fuelTankStat.getLevel());
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
	}
	public float getClimbStatValue() {
		try {
			return CLIMB_UPGRADE.getValue(climbStat.getLevel());
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
	}
	public TankStatLevelManager[] getStats() {
		return new TankStatLevelManager[] { healthStat, powerStat, fuelTankStat, climbStat };
	}
	public TankInventory getInventory() {
		return inventory;
	}

	public static float GetPowerPercentAvailable(float health, float maxHealth) {
		return Range.MapConstrain(health, 0, Numbers.Smaller(maxHealth, MAX_HEALTH_BEFORE_POWER_LOSS), 0, 1);
	}
}
