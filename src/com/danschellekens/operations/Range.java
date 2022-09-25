package com.danschellekens.operations;

/**
 * Provides helpful functions for dealing with numbers in ranges.
 * @author Dan2002s
 */
public class Range {
	
	/**
	 * Returns the input after running checks to keep it inbetween the values of a and b.
	 */
	public static float Constrain(float input, float a, float b) {
		float min = Numbers.Smaller(a, b);
		float max = Numbers.Larger(a, b);
		if (input > max) { input = max; }
		if (input < min) { input = min; }
		return input;
	}
	
	/**
	 * Returns the input after running checks to keep it inbetween the values of a and b.
	 */
	public static int Constrain(int input, int a, int b) {
		int min = Numbers.Smaller(a, b);
		int max = Numbers.Larger(a, b);
		if (input > max) { input = max; }
		if (input < min) { input = min; }
		return input;
	}
	
	/**
	 * Returns true if the input is between a and b.
	 */
	public static boolean IsWithin(float input, float a, float b) {
		float min = Numbers.Smaller(a, b);
		float max = Numbers.Larger(a, b);
		return (input >= min && max >= input);
	}
	
	/**
	 * Returns the input remapped from being within one set of ranges to another.
	 * @param aIn - The first value in the input range.
	 * @param bIn - The second value in the input range.
	 * @param aOut - The first value in the output range.
	 * @param bOut - The second value in the output range.
	 */
	public static float Map(float input, float aIn, float bIn, float aOut, float bOut) {
		return aOut + (bOut - aOut) * ((input - aIn) / (bIn - aIn));
	}
	
	/**
	 * Returns the input remapped from being within one set of ranges to another, making sure
	 * the result is between aOut and bOut.
	 * @param aIn - The first value in the input range.
	 * @param bIn - The second value in the input range.
	 * @param aOut - The minimum value in the output range.
	 * @param bOut - The maximum value in the output range.
	 */
	public static float MapConstrain(float input, float aIn, float bIn, float aOut, float bOut) {
		return Constrain(Map(input, aIn, bIn, aOut, bOut), aOut, bOut);
	}
	
	/**
	 * Returns the input as a percentage in decimal form.
	 * @param a - The 0% value.
	 * @param b - The 100% value.
	 */
	public static float Percent(float input, float a, float b) {
		return Map(input, a, b, 0, 1);
	}
	
	/**
	 * Returns the input as a percentage in decimal form making sure that the result is always
	 * between 0 and 1.
	 * @param a - The 0% value.
	 * @param b - The 100% value.
	 */
	public static float PercentConstrain(float input, float a, float b) {
		return MapConstrain(input, a, b, 0, 1);
	}
	
}
