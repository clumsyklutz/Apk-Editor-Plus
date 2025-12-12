package android.support.v7.widget

import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StyleRes
import android.support.v4.widget.PopupWindowCompat
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.View
import android.widget.PopupWindow

class AppCompatPopupWindow extends PopupWindow {
    private static final Boolean COMPAT_OVERLAP_ANCHOR
    private Boolean mOverlapAnchor

    static {
        COMPAT_OVERLAP_ANCHOR = Build.VERSION.SDK_INT < 21
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes Int i) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        super(context, attributeSet, i)
        init(context, attributeSet, i, 0)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes Int i, @StyleRes Int i2) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        super(context, attributeSet, i, i2)
        init(context, attributeSet, i, i2)
    }

    private fun init(Context context, AttributeSet attributeSet, Int i, Int i2) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.PopupWindow, i, i2)
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            setSupportOverlapAnchor(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.PopupWindow_overlapAnchor, false))
        }
        setBackgroundDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.PopupWindow_android_popupBackground))
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    private fun setSupportOverlapAnchor(Boolean z) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = z
        } else {
            PopupWindowCompat.setOverlapAnchor(this, z)
        }
    }

    @Override // android.widget.PopupWindow
    fun showAsDropDown(View view, Int i, Int i2) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            i2 -= view.getHeight()
        }
        super.showAsDropDown(view, i, i2)
    }

    @Override // android.widget.PopupWindow
    fun showAsDropDown(View view, Int i, Int i2, Int i3) {
        if (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) {
            i2 -= view.getHeight()
        }
        super.showAsDropDown(view, i, i2, i3)
    }

    @Override // android.widget.PopupWindow
    fun update(View view, Int i, Int i2, Int i3, Int i4) {
        super.update(view, i, (COMPAT_OVERLAP_ANCHOR && this.mOverlapAnchor) ? i2 - view.getHeight() : i2, i3, i4)
    }
}
