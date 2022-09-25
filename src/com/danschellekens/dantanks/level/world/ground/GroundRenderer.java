package com.danschellekens.dantanks.level.world.ground;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import com.danschellekens.slick2d.graphics.Artist;

public class GroundRenderer {
	public static void RenderGraphics(Artist artist, Ground ground) {
		Layer[] layers = ground.getLayers();
		
		for (int layerIndex = 0; layerIndex < layers.length; layerIndex++) {
			Shape[] shapes = layers[layerIndex].getSideShapes();
			Color color = layers[layerIndex].getSideColor();
			
			for (Shape shape : shapes) {
				artist.shape().draw(shape, color);
			}
		}
		
		SurfaceSegment[] segments = ground.getSurfaceSegments();
		
		for (SurfaceSegment segment : segments) {
			Polygon shape = segment.getPolygon();
			Color color = layers[segment.getLayerIndex()].getTopColor();
			
			artist.shape().draw(shape, color);
		}
	}
	
	public static void RenderHitboxes(Artist artist, Ground ground) {
		for (int i = 0; i < Ground.NODES_AMOUNT - 1; i++) {
			float x1 = Ground.GetNodeXFromIndex(i);
			float x2 = Ground.GetNodeXFromIndex(i + 1);
			float y1 = ground.getNodes()[i];
			float y2 = ground.getNodes()[i + 1];
			artist.line().draw(x1, y1, x2, y2, new Color(255, 255, 255), 4);
		}
		
		for (Layer l : ground.getLayers()) {
			for (int i = 0; i < Ground.NODES_AMOUNT - 1; i++) {
				float x1 = Ground.GetNodeXFromIndex(i);
				float x2 = Ground.GetNodeXFromIndex(i + 1);
				float y1 = l.getVisualNodes()[i];
				float y2 = l.getVisualNodes()[i + 1];
				artist.line().draw(x1, y1, x2, y2, new Color(255, 255, 255), 1);
			}
		}
	}
}
