package com.danschellekens.dantanks.game.ai;

import java.util.ArrayList;
import java.util.Arrays;

import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AnglePowerAimData;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.actions.UtilityAction;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.operations.Random;

public class AIHelper {
	public static boolean UseUtility(Player player, Level level, Utility utility, AimData aim) throws AIException {
		try {
			TankObj tankObj = level.getTankFromOwner(player.getID());
			
			if (aim instanceof AnglePowerAimData) {
				AnglePowerAimData anglePowerAim = (AnglePowerAimData) aim;
				
				tankObj.setAngle(anglePowerAim.getAngle());
				tankObj.setPower(anglePowerAim.getPower());
			}
			
			UtilityAction action = utility.getAction(aim);
			
			if (action.shouldRun(level, level.getTankFromOwner(player.getID()))) {
				player.getTank().getInventory().useUtility(utility.getID());
				level.addAction(action, player.getID());
				
				if (!action.isEndsTurn()) {
					return false;
				}
				return true;
			}
			
			return false;
		}
		catch (UtilityException | MissingPlayerException e) {
			throw new AIException("Failed to use utility. " + e.getMessage());
		}
	}
	public static boolean BuyIfCan(Player player, String utilityID) throws UtilityException {
		try {
			Utility utility = UtilityLibrary.CreateUtility(utilityID);
			
			if (!player.canAfford(utility.getShopInfo().getPriceOfPack())) {
				return false;
			}
			
			TankInventory manager = player.getTank().getInventory();
			manager.buyUtility(utility, player);
			return true;
		}
		catch (CostException e) {
			throw new OopsieException(e);
		}
	}
	public static Utility ChooseRandomUtility(String[] optionsUtilityID, TankInventory manager, String backupUtility) throws UtilityException, AIException {
		ArrayList<String> pool = new ArrayList<String>(Arrays.asList(optionsUtilityID));
		
		while (pool.size() > 0) {
			String utilityID = Random.ListElement(pool);
			
			if (manager.canUseUtility(utilityID)) {
				return UtilityLibrary.CreateUtility(utilityID);
			}
			else {
				pool.remove(utilityID);
			}
		}
		
		if (manager.canUseUtility(backupUtility)) {
			return UtilityLibrary.CreateUtility(backupUtility);
		}
		else {
			throw new AIException("'" + backupUtility + "' is always expected to be" + 
					" available for AI to use.");
		}
	}
}
