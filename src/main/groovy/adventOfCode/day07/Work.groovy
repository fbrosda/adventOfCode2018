package adventOfCode.day07

import groovy.transform.*

class Work implements Comparable<Work> {
    String id
    boolean isAssigned
    private Set<Work> predecessors
    private List<Work> successors

    Work(String id) {
        this.id = id
        predecessors = [] as HashSet
        successors = []
    }

    void addPredecessor(Work pre) {
        if(!predecessors.contains(pre)) {
            predecessors.add(pre)
        }
    }

    void addSuccessor(Work suc) {
        if(!successors.contains(suc)) {
            successors.push(suc)
        }
    }

    List<Work> getSuccessors() {
        return successors
    }

    int getDuration() {
        def c = id.charAt(0) as int;
        return 60 + c - 64;
    }

    int nrOfPredesessors() {
        return predecessors.size()
    }

    int removePredecessor(Work pre) {
        predecessors.removeElement(pre)
        return predecessors.size()
    }

    String toString() {
        return "${id}: ${getDuration()}"
    }

    int compareTo(Work n) {
        if(predecessors.size() == n.predecessors.size()) {
            return id <=> n.id
        }
        return predecessors.size() <=> n.predecessors.size()
    }
}
