package com.danschellekens.dantanks.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.MatchOptions;
import com.danschellekens.dantanks.data.values.MatchLength;
import com.danschellekens.dantanks.data.values.StartingInventory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.world.SunnySky;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;
import com.danschellekens.dantanks.ui.control.SmallIconButton;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class GameOptionsPage extends Page {
	public static final int WIDTH = 740;
	public static final int HEIGHT = 480;
	
	SunnySky sky;
	
	Panel mainPanel;
	LongRectangleTextButton nextButton;
	LongRectangleTextButton prevButton;
	Panel optionsPanel;
	
	Panel gameLengthSelector;
	HashMap<MatchLength, LongRectangleTextButton> lengthButtons;
	MatchLength selectedLength;
	
	Label selectedInventoryLabel;
	Label selectedInventoryDescriptionLabel;
	SmallIconButton prevInventoryButton;
	SmallIconButton nextInventoryButton;
	ArrayList<StartingInventory> inventories;
	int selectedInventoryIndex;
	StartingInventory selectedInventory;
	
	@Override
	public void init() {
		this.inventories = new ArrayList<StartingInventory>(Arrays.asList(StartingInventory.values()));
		
		createMainPanel();
		createButtons();
		createOptionsPanel();
		
		selectLength(MatchLength.REGULAR);
		selectInventory(StartingInventory.DEFAULT);
		
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
		
		Label title = new Label(mainPanel, new Rectangle(20, 20, WIDTH - 40, 30), FontLibrary.getTitleFont());
		title.setHAlign(HAlign.LEFT);
		title.setText("Game Options");
		mainPanel.addChild(title);
	}
	void createButtons() {
		nextButton = new LongRectangleTextButton(mainPanel, WIDTH - 150 - 20, HEIGHT - 40 - 20, 150, "NEXT");
		nextButton.setColor(new Color(100, 180, 0));
		mainPanel.addChild(nextButton);
		
		prevButton = new LongRectangleTextButton(mainPanel, WIDTH - 150 - 20 - 100 - 10, HEIGHT - 40 - 20, 100, "BACK");
		prevButton.setColor(new Color(0, 150, 240));
		prevButton.addKey(Input.KEY_ESCAPE);
		mainPanel.addChild(prevButton);
	}
	void createOptionsPanel() {
		optionsPanel = new Panel(mainPanel, new Rectangle(0, 70, WIDTH, HEIGHT - 70 - 80));
		optionsPanel.setBackgroundColor(new Color(0f, 0f, 0f, 0.05f));
		mainPanel.addChild(optionsPanel);
		
		createGameLengthSelector();
		createInventorySelector();
	}
	void createGameLengthSelector() {
		Label lengthLabel = new Label(optionsPanel, new Rectangle(20, 20, WIDTH - 40, 30), FontLibrary.getBodyFont());
		lengthLabel.setHAlign(HAlign.CENTER);
		lengthLabel.setText("Game Length");
		optionsPanel.addChild(lengthLabel);
		
		MatchLength[] lengths = MatchLength.values();
		int panelWidth = lengths.length * 170 - 10;
		
		gameLengthSelector = new Panel(optionsPanel, new Rectangle((WIDTH - panelWidth) / 2, 50, panelWidth, 60));
		optionsPanel.addChild(gameLengthSelector);
		
		lengthButtons = new HashMap<MatchLength, LongRectangleTextButton>();
		for (int i = 0; i < lengths.length; i++) {
			String buttonText = lengths[i].getDisplayName() + " - " + lengths[i].getAmountOfRounds() + (lengths[i].getAmountOfRounds() == 1 ? " round" : " rounds");
			LongRectangleTextButton button = new LongRectangleTextButton(gameLengthSelector, new Rectangle(i * 170, 5, 160, 50), buttonText);
			gameLengthSelector.addChild(button);
			lengthButtons.put(lengths[i], button);
		}
	}
	void createInventorySelector() {
		Label inventoryLabel = new Label(optionsPanel, new Rectangle(20, 120, WIDTH - 40, 30), FontLibrary.getBodyFont());
		inventoryLabel.setHAlign(HAlign.CENTER);
		inventoryLabel.setText("Starting Inventory");
		optionsPanel.addChild(inventoryLabel);
		
		selectedInventoryLabel = new Label(optionsPanel, new Rectangle(65, 150, WIDTH - 130, 30), FontLibrary.getLargeFont());
		selectedInventoryLabel.setHAlign(HAlign.CENTER);
		optionsPanel.addChild(selectedInventoryLabel);

		selectedInventoryDescriptionLabel = new Label(optionsPanel, new Rectangle(65, 180, WIDTH - 130, 20), FontLibrary.getSemiSmallFont());
		selectedInventoryDescriptionLabel.setHAlign(HAlign.CENTER);
		optionsPanel.addChild(selectedInventoryDescriptionLabel);
		
		try {
			prevInventoryButton = new SmallIconButton(optionsPanel, 30, 165, new Image("ui/smallIcon/left.png"));
			optionsPanel.addChild(prevInventoryButton);
			
			nextInventoryButton = new SmallIconButton(optionsPanel, WIDTH - 30 - 20, 165, new Image("ui/smallIcon/right.png"));
			optionsPanel.addChild(nextInventoryButton);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	@Override
	public void update(DanInput input) {
		sky.update();
		mainPanel.update(input);
		
		if (prevButton.justActivated()) {
			DanTanks.GAME.setPage(new MainMenuPage());
		}
		if (nextButton.justActivated()) {
			generateOptions();
		}
		
		for (Entry<MatchLength, LongRectangleTextButton> e : lengthButtons.entrySet()) {
			if (e.getValue().justActivated()) {
				selectLength(e.getKey());
			}
		}
		
		if (prevInventoryButton.justActivated()) {
			prevAIMode();
		}
		if (nextInventoryButton.justActivated()) {
			nextAIMode();
		}
	}
	
	void selectLength(MatchLength length) {
		selectedLength = length;
		
		for (Entry<MatchLength, LongRectangleTextButton> e : lengthButtons.entrySet()) {
			if (e.getKey() == length) {
				e.getValue().setColor(new Color(0, 150, 240));
			}
			else {
				e.getValue().setColor(new Color(0, 150, 240, 127));
			}
		}
	}
	void selectInventory(StartingInventory inventory) {
		selectedInventoryIndex = inventories.indexOf(inventory);
		selectedInventory = inventory;
		selectedInventoryLabel.setText(inventory.getDisplayName());
		selectedInventoryDescriptionLabel.setText(inventory.getDescription());
	}
	void prevAIMode() {
		selectedInventoryIndex --;
		if (selectedInventoryIndex < 0) { selectedInventoryIndex = inventories.size() - 1; }
		
		selectInventory(inventories.get(selectedInventoryIndex));
	}
	void nextAIMode() {
		selectedInventoryIndex ++;
		if (selectedInventoryIndex >= inventories.size()) { selectedInventoryIndex = 0; }
		
		selectInventory(inventories.get(selectedInventoryIndex));
	}
	
	void generateOptions() {
		MatchOptions options = new MatchOptions();
		options.setLength(selectedLength);
		options.setInventory(selectedInventory);
		DanTanks.GAME.setPage(new AddPlayersPage(options));
	}
	
	@Override
	public void render(Artist artist) {
		sky.render(artist);
		mainPanel.render(artist);
	}

}
