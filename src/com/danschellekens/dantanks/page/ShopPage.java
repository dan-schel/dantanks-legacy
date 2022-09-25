package com.danschellekens.dantanks.page;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.game.ShopUmpire;
import com.danschellekens.dantanks.level.world.SunnySky;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.PausedUI;
import com.danschellekens.dantanks.ui.control.*;
import com.danschellekens.dantanks.ui.shop.*;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class ShopPage extends Page {
	public static final int WIDTH = 1180;
	public static final int HEIGHT = 580;
	public static final int TOP_BAR_HEIGHT = 70;
	public static final int SIDE_PANEL_WIDTH = 280;
	public static final int BOTTOM_BAR_HEIGHT = 80;
	public static final int BUY_UI_WIDTH = 355 + 80;
	
	public static final int MCP_LENGTH = 60;
	
	final Match match;
	final ShopUmpire umpire;
	final Player player;
	
	SunnySky sky;

	Panel mainPanel;
	Panel titleBar;
	Panel bottomBar;
	Panel utilitiesView;
	Panel categoryBar;
	
	Label playerMoneyLabel;
	ProgressBar turnOverBar;
	ArrayList<UtilityCategoryButton> categoryButtons;
	
	LongRectangleTextButton nextButton;
	
	SmallIconButton pauseButton;
	boolean paused;
	PausedUI pausedUI;
	
	int mcpTick;
	
	public ShopPage(Match match, ShopUmpire umpire, Player player) {
		this.match = match;
		this.umpire = umpire;
		this.player = player;
	}
	
	@Override
	public void init() {
		paused = false;
		
		sky = new SunnySky();
		
		createUI();
		populatePage(ShopCategory.UPGRADES, 0);
		
		pausedUI = new PausedUI(null);
		
		try {
			pauseButton = new SmallIconButton(mainPanel, WIDTH - 40, 20, new Image("ui/smallIcon/pause.png"));
			pauseButton.addKey(Input.KEY_P);
			mainPanel.addChild(pauseButton);
		} 
		catch (SlickException e) {
			throw new OopsieException(e);
		}
		
		this.mcpTick = MCP_LENGTH;
	}
	void createUI() {
		mainPanel = new Panel(null, new Rectangle(
			(DanTanks.GAME.getWidth() - WIDTH) / 2,
			(DanTanks.GAME.getHeight() - HEIGHT) / 2,
			WIDTH,
			HEIGHT)
		);
		mainPanel.setBackgroundColor(new Color(1f, 1f, 1f, 0.8f));
		
		createTitleBar();
		createCategoryBar();
		createUtilitiesView();
		createBottomBar();
	}
	void createTitleBar() {
		titleBar = new Panel(mainPanel, new Rectangle(SIDE_PANEL_WIDTH, 0, WIDTH - SIDE_PANEL_WIDTH, TOP_BAR_HEIGHT));
		titleBar.setBackgroundColor(new Color(0f, 0f, 0f, 0.05f));
		mainPanel.addChild(titleBar);
		
		TankIcon playerIcon = new TankIcon(titleBar, 10, 10);
		playerIcon.setPlayer(player);
		titleBar.addChild(playerIcon);
		
		Label playerNameLabel = new Label(titleBar, new Rectangle(70, 10, 200, 25), FontLibrary.getLargeFont());
		playerNameLabel.setHAlign(HAlign.LEFT);
		playerNameLabel.setText(player.getName());
		titleBar.addChild(playerNameLabel);
		
		playerMoneyLabel = new Label(titleBar, new Rectangle(70, 35, 200, 25), FontLibrary.getBodyFont());
		playerMoneyLabel.setHAlign(HAlign.LEFT);
		titleBar.addChild(playerMoneyLabel);
		
		turnOverBar = new ProgressBar(titleBar, new Rectangle(0, 0, titleBar.getWidth(), 2));
		turnOverBar.setBackColor(new Color(0, 0, 0, 0));
		turnOverBar.setForeColor(new Color(240, 0, 0, 255));
		titleBar.addChild(turnOverBar);
	}
	void createCategoryBar() {
		categoryBar = new Panel(mainPanel, new Rectangle(0, 0, SIDE_PANEL_WIDTH, HEIGHT - BOTTOM_BAR_HEIGHT));
		mainPanel.addChild(categoryBar);
		
		categoryButtons = new ArrayList<UtilityCategoryButton>();
		
		Label title = new Label(categoryBar, new Rectangle(20, 20, SIDE_PANEL_WIDTH - 40, 30), FontLibrary.getTitleFont());
		title.setText("Shop - Round " + Integer.toString(umpire.getRoundNo() + 1));
		
		categoryBar.addChild(title);
		
		int y = 0;
		for (ShopCategory g : ShopCategory.values()) {
			if (g == ShopCategory.HIDDEN_IN_SHOP) { continue; }
			if (g == ShopCategory.COMPLETELY_HIDDEN) { continue; }
			
			UtilityCategoryButton button = new UtilityCategoryButton(categoryBar, 0, 70 + y * 40, SIDE_PANEL_WIDTH, g);
			button.setColor(player.getColor().getColor());
			categoryBar.addChild(button);
			categoryButtons.add(button);
			
			y++;
		}
	}
	void createUtilitiesView() {
		utilitiesView = new Panel(mainPanel, new Rectangle(
			SIDE_PANEL_WIDTH, 
			TOP_BAR_HEIGHT, 
			WIDTH - SIDE_PANEL_WIDTH, 
			HEIGHT - TOP_BAR_HEIGHT - BOTTOM_BAR_HEIGHT
		));
		utilitiesView.setBackgroundColor(new Color(0f, 0f, 0f, 0.05f));
		mainPanel.addChild(utilitiesView);
	}
	void createBottomBar() {
		bottomBar = new Panel(mainPanel, new Rectangle(
			0, 
			HEIGHT - BOTTOM_BAR_HEIGHT, 
			WIDTH, 
			BOTTOM_BAR_HEIGHT
		));
		mainPanel.addChild(bottomBar);
		
		nextButton = new LongRectangleTextButton(bottomBar, WIDTH - 150 - 20, 20, 150, "NEXT");
		nextButton.setColor(new Color(100, 180, 0));
		bottomBar.addChild(nextButton);
	}
	
	@Override
	public void update(DanInput input) {
		sky.update();
		
		if (!paused) {
			updatePlaying(input);
		}
		else {
			updatePaused(input);
		}
		
		if (mcpTick > 0) {
			mcpTick--;
		}
	}
	void updatePlaying(DanInput input) {
		mainPanel.update(input);
		
		playerMoneyLabel.setText("$" + player.getMoney());
		
		umpire.update();
		
		turnOverBar.setValues(umpire.percentTurnOver(), 0, 1);
		
		if (nextButton.justActivated() && mcpTick < 1) {
			umpire.playerDoneShopping();
		}
		
		for (UtilityCategoryButton button : categoryButtons) {
			if (button.justActivated()) {
				populatePage(button.getCategory(), 0);
			}
		}
		
		if (pauseButton.justActivated()) {
			paused = true;
		}
		
		pausedUI.update(new DanInput());
		pausedUI.setPaused(false);
	}
	void updatePaused(DanInput input) {
		pauseButton.update(new DanInput());
		pausedUI.update(input);
		pausedUI.setPaused(true);
		
		if (pausedUI.justActivatedPlay()) {
			paused = false;
		}
		if (pausedUI.justActivatedHome()) {
			DanTanks.GAME.setPage(new MainMenuPage());
		}
	}
	
	void populatePage(ShopCategory category, int page) {
		for (UtilityCategoryButton button : categoryButtons) {
			button.setChosen(button.getCategory() == category);
		}
		
		utilitiesView.clearChildren();
		
		if (category == ShopCategory.UPGRADES) {
			populateUpgradePage();
			return;
		}
		
		ArrayList<Utility> utilities = UtilityLibrary.GetUtilitiesForCategory(category);
		
		int startOfPage = page * 10;
		int endOfPage = startOfPage + 9;
		for (int i = startOfPage; i <= endOfPage; i++) {
			int x = 10 + (i % 2) * (BuyUtilityUI.WIDTH + 10);
			int y = 10 + ((i - startOfPage) / 2) * (BuyUtilityUI.HEIGHT + 10);
			
			if (utilities.size() == i) { return; }
			
			BuyUtilityUI buyUI1 = new BuyUtilityUI(utilitiesView, x, y, player, utilities.get(i));
			utilitiesView.addChild(buyUI1);
		}
	}
	void populateUpgradePage() {
		TankStatLevelManager[] stats = player.getTank().getStats();
		
		for (int i = 0; i < stats.length; i++) {
			int x = 10 + (i % 2) * (BuyUpgradeUI.WIDTH + 10);
			int y = 10 + (i / 2) * (BuyUpgradeUI.HEIGHT + 10);
			
			BuyUpgradeUI buyUI1 = new BuyUpgradeUI(utilitiesView, x, y, player, stats[i]);
			utilitiesView.addChild(buyUI1);
		}
	}

	@Override
	public void render(Artist artist) {
		sky.render(artist);
		
		mainPanel.render(artist);
		pausedUI.render(artist);
	}
}
