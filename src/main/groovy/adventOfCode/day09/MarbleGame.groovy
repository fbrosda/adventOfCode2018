package adventOfCode.day09

import adventOfCode.util.AbstractChallange

class MarbleGame extends AbstractChallange {

    int players
    int maxValue
    
    MarbleGame() {
        day = 9
        def tmp = (data[0] =~ /^(\d+)\D+(\d+)/).getAt(0)
        players = tmp[1].toInteger()
        maxValue = tmp[2].toInteger()
        
    }

    private long execute() {
        def circle = new LinkedList<Long>()
        def scores = [].withDefault { 0L }
        circle.offerFirst(0)
        (1..maxValue).each { i ->
            if(i % 23 == 0) {
                rotate(circle, -5)
                scores[i % players] += circle.pollFirst() + i
                rotate(circle, 1)
            } else {
                rotate(circle, 1)
                circle.offerFirst(i)
                                
            }
        }
        return scores.max()
    }

    private void rotate(Deque circle, int steps) {
        if( steps > 0 ) {
            (1..steps).each {
                circle.offerFirst(circle.pollLast())
            }
        } else if( steps < 0 ) {
            (1..steps).each {
                circle.offerLast(circle.pollFirst())
            }
        }
    }

    String solution1() {
        return execute()

    }

    String solution2() {
        maxValue *= 100
        return execute()
    }
}
