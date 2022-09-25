package com.danschellekens.dantanks.game.log;

import java.util.ArrayList;

public class GameLog {
	ArrayList<LogEntry> entries;
	
	public GameLog() {
		entries = new ArrayList<LogEntry>();
	}
	public void log(LogEntry entry) {
		entries.add(entry);
	}
	public void logDamage(String playerDamaged, String playerResponsible, String utilityResponsible, float initialHealth, float finalHealth, float damageBlocked) {
		entries.add(new DamageLogEntry(playerDamaged, playerResponsible, utilityResponsible, initialHealth, finalHealth, damageBlocked));
	}
	public void logHealing(String playerHealed, String playerResponsible, String utilityResponsible, float damageHealed) {
		entries.add(new HealedLogEntry(playerHealed, playerResponsible, utilityResponsible, damageHealed));
	}
	public void logDeath(String playerThatDied) {
		entries.add(new DeathLogEntry(playerThatDied));
	}
	public void logRoundStart(int roundIndex) {
		entries.add(new RoundStartLogEntry(roundIndex));
	}
	public void logPointsAwarded(String playerID, int pointsAwarded, int moneyAwarded) {
		entries.add(new PointsAwardedLogEntry(playerID, pointsAwarded, moneyAwarded));
	}
	public LogEntry[] getEntries() {
		LogEntry[] result = new LogEntry[entries.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = entries.get(i);
		}
		return result;
	}
	public LogEntry[] getCurrentRoundEntries() {
		int startIndex = 0;
		for (int i = entries.size() - 1; i >= 0 ; i--) {
			if (entries.get(i) instanceof RoundStartLogEntry) {
				startIndex = i;
				break;
			}
		}
		
		LogEntry[] result = new LogEntry[entries.size() - startIndex];
		for (int i = startIndex; i < entries.size(); i++) {
			result[i - startIndex] = entries.get(i);
		}
		return result;
	}
}
