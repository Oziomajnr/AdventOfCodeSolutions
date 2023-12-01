import * as fs from 'fs';

fs.readFile('input1.txt', 'utf8', function(err, input){ 
  console.log(input.split('\n').map((line) => {
    let digitmap = new Map<string, string>([
        ["1", "1"],["2", "2"],["3", "3"],["4", "4"],["5", "5"],
        ["6", "6"],["7", "7"],["8", "8"],["9", "9"]
    ])
    let forWardMap = new Map([
            ...digitmap.entries(),
        ...new Map<string, string>( [["one", "1"],["two", "2"],["three", "3"],["four", "4"],
        ["five", "5"],["six", "6"],["seven", "7"],["eight", "8"],["nine", "9"],]).entries()
    ])

    let reverseMap = new Map([
        ...digitmap.entries(),
        ...new Map<string, string>(  [["eno", "1"],["owt", "2"],["eerht", "3"],["ruof", "4"],["evif", "5"],["xis", "6"],["neves", "7"],["thgie", "8"],["enin", "9"],]).entries()
    ])
    const line2 = line.split("").reverse().join("")
      const matched = line.match(/(\d|eight|one|two|three|four|five|six|seven|nine)/g)
      const matched1 = line2.match(/(\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin)/g)
      return +(forWardMap.get(matched[0]) +  reverseMap.get(matched1[0]))
  }).reduce((acc, curr) => acc + curr, 0))
}); 
