package com.danschellekens.dantanks.ui.tankControls.aimUI;

import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.level.Level;

public abstract class AimUI extends UIElement {
	final Level level;
	protected Player player;
	
	public AimUI(UIElement parent, float x, float y, Level level) {
		super(parent, new Rectangle(x, y, 200, 50));
		
		this.level = level;
	}
	
	public abstract AimData getAim();

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
