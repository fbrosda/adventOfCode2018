package adventOfCode.day07

import adventOfCode.util.AbstractChallange
import adventOfCode.day07.Work
import adventOfCode.day07.Worker

class DAG extends AbstractChallange {

    List<Work> works;

    DAG() {
        day = 7
    }

    private void createGraph() {
        def tmp = [:].withDefault { key -> new Work(key) }
        data.each{ line ->
            def ids = (line =~ /^Step\s(\w)[^A-Z]+([A-Z]).*$/).getAt(0).drop(1)
            def pre = tmp.get(ids[0])
            def suc = tmp.get(ids[1])
            pre.addSuccessor(suc)
            suc.addPredecessor(pre)

        }
        works = tmp.values() as List
    }

    private PriorityQueue<Work> getTaskQueue() {
        def queue = new PriorityQueue<Work>()
        
        queue.addAll(works.findAll { it.nrOfPredesessors() == 0 })
        return queue
    }

    String solution1() {
        createGraph()
        def queue = getTaskQueue()
        def ret = []

        while(queue.size() > 0) {
            def work = queue.poll()
            ret << work.id
            work.getSuccessors().each { suc ->
                def preCount = suc.removePredecessor(work)
                if(preCount == 0) {
                    queue.add(suc)
                }
            }
        }
        return ret.join()
    }

    String solution2() {
        createGraph()
        def queue = getTaskQueue()
        def workers = (1..5).collect{ new Worker() }
        def ret = 0
        def step = 0

        while(queue.size() > 0) {
            ret += step
            workers.findAll{ it.isBusy() }.each{ it.time -= step }
            def finished = workers.findAll{ it.hasWork() && !it.isBusy() }
            finished.each { worker ->
                def work = worker.work
                queue.remove(work)
                worker.work = null
                
                work.getSuccessors().each { suc ->
                    def preCount = suc.removePredecessor(work)
                    if(preCount == 0) {
                        queue.add(suc)
                    }
                }
            }
            
            def free = workers.findAll { !it.isBusy() }
            def unassigned = queue.findAll { !it.isAssigned }
            for(def i = 0; i < free.size() && i < unassigned.size(); i++) {
                free[i].acceptWork(unassigned[i])
            }
            def xx = workers.findAll{ it.isBusy() }.min { it.time }
            if( xx ) step = xx.time
        }

        return ret
    }
}
