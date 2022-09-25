package com.danschellekens.dantanks.data.aim;

public class LevelPositionAimData extends AimData {
	float x;
	float y;
	
	public LevelPositionAimData(float x, float y) {
		super(AimType.LEVEL_POSITION);
		
		this.x = x;
		this.y = y;
	}

}
