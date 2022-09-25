package com.danschellekens.dangame.settings;

/**
 * Stores settings used when starting a game.
 * @author Dan2002s
 */
public class StartupSettings {

	int width;
	int height;
	boolean vsync;
	boolean showFPS;
	boolean defaultLoggingEnabled;
	boolean hideSystemCursor;
	String iconDirectory;
	
	/**
	 * Creates a new StartupSettings.
	 */
	public StartupSettings() {
		this.width = 800;
		this.height = 450;
		this.vsync = true;
		this.showFPS = false;
		this.defaultLoggingEnabled = false;
		this.hideSystemCursor = false;
		this.iconDirectory = null;
	}
	
	/**
	 * Creates a new StartupSettings with a set width and height.
	 * @param width The width of the window.
	 * @param height The height of the window.
	 */
	public static StartupSettings CreateSettings(int width, int height) {
		StartupSettings settings = new StartupSettings();
		settings.setWidth(width);
		settings.setHeight(height);
		return settings;
	}

	/**
	 * Returns the desired width of the game.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the desired width of the game.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the desired height of the game.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the desired height of the game.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns true if Vsync should be enabled.
	 */
	public boolean isVsync() {
		return vsync;
	}

	/**
	 * Sets whether Vsync should be enabled or not.
	 */
	public void setVsync(boolean vsync) {
		this.vsync = vsync;
	}

	/**
	 * Returns true if FPS should be shown.
	 */
	public boolean isShowFPS() {
		return showFPS;
	}

	/**
	 * Sets whether FPS should be shown or not.
	 */
	public void setShowFPS(boolean showFPS) {
		this.showFPS = showFPS;
	}

	/**
	 * Returns true if Slick's default logging should be turned on.
	 */
	public boolean isDefaultLoggingEnabled() {
		return defaultLoggingEnabled;
	}

	/**
	 * Sets whether Slick's default logging should be turned on or not.
	 */
	public void setDefaultLoggingEnabled(boolean defaultLoggingEnabled) {
		this.defaultLoggingEnabled = defaultLoggingEnabled;
	}

	/**
	 * Returns true if the system cursor should be hidden.
	 * Allows the game to have a custom cursor.
	 */
	public boolean isHideSystemCursor() {
		return hideSystemCursor;
	}

	/**
	 * Sets whether the system cursor should be hidden.
	 * Allows the game to have a custom cursor.
	 */
	public void setHideSystemCursor(boolean hideSystemCursor) {
		this.hideSystemCursor = hideSystemCursor;
	}

	/**
	 * Returns the directory of the game's icons.
	 */
	public String getIconDirectory() {
		return iconDirectory;
	}

	/**
	 * Set the directory of the game's icons.
	 */
	public void setIconDirectory(String iconDirectory) {
		this.iconDirectory = iconDirectory;
	}
}
