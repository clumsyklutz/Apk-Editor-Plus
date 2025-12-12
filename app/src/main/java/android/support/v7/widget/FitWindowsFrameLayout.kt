package android.support.v7.widget

import android.content.Context
import android.graphics.Rect
import android.support.annotation.RestrictTo
import android.support.v7.widget.FitWindowsViewGroup
import android.util.AttributeSet
import android.widget.FrameLayout

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class FitWindowsFrameLayout extends FrameLayout implements FitWindowsViewGroup {
    private FitWindowsViewGroup.OnFitSystemWindowsListener mListener

    constructor(Context context) {
        super(context)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    @Override // android.view.View
    protected fun fitSystemWindows(Rect rect) {
        if (this.mListener != null) {
            this.mListener.onFitSystemWindows(rect)
        }
        return super.fitSystemWindows(rect)
    }

    @Override // android.support.v7.widget.FitWindowsViewGroup
    fun setOnFitSystemWindowsListener(FitWindowsViewGroup.OnFitSystemWindowsListener onFitSystemWindowsListener) {
        this.mListener = onFitSystemWindowsListener
    }
}
