package adventOfCode.day04

import adventOfCode.day04.Duration

class Guard {
    Integer id
    List<Duration> sleepTimes

    Guard(String id) {
        this.id = id.toInteger()
        sleepTimes = []
    }

    void addSleepTime(Integer start, Integer end) {
        sleepTimes.push(new Duration(start, end))
    }

    Integer getTotalSleepTime() {
        return sleepTimes.inject(0) { acc, val -> acc + val.getDuration()}
    }

    Integer getMostSleepMinute() {
        return getMostSleepMinuteAndCount()[1]
    }

    Integer getMostSleepMinuteCount() {
        def tmp = getMostSleepMinuteAndCount()
        return tmp ? tmp[0] : 0
    }

    private Tuple2<Integer, Integer> getMostSleepMinuteAndCount() {
        def tmp = [].withDefault{0}
        sleepTimes.each {
            (it.start..<it.end).each { i ->
                tmp[i]++
            }
        }
        return tmp.withIndex(0).max { it[0] }
    }

    String toString() {
        return "{${id}: ${sleepTimes}}"
    }
}
