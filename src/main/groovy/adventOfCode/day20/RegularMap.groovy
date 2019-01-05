package adventOfCode.day20

import adventOfCode.util.AbstractChallange

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.*;

class RegularMap extends AbstractChallange {
    Graph<Tuple2<Integer,Integer>, DefaultEdge> g

    RegularMap() {
        day = 20

        def input = data[0]

        g = new SimpleGraph<>(DefaultEdge.class)
        def start = [0,0] as Tuple2
        g.addVertex(start)

        parseDirections(input, 1, start)
    }

    // Construct the graph
    private List parseDirections(input, index, start) {
        def cur = new HashSet<Tuple2>()
        cur.add(start)
        def i = 0
        def prev = []
        def next = []

        while(i < input.size()) {
            def dir = input.getAt(i)
            if(dir =~ /[NESW]/) {
                def tmp = new HashSet()
                cur.each { n ->
                    def nn = createNextNode(n, dir)
                    g.addVertex(nn)
                    g.addEdge(n, nn)
                    tmp.add(nn)
                }
                cur = tmp
            } else if(dir == '(') {
                prev.push(cur)
                next.push(new HashSet())
            } else if(dir == '|') {
                next.first().addAll(cur)
                cur = prev.first()
            } else if(dir == ')') {
                prev.pop()
                next.first().addAll(cur)
                cur = next.pop()
            }
            i++
        }
        assert prev.isEmpty()
        assert next.isEmpty()
    }

    private Tuple2 createNextNode(n, dir) {
        switch(dir) {
            case 'N':
                [n[0], n[1] - 1]
                break
            case 'E':
                [n[0] + 1, n[1]]
                break
            case 'S':
                [n[0], n[1] + 1]
                break
            case 'W':
                [n[0] - 1, n[1]]
                break
        }
    }

    String solution1() {
        def sp = new DijkstraShortestPath(g) // dijkstra: simpla and fast, without negative edge weight
        def paths = sp.getPaths([0,0] as Tuple2)
        g.vertexSet().collect {
            paths.getWeight(it)
        }.max()
    }

    String solution2() {
        def sp = new DijkstraShortestPath(g)
        def paths = sp.getPaths([0,0] as Tuple2)
        g.vertexSet().collect {
            paths.getWeight(it)
        }.findAll { it >= 1000 }.size()
    }
}
