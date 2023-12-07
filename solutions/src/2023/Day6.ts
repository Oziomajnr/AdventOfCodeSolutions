typescript
import * as fs from 'fs';

fs.readFile('input1.txt', 'utf8', (err, input) => {
    const lines = input.split('\n');
    const times = lines[0].match(/\d+/g).map(value => +value);
    const distance = lines[1].match(/\d+/g).map(value => +value);
    console.log(
        times.map((time, index) => {
            for (let x = 0; x < time; x++) {
                if ((time - x) * x > distance[index]) {
                    let k = (time - (2 * x)) + 1;
                    return k;
                }
            }
            throw new Error('No solution found');
        }).reduce((a, b) => a * b, 1),
    );
});
