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
import com.danschellekens.dantanks.level.actions.MultiShellAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class Wipeout extends Utility {
	public static final String ID = "wipeout";
	
	public Wipeout() {
		super(ID);
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Wipeout", "Wipeouts");
		info.setDescription("Shoots seven large shells.");
		
		try { info.setIcon(new Image("utility/wipeout.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(4000);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		MultiShellAction action = new MultiShellAction(this, data);
		action.setShellColor(new Color(240, 0, 0));
		action.setShellSize(3);
		action.setShellQuantity(7);
		action.setShellSpread(15);
		action.setGroundDamageRadius(18);
		action.setGroundDamageStrength(5);
		action.setPlayerDamageRadius(72);
		action.setMaxPlayerDamage(30);
		return action;
	}

}
