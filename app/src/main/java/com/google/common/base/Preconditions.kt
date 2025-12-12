package com.google.common.base

class Preconditions {
    fun badElementIndex(Int i, Int i2, String str) {
        if (i < 0) {
            return Strings.lenientFormat("%s (%s) must not be negative", str, Integer.valueOf(i))
        }
        if (i2 >= 0) {
            return Strings.lenientFormat("%s (%s) must be less than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2))
        }
        StringBuilder sb = StringBuilder(26)
        sb.append("negative size: ")
        sb.append(i2)
        throw IllegalArgumentException(sb.toString())
    }

    fun badPositionIndex(Int i, Int i2, String str) {
        if (i < 0) {
            return Strings.lenientFormat("%s (%s) must not be negative", str, Integer.valueOf(i))
        }
        if (i2 >= 0) {
            return Strings.lenientFormat("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2))
        }
        StringBuilder sb = StringBuilder(26)
        sb.append("negative size: ")
        sb.append(i2)
        throw IllegalArgumentException(sb.toString())
    }

    fun badPositionIndexes(Int i, Int i2, Int i3) {
        return (i < 0 || i > i3) ? badPositionIndex(i, i3, "start index") : (i2 < 0 || i2 > i3) ? badPositionIndex(i2, i3, "end index") : Strings.lenientFormat("end index (%s) must not be less than start index (%s)", Integer.valueOf(i2), Integer.valueOf(i))
    }

    fun checkArgument(Boolean z) {
        if (!z) {
            throw IllegalArgumentException()
        }
    }

    fun checkArgument(Boolean z, Object obj) {
        if (!z) {
            throw IllegalArgumentException(String.valueOf(obj))
        }
    }

    fun checkArgument(Boolean z, String str, Int i) {
        if (!z) {
            throw IllegalArgumentException(Strings.lenientFormat(str, Integer.valueOf(i)))
        }
    }

    fun checkArgument(Boolean z, String str, Int i, Int i2) {
        if (!z) {
            throw IllegalArgumentException(Strings.lenientFormat(str, Integer.valueOf(i), Integer.valueOf(i2)))
        }
    }

    fun checkArgument(Boolean z, String str, Object obj) {
        if (!z) {
            throw IllegalArgumentException(Strings.lenientFormat(str, obj))
        }
    }

    fun checkArgument(Boolean z, String str, Object obj, Object obj2) {
        if (!z) {
            throw IllegalArgumentException(Strings.lenientFormat(str, obj, obj2))
        }
    }

    fun checkElementIndex(Int i, Int i2) {
        return checkElementIndex(i, i2, "index")
    }

    fun checkElementIndex(Int i, Int i2, String str) {
        if (i < 0 || i >= i2) {
            throw IndexOutOfBoundsException(badElementIndex(i, i2, str))
        }
        return i
    }

    public static <T> T checkNotNull(T t) {
        t.getClass()
        return t
    }

    public static <T> T checkNotNull(T t, Object obj) {
        if (t != null) {
            return t
        }
        throw NullPointerException(String.valueOf(obj))
    }

    fun checkPositionIndex(Int i, Int i2) {
        return checkPositionIndex(i, i2, "index")
    }

    fun checkPositionIndex(Int i, Int i2, String str) {
        if (i < 0 || i > i2) {
            throw IndexOutOfBoundsException(badPositionIndex(i, i2, str))
        }
        return i
    }

    fun checkPositionIndexes(Int i, Int i2, Int i3) {
        if (i < 0 || i2 < i || i2 > i3) {
            throw IndexOutOfBoundsException(badPositionIndexes(i, i2, i3))
        }
    }

    fun checkState(Boolean z) {
        if (!z) {
            throw IllegalStateException()
        }
    }

    fun checkState(Boolean z, Object obj) {
        if (!z) {
            throw IllegalStateException(String.valueOf(obj))
        }
    }
}
