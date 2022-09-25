package com.danschellekens.dantanks.page;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.Match;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.game.Umpire;
import com.danschellekens.dantanks.game.log.*;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.standingUpdate.PlayerScoreUI;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class StandingsUpdatePage extends Page {
	public static final int ANIMATION_LENGTH_PHASE_1 = 60 * 2; // Initial score bar expansion.
	public static final int ANIMATION_LENGTH_PHASE_2 = 30; // Points awarded score bar expansion.
	public static final int ANIMATION_LENGTH_PHASE_3 = 30; // Color fade in.
	public static final int ANIMATION_LENGTH_PHASE_4 = 60; // Wait.
	public static final int ANIMATION_LENGTH_PHASE_4_EXTENSION_PER_PLAYER = 40; // Wait an additional 40 frames per player. 
	
	final Match match;
	final Umpire umpire;
	final Player[] players;
	final GameLog log;
	
	Panel mainPanel;
	PlayerScoreUI[] scoreUIs; 
	
	int animation;
	
	public StandingsUpdatePage(Match match, Umpire umpire) {
		this.match = match;
		this.umpire = umpire;
		this.players = match.getPlayers();
		this.log = umpire.getLog();
	}
	
	@Override
	public void init() {
		int mainPanelWidth = PlayerScoreUI.WIDTH;
		int mainPanelHeight = PlayerScoreUI.HEIGHT * players.length + 80;
		mainPanel = new Panel(null, new Rectangle(
			(DanTanks.GAME.getWidth() - mainPanelWidth) / 2,
			(DanTanks.GAME.getHeight() - mainPanelHeight) / 2,
			mainPanelWidth,
			mainPanelHeight
		));
		
		Label titleLabel = new Label(mainPanel, new Rectangle(0, 0, mainPanelWidth, 80), FontLibrary.getTitleFont());
		titleLabel.setText("End Of Round " + (umpire.getRoundIndex() + 1));
		titleLabel.setColor(new Color(1f, 1f, 1f, 1f));
		titleLabel.setAlign(VAlign.TOP, HAlign.CENTER);
		mainPanel.addChild(titleLabel);
		
		createScoreUIs();
	}
	void createScoreUIs() {
		// Starts at 1 in case all points are 0, where division by 0 causes the graph to break.
		int maxDomain = 1;
		for (Player player : players) {
			if (player.getScore() > maxDomain) {
				maxDomain = player.getScore();
			}
		}
		
		scoreUIs = new PlayerScoreUI[players.length];
		for (int i = 0; i < players.length; i++) {
			int pointsAwarded = 0;
			
			for (LogEntry entry : log.getCurrentRoundEntries()) {
				if (entry instanceof PointsAwardedLogEntry) {
					PointsAwardedLogEntry pointsEntry = (PointsAwardedLogEntry) entry;
					if (!pointsEntry.getPlayerID().equals(players[i].getID())) { continue; }
					pointsAwarded += pointsEntry.getPointsAwarded();
				}
			}
			
			int initialScore = players[i].getScore() - pointsAwarded;
			
			scoreUIs[i] = new PlayerScoreUI(
				mainPanel, 
				0, 
				PlayerScoreUI.HEIGHT * i + 80,
				players[i], 
				initialScore, 
				pointsAwarded, 
				maxDomain
			);
			mainPanel.addChild(scoreUIs[i]);
		}
	}
	
	
	@Override
	public void update(DanInput input) {
		animation++;
		
		int animationLength = ANIMATION_LENGTH_PHASE_1 + ANIMATION_LENGTH_PHASE_2
				+ ANIMATION_LENGTH_PHASE_3 + ANIMATION_LENGTH_PHASE_4
				+ (ANIMATION_LENGTH_PHASE_4_EXTENSION_PER_PLAYER * match.getPlayersAmount());
		
		if (animation > animationLength) {
			umpire.startShoppingOrPodium();
		}
		
		mainPanel.update(input);
	}
	
	@Override
	public void render(Artist artist) {
		mainPanel.render(artist);
	}

}
