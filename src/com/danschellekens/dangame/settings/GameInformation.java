package com.danschellekens.dangame.settings;

import com.danschellekens.operations.Version;

/**
 * Stores information about a game.
 * @author Dan2002s
 */
public class GameInformation {

	String fullName;
	String shortName;
	
	Version version;
	
	/**
	 * Creates a new GameInformation.
	 */
	public GameInformation() {
		this("New DanGame");
	}
	
	/**
	 * Creates a new GameInformation.
	 */
	public GameInformation(String name) {
		this(name, name);
	}
	
	/**
	 * Creates a new GameInformation.
	 */
	public GameInformation(String fullName, String shortName) {
		this(fullName, shortName, new Version());
	}
	
	/**
	 * Creates a new GameInformation.
	 */
	public GameInformation(String name, Version version) {
		this(name, name, version);
	}
	
	/**
	 * Creates a new GameInformation.
	 */
	public GameInformation(String fullName, String shortName, Version version) {
		this.fullName = fullName;
		this.shortName = shortName;
		this.version = version;
	}

	/**
	 * Returns the full name of the game.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name of the game.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Returns the shorter version of the name of the game.
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Sets the shorter version of the name of the game.
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Returns the version of the game.
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * Sets the version of the game.
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
	
	public String getGameTitle() {
		return this.getShortName() + " " + this.getVersion().toString();
	}
}
