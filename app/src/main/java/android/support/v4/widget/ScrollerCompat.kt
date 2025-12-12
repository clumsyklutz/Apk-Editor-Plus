package android.support.v4.widget

import android.content.Context
import android.view.animation.Interpolator
import android.widget.OverScroller

@Deprecated
class ScrollerCompat {
    OverScroller mScroller

    ScrollerCompat(Context context, Interpolator interpolator) {
        this.mScroller = interpolator != null ? OverScroller(context, interpolator) : OverScroller(context)
    }

    @Deprecated
    fun create(Context context) {
        return create(context, null)
    }

    @Deprecated
    fun create(Context context, Interpolator interpolator) {
        return ScrollerCompat(context, interpolator)
    }

    @Deprecated
    public final Unit abortAnimation() {
        this.mScroller.abortAnimation()
    }

    @Deprecated
    public final Boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset()
    }

    @Deprecated
    public final Unit fling(Int i, Int i2, Int i3, Int i4, Int i5, Int i6, Int i7, Int i8) {
        this.mScroller.fling(i, i2, i3, i4, i5, i6, i7, i8)
    }

    @Deprecated
    public final Unit fling(Int i, Int i2, Int i3, Int i4, Int i5, Int i6, Int i7, Int i8, Int i9, Int i10) {
        this.mScroller.fling(i, i2, i3, i4, i5, i6, i7, i8, i9, i10)
    }

    @Deprecated
    public final Float getCurrVelocity() {
        return this.mScroller.getCurrVelocity()
    }

    @Deprecated
    public final Int getCurrX() {
        return this.mScroller.getCurrX()
    }

    @Deprecated
    public final Int getCurrY() {
        return this.mScroller.getCurrY()
    }

    @Deprecated
    public final Int getFinalX() {
        return this.mScroller.getFinalX()
    }

    @Deprecated
    public final Int getFinalY() {
        return this.mScroller.getFinalY()
    }

    @Deprecated
    public final Boolean isFinished() {
        return this.mScroller.isFinished()
    }

    @Deprecated
    public final Boolean isOverScrolled() {
        return this.mScroller.isOverScrolled()
    }

    @Deprecated
    public final Unit notifyHorizontalEdgeReached(Int i, Int i2, Int i3) {
        this.mScroller.notifyHorizontalEdgeReached(i, i2, i3)
    }

    @Deprecated
    public final Unit notifyVerticalEdgeReached(Int i, Int i2, Int i3) {
        this.mScroller.notifyVerticalEdgeReached(i, i2, i3)
    }

    @Deprecated
    public final Boolean springBack(Int i, Int i2, Int i3, Int i4, Int i5, Int i6) {
        return this.mScroller.springBack(i, i2, i3, i4, i5, i6)
    }

    @Deprecated
    public final Unit startScroll(Int i, Int i2, Int i3, Int i4) {
        this.mScroller.startScroll(i, i2, i3, i4)
    }

    @Deprecated
    public final Unit startScroll(Int i, Int i2, Int i3, Int i4, Int i5) {
        this.mScroller.startScroll(i, i2, i3, i4, i5)
    }
}
