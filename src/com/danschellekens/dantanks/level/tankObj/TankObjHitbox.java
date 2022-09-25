package com.danschellekens.dantanks.level.tankObj;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.collisions.RectUtils;

public class TankObjHitbox {
	final Rectangle hitbox;
	final Vector2 textureOffset;
	Vector2 position;
	
	public TankObjHitbox(Vector2 position) {
		this.hitbox = new Rectangle(-10, -15, 20, 15);
		this.textureOffset = new Vector2(0, -5);
		
		this.position = position;
	}
	
	public Vector2 getPosition() {
		return position.clone();
	}
	public Vector2 getBarrelEndPosition(float angle) {
		Vector2 position = getPosition();
		position = position.add(new Vector2(0, -13));
		position = position.add(new Vector2(0, -15).rotateDegrees(angle));
		return position;
	}
	public Vector2 getHitboxCenter() {
		Rectangle hitbox = getHitbox();
		return new Vector2(hitbox.getX() + hitbox.getWidth() / 2, hitbox.getY() + hitbox.getHeight() / 2);
	}
	public Vector2 getHitboxTopMidpoint() {
		Rectangle hitbox = getHitbox();
		return new Vector2(hitbox.getX() + hitbox.getWidth() / 2, hitbox.getY());
	}
	public Rectangle getHitboxDimensions() {
		return RectUtils.Clone(hitbox);
	}
	public Rectangle getHitbox() {
		return RectUtils.Translate(hitbox, position);
	}
	
	public float getX1() {
		return position.getX() + hitbox.getX();
	}
	public float getX2() {
		return position.getX() + hitbox.getX() + hitbox.getWidth();
	}
	public float getY1() {
		return position.getY() + hitbox.getY();
	}
	public float getY2() {
		return position.getY() + hitbox.getY() + hitbox.getHeight();
	}
	public Vector2 getTextureOffset() {
		return textureOffset.clone();
	}
	public float getHitboxBottomOffset() {
		return hitbox.getY() + hitbox.getHeight();
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public void setX(float x) {
		this.position.setX(x);
	}
	public void setY(float y) {
		this.position.setY(y);
	}
	public void changePosition(Vector2 offset) {
		this.position = this.position.add(offset);
	}
	public void changeX(float x) {
		changePosition(new Vector2(x, 0));
	}
	public void changeY(float y) {
		changePosition(new Vector2(0, y));
	}
}
