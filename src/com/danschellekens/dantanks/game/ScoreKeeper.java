package com.danschellekens.dantanks.game;

import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.exceptions.UmpireException;
import com.danschellekens.dantanks.game.log.*;
import com.danschellekens.operations.Numbers;

public class ScoreKeeper {
	final Match match;
	final Umpire umpire;
	
	public ScoreKeeper(Match match, Umpire umpire) {
		this.match = match;
		this.umpire = umpire;
	}
	public void updateScores(GameLog log) throws UmpireException {
		Round round = match.getCurrentRound();
		int passiveMoneyAvailable = round.getPassiveMoneyAvailable();
		int damageMoneyAvailable = round.getDamageMoneyAvailable();
		int pointsAvailable = round.getPointsAvailable();
		
		Player[] players = match.getPlayers();
		float totalDamage = totalDamageDealt(players, log);
		
		for (Player player : players) {
			float damageByPlayer = damageDealtBy(player, log);
			
			int moneyToAward = Numbers.Round(damageMoneyAvailable * (damageByPlayer / totalDamage));
			int pointsToAward = Numbers.Round(pointsAvailable * (damageByPlayer / totalDamage));
			
			log.logPointsAwarded(player.getID(), pointsToAward, moneyToAward);
			player.addMoney(moneyToAward + passiveMoneyAvailable);
			player.addScore(pointsToAward);
		}
	}
	float damageDealtBy(Player player, GameLog log) {
		float damageDealt = 0;
		for (LogEntry entry : log.getCurrentRoundEntries()) {
			if (entry instanceof DamageLogEntry) {
				DamageLogEntry damageEntry = (DamageLogEntry) entry;
				if (!damageEntry.getPlayerResponsible().equals(damageEntry.getPlayerDamaged()) 
						&& damageEntry.getPlayerResponsible().equals(player.getID())) {
					damageDealt += damageEntry.getDamageCaused();
				}
			}
		}
		return damageDealt;
	}
	float totalDamageDealt(Player[] players, GameLog log) {
		float damageDealt = 0;
		for (Player player : players) {
			damageDealt += damageDealtBy(player, log);
		}
		return damageDealt;
	}
}
