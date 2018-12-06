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
    return ret;
}

def data = loadFile()

def matrix = [].withDefault { [].withDefault{ 0 }}
data.each{
    def x0 = it[0]
    def y0 = it[1]
    def x1 = it[2]
    def y1 = it[3]

    for(def i = y0; i < y1; i++) {
        for(def j = x0; j < x1; j++) {
            matrix[j][i] += 1;
        }
    }
}

println matrix.flatten().findAll{ it > 1 }.size()
