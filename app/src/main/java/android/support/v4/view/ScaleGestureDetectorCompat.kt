package android.support.v4.view

import android.os.Build
import android.view.ScaleGestureDetector

class ScaleGestureDetectorCompat {
    private constructor() {
    }

    fun isQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector) {
        if (Build.VERSION.SDK_INT >= 19) {
            return scaleGestureDetector.isQuickScaleEnabled()
        }
        return false
    }

    @Deprecated
    fun isQuickScaleEnabled(Object obj) {
        return isQuickScaleEnabled((ScaleGestureDetector) obj)
    }

    fun setQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector, Boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            scaleGestureDetector.setQuickScaleEnabled(z)
        }
    }

    @Deprecated
    fun setQuickScaleEnabled(Object obj, Boolean z) {
        setQuickScaleEnabled((ScaleGestureDetector) obj, z)
    }
}
