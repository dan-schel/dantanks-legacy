package com.danschellekens.dantanks.ui.tankControls;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.exceptions.UtilityException;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.LegacyUtilitySelectorButton;
import com.danschellekens.dantanks.ui.control.SmallIconButton;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class UtilitySelectorUI extends UIElement {
	public static final int ICONS_AMOUNT = 6;
	
	public static String MEMORY_LAST_USED_UTILITY = "lastUsedUtility";
	
	final Level level;
	Player player;
	boolean refresh;
	
	Utility selectedUtility;
	ArrayList<LegacyUtilitySelectorButton> buttons;
	Panel panel;
	int page;
	
	Label nameLabel;
	Label descLabel;
	Label quantityLabel;
	
	SmallIconButton leftPageButton;
	SmallIconButton rightPageButton;
	
	boolean selectionChanged;
	
	public UtilitySelectorUI(UIElement parent, float x, float y, Level level) {
		super(parent, new Rectangle(x, y, 30*ICONS_AMOUNT+80, TankControlUI.HEIGHT));
		
		this.level = level;
		
		panel = new Panel(this, new Rectangle(40, 10, 30*ICONS_AMOUNT, 30));
		buttons = new ArrayList<LegacyUtilitySelectorButton>();
		page = 0;
		
		createLabels();
		createArrowButtons();
	}
	void createLabels() {
		nameLabel = new Label(this, new Rectangle(10, 50, 30*ICONS_AMOUNT+20, 30), FontLibrary.getBodyFont());
		nameLabel.setHAlign(HAlign.LEFT);
		
		descLabel = new Label(this, new Rectangle(10, 80, 30*ICONS_AMOUNT+60, 20), FontLibrary.getSmallFont());
		descLabel.setHAlign(HAlign.LEFT);
		
		quantityLabel = new Label(this, new Rectangle(30*ICONS_AMOUNT+30, 50, 40, 30), FontLibrary.getBodyFont());
		quantityLabel.setHAlign(HAlign.RIGHT);
	}
	void createArrowButtons() {
		try {
			leftPageButton = new SmallIconButton(this, 10, 15, new Image("ui/smallIcon/left.png"));
			leftPageButton.addKey(Input.KEY_PRIOR);
			leftPageButton.addKey(Input.KEY_GRAVE);
			
			rightPageButton = new SmallIconButton(this, 30*ICONS_AMOUNT+50, 15, new Image("ui/smallIcon/right.png"));
			rightPageButton.addKey(Input.KEY_NEXT);
			rightPageButton.addKey(Input.KEY_7);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	@Override
	public void update(DanInput input) {
		selectionChanged = false;
		
		if (refresh) {
			buttons = new ArrayList<LegacyUtilitySelectorButton>();
			panel.clearChildren();
			if (player != null) { populatePanel(); }
		}
		
		updateControls(input);
		
		for (LegacyUtilitySelectorButton b : buttons) {
			if (b.justActivated()) {
				selectUtility(b.getUtility());
				
				if (player != null) {
					player.rememberForMatch(MEMORY_LAST_USED_UTILITY, selectedUtility.getID());
				}
			}
		}
		
		super.update(input);
	}
	void updateControls(DanInput input) {
		if (selectedUtility != null && player != null) {
			nameLabel.setText(selectedUtility.getShopInfo().getDisplayName().toUpperCase());
			descLabel.setText(selectedUtility.getShopInfo().getDescription());
			quantityLabel.setText(player.getTank().getInventory().getUtilityQuantityString(selectedUtility.getID()));
			
			if (player.getTank().getInventory().getUtilityQuantity(selectedUtility.getID()) == 0) {
				selectUtility(determinePreviouslySelectedUtility(player));
			}
		}
		else {

			nameLabel.setText("<null>");
			descLabel.setText("<null>");
			quantityLabel.setText("0");
		}
		
		panel.update(input);
		nameLabel.update(input);
		descLabel.update(input);
		quantityLabel.update(input);
		
		leftPageButton.update(input);
		rightPageButton.update(input);
		
		if (leftPageButton.justActivated()) {
			backPage();
		}
		if (rightPageButton.justActivated()) {
			nextPage();
		}
	}
	void populatePanel() {
		ArrayList<String> utilityIDs = player.getTank().getInventory().getOwnedUtilityIDs();
		for (int i = ICONS_AMOUNT * page; i < utilityIDs.size() && i < ICONS_AMOUNT * (page + 1); i++) {
			int x = i % ICONS_AMOUNT;
			createButton(utilityIDs.get(i), x);
		}
		
		refresh = false;
	}
	void createButton(String utilityID, int x) {
		try {
			Utility utility = UtilityLibrary.CreateUtility(utilityID);
			
			LegacyUtilitySelectorButton button = new LegacyUtilitySelectorButton(panel, x * 30, 0, utility);
			
			switch (x) {
			case 0:
				button.addKey(Input.KEY_1);
				break;
			case 1:
				button.addKey(Input.KEY_2);
				break;
			case 2:
				button.addKey(Input.KEY_3);
				break;
			case 3:
				button.addKey(Input.KEY_4);
				break;
			case 4:
				button.addKey(Input.KEY_5);
				break;
			case 5:
				button.addKey(Input.KEY_6);
				break;
			}
			
			buttons.add(button);
			panel.addChild(button);
		} 
		catch (UtilityException e) {
			throw new OopsieException(e);
		}
	}
	void backPage() {
		if (player == null) { page = 0; return; }
		
		refresh = true;
		page--;
		
		ArrayList<String> utilityIDs = player.getTank().getInventory().getOwnedUtilityIDs();
		if (page < 0) {
			page = (utilityIDs.size() - 1) / ICONS_AMOUNT;
		}
	}
	void nextPage() {
		if (player == null) { page = 0; return; }
		
		refresh = true;
		page++;
		
		ArrayList<String> utilityIDs = player.getTank().getInventory().getOwnedUtilityIDs();
		if (page > (utilityIDs.size() - 1) / ICONS_AMOUNT) {
			page = 0;
		}
	}
	void selectUtility(Utility utility) {
		selectedUtility = utility;
		
		if (player == null) {
			page = 0;
			return;
		}
		
		selectionChanged = true;
		refresh = true;
		page = player.getTank().getInventory().getOwnedUtilityIDs().indexOf(this.selectedUtility.getID()) / ICONS_AMOUNT;
	}
	
	@Override
	protected void renderInPosition(Artist artist) {
		for (LegacyUtilitySelectorButton u : buttons) {
			if (u.getUtility().getID() == this.selectedUtility.getID() && player != null) {
				artist.setTransform(new Transform());
				artist.shape().draw(
						new Rectangle(u.getScreenX(), u.getScreenY(), u.getWidth(), u.getHeight()),
						new Color(new Color(0, 0, 0, 0.1f))
				);
				artist.shape().draw(
						new Rectangle(u.getScreenX(), u.getScreenY() + u.getHeight(), u.getWidth(), 2),
						new Color(player.getColor().getColor())
				);
				artist.pop();
			}
		}
		
		panel.render(artist);
		nameLabel.render(artist);
		descLabel.render(artist);
		quantityLabel.render(artist);
		
		leftPageButton.render(artist);
		rightPageButton.render(artist);
		
		super.renderInPosition(artist);
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		if (this.player != player) { refresh = true; }
		if (player != null && this.player != player) {
			selectUtility(determinePreviouslySelectedUtility(player));
		}
		this.player = player;
	}
	public Utility determinePreviouslySelectedUtility(Player player) {
		ArrayList<String> utilityIDs = player.getTank().getInventory().getOwnedUtilityIDs();
		if (utilityIDs.size() == 0) { return null; }
		
		String backup = utilityIDs.get(0);
		String memory = player.recallForMatch(MEMORY_LAST_USED_UTILITY, backup);
		if (!utilityIDs.contains(memory)) { memory = backup; }
		
		try {
			return UtilityLibrary.CreateUtility(memory);
		} 
		catch (UtilityException e) {
			throw new OopsieException(e);
		}
	}

	public String getSelectedUtility() {
		if (player == null) { return null; }
		if (buttons.size() == 0) { return null; }
		if (selectedUtility == null) { return null; }
		return selectedUtility.getID();
	}
	public boolean justChangedSelection() {
		return selectionChanged;
	}
}
