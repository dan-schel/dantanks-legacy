package com.danschellekens.dangame.rect_ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class Label extends UIElement {
	String text;
	Color color;
	
	Font font;
	
	HAlign hAlign;
	VAlign vAlign;
	
	public Label(UIElement parent, Rectangle area, Font font) {
		super(parent, area);
		
		this.text = "";
		this.color = new Color(0, 0, 0);
		
		this.font = font;
		
		this.hAlign = HAlign.LEFT;
		this.vAlign = VAlign.CENTER;
	}

	@Override
	public void update(DanInput input) {
		super.update(input);
	}

	@Override
	public void render(Artist artist) {
		float textX = this.getScreenX();
		float textY = this.getScreenY();
		
		Font font = getFont();
		float textWidth = font.getWidth(text);
		float textHeight = font.getLineHeight();
		
		if (hAlign == HAlign.RIGHT) { textX += getWidth() - textWidth; }
		if (vAlign == VAlign.BOTTOM) { textY += getHeight() - textHeight; }
		if (hAlign == HAlign.CENTER) { textX += (getWidth() - textWidth) / 2; }
		if (vAlign == VAlign.CENTER) { textY += (getHeight() - textHeight) / 2; }
		
		artist.slickFont().draw(font, text, textX, textY, color);
		
		super.render(artist);
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
}
