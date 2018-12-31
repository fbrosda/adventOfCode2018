package adventOfCode.day11

import adventOfCode.util.AbstractChallange

class PowerLevel extends AbstractChallange {
    int gsn;
    List<List<Integer>> grid
    int gridSize = 300

    PowerLevel() {
        day = 11

        gsn = data[0].toInteger()

        // Create the summed area table
        // Doing recursive calls if necessary, while at the same time memoizing
        // the value in the grid itself :-)
        grid = [].withDefault { x ->
            [].withDefault { y ->
                if(x < 1 || y < 1) {
                    return 0
                }
                def xy = calcPowerLevel(x, y)
                def yp = y > 1 ? grid[x][y - 1] : 0
                def xp = x > 1 ? grid[x - 1][y] : 0
                def xpyp = x > 1 && y > 1 ? grid[x-1][y-1] : 0
                xy + yp + xp - xpyp
            }
        }
    }

    private int calcPowerLevel(x, y) {
        def rackId = x + 10
        ((int) ((((rackId * y) + gsn) * rackId) / 100)) % 10 - 5
    }

    private int getNxNPower(xStart, yStart, cellSize) {
        def xEnd = xStart + cellSize
        def yEnd = yStart + cellSize
        grid[xStart][yStart] + grid[xEnd][yEnd] - grid[xStart][yEnd] - grid[xEnd][yStart]
    }

    private List<Object> getMaxCell(cellSize) {
        def pValues = (0..gridSize - cellSize + 1).collect { x ->
            (0..gridSize - cellSize + 1).collect { y ->
                getNxNPower(x, y, cellSize)
            }
        }
        pValues.withIndex(1).inject([Integer.MIN_VALUE, [-1, -1]]) { ret, xi ->
            def yMax = xi[0].withIndex(1).max { yi -> yi[0] }
            if(yMax[0] > ret[0]) {
                ret[0] = yMax[0]
                ret[1] = [xi[1], yMax[1]]
            }
            return ret
        }
    }

    String solution1() {
        getMaxCell(3)[1]
    }

    String solution2() {
        (1..300).collect{ getMaxCell(it) }.withIndex(1).max { it[0][0] }
    }
}
