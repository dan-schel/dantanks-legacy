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

public class SunnyHillsOfParadise extends Environment {
	static final Image[] treeImages = loadTreeImages();
	static Image[] loadTreeImages() {
		try {
			return new Image[] { 
				new Image("level/decoration/tree1.png"),
				new Image("level/decoration/tree2.png"),
				new Image("level/decoration/tree3.png")
			};
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	public SunnyHillsOfParadise(Level level, LevelUmpire umpire) {
		super(level, umpire);
	}

	@Override
	LevelBackground createBackground() {
		return new SunnySky();
	}

	@Override
	Ground createGround(Level level, LevelUmpire umpire) {
		Layer[] layers = createGroundLayers();
		NodeGenerator nodes = new CopyNodeGenerator(layers[1].getVisualNodes(), -10);
		return new Ground(level, umpire, layers, nodes);
	}
	Layer[] createGroundLayers() {
		Layer[] layers = new Layer[4];
		
		layers[0] = new Layer(
				new FlatNodeGenerator(0), 
				1, 
				new Color(100, 150, 0), 
				new Color(80, 130, 0)
		);
		layers[1] = new Layer(
				new NoiseNodeGenerator(300, 500, 0.2f, Random.Int(Integer.MAX_VALUE)),
				0.8f, 
				new Color(95, 75, 50), 
				new Color(80, 60, 40)
		);
		layers[2] = new Layer(
				new NoiseNodeGenerator(450, 550, 0.6f, Random.Int(Integer.MAX_VALUE)), 
				0.2f, 
				new Color(120, 120, 120), 
				new Color(100, 100, 100)
		);
		layers[3] = new Layer(
				new NoiseNodeGenerator(700, 750, 1f, Random.Int(Integer.MAX_VALUE)),  
				0f, 
				new Color(60, 60, 60), 
				new Color(40, 40, 40)
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
			
			list.add(new Decoration(new Rectangle(-5, -20, 10, 20), new Vector2(x, y), treeImages[imgIndex], groundHeight, new LeavesEffect(new Vector2(x, y), new Color(70, 113, 0), true)));
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
			
			list.add(new Decoration(new Rectangle(-5, -20, 10, 20), new Vector2(x, y), treeImages[imgIndex], groundHeight, new LeavesEffect(new Vector2(x, y), new Color(70, 113, 0), true)));
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
