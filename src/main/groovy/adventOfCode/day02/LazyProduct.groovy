package adventOfCode.day02

class LazyProduct implements Iterable {
    List<String> l1
    List<String> l2
    
    private Integer i1 = 0
    private Integer i2 = 0
     
    // Return new Iterator instance.
    @Override
    Iterator iterator() {
        [
            hasNext: { i1 < l1.size() && i2 < l2.size() },
            next: {
                def ret = [l1[i1] as List, l2[i2++] as List]
                if(i2 == l2.size() && i1 < l1.size()) {
                    i1++
                    i2 = 0
                }
                return ret
            }
        ] as Iterator
    }
}
