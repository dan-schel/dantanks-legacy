package com.danschellekens.slick2d.input;

import org.newdawn.slick.Input;

public class KeyboardState {
	public static final int AMOUNT_OF_KEYS = 255;
	boolean[] keys;
	
	boolean valid;
	
	public KeyboardState(Input input) {
		keys = new boolean[AMOUNT_OF_KEYS];
		
		if (input != null) {
			for (int i = 0; i < AMOUNT_OF_KEYS; i++) {
				keys[i] = input.isKeyDown(i);
			}
			setValid(true);
		}
		else {
			for (int i = 0; i < AMOUNT_OF_KEYS; i++) {
				keys[i] = false;
			}
			setValid(false);
		}
	}

	public boolean isKeyDown(int keycode) {
		if (keycode > AMOUNT_OF_KEYS || keycode < 0) {
			return false;
		}
		return keys[keycode];
	}
	public boolean isKeyUp(int keycode) {
		return !isKeyDown(keycode);
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
