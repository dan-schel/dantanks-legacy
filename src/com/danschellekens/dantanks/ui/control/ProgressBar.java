package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class ProgressBar extends UIElement {
	Color foreColor;
	Color backColor;
	
	float value;
	float min;
	float max;
	float displayValue;
	
	public ProgressBar(UIElement parent, Rectangle area) {
		super(parent, area);
		
		this.foreColor = new Color(0, 200, 40);
		this.backColor = new Color(0, 0, 0, 0.1f);
		
		this.value = 0f;
		this.min = 0;
		this.max = 1;
		this.displayValue = 0;
	}

	@Override
	public void update(DanInput input) {
		displayValue = Increment.Exponential(displayValue, value, 0.25f);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new Rectangle(0, 0, getWidth(), getHeight()), backColor);
		
		float fill = Range.MapConstrain(displayValue, min, max, 0, getWidth());
		artist.shape().draw(new Rectangle(0, 0, fill, getHeight()), foreColor);
	}
	
	public void setValues(float value, float min, float max) {
		setValue(value);
		setMin(min);
		setMax(max);
	}

	public Color getForeColor() {
		return foreColor;
	}
	public void setForeColor(Color foreColor) {
		this.foreColor = foreColor;
	}

	public Color getBackColor() {
		return backColor;
	}
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public float getValue() {
		return value;
	}
	public float getValuePercent() {
		return Range.MapConstrain(value, min, max, 0, 1);
	}
	public void setValue(float value) {
		this.value = value;
	}

	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
}

