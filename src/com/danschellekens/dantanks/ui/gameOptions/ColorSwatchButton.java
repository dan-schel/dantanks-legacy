package com.danschellekens.dantanks.ui.gameOptions;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.values.PlayerColor;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;

public class ColorSwatchButton extends LongRectangleTextButton {
	PlayerColor color;
	
	public ColorSwatchButton(UIElement parent, Rectangle space, PlayerColor color) {
		super(parent, space, "");
		
		this.color = color;
		this.setColor(color.getColor());
	}

	public PlayerColor getPlayerColor() {
		return color;
	}
}
