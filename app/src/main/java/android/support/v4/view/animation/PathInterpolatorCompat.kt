package android.support.v4.view.animation

import android.graphics.Path
import android.os.Build
import android.view.animation.Interpolator
import android.view.animation.PathInterpolator

class PathInterpolatorCompat {
    private constructor() {
    }

    fun create(Float f, Float f2) {
        return Build.VERSION.SDK_INT >= 21 ? PathInterpolator(f, f2) : PathInterpolatorApi14(f, f2)
    }

    fun create(Float f, Float f2, Float f3, Float f4) {
        return Build.VERSION.SDK_INT >= 21 ? PathInterpolator(f, f2, f3, f4) : PathInterpolatorApi14(f, f2, f3, f4)
    }

    fun create(Path path) {
        return Build.VERSION.SDK_INT >= 21 ? PathInterpolator(path) : PathInterpolatorApi14(path)
    }
}
