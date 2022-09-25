package com.danschellekens.operations;

import java.text.DecimalFormat;

/**
 * Provides helpful functions for converting from strings to numbers.
 * @author Dan2002s
 */
public class Converter {

	/**
	 * Returns the input converted to a float, on failure the function will return backup.
	 */
	public static float FloatOrDefault(String input, float backup) {
		try {
			return Float.parseFloat(input);
		}
		catch (NumberFormatException e) {
			return backup;
		}
	}
	
	/**
	 * Returns the input converted to a float, on failure the function will return 0.
	 */
	public static float FloatOrZero(String input) {
		try {
			return Float.parseFloat(input);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Returns the input converted to a int, on failure the function will return backup.
	 */
	public static int IntOrDefault(String input, int backup) {
		try {
			return Integer.parseInt(input);
		}
		catch (NumberFormatException e) {
			return backup;
		}
	}
	
	/**
	 * Returns the input converted to a int, on failure the function will return 0.
	 */
	public static int IntOrZero(String input) {
		try {
			return Integer.parseInt(input);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Returns the input converted to a string rounded to 2 decimal places.
	 */
	public static String String(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return decimalFormat.format(value);
	}
	
	public static String StringUnits(double value, UnitRuleset ruleSet) {
		double denomination = ruleSet.getDenomination(value);
		
		return String(value / denomination) + ruleSet.getUnit(denomination);
	}
}
