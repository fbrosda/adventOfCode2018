package adventOfCode.day19

import adventOfCode.util.AbstractChallange
import adventOfCode.day16.ChronalDevice
import adventOfCode.day16.Instruction

class InstructionPointer extends AbstractChallange {
    private ChronalDevice cd
    private List<Instruction> instructions

    InstructionPointer() {
        day = 19

        instructions = []
        data.each {
            instructions << new Instruction(it.split(' '), false)
        }

        cd = new ChronalDevice()
    }

    String solution1() {
        // cd.exec(instructions, false)
        // cd.getRegisters()
    }

    String solution2() {
        cd.resetRegisters()
        cd.setRegisters([1])
        cd.exec(instructions, false)
        cd.getRegisters()
    }
}
