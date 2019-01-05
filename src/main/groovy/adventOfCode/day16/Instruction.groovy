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
}
