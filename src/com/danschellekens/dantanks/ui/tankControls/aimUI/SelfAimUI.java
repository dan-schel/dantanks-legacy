package com.danschellekens.dantanks.ui.tankControls.aimUI;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.SelfAimData;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class SelfAimUI extends AimUI {
	Label label;
	
	public SelfAimUI(UIElement parent, float x, float y, Level level) {
		super(parent, x, y, level);
		
		label = new Label(this, new Rectangle(0, 0, getWidth(), getHeight()), FontLibrary.getBodyFont());
		label.setText("This utility is applied to you.".toUpperCase());
		label.setHAlign(HAlign.LEFT);
	}
	
	@Override
	public void update(DanInput input) {
		label.update(input);
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		label.render(artist);
		
		super.renderInPosition(artist);
	}

	@Override
	public AimData getAim() {
		return new SelfAimData();
	}

}
