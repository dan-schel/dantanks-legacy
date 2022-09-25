package com.danschellekens.dantanks.data.aim;

public abstract class AimData {
	AimType type;
	
	public AimData(AimType type) {
		this.type = type;
	}

	public AimType getType() {
		return type;
	}

	public void setType(AimType type) {
		this.type = type;
	}
}
