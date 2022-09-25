package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import com.danschellekens.dangame.rect_ui.Textbox;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.slick2d.graphics.Artist;

public class RectangleTextbox extends Textbox {

	public RectangleTextbox(UIElement parent, Rectangle area, Font font) {
		super(parent, area, font);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new RoundedRectangle(0, 0, getWidth(), getHeight(), 5), new Color(0f, 0f, 0f, 0.075f));
		
		super.renderInPosition(artist);
	}
	
}
