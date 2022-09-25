package com.danschellekens.dantanks.page;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.world.SunnySky;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;
import com.danschellekens.operations.*;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class MainMenuPage extends Page {
	public static final int PANEL_DELAY = 40;
	
	SunnySky sky;
	Image level;
	
	Vector2 levelParallax;
	Vector2 panelParallax;
	int panelDelayTick;
	
	Panel panel;
	LongRectangleTextButton playButton;
	
	public MainMenuPage() {
		
	}

	@Override
	public void init() {
		sky = new SunnySky();
		
		try {
			level = new Image("level/promoLevel.png");
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
		
		panel = new Panel(null, new Rectangle(0, 0, DanTanks.GAME.getWidth(), DanTanks.GAME.getHeight()));
		
		createButton();
		
		levelParallax = new Vector2(0, 600);
		panelParallax = new Vector2(0, 600);
		panelDelayTick = PANEL_DELAY;
	}
	void createButton() {
		playButton = new LongRectangleTextButton(
			panel, 
			(DanTanks.GAME.getWidth() - 200) / 2,
			380,
			200,
			"PLAY"
		);
		playButton.addKey(Input.KEY_SPACE);
		playButton.setColor(new Color(100, 180, 0));
		panel.addChild(playButton);
		
		createLabels();
	}
	void createLabels() {
		Label versionLabel = new Label(
				panel, 
				new Rectangle(10, panel.getHeight() - 26, panel.getWidth() - 20, 20), 
				FontLibrary.getSmallFont()
		);
		versionLabel.setHAlign(HAlign.LEFT);
		versionLabel.setText("DanTanks " + DanTanks.GAME_VERSION.toString());
		versionLabel.setColor(new Color(255, 255, 255));
		panel.addChild(versionLabel);
		
		Label creditsLabel = new Label(
				panel, 
				new Rectangle(10, panel.getHeight() - 26, panel.getWidth() - 20, 20), 
				FontLibrary.getSmallFont()
		);
		creditsLabel.setHAlign(HAlign.RIGHT);
		creditsLabel.setText("By Dan Schellekens");
		creditsLabel.setColor(new Color(255, 255, 255));
		panel.addChild(creditsLabel);
	}
	
	@Override
	public void update(DanInput input) {
		sky.update();
		
		float desiredX = Range.MapConstrain(input.getMouseX(), 0, DanTanks.GAME.getWidth(), -5, 5);
		float desiredY = Range.MapConstrain(input.getMouseY(), 0, DanTanks.GAME.getHeight(), -5, 5);
		
		if (!input.isMouseValid()) {
			desiredX = 0;
			desiredY = 0;
		}
		
		levelParallax.setX(Increment.Exponential(levelParallax.getX(), desiredX, 0.1f));
		levelParallax.setY(Increment.Exponential(levelParallax.getY(), desiredY, 0.1f));
		
		if (panelDelayTick <= 0) {
			panelParallax.setX(Increment.Exponential(panelParallax.getX(), 0, 0.1f));
			panelParallax.setY(Increment.Exponential(panelParallax.getY(), 0, 0.1f));
		}
		else {
			panelDelayTick--;
		}
		
		panel.setX(panelParallax.getX());
		panel.setY(panelParallax.getY());
		panel.update(input);
		
		if (playButton.justActivated()) {
			DanTanks.GAME.setPage(new GameOptionsPage());
		}
	}

	@Override
	public void render(Artist artist) {
		sky.render(artist);
		artist.image().draw(level, -10 + levelParallax.getX(), -10 + levelParallax.getY());
		
		panel.render(artist);
	}

}
