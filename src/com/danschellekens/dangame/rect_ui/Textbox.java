package com.danschellekens.dangame.rect_ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class Textbox extends UIElement {
	String text;
	Color color;
	
	Font font;
	
	HAlign hAlign;
	VAlign vAlign;
	boolean focused;
	int charLimit;
	
	int cursorTick;
	int repeatTick;
	
	public Textbox(UIElement parent, Rectangle area, Font font) {
		super(parent, area);
		
		this.text = "";
		this.color = new Color(0, 0, 0);
		
		this.font = font;
		
		this.hAlign = HAlign.LEFT;
		this.vAlign = VAlign.CENTER;
		this.focused = false;
		this.charLimit = 50;
		
		this.cursorTick = 0;
		this.repeatTick = 0;
	}

	@Override
	public void update(DanInput input) {
		if (this.mouseJustPressedInside(input)) { focused = true; }
		if (this.mouseJustPressedOutside(input)) { focused = false; }
		
		if (focused) { cursorTick = (cursorTick + 1) % 40; }
		else { cursorTick = 19; }
		
		if (focused) { doTyping(input); }
		
		super.update(input);
	}
	boolean isKeyTyped(DanInput input, int keycode) {
		if (input.keyJustPressed(keycode)) {
			this.repeatTick = 30;
			return true;
		}
		
		if (input.isKeyDown(keycode)) {
			this.repeatTick --;
			
			if (this.repeatTick == 0) {
				this.repeatTick = 2;
				return true;
			}
		}
		return false;
	}
	void doTyping(DanInput input) {
		if (text.length() < charLimit) {
			if (input.isKeyDown(Input.KEY_LSHIFT) || input.isKeyDown(Input.KEY_RSHIFT)) {
				if (isKeyTyped(input, Input.KEY_A)) { text += "A"; }
				if (isKeyTyped(input, Input.KEY_B)) { text += "B"; }
				if (isKeyTyped(input, Input.KEY_C)) { text += "C"; }
				if (isKeyTyped(input, Input.KEY_D)) { text += "D"; }
				if (isKeyTyped(input, Input.KEY_E)) { text += "E"; }
				if (isKeyTyped(input, Input.KEY_F)) { text += "F"; }
				if (isKeyTyped(input, Input.KEY_G)) { text += "G"; }
				if (isKeyTyped(input, Input.KEY_H)) { text += "H"; }
				if (isKeyTyped(input, Input.KEY_I)) { text += "I"; }
				if (isKeyTyped(input, Input.KEY_J)) { text += "J"; }
				if (isKeyTyped(input, Input.KEY_K)) { text += "K"; }
				if (isKeyTyped(input, Input.KEY_L)) { text += "L"; }
				if (isKeyTyped(input, Input.KEY_M)) { text += "M"; }
				if (isKeyTyped(input, Input.KEY_N)) { text += "N"; }
				if (isKeyTyped(input, Input.KEY_O)) { text += "O"; }
				if (isKeyTyped(input, Input.KEY_P)) { text += "P"; }
				if (isKeyTyped(input, Input.KEY_Q)) { text += "Q"; }
				if (isKeyTyped(input, Input.KEY_R)) { text += "R"; }
				if (isKeyTyped(input, Input.KEY_S)) { text += "S"; }
				if (isKeyTyped(input, Input.KEY_T)) { text += "T"; }
				if (isKeyTyped(input, Input.KEY_U)) { text += "U"; }
				if (isKeyTyped(input, Input.KEY_V)) { text += "V"; }
				if (isKeyTyped(input, Input.KEY_W)) { text += "W"; }
				if (isKeyTyped(input, Input.KEY_X)) { text += "X"; }
				if (isKeyTyped(input, Input.KEY_Y)) { text += "Y"; }
				if (isKeyTyped(input, Input.KEY_Z)) { text += "Z"; }
				if (isKeyTyped(input, Input.KEY_SPACE)) { text += " "; }
			}
			else {
				if (isKeyTyped(input, Input.KEY_A)) { text += "a"; }
				if (isKeyTyped(input, Input.KEY_B)) { text += "b"; }
				if (isKeyTyped(input, Input.KEY_C)) { text += "c"; }
				if (isKeyTyped(input, Input.KEY_D)) { text += "d"; }
				if (isKeyTyped(input, Input.KEY_E)) { text += "e"; }
				if (isKeyTyped(input, Input.KEY_F)) { text += "f"; }
				if (isKeyTyped(input, Input.KEY_G)) { text += "g"; }
				if (isKeyTyped(input, Input.KEY_H)) { text += "h"; }
				if (isKeyTyped(input, Input.KEY_I)) { text += "i"; }
				if (isKeyTyped(input, Input.KEY_J)) { text += "j"; }
				if (isKeyTyped(input, Input.KEY_K)) { text += "k"; }
				if (isKeyTyped(input, Input.KEY_L)) { text += "l"; }
				if (isKeyTyped(input, Input.KEY_M)) { text += "m"; }
				if (isKeyTyped(input, Input.KEY_N)) { text += "n"; }
				if (isKeyTyped(input, Input.KEY_O)) { text += "o"; }
				if (isKeyTyped(input, Input.KEY_P)) { text += "p"; }
				if (isKeyTyped(input, Input.KEY_Q)) { text += "q"; }
				if (isKeyTyped(input, Input.KEY_R)) { text += "r"; }
				if (isKeyTyped(input, Input.KEY_S)) { text += "s"; }
				if (isKeyTyped(input, Input.KEY_T)) { text += "t"; }
				if (isKeyTyped(input, Input.KEY_U)) { text += "u"; }
				if (isKeyTyped(input, Input.KEY_V)) { text += "v"; }
				if (isKeyTyped(input, Input.KEY_W)) { text += "w"; }
				if (isKeyTyped(input, Input.KEY_X)) { text += "x"; }
				if (isKeyTyped(input, Input.KEY_Y)) { text += "y"; }
				if (isKeyTyped(input, Input.KEY_Z)) { text += "z"; }
				if (isKeyTyped(input, Input.KEY_1)) { text += "1"; }
				if (isKeyTyped(input, Input.KEY_2)) { text += "2"; }
				if (isKeyTyped(input, Input.KEY_3)) { text += "3"; }
				if (isKeyTyped(input, Input.KEY_4)) { text += "4"; }
				if (isKeyTyped(input, Input.KEY_5)) { text += "5"; }
				if (isKeyTyped(input, Input.KEY_6)) { text += "6"; }
				if (isKeyTyped(input, Input.KEY_7)) { text += "7"; }
				if (isKeyTyped(input, Input.KEY_8)) { text += "8"; }
				if (isKeyTyped(input, Input.KEY_9)) { text += "9"; }
				if (isKeyTyped(input, Input.KEY_0)) { text += "0"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD1)) { text += "1"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD2)) { text += "2"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD3)) { text += "3"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD4)) { text += "4"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD5)) { text += "5"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD6)) { text += "6"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD7)) { text += "7"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD8)) { text += "8"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD9)) { text += "9"; }
				if (isKeyTyped(input, Input.KEY_NUMPAD0)) { text += "0"; }
				if (isKeyTyped(input, Input.KEY_SPACE)) { text += " "; }
			}
		}
		
		if (isKeyTyped(input, Input.KEY_BACK) && text.length() >= 1) {
			text = text.substring(0, text.length() - 1);
		}
	}

	@Override
	protected void renderInPosition(Artist artist) {
		float textX = this.getScreenX();
		float textY = this.getScreenY();
		
		String measurementText = text;
		if (text.length() == 0) { measurementText = "_"; }
		
		Font font = getFont();
		float textWidth = font.getWidth(measurementText);
		float textHeight = font.getLineHeight();
		
		if (hAlign == HAlign.RIGHT) { textX += getWidth() - textWidth; }
		if (vAlign == VAlign.BOTTOM) { textY += getHeight() - textHeight; }
		if (hAlign == HAlign.CENTER) { textX += (getWidth() - textWidth) / 2; }
		if (vAlign == VAlign.CENTER) { textY += (getHeight() - textHeight) / 2; }
		
		if (cursorTick < 20) {
			artist.slickFont().draw(font, text, textX, textY, color);
		}
		else {
			artist.slickFont().draw(font, text + "_", textX, textY, color);
		}
		
		
		super.renderInPosition(artist);
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	public Font getFont() {
		return font;
	}

	public HAlign getHAlign() {
		return hAlign;
	}
	public void setHAlign(HAlign hAlign) {
		this.hAlign = hAlign;
	}

	public VAlign getVAlign() {
		return vAlign;
	}
	public void setVAlign(VAlign vAlign) {
		this.vAlign = vAlign;
	}
	
	public void setAlign(VAlign vAlign, HAlign hAlign) {
		setHAlign(hAlign);
		setVAlign(vAlign);
	}

	public boolean isFocused() {
		return focused;
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	public int getCharLimit() {
		return charLimit;
	}
	public void setCharLimit(int charLimit) {
		this.charLimit = charLimit;
	}
}
