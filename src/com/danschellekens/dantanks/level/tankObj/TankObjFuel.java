package com.danschellekens.dantanks.level.tankObj;

import com.danschellekens.dantanks.data.Tank;
import com.danschellekens.operations.Range;

public class TankObjFuel {
	final float fuelMax;
	float fuel;
	
	public TankObjFuel(Tank tank) {
		this.fuelMax = tank.getFuelTankStatValue();
		setFuel(tank.getFuelTankStatValue());
	}
	
	void setFuel(float fuel) {
		this.fuel = Range.Constrain(fuel, 0, this.fuelMax);
	}
	
	public float current() {
		return fuel;
	}
	public float max() {
		return fuelMax;
	}
	
	public boolean canConsume(float fuel) {
		return current() >= fuel;
	}
	public void consume(float fuel) {
		setFuel(current() - fuel);
	}
	public boolean tryConsume(float fuel) {
		boolean result = canConsume(fuel);
		consume(fuel);
		return result;
	}
	public boolean isEmpty() {
		return current() == 0;
	}
}
