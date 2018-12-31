package adventOfCode.day10

import adventOfCode.util.AbstractChallange
import adventOfCode.day10.Point

class StarLetters extends AbstractChallange {
    List<Point> stars
    int time = 0

    StarLetters() {
        day = 10

        stars = data.collect{
            def tmp = it =~ /^position=<([^,]+),([^>]+)>\svelocity=<([^,]+),([^>]+)>$/
            new Point(tmp.getAt(0).drop(1).collect{ it.toInteger() })
        }
    }

    private void fastForward() {
        // The stars only move linear, so there must be a globla minimum somewhere
        def cur = Integer.MAX_VALUE
        def next = nextDist()
        while(next < cur) {
            stars.each{ it.move() }
            time++

            cur = next
            next = nextDist()
        }
    }

    private int nextDist() {
        Math.abs(stars.max{ it.y + it.velY }.y - stars.min{ it.y + it.velY}.y)
    }

    private String printStars() {
        def maxX = stars.max{ it.x }.x
        def minY = stars.min{ it.y }.y

        def ret = [].withEagerDefault{['.'] * (maxX+1)}
        stars.each { star ->
            ret.get(star.y - minY).set(star.x, '#')
        }
        return "\n${ret.collect { it.join('') }.join('\n')}"
    }

    String solution1() {
        fastForward()
        return printStars()
    }

    String solution2() {
        if(!time) {
            fastForward()
        }
        return time
    }
}
