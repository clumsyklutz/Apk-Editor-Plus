package android.support.v4.view.animation

import android.view.animation.Interpolator

abstract class LookupTableInterpolator implements Interpolator {
    private final Float mStepSize
    private final Array<Float> mValues

    protected constructor(Array<Float> fArr) {
        this.mValues = fArr
        this.mStepSize = 1.0f / (this.mValues.length - 1)
    }

    @Override // android.animation.TimeInterpolator
    fun getInterpolation(Float f) {
        if (f >= 1.0f) {
            return 1.0f
        }
        if (f <= 0.0f) {
            return 0.0f
        }
        Int iMin = Math.min((Int) ((this.mValues.length - 1) * f), this.mValues.length - 2)
        return ((this.mValues[iMin + 1] - this.mValues[iMin]) * ((f - (iMin * this.mStepSize)) / this.mStepSize)) + this.mValues[iMin]
    }
}
