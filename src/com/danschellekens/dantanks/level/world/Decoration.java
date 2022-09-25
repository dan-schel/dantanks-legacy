package com.danschellekens.dantanks.level.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dantanks.level.effects.Effect;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.collisions.RectUtils;
import com.danschellekens.slick2d.graphics.Artist;

public class Decoration {
	final Rectangle hitbox;
	final Vector2 position;
	final Image image;
	final float groundHeight;
	final Effect deathEffect;
	
	public Decoration(Rectangle hitbox, Vector2 position, Image image, float groundHeight, Effect deathEffect) {
		this.hitbox = hitbox;
		this.position = position;
		this.image = image;
		this.groundHeight = groundHeight;
		this.deathEffect = deathEffect;
	}
	public void update() {
		
	}
	public void render(Artist artist) {
		artist.image().draw(image, position.getX() - image.getWidth() / 2, position.getY() - image.getHeight());
	}
	public void renderHitboxes(Artist artist) {
		artist.shape().draw(getHitboxInPosition(), null, new Color(255, 0, 0), 1);
	}
	public Rectangle getHitboxInPosition() {
		return RectUtils.Translate(hitbox, position);
	}
	public float getGroundHeight() {
		return groundHeight;
	}
	public float getX() {
		return position.getX();
	}
	public Vector2 getPosition() {
		return position;
	}
	public Effect getDeathEffect() {
		return deathEffect;
	}
}
