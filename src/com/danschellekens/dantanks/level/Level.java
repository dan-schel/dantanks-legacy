package com.danschellekens.dantanks.level;

import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dantanks.DanTanks;
import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.actions.UtilityAction;
import com.danschellekens.dantanks.level.effects.*;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.level.world.environments.*;
import com.danschellekens.dantanks.level.world.ground.Ground;
import com.danschellekens.dantanks.ui.world.TankIndicator;
import com.danschellekens.operations.*;
import com.danschellekens.operations.Random;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class Level {
	public static final float LEVEL_WIDTH = 1200;
	public static final float LEVEL_HEIGHT = 800;
	
	final UUID uuid;
	final Round round;
	final LevelUmpire umpire;
	final Environment environment;
	
	final ArrayList<TankObj> tanks;
	final ArrayList<UtilityAction> runningActions;
	final ArrayList<TankIndicator> indicators;
	final Image turnIndicatorGraphic;
	final ArrayList<Effect> visibleEffects;
	
	Vector2 currentTankPos;
	float scrollPos;
	float scrollPosSmoothened;
	float turnIndicatorBreathing;
	
	public Level(Round round, LevelUmpire umpire) {
		this.uuid = UUID.randomUUID();
		this.umpire = umpire;
		this.round = round;
		this.environment = chooseEnvironment();
		
		this.tanks = new ArrayList<TankObj>();
		this.runningActions = new ArrayList<UtilityAction>();
		this.indicators = new ArrayList<TankIndicator>();
		this.visibleEffects = new ArrayList<Effect>();
		
		scrollPos = 1000;
		scrollPosSmoothened = 1000;
		
		try { turnIndicatorGraphic = new Image("level/turnMarker.png"); } 
		catch (SlickException e) { throw new OopsieException(e); }
		
		createTanks();
	}
	Environment chooseEnvironment() {
		int choice = Random.Int(3);
		
		if (choice == 0) {
			return new SunnyHillsOfParadise(this, umpire);
		}
		else if (choice == 1) {
			return new Desert(this, umpire);
		}
		else {
			return new SnowyMountains(this, umpire);
		}
	}
	void createTanks() {
		Player[] players = round.getPlayersAlive();
		Object[] shuffledPlayers = Random.Shuffle(players);
		
		float edgeUsage = Range.Map(round.countPlayersAlive(), Match.MIN_PLAYERS, Match.MAX_PLAYERS, 300, 100);
		
		for (int i = 0; i < shuffledPlayers.length; i++) {
			float x = Range.Map(i, 0, shuffledPlayers.length - 1, edgeUsage, LEVEL_WIDTH - edgeUsage);
			x += Random.Float(-50, 50);
			Vector2 position = new Vector2(x, 100);
			
			Player player = (Player) shuffledPlayers[i];
			TankObj tankObj = new TankObj(player.getTank(), this, position);
			
			tankObj.settle();
			
			this.tanks.add(tankObj);
			
			TankIndicator indicator = new TankIndicator(null, tankObj, this, round);
			indicators.add(indicator);
		}
	}
	
	public void update(DanInput input, boolean paused) {
		if (scrollPos < DanTanks.GAME.getHeight() - Level.LEVEL_HEIGHT) { scrollPos = DanTanks.GAME.getHeight() - Level.LEVEL_HEIGHT; }
		scrollPosSmoothened = Increment.Exponential(scrollPosSmoothened, scrollPos, 0.1f);
		
		for (TankIndicator t : indicators) {
			t.update(input);
			t.setLevelPaused(paused);
		}
	}
	public void updatePlaying() {
		turnIndicatorBreathing += 0.12f;
		
		environment.update();
		
		Iterator<TankObj> ti = tanks.iterator();
		while (ti.hasNext()) {
			TankObj t = ti.next();
			t.update(umpire);
			
			if (t.health().isDead()) {
				try {
					umpire.getLog().logDeath(t.getTank().getOwner().getID());
					umpire.playerDied(t.getTank().getOwner().getID());
					
					TankDeathEffect effect = new TankDeathEffect(t.hitbox().getHitboxCenter(), t.getTank().getOwner().getColor());
					this.addEffect(effect);
					
					ti.remove();
				}
				catch (MissingPlayerException e) { throw new OopsieException(e); }
			}
		}
		
		Iterator<UtilityAction> iu = runningActions.iterator();
		while (iu.hasNext()) {
			UtilityAction a = iu.next();
			a.update();
			if (a.isCompletelyDone()) {
				iu.remove();
			}
		}
		
		Iterator<Effect> ie = visibleEffects.iterator();
		while (ie.hasNext()) {
			Effect e = ie.next();
			e.update();
			if (e.isDone()) { ie.remove(); }
		}
		
		updateCameraPosition();
	}
	void updateCameraPosition() {
		if (round.isCurrentlySomeonesTurn()) {
			try {
				Player player = round.getCurrentPlayer();
				TankObj tankobj = getTankFromOwner(player.getID());
				currentTankPos = tankobj.hitbox().getPosition();
				scrollPos = Numbers.Round(-tankobj.hitbox().getPosition().getY() + 400);
			}
			catch (UmpireException e) { throw new OopsieException(e); }
			catch (MissingPlayerException e) { throw new OopsieException(e); }
		}
		else {
			currentTankPos = null;
		}
	}
	
	public void render(Artist artist) {
		artist.push();
		artist.translate(0, scrollPosSmoothened);
		
		environment.renderBehindTanks(artist);
			
		for (TankObj t : tanks) {
			t.renderGraphics(artist);
		}
		for (UtilityAction a : runningActions) {
			a.renderGraphics(artist);
		}
		
		for (Effect e : visibleEffects) {
			if (e.getLayer() == EffectLayer.OVER_TANKS) {
				e.render(artist);
			}
		}
		
		environment.renderOverTanks(artist);
		
		for (Effect e : visibleEffects) {
			if (e.getLayer() == EffectLayer.OVER_DECORATIONS) {
				e.render(artist);
			}
		}
		
		if (DanTanks.GAME.getInput().isKeyDown(Input.KEY_LALT)) {
			environment.renderHitboxes(artist);
			
			for (TankObj t : tanks) {
				t.renderHitboxes(artist);
			}
			for (UtilityAction a : runningActions) {
				a.renderHitboxes(artist);
			}
		}
	
		if (currentTankPos != null) {
			float sine = (float) Math.sin(turnIndicatorBreathing);
			float scale = Range.Map(sine, -1f, 1f, 0.9f, 1.05f);
			artist.image().drawCentered(turnIndicatorGraphic, currentTankPos.getX(), currentTankPos.getY() - 8, scale);
		}
		
		for (TankIndicator t : indicators) {
			t.render(artist);
		}
		
		artist.pop();
	}
	
	public void addAction(UtilityAction action, String userPlayerID) throws MissingPlayerException {
		action.run(this, umpire, getTankFromOwner(userPlayerID));
		runningActions.add(action);
	}
	public void addEffect(Effect effect) {
		visibleEffects.add(effect);
	}
	
	public void damagePlayers(Vector2 position, float damageRadiusSize, float maxDamage, String playerResponsible, String utilityResponsible) {
		for (TankObj t : tanks) {
			Rectangle r = t.hitbox().getHitbox();
			
			if (r.contains(position.getX(), position.getY())) {
				t.health().damage(maxDamage, umpire, playerResponsible, utilityResponsible);
				continue;
			}
			
			float nearestXOnRect = Range.Constrain(position.getX(), r.getX(), r.getX() + r.getWidth());
			float nearestYOnRect = Range.Constrain(position.getY(), r.getY(), r.getY() + r.getHeight());
			float distance = position.subtract(new Vector2(nearestXOnRect, nearestYOnRect)).magnitude();
			float damageToDeal = Range.MapConstrain(distance, 0, damageRadiusSize, maxDamage, 0);
			t.health().damage(damageToDeal, umpire, playerResponsible, utilityResponsible);
		}
	}
	
	public Round getRound() {
		return round;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
	public Ground getGround() {
		return getEnvironment().getGround();
	}
	
	public TankObj[] getTanks() {
		TankObj[] result = new TankObj[tanks.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = tanks.get(i);
		}
		return result;
	}
	public TankObj getTankFromOwner(String playerID) throws MissingPlayerException {
		for (TankObj tank : tanks) {
			if (tank.getTank().getOwner().getID() == playerID) {
				return tank;
			}
		}
		
		throw new MissingPlayerException("Cannot get tank game-object from the level for player '" + playerID + "'.");
	}
	public boolean isCurrentlyRunningActions() {
		for (UtilityAction a : runningActions) {
			if (!a.isTurnDone()) {
				return true;
			}
		}
		
		return false;
	}
	public UUID getUuid() {
		return uuid;
	}
	public float getScrollPosSmoothened() {
		return scrollPosSmoothened;
	}
}
