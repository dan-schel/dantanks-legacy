package com.danschellekens.slick2d.graphics.font;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Stores a Bitmap based font.
 * @author dnsc3346
 */
public class BitmapFont {
	public static final String ASCII = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	public static final Color PINK = new Color(255, 0, 255);
	
	Image sourceImage;
	HashMap<Character, Image> charsImage;
	
	String charSet;
	int topSpacing;
	int bottomSpacing;
	int charHeight;
	int horizontalSpacing;
	
	/**
	 * Creates a new BitmapFont.
	 * @throws Exception 
	 */
	public BitmapFont(Image sourceImage) throws Exception {
		this.sourceImage = sourceImage;
		
		charSet = ASCII;
		
		topSpacing = 0;
		bottomSpacing = 0;
		charHeight = 0;
		horizontalSpacing = 1;
	}
	public void init() throws Exception {
		initChars();
	}
	void initChars() throws Exception {
		charsImage = new HashMap<Character, Image>();
		int charIndex = 0;
		
		for (int y = 1; y < sourceImage.getHeight() - 2; y++) {
			for (int x = 1; x < sourceImage.getWidth() - 2; x++) {
				Color color = sourceImage.getColor(x, y);
				if (!color.equals(PINK)) {
					Color oneAbove = sourceImage.getColor(x, y - 1);
					Color oneLeft = sourceImage.getColor(x - 1, y);
					if (oneAbove.equals(PINK) && oneLeft.equals(PINK)) {
						Image charImage = initChar(x, y);
						charImage.setFilter(sourceImage.getFilter());
						
						if (charHeight + topSpacing + bottomSpacing != charImage.getHeight()) {
							throw new Exception("The CharHeight, TopSpacing and BottomSpacing"
									+ " do not match the characters I'm getting in the images!");
						}
						
						charsImage.put(charSet.charAt(charIndex), charImage);
						charIndex++;
					}
				}
			}
		}
	}
	Image initChar(int startX, int startY) {
		int width = 1;
		int height = 1;
		
		while (!sourceImage.getColor(startX + width, startY).equals(PINK)) {
			width++;
		}
		while (!sourceImage.getColor(startX, startY + height).equals(PINK)) {
			height++;
		}
		
		return sourceImage.getSubImage(startX, startY, width, height);
	}
	
	/**
	 * Returns the image that is the character.
	 */
	public Image getChar(char requested) {
		if (charsImage == null) { throw new RuntimeException("Init the font before use."); }
		
		if (hasChar(requested)) {
			return charsImage.get(requested);
		}
		return null;
	}
	
	/**
	 * Returns true is the provided character is included in this font.
	 */
	public boolean hasChar(char requested) {
		if (charsImage == null) { throw new RuntimeException("Init the font before use."); }
		
		return charsImage.containsKey(requested);
	}
	
	public int measureWidth(String text) {
		if (charsImage == null) { throw new RuntimeException("Init the font before use."); }
		if (text == null || text.isEmpty()) { return 0; }
		
		int width = 0;
		for (int i = 0; i < text.length(); i++) {
			Image glyph = getChar(text.charAt(i));
			if (glyph != null) { 
				width += glyph.getWidth() + this.getHorizontalSpacing();
			}
		}
		return width - this.getHorizontalSpacing();
	}
	
	public int measureHeight(String text) {
		if (charsImage == null) { throw new RuntimeException("Init the font before use."); }
		if (text == null || text.isEmpty()) { return 0; }
		return charHeight;
	}
	
	/**
	 * Returns a string containing each character in the order they appear on 
	 * the image.
	 */
	public String getCharSet() {
		return charSet;
	}
	/**
	 * Sets a string containing each character in the order they appear on 
	 * the image.
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	/**
	 * Returns the amount of space provided above the characters in the image.
	 */
	public int getTopSpacing() {
		return topSpacing;
	}
	/**
	 * Sets the amount of space provided above the characters in the image.
	 */
	public void setTopSpacing(int topSpacing) {
		this.topSpacing = topSpacing;
	}
	/**
	 * Returns the amount of space provided below the characters in the image.
	 */
	public int getBottomSpacing() {
		return bottomSpacing;
	}
	/**
	 * Sets the amount of space provided below the characters in the image.
	 */
	public void setBottomSpacing(int bottomSpacing) {
		this.bottomSpacing = bottomSpacing;
	}
	/**
	 * Returns the height of the characters in the image.
	 */
	public int getCharHeight() {
		return charHeight;
	}
	/**
	 * Sets the height of the characters in the image.
	 */
	public void setCharHeight(int charHeight) {
		this.charHeight = charHeight;
	}
	/**
	 * Returns the spacing between each character used in rendering.
	 */
	public int getHorizontalSpacing() {
		return horizontalSpacing;
	}
	/**
	 * Sets the spacing between each character used in rendering.
	 */
	public void setHorizontalSpacing(int horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
	}
}
