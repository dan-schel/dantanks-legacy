package com.danschellekens.dantanks.game.log;

public class PointsAwardedLogEntry extends LogEntry {
	final String playerID;
	final int pointsAwarded;
	final int moneyAwarded;
	
	public PointsAwardedLogEntry(String playerID, int pointsAwarded, int moneyAwarded) {
		this.playerID = playerID;
		this.pointsAwarded = pointsAwarded;
		this.moneyAwarded = moneyAwarded;
	}

	public String getPlayerID() {
		return playerID;
	}
	public int getPointsAwarded() {
		return pointsAwarded;
	}
	public int getMoneyAwarded() {
		return moneyAwarded;
	}
}
