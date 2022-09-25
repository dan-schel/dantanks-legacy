package com.danschellekens.dantanks.data.aim;

import com.danschellekens.dantanks.level.tankObj.TankObj;

public class TankSelectAimData extends AimData {
	TankObj tank;
	
	public TankSelectAimData(TankObj tank) {
		super(AimType.TANK_SELECT);
		this.tank = tank;
	}
}
