package adventOfCode.day02

import adventOfCode.util.AbstractChallange
import adventOfCode.day02.LazyProduct

class InventoryManagement extends AbstractChallange {
    InventoryManagement() {
        day = 2
    }

    private boolean charRepeatsNTimes( times, word ) {
        while( word.length() > 0) {
            def pre = word.length()
            def ch = word.take(1)
            word = word.replace(ch, '')
            if(pre - word.length() == times) {
                return true
            }
        }
        return false
    }

    private List<List<String>> zipStrings(s1, s2) {
        return s1.withIndex(0).collect{ [it[0], s2[it[1]]] }
    }


    String solution1() {
        def nTouple = data.collect{ charRepeatsNTimes(2, it) }.findAll{ it }.size()
        def nTriple = data.collect{ charRepeatsNTimes(3, it) }.findAll{ it }.size()

        return nTouple * nTriple
    }

    String solution2() {
        def iter = new LazyProduct(l1: data, l2: data)

        def ofByOne = iter.find{ zipStrings(it[0], it[1]).findAll{ it[0] == it[1] }.size() == it[0].size() - 1 }
        return zipStrings(ofByOne[0], ofByOne[1]).findAll{ it[0] == it[1] }.inject('') { res, i -> res + i[0] }

    }

}
