package adventOfCode.day16

import adventOfCode.day16.Opcode

class ChronalDevice {
    private Map<Integer, Opcode> int2opcode
    private List<Integer> registers
    int sol1

    ChronalDevice() {
        int2opcode = [:]
        registers = [0, 0, 0, 0]
    }

    ChronalDevice(registers) {
        int2opcode = [:]
        this.registers = registers
    }

    public void resetRegisters() {
        registers = [0, 0, 0, 0]
    }

    public List getRegisters() {
        registers
    }

    public void exec(List<Instruction> instructions) {
        instructions.each {
            int2opcode.get(it.code).apply(it.args, registers)
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
