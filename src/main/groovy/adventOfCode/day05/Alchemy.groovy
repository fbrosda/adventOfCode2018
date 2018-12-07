package adventOfCode.day05

import adventOfCode.util.AbstractChallange

class Alchemy extends AbstractChallange {

    private String formula;
    private String reducedFormula

    Alchemy() {
        day = 5
        formula = data[0]
    }

    String scan(String tail) {
        def tailList = asList(tail)
        def initList = []
        
        while(tailList.size() > 0) {
            char head = tailList.take(1)[0]

            if(initList.size() > 0) {
                char sec = initList.last()
                if(isCompatible(head, sec)) {
                    tailList = tailList.drop(1)
                    initList = initList.dropRight(1)
                    continue
                }
            }
            tailList = tailList.drop(1)
            initList << head
        }
        return initList.join()
    }

    private boolean isCompatible(char c1, char c2) {
        def n1 = (int) c1
        def n2 = (int) c2

        def up = Math.min(n1, n2)
        def lo = Math.max(n1, n2)

        if( up > 64 && up < 91 && lo == up + 32) {
            return true
        }
        return false
    }

    private List<Character> asList(final String string) {
        return new AbstractList<Character>() {
            public int size() { return string.length(); }
            public Character get(int index) { return string.charAt(index); }
        };
    }

    String solution1() {
        reducedFormula = scan(formula)
        return reducedFormula.length()
    }

    String solution2() {
        def tmp = asList(reducedFormula)
        def parts =  asList(reducedFormula.toLowerCase()).toSet()
        def reducesSizesList = parts.collect { ch ->
            def xx = tmp.inject([]) { acc, cur ->
                if(cur != ch && cur != ch - 32) {
                    acc << cur
                }
                acc
            }
            scan(xx.join()).length()
        }
        return reducesSizesList.min()
    }
}
