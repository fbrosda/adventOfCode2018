package adventOfCode.day04

class Duration {
    Integer start
    Integer end

    Duration(Integer start, Integer end) {
        this.start = start
        this.end = end
    }

    Integer getDuration() {
        return end - start
    }

    String toString() {
        return "${start}-${end}: ${getDuration()}"
    }
}
