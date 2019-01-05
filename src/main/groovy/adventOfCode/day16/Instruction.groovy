package adventOfCode.day16

class Instruction {
    List<Integer> args
    String name
    int code

    Instruction(instruction, raw = true) {
        if(raw) {
            code = instruction[0]
        } else {
            name = instruction[0].toUpperCase()
        }
        args = instruction[1..Math.min(instruction.size() - 1, 3)]*.toInteger()
    }

    String toString() {
        "$name($code):$args"
    }

    String toGroovy() {
        switch(name) {
            case 'SETI':
                "r[${args[2]}] = ${args[0]}"
                break
            case 'SETR':
                "r[${args[2]}] = r[${args[0]}]"
                break
            case 'BANI':
                "r[${args[2]}] = r[${args[0]}] & ${args[1]}"
                break
            case 'BORI':
                "r[${args[2]}] = r[${args[0]}] | ${args[1]}"
                break
            case 'EQRI':
                "r[${args[2]}] = r[${args[0]}] == ${args[1]} ? 1 : 0"
                break
            case 'EQRR':
                "r[${args[2]}] = r[${args[0]}] == r[${args[1]}] ? 1 : 0"
                break
            case 'GTIR':
                "r[${args[2]}] = ${args[0]} > r[${args[1]}] ? 1 : 0"
                break
            case 'GTRR':
                "r[${args[2]}] = r[${args[0]}] > r[${args[1]}] ? 1 : 0"
                break
            case 'ADDR':
                "r[${args[2]}] = r[${args[0]}] + r[${args[1]}]"
                break
            case 'ADDI':
                "r[${args[2]}] = r[${args[0]}] + ${args[1]}"
                break
            case 'MULI':
                "r[${args[2]}] = r[${args[0]}] * ${args[1]}"
                break
        }
    }
}
