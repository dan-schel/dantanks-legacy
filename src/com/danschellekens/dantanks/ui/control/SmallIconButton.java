package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.Button;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class SmallIconButton extends Button {
	public static final float BORED_OPACITY = 0.3f;
	public static final float HOVERED_OPACITY = 0.5f;
	public static final float HELD_OPACITY = 1f;
	
	Image icon;
	float opacity;
	
	public SmallIconButton(UIElement parent, float x, float y, Image icon) {
		super(parent, new Rectangle(x, y, 20, 20));
		
		this.icon = icon;
		this.opacity = BORED_OPACITY;
	}

	@Override
	public void update(DanInput input) {
		if (isHeld()) {
			opacity = Increment.Exponential(opacity, HELD_OPACITY, 0.5f);
		}
		else if (isHovered()) {
			opacity = Increment.Exponential(opacity, HOVERED_OPACITY, 0.25f);
		}
		else {
			opacity = Increment.Exponential(opacity, BORED_OPACITY, 0.25f);
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.image().draw(icon, 0, 0, new Color(1, 1, 1, opacity));
		
		float circleOpacity = Range.MapConstrain(opacity, HOVERED_OPACITY, HELD_OPACITY, 0, 0.05f);
		float circleSize = Range.MapConstrain(opacity, HOVERED_OPACITY, HELD_OPACITY, 10, 15);
		artist.shape().draw(new Circle(getWidth() / 2, getHeight() / 2, circleSize), new Color(0, 0, 0, circleOpacity));
	}

}

