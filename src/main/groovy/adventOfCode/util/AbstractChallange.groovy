package adventOfCode.util

abstract class AbstractChallange {

    List<String> data;
    Integer day;

    AbstractChallange() {
        data = [];
        getClass().getResource('data.txt').eachLine {
            data << it
        };
    }

    void printSolutions() {
        println "Solutions for Day ${day.toString().padLeft(2, '0')}:"
        println " 1. ${this.solution1()}"
        println " 2. ${this.solution2()}"
    }

    abstract String solution1()
    abstract String solution2()
}
