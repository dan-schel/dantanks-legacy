package com.danschellekens.dantanks.data.values;

import org.newdawn.slick.Color;

import com.danschellekens.operations.Random;

public enum PlayerColor {
	RED(new Color(240, 0, 0)),
	ORANGE(new Color(240, 140, 0)),
	YELLOW(new Color(240, 200, 0)),
	LIME(new Color(160, 190, 0)),
	GREEN(new Color(0, 180, 20)),
	CYAN(new Color(0, 180, 180)),
	LIGHT_BLUE(new Color(0, 150, 240)),
	DARK_BLUE(new Color(50, 80, 240)),
	PURPLE(new Color(160, 100, 240)),
	PINK(new Color(240, 0, 150));
	
	private final Color color;
	
	private PlayerColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static PlayerColor[] AvailableColors() {
		PlayerColor[] colors = new PlayerColor[10];
		colors[0] = RED;
		colors[1] = ORANGE;
		colors[2] = YELLOW;
		colors[3] = LIME;
		colors[4] = GREEN;
		colors[5] = CYAN;
		colors[6] = LIGHT_BLUE;
		colors[7] = DARK_BLUE;
		colors[8] = PURPLE;
		colors[9] = PINK;
		return colors;
	}
	public static PlayerColor RandomColor() {
		PlayerColor[] colors = AvailableColors();
		return colors[Random.Int(colors.length)];
	}
}
