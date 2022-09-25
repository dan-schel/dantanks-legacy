package com.danschellekens.operations;

public class Conditions {
	/**
	 * Checks the boolean values, if they have the same value, returns 0.
	 * Otherwise returns the number relating to name of the value.
	 */
	public static int ToNumeric(boolean negative, boolean positive) {
		if (negative == positive) { return 0; }
		if (positive) { return 1; }
		if (negative) { return -1; }
		else { return 0; }
	}
	
	public static boolean Equals(Object a, Object b) {
		if (a == null && b == null) { return true; }
		if (a == null) { return false; }
		return a.equals(b);
	}
}
