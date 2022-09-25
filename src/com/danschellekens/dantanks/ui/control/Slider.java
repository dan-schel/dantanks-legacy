package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Range;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class Slider extends UIElement {
	Color foreColor;
	Color backColor;
	Color zeroMarkerColor;
	Color blockedColor;
	
	float value;
	float zeroPoint;
	float visibleMin;
	float visibleMax;
	float usableMin;
	float usableMax;
	
	float ghostValue;
	boolean showGhostValue;
	
	float displayValue;
	float displayUsableMin;
	float displayUsableMax;
	
	float keyChangeSpeed;
	int leftKey = -1;
	int rightKey = -1;
	boolean dragging;
	
	public Slider(UIElement parent, Rectangle area) {
		super(parent, area);
		
		this.foreColor = new Color(0, 200, 40);
		this.backColor = new Color(0, 0, 0, 0.1f);
		this.zeroMarkerColor = new Color(0, 0, 0, 0.2f);
		this.blockedColor = new Color(0, 0, 0, 50);
		
		this.value = 0.5f;
		this.zeroPoint = 0;
		this.visibleMin = 0;
		this.visibleMax = 1;
		this.usableMin = 0;
		this.usableMax = 1;
		
		this.ghostValue = 0;
		this.showGhostValue = false;
		
		this.displayValue = 0;
		this.displayUsableMin = 0;
		this.displayUsableMax = 1;
		
		this.keyChangeSpeed = 0.01f;
	}

	@Override
	public void update(DanInput input) {
		getInput(input);
		
		value = Range.Constrain(value, usableMin, usableMax);
		
		displayValue = Increment.Exponential(displayValue, value, 0.25f);
		displayUsableMin = Increment.Exponential(displayUsableMin, usableMin, 0.25f);
		displayUsableMax = Increment.Exponential(displayUsableMax, usableMax, 0.25f);
		
		displayValue = Range.Constrain(displayValue, displayUsableMin, displayUsableMax);
	}
	void getInput(DanInput input) {
		if (leftKey != -1 && input.isKeyDown(leftKey)) {
			changeValueNoAnimation(-keyChangeSpeed);
		}
		if (rightKey != -1 && input.isKeyDown(rightKey)) {
			changeValueNoAnimation(keyChangeSpeed);
		}
		
		if (this.mouseJustPressedInside(input)) {
			dragging = true;
		}
		
		if (dragging) {
			if (input.isLeftMouseUp()) { dragging = false; }
			float mouseX = this.getMouseRelativePosition(input).getX();
			float newValue = Range.MapConstrain(mouseX, 0, getWidth(), getVisibleMin(), getVisibleMax());
			setValueNoAnimation(newValue);
		}
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new Rectangle(0, 4, getWidth(), getHeight() - 8), backColor);
		
		float valueX = getValueX(displayValue);
		float ghostX = getValueX(ghostValue);
		float zeroX = getValueX(getZeroPoint());
		float usableMinX = getValueX(displayUsableMin);
		float usableMaxX = getValueX(displayUsableMax);
		
		if (zeroX > 0 && zeroX < getWidth()) {
			artist.line().draw(zeroX, 0, zeroX, getHeight(), getZeroMarkerColor(), 2);
		}
		
		artist.shape().draw(new Rectangle(zeroX, 4, valueX - zeroX, getHeight() - 8), getForeColor());
		artist.shape().draw(new Rectangle(0, 4, usableMinX, getHeight() - 8), getBlockedColor());
		artist.shape().draw(new Rectangle(usableMaxX, 4, getWidth() - usableMaxX, getHeight() - 8), getBlockedColor());
		
		if (this.showGhostValue) {
			artist.line().draw(ghostX, -2, ghostX, getHeight() + 2, getForeColor(), 2);
		}
		
		drawSlider(artist, valueX);
	}
	void drawSlider(Artist artist, float valueX) {
		artist.push();
		artist.translate(valueX, 0);
		
		Polygon sliderShape = new Polygon();
		sliderShape.addPoint(-5, 0);
		sliderShape.addPoint(5, 0);
		sliderShape.addPoint(5, getHeight() - 5);
		sliderShape.addPoint(0, getHeight());
		sliderShape.addPoint(-5, getHeight() - 5);
		
		artist.shape().draw(sliderShape, getForeColor());
		artist.shape().draw(sliderShape, new Color(0, 0, 0, 50));
		
		artist.pop();
	}
	
	public void setInitialValues(float value, float min, float max) {
		setInitialValues(value, 0, min, max);
	}
	public void setInitialValues(float value, float zeroPoint, float min, float max) {
		setInitialValues(value, zeroPoint, min, max, min, max);
	}
	public void setInitialValues(float value, float min, float max, float usableMin, float usableMax) {
		setInitialValues(value, 0, min, max, usableMin, usableMax);
	}
	public void setInitialValues(float value, float zeroPoint, float min, float max, float usableMin, float usableMax) {
		setValueNoAnimation(value);
		setVisibleMin(min);
		setVisibleMax(max);
		setUsableMinNoAnimation(usableMin);
		setUsableMaxNoAnimation(usableMax);
		setZeroPoint(zeroPoint);
	}
	public void setValues(float min, float max) {
		setValues(0, min, max);
	}
	public void setValues(float zeroPoint, float min, float max) {
		setValues(zeroPoint, min, max, min, max);
	}
	public void setValues(float min, float max, float usableMin, float usableMax) {
		setValues(0, min, max, usableMin, usableMax);
	}
	public void setValues(float zeroPoint, float min, float max, float usableMin, float usableMax) {
		setVisibleMin(min);
		setVisibleMax(max);
		setUsableMin(usableMin);
		setUsableMax(usableMax);
		setZeroPoint(zeroPoint);
	}
	public float getGhostValue() {
		return ghostValue;
	}
	public void setGhostValue(float ghostValue) {
		this.ghostValue = ghostValue;
		this.showGhostValue = true;
	}
	public void hideGhostValue() {
		this.showGhostValue = false;
	}

	public float getValueX(float value) {
		return Range.MapConstrain(value, getVisibleMin(), getVisibleMax(), 0, getWidth());
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
	public Color getZeroMarkerColor() {
		return zeroMarkerColor;
	}
	public void setZeroMarkerColor(Color zeroMarkerColor) {
		this.zeroMarkerColor = zeroMarkerColor;
	}
	public Color getBlockedColor() {
		return blockedColor;
	}
	public void setBlockedColor(Color blockedColor) {
		this.blockedColor = blockedColor;
	}

	public float getValue() {
		return value;
	}
	public float getValuePercent() {
		float valuePercent = Range.MapConstrain(value, getVisibleMin(), getVisibleMax(), 0, 1);
		return valuePercent;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public void setValueNoAnimation(float value) {
		setValue(value);
		this.displayValue = getValue();
	}
	public void changeValue(float value) {
		setValue(getValue() + value);
	}
	public void changeValueNoAnimation(float value) {
		setValue(getValue() + value);
		this.displayValue = getValue();
	}

	public float getZeroPoint() {
		return zeroPoint;
	}
	public void setZeroPoint(float zeroPoint) {
		this.zeroPoint = zeroPoint;
	}

	public float getVisibleMin() {
		return visibleMin;
	}
	public void setVisibleMin(float visibleMin) {
		this.visibleMin = visibleMin;
	}

	public float getVisibleMax() {
		return visibleMax;
	}
	public void setVisibleMax(float visibleMax) {
		this.visibleMax = visibleMax;
	}

	public float getUsableMin() {
		return usableMin;
	}
	public void setUsableMin(float usableMin) {
		this.usableMin = usableMin;
	}
	public void setUsableMinNoAnimation(float usableMin) {
		setUsableMin(usableMin);
		this.displayUsableMin = usableMin;
	}

	public float getUsableMax() {
		return usableMax;
	}
	public void setUsableMax(float usableMax) {
		this.usableMax = usableMax;
	}
	public void setUsableMaxNoAnimation(float usableMax) {
		setUsableMax(usableMax);
		this.displayUsableMax = usableMax;
	}

	public void setupKeys(float keyChangeSpeed, int leftKey, int rightKey) {
		setKeyChangeSpeed(keyChangeSpeed);
		setLeftKey(leftKey);
		setRightKey(rightKey);
	}
	public float getKeyChangeSpeed() {
		return keyChangeSpeed;
	}
	public void setKeyChangeSpeed(float keyChangeSpeed) {
		this.keyChangeSpeed = keyChangeSpeed;
	}
	public int getLeftKey() {
		return leftKey;
	}
	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}
	public int getRightKey() {
		return rightKey;
	}
	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}
}
