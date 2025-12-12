package androidx.cardview.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class CardView extends FrameLayout {
    private static final Array<Int> COLOR_BACKGROUND_ATTR = {R.attr.colorBackground}
    private static final h IMPL
    private final g mCardViewDelegate
    private Boolean mCompatPadding
    final Rect mContentPadding
    private Boolean mPreventCornerOverlap
    final Rect mShadowBounds
    Int mUserSetMinHeight
    Int mUserSetMinWidth

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = d()
        } else if (Build.VERSION.SDK_INT >= 17) {
            IMPL = b()
        } else {
            IMPL = e()
        }
        IMPL.initStatic()
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, m.cardViewStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        ColorStateList colorStateListValueOf
        super(context, attributeSet, i)
        this.mContentPadding = Rect()
        this.mShadowBounds = Rect()
        this.mCardViewDelegate = a(this)
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, q.CardView, i, p.MD_Card)
        if (typedArrayObtainStyledAttributes.hasValue(q.CardView_cardBackgroundColor)) {
            colorStateListValueOf = typedArrayObtainStyledAttributes.getColorStateList(q.CardView_cardBackgroundColor)
        } else {
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR)
            Int color = typedArrayObtainStyledAttributes2.getColor(0, 0)
            typedArrayObtainStyledAttributes2.recycle()
            Array<Float> fArr = new Float[3]
            Color.colorToHSV(color, fArr)
            colorStateListValueOf = ColorStateList.valueOf(fArr[2] > 0.5f ? getResources().getColor(n.cardview_light_background) : getResources().getColor(n.cardview_dark_background))
        }
        ColorStateList colorStateList = colorStateListValueOf
        Float dimension = typedArrayObtainStyledAttributes.getDimension(q.CardView_cardCornerRadius, 0.0f)
        Float dimension2 = typedArrayObtainStyledAttributes.getDimension(q.CardView_cardElevation, 0.0f)
        Float dimension3 = typedArrayObtainStyledAttributes.getDimension(q.CardView_cardMaxElevation, 0.0f)
        this.mCompatPadding = typedArrayObtainStyledAttributes.getBoolean(q.CardView_cardUseCompatPadding, false)
        this.mPreventCornerOverlap = typedArrayObtainStyledAttributes.getBoolean(q.CardView_cardPreventCornerOverlap, true)
        Int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_contentPadding, 0)
        this.mContentPadding.left = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_contentPaddingLeft, dimensionPixelSize)
        this.mContentPadding.top = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_contentPaddingTop, dimensionPixelSize)
        this.mContentPadding.right = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_contentPaddingRight, dimensionPixelSize)
        this.mContentPadding.bottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_contentPaddingBottom, dimensionPixelSize)
        Float f = dimension2 > dimension3 ? dimension2 : dimension3
        this.mUserSetMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_android_minWidth, 0)
        this.mUserSetMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(q.CardView_android_minHeight, 0)
        typedArrayObtainStyledAttributes.recycle()
        IMPL.initialize(this.mCardViewDelegate, context, colorStateList, dimension, dimension2, f)
    }

    fun getCardBackgroundColor() {
        return IMPL.getBackgroundColor(this.mCardViewDelegate)
    }

    fun getCardElevation() {
        return IMPL.getElevation(this.mCardViewDelegate)
    }

    fun getContentPaddingBottom() {
        return this.mContentPadding.bottom
    }

    fun getContentPaddingLeft() {
        return this.mContentPadding.left
    }

    fun getContentPaddingRight() {
        return this.mContentPadding.right
    }

    fun getContentPaddingTop() {
        return this.mContentPadding.top
    }

    fun getMaxCardElevation() {
        return IMPL.getMaxElevation(this.mCardViewDelegate)
    }

    fun getPreventCornerOverlap() {
        return this.mPreventCornerOverlap
    }

    fun getRadius() {
        return IMPL.getRadius(this.mCardViewDelegate)
    }

    fun getUseCompatPadding() {
        return this.mCompatPadding
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        if (IMPL is d) {
            super.onMeasure(i, i2)
            return
        }
        Int mode = View.MeasureSpec.getMode(i)
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            i = View.MeasureSpec.makeMeasureSpec(Math.max((Int) Math.ceil(IMPL.getMinWidth(this.mCardViewDelegate)), View.MeasureSpec.getSize(i)), mode)
        }
        Int mode2 = View.MeasureSpec.getMode(i2)
        if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.max((Int) Math.ceil(IMPL.getMinHeight(this.mCardViewDelegate)), View.MeasureSpec.getSize(i2)), mode2)
        }
        super.onMeasure(i, i2)
    }

    fun setCardBackgroundColor(Int i) {
        IMPL.setBackgroundColor(this.mCardViewDelegate, ColorStateList.valueOf(i))
    }

    fun setCardBackgroundColor(ColorStateList colorStateList) {
        IMPL.setBackgroundColor(this.mCardViewDelegate, colorStateList)
    }

    fun setCardElevation(Float f) {
        IMPL.setElevation(this.mCardViewDelegate, f)
    }

    fun setContentPadding(Int i, Int i2, Int i3, Int i4) {
        this.mContentPadding.set(i, i2, i3, i4)
        IMPL.updatePadding(this.mCardViewDelegate)
    }

    fun setMaxCardElevation(Float f) {
        IMPL.setMaxElevation(this.mCardViewDelegate, f)
    }

    @Override // android.view.View
    fun setMinimumHeight(Int i) {
        this.mUserSetMinHeight = i
        super.setMinimumHeight(i)
    }

    @Override // android.view.View
    fun setMinimumWidth(Int i) {
        this.mUserSetMinWidth = i
        super.setMinimumWidth(i)
    }

    @Override // android.view.View
    fun setPadding(Int i, Int i2, Int i3, Int i4) {
    }

    @Override // android.view.View
    fun setPaddingRelative(Int i, Int i2, Int i3, Int i4) {
    }

    fun setPreventCornerOverlap(Boolean z) {
        if (z != this.mPreventCornerOverlap) {
            this.mPreventCornerOverlap = z
            IMPL.onPreventCornerOverlapChanged(this.mCardViewDelegate)
        }
    }

    fun setRadius(Float f) {
        IMPL.setRadius(this.mCardViewDelegate, f)
    }

    fun setUseCompatPadding(Boolean z) {
        if (this.mCompatPadding != z) {
            this.mCompatPadding = z
            IMPL.onCompatPaddingChanged(this.mCardViewDelegate)
        }
    }
}
