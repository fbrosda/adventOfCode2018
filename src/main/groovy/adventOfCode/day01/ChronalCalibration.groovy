package adventOfCode.day01

import adventOfCode.util.AbstractChallange

class ChronalCalibration extends AbstractChallange {

    ChronalCalibration() {
        day = 1
    }
    
    String solution1() {
        return data.collect{ it.toInteger() }.inject(0) { acc, val -> acc + val }
    }

    String solution2() {
        Set<Integer> seen = []
        def i = 1;
        def cur = data[0].toInteger();

        while(!seen.contains(cur)) {
            seen.add(cur)
            cur += data[i].toInteger()
            i = (i+1) % data.size()
        }
        return cur
    }
}
