package android.support.v7.widget

import android.content.Context
import android.graphics.Rect
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout

@RestrictTo({RestrictTo.Scope.LIBRARY})
class ContentFrameLayout extends FrameLayout {
    private OnAttachListener mAttachListener
    private final Rect mDecorPadding
    private TypedValue mFixedHeightMajor
    private TypedValue mFixedHeightMinor
    private TypedValue mFixedWidthMajor
    private TypedValue mFixedWidthMinor
    private TypedValue mMinWidthMajor
    private TypedValue mMinWidthMinor

    public interface OnAttachListener {
        Unit onAttachedFromWindow()

        Unit onDetachedFromWindow()
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mDecorPadding = Rect()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun dispatchFitSystemWindows(Rect rect) {
        fitSystemWindows(rect)
    }

    fun getFixedHeightMajor() {
        if (this.mFixedHeightMajor == null) {
            this.mFixedHeightMajor = TypedValue()
        }
        return this.mFixedHeightMajor
    }

    fun getFixedHeightMinor() {
        if (this.mFixedHeightMinor == null) {
            this.mFixedHeightMinor = TypedValue()
        }
        return this.mFixedHeightMinor
    }

    fun getFixedWidthMajor() {
        if (this.mFixedWidthMajor == null) {
            this.mFixedWidthMajor = TypedValue()
        }
        return this.mFixedWidthMajor
    }

    fun getFixedWidthMinor() {
        if (this.mFixedWidthMinor == null) {
            this.mFixedWidthMinor = TypedValue()
        }
        return this.mFixedWidthMinor
    }

    fun getMinWidthMajor() {
        if (this.mMinWidthMajor == null) {
            this.mMinWidthMajor = TypedValue()
        }
        return this.mMinWidthMajor
    }

    fun getMinWidthMinor() {
        if (this.mMinWidthMinor == null) {
            this.mMinWidthMinor = TypedValue()
        }
        return this.mMinWidthMinor
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (this.mAttachListener != null) {
            this.mAttachListener.onAttachedFromWindow()
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (this.mAttachListener != null) {
            this.mAttachListener.onDetachedFromWindow()
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0100  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun onMeasure(Int r13, Int r14) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ContentFrameLayout.onMeasure(Int, Int):Unit")
    }

    fun setAttachListener(OnAttachListener onAttachListener) {
        this.mAttachListener = onAttachListener
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setDecorPadding(Int i, Int i2, Int i3, Int i4) {
        this.mDecorPadding.set(i, i2, i3, i4)
        if (ViewCompat.isLaidOut(this)) {
            requestLayout()
        }
    }
}
