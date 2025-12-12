package android.support.v4.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.View

@Deprecated
class Space extends View {
    @Deprecated
    constructor(@NonNull Context context) {
        this(context, null)
    }

    @Deprecated
    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    @Deprecated
    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        if (getVisibility() == 0) {
            setVisibility(4)
        }
    }

    private fun getDefaultSize2(Int i, Int i2) {
        Int mode = View.MeasureSpec.getMode(i2)
        Int size = View.MeasureSpec.getSize(i2)
        switch (mode) {
            case Integer.MIN_VALUE:
                return Math.min(i, size)
            case 0:
            default:
                return i
            case 1073741824:
                return size
        }
    }

    @Override // android.view.View
    @SuppressLint({"MissingSuperCall"})
    @Deprecated
    fun draw(Canvas canvas) {
    }

    @Override // android.view.View
    @Deprecated
    protected fun onMeasure(Int i, Int i2) {
        setMeasuredDimension(getDefaultSize2(getSuggestedMinimumWidth(), i), getDefaultSize2(getSuggestedMinimumHeight(), i2))
    }
}
