package com.danschellekens.dantanks.ui.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.Round;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.dantanks.ui.control.ProgressBar;
import com.danschellekens.operations.Increment;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class TankIndicator extends Panel {
	final TankObj tankObj;
	final Level level;
	final Round round;
	
	boolean levelPaused;
	
	Label nameLabel;
	ProgressBar healthBar;
	ProgressBar shieldBar;
	
	float healthMemory;
	float shieldMemory;
	
	int damagedTick;
	float opacity;
	
	public TankIndicator(UIElement parent, TankObj tankObj, Level level, Round round) {
		super(parent, new Rectangle(0, 0, 150, 46));
		
		this.tankObj = tankObj;
		this.level = level;
		this.round = round;
		
		nameLabel = new Label(this, new Rectangle(0, 0, 150, 30), FontLibrary.getBodyFont());
		nameLabel.setHAlign(HAlign.CENTER);
		this.addChild(nameLabel);

		healthBar = new ProgressBar(this, new Rectangle(10, 30, 130, 6));
		this.addChild(healthBar);

		shieldBar = new ProgressBar(this, new Rectangle(10, 33, 130, 3));
		shieldBar.setBackColor(new Color(0, 0, 0, 0));
		this.addChild(shieldBar);		
		
		healthMemory = tankObj.health().current();
		shieldMemory = tankObj.health().shieldPercentage();
	}

	@Override
	public void update(DanInput input) {
		float scrollPos = level.getScrollPosSmoothened();
		this.setX(tankObj.hitbox().getPosition().getX() - 75);
		this.setY(tankObj.hitbox().getPosition().getY() + scrollPos + 30);
		
		updateOpacity(input);
		
		try {
			nameLabel.setText(tankObj.getTank().getOwner().getName());
			healthBar.setValues(tankObj.health().current(), 0, tankObj.health().max());
			shieldBar.setValues(tankObj.health().shieldPercentage(), 0, 1);
			
			nameLabel.setColor(new Color(0f, 0f, 0f, opacity));
			healthBar.setForeColor(new Color(240/255f, 0, 0, opacity));
			healthBar.setBackColor(new Color(0f, 0f, 0f, 0.1f * opacity));
	
			shieldBar.setForeColor(new Color(0, 0.8f, 0.8f, opacity));
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
		
		super.update(input);
	}
	void updateOpacity(DanInput input) {
		try {
			float scrollPos = level.getScrollPosSmoothened();
			
			if (healthMemory != tankObj.health().current() || shieldMemory != tankObj.health().shieldPercentage()) {
				damagedTick = 90;
			}
			healthMemory = tankObj.health().current();
			shieldMemory = tankObj.health().shieldPercentage();
			
			damagedTick = Increment.Linear(damagedTick, 0, 1);
			float mouseDistToTank = input.getMouse()
					.subtract(new Vector2(0, scrollPos))
					.subtract(tankObj.hitbox().getPosition()).magnitude();
			boolean mouseHovering = mouseDistToTank < 20 && input.isMouseValid();
			boolean tankHealthRecentlyChanged = damagedTick > 0;
			
			Player tankOwner = tankObj.getTank().getOwner();
			boolean isTanksTurn = round.isCurrentlySomeonesTurn() 
					&& round.getCurrentPlayerID().equals(
							tankOwner.getID()) && tankOwner.isAI();
			
			boolean isPlayerAlive = !tankObj.health().isDead();
			
			if (((mouseHovering && isPlayerAlive) || tankHealthRecentlyChanged || isTanksTurn) && !levelPaused) {
				opacity = Increment.Linear(opacity, 1f, 0.1f);
			}
			else {
				opacity = Increment.Linear(opacity, 0f, 0.1f);
			}
		} 
		catch (UmpireException | MissingPlayerException e) {
			throw new OopsieException(e);
		}	
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new RoundedRectangle(0, 0, getWidth(), getHeight(), 5), new Color(1f, 1f, 1f, 0.8f * opacity));
		super.renderInPosition(artist);
	}

	public void setLevelPaused(boolean levelPaused) {
		this.levelPaused = levelPaused;
	}
}
