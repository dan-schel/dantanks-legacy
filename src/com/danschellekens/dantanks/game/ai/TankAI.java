package com.danschellekens.dantanks.game.ai;

import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.exceptions.AIException;
import com.danschellekens.dantanks.level.Level;

public abstract class TankAI {
	final Player player;
	
	public TankAI(Player player) {
		this.player = player;
	}
	
	public abstract boolean run(Level level) throws AIException;
	public abstract void shop() throws AIException;
	public abstract void clearRoundMemory();
}
