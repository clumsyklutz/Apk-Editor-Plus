package android.support.v4.widget

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import androidx.core.content.ContextCompat
import android.support.v4.view.AbsSavedState
import android.support.v4.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList

class SlidingPaneLayout extends ViewGroup {
    private static val DEFAULT_FADE_COLOR = -858993460
    private static val DEFAULT_OVERHANG_SIZE = 32
    private static val MIN_FLING_VELOCITY = 400
    private static val TAG = "SlidingPaneLayout"
    private Boolean mCanSlide
    private Int mCoveredFadeColor
    private Boolean mDisplayListReflectionLoaded
    final ViewDragHelper mDragHelper
    private Boolean mFirstLayout
    private Method mGetDisplayList
    private Float mInitialMotionX
    private Float mInitialMotionY
    Boolean mIsUnableToDrag
    private final Int mOverhangSize
    private PanelSlideListener mPanelSlideListener
    private Int mParallaxBy
    private Float mParallaxOffset
    final ArrayList mPostedRunnables
    Boolean mPreservedOpenState
    private Field mRecreateDisplayList
    private Drawable mShadowDrawableLeft
    private Drawable mShadowDrawableRight
    Float mSlideOffset
    Int mSlideRange
    View mSlideableView
    private Int mSliderFadeColor
    private final Rect mTmpRect

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private val mTmpRect = Rect()

        AccessibilityDelegate() {
        }

