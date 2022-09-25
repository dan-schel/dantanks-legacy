package com.danschellekens.dantanks.ui.shop;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.exceptions.CostException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.page.ShopPage;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.LongRectangleTextButton;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class BuyUtilityUI extends Button {
	public static int WIDTH = ShopPage.BUY_UI_WIDTH;
	public static int HEIGHT = 54;
	
	final Player player;
	final Utility utility;
	
	ImageViewer iconViewer;
	Label nameLabel;
	Label descLabel;
	Label quantityLabel;
	LongRectangleTextButton buyButton;
	
	public BuyUtilityUI(UIElement parent, int x, int y, Player player, Utility utility) {
		super(parent, new Rectangle(x, y, WIDTH, HEIGHT));
		
		this.player = player;
		this.utility = utility;
		
		iconViewer = new ImageViewer(this, new Rectangle(12, 4, 30, 30), utility.getShopInfo().getIcon());
		
		nameLabel = new Label(this, new Rectangle(30 + 12 + 12, 7, WIDTH - 168, 20), FontLibrary.getBodyFont());
		nameLabel.setHAlign(HAlign.LEFT);
		nameLabel.setText(utility.getShopInfo().getDisplayName());
		
		descLabel = new Label(this, new Rectangle(30 + 12 + 12, 27, WIDTH - 168, 20), FontLibrary.getSmallFont());
		descLabel.setText(utility.getShopInfo().getDescription());
		descLabel.setHAlign(HAlign.LEFT);
		
		quantityLabel = new Label(this, new Rectangle(12, 29, 30, 20), FontLibrary.getBodyFont());
		quantityLabel.setHAlign(HAlign.CENTER);
		
		String text = utility.getShopInfo().getQuantityInPack() + " for $" + utility.getShopInfo().getPriceOfPack();
		buyButton = new LongRectangleTextButton(this, new Rectangle(WIDTH - 100 - 7, 7, 100, 40), text);
		buyButton.setEnabled(false);
		
		if (utility.getShopInfo().getQuantityInPack() == 0) {
			buyButton.setText("MAXED OUT");
		}
		if (utility.getShopInfo().getPriceOfPack() == 0) {
			buyButton.setText("FREE");
		}
	}
	
	@Override
	public void update(DanInput input) {
		iconViewer.update(input);
		nameLabel.update(input);
		descLabel.update(input);
		quantityLabel.update(input);
		buyButton.update(input);
		
		quantityLabel.setText(player.getTank().getInventory().getUtilityQuantityString(utility.getID()));
		
		buyButton.setEnabled(player.canAfford(utility.getShopInfo().getPriceOfPack()) && utility.getShopInfo().getQuantityInPack() != 0);
		if (buyButton.justActivated() && player.canAfford(utility.getShopInfo().getPriceOfPack())) {
			try {
				player.getTank().getInventory().buyUtility(utility, player);
			} 
			catch (CostException e) {
				throw new OopsieException(e);
			}
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new RoundedRectangle(0, 0, this.getWidth(), this.getHeight(), 5), new Color(0, 0, 0, 0.05f));
		
		iconViewer.render(artist);
		nameLabel.render(artist);
		descLabel.render(artist);
		quantityLabel.render(artist);
		buyButton.render(artist);
	}
}
