package android.support.v4.widget

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.AccessibilityDelegateCompat
import android.support.v4.view.NestedScrollingChild2
import android.support.v4.view.NestedScrollingChildHelper
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ScrollingView
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.view.accessibility.AccessibilityRecordCompat
import android.support.v7.widget.ActivityChooserView
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.FocusFinder
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewParent
import android.view.accessibility.AccessibilityEvent
import android.view.animation.AnimationUtils
import android.widget.EdgeEffect
import android.widget.FrameLayout
import android.widget.OverScroller
import android.widget.ScrollView

class NestedScrollView extends FrameLayout implements NestedScrollingChild2, NestedScrollingParent2, ScrollingView {
    static val ANIMATED_SCROLL_GAP = 250
    private static val INVALID_POINTER = -1
    static val MAX_SCROLL_FACTOR = 0.5f
    private static val TAG = "NestedScrollView"
    private Int mActivePointerId
    private final NestedScrollingChildHelper mChildHelper
    private View mChildToScrollTo
    private EdgeEffect mEdgeGlowBottom
    private EdgeEffect mEdgeGlowTop
    private Boolean mFillViewport
    private Boolean mIsBeingDragged
    private Boolean mIsLaidOut
    private Boolean mIsLayoutDirty
    private Int mLastMotionY
    private Long mLastScroll
    private Int mLastScrollerY
    private Int mMaximumVelocity
    private Int mMinimumVelocity
    private Int mNestedYOffset
    private OnScrollChangeListener mOnScrollChangeListener
    private final NestedScrollingParentHelper mParentHelper
    private SavedState mSavedState
    private final Array<Int> mScrollConsumed
    private final Array<Int> mScrollOffset
    private OverScroller mScroller
    private Boolean mSmoothScrollingEnabled
    private final Rect mTempRect
    private Int mTouchSlop
    private VelocityTracker mVelocityTracker
    private Float mVerticalScrollFactor
    private static val ACCESSIBILITY_DELEGATE = AccessibilityDelegate()
    private static final Array<Int> SCROLLVIEW_STYLEABLE = {R.attr.fillViewport}

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent)
            NestedScrollView nestedScrollView = (NestedScrollView) view
            accessibilityEvent.setClassName(ScrollView.class.getName())
            accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0)
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX())
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY())
            AccessibilityRecordCompat.setMaxScrollX(accessibilityEvent, nestedScrollView.getScrollX())
            AccessibilityRecordCompat.setMaxScrollY(accessibilityEvent, nestedScrollView.getScrollRange())
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Int scrollRange
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat)
            NestedScrollView nestedScrollView = (NestedScrollView) view
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName())
            if (!nestedScrollView.isEnabled() || (scrollRange = nestedScrollView.getScrollRange()) <= 0) {
                return
            }
            accessibilityNodeInfoCompat.setScrollable(true)
            if (nestedScrollView.getScrollY() > 0) {
                accessibilityNodeInfoCompat.addAction(8192)
            }
            if (nestedScrollView.getScrollY() < scrollRange) {
                accessibilityNodeInfoCompat.addAction(4096)
            }
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun performAccessibilityAction(View view, Int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view
            if (!nestedScrollView.isEnabled()) {
                return false
            }
            switch (i) {
                case 4096:
                    Int iMin = Math.min(((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()) + nestedScrollView.getScrollY(), nestedScrollView.getScrollRange())
                    if (iMin == nestedScrollView.getScrollY()) {
                        return false
                    }
                    nestedScrollView.smoothScrollTo(0, iMin)
                    return true
                case 8192:
                    Int iMax = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0)
                    if (iMax == nestedScrollView.getScrollY()) {
                        return false
                    }
                    nestedScrollView.smoothScrollTo(0, iMax)
                    return true
                default:
                    return false
            }
        }
    }

    public interface OnScrollChangeListener {
        Unit onScrollChange(NestedScrollView nestedScrollView, Int i, Int i2, Int i3, Int i4)
    }

    class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.widget.NestedScrollView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        public Int scrollPosition

        SavedState(Parcel parcel) {
            super(parcel)
            this.scrollPosition = parcel.readInt()
        }

        SavedState(Parcelable parcelable) {
            super(parcelable)
        }

        fun toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.scrollPosition + "}"
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeInt(this.scrollPosition)
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
        this.mTempRect = Rect()
        this.mIsLayoutDirty = true
        this.mIsLaidOut = false
        this.mChildToScrollTo = null
        this.mIsBeingDragged = false
        this.mSmoothScrollingEnabled = true
        this.mActivePointerId = -1
        this.mScrollOffset = new Int[2]
        this.mScrollConsumed = new Int[2]
        initScrollView()
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, i, 0)
        setFillViewport(typedArrayObtainStyledAttributes.getBoolean(0, false))
        typedArrayObtainStyledAttributes.recycle()
        this.mParentHelper = NestedScrollingParentHelper(this)
        this.mChildHelper = NestedScrollingChildHelper(this)
        setNestedScrollingEnabled(true)
        ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE)
    }

    private fun canScroll() {
        if (getChildCount() <= 0) {
            return false
        }
        View childAt = getChildAt(0)
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
        return layoutParams.bottomMargin + (childAt.getHeight() + layoutParams.topMargin) > (getHeight() - getPaddingTop()) - getPaddingBottom()
    }

    private fun clamp(Int i, Int i2, Int i3) {
        if (i2 >= i3 || i < 0) {
            return 0
        }
        return i2 + i > i3 ? i3 - i2 : i
    }

    private fun doScrollY(Int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(0, i)
            } else {
                scrollBy(0, i)
            }
        }
    }

    private fun endDrag() {
        this.mIsBeingDragged = false
        recycleVelocityTracker()
        stopNestedScroll(0)
        if (this.mEdgeGlowTop != null) {
            this.mEdgeGlowTop.onRelease()
            this.mEdgeGlowBottom.onRelease()
        }
    }

    private fun ensureGlows() {
        if (getOverScrollMode() == 2) {
            this.mEdgeGlowTop = null
            this.mEdgeGlowBottom = null
        } else if (this.mEdgeGlowTop == null) {
            Context context = getContext()
            this.mEdgeGlowTop = EdgeEffect(context)
            this.mEdgeGlowBottom = EdgeEffect(context)
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.view.View findFocusableViewInBounds(Boolean r12, Int r13, Int r14) {
        /*
            r11 = this
            r0 = 2
            java.util.ArrayList r6 = r11.getFocusables(r0)
            r3 = 0
            r2 = 0
            Int r7 = r6.size()
            r0 = 0
            r5 = r0
        Ld:
            if (r5 >= r7) goto L5b
            java.lang.Object r0 = r6.get(r5)
            android.view.View r0 = (android.view.View) r0
            Int r4 = r0.getTop()
            Int r8 = r0.getBottom()
            if (r13 >= r8) goto L5c
            if (r4 >= r14) goto L5c
            if (r13 >= r4) goto L31
            if (r8 >= r14) goto L31
            r1 = 1
        L26:
            if (r3 != 0) goto L33
            r10 = r1
            r1 = r0
            r0 = r10
        L2b:
            Int r2 = r5 + 1
            r5 = r2
            r3 = r1
            r2 = r0
            goto Ld
        L31:
            r1 = 0
            goto L26
        L33:
            if (r12 == 0) goto L3b
            Int r9 = r3.getTop()
            if (r4 < r9) goto L43
        L3b:
            if (r12 != 0) goto L4d
            Int r4 = r3.getBottom()
            if (r8 <= r4) goto L4d
        L43:
            r4 = 1
        L44:
            if (r2 == 0) goto L4f
            if (r1 == 0) goto L5c
            if (r4 == 0) goto L5c
            r1 = r0
            r0 = r2
            goto L2b
        L4d:
            r4 = 0
            goto L44
        L4f:
            if (r1 == 0) goto L56
            r1 = 1
            r10 = r1
            r1 = r0
            r0 = r10
            goto L2b
        L56:
            if (r4 == 0) goto L5c
            r1 = r0
            r0 = r2
            goto L2b
        L5b:
            return r3
        L5c:
            r0 = r2
            r1 = r3
            goto L2b
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.NestedScrollView.findFocusableViewInBounds(Boolean, Int, Int):android.view.View")
    }

    private fun flingWithNestedDispatch(Int i) {
        Int scrollY = getScrollY()
        Boolean z = (scrollY > 0 || i > 0) && (scrollY < getScrollRange() || i < 0)
        if (dispatchNestedPreFling(0.0f, i)) {
            return
        }
        dispatchNestedFling(0.0f, i, z)
        fling(i)
    }

    private fun getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = TypedValue()
            Context context = getContext()
            if (!context.getTheme().resolveAttribute(R.attr.listPreferredItemHeight, typedValue, true)) {
                throw IllegalStateException("Expected theme to define listPreferredItemHeight.")
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics())
        }
        return this.mVerticalScrollFactor
    }

    private fun inChild(Int i, Int i2) {
        if (getChildCount() <= 0) {
            return false
        }
        Int scrollY = getScrollY()
        View childAt = getChildAt(0)
        return i2 >= childAt.getTop() - scrollY && i2 < childAt.getBottom() - scrollY && i >= childAt.getLeft() && i < childAt.getRight()
    }

    private fun initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain()
        } else {
            this.mVelocityTracker.clear()
        }
    }

    private fun initScrollView() {
        this.mScroller = OverScroller(getContext())
        setFocusable(true)
        setDescendantFocusability(262144)
        setWillNotDraw(false)
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext())
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop()
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity()
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity()
    }

    private fun initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun isOffScreen(View view) {
        return !isWithinDeltaOfScreen(view, 0, getHeight())
    }

    private fun isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true
        }
        Object parent = view.getParent()
        return (parent is ViewGroup) && isViewDescendantOf((View) parent, view2)
    }

    private fun isWithinDeltaOfScreen(View view, Int i, Int i2) {
        view.getDrawingRect(this.mTempRect)
        offsetDescendantRectToMyCoords(view, this.mTempRect)
        return this.mTempRect.bottom + i >= getScrollY() && this.mTempRect.top - i <= getScrollY() + i2
    }

    private fun onSecondaryPointerUp(MotionEvent motionEvent) {
        Int actionIndex = motionEvent.getActionIndex()
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            Int i = actionIndex == 0 ? 1 : 0
            this.mLastMotionY = (Int) motionEvent.getY(i)
            this.mActivePointerId = motionEvent.getPointerId(i)
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear()
            }
        }
    }

    private fun recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle()
            this.mVelocityTracker = null
        }
    }

    private fun scrollAndFocus(Int i, Int i2, Int i3) {
        Boolean z = false
        Int height = getHeight()
        Int scrollY = getScrollY()
        Int i4 = scrollY + height
        Boolean z2 = i == 33
        View viewFindFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3)
        if (viewFindFocusableViewInBounds == null) {
            viewFindFocusableViewInBounds = this
        }
        if (i2 < scrollY || i3 > i4) {
            doScrollY(z2 ? i2 - scrollY : i3 - i4)
            z = true
        }
        if (viewFindFocusableViewInBounds != findFocus()) {
            viewFindFocusableViewInBounds.requestFocus(i)
        }
        return z
    }

    private fun scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect)
        offsetDescendantRectToMyCoords(view, this.mTempRect)
        Int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect)
        if (iComputeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen)
        }
    }

    private fun scrollToChildRect(Rect rect, Boolean z) {
        Int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect)
        Boolean z2 = iComputeScrollDeltaToGetChildRectOnScreen != 0
        if (z2) {
            if (z) {
                scrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen)
            } else {
                smoothScrollBy(0, iComputeScrollDeltaToGetChildRectOnScreen)
            }
        }
        return z2
    }

    @Override // android.view.ViewGroup
    fun addView(View view) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view)
    }

    @Override // android.view.ViewGroup
    fun addView(View view, Int i) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view, i)
    }

    @Override // android.view.ViewGroup
    fun addView(View view, Int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view, i, layoutParams)
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    fun addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view, layoutParams)
    }

    fun arrowScroll(Int i) {
        Int iMin
        View viewFindFocus = findFocus()
        View view = viewFindFocus == this ? null : viewFindFocus
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i)
        Int maxScrollAmount = getMaxScrollAmount()
        if (viewFindNextFocus == null || !isWithinDeltaOfScreen(viewFindNextFocus, maxScrollAmount, getHeight())) {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                iMin = getScrollY()
            } else if (i != 130 || getChildCount() <= 0) {
                iMin = maxScrollAmount
            } else {
                View childAt = getChildAt(0)
                iMin = Math.min((((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin + childAt.getBottom()) - ((getScrollY() + getHeight()) - getPaddingBottom()), maxScrollAmount)
            }
            if (iMin == 0) {
                return false
            }
            if (i != 130) {
                iMin = -iMin
            }
            doScrollY(iMin)
        } else {
            viewFindNextFocus.getDrawingRect(this.mTempRect)
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.mTempRect)
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect))
            viewFindNextFocus.requestFocus(i)
        }
        if (view != null && view.isFocused() && isOffScreen(view)) {
            Int descendantFocusability = getDescendantFocusability()
            setDescendantFocusability(131072)
            requestFocus()
            setDescendantFocusability(descendantFocusability)
        }
        return true
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent()
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset()
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange()
    }

    @Override // android.view.View
    fun computeScroll() {
        if (!this.mScroller.computeScrollOffset()) {
            if (hasNestedScrollingParent(1)) {
                stopNestedScroll(1)
            }
            this.mLastScrollerY = 0
            return
        }
        this.mScroller.getCurrX()
        Int currY = this.mScroller.getCurrY()
        Int i = currY - this.mLastScrollerY
        if (dispatchNestedPreScroll(0, i, this.mScrollConsumed, null, 1)) {
            i -= this.mScrollConsumed[1]
        }
        if (i != 0) {
            Int scrollRange = getScrollRange()
            Int scrollY = getScrollY()
            overScrollByCompat(0, i, getScrollX(), scrollY, 0, scrollRange, 0, 0, false)
            Int scrollY2 = getScrollY() - scrollY
            if (!dispatchNestedScroll(0, scrollY2, 0, i - scrollY2, null, 1)) {
                Int overScrollMode = getOverScrollMode()
                if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) {
                    ensureGlows()
                    if (currY <= 0 && scrollY > 0) {
                        this.mEdgeGlowTop.onAbsorb((Int) this.mScroller.getCurrVelocity())
                    } else if (currY >= scrollRange && scrollY < scrollRange) {
                        this.mEdgeGlowBottom.onAbsorb((Int) this.mScroller.getCurrVelocity())
                    }
                }
            }
        }
        this.mLastScrollerY = currY
        ViewCompat.postInvalidateOnAnimation(this)
    }

    protected fun computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        Int iMax
        if (getChildCount() == 0) {
            return 0
        }
        Int height = getHeight()
        Int scrollY = getScrollY()
        Int i = scrollY + height
        Int verticalFadingEdgeLength = getVerticalFadingEdgeLength()
        Int i2 = rect.top > 0 ? scrollY + verticalFadingEdgeLength : scrollY
        View childAt = getChildAt(0)
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
        Int i3 = rect.bottom < (childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin ? i - verticalFadingEdgeLength : i
        if (rect.bottom > i3 && rect.top > i2) {
            iMax = Math.min(rect.height() > height ? (rect.top - i2) + 0 : (rect.bottom - i3) + 0, (layoutParams.bottomMargin + childAt.getBottom()) - i)
        } else if (rect.top >= i2 || rect.bottom >= i3) {
            iMax = 0
        } else {
            iMax = Math.max(rect.height() > height ? 0 - (i3 - rect.bottom) : 0 - (i2 - rect.top), -getScrollY())
        }
        return iMax
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent()
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset())
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun computeVerticalScrollRange() {
        Int childCount = getChildCount()
        Int height = (getHeight() - getPaddingBottom()) - getPaddingTop()
        if (childCount == 0) {
            return height
        }
        View childAt = getChildAt(0)
        Int bottom = ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin + childAt.getBottom()
        Int scrollY = getScrollY()
        Int iMax = Math.max(0, bottom - height)
        return scrollY < 0 ? bottom - scrollY : scrollY > iMax ? bottom + (scrollY - iMax) : bottom
    }

    @Override // android.view.ViewGroup, android.view.View
    fun dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedFling(Float f, Float f2, Boolean z) {
        return this.mChildHelper.dispatchNestedFling(f, f2, z)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedPreFling(Float f, Float f2) {
        return this.mChildHelper.dispatchNestedPreFling(f, f2)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedPreScroll(Int i, Int i2, Array<Int> iArr, Array<Int> iArr2) {
        return dispatchNestedPreScroll(i, i2, iArr, iArr2, 0)
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    fun dispatchNestedPreScroll(Int i, Int i2, Array<Int> iArr, Array<Int> iArr2, Int i3) {
        return this.mChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2, i3)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, Array<Int> iArr) {
        return dispatchNestedScroll(i, i2, i3, i4, iArr, 0)
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    fun dispatchNestedScroll(Int i, Int i2, Int i3, Int i4, Array<Int> iArr, Int i5) {
        return this.mChildHelper.dispatchNestedScroll(i, i2, i3, i4, iArr, i5)
    }

    @Override // android.view.View
    fun draw(Canvas canvas) {
        Int paddingLeft
        Int paddingLeft2
        Int paddingLeft3 = 0
        super.draw(canvas)
        if (this.mEdgeGlowTop != null) {
            Int scrollY = getScrollY()
            if (!this.mEdgeGlowTop.isFinished()) {
                Int iSave = canvas.save()
                Int width = getWidth()
                Int height = getHeight()
                Int iMin = Math.min(0, scrollY)
                if (Build.VERSION.SDK_INT < 21 || getClipToPadding()) {
                    paddingLeft = width - (getPaddingLeft() + getPaddingRight())
                    paddingLeft2 = getPaddingLeft() + 0
                } else {
                    paddingLeft = width
                    paddingLeft2 = 0
                }
                if (Build.VERSION.SDK_INT >= 21 && getClipToPadding()) {
                    height -= getPaddingTop() + getPaddingBottom()
                    iMin += getPaddingTop()
                }
                canvas.translate(paddingLeft2, iMin)
                this.mEdgeGlowTop.setSize(paddingLeft, height)
                if (this.mEdgeGlowTop.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                canvas.restoreToCount(iSave)
            }
            if (this.mEdgeGlowBottom.isFinished()) {
                return
            }
            Int iSave2 = canvas.save()
            Int width2 = getWidth()
            Int height2 = getHeight()
            Int iMax = Math.max(getScrollRange(), scrollY) + height2
            if (Build.VERSION.SDK_INT < 21 || getClipToPadding()) {
                width2 -= getPaddingLeft() + getPaddingRight()
                paddingLeft3 = getPaddingLeft() + 0
            }
            if (Build.VERSION.SDK_INT >= 21 && getClipToPadding()) {
                height2 -= getPaddingTop() + getPaddingBottom()
                iMax -= getPaddingBottom()
            }
            canvas.translate(paddingLeft3 - width2, iMax)
            canvas.rotate(180.0f, width2, 0.0f)
            this.mEdgeGlowBottom.setSize(width2, height2)
            if (this.mEdgeGlowBottom.draw(canvas)) {
                ViewCompat.postInvalidateOnAnimation(this)
            }
            canvas.restoreToCount(iSave2)
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0045 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun executeKeyEvent(@android.support.annotation.NonNull android.view.KeyEvent r5) {
        /*
            r4 = this
            r0 = 33
            r2 = 0
            r1 = 130(0x82, Float:1.82E-43)
            android.graphics.Rect r3 = r4.mTempRect
            r3.setEmpty()
            Boolean r3 = r4.canScroll()
            if (r3 != 0) goto L38
            Boolean r0 = r4.isFocused()
            if (r0 == 0) goto L37
            Int r0 = r5.getKeyCode()
            r3 = 4
            if (r0 == r3) goto L37
            android.view.View r0 = r4.findFocus()
            if (r0 != r4) goto L24
            r0 = 0
        L24:
            android.view.FocusFinder r3 = android.view.FocusFinder.getInstance()
            android.view.View r0 = r3.findNextFocus(r4, r0, r1)
            if (r0 == 0) goto L37
            if (r0 == r4) goto L37
            Boolean r0 = r0.requestFocus(r1)
            if (r0 == 0) goto L37
            r2 = 1
        L37:
            return r2
        L38:
            Int r3 = r5.getAction()
            if (r3 != 0) goto L45
            Int r3 = r5.getKeyCode()
            switch(r3) {
                case 19: goto L48
                case 20: goto L58
                case 62: goto L68
                default: goto L45
            }
        L45:
            r0 = r2
        L46:
            r2 = r0
            goto L37
        L48:
            Boolean r1 = r5.isAltPressed()
            if (r1 != 0) goto L53
            Boolean r0 = r4.arrowScroll(r0)
            goto L46
        L53:
            Boolean r0 = r4.fullScroll(r0)
            goto L46
        L58:
            Boolean r0 = r5.isAltPressed()
            if (r0 != 0) goto L63
            Boolean r0 = r4.arrowScroll(r1)
            goto L46
        L63:
            Boolean r0 = r4.fullScroll(r1)
            goto L46
        L68:
            Boolean r3 = r5.isShiftPressed()
            if (r3 == 0) goto L72
        L6e:
            r4.pageScroll(r0)
            goto L45
        L72:
            r0 = r1
            goto L6e
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.NestedScrollView.executeKeyEvent(android.view.KeyEvent):Boolean")
    }

    fun fling(Int i) {
        if (getChildCount() > 0) {
            startNestedScroll(2, 1)
            this.mScroller.fling(getScrollX(), getScrollY(), 0, i, 0, 0, Integer.MIN_VALUE, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0, 0)
            this.mLastScrollerY = getScrollY()
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun fullScroll(Int i) {
        Int childCount
        Boolean z = i == 130
        Int height = getHeight()
        this.mTempRect.top = 0
        this.mTempRect.bottom = height
        if (z && (childCount = getChildCount()) > 0) {
            View childAt = getChildAt(childCount - 1)
            this.mTempRect.bottom = ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin + childAt.getBottom() + getPaddingBottom()
            this.mTempRect.top = this.mTempRect.bottom - height
        }
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom)
    }

    @Override // android.view.View
    protected fun getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f
        }
        View childAt = getChildAt(0)
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
        Int verticalFadingEdgeLength = getVerticalFadingEdgeLength()
        Int bottom = ((layoutParams.bottomMargin + childAt.getBottom()) - getScrollY()) - (getHeight() - getPaddingBottom())
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength
        }
        return 1.0f
    }

    fun getMaxScrollAmount() {
        return (Int) (MAX_SCROLL_FACTOR * getHeight())
    }

    @Override // android.view.ViewGroup, android.support.v4.view.NestedScrollingParent
    fun getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes()
    }

    Int getScrollRange() {
        if (getChildCount() <= 0) {
            return 0
        }
        View childAt = getChildAt(0)
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
        return Math.max(0, (layoutParams.bottomMargin + (childAt.getHeight() + layoutParams.topMargin)) - ((getHeight() - getPaddingTop()) - getPaddingBottom()))
    }

    @Override // android.view.View
    protected fun getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f
        }
        Int verticalFadingEdgeLength = getVerticalFadingEdgeLength()
        Int scrollY = getScrollY()
        if (scrollY < verticalFadingEdgeLength) {
            return scrollY / verticalFadingEdgeLength
        }
        return 1.0f
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun hasNestedScrollingParent() {
        return hasNestedScrollingParent(0)
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    fun hasNestedScrollingParent(Int i) {
        return this.mChildHelper.hasNestedScrollingParent(i)
    }

    fun isFillViewport() {
        return this.mFillViewport
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled()
    }

    fun isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled
    }

    @Override // android.view.ViewGroup
    protected fun measureChild(View view, Int i, Int i2) {
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(0, 0))
    }

    @Override // android.view.ViewGroup
    protected fun measureChildWithMargins(View view, Int i, Int i2, Int i3, Int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.bottomMargin + marginLayoutParams.topMargin, 0))
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.mIsLaidOut = false
    }

    @Override // android.view.View
    fun onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0) {
            return false
        }
        switch (motionEvent.getAction()) {
            case 8:
                if (!this.mIsBeingDragged) {
                    Float axisValue = motionEvent.getAxisValue(9)
                    if (axisValue != 0.0f) {
                        Int verticalScrollFactorCompat = (Int) (axisValue * getVerticalScrollFactorCompat())
                        Int scrollRange = getScrollRange()
                        Int scrollY = getScrollY()
                        Int i = scrollY - verticalScrollFactorCompat
                        if (i < 0) {
                            scrollRange = 0
                        } else if (i <= scrollRange) {
                            scrollRange = i
                        }
                        if (scrollRange != scrollY) {
                            super.scrollTo(getScrollX(), scrollRange)
                            break
                        }
                    }
                }
                break
        }
        return false
    }

    @Override // android.view.ViewGroup
    fun onInterceptTouchEvent(MotionEvent motionEvent) {
        Int action = motionEvent.getAction()
        if (action == 2 && this.mIsBeingDragged) {
            return true
        }
        switch (action & 255) {
            case 0:
                Int y = (Int) motionEvent.getY()
                if (!inChild((Int) motionEvent.getX(), y)) {
                    this.mIsBeingDragged = false
                    recycleVelocityTracker()
                    break
                } else {
                    this.mLastMotionY = y
                    this.mActivePointerId = motionEvent.getPointerId(0)
                    initOrResetVelocityTracker()
                    this.mVelocityTracker.addMovement(motionEvent)
                    this.mScroller.computeScrollOffset()
                    this.mIsBeingDragged = this.mScroller.isFinished() ? false : true
                    startNestedScroll(2, 0)
                    break
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false
                this.mActivePointerId = -1
                recycleVelocityTracker()
                if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                stopNestedScroll(0)
                break
            case 2:
                Int i = this.mActivePointerId
                if (i != -1) {
                    Int iFindPointerIndex = motionEvent.findPointerIndex(i)
                    if (iFindPointerIndex != -1) {
                        Int y2 = (Int) motionEvent.getY(iFindPointerIndex)
                        if (Math.abs(y2 - this.mLastMotionY) > this.mTouchSlop && (getNestedScrollAxes() & 2) == 0) {
                            this.mIsBeingDragged = true
                            this.mLastMotionY = y2
                            initVelocityTrackerIfNotExists()
                            this.mVelocityTracker.addMovement(motionEvent)
                            this.mNestedYOffset = 0
                            ViewParent parent = getParent()
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true)
                                break
                            }
                        }
                    } else {
                        Log.e(TAG, "Invalid pointerId=" + i + " in onInterceptTouchEvent")
                        break
                    }
                }
                break
            case 6:
                onSecondaryPointerUp(motionEvent)
                break
        }
        return this.mIsBeingDragged
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int measuredHeight = 0
        super.onLayout(z, i, i2, i3, i4)
        this.mIsLayoutDirty = false
        if (this.mChildToScrollTo != null && isViewDescendantOf(this.mChildToScrollTo, this)) {
            scrollToChild(this.mChildToScrollTo)
        }
        this.mChildToScrollTo = null
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                scrollTo(getScrollX(), this.mSavedState.scrollPosition)
                this.mSavedState = null
            }
            if (getChildCount() > 0) {
                View childAt = getChildAt(0)
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
                measuredHeight = layoutParams.bottomMargin + childAt.getMeasuredHeight() + layoutParams.topMargin
            }
            Int paddingTop = ((i4 - i2) - getPaddingTop()) - getPaddingBottom()
            Int scrollY = getScrollY()
            Int iClamp = clamp(scrollY, paddingTop, measuredHeight)
            if (iClamp != scrollY) {
                scrollTo(getScrollX(), iClamp)
            }
        }
        scrollTo(getScrollX(), getScrollY())
        this.mIsLaidOut = true
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        super.onMeasure(i, i2)
        if (this.mFillViewport && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0)
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
            Int measuredHeight = childAt.getMeasuredHeight()
            Int measuredHeight2 = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - layoutParams.topMargin) - layoutParams.bottomMargin
            if (measuredHeight < measuredHeight2) {
                childAt.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824))
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedFling(View view, Float f, Float f2, Boolean z) {
        if (z) {
            return false
        }
        flingWithNestedDispatch((Int) f2)
        return true
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreFling(View view, Float f, Float f2) {
        return dispatchNestedPreFling(f, f2)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreScroll(View view, Int i, Int i2, Array<Int> iArr) {
        onNestedPreScroll(view, i, i2, iArr, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onNestedPreScroll(@NonNull View view, Int i, Int i2, @NonNull Array<Int> iArr, Int i3) {
        dispatchNestedPreScroll(i, i2, iArr, null, i3)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScroll(View view, Int i, Int i2, Int i3, Int i4) {
        onNestedScroll(view, i, i2, i3, i4, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onNestedScroll(View view, Int i, Int i2, Int i3, Int i4, Int i5) {
        Int scrollY = getScrollY()
        scrollBy(0, i4)
        Int scrollY2 = getScrollY() - scrollY
        dispatchNestedScroll(0, scrollY2, 0, i4 - scrollY2, null, i5)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScrollAccepted(View view, View view2, Int i) {
        onNestedScrollAccepted(view, view2, i, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onNestedScrollAccepted(@NonNull View view, @NonNull View view2, Int i, Int i2) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, i, i2)
        startNestedScroll(2, i2)
    }

    @Override // android.view.View
    protected fun onOverScrolled(Int i, Int i2, Boolean z, Boolean z2) {
        super.scrollTo(i, i2)
    }

    @Override // android.view.ViewGroup
    protected fun onRequestFocusInDescendants(Int i, Rect rect) {
        if (i == 2) {
            i = 130
        } else if (i == 1) {
            i = 33
        }
        View viewFindNextFocus = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, i) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, i)
        if (viewFindNextFocus == null || isOffScreen(viewFindNextFocus)) {
            return false
        }
        return viewFindNextFocus.requestFocus(i, rect)
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        this.mSavedState = savedState
        requestLayout()
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        savedState.scrollPosition = getScrollY()
        return savedState
    }

    @Override // android.view.View
    protected fun onScrollChanged(Int i, Int i2, Int i3, Int i4) {
        super.onScrollChanged(i, i2, i3, i4)
        if (this.mOnScrollChangeListener != null) {
            this.mOnScrollChangeListener.onScrollChange(this, i, i2, i3, i4)
        }
    }

    @Override // android.view.View
    protected fun onSizeChanged(Int i, Int i2, Int i3, Int i4) {
        super.onSizeChanged(i, i2, i3, i4)
        View viewFindFocus = findFocus()
        if (viewFindFocus == null || this == viewFindFocus || !isWithinDeltaOfScreen(viewFindFocus, 0, i4)) {
            return
        }
        viewFindFocus.getDrawingRect(this.mTempRect)
        offsetDescendantRectToMyCoords(viewFindFocus, this.mTempRect)
        doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect))
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStartNestedScroll(View view, View view2, Int i) {
        return onStartNestedScroll(view, view2, i, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onStartNestedScroll(@NonNull View view, @NonNull View view2, Int i, Int i2) {
        return (i & 2) != 0
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onStopNestedScroll(@NonNull View view, Int i) {
        this.mParentHelper.onStopNestedScroll(view, i)
        stopNestedScroll(i)
    }

    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        ViewParent parent
        initVelocityTrackerIfNotExists()
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent)
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 0) {
            this.mNestedYOffset = 0
        }
        motionEventObtain.offsetLocation(0.0f, this.mNestedYOffset)
        switch (actionMasked) {
            case 0:
                if (getChildCount() != 0) {
                    Boolean z = !this.mScroller.isFinished()
                    this.mIsBeingDragged = z
                    if (z && (parent = getParent()) != null) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                    if (!this.mScroller.isFinished()) {
                        this.mScroller.abortAnimation()
                    }
                    this.mLastMotionY = (Int) motionEvent.getY()
                    this.mActivePointerId = motionEvent.getPointerId(0)
                    startNestedScroll(2, 0)
                    break
                } else {
                    return false
                }
                break
            case 1:
                VelocityTracker velocityTracker = this.mVelocityTracker
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity)
                Int yVelocity = (Int) velocityTracker.getYVelocity(this.mActivePointerId)
                if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                    flingWithNestedDispatch(-yVelocity)
                } else if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                this.mActivePointerId = -1
                endDrag()
                break
            case 2:
                Int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId)
                if (iFindPointerIndex != -1) {
                    Int y = (Int) motionEvent.getY(iFindPointerIndex)
                    Int i = this.mLastMotionY - y
                    if (dispatchNestedPreScroll(0, i, this.mScrollConsumed, this.mScrollOffset, 0)) {
                        i -= this.mScrollConsumed[1]
                        motionEventObtain.offsetLocation(0.0f, this.mScrollOffset[1])
                        this.mNestedYOffset += this.mScrollOffset[1]
                    }
                    if (!this.mIsBeingDragged && Math.abs(i) > this.mTouchSlop) {
                        ViewParent parent2 = getParent()
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true)
                        }
                        this.mIsBeingDragged = true
                        i = i > 0 ? i - this.mTouchSlop : i + this.mTouchSlop
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y - this.mScrollOffset[1]
                        Int scrollY = getScrollY()
                        Int scrollRange = getScrollRange()
                        Int overScrollMode = getOverScrollMode()
                        Boolean z2 = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)
                        if (overScrollByCompat(0, i, 0, getScrollY(), 0, scrollRange, 0, 0, true) && !hasNestedScrollingParent(0)) {
                            this.mVelocityTracker.clear()
                        }
                        Int scrollY2 = getScrollY() - scrollY
                        if (!dispatchNestedScroll(0, scrollY2, 0, i - scrollY2, this.mScrollOffset, 0)) {
                            if (z2) {
                                ensureGlows()
                                Int i2 = scrollY + i
                                if (i2 < 0) {
                                    EdgeEffectCompat.onPull(this.mEdgeGlowTop, i / getHeight(), motionEvent.getX(iFindPointerIndex) / getWidth())
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        this.mEdgeGlowBottom.onRelease()
                                    }
                                } else if (i2 > scrollRange) {
                                    EdgeEffectCompat.onPull(this.mEdgeGlowBottom, i / getHeight(), 1.0f - (motionEvent.getX(iFindPointerIndex) / getWidth()))
                                    if (!this.mEdgeGlowTop.isFinished()) {
                                        this.mEdgeGlowTop.onRelease()
                                    }
                                }
                                if (this.mEdgeGlowTop != null && (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished())) {
                                    ViewCompat.postInvalidateOnAnimation(this)
                                    break
                                }
                            }
                        } else {
                            this.mLastMotionY -= this.mScrollOffset[1]
                            motionEventObtain.offsetLocation(0.0f, this.mScrollOffset[1])
                            this.mNestedYOffset += this.mScrollOffset[1]
                            break
                        }
                    }
                } else {
                    Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent")
                    break
                }
                break
            case 3:
                if (this.mIsBeingDragged && getChildCount() > 0 && this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                this.mActivePointerId = -1
                endDrag()
                break
            case 5:
                Int actionIndex = motionEvent.getActionIndex()
                this.mLastMotionY = (Int) motionEvent.getY(actionIndex)
                this.mActivePointerId = motionEvent.getPointerId(actionIndex)
                break
            case 6:
                onSecondaryPointerUp(motionEvent)
                this.mLastMotionY = (Int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId))
                break
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEventObtain)
        }
        motionEventObtain.recycle()
        return true
    }

    Boolean overScrollByCompat(Int i, Int i2, Int i3, Int i4, Int i5, Int i6, Int i7, Int i8, Boolean z) {
        Boolean z2
        Boolean z3
        Int overScrollMode = getOverScrollMode()
        Boolean z4 = computeHorizontalScrollRange() > computeHorizontalScrollExtent()
        Boolean z5 = computeVerticalScrollRange() > computeVerticalScrollExtent()
        Boolean z6 = overScrollMode == 0 || (overScrollMode == 1 && z4)
        Boolean z7 = overScrollMode == 0 || (overScrollMode == 1 && z5)
        Int i9 = i3 + i
        if (!z6) {
            i7 = 0
        }
        Int i10 = i4 + i2
        if (!z7) {
            i8 = 0
        }
        Int i11 = -i7
        Int i12 = i7 + i5
        Int i13 = -i8
        Int i14 = i8 + i6
        if (i9 > i12) {
            z2 = true
        } else if (i9 < i11) {
            z2 = true
            i12 = i11
        } else {
            z2 = false
            i12 = i9
        }
        if (i10 > i14) {
            z3 = true
        } else if (i10 < i13) {
            z3 = true
            i14 = i13
        } else {
            z3 = false
            i14 = i10
        }
        if (z3 && !hasNestedScrollingParent(1)) {
            this.mScroller.springBack(i12, i14, 0, 0, 0, getScrollRange())
        }
        onOverScrolled(i12, i14, z2, z3)
        return z2 || z3
    }

    fun pageScroll(Int i) {
        Boolean z = i == 130
        Int height = getHeight()
        if (z) {
            this.mTempRect.top = getScrollY() + height
            Int childCount = getChildCount()
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1)
                Int bottom = ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin + childAt.getBottom() + getPaddingBottom()
                if (this.mTempRect.top + height > bottom) {
                    this.mTempRect.top = bottom - height
                }
            }
        } else {
            this.mTempRect.top = getScrollY() - height
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + height
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestChildFocus(View view, View view2) {
        if (this.mIsLayoutDirty) {
            this.mChildToScrollTo = view2
        } else {
            scrollToChild(view2)
        }
        super.requestChildFocus(view, view2)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestChildRectangleOnScreen(View view, Rect rect, Boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY())
        return scrollToChildRect(rect, z)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestDisallowInterceptTouchEvent(Boolean z) {
        if (z) {
            recycleVelocityTracker()
        }
        super.requestDisallowInterceptTouchEvent(z)
    }

    @Override // android.view.View, android.view.ViewParent
    fun requestLayout() {
        this.mIsLayoutDirty = true
        super.requestLayout()
    }

    @Override // android.view.View
    fun scrollTo(Int i, Int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0)
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
            Int width = (getWidth() - getPaddingLeft()) - getPaddingRight()
            Int width2 = childAt.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin
            Int height = (getHeight() - getPaddingTop()) - getPaddingBottom()
            Int height2 = layoutParams.bottomMargin + childAt.getHeight() + layoutParams.topMargin
            Int iClamp = clamp(i, width, width2)
            Int iClamp2 = clamp(i2, height, height2)
            if (iClamp == getScrollX() && iClamp2 == getScrollY()) {
                return
            }
            super.scrollTo(iClamp, iClamp2)
        }
    }

    fun setFillViewport(Boolean z) {
        if (z != this.mFillViewport) {
            this.mFillViewport = z
            requestLayout()
        }
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun setNestedScrollingEnabled(Boolean z) {
        this.mChildHelper.setNestedScrollingEnabled(z)
    }

    fun setOnScrollChangeListener(@Nullable OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener
    }

    fun setSmoothScrollingEnabled(Boolean z) {
        this.mSmoothScrollingEnabled = z
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    fun shouldDelayChildPressedState() {
        return true
    }

    public final Unit smoothScrollBy(Int i, Int i2) {
        if (getChildCount() == 0) {
            return
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            View childAt = getChildAt(0)
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams()
            Int height = layoutParams.bottomMargin + childAt.getHeight() + layoutParams.topMargin
            Int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom()
            Int scrollY = getScrollY()
            Int iMax = Math.max(0, Math.min(scrollY + i2, Math.max(0, height - height2))) - scrollY
            this.mLastScrollerY = getScrollY()
            this.mScroller.startScroll(getScrollX(), scrollY, 0, iMax)
            ViewCompat.postInvalidateOnAnimation(this)
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation()
            }
            scrollBy(i, i2)
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis()
    }

    public final Unit smoothScrollTo(Int i, Int i2) {
        smoothScrollBy(i - getScrollX(), i2 - getScrollY())
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun startNestedScroll(Int i) {
        return startNestedScroll(i, 0)
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    fun startNestedScroll(Int i, Int i2) {
        return this.mChildHelper.startNestedScroll(i, i2)
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    fun stopNestedScroll() {
        stopNestedScroll(0)
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    fun stopNestedScroll(Int i) {
        this.mChildHelper.stopNestedScroll(i)
    }
}
