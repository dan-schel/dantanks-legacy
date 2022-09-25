package com.danschellekens.dangame.rect_ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.collisions.RectUtils;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class UIElement {
	public static boolean SHOW_CONTROL_BOUNDARIES = false;
	
	Rectangle space;
	UIElement parent;
	
	public UIElement(UIElement parent, Rectangle space) {
		this.space = space;
		this.parent = parent;
	}

	public void update(DanInput input) {
		
	}
	
	public void render(Artist artist) {
		artist.setTransform(new Transform());
		
		if (SHOW_CONTROL_BOUNDARIES) {
			artist.shape().draw(this.getScreenSpace(), null, new Color(1, 0, 0, 0.2f), 1);
		}
		
		artist.translate(getScreenX(), getScreenY());
		renderInPosition(artist);
		artist.pop();
	}
	protected void renderInPosition(Artist artist) {
		
	}
	
	public UIElement getParent() {
		return parent;
	}

	public float getWidth() { 
		return space.getWidth(); 
	}
	public float getHeight() { 
		return space.getHeight(); 
	}
	
	public Vector2 getRelativePosition() {
		return new Vector2(space.getX(), space.getY());
	}
	public Rectangle getRelativeSpace() {
		return RectUtils.Clone(this.space);
	}
	public float getRelativeX() { 
		return getRelativePosition().getX(); 
	}
	public float getRelativeY() { 
		return getRelativePosition().getY(); 
	}
	
	public Vector2 getScreenPosition() {
		if (parent == null) { return getRelativePosition(); }
		else { return parent.getScreenPosition().add(getRelativePosition()); }
	}
	public Rectangle getScreenSpace() {
		return RectUtils.SetPosition(this.space, getScreenPosition());
	}
	public float getScreenX() { 
		return getScreenPosition().getX();
	}
	public float getScreenY() { 
		return getScreenPosition().getY();
	}
	
	public boolean isMouseInside(DanInput input) {
		return getScreenSpace().contains(input.getMouseX(), input.getMouseY());
	}
	public boolean isMouseOutside(DanInput input) {
		return !isMouseInside(input);
	}
	public boolean wasMouseInside(DanInput input) {
		return getScreenSpace().contains(input.getOldMouseX(), input.getOldMouseY());
	}
	public boolean wasMouseOutside(DanInput input) {
		return !wasMouseInside(input);
	}
	public boolean mouseJustEntered(DanInput input) {
		return isMouseInside(input) && wasMouseOutside(input);
	}
	public boolean mouseJustLeft(DanInput input) {
		return isMouseOutside(input) && wasMouseInside(input);
	}
	public boolean mouseJustPressedInside(DanInput input) {
		return isMouseInside(input) && input.leftMouseJustPressed();
	}
	public boolean mouseJustPressedOutside(DanInput input) {
		return isMouseOutside(input) && input.leftMouseJustPressed();
	}
	public boolean mouseJustReleasedInside(DanInput input) {
		return isMouseInside(input) && input.leftMouseJustReleased();
	}
	public boolean mouseJustReleasedOutside(DanInput input) {
		return isMouseOutside(input) && input.leftMouseJustReleased();
	}
	public Vector2 getMouseRelativePosition(DanInput input) {
		return input.getMouse().subtract(this.getScreenPosition());
	}
	
	public void setX(float x) {
		this.space.setX(x);
	}
	public void setY(float y) {
		this.space.setY(y);
	}
}
