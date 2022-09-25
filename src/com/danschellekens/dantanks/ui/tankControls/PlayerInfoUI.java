package com.danschellekens.dantanks.ui.tankControls;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.exceptions.MissingPlayerException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.IconBar;
import com.danschellekens.dantanks.ui.control.TankIcon;
import com.danschellekens.operations.Numbers;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class PlayerInfoUI extends UIElement {
	final Level level;
	Player player;
	
	Label usernameLabel;
	Label moneyLabel;
	TankIcon tankIcon;
	IconBar healthBar;
	IconBar fuelBar;
	
	public PlayerInfoUI(UIElement parent, float x, float y, Level level) {
		super(parent, new Rectangle(x, y, 275, TankControlUI.HEIGHT));
		
		this.level = level;
		
		usernameLabel = new Label(this, new Rectangle(10, 10, 150, 30), FontLibrary.getLargeFont());
		usernameLabel.setHAlign(HAlign.LEFT);
		
		moneyLabel = new Label(this, new Rectangle(160, 10, 100, 30), FontLibrary.getBodyFont());
		moneyLabel.setHAlign(HAlign.RIGHT);
		
		tankIcon = new TankIcon(this, 10, 50);
		
		try {
			healthBar = new IconBar(this, 75, 50, 100, new Image("ui/smallIcon/health.png"));
			healthBar.getBar().setForeColor(new Color(255, 0, 0));
			healthBar.setSuffix(" HP");
			
			fuelBar = new IconBar(this, 75, 80, 100, new Image("ui/smallIcon/fuel.png"));
			fuelBar.getBar().setForeColor(new Color(0, 140, 0));
			fuelBar.setUsingPercent(true);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	@Override
	public void update(DanInput input) {
		usernameLabel.update(input);
		moneyLabel.update(input);
		tankIcon.update(input);
		healthBar.update(input);
		fuelBar.update(input);
		
		if (player != null) {
			usernameLabel.setText(player.getName());
			moneyLabel.setText("$" + player.getMoney());
			tankIcon.setPlayer(player);
			
			try {
				TankObj tankObj = level.getTankFromOwner(player.getID());
				healthBar.setBarValues(Numbers.Ceil(tankObj.health().current()), 0, tankObj.health().max());
				fuelBar.setBarValues(tankObj.fuel().current(), 0, tankObj.fuel().max());
			} 
			catch (MissingPlayerException e) {
				throw new OopsieException(e);
			}
		}
		else {
			usernameLabel.setText("<null>");
			moneyLabel.setText("$0");
			tankIcon.setPlayer(null);
			healthBar.setBarValues(0, 0, 1);
			fuelBar.setBarValues(0, 0, 1);
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		usernameLabel.render(artist);
		moneyLabel.render(artist);
		tankIcon.render(artist);
		healthBar.render(artist);
		fuelBar.render(artist);
		
		super.renderInPosition(artist);
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
