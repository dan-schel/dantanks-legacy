package com.danschellekens.slick2d.graphics;

import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.operations.Degrees;


/**
 * The class used for rendering to a DanGame.
 * @author Dan2002s
 */
public class Artist {
	Transform currentTransform;
	Stack<Transform> transformStack;
	Graphics graphics;
	
	boolean pixelArtMode;
	
	/**
	 * Creates a new Artist.
	 */
	public Artist(Graphics graphics) {
		this.graphics = graphics;
		
		currentTransform = new Transform();
		transformStack = new Stack<Transform>();
	}

	/**
	 * Returns the graphics class from Slick2D. 
	 */
	public Graphics getGraphics() {
		return graphics;
	}
	
	/**
	 * Resets the graphics options in the Graphics class. You shouldn't really ever need
	 * to use this.
	 */
	public void resetGraphics() {
		graphics.setColor(new Color(255, 255, 255));
		graphics.setLineWidth(1);
		graphics.setDrawMode(Graphics.MODE_NORMAL);
		graphics.setAntiAlias(true);
		graphics.clearClip();
		graphics.clearWorldClip();
	}
	
	/**
	 * 
	 */
	public void setBlendMode(BlendMode blendMode) {
		if (blendMode == BlendMode.NORMAL) {
			graphics.setDrawMode(Graphics.MODE_NORMAL);
		}
		else if (blendMode == BlendMode.ADD) {
			graphics.setDrawMode(Graphics.MODE_ADD);
		}
		else if (blendMode == BlendMode.MULTIPLY) {
			graphics.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
		}
		else {
			throw new Error("A new blend mode has been added that isn't yet supported by"
					+ " this function.");
		}
	}
	
	/**
	 * Returns an ImageArtist.
	 */
	public ImageArtist image() {
		return new ImageArtist(this);
	}
	
	/**
	 * Returns an ImageArtist which uses a custom transform.
	 */
	public ImageArtist image(Transform transform) {
		return new ImageArtist(this, transform);
	}
	
	/**
	 * Returns an ShapeArtist.
	 */
	public ShapeArtist shape() {
		return new ShapeArtist(this);
	}
	
	/**
	 * Returns an ShapeArtist which uses a custom transform.
	 */
	public ShapeArtist shape(Transform transform) {
		return new ShapeArtist(this, transform);
	}
	
	/**
	 * Returns an FontArtist.
	 */
	public BitmapFontArtist bitmapFont() {
		return new BitmapFontArtist(this);
	}
	
	/**
	 * Returns an FontArtist which uses a custom transform.
	 */
	public BitmapFontArtist bitmapFont(Transform transform) {
		return new BitmapFontArtist(this, transform);
	}

	/**
	 * Returns an FontArtist.
	 */
	public SlickFontArtist slickFont() {
		return new SlickFontArtist(this);
	}
	
	/**
	 * Returns an FontArtist which uses a custom transform.
	 */
	public SlickFontArtist slickFont(Transform transform) {
		return new SlickFontArtist(this, transform);
	}
	
	/**
	 * Returns an LineArtist.
	 */
	public LineArtist line() {
		return new LineArtist(this);
	}
	
	/**
	 * Returns an LineArtist which uses a custom transform.
	 */
	public LineArtist line(Transform transform) {
		return new LineArtist(this, transform);
	}

	/**
	 * Returns the current transform.
	 */
	public Transform getTransform() {
		if (currentTransform == null) { return new Transform(); }
		return currentTransform;
	}

	/**
	 * Sets the current transform, after pushing the old transform to the stack.
	 */
	public void setTransform(Transform transform) {
		transformStack.push(TransformCloner.Clone(currentTransform));
		currentTransform = transform;
	}
	/**
	 * Sets the current transform to a new transform, after pushing the old one to the stack.
	 */
	public void resetTransform() {
		setTransform(new Transform());
	}
	
	/**
	 * Clears the transform stack, and then sets the current transform.
	 */
	public void setTransformAsOnly(Transform transform) {
		transformStack.clear();
		currentTransform = transform;
	}
	
	/**
	 * Sets the current transform without first pushing the old transform to the stack.
	 * The old transform is forever lost. The stack is not altered in any way.
	 */
	public void setTransformOverride(Transform transform) {
		currentTransform = transform;
	}
	
	/**
	 * Saves the state of the current transform. Use popTransform to retrieve it.
	 */
	public void push() {
		transformStack.push(TransformCloner.Clone(currentTransform));
	}
	
	/**
	 * Retrieves the previously pushed transform.
	 */
	public void pop() {
		if (transformStack.isEmpty()) { currentTransform = new Transform(); }
		else { currentTransform = transformStack.pop(); }
	}
	
	/**
	 * Sets the current transform to a fresh transform. The old transform is pushed
	 * to the stack.
	 */
	public void pushAndClear() {
		setTransform(new Transform());
	}
	
	/**
	 * Clears the transform stack, and then sets the current transform to be a fresh 
	 * transform.
	 */
	public void deleteAndClear() {
		setTransformAsOnly(new Transform());
	}
	
	/**
	 * Applies translation to the current transform.
	 */
	public void translate(float x, float y) {
		setTransformOverride(getTransform().concatenate(
				Transform.createTranslateTransform(x, y)
		));
	}
	
	/**
	 * Applies translation to the current transform.
	 * @param degrees The angle to rotate by (in degrees).
	 */
	public void rotate(float degrees) {
		setTransformOverride(getTransform().concatenate(
				Transform.createRotateTransform(Degrees.ToRadians(degrees))
		));
	}
	
	/**
	 * Applies translation to the current transform, based on a set of coordinates.
	 * @param degrees The angle to rotate by (in degrees).
	 */
	public void rotate(float degrees, float x, float y) {
		setTransformOverride(getTransform().concatenate(
				Transform.createRotateTransform(Degrees.ToRadians(degrees), x, y)				
		));
	}
	
	/**
	 * Applies scaling to the current transform.
	 */
	public void scale(float scaleX, float scaleY) {
		setTransformOverride(getTransform().concatenate(
				Transform.createScaleTransform(scaleX, scaleY)
		));
	}
	
	/**
	 * Applies scaling to the current transform.
	 */
	public void scale(float scale) {
		scale(scale, scale);
	}

	/**
	 * Returns true if the artist should be in pixel art mode.
	 */
	public boolean isPixelArtMode() {
		return pixelArtMode;
	}

	/**
	 * Sets if the artist should be in pixel art mode.
	 */
	public void setPixelArtMode(boolean pixelArtMode) {
		this.pixelArtMode = pixelArtMode;
	}
}
