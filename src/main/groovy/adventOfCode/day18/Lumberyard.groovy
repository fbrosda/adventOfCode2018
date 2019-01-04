package adventOfCode.day18

import adventOfCode.util.AbstractChallange

class Lumberyard extends AbstractChallange {

    private List<List<String>> ground
    private Map memo

    Lumberyard() {
        day = 18

        ground = data.collect { it.split('') }
        memo = [:]
    }

    private List update(map) {
        def tmp = [].withDefault { [] }
        map.eachWithIndex { row, y ->
            row.eachWithIndex { cell, x ->
                tmp[y][x] = updateOne(map, x, y)
            }
        }
        return tmp
    }

    private String updateOne(map, x, y) {
        def val = map[y][x]
        def count = [0, 0, 0]
        for(def j = Math.max(0, y - 1); j < Math.min(map.size(), y + 2); j++) {
            for(def i = Math.max(0, x - 1); i < Math.min(map[j].size(), x + 2); i++) {
                if(i == x && j == y) {
                    continue
                }
                if(map[j][i] == '.') count[0]++
                else if(map[j][i] == '|') count[1]++
                else count[2]++
            }
        }
        assert count.sum() <= 8

        if(val == '.') {
            if(count[1] > 2) '|'
            else '.'
        }
        else if(val == '|') {
            if(count[2] > 2) '#'
            else '|'
        } else {
            if(count[1] > 0 && count[2] > 0) '#'
            else '.'
        }
    }

    private void printGround(map) {
        def tmp = map*.join()
        tmp.each {
            println it
        }
    }

    private List iterate(map, i, count) {
        if(i >= count) {
            return countTrees(map*.join().join())
        } else {
            def key = map*.join().join()
            def index = memo.get(key)
            if(index != null && i != index) {
                def t = index + (count - i) % (i - index)
                def tmp = memo.find { it.value == t }
                return countTrees(tmp.getKey())
            } else {
                memo.put(map*.join().join(), i)
                return iterate(update(map), i+1, count)
            }
        }
    }

    private List countTrees(String groundStr) {
        groundStr.inject([0, 0]) { ret, cell ->
            if(cell == '|') ret[0]++
            else if(cell == '#') ret[1]++
            return ret
        }
    }

    String solution1() {
        def count = iterate(ground, 0, 10)
        return count[0] * count[1]
    }

    String solution2() {
        def count = iterate(ground, 0, 1000000000)
        return count[0] * count[1]
    }
}
