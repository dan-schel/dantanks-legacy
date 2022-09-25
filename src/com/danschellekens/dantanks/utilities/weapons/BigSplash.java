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
import com.danschellekens.dantanks.level.actions.FragsAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class BigSplash extends Utility {
	public static final String ID = "bigSplash";
	
	public BigSplash() {
		super(ID);
		
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Big Splash", "Big Splashes");
		info.setDescription("A Volcano Bomb... but better.");
		
		try { info.setIcon(new Image("utility/bigSplash.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(8000);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		FragsAction action = new FragsAction(this, data);
		action.setPrimaryShellColor(new Color(0, 50, 240));
		action.setPrimaryShellSize(3);
		action.setPrimaryShellExplosionEffectSize(6);
		action.setFragsColor(new Color(0, 0, 0));
		action.setFragsSize(2);
		action.setFragsGroundDamageRadius(12);
		action.setFragsGroundDamageStrength(1);
		action.setFragsPlayerDamageRadius(48);
		action.setFragsMaxPlayerDamage(20);
		action.setFragsFireAngle(-10, 10);
		action.setFragsFirePower(3, 8);
		action.setFragsQuantity(20);
		
		return action;
	}

}
