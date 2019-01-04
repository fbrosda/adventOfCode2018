package adventOfCode.day17

import adventOfCode.util.AbstractChallange

class ReservoirResearch extends AbstractChallange {

    int yMin
    int yMax

    int fountainX
    List ground

    ReservoirResearch() {
        day = 17

        def coords = []
        def xMinMax = [Integer.MAX_VALUE, Integer.MIN_VALUE]
        def yMinMax = [Integer.MAX_VALUE, Integer.MIN_VALUE]

        data.collect { l ->
            def entry
            if(l.startsWith('x')) {
                def t = l =~ /^x=(\d+), y=(\d+)..(\d+)$/
                def x = t[0][1] as int
                def y1 = t[0][2] as int
                def y2 = t[0][3] as int
                entry = [[x], [y1, y2]]

                xMinMax[0] = x < xMinMax[0] ? x : xMinMax[0]
                xMinMax[1] = x > xMinMax[1] ? x : xMinMax[1]

                yMinMax[0] = y1 < yMinMax[0] ? y1 : yMinMax[0]
                yMinMax[1] = y2 > yMinMax[1] ? y2 : yMinMax[1]
            } else {
                def t = l =~ /^y=(\d+), x=(\d+)..(\d+)$/
                def y = t[0][1] as int
                def x1 = t[0][2] as int
                def x2 = t[0][3] as int
                entry = [[x1, x2], [y]]

                yMinMax[0] = y < yMinMax[0] ? y : yMinMax[0]
                yMinMax[1] = y > yMinMax[1] ? y : yMinMax[1]

                xMinMax[0] = x1 < xMinMax[0] ? x1 : xMinMax[0]
                xMinMax[1] = x2 > xMinMax[1] ? x2 : xMinMax[1]
            }
            coords << entry
        }

        yMin = yMinMax[0]
        yMax = yMinMax[1]

        ground = [].withDefault { [].withDefault { '.' }}

        def offset = xMinMax[0] - 1
        coords.each {
            if(it[0].size() == 1) {
                (it[1][0]..it[1][1]).each { y ->
                    ground[y][it[0][0] - offset] = '#'
                }
            } else {
                (it[0][0]..it[0][1]).each { x ->
                    ground[it[1][0]][x - offset] = '#'
                }
            }
        }
        fountainX = 500 - offset
        ground[0][fountainX] = '+'
    }

    private void printGrid() {
        ground.each { line ->
            line.each {
                print it ? it : '.'
            }
            print '\n'
        }
    }

    private void fill() {
        fill(0, fountainX)
    }

    private boolean fill(y, x) {
        if(y >= yMax) {
            return false
        }

        if(ground[y+1][x] == '.') {
            ground[y+1][x] = '|'
            fill(y+1, x)
        }

        def rightBorder = false
        if(ground[y+1][x] =~ /#|~/) {
            if(ground[y][x+1] == '.') {
                ground[y][x+1] = '|'
                rightBorder |= fill(y, x+1)
            } else if(ground[y][x+1] == '#') {
                rightBorder = true
            }
        }

        def leftBorder = false
        if(ground[y+1][x] =~ /#|~/) {
            if(ground[y][x-1] == '.') {
                ground[y][x-1] = '|'
                leftBorder |= fill(y, x-1)
        } else if(ground[y][x-1] == '#') {
                leftBorder = true
            }
        }

        if(leftBorder && rightBorder) {
            ground[y][x] = '~'
            fillLevel(y, x+1, 1)
            fillLevel(y, x-1, -1)
        }
        return leftBorder || rightBorder
    }

    private void fillLevel(y, x, dir) {
        def cur = x
        while(ground[y][cur] == '|') {
            ground[y][cur] = '~'
            cur += dir
        }
    }

    private int countByType(typeRX) {
        ground.withIndex().inject(0) { y, rowI ->
            if(rowI[1] >= yMin) {
                y += rowI[0].inject(0) { x, cell ->
                    x += cell =~ typeRX ? 1 : 0
                }
            }
            return y
        }
    }

    String solution1() {
        fill()
        countByType(/~|\|/)
    }

    String solution2() {
        countByType(/~/)
    }
}
