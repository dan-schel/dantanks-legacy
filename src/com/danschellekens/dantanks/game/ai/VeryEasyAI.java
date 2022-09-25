package com.danschellekens.dantanks.game.ai;

import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.aim.*;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.utilities.completelyHidden.AISmallShell;
import com.danschellekens.operations.*;

public class VeryEasyAI extends TankAI {
	float targetHealth;
	String targetID;
	int failedAttemptsCount;
	
	public VeryEasyAI(Player player) {
		super(player);
	}

	@Override
	public boolean run(Level level) throws AIException {
		updateFailedAttemptsCount(level);
		
		Utility utility = chooseUtility(level);
		AimData aim = calculateAim(level, utility);
		
		if (Random.Chance(Range.MapConstrain(failedAttemptsCount, 4, 8, 0, 1))) {
			try {
				TankObj playerTank = level.getTankFromOwner(player.getID());
				
				utility = UtilityLibrary.CreateUtility(AISmallShell.ID);
				aim = new AnglePowerAimData(Random.Float(-5, 5), Random.Float(0, 5), playerTank);
			} 
			catch (UtilityException | MissingPlayerException e) {
				throw new AIException("Running AI for player '" + player.getID() + "' failed. " + e.getMessage());
			}
		}

		return AIHelper.UseUtility(player, level, utility, aim);
	}
	@Override
	public void shop() throws AIException {
		// Nope I don't do that :)
	}
 	
	Utility chooseUtility(Level level) throws AIException {
		try {
			return UtilityLibrary.CreateUtility(AISmallShell.ID);
		}
		catch (UtilityException e) {
			throw new OopsieException(e);
		}
	}	
	AimData calculateAim(Level level, Utility utility) throws AIException {
		if (utility.getAimType() == AimType.ANGLE_POWER) {
			try {
				TankObj playerTank = level.getTankFromOwner(player.getID());
				TankObj target = getRandomTank(playerTank, level);
				
				recordTargetHealth(target, level);
				return calculateAnglePowerAim(playerTank, target, level);
			}
			catch (MissingPlayerException e) {
				throw new OopsieException(e);
			}
		}
		else if (utility.getAimType() == AimType.SELF) {
			forgetTargetHealth();
			return new SelfAimData();
		}
		else {
			throw new AIException("AimType not supported.");
		}
	}
	AnglePowerAimData calculateAnglePowerAim(TankObj playerTank, TankObj target, Level level) {
		Vector2 targetDisplacement = target.hitbox().getPosition().subtract(playerTank.hitbox().getPosition());
		float targetDistance = targetDisplacement.magnitude();
		
		float angleFactor = targetDisplacement.getX();
		float powerFactor = targetDistance - Range.MapConstrain(targetDisplacement.getY(), -200, 200, 50, -50);			
		float angle = Range.MapConstrain(angleFactor, -500, 500, -50, 50);
		float power = Range.MapConstrain(powerFactor, 0, 700, 20, 40);
		
		angle += Random.Float(-10, 10);
		power = Range.Constrain(power + Random.Float(-10, 10), 20, 40);
		
		return new AnglePowerAimData(angle, power, playerTank);
	}
	TankObj getRandomTank(TankObj playerTank, Level level) throws AIException {
		return Random.ArrayElement(level.getTanks());
	}
	void recordTargetHealth(TankObj target, Level level) {
		targetHealth = target.health().current();
		try {
			targetID = target.getTank().getOwner().getID();
		} catch (MissingPlayerException e) {
			throw new OopsieException(e);
		}
	}
	void forgetTargetHealth() {
		targetHealth = 0;
		targetID = "";
		failedAttemptsCount = 0;
	}
	void updateFailedAttemptsCount(Level level) {
		try {
			float newTargetHealth = level.getTankFromOwner(targetID).health().current();
			
			if (newTargetHealth != targetHealth) {
				forgetTargetHealth();
				return;
			}
			else {
				failedAttemptsCount++;
				return;
			}
		} catch (MissingPlayerException e) {
			forgetTargetHealth();
			return;
		}
	}

	@Override
	public void clearRoundMemory() {
		forgetTargetHealth();
	}
}
