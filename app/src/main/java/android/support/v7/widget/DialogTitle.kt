package android.support.v7.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.RestrictTo
import androidx.core.widget.TextViewCompat
import androidx.appcompat.R
import android.text.Layout
import android.util.AttributeSet
import android.view.ActionMode
import android.widget.TextView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class DialogTitle extends TextView {
    constructor(Context context) {
        super(context)
    }

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Int lineCount
        super.onMeasure(i, i2)
        Layout layout = getLayout()
        if (layout == null || (lineCount = layout.getLineCount()) <= 0 || layout.getEllipsisCount(lineCount - 1) <= 0) {
            return
        }
        setSingleLine(false)
        setMaxLines(2)
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.TextAppearance, android.R.attr.textAppearanceMedium, 16973892)
        Int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0)
        if (dimensionPixelSize != 0) {
            setTextSize(0, dimensionPixelSize)
        }
        typedArrayObtainStyledAttributes.recycle()
        super.onMeasure(i, i2)
    }

    @Override // android.widget.TextView
    fun setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback))
    }
}
