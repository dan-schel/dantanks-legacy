package com.danschellekens.operations;

/**
 * Provides helpful functions for incrementing numbers.
 * @author Dan2002s
 */
public class Increment {
	
	/**
	 * Returns the input but with the increment added or subtracted to make it closer to the aim.
	 * The function will never overshoot the aim.
	 */
	public static float Linear(float input, float aim, float increment) {
		increment = Math.abs(increment);
		if (aim > input + increment) { input += increment; }
		else if (aim < input - increment) { input -= increment; }
		else { input = aim; }
		return input;
	}
	
	/**
	 * Returns the input but with the increment added or subtracted to make it further from the aim.
	 */
	public static float LinearFlee(float input, float aim, float increment) {
		increment = Math.abs(increment);
		if (aim > input) { input -= increment; }
		else { input += increment; }
		return input;
	}

	/**
	 * Returns the input passed though {@link #Linear(float, float, float) Linear} if the 
	 * increment is positive, or {@link #LinearFlee(float, float, float) LinearFlee} if the 
	 * increment is negative.
	 */
	public static float LinearHybrid(float input, float aim, float increment) {
		if (increment > 0) { return Linear(input, aim, increment); }
		if (increment < 0) { return LinearFlee(input, aim, increment); }
		return input;
	}

	/**
	 * Returns the input but with the increment added or subtracted to make it closer to the aim.
	 * The function will never overshoot the aim.
	 */
	public static int Linear(int input, int aim, int increment) {
		increment = Math.abs(increment);
		if (aim > input + increment) { input += increment; }
		else if (aim < input - increment) { input -= increment; }
		else { input = aim; }
		return input;
	}
	
	/**
	 * Returns the input but with the increment added or subtracted to make it further from 
	 * the aim.
	 */
	public static int LinearFlee(int input, int aim, int increment) {
		increment = Math.abs(increment);
		if (aim > input) { input -= increment; }
		else { input += increment; }
		return input;
	}

	/**
	 * Returns the input passed though {@link #Linear(int, int, int) Linear} if the increment 
	 * is positive, or {@link #LinearFlee(int, int, int) LinearFlee} if the increment is 
	 * negative.
	 */
	public static int LinearHybrid(int input, int aim, int increment) {
		if (increment > 0) { return Linear(input, aim, increment); }
		if (increment < 0) { return LinearFlee(input, aim, increment); }
		return input;
	}
	
	/**
	 * Returns the input but with the multiplying factor used to move it exponentially closer 
	 * to the aim. The value will increment more slowly the closer it gets to the aim, producing 
	 * a 'smoothening' effect.
	 */
	public static float Exponential(float input, float aim, float factor) {
		factor = Numbers.Smaller(Math.abs(factor), 1);
		input += (aim - input) * factor;
		return input;
	}
	
	/**
	 * Returns the input but with the multiplying factor used to move it exponentially further 
	 * from the aim. The value will increment more quickly the further it gets from the aim, 
	 * producing a 'acceleration' effect.
	 */
	public static float ExponentialFlee(float input, float aim, float factor) {
		factor = -Numbers.Smaller(Math.abs(factor), 1);
		input += (aim - input) * factor;
		return input;
	}
	
	/**
	 * Returns the input passed though {@link #Exponential(float, float, float) Exponential} 
	 * if the factor is positive, or {@link #ExponentialFlee(float, float, float) ExponentialFlee}
	 * if the factor is negative.
	 */
	public static float ExponentialHybrid(float input, float aim, float factor) {
		if (factor > 0) { return Exponential(input, aim, factor); }
		if (factor < 0) { return ExponentialFlee(input, aim, factor); }
		return input;
	}
	
}
