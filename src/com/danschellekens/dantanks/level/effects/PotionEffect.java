package com.danschellekens.dantanks.level.effects;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Color;

import com.danschellekens.operations.Random;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class PotionEffect extends Effect {
	ArrayList<PotionParticle> particles;
	
	public PotionEffect(Vector2 position, Color color) {
		super(position, EffectLayer.OVER_TANKS);
		
		particles = new ArrayList<PotionParticle>();
		particles.add(new PotionParticle(position, 40, color));
		for (int i = 0; i < 8; i++) {
			particles.add(new PotionParticle(position.add(new Vector2(0, Random.Float(25)).rotateDegrees(Random.Degrees())), Random.Float(10, 25), color));
		}
	}
	
	public void update() {
		Iterator<PotionParticle> i = particles.iterator();
		while (i.hasNext()) {
			PotionParticle p = i.next();
			p.update();
			
			if (p.isDone()) {
				i.remove();
			}
		}
	}
	
	public void render(Artist artist) {
		for (PotionParticle p : particles) {
			p.render(artist);
		}
	}
	
	public boolean isDone() {
		return particles.size() == 0;
	}
}
