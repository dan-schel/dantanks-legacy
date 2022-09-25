package com.danschellekens.dantanks.exceptions;

import com.danschellekens.dantanks.data.Player;

public class CostException extends Exception {
	private static final long serialVersionUID = -1589170466431314566L;

	public CostException(Player player, int cost) {
		super("Player '" + player.getID() + "' could not afford $" + cost + " payment.");
	}
}
