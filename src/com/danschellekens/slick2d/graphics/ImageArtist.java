package com.danschellekens.slick2d.graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.shady_business.TexturedQuad;

/**
 * The Artist that renders images.
 * @author Dan2002s
 */
public class ImageArtist {
	Artist artist;
	Transform transform;
	Graphics graphics;
	
	/**
	 * Creates a new ImageArtist.
	 */
	public ImageArtist(Artist artist, Transform transform) {
		this.artist = artist;
		this.transform = transform;
		this.graphics = artist.getGraphics();
	}
	
	/**
	 * Creates a new ImageArtist.
	 */
	public ImageArtist(Artist artist) {
		this(artist, artist.getTransform());
	}
	
	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height, 
			float srcX, float srcY, float srcW, float srcH, BlendMode blendMode, Color filter, ColorFilterMode filterMode, boolean centered) {
		
		artist.resetGraphics();
		artist.setBlendMode(blendMode);
		
		try {
			float offsetX = centered ? width/-2 : 0;
			float offsetY = centered ? height/-2 : 0;
			
			image.setFilter(artist.isPixelArtMode() ? Image.FILTER_NEAREST : Image.FILTER_LINEAR);
			
			TexturedQuad renderer = new TexturedQuad(
				new Rectangle(x, y, width, height).transform(Transform.createTranslateTransform(offsetX, offsetY)).transform(transform),
				image,
				srcX, srcY, srcX + srcW, srcY + srcH
			);
			renderer.setFilter(filter, filterMode);
			renderer.render();
		}
		catch (Exception e) {
			throw new Error("This code should be creating a rectangle. If it is"
					+ " I don't know what went wrong.", e);
		}
	}
	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height, float srcX, float srcY, float srcW, float srcH) {
		float asrcX = Range.Map(srcX, 0, image.getWidth(), image.getTextureOffsetX(), image.getTextureOffsetX() + image.getTextureWidth());
		float asrcY = Range.Map(srcY, 0, image.getHeight(), image.getTextureOffsetY(), image.getTextureOffsetY() + image.getTextureHeight());
		float asrcW = Range.Map(srcW, 0, image.getWidth(), 0, image.getTextureWidth());
		float asrcH = Range.Map(srcH, 0, image.getHeight(), 0, image.getTextureHeight());
		
		draw(image, x, y, width, height, asrcX, asrcY, asrcW, asrcH, BlendMode.NORMAL, new Color(255, 255, 255), ColorFilterMode.BLEND, false);
	}
	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height, 
			BlendMode blendMode, Color filter, ColorFilterMode filterMode, boolean centered) {
		draw(image, x, y, width, height, image.getTextureOffsetX(), image.getTextureOffsetY(), image.getTextureWidth(), image.getTextureHeight(), blendMode, filter, filterMode, centered);
	}
	/** Draws an image. */
	public void draw(Image image, float x, float y, BlendMode blendMode, Color filter, ColorFilterMode filterMode)
	{ draw(image, x, y, image.getWidth(), image.getHeight(), blendMode, filter, filterMode, false); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, BlendMode blendMode, Color filter)
	{ draw(image, x, y, blendMode, filter, ColorFilterMode.BLEND); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, Color filter)
	{ draw(image, x, y, BlendMode.NORMAL, filter); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, BlendMode blendMode)
	{ draw(image, x, y, blendMode, new Color(255, 255, 255)); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float scale, BlendMode blendMode, Color filter, ColorFilterMode filterMode)
	{ draw(image, x, y, image.getWidth() * scale, image.getHeight() * scale, blendMode, filter, filterMode, false); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float scale, BlendMode blendMode, Color filter)
	{ draw(image, x, y, scale, blendMode, filter, ColorFilterMode.BLEND); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float scale, Color filter)
	{ draw(image, x, y, scale, BlendMode.NORMAL, filter); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float scale, BlendMode blendMode)
	{ draw(image, x, y, scale, blendMode, new Color(255, 255, 255)); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height, BlendMode blendMode, Color filter)
	{ draw(image, x, y, width, height, blendMode, filter, ColorFilterMode.BLEND, false); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height, Color filter)
	{ draw(image, x, y, width, height, BlendMode.NORMAL, filter); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height, BlendMode blendMode)
	{ draw(image, x, y, width, height, blendMode, new Color(255, 255, 255)); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float width, float height)
	{ draw(image, x, y, width, height, BlendMode.NORMAL); }

	/** Draws an image. */
	public void draw(Image image, float x, float y, float scale)
	{ draw(image, x, y, image.getWidth() * scale, image.getHeight() * scale); }

	/** Draws an image. */
	public void draw(Image image, float x, float y)
	{ draw(image, x, y, 1); }

	/** Draws an image. */
	public void draw(Image image) 
	{ draw(image, 0, 0); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, BlendMode blendMode, Color filter, ColorFilterMode filterMode)
	{ draw(image, x, y, image.getWidth(), image.getHeight(), blendMode, filter, filterMode, true); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, BlendMode blendMode, Color filter)
	{ drawCentered(image, x, y, blendMode, filter, ColorFilterMode.BLEND); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, Color filter)
	{ drawCentered(image, x, y, BlendMode.NORMAL, filter); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, BlendMode blendMode)
	{ drawCentered(image, x, y, blendMode, new Color(255, 255, 255)); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float scale, BlendMode blendMode, Color filter, ColorFilterMode filterMode)
	{ draw(image, x, y, image.getWidth() * scale, image.getHeight() * scale, blendMode, filter, filterMode, true); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float scale, BlendMode blendMode, Color filter)
	{ drawCentered(image, x, y, scale, blendMode, filter, ColorFilterMode.BLEND); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float scale, Color filter)
	{ drawCentered(image, x, y, scale, BlendMode.NORMAL, filter); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float scale, BlendMode blendMode)
	{ drawCentered(image, x, y, scale, blendMode, new Color(255, 255, 255)); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float width, float height, BlendMode blendMode, Color filter)
	{ draw(image, x, y, width, height, blendMode, filter, ColorFilterMode.BLEND, true); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float width, float height, Color filter)
	{ drawCentered(image, x, y, width, height, BlendMode.NORMAL, filter); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float width, float height, BlendMode blendMode)
	{ drawCentered(image, x, y, width, height, blendMode, new Color(255, 255, 255)); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float width, float height)
	{ drawCentered(image, x, y, width, height, BlendMode.NORMAL); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y, float scale)
	{ drawCentered(image, x, y, image.getWidth() * scale, image.getHeight() * scale); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image, float x, float y)
	{ drawCentered(image, x, y, 1); }

	/** Draws an image centered around the coodinates provided. */
	public void drawCentered(Image image) 
	{ drawCentered(image, 0, 0); }
}
