package adventOfCode.day23

import adventOfCode.util.AbstractChallange

class Nanobots extends AbstractChallange {
    List<Nanobot> nanobots

    Nanobots() {
        day = 23

        nanobots = data.collect { new Nanobot(it) }
    }

    String solution1() {
        def strongest = nanobots.max { it.radius }
        nanobots.findAll { it.distance(strongest) <= strongest.radius }.size()
    }

    String solution2() {
        
    }
}
