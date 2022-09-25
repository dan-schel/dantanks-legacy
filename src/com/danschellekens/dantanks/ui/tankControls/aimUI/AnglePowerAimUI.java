package com.danschellekens.dantanks.ui.tankControls.aimUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.Tank;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.aim.AnglePowerAimData;
import com.danschellekens.dantanks.exceptions.MissingPlayerException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.ui.control.IconSlider;
import com.danschellekens.operations.Conditions;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class AnglePowerAimUI extends AimUI {
	public static final String MEMORY_KEY_ANGLE = "AnglePowerAimUI.PreviousAngle";
	public static final String MEMORY_KEY_POWER = "AnglePowerAimUI.PreviousPower";
	
	IconSlider angleSlider;
	IconSlider powerSlider;
	
	public AnglePowerAimUI(UIElement parent, float x, float y, Level level) {
		super(parent, x, y, level);
		
		try {
			angleSlider = new IconSlider(this, 0, 0, 120, new Image("ui/smallIcon/angle.png"));
			this.angleSlider.getSlider().setForeColor(new Color(0, 150, 240));
			this.angleSlider.getSlider().setupKeys(0.7f, Input.KEY_A, Input.KEY_D);
			this.angleSlider.setSuffix("°");
			
			powerSlider = new IconSlider(this, 0, 30, 120, new Image("ui/smallIcon/power.png"));
			this.powerSlider.getSlider().setForeColor(new Color(0, 150, 240));
			this.powerSlider.getSlider().setupKeys(0.04f, Input.KEY_S, Input.KEY_W);
			this.powerSlider.setUsingPercent(true);
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	@Override
	public void update(DanInput input) {
		angleSlider.update(input);
		powerSlider.update(input);
		
		if (player != null) {
			try {
				TankObj tankObj = level.getTankFromOwner(player.getID());
				tankObj.setAngle(angleSlider.getValue());
				tankObj.setPower(powerSlider.getValue());
				
				powerSlider.setSliderValues(0, 0, player.getTank().getPowerStatValue(), 0, tankObj.getMaxPowerAvailable());
			}
			catch (MissingPlayerException e) {
				throw new OopsieException(e);
			}
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		angleSlider.render(artist);
		powerSlider.render(artist);
		
		super.renderInPosition(artist);
	}

	void newPlayer(Player player) {
		if (player == null) { return; }
		
		float tankObjsAngle = 0;
		float tankObjsPower = 0;
		float tankObjsMaxPower = 0;
		
		try {
			TankObj tankObj = level.getTankFromOwner(player.getID());
			tankObjsAngle = tankObj.getAngle();
			tankObjsPower = tankObj.getPower();
			tankObjsMaxPower = tankObj.getMaxPowerAvailable();
		}
		catch (MissingPlayerException e) {
			throw new OopsieException(e);
		}
		
		angleSlider.setSliderInitialValues(tankObjsAngle, Tank.MIN_ANGLE, Tank.MAX_ANGLE);
		powerSlider.setSliderInitialValues(tankObjsPower, 0, player.getTank().getPowerStatValue(), 0, tankObjsMaxPower);
		
		try {
			float ghostAngle = Float.parseFloat(player.recallForRound(MEMORY_KEY_ANGLE, "NO"));
			float ghostPower = Float.parseFloat(player.recallForRound(MEMORY_KEY_POWER, "NO"));
			
			angleSlider.setSliderGhostValue(ghostAngle);
			powerSlider.setSliderGhostValue(ghostPower);
		}
		catch (NumberFormatException e) {
			angleSlider.setSliderHideGhostValue();
			powerSlider.setSliderHideGhostValue();
		}
	}
	
	@Override
	public void setPlayer(Player player) {
		if (player != null && !Conditions.Equals(this.player, player)) {
			newPlayer(player);
		}
		
		super.setPlayer(player);
	}

	@Override
	public AimData getAim() {
		try {
			player.rememberForRound(MEMORY_KEY_ANGLE, Float.toString(angleSlider.getValue()));
			player.rememberForRound(MEMORY_KEY_POWER, Float.toString(powerSlider.getValue()));
			
			return new AnglePowerAimData(angleSlider.getValue(), powerSlider.getValue(), level.getTankFromOwner(player.getID()));
		}
		catch (MissingPlayerException e) {
			throw new OopsieException(e);
		}
	}

}
