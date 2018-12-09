package adventOfCode.day08

class Node {
    String id
    List<Integer> metadata
    
    Node parent
    List<Node> children

    Node(String id) {
        this.id = id;
        
        children = []
        metadata = []
        parent = null
    }

    int getMetadataSum() {
        return metadata.sum() + children.inject(0) { acc, child -> acc + child.getMetadataSum() }
    }

    int getIndexedSum() {
        if(children.size() == 0) {
            return metadata.sum()
        }
        return children[metadata.collect{ it - 1 }].findAll{ it }.inject(0) { acc, child -> acc + child.getIndexedSum() }
    }

    void addChild(Node child) {
        children << child
    }

    String toString() {
        return "${id} <${metadata}> ${children}"
    }
}
