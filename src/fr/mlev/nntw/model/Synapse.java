package fr.mlev.nntw.model;

import java.util.Random;

public class Synapse implements Mutable {
	Double weight;
	Double oldWeight;
	Neuron startingNeuron;
	Neuron endingNeuron;

	public Synapse(Neuron startingNeuron, Neuron endingNeuron) {
		weight = new Random().nextDouble(-1, 1);
		oldWeight = weight;
		this.startingNeuron = startingNeuron;
		this.endingNeuron = endingNeuron;
	}

	public void carry(Double input) {
		endingNeuron.input(this.weight * input);
	}
	
	/**
	 * This randomly changes the in and out values.
	 * todo juste épaissir ?
	 */
	public void mutate(){
		this.weight += new Random().nextDouble(-1, 1); 
	}
	
	public Neuron getStartingNeuron() {
		return startingNeuron;
	}

	public Neuron getEndingNeuron() {
		return endingNeuron;
	}
	
	public void forget() {
		weight = oldWeight;

	}

	public void remember() {
		oldWeight = weight;
	}
}
