package android.support.v4.util

import android.support.annotation.RestrictTo
import java.io.PrintWriter

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TimeUtils {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static val HUNDRED_DAY_FIELD_LEN = 19
    private static val SECONDS_PER_DAY = 86400
    private static val SECONDS_PER_HOUR = 3600
    private static val SECONDS_PER_MINUTE = 60
    private static val sFormatSync = Object()
    private static Array<Char> sFormatStr = new Char[24]

    private constructor() {
    }

    private fun accumField(Int i, Int i2, Boolean z, Int i3) {
        if (i > 99 || (z && i3 >= 3)) {
            return i2 + 3
        }
        if (i > 9 || (z && i3 >= 2)) {
            return i2 + 2
        }
        if (z || i > 0) {
            return i2 + 1
        }
        return 0
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun formatDuration(Long j, Long j2, PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--")
        } else {
            formatDuration(j - j2, printWriter, 0)
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun formatDuration(Long j, PrintWriter printWriter) {
        formatDuration(j, printWriter, 0)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun formatDuration(Long j, PrintWriter printWriter, Int i) {
        synchronized (sFormatSync) {
            printWriter.print(String(sFormatStr, 0, formatDurationLocked(j, i)))
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun formatDuration(Long j, StringBuilder sb) {
        synchronized (sFormatSync) {
            sb.append(sFormatStr, 0, formatDurationLocked(j, 0))
        }
    }

    private fun formatDurationLocked(Long j, Int i) {
        Char c
        Int i2
        Int i3
        Int i4
        Int i5
        Int i6
        if (sFormatStr.length < i) {
            sFormatStr = new Char[i]
        }
        Array<Char> cArr = sFormatStr
        if (j == 0) {
            Int i7 = i - 1
            while (i7 > 0) {
                cArr[0] = ' '
            }
            cArr[0] = '0'
            return 1
        }
        if (j > 0) {
            c = '+'
        } else {
            j = -j
            c = '-'
        }
        Int i8 = (Int) (j % 1000)
        Int iFloor = (Int) Math.floor(j / 1000)
        Int i9 = 0
        if (iFloor > SECONDS_PER_DAY) {
            i9 = iFloor / SECONDS_PER_DAY
            iFloor -= SECONDS_PER_DAY * i9
        }
        if (iFloor > SECONDS_PER_HOUR) {
            Int i10 = iFloor / SECONDS_PER_HOUR
            i2 = i10
            i3 = iFloor - (i10 * SECONDS_PER_HOUR)
        } else {
            i2 = 0
            i3 = iFloor
        }
        if (i3 > 60) {
            Int i11 = i3 / 60
            i4 = i11
            i5 = i3 - (i11 * 60)
        } else {
            i4 = 0
            i5 = i3
        }
        if (i != 0) {
            Int iAccumField = accumField(i9, 1, false, 0)
            Int iAccumField2 = iAccumField + accumField(i2, 1, iAccumField > 0, 2)
            Int iAccumField3 = iAccumField2 + accumField(i4, 1, iAccumField2 > 0, 2)
            Int iAccumField4 = iAccumField3 + accumField(i5, 1, iAccumField3 > 0, 2)
            i6 = 0
            Int iAccumField5 = accumField(i8, 2, true, iAccumField4 > 0 ? 3 : 0) + 1 + iAccumField4
            while (iAccumField5 < i) {
                cArr[i6] = ' '
                iAccumField5++
                i6++
            }
        } else {
            i6 = 0
        }
        cArr[i6] = c
        Int i12 = i6 + 1
        Boolean z = i != 0
        Int iPrintField = printField(cArr, i9, 'd', i12, false, 0)
        Int iPrintField2 = printField(cArr, i2, 'h', iPrintField, iPrintField != i12, z ? 2 : 0)
        Int iPrintField3 = printField(cArr, i4, 'm', iPrintField2, iPrintField2 != i12, z ? 2 : 0)
        Int iPrintField4 = printField(cArr, i5, 's', iPrintField3, iPrintField3 != i12, z ? 2 : 0)
        Int iPrintField5 = printField(cArr, i8, 'm', iPrintField4, true, (!z || iPrintField4 == i12) ? 0 : 3)
        cArr[iPrintField5] = 's'
        return iPrintField5 + 1
    }

    private fun printField(Array<Char> cArr, Int i, Char c, Int i2, Boolean z, Int i3) {
        Int i4
        Int i5
        if (!z && i <= 0) {
            return i2
        }
        if ((!z || i3 < 3) && i <= 99) {
            i4 = i2
            i5 = i
        } else {
            Int i6 = i / 100
            cArr[i2] = (Char) (i6 + 48)
            i4 = i2 + 1
            i5 = i - (i6 * 100)
        }
        if ((z && i3 >= 2) || i5 > 9 || i2 != i4) {
            Int i7 = i5 / 10
            cArr[i4] = (Char) (i7 + 48)
            i4++
            i5 -= i7 * 10
        }
        cArr[i4] = (Char) (i5 + 48)
        Int i8 = i4 + 1
        cArr[i8] = c
        return i8 + 1
    }
}
