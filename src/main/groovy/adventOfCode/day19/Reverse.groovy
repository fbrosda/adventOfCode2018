// Reverse engineered algorithm from day19/data.txt
// The function calculates the sum of all factors of r1
// The basic assembly code has a runtime of O(r1^2)
// Using the modulo operator it is easy to get O(r1),
// which is sufficient to get the solution for part2
//
// instructions.txt contains the original program with some annotations

def r0 = 1
def r1 = r0 < 1 ? 867 : 10551267

r0 = 0

def r3 = 1
// def r2 = 1

while(r3 <= r1) {
    // r2 = 1

    // while(r2 <= r1) {
    //     if((r2 * r3) == r1) {
    //         r0 = r3 + r0
    //     }
    //     r2++
    // }
    if(r1 % r3 == 0) {
        r0 += r3
    }
    r3++
}

println r0
