package com.danschellekens.dantanks.level.effects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import com.danschellekens.operations.*;
import com.danschellekens.slick2d.graphics.Artist;

public class LeafParticle {
	final float initialSize;
	final boolean sway;
	
	Vector2 position;
	float size;
	Color color;
	
	float sineSeed;
	
	public LeafParticle(Vector2 position, float size, Color color, boolean sway) {
		this.position = position;
		this.size = size;
		this.initialSize = size;
		this.color = color;
		this.sway = sway;
		
		this.sineSeed = Random.Radians();
	}
	
	public void update() {
		size = Increment.Linear(size, 0, 0.1f);
		
		float drift = 0f;
		
		if (sway) {
			drift = (float) Math.sin(sineSeed) * 0.4f;
			sineSeed += 0.1f;
		}
		else {
			drift = (float) Math.sin(sineSeed) * 0.1f;
		}
		
		this.position = this.position.add(new Vector2(drift, 0.15f));
	}
	
	public void render(Artist artist) {
		artist.push();
		artist.translate(position.getX(), position.getY());
		artist.shape().draw(new Circle(0, 0, size), color);
		artist.pop();
	}
	
	public boolean isDone() {
		return size < 0.5f;
	}
}
