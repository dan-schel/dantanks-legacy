package com.danschellekens.dantanks.level.world.ground;

import java.util.ArrayList;

import org.newdawn.slick.geom.Polygon;

import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.operations.*;

public class Ground {
	public static final float TOP_VIEW_OFFSET = 20;
	public static final int NODES_AMOUNT = 121;
	public static final float NODE_SPACING = Level.LEVEL_WIDTH / (NODES_AMOUNT - 1);
	public static final int FINAL_NODE_INDEX = NODES_AMOUNT - 1;
	public static float GetNodeXFromIndex(int index) { return index * NODE_SPACING; }
	
	final Level level;
	final LevelUmpire umpire;
	final Layer[] layers;
	
	float[] nodes;
	Polygon subtractionPolygon;
	SurfaceSegment[] surfaceSegments;
	
	public Ground(Level level, LevelUmpire umpire, Layer[] layers, NodeGenerator groundLevel) {
		this.level = level;
		this.umpire = umpire;
		
		this.layers = layers;
		
		nodes = groundLevel.generateNodes();
		
		updateShapes();
	}
	
	public void explode(Vector2 position, float size, float strength) {
		for (int nodeIndex = 0; nodeIndex < NODES_AMOUNT; nodeIndex++) {
			float x = GetNodeXFromIndex(nodeIndex);
			float previousDepth = nodes[nodeIndex];
			float proposedDepth = position.getY() + (float) Math.sqrt(size * size - (x - position.getX()) * (x - position.getX()));
			float diggingToDo = proposedDepth - previousDepth;
			
			if (Float.isNaN(diggingToDo) || diggingToDo <= 0) { continue; }
			
			ArrayList<Layer> layersInDepthOrder = new ArrayList<Layer>();
			while (layersInDepthOrder.size() < layers.length) {
				Layer highestDepthLayer = null;
				for (int j = 0; j < layers.length; j++) {
					if (layersInDepthOrder.contains(layers[j])) { continue; }
					
					if (highestDepthLayer == null || layers[j].getVisualNodes()[nodeIndex] < highestDepthLayer.getVisualNodes()[nodeIndex]) {
						highestDepthLayer = layers[j];
					}
				}
				layersInDepthOrder.add(highestDepthLayer);
			}
			
			float currentHeight = nodes[nodeIndex];
			for (int layerIndex = 0; layerIndex < layers.length - 1; layerIndex++) {
				float thisLayersBottom = layersInDepthOrder.get(layerIndex + 1).getVisualNodes()[nodeIndex];
				
				
				if (currentHeight > thisLayersBottom) { continue; }
				float diggingPossibleInThisLayer = thisLayersBottom - currentHeight;
				float diggingToDoInThisLayer = diggingToDo * layersInDepthOrder.get(layerIndex).getBreakability();
				
				if (diggingToDoInThisLayer == 0) { continue; }
				if (diggingToDoInThisLayer <= diggingPossibleInThisLayer) {
					diggingToDo = 0;
					currentHeight += diggingToDoInThisLayer;
				}
				else {
					diggingToDo -= diggingPossibleInThisLayer / layersInDepthOrder.get(layerIndex).getBreakability();
					
					
					currentHeight += diggingPossibleInThisLayer;
				}
				
				
			}
			nodes[nodeIndex] = currentHeight;
		}
		
		updateShapes();
	}
	public void updateShapes() {
		updateSubtractionPolygon();
		for (Layer layer : layers) { layer.updateSideShapes(subtractionPolygon); }
		
		updateSurfaceSegments();
	}
	void updateSubtractionPolygon() {
		// Must use -1 as subtract only deals with edges and won't create holes in shapes.
		
		Polygon p = new Polygon();
		p.addPoint(-1, -1);
		p.addPoint(-1, GetNodeXFromIndex(FINAL_NODE_INDEX));
		for (int i = 0; i < NODES_AMOUNT; i++) {
			float x = GetNodeXFromIndex(i);
			float y = nodes[i] + TOP_VIEW_OFFSET;
			p.addPoint(x, y);
		}
		p.addPoint(GetNodeXFromIndex(FINAL_NODE_INDEX) + 1, nodes[FINAL_NODE_INDEX] + TOP_VIEW_OFFSET);
		p.addPoint(GetNodeXFromIndex(FINAL_NODE_INDEX) + 1, -1);
		
		subtractionPolygon = p;
	}
	void updateSurfaceSegments() {
		int[] exposedLayerIndices = getExposedLayerIndicesPerIndex();
		ArrayList<SurfaceSegment> segments = new ArrayList<SurfaceSegment>();
		
		int exposedLayerIndex = exposedLayerIndices[0];
		ArrayList<Vector2> points = new ArrayList<Vector2>();
		points.add(new Vector2(GetNodeXFromIndex(0), nodes[0]));
		
		int nextIndexPointToAdd = 1;
		
		
		for (int x = 1; x < Level.LEVEL_WIDTH; x += 1) {
			if (x >= GetNodeXFromIndex(nextIndexPointToAdd)) {
				points.add(new Vector2(GetNodeXFromIndex(nextIndexPointToAdd), nodes[nextIndexPointToAdd]));
				nextIndexPointToAdd++;
			}
			
			int nowExposedLayerIndex = this.getExposedLayerIndexAtX(x);
			if (nowExposedLayerIndex != exposedLayerIndex) {
				points.add(new Vector2(x, getHeightOfX(x)));
				
				SurfaceSegment segment = new SurfaceSegment(points, exposedLayerIndex, -1);
				segments.add(segment);
				
				exposedLayerIndex = nowExposedLayerIndex;
				points = new ArrayList<Vector2>(); 
				points.add(new Vector2(x, getHeightOfX(x)));
			}
		}
		
		points.add(new Vector2(Level.LEVEL_WIDTH, getHeightOfX(Level.LEVEL_WIDTH)));
		SurfaceSegment segment = new SurfaceSegment(points, exposedLayerIndex, -1);
		segments.add(segment);
		
		this.surfaceSegments = segments.toArray(new SurfaceSegment[segments.size()]);
	}
	
