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

public class HeavyShower extends Utility {
	public static final String ID = "heavyShower";
	
	public HeavyShower() {
		super(ID);	
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Heavy Shower", "Heavy Showers");
		info.setDescription("Never runs out of hot water.");
		
		try { info.setIcon(new Image("utility/heavyShower.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(16000);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		SprayAction action = new SprayAction(this, data);
		action.setShellColor(new Color(240, 0, 0));
		action.setShellSize(3);
		action.setShellQuantity(20);
		action.setShellSpawnTime(3);
		action.setShellAngleRandomness(10);
		
		action.setGroundDamageRadius(18);
		action.setGroundDamageStrength(5);
		action.setPlayerDamageRadius(72);
		action.setMaxPlayerDamage(30);
		return action;
	}

}
