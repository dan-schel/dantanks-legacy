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

public class DodgyShield extends Utility {
	public static final String ID = "dodgyShield";
	
	public DodgyShield() {
		super(ID);
		this.setConsumeIcon("consume");
	}

	@Override
	protected AimType createAimType() {
		return AimType.SELF;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Dodgy Shield", "Dodgy Shields");
		info.setDescription("Just barely makes a difference.");

		try { info.setIcon(new Image("utility/dodgyShield.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.SHIELDS);
		info.setPriceOfPack(1000);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		DefenceShieldObj algorithm = new DefenceShieldObj(ID, 25, 1, new Color(255, 191, 0));
		ShieldAction action = new ShieldAction(this, data, algorithm, new Color(255, 191, 0));
		return action;
	}

}
