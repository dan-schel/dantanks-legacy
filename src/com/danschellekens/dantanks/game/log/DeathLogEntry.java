package com.danschellekens.dantanks.game.log;

public class DeathLogEntry extends LogEntry {
	final String playerThatDied;
	
	public DeathLogEntry(String playerThatDied) {
		this.playerThatDied = playerThatDied;
	}

	public String getPlayerThatDied() {
		return playerThatDied;
	}
}
