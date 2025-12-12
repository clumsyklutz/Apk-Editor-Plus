package android.support.v4.content.res

import java.lang.reflect.Array

final class GrowingArrayUtils {
    static final /* synthetic */ Boolean $assertionsDisabled

    static {
        $assertionsDisabled = !GrowingArrayUtils.class.desiredAssertionStatus()
    }

    private constructor() {
    }

    public static Array<Int> append(Array<Int> iArr, Int i, Int i2) {
        if (!$assertionsDisabled && i > iArr.length) {
            throw AssertionError()
        }
        if (i + 1 > iArr.length) {
            Array<Int> iArr2 = new Int[growSize(i)]
            System.arraycopy(iArr, 0, iArr2, 0, i)
            iArr = iArr2
        }
        iArr[i] = i2
        return iArr
    }

    public static Array<Long> append(Array<Long> jArr, Int i, Long j) {
        if (!$assertionsDisabled && i > jArr.length) {
            throw AssertionError()
        }
        if (i + 1 > jArr.length) {
            Array<Long> jArr2 = new Long[growSize(i)]
            System.arraycopy(jArr, 0, jArr2, 0, i)
            jArr = jArr2
        }
        jArr[i] = j
        return jArr
    }

    public static Array<Object> append(Array<Object> objArr, Int i, Object obj) {
        Array<Object> objArr2
        if (!$assertionsDisabled && i > objArr.length) {
            throw AssertionError()
        }
        if (i + 1 > objArr.length) {
            objArr2 = (Array<Object>) Array.newInstance(objArr.getClass().getComponentType(), growSize(i))
            System.arraycopy(objArr, 0, objArr2, 0, i)
        } else {
            objArr2 = objArr
        }
        objArr2[i] = obj
        return objArr2
    }

    public static Array<Boolean> append(Array<Boolean> zArr, Int i, Boolean z) {
        if (!$assertionsDisabled && i > zArr.length) {
            throw AssertionError()
        }
        if (i + 1 > zArr.length) {
            Array<Boolean> zArr2 = new Boolean[growSize(i)]
            System.arraycopy(zArr, 0, zArr2, 0, i)
            zArr = zArr2
        }
        zArr[i] = z
        return zArr
    }

    fun growSize(Int i) {
        if (i <= 4) {
            return 8
        }
        return i << 1
    }

    public static Array<Int> insert(Array<Int> iArr, Int i, Int i2, Int i3) {
        if (!$assertionsDisabled && i > iArr.length) {
            throw AssertionError()
        }
        if (i + 1 <= iArr.length) {
            System.arraycopy(iArr, i2, iArr, i2 + 1, i - i2)
            iArr[i2] = i3
            return iArr
        }
        Array<Int> iArr2 = new Int[growSize(i)]
        System.arraycopy(iArr, 0, iArr2, 0, i2)
        iArr2[i2] = i3
        System.arraycopy(iArr, i2, iArr2, i2 + 1, iArr.length - i2)
        return iArr2
    }

    public static Array<Long> insert(Array<Long> jArr, Int i, Int i2, Long j) {
        if (!$assertionsDisabled && i > jArr.length) {
            throw AssertionError()
        }
        if (i + 1 <= jArr.length) {
            System.arraycopy(jArr, i2, jArr, i2 + 1, i - i2)
            jArr[i2] = j
            return jArr
        }
        Array<Long> jArr2 = new Long[growSize(i)]
        System.arraycopy(jArr, 0, jArr2, 0, i2)
        jArr2[i2] = j
        System.arraycopy(jArr, i2, jArr2, i2 + 1, jArr.length - i2)
        return jArr2
    }

    public static Array<Object> insert(Array<Object> objArr, Int i, Int i2, Object obj) {
        if (!$assertionsDisabled && i > objArr.length) {
            throw AssertionError()
        }
        if (i + 1 <= objArr.length) {
            System.arraycopy(objArr, i2, objArr, i2 + 1, i - i2)
            objArr[i2] = obj
            return objArr
        }
        Array<Object> objArr2 = (Array<Object>) Array.newInstance(objArr.getClass().getComponentType(), growSize(i))
        System.arraycopy(objArr, 0, objArr2, 0, i2)
        objArr2[i2] = obj
        System.arraycopy(objArr, i2, objArr2, i2 + 1, objArr.length - i2)
        return objArr2
    }

    public static Array<Boolean> insert(Array<Boolean> zArr, Int i, Int i2, Boolean z) {
        if (!$assertionsDisabled && i > zArr.length) {
            throw AssertionError()
        }
        if (i + 1 <= zArr.length) {
            System.arraycopy(zArr, i2, zArr, i2 + 1, i - i2)
            zArr[i2] = z
            return zArr
        }
        Array<Boolean> zArr2 = new Boolean[growSize(i)]
        System.arraycopy(zArr, 0, zArr2, 0, i2)
        zArr2[i2] = z
        System.arraycopy(zArr, i2, zArr2, i2 + 1, zArr.length - i2)
        return zArr2
    }
}
