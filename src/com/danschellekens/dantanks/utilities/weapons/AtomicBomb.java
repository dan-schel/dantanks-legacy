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

public class AtomicBomb extends Utility {
	public static final String ID = "atomicBomb";
	
	public AtomicBomb() {
		super(ID);
		
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Atomic Bomb", "Atomic Bombs");
		info.setDescription("Basically a 'Skip Level' button.");
		
		try { info.setIcon(new Image("utility/atomicBomb.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(30000);
		info.setQuantityInPack(1);
		
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		ShellAction action = new ShellAction(this, data);
		action.setEndsTurn(true);
		
		action.setShellColor(new Color(0, 0, 0));
		action.setShellSize(4);
		action.setGroundDamageRadius(200);
		action.setGroundDamageStrength(50);
		action.setPlayerDamageRadius(1000);
		action.setMaxPlayerDamage(200);
		return action;
	}

}
