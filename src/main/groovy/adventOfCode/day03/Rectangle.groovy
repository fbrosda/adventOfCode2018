package adventOfCode.day03

class Rectangle {

    Integer x0
    Integer x1
    Integer y0
    Integer y1
    
    Rectangle(List<Integer> startAndOffset) {
        def i = 0
        x0 = startAndOffset[i++]
        y0 = startAndOffset[i++]

        x1 = startAndOffset[i-2] + startAndOffset[i++]
        y1 = startAndOffset[i-2] + startAndOffset[i++]
    }

    String toString() {
        return "(${x0}, ${y0}) (${x1},${y1})"
    }
}
