package android.support.v4.util

class CircularArray {
    private Int mCapacityBitmask
    private Array<Object> mElements
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
        this.mElements = new Object[i]
    }

    private fun doubleCapacity() {
        Int length = this.mElements.length
        Int i = length - this.mHead
        Int i2 = length << 1
        if (i2 < 0) {
            throw RuntimeException("Max array capacity exceeded")
        }
        Array<Object> objArr = new Object[i2]
        System.arraycopy(this.mElements, this.mHead, objArr, 0, i)
        System.arraycopy(this.mElements, 0, objArr, i, this.mHead)
        this.mElements = objArr
        this.mHead = 0
        this.mTail = length
        this.mCapacityBitmask = i2 - 1
    }

    public final Unit addFirst(Object obj) {
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask
        this.mElements[this.mHead] = obj
        if (this.mHead == this.mTail) {
            doubleCapacity()
        }
    }

    public final Unit addLast(Object obj) {
        this.mElements[this.mTail] = obj
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask
        if (this.mTail == this.mHead) {
            doubleCapacity()
        }
    }

    public final Unit clear() {
        removeFromStart(size())
    }

    public final Object get(Int i) {
        if (i < 0 || i >= size()) {
            throw ArrayIndexOutOfBoundsException()
        }
        return this.mElements[(this.mHead + i) & this.mCapacityBitmask]
    }

    public final Object getFirst() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        return this.mElements[this.mHead]
    }

    public final Object getLast() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        return this.mElements[(this.mTail - 1) & this.mCapacityBitmask]
    }

    public final Boolean isEmpty() {
        return this.mHead == this.mTail
    }

    public final Object popFirst() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        Object obj = this.mElements[this.mHead]
        this.mElements[this.mHead] = null
        this.mHead = (this.mHead + 1) & this.mCapacityBitmask
        return obj
    }

    public final Object popLast() {
        if (this.mHead == this.mTail) {
            throw ArrayIndexOutOfBoundsException()
        }
        Int i = (this.mTail - 1) & this.mCapacityBitmask
        Object obj = this.mElements[i]
        this.mElements[i] = null
        this.mTail = i
        return obj
    }

    public final Unit removeFromEnd(Int i) {
        if (i <= 0) {
            return
        }
        if (i > size()) {
            throw ArrayIndexOutOfBoundsException()
        }
        Int i2 = i < this.mTail ? this.mTail - i : 0
        for (Int i3 = i2; i3 < this.mTail; i3++) {
            this.mElements[i3] = null
        }
        Int i4 = this.mTail - i2
        Int i5 = i - i4
        this.mTail -= i4
        if (i5 > 0) {
            this.mTail = this.mElements.length
            Int i6 = this.mTail - i5
            for (Int i7 = i6; i7 < this.mTail; i7++) {
                this.mElements[i7] = null
            }
            this.mTail = i6
        }
    }

    public final Unit removeFromStart(Int i) {
        if (i <= 0) {
            return
        }
        if (i > size()) {
            throw ArrayIndexOutOfBoundsException()
        }
        Int length = this.mElements.length
        if (i < length - this.mHead) {
            length = this.mHead + i
        }
        for (Int i2 = this.mHead; i2 < length; i2++) {
            this.mElements[i2] = null
        }
        Int i3 = length - this.mHead
        Int i4 = i - i3
        this.mHead = (i3 + this.mHead) & this.mCapacityBitmask
        if (i4 > 0) {
            for (Int i5 = 0; i5 < i4; i5++) {
                this.mElements[i5] = null
            }
            this.mHead = i4
        }
    }

    public final Int size() {
        return (this.mTail - this.mHead) & this.mCapacityBitmask
    }
}
