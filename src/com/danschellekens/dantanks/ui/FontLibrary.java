package com.danschellekens.dantanks.ui;

import org.newdawn.slick.Font;

public class FontLibrary {
	public static Font getSmallFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/regular.ttf", 12);
	}
	public static Font getSemiSmallFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/regular.ttf", 14);
	}
	
	public static Font getBodyFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/regular.ttf", 16);
	}
	
	public static Font getBodyBoldFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/bold.ttf", 16);
	}
	
	public static Font getLargeFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/bold.ttf", 18);
	}
	
	public static Font getSubtitleFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/bold.ttf", 20);
	}
	
	public static Font getTitleFont() {
		return com.danschellekens.dangame.rect_ui.Fonts.getFont("font/bold.ttf", 26);
	}
}
