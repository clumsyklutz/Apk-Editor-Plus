package android.support.v4.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

class PagerTabStrip extends PagerTitleStrip {
    private static val FULL_UNDERLINE_HEIGHT = 1
    private static val INDICATOR_HEIGHT = 3
    private static val MIN_PADDING_BOTTOM = 6
    private static val MIN_STRIP_HEIGHT = 32
    private static val MIN_TEXT_SPACING = 64
    private static val TAB_PADDING = 16
    private static val TAB_SPACING = 32
    private static val TAG = "PagerTabStrip"
    private Boolean mDrawFullUnderline
    private Boolean mDrawFullUnderlineSet
    private Int mFullUnderlineHeight
    private Boolean mIgnoreTap
    private Int mIndicatorColor
    private Int mIndicatorHeight
    private Float mInitialMotionX
    private Float mInitialMotionY
    private Int mMinPaddingBottom
    private Int mMinStripHeight
    private Int mMinTextSpacing
    private Int mTabAlpha
    private Int mTabPadding
    private final Paint mTabPaint
    private final Rect mTempRect
    private Int mTouchSlop

    constructor(@NonNull Context context) {
        this(context, null)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet)
        this.mTabPaint = Paint()
        this.mTempRect = Rect()
        this.mTabAlpha = 255
        this.mDrawFullUnderline = false
        this.mDrawFullUnderlineSet = false
        this.mIndicatorColor = this.mTextColor
        this.mTabPaint.setColor(this.mIndicatorColor)
        Float f = context.getResources().getDisplayMetrics().density
        this.mIndicatorHeight = (Int) ((3.0f * f) + 0.5f)
        this.mMinPaddingBottom = (Int) ((6.0f * f) + 0.5f)
        this.mMinTextSpacing = (Int) (64.0f * f)
        this.mTabPadding = (Int) ((16.0f * f) + 0.5f)
        this.mFullUnderlineHeight = (Int) ((1.0f * f) + 0.5f)
        this.mMinStripHeight = (Int) ((f * 32.0f) + 0.5f)
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop()
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom())
        setTextSpacing(getTextSpacing())
        setWillNotDraw(false)
        this.mPrevText.setFocusable(true)
        this.mPrevText.setOnClickListener(new View.OnClickListener() { // from class: android.support.v4.view.PagerTabStrip.1
            @Override // android.view.View.OnClickListener
            fun onClick(View view) {
                PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() - 1)
            }
        })
        this.mNextText.setFocusable(true)
        this.mNextText.setOnClickListener(new View.OnClickListener() { // from class: android.support.v4.view.PagerTabStrip.2
            @Override // android.view.View.OnClickListener
            fun onClick(View view) {
                PagerTabStrip.this.mPager.setCurrentItem(PagerTabStrip.this.mPager.getCurrentItem() + 1)
            }
        })
        if (getBackground() == null) {
            this.mDrawFullUnderline = true
        }
    }

    fun getDrawFullUnderline() {
        return this.mDrawFullUnderline
    }

    @Override // android.support.v4.view.PagerTitleStrip
    Int getMinHeight() {
        return Math.max(super.getMinHeight(), this.mMinStripHeight)
    }

    @ColorInt
    fun getTabIndicatorColor() {
        return this.mIndicatorColor
    }

    @Override // android.view.View
    protected fun onDraw(Canvas canvas) {
        super.onDraw(canvas)
        Int height = getHeight()
        Int left = this.mCurrText.getLeft() - this.mTabPadding
        Int right = this.mCurrText.getRight() + this.mTabPadding
        Int i = height - this.mIndicatorHeight
        this.mTabPaint.setColor((this.mTabAlpha << 24) | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK))
        canvas.drawRect(left, i, right, height, this.mTabPaint)
        if (this.mDrawFullUnderline) {
            this.mTabPaint.setColor((-16777216) | (this.mIndicatorColor & ViewCompat.MEASURED_SIZE_MASK))
            canvas.drawRect(getPaddingLeft(), height - this.mFullUnderlineHeight, getWidth() - getPaddingRight(), height, this.mTabPaint)
        }
    }

    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        Int action = motionEvent.getAction()
        if (action != 0 && this.mIgnoreTap) {
            return false
        }
        Float x = motionEvent.getX()
        Float y = motionEvent.getY()
        switch (action) {
            case 0:
                this.mInitialMotionX = x
                this.mInitialMotionY = y
                this.mIgnoreTap = false
                break
            case 1:
                if (x >= this.mCurrText.getLeft() - this.mTabPadding) {
                    if (x > this.mCurrText.getRight() + this.mTabPadding) {
                        this.mPager.setCurrentItem(this.mPager.getCurrentItem() + 1)
                        break
                    }
                } else {
                    this.mPager.setCurrentItem(this.mPager.getCurrentItem() - 1)
                    break
                }
                break
            case 2:
                if (Math.abs(x - this.mInitialMotionX) > this.mTouchSlop || Math.abs(y - this.mInitialMotionY) > this.mTouchSlop) {
                    this.mIgnoreTap = true
                    break
                }
                break
        }
        return true
    }

    @Override // android.view.View
    fun setBackgroundColor(@ColorInt Int i) {
        super.setBackgroundColor(i)
        if (this.mDrawFullUnderlineSet) {
            return
        }
        this.mDrawFullUnderline = ((-16777216) & i) == 0
    }

    @Override // android.view.View
    fun setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable)
        if (this.mDrawFullUnderlineSet) {
            return
        }
        this.mDrawFullUnderline = drawable == null
    }

    @Override // android.view.View
    fun setBackgroundResource(@DrawableRes Int i) {
        super.setBackgroundResource(i)
        if (this.mDrawFullUnderlineSet) {
            return
        }
        this.mDrawFullUnderline = i == 0
    }

    fun setDrawFullUnderline(Boolean z) {
        this.mDrawFullUnderline = z
        this.mDrawFullUnderlineSet = true
        invalidate()
    }

    @Override // android.view.View
    fun setPadding(Int i, Int i2, Int i3, Int i4) {
        if (i4 < this.mMinPaddingBottom) {
            i4 = this.mMinPaddingBottom
        }
        super.setPadding(i, i2, i3, i4)
    }

    fun setTabIndicatorColor(@ColorInt Int i) {
        this.mIndicatorColor = i
        this.mTabPaint.setColor(this.mIndicatorColor)
        invalidate()
    }

    fun setTabIndicatorColorResource(@ColorRes Int i) {
        setTabIndicatorColor(ContextCompat.getColor(getContext(), i))
    }

    @Override // android.support.v4.view.PagerTitleStrip
    fun setTextSpacing(Int i) {
        if (i < this.mMinTextSpacing) {
            i = this.mMinTextSpacing
        }
        super.setTextSpacing(i)
    }

    @Override // android.support.v4.view.PagerTitleStrip
    Unit updateTextPositions(Int i, Float f, Boolean z) {
        Rect rect = this.mTempRect
        Int height = getHeight()
        Int left = this.mCurrText.getLeft() - this.mTabPadding
        Int right = this.mCurrText.getRight() + this.mTabPadding
        Int i2 = height - this.mIndicatorHeight
        rect.set(left, i2, right, height)
        super.updateTextPositions(i, f, z)
        this.mTabAlpha = (Int) (Math.abs(f - 0.5f) * 2.0f * 255.0f)
        rect.union(this.mCurrText.getLeft() - this.mTabPadding, i2, this.mCurrText.getRight() + this.mTabPadding, height)
        invalidate(rect)
    }
}
