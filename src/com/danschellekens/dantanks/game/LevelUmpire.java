package com.danschellekens.dantanks.game;

import java.util.HashMap;

import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.aim.AimData;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.game.ai.TankAI;
import com.danschellekens.dantanks.game.log.GameLog;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.actions.UtilityAction;
import com.danschellekens.operations.Range;

public class LevelUmpire {
	public static int TURN_TIME = 1000 * 60;
	public static int TURN_DELAY_TIME = 60 / 2;
	public static int INITIAL_DELAY_TIME = 2 * 60;
	public static int SHOPPING_DELAY_TIME = 2 * 60;
	public static int AI_DELAY_TIME = 20;
	
	final Umpire umpire;
	final Match match;
	final HashMap<String, TankAI> tankAIs;
	
	Level level;
	LevelUmpireStage stage;
	int tick;
	
	public LevelUmpire(Umpire umpire, Match match, HashMap<String, TankAI> tankAIs) {
		this.umpire = umpire;
		this.match = match;
		this.tankAIs = tankAIs;
		
		umpire.getLog().logRoundStart(this.getRoundNo());
		setNextStage(LevelUmpireStage.INITIAL_DELAY_BEFORE_LEVEL_BEGINS);
	}
	public void provideLevel(Level level) {
		this.level = level;
	}
	
	public void update() throws UmpireException {
		if (level == null) { throw new OopsieException(new Exception("Level not provided to LevelUmpire.")); }
		
		switch (stage) {
		case INITIAL_DELAY_BEFORE_LEVEL_BEGINS:
			updateInitalDelay();
			break;
		case MAKING_AI_SEEM_HUMAN:
			updateFakingAI();
			break;
		case WAITING_FOR_PLAYER_TO_FIRE:
			updateWaitingForPlayer();
			break;
		case WAITING_FOR_LEVEL_TO_RUN_ACTIONS:
			updateWaitingForActions();
			break;
		case DELAY_BETWEEN_TURNS:
			updateDelayBetweenTurns();
			break;
		case DELAY_AFTER_CURRENT_PLAYER_DIES:
			updateDelayAfterCurrentPlayerDies();
			break;
		case FINAL_DELAY_BEFORE_SHOPPING:
			updateFinalDelay();
			break;
		default:
			throw new OopsieException(new Exception("Unknown LevelUmpireStage.")); 
		}
	}
	void updateInitalDelay() {
		tick--;
		
		if (tick == 0) {
			try {
				Player player = match.getCurrentRound().getCurrentPlayer();
				
				if (player.isAI()) {
					setNextStage(LevelUmpireStage.MAKING_AI_SEEM_HUMAN);
				}
				else {
					setNextStage(LevelUmpireStage.WAITING_FOR_PLAYER_TO_FIRE);
				}
			} 
			catch (UmpireException e) {
				throw new OopsieException(e);
			}
		}
	}
	void updateFakingAI() throws UmpireException {
		if (level.isCurrentlyRunningActions()) { return; }
			
		tick--;
		
		if (tick == 0) {
			Player player = match.getCurrentRound().getCurrentPlayer();
			boolean finished = runAI(player);
			if (finished) {
				setNextStage(LevelUmpireStage.WAITING_FOR_LEVEL_TO_RUN_ACTIONS);
			}
			else {
				setNextStage(LevelUmpireStage.MAKING_AI_SEEM_HUMAN);
			}
		}
	}
	void updateWaitingForPlayer() {
		tick--;
		
		if (tick == 0) {
			setNextStage(LevelUmpireStage.DELAY_BETWEEN_TURNS);
		}
	}
	void updateWaitingForActions() throws UmpireException {
		if (!level.isCurrentlyRunningActions()) {
			if (match.getCurrentRound().isRoundOver()) {
				setNextStage(LevelUmpireStage.FINAL_DELAY_BEFORE_SHOPPING);
			}
			else {
				setNextStage(LevelUmpireStage.DELAY_BETWEEN_TURNS);
			}
			
		}
	}
	void updateDelayBetweenTurns() throws UmpireException {
		tick--;
		
		if (tick == 0) {
			try {
				match.getCurrentRound().nextPlayersTurn();
				Player player = match.getCurrentRound().getCurrentPlayer();
				
				if (player.isAI()) {
					setNextStage(LevelUmpireStage.MAKING_AI_SEEM_HUMAN);
				}
				else {
					setNextStage(LevelUmpireStage.WAITING_FOR_PLAYER_TO_FIRE);
				}
			}
			catch (MissingPlayerException e) {
				throw new OopsieException(e);
			}
		}
	}
	void updateDelayAfterCurrentPlayerDies() throws UmpireException {
		tick--;
		
		if (tick == 0) {
			Player player = match.getCurrentRound().getCurrentPlayer();
			
			if (player.isAI()) {
				setNextStage(LevelUmpireStage.MAKING_AI_SEEM_HUMAN);
			}
			else {
				setNextStage(LevelUmpireStage.WAITING_FOR_PLAYER_TO_FIRE);
			}
		}
	}
	void updateFinalDelay() throws UmpireException {
		tick--;
		
		if (tick == 0) {
			umpire.endRound();
		}
	}
	boolean runAI(Player player) {
		try {
			boolean finished = tankAIs.get(player.getID()).run(level);
			return finished;
		}
		catch (AIException e) {
			throw new OopsieException(e);
		}
	}
	void setNextStage(LevelUmpireStage stage) {
		this.stage = stage;
		
		switch (stage) {
			case INITIAL_DELAY_BEFORE_LEVEL_BEGINS: tick = INITIAL_DELAY_TIME; break;
			case MAKING_AI_SEEM_HUMAN: tick = AI_DELAY_TIME; break;
			case WAITING_FOR_PLAYER_TO_FIRE: tick = TURN_TIME; break;
			case WAITING_FOR_LEVEL_TO_RUN_ACTIONS: tick = 0; break;
			case DELAY_BETWEEN_TURNS: tick = TURN_DELAY_TIME; break;
			case DELAY_AFTER_CURRENT_PLAYER_DIES: tick = TURN_DELAY_TIME; break;
			case FINAL_DELAY_BEFORE_SHOPPING: tick = SHOPPING_DELAY_TIME; break;
			default: throw new OopsieException(new Exception("Unknown LevelUmpireStage.")); 
		}
	}
	
