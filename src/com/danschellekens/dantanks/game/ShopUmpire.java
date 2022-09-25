package com.danschellekens.dantanks.game;

import java.util.HashMap;

import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.Match;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.exceptions.AIException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.game.ai.TankAI;
import com.danschellekens.dantanks.page.GamePage;
import com.danschellekens.dantanks.page.ShopPage;
import com.danschellekens.operations.Range;

public class ShopUmpire {
	public static final int SHOP_TIME = 1000 * 60;
	
	final Umpire umpire;
	final Match match;
	final HashMap<String, TankAI> tankAIs;

	int tick;
	
	Player[] players;
	int currentPlayerIndex;
	
	public ShopUmpire(Umpire umpire, Match match, HashMap<String, TankAI> tankAIs) {
		this.umpire = umpire;
		this.match = match;
		this.tankAIs = tankAIs;
		
		this.players = this.match.getPlayers();
		this.currentPlayerIndex = -1;
		
		playerDoneShopping();
	}
	
	public void update() {
		tick --;
		
		if (tick <= 0) {
			playerDoneShopping();
		}
	}

	public Player getCurrentPlayerShopping() {
		return players[currentPlayerIndex];
	}
	public void playerDoneShopping() {
		tick = SHOP_TIME;
		this.currentPlayerIndex ++;
		
		if (this.currentPlayerIndex == this.players.length) {
			DanTanks.GAME.setPage(new GamePage(match, umpire));
			return;
		}
		
		if (getCurrentPlayerShopping().isAI()) {
			runAI(getCurrentPlayerShopping());
			playerDoneShopping();
			return;
		}
		else {
			DanTanks.GAME.setPage(new ShopPage(match, this, getCurrentPlayerShopping()));
		}
	}
	
	void runAI(Player player) {
		try {
			tankAIs.get(player.getID()).shop();
		}
		catch (AIException e) {
			throw new OopsieException(e);
		}
	}
	
	public float percentTurnOver() {
		return Range.Percent(this.tick, SHOP_TIME, 0);
	}

	public int getRoundNo() {
		return umpire.getRoundIndex();
	}
}
