package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.Button;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class BigCircleIconButton extends Button {
	Image icon;
	float iconY;
	
	public BigCircleIconButton(UIElement parent, float x, float y, Image icon) {
		super(parent, new Rectangle(x, y, 50, 50));
		
		this.icon = icon;
		this.iconY = 0;
	}

	@Override
	public void update(DanInput input) {	
		if (isHeld()) {
			iconY += (2 - iconY) / 2;
		}
		else if (isHovered()) {
			iconY += (-2 - iconY) / 4;
		}
		else {
			iconY += (0 - iconY) / 4;
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new Circle(getWidth() / 2, getHeight() / 2 + 5, 24), new Color(0, 0, 0, 50));
		artist.image().draw(icon, 0, iconY);
		
		if (isHeld()) {
			float circleOpacity = Range.MapConstrain(iconY, 0f, 2, 0, 0.05f);
			float circleSize = Range.MapConstrain(iconY, 0f, 2, 0, 25);
			artist.shape().draw(new Circle(getWidth() / 2, getHeight() / 2 + 2, circleSize), new Color(0, 0, 0, circleOpacity));
		}
		else if (isHovered()) {
			artist.shape().draw(new Circle(getWidth() / 2, getHeight() / 2 + iconY, 25), new Color(255, 255, 255, 30));
		}
		
		super.renderInPosition(artist);
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}
}
