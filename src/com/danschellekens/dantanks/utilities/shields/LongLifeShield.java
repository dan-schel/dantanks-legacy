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
import com.danschellekens.dantanks.level.shieldObj.LongLifeShieldObj;

public class LongLifeShield extends Utility {
	public static final String ID = "longLifeShield";
	
	public LongLifeShield() {
		super(ID);
		this.setConsumeIcon("consume");
	}

	@Override
	protected AimType createAimType() {
		return AimType.SELF;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Long Life Shield", "Long Life Shields");
		info.setDescription("Blocks 70% of all damage. Never breaks.");

		try { info.setIcon(new Image("utility/longLifeShield.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.SHIELDS);
		info.setPriceOfPack(4000);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		LongLifeShieldObj algorithm = new LongLifeShieldObj(ID, 0.7f, new Color(255, 40, 255));
		ShieldAction action = new ShieldAction(this, data, algorithm, new Color(255, 40, 255));
		return action;
	}

}
