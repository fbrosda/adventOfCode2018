package adventOfCode.day3

def loadFile() {
    def lines = new File('./data.txt').readLines()
    def ret =  lines.collect{ (it =~ /^[^@]+@\s(\d+),(\d+):\s(\d+)x(\d+)$/).getAt(0).drop(1).collect{ it.toInteger() } }
    ret = ret.collect{
        for(def i = 2; i < it.size(); i++ ) {
            it[i] += it[i-2]
        }
        return it
    }
    return ret.withIndex(1)
}

def data = loadFile()

def matrix = [].withDefault { [].withDefault{ 0 }}
def keys = (1..data.size()).toList()

data.each{ it, index ->
    def x0 = it[0]
    def y0 = it[1]
    def x1 = it[2]
    def y1 = it[3]

    for(def i = y0; i < y1; i++) {
        for(def j = x0; j < x1; j++) {
            def val = matrix[j][i]
            if( val != 0 ) {
                keys[val-1] = null
                keys[index-1] = null
            }
            matrix[j][i] = index;
        }
    }
}

println keys.find{ it }
