package android.support.v4.view.animation

import android.graphics.Path
import android.graphics.PathMeasure
import android.view.animation.Interpolator

class PathInterpolatorApi14 implements Interpolator {
    private static val PRECISION = 0.002f
    private final Array<Float> mX
    private final Array<Float> mY

    PathInterpolatorApi14(Float f, Float f2) {
        this(createQuad(f, f2))
    }

    PathInterpolatorApi14(Float f, Float f2, Float f3, Float f4) {
        this(createCubic(f, f2, f3, f4))
    }

    PathInterpolatorApi14(Path path) {
        PathMeasure pathMeasure = PathMeasure(path, false)
        Float length = pathMeasure.getLength()
        Int i = ((Int) (length / PRECISION)) + 1
        this.mX = new Float[i]
        this.mY = new Float[i]
        Array<Float> fArr = new Float[2]
        for (Int i2 = 0; i2 < i; i2++) {
            pathMeasure.getPosTan((i2 * length) / (i - 1), fArr, null)
            this.mX[i2] = fArr[0]
            this.mY[i2] = fArr[1]
        }
    }

    private fun createCubic(Float f, Float f2, Float f3, Float f4) {
        Path path = Path()
        path.moveTo(0.0f, 0.0f)
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f)
        return path
    }

    private fun createQuad(Float f, Float f2) {
        Path path = Path()
        path.moveTo(0.0f, 0.0f)
        path.quadTo(f, f2, 1.0f, 1.0f)
        return path
    }

    @Override // android.animation.TimeInterpolator
    fun getInterpolation(Float f) {
        if (f <= 0.0f) {
            return 0.0f
        }
        if (f >= 1.0f) {
            return 1.0f
        }
        Int length = this.mX.length - 1
        Int i = 0
        while (length - i > 1) {
            Int i2 = (i + length) / 2
            if (f < this.mX[i2]) {
                length = i2
            } else {
                i = i2
            }
        }
        Float f2 = this.mX[length] - this.mX[i]
        if (f2 == 0.0f) {
            return this.mY[i]
        }
        Float f3 = (f - this.mX[i]) / f2
        Float f4 = this.mY[i]
        return (f3 * (this.mY[length] - f4)) + f4
    }
}
