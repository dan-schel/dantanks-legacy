package com.danschellekens.dantanks.level.projectile;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class ShellProjectile extends Projectile {
	protected final float angle;
	protected final float power;
	protected final float windConstant;
	protected final float gravityConstant;
	
	protected Vector2 velocity;
	protected float size;
	protected Color color;
	
	public ShellProjectile(Vector2 position, float angle, float power, float windConstant, float gravityConstant) {
		super(position);
		
		this.angle = angle;
		this.power = power;
		this.gravityConstant = gravityConstant;
		this.windConstant = windConstant;
		
		velocity = ProjectileTrajectory.velocity(angle, power);
		color = new Color(0, 0, 0);
		size = 2;
	}

	@Override
	public void update() {
		velocity = ProjectileTrajectory.accelerate(velocity, windConstant, gravityConstant);
		position = position.add(velocity);
		
		super.update();
	}

//	 @Override
//	 public void render(Artist artist) {
//		 for (int t = 0; t <= 400; t += 10) {
//			 Vector2 position = ProjectileTrajectory.position(
//				 initialPosition, 
//				 angle, 
//				 power, 
//				 t, 
//				 windConstant, 
//				 gravityConstant
//			 );
//			 artist.shape().draw(
//				 new Rectangle(position.getX() - 2, position.getY() - 2, 4, 4), 
//				 new Color(1f, 1f, 1f, 0.5f)
//			 );
//		 }	
//		 super.render(artist);
//	 }

	@Override
	public void renderInPosition(Artist artist) {
		artist.shape().draw(new Circle(0, 0, size), color);
		super.renderInPosition(artist);
	}

	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
