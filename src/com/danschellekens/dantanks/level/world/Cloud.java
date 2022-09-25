package com.danschellekens.dantanks.level.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.operations.Random;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;

public class Cloud {
	enum Direction {
		LEFT,
		RIGHT
	}
	
	public static final int TEXTURE_WIDTH = 300;
	public static final float MIN_VELOCITY = 0.05f;
	public static final float MAX_VELOCITY = 0.2f;
	public static final float MIN_HEIGHT = 0;
	public static final float MAX_HEIGHT = 200;
	
	
	static boolean graphicsLoaded = false;
	static Image[] cloudGraphics;
	
	Vector2 position;
	Vector2 velocity;
	int graphicNo;
	Direction direction;
	
	public Cloud(boolean startInsideScreen) {
		if (!graphicsLoaded) { loadGraphics(); }
		
		if (Random.Chance()) {
			direction = Direction.LEFT;
			position = new Vector2(Level.LEVEL_WIDTH + TEXTURE_WIDTH / 2, Random.Float(MIN_HEIGHT, MAX_HEIGHT));
			velocity = new Vector2(Random.Float(-MIN_VELOCITY, -MAX_VELOCITY), 0);
		} 
		else {
			direction = Direction.RIGHT;
			position = new Vector2(0 - TEXTURE_WIDTH / 2, Random.Float(MIN_HEIGHT, MAX_HEIGHT));
			velocity = new Vector2(Random.Float(MIN_VELOCITY, MAX_VELOCITY), 0);
		}
		
		if (startInsideScreen) {
			position = new Vector2(Random.Float(0, Level.LEVEL_WIDTH), Random.Float(MIN_HEIGHT, MAX_HEIGHT));
		}
		
		graphicNo = Random.Int(5);
	}
	static void loadGraphics() {
		cloudGraphics = new Image[5];
		try {
			cloudGraphics[0] = new Image("level/clouds/cloud1.png");
			cloudGraphics[1] = new Image("level/clouds/cloud2.png");
			cloudGraphics[2] = new Image("level/clouds/cloud3.png");
			cloudGraphics[3] = new Image("level/clouds/cloud4.png");
			cloudGraphics[4] = new Image("level/clouds/cloud5.png");	
		}
		catch (SlickException ex) {
			throw new OopsieException(ex);
		}
		graphicsLoaded = true;
	}
	
	public void update() {
		position = position.add(velocity);
	}
	
	public void render(Artist artist) {
		artist.push();
		artist.translate(position.getX(), position.getY());
		artist.image().drawCentered(cloudGraphics[graphicNo], 0, 0, new Color(1f, 1f, 1f, 0.5f));
		artist.pop();
	}
	
	public boolean isDead() {
		if (direction == Direction.LEFT) {
			return position.getX() < 0 - TEXTURE_WIDTH / 2;
		}
		else {
			return position.getY() > Level.LEVEL_WIDTH + TEXTURE_WIDTH / 2;
		}
		
	}
}
