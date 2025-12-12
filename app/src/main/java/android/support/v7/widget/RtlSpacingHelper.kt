package android.support.v7.widget

class RtlSpacingHelper {
    public static val UNDEFINED = Integer.MIN_VALUE
    private Int mLeft = 0
    private Int mRight = 0
    private Int mStart = Integer.MIN_VALUE
    private Int mEnd = Integer.MIN_VALUE
    private Int mExplicitLeft = 0
    private Int mExplicitRight = 0
    private Boolean mIsRtl = false
    private Boolean mIsRelative = false

    RtlSpacingHelper() {
    }

    fun getEnd() {
        return this.mIsRtl ? this.mLeft : this.mRight
    }

    fun getLeft() {
        return this.mLeft
    }

    fun getRight() {
        return this.mRight
    }

    fun getStart() {
        return this.mIsRtl ? this.mRight : this.mLeft
    }

    fun setAbsolute(Int i, Int i2) {
        this.mIsRelative = false
        if (i != Integer.MIN_VALUE) {
            this.mExplicitLeft = i
            this.mLeft = i
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mExplicitRight = i2
            this.mRight = i2
        }
    }

    fun setDirection(Boolean z) {
        if (z == this.mIsRtl) {
            return
        }
        this.mIsRtl = z
        if (!this.mIsRelative) {
            this.mLeft = this.mExplicitLeft
            this.mRight = this.mExplicitRight
        } else if (z) {
            this.mLeft = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitLeft
            this.mRight = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitRight
        } else {
            this.mLeft = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitLeft
            this.mRight = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitRight
        }
    }

    fun setRelative(Int i, Int i2) {
        this.mStart = i
        this.mEnd = i2
        this.mIsRelative = true
        if (this.mIsRtl) {
            if (i2 != Integer.MIN_VALUE) {
                this.mLeft = i2
            }
            if (i != Integer.MIN_VALUE) {
                this.mRight = i
                return
            }
            return
        }
        if (i != Integer.MIN_VALUE) {
            this.mLeft = i
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mRight = i2
        }
    }
}
