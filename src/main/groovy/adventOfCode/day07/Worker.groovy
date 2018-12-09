package adventOfCode.day07

import adventOfCode.day07.Work

class Worker {
    Work work
    int time
    int id

    Worker(int id) {
        this.id = id
    }

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

    Work releaseWork() {
        if(work && !isBusy()) {
            def tmp = work
            this.work = null
            return tmp
        }
        return null
    }

    String toString() {
        return "{$id: (${work}: ${time})}"
    }
}
