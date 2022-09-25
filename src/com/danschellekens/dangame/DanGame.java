package com.danschellekens.dangame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.EmptyImageData;

import com.danschellekens.dangame.settings.GameInformation;
import com.danschellekens.dangame.settings.StartupSettings;
import com.danschellekens.operations.Vector2;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

/**
 * An abstract class for games.
 * @author Dan2002s
 */
public abstract class DanGame extends BasicGame {
	GameInformation gameInformation;
	StartupSettings startupSettings;
	AppGameContainer container;
	DanInput input;
	
	float delta;
	
	/**
	 * Creates a new DanGame.
	 */
	public DanGame(GameInformation gameInformation) {
		super(gameInformation.getGameTitle());
		
		this.gameInformation = gameInformation;
		this.startupSettings = new StartupSettings();
	}

	/**
	 * Loads the game's content and launches the game.
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = (AppGameContainer) container;
		this.input = new DanInput();
		
		if (this.getStartupSettings().isHideSystemCursor()) {
			this.container.setMouseCursor(new EmptyImageData(32, 32), 0, 0);
		}
		
		initGame();
	}

	/**
	 * Allows the components of the game to be updated to do things
	 * such as respond to input and animate.
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		this.delta = delta / 1000f;
		this.input.update(container.getInput());
		
		updateGame();
	}
	
	/**
	 * Allows the game to render things onto the screen.
	 */
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setAntiAlias(true);
		renderGame(new Artist(graphics));
	}
	
	/**
	 * Override this method to load content for your game and create anything
	 * that you need before starting the game loop.
	 */
	protected abstract void initGame() throws SlickException;
	
	/**
	 * Override this method to update the components for the game do to 
	 * things such as respond to input and animate.
	 */
	protected abstract void updateGame() throws SlickException;
	
	/**
	 * Override this method to to render things onto the screen.
	 * @param artist The Artist object with functions for rendering.
	 */
	protected abstract void renderGame(Artist artist) throws SlickException;
	
	/**
	 * Returns information about the game.
	 */
	public GameInformation getGameInformation() {
		return gameInformation;
	}
	
	/**
	 * Returns the settings that should be used to launch the game.
	 */
	public StartupSettings getStartupSettings() {
		return startupSettings;
	}
	
	/**
	 * Returns the game's container.
	 */
	public AppGameContainer getContainer() {
		return container;
	}
	
	/**
	 * Returns the game's DanInput for retrieving input information.
	 */
	public DanInput getInput() {
		return input;
	}
	
	/**
	 * Returns the amount of seconds since the last update.
	 * (Only intended to be used when updating the game!)
	 */
	public float getDelta() {
		return delta;
	}
	
	/**
	 * Returns the current frames per seconds of the game.
	 */
	public float getFPS() {
		return 1 / delta;
	}
	
	/**
	 * Returns the width of the current viewport.
	 */
	public float getWidth() {
		return getContainer().getWidth();
	}
	
	/**
	 * Returns the height of the current viewport.
	 */
	public float getHeight() {
		return getContainer().getHeight();
	}
	
	/** 
	 * Returns the position of the bottom left corner of the game screen.
	 */
	public Vector2 getScreenBottomRight() {
		return new Vector2(getWidth(), getHeight());
	}
	
	/** 
	 * Returns the position of the center of the game screen.
	 */
	public Vector2 getScreenCenter() {
		return getScreenBottomRight().divide(2);
	}
}
