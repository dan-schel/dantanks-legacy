package com.danschellekens.dantanks.data;

import com.danschellekens.dantanks.data.values.PlayerAILevel;
import com.danschellekens.dantanks.data.values.PlayerColor;

public class PlayerSettings {
	String name;
	PlayerColor color;
	PlayerAILevel aiLevel;
	
	public PlayerSettings(String name) {
		this(name, PlayerColor.RandomColor(), PlayerAILevel.HUMAN);
	}
	public PlayerSettings(String name, PlayerColor color, PlayerAILevel aiLevel) {
		this.name = name;
		this.color = color;
		this.aiLevel = aiLevel;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String generateID(int playerIndex) {
		return String.format("%02d", playerIndex) + getName();
	}
	
	public PlayerColor getColor() {
		return color;
	}
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	public PlayerAILevel getAILevel() {
		return aiLevel;
	}
	public boolean isAI() {
		return getAILevel() != PlayerAILevel.HUMAN;
	}
	public void setAILevel(PlayerAILevel aiLevel) {
		this.aiLevel = aiLevel;
	}
}
