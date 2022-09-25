package com.danschellekens.dantanks.game.log;

public class DamageLogEntry extends LogEntry {
	final String playerDamaged;
	final String playerResponsible;
	final String utilityResponsible;
	
	final float initialHealth;
	final float finalHealth;
	final float damageTaken;
	final float damageBlocked;
	final float damageCaused;
	
	public DamageLogEntry(String playerDamaged, String playerResponsible, String utilityResponsible, 
			float initialHealth, float finalHealth, float damageBlocked) {
		this.playerDamaged = playerDamaged;
		this.playerResponsible = playerResponsible;
		this.utilityResponsible = utilityResponsible;
		
		this.initialHealth = initialHealth;
		this.finalHealth = finalHealth;
		this.damageTaken = initialHealth - finalHealth;
		this.damageBlocked = damageBlocked;
		this.damageCaused = this.damageTaken + this.damageBlocked;
	}

	public String getPlayerDamaged() {
		return playerDamaged;
	}
	public String getPlayerResponsible() {
		return playerResponsible;
	}
	public String getUtilityResponsible() {
		return utilityResponsible;
	}

	public float getInitialHealth() {
		return initialHealth;
	}
	public float getFinalHealth() {
		return finalHealth;
	}
	public float getDamageTaken() {
		return damageTaken;
	}
	public float getDamageBlocked() {
		return damageBlocked;
	}
	public float getDamageCaused() {
		return damageCaused;
	}
}
