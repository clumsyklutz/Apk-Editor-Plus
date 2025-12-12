package android.support.design.widget

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Region
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.FloatRange
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.VisibleForTesting
import android.support.coordinatorlayout.R
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.util.ObjectsCompat
import android.support.v4.util.Pools
import android.support.v4.view.AbsSavedState
import androidx.core.view.GravityCompat
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.support.v4.widget.DirectedAcyclicGraph
import android.support.v4.widget.ViewGroupUtils
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import jadx.core.deobf.Deobfuscator
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.List
import java.util.Map

class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2 {
    static final Array<Class> CONSTRUCTOR_PARAMS
    static val EVENT_NESTED_SCROLL = 1
    static val EVENT_PRE_DRAW = 0
    static val EVENT_VIEW_REMOVED = 2
    static val TAG = "CoordinatorLayout"
    static final Comparator TOP_SORTED_CHILDREN_COMPARATOR
    private static val TYPE_ON_INTERCEPT = 0
    private static val TYPE_ON_TOUCH = 1
    static final String WIDGET_PACKAGE_NAME
    static final ThreadLocal sConstructors
    private static final Pools.Pool sRectPool
    private OnApplyWindowInsetsListener mApplyWindowInsetsListener
    private View mBehaviorTouchView
    private final DirectedAcyclicGraph mChildDag
    private final List mDependencySortedChildren
    private Boolean mDisallowInterceptReset
    private Boolean mDrawStatusBarBackground
    private Boolean mIsAttachedToWindow
    private Array<Int> mKeylines
    private WindowInsetsCompat mLastInsets
    private Boolean mNeedsPreDrawListener
    private final NestedScrollingParentHelper mNestedScrollingParentHelper
    private View mNestedScrollingTarget
    ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener
    private OnPreDrawListener mOnPreDrawListener
    private Paint mScrimPaint
    private Drawable mStatusBarBackground
    private final List mTempDependenciesList
    private final Array<Int> mTempIntPair
    private final List mTempList1

    public interface AttachedBehavior {
        @NonNull
        Behavior getBehavior()
    }

    abstract class Behavior {
        constructor() {
        }

        constructor(Context context, AttributeSet attributeSet) {
        }

        @Nullable
        fun getTag(@NonNull View view) {
            return ((LayoutParams) view.getLayoutParams()).mBehaviorTag
        }

        fun setTag(@NonNull View view, @Nullable Object obj) {
            ((LayoutParams) view.getLayoutParams()).mBehaviorTag = obj
        }

        fun blocksInteractionBelow(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            return getScrimOpacity(coordinatorLayout, view) > 0.0f
        }

        fun getInsetDodgeRect(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull Rect rect) {
            return false
        }

        @ColorInt
        fun getScrimColor(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            return ViewCompat.MEASURED_STATE_MASK
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        fun getScrimOpacity(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            return 0.0f
        }

        fun layoutDependsOn(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
            return false
        }

        @NonNull
        fun onApplyWindowInsets(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat
        }

        fun onAttachedToLayoutParams(@NonNull LayoutParams layoutParams) {
        }

        fun onDependentViewChanged(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
            return false
        }

        fun onDependentViewRemoved(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
        }

        fun onDetachedFromLayoutParams() {
        }

        fun onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull MotionEvent motionEvent) {
            return false
        }

        fun onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, Int i) {
            return false
        }

