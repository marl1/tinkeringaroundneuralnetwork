package fr.mlev.nntw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.mlev.nntw.Util;


public class Network {

	List<Neuron> inputLayer = new ArrayList<>(); //créée dynamiquement
	List<Neuron> hiddenLayer = List.of(new Neuron(), new Neuron(), new Neuron());
	List<Neuron> ouputLayer = List.of(new Neuron());
	
	List<Neuron> allNeurons = new ArrayList<>();
	
    public Network(int tailleInput) {
    	for(int i=0;i<tailleInput;i++) {
    		inputLayer.add(new Neuron());
    	}
    	
    	allNeurons.addAll(inputLayer);
    	allNeurons.addAll(hiddenLayer);
    	allNeurons.addAll(ouputLayer);
    	
    	for(Neuron neuron:inputLayer) {
    		System.out.println("---");
        	for(Neuron nextNeuron:hiddenLayer) {
        		System.out.println("adding syn to hidden layer");
        		Synapse synapse = new Synapse(neuron, nextNeuron);
        		neuron.getOutgoingSynapses().add(synapse);
        		nextNeuron.getIngoingSynapses().add(synapse);
        	}
    	}
    	
    	for(Neuron neuron:hiddenLayer) {
    		System.out.println("---");
        	for(Neuron nextNeuron:ouputLayer) {
        		System.out.println("adding syn to output layer");
        		Synapse synapse = new Synapse(neuron, nextNeuron);
        		neuron.getOutgoingSynapses().add(synapse);
        		nextNeuron.getIngoingSynapses().add(synapse);
        	}
    	}
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
		Double bestEpochLoss = null;
		for (int epoch = 0; epoch < 100000; epoch++){
			// adapt neuron
			Neuron epochNeuron = allNeurons.get(random.nextInt(allNeurons.size()));
			Synapse epochSynapse = null;
			if (epochNeuron.getOutgoingSynapses().size() > 0) {
				epochSynapse = epochNeuron.getOutgoingSynapses().get(random.nextInt(epochNeuron.getOutgoingSynapses().size()));
			}
			Mutable mutable;
			if(epochSynapse !=null && random.nextInt(1) == 1) {
				mutable = epochSynapse;
			}else {
				mutable = epochNeuron;
			}
			mutable.mutate();


			List<Double> predictions = new ArrayList<Double>();
			for (int i = 0; i < data.size(); i++){ //the layer tries to predict using this mutated neuron
				this.interrogate(data.get(i));
				predictions.add(i, this.ouputLayer.get(0).getPreviousSum());
			}
			Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

			if (bestEpochLoss == null){
				bestEpochLoss = thisEpochLoss;
				mutable.remember();
			} else {
				if (thisEpochLoss < bestEpochLoss){ // if we were closer to the solution than the last epoch...
					bestEpochLoss = thisEpochLoss; // ...we save how close we are to the solution now
					epochNeuron.remember();//... we save the neuron values.
					if (epochSynapse != null) epochSynapse.remember();
				} else {
					mutable.forget();
				}
			}
			if (epoch % 100 == 0) System.out.println(String.format("Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f", epoch, bestEpochLoss, thisEpochLoss));
		}
	}

}
