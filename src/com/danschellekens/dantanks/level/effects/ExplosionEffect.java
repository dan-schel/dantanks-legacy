package com.danschellekens.dantanks.level.effects;

import java.util.ArrayList;
import java.util.Iterator;

import com.danschellekens.operations.Random;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class ExplosionEffect extends Effect {
	float size;
	
	ArrayList<ExplosionParticle> particles;
	
	public ExplosionEffect(Vector2 position, float size) {
		super(position, EffectLayer.OVER_DECORATIONS);
		this.size = size;
		
		particles = new ArrayList<ExplosionParticle>();
		particles.add(new ExplosionParticle(position, size * 2));
		for (int i = 0; i < size / 5; i++) {
			particles.add(new ExplosionParticle(position.add(new Vector2(0, Random.Float(size)).rotateDegrees(Random.Degrees())), size));
		}
	}
	
	public void update() {
		Iterator<ExplosionParticle> i = particles.iterator();
		while (i.hasNext()) {
			ExplosionParticle p = i.next();
			p.update();
			
			if (p.isDone()) {
				i.remove();
			}
		}
	}
	
	public void render(Artist artist) {
		for (ExplosionParticle p : particles) {
			p.render(artist);
		}
	}
	
	public boolean isDone() {
		return particles.size() == 0;
	}
}
