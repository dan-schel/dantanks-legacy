package com.danschellekens.slick2d.input;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Input;

import com.danschellekens.operations.Vector2;

public class DanInput {

	MouseState oldMouse;
	MouseState newMouse;
	KeyboardState oldKeyboard;
	KeyboardState newKeyboard;
	
	public DanInput() {
		oldMouse = new MouseState(null);
		newMouse = new MouseState(null);
		oldKeyboard = new KeyboardState(null);
		newKeyboard = new KeyboardState(null);
	}

	public void update(Input input) {
		oldMouse = newMouse;
		oldKeyboard = newKeyboard;
		
		if (Mouse.isInsideWindow()) { newMouse = new MouseState(input); }
		else { newMouse = new MouseState(null); }
		
		newKeyboard = new KeyboardState(input);
	}
	
	// MOUSE POSITION
	public boolean isMouseValid() {
		return newMouse.isValid();
	}
	public boolean wasMouseValid() {
		return oldMouse.isValid();
	}
	public boolean bothMiceValid() {
		return isMouseValid() && wasMouseValid();
	}
	public float getMouseX() {
		return newMouse.getX();
	}
	public float getOldMouseX() {
		return oldMouse.getX();
	}
	public float getMouseY() {
		return newMouse.getY();
	}
	public float getOldMouseY() {
		return oldMouse.getY();
	}
	public float getMouseDeltaX() {
		if (!bothMiceValid()) { return 0; }
		return getMouseX() - getOldMouseX();
	}
	public float getMouseDeltaY() {
		if (!bothMiceValid()) { return 0; }
		return getMouseY() - getOldMouseY();
	}
	public Vector2 getMouse() {
		return new Vector2(getMouseX(), getMouseY());
	}
	public Vector2 getOldMouse() {
		return new Vector2(getOldMouseX(), getOldMouseY());
	}
	public Vector2 getMouseDelta() {
		return new Vector2(getMouseDeltaX(), getMouseDeltaY());
	}
	
	// KEYBOARD
	public boolean isKeyDown(int keycode) {
		return newKeyboard.isKeyDown(keycode);
	}
	public boolean wasKeyDown(int keycode) {
		return oldKeyboard.isKeyDown(keycode);
	}
	public boolean isKeyUp(int keycode) {
		return newKeyboard.isKeyUp(keycode);
	}
	public boolean wasKeyUp(int keycode) {
		return oldKeyboard.isKeyUp(keycode);
	}
	public boolean keyJustPressed(int keycode) {
		return isKeyDown(keycode) && wasKeyUp(keycode);
	}
	public boolean keyJustReleased(int keycode) {
		return wasKeyDown(keycode) && isKeyUp(keycode);
	}
	
	// MOUSE BUTTONS
	public boolean isLeftMouseDown() {
		return newMouse.isLeft();
	}
	public boolean wasLeftMouseDown() {
		return oldMouse.isLeft();
	}
	public boolean isLeftMouseUp() {
		return !newMouse.isLeft();
	}
	public boolean wasLeftMouseUp() {
		return !oldMouse.isLeft();
	}
	public boolean leftMouseJustPressed() {
		return isLeftMouseDown() && wasLeftMouseUp();
	}
	public boolean leftMouseJustReleased() {
		return wasLeftMouseDown() && isLeftMouseUp();
	}
	
	public boolean isMiddleMouseDown() {
		return newMouse.isMiddle();
	}
	public boolean wasMiddleMouseDown() {
		return oldMouse.isMiddle();
	}
	public boolean isMiddleMouseUp() {
		return !newMouse.isMiddle();
	}
	public boolean wasMiddleMouseUp() {
		return !oldMouse.isMiddle();
	}
	public boolean middleMouseJustPressed() {
		return isMiddleMouseDown() && wasMiddleMouseUp();
	}
	public boolean middleMouseJustReleased() {
		return wasMiddleMouseDown() && isMiddleMouseUp();
	}
	
	public boolean isRightMouseDown() {
		return newMouse.isRight();
	}
	public boolean wasRightMouseDown() {
		return oldMouse.isRight();
	}
	public boolean isRightMouseUp() {
		return !newMouse.isRight();
	}
	public boolean wasRightMouseUp() {
		return !oldMouse.isRight();
	}
	public boolean rightMouseJustPressed() {
		return isRightMouseDown() && wasRightMouseUp();
	}
	public boolean rightMouseJustReleased() {
		return wasRightMouseDown() && isRightMouseUp();
	}
	
	public boolean isAnyMouseDown() {
		return isLeftMouseDown() || isMiddleMouseDown() || isRightMouseDown();
	}
	public boolean isAllMouseUp() {
		return isLeftMouseUp() && isMiddleMouseUp() && isRightMouseUp();
	}	
	public boolean wasAnyMouseDown() {
		return wasLeftMouseDown() || wasMiddleMouseDown() || wasRightMouseDown();
	}
	public boolean wasAllMouseUp() {
		return wasLeftMouseUp() && wasMiddleMouseUp() && wasRightMouseUp();
	}
	public boolean mouseJustPressed() {
		return wasAllMouseUp() && isAnyMouseDown();
	}
	public boolean mouseJustReleased() {
		return wasAnyMouseDown() && isAllMouseUp();
	}
}
