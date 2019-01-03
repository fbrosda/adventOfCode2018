package adventOfCode.day16

enum Opcode {
    ADDR({ a, b, r -> r[a] + r[b] }),
        ADDI({ a, b, r -> r[a] + b }),

        MULR({ a, b, r -> r[a] * r[b] }),
        MULI({ a, b, r -> r[a] * b }),

        BANR({ a, b, r -> r[a] & r[b] }),
        BANI({ a, b, r -> r[a] & b }),

        BORR({ a, b, r -> r[a] | r[b] }),
        BORI({ a, b, r -> r[a] | b }),

        SETR({ a, b, r -> r[a] }),
        SETI({ a, b, r -> a }),

        GTIR({ a, b, r -> a > r[b] ? 1 : 0 }),
        GTRI({ a, b, r -> r[a] > b ? 1 : 0 }),
        GTRR({ a, b, r -> r[a] > r[b] ? 1 : 0 }),

        EQIR({ a, b, r -> a == r[b] ? 1 : 0 }),
        EQRI({ a, b, r -> r[a] == b ? 1 : 0 }),
        EQRR({ a, b, r -> r[a] == r[b] ? 1 : 0 })

    private final Closure op

    Opcode(op) {
        this.op = op
    }

    public void apply(int a, int b, int c, List registers) {
        assert registers.size() == 4
        registers[c] = op.call(a, b, registers)
    }

    public void apply(List args, List registers) {
        assert args.size() == 3
        assert registers.size() == 4

        registers[args[2]] = op.call(args[0], args[1], registers)
    }

    public List exec(int a, int b, int c, List registers) {
        assert registers.size() == 4

        def tmp = registers.clone()
        tmp[c] = op.call(a, b, tmp)
        return tmp
    }

    public List exec(List args, List registers) {
        assert args.size() == 3
        assert registers.size() == 4

        def tmp = registers.clone()
        tmp[args[2]] = op.call(args[0], args[1], tmp)
        return tmp
    }
}
