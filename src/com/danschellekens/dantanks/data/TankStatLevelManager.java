package com.danschellekens.dantanks.data;

import com.danschellekens.dantanks.exceptions.*;

public class TankStatLevelManager {
	final TankStat stat;
	int level;
	
	public TankStatLevelManager(TankStat stat) {
		this.stat = stat;
		this.level = 1;
	}
	
	public int getLevel() {
		return level;
	}	
	public TankStat getStat() {
		return stat;
	}

	public boolean isMaxedOut() {
		return this.stat.getMaxLevel() == level;
	}
	public boolean canUpgrade(Player player) {
		if (isMaxedOut()) { return false; }
		if (player == null) { return false; }
		
		try {
			return player.canAfford(stat.getCost(this.level + 1));
		} 
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
	}
	public int priceOfNextUpgrade() {
		if (isMaxedOut()) { return 0; }
		
		try {
			return stat.getCost(this.level + 1);
		}
		catch (UpgradeException e) {
			throw new OopsieException(e);
		}
	}
	public void upgrade(Player player) throws UpgradeException {
		if (!canUpgrade(player)) {
			throw new UpgradeException("Player '" + player.getID() + "' cannot upgrade '" + stat.getID() + "'. Either the player does not have the funds, or the upgrade is maxed out.");
		}
		
		try { player.spendMoney(stat.getCost(this.level + 1)); }
		catch (CostException e) { throw new OopsieException(e); }
		
		level ++;
	}
}
