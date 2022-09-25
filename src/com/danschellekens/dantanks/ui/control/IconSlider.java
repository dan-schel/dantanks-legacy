package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class IconSlider extends UIElement {
	ImageViewer icon;
	Slider slider;
	Label label;
	
	String prefix;
	String suffix;
	boolean usingPercent;
	
	public IconSlider(UIElement parent, float x, float y, float sliderWidth, Image icon) {
		super(parent, new Rectangle(x, y, 80 + sliderWidth, 20));
		
		this.icon = new ImageViewer(this, new Rectangle(0, 0, 20, 20), icon);
		this.slider = new Slider(this, new Rectangle(30, 0, sliderWidth, 20));
		this.label = new Label(this, new Rectangle(40 + sliderWidth, 0, 40, 18), FontLibrary.getBodyFont());
		this.label.setHAlign(HAlign.LEFT);
		
		prefix = "";
		suffix = "";
		usingPercent = false;
	}

	@Override
	public void update(DanInput input) {
		String valueText = "";
		
		if (usingPercent) {
			float value = this.slider.getValuePercent() * 100;
			valueText = String.valueOf(Math.round(value)) + "%";
		}
		else {
			valueText = String.valueOf(Math.round(this.slider.getValue()));
		}
		label.setText(prefix + valueText + suffix);
		
		icon.update(input);
		slider.update(input);
		label.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		icon.render(artist);
		slider.render(artist);
		label.render(artist);
	}
	
	public float getValue() {
		return getSlider().getValue();
	}

	public ImageViewer getIcon() {
		return icon;
	}

	public Slider getSlider() {
		return slider;
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
	
	public void setSliderValue(float value) {
		slider.setValue(value);
	}
	
	public void setSliderInitialValues(float value, float min, float max) {
		slider.setInitialValues(value, min, max);
	}
	public void setSliderInitialValues(float value, float zeroPoint, float min, float max) {
		slider.setInitialValues(value, zeroPoint, min, max);
	}
	public void setSliderInitialValues(float value, float min, float max, float usableMin, float usableMax) {
		slider.setInitialValues(value, min, max, usableMin, usableMax);
	}
	public void setSliderInitialValues(float value, float zeroPoint, float min, float max, float usableMin, float usableMax) {
		slider.setInitialValues(value, zeroPoint, min, max, usableMin, usableMax);
	}
	
	public void setSliderValues(float min, float max) {
		slider.setValues(min, max);
	}
	public void setSliderValues(float zeroPoint, float min, float max) {
		slider.setValues(zeroPoint, min, max);
	}
	public void setSliderValues(float min, float max, float usableMin, float usableMax) {
		slider.setValues(min, max, usableMin, usableMax);
	}
	public void setSliderValues(float zeroPoint, float min, float max, float usableMin, float usableMax) {
		slider.setValues(zeroPoint, min, max, usableMin, usableMax);
	}
	public void setSliderGhostValue(float ghostValue) {
		slider.setGhostValue(ghostValue);
	}
	public void setSliderHideGhostValue() {
		slider.hideGhostValue();
	}
}

