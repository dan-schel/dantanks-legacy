package com.danschellekens.dantanks.data.values;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.exceptions.UtilityException;
import com.danschellekens.dantanks.utilities.misc.RepairKit;
import com.danschellekens.dantanks.utilities.weapons.*;

public enum StartingInventory {
	DEFAULT(
			"Default",
			CreateDescriptionString(CreateDefaultInventory()),
			CreateDefaultInventory()
	),
	NO_REPAIR_KITS(
			"No Repair Kits",
			CreateDescriptionString(CreateNoRepairKitsInventory()),
			CreateNoRepairKitsInventory()
	),
	VOLCANO_BOMBS(
			"Volcano Bombs",
			CreateDescriptionString(CreateVolcanoBombsInventory()),
			CreateVolcanoBombsInventory()
	),
	ROBOT(
			"AI's Selection",
			CreateDescriptionString(CreateRobotInventory()),
			CreateRobotInventory()
	),
	DEVELOPER(
			"Developer Inventory",
			CreateDescriptionString(CreateDeveloperInventory()),
			CreateDeveloperInventory()
	);
	
	final String displayName;
	final String description;
	final LinkedHashMap<String, Integer> inventory;
	StartingInventory(String displayName, String description, LinkedHashMap<String, Integer> inventory) {
		this.displayName = displayName;
		this.description = description;
		this.inventory = inventory;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public String getDescription() {
		return description;
	}
	public LinkedHashMap<String, Integer> getInventory() {
		return inventory;
	}

	static LinkedHashMap<String, Integer> CreateDefaultInventory() {
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		result.put(SmallShell.ID, TankInventory.INFINITY_QUANTITY);
		result.put(RepairKit.ID, 4);
		return result;
	}
	static LinkedHashMap<String, Integer> CreateNoRepairKitsInventory() {
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		result.put(SmallShell.ID, TankInventory.INFINITY_QUANTITY);
		return result;
	}
	static LinkedHashMap<String, Integer> CreateVolcanoBombsInventory() {
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		result.put(SmallShell.ID, TankInventory.INFINITY_QUANTITY);
		result.put(RepairKit.ID, 4);
		result.put(VolcanoBomb.ID, 4);
		return result;
	}
	static LinkedHashMap<String, Integer> CreateRobotInventory() {
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		result.put(SmallShell.ID, TankInventory.INFINITY_QUANTITY);
		result.put(RepairKit.ID, 4);
		result.put(TripleShell.ID, 6);
		return result;
	}
	static LinkedHashMap<String, Integer> CreateDeveloperInventory() {
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		for (Utility u : UtilityLibrary.GetAllUtilities()) {
			result.put(u.getID(), TankInventory.INFINITY_QUANTITY);
		}
		
		return result;
	}
	
	static String CreateDescriptionString(LinkedHashMap<String, Integer> inventory) {
		String result = "";
		
		int things = 0;
		
		for (Entry<String, Integer> entry : inventory.entrySet()) {
			if (things > 4) {
				result += "..., ";
				break;
			}
			
			try {
				Utility utility = UtilityLibrary.CreateUtility(entry.getKey());
				String utilityName = utility.getShopInfo().getRelevantDisplayName(entry.getValue());
				String quantityString = TankInventory.GetUtilityQuantityString(entry.getValue());
				
				if (utility.getShopInfo().getCategory() == ShopCategory.COMPLETELY_HIDDEN) { continue; }
				
				result += quantityString + " " + utilityName + ", ";
			}
			catch (UtilityException e) {
				throw new OopsieException(e);
			}
			things++;
		}
		
		result += "{END}";
		return result.replace(", {END}", "");
	}
}
