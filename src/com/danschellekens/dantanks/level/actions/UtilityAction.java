package com.danschellekens.dantanks.level.actions;

import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.exceptions.MissingPlayerException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.slick2d.graphics.Artist;

public abstract class UtilityAction {
	protected final Utility utility;
	protected final AimData aim;
	
	protected Level level;
	protected TankObj firer;
	protected String firerID;
	
	boolean endsTurn;
	
	public UtilityAction(Utility utility, AimData aim) {
		this.utility = utility;
		this.aim = aim;
		
		this.endsTurn = true;
	}
	
	public void run(Level level, LevelUmpire umpire, TankObj firer) {
		this.level = level;
		this.firer = firer;
		
		try {
			this.firerID = firer.getTank().getOwner().getID();
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
		
		start(umpire);
	}
	public abstract boolean shouldRun(Level level, TankObj firer);
	
	protected abstract void start(LevelUmpire umpire);
	public abstract void update();
	public abstract void renderGraphics(Artist artist);
	public abstract void renderHitboxes(Artist artist);
	public abstract boolean isTurnDone();
	public abstract boolean isCompletelyDone();

	public boolean isEndsTurn() {
		return endsTurn;
	}

	public void setEndsTurn(boolean endsTurn) {
		this.endsTurn = endsTurn;
	}
}
