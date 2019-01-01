package adventOfCode.day12

import adventOfCode.util.AbstractChallange

class Plants extends AbstractChallange {
    String initialState
    Map rules
    Map memo
    Map offsetMemo
    long offset

    Plants() {
        day = 12

        def tmp = data.pop()
        def inputStart = tmp.indexOf('#')
        initialState = tmp.substring(inputStart, tmp.size())
        data.pop()

        rules = [:]
        data.each {
            def rule = it.split('=>')
            rules.put(rule[0].trim(), rule[1].trim())
        }

        memo = [:]
        offsetMemo = [:]
    }

    private String iterate(count) {
        offset = 0
        return iterate(initialState, 0, count)
    }

    private String iterate(state, i, count) {
        if(i == count) {
            return state
        } else {
            if(!memo.containsKey(state)) {
                memo.put(state, i)
                offsetMemo.put(i, offset)
            } else {
                def first = memo.get(state)
                if(first != i) { // avoid side effects with multiple calls
                    def len = i - first

                    def rest = count - i
                    def rem = Math.floor(rest / len)

                    def last = first + (rest % len)

                    def firstOffset = offsetMemo.get(first)
                    def remOffset = offsetMemo.get((int) last) - firstOffset
                    def offsetDiff = offset - firstOffset

                    offset = offset + rem * offsetDiff + remOffset
                    return memo.find { it.value == last }
                }
            }

            def nextGen = getNextGen(state)
            return iterate(nextGen, i+1, count)
        }
    }

    private String getNextGen(currentGen) {
        def input = fixLength(currentGen)
        (0..<input.size()).collect { i ->
            if(i < 2 || i > input.size() - 3) {
                return '.'
            }
            def key = input.substring(i-2, i+3)
            rules.get(key, '.')
        }.join('')
    }

    private String fixLength(currentGen) {
        def ret = currentGen
        if(!currentGen.startsWith('...')) {
            ret = '...' + ret
            offset += 3
        } else {
            while(ret.startsWith('....')) {
                ret = ret.substring(1)
                offset -= 1
            }
        }
        if(!currentGen.endsWith('...')) {
            ret = ret + '...'
        }
        return ret
    }

    private long countPlants(curGen) {
        (curGen.split('') as List).withIndex().inject(0) { ret, i ->
            if(i[0] == '#') {
                return ret += i[1] - offset
            } else {
                return ret
            }
        }
    }

    String solution1() {
        countPlants(iterate(20))
    }

    String solution2() {
        countPlants(iterate(50000000000))
    }
}
