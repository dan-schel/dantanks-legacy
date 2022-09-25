package com.danschellekens.dantanks.level.projectile;

import com.danschellekens.operations.Vector2;

public class QuadraticFunction {
	public float a, b, c;
	public QuadraticFunction(float a, float b, float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public float calculateYValue(float x) {
		return a * x * x + b * x + c;
	}
	public static QuadraticFunction FromThreePoints(Vector2 point1, Vector2 point2, Vector2 point3) {
		float x1 = point1.getX();
		float y1 = point1.getY();
		float x2 = point2.getX();
		float y2 = point2.getY();
		float x3 = point3.getX();
		float y3 = point3.getY();
		
		float A1 = -(x1 * x1) + (x2 * x2);
		float B1 = -x1 + x2;
		float D1 = -y1 + y2;
		
		float A2 = -(x2 * x2) + (x3 * x3);
		float B2 = -x2 + x3;
		float D2 = -y2 + y3;
		
		float BMultiplier = -(B2/B1);
		float A3 = BMultiplier * A1 + A2;
		float D3 = BMultiplier * D1 + D2;
		
		float a = D3 / A3;
		float b = (D1 - A1 * a) / B1;
		float c = y1 - a * x1 * x1 - b * x1;
		
		return new QuadraticFunction(a, b, c);
	}
}