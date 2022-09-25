package com.danschellekens.dantanks.utilities.completelyHidden;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.UtilityShopInfo;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AimType;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.actions.DriveAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class DriveLeft extends Utility {
	public static final String ID = "driveLeft";
	
	public DriveLeft() {
		super(ID);
		
		this.setConsumeIcon("consume");
	}

	@Override
	protected AimType createAimType() {
		return AimType.SELF;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Drive Left (AI)", "Drive Lefts (AI)");
		info.setDescription("For AI use only.");
		
		try { info.setIcon(new Image("utility/unknown.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.COMPLETELY_HIDDEN);
		info.setPriceOfPack(0);
		info.setQuantityInPack(0);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		DriveAction action = new DriveAction(this, data);
		action.setDriveAmount(-200);
		return action;
	}

}
