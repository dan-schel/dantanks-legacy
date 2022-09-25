package com.danschellekens.dantanks.level.world.ground;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;

import com.danschellekens.operations.Vector2;

public class SurfaceSegment {
	final ArrayList<Vector2> points;
	final int layerIndex;
	final int layerFollowingIndex;
	final Polygon polygon;
	
	public SurfaceSegment(ArrayList<Vector2> points, int layerIndex, int layerFollowingIndex) {
		this.points = points;
		this.layerIndex = layerIndex;
		this.layerFollowingIndex = layerFollowingIndex;
		
		Polygon p = new Polygon();
		for (int i = 0; i < points.size(); i++) {
			float x = points.get(i).getX();
			float y = points.get(i).getY() - Ground.TOP_VIEW_OFFSET;
			p.addPoint(x, y);
		}
		for (int i = points.size() - 1; i >= 0; i--) {
			float x = points.get(i).getX();
			float y = points.get(i).getY() + Ground.TOP_VIEW_OFFSET;
			p.addPoint(x, y);
		}
		polygon = p;
	}
	public ArrayList<Vector2> getPoints() {
		return points;
	}
	public int getLayerIndex() {
		return layerIndex;
	}
	public Polygon getPolygon() {
		return polygon;
	}
	public Color getColor(Ground ground) {
		return ground.getLayers()[this.layerIndex].getTopColor();
	}
	public int getLayerFollowingIndex() {
		return layerFollowingIndex;
	}
	public Vector2 getFinalPoint() {
		return points.get(points.size() - 1);
	}
}
