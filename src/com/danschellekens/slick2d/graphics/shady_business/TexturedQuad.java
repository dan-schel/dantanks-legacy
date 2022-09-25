package com.danschellekens.slick2d.graphics.shady_business;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

import com.danschellekens.slick2d.graphics.ColorFilterMode;

public class TexturedQuad {
    Shape shape;
    Image texture;
    float srcX, srcY, srcX2, srcY2;
    
    Color filterColor;
    ColorFilterMode filterMode;

    /**
     * Creates a TexturedQuad.
     * @throws Exception If the provided shape does not have 4 sides.
     */
    public TexturedQuad(Shape shape, Image texture, float srcX, float srcY, float srcX2, float srcY2) throws Exception {
        this.shape = shape;
        this.texture = texture;
        
        if (texture == null) { throw new Exception("Texture cannot be null."); }
        
        this.srcX = srcX;
        this.srcY = srcY;
        this.srcX2 = srcX2;
        this.srcY2 = srcY2;
        
        filterColor = new Color(255, 255, 255);
        filterMode = ColorFilterMode.BLEND;
        
        if (shape == null) { throw new Exception("Shape cannot be null."); }
        if (shape.getPointCount() != 4) { throw new Exception("Shape must have 4 points."); }
    }

    /**
     * Renders the textured shape to the screen.
     */
    public void render() {
    	// Bind texture.
        TextureImpl.bindNone();
        texture.getTexture().bind();
        
        // Bind color.
        Color filterColor = getFilterColor();
        filterColor.bind();
        
        // Get renderer.
        SGL GL = Renderer.get();
        
        if (getFilterMode() == ColorFilterMode.REPLACE_COLOR && GL.canSecondaryColor()) {
			GL.glEnable(SGL.GL_COLOR_SUM_EXT);
			GL.glSecondaryColor3ubEXT(
					(byte)(filterColor.r * 255), 
					(byte)(filterColor.g * 255), 
					(byte)(filterColor.b * 255)
			);
		}
        
        // Setting antialising options.
        GL.glTexParameteri(SGL.GL_TEXTURE_2D, SGL.GL_TEXTURE_MIN_FILTER, texture.getFilter()); 
        GL.glTexParameteri(SGL.GL_TEXTURE_2D, SGL.GL_TEXTURE_MAG_FILTER, texture.getFilter()); 
        
        // Not sure if this is needed:
        // GL.glTexEnvi(SGL.GL_TEXTURE_ENV, SGL.GL_TEXTURE_ENV_MODE, SGL.GL_MODULATE);

        GL.glEnable(SGL.GL_TEXTURE_2D);
        GL.glBegin(SGL.GL_QUADS);

        GL.glTexCoord2f(srcX, srcY);
        GL.glVertex2f(shape.getPoints()[0], shape.getPoints()[1]);

        GL.glTexCoord2f(srcX2, srcY);
        GL.glVertex2f(shape.getPoints()[2], shape.getPoints()[3]);

        GL.glTexCoord2f(srcX2, srcY2);
        GL.glVertex2f(shape.getPoints()[4], shape.getPoints()[5]);

        GL.glTexCoord2f(srcX, srcY2);
        GL.glVertex2f(shape.getPoints()[6], shape.getPoints()[7]);

        GL.glEnd();
        GL.glDisable(SGL.GL_TEXTURE_2D);
        
        if (GL.canSecondaryColor()) {
			GL.glDisable(SGL.GL_COLOR_SUM_EXT);
		}
    }

    /**
     * Returns the shape.
     */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Returns the texture.
	 */
	public Image getTexture() {
		return texture;
	}

	/**
	 * Returns the color of the filter.
	 */
	public Color getFilterColor() {
		if (filterColor == null) {
			return new Color(255, 255, 255);
		}
		return filterColor;
	}

	/**
	 * Sets the color of the filter.
	 */
	public void setFilterColor(Color filter) {
		this.filterColor = filter;
	}

	/**
	 * Returns the mode of the filter.
	 */
	public ColorFilterMode getFilterMode() {
		return filterMode;
	}

	/**
	 * Sets the mode of the filter.
	 */
	public void setFilterMode(ColorFilterMode colorFilterMode) {
		this.filterMode = colorFilterMode;
	}
	
	/**
	 * Sets both the filter color and the filter mode.
	 */
	public void setFilter(Color color, ColorFilterMode mode) {
		setFilterColor(color);
		setFilterMode(mode);
	}
}
