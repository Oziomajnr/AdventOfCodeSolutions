import * as fs from 'fs';

fs.readFile('input1.txt', 'utf8', function (err, input) {
    console.log(input.split('\n').map((line) => {
        let splitData = line.split(":")
        let gameNumber = +splitData[0].split(" ")[1]

        function validateCubeData(regex: RegExp, max: number): boolean {
            return Math.max(...splitData[1].match(regex).map((value) => +value)) > max
        }

        if (validateCubeData(/(\d+(?=( green)))/g, 13)
            || validateCubeData(/(\d+(?=( red)))/g, 12)
            || validateCubeData(/(\d+(?=( blue)))/g, 14)) return 0
        return gameNumber
    }).reduce((a, b) => a + b, 0))
});

fs.readFile('input1.txt', 'utf8', function (err, input) {
    console.log(input.split('\n').map((line) => {
        let splitData = line.split(":")

        function findMinimumCubeData(regex: RegExp): number {
            return Math.max(...splitData[1].match(regex).map((value) => +value))
        }

        return (findMinimumCubeData(/(\d+(?=( green)))/g)
            * findMinimumCubeData(/(\d+(?=( red)))/g)
            * findMinimumCubeData(/(\d+(?=( blue)))/g))
    }).reduce((a, b) => a + b, 0))
});
