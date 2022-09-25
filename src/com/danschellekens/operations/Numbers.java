package com.danschellekens.operations;


/**
 * Provides helpful functions for dealing with numbers in general.
 * @author Dan2002s
 */
public class Numbers {

	/**
	 * Returns whichever out of a or b is smaller.
	 */
	public static float Smaller(float a, float b) {
		if (a < b) { return a; }
		return b;
	}
	
	/**
	 * Returns whichever out of a or b is smaller.
	 */
	public static int Smaller(int a, int b) {
		if (a < b) { return a; }
		return b;
	}
	
	/**
	 * Returns whichever out of a or b is larger.
	 */
	public static float Larger(float a, float b) {
		if (a > b) { return a; }
		return b;
	}
	
	/**
	 * Returns whichever out of a or b is larger.
	 */
	public static int Larger(int a, int b) {
		if (a > b) { return a; }
		return b;
	}
	
	/**
	 * Returns true if none of the others provided are smaller than the input.
	 */
	public static boolean SmallerThan(float input, float... others) {
		for (float other : others) {
			if (other < input) { return false; }
		}
		
		return true;
	}
	
	/**
	 * Returns true if none of the others provided are smaller than the input.
	 */
	public static boolean SmallerThan(int input, int... others) {
		for (float other : others) {
			if (other < input) { return false; }
		}
		
		return true;
	}
	
	/**
	 * Returns true if none of the others provided are larger than the input.
	 */
	public static boolean LargerThan(int input, int... others) {
		for (float other : others) {
			if (other > input) { return false; }
		}
		
		return true;
	}
	
	/**
	 * Returns true if none of the others provided are larger than the input.
	 */
	public static boolean LargerThan(float input, float... others) {
		for (float other : others) {
			if (other > input) { return false; }
		}
		
		return true;
	}
	
	/**
	 * Returns the distance between two numbers.
	 */
	public static float Distance(float a, float b) {
		return Math.abs(a - b);
	}

	/**
	 * Returns the distance between two numbers.
	 */
	public static int Distance(int a, int b) {
		return Math.abs(a - b);
	}
	
	/**
	 * Returns the required increment's sign (either -1, 0 or 1) for input to get to target.
	 */
	public static int RequiredIncrement(float input, float target) {
		if (input < target) { return 1; }
		if (input > target) { return -1; }
		return 0;
	}

	/**
	 * Returns the required increment's sign (either -1, 0 or 1) for input to get to target.
	 */
	public static int RequiredIncrement(int input, int target) {
		if (input < target) { return 1; }
		if (input > target) { return -1; }
		return 0;
	}

	/**
	 * Returns the sign (either -1, 0 or 1) of the input.
	 */
	public static int Sign(int input) {
		if (input > 0) { return 1; }
		if (input < 0) { return -1; }
		return 0;
	}
	
	/**
	 * Returns the sign (either -1, 0 or 1) of the input.
	 */
	public static int Sign(float input) {
		if (input > 0) { return 1; }
		if (input < 0) { return -1; }
		return 0;
	}
	
	/**
	 * Returns the input rounded to a certain amount of decimal places. Use a negative amount
	 * of decimal places to round numbers to the nearest 10, 100, 1000, etc.
	 */
	public static float Round(float input, int decimalPlaces) {
		return (float) (Math.round(input * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));
	}
	
	/**
	 * Returns the input rounded to 0 decimal places.
	 */
	public static int Round(float input) {
		return (int) Round(input, 0);
	}
	
	/**
	 * Returns the input ceilinged to a certain amount of decimal places. Use a negative amount
	 * of decimal places to round numbers to the nearest 10, 100, 1000, etc.
	 */
	public static float Ceil(float input, int decimalPlaces) {
		return (float) (Math.ceil(input * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));
	}
	
	/**
	 * Returns the input ceilinged to 0 decimal places.
	 */
	public static int Ceil(float input) {
		return (int) Ceil(input, 0);
	}
	
	/**
	 * Returns the input floored to a certain amount of decimal places. Use a negative amount
	 * of decimal places to round numbers to the nearest 10, 100, 1000, etc.
	 */
	public static float Floor(float input, int decimalPlaces) {
		return (float) (Math.floor(input * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));
	}
	
	/**
	 * Returns the input floored to 0 decimal places.
	 */
	public static int Floor(float input) {
		return (int) Floor(input, 0);
	}

	/**
	 * Returns the input as a positive number.
	 */
	public static float Abs(float input) {
		return Math.abs(input);
	}
	
	/**
	 * Returns the input as a positive number.
	 */
	public static int Abs(int input) {
		return Math.abs(input);
	}
}


