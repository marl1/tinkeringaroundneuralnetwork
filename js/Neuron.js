class Neuron {
    
    constructor() {
        this.bias = getRandomBetweenMinus1and1();
        this.weight1 = getRandomBetweenMinus1and1();
        this.weight2 = getRandomBetweenMinus1and1();

        //after 1
        this.oldBias = 0;
        this.oldWeight1 = 0;
        this.oldWeight2 = 0;
      }

      compute(input1, input2) {
        let calc = (this.weight1 * input1) + (this.weight2 * input2) + this.bias;
        return sigmoid(calc);
      }

      //after 2
      mutate(){
		let propertyToChange = getRandomIntBetween(1, 3);
		let changeFactor = getRandomBetweenMinus1and1();
		if (propertyToChange == 1){ 
			this.bias += changeFactor; 
		} else if (propertyToChange == 2){ 
			this.weight1 += changeFactor; 
		} else { 
			this.weight2 += changeFactor; 
		};
	}

    forget() {
        this.bias = this.oldBias;
        this.weight1 = this.oldWeight1;
        this.weight2 = this.oldWeight2;
    }

    remember() {
        this.oldBias = this.bias;
        this.oldWeight1 = this.weight1;
        this.oldWeight2 = this.weight2;
    }

}