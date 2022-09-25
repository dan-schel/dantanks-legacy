package com.danschellekens.dantanks.level.world.ground;

import com.danschellekens.operations.Range;
import com.danschellekens.operations.fastnoise.FastNoise;
import com.danschellekens.operations.fastnoise.FastNoise.NoiseType;

public class NoiseNodeGenerator implements NodeGenerator {
	final float minHeight;
	final float maxHeight;
	final float roughness;
	final int seed;
	
	public NoiseNodeGenerator(float minHeight, float maxHeight, float roughness, int seed) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.roughness = roughness;
		this.seed = seed;
	}
	
	@Override
	public float[] generateNodes() {
		float[] nodes = new float[Ground.NODES_AMOUNT];
		
		FastNoise noise = new FastNoise();
		noise.SetSeed(seed);
		noise.SetNoiseType(NoiseType.Simplex);
		
		for (int i = 0; i < nodes.length; i++) {
			float noiseValue = noise.GetNoise(i * roughness * Ground.NODE_SPACING + 40, 40);
			float octaveValue = noise.GetNoise(i * roughness * 5 * Ground.NODE_SPACING + 40, 40) / 10;
			nodes[i] = Range.MapConstrain(noiseValue + octaveValue, -1.1f, 1.1f, minHeight, maxHeight);
		}
		return nodes;
	}
}
