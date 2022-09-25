package com.danschellekens.dantanks.utilities.hiddenInShop;

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

public class DeveloperShell extends Utility {
	public static final String ID = "developerShell";
	
	public DeveloperShell() {
		super(ID);
		
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Developer Shell", "Developer Shells");
		info.setDescription("Digs a big hole. Harmless to players.");
		
		try { info.setIcon(new Image("utility/developerShell.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.HIDDEN_IN_SHOP);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		ShellAction action = new ShellAction(this, data);
		action.setShellColor(new Color(255, 0, 255));
		action.setShellSize(3);
		action.setGroundDamageRadius(120);
		action.setGroundDamageStrength(100);
		action.setPlayerDamageRadius(48);
		action.setMaxPlayerDamage(0);
		return action;
	}

}
