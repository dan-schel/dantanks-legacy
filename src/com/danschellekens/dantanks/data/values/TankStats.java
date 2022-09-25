package com.danschellekens.dantanks.data.values;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.TankStat;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.exceptions.UpgradeException;

public class TankStats {
	public static final String HEALTH_STAT_ID = "health";
	public static final String POWER_STAT_ID = "power";
	public static final String FUEL_TANK_STAT_ID = "fuelTank";
	public static final String CLIMB_STAT_ID = "climb";
	
	public static TankStat CreateHealthStat() {
		try {
			return new TankStat(
				HEALTH_STAT_ID,
				"Upgrade Health",
				"Allows you to resist more damage.",
				new Image("upgrade/health.png"),
				new int[] { 0, 3000, 5000, 8000, 12000, 16000, 20000 }, 
				new float[] { 40, 50, 65, 85, 110, 140, 180 }
			);
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	public static TankStat CreatePowerStat() {
		try {
			return new TankStat(
				POWER_STAT_ID,
				"Upgrade Firing Power",
				"Allows you to fire further.",
				new Image("upgrade/power.png"),
				new int[] { 0, 500, 2000, 5000, 10000 }, 
				new float[] { 8, 9, 10, 11, 12 }
			);
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	public static TankStat CreateFuelTankStat() {
		try {
			return new TankStat(
				FUEL_TANK_STAT_ID,
				"Upgrade Fuel Capacity",
				"Allows you to drive further.",
				new Image("upgrade/fuel.png"),
				new int[] { 0, 2000, 4000, 6000, 8000 }, 
				new float[] { 50, 70, 100, 150, 250 }
			);
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	public static TankStat CreateClimbStat() {
		try {
			return new TankStat(
				CLIMB_STAT_ID, 
				"Upgrade Hill Climbing Abilities",
				"Allows you to climb steeper hills.",
				new Image("upgrade/climb.png"),
				new int[] { 0, 2000, 4000 }, 
				new float[] { 0.5f, 0.9f, 1.5f });
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
}
