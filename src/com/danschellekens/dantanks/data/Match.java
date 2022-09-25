package com.danschellekens.dantanks.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import com.danschellekens.dantanks.exceptions.*;

public class Match {
	public static Match Current;
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 6;
	
	final MatchOptions options;
	final LinkedHashMap<String, Player> players;
	final int roundsToPlay;
	
	int[] roundDamageMoneyWorths;
	int[] roundPassiveMoneyWorths;
	int[] roundPointWorths;
	
	Round currentRound;
	
	public Match(ArrayList<PlayerSettings> players, MatchOptions options) throws MatchCreationException {
		Current = this;

		if (players.size() < MIN_PLAYERS) { throw new MatchCreationException("Match must have at least " + MIN_PLAYERS + " players."); }
		if (players.size() > MAX_PLAYERS) { throw new MatchCreationException("Match cannot have over " + MAX_PLAYERS + " players."); }
		
		this.options = options;
		this.roundsToPlay = options.getLength().getAmountOfRounds();
		
		this.players = new LinkedHashMap<String, Player>();
		for (int i = 0; i < players.size(); i++) {
			PlayerSettings settings = players.get(i);
			String ID = settings.generateID(i);
			Player player = new Player(this, ID, settings);
			this.players.put(ID, player);
		}
		
		calculateMoneyAndPointPools(players.size());
	}
	void calculateMoneyAndPointPools(int playerCount) {
		roundDamageMoneyWorths = options.getLength().getRoundDamageMoneyWorths(playerCount);
		roundPassiveMoneyWorths = options.getLength().getRoundPassiveMoneyWorths(playerCount);
		roundPointWorths = options.getLength().getRoundPointWorths(playerCount);
		
		if (roundPassiveMoneyWorths.length != roundPointWorths.length && roundPassiveMoneyWorths.length != roundDamageMoneyWorths.length) {
			throw new OopsieException(new Exception("'roundPassiveMoneyWorths', " + "'roundDamageMoneyWorths', and 'roundPointWorths' must have the same amount of values."));
		}
	}
	
	public Player[] getPlayers() {
		Player[] result = new Player[players.size()];
		
		Object[] values = players.values().toArray();
		for (int i = 0; i < result.length; i++) {
			result[i] = (Player) values[i];
		}
		
		return result;
	}
	public Player getPlayer(String playerID) throws MissingPlayerException {
		if (!players.containsKey(playerID)) { throw new MissingPlayerException("Match doesn't have a player with ID '" + playerID + "'."); }
		return players.get(playerID);
	}
	public ArrayList<Player> getPlayersByScore() {
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(getPlayers()));
		
		Collections.sort(players, new Comparator<Player>() {
			@Override
			public int compare(Player player1, Player player2) {
				try {
					Integer score1 = player1.getScore();
					Integer score2 = player2.getScore();
					
					return score2.compareTo(score1);
				}
				catch (Exception e) {
					throw new OopsieException(e);
				}
			}		  
		});
		
		return players;
	}
	public int getPlayersAmount() {
		return players.size();
	}
	
	public void beginNewRound(int roundNo) throws UmpireException {
		if (this.currentRound != null) {
			throw new UmpireException("Match cannot begin a new round. A round is currently in progress and must end first.");
		}
		
		for (Player player : this.getPlayers()) {
			player.resetRoundMemory();
		}
		
		Round round = new Round(this);
		round.setDamageMoneyAvailable(roundDamageMoneyWorths[roundNo]);
		round.setPassiveMoneyAvailable(roundPassiveMoneyWorths[roundNo]);
		round.setPointsAvailable(roundPointWorths[roundNo]);
		
		this.currentRound = round;
	}
	public Round getCurrentRound() throws UmpireException {
		if (this.currentRound == null) {
			throw new UmpireException("Match cannot get current round. No round is currently in progress.");
		}
		
		return currentRound;
	}
	public void endRound() throws UmpireException {
		if (this.currentRound == null) {
			throw new UmpireException("Match cannot get end the current round. No round is currently in progress.");
		}
		
		this.currentRound = null;
	}
	public boolean isRoundInProgress() {
		return this.currentRound != null;
	}
	public int getRoundsToPlay() {
		return roundsToPlay;
	}
	public MatchOptions getOptions() {
		return options;
	}
}
