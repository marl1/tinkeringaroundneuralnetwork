let network = new Network();

//after 4
let trainingDataList = [
    {height: 185, weight:85},
    {height: 175, weight:78},
    {height: 190, weight:95},
    {height: 150, weight:52},
    {height: 163, weight:59},
    {height: 152, weight:47},
];
let answers = [1, 1, 1, 0, 0, 0];
console.log("trainingDataList", trainingDataList);
network.train(trainingDataList, answers);


console.log("Is it a man ?" + network.predict(180, 80).toFixed(2));
console.log("Is it a man ?" + network.predict(170, 70).toFixed(2));
console.log("Is it a man ?" + network.predict(190, 90).toFixed(2));
console.log("Is it a man ?" + network.predict(150, 40).toFixed(2));
console.log("Is it a man ?" + network.predict(149, 42).toFixed(2));
console.log("Is it a man ?" + network.predict(152, 50).toFixed(2));