        private fun copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect
            accessibilityNodeInfoCompat2.getBoundsInParent(rect)
            accessibilityNodeInfoCompat.setBoundsInParent(rect)
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect)
            accessibilityNodeInfoCompat.setBoundsInScreen(rect)
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser())
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName())
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName())
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription())
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled())
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable())
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable())
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused())
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused())
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected())
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable())
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions())
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities())
        }

        fun filter(View view) {
            return SlidingPaneLayout.this.isDimmed(view)
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent)
            accessibilityEvent.setClassName(SlidingPaneLayout.class.getName())
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat)
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompatObtain)
            copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompatObtain)
            accessibilityNodeInfoCompatObtain.recycle()
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName())
            accessibilityNodeInfoCompat.setSource(view)
            Object parentForAccessibility = ViewCompat.getParentForAccessibility(view)
            if (parentForAccessibility is View) {
                accessibilityNodeInfoCompat.setParent((View) parentForAccessibility)
            }
            Int childCount = SlidingPaneLayout.this.getChildCount()
            for (Int i = 0; i < childCount; i++) {
                View childAt = SlidingPaneLayout.this.getChildAt(i)
                if (!filter(childAt) && childAt.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(childAt, 1)
                    accessibilityNodeInfoCompat.addChild(childAt)
                }
            }
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (filter(view)) {
                return false
            }
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent)
        }
    }

    class DisableLayerRunnable implements Runnable {
        final View mChildView

        DisableLayerRunnable(View view) {
            this.mChildView = view
        }

        @Override // java.lang.Runnable
        fun run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                this.mChildView.setLayerType(0, null)
                SlidingPaneLayout.this.invalidateChildRegion(this.mChildView)
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this)
        }
    }

    class DragHelperCallback extends ViewDragHelper.Callback {
        DragHelperCallback() {
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun clampViewPositionHorizontal(View view, Int i, Int i2) {
            LayoutParams layoutParams = (LayoutParams) SlidingPaneLayout.this.mSlideableView.getLayoutParams()
            if (!SlidingPaneLayout.this.isLayoutRtlSupport()) {
                Int paddingLeft = layoutParams.leftMargin + SlidingPaneLayout.this.getPaddingLeft()
                return Math.min(Math.max(i, paddingLeft), SlidingPaneLayout.this.mSlideRange + paddingLeft)
            }
            Int width = SlidingPaneLayout.this.getWidth() - ((layoutParams.rightMargin + SlidingPaneLayout.this.getPaddingRight()) + SlidingPaneLayout.this.mSlideableView.getWidth())
            return Math.max(Math.min(i, width), width - SlidingPaneLayout.this.mSlideRange)
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun clampViewPositionVertical(View view, Int i, Int i2) {
            return view.getTop()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.mSlideRange
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onEdgeDragStarted(Int i, Int i2) {
            SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, i2)
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewCaptured(View view, Int i) {
            SlidingPaneLayout.this.setAllChildrenVisible()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewDragStateChanged(Int i) {
            if (SlidingPaneLayout.this.mDragHelper.getViewDragState() == 0) {
                if (SlidingPaneLayout.this.mSlideOffset != 0.0f) {
                    SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView)
                    SlidingPaneLayout.this.mPreservedOpenState = true
                } else {
                    SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView)
                    SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView)
                    SlidingPaneLayout.this.mPreservedOpenState = false
                }
            }
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewPositionChanged(View view, Int i, Int i2, Int i3, Int i4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            SlidingPaneLayout.this.onPanelDragged(i)
            SlidingPaneLayout.this.invalidate()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewReleased(View view, Float f, Float f2) {
            Int paddingLeft
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                Int paddingRight = layoutParams.rightMargin + SlidingPaneLayout.this.getPaddingRight()
                if (f < 0.0f || (f == 0.0f && SlidingPaneLayout.this.mSlideOffset > 0.5f)) {
                    paddingRight += SlidingPaneLayout.this.mSlideRange
                }
                paddingLeft = (SlidingPaneLayout.this.getWidth() - paddingRight) - SlidingPaneLayout.this.mSlideableView.getWidth()
            } else {
                paddingLeft = layoutParams.leftMargin + SlidingPaneLayout.this.getPaddingLeft()
                if (f > 0.0f || (f == 0.0f && SlidingPaneLayout.this.mSlideOffset > 0.5f)) {
                    paddingLeft += SlidingPaneLayout.this.mSlideRange
                }
            }
            SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(paddingLeft, view.getTop())
            SlidingPaneLayout.this.invalidate()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun tryCaptureView(View view, Int i) {
            if (SlidingPaneLayout.this.mIsUnableToDrag) {
                return false
            }
            return ((LayoutParams) view.getLayoutParams()).slideable
        }
    }

    class LayoutParams extends ViewGroup.MarginLayoutParams {
        private static final Array<Int> ATTRS = {R.attr.layout_weight}
        Paint dimPaint
        Boolean dimWhenOffset
        Boolean slideable
        public Float weight

        constructor() {
            super(-1, -1)
            this.weight = 0.0f
        }

        constructor(Int i, Int i2) {
            super(i, i2)
            this.weight = 0.0f
        }

        constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet)
            this.weight = 0.0f
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS)
            this.weight = typedArrayObtainStyledAttributes.getFloat(0, 0.0f)
            typedArrayObtainStyledAttributes.recycle()
        }

        constructor(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams)
            this.weight = 0.0f
            this.weight = layoutParams.weight
        }

        constructor(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
            this.weight = 0.0f
        }

        constructor(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams)
            this.weight = 0.0f
        }
    }

    public interface PanelSlideListener {
        Unit onPanelClosed(@NonNull View view)

        Unit onPanelOpened(@NonNull View view)

        Unit onPanelSlide(@NonNull View view, Float f)
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v4.widget.SlidingPaneLayout.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel, null)
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return SavedState(parcel, null)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        Boolean isOpen

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader)
            this.isOpen = parcel.readInt() != 0
        }

        SavedState(Parcelable parcelable) {
            super(parcelable)
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeInt(this.isOpen ? 1 : 0)
        }
    }

    class SimplePanelSlideListener implements PanelSlideListener {
        @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
        fun onPanelClosed(View view) {
        }

        @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
        fun onPanelOpened(View view) {
        }

        @Override // android.support.v4.widget.SlidingPaneLayout.PanelSlideListener
        fun onPanelSlide(View view, Float f) {
        }
    }

    constructor(@NonNull Context context) {
        this(context, null)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mSliderFadeColor = DEFAULT_FADE_COLOR
        this.mFirstLayout = true
        this.mTmpRect = Rect()
        this.mPostedRunnables = ArrayList()
        Float f = context.getResources().getDisplayMetrics().density
        this.mOverhangSize = (Int) ((32.0f * f) + 0.5f)
        setWillNotDraw(false)
        ViewCompat.setAccessibilityDelegate(this, AccessibilityDelegate())
        ViewCompat.setImportantForAccessibility(this, 1)
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, DragHelperCallback())
        this.mDragHelper.setMinVelocity(f * 400.0f)
    }

    private fun closePane(View view, Int i) {
        if (!this.mFirstLayout && !smoothSlideTo(0.0f, i)) {
            return false
        }
        this.mPreservedOpenState = false
        return true
    }

    private fun dimChildView(View view, Float f, Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (f <= 0.0f || i == 0) {
            if (view.getLayerType() != 0) {
                if (layoutParams.dimPaint != null) {
                    layoutParams.dimPaint.setColorFilter(null)
                }
                DisableLayerRunnable disableLayerRunnable = DisableLayerRunnable(view)
                this.mPostedRunnables.add(disableLayerRunnable)
                ViewCompat.postOnAnimation(this, disableLayerRunnable)
                return
            }
            return
        }
        Int i2 = (((Int) ((((-16777216) & i) >>> 24) * f)) << 24) | (16777215 & i)
        if (layoutParams.dimPaint == null) {
            layoutParams.dimPaint = Paint()
        }
        layoutParams.dimPaint.setColorFilter(PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_OVER))
        if (view.getLayerType() != 2) {
            view.setLayerType(2, layoutParams.dimPaint)
        }
        invalidateChildRegion(view)
    }

    private fun openPane(View view, Int i) {
        if (!this.mFirstLayout && !smoothSlideTo(1.0f, i)) {
            return false
        }
        this.mPreservedOpenState = true
        return true
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun parallaxOtherViews(Float r10) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r9 = this
            r1 = 0
            r8 = 1065353216(0x3f800000, Float:1.0)
            Boolean r3 = r9.isLayoutRtlSupport()
            android.view.View r0 = r9.mSlideableView
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.support.v4.widget.SlidingPaneLayout$LayoutParams r0 = (android.support.v4.widget.SlidingPaneLayout.LayoutParams) r0
            Boolean r2 = r0.dimWhenOffset
            if (r2 == 0) goto L55
            if (r3 == 0) goto L52
            Int r0 = r0.rightMargin
        L17:
            if (r0 > 0) goto L55
            r0 = 1
        L1a:
            Int r4 = r9.getChildCount()
            r2 = r1
        L1f:
            if (r2 >= r4) goto L5c
            android.view.View r5 = r9.getChildAt(r2)
            android.view.View r1 = r9.mSlideableView
            if (r5 == r1) goto L4e
            Float r1 = r9.mParallaxOffset
            Float r1 = r8 - r1
            Int r6 = r9.mParallaxBy
            Float r6 = (Float) r6
            Float r1 = r1 * r6
            Int r1 = (Int) r1
            r9.mParallaxOffset = r10
            Float r6 = r8 - r10
            Int r7 = r9.mParallaxBy
            Float r7 = (Float) r7
            Float r6 = r6 * r7
            Int r6 = (Int) r6
            Int r1 = r1 - r6
            if (r3 == 0) goto L3f
            Int r1 = -r1
        L3f:
            r5.offsetLeftAndRight(r1)
            if (r0 == 0) goto L4e
            if (r3 == 0) goto L57
            Float r1 = r9.mParallaxOffset
            Float r1 = r1 - r8
        L49:
            Int r6 = r9.mCoveredFadeColor
            r9.dimChildView(r5, r1, r6)
        L4e:
            Int r1 = r2 + 1
            r2 = r1
            goto L1f
        L52:
            Int r0 = r0.leftMargin
            goto L17
        L55:
            r0 = r1
            goto L1a
        L57:
            Float r1 = r9.mParallaxOffset
            Float r1 = r8 - r1
            goto L49
        L5c:
            return
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.parallaxOtherViews(Float):Unit")
    }

    private fun viewIsOpaque(View view) {
        Drawable background
        if (view.isOpaque()) {
            return true
        }
        return Build.VERSION.SDK_INT < 18 && (background = view.getBackground()) != null && background.getOpacity() == -1
    }

    protected fun canScroll(View view, Boolean z, Int i, Int i2, Int i3) {
        if (view is ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view
            Int scrollX = view.getScrollX()
            Int scrollY = view.getScrollY()
            for (Int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount)
                if (i2 + scrollX >= childAt.getLeft() && i2 + scrollX < childAt.getRight() && i3 + scrollY >= childAt.getTop() && i3 + scrollY < childAt.getBottom() && canScroll(childAt, true, i, (i2 + scrollX) - childAt.getLeft(), (i3 + scrollY) - childAt.getTop())) {
                    return true
                }
            }
        }
        if (z) {
            if (!isLayoutRtlSupport()) {
                i = -i
            }
            if (view.canScrollHorizontally(i)) {
                return true
            }
        }
        return false
    }

    @Deprecated
    fun canSlide() {
        return this.mCanSlide
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams is LayoutParams) && super.checkLayoutParams(layoutParams)
    }

    fun closePane() {
        return closePane(this.mSlideableView, 0)
    }

    @Override // android.view.View
    fun computeScroll() {
        if (this.mDragHelper.continueSettling(true)) {
            if (this.mCanSlide) {
                ViewCompat.postInvalidateOnAnimation(this)
            } else {
                this.mDragHelper.abort()
            }
        }
    }

    Unit dispatchOnPanelClosed(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(view)
        }
        sendAccessibilityEvent(32)
    }

    Unit dispatchOnPanelOpened(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(view)
        }
        sendAccessibilityEvent(32)
    }

    Unit dispatchOnPanelSlide(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(view, this.mSlideOffset)
        }
    }

    @Override // android.view.View
    fun draw(Canvas canvas) {
        Int left
        Int right
        super.draw(canvas)
        Drawable drawable = isLayoutRtlSupport() ? this.mShadowDrawableRight : this.mShadowDrawableLeft
        View childAt = getChildCount() > 1 ? getChildAt(1) : null
        if (childAt == null || drawable == null) {
            return
        }
        Int top = childAt.getTop()
        Int bottom = childAt.getBottom()
        Int intrinsicWidth = drawable.getIntrinsicWidth()
        if (isLayoutRtlSupport()) {
            right = childAt.getRight()
            left = right + intrinsicWidth
        } else {
            left = childAt.getLeft()
            right = left - intrinsicWidth
        }
        drawable.setBounds(right, top, left, bottom)
        drawable.draw(canvas)
    }

    @Override // android.view.ViewGroup
    protected fun drawChild(Canvas canvas, View view, Long j) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int iSave = canvas.save()
        if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect)
            if (isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight())
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft())
            }
            canvas.clipRect(this.mTmpRect)
        }
        Boolean zDrawChild = super.drawChild(canvas, view, j)
        canvas.restoreToCount(iSave)
        return zDrawChild
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return LayoutParams()
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams is ViewGroup.MarginLayoutParams ? LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : LayoutParams(layoutParams)
    }

    @ColorInt
    fun getCoveredFadeColor() {
        return this.mCoveredFadeColor
    }

    @Px
    fun getParallaxDistance() {
        return this.mParallaxBy
    }

    @ColorInt
    fun getSliderFadeColor() {
        return this.mSliderFadeColor
    }

    Unit invalidateChildRegion(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 17) {
            ViewCompat.setLayerPaint(view, ((LayoutParams) view.getLayoutParams()).dimPaint)
            return
        }
        if (Build.VERSION.SDK_INT >= 16) {
            if (!this.mDisplayListReflectionLoaded) {
                try {
                    this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null)
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "Couldn't fetch getDisplayList method; dimming won't work right.", e)
                }
                try {
                    this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList")
                    this.mRecreateDisplayList.setAccessible(true)
                } catch (NoSuchFieldException e2) {
                    Log.e(TAG, "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e2)
                }
                this.mDisplayListReflectionLoaded = true
            }
            if (this.mGetDisplayList == null || this.mRecreateDisplayList == null) {
                view.invalidate()
                return
            }
            try {
                this.mRecreateDisplayList.setBoolean(view, true)
                this.mGetDisplayList.invoke(view, null)
            } catch (Exception e3) {
                Log.e(TAG, "Error refreshing display list state", e3)
            }
        }
        ViewCompat.postInvalidateOnAnimation(this, view.getLeft(), view.getTop(), view.getRight(), view.getBottom())
    }

    Boolean isDimmed(View view) {
        if (view == null) {
            return false
        }
        return this.mCanSlide && ((LayoutParams) view.getLayoutParams()).dimWhenOffset && this.mSlideOffset > 0.0f
    }

    Boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection(this) == 1
    }

    fun isOpen() {
        return !this.mCanSlide || this.mSlideOffset == 1.0f
    }

    fun isSlideable() {
        return this.mCanSlide
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.mFirstLayout = true
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.onDetachedFromWindow()
        this.mFirstLayout = true
        Int size = this.mPostedRunnables.size()
        for (Int i = 0; i < size; i++) {
            ((DisableLayerRunnable) this.mPostedRunnables.get(i)).run()
        }
        this.mPostedRunnables.clear()
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0051  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this
            r2 = 0
            r1 = 1
            Int r3 = r8.getActionMasked()
            Boolean r0 = r7.mCanSlide
            if (r0 != 0) goto L2d
            if (r3 != 0) goto L2d
            Int r0 = r7.getChildCount()
            if (r0 <= r1) goto L2d
            android.view.View r0 = r7.getChildAt(r1)
            if (r0 == 0) goto L2d
            android.support.v4.widget.ViewDragHelper r4 = r7.mDragHelper
            Float r5 = r8.getX()
            Int r5 = (Int) r5
            Float r6 = r8.getY()
            Int r6 = (Int) r6
            Boolean r0 = r4.isViewUnder(r0, r5, r6)
            if (r0 != 0) goto L41
            r0 = r1
        L2b:
            r7.mPreservedOpenState = r0
        L2d:
            Boolean r0 = r7.mCanSlide
            if (r0 == 0) goto L37
            Boolean r0 = r7.mIsUnableToDrag
            if (r0 == 0) goto L43
            if (r3 == 0) goto L43
        L37:
            android.support.v4.widget.ViewDragHelper r0 = r7.mDragHelper
            r0.cancel()
            Boolean r2 = super.onInterceptTouchEvent(r8)
        L40:
            return r2
        L41:
            r0 = r2
            goto L2b
        L43:
            r0 = 3
            if (r3 == r0) goto L48
            if (r3 != r1) goto L4e
        L48:
            android.support.v4.widget.ViewDragHelper r0 = r7.mDragHelper
            r0.cancel()
            goto L40
        L4e:
            switch(r3) {
                case 0: goto L5e
                case 1: goto L51
                case 2: goto L82
                default: goto L51
            }
        L51:
            r0 = r2
        L52:
            android.support.v4.widget.ViewDragHelper r3 = r7.mDragHelper
            Boolean r3 = r3.shouldInterceptTouchEvent(r8)
            if (r3 != 0) goto L5c
            if (r0 == 0) goto L40
        L5c:
            r2 = r1
            goto L40
        L5e:
            r7.mIsUnableToDrag = r2
            Float r0 = r8.getX()
            Float r3 = r8.getY()
            r7.mInitialMotionX = r0
            r7.mInitialMotionY = r3
            android.support.v4.widget.ViewDragHelper r4 = r7.mDragHelper
            android.view.View r5 = r7.mSlideableView
            Int r0 = (Int) r0
            Int r3 = (Int) r3
            Boolean r0 = r4.isViewUnder(r5, r0, r3)
            if (r0 == 0) goto L51
            android.view.View r0 = r7.mSlideableView
            Boolean r0 = r7.isDimmed(r0)
            if (r0 == 0) goto L51
            r0 = r1
            goto L52
        L82:
            Float r0 = r8.getX()
            Float r3 = r8.getY()
            Float r4 = r7.mInitialMotionX
            Float r0 = r0 - r4
            Float r0 = java.lang.Math.abs(r0)
            Float r4 = r7.mInitialMotionY
            Float r3 = r3 - r4
            Float r3 = java.lang.Math.abs(r3)
            android.support.v4.widget.ViewDragHelper r4 = r7.mDragHelper
            Int r4 = r4.getTouchSlop()
            Float r4 = (Float) r4
            Int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 <= 0) goto L51
            Int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L51
            android.support.v4.widget.ViewDragHelper r0 = r7.mDragHelper
            r0.cancel()
            r7.mIsUnableToDrag = r1
            goto L40
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.onInterceptTouchEvent(android.view.MotionEvent):Boolean")
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Int width
        Int i5
        Int i6
        Int i7
        Int i8
        Boolean zIsLayoutRtlSupport = isLayoutRtlSupport()
        if (zIsLayoutRtlSupport) {
            this.mDragHelper.setEdgeTrackingEnabled(2)
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1)
        }
        Int i9 = i3 - i
        Int paddingRight = zIsLayoutRtlSupport ? getPaddingRight() : getPaddingLeft()
        Int paddingLeft = zIsLayoutRtlSupport ? getPaddingLeft() : getPaddingRight()
        Int paddingTop = getPaddingTop()
        Int childCount = getChildCount()
        if (this.mFirstLayout) {
            this.mSlideOffset = (this.mCanSlide && this.mPreservedOpenState) ? 1.0f : 0.0f
        }
        Int i10 = 0
        Int i11 = paddingRight
        while (i10 < childCount) {
            View childAt = getChildAt(i10)
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                Int measuredWidth = childAt.getMeasuredWidth()
                Int i12 = 0
                if (layoutParams.slideable) {
                    Int iMin = (Math.min(paddingRight, (i9 - paddingLeft) - this.mOverhangSize) - i11) - (layoutParams.leftMargin + layoutParams.rightMargin)
                    this.mSlideRange = iMin
                    Int i13 = zIsLayoutRtlSupport ? layoutParams.rightMargin : layoutParams.leftMargin
                    layoutParams.dimWhenOffset = ((i11 + i13) + iMin) + (measuredWidth / 2) > i9 - paddingLeft
                    Int i14 = (Int) (iMin * this.mSlideOffset)
                    i6 = i11 + i13 + i14
                    this.mSlideOffset = i14 / this.mSlideRange
                } else {
                    i12 = (!this.mCanSlide || this.mParallaxBy == 0) ? 0 : (Int) ((1.0f - this.mSlideOffset) * this.mParallaxBy)
                    i6 = paddingRight
                }
                if (zIsLayoutRtlSupport) {
                    i8 = (i9 - i6) + i12
                    i7 = i8 - measuredWidth
                } else {
                    i7 = i6 - i12
                    i8 = i7 + measuredWidth
                }
                childAt.layout(i7, paddingTop, i8, childAt.getMeasuredHeight() + paddingTop)
                width = childAt.getWidth() + paddingRight
                i5 = i6
            } else {
                width = paddingRight
                i5 = i11
            }
            i10++
            paddingRight = width
            i11 = i5
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    parallaxOtherViews(this.mSlideOffset)
                }
                if (((LayoutParams) this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor)
                }
            } else {
                for (Int i15 = 0; i15 < childCount; i15++) {
                    dimChildView(getChildAt(i15), 0.0f, this.mSliderFadeColor)
                }
            }
            updateObscuredViewsVisibility(this.mSlideableView)
        }
        this.mFirstLayout = false
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c9 A[PHI: r3
  0x00c9: PHI (r3v12 Float) = (r3v11 Float), (r3v14 Float) binds: [B:34:0x00c0, B:36:0x00c7] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun onMeasure(Int r18, Int r19) {
        /*
            Method dump skipped, instructions count: 628
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SlidingPaneLayout.onMeasure(Int, Int):Unit")
    }

    Unit onPanelDragged(Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f
            return
        }
        Boolean zIsLayoutRtlSupport = isLayoutRtlSupport()
        LayoutParams layoutParams = (LayoutParams) this.mSlideableView.getLayoutParams()
        Int width = this.mSlideableView.getWidth()
        if (zIsLayoutRtlSupport) {
            i = (getWidth() - i) - width
        }
        this.mSlideOffset = (i - ((zIsLayoutRtlSupport ? layoutParams.rightMargin : layoutParams.leftMargin) + (zIsLayoutRtlSupport ? getPaddingRight() : getPaddingLeft()))) / this.mSlideRange
        if (this.mParallaxBy != 0) {
            parallaxOtherViews(this.mSlideOffset)
        }
        if (layoutParams.dimWhenOffset) {
            dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor)
        }
        dispatchOnPanelSlide(this.mSlideableView)
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        if (savedState.isOpen) {
            openPane()
        } else {
            closePane()
        }
        this.mPreservedOpenState = savedState.isOpen
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        savedState.isOpen = isSlideable() ? isOpen() : this.mPreservedOpenState
        return savedState
    }

    @Override // android.view.View
    protected fun onSizeChanged(Int i, Int i2, Int i3, Int i4) {
        super.onSizeChanged(i, i2, i3, i4)
        if (i != i3) {
            this.mFirstLayout = true
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCanSlide) {
            return super.onTouchEvent(motionEvent)
        }
        this.mDragHelper.processTouchEvent(motionEvent)
        switch (motionEvent.getActionMasked()) {
            case 0:
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                this.mInitialMotionX = x
                this.mInitialMotionY = y
                return true
            case 1:
                if (isDimmed(this.mSlideableView)) {
                    Float x2 = motionEvent.getX()
                    Float y2 = motionEvent.getY()
                    Float f = x2 - this.mInitialMotionX
                    Float f2 = y2 - this.mInitialMotionY
                    Int touchSlop = this.mDragHelper.getTouchSlop()
                    if ((f * f) + (f2 * f2) < touchSlop * touchSlop && this.mDragHelper.isViewUnder(this.mSlideableView, (Int) x2, (Int) y2)) {
                        closePane(this.mSlideableView, 0)
                    }
                }
                return true
            default:
                return true
        }
    }

    fun openPane() {
        return openPane(this.mSlideableView, 0)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2)
        if (isInTouchMode() || this.mCanSlide) {
            return
        }
        this.mPreservedOpenState = view == this.mSlideableView
    }

    Unit setAllChildrenVisible() {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0)
            }
        }
    }

    fun setCoveredFadeColor(@ColorInt Int i) {
        this.mCoveredFadeColor = i
    }

    fun setPanelSlideListener(@Nullable PanelSlideListener panelSlideListener) {
        this.mPanelSlideListener = panelSlideListener
    }

    fun setParallaxDistance(@Px Int i) {
        this.mParallaxBy = i
        requestLayout()
    }

    @Deprecated
    fun setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable)
    }

    fun setShadowDrawableLeft(@Nullable Drawable drawable) {
        this.mShadowDrawableLeft = drawable
    }

    fun setShadowDrawableRight(@Nullable Drawable drawable) {
        this.mShadowDrawableRight = drawable
    }

    @Deprecated
    fun setShadowResource(@DrawableRes Int i) {
        setShadowDrawable(getResources().getDrawable(i))
    }

    fun setShadowResourceLeft(Int i) {
        setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), i))
    }

    fun setShadowResourceRight(Int i) {
        setShadowDrawableRight(ContextCompat.getDrawable(getContext(), i))
    }

    fun setSliderFadeColor(@ColorInt Int i) {
        this.mSliderFadeColor = i
    }

    @Deprecated
    fun smoothSlideClosed() {
        closePane()
    }

    @Deprecated
    fun smoothSlideOpen() {
        openPane()
    }

    Boolean smoothSlideTo(Float f, Int i) {
        Int paddingLeft
        if (!this.mCanSlide) {
            return false
        }
        Boolean zIsLayoutRtlSupport = isLayoutRtlSupport()
        LayoutParams layoutParams = (LayoutParams) this.mSlideableView.getLayoutParams()
        if (zIsLayoutRtlSupport) {
            paddingLeft = (Int) (getWidth() - (((layoutParams.rightMargin + getPaddingRight()) + (this.mSlideRange * f)) + this.mSlideableView.getWidth()))
        } else {
            paddingLeft = (Int) (layoutParams.leftMargin + getPaddingLeft() + (this.mSlideRange * f))
        }
        if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, paddingLeft, this.mSlideableView.getTop())) {
            return false
        }
        setAllChildrenVisible()
        ViewCompat.postInvalidateOnAnimation(this)
        return true
    }

    Unit updateObscuredViewsVisibility(View view) {
        Int bottom
        Int top
        Int right
        Int left
        Boolean zIsLayoutRtlSupport = isLayoutRtlSupport()
        Int width = zIsLayoutRtlSupport ? getWidth() - getPaddingRight() : getPaddingLeft()
        Int paddingLeft = zIsLayoutRtlSupport ? getPaddingLeft() : getWidth() - getPaddingRight()
        Int paddingTop = getPaddingTop()
        Int height = getHeight() - getPaddingBottom()
        if (view == null || !viewIsOpaque(view)) {
            bottom = 0
            top = 0
            right = 0
            left = 0
        } else {
            left = view.getLeft()
            right = view.getRight()
            top = view.getTop()
            bottom = view.getBottom()
        }
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if (childAt == view) {
                return
            }
            if (childAt.getVisibility() != 8) {
                childAt.setVisibility((Math.max(zIsLayoutRtlSupport ? paddingLeft : width, childAt.getLeft()) < left || Math.max(paddingTop, childAt.getTop()) < top || Math.min(zIsLayoutRtlSupport ? width : paddingLeft, childAt.getRight()) > right || Math.min(height, childAt.getBottom()) > bottom) ? 0 : 4)
            }
        }
    }
}
