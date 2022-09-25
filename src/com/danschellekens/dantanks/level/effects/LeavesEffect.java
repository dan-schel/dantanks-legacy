package com.danschellekens.dantanks.level.effects;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Color;

import com.danschellekens.operations.Random;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class LeavesEffect extends Effect {
	ArrayList<LeafParticle> particles;
	
	public LeavesEffect(Vector2 position, Color color, boolean sway) {
		super(position, EffectLayer.OVER_TANKS);
		
		particles = new ArrayList<LeafParticle>();
		for (int i = 0; i < 6; i++) {
			particles.add(new LeafParticle(position.add(new Vector2(Random.Float(-5, 5), Random.Float(0, -30))), Random.Float(5, 10), color, sway));
		}
	}
	
	public void update() {
		Iterator<LeafParticle> i = particles.iterator();
		while (i.hasNext()) {
			LeafParticle p = i.next();
			p.update();
			
			if (p.isDone()) {
				i.remove();
			}
		}
	}
	
	public void render(Artist artist) {
		for (LeafParticle p : particles) {
			p.render(artist);
		}
	}
	
	public boolean isDone() {
		return particles.size() == 0;
	}
}
