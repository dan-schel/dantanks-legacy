package com.danschellekens.dantanks.ui.tankControls;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.exceptions.UtilityException;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.ui.control.BigCircleIconButton;
import com.danschellekens.dantanks.ui.tankControls.aimUI.*;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class AimAndConsumeUI extends UIElement {
	public static final int MCP_LENGTH = 40;
	
	final Level level;
	Player player;
	String utilityID;
	
	AimUI aimUI;
	BigCircleIconButton consumeButton;
	
	int mcpTick;
	
	public AimAndConsumeUI(UIElement parent, float x, float y, Level level) {
		super(parent, new Rectangle(x, y, 280, TankControlUI.HEIGHT));
		
		this.level = level;
		
		try {
			consumeButton = new BigCircleIconButton(this, 220, (TankControlUI.HEIGHT - 50) / 2, new Image("ui/bigCircleIconButton/fire.png"));
			consumeButton.addKey(Input.KEY_SPACE);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	@Override
	public void update(DanInput input) {
		if (aimUI != null) {
			aimUI.setPlayer(player);
			aimUI.update(input);
		}
		
		if (mcpTick > 0) {
			mcpTick--;
		}
		
		consumeButton.update(input);
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		if (aimUI != null) {
			aimUI.render(artist);
		}
		
		consumeButton.render(artist);
		super.renderInPosition(artist);
	}

	void utilityChanged(String utilityID) {
		this.mcpTick = MCP_LENGTH;
		
		if (utilityID == null) { 
			aimUI = null;
			return;
		}
		
		try {
			Utility utility = UtilityLibrary.CreateUtility(utilityID);
			
			switch (utility.getAimType()) {
			case ANGLE_POWER:
				aimUI = new AnglePowerAimUI(this, 10, (TankControlUI.HEIGHT - 50) / 2, level);
				break;
			case SELF:
				aimUI = new SelfAimUI(this, 10, (TankControlUI.HEIGHT - 50) / 2, level);
				break;
			case NO_TARGET:
				aimUI = new NoTargetAimUI(this, 10, (TankControlUI.HEIGHT - 50) / 2, level);
				break;
			default:
				aimUI = null;
				throw new OopsieException(new Exception("AimType '" + utility.getAimType() + "' not supported."));
			}
			
			consumeButton.setIcon(new Image("ui/bigCircleIconButton/" + utility.getConsumeIcon() + ".png"));
		} 
		catch (UtilityException e) {
			throw new OopsieException(e);
		} 
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getUtilityID() {
		return utilityID;
	}

	public void setUtilityID(String utilityID) {
		if ((this.utilityID == null && utilityID != null) || 
				(this.utilityID != null && !this.utilityID.equals(utilityID))) {
			utilityChanged(utilityID);
		}
		this.utilityID = utilityID;
	}
	
	public AimData getAim() {
		if (aimUI != null) {
			return aimUI.getAim();
		}
		else {
			throw new OopsieException("Cannot get aim. No utility is selected.");
		}
	}
	public boolean justActivatedConsumeButton() {
		return mcpTick < 1 && consumeButton.justActivated();
	}
}
