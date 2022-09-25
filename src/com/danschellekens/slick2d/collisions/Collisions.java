package com.danschellekens.slick2d.collisions;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.operations.Vector2;

public class Collisions {
	public static final int ACCURACY = 4;
	
	public Collisions() {
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<Collidable> GetOverlapping(ArrayList<? extends Collidable> allObjs, Rectangle space) {
		space = RectUtils.Round(space, ACCURACY);
		
		ArrayList<Collidable> result = new ArrayList<Collidable>();
		for (Collidable b : allObjs) {
			Rectangle them = RectUtils.Round(b.getCollisionHitbox(), ACCURACY);
			if (RectUtils.Overlapping(them, space, ACCURACY)) { result.add(b); }
		}
		return result;
	}
	public static ArrayList<Collidable> GetOverlapping(ArrayList<? extends Collidable> allObjs, Collidable exception, Rectangle space) {
		ArrayList<Collidable> collisions = GetOverlapping(allObjs, space);
		if (collisions.contains(exception)) {
			collisions.remove(exception);
		}
		return collisions;
	}
	public static ArrayList<Collidable> GetCollisions(ArrayList<? extends Collidable> allObjs, Rectangle space) {
		ArrayList<Collidable> collisions = GetOverlapping(allObjs, space);
		Iterator<Collidable> i = collisions.iterator();
		while (i.hasNext()) {
			Collidable c = i.next();
			if (!c.isCollisionSolid()) {
				i.remove();
			}
		}
		return collisions;
	}
	public static ArrayList<Collidable> GetCollisions(ArrayList<? extends Collidable> allObjs, Collidable exception, Rectangle space) {
		ArrayList<Collidable> collisions = GetCollisions(allObjs, space);
		if (collisions.contains(exception)) {
			collisions.remove(exception);
		}
		return collisions;
	}
	
	public static Vector2 GetAvailableMovement(Collidable obj, ArrayList<? extends Collidable> allObjs, Vector2 attemptedMovement) {
		if (!obj.isCollisionSolid()) { return attemptedMovement; }
		
		Rectangle startingHitbox = obj.getCollisionHitbox();
		ArrayList<Collidable> intersecting = GetCollisions(allObjs, obj, startingHitbox);
		if (!intersecting.isEmpty()) {
			System.out.println("Physics error.");
			return new Vector2(0, 0);
		}
		
		float x = GetAvailableX(obj, allObjs, attemptedMovement.getX());
		float y = GetAvailableY(obj, allObjs, x, attemptedMovement.getY());
		return new Vector2(x, y);
	}
	public static float GetAvailableX(Collidable obj, ArrayList<? extends Collidable> allObjs, float attemptedX) {
		Rectangle oldHitbox = obj.getCollisionHitbox();
		Rectangle newHitbox = RectUtils.Translate(oldHitbox, new Vector2(attemptedX, 0));
		
		// Get everything intersecting the new hitbox.
		ArrayList<Collidable> intersecting = GetCollisions(allObjs, obj, newHitbox);
		
		if (intersecting.isEmpty()) {
			// Nothing in the way of where I want to go.
			return attemptedX;
		}
		
		// Decide how far forward I can move.
		float availableSpace = attemptedX;
		for (Collidable o : intersecting) {
			// Loop through everything my desired new hitbox was intersecting with.
			
			Rectangle theirHitbox = o.getCollisionHitbox();
			
			if (attemptedX > 0) {
				// If trying to move right:
				// Find the leftmost edge of their hitbox.
				float theirLeftEdge = theirHitbox.getX();
				
				// Find the rightmost edge of my hitbox.
				float myRightEdge = oldHitbox.getX() + oldHitbox.getWidth();
				
				// Find how much space there is between my right edge and their left edge.
				// This value should be positive if you are moving left.
				float distance = theirLeftEdge - myRightEdge;
				
				// If the available space is smaller than previously thought, set it
				// accordingly.
				if (distance < availableSpace) { availableSpace = distance; }
			}
			else {
				// If trying to move left:
				// Find the rightmost edge of their hitbox.
				float theirRightEdge = theirHitbox.getX() + theirHitbox.getWidth();
				
				// Find the leftmost edge of my hitbox.
				float myLeftEdge = oldHitbox.getX();
				
				// Find how much space there is between my left edge and their right edge.
				// This value should be negative if you are moving left.
				float distance = theirRightEdge - myLeftEdge;
				
				// If the available space is smaller (negatively) than previously thought, 
				// set it accordingly.
				if (distance > availableSpace) { availableSpace = distance; }
			}
		}
		return availableSpace;
	}
	
	public static float GetAvailableY(Collidable obj, ArrayList<? extends Collidable> allObjs, float movedX, float attemptedY) {
		Rectangle oldHitbox = RectUtils.Translate(obj.getCollisionHitbox(), new Vector2(movedX, 0));
		Rectangle newHitbox = RectUtils.Translate(oldHitbox, new Vector2(0, attemptedY));
		
		// Get everything intersecting the new hitbox.
		ArrayList<Collidable> intersecting = GetCollisions(allObjs, obj, newHitbox);
		
		if (intersecting.isEmpty()) {
			// Nothing in the way of where I want to go.
			return attemptedY;
		}
		
		// Decide how far forward I can move.
		float availableSpace = attemptedY;
		for (Collidable o : intersecting) {
			// Loop through everything my desired new hitbox was intersecting with.
			
			Rectangle theirHitbox = o.getCollisionHitbox();
			
			if (attemptedY > 0) {
				// If trying to move down:
				// Find the topmost edge of their hitbox.
				float theirTopEdge = theirHitbox.getY();
				
				// Find the bottommost edge of my hitbox.
				float myBottomEdge = oldHitbox.getY() + oldHitbox.getHeight();
				
				// Find how much space there is between my bottom edge and their top edge.
				// This value should be positive if you are moving down.
				float distance = theirTopEdge - myBottomEdge;
				
				// If the available space is smaller than previously thought, set it
				// accordingly.
				if (distance < availableSpace) { availableSpace = distance; }
			}
			else {
				// If trying to move up:
				// Find the bottommost edge of their hitbox.
				float theirBottomEdge = theirHitbox.getY() + theirHitbox.getHeight();
				
				// Find the topmost edge of my hitbox.
				float myTopEdge = oldHitbox.getY();
				
				// Find how much space there is between my top edge and their bottom edge.
				// This value should be negative if you are moving up.
				float distance = theirBottomEdge - myTopEdge;
				
				// If the available space is smaller (negatively) than previously thought, 
				// set it accordingly.
				if (distance > availableSpace) { availableSpace = distance; }
			}
		}
		return availableSpace;
	}

	public static boolean CanMoveUp(Collidable obj, ArrayList<? extends Collidable> allObjs) {
		return GetAvailableMovement(obj, allObjs, new Vector2(0, -1)).getY() != 0;
	}
	public static boolean CanMoveDown(Collidable obj, ArrayList<? extends Collidable> allObjs) {
		return GetAvailableMovement(obj, allObjs, new Vector2(0, 1)).getY() != 0;
	}
	public static boolean CanMoveLeft(Collidable obj, ArrayList<? extends Collidable> allObjs) {
		return GetAvailableMovement(obj, allObjs, new Vector2(-1, 0)).getX() != 0;
	}
	public static boolean CanMoveRight(Collidable obj, ArrayList<? extends Collidable> allObjs) {
		return GetAvailableMovement(obj, allObjs, new Vector2(1, 0)).getX() != 0;
	}
}
