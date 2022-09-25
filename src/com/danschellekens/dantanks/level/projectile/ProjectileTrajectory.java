package com.danschellekens.dantanks.level.projectile;

import com.danschellekens.operations.Degrees;
import com.danschellekens.operations.Vector2;

public class ProjectileTrajectory {
	public static float velocityX(float angle, float power) {
		return power * Degrees.Sin(angle);
	}
	public static float velocityY(float angle, float power) {
		return power * -Degrees.Cos(angle);
	}
	public static Vector2 velocity(float angle, float power) {
		return new Vector2(velocityX(angle, power), velocityY(angle, power));
	}
	public static Vector2 accelerate(Vector2 velocity, float windConstant, float gravityConstant) {
		return velocity.add(new Vector2(windConstant, gravityConstant));
	}
	
	public static float positionX(float xStart, float angle, float power, float time, float windConstant) {
		return xStart + velocityX(angle, power) * time + 0.5f * windConstant * time * time;
	}
	public static float positionY(float yStart, float angle, float power, float time, float gravityConstant) {
		return yStart + velocityY(angle, power) * time + 0.5f * gravityConstant * time * time;
	}
	public static Vector2 position(Vector2 initialPosition, float angle, float power, float time, float windConstant, float gravityConstant) {
		return new Vector2(
				positionX(initialPosition.getX(), angle, power, time, windConstant), 
				positionY(initialPosition.getY(), angle, power, time, gravityConstant)
		);
	}
}
