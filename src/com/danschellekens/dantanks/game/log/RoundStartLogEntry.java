package com.danschellekens.dantanks.game.log;

public class RoundStartLogEntry extends LogEntry {
	final int roundIndex;
	
	public RoundStartLogEntry(int roundIndex) {
		this.roundIndex = roundIndex;
	}

	public int getRoundNumber() {
		return roundIndex;
	}
}
