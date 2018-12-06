/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package adventOfCode

import adventOfCode.day01.ChronalCalibration
import adventOfCode.day02.InventoryManagement
import adventOfCode.day03.SantaSuite

class App {
    static void main(String[] args) {
        println '##################'
        println '# Advent of Code #'
        println '##################\n'

        // def day01 = new ChronalCalibration()
        // day01.printSolutions()
        
        // def day02 = new InventoryManagement()
        // day02.printSolutions()

        def day03 = new SantaSuite()
        day03.printSolutions()

    }
}
