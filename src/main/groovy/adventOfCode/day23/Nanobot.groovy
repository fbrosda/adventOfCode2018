package adventOfCode.day23

class Nanobot {
    List<Integer> pos
    int radius

    Nanobot(pos, radius) {
        this.pos = pos
        this.radius = radius
    }

    Nanobot(nanoString) {
        def data = nanoString =~ /^pos=<([^>]+)>, r=(\d+)$/
        pos = data[0][1].split(',')*.toInteger()
        radius = data[0][2] as int
    }

    String toString() {
        "pos=<${pos[0]},${pos[1]},${pos[2]}>, r=$radius"
    }

    int distance(Nanobot other) {
        [this.pos, other.pos].transpose().collect { x1, x2 -> Math.abs(x1 - x2) }.sum()
    }
}
