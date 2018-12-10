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
       while(Math.abs(stars.max{ it.y }.y - stars.min{ it.y }.y) > 9) {
            stars.each{ it.move() }
            time++
        }
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
