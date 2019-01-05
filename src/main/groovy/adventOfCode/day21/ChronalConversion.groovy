package adventOfCode.day21

import adventOfCode.util.AbstractChallange
import adventOfCode.day16.Instruction

class ChronalConversion extends AbstractChallange {
    ChronalConversion() {
        day = 21
    }

    private String writeProgram(initVal, debugIndex, first) {
        def instructions = []
        data.each {
            instructions << new Instruction(it.split(' '), false)
        }
        def ip = instructions.pop()
        def prog = []
        prog << "def r = [${initVal},0,0,0,0,0]"
        prog << 'def memo = [:]'
        prog << 'def ret = []'
        prog << "while(r[${ip.args[0]}] < ${instructions.size()}) {"
        prog << "switch(r[${ip.args[0]}]) {"
        prog.addAll(instructions.withIndex().collect { a, i ->
            if(i == debugIndex) {
                def tmp = "case($i): ${a.toGroovy()};\n"
                // tmp += "println r[${a.args[0]}]\n"
                tmp += "ret << r[${a.args[0]}]\n"
                tmp += "if($first){ r[${ip.args[0]}] = 1000 }else if(memo.get(${a.args[0]})) { r[${ip.args[0]}] = 1000 } else { memo.put(r[${a.args[0]}], true) }\n"
                tmp += 'break'
            } else {
                "case($i): ${a.toGroovy()}; break"
            }
        })
        prog << '}'
        prog << "r[${ip.args[0]}]++"
        prog << '}'
        prog << 'ret'
        return prog.join('\n')
    }

    String solution1() {
        def prog = writeProgram(0, 28, true)
        Eval.me( prog ).first()
    }

    // Reverse engineered assembler code, as brute forcing the solution takes way to long
    String solution2() {
        def memo = [:]
        def list = []

        long a = 15028787
        long b = 65536

        while(true) {
            long c = b % 256
            a += c
            a = (a%(2**24) * 65899) % (2**24)
            if(b < 256){
                list << a
                b = a | (2**16)
                if(memo.get(b)) {
                    break
                }
                memo.put(b, true)
                a = 15028787
                continue
            }
            b = b/256
        }
        list[list.size() - 2]
        // def prog = writeProgram(0, 28, false)
        // def ret = Eval.me( prog )
        // println ret[ret.size() - 2]
        // return ret
    }
}
