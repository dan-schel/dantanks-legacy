package com.danschellekens.dantanks.game.log;

public class HealedLogEntry extends LogEntry {
	final String playerHealed;
	final String playerResponsible;
	final String utilityResponsible;
	final float damageHealed;
	
	public HealedLogEntry(String playerHealed, String playerResponsible, String utilityResponsible, float damageHealed) {
		this.playerHealed = playerHealed;
		this.playerResponsible = playerResponsible;
		this.utilityResponsible = utilityResponsible;
		this.damageHealed = damageHealed;
	}

	public String getPlayerHealed() {
		return playerHealed;
	}
	public String getPlayerResponsible() {
		return playerResponsible;
	}
	public String getUtilityResponsible() {
		return utilityResponsible;
	}
	public float getDamageHealed() {
		return damageHealed;
	}
}
