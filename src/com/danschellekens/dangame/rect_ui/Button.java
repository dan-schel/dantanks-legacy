package com.danschellekens.dangame.rect_ui;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.slick2d.input.DanInput;

public class Button extends UIElement {
	ButtonState newState;
	DanInput input;
	ArrayList<Integer> keys;
	
	boolean enabled;
	boolean ignoreMouse;
	int ignoreMouseCooldown;

	public Button(UIElement parent, Rectangle space) {
		super(parent, space);
		
		newState = ButtonState.NORMAL;
		keys = new ArrayList<Integer>();
		this.enabled = true;
		this.input = new DanInput();
	}

	@Override
	public void update(DanInput input) {
		super.update(input);
		
		if ((ignoreMouse && input.isLeftMouseUp()) || (ignoreMouse && this.mouseJustLeft(input))) {
			if (ignoreMouseCooldown == 0) { ignoreMouseCooldown = 2; }
		}
		if (this.mouseJustEntered(input) && input.isLeftMouseDown()) {
			ignoreMouse = true;
		}
		if (ignoreMouseCooldown != 0) {
			ignoreMouseCooldown --;
			if (ignoreMouseCooldown == 0) {
				ignoreMouse = false;
			}
		}
		
		if (enabled && !ignoreMouse) {
			this.input = input;
		}
		else {
			this.input = new DanInput();
		}
		
		this.newState = determineState();
	}
	
	public ButtonState determineState() {
		if (this.enabled == false) {
			return ButtonState.DISABLED;
		}
		if ((this.isMouseInside(input) && input.isLeftMouseDown() && !ignoreMouse) || areKeysDown()) {
			return ButtonState.HELD;
		}
		else if (this.isMouseInside(input) && !ignoreMouse) {
			return ButtonState.HOVERED;
		}
		else {
			return ButtonState.NORMAL;
		}
	}
	boolean areKeysDown() {
		for (int key : keys) {
			if (this.input.isKeyDown(key)) {
				return true;
			}
		}
		
		return false;
	}
	boolean justReleasedAKey() {
		for (int key : keys) {
			if (this.input.keyJustReleased(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean justActivated() {
		return (this.mouseJustReleasedInside(this.input) && !ignoreMouse) || justReleasedAKey();
	}

	public ButtonState getState() {
		return this.newState;
	}
	public boolean isHeld() {
		return getState() == ButtonState.HELD;
	}
	public boolean isHovered() {
		return getState() == ButtonState.HOVERED;
	}
	public boolean isNormal() {
		return getState() == ButtonState.NORMAL;
	}
	public boolean isDisabled() {
		return getState() == ButtonState.DISABLED;
	}
	
	public void addKey(int key) {
		this.keys.add(key);
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public enum ButtonState {
		NORMAL,
		HOVERED,
		HELD,
		DISABLED
	}
}
