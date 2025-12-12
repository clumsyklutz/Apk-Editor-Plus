package android.support.v4.util

import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import android.support.v4.media.MediaDescriptionCompat
import android.text.TextUtils
import java.util.Collection
import java.util.Iterator
import java.util.Locale

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class Preconditions {
    private constructor() {
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

    fun checkArgumentFinite(Float f, String str) {
        if (Float.isNaN(f)) {
            throw IllegalArgumentException(str + " must not be NaN")
        }
        if (Float.isInfinite(f)) {
            throw IllegalArgumentException(str + " must not be infinite")
        }
        return f
    }

    fun checkArgumentInRange(Float f, Float f2, Float f3, String str) {
        if (Float.isNaN(f)) {
            throw IllegalArgumentException(str + " must not be NaN")
        }
        if (f < f2) {
            throw IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", str, Float.valueOf(f2), Float.valueOf(f3)))
        }
        if (f > f3) {
            throw IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", str, Float.valueOf(f2), Float.valueOf(f3)))
        }
        return f
    }

    fun checkArgumentInRange(Int i, Int i2, Int i3, String str) {
        if (i < i2) {
            throw IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", str, Integer.valueOf(i2), Integer.valueOf(i3)))
        }
        if (i > i3) {
            throw IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", str, Integer.valueOf(i2), Integer.valueOf(i3)))
        }
        return i
    }

    fun checkArgumentInRange(Long j, Long j2, Long j3, String str) {
        if (j < j2) {
            throw IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", str, Long.valueOf(j2), Long.valueOf(j3)))
        }
        if (j > j3) {
            throw IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", str, Long.valueOf(j2), Long.valueOf(j3)))
        }
        return j
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    fun checkArgumentNonnegative(Int i) {
        if (i < 0) {
            throw IllegalArgumentException()
        }
        return i
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    fun checkArgumentNonnegative(Int i, String str) {
        if (i < 0) {
            throw IllegalArgumentException(str)
        }
        return i
    }

    fun checkArgumentNonnegative(Long j) {
        if (j < 0) {
            throw IllegalArgumentException()
        }
        return j
    }

    fun checkArgumentNonnegative(Long j, String str) {
        if (j < 0) {
            throw IllegalArgumentException(str)
        }
        return j
    }

    fun checkArgumentPositive(Int i, String str) {
        if (i <= 0) {
            throw IllegalArgumentException(str)
        }
        return i
    }

    public static Array<Float> checkArrayElementsInRange(Array<Float> fArr, Float f, Float f2, String str) {
        checkNotNull(fArr, str + " must not be null")
        for (Int i = 0; i < fArr.length; i++) {
            Float f3 = fArr[i]
            if (Float.isNaN(f3)) {
                throw IllegalArgumentException(str + "[" + i + "] must not be NaN")
            }
            if (f3 < f) {
                throw IllegalArgumentException(String.format(Locale.US, "%s[%d] is out of range of [%f, %f] (too low)", str, Integer.valueOf(i), Float.valueOf(f), Float.valueOf(f2)))
            }
            if (f3 > f2) {
                throw IllegalArgumentException(String.format(Locale.US, "%s[%d] is out of range of [%f, %f] (too high)", str, Integer.valueOf(i), Float.valueOf(f), Float.valueOf(f2)))
            }
        }
        return fArr
    }

    public static Array<Object> checkArrayElementsNotNull(Array<Object> objArr, String str) {
        if (objArr == null) {
            throw NullPointerException(str + " must not be null")
        }
        for (Int i = 0; i < objArr.length; i++) {
            if (objArr[i] == null) {
                throw NullPointerException(String.format(Locale.US, "%s[%d] must not be null", str, Integer.valueOf(i)))
            }
        }
        return objArr
    }

    @NonNull
    fun checkCollectionElementsNotNull(Collection collection, String str) {
        if (collection == null) {
            throw NullPointerException(str + " must not be null")
        }
        Long j = 0
        Iterator it = collection.iterator()
        while (it.hasNext()) {
            if (it.next() == null) {
                throw NullPointerException(String.format(Locale.US, "%s[%d] must not be null", str, Long.valueOf(j)))
            }
            j++
        }
        return collection
    }

    fun checkCollectionNotEmpty(Collection collection, String str) {
        if (collection == null) {
            throw NullPointerException(str + " must not be null")
        }
        if (collection.isEmpty()) {
            throw IllegalArgumentException(str + " is empty")
        }
        return collection
    }

    fun checkFlagsArgument(Int i, Int i2) {
        if ((i & i2) != i) {
            throw IllegalArgumentException("Requested flags 0x" + Integer.toHexString(i) + ", but only 0x" + Integer.toHexString(i2) + " are allowed")
        }
        return i
    }

    @NonNull
    fun checkNotNull(Object obj) {
        if (obj == null) {
            throw NullPointerException()
        }
        return obj
    }

    @NonNull
    fun checkNotNull(Object obj, Object obj2) {
        if (obj == null) {
            throw NullPointerException(String.valueOf(obj2))
        }
        return obj
    }

    fun checkState(Boolean z) {
        checkState(z, null)
    }

    fun checkState(Boolean z, String str) {
        if (!z) {
            throw IllegalStateException(str)
        }
    }

    @NonNull
    fun checkStringNotEmpty(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            throw IllegalArgumentException()
        }
        return charSequence
    }

    @NonNull
    fun checkStringNotEmpty(CharSequence charSequence, Object obj) {
        if (TextUtils.isEmpty(charSequence)) {
            throw IllegalArgumentException(String.valueOf(obj))
        }
        return charSequence
    }
}
