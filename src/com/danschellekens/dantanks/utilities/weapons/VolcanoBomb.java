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

public class VolcanoBomb extends Utility {
	public static final String ID = "volcanoBomb";
	
	public VolcanoBomb() {
		super(ID);
		
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Volcano Bomb", "Volcano Bombs");
		info.setDescription("Creates explosive frags on impact.");
		
		try { info.setIcon(new Image("utility/volcanoBomb.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(1200);
		info.setQuantityInPack(3);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		FragsAction action = new FragsAction(this, data);
		action.setPrimaryShellColor(new Color(0, 0, 0));
		action.setPrimaryShellSize(3);
		action.setPrimaryShellExplosionEffectSize(6);
		action.setFragsColor(new Color(0, 0, 0));
		action.setFragsSize(2);
		action.setFragsGroundDamageRadius(12);
		action.setFragsGroundDamageStrength(1);
		action.setFragsPlayerDamageRadius(48);
		action.setFragsMaxPlayerDamage(20);
		action.setFragsFireAngle(-30, 30);
		action.setFragsFirePower(3, 4);
		action.setFragsQuantity(5);
		
		return action;
	}

}
