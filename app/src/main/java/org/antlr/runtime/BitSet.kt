package org.antlr.runtime

class BitSet implements Cloneable {
    public Array<Long> bits

    constructor() {
        this(64)
    }

    constructor(Int i) {
        this.bits = new Long[((i - 1) >> 6) + 1]
    }

    constructor(Array<Long> jArr) {
        this.bits = jArr
    }

    fun bitMask(Int i) {
        return 1 << (i & 63)
    }

    fun wordNumber(Int i) {
        return i >> 6
    }

    fun clone() {
        try {
            BitSet bitSet = (BitSet) super.clone()
            Array<Long> jArr = new Long[this.bits.length]
            bitSet.bits = jArr
            Array<Long> jArr2 = this.bits
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length)
            return bitSet
        } catch (CloneNotSupportedException unused) {
            throw InternalError()
        }
    }

    fun equals(Object obj) {
        if (obj == null || !(obj is BitSet)) {
            return false
        }
        BitSet bitSet = (BitSet) obj
        Int iMin = Math.min(this.bits.length, bitSet.bits.length)
        for (Int i = 0; i < iMin; i++) {
            if (this.bits[i] != bitSet.bits[i]) {
                return false
            }
        }
        if (this.bits.length > iMin) {
            Int i2 = iMin + 1
            while (true) {
                Array<Long> jArr = this.bits
                if (i2 >= jArr.length) {
                    break
                }
                if (jArr[i2] != 0) {
                    return false
                }
                i2++
            }
        } else if (bitSet.bits.length > iMin) {
            Int i3 = iMin + 1
            while (true) {
                Array<Long> jArr2 = bitSet.bits
                if (i3 >= jArr2.length) {
                    break
                }
                if (jArr2[i3] != 0) {
                    return false
                }
                i3++
            }
        }
        return true
    }

    fun member(Int i) {
        if (i < 0) {
            return false
        }
        Int iWordNumber = wordNumber(i)
        Array<Long> jArr = this.bits
        return iWordNumber < jArr.length && (jArr[iWordNumber] & bitMask(i)) != 0
    }

    fun or(BitSet bitSet) {
        if (bitSet == null) {
            return this
        }
        BitSet bitSet2 = (BitSet) clone()
        bitSet2.orInPlace(bitSet)
        return bitSet2
    }

    fun orInPlace(BitSet bitSet) {
        if (bitSet == null) {
            return
        }
        Array<Long> jArr = bitSet.bits
        if (jArr.length > this.bits.length) {
            setSize(jArr.length)
        }
        for (Int iMin = Math.min(this.bits.length, bitSet.bits.length) - 1; iMin >= 0; iMin--) {
            Array<Long> jArr2 = this.bits
            jArr2[iMin] = jArr2[iMin] | bitSet.bits[iMin]
        }
    }

    fun remove(Int i) {
        Int iWordNumber = wordNumber(i)
        Array<Long> jArr = this.bits
        if (iWordNumber < jArr.length) {
            jArr[iWordNumber] = jArr[iWordNumber] & (bitMask(i) ^ (-1))
        }
    }

    public final Unit setSize(Int i) {
        Array<Long> jArr = new Long[i]
        System.arraycopy(this.bits, 0, jArr, 0, Math.min(i, this.bits.length))
        this.bits = jArr
    }

    fun toString() {
        return toString(null)
    }

    fun toString(Array<String> strArr) {
        StringBuilder sb = StringBuilder()
        sb.append('{')
        Boolean z = false
        for (Int i = 0; i < (this.bits.length << 6); i++) {
            if (member(i)) {
                if (i > 0 && z) {
                    sb.append(",")
                }
                if (strArr != null) {
                    sb.append(strArr[i])
                } else {
                    sb.append(i)
                }
                z = true
            }
        }
        sb.append('}')
        return sb.toString()
    }
}
