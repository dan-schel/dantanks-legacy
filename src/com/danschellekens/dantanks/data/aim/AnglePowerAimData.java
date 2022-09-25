package com.danschellekens.dantanks.data.aim;

import com.danschellekens.dantanks.data.Tank;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.operations.Range;

public class AnglePowerAimData extends AimData {
	float angle;
	float power;
	
	public AnglePowerAimData(float angle, float power, TankObj tankObj) {
		super(AimType.ANGLE_POWER);
		
		setAngle(angle);
		setPower(power, tankObj);
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = Range.Constrain(angle, Tank.MIN_ANGLE, Tank.MAX_ANGLE);
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power, TankObj tankObj) {
		this.power = Range.Constrain(power, 0, tankObj.getMaxPowerAvailable());
	}
}
