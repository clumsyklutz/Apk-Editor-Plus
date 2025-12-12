package jadx.core.utils

import java.util.BitSet

class EmptyBitSet extends BitSet {
    public static val EMPTY = EmptyBitSet()
    private static val serialVersionUID = -1194884945157778639L

    constructor() {
        super(0)
    }

    @Override // java.util.BitSet
    public final Unit and(BitSet bitSet) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Unit andNot(BitSet bitSet) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Int cardinality() {
        return 0
    }

    @Override // java.util.BitSet
    public final Object clone() {
        return this
    }

    @Override // java.util.BitSet
    public final BitSet get(Int i, Int i2) {
        return EMPTY
    }

    @Override // java.util.BitSet
    public final Boolean get(Int i) {
        return false
    }

    @Override // java.util.BitSet
    public final Boolean isEmpty() {
        return true
    }

    @Override // java.util.BitSet
    public final Int length() {
        return 0
    }

    @Override // java.util.BitSet
    public final Int nextSetBit(Int i) {
        return -1
    }

    @Override // java.util.BitSet
    public final Unit or(BitSet bitSet) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Unit set(Int i) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Unit set(Int i, Int i2) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Unit set(Int i, Int i2, Boolean z) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Unit set(Int i, Boolean z) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.BitSet
    public final Int size() {
        return 0
    }

    @Override // java.util.BitSet
    public final Unit xor(BitSet bitSet) {
        throw UnsupportedOperationException()
    }
}
