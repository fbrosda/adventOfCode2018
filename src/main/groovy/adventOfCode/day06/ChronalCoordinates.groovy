package adventOfCode.day06

import adventOfCode.util.AbstractChallange

class ChronalCoordinates extends AbstractChallange {

    List<List<Integer>> places

    ChronalCoordinates() {
        day = 6

        places = data.collect {
            def x = it.split(',')
            x.collect { it.toInteger() }
        }
    }

    private void loopRectangle( cl ) {
        def minX = places.min { it[0] }[0]
        def maxX = places.max { it[0] }[0]

        def minY = places.min { it[1] }[1]
        def maxY = places.max { it[1] }[1]

        for(def x = minX; x <= maxX; x++) {
            for(def y = minY; y <= maxY; y++) {
                def atBorder = x == minX || x == maxX || y == minY || y == maxY
                cl(x, y, atBorder)
            }
        }
    }

    private int nearestPlace( point ) {
        def distances = places.collect { distance(it, point) }
        def min = distances.withIndex().min { it[0] }
        if(distances.findAll { it == min[0] }.size() > 1) {
            return -1
        } else {
            return min[1]
        }
    }

    private int getSumDistance( point ) {
        places.collect { distance(it, point) }.sum()
    }

    private int distance( p1, p2 ) {
        [p1, p2].transpose().collect { Math.abs( it[0] - it[1] )}.sum()
    }

    String solution1() {
        def areas = [].withDefault { 0 }

        loopRectangle() { x, y, atBorder ->
            def next = nearestPlace([x,y])
            if(next > -1) {
                if(atBorder) {
                    areas[next] = -1
                } else if(areas[next] > -1) {
                    areas[next]++
                }
            }
        }
        return areas.max()

    }

    String solution2() {
        def maxDist = 10000
        def area = 0
        loopRectangle() { x, y, _ ->
            if(getSumDistance([x,y]) <= maxDist) {
                area++
            }
        }
        return area
    }
}
