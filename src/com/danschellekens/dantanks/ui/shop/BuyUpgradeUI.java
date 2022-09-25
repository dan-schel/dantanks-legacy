package com.danschellekens.dantanks.ui.shop;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.TankStatLevelManager;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.exceptions.UpgradeException;
import com.danschellekens.dantanks.page.ShopPage;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;
import com.danschellekens.dantanks.ui.control.ProgressBar;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class BuyUpgradeUI extends Button {
	public static int WIDTH = ShopPage.BUY_UI_WIDTH;
	public static int HEIGHT = 98;
	
	final Player player;
	final TankStatLevelManager stat;
	
	ImageViewer iconViewer;
	Label nameLabel;
	Label descLabel;
	Label levelLabel;
	ProgressBar levelBar;
	LongRectangleTextButton buyButton;
	
	public BuyUpgradeUI(UIElement parent, int x, int y, Player player, TankStatLevelManager stat) {
		super(parent, new Rectangle(x, y, WIDTH, HEIGHT));
		
		this.player = player;
		this.stat = stat;
		
		iconViewer = new ImageViewer(this, new Rectangle(12, 12, 30, 30), stat.getStat().getIcon());
		
		nameLabel = new Label(this, new Rectangle(30 + 12 + 12, 7, WIDTH - 168, 20), FontLibrary.getBodyFont());
		nameLabel.setHAlign(HAlign.LEFT);
		nameLabel.setText(stat.getStat().getDisplayName());
		
		descLabel = new Label(this, new Rectangle(30 + 12 + 12, 27, WIDTH - 168, 20), FontLibrary.getSmallFont());
		descLabel.setText(stat.getStat().getDescription());
		descLabel.setHAlign(HAlign.LEFT);
		
		levelLabel = new Label(this, new Rectangle(12, 57, 100, 20), FontLibrary.getBodyFont());
		levelLabel.setHAlign(HAlign.LEFT);
		
		levelBar = new ProgressBar(this, new Rectangle(0 + 12, 94 - 12, WIDTH - 24, 4));
		levelBar.setForeColor(new Color(0, 150, 240));
		
		buyButton = new LongRectangleTextButton(this, new Rectangle(WIDTH - 100 - 7, 7, 100, 40), "");
		
		setValues();
	}
	
	@Override
	public void update(DanInput input) {
		iconViewer.update(input);
		nameLabel.update(input);
		descLabel.update(input);
		buyButton.update(input);
		levelLabel.update(input);
		levelBar.update(input);
		
		setValues();
		
		if (buyButton.justActivated() && stat.canUpgrade(player)) {
			try {
				stat.upgrade(player);
			} 
			catch (UpgradeException e) {
				throw new OopsieException(e);
			}
		}
		
		super.update(input);
	}
	void setValues() {
		buyButton.setEnabled(stat.canUpgrade(player));
		
		String buttonText = "$" + stat.priceOfNextUpgrade();
		if (stat.isMaxedOut()) {
			buttonText = "MAXED OUT";
		}
		buyButton.setText(buttonText);
		
		String levelText = "Level " + stat.getLevel() + " of " + stat.getStat().getMaxLevel();
		levelLabel.setText(levelText);
		
		levelBar.setValues(stat.getLevel(), 1, stat.getStat().getMaxLevel());
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new RoundedRectangle(0, 0, this.getWidth(), this.getHeight(), 5), new Color(0, 0, 0, 0.05f));
		
		iconViewer.render(artist);
		nameLabel.render(artist);
		descLabel.render(artist);
		buyButton.render(artist);
		levelLabel.render(artist);
		levelBar.render(artist);
	}
	
	
}
