package com.danschellekens.dantanks.level.effects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import com.danschellekens.dantanks.data.values.PlayerColor;
import com.danschellekens.operations.*;
import com.danschellekens.slick2d.graphics.Artist;

public class TankBitsParticle {
	final Image image;
	final PlayerColor color;
	final boolean shouldTint;
	
	Vector2 position;
	Vector2 velocity;
	float rotation;
	float rotationSpeed;
	float opacity;
	
	public TankBitsParticle(Vector2 position, Image image, PlayerColor color, boolean shouldTint) {
		this.position = position;
		this.image = image;
		this.color = color;
		this.shouldTint = shouldTint;
		
		velocity = Random.Vector2(1.5f);
		rotationSpeed = Random.Float(-10f, 10f);
		opacity = 1f;
	}
	
	public void update() {
		this.opacity = Increment.Linear(opacity, 0f, 0.01f);
		
		velocity = velocity.add(new Vector2(0f, 0.05f));
		
		position = position.add(velocity);
		rotation += rotationSpeed;
	}
	
	public void render(Artist artist) {
		artist.push();
		artist.translate(position.getX(), position.getY());
		artist.rotate(rotation);
		
		if (shouldTint) {
			Color c = color.getColor();
			Color a = new Color(c.r, c.g, c.b, opacity);
			
			artist.image().drawCentered(image, 0f, 0f, a);
		}
		else {
			artist.image().drawCentered(image, 0f, 0f, new Color(1f, 1f, 1f, opacity));
		}
		
		artist.pop();
	}
	
	public boolean isDone() {
		return opacity < 0.01f;
	}
}
