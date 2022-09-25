package com.danschellekens.dantanks.level.world;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Transform;

import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.operations.Random;
import com.danschellekens.slick2d.graphics.Artist;

public class SunnySky extends LevelBackground {
	public static final int INITIAL_CLOUDS_MIN = 1;
	public static final int INITIAL_CLOUDS_MAX = 3;
	public static final int NEW_CLOUD_TIME_MIN = 60 * 40;
	public static final int NEW_CLOUD_TIME_MAX = 60 * 60;
	
	static boolean graphicsLoaded = false;
	static Image skyGraphic;
	static Image hillsGraphic;
	static Image hillsDesertGraphic;
	static Image hillsSnowyGraphic;
	
	ArrayList<Cloud> clouds;
	int cloudSpawnTick = 0;
	Image hillsImage;
	
	public SunnySky() {
		if (!graphicsLoaded) {
			loadGraphics();
		}
		
		hillsImage = hillsGraphic;
		
		clouds = new ArrayList<Cloud>();
		int cloudsAmt = Random.Int(INITIAL_CLOUDS_MIN, INITIAL_CLOUDS_MAX);
		for (int i = 0; i < cloudsAmt; i++) {
			Cloud cloud = new Cloud(true);
			clouds.add(cloud);	
		}
	}
	static void loadGraphics() {
		try { 
			skyGraphic = new Image("level/sky.png");
			hillsGraphic = new Image("level/hills.png");
			hillsDesertGraphic = new Image("level/hillsDesert.png");
			hillsSnowyGraphic = new Image("level/hillsSnowy.png");
			
			graphicsLoaded = true;
		} 
		catch (SlickException e) { throw new OopsieException(e); }
	}
	
	public void update() {
		cloudSpawnTick--;
		if (cloudSpawnTick <= 0) { 
			clouds.add(new Cloud(false));
			cloudSpawnTick = Random.Int(NEW_CLOUD_TIME_MIN, NEW_CLOUD_TIME_MAX);
		}
		
		Iterator<Cloud> i = clouds.iterator();
		while (i.hasNext()) {
			Cloud cloud = i.next();
			cloud.update();
			
			if (cloud.isDead()) {
				i.remove();
			}
		}
	}
	
	public void render(Artist artist) {
		artist.setTransform(new Transform());
		artist.image().draw(skyGraphic);
		
		for (Cloud cloud : clouds) {
			cloud.render(artist);
		}
		
		artist.image().draw(hillsImage);
		
		artist.pop();
	}
	
	public void useDesertImage() {
		this.hillsImage = hillsDesertGraphic;
	}
	public void useSnowyImage() {
		this.hillsImage = hillsSnowyGraphic;
	}
}
