package fr.mlev.nntw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.mlev.nntw.Util;


public class Network {

	List<Neuron> inputLayer = new ArrayList<>();
	List<Neuron> hiddenLayer = new ArrayList<>();
	List<Neuron> ouputLayer = List.of(new Neuron());
	
	List<Neuron> allNeurons = new ArrayList<>();
	List<Synapse> allSynapses = new ArrayList<>();
	
    public Network(int tailleInput) {
    	for(int i=0;i<tailleInput;i++) {
    		inputLayer.add(new Neuron());
    	}
    	
    	for(int i=0;i<(Math.ceil(tailleInput/2)+1);i++) { // For now let's say that the hidden layer have half the neurons of the input layer+1. Totally arbitrary.
    		hiddenLayer.add(new Neuron());
    	}
    	
    	allNeurons.addAll(inputLayer);
    	allNeurons.addAll(hiddenLayer);
    	allNeurons.addAll(ouputLayer);
    	
    	for(Neuron neuron:inputLayer) {
        	for(Neuron nextNeuron:hiddenLayer) {
        		Synapse synapse = new Synapse(neuron, nextNeuron);
        		allSynapses.add(synapse);
        		neuron.getOutgoingSynapses().add(synapse);
        		nextNeuron.getIngoingSynapses().add(synapse);
        	}
    	}
    	
    	for(Neuron neuron:hiddenLayer) {
        	for(Neuron nextNeuron:ouputLayer) {
        		Synapse synapse = new Synapse(neuron, nextNeuron);
        		allSynapses.add(synapse);
        		neuron.getOutgoingSynapses().add(synapse);
        		nextNeuron.getIngoingSynapses().add(synapse);
        	}
    	}
    	
    	graphvizGeneration(allSynapses);
    	
    	System.out.println("Network has been set up.");
	}

	public Double interrogate(Double[] valuesToCompute) {
    	for(int i=0;i<valuesToCompute.length;i++) {
    		//System.out.println("propagate");
    		inputLayer.get(i).input(valuesToCompute[i]);
    	}
    	return this.ouputLayer.get(0).getPreviousSum();
    }

	/**
	 * For a certain number of time (epoch) the neurons will apply their function of the data and check
	 * what they output against the provided answers.
	 * @param data: the data they exercice on
	 * @param answers: the answers of those data
	 * 
	 * 
	 */
	public void train(List<Double[]> data, List<Double> answers){
		Random random = new Random();
		Double bestEpochLoss = 99999.0;
		for (int epoch = 0; epoch < 500000; epoch++){
			// adapt neuron
			Neuron epochNeuron = allNeurons.get(random.nextInt(allNeurons.size()));
			Synapse epochSynapse = null; // we can mutate this neuron or one of his outgoing synapse but they don't ALL have an outgoing synapse (the last layer don't have any)
			if (epochNeuron.getOutgoingSynapses().size() > 0) {
				epochSynapse = epochNeuron.getOutgoingSynapses().get(random.nextInt(epochNeuron.getOutgoingSynapses().size()));
			}
			Mutable mutable;
			if(epochSynapse !=null && random.nextInt(1) == 1) { // if this neuron has an outgoing synapse, 50/50 chances that we modify the synapse or the neuron 
				mutable = epochSynapse;
			}else {
				mutable = epochNeuron; // if no outgoing synapse or if we picked 0 at the 50/50 chances we modify the neuron
			}
			mutable.mutate();


			List<Double> predictions = new ArrayList<Double>();
			for (int i = 0; i < data.size(); i++){ //the layer tries to predict using this mutated neuron
				this.interrogate(data.get(i));
				predictions.add(i, this.ouputLayer.get(0).getPreviousSum());
			}
			Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

			if (thisEpochLoss < bestEpochLoss){ // if we were closer to the solution than the last epoch...
				bestEpochLoss = thisEpochLoss; // ...we save how close we are to the solution now
				mutable.remember();//... we save the neuron/synapse values.
				backPropagate(thisEpochLoss);
			} else {
				mutable.forget();
			}

			if (epoch % 100 == 0) System.out.println(String.format("Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f", epoch, bestEpochLoss, thisEpochLoss));
		}
	}

    private void graphvizGeneration(List<Synapse> allSynapses) {
    	System.out.println("digraph Network {");
    	for(Synapse synapse : allSynapses) {
    		System.out.println(synapse.getStartingNeuron() + "->" + synapse.getEndingNeuron());
    	}
    	System.out.println("}");
	}
    
    private void backPropagate(Double thisEpochLoss) {
    	for(Neuron neuron:allNeurons) {
    		neuron.backPropagation(thisEpochLoss);
    	}
    }

}
