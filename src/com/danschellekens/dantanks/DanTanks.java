package com.danschellekens.dantanks;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.DanGame;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dangame.settings.GameInformation;
import com.danschellekens.dangame.settings.StartupSettings;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.values.*;
import com.danschellekens.dantanks.exceptions.MatchCreationException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.page.*;
import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Version;
import com.danschellekens.slick2d.graphics.Artist;

public class DanTanks extends DanGame {
	public static final Version GAME_VERSION = new Version(1,0, 1);
	public static final DanTanks GAME = new DanTanks();
	
	Page page;
	Page newPage;
	PageTransition transition;
	float fadeOpacity;
	
	public DanTanks() {
		super(new GameInformation("DanTanks", GAME_VERSION));
	}
	
	@Override
	public StartupSettings getStartupSettings() {
		StartupSettings settings = new StartupSettings();
		settings.setIconDirectory("icon");
		settings.setWidth(1200);
		settings.setHeight(600);
		return settings;
	}

	@Override
	protected void initGame() throws SlickException {
		setPage(new MainMenuPage());
		// createDummyMatch();
	}
	
	void createDummyMatch() {
		MatchOptions options = new MatchOptions();
		options.setInventory(StartingInventory.DEVELOPER);
		
		ArrayList<PlayerSettings> players = new ArrayList<PlayerSettings>();
		players.add(new PlayerSettings("Player 1", PlayerColor.RED, PlayerAILevel.HUMAN));
		players.add(new PlayerSettings("Player 2", PlayerColor.YELLOW, PlayerAILevel.HUMAN));
		players.add(new PlayerSettings("Player 3", PlayerColor.CYAN, PlayerAILevel.HUMAN));
		
		try {
			setPage(new GamePage(new Match(players, options)));
		} 
		catch (MatchCreationException e) { throw new OopsieException(e); }
	}

	@Override
	protected void updateGame() throws SlickException {
		if (this.transition == PageTransition.OLD_PAGE_GOING_OUT) {
			if (this.fadeOpacity == 1) {
				transitionNewPage(this.newPage);
			}
			
			this.fadeOpacity = Increment.Linear(this.fadeOpacity, 1, 0.1f);
		}
		else if (this.transition == PageTransition.NEW_PAGE_COMING_IN) {
			if (this.fadeOpacity == 0) {
				transitionDone();
			}
			
			this.fadeOpacity = Increment.Linear(this.fadeOpacity, 0, 0.05f);
		}
		
		UIElement.SHOW_CONTROL_BOUNDARIES = getInput().isKeyDown(Input.KEY_LALT);
		
		if (this.transition != PageTransition.OLD_PAGE_GOING_OUT) {
			page.update(this.getInput());
		}
	}

	@Override
	protected void renderGame(Artist artist) throws SlickException {
		page.render(artist);
		
		artist.shape().draw(
			new Rectangle(0, 0, DanTanks.GAME.getWidth(), DanTanks.GAME.getHeight()),
			new Color(0, 0, 0, this.fadeOpacity)
		);
	}

	public void setPage(Page page) {
		if (this.page == null) {
			transitionNewPage(page);
		}
		else {
			transitionOldPage(page);
		}
	}
	void transitionNewPage(Page newPage) {
		this.page = newPage;
		this.page.init();
		this.fadeOpacity = 1;
		this.transition = PageTransition.NEW_PAGE_COMING_IN;
	}
	void transitionOldPage(Page newPage) {
		this.newPage = newPage;
		this.fadeOpacity = 0;
		this.transition = PageTransition.OLD_PAGE_GOING_OUT;
	}
	void transitionDone() {
		this.fadeOpacity = 0;
		this.transition = PageTransition.NOTHING_HAPPENING;
	}
}
