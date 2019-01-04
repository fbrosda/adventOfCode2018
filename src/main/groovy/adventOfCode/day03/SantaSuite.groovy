package adventOfCode.day03

import adventOfCode.util.AbstractChallange

class SantaSuite extends AbstractChallange {
    private List<Rectangle> rectangles

    SantaSuite() {
        day = 3
        rectangles = parseData()
    }

    List<Rectangle> parseData() {
        def ret =  data.collect{ (it =~ /^[^@]+@\s(\d+),(\d+):\s(\d+)x(\d+)$/).getAt(0).drop(1).collect{ it.toInteger() } }
        ret = ret.collect{ new Rectangle( it ) }
        return ret;
    }

    String solution1() {
        def matrix = [].withDefault { [].withDefault{ 0 }}
        rectangles.each { rect ->
            (rect.y0..<rect.y1).each { i ->
                (rect.x0..<rect.x1).each { j ->
                    matrix[j][i] += 1;
                }
            }
        }

        return matrix.flatten().findAll{ it > 1 }.size()
    }

    String solution2() {
        def matrix = [].withDefault { [].withDefault{ 0 }}
        def keys = (1..data.size()).toList()

        rectangles.withIndex(1).each { rect, index ->
            (rect.y0..<rect.y1).each { i ->
                (rect.x0..<rect.x1).each { j ->
                    def val = matrix[j][i]
                    if( val != 0 ) {
                        keys[val-1] = null
                        keys[index-1] = null
                    }
                    matrix[j][i] = index;
                }
            }
        }

        return keys.find{ it }
    }
}
