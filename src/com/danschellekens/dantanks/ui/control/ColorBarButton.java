package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class ColorBarButton extends Button {
	Label label;
	Color color;
	
	float colorBar;
	float hoverOpacity;
	
	boolean chosen;
	
	public ColorBarButton(UIElement parent, int x, int y, int w, int textMargin) {
		super(parent, new Rectangle(x, y, w, 40));
		
		this.color = new Color(240, 0, 0);
		this.colorBar = 0;
		this.hoverOpacity = 0;
		this.chosen = false;
		
		this.label = new Label(this, new Rectangle(2 + textMargin, 10, w - 10 - 2 - textMargin, this.getHeight() - 20), FontLibrary.getBodyFont());
		this.label.setHAlign(HAlign.LEFT);
	}
	public ColorBarButton(UIElement parent, int x, int y, int w) {
		this(parent, x, y, w, 18);
	}
	
	@Override
	public void update(DanInput input) {
		if (isHeld()) {
			hoverOpacity = Increment.Linear(hoverOpacity, 0.1f, 0.02f);
			colorBar = Increment.Exponential(colorBar, 1f, 0.5f);
		}
		else if (isHovered()) {
			hoverOpacity = Increment.Linear(hoverOpacity, 0.05f, 0.02f);
			colorBar = Increment.Exponential(colorBar, 1f, 0.5f);
		}
		else {
			hoverOpacity = Increment.Linear(hoverOpacity, 0f, 0.02f);
			
			if (chosen) { colorBar = Increment.Exponential(colorBar, 1f, 0.5f); }
			else { colorBar = Increment.Exponential(colorBar, 0f, 0.5f); }
		}
	
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		label.render(artist);
		
		float rectY = Range.Map(colorBar, 0, 1, this.getHeight() / 2, 0);
		float rectHeight = Range.Map(colorBar, 0, 1, 0, this.getHeight());
		
		artist.shape().draw(new Rectangle(0, rectY, 2, rectHeight), color);
		artist.shape().draw(
				new Rectangle(0, 0, this.getWidth(), this.getHeight()), 
				new Color(0f, 0f, 0f, hoverOpacity)
			);
		
		super.renderInPosition(artist);
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isChosen() {
		return chosen;
	}
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
	public String getText() {
		return label.getText();
	}
	public void setText(String text) {
		label.setText(text);
	}
}
