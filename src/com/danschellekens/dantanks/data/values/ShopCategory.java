package com.danschellekens.dantanks.data.values;

public enum ShopCategory {
	UPGRADES("Upgrades"),
	WEAPONS("Weapons"),
	SHIELDS("Shields"),
	MISCELLENEOUS("Miscelleneous"),
	HIDDEN_IN_SHOP("(Hidden)"),
	COMPLETELY_HIDDEN("(AI only)");
	
	String displayName;
	
	ShopCategory(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}	
}