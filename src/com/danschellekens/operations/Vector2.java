package com.danschellekens.operations;

/**
 * Stores a position or direction in 2D space.
 * @author Dan2002s
 */
public class Vector2 {
	float x;
	float y;
	
	/**
	 * Creates a new Vector2.
	 */
	public Vector2(float x, float y) {
		setX(x);
		setY(y);
	}

	/**
	 * Returns the X of the vector.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the X of the vector.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the Y of the vector.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the Y of the vector.
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Returns a clone of the vector.
	 */
	public Vector2 clone() {
		return new Vector2(this.getX(), this.getY());
	}
	
	/**
	 * Returns a vector rounded to the specified amount of decimal places.
	 */
	public Vector2 round(int decimalPlaces) {
		return new Vector2(
			Numbers.Round(this.getX(), decimalPlaces),
			Numbers.Round(this.getY(), decimalPlaces)
		);
	}
	
	/**
	 * Returns the input added to this vector.
	 */
	public Vector2 add(Vector2 input) {
		float newX = this.x + input.getX();
		float newY = this.y + input.getY();
		return new Vector2(newX, newY);
	}
	
	/**
	 * Returns the input subtracted from this vector.
	 */
	public Vector2 subtract(Vector2 input) {
		float newX = this.x - input.getX();
		float newY = this.y - input.getY();
		return new Vector2(newX, newY);
	}
	
	/**
	 * Returns this vector multiplied by the input.
	 */
	public Vector2 multiply(float input) {
		float newX = this.x * input;
		float newY = this.y * input;
		return new Vector2(newX, newY);
	}
	
	/**
	 * Returns this vector divided by the input.
	 */
	public Vector2 divide(float input) {
		float newX = this.x / input;
		float newY = this.y / input;
		return new Vector2(newX, newY);
	}

	
	/**
	 * Returns the magnitude (length) of the vector.
	 */
	public float magnitude() {
		return (float) Math.sqrt(magnitudeSquared());
	}
	
	/**
	 * Returns the magnitude (length) of the vector, before it is squared rooted.
	 * This method is faster if the real length isn't needed.
	 */
	public float magnitudeSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	/**
	 * Returns the vector with a magnitude of 1.
	 */
	public Vector2 normalize() {
		if (magnitude() == 0) {
			return new Vector2(0, 0);
		}
		return new Vector2(this.x / magnitude(), this.y / magnitude());
	}
	
	/**
	 * Returns the vector with a magnitude of the input.
	 */
	public Vector2 limit(float input) {
		return normalize().multiply(input);
	}
	
	/**
	 * Returns the angle of the vector (in radians).
	 */
	public float headingRadians() {
		return Radians.Atan2(this.y, this.x);
	}
	
	/**
	 * Returns the angle of the vector (in degrees).
	 */
	public float headingDegrees() {
		return Radians.ToDegrees(headingRadians());
	}
	
	/**
	 * Returns the vector by an angle (in radians).
	 */
	public Vector2 rotateRadians(float radians) {
		float angle = this.headingRadians() + radians;
		float magnitude = this.magnitude();
		return PolarCoordsRadians(angle, magnitude);
	}
	
	/**
	 * Returns the vector by an angle (in degrees).
	 */
	public Vector2 rotateDegrees(float degrees) {
		return rotateRadians(Degrees.ToRadians(degrees));
	}
	
	/**
	 * Returns the dot product of this vector and the input vector.
	 */
	public float dotProduct(Vector2 input) {
		return this.x * input.x + this.y * input.y;
	}
	
	/**
	 * Returns the vector in an x, y formatted string.
	 */
	@Override
	public String toString() {
		return Converter.String(this.x) + ", " + Converter.String(this.y);
	}
	

	/**
	 * Creates a vector with polar coordinates (angle in radians).
	 */
	public static Vector2 PolarCoordsRadians(float radians, float magnitude) {
		float x = Radians.Cos(radians) * magnitude;
		float y = Radians.Sin(radians) * magnitude;
		return new Vector2(x, y);
	}
	
	/**
	 * Creates a vector with polar coordinates (angle in degrees).
	 */
	public static Vector2 PolarCoordsDegrees(float degrees, float magnitude) {
		return PolarCoordsRadians(Degrees.ToRadians(degrees), magnitude);
	}
	
	/**
	 * Returns the vector with the shortest magnitude.
	 */
	public static Vector2 ShortestMagnitude(Vector2... vectors) {
		float shortest = 0;
		Vector2 winner = null;
		
		for (Vector2 v : vectors) {
			if (winner == null) {
				shortest = v.magnitudeSquared(); 
				winner = v; 
				continue;
			}
			if (v.magnitudeSquared() < shortest) { 
				shortest = v.magnitudeSquared(); 
				winner = v;
			}
		}
		
		return winner;
	}

	/**
	 * Returns the distance between the two vectors.
	 */
	public static float Distance(Vector2 n, Vector2 epicenter) {
		return n.subtract(epicenter).magnitude();
	}
}
