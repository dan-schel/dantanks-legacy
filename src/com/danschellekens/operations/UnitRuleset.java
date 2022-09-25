package com.danschellekens.operations;

import java.util.HashMap;

/**
 * Stores the rule for rounding to a particular set of units.
 * @author Dan2002s
 */
public class UnitRuleset {
	HashMap<Double, String> units; 
	
	/**
	 * Creates a new UnitRuleset.
	 */
	public UnitRuleset() {
		this.units = new HashMap<Double, String>();
	}
	
	/**
	 * Adds a new unit to the rule set.
	 */
	public void addUnit(String symbol, double value) {
		units.put(value, symbol);
	}
	
	/**
	 * Returns the most appropriate denomination.
	 */
	public double getDenomination(double input) {
		double closestV = -1;
		
		for (double v : units.keySet()) {
			if (closestV < 0 || (closestV < v && input >= v)) {
				closestV = v;
			}
		}
		
		return closestV;
	}
	
	/**
	 * Returns the match unit for a denomination.
	 */
	public String getUnit(double denomination) {
		return units.get(denomination);
	}

	/**
	 * Return a rule set for general numbers, using the initials. (k for thousand, m for million, etc.)
	 * <br>Base Unit: 1
	 */
	public static UnitRuleset GeneralInitial() {
		UnitRuleset units = new UnitRuleset();
		units.addUnit("", 1);
		units.addUnit("k", 1000);
		units.addUnit("m", 1000000);
		units.addUnit("b", 1000000000);
		units.addUnit("t", 1000000000000d);
		units.addUnit("q", 1000000000000000d);
		
		return units;
	}
	
	/**
	 * Return a rule set for general numbers. (thousand, million, etc.)
	 * <br>Base Unit: 1
	 */
	public static UnitRuleset General() {
		UnitRuleset units = new UnitRuleset();
		units.addUnit("", 1);
		units.addUnit(" thousand", 1000);
		units.addUnit(" million", 1000000);
		units.addUnit(" billion", 1000000000);
		units.addUnit(" trillion", 1000000000000d);
		units.addUnit(" quadrillion", 1000000000000000d);
		
		return units;
	}
	
	/**
	 * Return a rule set for metric lengths. (mm, m, etc.)
	 * <br>Base Unit: m (metres)
	 */
	public static UnitRuleset Length() {
		UnitRuleset units = new UnitRuleset();
		
		units.addUnit("nm", 0.000000001);
		units.addUnit("um", 0.000001);
		units.addUnit("mm", 0.001);
		units.addUnit("m", 1);
		units.addUnit("km", 1000);
		
		return units;
	}
	
	/**
	 * Return a rule set for metric lengths including centimeters. (cm, m, etc.)
	 * <br>Base Unit: m (metres)
	 */
	public static UnitRuleset LengthWithCm() {
		UnitRuleset units = new UnitRuleset();
		
		units.addUnit("nm", 0.000000001);
		units.addUnit("um", 0.000001);
		units.addUnit("mm", 0.001);
		units.addUnit("cm", 0.01);
		units.addUnit("m", 1);
		units.addUnit("km", 1000);
		
		return units;
	}
	
	/**
	 * Return a rule set for metric weights. (g, kg, etc.)
	 * <br>Base Unit: g (grams)
	 */
	public static UnitRuleset Weight() {
		UnitRuleset units = new UnitRuleset();
		
		units.addUnit("ng", 0.000000001);
		units.addUnit("ug", 0.000001);
		units.addUnit("mg", 0.001);
		units.addUnit("g", 1);
		units.addUnit("kg", 1000);
		
		return units;
	}
	
	/**
	 * Return a rule set for time. (m, h, etc.)
	 * <br>Base Unit: s (seconds)
	 */
	public static UnitRuleset Time() {
		UnitRuleset units = new UnitRuleset();
		
		units.addUnit("ns", 0.000000001);
		units.addUnit("us", 0.000001);
		units.addUnit("ms", 0.001);
		units.addUnit("s", 1);
		units.addUnit("m", 60);
		units.addUnit("h", 60*60);
		units.addUnit("d", 60*60*24);
		units.addUnit("wks", 60*60*24*7);
		units.addUnit("yrs", 60*60*24*365);
		
		return units;
	}
	
	/**
	 * Return a rule set for imperial lengths. (in, ft, etc.)
	 * <br>Base Unit: m (metres)
	 */
	public static UnitRuleset ImperialLength() {
		UnitRuleset units = new UnitRuleset();
		
		units.addUnit("in", 0.0254);
		units.addUnit("ft", 0.3048);
		units.addUnit("yd", 0.9144);
		units.addUnit("mi", 1609.344);
		
		return units;
	}
}
