package com.danschellekens.dantanks.level.world.ground;


public class CopyNodeGenerator implements NodeGenerator {
	final float[] nodesToClone;
	final float yOffset;
	
	public CopyNodeGenerator(float[] nodesToClone, float yOffset) {
		this.nodesToClone = nodesToClone.clone();
		this.yOffset = yOffset;
	}
	
	@Override
	public float[] generateNodes() {
		float[] nodes = new float[Ground.NODES_AMOUNT];
		for (int i = 0; i < Ground.NODES_AMOUNT; i++) { nodes[i] = nodesToClone[i] + yOffset; }
		
		return nodes;
	}
}
