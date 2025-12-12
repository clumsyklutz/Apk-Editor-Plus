package android.support.v4.view

import android.R
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.support.annotation.CallSuper
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewParent
import android.view.accessibility.AccessibilityEvent
import android.view.animation.Interpolator
import android.widget.EdgeEffect
import android.widget.Scroller
import java.lang.annotation.ElementType
import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List

class ViewPager extends ViewGroup {
    private static val CLOSE_ENOUGH = 2
    private static val DEBUG = false
    private static val DEFAULT_GUTTER_SIZE = 16
    private static val DEFAULT_OFFSCREEN_PAGES = 1
    private static val DRAW_ORDER_DEFAULT = 0
    private static val DRAW_ORDER_FORWARD = 1
    private static val DRAW_ORDER_REVERSE = 2
    private static val INVALID_POINTER = -1
    private static val MAX_SETTLE_DURATION = 600
    private static val MIN_DISTANCE_FOR_FLING = 25
    private static val MIN_FLING_VELOCITY = 400
    public static val SCROLL_STATE_DRAGGING = 1
    public static val SCROLL_STATE_IDLE = 0
    public static val SCROLL_STATE_SETTLING = 2
    private static val TAG = "ViewPager"
    private static val USE_CACHE = false
    private Int mActivePointerId
    PagerAdapter mAdapter
    private List mAdapterChangeListeners
    private Int mBottomPageBounds
    private Boolean mCalledSuper
    private Int mChildHeightMeasureSpec
    private Int mChildWidthMeasureSpec
    private Int mCloseEnough
    Int mCurItem
    private Int mDecorChildCount
    private Int mDefaultGutterSize
    private Int mDrawingOrder
    private ArrayList mDrawingOrderedChildren
    private final Runnable mEndScrollRunnable
    private Int mExpectedAdapterCount
    private Long mFakeDragBeginTime
    private Boolean mFakeDragging
    private Boolean mFirstLayout
    private Float mFirstOffset
    private Int mFlingDistance
    private Int mGutterSize
    private Boolean mInLayout
    private Float mInitialMotionX
    private Float mInitialMotionY
    private OnPageChangeListener mInternalPageChangeListener
    private Boolean mIsBeingDragged
    private Boolean mIsScrollStarted
    private Boolean mIsUnableToDrag
    private final ArrayList mItems
    private Float mLastMotionX
    private Float mLastMotionY
    private Float mLastOffset
    private EdgeEffect mLeftEdge
    private Drawable mMarginDrawable
    private Int mMaximumVelocity
    private Int mMinimumVelocity
    private Boolean mNeedCalculatePageOffsets
    private PagerObserver mObserver
    private Int mOffscreenPageLimit
    private OnPageChangeListener mOnPageChangeListener
    private List mOnPageChangeListeners
    private Int mPageMargin
    private PageTransformer mPageTransformer
    private Int mPageTransformerLayerType
    private Boolean mPopulatePending
    private Parcelable mRestoredAdapterState
    private ClassLoader mRestoredClassLoader
    private Int mRestoredCurItem
    private EdgeEffect mRightEdge
    private Int mScrollState
    private Scroller mScroller
    private Boolean mScrollingCacheEnabled
    private final ItemInfo mTempItem
    private final Rect mTempRect
    private Int mTopPageBounds
    private Int mTouchSlop
    private VelocityTracker mVelocityTracker
    static final Array<Int> LAYOUT_ATTRS = {R.attr.layout_gravity}
    private static val COMPARATOR = Comparator() { // from class: android.support.v4.view.ViewPager.1
        @Override // java.util.Comparator
        public final Int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position
        }
    }
    private static val sInterpolator = Interpolator() { // from class: android.support.v4.view.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public final Float getInterpolation(Float f) {
            Float f2 = f - 1.0f
            return (f2 * f2 * f2 * f2 * f2) + 1.0f
        }
    }
    private static val sPositionComparator = ViewPositionComparator()

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    class ItemInfo {
        Object object
        Float offset
        Int position
        Boolean scrolling
        Float widthFactor

        ItemInfo() {
        }
    }

    class LayoutParams extends ViewGroup.LayoutParams {
        Int childIndex
        public Int gravity
        public Boolean isDecor
        Boolean needsMeasure
        Int position
        Float widthFactor

        constructor() {
            super(-1, -1)
            this.widthFactor = 0.0f
        }

        constructor(Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
            this.widthFactor = 0.0f
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS)
            this.gravity = typedArrayObtainStyledAttributes.getInteger(0, 48)
            typedArrayObtainStyledAttributes.recycle()
        }
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        private fun canScroll() {
            return ViewPager.this.mAdapter != null && ViewPager.this.mAdapter.getCount() > 1
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent)
            accessibilityEvent.setClassName(ViewPager.class.getName())
            accessibilityEvent.setScrollable(canScroll())
            if (accessibilityEvent.getEventType() != 4096 || ViewPager.this.mAdapter == null) {
                return
            }
            accessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount())
            accessibilityEvent.setFromIndex(ViewPager.this.mCurItem)
            accessibilityEvent.setToIndex(ViewPager.this.mCurItem)
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat)
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName())
            accessibilityNodeInfoCompat.setScrollable(canScroll())
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096)
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192)
            }
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun performAccessibilityAction(View view, Int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true
            }
            switch (i) {
                case 4096:
                    if (!ViewPager.this.canScrollHorizontally(1)) {
                        return false
                    }
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem + 1)
                    return true
                case 8192:
                    if (!ViewPager.this.canScrollHorizontally(-1)) {
                        return false
                    }
                    ViewPager.this.setCurrentItem(ViewPager.this.mCurItem - 1)
                    return true
                default:
                    return false
            }
        }
    }

    public interface OnAdapterChangeListener {
        Unit onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2)
    }

    public interface OnPageChangeListener {
        Unit onPageScrollStateChanged(Int i)

        Unit onPageScrolled(Int i, Float f, @Px Int i2)

        Unit onPageSelected(Int i)
    }

    public interface PageTransformer {
        Unit transformPage(@NonNull View view, Float f)
    }

    class PagerObserver extends DataSetObserver {
        PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        fun onChanged() throws Resources.NotFoundException {
            ViewPager.this.dataSetChanged()
        }

        @Override // android.database.DataSetObserver
        fun onInvalidated() throws Resources.NotFoundException {
            ViewPager.this.dataSetChanged()
        }
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v4.view.ViewPager.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel, null)
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return SavedState(parcel, classLoader)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        Parcelable adapterState
        ClassLoader loader
        Int position

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader)
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader
            this.position = parcel.readInt()
            this.adapterState = parcel.readParcelable(classLoader)
            this.loader = classLoader
        }

        constructor(@NonNull Parcelable parcelable) {
            super(parcelable)
        }

        fun toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}"
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeInt(this.position)
            parcel.writeParcelable(this.adapterState, i)
        }
    }

    class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        fun onPageScrollStateChanged(Int i) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        fun onPageScrolled(Int i, Float f, Int i2) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        fun onPageSelected(Int i) {
        }
    }

    class ViewPositionComparator implements Comparator {
        ViewPositionComparator() {
        }

        @Override // java.util.Comparator
        fun compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams()
            return layoutParams.isDecor != layoutParams2.isDecor ? layoutParams.isDecor ? 1 : -1 : layoutParams.position - layoutParams2.position
        }
    }

    constructor(@NonNull Context context) {
        super(context)
        this.mItems = ArrayList()
        this.mTempItem = ItemInfo()
        this.mTempRect = Rect()
        this.mRestoredCurItem = -1
        this.mRestoredAdapterState = null
        this.mRestoredClassLoader = null
        this.mFirstOffset = -3.4028235E38f
        this.mLastOffset = Float.MAX_VALUE
        this.mOffscreenPageLimit = 1
        this.mActivePointerId = -1
        this.mFirstLayout = true
        this.mNeedCalculatePageOffsets = false
        this.mEndScrollRunnable = Runnable() { // from class: android.support.v4.view.ViewPager.3
            @Override // java.lang.Runnable
            fun run() throws Resources.NotFoundException {
                ViewPager.this.setScrollState(0)
                ViewPager.this.populate()
            }
        }
        this.mScrollState = 0
        initViewPager()
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet)
        this.mItems = ArrayList()
        this.mTempItem = ItemInfo()
        this.mTempRect = Rect()
        this.mRestoredCurItem = -1
        this.mRestoredAdapterState = null
        this.mRestoredClassLoader = null
        this.mFirstOffset = -3.4028235E38f
        this.mLastOffset = Float.MAX_VALUE
        this.mOffscreenPageLimit = 1
        this.mActivePointerId = -1
        this.mFirstLayout = true
        this.mNeedCalculatePageOffsets = false
        this.mEndScrollRunnable = Runnable() { // from class: android.support.v4.view.ViewPager.3
            @Override // java.lang.Runnable
            fun run() throws Resources.NotFoundException {
                ViewPager.this.setScrollState(0)
                ViewPager.this.populate()
            }
        }
        this.mScrollState = 0
        initViewPager()
    }

    private fun calculatePageOffsets(ItemInfo itemInfo, Int i, ItemInfo itemInfo2) {
        ItemInfo itemInfo3
        ItemInfo itemInfo4
        Int count = this.mAdapter.getCount()
        Int clientWidth = getClientWidth()
        Float f = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f
        if (itemInfo2 != null) {
            Int i2 = itemInfo2.position
            if (i2 < itemInfo.position) {
                Int i3 = i2 + 1
                Float f2 = itemInfo2.offset + itemInfo2.widthFactor + f
                Int i4 = 0
                while (true) {
                    Int i5 = i3
                    if (i5 > itemInfo.position || i4 >= this.mItems.size()) {
                        break
                    }
                    Object obj = this.mItems.get(i4)
                    while (true) {
                        itemInfo4 = (ItemInfo) obj
                        if (i5 <= itemInfo4.position || i4 >= this.mItems.size() - 1) {
                            break
                        }
                        i4++
                        obj = this.mItems.get(i4)
                    }
                    Float f3 = f2
                    Int i6 = i5
                    while (i6 < itemInfo4.position) {
                        Float pageWidth = this.mAdapter.getPageWidth(i6) + f + f3
                        i6++
                        f3 = pageWidth
                    }
                    itemInfo4.offset = f3
                    Float f4 = f3 + itemInfo4.widthFactor + f
                    i3 = i6 + 1
                    f2 = f4
                }
            } else if (i2 > itemInfo.position) {
                Int size = this.mItems.size() - 1
                Float f5 = itemInfo2.offset
                Int i7 = i2 - 1
                Int i8 = size
                while (true) {
                    Float f6 = f5
                    Int i9 = i7
                    if (i9 < itemInfo.position || i8 < 0) {
                        break
                    }
                    Object obj2 = this.mItems.get(i8)
                    while (true) {
                        itemInfo3 = (ItemInfo) obj2
                        if (i9 >= itemInfo3.position || i8 <= 0) {
                            break
                        }
                        i8--
                        obj2 = this.mItems.get(i8)
                    }
                    Float f7 = f6
                    Int i10 = i9
                    while (i10 > itemInfo3.position) {
                        Float pageWidth2 = f7 - (this.mAdapter.getPageWidth(i10) + f)
                        i10--
                        f7 = pageWidth2
                    }
                    f5 = f7 - (itemInfo3.widthFactor + f)
                    itemInfo3.offset = f5
                    i7 = i10 - 1
                }
            }
        }
        Int size2 = this.mItems.size()
        Float pageWidth3 = itemInfo.offset
        Int i11 = itemInfo.position - 1
        this.mFirstOffset = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f
        this.mLastOffset = itemInfo.position == count + (-1) ? (itemInfo.offset + itemInfo.widthFactor) - 1.0f : Float.MAX_VALUE
        for (Int i12 = i - 1; i12 >= 0; i12--) {
            ItemInfo itemInfo5 = (ItemInfo) this.mItems.get(i12)
            while (i11 > itemInfo5.position) {
                pageWidth3 -= this.mAdapter.getPageWidth(i11) + f
                i11--
            }
            pageWidth3 -= itemInfo5.widthFactor + f
            itemInfo5.offset = pageWidth3
            if (itemInfo5.position == 0) {
                this.mFirstOffset = pageWidth3
            }
            i11--
        }
        Float pageWidth4 = itemInfo.offset + itemInfo.widthFactor + f
        Int i13 = itemInfo.position + 1
        for (Int i14 = i + 1; i14 < size2; i14++) {
            ItemInfo itemInfo6 = (ItemInfo) this.mItems.get(i14)
            while (i13 < itemInfo6.position) {
                pageWidth4 += this.mAdapter.getPageWidth(i13) + f
                i13++
            }
            if (itemInfo6.position == count - 1) {
                this.mLastOffset = (itemInfo6.widthFactor + pageWidth4) - 1.0f
            }
            itemInfo6.offset = pageWidth4
            pageWidth4 += itemInfo6.widthFactor + f
            i13++
        }
        this.mNeedCalculatePageOffsets = false
    }

    private fun completeScroll(Boolean z) {
        Boolean z2 = this.mScrollState == 2
        if (z2) {
            setScrollingCacheEnabled(false)
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation()
                Int scrollX = getScrollX()
                Int scrollY = getScrollY()
                Int currX = this.mScroller.getCurrX()
                Int currY = this.mScroller.getCurrY()
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY)
                    if (currX != scrollX) {
                        pageScrolled(currX)
                    }
                }
            }
        }
        this.mPopulatePending = false
        Boolean z3 = z2
        for (Int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i)
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false
                z3 = true
            }
        }
        if (z3) {
            if (z) {
                ViewCompat.postOnAnimation(this, this.mEndScrollRunnable)
            } else {
                this.mEndScrollRunnable.run()
            }
        }
    }

    private fun determineTargetPage(Int i, Float f, Int i2, Int i3) {
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity) {
            i += (Int) ((i >= this.mCurItem ? 0.4f : 0.6f) + f)
        } else if (i2 <= 0) {
            i++
        }
        if (this.mItems.size() > 0) {
            return Math.max(((ItemInfo) this.mItems.get(0)).position, Math.min(i, ((ItemInfo) this.mItems.get(this.mItems.size() - 1)).position))
        }
        return i
    }

    private fun dispatchOnPageScrolled(Int i, Float f, Int i2) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(i, f, i2)
        }
        if (this.mOnPageChangeListeners != null) {
            Int size = this.mOnPageChangeListeners.size()
            for (Int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i3)
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(i, f, i2)
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(i, f, i2)
        }
    }

    private fun dispatchOnPageSelected(Int i) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(i)
        }
        if (this.mOnPageChangeListeners != null) {
            Int size = this.mOnPageChangeListeners.size()
            for (Int i2 = 0; i2 < size; i2++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i2)
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(i)
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(i)
        }
    }

    private fun dispatchOnScrollStateChanged(Int i) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(i)
        }
        if (this.mOnPageChangeListeners != null) {
            Int size = this.mOnPageChangeListeners.size()
            for (Int i2 = 0; i2 < size; i2++) {
                OnPageChangeListener onPageChangeListener = (OnPageChangeListener) this.mOnPageChangeListeners.get(i2)
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(i)
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrollStateChanged(i)
        }
    }

    private fun enableLayers(Boolean z) {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            getChildAt(i).setLayerType(z ? this.mPageTransformerLayerType : 0, null)
        }
    }

    private fun endDrag() {
        this.mIsBeingDragged = false
        this.mIsUnableToDrag = false
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle()
            this.mVelocityTracker = null
        }
    }

    private fun getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect == null ? Rect() : rect
        if (view == null) {
            rect2.set(0, 0, 0, 0)
            return rect2
        }
        rect2.left = view.getLeft()
        rect2.right = view.getRight()
        rect2.top = view.getTop()
        rect2.bottom = view.getBottom()
        ViewParent parent = view.getParent()
        while ((parent is ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent
            rect2.left += viewGroup.getLeft()
            rect2.right += viewGroup.getRight()
            rect2.top += viewGroup.getTop()
            rect2.bottom += viewGroup.getBottom()
            parent = viewGroup.getParent()
        }
        return rect2
    }

    private fun getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()
    }

    private fun infoForCurrentScrollPosition() {
        Int i
        ItemInfo itemInfo
        Int clientWidth = getClientWidth()
        Float scrollX = clientWidth > 0 ? getScrollX() / clientWidth : 0.0f
        Float f = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f
        Float f2 = 0.0f
        Float f3 = 0.0f
        Int i2 = -1
        Int i3 = 0
        Boolean z = true
        ItemInfo itemInfo2 = null
        while (i3 < this.mItems.size()) {
            ItemInfo itemInfo3 = (ItemInfo) this.mItems.get(i3)
            if (z || itemInfo3.position == i2 + 1) {
                i = i3
                itemInfo = itemInfo3
            } else {
                ItemInfo itemInfo4 = this.mTempItem
                itemInfo4.offset = f2 + f3 + f
                itemInfo4.position = i2 + 1
                itemInfo4.widthFactor = this.mAdapter.getPageWidth(itemInfo4.position)
                i = i3 - 1
                itemInfo = itemInfo4
            }
            Float f4 = itemInfo.offset
            Float f5 = itemInfo.widthFactor + f4 + f
            if (!z && scrollX < f4) {
                return itemInfo2
            }
            if (scrollX < f5 || i == this.mItems.size() - 1) {
                return itemInfo
            }
            f3 = f4
            i2 = itemInfo.position
            z = false
            f2 = itemInfo.widthFactor
            itemInfo2 = itemInfo
            i3 = i + 1
        }
        return itemInfo2
    }

    private fun isDecorView(@NonNull View view) {
        return view.getClass().getAnnotation(DecorView.class) != null
    }

    private fun isGutterDrag(Float f, Float f2) {
        return (f < ((Float) this.mGutterSize) && f2 > 0.0f) || (f > ((Float) (getWidth() - this.mGutterSize)) && f2 < 0.0f)
    }

    private fun onSecondaryPointerUp(MotionEvent motionEvent) {
        Int actionIndex = motionEvent.getActionIndex()
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            Int i = actionIndex == 0 ? 1 : 0
            this.mLastMotionX = motionEvent.getX(i)
            this.mActivePointerId = motionEvent.getPointerId(i)
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear()
            }
        }
    }

    private fun pageScrolled(Int i) {
        if (this.mItems.size() == 0) {
            if (this.mFirstLayout) {
                return false
            }
            this.mCalledSuper = false
            onPageScrolled(0, 0.0f, 0)
            if (this.mCalledSuper) {
                return false
            }
            throw IllegalStateException("onPageScrolled did not call superclass implementation")
        }
        ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition()
        Int clientWidth = getClientWidth()
        Int i2 = this.mPageMargin + clientWidth
        Int i3 = itemInfoInfoForCurrentScrollPosition.position
        Float f = ((i / clientWidth) - itemInfoInfoForCurrentScrollPosition.offset) / (itemInfoInfoForCurrentScrollPosition.widthFactor + (this.mPageMargin / clientWidth))
        this.mCalledSuper = false
        onPageScrolled(i3, f, (Int) (i2 * f))
        if (this.mCalledSuper) {
            return true
        }
        throw IllegalStateException("onPageScrolled did not call superclass implementation")
    }

    private fun performDrag(Float f) {
        Boolean z
        Float f2
        Boolean z2
        Boolean z3 = true
        Float f3 = this.mLastMotionX - f
        this.mLastMotionX = f
        Float scrollX = getScrollX() + f3
        Int clientWidth = getClientWidth()
        Float f4 = clientWidth * this.mFirstOffset
        Float f5 = clientWidth * this.mLastOffset
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0)
        ItemInfo itemInfo2 = (ItemInfo) this.mItems.get(this.mItems.size() - 1)
        if (itemInfo.position != 0) {
            f4 = itemInfo.offset * clientWidth
            z = false
        } else {
            z = true
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f2 = itemInfo2.offset * clientWidth
            z2 = false
        } else {
            f2 = f5
            z2 = true
        }
        if (scrollX < f4) {
            if (z) {
                this.mLeftEdge.onPull(Math.abs(f4 - scrollX) / clientWidth)
            } else {
                z3 = false
            }
        } else if (scrollX > f2) {
            if (z2) {
                this.mRightEdge.onPull(Math.abs(scrollX - f2) / clientWidth)
            } else {
                z3 = false
            }
            f4 = f2
        } else {
            z3 = false
            f4 = scrollX
        }
        this.mLastMotionX += f4 - ((Int) f4)
        scrollTo((Int) f4, getScrollY())
        pageScrolled((Int) f4)
        return z3
    }

    private fun recomputeScrollPosition(Int i, Int i2, Int i3, Int i4) {
        if (i2 <= 0 || this.mItems.isEmpty()) {
            ItemInfo itemInfoInfoForPosition = infoForPosition(this.mCurItem)
            Int iMin = (Int) ((itemInfoInfoForPosition != null ? Math.min(itemInfoInfoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()))
            if (iMin != getScrollX()) {
                completeScroll(false)
                scrollTo(iMin, getScrollY())
                return
            }
            return
        }
        if (!this.mScroller.isFinished()) {
            this.mScroller.setFinalX(getCurrentItem() * getClientWidth())
            return
        }
        scrollTo((Int) ((((i - getPaddingLeft()) - getPaddingRight()) + i3) * (getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4))), getScrollY())
    }

    private fun removeNonDecorViews() {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= getChildCount()) {
                return
            }
            if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                removeViewAt(i2)
                i2--
            }
            i = i2 + 1
        }
    }

    private fun requestParentDisallowInterceptTouchEvent(Boolean z) {
        ViewParent parent = getParent()
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z)
        }
    }

    private fun resetTouch() {
        this.mActivePointerId = -1
        endDrag()
        this.mLeftEdge.onRelease()
        this.mRightEdge.onRelease()
        return this.mLeftEdge.isFinished() || this.mRightEdge.isFinished()
    }

    private fun scrollToItem(Int i, Boolean z, Int i2, Boolean z2) throws Resources.NotFoundException {
        Int iMax
        ItemInfo itemInfoInfoForPosition = infoForPosition(i)
        if (itemInfoInfoForPosition != null) {
            iMax = (Int) (Math.max(this.mFirstOffset, Math.min(itemInfoInfoForPosition.offset, this.mLastOffset)) * getClientWidth())
        } else {
            iMax = 0
        }
        if (z) {
            smoothScrollTo(iMax, 0, i2)
            if (z2) {
                dispatchOnPageSelected(i)
                return
            }
            return
        }
        if (z2) {
            dispatchOnPageSelected(i)
        }
        completeScroll(false)
        scrollTo(iMax, 0)
        pageScrolled(iMax)
    }

    private fun setScrollingCacheEnabled(Boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z
        }
    }

    private fun sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = ArrayList()
            } else {
                this.mDrawingOrderedChildren.clear()
            }
            Int childCount = getChildCount()
            for (Int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i))
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator)
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    fun addFocusables(ArrayList arrayList, Int i, Int i2) {
        ItemInfo itemInfoInfoForChild
        Int size = arrayList.size()
        Int descendantFocusability = getDescendantFocusability()
        if (descendantFocusability != 393216) {
            for (Int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3)
                if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
                    childAt.addFocusables(arrayList, i, i2)
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if (((i2 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) || arrayList == null) {
                return
            }
            arrayList.add(this)
        }
    }

    ItemInfo addNewItem(Int i, Int i2) {
        ItemInfo itemInfo = ItemInfo()
        itemInfo.position = i
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, i)
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i)
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo)
        } else {
            this.mItems.add(i2, itemInfo)
        }
        return itemInfo
    }

    fun addOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = ArrayList()
        }
        this.mAdapterChangeListeners.add(onAdapterChangeListener)
    }

    fun addOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = ArrayList()
        }
        this.mOnPageChangeListeners.add(onPageChangeListener)
    }

    @Override // android.view.ViewGroup, android.view.View
    fun addTouchables(ArrayList arrayList) {
        ItemInfo itemInfoInfoForChild
        for (Int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i)
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList)
            }
        }
    }

    @Override // android.view.ViewGroup
    fun addView(View view, Int i, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParamsGenerateLayoutParams = !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : layoutParams
        LayoutParams layoutParams2 = (LayoutParams) layoutParamsGenerateLayoutParams
        layoutParams2.isDecor |= isDecorView(view)
        if (!this.mInLayout) {
            super.addView(view, i, layoutParamsGenerateLayoutParams)
        } else {
            if (layoutParams2 != null && layoutParams2.isDecor) {
                throw IllegalStateException("Cannot add pager decor view during layout")
            }
            layoutParams2.needsMeasure = true
            addViewInLayout(view, i, layoutParamsGenerateLayoutParams)
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun arrowScroll(Int r10) {
        /*
            r9 = this
            r1 = 0
            r8 = 66
            r7 = 17
            r4 = 1
            r3 = 0
            android.view.View r2 = r9.findFocus()
            if (r2 != r9) goto L3e
            r0 = r1
        Le:
            android.view.FocusFinder r1 = android.view.FocusFinder.getInstance()
            android.view.View r1 = r1.findNextFocus(r9, r0, r10)
            if (r1 == 0) goto Lc2
            if (r1 == r0) goto Lc2
            if (r10 != r7) goto La0
            android.graphics.Rect r2 = r9.mTempRect
            android.graphics.Rect r2 = r9.getChildRectInPagerCoordinates(r2, r1)
            Int r2 = r2.left
            android.graphics.Rect r3 = r9.mTempRect
            android.graphics.Rect r3 = r9.getChildRectInPagerCoordinates(r3, r0)
            Int r3 = r3.left
            if (r0 == 0) goto L9b
            if (r2 < r3) goto L9b
            Boolean r0 = r9.pageLeft()
        L34:
            if (r0 == 0) goto L3d
            Int r1 = android.view.SoundEffectConstants.getContantForFocusDirection(r10)
            r9.playSoundEffect(r1)
        L3d:
            return r0
        L3e:
            if (r2 == 0) goto Lda
            android.view.ViewParent r0 = r2.getParent()
        L44:
            Boolean r5 = r0 is android.view.ViewGroup
            if (r5 == 0) goto Ldd
            if (r0 != r9) goto L7b
            r0 = r4
        L4b:
            if (r0 != 0) goto Lda
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Class r0 = r2.getClass()
            java.lang.String r0 = r0.getSimpleName()
            r5.append(r0)
            android.view.ViewParent r0 = r2.getParent()
        L61:
            Boolean r2 = r0 is android.view.ViewGroup
            if (r2 == 0) goto L80
            java.lang.String r2 = " => "
            java.lang.StringBuilder r2 = r5.append(r2)
            java.lang.Class r6 = r0.getClass()
            java.lang.String r6 = r6.getSimpleName()
            r2.append(r6)
            android.view.ViewParent r0 = r0.getParent()
            goto L61
        L7b:
            android.view.ViewParent r0 = r0.getParent()
            goto L44
        L80:
            java.lang.String r0 = "ViewPager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r6 = "arrowScroll tried to find focus based on non-child current focused view "
            r2.<init>(r6)
            java.lang.String r5 = r5.toString()
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            r0 = r1
            goto Le
        L9b:
            Boolean r0 = r1.requestFocus()
            goto L34
        La0:
            if (r10 != r8) goto Ld7
            android.graphics.Rect r2 = r9.mTempRect
            android.graphics.Rect r2 = r9.getChildRectInPagerCoordinates(r2, r1)
            Int r2 = r2.left
            android.graphics.Rect r3 = r9.mTempRect
            android.graphics.Rect r3 = r9.getChildRectInPagerCoordinates(r3, r0)
            Int r3 = r3.left
            if (r0 == 0) goto Lbc
            if (r2 > r3) goto Lbc
            Boolean r0 = r9.pageRight()
            goto L34
        Lbc:
            Boolean r0 = r1.requestFocus()
            goto L34
        Lc2:
            if (r10 == r7) goto Lc6
            if (r10 != r4) goto Lcc
        Lc6:
            Boolean r0 = r9.pageLeft()
            goto L34
        Lcc:
            if (r10 == r8) goto Ld1
            r0 = 2
            if (r10 != r0) goto Ld7
        Ld1:
            Boolean r0 = r9.pageRight()
            goto L34
        Ld7:
            r0 = r3
            goto L34
        Lda:
            r0 = r2
            goto Le
        Ldd:
            r0 = r3
            goto L4b
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.arrowScroll(Int):Boolean")
    }

    fun beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false
        }
        this.mFakeDragging = true
        setScrollState(1)
        this.mLastMotionX = 0.0f
        this.mInitialMotionX = 0.0f
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain()
        } else {
            this.mVelocityTracker.clear()
        }
        Long jUptimeMillis = SystemClock.uptimeMillis()
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, 0.0f, 0.0f, 0)
        this.mVelocityTracker.addMovement(motionEventObtain)
        motionEventObtain.recycle()
        this.mFakeDragBeginTime = jUptimeMillis
        return true
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
        return z && view.canScrollHorizontally(-i)
    }

    @Override // android.view.View
    fun canScrollHorizontally(Int i) {
        if (this.mAdapter == null) {
            return false
        }
        Int clientWidth = getClientWidth()
        Int scrollX = getScrollX()
        return i < 0 ? scrollX > ((Int) (((Float) clientWidth) * this.mFirstOffset)) : i > 0 && scrollX < ((Int) (((Float) clientWidth) * this.mLastOffset))
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams is LayoutParams) && super.checkLayoutParams(layoutParams)
    }

    fun clearOnPageChangeListeners() {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.clear()
        }
    }

    @Override // android.view.View
    fun computeScroll() {
        this.mIsScrollStarted = true
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true)
            return
        }
        Int scrollX = getScrollX()
        Int scrollY = getScrollY()
        Int currX = this.mScroller.getCurrX()
        Int currY = this.mScroller.getCurrY()
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY)
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation()
                scrollTo(0, currY)
            }
        }
        ViewCompat.postInvalidateOnAnimation(this)
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    Unit dataSetChanged() throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 203
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.dataSetChanged():Unit")
    }

    @Override // android.view.ViewGroup, android.view.View
    fun dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent)
    }

    @Override // android.view.View
    fun dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        ItemInfo itemInfoInfoForChild
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent)
        }
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true
            }
        }
        return false
    }

    Float distanceInfluenceForSnapDuration(Float f) {
        return (Float) Math.sin((f - 0.5f) * 0.47123894f)
    }

    @Override // android.view.View
    fun draw(Canvas canvas) {
        super.draw(canvas)
        Boolean zDraw = false
        Int overScrollMode = getOverScrollMode()
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                Int iSave = canvas.save()
                Int height = (getHeight() - getPaddingTop()) - getPaddingBottom()
                Int width = getWidth()
                canvas.rotate(270.0f)
                canvas.translate((-height) + getPaddingTop(), this.mFirstOffset * width)
                this.mLeftEdge.setSize(height, width)
                zDraw = this.mLeftEdge.draw(canvas) | false
                canvas.restoreToCount(iSave)
            }
            if (!this.mRightEdge.isFinished()) {
                Int iSave2 = canvas.save()
                Int width2 = getWidth()
                Int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom()
                canvas.rotate(90.0f)
                canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2)
                this.mRightEdge.setSize(height2, width2)
                zDraw |= this.mRightEdge.draw(canvas)
                canvas.restoreToCount(iSave2)
            }
        } else {
            this.mLeftEdge.finish()
            this.mRightEdge.finish()
        }
        if (zDraw) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        Drawable drawable = this.mMarginDrawable
        if (drawable == null || !drawable.isStateful()) {
            return
        }
        drawable.setState(getDrawableState())
    }

    fun endFakeDrag() throws Resources.NotFoundException {
        if (!this.mFakeDragging) {
            throw IllegalStateException("No fake drag in progress. Call beginFakeDrag first.")
        }
        if (this.mAdapter != null) {
            VelocityTracker velocityTracker = this.mVelocityTracker
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity)
            Int xVelocity = (Int) velocityTracker.getXVelocity(this.mActivePointerId)
            this.mPopulatePending = true
            Int clientWidth = getClientWidth()
            Int scrollX = getScrollX()
            ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition()
            setCurrentItemInternal(determineTargetPage(itemInfoInfoForCurrentScrollPosition.position, ((scrollX / clientWidth) - itemInfoInfoForCurrentScrollPosition.offset) / itemInfoInfoForCurrentScrollPosition.widthFactor, xVelocity, (Int) (this.mLastMotionX - this.mInitialMotionX)), true, true, xVelocity)
        }
        endDrag()
        this.mFakeDragging = false
    }

    fun executeKeyEvent(@NonNull KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return false
        }
        switch (keyEvent.getKeyCode()) {
            case 21:
                if (!keyEvent.hasModifiers(2)) {
                    break
                } else {
                    break
                }
            case 22:
                if (!keyEvent.hasModifiers(2)) {
                    break
                } else {
                    break
                }
            case android.support.v7.appcompat.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
                if (!keyEvent.hasNoModifiers()) {
                    if (keyEvent.hasModifiers(1)) {
                        break
                    }
                } else {
                    break
                }
                break
        }
        return false
    }

    fun fakeDragBy(Float f) {
        if (!this.mFakeDragging) {
            throw IllegalStateException("No fake drag in progress. Call beginFakeDrag first.")
        }
        if (this.mAdapter == null) {
            return
        }
        this.mLastMotionX += f
        Float scrollX = getScrollX() - f
        Int clientWidth = getClientWidth()
        Float f2 = clientWidth * this.mFirstOffset
        Float f3 = clientWidth * this.mLastOffset
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0)
        ItemInfo itemInfo2 = (ItemInfo) this.mItems.get(this.mItems.size() - 1)
        Float f4 = itemInfo.position != 0 ? itemInfo.offset * clientWidth : f2
        Float f5 = itemInfo2.position != this.mAdapter.getCount() + (-1) ? itemInfo2.offset * clientWidth : f3
        if (scrollX >= f4) {
            f4 = scrollX > f5 ? f5 : scrollX
        }
        this.mLastMotionX += f4 - ((Int) f4)
        scrollTo((Int) f4, getScrollY())
        pageScrolled((Int) f4)
        MotionEvent motionEventObtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0)
        this.mVelocityTracker.addMovement(motionEventObtain)
        motionEventObtain.recycle()
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
        return generateDefaultLayoutParams()
    }

    @Nullable
    fun getAdapter() {
        return this.mAdapter
    }

    @Override // android.view.ViewGroup
    protected fun getChildDrawingOrder(Int i, Int i2) {
        if (this.mDrawingOrder == 2) {
            i2 = (i - 1) - i2
        }
        return ((LayoutParams) ((View) this.mDrawingOrderedChildren.get(i2)).getLayoutParams()).childIndex
    }

    fun getCurrentItem() {
        return this.mCurItem
    }

    fun getOffscreenPageLimit() {
        return this.mOffscreenPageLimit
    }

    fun getPageMargin() {
        return this.mPageMargin
    }

    ItemInfo infoForAnyChild(View view) {
        while (true) {
            Object parent = view.getParent()
            if (parent == this) {
                return infoForChild(view)
            }
            if (parent == null || !(parent is View)) {
                break
            }
            view = (View) parent
        }
        return null
    }

    ItemInfo infoForChild(View view) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.mItems.size()) {
                return null
            }
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i2)
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo
            }
            i = i2 + 1
        }
    }

    ItemInfo infoForPosition(Int i) {
        Int i2 = 0
        while (true) {
            Int i3 = i2
            if (i3 >= this.mItems.size()) {
                return null
            }
            ItemInfo itemInfo = (ItemInfo) this.mItems.get(i3)
            if (itemInfo.position == i) {
                return itemInfo
            }
            i2 = i3 + 1
        }
    }

    Unit initViewPager() {
        setWillNotDraw(false)
        setDescendantFocusability(262144)
        setFocusable(true)
        Context context = getContext()
        this.mScroller = Scroller(context, sInterpolator)
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context)
        Float f = context.getResources().getDisplayMetrics().density
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop()
        this.mMinimumVelocity = (Int) (400.0f * f)
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity()
        this.mLeftEdge = EdgeEffect(context)
        this.mRightEdge = EdgeEffect(context)
        this.mFlingDistance = (Int) (25.0f * f)
        this.mCloseEnough = (Int) (2.0f * f)
        this.mDefaultGutterSize = (Int) (16.0f * f)
        ViewCompat.setAccessibilityDelegate(this, MyAccessibilityDelegate())
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1)
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, OnApplyWindowInsetsListener() { // from class: android.support.v4.view.ViewPager.4
            private val mTempRect = Rect()

            @Override // android.support.v4.view.OnApplyWindowInsetsListener
            fun onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat windowInsetsCompatOnApplyWindowInsets = ViewCompat.onApplyWindowInsets(view, windowInsetsCompat)
                if (windowInsetsCompatOnApplyWindowInsets.isConsumed()) {
                    return windowInsetsCompatOnApplyWindowInsets
                }
                Rect rect = this.mTempRect
                rect.left = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetLeft()
                rect.top = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetTop()
                rect.right = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetRight()
                rect.bottom = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetBottom()
                Int childCount = ViewPager.this.getChildCount()
                for (Int i = 0; i < childCount; i++) {
                    WindowInsetsCompat windowInsetsCompatDispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), windowInsetsCompatOnApplyWindowInsets)
                    rect.left = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left)
                    rect.top = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top)
                    rect.right = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right)
                    rect.bottom = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom)
                }
                return windowInsetsCompatOnApplyWindowInsets.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom)
            }
        })
    }

    fun isFakeDragging() {
        return this.mFakeDragging
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.mFirstLayout = true
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable)
        if (this.mScroller != null && !this.mScroller.isFinished()) {
            this.mScroller.abortAnimation()
        }
        super.onDetachedFromWindow()
    }

    @Override // android.view.View
    protected fun onDraw(Canvas canvas) {
        Float f
        super.onDraw(canvas)
        if (this.mPageMargin <= 0 || this.mMarginDrawable == null || this.mItems.size() <= 0 || this.mAdapter == null) {
            return
        }
        Int scrollX = getScrollX()
        Int width = getWidth()
        Float f2 = this.mPageMargin / width
        ItemInfo itemInfo = (ItemInfo) this.mItems.get(0)
        Float f3 = itemInfo.offset
        Int size = this.mItems.size()
        Int i = itemInfo.position
        Int i2 = ((ItemInfo) this.mItems.get(size - 1)).position
        Int i3 = 0
        for (Int i4 = i; i4 < i2; i4++) {
            while (i4 > itemInfo.position && i3 < size) {
                i3++
                itemInfo = (ItemInfo) this.mItems.get(i3)
            }
            if (i4 == itemInfo.position) {
                f = (itemInfo.offset + itemInfo.widthFactor) * width
                f3 = itemInfo.offset + itemInfo.widthFactor + f2
            } else {
                Float pageWidth = this.mAdapter.getPageWidth(i4)
                f = (f3 + pageWidth) * width
                f3 += pageWidth + f2
            }
            if (this.mPageMargin + f > scrollX) {
                this.mMarginDrawable.setBounds(Math.round(f), this.mTopPageBounds, Math.round(this.mPageMargin + f), this.mBottomPageBounds)
                this.mMarginDrawable.draw(canvas)
            }
            if (f > scrollX + width) {
                return
            }
        }
    }

    @Override // android.view.ViewGroup
    fun onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        Int action = motionEvent.getAction() & 255
        if (action == 3 || action == 1) {
            resetTouch()
            return false
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true
            }
            if (this.mIsUnableToDrag) {
                return false
            }
        }
        switch (action) {
            case 0:
                Float x = motionEvent.getX()
                this.mInitialMotionX = x
                this.mLastMotionX = x
                Float y = motionEvent.getY()
                this.mInitialMotionY = y
                this.mLastMotionY = y
                this.mActivePointerId = motionEvent.getPointerId(0)
                this.mIsUnableToDrag = false
                this.mIsScrollStarted = true
                this.mScroller.computeScrollOffset()
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation()
                    this.mPopulatePending = false
                    populate()
                    this.mIsBeingDragged = true
                    requestParentDisallowInterceptTouchEvent(true)
                    setScrollState(1)
                    break
                } else {
                    completeScroll(false)
                    this.mIsBeingDragged = false
                    break
                }
            case 2:
                Int i = this.mActivePointerId
                if (i != -1) {
                    Int iFindPointerIndex = motionEvent.findPointerIndex(i)
                    Float x2 = motionEvent.getX(iFindPointerIndex)
                    Float f = x2 - this.mLastMotionX
                    Float fAbs = Math.abs(f)
                    Float y2 = motionEvent.getY(iFindPointerIndex)
                    Float fAbs2 = Math.abs(y2 - this.mInitialMotionY)
                    if (f != 0.0f && !isGutterDrag(this.mLastMotionX, f) && canScroll(this, false, (Int) f, (Int) x2, (Int) y2)) {
                        this.mLastMotionX = x2
                        this.mLastMotionY = y2
                        this.mIsUnableToDrag = true
                        return false
                    }
                    if (fAbs > this.mTouchSlop && 0.5f * fAbs > fAbs2) {
                        this.mIsBeingDragged = true
                        requestParentDisallowInterceptTouchEvent(true)
                        setScrollState(1)
                        this.mLastMotionX = f > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop
                        this.mLastMotionY = y2
                        setScrollingCacheEnabled(true)
                    } else if (fAbs2 > this.mTouchSlop) {
                        this.mIsUnableToDrag = true
                    }
                    if (this.mIsBeingDragged && performDrag(x2)) {
                        ViewCompat.postInvalidateOnAnimation(this)
                        break
                    }
                }
                break
            case 6:
                onSecondaryPointerUp(motionEvent)
                break
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain()
        }
        this.mVelocityTracker.addMovement(motionEvent)
        return this.mIsBeingDragged
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0141  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun onLayout(Boolean r18, Int r19, Int r20, Int r21, Int r22) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onLayout(Boolean, Int, Int, Int, Int):Unit")
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x010a A[PHI: r1
  0x010a: PHI (r1v18 Int) = (r1v17 Int), (r1v20 Int) binds: [B:28:0x0088, B:30:0x008f] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun onMeasure(Int r14, Int r15) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onMeasure(Int, Int):Unit")
    }

    @CallSuper
    protected fun onPageScrolled(Int i, Float f, Int i2) {
        Int i3
        Int i4
        Int measuredWidth
        if (this.mDecorChildCount > 0) {
            Int scrollX = getScrollX()
            Int paddingLeft = getPaddingLeft()
            Int paddingRight = getPaddingRight()
            Int width = getWidth()
            Int childCount = getChildCount()
            Int i5 = 0
            while (i5 < childCount) {
                View childAt = getChildAt(i5)
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                if (layoutParams.isDecor) {
                    switch (layoutParams.gravity & 7) {
                        case 1:
                            measuredWidth = Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft)
                            Int i6 = paddingRight
                            i3 = paddingLeft
                            i4 = i6
                            break
                        case 2:
                        case 4:
                        default:
                            measuredWidth = paddingLeft
                            Int i7 = paddingRight
                            i3 = paddingLeft
                            i4 = i7
                            break
                        case 3:
                            Int width2 = childAt.getWidth() + paddingLeft
                            Int i8 = paddingLeft
                            i4 = paddingRight
                            i3 = width2
                            measuredWidth = i8
                            break
                        case 5:
                            measuredWidth = (width - paddingRight) - childAt.getMeasuredWidth()
                            Int measuredWidth2 = paddingRight + childAt.getMeasuredWidth()
                            i3 = paddingLeft
                            i4 = measuredWidth2
                            break
                    }
                    Int left = (measuredWidth + scrollX) - childAt.getLeft()
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left)
                    }
                } else {
                    Int i9 = paddingRight
                    i3 = paddingLeft
                    i4 = i9
                }
                i5++
                Int i10 = i4
                paddingLeft = i3
                paddingRight = i10
            }
        }
        dispatchOnPageScrolled(i, f, i2)
        if (this.mPageTransformer != null) {
            Int scrollX2 = getScrollX()
            Int childCount2 = getChildCount()
            for (Int i11 = 0; i11 < childCount2; i11++) {
                View childAt2 = getChildAt(i11)
                if (!((LayoutParams) childAt2.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(childAt2, (childAt2.getLeft() - scrollX2) / getClientWidth())
                }
            }
        }
        this.mCalledSuper = true
    }

    @Override // android.view.ViewGroup
    protected fun onRequestFocusInDescendants(Int i, Rect rect) {
        Int i2
        ItemInfo itemInfoInfoForChild
        Int i3 = -1
        Int childCount = getChildCount()
        if ((i & 2) != 0) {
            i3 = 1
            i2 = 0
        } else {
            i2 = childCount - 1
            childCount = -1
        }
        while (i2 != childCount) {
            View childAt = getChildAt(i2)
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                return true
            }
            i2 += i3
        }
        return false
    }

    @Override // android.view.View
    fun onRestoreInstanceState(Parcelable parcelable) throws Resources.NotFoundException {
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(savedState.adapterState, savedState.loader)
            setCurrentItemInternal(savedState.position, false, true)
        } else {
            this.mRestoredCurItem = savedState.position
            this.mRestoredAdapterState = savedState.adapterState
            this.mRestoredClassLoader = savedState.loader
        }
    }

    @Override // android.view.View
    fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        savedState.position = this.mCurItem
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState()
        }
        return savedState
    }

    @Override // android.view.View
    protected fun onSizeChanged(Int i, Int i2, Int i3, Int i4) {
        super.onSizeChanged(i, i2, i3, i4)
        if (i != i3) {
            recomputeScrollPosition(i, i3, this.mPageMargin, this.mPageMargin)
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00c4  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onTouchEvent(android.view.MotionEvent r8) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onTouchEvent(android.view.MotionEvent):Boolean")
    }

    Boolean pageLeft() throws Resources.NotFoundException {
        if (this.mCurItem <= 0) {
            return false
        }
        setCurrentItem(this.mCurItem - 1, true)
        return true
    }

    Boolean pageRight() throws Resources.NotFoundException {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false
        }
        setCurrentItem(this.mCurItem + 1, true)
        return true
    }

    Unit populate() throws Resources.NotFoundException {
        populate(this.mCurItem)
    }

    Unit populate(Int i) throws Resources.NotFoundException {
        ItemInfo itemInfo
        String hexString
        Int i2
        ItemInfo itemInfo2
        ItemInfo itemInfoInfoForChild
        if (this.mCurItem != i) {
            ItemInfo itemInfoInfoForPosition = infoForPosition(this.mCurItem)
            this.mCurItem = i
            itemInfo = itemInfoInfoForPosition
        } else {
            itemInfo = null
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder()
            return
        }
        if (this.mPopulatePending) {
            sortChildDrawingOrder()
            return
        }
        if (getWindowToken() != null) {
            this.mAdapter.startUpdate((ViewGroup) this)
            Int i3 = this.mOffscreenPageLimit
            Int iMax = Math.max(0, this.mCurItem - i3)
            Int count = this.mAdapter.getCount()
            Int iMin = Math.min(count - 1, i3 + this.mCurItem)
            if (count != this.mExpectedAdapterCount) {
                try {
                    hexString = getResources().getResourceName(getId())
                } catch (Resources.NotFoundException e) {
                    hexString = Integer.toHexString(getId())
                }
                throw IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + count + " Pager id: " + hexString + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass())
            }
            Int i4 = 0
            while (true) {
                i2 = i4
                if (i2 >= this.mItems.size()) {
                    break
                }
                itemInfo2 = (ItemInfo) this.mItems.get(i2)
                if (itemInfo2.position >= this.mCurItem) {
                    if (itemInfo2.position != this.mCurItem) {
                        break
                    }
                } else {
                    i4 = i2 + 1
                }
            }
            itemInfo2 = null
            ItemInfo itemInfoAddNewItem = (itemInfo2 != null || count <= 0) ? itemInfo2 : addNewItem(this.mCurItem, i2)
            if (itemInfoAddNewItem != null) {
                Int i5 = i2 - 1
                ItemInfo itemInfo3 = i5 >= 0 ? (ItemInfo) this.mItems.get(i5) : null
                Int clientWidth = getClientWidth()
                Float paddingLeft = clientWidth <= 0 ? 0.0f : (2.0f - itemInfoAddNewItem.widthFactor) + (getPaddingLeft() / clientWidth)
                Float f = 0.0f
                Int i6 = i2
                Int i7 = i5
                for (Int i8 = this.mCurItem - 1; i8 >= 0; i8--) {
                    if (f >= paddingLeft && i8 < iMax) {
                        if (itemInfo3 == null) {
                            break
                        }
                        if (i8 == itemInfo3.position && !itemInfo3.scrolling) {
                            this.mItems.remove(i7)
                            this.mAdapter.destroyItem((ViewGroup) this, i8, itemInfo3.object)
                            i7--
                            i6--
                            itemInfo3 = i7 >= 0 ? (ItemInfo) this.mItems.get(i7) : null
                        }
                    } else if (itemInfo3 == null || i8 != itemInfo3.position) {
                        f += addNewItem(i8, i7 + 1).widthFactor
                        i6++
                        itemInfo3 = i7 >= 0 ? (ItemInfo) this.mItems.get(i7) : null
                    } else {
                        f += itemInfo3.widthFactor
                        i7--
                        itemInfo3 = i7 >= 0 ? (ItemInfo) this.mItems.get(i7) : null
                    }
                }
                Float f2 = itemInfoAddNewItem.widthFactor
                Int i9 = i6 + 1
                if (f2 < 2.0f) {
                    ItemInfo itemInfo4 = i9 < this.mItems.size() ? (ItemInfo) this.mItems.get(i9) : null
                    Float paddingRight = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f
                    ItemInfo itemInfo5 = itemInfo4
                    Int i10 = i9
                    Int i11 = this.mCurItem + 1
                    ItemInfo itemInfo6 = itemInfo5
                    while (i11 < count) {
                        if (f2 >= paddingRight && i11 > iMin) {
                            if (itemInfo6 == null) {
                                break
                            }
                            if (i11 == itemInfo6.position && !itemInfo6.scrolling) {
                                this.mItems.remove(i10)
                                this.mAdapter.destroyItem((ViewGroup) this, i11, itemInfo6.object)
                                itemInfo6 = i10 < this.mItems.size() ? (ItemInfo) this.mItems.get(i10) : null
                            }
                        } else if (itemInfo6 == null || i11 != itemInfo6.position) {
                            ItemInfo itemInfoAddNewItem2 = addNewItem(i11, i10)
                            i10++
                            f2 += itemInfoAddNewItem2.widthFactor
                            itemInfo6 = i10 < this.mItems.size() ? (ItemInfo) this.mItems.get(i10) : null
                        } else {
                            f2 += itemInfo6.widthFactor
                            i10++
                            itemInfo6 = i10 < this.mItems.size() ? (ItemInfo) this.mItems.get(i10) : null
                        }
                        i11++
                        itemInfo6 = itemInfo6
                        f2 = f2
                    }
                }
                calculatePageOffsets(itemInfoAddNewItem, i6, itemInfo)
                this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, itemInfoAddNewItem.object)
            }
            this.mAdapter.finishUpdate((ViewGroup) this)
            Int childCount = getChildCount()
            for (Int i12 = 0; i12 < childCount; i12++) {
                View childAt = getChildAt(i12)
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                layoutParams.childIndex = i12
                if (!layoutParams.isDecor && layoutParams.widthFactor == 0.0f && (itemInfoInfoForChild = infoForChild(childAt)) != null) {
                    layoutParams.widthFactor = itemInfoInfoForChild.widthFactor
                    layoutParams.position = itemInfoInfoForChild.position
                }
            }
            sortChildDrawingOrder()
            if (hasFocus()) {
                View viewFindFocus = findFocus()
                ItemInfo itemInfoInfoForAnyChild = viewFindFocus != null ? infoForAnyChild(viewFindFocus) : null
                if (itemInfoInfoForAnyChild == null || itemInfoInfoForAnyChild.position != this.mCurItem) {
                    for (Int i13 = 0; i13 < getChildCount(); i13++) {
                        View childAt2 = getChildAt(i13)
                        ItemInfo itemInfoInfoForChild2 = infoForChild(childAt2)
                        if (itemInfoInfoForChild2 != null && itemInfoInfoForChild2.position == this.mCurItem && childAt2.requestFocus(2)) {
                            return
                        }
                    }
                }
            }
        }
    }

    fun removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners != null) {
            this.mAdapterChangeListeners.remove(onAdapterChangeListener)
        }
    }

    fun removeOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.remove(onPageChangeListener)
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    fun removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view)
        } else {
            super.removeView(view)
        }
    }

    fun setAdapter(@Nullable PagerAdapter pagerAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.setViewPagerObserver(null)
            this.mAdapter.startUpdate((ViewGroup) this)
            for (Int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = (ItemInfo) this.mItems.get(i)
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object)
            }
            this.mAdapter.finishUpdate((ViewGroup) this)
            this.mItems.clear()
            removeNonDecorViews()
            this.mCurItem = 0
            scrollTo(0, 0)
        }
        PagerAdapter pagerAdapter2 = this.mAdapter
        this.mAdapter = pagerAdapter
        this.mExpectedAdapterCount = 0
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = PagerObserver()
            }
            this.mAdapter.setViewPagerObserver(this.mObserver)
            this.mPopulatePending = false
            Boolean z = this.mFirstLayout
            this.mFirstLayout = true
            this.mExpectedAdapterCount = this.mAdapter.getCount()
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader)
                setCurrentItemInternal(this.mRestoredCurItem, false, true)
                this.mRestoredCurItem = -1
                this.mRestoredAdapterState = null
                this.mRestoredClassLoader = null
            } else if (z) {
                requestLayout()
            } else {
                populate()
            }
        }
        if (this.mAdapterChangeListeners == null || this.mAdapterChangeListeners.isEmpty()) {
            return
        }
        Int size = this.mAdapterChangeListeners.size()
        for (Int i2 = 0; i2 < size; i2++) {
            ((OnAdapterChangeListener) this.mAdapterChangeListeners.get(i2)).onAdapterChanged(this, pagerAdapter2, pagerAdapter)
        }
    }

    fun setCurrentItem(Int i) {
        this.mPopulatePending = false
        setCurrentItemInternal(i, !this.mFirstLayout, false)
    }

    fun setCurrentItem(Int i, Boolean z) throws Resources.NotFoundException {
        this.mPopulatePending = false
        setCurrentItemInternal(i, z, false)
    }

    Unit setCurrentItemInternal(Int i, Boolean z, Boolean z2) throws Resources.NotFoundException {
        setCurrentItemInternal(i, z, z2, 0)
    }

    Unit setCurrentItemInternal(Int i, Boolean z, Boolean z2, Int i2) throws Resources.NotFoundException {
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false)
            return
        }
        if (!z2 && this.mCurItem == i && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false)
            return
        }
        if (i < 0) {
            i = 0
        } else if (i >= this.mAdapter.getCount()) {
            i = this.mAdapter.getCount() - 1
        }
        Int i3 = this.mOffscreenPageLimit
        if (i > this.mCurItem + i3 || i < this.mCurItem - i3) {
            for (Int i4 = 0; i4 < this.mItems.size(); i4++) {
                ((ItemInfo) this.mItems.get(i4)).scrolling = true
            }
        }
        Boolean z3 = this.mCurItem != i
        if (!this.mFirstLayout) {
            populate(i)
            scrollToItem(i, z, i2, z3)
        } else {
            this.mCurItem = i
            if (z3) {
                dispatchOnPageSelected(i)
            }
            requestLayout()
        }
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener
        this.mInternalPageChangeListener = onPageChangeListener
        return onPageChangeListener2
    }

    fun setOffscreenPageLimit(Int i) throws Resources.NotFoundException {
        if (i <= 0) {
            Log.w(TAG, "Requested offscreen page limit " + i + " too small; defaulting to 1")
            i = 1
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i
            populate()
        }
    }

    @Deprecated
    fun setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener
    }

    fun setPageMargin(Int i) {
        Int i2 = this.mPageMargin
        this.mPageMargin = i
        Int width = getWidth()
        recomputeScrollPosition(width, width, i, i2)
        requestLayout()
    }

    fun setPageMarginDrawable(@DrawableRes Int i) {
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), i))
    }

    fun setPageMarginDrawable(@Nullable Drawable drawable) {
        this.mMarginDrawable = drawable
        if (drawable != null) {
            refreshDrawableState()
        }
        setWillNotDraw(drawable == null)
        invalidate()
    }

    fun setPageTransformer(Boolean z, @Nullable PageTransformer pageTransformer) throws Resources.NotFoundException {
        setPageTransformer(z, pageTransformer, 2)
    }

    fun setPageTransformer(Boolean z, @Nullable PageTransformer pageTransformer, Int i) throws Resources.NotFoundException {
        Boolean z2 = pageTransformer != null
        Boolean z3 = z2 != (this.mPageTransformer != null)
        this.mPageTransformer = pageTransformer
        setChildrenDrawingOrderEnabled(z2)
        if (z2) {
            this.mDrawingOrder = z ? 2 : 1
            this.mPageTransformerLayerType = i
        } else {
            this.mDrawingOrder = 0
        }
        if (z3) {
            populate()
        }
    }

    Unit setScrollState(Int i) {
        if (this.mScrollState == i) {
            return
        }
        this.mScrollState = i
        if (this.mPageTransformer != null) {
            enableLayers(i != 0)
        }
        dispatchOnScrollStateChanged(i)
    }

    Unit smoothScrollTo(Int i, Int i2) throws Resources.NotFoundException {
        smoothScrollTo(i, i2, 0)
    }

    Unit smoothScrollTo(Int i, Int i2, Int i3) throws Resources.NotFoundException {
        Int scrollX
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false)
            return
        }
        if ((this.mScroller == null || this.mScroller.isFinished()) ? false : true) {
            Int currX = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX()
            this.mScroller.abortAnimation()
            setScrollingCacheEnabled(false)
            scrollX = currX
        } else {
            scrollX = getScrollX()
        }
        Int scrollY = getScrollY()
        Int i4 = i - scrollX
        Int i5 = i2 - scrollY
        if (i4 == 0 && i5 == 0) {
            completeScroll(false)
            populate()
            setScrollState(0)
            return
        }
        setScrollingCacheEnabled(true)
        setScrollState(2)
        Int clientWidth = getClientWidth()
        Int i6 = clientWidth / 2
        Float fDistanceInfluenceForSnapDuration = (i6 * distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(i4) * 1.0f) / clientWidth))) + i6
        Int iAbs = Math.abs(i3)
        Int iMin = Math.min(iAbs > 0 ? Math.round(1000.0f * Math.abs(fDistanceInfluenceForSnapDuration / iAbs)) * 4 : (Int) (((Math.abs(i4) / ((clientWidth * this.mAdapter.getPageWidth(this.mCurItem)) + this.mPageMargin)) + 1.0f) * 100.0f), MAX_SETTLE_DURATION)
        this.mIsScrollStarted = false
        this.mScroller.startScroll(scrollX, scrollY, i4, i5, iMin)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    @Override // android.view.View
    protected fun verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable
    }
}
