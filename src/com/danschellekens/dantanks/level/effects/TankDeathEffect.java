package com.danschellekens.dantanks.level.effects;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.*;

import com.danschellekens.dantanks.data.values.PlayerColor;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class TankDeathEffect extends Effect {
	static boolean graphicsLoaded = false;
	static Image deathMarkImage;
	static Image[] tankBitsColored;
	static Image[] tankBitsNormal;
	
	int time;
	Vector2 deathMarkPosition;
	float deathMarkOpacity;
	ArrayList<TankBitsParticle> particles;
	
	public TankDeathEffect(Vector2 position, PlayerColor color) {
		super(position, EffectLayer.OVER_DECORATIONS);
		
		loadGraphics();
		
		this.deathMarkPosition = position.add(new Vector2(0, -40));
		this.deathMarkOpacity = 0f;
		
		this.particles = new ArrayList<TankBitsParticle>();
		this.particles.add(new TankBitsParticle(position, tankBitsColored[0], color, true));
		this.particles.add(new TankBitsParticle(position, tankBitsNormal[0], color, false));
	}
	static void loadGraphics() {
		if (!graphicsLoaded) {
			try {
				deathMarkImage = new Image("level/deathMark.png");
				tankBitsColored = new Image[] { new Image("level/tank/color.png") };
				tankBitsNormal = new Image[] { new Image("level/tank/base.png") };
			}
			catch (SlickException e) {
				throw new OopsieException(e);
			}
			
			graphicsLoaded = true;
		}
	}
	
	public void update() {
		time ++;
		
		final float desiredOpacity = 0.5f;
		
		if (time <= 10) {
			deathMarkOpacity = time * 0.1f * desiredOpacity;
		}
		else if (time > 60) {
			deathMarkOpacity = (100 - time) * 0.025f * desiredOpacity;
		}
		else {
			deathMarkOpacity = desiredOpacity;
		}
		
		Iterator<TankBitsParticle> i = particles.iterator();
		while (i.hasNext()) {
			TankBitsParticle p = i.next();
			p.update();
			
			if (p.isDone()) {
				i.remove();
			}
		}
		
		deathMarkPosition = deathMarkPosition.add(new Vector2(0, -0.5f));
	}
	
	public void render(Artist artist) {
		artist.image().drawCentered(deathMarkImage, deathMarkPosition.getX(), deathMarkPosition.getY(), new Color(1f, 1f, 1f, deathMarkOpacity));
		
		for (TankBitsParticle p : particles) {
			p.render(artist);
		}
	}
	
	public boolean isDone() {
		return time > 100;
	}
}
