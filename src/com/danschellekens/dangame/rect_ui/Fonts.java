package com.danschellekens.dangame.rect_ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

public class Fonts {
	public static HashMap<String, Font> fonts = new HashMap<String, Font>();
	
	public static Font getFont(String fontPath, float size) {
		String id = generateID(fontPath, size);
		
		if (!fonts.containsKey(id)) { 
			try {
				Font font = loadFontUnicode(fontPath, size);
				fonts.put(id, font);
				
				return font;
			} 
			catch (FontFormatException | IOException | SlickException e) {
				throw new RuntimeException("Cannot load font.", e);
			} 
		}
		return fonts.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public static Font loadFontUnicode(String fontPath, float size) throws FontFormatException, IOException, SlickException {
		final java.awt.Font javaFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(fontPath));
		final java.awt.Font sizedFont = javaFont.deriveFont(java.awt.Font.PLAIN, size);
		
		UnicodeFont uniFont = new UnicodeFont(sizedFont);
		uniFont.addAsciiGlyphs();
		uniFont.getEffects().add(new ColorEffect());
		uniFont.loadGlyphs();
		return uniFont;
	}
	
	public static String generateID(String fontPath, float size) {
		return "UF Path:\"" + fontPath + "\" Size:\"" + size + "\"";
	}
}
