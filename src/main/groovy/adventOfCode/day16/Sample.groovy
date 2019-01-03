package adventOfCode.day16

import adventOfCode.day16.Instruction

class Sample extends Instruction {
    List<Integer> input
    List<Integer> output

    Sample(input, output, instruction) {
        super(instruction)

        this.input = input
        this.output = output
    }

    String toString() {
        "$code:$args<$input = $output>"
    }
}
