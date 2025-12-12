package android.support.v4.util

class ContainerHelpers {
    static final Array<Int> EMPTY_INTS = new Int[0]
    static final Array<Long> EMPTY_LONGS = new Long[0]
    static final Array<Object> EMPTY_OBJECTS = new Object[0]

    private constructor() {
    }

    static Int binarySearch(Array<Int> iArr, Int i, Int i2) {
        Int i3 = i - 1
        Int i4 = 0
        while (i4 <= i3) {
            Int i5 = (i4 + i3) >>> 1
            Int i6 = iArr[i5]
            if (i6 < i2) {
                i4 = i5 + 1
            } else {
                if (i6 <= i2) {
                    return i5
                }
                i3 = i5 - 1
            }
        }
        return i4 ^ (-1)
    }

    static Int binarySearch(Array<Long> jArr, Int i, Long j) {
        Int i2 = i - 1
        Int i3 = 0
        while (i3 <= i2) {
            Int i4 = (i3 + i2) >>> 1
            Long j2 = jArr[i4]
            if (j2 < j) {
                i3 = i4 + 1
            } else {
                if (j2 <= j) {
                    return i4
                }
                i2 = i4 - 1
            }
        }
        return i3 ^ (-1)
    }

    fun equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2))
    }

    fun idealByteArraySize(Int i) {
        for (Int i2 = 4; i2 < 32; i2++) {
            if (i <= (1 << i2) - 12) {
                return (1 << i2) - 12
            }
        }
        return i
    }

    fun idealIntArraySize(Int i) {
        return idealByteArraySize(i << 2) / 4
    }

    fun idealLongArraySize(Int i) {
        return idealByteArraySize(i << 3) / 8
    }
}
