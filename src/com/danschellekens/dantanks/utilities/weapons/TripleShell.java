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

public class TripleShell extends Utility {
	public static final String ID = "tripleShell";
	
	public TripleShell() {
		super(ID);	
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Triple Shell", "Triple Shells");
		info.setDescription("Shoots three small shells.");
		
		try { info.setIcon(new Image("utility/tripleShell.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(600);
		info.setQuantityInPack(3);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		MultiShellAction action = new MultiShellAction(this, data);
		action.setShellColor(new Color(0, 0, 0));
		action.setShellSize(2);
		action.setShellQuantity(3);
		action.setShellSpread(10);
		action.setGroundDamageRadius(12);
		action.setGroundDamageStrength(1);
		action.setPlayerDamageRadius(48);
		action.setMaxPlayerDamage(20);
		return action;
	}

}
