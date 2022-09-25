package com.danschellekens.slick2d.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * The Artist that renders shapes.
 * @author Dan2002s
 */
public class ShapeArtist {
	Artist artist;
	Transform transform;
	Graphics graphics;
	
	/**
	 * Creates a new ShapeArtist.
	 */
	public ShapeArtist(Artist artist, Transform transform) {
		this.artist = artist;
		this.transform = transform;
		this.graphics = artist.getGraphics();
	}
	
	/**
	 * Creates a new ShapeArtist.
	 */
	public ShapeArtist(Artist artist) {
		this(artist, artist.getTransform());
	}
	
	/** Draws a shape. */
	public void draw(Shape shape, Color fill, Color stroke, float strokeWeight, 
			BlendMode blendMode) {
		
		artist.resetGraphics();
		artist.setBlendMode(blendMode);
		
		shape = shape.transform(transform);
		
		if (fill != null) {
			graphics.setColor(fill);
			graphics.fill(shape);
		}
		
		if (stroke != null) {
			graphics.setColor(stroke);
			graphics.setLineWidth(strokeWeight);
			graphics.draw(shape);
		}
	}
	
	/** Draws a shape. */
	public void draw(Shape shape, Color fill, BlendMode mode)
	{ draw(shape, fill, null, 1, mode); }
	
	/** Draws a shape. */
	public void draw(Shape shape, Color fill, Color stroke, float strokeWeight)
	{ draw(shape, fill, stroke, strokeWeight, BlendMode.NORMAL); }
	
	/** Draws a shape. */
	public void draw(Shape shape, Color stroke, float strokeWeight)
	{ draw(shape, null, stroke, strokeWeight); }
	
	/** Draws a shape. */
	public void draw(Shape shape, Color fill)
	{ draw(shape, fill, null, 1); }
	
	/** Draws a shape. */
	public void draw(Shape shape)
	{ draw(shape, new Color(255, 255, 255)); }
}
