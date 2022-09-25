package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.operations.Increment;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class LongRectangleTextButton extends Button {
	Label label;
	Color color;
	float borderOpacity;
	
	public LongRectangleTextButton(UIElement parent, float x, float y, float w, String text) {
		this(parent, new Rectangle(x, y, w, 40), text);
	}
	public LongRectangleTextButton(UIElement parent, Rectangle space, String text) {
		super(parent, space);
		
		this.label = new Label(this, new Rectangle(0, 0, this.getWidth(), this.getHeight()), FontLibrary.getBodyFont());
		this.label.setHAlign(HAlign.CENTER);
		this.label.setText(text);
		this.label.setColor(new Color(1f, 1f, 1f));
		
		this.color = new Color(0, 150, 240);
	}

	@Override
	public void update(DanInput input) {
		if (isHeld()) {
			borderOpacity = Increment.Linear(borderOpacity, 0.2f, 0.025f);
		}
		else if (isHovered()) {
			borderOpacity = Increment.Linear(borderOpacity, 0.1f, 0.025f);
		}
		else {
			borderOpacity = Increment.Linear(borderOpacity, 0f, 0.025f);
		}
		
		label.update(input);
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		if (!isEnabled()) {
			artist.shape().draw(new RoundedRectangle(0, 0, getWidth(), getHeight(), 5), new Color(0, 0, 0, 0.2f));
			label.render(artist);
			
			return;
		}
		
		artist.shape().draw(new RoundedRectangle(0, 0, getWidth(), getHeight(), 5), color);
		artist.shape().draw(new RoundedRectangle(0, 0, getWidth(), getHeight(), 5), new Color(1f, 1f, 1f, borderOpacity));
		
		artist.shape().draw(new RoundedRectangle(2, 2, getWidth() - 4, getHeight() - 4, 3), null, new Color(1f, 1f, 1f, borderOpacity * 3), 2);
		
		label.render(artist);

		super.renderInPosition(artist);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public String getText() {
		return label.getText();
	}
	public void setText(String text) {
		label.setText(text);
	}
}
