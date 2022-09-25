package com.danschellekens.dantanks.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.operations.Random;

public class Round {
	final Match match;
	
	HashMap<String, Boolean> playerIDsAlive;
	ArrayList<String> playerOrder;
	String currentPlayersTurn;
	
	int passiveMoneyAvailable;
	int damageMoneyAvailable;
	int pointsAvailable;
	boolean roundOver;
	
	public Round(Match match) {
		this.match = match;
		this.roundOver = false;
		
		playerIDsAlive = new HashMap<String, Boolean>();
		for (Player p : match.getPlayers()) {
			playerIDsAlive.put(p.getID(), true);
		}
		determinePlayerOrder();
		
		currentPlayersTurn = playerOrder.get(0);
		
		passiveMoneyAvailable = 0;
		damageMoneyAvailable = 0;
		pointsAvailable = 0;
	}
	void determinePlayerOrder() {
		playerOrder = new ArrayList<String>();
		
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(match.getPlayers()));
		ArrayList<Float> weights = new ArrayList<Float>();
		
		for (int i = 0; i < players.size(); i++) {
			weights.add((float) players.get(i).getAdvantagePoints());
		}
		
		int playerCount = players.size();
		for (int a = 0; a < playerCount; a++) {
			Player player = Random.ListElement(players, weights);
			
			playerOrder.add(player.getID());
			player.addAdvantagePoints(a);
			
			int playerIndex = players.indexOf(player);
			players.remove(playerIndex);
			weights.remove(playerIndex);
		}
	}
	
	public void nextPlayersTurn() throws MissingPlayerException {
		int index = playerOrder.indexOf(currentPlayersTurn);
		
		if (index == -1) {
			throw new MissingPlayerException("The next player's turn cannot be determined. The player '" + currentPlayersTurn + "' turn ended, but their name is not within the player order array stored by the Round.");
		}
		else {
			index++;
			if (index >= playerOrder.size()) { index = 0; }
			currentPlayersTurn = playerOrder.get(index);
		}
	}
	public void killPlayer(String playerID) throws MissingPlayerException, UmpireException {
		if (!playerIDsAlive.containsKey(playerID)) {
			throw new MissingPlayerException("Cannot kill player '" + playerID + "'. No entry for this player was found in the Round's players alive list.");
		}
		if (playerIDsAlive.get(playerID) == false) {
			throw new UmpireException("Cannot kill player '" + playerID + "'. According to the Round's player alive list, this player is already dead.");
		}
		
		if (this.currentPlayersTurn == playerID) {
			nextPlayersTurn();
		}
			
		playerIDsAlive.put(playerID, false);
		playerOrder.remove(playerID);
			
		if (countPlayersAlive() < 2) {
			this.roundOver = true;
			this.currentPlayersTurn = null;
		}
	}
	public boolean isCurrentlySomeonesTurn() {
		return this.currentPlayersTurn != null && !this.roundOver;
	}
	public String getCurrentPlayerID() throws UmpireException {
		if (this.currentPlayersTurn == null || this.roundOver) { throw new UmpireException("Cannot get the ID of the player who's turn it currently is. Currently, it is no-one's turn."); }
		if (this.roundOver) { throw new UmpireException("Cannot get the ID of the player who's turn it currently is. This round has ended."); }
		
		return this.currentPlayersTurn;
	}
	public Player getCurrentPlayer() throws UmpireException {
		try { return match.getPlayer(getCurrentPlayerID()); }
		catch (MissingPlayerException e) { throw new OopsieException(e); }
	}
	public int countPlayersAlive() {
		return playerOrder.size();
	}
	public Player[] getPlayersAlive() {
		Player[] players = new Player[countPlayersAlive()];
		
		try {
			for (int i = 0; i < players.length; i++) {
				players[i] = match.getPlayer(playerOrder.get(i));
			}
			
			return players;
		}
		catch (MissingPlayerException e) {
			throw new OopsieException(e);
		}
	}
	public boolean isRoundOver() {
		return roundOver;
	}

	public int getPassiveMoneyAvailable() {
		return passiveMoneyAvailable;
	}
	public void setPassiveMoneyAvailable(int passiveMoneyAvailable) {
		this.passiveMoneyAvailable = passiveMoneyAvailable;
	}
	public int getDamageMoneyAvailable() {
		return damageMoneyAvailable;
	}
	public void setDamageMoneyAvailable(int damageMoneyAvailable) {
		this.damageMoneyAvailable = damageMoneyAvailable;
	}
	public int getPointsAvailable() {
		return pointsAvailable;
	}
	public void setPointsAvailable(int pointsAvailable) {
		this.pointsAvailable = pointsAvailable;
	}
}
