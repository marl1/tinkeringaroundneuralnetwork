package fr.mlev.nntw.model;

import java.util.Random;

import fr.mlev.nntw.Util;

/**
 * A neuro takes 2 input values. As soon as they enter, it multiplies them by a
 * random value.
 * 
 * It then combines them somehow with a function. The function is called an
 * "Activation Function" or "Transfer Function".
 * 
 * You can use any function you want. There is 2 types : -Linear Activation
 * Function (the output of the range is not limited, from minus infinity to
 * infinity). -Non-linear Activation Functions (that have a minimum and a
 * maximum). That's very useful because it can be used to give estimates between
 * 0 and 100%. Sigmoid, Than and ReLU are popular types. See
 * https://towardsdatascience.com/activation-functions-neural-networks-1cbd9f8d91d6
 * 
 * 
 *
 */
public class Neuron {
	Random random = new Random();

	// The random in and out values.
	private Double bias = random.nextDouble(-1, 1);
	public Double weight1 = random.nextDouble(-1, 1);
	private Double weight2 = random.nextDouble(-1, 1);

	// To keep in memory the last random values
	private Double oldBias = bias;
	public Double oldWeight1 = weight1;
	private Double oldWeight2 = weight2;

	/**
	 * The function that takes 2 input values and output another one.
	 */
	public double compute(double input1, double input2) {
		double preActivation = (this.weight1 * input1) + (this.weight2 * input2) + this.bias;
		double output = Util.sigmoid(preActivation);
		return output;
	}

	/**
	 * This randomly changes the in and out values.
	 */
	public void mutate(Double learnFactor){
		int propertyToChange = random.nextInt(0, 3);
		Double changeFactor = (learnFactor == null) ? random.nextDouble(-1, 1) : (learnFactor * random.nextDouble(-1, 1));
		if (propertyToChange == 0){ 
			this.bias += changeFactor; 
		} else if (propertyToChange == 1){ 
			this.weight1 += changeFactor; 
		} else { 
			this.weight2 += changeFactor; 
		};
	}

	/**
	 * Will be used to used the previous values if they were better.
	 */
	public void forget() {
		bias = oldBias;
		weight1 = oldWeight1;
		weight2 = oldWeight2;
	}

	/**
	 * Will be used to save the new values if they were better than the old.
	 */
	public void remember() {
		oldBias = bias;
		oldWeight1 = weight1;
		oldWeight2 = weight2;
	}

}
