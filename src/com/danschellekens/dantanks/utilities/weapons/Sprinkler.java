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
import com.danschellekens.dantanks.level.actions.SprinklerAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class Sprinkler extends Utility {
	public static final String ID = "sprinkler";
	
	public Sprinkler() {
		super(ID);	
	}

	@Override
	protected AimType createAimType() {
		return AimType.NO_TARGET;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Sprinkler", "Sprinklers");
		info.setDescription("Sprinkles death to nearby tanks.");
		
		try { info.setIcon(new Image("utility/sprinkler.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.WEAPONS);
		info.setPriceOfPack(2500);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		SprinklerAction action = new SprinklerAction(this, data);
		action.setShellColor(new Color(0, 0, 0));
		action.setShellSize(2);
		action.setShellQuantity(8);
		action.setShellSpawnTime(5);
		action.setShellAngleMin(10);
		action.setShellAngleMax(80);
		action.setShellPower(4);
		action.setGroundDamageRadius(12);
		action.setGroundDamageStrength(1);
		action.setPlayerDamageRadius(48);
		action.setMaxPlayerDamage(20);
		return action;
	}

}
