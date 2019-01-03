package adventOfCode.day16

class Instruction {
    List<Integer> args
    int code

    Instruction(instruction) {
        code = instruction[0]
        args = instruction[1..3]
    }

    String toString() {
        "$code:$args"
    }
}
