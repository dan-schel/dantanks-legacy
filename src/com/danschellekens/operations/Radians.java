package com.danschellekens.operations;

/**
 * Provides helpful functions for dealing with angles.
 * @author Dan2002s
 *
 */
public class Radians {
	
	public static final float PI = (float) Math.PI;
	public static final float HALF_PI = PI / 2;
	public static final float QUARTER_PI = PI / 4;
	public static final float TWO_PI = PI * 2;
	
	/**
	 * Returns the input converted to degrees.
	 */
	public static float ToDegrees(double radians) {
		return Degrees.Loop(radians / PI * 180);
	}
	
	/**
	 * Returns the input converted to degrees without looping the output.
	 */
	public static float ToDegreesNoLoop(double radians) {
		return (float) (radians / PI * 180);
	}
	
	/**
	 * Returns this angle looped around to make sure it is somewhere between 0 
	 * and 2 PI. The value of the angle doesn't change though.
	 */
	public static float Loop(double radians) {
		return (float) ((radians % TWO_PI + TWO_PI) % TWO_PI);
	}
	
	/**
	 * Finds the closest unlooped equivalent of targetAngle to the input.
	 */
	public static float AngleClosest(double input, double targetAngle) {
		float angle = Loop(input);
		float target = Loop(targetAngle);
		
		float standard = Numbers.Distance(angle, target);
		float upperLoop = Numbers.Distance(angle, target + TWO_PI);
		float lowerLoop = Numbers.Distance(angle, target - TWO_PI);
		
		if (Numbers.SmallerThan(upperLoop, standard, lowerLoop)) { 
			return target + TWO_PI;
		}
		if (Numbers.SmallerThan(lowerLoop, upperLoop, standard)) { 
			return target - TWO_PI;
		}
		return target;
	}
	
	/**
	 * Finds the quickest way to get from input to targetAngle taking looping into account.
	 * Returns -1 if the quickest way is to go negatively.
	 * Returns 1 is the quickest way is to be positively.
	 * Returns 0 if the angles match.
	 */
	public static float DirectionClosest(double input, double targetAngle) {
		float angle = Loop(input);
		return Numbers.RequiredIncrement(angle, AngleClosest(input, targetAngle));
	}
	
	/**
	 * Returns the distance to the quickest way to get from input to targetAngle taking 
	 * looping into account.
	 */
	public static float DistanceClosest(double input, double targetAngle) {
		float angle = Loop(input);
		return Numbers.Distance(angle, AngleClosest(input, targetAngle));
	}
	
	/**
	 * Returns the sine of the angle.
	 */
	public static float Sin(double angle) {
		return (float) Math.sin(angle);
	}

	/**
	 * Returns the cosine of the angle.
	 */
	public static float Cos(double angle) {
		return (float) Math.cos(angle);
	}
	
	/**
	 * Returns the tangent of the angle.
	 */
	public static float Tan(double angle) {
		return (float) Math.tan(angle);
	}
	
	/**
	 * Returns the angle of the coordinates.
	 */
	public static float Atan2(double y, double x) {
		return Loop(Math.atan2(y, x));
	}
	
}
