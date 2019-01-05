package adventOfCode.day16

import adventOfCode.day16.Opcode

class ChronalDevice {
    private Map<Integer, Opcode> int2opcode
    private List<Integer> registers
    private int ipr

    int sol1

    ChronalDevice() {
        int2opcode = [:]
        registers = [0, 0, 0, 0, 0, 0, 0]
        ipr = 6 // hidden register which will never be modified by the code
    }

    public void resetRegisters() {
        registers = [0, 0, 0, 0, 0, 0, 0]
    }

    public List getRegisters() {
        registers
    }

    public void setRegisters(registers) {
        this.registers = registers
        while(registers.size() < 7) {
            registers << 0
        }
    }

    public void exec(List<Instruction> instructions, raw = true) {
        if(raw) {
            assert int2opcode.size() == Opcode.values().size() // make sure the mapping is initialized
        }

        if(instructions[0].name?.startsWith('#IP')) {
            ipr = instructions.pop().args[0]
        }

        while(registers[ipr] < instructions.size()) {
            def opcode
            def instruction = instructions[registers[ipr]]

            if(raw) {
                opcode = int2opcode.get(instruction.code)
            } else {
                opcode = instruction.name as Opcode
            }

            opcode.apply(instruction.args, registers)
            registers[ipr]++
        }
    }

    public void calibrate(List<Sample> samples) {
        def possibleMatches = [:].withDefault { Opcode.values() as List }
        samples.each { sample ->
            def tmp = []
            Opcode.values().each { op ->
                if(sample.output == op.exec(sample.args, sample.input)) {
                    tmp << op
                }
            }
            if(tmp.size() > 2) {
                sol1++
            }
            possibleMatches.put(sample.code, possibleMatches.get(sample.code).intersect(tmp))
        }
        def pq = new PriorityQueue(possibleMatches.size(), new Comparator<Tuple>() {
            public int compare(Tuple t1, Tuple t2) {
                t1[0].size() <=> t2[0].size()
            }
        })
        possibleMatches.each {
            pq.add(new Tuple(it.getValue(), it.getKey()))
        }
        while(!pq.isEmpty()) {
            def head = pq.poll()
            int2opcode.put(head[1], head[0][0])
            def tmp = []
            pq.each { tuple ->
                if(tuple[0].contains(head[0][0])) {
                    tuple[0].remove(head[0][0])
                    tmp << tuple
                }
            }
            // Do NOT do this while iterating the PriorityQueue
            tmp.each { pq.remove(it) }
            pq.addAll(tmp)
        }
    }
}
