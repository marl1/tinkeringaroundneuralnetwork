class Network {
    
    constructor() {
        this.neurons = [
          new Neuron(), new Neuron(), new Neuron(), //input layer
          new Neuron(), new Neuron(), //hidden layer
          new Neuron() //output layer
        ]
      }

      predict(input1, input2){
        return this.neurons[5].compute( //output layer
                      this.neurons[4].compute( //hidden layer
                      this.neurons[2].compute(input1, input2), // input layer
                          this.neurons[1].compute(input1, input2) // input layer
                ),
                this.neurons[3].compute(//hidden layer
                      this.neurons[1].compute(input1, input2), // input layer
                      this.neurons[0].compute(input1, input2) // input layer
                )
              );
    }

    // after 5
    train(trainingDataList, answers){
      let bestEpochLoss;
      for(let epoch = 0; epoch < 10000; epoch++){
        let r = getRandomIntBetween(0, 6);
        let epochNeuron = this.neurons[r];
        epochNeuron.mutate();
        let predictions = [];
        let trainingData;
        for(trainingData of trainingDataList) {
          predictions.push(this.predict(trainingData.height, trainingData.weight));
        }
        let thisEpochLoss = meanSquareLoss(predictions, answers);
        
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
        if (epoch % 100 == 0) {
          console.log("Epoch :" + epoch + ", bestEpochLoss " + bestEpochLoss + ", thisEpochLoss" + thisEpochLoss);
        }
      }
  }

}