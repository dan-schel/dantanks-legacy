package com.danschellekens.slick2d.collisions;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.operations.Numbers;
import com.danschellekens.operations.Vector2;

public class RectUtils {
	public static Rectangle TwoPoints(float x1, float y1, float x2, float y2) {
		float minX = Numbers.Smaller(x1, x2);
		float maxX = Numbers.Larger(x1, x2);
		float minY = Numbers.Smaller(y1, y2);
		float maxY = Numbers.Larger(y1, y2);
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
	
	public static Rectangle Clone(Rectangle input) {
		return new Rectangle(input.getX(), input.getY(), input.getWidth(), input.getHeight());
	}
	
	public static Rectangle Round(Rectangle input, int decimalPlaces) {
		return new Rectangle(
			Numbers.Round(input.getX(), decimalPlaces), 
			Numbers.Round(input.getY(), decimalPlaces), 
			Numbers.Round(input.getWidth(), decimalPlaces), 
			Numbers.Round(input.getHeight(), decimalPlaces)
		);
	}
	
	public static Rectangle Extend(Rectangle input, float up, float down, float left, float right) {
		Rectangle result = Clone(input);
		
		result.setWidth(result.getWidth() + right);
		result.setHeight(result.getHeight() + down);
		
		result.setX(result.getX() - left);
		result.setWidth(result.getWidth() + left);
		result.setY(result.getY() - up);
		result.setHeight(result.getHeight() + up);
		
		return result;
	}
	
	public static Rectangle Extend(Rectangle input, Vector2 vector) {
		float up = 0;
		float down = 0;
		float left = 0;
		float right = 0;
		
		if (vector.getX() < 0) { left = Math.abs(vector.getX()); }
		if (vector.getX() > 0) { right = Math.abs(vector.getX()); }
		if (vector.getY() < 0) { up = Math.abs(vector.getY()); }
		if (vector.getY() > 0) { down = Math.abs(vector.getY()); }
		
		return Extend(input, up, down, left, right);
	}
	
	public static Rectangle Translate(Rectangle input, Vector2 vector) {
		Rectangle result = Clone(input);
		
		result.setX(result.getX() + vector.getX());
		result.setY(result.getY() + vector.getY());
		
		return result;
	}
	public static Rectangle SetPosition(Rectangle input, Vector2 vector) {
		Rectangle result = Clone(input);
		
		result.setX(vector.getX());
		result.setY(vector.getY());
		
		return result;
	}

	public static boolean Overlapping(Rectangle a, Rectangle b, int precision) {
		float aX1 = a.getX();
		float aX2 = a.getX() + a.getWidth();
		float aY1 = a.getY();
		float aY2 = a.getY() + a.getHeight();
		float bX1 = b.getX();
		float bX2 = b.getX() + b.getWidth();
		float bY1 = b.getY();
		float bY2 = b.getY() + b.getHeight();
		
		return (Numbers.Ceil(aX1, precision) < Numbers.Floor(bX2, precision) && 
				Numbers.Floor(aX2, precision) > Numbers.Ceil(bX1, precision) && 
				Numbers.Ceil(aY1, precision) < Numbers.Floor(bY2, precision) && 
				Numbers.Floor(aY2, precision) > Numbers.Ceil(bY1, precision));
	}
}
