package android.support.v4.view

import android.view.VelocityTracker

@Deprecated
class VelocityTrackerCompat {
    private constructor() {
    }

    @Deprecated
    fun getXVelocity(VelocityTracker velocityTracker, Int i) {
        return velocityTracker.getXVelocity(i)
    }

    @Deprecated
    fun getYVelocity(VelocityTracker velocityTracker, Int i) {
        return velocityTracker.getYVelocity(i)
    }
}
