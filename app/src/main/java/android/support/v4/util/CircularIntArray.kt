package android.support.v4.util

class CircularIntArray {
    private Int mCapacityBitmask
    private Array<Int> mElements
    private Int mHead
    private Int mTail

    constructor() {
        this(8)
    }

    constructor(Int i) {
        if (i <= 0) {
            throw IllegalArgumentException("capacity must be >= 1")
        }
        if (i > 1073741824) {
            throw IllegalArgumentException("capacity must be <= 2^30")
        }
        i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i
        this.mCapacityBitmask = i - 1
        this.mElements = new Int[i]
    }

    private fun doubleCapacity() {
        Int length = this.mElements.length
        Int i = length - this.mHead
        Int i2 = length << 1
        if (i2 < 0) {
            throw RuntimeException("Max array capacity exceeded")
        }
        Array<Int> iArr = new Int[i2]
        System.arraycopy(this.mElements, this.mHead, iArr, 0, i)
        System.arraycopy(this.mElements, 0, iArr, i, this.mHead)
        this.mElements = iArr
        this.mHead = 0
        this.mTail = length
        this.mCapacityBitmask = i2 - 1
    }

    public final Unit addFirst(Int i) {
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask
        this.mElements[this.mHead] = i
        if (this.mHead == this.mTail) {
            doubleCapacity()
        }
    }

    public final Unit addLast(Int i) {
        this.mElements[this.mTail] = i
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask
        if (this.mTail == this.mHead) {
            doubleCapacity()
        }
    }

    public final Unit clear() {
        this.mTail = this.mHead
    }

    public final Int get(Int i) {
        if (i < 0 || i >= size()) {
            throw ArrayIndexOutOfBoundsException()
        }
        return this.mElements[(this.mHead + i) & this.mCapacityBitmask]
    }

    public final Int getFirst() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        return this.mElements[this.mHead]
    }

    public final Int getLast() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        return this.mElements[(this.mTail - 1) & this.mCapacityBitmask]
    }

    public final Boolean isEmpty() {
        return this.mHead == this.mTail
    }

    public final Int popFirst() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        Int i = this.mElements[this.mHead]
        this.mHead = (this.mHead + 1) & this.mCapacityBitmask
        return i
    }

    public final Int popLast() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        Int i = (this.mTail - 1) & this.mCapacityBitmask
        Int i2 = this.mElements[i]
        this.mTail = i
        return i2
    }

    public final Unit removeFromEnd(Int i) {
        if (i <= 0) {
            return
        }
        if (i > size()) {
            throw ArrayIndexOutOfBoundsException()
        }
        this.mTail = (this.mTail - i) & this.mCapacityBitmask
    }

    public final Unit removeFromStart(Int i) {
        if (i <= 0) {
            return
        }
        if (i > size()) {
            throw ArrayIndexOutOfBoundsException()
        }
        this.mHead = (this.mHead + i) & this.mCapacityBitmask
    }

    public final Int size() {
        return (this.mTail - this.mHead) & this.mCapacityBitmask
    }
}
