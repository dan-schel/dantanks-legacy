package com.danschellekens.dantanks.level.world.environments;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.effects.LeavesEffect;
import com.danschellekens.dantanks.level.world.*;
import com.danschellekens.dantanks.level.world.ground.*;
import com.danschellekens.operations.Random;
import com.danschellekens.operations.Vector2;

public class SnowyMountains extends Environment {
	static final Image[] treeImages = loadTreeImages();
	static Image[] loadTreeImages() {
		try {
			return new Image[] { 
				new Image("level/decoration/snowyTree1.png"),
				new Image("level/decoration/snowyTree2.png"),
				new Image("level/decoration/snowyTree3.png")
			};
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	public SnowyMountains(Level level, LevelUmpire umpire) {
		super(level, umpire);
	}

	@Override
	LevelBackground createBackground() {
		SunnySky sky = new SunnySky();
		sky.useSnowyImage();
		return sky;
	}

	@Override
	Ground createGround(Level level, LevelUmpire umpire) {
		Layer[] layers = createGroundLayers();
		NodeGenerator nodes = new NoiseNodeGenerator(450, 550, 0.2f, Random.Int(Integer.MAX_VALUE));
		return new Ground(level, umpire, layers, nodes);
	}
	Layer[] createGroundLayers() {
Layer[] layers = new Layer[3];
		
		layers[0] = new Layer(
				new FlatNodeGenerator(0), 
				1, 
				new Color(240, 240, 240), 
				new Color(220, 220, 220)
		);
		layers[1] = new Layer(
				new NoiseNodeGenerator(570, 650, 0.4f, Random.Int(Integer.MAX_VALUE)), 
				0.2f, 
				new Color(171, 201, 211), 
				new Color(148, 186, 201)
		);
		layers[2] = new Layer(
				new NoiseNodeGenerator(700, 750, 1f, Random.Int(Integer.MAX_VALUE)),  
				0f, 
				new Color(120, 120, 120), 
				new Color(100, 100, 100)
		);
		
		return layers;
	}

	@Override
	ArrayList<Decoration> createBackgroundDecorations(Ground ground) {
		ArrayList<Decoration> list = new ArrayList<Decoration>();
		
		float left = Random.Float(0, 20);
		float right = Random.Float(Level.LEVEL_WIDTH - 0, Level.LEVEL_WIDTH - 20);
		for (float x = left; x < right; x += Random.Float(30, 80)) {
			float groundHeight = ground.getHeightOfX(x);
			float y = groundHeight - Random.Float(5, 15);
			int imgIndex = Random.Int(treeImages.length);
			
			list.add(new Decoration(new Rectangle(-5, -20, 10, 20), new Vector2(x, y), treeImages[imgIndex], groundHeight, new LeavesEffect(new Vector2(x, y), new Color(221, 221, 221), true)));
		}
		
		return list;
	}

	@Override
	ArrayList<Decoration> createForegroundDecorations(Ground ground) {
		ArrayList<Decoration> list = new ArrayList<Decoration>();
		
		float left = Random.Float(20, 80);
		float right = Random.Float(Level.LEVEL_WIDTH - 20, Level.LEVEL_WIDTH - 80);
		for (float x = left; x < right; x += Random.Float(30, 80)) {
			float groundHeight = ground.getHeightOfX(x);
			float y = groundHeight + Random.Float(12, 18);
			int imgIndex = Random.Int(treeImages.length);
			
			list.add(new Decoration(new Rectangle(-5, -20, 10, 20), new Vector2(x, y), treeImages[imgIndex], groundHeight, new LeavesEffect(new Vector2(x, y), new Color(221, 221, 221), true)));
		}
		
		return list;
	}

	@Override
	float createWindConstant() {
		return 0.0f;
	}
	@Override
	float createGravityConstant() {
		return 0.1f;
	}
}
