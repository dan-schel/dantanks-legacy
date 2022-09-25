package com.danschellekens.dantanks.page;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.exceptions.MatchCreationException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.world.SunnySky;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class PodiumPage extends Page {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	final Match match;
	
	SunnySky sky;

	Panel mainPanel;
	Panel titleBar;
	Panel podiumView;
	Panel bottomBar;
	
	Label title;
	LongRectangleTextButton menuButton;
	LongRectangleTextButton rematchButton;
	
	public PodiumPage(Match match) {
		this.match = match;
	}
	
	@Override
	public void init() {
		sky = new SunnySky();
		
		createUI();
	}
	void createUI() {
		mainPanel = new Panel(null, new Rectangle(
				(DanTanks.GAME.getWidth() - WIDTH) / 2,
				(DanTanks.GAME.getHeight() - HEIGHT) / 2,
				WIDTH,
				HEIGHT)
		);
		mainPanel.setBackgroundColor(new Color(1f, 1f, 1f, 0.8f));
		
		titleBar = new Panel(mainPanel, new Rectangle(0, 0, WIDTH, 70));
		mainPanel.addChild(titleBar);
		
		bottomBar = new Panel(mainPanel, new Rectangle(0, HEIGHT - 80, WIDTH, 80));
		mainPanel.addChild(bottomBar);
		
		title = new Label(titleBar, new Rectangle(20, 20, WIDTH - 40, 30), FontLibrary.getTitleFont());
		title.setHAlign(HAlign.CENTER);
		title.setText("Final Scores");
		titleBar.addChild(title);
		
		createPodiumView();
		
		menuButton = new LongRectangleTextButton(bottomBar, (WIDTH - 310) / 2, 20, 150, "MAIN MENU");
		menuButton.setColor(new Color(0, 150, 240));
		menuButton.addKey(Input.KEY_ESCAPE);
		bottomBar.addChild(menuButton);
		
		rematchButton = new LongRectangleTextButton(bottomBar, (WIDTH + 10) / 2, 20, 150, "REMATCH");
		rematchButton.setColor(new Color(100, 180, 0));
		bottomBar.addChild(rematchButton);
	}
	void createPodiumView() {
		podiumView = new Panel(mainPanel, new Rectangle(0, 70, WIDTH, HEIGHT - 150));
		podiumView.setBackgroundColor(new Color(0, 0, 0, 0.05f));
		mainPanel.addChild(podiumView);
		
		int previousScore = -1;
		int position = 0;
		int y = 50;
		for (Player player : match.getPlayersByScore()) {
			boolean drawedPlayer = player.getScore() == previousScore;
			if (!drawedPlayer) {
				position ++;
				previousScore = player.getScore();
			}
			
			Panel playerPanel = new Panel(podiumView, new Rectangle(100, y, WIDTH - 200, 30));
			podiumView.addChild(playerPanel);
			
			Label positionLabel = new Label(playerPanel, new Rectangle(0, 0, 50, 30), FontLibrary.getBodyFont());
			positionLabel.setText(positionToString(position));
			if (!drawedPlayer) { playerPanel.addChild(positionLabel); }
			
			Label usernameLabel = new Label(playerPanel, new Rectangle(50, 0, 300, 30), FontLibrary.getLargeFont());
			usernameLabel.setText(player.getName());
			playerPanel.addChild(usernameLabel);
			
			Label scoreLabel = new Label(playerPanel, new Rectangle(350, 0, WIDTH - 550, 30), FontLibrary.getBodyFont());
			scoreLabel.setText(player.getScore() + " pts");
			scoreLabel.setHAlign(HAlign.RIGHT);
			scoreLabel.setColor(new Color(0, 0, 0, 0.6f));
			playerPanel.addChild(scoreLabel);
			
			y += 40;
			
		}
	}
	String positionToString(int position) {
		switch (position) {
			case 1: return "1st";
			case 2: return "2nd";
			case 3: return "3rd";
			default: return position + "th";
		}
	}

	@Override
	public void update(DanInput input) {
		sky.update();
		mainPanel.update(input);
		
		if (menuButton.justActivated()) {
			DanTanks.GAME.setPage(new MainMenuPage());
		}
		if (rematchButton.justActivated()) {
			ArrayList<PlayerSettings> newPlayers = new ArrayList<PlayerSettings>();
			
			for (Player originalPlayer : match.getPlayers()) {
				newPlayers.add(originalPlayer.createNew(match.getOptions()));
			}
			
			try {
				Match newMatch = new Match(newPlayers, match.getOptions());
				DanTanks.GAME.setPage(new GamePage(newMatch));
			}
			catch (MatchCreationException e) {
				throw new OopsieException(e);
			}
		}
	}

	@Override
	public void render(Artist artist) {
		sky.render(artist);
		mainPanel.render(artist);
	}
}
