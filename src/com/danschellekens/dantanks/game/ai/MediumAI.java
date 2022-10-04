package com.danschellekens.dantanks.game.ai;

import com.danschellekens.dantanks.data.*;
import com.danschellekens.dantanks.data.aim.*;
import com.danschellekens.dantanks.exceptions.*;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.tankObj.TankObj;
import com.danschellekens.dantanks.utilities.completelyHidden.*;
import com.danschellekens.dantanks.utilities.misc.RepairKit;
import com.danschellekens.dantanks.utilities.weapons.TripleShell;
import com.danschellekens.dantanks.utilities.weapons.VolcanoBomb;
import com.danschellekens.operations.*;

public class MediumAI extends TankAI {
	float targetHealth;
	String targetID;
	int failedAttemptsCount;
	
	public MediumAI(Player player) {
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
		int timesToBuy = 3;
		
		try {
			TankInventory utilities = player.getTank().getInventory();
			if (utilities.getUtilityQuantity(RepairKit.ID) < 3) {
				AIHelper.BuyIfCan(player, RepairKit.ID);
			}
			
			for (int i = 0; i < timesToBuy; i++) {
				boolean success1 = AIHelper.BuyIfCan(player, VolcanoBomb.ID);
				boolean success2 = AIHelper.BuyIfCan(player, TripleShell.ID);
				if (!success1 && !success2) { break; }
			}
		}
		catch (UtilityException e) {
			throw new OopsieException(e);
		}
	}
 	
	Utility chooseUtility(Level level) throws AIException {
		try {
			TankInventory utilities = player.getTank().getInventory();
			TankObj playerTank = level.getTankFromOwner(player.getID());
			
			DrivingAdvice advice = shouldDrive(playerTank, level);
			if (advice == DrivingAdvice.DRIVE_LEFT) {
				return UtilityLibrary.CreateUtility(DriveLeft.ID);
			}
			else if (advice == DrivingAdvice.DRIVE_RIGHT) {
				return UtilityLibrary.CreateUtility(DriveRight.ID);
			}
			
			if (!playerTank.health().isMax() && utilities.canUseUtility(RepairKit.ID) && 
					Random.Chance(0.5f)) {
				return UtilityLibrary.CreateUtility(RepairKit.ID);
			}
			
			return AIHelper.ChooseRandomUtility(new String[]{
					VolcanoBomb.ID,
					TripleShell.ID
			}, utilities, AISmallShell.ID);
		}
		catch (UtilityException | MissingPlayerException e) {
			throw new OopsieException(e);
		}
	}	
	AimData calculateAim(Level level, Utility utility) throws AIException {
		if (utility.getAimType() == AimType.ANGLE_POWER) {
			try {
				TankObj playerTank = level.getTankFromOwner(player.getID());
				TankObj target = getClosestTank(playerTank, level);
				
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
		
		angle += Random.Float(-5, 5);
		power = Range.Constrain(power + Random.Float(-5, 5), 20, 40);
		
		return new AnglePowerAimData(angle, power / 5, playerTank);
	}
	TankObj getClosestTank(TankObj playerTank, Level level) throws AIException {
		TankObj closestTank = null;
		float closestDistance = 0;
		
		for (TankObj tank : level.getTanks()) {
			if (tank == playerTank) { continue; }
			
			float distance = tank.hitbox().getPosition().subtract(playerTank.hitbox().getPosition()).magnitude();
			
			if (closestTank == null) {
				closestTank = tank;
				closestDistance = distance;
			}
			else if (distance < closestDistance) {
				closestTank = tank;
				closestDistance = distance;
			}
		}
		
		if (closestTank != null) {
			return closestTank;
		}
		else {
			throw new AIException("Tank with closest position couldn't be found. (Were there no tanks at all in the leve?)");
		}
	}
	DrivingAdvice shouldDrive(TankObj playerTank, Level level) throws AIException {
		TankObj closestTank = getClosestTank(playerTank, level);
		float xDisplacement = closestTank.hitbox().getPosition().getX() - playerTank.hitbox().getPosition().getX();
		float xDistance = Numbers.Abs(xDisplacement);
		
		boolean targetOnLeft = xDisplacement < 0;
		boolean targetOnRight = xDisplacement > 0;
		boolean canDriveLeft = playerTank.canDriveLeft();
		boolean canDriveRight = playerTank.canDriveRight();
		
		if (canDriveLeft && xDistance > 600 && targetOnLeft) {
			return DrivingAdvice.DRIVE_LEFT;
		}
		else if (canDriveRight && xDistance > 600 && targetOnRight) {
			return DrivingAdvice.DRIVE_RIGHT;
		}
		else {
			return DrivingAdvice.STAY_PUT;
		}
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
