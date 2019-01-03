package adventOfCode.day16

import adventOfCode.util.AbstractChallange
import adventOfCode.day16.ChronalDevice
import adventOfCode.day16.Sample


class ChronalClassification extends AbstractChallange {
    List<Sample> samples
    List<Instruction> instructions
    ChronalDevice cd

    ChronalClassification() {
        day = 16

        def i = 0
        samples = []
        while(data[i].startsWith('Before')) {
            def rx = /(\d+), (\d+), (\d+), (\d+)/
            def input = (data[i] =~ rx)[0][1..4]*.toInteger()
            def output = (data[i+2] =~ rx)[0][1..4]*.toInteger()
            def instruction = data[i+1].split(' ')*.toInteger()
            samples << new Sample(input, output, instruction)
            i += 4
        }

        instructions = []
        while(++i < data.size()) {
            if(!data[i].isEmpty()) {
                instructions << new Instruction(data[i].split(' ')*.toInteger())
            }
        }

        cd = new ChronalDevice()
    }

    String solution1() {
        cd.calibrate(samples)
        cd.sol1
    }

    String solution2() {
        cd.exec(instructions)
        cd.getRegisters()
    }
}