	public int getExposedLayerIndexAtIndex(int nodeIndex) {
		float thisNodeHeight = this.nodes[nodeIndex];
		
		int currentWinnerLayerIndex = -1;
		float currentWinnerHeight = -1;
		
		for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
			float layerNodeHeight = layers[layerIndex].getVisualNodes()[nodeIndex];
			
			if (layerNodeHeight > thisNodeHeight) { continue; }
			else if (layerNodeHeight > currentWinnerHeight) {
				currentWinnerLayerIndex = layerIndex;
				currentWinnerHeight = layerNodeHeight;
			}
		}
		
		return currentWinnerLayerIndex;
	}
	public int getExposedLayerIndexAtX(float x) {
		int currentWinnerLayerIndex = -1;
		float currentWinnerHeight = -1;
		float thisNodeHeight = getHeightOfX(x);
		
		for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
			float layerHeight = getHeightOfX(x, layerIndex);
			
			if (layerHeight > thisNodeHeight) { continue; }
			else if (layerHeight > currentWinnerHeight) {
				currentWinnerLayerIndex = layerIndex;
				currentWinnerHeight = layerHeight;
			}
		}
		
		return currentWinnerLayerIndex;
	}
	public int[] getExposedLayerIndicesPerIndex() {
		int[] result = new int[NODES_AMOUNT];
		for (int nodeIndex = 0; nodeIndex < NODES_AMOUNT; nodeIndex++) {
			result[nodeIndex] = getExposedLayerIndexAtIndex(nodeIndex);
		}
		return result;
	}
	
	public float getNodeHeightFromIndex(int index) {
		if (index < 0) { return nodes[0]; }
		if (index > nodes.length - 1) { return nodes[nodes.length - 1]; }
		else { return nodes[index]; }
	}
	public float getNodeHeightFromIndex(int index, int layerIndex) {
		if (index < 0) { return layers[layerIndex].getVisualNodes()[0]; }
		if (index > nodes.length - 1) { return  layers[layerIndex].getVisualNodes()[nodes.length - 1]; }
		else { return  layers[layerIndex].getVisualNodes()[index]; }
	}
	public Vector2 getNodePositionFromIndex(int index) {
		return new Vector2(GetNodeXFromIndex(index), getNodeHeightFromIndex(index));
	}
	
	public float getHeightOfX(float x) {
		// get nearest node index on either side.
		int index1 = Numbers.Floor(x / NODE_SPACING);
		int index2 = Numbers.Ceil(x / NODE_SPACING);
		
		// x value falls directly on a node.
		if (index1 == index2) { return getNodeHeightFromIndex(index1); }
		
		// x value falls between two nodes.
		float fractionalIndex = x / NODE_SPACING;
		float height1 = getNodeHeightFromIndex(index1);
		float height2 = getNodeHeightFromIndex(index2);
		return Range.Map(fractionalIndex, index1, index2, height1, height2);
	}
	public float getHeightOfX(float x, int layerIndex) {
		// get nearest node index on either side.
		int index1 = Numbers.Floor(x / NODE_SPACING);
		int index2 = Numbers.Ceil(x / NODE_SPACING);
		
		// x value falls directly on a node.
		if (index1 == index2) { return getNodeHeightFromIndex(index1, layerIndex); }
		
		// x value falls between two nodes.
		float fractionalIndex = x / NODE_SPACING;
		float height1 = getNodeHeightFromIndex(index1, layerIndex);
		float height2 = getNodeHeightFromIndex(index2, layerIndex);
		return Range.Map(fractionalIndex, index1, index2, height1, height2);
	}
	public float getHighestPointBetween(float x1, float x2) {
		int x1Index = Numbers.Ceil(Numbers.Smaller(x1, x2) / NODE_SPACING);
		int x2Index = Numbers.Floor(Numbers.Larger(x1, x2) / NODE_SPACING);

		float highest = Numbers.Smaller(getHeightOfX(x1), getHeightOfX(x2));
		if (x1 >= x2) { 
			return highest;
		}
		
		for (int index = x1Index; index <= x2Index; index++) {
			if (getNodeHeightFromIndex(index) < highest) {
				highest = getNodeHeightFromIndex(index);
			}
		}
		
		return highest;
	}
	public Vector2 getClimb(float x) {
		// Once off screen, land is flat.
		if (x < 0) { return new Vector2(1, 0); }
		if (x > Level.LEVEL_WIDTH - 1) { return new Vector2(1, 0); }
		
		int indexA = (int) Math.floor(x / NODE_SPACING);
		int indexB = indexA + 1;
		Vector2 a = getNodePositionFromIndex(indexA);
		Vector2 b = getNodePositionFromIndex(indexB);
		Vector2 c = b.subtract(a);
		return c.normalize();
	}
	
	public float[] getNodes() {
		return nodes;
	}
	public Layer[] getLayers() {
		return layers;
	}

	public SurfaceSegment[] getSurfaceSegments() {
		return surfaceSegments;
	}
}
