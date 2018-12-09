package adventOfCode.day07

import adventOfCode.util.AbstractChallange
import adventOfCode.day07.Work
import adventOfCode.day07.Worker

class DAG extends AbstractChallange {

    DAG() {
        day = 7
    }

    private List<Work> createGraph() {
        def tmp = [:].withDefault { key -> new Work(key) }
        data.each{ line ->
            def ids = (line =~ /^Step\s(\w)[^A-Z]+([A-Z]).*$/).getAt(0).drop(1)
            def pre = tmp.get(ids[0])
            def suc = tmp.get(ids[1])
            pre.addSuccessor(suc)
            suc.addPredecessor(pre)

        }
        return tmp.values() as List
    }

    private PriorityQueue<Work> getTaskQueue(List allTasks) {
        def queue = new PriorityQueue<Work>()
        
        queue.addAll(allTasks.findAll { it.nrOfPredesessors() == 0 })
        return queue
    }

    String solution1() {
        def allTasks = createGraph()
        def openTasks = getTaskQueue(allTasks)
        def ret = []

        while(openTasks.size() > 0) {
            def task = openTasks.poll()
            ret << task.id
            task.getSuccessors().each { suc ->
                def preCount = suc.removePredecessor(task)
                if(preCount == 0) {
                    openTasks.add(suc)
                }
            }
        }
        return ret.join()
    }

    String solution2() {
        def allTasks = createGraph()
        def openTasks = getTaskQueue(allTasks)
        def workers = (1..5).collect{ new Worker(it) }
        def ret = 0

        while(true) {
            def busyWorkers = workers.findAll{ it.isBusy() }
            def xx = busyWorkers.min{ it.time }
            def step = xx ? xx.time : 0
            def finished = busyWorkers.each{ it.reduceTime(step) }.collect{ it.releaseWork() }.findAll()
            ret += step

            finished.each { work ->
                work.getSuccessors().each { suc ->
                    def preCount = suc.removePredecessor(work)
                    if(preCount == 0) {
                        openTasks.add(suc)
                    }
                }
            }

            def free = workers.findAll { !it.isBusy() }
            free.each{ worker ->
                def task = openTasks.poll()
                if(task) {
                    worker.acceptWork(task)
                }
            }

            if(!workers.findAll{ it.isBusy() }) break
        }

        return ret
    }
}
