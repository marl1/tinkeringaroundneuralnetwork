package fr.mlev.nntw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import fr.mlev.nntw.Util;

/**
 * A neuron takes 2 input values. As soon as they enter, it multiplies them by a
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
public class Neuron implements Mutable {
	Random random = new Random();

	// The random in and out values.
	private Double bias;

	private List<Synapse> ingoingSynapses = new ArrayList<>();
	private Map<Synapse, Double> ingoingSynapsesLastSum = new HashMap<>();
	
	private List<Synapse> outgoingSynapses = new ArrayList<>();
	
	private Double sum = 0.0;
	
	private Double previousSum = 0.0;

	// To keep in memory the last random values
	private Double oldBias;
	
	private int nbrInputReceived = 0;

	
	
	public Neuron() {
		System.out.println("neuron");
		bias = random.nextDouble(-1, 1);
		oldBias = bias;
	}

	public void input(double input) {		
		nbrInputReceived++;
		sum += input; //accumulating inputs until we received as much input as we have incoming synapses
		if (ingoingSynapses.size() == 0 || nbrInputReceived == ingoingSynapses.size()) {
			nbrInputReceived = 0;
			sum = Util.sigmoid(sum * this.bias);
			for(Synapse synapse:outgoingSynapses) {
				synapse.carry(sum);
				ingoingSynapsesLastSum.put(synapse, sum);
				sum = 0.0;
			}
			if (outgoingSynapses.size() == 0) {
				previousSum = sum;
				sum = 0.0;
			}
		}
		//System.out.println(sum);
	}
	
	public void backPropagation(double epochLoss) {
		for(Entry<Synapse, Double> synapseAndLastSum:ingoingSynapsesLastSum.entrySet()) {
			Synapse synapse = synapseAndLastSum.getKey();
			Double synapseLastSum = synapseAndLastSum.getValue();
			double valueToApplyToWeight = synapse.weight * (-epochLoss) * synapseLastSum * epochLoss * (1-epochLoss);
			synapse.weight = synapse.weight + valueToApplyToWeight; 
		}
	}

	/**
	 * This randomly changes the in and out values.
	 */
	public void mutate(){
		this.bias += random.nextDouble(-1, 1);
	}


	public void forget() {
		bias = oldBias;

	}

	public void remember() {
		oldBias = bias;
	}
	
	public List<Synapse> getOutgoingSynapses() {
		return outgoingSynapses;
	}

	public void setOutgoingSynapses(List<Synapse> outgoingSynapses) {
		this.outgoingSynapses = outgoingSynapses;
	}

	public Double getSum() {
		return sum;
	}

	public List<Synapse> getIngoingSynapses() {
		return ingoingSynapses;
	}

	public void setIngoingSynapses(List<Synapse> ingoingSynapses) {
		this.ingoingSynapses = ingoingSynapses;
	}

	public Double getPreviousSum() {
		return previousSum;
	}

	@Override
	public String toString() {
		return String.valueOf(this.hashCode());
	}
	
	
	
}
