package com.danschellekens.dantanks.utilities.completelyHidden;

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

public class AISmallShell extends Utility {
	public static final String ID = "aiSmallShell";
	
	public AISmallShell() {
		super(ID);
		
	}

	@Override
	protected AimType createAimType() {
		return AimType.ANGLE_POWER;
	}

	@Override
	protected UtilityShopInfo createShopInfo() {
		UtilityShopInfo info = new UtilityShopInfo("Small Shell (AI)", "Small Shells (AI)");
		info.setDescription("For AI use only.");
		
		try { info.setIcon(new Image("utility/unknown.png")); }
		catch (SlickException e) { throw new OopsieException(e); }
		
		info.setCategory(ShopCategory.COMPLETELY_HIDDEN);
		return info;
	}

	@Override
	protected UtilityAction createAction(AimData data) {
		ShellAction action = new ShellAction(this, data);
		action.setShellColor(new Color(0, 0, 0));
		action.setShellSize(2);
		action.setGroundDamageRadius(12);
		action.setGroundDamageStrength(1);
		action.setPlayerDamageRadius(48);
		action.setMaxPlayerDamage(20);
		return action;
	}

}
