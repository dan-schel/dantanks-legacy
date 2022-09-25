package com.danschellekens.dantanks.utilities.misc;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.UtilityShopInfo;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AimType;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.actions.RepairKitAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class RepairKit extends Utility {
	public static final String ID = "repairKit";
	
	public RepairKit() {
		super(ID);
		
		this.setConsumeIcon("consume");
	}

	@Override
	protected AimType createAimType() {
		return AimType.SELF;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Repair Kit", "Repair Kits");
		info.setDescription("Heals 10 HP.");
		
		try { info.setIcon(new Image("utility/repairKit.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.MISCELLENEOUS);
		info.setPriceOfPack(1000);
		info.setQuantityInPack(5);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		RepairKitAction action = new RepairKitAction(this, data);
		action.setHealthRepaired(10);
		return action;
	}

}
