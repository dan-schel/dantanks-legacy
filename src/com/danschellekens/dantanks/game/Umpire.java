package com.danschellekens.dantanks.game;

import java.util.HashMap;

import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.Match;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.data.values.PlayerAILevel;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.game.ai.*;
import com.danschellekens.dantanks.game.log.GameLog;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.page.PodiumPage;
import com.danschellekens.dantanks.page.StandingsUpdatePage;

public class Umpire {
	final Match match;
	final GameLog log;
	final ScoreKeeper scoreKeeper;
	
	Level level;
	
	HashMap<String, TankAI> tankAIs;
	LevelUmpire levelUmpire;
	ShopUmpire shopUmpire;
	
	UmpireStage stage;
	int roundsToPlayLeft;
	
	public Umpire(Match match) {
		this.match = match;
		this.log = new GameLog();
		this.scoreKeeper = new ScoreKeeper(match, this);
		
		stage = UmpireStage.MATCH_NOT_STARTED;
		roundsToPlayLeft = match.getRoundsToPlay();
		
		generateAIs();
	}
	void generateAIs() {
		tankAIs = new HashMap<String, TankAI>();
		
		for (Player player : match.getPlayers()) {
			if (!player.isAI()) { continue; }
			
			TankAI tankAI = null;
			
			if (player.getAILevel() == PlayerAILevel.VERY_EASY) {
				tankAI = new VeryEasyAI(player);
			}
			else if (player.getAILevel() == PlayerAILevel.EASY) {
				tankAI = new EasyAI(player);
			}
			else if (player.getAILevel() == PlayerAILevel.MEDIUM) {
				tankAI = new MediumAI(player);
			}
//			else if (player.getAILevel() == PlayerAILevel.HARD) {
//				tankAI = new HardAI(player);
//			}
//			else if (player.getAILevel() == PlayerAILevel.VERY_HARD) {
//				tankAI = new VeryHardAI(player);
//			}
			else {
				throw new OopsieException(new Exception("AI type not recognised."));
			}
			
			tankAIs.put(player.getID(), tankAI);
		}
	}
	
	public void update() {
		if (stage == UmpireStage.ROUND_IN_PROGRESS) {
			try {
				levelUmpire.update();
			} 
			catch (UmpireException e) {
				throw new OopsieException(e);
			}
		}
		else if (stage == UmpireStage.SHOPPING) {
			shopUmpire.update();
		}
	}
	
	public Level startRound() throws UmpireException {		
		int roundNo = match.getRoundsToPlay() - roundsToPlayLeft;
		match.beginNewRound(roundNo);
		
		stage = UmpireStage.ROUND_IN_PROGRESS;
		
		levelUmpire = new LevelUmpire(this, match, tankAIs);
		level = new Level(this.match.getCurrentRound(), levelUmpire);
		levelUmpire.provideLevel(level);
			
		return level;
	}
	public void endRound() throws UmpireException {
		scoreKeeper.updateScores(log);
		match.endRound();

		for (TankAI tankAI : tankAIs.values()) {
			tankAI.clearRoundMemory();
		}
		
		stage = UmpireStage.SHOWING_STANDINGS_UPDATE;
		DanTanks.GAME.setPage(new StandingsUpdatePage(match, this));
	}
	public void startShoppingOrPodium() {
		roundsToPlayLeft--;
		
		if (roundsToPlayLeft == 0) {
			stage = UmpireStage.MATCH_OVER;
			DanTanks.GAME.setPage(new PodiumPage(match));
		}
		else {
			stage = UmpireStage.SHOPPING;
			shopUmpire = new ShopUmpire(this, match, tankAIs);	
		}
	}
	public void consumeUtility(String utilityID, Player player, AimData aim) throws UmpireException, UtilityException {
		if (stage != UmpireStage.ROUND_IN_PROGRESS) { throw new UmpireException("Cannot consume utility. A round is not in progress."); }
		levelUmpire.consumeUtility(utilityID, player, aim);
	}
	public void playerDoneShopping() throws UmpireException {
		if (stage != UmpireStage.SHOPPING) { throw new UmpireException("Cannot finish player's shopping. Cannot do any shopping while in the '" + stage.toString() + "' stage."); }
		shopUmpire.playerDoneShopping();
	}
	
	public Player tankControlsPlayer() {
		if (stage != UmpireStage.ROUND_IN_PROGRESS) { return null; }
		return levelUmpire.tankControlsPlayer();
	}
	public boolean tankControlsVisible() {
		if (stage != UmpireStage.ROUND_IN_PROGRESS) { return false; }
		return levelUmpire.tankControlsVisible();
	}
	public float tankControlsPercentTurnOver() {
		if (stage != UmpireStage.ROUND_IN_PROGRESS) { return 0; }
		return levelUmpire.tankControlsPercentTurnOver();
	}
	public float shoppingPercentTurnOver() {
		if (stage != UmpireStage.SHOPPING) { return 0; }
		return shopUmpire.percentTurnOver();
	}

	public int getRoundsToPlayLeft() {
		return roundsToPlayLeft;
	}
	public int getRoundIndex() {
		return match.getRoundsToPlay() - roundsToPlayLeft;
	}
	
	public GameLog getLog() {
		return log;
	}
}
