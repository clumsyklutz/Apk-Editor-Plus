package android.support.v4.graphics

import android.graphics.Path
import android.graphics.PointF
import android.support.annotation.FloatRange
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import java.util.ArrayList
import java.util.Collection

class PathUtils {
    private constructor() {
    }

    @NonNull
    @RequiresApi(26)
    fun flatten(@NonNull Path path) {
        return flatten(path, 0.5f)
    }

    @NonNull
    @RequiresApi(26)
    fun flatten(@NonNull Path path, @FloatRange(from = 0.0d) Float f) {
        Array<Float> fArrApproximate = path.approximate(f)
        Int length = fArrApproximate.length / 3
        ArrayList arrayList = ArrayList(length)
        for (Int i = 1; i < length; i++) {
            Int i2 = i * 3
            Int i3 = (i - 1) * 3
            Float f2 = fArrApproximate[i2]
            Float f3 = fArrApproximate[i2 + 1]
            Float f4 = fArrApproximate[i2 + 2]
            Float f5 = fArrApproximate[i3]
            Float f6 = fArrApproximate[i3 + 1]
            Float f7 = fArrApproximate[i3 + 2]
            if (f2 != f5 && (f3 != f6 || f4 != f7)) {
                arrayList.add(PathSegment(PointF(f6, f7), f5, PointF(f3, f4), f2))
            }
        }
        return arrayList
    }
}
