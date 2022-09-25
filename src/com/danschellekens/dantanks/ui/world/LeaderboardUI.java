package com.danschellekens.dantanks.ui.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.Match;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.game.Umpire;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.operations.Increment;
import com.danschellekens.slick2d.input.DanInput;

public class LeaderboardUI extends Panel {
	final Umpire umpire;
	final Match match;
	
	Label titleLabel;
	boolean open;
	
	public LeaderboardUI(UIElement parent, Umpire umpire, Match match) {
		super(parent, new Rectangle(0, DanTanks.GAME.getHeight(), 250, 270));
		
		this.umpire = umpire;
		this.match = match;
		
		titleLabel = new Label(this, new Rectangle(10, 10, 230, 30), FontLibrary.getLargeFont());
		titleLabel.setColor(new Color(1f, 1f, 1f, 0.6f));
		titleLabel.setText("Round " + (umpire.getRoundIndex() + 1) + " of " + match.getRoundsToPlay());
		this.addChild(titleLabel);
		
		Label descLabel = new Label(this, new Rectangle(10, 50, 230, 30), FontLibrary.getBodyBoldFont());
		
		if (umpire.getRoundIndex() == 0) {
			descLabel.setText("Leaderboard - Start");
		}
		else {
			descLabel.setText("Leaderboard - Round " + (umpire.getRoundIndex()) + "");
		}
		
		descLabel.setColor(new Color(1f, 1f, 1f, 0.6f));
		this.addChild(descLabel);
		
		setBackgroundColor(new Color(0f, 0f, 0f, 0.4f));
		
		createPodium();
	}
	void createPodium() {
		int y = 80;
		
		for (Player player : match.getPlayersByScore()) {
			Panel playerPanel = new Panel(this, new Rectangle(10, y, 180, 30));
			this.addChild(playerPanel);

			Label usernameLabel = new Label(playerPanel, new Rectangle(0, 0, 150, 30), FontLibrary.getBodyFont());
			usernameLabel.setText(player.getName());
			usernameLabel.setColor(new Color(1f, 1f, 1f, 0.6f));
			playerPanel.addChild(usernameLabel);
			
			Label scoreLabel = new Label(playerPanel, new Rectangle(150, 0, 80, 30), FontLibrary.getBodyFont());
			scoreLabel.setText(player.getScore() + " pts");
			scoreLabel.setHAlign(HAlign.RIGHT);
			scoreLabel.setColor(new Color(1f, 1f, 1f, 0.3f));
			playerPanel.addChild(scoreLabel);
			
			y += 30;
		}
	}
	@Override
	public void update(DanInput input) {
		int height = 90 + match.getPlayersAmount() * 30;
		
		if (input.getMouseY() > DanTanks.GAME.getHeight() - 50 && input.getMouseX() < this.getWidth() && input.isMouseValid()) {
			open = true;
		}
		if (!(input.getMouseY() > DanTanks.GAME.getHeight() - height && input.getMouseX() < this.getWidth() && input.isMouseValid())) {
			open = false;
		}
		
		if (open) {
			this.setY(Increment.Exponential(this.getRelativeY(), DanTanks.GAME.getHeight() - height, 0.2f));
		}
		else {
			this.setY(Increment.Exponential(this.getRelativeY(), DanTanks.GAME.getHeight() - 50, 0.2f));
		}
		
	}
}
