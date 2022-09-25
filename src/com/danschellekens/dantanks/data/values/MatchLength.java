package com.danschellekens.dantanks.data.values;

import com.danschellekens.dantanks.exceptions.OopsieException;

public enum MatchLength {
	EXPRESS(
			"Express", 
			1,
			new int[] { 0 },
			new int[] { 0 },
			new int[] { 0 },
			new int[] { 0 },
			new int[] { 1000 },
			new int[] { 0 }
	),
	SHORT(
			"Short", 
			3,
			new int[] { 200, 2000, 4000 },
			new int[] { 200, 2000, 4000 },
			new int[] { 2000, 6000, 10000 },
			new int[] { 0, 0, 0 },
			new int[] { 1000, 1000, 1000 },
			new int[] { 0, 0, 0 }
	),
	REGULAR(
			"Regular", 
			6,
			new int[] { 200, 1000, 2000, 4000, 4000, 4000 },
			new int[] { 200, 2000, 4000, 4000, 4000, 4000 },
			new int[] { 2000, 4000, 4000, 6000, 8000, 10000 },
			new int[] { 0, 0, 0, 0, 0, 0 },
			new int[] { 1000, 1000, 1000, 1000, 1000, 1000 },
			new int[] { 0, 0, 0, 0, 0, 0 }
	),
	LONG(
			"Long", 
			10,
			new int[] { 200, 1000, 2000, 4000, 4000, 4000, 4000, 4000, 4000, 4000 },
			new int[] { 200, 2000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000 },
			new int[] {  2000, 4000, 4000, 6000, 6000, 6000, 8000, 8000, 10000, 10000 },
			new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			new int[] { 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000 },
			new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
	);
	
	final String displayName;
	final int amountOfRounds;
	final int[] damageMoney_Flat;
	final int[] damageMoney_Gradient;
	final int[] passiveMoney_Flat;
	final int[] passiveMoney_Gradient;
	final int[] points_Flat;
	final int[] points_Gradient;
	
	MatchLength(String displayName, int amountOfRounds, int[] damageMoney_Flat, 
			int[] damageMoney_Gradient, int[] passiveMoney_Flat, int[] passiveMoney_Gradient,
			int[] points_Flat, int[] points_Gradient) {
		
		this.displayName = displayName;
		this.amountOfRounds = amountOfRounds;
		
		if (amountOfRounds != damageMoney_Flat.length) { throw new OopsieException("Arrays should have one entry for each round."); }
		if (amountOfRounds != damageMoney_Gradient.length) { throw new OopsieException("Arrays should have one entry for each round."); }
		if (amountOfRounds != passiveMoney_Flat.length) { throw new OopsieException("Arrays should have one entry for each round."); }
		if (amountOfRounds != passiveMoney_Gradient.length) { throw new OopsieException("Arrays should have one entry for each round."); }
		if (amountOfRounds != points_Flat.length) { throw new OopsieException("Arrays should have one entry for each round."); }
		if (amountOfRounds != points_Gradient.length) { throw new OopsieException("Arrays should have one entry for each round."); }
		
		this.damageMoney_Flat = damageMoney_Flat;
		this.damageMoney_Gradient = damageMoney_Gradient;
		this.passiveMoney_Flat = passiveMoney_Flat;
		this.passiveMoney_Gradient = passiveMoney_Gradient;
		this.points_Flat = points_Flat;
		this.points_Gradient = points_Gradient;
	}
	public int[] getRoundDamageMoneyWorths(int playerCount) {
		int[] result = new int[this.amountOfRounds];
		for (int i = 0; i < this.amountOfRounds; i++) {
			result[i] = damageMoney_Flat[i] + damageMoney_Gradient[i] * playerCount;
		}
		return result;
	}
	public int[] getRoundPassiveMoneyWorths(int playerCount) {
		int[] result = new int[this.amountOfRounds];
		for (int i = 0; i < this.amountOfRounds; i++) {
			result[i] = passiveMoney_Flat[i] + passiveMoney_Gradient[i] * playerCount;
		}
		return result;
	}
	public int[] getRoundPointWorths(int playerCount) {
		int[] result = new int[this.amountOfRounds];
		for (int i = 0; i < this.amountOfRounds; i++) {
			result[i] = points_Flat[i] + points_Gradient[i] * playerCount;
		}
		return result;
	}
	public String getDisplayName() {
		return displayName;
	}
	public int getAmountOfRounds() {
		return amountOfRounds;
	}
}
