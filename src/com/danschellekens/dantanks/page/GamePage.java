package com.danschellekens.dantanks.page;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.ImageViewer;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.Match;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.game.Umpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.ui.PausedUI;
import com.danschellekens.dantanks.ui.tankControls.TankControlUI;
import com.danschellekens.dantanks.ui.world.LeaderboardUI;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.collisions.RectUtils;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class GamePage extends Page {
	final Match match;
	final Umpire umpire;
	
	boolean paused;
	
	Level level;
	ImageViewer watermark;
	TankControlUI tankControls;
	LeaderboardUI leaderboard;
	
	PausedUI pausedUI;
	
	public GamePage(Match match) {
		this.match = match;
		this.umpire = new Umpire(match);
	}
	public GamePage(Match match, Umpire umpire) {
		this.match = match;
		this.umpire = umpire;	
	}
	void createWatermark() {
		try {
			Vector2 position = DanTanks.GAME.getScreenBottomRight().subtract(new Vector2(96, 30));
			Rectangle logoArea = RectUtils.Translate(new Rectangle(-10, -8, 96, 30), position);
			watermark = new ImageViewer(null, logoArea, new Image("ui/logoSmall.png"));
			watermark.setFilter(new Color(1, 1, 1, 0.4f));
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	@Override
	public void init() {
		paused = false;
		
		try {
			level = umpire.startRound();
		}
		catch (UmpireException e) {
			throw new OopsieException(e);
		}	
		
		pausedUI = new PausedUI(null);
		tankControls = new TankControlUI(level);
		
		createWatermark();
		
		leaderboard = new LeaderboardUI(null, umpire, match);
	}
	
	@Override
	public void update(DanInput input) {
		if (!paused) {
			updatePlaying(input);
		}
		else {
			updatePaused(input);
		}
		
		level.update(input, paused);
		
		watermark.update(input);
	}
	void updatePlaying(DanInput input) {
		umpire.update();	
		level.updatePlaying();
		
		tankControls.setPlayer(umpire.tankControlsPlayer());
		tankControls.setVisible(umpire.tankControlsVisible());
		tankControls.setPercentTurnOver(umpire.tankControlsPercentTurnOver());
		tankControls.update(input);
		
		pausedUI.update(new DanInput());
		pausedUI.setPaused(false);
		
		leaderboard.update(input);
		
		if (tankControls.justActivatedPauseButton()) {
			paused = true;
		}
		if (tankControls.justActivatedConsumeButton()) {
			try {
				AimData aim = tankControls.getAim();
				String utilityID = tankControls.getSelectedUtility();
				
				if (utilityID == null) {
					throw new OopsieException(new Exception("No utility selected?"));
				}
				
				umpire.consumeUtility(utilityID, umpire.tankControlsPlayer(), aim);
			}
			catch (UtilityException | UmpireException e) {
				throw new OopsieException(e);
			} 
		}
	}
	void updatePaused(DanInput input) {
		tankControls.update(new DanInput());
		tankControls.setVisible(false);
		
		pausedUI.update(input);
		pausedUI.setPaused(true);
		
		leaderboard.update(new DanInput());
		
		if (pausedUI.justActivatedPlay()) {
			paused = false;
		}
		if (pausedUI.justActivatedHome()) {
			DanTanks.GAME.setPage(new MainMenuPage());
		}
	}

	@Override
	public void render(Artist artist) {
		level.render(artist);
		leaderboard.render(artist);
		
		tankControls.render(artist);
		
		pausedUI.render(artist);
		watermark.render(artist);
	}

}
