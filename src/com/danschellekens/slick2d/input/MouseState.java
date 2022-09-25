package com.danschellekens.slick2d.input;

import org.newdawn.slick.Input;

public class MouseState {
	float x;
	float y;
	
	boolean left;
	boolean middle;
	boolean right;
	
	boolean valid;
	
	public MouseState(Input input) {
		if (input != null) {
			setX(input.getMouseX());
			setY(input.getMouseY());
			setLeft(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON));
			setMiddle(input.isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON));
			setRight(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON));
			
			setValid(true);
		}
		else {
			setX(0);
			setY(0);
			setLeft(false);
			setMiddle(false);
			setRight(false);
			
			setValid(false);
		}
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isMiddle() {
		return middle;
	}
	public void setMiddle(boolean middle) {
		this.middle = middle;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
