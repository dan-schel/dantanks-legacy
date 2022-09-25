package com.danschellekens.slick2d.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.operations.Numbers;

/**
 * The Artist that renders fonts.
 * @author Dan2002s
 */
public class SlickFontArtist {
	Artist artist;
	Transform transform;
	Graphics graphics;
	
	/**
	 * Creates a new FontArtist.
	 */
	public SlickFontArtist(Artist artist, Transform transform) {
		this.artist = artist;
		this.transform = transform;
		this.graphics = artist.getGraphics();
	}
	
	/**
	 * Creates a new FontArtist.
	 */
	public SlickFontArtist(Artist artist) {
		this(artist, artist.getTransform());
	}
	
	/** Draws the text. */
	public void draw(Font font, String text, float x, float y, Color color, BlendMode blendMode) {
		artist.resetGraphics();
		artist.setBlendMode(blendMode);
		
		if (color != null && font != null) {
			graphics.setColor(color);
			graphics.setFont(font);
			graphics.drawString(text, Numbers.Round(x), Numbers.Round(y));
		}
	}
	/** Draws the text **/
	public void draw(Font font, String text, float x, float y, Color color) 
	{ draw(font, text, x, y, color, BlendMode.NORMAL); }
	/** Draws the text **/
	public void draw(Font font, String text, float x, float y)
	{ draw(font, text, x, y, new Color(255, 255, 255)); }
	/** Draws the text **/
	public void draw(Font font, String text, Color color)
	{ draw(font, text, 0, 0, color); }
	/** Draws the text **/
	public void draw(Font font, String text)
	{ draw(font, text, 0, 0); }
}
