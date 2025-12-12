package android.support.v4.view

import android.R
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.viewpager.widget.ViewPager
import androidx.core.widget.TextViewCompat
import android.text.TextUtils
import android.text.method.SingleLineTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import java.lang.ref.WeakReference
import java.util.Locale

@ViewPager.DecorView
class PagerTitleStrip extends ViewGroup {
    private static val SIDE_ALPHA = 0.6f
    private static val TEXT_SPACING = 16
    TextView mCurrText
    private Int mGravity
    private Int mLastKnownCurrentPage
    Float mLastKnownPositionOffset
    TextView mNextText
    private Int mNonPrimaryAlpha
    private final PageListener mPageListener
    ViewPager mPager
    TextView mPrevText
    private Int mScaledTextSpacing
    Int mTextColor
    private Boolean mUpdatingPositions
    private Boolean mUpdatingText
    private WeakReference mWatchingAdapter
    private static final Array<Int> ATTRS = {R.attr.textAppearance, R.attr.textSize, R.attr.textColor, R.attr.gravity}
    private static final Array<Int> TEXT_ATTRS = {R.attr.textAllCaps}

    class PageListener extends DataSetObserver implements ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener {
        private Int mScrollState

        PageListener() {
        }

        @Override // android.support.v4.view.ViewPager.OnAdapterChangeListener
        fun onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2)
        }

        @Override // android.database.DataSetObserver
        fun onChanged() {
            PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter())
            PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f ? PagerTitleStrip.this.mLastKnownPositionOffset : 0.0f, true)
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        fun onPageScrollStateChanged(Int i) {
            this.mScrollState = i
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        fun onPageScrolled(Int i, Float f, Int i2) {
            if (f > 0.5f) {
                i++
            }
            PagerTitleStrip.this.updateTextPositions(i, f, false)
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        fun onPageSelected(Int i) {
            if (this.mScrollState == 0) {
                PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter())
                PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f ? PagerTitleStrip.this.mLastKnownPositionOffset : 0.0f, true)
            }
        }
    }

    class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale mLocale

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale
        }

        @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
        fun getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view)
            if (transformation != null) {
                return transformation.toString().toUpperCase(this.mLocale)
            }
            return null
        }
    }

    constructor(@NonNull Context context) {
        this(context, null)
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet)
        Boolean z = false
        this.mLastKnownCurrentPage = -1
        this.mLastKnownPositionOffset = -1.0f
        this.mPageListener = PageListener()
        TextView textView = TextView(context)
        this.mPrevText = textView
        addView(textView)
        TextView textView2 = TextView(context)
        this.mCurrText = textView2
        addView(textView2)
        TextView textView3 = TextView(context)
        this.mNextText = textView3
        addView(textView3)
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS)
        Int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)
        if (resourceId != 0) {
            TextViewCompat.setTextAppearance(this.mPrevText, resourceId)
            TextViewCompat.setTextAppearance(this.mCurrText, resourceId)
            TextViewCompat.setTextAppearance(this.mNextText, resourceId)
        }
        Int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0)
        if (dimensionPixelSize != 0) {
            setTextSize(0, dimensionPixelSize)
        }
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            Int color = typedArrayObtainStyledAttributes.getColor(2, 0)
            this.mPrevText.setTextColor(color)
            this.mCurrText.setTextColor(color)
            this.mNextText.setTextColor(color)
        }
        this.mGravity = typedArrayObtainStyledAttributes.getInteger(3, 80)
        typedArrayObtainStyledAttributes.recycle()
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor()
        setNonPrimaryAlpha(SIDE_ALPHA)
        this.mPrevText.setEllipsize(TextUtils.TruncateAt.END)
        this.mCurrText.setEllipsize(TextUtils.TruncateAt.END)
        this.mNextText.setEllipsize(TextUtils.TruncateAt.END)
        if (resourceId != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, TEXT_ATTRS)
            z = typedArrayObtainStyledAttributes2.getBoolean(0, false)
            typedArrayObtainStyledAttributes2.recycle()
        }
        if (z) {
            setSingleLineAllCaps(this.mPrevText)
            setSingleLineAllCaps(this.mCurrText)
            setSingleLineAllCaps(this.mNextText)
        } else {
            this.mPrevText.setSingleLine()
            this.mCurrText.setSingleLine()
            this.mNextText.setSingleLine()
        }
        this.mScaledTextSpacing = (Int) (context.getResources().getDisplayMetrics().density * 16.0f)
    }

    private fun setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod(SingleLineAllCapsTransform(textView.getContext()))
    }

    Int getMinHeight() {
        Drawable background = getBackground()
        if (background != null) {
            return background.getIntrinsicHeight()
        }
        return 0
    }

    fun getTextSpacing() {
        return this.mScaledTextSpacing
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ViewParent parent = getParent()
        if (!(parent is ViewPager)) {
            throw IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.")
        }
        ViewPager viewPager = (ViewPager) parent
        PagerAdapter adapter = viewPager.getAdapter()
        viewPager.setInternalPageChangeListener(this.mPageListener)
        viewPager.addOnAdapterChangeListener(this.mPageListener)
        this.mPager = viewPager
        updateAdapter(this.mWatchingAdapter != null ? (PagerAdapter) this.mWatchingAdapter.get() : null, adapter)
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (this.mPager != null) {
            updateAdapter(this.mPager.getAdapter(), null)
            this.mPager.setInternalPageChangeListener(null)
            this.mPager.removeOnAdapterChangeListener(this.mPageListener)
            this.mPager = null
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        if (this.mPager != null) {
            updateTextPositions(this.mLastKnownCurrentPage, this.mLastKnownPositionOffset >= 0.0f ? this.mLastKnownPositionOffset : 0.0f, true)
        }
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Int iMax
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw IllegalStateException("Must measure with an exact width")
        }
        Int paddingTop = getPaddingTop() + getPaddingBottom()
        Int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2)
        Int size = View.MeasureSpec.getSize(i)
        Int childMeasureSpec2 = getChildMeasureSpec(i, (Int) (size * 0.2f), -2)
        this.mPrevText.measure(childMeasureSpec2, childMeasureSpec)
        this.mCurrText.measure(childMeasureSpec2, childMeasureSpec)
        this.mNextText.measure(childMeasureSpec2, childMeasureSpec)
        if (View.MeasureSpec.getMode(i2) == 1073741824) {
            iMax = View.MeasureSpec.getSize(i2)
        } else {
            iMax = Math.max(getMinHeight(), paddingTop + this.mCurrText.getMeasuredHeight())
        }
        setMeasuredDimension(size, View.resolveSizeAndState(iMax, i2, this.mCurrText.getMeasuredState() << 16))
    }

    @Override // android.view.View, android.view.ViewParent
    fun requestLayout() {
        if (this.mUpdatingText) {
            return
        }
        super.requestLayout()
    }

    fun setGravity(Int i) {
        this.mGravity = i
        requestLayout()
    }

    fun setNonPrimaryAlpha(@FloatRange(from = 0.0d, to = 1.0d) Float f) {
        this.mNonPrimaryAlpha = ((Int) (255.0f * f)) & 255
        Int i = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK)
        this.mPrevText.setTextColor(i)
        this.mNextText.setTextColor(i)
    }

    fun setTextColor(@ColorInt Int i) {
        this.mTextColor = i
        this.mCurrText.setTextColor(i)
        Int i2 = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK)
        this.mPrevText.setTextColor(i2)
        this.mNextText.setTextColor(i2)
    }

    fun setTextSize(Int i, Float f) {
        this.mPrevText.setTextSize(i, f)
        this.mCurrText.setTextSize(i, f)
        this.mNextText.setTextSize(i, f)
    }

    fun setTextSpacing(Int i) {
        this.mScaledTextSpacing = i
        requestLayout()
    }

    Unit updateAdapter(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mPageListener)
            this.mWatchingAdapter = null
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.mPageListener)
            this.mWatchingAdapter = WeakReference(pagerAdapter2)
        }
        if (this.mPager != null) {
            this.mLastKnownCurrentPage = -1
            this.mLastKnownPositionOffset = -1.0f
            updateText(this.mPager.getCurrentItem(), pagerAdapter2)
            requestLayout()
        }
    }

    Unit updateText(Int i, PagerAdapter pagerAdapter) {
        CharSequence pageTitle = null
        Int count = pagerAdapter != null ? pagerAdapter.getCount() : 0
        this.mUpdatingText = true
        this.mPrevText.setText((i <= 0 || pagerAdapter == null) ? null : pagerAdapter.getPageTitle(i - 1))
        this.mCurrText.setText((pagerAdapter == null || i >= count) ? null : pagerAdapter.getPageTitle(i))
        if (i + 1 < count && pagerAdapter != null) {
            pageTitle = pagerAdapter.getPageTitle(i + 1)
        }
        this.mNextText.setText(pageTitle)
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (Int) (((getWidth() - getPaddingLeft()) - getPaddingRight()) * 0.8f)), Integer.MIN_VALUE)
        Int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE)
        this.mPrevText.measure(iMakeMeasureSpec, iMakeMeasureSpec2)
        this.mCurrText.measure(iMakeMeasureSpec, iMakeMeasureSpec2)
        this.mNextText.measure(iMakeMeasureSpec, iMakeMeasureSpec2)
        this.mLastKnownCurrentPage = i
        if (!this.mUpdatingPositions) {
            updateTextPositions(i, this.mLastKnownPositionOffset, false)
        }
        this.mUpdatingText = false
    }

    Unit updateTextPositions(Int i, Float f, Boolean z) {
        Int i2
        Int i3
        Int i4
        if (i != this.mLastKnownCurrentPage) {
            updateText(i, this.mPager.getAdapter())
        } else if (!z && f == this.mLastKnownPositionOffset) {
            return
        }
        this.mUpdatingPositions = true
        Int measuredWidth = this.mPrevText.getMeasuredWidth()
        Int measuredWidth2 = this.mCurrText.getMeasuredWidth()
        Int measuredWidth3 = this.mNextText.getMeasuredWidth()
        Int i5 = measuredWidth2 / 2
        Int width = getWidth()
        Int height = getHeight()
        Int paddingLeft = getPaddingLeft()
        Int paddingRight = getPaddingRight()
        Int paddingTop = getPaddingTop()
        Int paddingBottom = getPaddingBottom()
        Int i6 = paddingRight + i5
        Int i7 = (width - (paddingLeft + i5)) - i6
        Float f2 = 0.5f + f
        if (f2 > 1.0f) {
            f2 -= 1.0f
        }
        Int i8 = ((width - i6) - ((Int) (f2 * i7))) - i5
        Int i9 = i8 + measuredWidth2
        Int baseline = this.mPrevText.getBaseline()
        Int baseline2 = this.mCurrText.getBaseline()
        Int baseline3 = this.mNextText.getBaseline()
        Int iMax = Math.max(Math.max(baseline, baseline2), baseline3)
        Int i10 = iMax - baseline
        Int i11 = iMax - baseline2
        Int i12 = iMax - baseline3
        Int iMax2 = Math.max(Math.max(this.mPrevText.getMeasuredHeight() + i10, this.mCurrText.getMeasuredHeight() + i11), this.mNextText.getMeasuredHeight() + i12)
        switch (this.mGravity & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall) {
            case 16:
                Int i13 = (((height - paddingTop) - paddingBottom) - iMax2) / 2
                i2 = i13 + i10
                i3 = i13 + i11
                i4 = i13 + i12
                break
            case android.support.v7.appcompat.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                Int i14 = (height - paddingBottom) - iMax2
                i2 = i14 + i10
                i3 = i14 + i11
                i4 = i14 + i12
                break
            default:
                i2 = paddingTop + i10
                i3 = paddingTop + i11
                i4 = paddingTop + i12
                break
        }
        this.mCurrText.layout(i8, i3, i9, this.mCurrText.getMeasuredHeight() + i3)
        Int iMin = Math.min(paddingLeft, (i8 - this.mScaledTextSpacing) - measuredWidth)
        this.mPrevText.layout(iMin, i2, measuredWidth + iMin, this.mPrevText.getMeasuredHeight() + i2)
        Int iMax3 = Math.max((width - paddingRight) - measuredWidth3, this.mScaledTextSpacing + i9)
        this.mNextText.layout(iMax3, i4, iMax3 + measuredWidth3, this.mNextText.getMeasuredHeight() + i4)
        this.mLastKnownPositionOffset = f
        this.mUpdatingPositions = false
    }
}
