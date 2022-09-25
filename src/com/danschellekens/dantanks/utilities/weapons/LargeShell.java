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
import com.danschellekens.dantanks.level.actions.ShellAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class LargeShell extends Utility {
	public static final String ID = "largeShell";
	
	public LargeShell() {
		super(ID);
		
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Large Shell", "Large Shells");
		info.setDescription("Higher damage than Small Shell.");
		
		try { info.setIcon(new Image("utility/largeShell.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(500);
		info.setQuantityInPack(3);
		
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		ShellAction action = new ShellAction(this, data);
		action.setEndsTurn(true);
		
		action.setShellColor(new Color(240, 0, 0));
		action.setShellSize(3);
		action.setGroundDamageRadius(18);
		action.setGroundDamageStrength(5);
		action.setPlayerDamageRadius(72);
		action.setMaxPlayerDamage(30);
		return action;
	}

}
