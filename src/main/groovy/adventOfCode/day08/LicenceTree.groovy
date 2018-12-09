package adventOfCode.day08

import adventOfCode.util.AbstractChallange

class LicenceTree extends AbstractChallange {

    Node head

    LicenceTree() {
        day = 8

        createTree()
    }

    private void createTree() {
        def list = data[0].split(' ').collect{ it.toInteger() }
        createNode(null, list, 0)
    }

    private int createNode(Node parent, List<Integer> list, int start) {
        def childCount = list[start]
        def metadataCount = list[start+1]
        def cur = new Node(start.toString())
        start += 2
        
        if(childCount > 0) {
            (1..childCount).each {
                start = createNode(cur, list, start)
            }
        }

        cur.metadata = list[(start..<start+metadataCount)]

        if(parent) {
            parent.addChild(cur)
        } else {
            this.head = cur
        }
        return start+metadataCount
    }

    String solution1() {
        return head.getMetadataSum()
    }

    String solution2() {
        return head.getIndexedSum()
    }
}
