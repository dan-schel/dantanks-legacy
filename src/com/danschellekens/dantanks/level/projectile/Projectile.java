package com.danschellekens.dantanks.level.projectile;

import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class Projectile {
	protected final Vector2 initialPosition;
	
	protected Vector2 position;
	
	public Projectile(Vector2 position) {
		this.initialPosition = position;
		this.position = position;
	}
	
	public void update() {
		
	}
	
	public void render(Artist artist) {
		artist.push();
		artist.translate(position.getX(), position.getY());
		renderInPosition(artist);
		artist.pop();
	}
	public void renderInPosition(Artist artist) {
		
	}

	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
