package com.danschellekens.slick2d.collisions;

import org.newdawn.slick.geom.Rectangle;

public interface Collidable {
	Rectangle getCollisionHitbox();
	boolean isCollisionSolid();
}
