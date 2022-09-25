package com.danschellekens.slick2d.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.slick2d.graphics.font.BitmapFont;

/**
 * The Artist that renders fonts.
 * @author Dan2002s
 */
public class BitmapFontArtist {
	Artist artist;
	Transform transform;
	Graphics graphics;
	
	/**
	 * Creates a new FontArtist.
	 */
	public BitmapFontArtist(Artist artist, Transform transform) {
		this.artist = artist;
		this.transform = transform;
		this.graphics = artist.getGraphics();
	}
	
	/**
	 * Creates a new FontArtist.
	 */
	public BitmapFontArtist(Artist artist) {
		this(artist, artist.getTransform());
	}
	
	/** Draws the text. */
	public void draw(BitmapFont font, String text, float x, float y, Color color, BlendMode blendMode) {
		float nextCharX = x;
		for (int i = 0; i < text.length(); i++) {
			char current = text.charAt(i);
			Image glyph = font.getChar(current);
			if (glyph != null) {
				artist.image(transform).draw(glyph, nextCharX, y - font.getTopSpacing(), blendMode, color);
				nextCharX += glyph.getWidth() + font.getHorizontalSpacing();
			}
		}
	}
	/** Draws the text **/
	public void draw(BitmapFont font, String text, float x, float y, Color color) 
	{ draw(font, text, x, y, color, BlendMode.NORMAL); }
	/** Draws the text **/
	public void draw(BitmapFont font, String text, float x, float y)
	{ draw(font, text, x, y, new Color(255, 255, 255)); }
	/** Draws the text **/
	public void draw(BitmapFont font, String text, Color color)
	{ draw(font, text, 0, 0, color); }
	/** Draws the text **/
	public void draw(BitmapFont font, String text)
	{ draw(font, text, 0, 0); }
}
