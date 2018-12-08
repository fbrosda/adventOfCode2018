package adventOfCode.day07

import adventOfCode.day07.Work

class Worker {
    Work work
    int time

    boolean isBusy() {
        return time > 0
    }

    boolean hasWork() {
        return work != null
    }

    void reduceTime(int time) {
        this.time -= time
    }

    void acceptWork(Work work) {
        this.work = work
        this.time = work.getDuration()
        work.isAssigned = true
    }

    String toString() {
        return "${work}: ${time}"
    }
}
