package com.danschellekens.dantanks.level.effects;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class ExplosionParticle {
	Vector2 position;
	float size;
	float initialSize;
	Color color;
	
	float rise;
	
	public ExplosionParticle(Vector2 position, float size) {
		this.position = position;
		this.size = size;
		this.initialSize = size;
		this.color = new Color(1f, 1f, 1f);
	}
	
	public void update() {
		size = Increment.Exponential(size, 0, 0.05f);
		rise -= 0.01f;
		this.position = this.position.add(new Vector2(0, rise));
		
		color.b -= 0.1f;
		if (color.b <= 0) {
			color.g -= 0.1f;
		}
		if (color.g <= 0) {
			color.r -= 0.02f;
		}
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
