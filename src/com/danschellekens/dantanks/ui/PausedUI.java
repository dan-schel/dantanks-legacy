package com.danschellekens.dantanks.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;
import com.danschellekens.operations.Increment;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class PausedUI extends UIElement {
	float bgOpacity;
	boolean paused;
	
	Panel panel;
	Label label;
	LongRectangleTextButton homeButton;
	LongRectangleTextButton playButton;
	
	public PausedUI(UIElement parent) {
		super(parent, new Rectangle(0, 0, DanTanks.GAME.getWidth(), DanTanks.GAME.getHeight()));
		
		bgOpacity = 0;
		paused = false;
		
		panel = new Panel(this, new Rectangle(
			(DanTanks.GAME.getWidth() - 200) / 2,
			DanTanks.GAME.getHeight(),
			200, 150
		));
		
		label = new Label(panel, new Rectangle(0, 0, 200, 40), FontLibrary.getTitleFont());
		label.setText("Game Paused");
		label.setColor(new Color(255, 255, 255));
		label.setHAlign(HAlign.CENTER);
		panel.addChild(label);
		
		playButton = new LongRectangleTextButton(panel, 0, 60, 200, "CONTINUE PLAYING");
		playButton.addKey(Input.KEY_ESCAPE);
		playButton.addKey(Input.KEY_P);
		panel.addChild(playButton);
	
		homeButton = new LongRectangleTextButton(panel, 0, 110, 200, "QUIT TO TITLE");
		homeButton.setColor(new Color(240, 0, 0));
		panel.addChild(homeButton);
	}

	@Override
	public void update(DanInput input) {
		if (paused) {
			bgOpacity = Increment.Linear(bgOpacity, 0.5f, 0.05f);
			panel.setY(Increment.Exponential(panel.getRelativeY(), (DanTanks.GAME.getHeight() - panel.getHeight()) / 2, 0.25f));
		}
		else {
			bgOpacity = Increment.Linear(bgOpacity, 0, 0.05f);
			panel.setY(Increment.Exponential(panel.getRelativeY(), DanTanks.GAME.getHeight(), 0.25f));
		}
		
		panel.update(input);
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new Rectangle(0, 0, getWidth(), getHeight()), new Color(0, 0, 0, bgOpacity));
		panel.render(artist);
		
		super.renderInPosition(artist);
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public boolean justActivatedPlay() {
		return playButton.justActivated();
	}
	public boolean justActivatedHome() {
		return homeButton.justActivated();
	}
}
