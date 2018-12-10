package adventOfCode.day10

class Point {
    int x
    int y

    int velX
    int velY

    Point(List<Integer> data) {
        x = data[0]
        y = data[1]

        velX = data[2]
        velY = data[3]
    }

    void move() {
        x += velX;
        y += velY
    }

    String toString() {
        return "(${x},${y}),<${velX},${velY}>"
    }
}
