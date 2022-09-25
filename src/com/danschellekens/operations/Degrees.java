package com.danschellekens.operations;

/**
 * Provides helpful functions for dealing with angles.
 * @author Dan2002s
 *
 */
public class Degrees {
	
	/**
	 * Returns the input converted to radians.
	 */
	public static float ToRadians(double degrees) {
		return Radians.Loop(degrees / 180 * Radians.PI);
	}
	
	/**
	 * Returns the input converted to radians without looping the output.
	 */
	public static float ToRadiansNoLoop(double degrees) {
		return (float) (degrees / 180 * Radians.PI);
	}
	
	/**
	 * Returns this angle looped around to make sure it is somewhere between 0 
	 * and 360. The value of the angle doesn't change though.
	 */
	public static float Loop(double degrees) {
		return (float) ((degrees % 360 + 360) % 360);
	}
	
	/**
	 * Finds the closest unlooped equivalent of targetAngle to the input.
	 */
	public static float AngleClosest(double input, double targetAngle) {
		return Radians.ToDegreesNoLoop(Radians.AngleClosest(ToRadians(input), ToRadians(targetAngle)));
	}
	
	/**
	 * Finds the quickest way to get from input to targetAngle taking looping into account.
	 * Returns -1 if the quickest way is to go negatively.
	 * Returns 1 is the quickest way is to be positively.
	 * Returns 0 if the angles match.
	 */
	public static float DirectionClosest(double input, double targetAngle) {
		return Radians.DirectionClosest(ToRadians(input), ToRadians(targetAngle));
	}
	
	/**
	 * Returns the distance to the quickest way to get from input to targetAngle taking 
	 * looping into account.
	 */
	public static float DistanceClosest(double input, double targetAngle) {
		return Radians.ToDegreesNoLoop(Radians.DistanceClosest(ToRadians(input), ToRadians(targetAngle)));
	}

	/**
	 * Returns the sine of the angle.
	 */
	public static float Sin(double angle) {
		return Radians.Sin(ToRadians(angle));
	}

	/**
	 * Returns the cosine of the angle.
	 */
	public static float Cos(double angle) {
		return Radians.Cos(ToRadians(angle));
	}
	
	/**
	 * Returns the tangent of the angle.
	 */
	public static float Tan(double angle) {
		return Radians.Tan(ToRadians(angle));
	}
	
	/**
	 * Returns the angle of the coordinates.
	 */
	public static float Atan2(double y, double x) {
		return Radians.ToDegrees(Radians.Atan2(y, x));
	}
	
}