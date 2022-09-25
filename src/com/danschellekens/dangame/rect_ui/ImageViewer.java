package com.danschellekens.dangame.rect_ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class ImageViewer extends UIElement {
	Image image;
	Color filter;
	
	public ImageViewer(UIElement parent, Rectangle area, Image image) {
		super(parent, area);
		
		this.image = image;
		filter = new Color(255, 255, 255);
	}

	@Override
	public void update(DanInput input) {
		
	}

	@Override
	protected void renderInPosition(Artist artist) {
		if (image != null) {
			artist.image().draw(image, 0, 0, getWidth(), getHeight(), filter);
		}
	}

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

	public Color getFilter() {
		return filter;
	}
	public void setFilter(Color filter) {
		this.filter = filter;
	}
}
