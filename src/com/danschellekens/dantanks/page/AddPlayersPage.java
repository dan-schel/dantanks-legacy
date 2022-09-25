package com.danschellekens.dantanks.page;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.values.PlayerAILevel;
import com.danschellekens.dantanks.data.values.PlayerColor;
import com.danschellekens.dantanks.exceptions.MatchCreationException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.world.SunnySky;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.*;
import com.danschellekens.dantanks.ui.gameOptions.ColorSwatchButton;
import com.danschellekens.dantanks.ui.gameOptions.PlayerButton;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class AddPlayersPage extends Page {
	public static final int WIDTH = 740;
	public static final int HEIGHT = 480;
	public static final int SIDE_PANEL_WIDTH = 250;
	
	final MatchOptions options;
	
	SunnySky sky;
	
	Panel mainPanel;
	LongRectangleTextButton nextButton;
	LongRectangleTextButton prevButton;
	LongRectangleTextButton addButton;
	
	Panel playersBar;
	Panel playerEditorView;
	
	ArrayList<PlayerSettings> players;
	ArrayList<PlayerButton> playerButtons;
	
	PlayerSettings selectedPlayer;
	RectangleTextbox playerNameBox;
	ArrayList<ColorSwatchButton> colorSwatches;
	ImageViewer colorSwatchTick;
	Label selectedAIModeLabel;
	Label selectedAIModeDescriptionLabel;
	SmallIconButton prevAIModeButton;
	SmallIconButton nextAIModeButton;
	LongRectangleTextButton deleteButton;
	ArrayList<PlayerAILevel> aiModes;
	int selectedAIModeIndex;
	
	public AddPlayersPage(MatchOptions options) {
		this.options = options;
	}
	
	@Override
	public void init() {
		this.aiModes = new ArrayList<PlayerAILevel>(Arrays.asList(PlayerAILevel.values()));
		
		createMainPanel();
		createButtons();
		createPlayersBar();
		createPlayerEditorView();
		
		players = new ArrayList<PlayerSettings>();
		
		PlayerSettings player1 = new PlayerSettings("Player 1", PlayerColor.RED, PlayerAILevel.HUMAN);
		players.add(player1);

		populatePlayers(0);
		
		sky = new SunnySky();
	}
	
	void createMainPanel() {
		mainPanel = new Panel(null, new Rectangle(
				(DanTanks.GAME.getWidth() - WIDTH) / 2,
				(DanTanks.GAME.getHeight() - HEIGHT) / 2,
				WIDTH,
				HEIGHT)
		);
		mainPanel.setBackgroundColor(new Color(1f, 1f, 1f, 0.8f));
		
		Label title = new Label(mainPanel, new Rectangle(20, 20, SIDE_PANEL_WIDTH - 40, 30), FontLibrary.getTitleFont());
		title.setHAlign(HAlign.LEFT);
		title.setText("Add Players");
		mainPanel.addChild(title);
	}
	void createButtons() {
		nextButton = new LongRectangleTextButton(mainPanel, WIDTH - 150 - 20, HEIGHT - 40 - 20, 150, "START MATCH");
		nextButton.setColor(new Color(100, 180, 0));
		mainPanel.addChild(nextButton);
		
		prevButton = new LongRectangleTextButton(mainPanel, WIDTH - 150 - 20 - 100 - 10, HEIGHT - 40 - 20, 100, "BACK");
		prevButton.setColor(new Color(0, 150, 240));
		prevButton.addKey(Input.KEY_ESCAPE);
		mainPanel.addChild(prevButton);

		addButton = new LongRectangleTextButton(mainPanel, 20, HEIGHT - 40 - 20, 150, "ADD PLAYER");
		addButton.setColor(new Color(0, 150, 240));
		mainPanel.addChild(addButton);
	}
	void createPlayersBar() {
		playersBar = new Panel(mainPanel, new Rectangle(0, 70, SIDE_PANEL_WIDTH, HEIGHT - 70 - 80));
		mainPanel.addChild(playersBar);
	}
	void createPlayerEditorView() {		
		playerEditorView = new Panel(mainPanel, new Rectangle(SIDE_PANEL_WIDTH, 70, WIDTH - SIDE_PANEL_WIDTH, HEIGHT - 70 - 80));
		playerEditorView.setBackgroundColor(new Color(0f, 0f, 0f, 0.05f));
		mainPanel.addChild(playerEditorView);
		
		Label nameLabel = new Label(playerEditorView, new Rectangle(20, 20, WIDTH - SIDE_PANEL_WIDTH - 40, 30), FontLibrary.getBodyFont());
		nameLabel.setHAlign(HAlign.CENTER);
		nameLabel.setText("Name");
		playerEditorView.addChild(nameLabel);
		
		playerNameBox = new RectangleTextbox(playerEditorView, new Rectangle(20, 50, WIDTH - SIDE_PANEL_WIDTH - 40, 40), FontLibrary.getLargeFont());
		playerNameBox.setHAlign(HAlign.CENTER);
		playerNameBox.setCharLimit(20);
		playerEditorView.addChild(playerNameBox);
		
		Label colorLabel = new Label(playerEditorView, new Rectangle(20, 100, WIDTH - SIDE_PANEL_WIDTH - 40, 30), FontLibrary.getBodyFont());
		colorLabel.setHAlign(HAlign.CENTER);
		colorLabel.setText("Color");
		playerEditorView.addChild(colorLabel);
		createColorSwatchPanel();
		
		Label aiLabel = new Label(playerEditorView, new Rectangle(20, 170, WIDTH - SIDE_PANEL_WIDTH - 40, 30), FontLibrary.getBodyFont());
		aiLabel.setHAlign(HAlign.CENTER);
		aiLabel.setText("AI Mode");
		playerEditorView.addChild(aiLabel);
		
		selectedAIModeLabel = new Label(playerEditorView, new Rectangle(50, 200, WIDTH - SIDE_PANEL_WIDTH - 100, 30), FontLibrary.getLargeFont());
		selectedAIModeLabel.setHAlign(HAlign.CENTER);
		playerEditorView.addChild(selectedAIModeLabel);

		selectedAIModeDescriptionLabel = new Label(playerEditorView, new Rectangle(50, 230, WIDTH - SIDE_PANEL_WIDTH - 100, 20), FontLibrary.getSemiSmallFont());
		selectedAIModeDescriptionLabel.setHAlign(HAlign.CENTER);
		playerEditorView.addChild(selectedAIModeDescriptionLabel);
		
		try {
			prevAIModeButton = new SmallIconButton(playerEditorView, 15, 215, new Image("ui/smallIcon/left.png"));
			playerEditorView.addChild(prevAIModeButton);
			
			nextAIModeButton = new SmallIconButton(playerEditorView, WIDTH - SIDE_PANEL_WIDTH - 15 - 20, 215, new Image("ui/smallIcon/right.png"));
			playerEditorView.addChild(nextAIModeButton);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
		
		deleteButton = new LongRectangleTextButton(
				playerEditorView, 
				(WIDTH - SIDE_PANEL_WIDTH - 150) / 2, 
				275, 
				150,
				"REMOVE PLAYER"
		);
		deleteButton.setColor(new Color(240, 0, 0));
		playerEditorView.addChild(deleteButton);
		
	}
	void createColorSwatchPanel() {
		PlayerColor[] colors = PlayerColor.values();
		float colorSwatchWidth = 30;
		float colorPickerPanelWidth = (colorSwatchWidth + 2) * colors.length - 2;
		
		Panel colorSwatchPanel = new Panel(playerEditorView, new Rectangle((playerEditorView.getWidth() - colorPickerPanelWidth) / 2, 130, colorPickerPanelWidth, 30));
		playerEditorView.addChild(colorSwatchPanel);

		colorSwatches = new ArrayList<ColorSwatchButton>();
		for (int i = 0; i < colors.length; i++) {
			ColorSwatchButton button = new ColorSwatchButton(colorSwatchPanel, new Rectangle((colorSwatchWidth + 2) * i, 0, colorSwatchWidth, colorSwatchPanel.getHeight()), colors[i]);			colorSwatches.add(button);
			colorSwatchPanel.addChild(button);
		}
		
		try {
			colorSwatchTick = new ImageViewer(colorSwatchPanel, new Rectangle(0, 0, 30, 30), new Image("ui/colorSwatchTick.png"));
			colorSwatchPanel.addChild(colorSwatchTick);
		} 
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	void populatePlayers(int selectedPlayerIndex) {
		playerButtons = new ArrayList<PlayerButton>();
		playersBar.clearChildren();
		
		for (int i = 0; i < players.size(); i++) {
			PlayerButton button = new PlayerButton(playersBar, 0, i * 40, SIDE_PANEL_WIDTH, players.get(i));
			playerButtons.add(button);
			playersBar.addChild(button);	
		}
		
		addButton.setEnabled(players.size() < Match.MAX_PLAYERS);
		nextButton.setEnabled(players.size() >= Match.MIN_PLAYERS);
		deleteButton.setEnabled(players.size() != 1);
		
		selectPlayer(selectedPlayerIndex);
	}
	void selectPlayer(int selectedPlayerIndex) {
		selectedPlayer = players.get(selectedPlayerIndex);
		
		for (int i = 0; i < playerButtons.size(); i++) {
			PlayerButton button = playerButtons.get(i);
			button.setChosen(i == selectedPlayerIndex);	
		}
		
		playerNameBox.setText(selectedPlayer.getName());
		placeColorSwatchTick();
		selectedAIModeLabel.setText(selectedPlayer.getAILevel().getDisplayName());
		selectedAIModeDescriptionLabel.setText(selectedPlayer.getAILevel().getDescription());
		selectedAIModeIndex = aiModes.indexOf(selectedPlayer.getAILevel());
	}
	void placeColorSwatchTick() {
		ArrayList<PlayerColor> colors = new ArrayList<PlayerColor>(Arrays.asList(PlayerColor.values()));
		int colorIndex = colors.indexOf(selectedPlayer.getColor());
		colorSwatchTick.setX(colorIndex * 32);
	}
	
	void addPlayer() {
		PlayerSettings player = new PlayerSettings("Player " + (players.size() + 1));
		players.add(player);
		
		populatePlayers(players.indexOf(player));
	}
	void removePlayer() {
		// Don't allow the player list to be empty (would break the playerEditorView).
		if (players.size() == 1) { return; }
		
		players.remove(selectedPlayer);
		populatePlayers(0);
	}	
	void generateMatch() {
		try {
			Match match = new Match(players, options);
			DanTanks.GAME.setPage(new GamePage(match));
		}
		catch (MatchCreationException e) {
			throw new OopsieException(e);
		}
	}
	String playerIDFromName(String input) {
		return input.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
	}
	
	@Override
	public void update(DanInput input) {
		sky.update();
		mainPanel.update(input);
		
		if (prevButton.justActivated()) {
			DanTanks.GAME.setPage(new MainMenuPage());
		}
		if (addButton.justActivated()) {
			addPlayer();
		}
		if (nextButton.justActivated()) {
			generateMatch();
		}
		
		for (PlayerButton button : playerButtons) {
			if (button.justActivated()) {
				selectPlayer(players.indexOf(button.getPlayer()));
			}
		}
		for (ColorSwatchButton button : colorSwatches) {
			if (button.justActivated()) {
				selectedPlayer.setColor(button.getPlayerColor());
				placeColorSwatchTick();
			}
		}
		
		if (prevAIModeButton.justActivated()) {
			prevAIMode();
		}
		if (nextAIModeButton.justActivated()) {
			nextAIMode();
		}
		
		if (deleteButton.justActivated()) {
			removePlayer();
		}
		
		selectedPlayer.setName(playerNameBox.getText());
	}
	void prevAIMode() {
		selectedAIModeIndex --;
		if (selectedAIModeIndex < 0) { selectedAIModeIndex = aiModes.size() - 1; }
		
		selectedPlayer.setAILevel(aiModes.get(selectedAIModeIndex));
		selectedAIModeLabel.setText(selectedPlayer.getAILevel().getDisplayName());
		selectedAIModeDescriptionLabel.setText(selectedPlayer.getAILevel().getDescription());
	}
	void nextAIMode() {
		selectedAIModeIndex ++;
		if (selectedAIModeIndex >= aiModes.size()) { selectedAIModeIndex = 0; }
		
		selectedPlayer.setAILevel(aiModes.get(selectedAIModeIndex));
		selectedAIModeLabel.setText(selectedPlayer.getAILevel().getDisplayName());
		selectedAIModeDescriptionLabel.setText(selectedPlayer.getAILevel().getDescription());
	}
	
	@Override
	public void render(Artist artist) {
		sky.render(artist);
		mainPanel.render(artist);
	}

}
