package com.danschellekens.dantanks.utilities.weapons;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.UtilityShopInfo;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AimType;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.actions.SprayAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class Spray extends Utility {
	public static final String ID = "spray";
	
	public Spray() {
		super(ID);	
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Spray Shot", "Spray Shots");
		info.setDescription("Fires multiple shells in succession.");
		
		try { info.setIcon(new Image("utility/spray.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(3200);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		SprayAction action = new SprayAction(this, data);
		action.setShellColor(new Color(0, 0, 0));
		action.setShellSize(2);
		action.setShellQuantity(10);
		action.setShellSpawnTime(5);
		action.setShellAngleRandomness(5);
		action.setGroundDamageRadius(12);
		action.setGroundDamageStrength(1);
		action.setPlayerDamageRadius(48);
		action.setMaxPlayerDamage(20);
		return action;
	}

}
