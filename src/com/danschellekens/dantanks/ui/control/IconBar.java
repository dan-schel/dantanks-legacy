package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class IconBar extends UIElement {
	ImageViewer icon;
	ProgressBar bar;
	Label label;
	
	String prefix;
	String suffix;
	boolean usingPercent;
	
	public IconBar(UIElement parent, float x, float y, float barWidth, Image icon) {
		super(parent, new Rectangle(x, y, 80 + barWidth, 20));
		
		this.icon = new ImageViewer(this, new Rectangle(0, 0, 20, 20), icon);
		this.bar = new ProgressBar(this, new Rectangle(30, 4, barWidth, 12));
		this.label = new Label(this, new Rectangle(40 + barWidth, 0, 40, 18), FontLibrary.getBodyFont());
		this.label.setHAlign(HAlign.LEFT);
		
		prefix = "";
		suffix = "";
		usingPercent = false;
	}

	@Override
	public void update(DanInput input) {
		String valueText = "";
		
		if (usingPercent) {
			float value = this.bar.getValuePercent() * 100;
			valueText = String.valueOf(Math.round(value)) + "%";
		}
		else {
			valueText = String.valueOf(Math.round(this.bar.getValue()));
		}
		label.setText(prefix + valueText + suffix);
		
		icon.update(input);
		bar.update(input);
		label.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		icon.render(artist);
		bar.render(artist);
		label.render(artist);
	}

	public ImageViewer getIcon() {
		return icon;
	}

	public ProgressBar getBar() {
		return bar;
	}

	public Label getLabel() {
		return label;
	}

	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean isUsingPercent() {
		return usingPercent;
	}
	public void setUsingPercent(boolean usingPercent) {
		this.usingPercent = usingPercent;
	}
	
	public void setBarValue(float value) {
		bar.setValue(value);
	}
	public void setBarValues(float value, float min, float max) {
		bar.setValues(value, min, max);
	}
}

