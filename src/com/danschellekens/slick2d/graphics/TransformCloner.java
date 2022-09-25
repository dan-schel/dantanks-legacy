package com.danschellekens.slick2d.graphics;

import org.newdawn.slick.geom.Transform;

public class TransformCloner {

	public static Transform Clone(Transform currentTransform) {
		float[] matrix = currentTransform.getMatrixPosition();
		float[] newMatrix = new float[6];
		
		for (int i = 0; i < 6; i++) {
			newMatrix[i] = matrix[i];
		}
		
		return new Transform(newMatrix);
	}

}
