package fr.mlev.nntw.model;

import java.util.ArrayList;
import java.util.List;

import fr.mlev.nntw.Util;


public class Network {
    List<Neuron> neurons = List.of(
    	      new Neuron(), new Neuron(), new Neuron(), /* input layer */
    	      new Neuron(), new Neuron(),               /* hidden layer */
    	      new Neuron()								/* output layer */
    	      );                            

	Double learnFactor = null;

	/**
	 * For a certain number of time (epoch) the neurons will apply their function of the data and check
	 * what they output against the provided answers.
	 * @param data: the data they exercice on
	 * @param answers: the answers of those data
	 * 
	 * 
	 */
	public void train(List<List<Integer>> data, List<Double> answers){
		Double bestEpochLoss = null;
		for (int epoch = 0; epoch < 1000; epoch++){
			// adapt neuron
			Neuron epochNeuron = neurons.get(epoch % 6); //a "random" neuron is mutated for this epoch
			epochNeuron.mutate(this.learnFactor);

			List<Double> predictions = new ArrayList<Double>();
			for (int i = 0; i < data.size(); i++){ //the layer tries to predict using this mutated neuron
				predictions.add(i, this.predict(data.get(i).get(0), data.get(i).get(1)));
			}
			Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

			if (bestEpochLoss == null){
				bestEpochLoss = thisEpochLoss;
				epochNeuron.remember();
			} else {
				if (thisEpochLoss < bestEpochLoss){ // if we were closer to the solution than the last epoch...
					bestEpochLoss = thisEpochLoss; // ...we save how close we are to the solution now
					epochNeuron.remember();//... we save the neuron values.
				} else {
					epochNeuron.forget(); 
				}
			}
			if (epoch % 100 == 0) System.out.println(String.format("Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f", epoch, bestEpochLoss, thisEpochLoss));
		}
	}

	public Double predict(Integer input1, Integer input2){
		  return neurons.get(5).compute( //output layer
				  neurons.get(4).compute( //hidden layer
						  neurons.get(2).compute(input1, input2), // input layer
						  neurons.get(1).compute(input1, input2) // input layer
		    ),
				  neurons.get(3).compute(//hidden layer
						  neurons.get(1).compute(input1, input2), // input layer
						  neurons.get(0).compute(input1, input2) // input layer
		    )
		  );
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}


}
