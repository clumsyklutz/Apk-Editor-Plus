package android.support.v4.math

class MathUtils {
    private constructor() {
    }

    fun clamp(Double d, Double d2, Double d3) {
        return d < d2 ? d2 : d > d3 ? d3 : d
    }

    fun clamp(Float f, Float f2, Float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f
    }

    fun clamp(Int i, Int i2, Int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i
    }
}
