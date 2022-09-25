package com.danschellekens.dantanks.data;

import java.util.HashMap;

import com.danschellekens.dantanks.data.values.PlayerAILevel;
import com.danschellekens.dantanks.data.values.PlayerColor;
import com.danschellekens.dantanks.exceptions.CostException;

public class Player {
	final String ID;
	
	String name;
	PlayerColor color;
	PlayerAILevel aiLevel;
	
	int money;
	int score;
	int advantagePoints;
	
	Tank tank;
	HashMap<String, String> matchMemory;
	HashMap<String, String> roundMemory;
	
	public Player(Match match, String ID, PlayerSettings settings) {
		this.ID = ID;
		
		this.name = settings.getName();
		this.color = settings.getColor();
		this.aiLevel = settings.getAILevel();
		
		this.money = 0;
		this.score = 0;
		this.advantagePoints = 1;
		
		this.tank = new Tank(this.ID, match);
		matchMemory = new HashMap<String, String>();
		roundMemory = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PlayerColor getColor() {
		return color;
	}
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		if (money < 0) { money = 0; }
		this.money = money;
	}
	public boolean canAfford(int cost) {
		return this.money >= cost;
	}
	public void spendMoney(int cost) throws CostException {
		if (this.money < cost) { throw new CostException(this, cost); }
		this.money -= cost;
	}
	public void addMoney(int money) {
		this.money += money;
	}
	public Tank getTank() {
		return tank;
	}
	public void setTank(Tank tank) {
		this.tank = tank;
	}
	public String getID() {
		return ID;
	}
	public boolean isAI() {
		return getAILevel() != PlayerAILevel.HUMAN;
	}
	public PlayerAILevel getAILevel() {
		return aiLevel;
	}
	public void setAILevel(PlayerAILevel aiLevel) {
		this.aiLevel = aiLevel;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void addScore(int score) {
		this.score += score;
	}
	public int getAdvantagePoints() {
		return advantagePoints;
	}
	public void setAdvantagePoints(int advantagePoints) {
		this.advantagePoints = advantagePoints;
	}
	public void addAdvantagePoints(int advantagePoints) {
		this.advantagePoints += advantagePoints;
	}

	public void rememberForMatch(String key, String value) {
		matchMemory.put(key, value);
	}
	public String recallForMatch(String key, String defaultValue) {
		if (matchMemory.containsKey(key)) {
			return matchMemory.get(key);
		}
		return defaultValue;
	}
	public void forgetForMatch(String key) {
		matchMemory.remove(key);
	}
	public void rememberForRound(String key, String value) {
		roundMemory.put(key, value);
	}
	public String recallForRound(String key, String defaultValue) {
		if (roundMemory.containsKey(key)) {
			return roundMemory.get(key);
		}
		return defaultValue;
	}
	public void forgetForRound(String key) {
		roundMemory.remove(key);
	}
	public void resetRoundMemory() {
		roundMemory.clear();
	}
	
	public PlayerSettings createNew(MatchOptions options) {
		PlayerSettings result = new PlayerSettings(this.getName());
		result.setColor(this.getColor());
		result.setAILevel(this.getAILevel());
		return result;
	}
}
