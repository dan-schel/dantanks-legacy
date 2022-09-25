package com.danschellekens.dantanks.data;

import com.danschellekens.dantanks.data.values.MatchLength;
import com.danschellekens.dantanks.data.values.StartingInventory;

public class MatchOptions {
	MatchLength length;
	StartingInventory inventory;
	boolean startWithSpawntimeShield;
	
	public MatchOptions() {
		this.length = MatchLength.REGULAR;
		this.inventory = StartingInventory.DEFAULT;
		this.startWithSpawntimeShield = false;
	}

	public MatchLength getLength() {
		return length;
	}
	public void setLength(MatchLength length) {
		this.length = length;
	}
	public boolean isStartWithSpawntimeShield() {
		return startWithSpawntimeShield;
	}
	public void setStartWithSpawntimeShield(boolean startWithSpawntimeShield) {
		this.startWithSpawntimeShield = startWithSpawntimeShield;
	}
	public StartingInventory getInventory() {
		return inventory;
	}
	public void setInventory(StartingInventory inventory) {
		this.inventory = inventory;
	}
}
