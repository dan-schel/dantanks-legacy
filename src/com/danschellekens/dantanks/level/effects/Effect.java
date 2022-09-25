package com.danschellekens.dantanks.level.effects;

import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public abstract class Effect {
	protected final Vector2 position;
	protected final EffectLayer layer;
	
	public Effect(Vector2 position, EffectLayer layer) {
		this.position = position;
		this.layer = layer;
	}
	
	public abstract void update();
	public abstract void render(Artist artist);
	public abstract boolean isDone();
	
	public Vector2 getPosition() {
		return position;
	}
	public EffectLayer getLayer() {
		return layer;
	}
}
