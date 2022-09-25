package com.danschellekens.dantanks.level.world.ground;


public class FlatNodeGenerator implements NodeGenerator {
	final float height;
	
	public FlatNodeGenerator(float height) {
		this.height = height;
	}
	
	@Override
	public float[] generateNodes() {
		float[] nodes = new float[Ground.NODES_AMOUNT];
		for (int i = 0; i < Ground.NODES_AMOUNT; i++) { nodes[i] = height; }
		return nodes;
	}
}
