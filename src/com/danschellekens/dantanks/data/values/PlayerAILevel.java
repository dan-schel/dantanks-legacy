package com.danschellekens.dantanks.data.values;

public enum PlayerAILevel {
	HUMAN("Human Player", "Gives you the controls."),
	VERY_EASY("Easy AI", "Usually aims in your vicinity."),
	EASY("Medium AI", "Hasn't been told about volcano bombs yet."),
	MEDIUM("\"Hard\" AI", "Smarter than your average door knob.");
	
	String displayName;
	String description;
	PlayerAILevel(String displayName, String description) {
		this.displayName = displayName;
		this.description = description;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getDescription() {
		return description;
	}
}
