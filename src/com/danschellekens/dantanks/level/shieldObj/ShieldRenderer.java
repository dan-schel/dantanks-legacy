package com.danschellekens.dantanks.level.shieldObj;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import com.danschellekens.operations.*;
import com.danschellekens.slick2d.graphics.Artist;

public class ShieldRenderer {
	public static final float BREATHING_RADIUS_MIN = 24;
	public static final float BREATHING_RADIUS_MAX = 28;
	public static final float BREATHING_SPEED = 0.06f;
	
	final Color color;
	
	float radius;
	float breathingTick;
	float shieldHealthPercent;
	
	public ShieldRenderer(Color color) {
		this.color = color;
		this.shieldHealthPercent = 1;
	}
	
	public void update() {
		breathingTick += BREATHING_SPEED;
		float breathingSine = (float) Math.sin(breathingTick);
		float radiusAim = Range.Map(breathingSine, -1f, 1f, BREATHING_RADIUS_MIN, BREATHING_RADIUS_MAX);
		
		radiusAim *= Range.MapConstrain(shieldHealthPercent, 0f, 1f, 0.6f, 1f);
		
		radius = Increment.Exponential(radius, radiusAim, 0.2f);
	}
	
	public void renderBehind(Artist artist) {
		Color healthBasedColor = new Color(1f, 1f, 1f, shieldHealthPercent * 0.2f);
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Numbers.Floor(0.5f * 256));
		
		artist.shape().draw(new Circle(0, -6, radius), healthBasedColor);
		artist.shape().draw(new Circle(0, -6, radius + 4), null, transparentColor, 4);
		artist.shape().draw(new Circle(0, -6, radius + 10), null, transparentColor, 2);
	}
	public void renderAbove(Artist artist) {
		Color healthBasedColor = new Color(1f, 1f, 1f, shieldHealthPercent * 0.1f);
		artist.shape().draw(new Circle(0, -6, radius), healthBasedColor);
	}

	public void setShieldHealthPercent(float percent) {
		this.shieldHealthPercent = percent;
	}
	public void tookDamage(float damage) {
		this.radius *= Range.MapConstrain(damage, 3, 20, 1f, 0.5f);
	}
}
