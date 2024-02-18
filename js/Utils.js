
function getRandomBetweenMinus1and1() {
    return (Math.random() - 0.5) * 2; //tester sans le *2
}

function sigmoid(number) {
    return 1 / (1 + Math.exp(-number));
}

//after 3
function getRandomIntBetween(min, max) {
    const minCeiled = Math.ceil(min);
    const maxFloored = Math.floor(max);
    return Math.floor(Math.random() * (maxFloored - minCeiled) + minCeiled);
  }

function meanSquareLoss(predictedAnswers, correctAnswers) {
    let sumSquare = 0;
    for (let i = 0; i < correctAnswers.length; i++) {
        let error = correctAnswers[i] - predictedAnswers[i];
        sumSquare += (error * error);
    }
    return sumSquare / (correctAnswers.length);
}
  