	public void playerDied(String playerID) {
		try {
			if (stage == LevelUmpireStage.FINAL_DELAY_BEFORE_SHOPPING) {
				match.getCurrentRound().killPlayer(playerID); 
				return;
			}
			
			String playerBefore = match.getCurrentRound().getCurrentPlayerID();			
			match.getCurrentRound().killPlayer(playerID);			
			
			if (!match.getCurrentRound().isCurrentlySomeonesTurn()) {
				setNextStage(LevelUmpireStage.FINAL_DELAY_BEFORE_SHOPPING);
				return;
			}
			
			String playerAfter = match.getCurrentRound().getCurrentPlayerID();
			if (playerBefore != playerAfter) {
				setNextStage(LevelUmpireStage.DELAY_AFTER_CURRENT_PLAYER_DIES);
				return;
			}
		}
		catch (UmpireException | MissingPlayerException e) { throw new OopsieException(e); }
	}
	public void consumeUtility(String utilityID, Player player, AimData aim) throws UtilityException {
		if (this.stage != LevelUmpireStage.WAITING_FOR_PLAYER_TO_FIRE) { return; }
		
		try {
			Utility utility = UtilityLibrary.CreateUtility(utilityID);
			UtilityAction action = utility.getAction(aim);
			
			if (action.shouldRun(level, level.getTankFromOwner(player.getID()))) {
				player.getTank().getInventory().useUtility(utilityID);
				
				if (action.isEndsTurn()) {
					setNextStage(LevelUmpireStage.WAITING_FOR_LEVEL_TO_RUN_ACTIONS);
				}
				
				level.addAction(action, player.getID());
			}
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
	}
	
	public Player tankControlsPlayer() {
		try {
			if (this.stage == LevelUmpireStage.FINAL_DELAY_BEFORE_SHOPPING) {
				return null;
			}
			
			Player player = this.match.getCurrentRound().getCurrentPlayer();
			if (player.isAI()) {
				return null;
			}
			else {
				return player;
			}
		} 
		catch (UmpireException e) { throw new OopsieException(e); } 
	}
	public boolean tankControlsVisible() {
		if (tankControlsPlayer() == null) { return false; }
		return stage == LevelUmpireStage.WAITING_FOR_PLAYER_TO_FIRE;
	}
	public float tankControlsPercentTurnOver() {
		if (!tankControlsVisible()) { return 0; }
		return Range.Percent(this.tick, TURN_TIME, 0);
	}

	public int getRoundNo() {
		return umpire.getRoundIndex();
	}
	public GameLog getLog() {
		return umpire.getLog();
	}
}
