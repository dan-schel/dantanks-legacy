package com.danschellekens.dantanks.level.tankObj;

import com.danschellekens.dantanks.data.Tank;
import com.danschellekens.dantanks.exceptions.MissingPlayerException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.shieldObj.ShieldObj;
import com.danschellekens.operations.Range;

public class TankObjHealth {
	final Tank tank;
	
	final float maxHealth;
	float health;
	
	ShieldObj shieldAlgorithm;
	
	public TankObjHealth(Tank tank) {
		this.tank = tank;
		
		this.maxHealth = tank.getHealthStatValue();
		this.health = tank.getHealthStatValue();
		
		this.shieldAlgorithm = null;
	}
	
	public float current() {
		return health;
	}
	public float max() {
		return maxHealth;
	}
	public float percentage() {
		return current() / max();
	}
	
	public void heal(float proposedHealing, LevelUmpire umpire, String playerResponsible, String utilityResponsible) {
		if (proposedHealing == 0) { return; }
		
		float maximumAmountOfHealingPossible = this.maxHealth - current();
		float healthAddition = Range.Constrain(proposedHealing, 0, maximumAmountOfHealingPossible);
		health += healthAddition;
		
		try {
			umpire.getLog().logHealing(
				this.tank.getOwner().getID(), 
				playerResponsible, 
				utilityResponsible, 
				healthAddition
			);
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
	}
	public void damage(float proposedDamaging, LevelUmpire umpire, String playerResponsible, String utilityResponsible) {		
		if (proposedDamaging == 0) { return; }
		
		float damageBlockedByShield = 0;
		
		if (hasShieldAlgorithm()) {
			float proposedAmountToBlock = this.shieldAlgorithm.calculateAmountToBlock(proposedDamaging);
			damageBlockedByShield = Range.Constrain(proposedAmountToBlock, 0, proposedDamaging);
			if (this.shieldAlgorithm.isDone()) { this.clearShieldAlgorithm(); }
		}
		
		float maximumAmountOfDamagePossible = current();
		float damageProposed = proposedDamaging - damageBlockedByShield;
		float healthSubtraction = Range.Constrain(damageProposed, 0, maximumAmountOfDamagePossible);
		
		float initialHealth = health;
		health -= healthSubtraction;
		
		try {
			umpire.getLog().logDamage(
				this.tank.getOwner().getID(), 
				playerResponsible, 
				utilityResponsible, 
				initialHealth,
				health,
				damageBlockedByShield
			);
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
	}
	
	public boolean isMax() {
		return this.health > this.maxHealth - 0.001f;
	}
	public boolean isDead() {
		return this.health < 0.001f;
	}
	
	public ShieldObj getShieldAlgorithm() {
		return shieldAlgorithm;
	}
	public boolean hasShieldAlgorithm() {
		return shieldAlgorithm != null;
	}
	public void setShieldAlgorithm(ShieldObj shieldAlgorithm) {
		this.shieldAlgorithm = shieldAlgorithm;
	}
	public void clearShieldAlgorithm() {
		this.shieldAlgorithm = null;
	}
	public float shieldPercentage() {
		if (!hasShieldAlgorithm()) { return 0; }
		else { return shieldAlgorithm.getShieldHealthPercentage(); }
	}
}
