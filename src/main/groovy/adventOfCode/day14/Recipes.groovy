package adventOfCode.day14

import adventOfCode.util.AbstractChallange
// import groovy.time.TimeCategory 
// import groovy.time.TimeDuration

class Recipes extends AbstractChallange {
    List recipes
    int iterations
    int p1 = 0
    int p2 = 1

    Recipes() {
        day = 14

        iterations = data[0].toInteger()
        recipes = [3, 7]
    }

    private void createRecipe(e1, e2) {
        def r1 = recipes[e1]
        def r2 = recipes[e2]
        def rN = (r1 + r2).toString()
        recipes.addAll(rN.split('').collect { it.toInteger() })
    }

    private void createMultiple(count) {
        (0..<count).each {
            createRecipe(p1, p2)
            p1 = getNextRecipe(p1)
            p2 = getNextRecipe(p2)
        }
    }

    private int getNextRecipe(e) {
        (e + 1 + recipes[e]) % recipes.size()
    }

    String solution1() {
        createMultiple(iterations + 10)
        recipes.subList(iterations, iterations + 10).join('')
    }

    String solution2() {
        def next = 2
        def pattern = iterations.toString()
        def index = recipes.join('').indexOf(pattern)
        // Date start = new Date()
        while(index < 0) {
            createMultiple(next)
            next = Math.floor(next * 1.5) as int // experimented a little bit with the indices and this performs pretty good
            index = recipes
                .subList(Math.max(0, recipes.size() - next - pattern.size()), recipes.size())
                .join('').indexOf(pattern)
        }
        // Date stop = new Date()
        // TimeDuration td = TimeCategory.minus( stop, start )
        // println td
        return recipes.join('').indexOf(pattern) // using substrings, so the exact match needs one final search
    }
}
