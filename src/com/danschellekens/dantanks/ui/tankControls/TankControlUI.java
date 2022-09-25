package com.danschellekens.dantanks.ui.tankControls;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.ImageViewer;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.exceptions.MissingPlayerException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.ui.control.ProgressBar;
import com.danschellekens.dantanks.ui.control.SmallIconButton;
import com.danschellekens.operations.Increment;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class TankControlUI extends UIElement {
	public static final int HEIGHT = 110;
	
	final Level level;
	Player player;
	boolean visible;
	float percentTurnOver;
	
	PlayerInfoUI playerInfoUI;
	UtilitySelectorUI selectorUI;
	AimAndConsumeUI aimConsumeUI;
	
	SmallIconButton pauseButton;
	ProgressBar turnOverBar;
	ImageViewer controlsGuide;
	
	public TankControlUI(Level level) {
		super(null, new Rectangle(0, -HEIGHT, DanTanks.GAME.getWidth(), HEIGHT));
		
		this.level = level;
		this.visible = false;
		
		try {
			pauseButton = new SmallIconButton(null, DanTanks.GAME.getWidth() - 40, 20, new Image("ui/smallIcon/pause.png"));
			pauseButton.addKey(Input.KEY_P);
			
			playerInfoUI = new PlayerInfoUI(this, 0, 0, level);
			selectorUI = new UtilitySelectorUI(this, 276, 0, level);
			aimConsumeUI = new AimAndConsumeUI(this, 536, 0, level);
			
			turnOverBar = new ProgressBar(this, new Rectangle(0, 0, this.getWidth(), 2));
			turnOverBar.setBackColor(new Color(0, 0, 0, 0));
			turnOverBar.setForeColor(new Color(240, 0, 0, 255));
			
			controlsGuide = new ImageViewer(this, new Rectangle(818, 0, 140, 110), new Image("ui/controls.png"));
			controlsGuide.setFilter(new Color(0, 0, 0, 0.4f));
		} 
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	@Override
	public void update(DanInput input) {
		if (visible) { this.setY(Increment.Exponential(this.getRelativeY(), 0, 0.25f)); }
		else { this.setY(Increment.Exponential(this.getRelativeY(), -HEIGHT, 0.25f)); }
		
		pauseButton.update(input);
		
		playerInfoUI.setPlayer(player);
		playerInfoUI.update(input);
		
		selectorUI.setPlayer(player);
		selectorUI.update(input);
		
		aimConsumeUI.setPlayer(player);
		aimConsumeUI.setUtilityID(selectorUI.getSelectedUtility());
		aimConsumeUI.update(input);
		
		turnOverBar.update(input);
		turnOverBar.setValues(this.percentTurnOver, 0, 1);
		
		controlsGuide.update(input);
		
		updateDrivingTank(input);
		
		super.update(input);
	}
	void updateDrivingTank(DanInput input) {
		if (player == null) { return; }
		if (input == null) { return; }
		
		try {
			TankObj tankObj = level.getTankFromOwner(player.getID());
			
			if (input.isKeyDown(Input.KEY_Z)) { tankObj.driveLeft(); }
			if (input.isKeyDown(Input.KEY_C)) { tankObj.driveRight(); }
		}
		catch (MissingPlayerException e) {
			throw new OopsieException(e);
		}
	}
	
	@Override
	public void render(Artist artist) {
		super.render(artist);
		
		pauseButton.render(artist);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new Rectangle(0, 0, getWidth(), HEIGHT), new Color(1, 1, 1, 0.8f));
		artist.line().draw(275.5f, 5, 275.5f, HEIGHT - 5, new Color(0, 0, 0, 0.2f), 1);
		artist.line().draw(535.5f, 5, 535.5f, HEIGHT - 5, new Color(0, 0, 0, 0.2f), 1);
		artist.line().draw(815.5f, 5, 815.5f, HEIGHT - 5, new Color(0, 0, 0, 0.2f), 1);
		
		playerInfoUI.render(artist);
		selectorUI.render(artist);
		aimConsumeUI.render(artist);
		turnOverBar.render(artist);
		
		controlsGuide.render(artist);
		
		super.renderInPosition(artist);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getPercentTurnOver() {
		return percentTurnOver;
	}

	public void setPercentTurnOver(float percentTurnOver) {
		this.percentTurnOver = percentTurnOver;
	}
	
	public boolean justActivatedPauseButton() {
		return this.pauseButton.justActivated();
	}
	public boolean justActivatedConsumeButton() {
		return this.aimConsumeUI.justActivatedConsumeButton() && this.player != null;
	}
	public String getSelectedUtility() {
		if (this.player == null) { return null; }
		return this.selectorUI.getSelectedUtility();
	}
	public AimData getAim() {
		if (this.player == null) { return null; }
		return this.aimConsumeUI.getAim();
	}
}
