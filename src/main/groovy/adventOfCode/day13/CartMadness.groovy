package adventOfCode.day13

import adventOfCode.util.AbstractChallange
import adventOfCode.day13.Cart

class CartMadness extends AbstractChallange {
    PriorityQueue carts
    List allCarts

    List<List<String>> grid

    CartMadness() {
        day = 13

        grid = data.collect { row ->
            row.split('') as List
        }

        carts = new PriorityQueue()
        allCarts = []

        (0..<grid.size()).each { y ->
            def row = grid[y]
            (0..<row.size()).each { x ->
                maybeCreateCart(x,y)
            }
        }
    }

    // Create carts with initial direction and position
    private void maybeCreateCart(x,y) {
        def cur = grid[y][x]
        def dir = null
        if(cur == '^') {
            grid[y][x] = '|'
            dir = [0, -1]
        } else if(cur == 'v') {
            grid[y][x] = '|'
            dir = [0, 1]
        } else if(cur == '<') {
            grid[y][x] = '-'
            dir = [-1, 0]
        } else if(cur == '>') {
            grid[y][x] = '-'
            dir = [1, 0]
        }

        if(dir) {
            def cart = new Cart([x, y], dir)
            carts.add(cart)
            allCarts.add(cart)
        }
    }

    private boolean tick() {
        def tmp = new PriorityQueue<Cart>()
        def hasCollision = false
        carts.each { cart ->
            if(!cart.isDead) {
                cart.move( getGridLine( cart.getPosition()))
                def collision = carts.find { cart.equalsOther(it) }
                if(collision) {
                    collision.isDead = true
                    cart.isDead = true
                    tmp.remove(collision) // if the car was already added, remove it
                    hasCollision = true
                } else {
                    tmp.add(cart)
                }
            }
        }
        carts = tmp
        return hasCollision
    }

    private String getGridLine(coords) {
        grid[coords.getEntry(1)][coords.getEntry(0)]
    }

    String solution1() {
        while(!tick());
        return allCarts.find { it.isDead }.position
    }

    String solution2() {
        while(carts.size() > 1) { tick() }
        return allCarts.find { !it.isDead }.position
    }
}
