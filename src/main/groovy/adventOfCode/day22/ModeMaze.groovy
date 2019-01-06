package adventOfCode.day22

import adventOfCode.util.AbstractChallange

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.*;

class ModeMaze extends AbstractChallange {
    private List<List<Integer>> maze
    private int depth
    private Tuple2 target

    private enum Gear {
        TORCH,CLIMBING_GEAR,NEITHER
    }
    private enum Type {
        ROCKY('.', [Gear.TORCH, Gear.CLIMBING_GEAR]),
            WET('=', [Gear.CLIMBING_GEAR, Gear.NEITHER]),
            NARROW('|', [Gear.TORCH, Gear.NEITHER])

        String symbol
        List tools

        Type(symbol, tools) {
            this.symbol = symbol
            this.tools = tools
        }
    }

    ModeMaze() {
        day = 22

        depth = data[0].split(' ')[1] as int

        def tmp = data[1].split(' ')[1].split(',')
        target = [tmp[0] as int, tmp[1] as int]

        maze = [].withDefault { y ->
            [].withDefault { x ->
                if(x == 0 && y == 0) {
                    return (0 + depth) % 20183
                } else if(x == 0) {
                    return ((y*48271) + depth) % 20183
                } else if(y == 0) {
                    return ((x*16807) + depth) % 20183
                } else if(x == target[0] && y == target[1]) {
                    return 0
                } else {
                    return ((maze[y][x-1] * maze[y-1][x]) + depth) % 20183
                }
            }
        }
    }

    private Type getType(x, y) {
        Type.values()[maze[y][x] % 3]
    }

    private void printMaze(xMax, yMax) {
        (0..yMax).each { y ->
            (0..xMax).each { x ->
                print getType(x, y).symbol
            }
            print '\n'
        }
    }

    private int sumMaze(xMax, yMax) {
        def ret = 0
        (0..yMax).each { y ->
            (0..xMax).each { x ->
                if(x != xMax || y != yMax) {
                    ret += getType(x, y).ordinal()
                }
            }
        }
        return ret
    }

    private Graph createGraph() {
        def g = new SimpleWeightedGraph(DefaultWeightedEdge.class)
        def visited = [:]
        def next = [[0,0] as Tuple2]

        while(!next.isEmpty()) {
            def cur = next.pop()
            def e = []
            def type = getType(cur)
            type.tools.each {
                e << [*cur, it.ordinal()]
                g.addVertex(e.last())
            }
            g.setEdgeWeight(g.addEdge(e[1], e[0]), 7)
            visited.put(cur, true)

            def n = neighbors(cur)
            n.each { elem ->
                if(!visited.get(elem)) {
                    if(!next.contains(elem)) {
                        next << elem
                    }
                } else {
                    def nType = getType(*elem)
                    def intersects = type.tools.intersect(nType.tools)
                    intersects.each { tool ->
                        g.addEdge([*elem, tool.ordinal()], [*cur, tool.ordinal()])
                    }
                }
            }
        }
        return g
    }

    private List neighbors(coords) {
        def ret = []
        if(coords[0] - 1 >= 0) ret << ([coords[0] - 1, coords[1]] as Tuple2)
        if(coords[0] + 1 <= target[0] + 20) ret << ([coords[0] + 1, coords[1]] as Tuple2)

        if(coords[1] - 1 >= 0) ret << ([coords[0], coords[1] - 1] as Tuple2)
        if(coords[1] + 1 <= target[1] + 20) ret << ([coords[0], coords[1] + 1] as Tuple2)
        return ret
    }

    // private int shortestPath(coords) {
    //     def current = [0, 0] as Tuple2
    //     def target = coords //[0, 0] as Tuple2
    //     def visited = [:]
    //     def distance = [:].withDefault { [Integer.MAX_VALUE, Gear.NEITHER] }
    //     distance.put(current, [0, Gear.TORCH])

    //     def paths = [:]
    //     paths.put(current, getNode(current, Gear.TORCH))

    //     def pq = new PriorityQueue<Tuple2>(10, new Comparator<Tuple2>() {
    //         public int compare(t1, t2) {
    //             def d1 = distance.get(t1)
    //             def d2 = distance.get(t2)
    //             d1[0] <=> d2[0]
    //         }
    //     })
    //     pq.add(current)

    //     while(!visited.get(target)) {
    //         current = pq.poll()
    //         def dist = distance.get(current)
    //         def next = neighbors(current)

    //         next.each {
    //             def weight = edgeWeight(current, it, dist[1])
    //             def nextDist = distance.get(it)
    //             def maybeDist = dist[0] + weight[0]

    //             if(nextDist[0] > maybeDist) {
    //                 distance.put(it, [maybeDist, weight[1]])
    //                 paths.put(it, paths.get(current) + getNode(it, weight[1]))
    //             }
    //             if(pq.contains(it)) {
    //                 pq.remove(it)
    //             }
    //             if(!visited.get(it)) {
    //                 pq.add(it)
    //             }
    //         }
    //         visited.put(current, true)
    //     }
    //     def dist = distance.get(target)
    //     return dist[0]
    // }

    // private Tuple2 edgeWeight(cur, next, equipment) {
    //     def curType = getType(*cur)
    //     def nextType = getType(*next)
    //     assert curType.tools.contains(equipment)

    //     def curGear = curType.tools
    //     def nextGear = nextType.tools
    //     def both = curGear.intersect(nextGear)

    //     both.contains(equipment) ? [1, equipment] : [8, both[0]]
    // }

    // private String getNode(coords, equipment) {
    //     "(${coords[0]}, ${coords[1]})<${equipment.name()}>:"
    // }

    String solution1() {
        sumMaze(*target)
    }

    String solution2() {
        def g = createGraph()
        def sp = new DijkstraShortestPath(g)
        def path = sp.getPath([0,0, Gear.TORCH.ordinal()], [*target, Gear.TORCH.ordinal()])
        path.getWeight()
    }
}
