package adventOfCode.day13

import org.apache.commons.math3.linear.RealMatrix
import org.apache.commons.math3.linear.RealVector
import org.apache.commons.math3.linear.MatrixUtils

class Cart implements Comparable<Cart> {
    // Rotation matrices for turns and intersections
    private final static RealMatrix TURN_RIGHT = MatrixUtils.createRealMatrix([[0, -1], [-1, 0]] as double[][])
    private final static RealMatrix TURN_LEFT = MatrixUtils.createRealMatrix([[0, 1], [1, 0]] as double[][])
    private final static RealMatrix CROSS_LEFT = MatrixUtils.createRealMatrix([[0, -1], [1, 0]] as double[][])
    private final static RealMatrix CROSS_RIGHT = MatrixUtils.createRealMatrix([[0, 1], [-1, 0]] as double[][])

    RealVector position
    RealVector direction
    int secDir
    boolean isDead

    Cart(position, direction) {
        this.position = MatrixUtils.createRealVector(position as double[])
        this.direction = MatrixUtils.createRealVector(direction as double[])
    }

    void move(dirStr) {
        // Update direction if cart is on turn or crossroad
        switch(dirStr) {
            case '/':
                direction = TURN_RIGHT.preMultiply(direction)
                break
            case '\\':
                direction = TURN_LEFT.preMultiply(direction)
                break
            case '+':
                if(secDir == 0) {
                    direction = CROSS_LEFT.preMultiply(direction)
                } else if(secDir == 2) {
                    direction = CROSS_RIGHT.preMultiply(direction)
                }
                secDir = (secDir + 1) % 3
                break
        }
        // Move cart one step forward
        position = position.add(direction)
    }

    // sort carts by y and then x coordinate
    // This means, the top left cart is the 'smallest' and the bottom right the 'largest'
    int compareTo(Cart n) {
        if(position.getEntry(1) == n.position.getEntry(1)) {
            return position.getEntry(0) <=> n.position.getEntry(0)
        }
        return position.getEntry(1) <=> n.position.getEntry(1)
    }

    // Get cart with same position wich is not itself (collision checks)
    boolean equalsOther(Cart n) {
        !this.is( n ) && this.compareTo(n) == 0
    }

    String toString() {
        "Position:\n${position}\nDirection\n${direction}\nNext Section: ${secDir}, Dead: ${isDead}"
    }
}