        fun onMeasureChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, Int i, Int i2, Int i3, Int i4) {
            return false
        }

        fun onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Float f, Float f2, Boolean z) {
            return false
        }

        fun onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Float f, Float f2) {
            return false
        }

        @Deprecated
        fun onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Int i, Int i2, @NonNull Array<Int> iArr) {
        }

        fun onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Int i, Int i2, @NonNull Array<Int> iArr, Int i3) {
            if (i3 == 0) {
                onNestedPreScroll(coordinatorLayout, view, view2, i, i2, iArr)
            }
        }

        @Deprecated
        fun onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Int i, Int i2, Int i3, Int i4) {
        }

        fun onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Int i, Int i2, Int i3, Int i4, Int i5) {
            if (i5 == 0) {
                onNestedScroll(coordinatorLayout, view, view2, i, i2, i3, i4)
            }
        }

        @Deprecated
        fun onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, @NonNull View view3, Int i) {
        }

        fun onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, @NonNull View view3, Int i, Int i2) {
            if (i2 == 0) {
                onNestedScrollAccepted(coordinatorLayout, view, view2, view3, i)
            }
        }

        fun onRequestChildRectangleOnScreen(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull Rect rect, Boolean z) {
            return false
        }

        fun onRestoreInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull Parcelable parcelable) {
        }

        @Nullable
        fun onSaveInstanceState(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view) {
            return View.BaseSavedState.EMPTY_STATE
        }

        @Deprecated
        fun onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, @NonNull View view3, Int i) {
            return false
        }

        fun onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, @NonNull View view3, Int i, Int i2) {
            if (i2 == 0) {
                return onStartNestedScroll(coordinatorLayout, view, view2, view3, i)
            }
            return false
        }

        @Deprecated
        fun onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
        }

        fun onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2, Int i) {
            if (i == 0) {
                onStopNestedScroll(coordinatorLayout, view, view2)
            }
        }

        fun onTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull MotionEvent motionEvent) {
            return false
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Deprecated
    public @interface DefaultBehavior {
        Class value()
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface DispatchChangeEvent {
    }

    class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        HierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        fun onChildViewAdded(View view, View view2) {
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(view, view2)
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        fun onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2)
            if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
                CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2)
            }
        }
    }

    class LayoutParams extends ViewGroup.MarginLayoutParams {
        public Int anchorGravity
        public Int dodgeInsetEdges
        public Int gravity
        public Int insetEdge
        public Int keyline
        View mAnchorDirectChild
        Int mAnchorId
        View mAnchorView
        Behavior mBehavior
        Boolean mBehaviorResolved
        Object mBehaviorTag
        private Boolean mDidAcceptNestedScrollNonTouch
        private Boolean mDidAcceptNestedScrollTouch
        private Boolean mDidBlockInteraction
        private Boolean mDidChangeAfterNestedScroll
        Int mInsetOffsetX
        Int mInsetOffsetY
        final Rect mLastChildRect

        constructor(Int i, Int i2) {
            super(i, i2)
            this.mBehaviorResolved = false
            this.gravity = 0
            this.anchorGravity = 0
            this.keyline = -1
            this.mAnchorId = -1
            this.insetEdge = 0
            this.dodgeInsetEdges = 0
            this.mLastChildRect = Rect()
        }

        LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet)
            this.mBehaviorResolved = false
            this.gravity = 0
            this.anchorGravity = 0
            this.keyline = -1
            this.mAnchorId = -1
            this.insetEdge = 0
            this.dodgeInsetEdges = 0
            this.mLastChildRect = Rect()
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout)
            this.gravity = typedArrayObtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0)
            this.mAnchorId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1)
            this.anchorGravity = typedArrayObtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0)
            this.keyline = typedArrayObtainStyledAttributes.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1)
            this.insetEdge = typedArrayObtainStyledAttributes.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0)
            this.dodgeInsetEdges = typedArrayObtainStyledAttributes.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0)
            this.mBehaviorResolved = typedArrayObtainStyledAttributes.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior)
            if (this.mBehaviorResolved) {
                this.mBehavior = CoordinatorLayout.parseBehavior(context, attributeSet, typedArrayObtainStyledAttributes.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior))
            }
            typedArrayObtainStyledAttributes.recycle()
            if (this.mBehavior != null) {
                this.mBehavior.onAttachedToLayoutParams(this)
            }
        }

        constructor(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams)
            this.mBehaviorResolved = false
            this.gravity = 0
            this.anchorGravity = 0
            this.keyline = -1
            this.mAnchorId = -1
            this.insetEdge = 0
            this.dodgeInsetEdges = 0
            this.mLastChildRect = Rect()
        }

        constructor(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
            this.mBehaviorResolved = false
            this.gravity = 0
            this.anchorGravity = 0
            this.keyline = -1
            this.mAnchorId = -1
            this.insetEdge = 0
            this.dodgeInsetEdges = 0
            this.mLastChildRect = Rect()
        }

        constructor(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams)
            this.mBehaviorResolved = false
            this.gravity = 0
            this.anchorGravity = 0
            this.keyline = -1
            this.mAnchorId = -1
            this.insetEdge = 0
            this.dodgeInsetEdges = 0
            this.mLastChildRect = Rect()
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v6, types: [android.view.ViewParent] */
        private fun resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            this.mAnchorView = coordinatorLayout.findViewById(this.mAnchorId)
            if (this.mAnchorView == null) {
                if (!coordinatorLayout.isInEditMode()) {
                    throw IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(this.mAnchorId) + " to anchor view " + view)
                }
                this.mAnchorDirectChild = null
                this.mAnchorView = null
                return
            }
            if (this.mAnchorView == coordinatorLayout) {
                if (!coordinatorLayout.isInEditMode()) {
                    throw IllegalStateException("View can not be anchored to the the parent CoordinatorLayout")
                }
                this.mAnchorDirectChild = null
                this.mAnchorView = null
                return
            }
            CoordinatorLayout coordinatorLayout2 = this.mAnchorView
            for (CoordinatorLayout parent = this.mAnchorView.getParent(); parent != coordinatorLayout && parent != null; parent = parent.getParent()) {
                if (parent == view) {
                    if (!coordinatorLayout.isInEditMode()) {
                        throw IllegalStateException("Anchor must not be a descendant of the anchored view")
                    }
                    this.mAnchorDirectChild = null
                    this.mAnchorView = null
                    return
                }
                if (parent is View) {
                    coordinatorLayout2 = parent
                }
            }
            this.mAnchorDirectChild = coordinatorLayout2
        }

        private fun shouldDodge(View view, Int i) {
            Int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).insetEdge, i)
            return absoluteGravity != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, i) & absoluteGravity) == absoluteGravity
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v2, types: [android.view.ViewParent] */
        private fun verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            if (this.mAnchorView.getId() != this.mAnchorId) {
                return false
            }
            CoordinatorLayout coordinatorLayout2 = this.mAnchorView
            for (CoordinatorLayout parent = this.mAnchorView.getParent(); parent != coordinatorLayout; parent = parent.getParent()) {
                if (parent == null || parent == view) {
                    this.mAnchorDirectChild = null
                    this.mAnchorView = null
                    return false
                }
                if (parent is View) {
                    coordinatorLayout2 = parent
                }
            }
            this.mAnchorDirectChild = coordinatorLayout2
            return true
        }

        Boolean checkAnchorChanged() {
            return this.mAnchorView == null && this.mAnchorId != -1
        }

        Boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 == this.mAnchorDirectChild || shouldDodge(view2, ViewCompat.getLayoutDirection(coordinatorLayout)) || (this.mBehavior != null && this.mBehavior.layoutDependsOn(coordinatorLayout, view, view2))
        }

        Boolean didBlockInteraction() {
            if (this.mBehavior == null) {
                this.mDidBlockInteraction = false
            }
            return this.mDidBlockInteraction
        }

        View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mAnchorId == -1) {
                this.mAnchorDirectChild = null
                this.mAnchorView = null
                return null
            }
            if (this.mAnchorView == null || !verifyAnchorView(view, coordinatorLayout)) {
                resolveAnchorView(view, coordinatorLayout)
            }
            return this.mAnchorView
        }

        @IdRes
        fun getAnchorId() {
            return this.mAnchorId
        }

        @Nullable
        fun getBehavior() {
            return this.mBehavior
        }

        Boolean getChangedAfterNestedScroll() {
            return this.mDidChangeAfterNestedScroll
        }

        Rect getLastChildRect() {
            return this.mLastChildRect
        }

        Unit invalidateAnchor() {
            this.mAnchorDirectChild = null
            this.mAnchorView = null
        }

        Boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mDidBlockInteraction) {
                return true
            }
            Boolean zBlocksInteractionBelow = (this.mBehavior != null ? this.mBehavior.blocksInteractionBelow(coordinatorLayout, view) : false) | this.mDidBlockInteraction
            this.mDidBlockInteraction = zBlocksInteractionBelow
            return zBlocksInteractionBelow
        }

        Boolean isNestedScrollAccepted(Int i) {
            switch (i) {
                case 0:
                    return this.mDidAcceptNestedScrollTouch
                case 1:
                    return this.mDidAcceptNestedScrollNonTouch
                default:
                    return false
            }
        }

        Unit resetChangedAfterNestedScroll() {
            this.mDidChangeAfterNestedScroll = false
        }

        Unit resetNestedScroll(Int i) {
            setNestedScrollAccepted(i, false)
        }

        Unit resetTouchBehaviorTracking() {
            this.mDidBlockInteraction = false
        }

        fun setAnchorId(@IdRes Int i) {
            invalidateAnchor()
            this.mAnchorId = i
        }

        fun setBehavior(@Nullable Behavior behavior) {
            if (this.mBehavior != behavior) {
                if (this.mBehavior != null) {
                    this.mBehavior.onDetachedFromLayoutParams()
                }
                this.mBehavior = behavior
                this.mBehaviorTag = null
                this.mBehaviorResolved = true
                if (behavior != null) {
                    behavior.onAttachedToLayoutParams(this)
                }
            }
        }

        Unit setChangedAfterNestedScroll(Boolean z) {
            this.mDidChangeAfterNestedScroll = z
        }

        Unit setLastChildRect(Rect rect) {
            this.mLastChildRect.set(rect)
        }

        Unit setNestedScrollAccepted(Int i, Boolean z) {
            switch (i) {
                case 0:
                    this.mDidAcceptNestedScrollTouch = z
                    break
                case 1:
                    this.mDidAcceptNestedScrollNonTouch = z
                    break
            }
        }
    }

    class OnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        OnPreDrawListener() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        fun onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0)
            return true
        }
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.design.widget.CoordinatorLayout.SavedState.1
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
        SparseArray behaviorStates

        constructor(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader)
            Int i = parcel.readInt()
            Array<Int> iArr = new Int[i]
            parcel.readIntArray(iArr)
            Array<Parcelable> parcelableArray = parcel.readParcelableArray(classLoader)
            this.behaviorStates = SparseArray(i)
            for (Int i2 = 0; i2 < i; i2++) {
                this.behaviorStates.append(iArr[i2], parcelableArray[i2])
            }
        }

        constructor(Parcelable parcelable) {
            super(parcelable)
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            Int size = this.behaviorStates != null ? this.behaviorStates.size() : 0
            parcel.writeInt(size)
            Array<Int> iArr = new Int[size]
            Array<Parcelable> parcelableArr = new Parcelable[size]
            for (Int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.behaviorStates.keyAt(i2)
                parcelableArr[i2] = (Parcelable) this.behaviorStates.valueAt(i2)
            }
            parcel.writeIntArray(iArr)
            parcel.writeParcelableArray(parcelableArr, i)
        }
    }

    class ViewElevationComparator implements Comparator {
        ViewElevationComparator() {
        }

        @Override // java.util.Comparator
        fun compare(View view, View view2) {
            Float z = ViewCompat.getZ(view)
            Float z2 = ViewCompat.getZ(view2)
            if (z > z2) {
                return -1
            }
            return z < z2 ? 1 : 0
        }
    }

    static {
        Package r0 = CoordinatorLayout.class.getPackage()
        WIDGET_PACKAGE_NAME = r0 != null ? r0.getName() : null
        if (Build.VERSION.SDK_INT >= 21) {
            TOP_SORTED_CHILDREN_COMPARATOR = ViewElevationComparator()
        } else {
            TOP_SORTED_CHILDREN_COMPARATOR = null
        }
        CONSTRUCTOR_PARAMS = new Array<Class>{Context.class, AttributeSet.class}
        sConstructors = ThreadLocal()
        sRectPool = new Pools.SynchronizedPool(12)
    }

    constructor(@NonNull Context context) {
        this(context, null)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.coordinatorLayoutStyle)
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes Int i) {
        super(context, attributeSet, i)
        this.mDependencySortedChildren = ArrayList()
        this.mChildDag = DirectedAcyclicGraph()
        this.mTempList1 = ArrayList()
        this.mTempDependenciesList = ArrayList()
        this.mTempIntPair = new Int[2]
        this.mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
        TypedArray typedArrayObtainStyledAttributes = i == 0 ? context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout) : context.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, i, 0)
        Int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CoordinatorLayout_keylines, 0)
        if (resourceId != 0) {
            Resources resources = context.getResources()
            this.mKeylines = resources.getIntArray(resourceId)
            Float f = resources.getDisplayMetrics().density
            Int length = this.mKeylines.length
            for (Int i2 = 0; i2 < length; i2++) {
                this.mKeylines[i2] = (Int) (this.mKeylines[i2] * f)
            }
        }
        this.mStatusBarBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground)
        typedArrayObtainStyledAttributes.recycle()
        setupForInsets()
        super.setOnHierarchyChangeListener(HierarchyChangeListener())
    }

    @NonNull
    private fun acquireTempRect() {
        Rect rect = (Rect) sRectPool.acquire()
        return rect == null ? Rect() : rect
    }

    private fun clamp(Int i, Int i2, Int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i
    }

    private fun constrainChildRect(LayoutParams layoutParams, Rect rect, Int i, Int i2) {
        Int width = getWidth()
        Int height = getHeight()
        Int iMax = Math.max(getPaddingLeft() + layoutParams.leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - layoutParams.rightMargin))
        Int iMax2 = Math.max(getPaddingTop() + layoutParams.topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - layoutParams.bottomMargin))
        rect.set(iMax, iMax2, iMax + i, iMax2 + i2)
    }

    private fun dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompatOnApplyWindowInsets
        Behavior behavior
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat
        }
        Int childCount = getChildCount()
        Int i = 0
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat
        while (true) {
            if (i >= childCount) {
                windowInsetsCompatOnApplyWindowInsets = windowInsetsCompat2
                break
            }
            View childAt = getChildAt(i)
            if (ViewCompat.getFitsSystemWindows(childAt) && (behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior()) != null) {
                windowInsetsCompatOnApplyWindowInsets = behavior.onApplyWindowInsets(this, childAt, windowInsetsCompat2)
                if (windowInsetsCompatOnApplyWindowInsets.isConsumed()) {
                    break
                }
            } else {
                windowInsetsCompatOnApplyWindowInsets = windowInsetsCompat2
            }
            i++
            windowInsetsCompat2 = windowInsetsCompatOnApplyWindowInsets
        }
        return windowInsetsCompatOnApplyWindowInsets
    }

    private fun getDesiredAnchoredChildRectWithoutConstraints(View view, Int i, Rect rect, Rect rect2, LayoutParams layoutParams, Int i2, Int i3) {
        Int iWidth
        Int iHeight
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveAnchoredChildGravity(layoutParams.gravity), i)
        Int absoluteGravity2 = GravityCompat.getAbsoluteGravity(resolveGravity(layoutParams.anchorGravity), i)
        Int i4 = absoluteGravity & 7
        Int i5 = absoluteGravity & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall
        Int i6 = absoluteGravity2 & 7
        Int i7 = absoluteGravity2 & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall
        switch (i6) {
            case 1:
                iWidth = (rect.width() / 2) + rect.left
                break
            case 5:
                iWidth = rect.right
                break
            default:
                iWidth = rect.left
                break
        }
        switch (i7) {
            case 16:
                iHeight = rect.top + (rect.height() / 2)
                break
            case android.support.v7.appcompat.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                iHeight = rect.bottom
                break
            default:
                iHeight = rect.top
                break
        }
        switch (i4) {
            case 1:
                iWidth -= i2 / 2
                break
            case 5:
                break
            default:
                iWidth -= i2
                break
        }
        switch (i5) {
            case 16:
                iHeight -= i3 / 2
                break
            case android.support.v7.appcompat.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                break
            default:
                iHeight -= i3
                break
        }
        rect2.set(iWidth, iHeight, iWidth + i2, iHeight + i3)
    }

    private fun getKeyline(Int i) {
        if (this.mKeylines == null) {
            Log.e(TAG, "No keylines defined for " + this + " - attempted index lookup " + i)
            return 0
        }
        if (i >= 0 && i < this.mKeylines.length) {
            return this.mKeylines[i]
        }
        Log.e(TAG, "Keyline index " + i + " out of range for " + this)
        return 0
    }

    private fun getTopSortedChildren(List list) {
        list.clear()
        Boolean zIsChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled()
        Int childCount = getChildCount()
        for (Int i = childCount - 1; i >= 0; i--) {
            list.add(getChildAt(zIsChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i) : i))
        }
        if (TOP_SORTED_CHILDREN_COMPARATOR != null) {
            Collections.sort(list, TOP_SORTED_CHILDREN_COMPARATOR)
        }
    }

    private fun hasDependencies(View view) {
        return this.mChildDag.hasOutgoingEdges(view)
    }

    private fun layoutChild(View view, Int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Rect rectAcquireTempRect = acquireTempRect()
        rectAcquireTempRect.set(getPaddingLeft() + layoutParams.leftMargin, getPaddingTop() + layoutParams.topMargin, (getWidth() - getPaddingRight()) - layoutParams.rightMargin, (getHeight() - getPaddingBottom()) - layoutParams.bottomMargin)
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this) && !ViewCompat.getFitsSystemWindows(view)) {
            rectAcquireTempRect.left += this.mLastInsets.getSystemWindowInsetLeft()
            rectAcquireTempRect.top += this.mLastInsets.getSystemWindowInsetTop()
            rectAcquireTempRect.right -= this.mLastInsets.getSystemWindowInsetRight()
            rectAcquireTempRect.bottom -= this.mLastInsets.getSystemWindowInsetBottom()
        }
        Rect rectAcquireTempRect2 = acquireTempRect()
        GravityCompat.apply(resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rectAcquireTempRect, rectAcquireTempRect2, i)
        view.layout(rectAcquireTempRect2.left, rectAcquireTempRect2.top, rectAcquireTempRect2.right, rectAcquireTempRect2.bottom)
        releaseTempRect(rectAcquireTempRect)
        releaseTempRect(rectAcquireTempRect2)
    }

    private fun layoutChildWithAnchor(View view, View view2, Int i) {
        Rect rectAcquireTempRect = acquireTempRect()
        Rect rectAcquireTempRect2 = acquireTempRect()
        try {
            getDescendantRect(view2, rectAcquireTempRect)
            getDesiredAnchoredChildRect(view, i, rectAcquireTempRect, rectAcquireTempRect2)
            view.layout(rectAcquireTempRect2.left, rectAcquireTempRect2.top, rectAcquireTempRect2.right, rectAcquireTempRect2.bottom)
        } finally {
            releaseTempRect(rectAcquireTempRect)
            releaseTempRect(rectAcquireTempRect2)
        }
    }

    private fun layoutChildWithKeyline(View view, Int i, Int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(layoutParams.gravity), i2)
        Int i3 = absoluteGravity & 7
        Int i4 = absoluteGravity & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall
        Int width = getWidth()
        Int height = getHeight()
        Int measuredWidth = view.getMeasuredWidth()
        Int measuredHeight = view.getMeasuredHeight()
        if (i2 == 1) {
            i = width - i
        }
        Int keyline = getKeyline(i) - measuredWidth
        Int i5 = 0
        switch (i3) {
            case 1:
                keyline += measuredWidth / 2
                break
            case 5:
                keyline += measuredWidth
                break
        }
        switch (i4) {
            case 16:
                i5 = (measuredHeight / 2) + 0
                break
            case android.support.v7.appcompat.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                i5 = measuredHeight + 0
                break
        }
        Int iMax = Math.max(getPaddingLeft() + layoutParams.leftMargin, Math.min(keyline, ((width - getPaddingRight()) - measuredWidth) - layoutParams.rightMargin))
        Int iMax2 = Math.max(getPaddingTop() + layoutParams.topMargin, Math.min(i5, ((height - getPaddingBottom()) - measuredHeight) - layoutParams.bottomMargin))
        view.layout(iMax, iMax2, iMax + measuredWidth, iMax2 + measuredHeight)
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0103  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun offsetChildByInset(android.view.View r11, android.graphics.Rect r12, Int r13) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.offsetChildByInset(android.view.View, android.graphics.Rect, Int):Unit")
    }

    static Behavior parseBehavior(Context context, AttributeSet attributeSet, String str) throws NoSuchMethodException, SecurityException {
        Map map
        if (TextUtils.isEmpty(str)) {
            return null
        }
        if (str.startsWith(Deobfuscator.CLASS_NAME_SEPARATOR)) {
            str = context.getPackageName() + str
        } else if (str.indexOf(46) < 0 && !TextUtils.isEmpty(WIDGET_PACKAGE_NAME)) {
            str = WIDGET_PACKAGE_NAME + '.' + str
        }
        try {
            Map map2 = (Map) sConstructors.get()
            if (map2 == null) {
                HashMap map3 = HashMap()
                sConstructors.set(map3)
                map = map3
            } else {
                map = map2
            }
            Constructor<?> constructor = (Constructor) map.get(str)
            if (constructor == null) {
                constructor = context.getClassLoader().loadClass(str).getConstructor(CONSTRUCTOR_PARAMS)
                constructor.setAccessible(true)
                map.put(str, constructor)
            }
            return (Behavior) constructor.newInstance(context, attributeSet)
        } catch (Exception e) {
            throw RuntimeException("Could not inflate Behavior subclass " + str, e)
        }
    }

    private fun performIntercept(MotionEvent motionEvent, Int i) {
        Boolean z
        MotionEvent motionEventObtain
        Boolean z2
        Boolean zOnTouchEvent = false
        Boolean z3 = false
        MotionEvent motionEvent2 = null
        Int actionMasked = motionEvent.getActionMasked()
        List list = this.mTempList1
        getTopSortedChildren(list)
        Int size = list.size()
        Int i2 = 0
        while (true) {
            if (i2 < size) {
                View view = (View) list.get(i2)
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
                Behavior behavior = layoutParams.getBehavior()
                if ((!zOnTouchEvent && !z3) || actionMasked == 0) {
                    if (!zOnTouchEvent && behavior != null) {
                        switch (i) {
                            case 0:
                                zOnTouchEvent = behavior.onInterceptTouchEvent(this, view, motionEvent)
                                break
                            case 1:
                                zOnTouchEvent = behavior.onTouchEvent(this, view, motionEvent)
                                break
                        }
                        if (zOnTouchEvent) {
                            this.mBehaviorTouchView = view
                        }
                    }
                    z = zOnTouchEvent
                    Boolean zDidBlockInteraction = layoutParams.didBlockInteraction()
                    Boolean zIsBlockingInteractionBelow = layoutParams.isBlockingInteractionBelow(this, view)
                    Boolean z4 = zIsBlockingInteractionBelow && !zDidBlockInteraction
                    if (!zIsBlockingInteractionBelow || z4) {
                        MotionEvent motionEvent3 = motionEvent2
                        z2 = z4
                        motionEventObtain = motionEvent3
                    }
                } else if (behavior != null) {
                    if (motionEvent2 == null) {
                        Long jUptimeMillis = SystemClock.uptimeMillis()
                        motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0)
                    } else {
                        motionEventObtain = motionEvent2
                    }
                    switch (i) {
                        case 0:
                            behavior.onInterceptTouchEvent(this, view, motionEventObtain)
                            z2 = z3
                            z = zOnTouchEvent
                            continue
                        case 1:
                            behavior.onTouchEvent(this, view, motionEventObtain)
                            break
                    }
                    z2 = z3
                    z = zOnTouchEvent
                } else {
                    motionEventObtain = motionEvent2
                    z = zOnTouchEvent
                    z2 = z3
                }
                i2++
                z3 = z2
                zOnTouchEvent = z
                motionEvent2 = motionEventObtain
            } else {
                z = zOnTouchEvent
            }
        }
        list.clear()
        return z
    }

    private fun prepareChildren() {
        this.mDependencySortedChildren.clear()
        this.mChildDag.clear()
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            LayoutParams resolvedLayoutParams = getResolvedLayoutParams(childAt)
            resolvedLayoutParams.findAnchorView(this, childAt)
            this.mChildDag.addNode(childAt)
            for (Int i2 = 0; i2 < childCount; i2++) {
                if (i2 != i) {
                    View childAt2 = getChildAt(i2)
                    if (resolvedLayoutParams.dependsOn(this, childAt, childAt2)) {
                        if (!this.mChildDag.contains(childAt2)) {
                            this.mChildDag.addNode(childAt2)
                        }
                        this.mChildDag.addEdge(childAt2, childAt)
                    }
                }
            }
        }
        this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList())
        Collections.reverse(this.mDependencySortedChildren)
    }

    private fun releaseTempRect(@NonNull Rect rect) {
        rect.setEmpty()
        sRectPool.release(rect)
    }

    private fun resetTouchBehaviors(Boolean z) {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior()
            if (behavior != null) {
                Long jUptimeMillis = SystemClock.uptimeMillis()
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0)
                if (z) {
                    behavior.onInterceptTouchEvent(this, childAt, motionEventObtain)
                } else {
                    behavior.onTouchEvent(this, childAt, motionEventObtain)
                }
                motionEventObtain.recycle()
            }
        }
        for (Int i2 = 0; i2 < childCount; i2++) {
            ((LayoutParams) getChildAt(i2).getLayoutParams()).resetTouchBehaviorTracking()
        }
        this.mBehaviorTouchView = null
        this.mDisallowInterceptReset = false
    }

    private fun resolveAnchoredChildGravity(Int i) {
        if (i == 0) {
            return 17
        }
        return i
    }

    private fun resolveGravity(Int i) {
        Int i2 = (i & 7) == 0 ? 8388611 | i : i
        return (i2 & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall) == 0 ? i2 | 48 : i2
    }

    private fun resolveKeylineGravity(Int i) {
        if (i == 0) {
            return 8388661
        }
        return i
    }

    private fun setInsetOffsetX(View view, Int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (layoutParams.mInsetOffsetX != i) {
            ViewCompat.offsetLeftAndRight(view, i - layoutParams.mInsetOffsetX)
            layoutParams.mInsetOffsetX = i
        }
    }

    private fun setInsetOffsetY(View view, Int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (layoutParams.mInsetOffsetY != i) {
            ViewCompat.offsetTopAndBottom(view, i - layoutParams.mInsetOffsetY)
            layoutParams.mInsetOffsetY = i
        }
    }

    private fun setupForInsets() {
        if (Build.VERSION.SDK_INT < 21) {
            return
        }
        if (!ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.setOnApplyWindowInsetsListener(this, null)
            return
        }
        if (this.mApplyWindowInsetsListener == null) {
            this.mApplyWindowInsetsListener = OnApplyWindowInsetsListener() { // from class: android.support.design.widget.CoordinatorLayout.1
                @Override // android.support.v4.view.OnApplyWindowInsetsListener
                fun onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat)
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener)
        setSystemUiVisibility(1280)
    }

    Unit addPreDrawListener() {
        if (this.mIsAttachedToWindow) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = OnPreDrawListener()
            }
            getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener)
        }
        this.mNeedsPreDrawListener = true
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams is LayoutParams) && super.checkLayoutParams(layoutParams)
    }

    fun dispatchDependentViewsChanged(@NonNull View view) {
        List incomingEdges = this.mChildDag.getIncomingEdges(view)
        if (incomingEdges == null || incomingEdges.isEmpty()) {
            return
        }
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= incomingEdges.size()) {
                return
            }
            View view2 = (View) incomingEdges.get(i2)
            Behavior behavior = ((LayoutParams) view2.getLayoutParams()).getBehavior()
            if (behavior != null) {
                behavior.onDependentViewChanged(this, view2, view)
            }
            i = i2 + 1
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun doViewsOverlap(@android.support.annotation.NonNull android.view.View r7, @android.support.annotation.NonNull android.view.View r8) {
        /*
            r6 = this
            r1 = 1
            r2 = 0
            Int r0 = r7.getVisibility()
            if (r0 != 0) goto L57
            Int r0 = r8.getVisibility()
            if (r0 != 0) goto L57
            android.graphics.Rect r3 = acquireTempRect()
            android.view.ViewParent r0 = r7.getParent()
            if (r0 == r6) goto L49
            r0 = r1
        L19:
            r6.getChildRect(r7, r0, r3)
            android.graphics.Rect r4 = acquireTempRect()
            android.view.ViewParent r0 = r8.getParent()
            if (r0 == r6) goto L4b
            r0 = r1
        L27:
            r6.getChildRect(r8, r0, r4)
            Int r0 = r3.left     // Catch: java.lang.Throwable -> L4f
            Int r5 = r4.right     // Catch: java.lang.Throwable -> L4f
            if (r0 > r5) goto L4d
            Int r0 = r3.top     // Catch: java.lang.Throwable -> L4f
            Int r5 = r4.bottom     // Catch: java.lang.Throwable -> L4f
            if (r0 > r5) goto L4d
            Int r0 = r3.right     // Catch: java.lang.Throwable -> L4f
            Int r5 = r4.left     // Catch: java.lang.Throwable -> L4f
            if (r0 < r5) goto L4d
            Int r0 = r3.bottom     // Catch: java.lang.Throwable -> L4f
            Int r5 = r4.top     // Catch: java.lang.Throwable -> L4f
            if (r0 < r5) goto L4d
        L42:
            releaseTempRect(r3)
            releaseTempRect(r4)
        L48:
            return r1
        L49:
            r0 = r2
            goto L19
        L4b:
            r0 = r2
            goto L27
        L4d:
            r1 = r2
            goto L42
        L4f:
            r0 = move-exception
            releaseTempRect(r3)
            releaseTempRect(r4)
            throw r0
        L57:
            r1 = r2
            goto L48
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.doViewsOverlap(android.view.View, android.view.View):Boolean")
    }

    @Override // android.view.ViewGroup
    protected fun drawChild(Canvas canvas, View view, Long j) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (layoutParams.mBehavior != null) {
            Float scrimOpacity = layoutParams.mBehavior.getScrimOpacity(this, view)
            if (scrimOpacity > 0.0f) {
                if (this.mScrimPaint == null) {
                    this.mScrimPaint = Paint()
                }
                this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, view))
                this.mScrimPaint.setAlpha(clamp(Math.round(scrimOpacity * 255.0f), 0, 255))
                Int iSave = canvas.save()
                if (view.isOpaque()) {
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), Region.Op.DIFFERENCE)
                }
                canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), this.mScrimPaint)
                canvas.restoreToCount(iSave)
            }
        }
        return super.drawChild(canvas, view, j)
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        Array<Int> drawableState = getDrawableState()
        Boolean state = false
        Drawable drawable = this.mStatusBarBackground
        if (drawable != null && drawable.isStateful()) {
            state = drawable.setState(drawableState) | false
        }
        if (state) {
            invalidate()
        }
    }

    Unit ensurePreDrawListener() {
        Boolean z = false
        Int childCount = getChildCount()
        Int i = 0
        while (true) {
            if (i >= childCount) {
                break
            }
            if (hasDependencies(getChildAt(i))) {
                z = true
                break
            }
            i++
        }
        if (z != this.mNeedsPreDrawListener) {
            if (z) {
                addPreDrawListener()
            } else {
                removePreDrawListener()
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateDefaultLayoutParams() {
        return LayoutParams(-2, -2)
    }

    @Override // android.view.ViewGroup
    fun generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    fun generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams is LayoutParams ? LayoutParams((LayoutParams) layoutParams) : layoutParams is ViewGroup.MarginLayoutParams ? LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : LayoutParams(layoutParams)
    }

    Unit getChildRect(View view, Boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty()
        } else if (z) {
            getDescendantRect(view, rect)
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())
        }
    }

    @NonNull
    fun getDependencies(@NonNull View view) {
        List outgoingEdges = this.mChildDag.getOutgoingEdges(view)
        this.mTempDependenciesList.clear()
        if (outgoingEdges != null) {
            this.mTempDependenciesList.addAll(outgoingEdges)
        }
        return this.mTempDependenciesList
    }

    @VisibleForTesting
    final List getDependencySortedChildren() {
        prepareChildren()
        return Collections.unmodifiableList(this.mDependencySortedChildren)
    }

    @NonNull
    fun getDependents(@NonNull View view) {
        List incomingEdges = this.mChildDag.getIncomingEdges(view)
        this.mTempDependenciesList.clear()
        if (incomingEdges != null) {
            this.mTempDependenciesList.addAll(incomingEdges)
        }
        return this.mTempDependenciesList
    }

    Unit getDescendantRect(View view, Rect rect) {
        ViewGroupUtils.getDescendantRect(this, view, rect)
    }

    Unit getDesiredAnchoredChildRect(View view, Int i, Rect rect, Rect rect2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        Int measuredWidth = view.getMeasuredWidth()
        Int measuredHeight = view.getMeasuredHeight()
        getDesiredAnchoredChildRectWithoutConstraints(view, i, rect, rect2, layoutParams, measuredWidth, measuredHeight)
        constrainChildRect(layoutParams, rect2, measuredWidth, measuredHeight)
    }

    Unit getLastChildRect(View view, Rect rect) {
        rect.set(((LayoutParams) view.getLayoutParams()).getLastChildRect())
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final WindowInsetsCompat getLastWindowInsets() {
        return this.mLastInsets
    }

    @Override // android.view.ViewGroup, android.support.v4.view.NestedScrollingParent
    fun getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes()
    }

    /* JADX WARN: Multi-variable type inference failed */
    LayoutParams getResolvedLayoutParams(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (!layoutParams.mBehaviorResolved) {
            if (view is AttachedBehavior) {
                Behavior behavior = ((AttachedBehavior) view).getBehavior()
                if (behavior == null) {
                    Log.e(TAG, "Attached behavior class is null")
                }
                layoutParams.setBehavior(behavior)
                layoutParams.mBehaviorResolved = true
            } else {
                DefaultBehavior defaultBehavior = null
                for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                    defaultBehavior = (DefaultBehavior) superclass.getAnnotation(DefaultBehavior.class)
                    if (defaultBehavior != null) {
                        break
                    }
                }
                DefaultBehavior defaultBehavior2 = defaultBehavior
                if (defaultBehavior2 != null) {
                    try {
                        layoutParams.setBehavior((Behavior) defaultBehavior2.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]))
                    } catch (Exception e) {
                        Log.e(TAG, "Default behavior class " + defaultBehavior2.value().getName() + " could not be instantiated. Did you forget a default constructor?", e)
                    }
                }
                layoutParams.mBehaviorResolved = true
            }
        }
        return layoutParams
    }

    @Nullable
    fun getStatusBarBackground() {
        return this.mStatusBarBackground
    }

    @Override // android.view.View
    protected fun getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom())
    }

    @Override // android.view.View
    protected fun getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight())
    }

    fun isPointInChildBounds(@NonNull View view, Int i, Int i2) {
        Rect rectAcquireTempRect = acquireTempRect()
        getDescendantRect(view, rectAcquireTempRect)
        try {
            return rectAcquireTempRect.contains(i, i2)
        } finally {
            releaseTempRect(rectAcquireTempRect)
        }
    }

    Unit offsetChildToAnchor(View view, Int i) {
        Behavior behavior
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (layoutParams.mAnchorView != null) {
            Rect rectAcquireTempRect = acquireTempRect()
            Rect rectAcquireTempRect2 = acquireTempRect()
            Rect rectAcquireTempRect3 = acquireTempRect()
            getDescendantRect(layoutParams.mAnchorView, rectAcquireTempRect)
            getChildRect(view, false, rectAcquireTempRect2)
            Int measuredWidth = view.getMeasuredWidth()
            Int measuredHeight = view.getMeasuredHeight()
            getDesiredAnchoredChildRectWithoutConstraints(view, i, rectAcquireTempRect, rectAcquireTempRect3, layoutParams, measuredWidth, measuredHeight)
            Boolean z = (rectAcquireTempRect3.left == rectAcquireTempRect2.left && rectAcquireTempRect3.top == rectAcquireTempRect2.top) ? false : true
            constrainChildRect(layoutParams, rectAcquireTempRect3, measuredWidth, measuredHeight)
            Int i2 = rectAcquireTempRect3.left - rectAcquireTempRect2.left
            Int i3 = rectAcquireTempRect3.top - rectAcquireTempRect2.top
            if (i2 != 0) {
                ViewCompat.offsetLeftAndRight(view, i2)
            }
            if (i3 != 0) {
                ViewCompat.offsetTopAndBottom(view, i3)
            }
            if (z && (behavior = layoutParams.getBehavior()) != null) {
                behavior.onDependentViewChanged(this, view, layoutParams.mAnchorView)
            }
            releaseTempRect(rectAcquireTempRect)
            releaseTempRect(rectAcquireTempRect2)
            releaseTempRect(rectAcquireTempRect3)
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onAttachedToWindow() {
        super.onAttachedToWindow()
        resetTouchBehaviors(false)
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = OnPreDrawListener()
            }
            getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener)
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows(this)) {
            ViewCompat.requestApplyInsets(this)
        }
        this.mIsAttachedToWindow = true
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final Unit onChildViewsChanged(Int r15) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onChildViewsChanged(Int):Unit")
    }

    @Override // android.view.ViewGroup, android.view.View
    fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        resetTouchBehaviors(false)
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener)
        }
        if (this.mNestedScrollingTarget != null) {
            onStopNestedScroll(this.mNestedScrollingTarget)
        }
        this.mIsAttachedToWindow = false
    }

    @Override // android.view.View
    fun onDraw(Canvas canvas) {
        super.onDraw(canvas)
        if (!this.mDrawStatusBarBackground || this.mStatusBarBackground == null) {
            return
        }
        Int systemWindowInsetTop = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0
        if (systemWindowInsetTop > 0) {
            this.mStatusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop)
            this.mStatusBarBackground.draw(canvas)
        }
    }

    @Override // android.view.ViewGroup
    fun onInterceptTouchEvent(MotionEvent motionEvent) {
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 0) {
            resetTouchBehaviors(true)
        }
        Boolean zPerformIntercept = performIntercept(motionEvent, 0)
        if (actionMasked == 1 || actionMasked == 3) {
            resetTouchBehaviors(true)
        }
        return zPerformIntercept
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Behavior behavior
        Int layoutDirection = ViewCompat.getLayoutDirection(this)
        Int size = this.mDependencySortedChildren.size()
        for (Int i5 = 0; i5 < size; i5++) {
            View view = (View) this.mDependencySortedChildren.get(i5)
            if (view.getVisibility() != 8 && ((behavior = ((LayoutParams) view.getLayoutParams()).getBehavior()) == null || !behavior.onLayoutChild(this, view, layoutDirection))) {
                onLayoutChild(view, layoutDirection)
            }
        }
    }

    fun onLayoutChild(@NonNull View view, Int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (layoutParams.checkAnchorChanged()) {
            throw IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.")
        }
        if (layoutParams.mAnchorView != null) {
            layoutChildWithAnchor(view, layoutParams.mAnchorView, i)
        } else if (layoutParams.keyline >= 0) {
            layoutChildWithKeyline(view, layoutParams.keyline, i)
        } else {
            layoutChild(view, i)
        }
    }

    @Override // android.view.View
    protected fun onMeasure(Int i, Int i2) {
        Int iCombineMeasuredStates
        Int iMax
        Int i3
        Int iMakeMeasureSpec
        Int iMakeMeasureSpec2
        prepareChildren()
        ensurePreDrawListener()
        Int paddingLeft = getPaddingLeft()
        Int paddingTop = getPaddingTop()
        Int paddingRight = getPaddingRight()
        Int paddingBottom = getPaddingBottom()
        Int layoutDirection = ViewCompat.getLayoutDirection(this)
        Boolean z = layoutDirection == 1
        Int mode = View.MeasureSpec.getMode(i)
        Int size = View.MeasureSpec.getSize(i)
        Int mode2 = View.MeasureSpec.getMode(i2)
        Int size2 = View.MeasureSpec.getSize(i2)
        Int i4 = paddingLeft + paddingRight
        Int i5 = paddingTop + paddingBottom
        Int suggestedMinimumWidth = getSuggestedMinimumWidth()
        Int suggestedMinimumHeight = getSuggestedMinimumHeight()
        Int i6 = 0
        Boolean z2 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this)
        Int size3 = this.mDependencySortedChildren.size()
        Int i7 = 0
        while (i7 < size3) {
            View view = (View) this.mDependencySortedChildren.get(i7)
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
                Int iMax2 = 0
                if (layoutParams.keyline >= 0 && mode != 0) {
                    Int keyline = getKeyline(layoutParams.keyline)
                    Int absoluteGravity = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(layoutParams.gravity), layoutDirection) & 7
                    if ((absoluteGravity == 3 && !z) || (absoluteGravity == 5 && z)) {
                        iMax2 = Math.max(0, (size - paddingRight) - keyline)
                    } else if ((absoluteGravity == 5 && !z) || (absoluteGravity == 3 && z)) {
                        iMax2 = Math.max(0, keyline - paddingLeft)
                    }
                }
                if (!z2 || ViewCompat.getFitsSystemWindows(view)) {
                    iMakeMeasureSpec = i2
                    iMakeMeasureSpec2 = i
                } else {
                    Int systemWindowInsetLeft = this.mLastInsets.getSystemWindowInsetLeft() + this.mLastInsets.getSystemWindowInsetRight()
                    Int systemWindowInsetTop = this.mLastInsets.getSystemWindowInsetTop() + this.mLastInsets.getSystemWindowInsetBottom()
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size - systemWindowInsetLeft, mode)
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2 - systemWindowInsetTop, mode2)
                }
                Behavior behavior = layoutParams.getBehavior()
                if (behavior == null || !behavior.onMeasureChild(this, view, iMakeMeasureSpec2, iMax2, iMakeMeasureSpec, 0)) {
                    onMeasureChild(view, iMakeMeasureSpec2, iMax2, iMakeMeasureSpec, 0)
                }
                Int iMax3 = Math.max(suggestedMinimumWidth, view.getMeasuredWidth() + i4 + layoutParams.leftMargin + layoutParams.rightMargin)
                iMax = Math.max(suggestedMinimumHeight, view.getMeasuredHeight() + i5 + layoutParams.topMargin + layoutParams.bottomMargin)
                iCombineMeasuredStates = View.combineMeasuredStates(i6, view.getMeasuredState())
                i3 = iMax3
            } else {
                iCombineMeasuredStates = i6
                iMax = suggestedMinimumHeight
                i3 = suggestedMinimumWidth
            }
            i7++
            i6 = iCombineMeasuredStates
            suggestedMinimumHeight = iMax
            suggestedMinimumWidth = i3
        }
        setMeasuredDimension(View.resolveSizeAndState(suggestedMinimumWidth, i, (-16777216) & i6), View.resolveSizeAndState(suggestedMinimumHeight, i2, i6 << 16))
    }

    fun onMeasureChild(View view, Int i, Int i2, Int i3, Int i4) {
        measureChildWithMargins(view, i, i2, i3, i4)
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003d  */
    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onNestedFling(android.view.View r12, Float r13, Float r14, Boolean r15) {
        /*
            r11 = this
            r9 = 0
            Int r10 = r11.getChildCount()
            r8 = r9
            r7 = r9
        L7:
            if (r8 >= r10) goto L36
            android.view.View r2 = r11.getChildAt(r8)
            Int r0 = r2.getVisibility()
            r1 = 8
            if (r0 == r1) goto L3d
            android.view.ViewGroup$LayoutParams r0 = r2.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r0 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r0
            Boolean r1 = r0.isNestedScrollAccepted(r9)
            if (r1 == 0) goto L3d
            android.support.design.widget.CoordinatorLayout$Behavior r0 = r0.getBehavior()
            if (r0 == 0) goto L3d
            r1 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            Boolean r0 = r0.onNestedFling(r1, r2, r3, r4, r5, r6)
            r0 = r0 | r7
        L31:
            Int r1 = r8 + 1
            r8 = r1
            r7 = r0
            goto L7
        L36:
            if (r7 == 0) goto L3c
            r0 = 1
            r11.onChildViewsChanged(r0)
        L3c:
            return r7
        L3d:
            r0 = r7
            goto L31
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onNestedFling(android.view.View, Float, Float, Boolean):Boolean")
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onNestedPreFling(android.view.View r11, Float r12, Float r13) {
        /*
            r10 = this
            r8 = 0
            Int r9 = r10.getChildCount()
            r7 = r8
            r6 = r8
        L7:
            if (r7 >= r9) goto L35
            android.view.View r2 = r10.getChildAt(r7)
            Int r0 = r2.getVisibility()
            r1 = 8
            if (r0 == r1) goto L36
            android.view.ViewGroup$LayoutParams r0 = r2.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r0 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r0
            Boolean r1 = r0.isNestedScrollAccepted(r8)
            if (r1 == 0) goto L36
            android.support.design.widget.CoordinatorLayout$Behavior r0 = r0.getBehavior()
            if (r0 == 0) goto L36
            r1 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            Boolean r0 = r0.onNestedPreFling(r1, r2, r3, r4, r5)
            r0 = r0 | r6
        L30:
            Int r1 = r7 + 1
            r7 = r1
            r6 = r0
            goto L7
        L35:
            return r6
        L36:
            r0 = r6
            goto L30
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onNestedPreFling(android.view.View, Float, Float):Boolean")
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedPreScroll(View view, Int i, Int i2, Array<Int> iArr) {
        onNestedPreScroll(view, i, i2, iArr, 0)
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0085  */
    @Override // android.support.v4.view.NestedScrollingParent2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onNestedPreScroll(android.view.View r15, Int r16, Int r17, Array<Int> r18, Int r19) {
        /*
            r14 = this
            r10 = 0
            r9 = 0
            r2 = 0
            Int r12 = r14.getChildCount()
            r1 = 0
            r11 = r1
        L9:
            if (r11 >= r12) goto L78
            android.view.View r3 = r14.getChildAt(r11)
            Int r1 = r3.getVisibility()
            r4 = 8
            if (r1 == r4) goto L85
            android.view.ViewGroup$LayoutParams r1 = r3.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r1 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r1
            r0 = r19
            Boolean r4 = r1.isNestedScrollAccepted(r0)
            if (r4 == 0) goto L85
            android.support.design.widget.CoordinatorLayout$Behavior r1 = r1.getBehavior()
            if (r1 == 0) goto L85
            Array<Int> r2 = r14.mTempIntPair
            r4 = 0
            Array<Int> r5 = r14.mTempIntPair
            r6 = 1
            r7 = 0
            r5[r6] = r7
            r2[r4] = r7
            Array<Int> r7 = r14.mTempIntPair
            r2 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r8 = r19
            r1.onNestedPreScroll(r2, r3, r4, r5, r6, r7, r8)
            if (r16 <= 0) goto L64
            Array<Int> r1 = r14.mTempIntPair
            r2 = 0
            r1 = r1[r2]
            Int r3 = java.lang.Math.max(r10, r1)
        L4e:
            if (r17 <= 0) goto L6e
            Array<Int> r1 = r14.mTempIntPair
            r2 = 1
            r1 = r1[r2]
            Int r1 = java.lang.Math.max(r9, r1)
        L59:
            r2 = 1
            r13 = r2
            r2 = r1
            r1 = r13
        L5d:
            Int r4 = r11 + 1
            r11 = r4
            r9 = r2
            r10 = r3
            r2 = r1
            goto L9
        L64:
            Array<Int> r1 = r14.mTempIntPair
            r2 = 0
            r1 = r1[r2]
            Int r3 = java.lang.Math.min(r10, r1)
            goto L4e
        L6e:
            Array<Int> r1 = r14.mTempIntPair
            r2 = 1
            r1 = r1[r2]
            Int r1 = java.lang.Math.min(r9, r1)
            goto L59
        L78:
            r1 = 0
            r18[r1] = r10
            r1 = 1
            r18[r1] = r9
            if (r2 == 0) goto L84
            r1 = 1
            r14.onChildViewsChanged(r1)
        L84:
            return
        L85:
            r1 = r2
            r3 = r10
            r2 = r9
            goto L5d
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onNestedPreScroll(android.view.View, Int, Int, Array<Int>, Int):Unit")
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScroll(View view, Int i, Int i2, Int i3, Int i4) {
        onNestedScroll(view, i, i2, i3, i4, 0)
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    @Override // android.support.v4.view.NestedScrollingParent2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onNestedScroll(android.view.View r13, Int r14, Int r15, Int r16, Int r17, Int r18) {
        /*
            r12 = this
            Int r11 = r12.getChildCount()
            r2 = 0
            r1 = 0
            r10 = r1
        L7:
            if (r10 >= r11) goto L3c
            android.view.View r3 = r12.getChildAt(r10)
            Int r1 = r3.getVisibility()
            r4 = 8
            if (r1 == r4) goto L43
            android.view.ViewGroup$LayoutParams r1 = r3.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r1 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r1
            r0 = r18
            Boolean r4 = r1.isNestedScrollAccepted(r0)
            if (r4 == 0) goto L43
            android.support.design.widget.CoordinatorLayout$Behavior r1 = r1.getBehavior()
            if (r1 == 0) goto L43
            r2 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r7 = r16
            r8 = r17
            r9 = r18
            r1.onNestedScroll(r2, r3, r4, r5, r6, r7, r8, r9)
            r1 = 1
        L37:
            Int r2 = r10 + 1
            r10 = r2
            r2 = r1
            goto L7
        L3c:
            if (r2 == 0) goto L42
            r1 = 1
            r12.onChildViewsChanged(r1)
        L42:
            return
        L43:
            r1 = r2
            goto L37
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onNestedScroll(android.view.View, Int, Int, Int, Int, Int):Unit")
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onNestedScrollAccepted(View view, View view2, Int i) {
        onNestedScrollAccepted(view, view2, i, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onNestedScrollAccepted(View view, View view2, Int i, Int i2) {
        Behavior behavior
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i, i2)
        this.mNestedScrollingTarget = view2
        Int childCount = getChildCount()
        for (Int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3)
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
            if (layoutParams.isNestedScrollAccepted(i2) && (behavior = layoutParams.getBehavior()) != null) {
                behavior.onNestedScrollAccepted(this, childAt, view, view2, i, i2)
            }
        }
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        SparseArray sparseArray = savedState.behaviorStates
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            Int id = childAt.getId()
            Behavior behavior = getResolvedLayoutParams(childAt).getBehavior()
            if (id != -1 && behavior != null && (parcelable2 = (Parcelable) sparseArray.get(id)) != null) {
                behavior.onRestoreInstanceState(this, childAt, parcelable2)
            }
        }
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState
        SavedState savedState = SavedState(super.onSaveInstanceState())
        SparseArray sparseArray = SparseArray()
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            Int id = childAt.getId()
            Behavior behavior = ((LayoutParams) childAt.getLayoutParams()).getBehavior()
            if (id != -1 && behavior != null && (parcelableOnSaveInstanceState = behavior.onSaveInstanceState(this, childAt)) != null) {
                sparseArray.append(id, parcelableOnSaveInstanceState)
            }
        }
        savedState.behaviorStates = sparseArray
        return savedState
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStartNestedScroll(View view, View view2, Int i) {
        return onStartNestedScroll(view, view2, i, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onStartNestedScroll(View view, View view2, Int i, Int i2) {
        Boolean z
        Boolean z2 = false
        Int childCount = getChildCount()
        Int i3 = 0
        while (i3 < childCount) {
            View childAt = getChildAt(i3)
            if (childAt.getVisibility() == 8) {
                z = z2
            } else {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                Behavior behavior = layoutParams.getBehavior()
                if (behavior != null) {
                    Boolean zOnStartNestedScroll = behavior.onStartNestedScroll(this, childAt, view, view2, i, i2)
                    z = z2 | zOnStartNestedScroll
                    layoutParams.setNestedScrollAccepted(i2, zOnStartNestedScroll)
                } else {
                    layoutParams.setNestedScrollAccepted(i2, false)
                    z = z2
                }
            }
            i3++
            z2 = z
        }
        return z2
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.v4.view.NestedScrollingParent
    fun onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0)
    }

    @Override // android.support.v4.view.NestedScrollingParent2
    fun onStopNestedScroll(View view, Int i) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, i)
        Int childCount = getChildCount()
        for (Int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2)
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
            if (layoutParams.isNestedScrollAccepted(i)) {
                Behavior behavior = layoutParams.getBehavior()
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, childAt, view, i)
                }
                layoutParams.resetNestedScroll(i)
                layoutParams.resetChangedAfterNestedScroll()
            }
        }
        this.mNestedScrollingTarget = null
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0040  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onTouchEvent(android.view.MotionEvent r12) {
        /*
            r11 = this
            r4 = 3
            r10 = 1
            r5 = 0
            r7 = 0
            r2 = 0
            Int r9 = r12.getActionMasked()
            android.view.View r0 = r11.mBehaviorTouchView
            if (r0 != 0) goto L57
            Boolean r0 = r11.performIntercept(r12, r10)
            if (r0 == 0) goto L54
            r1 = r0
        L14:
            android.view.View r0 = r11.mBehaviorTouchView
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.support.design.widget.CoordinatorLayout$LayoutParams r0 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r0
            android.support.design.widget.CoordinatorLayout$Behavior r0 = r0.getBehavior()
            if (r0 == 0) goto L52
            android.view.View r3 = r11.mBehaviorTouchView
            Boolean r0 = r0.onTouchEvent(r11, r3, r12)
            r8 = r0
        L29:
            android.view.View r0 = r11.mBehaviorTouchView
            if (r0 != 0) goto L40
            Boolean r0 = super.onTouchEvent(r12)
            r8 = r8 | r0
            r0 = r2
        L33:
            if (r0 == 0) goto L38
            r0.recycle()
        L38:
            if (r9 == r10) goto L3c
            if (r9 != r4) goto L3f
        L3c:
            r11.resetTouchBehaviors(r7)
        L3f:
            return r8
        L40:
            if (r1 == 0) goto L50
            Long r0 = android.os.SystemClock.uptimeMillis()
            r2 = r0
            r6 = r5
            android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r0, r2, r4, r5, r6, r7)
            super.onTouchEvent(r0)
            goto L33
        L50:
            r0 = r2
            goto L33
        L52:
            r8 = r7
            goto L29
        L54:
            r1 = r0
            r8 = r7
            goto L29
        L57:
            r1 = r7
            goto L14
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.design.widget.CoordinatorLayout.onTouchEvent(android.view.MotionEvent):Boolean")
    }

    Unit recordLastChildRect(View view, Rect rect) {
        ((LayoutParams) view.getLayoutParams()).setLastChildRect(rect)
    }

    Unit removePreDrawListener() {
        if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener)
        }
        this.mNeedsPreDrawListener = false
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestChildRectangleOnScreen(View view, Rect rect, Boolean z) {
        Behavior behavior = ((LayoutParams) view.getLayoutParams()).getBehavior()
        if (behavior == null || !behavior.onRequestChildRectangleOnScreen(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z)
        }
        return true
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestDisallowInterceptTouchEvent(Boolean z) {
        super.requestDisallowInterceptTouchEvent(z)
        if (!z || this.mDisallowInterceptReset) {
            return
        }
        resetTouchBehaviors(false)
        this.mDisallowInterceptReset = true
    }

    @Override // android.view.View
    fun setFitsSystemWindows(Boolean z) {
        super.setFitsSystemWindows(z)
        setupForInsets()
    }

    @Override // android.view.ViewGroup
    fun setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener
    }

    fun setStatusBarBackground(@Nullable Drawable drawable) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.mStatusBarBackground != drawable) {
            if (this.mStatusBarBackground != null) {
                this.mStatusBarBackground.setCallback(null)
            }
            this.mStatusBarBackground = drawable != null ? drawable.mutate() : null
            if (this.mStatusBarBackground != null) {
                if (this.mStatusBarBackground.isStateful()) {
                    this.mStatusBarBackground.setState(getDrawableState())
                }
                DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection(this))
                this.mStatusBarBackground.setVisible(getVisibility() == 0, false)
                this.mStatusBarBackground.setCallback(this)
            }
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun setStatusBarBackgroundColor(@ColorInt Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setStatusBarBackground(ColorDrawable(i))
    }

    fun setStatusBarBackgroundResource(@DrawableRes Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        setStatusBarBackground(i != 0 ? ContextCompat.getDrawable(getContext(), i) : null)
    }

    @Override // android.view.View
    fun setVisibility(Int i) {
        super.setVisibility(i)
        Boolean z = i == 0
        if (this.mStatusBarBackground == null || this.mStatusBarBackground.isVisible() == z) {
            return
        }
        this.mStatusBarBackground.setVisible(z, false)
    }

    final WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        if (ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat)) {
            return windowInsetsCompat
        }
        this.mLastInsets = windowInsetsCompat
        this.mDrawStatusBarBackground = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0
        setWillNotDraw(!this.mDrawStatusBarBackground && getBackground() == null)
        WindowInsetsCompat windowInsetsCompatDispatchApplyWindowInsetsToBehaviors = dispatchApplyWindowInsetsToBehaviors(windowInsetsCompat)
        requestLayout()
        return windowInsetsCompatDispatchApplyWindowInsetsToBehaviors
    }

    @Override // android.view.View
    protected fun verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mStatusBarBackground
    }
}
