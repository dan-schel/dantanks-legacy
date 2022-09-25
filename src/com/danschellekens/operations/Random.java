package com.danschellekens.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Provides helpful functions for picking random elements.
 * @author Dan2002s
 */
public class Random {
	
	/**
	 * Returns a random float between 0 and 1.
	 */
	public static float Float() {
		return (float) Math.random();
	}
	
	/**
	 * Returns a random float between 0 and max.
	 */
	public static float Float(float max) {
		return Range.Map(Float(), 0, 1, 0, max);
	}
	
	/**
	 * Returns a random float between min and max.
	 */
	public static float Float(float min, float max) {
		return Range.Map(Float(), 0, 1, min, max);
	}
	
	/**
	 * Returns a random int between 0 and one less than max.
	 */
	public static int Int(int max) {
		return Numbers.Floor(Range.Map(Float(), 0, 1, 0, max));
	}
	
	/**
	 * Returns a random int between min and one less than max.
	 */
	public static int Int(int min, int max) {
		return Numbers.Floor(Range.Map(Float(), 0, 1, min, max));
	}
	
	/**
	 * Returns either true or false depending on a random chance.
	 * Chance must be between 0 and 1: chance of 1 means always true, chance of 0 means 
	 * always false.
	 */
	public static boolean Chance(float chance) {
		return Float() < chance;
	}
	
	/**
	 * Returns either true or false depending on a 50/50 chance.
	 */ 
	public static boolean Chance() {
		return Chance(0.5f);
	}
	
	/**
	 * Returns either 1 or -1 depending on a 50/50 chance.
	 */ 
	public static int Sign() {
		return Chance() ? -1 : 1;
	}
	
	/**
	 * Returns a random element from an array, or null if the array is empty.
	 */
	public static <T> T ArrayElement(T[] array) {
		if (array.length == 0) { return null; }
		return array[Int(array.length)];
	}
	
	/**
	 * Returns a random element from a list, or null if the list is empty.
	 */
	public static <T> T ListElement(List<T> list) {
		if (list.isEmpty()) { return null; }
		return list.get(Int(list.size()));
	}
	
	/**
	 * Returns a random element from an list, or null if the array is empty, using the provided weights.
	 */
	public static <T> T ListElement(List<T> list, List<Float> weights) {
		if (list.size() != weights.size()) { throw new RuntimeException("Lists and Weights must have the same size."); }
		if (list.isEmpty()) { return null; }
		
		float totalWeight = 0;
		for (Float weight : weights) {
			totalWeight += weight;
		}
		
		float selected = Float(0, totalWeight);
		
		float weightSoFar = 0;
		int index = 0;
		for (Float weight : weights) {
			if (weightSoFar + weight > selected) {
				return list.get(index);
			}
			else {
				weightSoFar += weight;
				index ++;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a random key from a map, or null if the map is empty.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> K Key(Map<K, V> map) {
		if (map.isEmpty()) { return null; }
		return (K) ArrayElement(map.keySet().toArray());
	}
	
	/**
	 * Returns a random value from a map, or null if the map is empty.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> V Value(Map<K, V> map) {
		if (map.isEmpty()) { return null; }
		return (V) ArrayElement(map.values().toArray());
	}
	
	/**
	 * Returns the same ArrayList but shuffled.
	 */
	public static <T> ArrayList<T> Shuffle(ArrayList<T> list) {
		ArrayList<T> result = new ArrayList<T>(list);
		Collections.shuffle(result);
		return result;
	}
	
	/**
	 * Returns the same array but shuffled.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] Shuffle(T[] array) {
		ArrayList<T> result = new ArrayList<T>(Arrays.asList(array));
		Collections.shuffle(result);
		return (T[]) result.toArray();
	}
	
	/**
	 * Returns a random float between 0 and 360.
	 */
	public static float Degrees() {
		return Float(360);
	}
	
	/**
	 * Returns a random float between 0 and Two Pi.
	 */
	public static float Radians() {
		return Float(Radians.TWO_PI);
	}
	
	/**
	 * Returns a vector with a magnitude of size but random angle.
	 */
	public static Vector2 Vector2(float size) {
		return new Vector2(0, size).rotateDegrees(Degrees());
	}
	
	/**
	 * Returns a vector with a magnitude of minSize to maxSize but random angle.
	 */
	public static Vector2 Vector2(float minSize, float maxSize) {
		return new Vector2(0, Float(minSize, maxSize)).rotateDegrees(Degrees());
	}
}
