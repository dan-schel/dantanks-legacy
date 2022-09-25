package com.danschellekens.dantanks.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.utilities.completelyHidden.*;

public class TankInventory {
	public static final int INFINITY_QUANTITY = 100000;
	public static final int MAX_STRING_NUMBER = 999;
	public static final String MAX_STRING = "999+";
	
	LinkedHashMap<String, Integer> quantities;
	
	public TankInventory(MatchOptions options) {
		quantities = new LinkedHashMap<String, Integer>();
		
		for (Entry<String, Integer> entry : options.getInventory().getInventory().entrySet()) {
			this.setUtilityQuantity(entry.getKey(), entry.getValue());
		}
		
		if (options.isStartWithSpawntimeShield()) {
			// this.addUtilities(SpawntimeShield.ID, 1);
		}
		
		// For AI use only.
		this.setUtilityQuantity(AISmallShell.ID, INFINITY_QUANTITY);
		this.setUtilityQuantity(DriveLeft.ID, INFINITY_QUANTITY);
		this.setUtilityQuantity(DriveRight.ID, INFINITY_QUANTITY);
	}
	
	public void setUtilityQuantity(String utilityID, int quantity) {
		quantities.put(utilityID, quantity);
	}
	public int getUtilityQuantity(String utilityID) {
		if (!quantities.containsKey(utilityID)) { return 0; }
		return quantities.get(utilityID);
	}
	public String getUtilityQuantityString(String utilityID) {
		return GetUtilityQuantityString(getUtilityQuantity(utilityID));
	}
	public static String GetUtilityQuantityString(int quantity) {
		if (quantity < MAX_STRING_NUMBER) {
			return Integer.toString(quantity);
		}
		else {
			return MAX_STRING;
		}
	}
	
	public void addUtilities(String utilityID, int quantity) {
		setUtilityQuantity(utilityID, getUtilityQuantity(utilityID) + quantity);
	}
	public boolean canUseUtility(String utilityID) {
		return getUtilityQuantity(utilityID) > 0;
	}
	public void useUtility(String utilityID) throws UtilityException {
		if (!canUseUtility(utilityID)) {
			throw new UtilityException("Cannot use utility '" + utilityID + "'. There are none in the player's inventory.");
		}
		if (getUtilityQuantity(utilityID) < INFINITY_QUANTITY) {
			setUtilityQuantity(utilityID, getUtilityQuantity(utilityID) - 1);
		}
	}
	public void buyUtility(Utility utility, Player player) throws CostException {
		int price = utility.getShopInfo().getPriceOfPack();
		
		// Will throw if insufficient funds.
		player.spendMoney(price);
		
		addUtilities(utility.getID(), utility.getShopInfo().getQuantityInPack());
	}
	
	public ArrayList<String> getOwnedUtilityIDs() {
		ArrayList<String> result = new ArrayList<String>();
		
		for (Entry<String, Integer> u : quantities.entrySet()) {
			try {
				if (u.getValue() > 0 && UtilityLibrary.CreateUtility(u.getKey()).getShopInfo().getCategory() != ShopCategory.COMPLETELY_HIDDEN) {
					result.add(u.getKey());
				}
			} catch (UtilityException e) {
				throw new OopsieException(e);
			}
		}
		return result;
	}
	public ArrayList<String> getOwnedUtilityIDsIncludingAIOnly() {
		ArrayList<String> result = new ArrayList<String>();
		
		for (Entry<String, Integer> u : quantities.entrySet()) {
			if (u.getValue() > 0) {
				result.add(u.getKey());
			}
		}
		return result;
	}
}
