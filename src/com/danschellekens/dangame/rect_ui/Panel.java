package com.danschellekens.dangame.rect_ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class Panel extends UIElement {
	ArrayList<UIElement> children;
	Color backgroundColor;
	
	public Panel(UIElement parent, Rectangle space) {
		super(parent, space);
		
		children = new ArrayList<UIElement>();
		backgroundColor = new Color(0, 0, 0, 0);
	}

	@Override
	public void update(DanInput input) {
		for (UIElement i : children) {
			i.update(input);
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new Rectangle(0, 0, this.getWidth(), this.getHeight()), backgroundColor);
		
		for (UIElement i : children) {
			i.render(artist);
		}
		
		super.renderInPosition(artist);
	}
	
	public void addChild(UIElement element) {
		children.add(element);
	}
	public void removeChild(UIElement element) {
		children.remove(element);
	}
	public void clearChildren() {
		children.clear();
	}
	public int getChildrenAmount() {
		return children.size();
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
