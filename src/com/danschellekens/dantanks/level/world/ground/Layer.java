package com.danschellekens.dantanks.level.world.ground;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import com.danschellekens.dantanks.level.Level;

public class Layer {
	final float[] visualNodes;
	final float breakability;
	final Color topColor;
	final Color sideColor;
	
	Shape[] sideShapes;
	
	public Layer(NodeGenerator generator, float breakability, Color topColor, Color sideColor) {
		this.breakability = breakability;
		this.topColor = topColor;
		this.sideColor = sideColor;
		
		this.visualNodes = generator.generateNodes();
	}
	
	public void updateSideShapes(Polygon subtractionPolygon) {
		Polygon p = new Polygon();
		
		for (int i = 0; i < Ground.NODES_AMOUNT; i++) {
			float x = i * Ground.NODE_SPACING;
			float y = visualNodes[i] + Ground.TOP_VIEW_OFFSET;
			p.addPoint(x, y);
		}
		p.addPoint(Level.LEVEL_WIDTH, Level.LEVEL_HEIGHT);
		p.addPoint(0, Level.LEVEL_HEIGHT);
		
		sideShapes = p.subtract(subtractionPolygon);
	}
	public Shape[] getSideShapes() {
		return sideShapes;
	}
	
	public Color getTopColor() { return topColor; }
	public Color getSideColor() { return sideColor; }
	public float[] getVisualNodes() { return visualNodes; }
	public float getBreakability() { return breakability; }
}
