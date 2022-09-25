package com.danschellekens.dantanks.utilities.shields;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.UtilityShopInfo;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AimType;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.actions.ShieldAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;
import com.danschellekens.dantanks.level.shieldObj.DefenceShieldObj;

public class UltraShield extends Utility {
	public static final String ID = "ultraShield";
	
	public UltraShield() {
		super(ID);
		this.setConsumeIcon("consume");
	}

	@Override
	protected AimType createAimType() {
		return AimType.SELF;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Ultra Shield", "Ultra Shields");
		info.setDescription("Blocks 180 HP of damage.");

		try { info.setIcon(new Image("utility/ultraShield.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.SHIELDS);
		info.setPriceOfPack(6000);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		DefenceShieldObj algorithm = new DefenceShieldObj(ID, 180, 1, new Color(0, 255, 255));
		ShieldAction action = new ShieldAction(this, data, algorithm, new Color(0, 255, 255));
		return action;
	}

}
