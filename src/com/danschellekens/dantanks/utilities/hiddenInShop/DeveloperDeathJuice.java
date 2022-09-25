package com.danschellekens.dantanks.utilities.hiddenInShop;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.UtilityShopInfo;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AimType;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.actions.PlagueAction;
import com.danschellekens.dantanks.level.actions.UtilityAction;

public class DeveloperDeathJuice extends Utility {
	public static final String ID = "developerDeathJuice";
	
	public DeveloperDeathJuice() {
		super(ID);
		
		this.setConsumeIcon("consume");
	}

	@Override
	protected AimType createAimType() {
		return AimType.SELF;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Developer Death Juice", "Developer Death Juice");
		info.setDescription("Deaths everyone to death.");
		
		try { info.setIcon(new Image("utility/developerDeathJuice.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.HIDDEN_IN_SHOP);
		info.setPriceOfPack(0);
		info.setQuantityInPack(1);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		PlagueAction action = new PlagueAction(this, data);
		return action;
	}

}
