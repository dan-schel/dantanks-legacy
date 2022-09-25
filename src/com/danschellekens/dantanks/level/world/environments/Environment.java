package com.danschellekens.dantanks.level.world.environments;

import java.util.ArrayList;
import java.util.Iterator;

import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.world.*;
import com.danschellekens.dantanks.level.world.ground.Ground;
import com.danschellekens.dantanks.level.world.ground.GroundRenderer;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public abstract class Environment {
	protected final Level level;
	protected final LevelUmpire umpire;
	protected final LevelBackground background;
	protected final Ground ground;
	protected final ArrayList<Decoration> backgroundDecorations;
	protected final ArrayList<Decoration> foregroundDecorations;
	protected final float windConstant;
	protected final float gravityConstant;
	
	public Environment(Level level, LevelUmpire umpire) {
		this.level = level;
		this.umpire = umpire;
		
		this.background = createBackground();
		this.ground = createGround(level, umpire);
		this.backgroundDecorations = createBackgroundDecorations(ground);
		this.foregroundDecorations = createForegroundDecorations(ground);
		this.windConstant = createWindConstant();
		this.gravityConstant = createGravityConstant();
	}
	abstract LevelBackground createBackground();
	abstract Ground createGround(Level level, LevelUmpire umpire);
	abstract ArrayList<Decoration> createBackgroundDecorations(Ground ground);
	abstract ArrayList<Decoration> createForegroundDecorations(Ground ground);
	abstract float createWindConstant();
	abstract float createGravityConstant();
	
	public void update() {
		background.update();
	}
	public void renderBehindTanks(Artist artist) {
		background.render(artist);
		GroundRenderer.RenderGraphics(artist, ground);
		
		for (Decoration d : backgroundDecorations) {
			d.render(artist);
		}
	}
	public void renderOverTanks(Artist artist) {
		for (Decoration d : foregroundDecorations) {
			d.render(artist);
		}
	}
	public void renderHitboxes(Artist artist) {
		GroundRenderer.RenderHitboxes(artist, ground);
		for (Decoration d : backgroundDecorations) {
			d.renderHitboxes(artist);
		}
		for (Decoration d : foregroundDecorations) {
			d.renderHitboxes(artist);
		}
	}

	public LevelBackground getBackground() {
		return background;
	}
	public Ground getGround() {
		return ground;
	}

	public void explode(Vector2 position, float size, float strength) {
		ground.explode(position, size, strength);
		destroyDecorations(position, size + 15);
	}
	public void destroyDecorations(Vector2 position, float destroyRadius) {
		// decorations are destroyed if ground beneath changes level or is within explosion radius.
		
		Iterator<Decoration> itrF = foregroundDecorations.iterator();
		while (itrF.hasNext()) {
			Decoration d = itrF.next();
			if (Math.abs(d.getX() - position.getX()) <= destroyRadius || 
					d.getGroundHeight() < ground.getHeightOfX(d.getX()) - 0.01f) {
				itrF.remove();
				
				level.addEffect(d.getDeathEffect());
			}
		}
		
		Iterator<Decoration> itrB = backgroundDecorations.iterator();
		while (itrB.hasNext()) {
			Decoration d = itrB.next();
			if (Math.abs(d.getX() - position.getX()) <= destroyRadius ||
					d.getGroundHeight() < ground.getHeightOfX(d.getX()) - 0.01f) {
				itrB.remove();
				
				level.addEffect(d.getDeathEffect());
			}
		}
	}
	public float getWindConstant() {
		return windConstant;
	}
	public float getGravityConstant() {
		return gravityConstant;
	}
}
