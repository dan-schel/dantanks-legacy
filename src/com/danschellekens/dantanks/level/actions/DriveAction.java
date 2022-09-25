package com.danschellekens.dantanks.level.actions;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.operations.Numbers;
import com.danschellekens.slick2d.graphics.Artist;

public class DriveAction extends UtilityAction {
	int driveAmount;
	int driveTick;
	
	public DriveAction(Utility utility, AimData aim) {
		super(utility, aim);
		
		driveAmount = 20;
		setEndsTurn(false);
	}
	
	@Override
	protected void start(LevelUmpire umpire) {
		driveTick = Numbers.Abs(driveAmount);
	}

	@Override
	public void update() {
		if (driveTick > 0) {
			driveTick--;
			
			if (driveAmount < 0) {
				firer.driveLeft();
			}
			else {
				firer.driveRight();
			}
		}
	}

	@Override
	public void renderGraphics(Artist artist) {
		// Nothing to draw
	}
	@Override
	public void renderHitboxes(Artist artist) {
		// Nothing to draw
	}

	@Override
	public boolean isTurnDone() {
		if (driveAmount < 0) {
			if (!firer.canDriveLeft()) { return true; }
		}
		else {
			if (!firer.canDriveRight()) { return true; }
		}
		
		return driveTick == 0;
	}
	
	@Override
	public boolean isCompletelyDone() {
		return isTurnDone();
	}

	@Override
	public boolean shouldRun(Level level, TankObj firer) {
		return true;
	}

	
	public int getDriveAmount() {
		return driveAmount;
	}

	public void setDriveAmount(int driveAmount) {
		this.driveAmount = driveAmount;
	}
}
