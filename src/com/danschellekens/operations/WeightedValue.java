package com.danschellekens.operations;

import java.util.ArrayList;

/**
 * Stores a value and a weight for use with {@link com.danschellekens.operations.Random#Value(WeightedValue...) Random.Value} which picks a random element
 * based on it's weight.
 * @author Dan2002s
 */
public class WeightedValue <T> {
	T value;
	float weight;
	
	/**
	 * Creates a new WeightedValue.
	 */
	public WeightedValue(T value, float weight) {
		this.value = value;
		this.weight = Math.abs(weight);
	}
	
	/**
	 * Creates a new WeightedValue.
	 */
	public WeightedValue(T value) {
		this(value, 1);
	}

	/**
	 * Returns the value.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * Returns the weight (the chance to be randomly selected).
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * Sets the weight (the chance to be randomly selected).
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	/**
	 * Returns the total weight of the list.
	 */
	public static <T> float TotalWeight(ArrayList<WeightedValue<T>> values) {
		float total = 0;
		
		for (WeightedValue<T> v : values) {
			total += v.getWeight();
		}
		
		return total;
	}
	
	/**
	 * Returns a random element based on it's weight, or null if an error occurs.
	 */
	public static <T> T ChooseRandom(ArrayList<WeightedValue<T>> values) {
		float total = WeightedValue.TotalWeight(values);
		float chosenOne = Random.Float(total);
		
		float runningTotal = 0;
		
		for (WeightedValue<T> v : values) {
			if (runningTotal < chosenOne && runningTotal + v.getWeight() > chosenOne) {
				return v.getValue();
			}
			else {
				runningTotal += v.getWeight();
			}
		}
		
		return null;
	}
}
