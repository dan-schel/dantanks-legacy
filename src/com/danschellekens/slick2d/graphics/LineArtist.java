package com.danschellekens.slick2d.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Transform;

/**
 * The Artist that renders lines.
 * @author Dan2002s
 */
public class LineArtist {
	Artist artist;
	Transform transform;
	Graphics graphics;
	
	/**
	 * Creates a new LineArtist.
	 */
	public LineArtist(Artist artist, Transform transform) {
		this.artist = artist;
		this.transform = transform;
		this.graphics = artist.getGraphics();
	}
	
	/**
	 * Creates a new LineArtist.
	 */
	public LineArtist(Artist artist) {
		this(artist, artist.getTransform());
	}
	
	/** Draws the line. */
	public void draw(float x1, float y1, float x2, float y2, Color color, float strokeWeight, BlendMode blendMode) {
		artist.shape().draw(new Line(x1, y1, x2, y2), null, color, strokeWeight, blendMode);
	}
	public void draw(float x1, float y1, float x2, float y2, Color color, float strokeWeight) 
	{ draw(x1, y1, x2, y2, color, strokeWeight, BlendMode.NORMAL); }
	public void draw(float x1, float y1, float x2, float y2, Color color) 
	{ draw(x1, y1, x2, y2, color, 1); }
	public void draw(float x1, float y1, float x2, float y2) 
	{ draw(x1, y1, x2, y2, new Color(255, 255, 255)); }
}
