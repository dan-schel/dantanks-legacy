package com.danschellekens.dantanks.data;

import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AimType;
import com.danschellekens.dantanks.exceptions.UtilityException;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public abstract class Utility {
	final String ID;
	
	UtilityShopInfo shopInfo;
	AimType aimType;
	String consumeIcon;
	
	public Utility(String ID) {
		this.ID = ID;
		this.consumeIcon = "fire";
		
		this.shopInfo = createShopInfo();
		this.aimType = createAimType();
	}

	protected abstract AimType createAimType();
	protected abstract UtilityShopInfo createShopInfo();
	protected abstract UtilityAction createAction(AimData data);
	
	public UtilityAction getAction(AimData data) throws UtilityException {
		if (data.getType() != this.aimType) {
			throw new UtilityException("Cannot get action of utility '" + this.ID + "'. This utility requires '" + this.aimType.toString() + "' aim data, thus data of type '" + data.getType().toString() + "' is invalid.");
		}
		
		return createAction(data);
	}
	
	public UtilityShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(UtilityShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public AimType getAimType() {
		return aimType;
	}
	public void setAimType(AimType aimType) {
		this.aimType = aimType;
	}
	public String getConsumeIcon() {
		return consumeIcon;
	}
	public void setConsumeIcon(String consumeIcon) {
		this.consumeIcon = consumeIcon;
	}

	public String getID() {
		return ID;
	}
}
