package com.danschellekens.dantanks.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.exceptions.UtilityException;
import com.danschellekens.dantanks.utilities.completelyHidden.*;
import com.danschellekens.dantanks.utilities.hiddenInShop.DeveloperDeathJuice;
import com.danschellekens.dantanks.utilities.hiddenInShop.DeveloperShell;
import com.danschellekens.dantanks.utilities.misc.RepairKit;
import com.danschellekens.dantanks.utilities.shields.*;
import com.danschellekens.dantanks.utilities.weapons.*;

public class UtilityLibrary {
	static LinkedHashMap<String, Class<?>> utilities = InitUtilities();
	
	static LinkedHashMap<String, Class<?>> InitUtilities() {
		LinkedHashMap<String, Class<?>> map = new LinkedHashMap<String, Class<?>>();
		
		map.put(SmallShell.ID, SmallShell.class);
		map.put(LargeShell.ID, LargeShell.class);
		map.put(VolcanoBomb.ID, VolcanoBomb.class);
		map.put(RepairKit.ID, RepairKit.class);
		map.put(DeveloperDeathJuice.ID, DeveloperDeathJuice.class);
		map.put(TripleShell.ID, TripleShell.class);
		map.put(Wipeout.ID, Wipeout.class);
		map.put(BigSplash.ID, BigSplash.class);
		map.put(AtomicBomb.ID, AtomicBomb.class);
		map.put(DodgyShield.ID, DodgyShield.class);
		map.put(LongLifeShield.ID, LongLifeShield.class);
		map.put(UltraShield.ID, UltraShield.class);
		map.put(DeveloperShell.ID, DeveloperShell.class);
		map.put(Spray.ID, Spray.class);
		map.put(Sprinkler.ID, Sprinkler.class);
		map.put(HeavyShower.ID, HeavyShower.class);
		
		map.put(AISmallShell.ID, AISmallShell.class);
		map.put(DriveLeft.ID, DriveLeft.class);
		map.put(DriveRight.ID, DriveRight.class);
		
		LinkedHashMap<String, Class<?>> sortedList = SortedByPrice(map);
		
		return sortedList;
	}
	static LinkedHashMap<String, Class<?>> SortedByPrice(LinkedHashMap<String, Class<?>> input) {
		List<Map.Entry<String, Class<?>>> entries = new ArrayList<Map.Entry<String, Class<?>>>(input.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Class<?>>>() {
			@Override
			public int compare(Entry<String, Class<?>> entry1, Entry<String, Class<?>> entry2) {
				try {
					Utility utility1 = (Utility) entry1.getValue().newInstance();
					Utility utility2 = (Utility) entry2.getValue().newInstance();
					Integer price1 = utility1.getShopInfo().getPriceOfPack();
					Integer price2 = utility2.getShopInfo().getPriceOfPack();
					
					return price1.compareTo(price2);
				}
				catch (InstantiationException | IllegalAccessException e) {
					throw new OopsieException(e);
				}
			}		  
		});
		
		LinkedHashMap<String, Class<?>> sortedMap = new LinkedHashMap<String, Class<?>>();
		for (Map.Entry<String, Class<?>> entry : entries) {
		  sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
	
	public static Utility CreateUtility(String utilityID) throws UtilityException {
		if (!utilities.containsKey(utilityID)) {
			throw new UtilityException("Cannot create utility '" + utilityID + "'. No utility with that ID is in the library.");
		}
		
		Class<?> utilityClass = utilities.get(utilityID);
		try {
			Utility utility = (Utility) utilityClass.newInstance();
			return utility;
		} 
		catch (InstantiationException | IllegalAccessException e) {
			throw new OopsieException(e);
		}
	}
	
	public static ArrayList<Utility> GetUtilitiesForCategory(ShopCategory category) {
		ArrayList<Utility> result = new ArrayList<Utility>();
		
		for (String id : utilities.keySet()) {
			try {	
				Utility utility = CreateUtility(id);
				if (utility.getShopInfo().getCategory() == category) {
					result.add(utility);
				}
			}
			catch (UtilityException e) { throw new OopsieException(e); }
		}
		
		return result;
	}
	public static ArrayList<Utility> GetAllUtilities() {
		ArrayList<Utility> result = new ArrayList<Utility>();
		
		for (String id : utilities.keySet()) {
			try {	
				Utility utility = CreateUtility(id);
				result.add(utility);
			}
			catch (UtilityException e) { throw new OopsieException(e); }
		}
		
		return result;
	}
}
