package android.support.v4.widget

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.view.AbsSavedState
import android.support.v4.view.AccessibilityDelegateCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.accessibility.AccessibilityEvent
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.List

class DrawerLayout extends ViewGroup {
    private static val ALLOW_EDGE_LOCK = false
    static final Boolean CAN_HIDE_DESCENDANTS
    private static val CHILDREN_DISALLOW_INTERCEPT = true
    private static val DEFAULT_SCRIM_COLOR = -1728053248
    private static val DRAWER_ELEVATION = 10
    static final Array<Int> LAYOUT_ATTRS
    public static val LOCK_MODE_LOCKED_CLOSED = 1
    public static val LOCK_MODE_LOCKED_OPEN = 2
    public static val LOCK_MODE_UNDEFINED = 3
    public static val LOCK_MODE_UNLOCKED = 0
    private static val MIN_DRAWER_MARGIN = 64
    private static val MIN_FLING_VELOCITY = 400
    private static val PEEK_DELAY = 160
    private static final Boolean SET_DRAWER_SHADOW_FROM_ELEVATION
    public static val STATE_DRAGGING = 1
    public static val STATE_IDLE = 0
    public static val STATE_SETTLING = 2
    private static val TAG = "DrawerLayout"
    private static final Array<Int> THEME_ATTRS
    private static val TOUCH_SLOP_SENSITIVITY = 1.0f
    private final ChildAccessibilityDelegate mChildAccessibilityDelegate
    private Rect mChildHitRect
    private Matrix mChildInvertedMatrix
    private Boolean mChildrenCanceledTouch
    private Boolean mDisallowInterceptRequested
    private Boolean mDrawStatusBarBackground
    private Float mDrawerElevation
    private Int mDrawerState
    private Boolean mFirstLayout
    private Boolean mInLayout
    private Float mInitialMotionX
    private Float mInitialMotionY
    private Object mLastInsets
    private final ViewDragCallback mLeftCallback
    private final ViewDragHelper mLeftDragger

    @Nullable
    private DrawerListener mListener
    private List mListeners
    private Int mLockModeEnd
    private Int mLockModeLeft
    private Int mLockModeRight
    private Int mLockModeStart
    private Int mMinDrawerMargin
    private final ArrayList mNonDrawerViews
    private final ViewDragCallback mRightCallback
    private final ViewDragHelper mRightDragger
    private Int mScrimColor
    private Float mScrimOpacity
    private Paint mScrimPaint
    private Drawable mShadowEnd
    private Drawable mShadowLeft
    private Drawable mShadowLeftResolved
    private Drawable mShadowRight
    private Drawable mShadowRightResolved
    private Drawable mShadowStart
    private Drawable mStatusBarBackground
    private CharSequence mTitleLeft
    private CharSequence mTitleRight

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private val mTmpRect = Rect()

        AccessibilityDelegate() {
        }

        private fun addChildrenForAccessibility(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            Int childCount = viewGroup.getChildCount()
            for (Int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i)
                if (DrawerLayout.includeChildForAccessibility(childAt)) {
                    accessibilityNodeInfoCompat.addChild(childAt)
                }
            }
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
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent)
            }
            List<CharSequence> text = accessibilityEvent.getText()
            View viewFindVisibleDrawer = DrawerLayout.this.findVisibleDrawer()
            if (viewFindVisibleDrawer != null) {
                CharSequence drawerTitle = DrawerLayout.this.getDrawerTitle(DrawerLayout.this.getDrawerViewAbsoluteGravity(viewFindVisibleDrawer))
                if (drawerTitle != null) {
                    text.add(drawerTitle)
                }
            }
            return DrawerLayout.CHILDREN_DISALLOW_INTERCEPT
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent)
            accessibilityEvent.setClassName(DrawerLayout.class.getName())
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat)
            } else {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat)
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompatObtain)
                accessibilityNodeInfoCompat.setSource(view)
                Object parentForAccessibility = ViewCompat.getParentForAccessibility(view)
                if (parentForAccessibility is View) {
                    accessibilityNodeInfoCompat.setParent((View) parentForAccessibility)
                }
                copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompatObtain)
                accessibilityNodeInfoCompatObtain.recycle()
                addChildrenForAccessibility(accessibilityNodeInfoCompat, (ViewGroup) view)
            }
            accessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName())
            accessibilityNodeInfoCompat.setFocusable(false)
            accessibilityNodeInfoCompat.setFocused(false)
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS)
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS)
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        fun onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.CAN_HIDE_DESCENDANTS || DrawerLayout.includeChildForAccessibility(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent)
            }
            return false
        }
    }

    final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        ChildAccessibilityDelegate() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public final Unit onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat)
            if (DrawerLayout.includeChildForAccessibility(view)) {
                return
            }
            accessibilityNodeInfoCompat.setParent(null)
        }
    }

    public interface DrawerListener {
        Unit onDrawerClosed(@NonNull View view)

        Unit onDrawerOpened(@NonNull View view)

        Unit onDrawerSlide(@NonNull View view, Float f)

        Unit onDrawerStateChanged(Int i)
    }

    class LayoutParams extends ViewGroup.MarginLayoutParams {
        private static val FLAG_IS_CLOSING = 4
        private static val FLAG_IS_OPENED = 1
        private static val FLAG_IS_OPENING = 2
        public Int gravity
        Boolean isPeeking
        Float onScreen
        Int openState

        constructor(Int i, Int i2) {
            super(i, i2)
            this.gravity = 0
        }

        constructor(Int i, Int i2, Int i3) {
            this(i, i2)
            this.gravity = i3
        }

        constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet)
            this.gravity = 0
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.LAYOUT_ATTRS)
            this.gravity = typedArrayObtainStyledAttributes.getInt(0, 0)
            typedArrayObtainStyledAttributes.recycle()
        }

        constructor(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams)
            this.gravity = 0
            this.gravity = layoutParams.gravity
        }

        constructor(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
            this.gravity = 0
        }

        constructor(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams)
            this.gravity = 0
        }
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v4.widget.DrawerLayout.SavedState.1
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
        Int lockModeEnd
        Int lockModeLeft
        Int lockModeRight
        Int lockModeStart
        Int openDrawerGravity

        constructor(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader)
            this.openDrawerGravity = 0
            this.openDrawerGravity = parcel.readInt()
            this.lockModeLeft = parcel.readInt()
            this.lockModeRight = parcel.readInt()
            this.lockModeStart = parcel.readInt()
            this.lockModeEnd = parcel.readInt()
        }

        constructor(@NonNull Parcelable parcelable) {
            super(parcelable)
            this.openDrawerGravity = 0
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeInt(this.openDrawerGravity)
            parcel.writeInt(this.lockModeLeft)
            parcel.writeInt(this.lockModeRight)
            parcel.writeInt(this.lockModeStart)
            parcel.writeInt(this.lockModeEnd)
        }
    }

    abstract class SimpleDrawerListener implements DrawerListener {
        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        fun onDrawerClosed(View view) {
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        fun onDrawerOpened(View view) {
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        fun onDrawerSlide(View view, Float f) {
        }

        @Override // android.support.v4.widget.DrawerLayout.DrawerListener
        fun onDrawerStateChanged(Int i) {
        }
    }

    class ViewDragCallback extends ViewDragHelper.Callback {
        private final Int mAbsGravity
        private ViewDragHelper mDragger
        private val mPeekRunnable = Runnable() { // from class: android.support.v4.widget.DrawerLayout.ViewDragCallback.1
            @Override // java.lang.Runnable
            fun run() {
                ViewDragCallback.this.peekDrawer()
            }
        }

        ViewDragCallback(Int i) {
            this.mAbsGravity = i
        }

        private fun closeOtherDrawer() {
            View viewFindDrawerWithGravity = DrawerLayout.this.findDrawerWithGravity(this.mAbsGravity == 3 ? 5 : 3)
            if (viewFindDrawerWithGravity != null) {
                DrawerLayout.this.closeDrawer(viewFindDrawerWithGravity)
            }
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun clampViewPositionHorizontal(View view, Int i, Int i2) {
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i, 0))
            }
            Int width = DrawerLayout.this.getWidth()
            return Math.max(width - view.getWidth(), Math.min(i, width))
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun clampViewPositionVertical(View view, Int i, Int i2) {
            return view.getTop()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun getViewHorizontalDragRange(View view) {
            if (DrawerLayout.this.isDrawerView(view)) {
                return view.getWidth()
            }
            return 0
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onEdgeDragStarted(Int i, Int i2) {
            View viewFindDrawerWithGravity = (i & 1) == 1 ? DrawerLayout.this.findDrawerWithGravity(3) : DrawerLayout.this.findDrawerWithGravity(5)
            if (viewFindDrawerWithGravity == null || DrawerLayout.this.getDrawerLockMode(viewFindDrawerWithGravity) != 0) {
                return
            }
            this.mDragger.captureChildView(viewFindDrawerWithGravity, i2)
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onEdgeLock(Int i) {
            return false
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onEdgeTouched(Int i, Int i2) {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L)
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewCaptured(View view, Int i) {
            ((LayoutParams) view.getLayoutParams()).isPeeking = false
            closeOtherDrawer()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewDragStateChanged(Int i) {
            DrawerLayout.this.updateDrawerState(this.mAbsGravity, i, this.mDragger.getCapturedView())
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewPositionChanged(View view, Int i, Int i2, Int i3, Int i4) {
            Int width = view.getWidth()
            Float width2 = DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3) ? (width + i) / width : (DrawerLayout.this.getWidth() - i) / width
            DrawerLayout.this.setDrawerViewOffset(view, width2)
            view.setVisibility(width2 == 0.0f ? 4 : 0)
            DrawerLayout.this.invalidate()
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun onViewReleased(View view, Float f, Float f2) {
            Int width
            Float drawerViewOffset = DrawerLayout.this.getDrawerViewOffset(view)
            Int width2 = view.getWidth()
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                width = (f > 0.0f || (f == 0.0f && drawerViewOffset > 0.5f)) ? 0 : -width2
            } else {
                width = DrawerLayout.this.getWidth()
                if (f < 0.0f || (f == 0.0f && drawerViewOffset > 0.5f)) {
                    width -= width2
                }
            }
            this.mDragger.settleCapturedViewAt(width, view.getTop())
            DrawerLayout.this.invalidate()
        }

        Unit peekDrawer() {
            View view
            Int i
            Int edgeSize = this.mDragger.getEdgeSize()
            Boolean z = this.mAbsGravity == 3
            if (z) {
                View viewFindDrawerWithGravity = DrawerLayout.this.findDrawerWithGravity(3)
                Int i2 = (viewFindDrawerWithGravity != null ? -viewFindDrawerWithGravity.getWidth() : 0) + edgeSize
                view = viewFindDrawerWithGravity
                i = i2
            } else {
                View viewFindDrawerWithGravity2 = DrawerLayout.this.findDrawerWithGravity(5)
                Int width = DrawerLayout.this.getWidth() - edgeSize
                view = viewFindDrawerWithGravity2
                i = width
            }
            if (view != null) {
                if (((!z || view.getLeft() >= i) && (z || view.getLeft() <= i)) || DrawerLayout.this.getDrawerLockMode(view) != 0) {
                    return
                }
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
                this.mDragger.smoothSlideViewTo(view, i, view.getTop())
                layoutParams.isPeeking = DrawerLayout.CHILDREN_DISALLOW_INTERCEPT
                DrawerLayout.this.invalidate()
                closeOtherDrawer()
                DrawerLayout.this.cancelChildViewTouch()
            }
        }

        fun removeCallbacks() {
            DrawerLayout.this.removeCallbacks(this.mPeekRunnable)
        }

        fun setDragger(ViewDragHelper viewDragHelper) {
            this.mDragger = viewDragHelper
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        fun tryCaptureView(View view, Int i) {
            if (DrawerLayout.this.isDrawerView(view) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                return DrawerLayout.CHILDREN_DISALLOW_INTERCEPT
            }
            return false
        }
    }

    static {
        Boolean z = CHILDREN_DISALLOW_INTERCEPT
        THEME_ATTRS = new Array<Int>{R.attr.colorPrimaryDark}
        LAYOUT_ATTRS = new Array<Int>{R.attr.layout_gravity}
        CAN_HIDE_DESCENDANTS = Build.VERSION.SDK_INT >= 19
        if (Build.VERSION.SDK_INT < 21) {
            z = false
        }
        SET_DRAWER_SHADOW_FROM_ELEVATION = z
    }

    constructor(@NonNull Context context) {
        this(context, null)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(@NonNull Context context, @Nullable AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mChildAccessibilityDelegate = ChildAccessibilityDelegate()
        this.mScrimColor = DEFAULT_SCRIM_COLOR
        this.mScrimPaint = Paint()
        this.mFirstLayout = CHILDREN_DISALLOW_INTERCEPT
        this.mLockModeLeft = 3
        this.mLockModeRight = 3
        this.mLockModeStart = 3
        this.mLockModeEnd = 3
        this.mShadowStart = null
        this.mShadowEnd = null
        this.mShadowLeft = null
        this.mShadowRight = null
        setDescendantFocusability(262144)
        Float f = getResources().getDisplayMetrics().density
        this.mMinDrawerMargin = (Int) ((64.0f * f) + 0.5f)
        Float f2 = 400.0f * f
        this.mLeftCallback = ViewDragCallback(3)
        this.mRightCallback = ViewDragCallback(5)
        this.mLeftDragger = ViewDragHelper.create(this, TOUCH_SLOP_SENSITIVITY, this.mLeftCallback)
        this.mLeftDragger.setEdgeTrackingEnabled(1)
        this.mLeftDragger.setMinVelocity(f2)
        this.mLeftCallback.setDragger(this.mLeftDragger)
        this.mRightDragger = ViewDragHelper.create(this, TOUCH_SLOP_SENSITIVITY, this.mRightCallback)
        this.mRightDragger.setEdgeTrackingEnabled(2)
        this.mRightDragger.setMinVelocity(f2)
        this.mRightCallback.setDragger(this.mRightDragger)
        setFocusableInTouchMode(CHILDREN_DISALLOW_INTERCEPT)
        ViewCompat.setImportantForAccessibility(this, 1)
        ViewCompat.setAccessibilityDelegate(this, AccessibilityDelegate())
        setMotionEventSplittingEnabled(false)
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: android.support.v4.widget.DrawerLayout.1
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    fun onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        ((DrawerLayout) view).setChildInsets(windowInsets, windowInsets.getSystemWindowInsetTop() > 0 ? DrawerLayout.CHILDREN_DISALLOW_INTERCEPT : false)
                        return windowInsets.consumeSystemWindowInsets()
                    }
                })
                setSystemUiVisibility(1280)
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(THEME_ATTRS)
                try {
                    this.mStatusBarBackground = typedArrayObtainStyledAttributes.getDrawable(0)
                } finally {
                    typedArrayObtainStyledAttributes.recycle()
                }
            } else {
                this.mStatusBarBackground = null
            }
        }
        this.mDrawerElevation = f * 10.0f
        this.mNonDrawerViews = ArrayList()
    }

    private fun dispatchTransformedGenericPointerEvent(MotionEvent motionEvent, View view) {
        if (!view.getMatrix().isIdentity()) {
            MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view)
            Boolean zDispatchGenericMotionEvent = view.dispatchGenericMotionEvent(transformedMotionEvent)
            transformedMotionEvent.recycle()
            return zDispatchGenericMotionEvent
        }
        Float scrollX = getScrollX() - view.getLeft()
        Float scrollY = getScrollY() - view.getTop()
        motionEvent.offsetLocation(scrollX, scrollY)
        Boolean zDispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent)
        motionEvent.offsetLocation(-scrollX, -scrollY)
        return zDispatchGenericMotionEvent2
    }

    private fun getTransformedMotionEvent(MotionEvent motionEvent, View view) {
        Float scrollX = getScrollX() - view.getLeft()
        Float scrollY = getScrollY() - view.getTop()
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent)
        motionEventObtain.offsetLocation(scrollX, scrollY)
        Matrix matrix = view.getMatrix()
        if (!matrix.isIdentity()) {
            if (this.mChildInvertedMatrix == null) {
                this.mChildInvertedMatrix = Matrix()
            }
            matrix.invert(this.mChildInvertedMatrix)
            motionEventObtain.transform(this.mChildInvertedMatrix)
        }
        return motionEventObtain
    }

    static String gravityToString(Int i) {
        return (i & 3) == 3 ? "LEFT" : (i & 5) == 5 ? "RIGHT" : Integer.toHexString(i)
    }

    private fun hasOpaqueBackground(View view) {
        Drawable background = view.getBackground()
        if (background == null || background.getOpacity() != -1) {
            return false
        }
        return CHILDREN_DISALLOW_INTERCEPT
    }

    private fun hasPeekingDrawer() {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).isPeeking) {
                return CHILDREN_DISALLOW_INTERCEPT
            }
        }
        return false
    }

    private fun hasVisibleDrawer() {
        if (findVisibleDrawer() != null) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    static Boolean includeChildForAccessibility(View view) {
        if (ViewCompat.getImportantForAccessibility(view) == 4 || ViewCompat.getImportantForAccessibility(view) == 2) {
            return false
        }
        return CHILDREN_DISALLOW_INTERCEPT
    }

    private fun isInBoundsOfChild(Float f, Float f2, View view) {
        if (this.mChildHitRect == null) {
            this.mChildHitRect = Rect()
        }
        view.getHitRect(this.mChildHitRect)
        return this.mChildHitRect.contains((Int) f, (Int) f2)
    }

    private fun mirror(Drawable drawable, Int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (drawable == null || !DrawableCompat.isAutoMirrored(drawable)) {
            return false
        }
        DrawableCompat.setLayoutDirection(drawable, i)
        return CHILDREN_DISALLOW_INTERCEPT
    }

    private fun resolveLeftShadow() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Int layoutDirection = ViewCompat.getLayoutDirection(this)
        if (layoutDirection == 0) {
            if (this.mShadowStart != null) {
                mirror(this.mShadowStart, layoutDirection)
                return this.mShadowStart
            }
        } else if (this.mShadowEnd != null) {
            mirror(this.mShadowEnd, layoutDirection)
            return this.mShadowEnd
        }
        return this.mShadowLeft
    }

    private fun resolveRightShadow() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Int layoutDirection = ViewCompat.getLayoutDirection(this)
        if (layoutDirection == 0) {
            if (this.mShadowEnd != null) {
                mirror(this.mShadowEnd, layoutDirection)
                return this.mShadowEnd
            }
        } else if (this.mShadowStart != null) {
            mirror(this.mShadowStart, layoutDirection)
            return this.mShadowStart
        }
        return this.mShadowRight
    }

    private fun resolveShadowDrawables() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return
        }
        this.mShadowLeftResolved = resolveLeftShadow()
        this.mShadowRightResolved = resolveRightShadow()
    }

    private fun updateChildrenImportantForAccessibility(View view, Boolean z) {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if ((z || isDrawerView(childAt)) && !(z && childAt == view)) {
                ViewCompat.setImportantForAccessibility(childAt, 4)
            } else {
                ViewCompat.setImportantForAccessibility(childAt, 1)
            }
        }
    }

    fun addDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener == null) {
            return
        }
        if (this.mListeners == null) {
            this.mListeners = ArrayList()
        }
        this.mListeners.add(drawerListener)
    }

    @Override // android.view.ViewGroup, android.view.View
    fun addFocusables(ArrayList arrayList, Int i, Int i2) {
        if (getDescendantFocusability() == 393216) {
            return
        }
        Int childCount = getChildCount()
        Boolean z = false
        for (Int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3)
            if (!isDrawerView(childAt)) {
                this.mNonDrawerViews.add(childAt)
            } else if (isDrawerOpen(childAt)) {
                z = CHILDREN_DISALLOW_INTERCEPT
                childAt.addFocusables(arrayList, i, i2)
            }
        }
        if (!z) {
            Int size = this.mNonDrawerViews.size()
            for (Int i4 = 0; i4 < size; i4++) {
                View view = (View) this.mNonDrawerViews.get(i4)
                if (view.getVisibility() == 0) {
                    view.addFocusables(arrayList, i, i2)
                }
            }
        }
        this.mNonDrawerViews.clear()
    }

    @Override // android.view.ViewGroup
    fun addView(View view, Int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams)
        if (findOpenDrawer() != null || isDrawerView(view)) {
            ViewCompat.setImportantForAccessibility(view, 4)
        } else {
            ViewCompat.setImportantForAccessibility(view, 1)
        }
        if (CAN_HIDE_DESCENDANTS) {
            return
        }
        ViewCompat.setAccessibilityDelegate(view, this.mChildAccessibilityDelegate)
    }

    Unit cancelChildViewTouch() {
        if (this.mChildrenCanceledTouch) {
            return
        }
        Long jUptimeMillis = SystemClock.uptimeMillis()
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0)
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchTouchEvent(motionEventObtain)
        }
        motionEventObtain.recycle()
        this.mChildrenCanceledTouch = CHILDREN_DISALLOW_INTERCEPT
    }

    Boolean checkDrawerViewAbsoluteGravity(View view, Int i) {
        if ((getDrawerViewAbsoluteGravity(view) & i) == i) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    @Override // android.view.ViewGroup
    protected fun checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if ((layoutParams is LayoutParams) && super.checkLayoutParams(layoutParams)) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    fun closeDrawer(Int i) {
        closeDrawer(i, CHILDREN_DISALLOW_INTERCEPT)
    }

    fun closeDrawer(Int i, Boolean z) {
        View viewFindDrawerWithGravity = findDrawerWithGravity(i)
        if (viewFindDrawerWithGravity == null) {
            throw IllegalArgumentException("No drawer view found with gravity " + gravityToString(i))
        }
        closeDrawer(viewFindDrawerWithGravity, z)
    }

    fun closeDrawer(@NonNull View view) {
        closeDrawer(view, CHILDREN_DISALLOW_INTERCEPT)
    }

    fun closeDrawer(@NonNull View view, Boolean z) {
        if (!isDrawerView(view)) {
            throw IllegalArgumentException("View " + view + " is not a sliding drawer")
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (this.mFirstLayout) {
            layoutParams.onScreen = 0.0f
            layoutParams.openState = 0
        } else if (z) {
            layoutParams.openState |= 4
            if (checkDrawerViewAbsoluteGravity(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, -view.getWidth(), view.getTop())
            } else {
                this.mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop())
            }
        } else {
            moveDrawerToOffset(view, 0.0f)
            updateDrawerState(layoutParams.gravity, 0, view)
            view.setVisibility(4)
        }
        invalidate()
    }

    fun closeDrawers() {
        closeDrawers(false)
    }

    Unit closeDrawers(Boolean z) {
        Int childCount = getChildCount()
        Boolean zSmoothSlideViewTo = false
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
            if (isDrawerView(childAt) && (!z || layoutParams.isPeeking)) {
                zSmoothSlideViewTo = checkDrawerViewAbsoluteGravity(childAt, 3) ? zSmoothSlideViewTo | this.mLeftDragger.smoothSlideViewTo(childAt, -childAt.getWidth(), childAt.getTop()) : zSmoothSlideViewTo | this.mRightDragger.smoothSlideViewTo(childAt, getWidth(), childAt.getTop())
                layoutParams.isPeeking = false
            }
        }
        this.mLeftCallback.removeCallbacks()
        this.mRightCallback.removeCallbacks()
        if (zSmoothSlideViewTo) {
            invalidate()
        }
    }

    @Override // android.view.View
    fun computeScroll() {
        Int childCount = getChildCount()
        Float fMax = 0.0f
        for (Int i = 0; i < childCount; i++) {
            fMax = Math.max(fMax, ((LayoutParams) getChildAt(i).getLayoutParams()).onScreen)
        }
        this.mScrimOpacity = fMax
        Boolean zContinueSettling = this.mLeftDragger.continueSettling(CHILDREN_DISALLOW_INTERCEPT)
        Boolean zContinueSettling2 = this.mRightDragger.continueSettling(CHILDREN_DISALLOW_INTERCEPT)
        if (zContinueSettling || zContinueSettling2) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    @Override // android.view.View
    fun dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.mScrimOpacity <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent)
        }
        Int childCount = getChildCount()
        if (childCount != 0) {
            Float x = motionEvent.getX()
            Float y = motionEvent.getY()
            for (Int i = childCount - 1; i >= 0; i--) {
                View childAt = getChildAt(i)
                if (isInBoundsOfChild(x, y, childAt) && !isContentView(childAt) && dispatchTransformedGenericPointerEvent(motionEvent, childAt)) {
                    return CHILDREN_DISALLOW_INTERCEPT
                }
            }
        }
        return false
    }

    Unit dispatchOnDrawerClosed(View view) {
        View rootView
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if ((layoutParams.openState & 1) == 1) {
            layoutParams.openState = 0
            if (this.mListeners != null) {
                for (Int size = this.mListeners.size() - 1; size >= 0; size--) {
                    ((DrawerListener) this.mListeners.get(size)).onDrawerClosed(view)
                }
            }
            updateChildrenImportantForAccessibility(view, false)
            if (!hasWindowFocus() || (rootView = getRootView()) == null) {
                return
            }
            rootView.sendAccessibilityEvent(32)
        }
    }

    Unit dispatchOnDrawerOpened(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if ((layoutParams.openState & 1) == 0) {
            layoutParams.openState = 1
            if (this.mListeners != null) {
                for (Int size = this.mListeners.size() - 1; size >= 0; size--) {
                    ((DrawerListener) this.mListeners.get(size)).onDrawerOpened(view)
                }
            }
            updateChildrenImportantForAccessibility(view, CHILDREN_DISALLOW_INTERCEPT)
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32)
            }
        }
    }

    Unit dispatchOnDrawerSlide(View view, Float f) {
        if (this.mListeners != null) {
            for (Int size = this.mListeners.size() - 1; size >= 0; size--) {
                ((DrawerListener) this.mListeners.get(size)).onDrawerSlide(view, f)
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0053  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun drawChild(android.graphics.Canvas r10, android.view.View r11, Long r12) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.DrawerLayout.drawChild(android.graphics.Canvas, android.view.View, Long):Boolean")
    }

    View findDrawerWithGravity(Int i) {
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this)) & 7
        Int childCount = getChildCount()
        for (Int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2)
            if ((getDrawerViewAbsoluteGravity(childAt) & 7) == absoluteGravity) {
                return childAt
            }
        }
        return null
    }

    View findOpenDrawer() {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if ((((LayoutParams) childAt.getLayoutParams()).openState & 1) == 1) {
                return childAt
            }
        }
        return null
    }

    View findVisibleDrawer() {
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i)
            if (isDrawerView(childAt) && isDrawerVisible(childAt)) {
                return childAt
            }
        }
        return null
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return LayoutParams(-1, -1)
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return LayoutParams(getContext(), attributeSet)
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams is LayoutParams ? LayoutParams((LayoutParams) layoutParams) : layoutParams is ViewGroup.MarginLayoutParams ? LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : LayoutParams(layoutParams)
    }

    fun getDrawerElevation() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return this.mDrawerElevation
        }
        return 0.0f
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0008 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun getDrawerLockMode(Int r4) {
        /*
            r3 = this
            r2 = 3
            Int r0 = android.support.v4.view.ViewCompat.getLayoutDirection(r3)
            switch(r4) {
                case 3: goto La
                case 5: goto L1b
                case 8388611: goto L2c
                case 8388613: goto L3d
                default: goto L8
            }
        L8:
            r0 = 0
        L9:
            return r0
        La:
            Int r1 = r3.mLockModeLeft
            if (r1 == r2) goto L11
            Int r0 = r3.mLockModeLeft
            goto L9
        L11:
            if (r0 != 0) goto L18
            Int r0 = r3.mLockModeStart
        L15:
            if (r0 == r2) goto L8
            goto L9
        L18:
            Int r0 = r3.mLockModeEnd
            goto L15
        L1b:
            Int r1 = r3.mLockModeRight
            if (r1 == r2) goto L22
            Int r0 = r3.mLockModeRight
            goto L9
        L22:
            if (r0 != 0) goto L29
            Int r0 = r3.mLockModeEnd
        L26:
            if (r0 == r2) goto L8
            goto L9
        L29:
            Int r0 = r3.mLockModeStart
            goto L26
        L2c:
            Int r1 = r3.mLockModeStart
            if (r1 == r2) goto L33
            Int r0 = r3.mLockModeStart
            goto L9
        L33:
            if (r0 != 0) goto L3a
            Int r0 = r3.mLockModeLeft
        L37:
            if (r0 == r2) goto L8
            goto L9
        L3a:
            Int r0 = r3.mLockModeRight
            goto L37
        L3d:
            Int r1 = r3.mLockModeEnd
            if (r1 == r2) goto L44
            Int r0 = r3.mLockModeEnd
            goto L9
        L44:
            if (r0 != 0) goto L4b
            Int r0 = r3.mLockModeRight
        L48:
            if (r0 == r2) goto L8
            goto L9
        L4b:
            Int r0 = r3.mLockModeLeft
            goto L48
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.DrawerLayout.getDrawerLockMode(Int):Int")
    }

    fun getDrawerLockMode(@NonNull View view) {
        if (isDrawerView(view)) {
            return getDrawerLockMode(((LayoutParams) view.getLayoutParams()).gravity)
        }
        throw IllegalArgumentException("View " + view + " is not a drawer")
    }

    @Nullable
    fun getDrawerTitle(Int i) {
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this))
        if (absoluteGravity == 3) {
            return this.mTitleLeft
        }
        if (absoluteGravity == 5) {
            return this.mTitleRight
        }
        return null
    }

    Int getDrawerViewAbsoluteGravity(View view) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this))
    }

    Float getDrawerViewOffset(View view) {
        return ((LayoutParams) view.getLayoutParams()).onScreen
    }

    @Nullable
    fun getStatusBarBackgroundDrawable() {
        return this.mStatusBarBackground
    }

    Boolean isContentView(View view) {
        if (((LayoutParams) view.getLayoutParams()).gravity == 0) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    fun isDrawerOpen(Int i) {
        View viewFindDrawerWithGravity = findDrawerWithGravity(i)
        if (viewFindDrawerWithGravity != null) {
            return isDrawerOpen(viewFindDrawerWithGravity)
        }
        return false
    }

    fun isDrawerOpen(@NonNull View view) {
        if (!isDrawerView(view)) {
            throw IllegalArgumentException("View " + view + " is not a drawer")
        }
        if ((((LayoutParams) view.getLayoutParams()).openState & 1) == 1) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    Boolean isDrawerView(View view) {
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view))
        if ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) {
            return false
        }
        return CHILDREN_DISALLOW_INTERCEPT
    }

    fun isDrawerVisible(Int i) {
        View viewFindDrawerWithGravity = findDrawerWithGravity(i)
        if (viewFindDrawerWithGravity != null) {
            return isDrawerVisible(viewFindDrawerWithGravity)
        }
        return false
    }

    fun isDrawerVisible(@NonNull View view) {
        if (!isDrawerView(view)) {
            throw IllegalArgumentException("View " + view + " is not a drawer")
        }
        if (((LayoutParams) view.getLayoutParams()).onScreen > 0.0f) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    Unit moveDrawerToOffset(View view, Float f) {
        Float drawerViewOffset = getDrawerViewOffset(view)
        Int width = view.getWidth()
        Int i = ((Int) (width * f)) - ((Int) (drawerViewOffset * width))
        if (!checkDrawerViewAbsoluteGravity(view, 3)) {
            i = -i
        }
        view.offsetLeftAndRight(i)
        setDrawerViewOffset(view, f)
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.mFirstLayout = CHILDREN_DISALLOW_INTERCEPT
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.mFirstLayout = CHILDREN_DISALLOW_INTERCEPT
    }

    @Override // android.view.View
    fun onDraw(Canvas canvas) {
        super.onDraw(canvas)
        if (!this.mDrawStatusBarBackground || this.mStatusBarBackground == null) {
            return
        }
        Int systemWindowInsetTop = (Build.VERSION.SDK_INT < 21 || this.mLastInsets == null) ? 0 : ((WindowInsets) this.mLastInsets).getSystemWindowInsetTop()
        if (systemWindowInsetTop > 0) {
            this.mStatusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop)
            this.mStatusBarBackground.draw(canvas)
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0016  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this
            r1 = 1
            r2 = 0
            Int r0 = r8.getActionMasked()
            android.support.v4.widget.ViewDragHelper r3 = r7.mLeftDragger
            Boolean r3 = r3.shouldInterceptTouchEvent(r8)
            android.support.v4.widget.ViewDragHelper r4 = r7.mRightDragger
            Boolean r4 = r4.shouldInterceptTouchEvent(r8)
            r3 = r3 | r4
            switch(r0) {
                case 0: goto L27
                case 1: goto L65
                case 2: goto L50
                case 3: goto L65
                default: goto L16
            }
        L16:
            r0 = r2
        L17:
            if (r3 != 0) goto L25
            if (r0 != 0) goto L25
            Boolean r0 = r7.hasPeekingDrawer()
            if (r0 != 0) goto L25
            Boolean r0 = r7.mChildrenCanceledTouch
            if (r0 == 0) goto L26
        L25:
            r2 = r1
        L26:
            return r2
        L27:
            Float r0 = r8.getX()
            Float r4 = r8.getY()
            r7.mInitialMotionX = r0
            r7.mInitialMotionY = r4
            Float r5 = r7.mScrimOpacity
            r6 = 0
            Int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L6d
            android.support.v4.widget.ViewDragHelper r5 = r7.mLeftDragger
            Int r0 = (Int) r0
            Int r4 = (Int) r4
            android.view.View r0 = r5.findTopChildUnder(r0, r4)
            if (r0 == 0) goto L6d
            Boolean r0 = r7.isContentView(r0)
            if (r0 == 0) goto L6d
            r0 = r1
        L4b:
            r7.mDisallowInterceptRequested = r2
            r7.mChildrenCanceledTouch = r2
            goto L17
        L50:
            android.support.v4.widget.ViewDragHelper r0 = r7.mLeftDragger
            r4 = 3
            Boolean r0 = r0.checkTouchSlop(r4)
            if (r0 == 0) goto L16
            android.support.v4.widget.DrawerLayout$ViewDragCallback r0 = r7.mLeftCallback
            r0.removeCallbacks()
            android.support.v4.widget.DrawerLayout$ViewDragCallback r0 = r7.mRightCallback
            r0.removeCallbacks()
            r0 = r2
            goto L17
        L65:
            r7.closeDrawers(r1)
            r7.mDisallowInterceptRequested = r2
            r7.mChildrenCanceledTouch = r2
            goto L16
        L6d:
            r0 = r2
            goto L4b
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.DrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):Boolean")
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    fun onKeyDown(Int i, KeyEvent keyEvent) {
        if (i != 4 || !hasVisibleDrawer()) {
            return super.onKeyDown(i, keyEvent)
        }
        keyEvent.startTracking()
        return CHILDREN_DISALLOW_INTERCEPT
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    fun onKeyUp(Int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent)
        }
        View viewFindVisibleDrawer = findVisibleDrawer()
        if (viewFindVisibleDrawer != null && getDrawerLockMode(viewFindVisibleDrawer) == 0) {
            closeDrawers()
        }
        if (viewFindVisibleDrawer != null) {
            return CHILDREN_DISALLOW_INTERCEPT
        }
        return false
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        Int i5
        Float f
        this.mInLayout = CHILDREN_DISALLOW_INTERCEPT
        Int i6 = i3 - i
        Int childCount = getChildCount()
        for (Int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7)
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams()
                if (isContentView(childAt)) {
                    childAt.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + childAt.getMeasuredWidth(), layoutParams.topMargin + childAt.getMeasuredHeight())
                } else {
                    Int measuredWidth = childAt.getMeasuredWidth()
                    Int measuredHeight = childAt.getMeasuredHeight()
                    if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                        i5 = ((Int) (measuredWidth * layoutParams.onScreen)) + (-measuredWidth)
                        f = (measuredWidth + i5) / measuredWidth
                    } else {
                        i5 = i6 - ((Int) (measuredWidth * layoutParams.onScreen))
                        f = (i6 - i5) / measuredWidth
                    }
                    Boolean z2 = f != layoutParams.onScreen ? CHILDREN_DISALLOW_INTERCEPT : false
                    switch (layoutParams.gravity & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall) {
                        case 16:
                            Int i8 = i4 - i2
                            Int i9 = (i8 - measuredHeight) / 2
                            if (i9 < layoutParams.topMargin) {
                                i9 = layoutParams.topMargin
                            } else if (i9 + measuredHeight > i8 - layoutParams.bottomMargin) {
                                i9 = (i8 - layoutParams.bottomMargin) - measuredHeight
                            }
                            childAt.layout(i5, i9, measuredWidth + i5, measuredHeight + i9)
                            break
                        case android.support.v7.appcompat.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                            Int i10 = i4 - i2
                            childAt.layout(i5, (i10 - layoutParams.bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i5, i10 - layoutParams.bottomMargin)
                            break
                        default:
                            childAt.layout(i5, layoutParams.topMargin, measuredWidth + i5, measuredHeight + layoutParams.topMargin)
                            break
                    }
                    if (z2) {
                        setDrawerViewOffset(childAt, f)
                    }
                    Int i11 = layoutParams.onScreen > 0.0f ? 0 : 4
                    if (childAt.getVisibility() != i11) {
                        childAt.setVisibility(i11)
                    }
                }
            }
        }
        this.mInLayout = false
        this.mFirstLayout = false
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x01e1 A[PHI: r2
  0x01e1: PHI (r2v50 Int) = (r2v2 Int), (r2v2 Int), (r2v0 Int) binds: [B:13:0x0028, B:14:0x002a, B:5:0x0016] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    @android.annotation.SuppressLint({"WrongConstant"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun onMeasure(Int r18, Int r19) {
        /*
            Method dump skipped, instructions count: 485
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.DrawerLayout.onMeasure(Int, Int):Unit")
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        View viewFindDrawerWithGravity
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        if (savedState.openDrawerGravity != 0 && (viewFindDrawerWithGravity = findDrawerWithGravity(savedState.openDrawerGravity)) != null) {
            openDrawer(viewFindDrawerWithGravity)
        }
        if (savedState.lockModeLeft != 3) {
            setDrawerLockMode(savedState.lockModeLeft, 3)
        }
        if (savedState.lockModeRight != 3) {
            setDrawerLockMode(savedState.lockModeRight, 5)
        }
        if (savedState.lockModeStart != 3) {
            setDrawerLockMode(savedState.lockModeStart, GravityCompat.START)
        }
        if (savedState.lockModeEnd != 3) {
            setDrawerLockMode(savedState.lockModeEnd, GravityCompat.END)
        }
    }

    @Override // android.view.View
    fun onRtlPropertiesChanged(Int i) {
        resolveShadowDrawables()
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        Int childCount = getChildCount()
        for (Int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams()
            Boolean z = layoutParams.openState == 1
            Boolean z2 = layoutParams.openState == 2
            if (z || z2) {
                savedState.openDrawerGravity = layoutParams.gravity
                break
            }
        }
        savedState.lockModeLeft = this.mLockModeLeft
        savedState.lockModeRight = this.mLockModeRight
        savedState.lockModeStart = this.mLockModeStart
        savedState.lockModeEnd = this.mLockModeEnd
        return savedState
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        Boolean z
        View viewFindOpenDrawer
        this.mLeftDragger.processTouchEvent(motionEvent)
        this.mRightDragger.processTouchEvent(motionEvent)
        switch (motionEvent.getAction() & 255) {
            case 0:
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                this.mInitialMotionX = x
                this.mInitialMotionY = y
                this.mDisallowInterceptRequested = false
                this.mChildrenCanceledTouch = false
                return CHILDREN_DISALLOW_INTERCEPT
            case 1:
                Float x2 = motionEvent.getX()
                Float y2 = motionEvent.getY()
                View viewFindTopChildUnder = this.mLeftDragger.findTopChildUnder((Int) x2, (Int) y2)
                if (viewFindTopChildUnder == null || !isContentView(viewFindTopChildUnder)) {
                    z = true
                    closeDrawers(z)
                    this.mDisallowInterceptRequested = false
                } else {
                    Float f = x2 - this.mInitialMotionX
                    Float f2 = y2 - this.mInitialMotionY
                    Int touchSlop = this.mLeftDragger.getTouchSlop()
                    z = (f * f) + (f2 * f2) >= ((Float) (touchSlop * touchSlop)) || (viewFindOpenDrawer = findOpenDrawer()) == null || getDrawerLockMode(viewFindOpenDrawer) == 2
                    closeDrawers(z)
                    this.mDisallowInterceptRequested = false
                }
                return CHILDREN_DISALLOW_INTERCEPT
            case 2:
            default:
                return CHILDREN_DISALLOW_INTERCEPT
            case 3:
                closeDrawers(CHILDREN_DISALLOW_INTERCEPT)
                this.mDisallowInterceptRequested = false
                this.mChildrenCanceledTouch = false
                return CHILDREN_DISALLOW_INTERCEPT
        }
    }

    fun openDrawer(Int i) {
        openDrawer(i, CHILDREN_DISALLOW_INTERCEPT)
    }

    fun openDrawer(Int i, Boolean z) {
        View viewFindDrawerWithGravity = findDrawerWithGravity(i)
        if (viewFindDrawerWithGravity == null) {
            throw IllegalArgumentException("No drawer view found with gravity " + gravityToString(i))
        }
        openDrawer(viewFindDrawerWithGravity, z)
    }

    fun openDrawer(@NonNull View view) {
        openDrawer(view, CHILDREN_DISALLOW_INTERCEPT)
    }

    fun openDrawer(@NonNull View view, Boolean z) {
        if (!isDrawerView(view)) {
            throw IllegalArgumentException("View " + view + " is not a sliding drawer")
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (this.mFirstLayout) {
            layoutParams.onScreen = TOUCH_SLOP_SENSITIVITY
            layoutParams.openState = 1
            updateChildrenImportantForAccessibility(view, CHILDREN_DISALLOW_INTERCEPT)
        } else if (z) {
            layoutParams.openState |= 2
            if (checkDrawerViewAbsoluteGravity(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, 0, view.getTop())
            } else {
                this.mRightDragger.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop())
            }
        } else {
            moveDrawerToOffset(view, TOUCH_SLOP_SENSITIVITY)
            updateDrawerState(layoutParams.gravity, 0, view)
            view.setVisibility(0)
        }
        invalidate()
    }

    fun removeDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener == null || this.mListeners == null) {
            return
        }
        this.mListeners.remove(drawerListener)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    fun requestDisallowInterceptTouchEvent(Boolean z) {
        super.requestDisallowInterceptTouchEvent(z)
        this.mDisallowInterceptRequested = z
        if (z) {
            closeDrawers(CHILDREN_DISALLOW_INTERCEPT)
        }
    }

    @Override // android.view.View, android.view.ViewParent
    fun requestLayout() {
        if (this.mInLayout) {
            return
        }
        super.requestLayout()
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setChildInsets(Object obj, Boolean z) {
        this.mLastInsets = obj
        this.mDrawStatusBarBackground = z
        setWillNotDraw((z || getBackground() != null) ? false : CHILDREN_DISALLOW_INTERCEPT)
        requestLayout()
    }

    fun setDrawerElevation(Float f) {
        this.mDrawerElevation = f
        for (Int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i)
            if (isDrawerView(childAt)) {
                ViewCompat.setElevation(childAt, this.mDrawerElevation)
            }
        }
    }

    @Deprecated
    fun setDrawerListener(DrawerListener drawerListener) {
        if (this.mListener != null) {
            removeDrawerListener(this.mListener)
        }
        if (drawerListener != null) {
            addDrawerListener(drawerListener)
        }
        this.mListener = drawerListener
    }

    fun setDrawerLockMode(Int i) {
        setDrawerLockMode(i, 3)
        setDrawerLockMode(i, 5)
    }

    fun setDrawerLockMode(Int i, Int i2) {
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this))
        switch (i2) {
            case 3:
                this.mLockModeLeft = i
                break
            case 5:
                this.mLockModeRight = i
                break
            case GravityCompat.START /* 8388611 */:
                this.mLockModeStart = i
                break
            case GravityCompat.END /* 8388613 */:
                this.mLockModeEnd = i
                break
        }
        if (i != 0) {
            (absoluteGravity == 3 ? this.mLeftDragger : this.mRightDragger).cancel()
        }
        switch (i) {
            case 1:
                View viewFindDrawerWithGravity = findDrawerWithGravity(absoluteGravity)
                if (viewFindDrawerWithGravity != null) {
                    closeDrawer(viewFindDrawerWithGravity)
                    break
                }
                break
            case 2:
                View viewFindDrawerWithGravity2 = findDrawerWithGravity(absoluteGravity)
                if (viewFindDrawerWithGravity2 != null) {
                    openDrawer(viewFindDrawerWithGravity2)
                    break
                }
                break
        }
    }

    fun setDrawerLockMode(Int i, @NonNull View view) {
        if (!isDrawerView(view)) {
            throw IllegalArgumentException("View " + view + " is not a drawer with appropriate layout_gravity")
        }
        setDrawerLockMode(i, ((LayoutParams) view.getLayoutParams()).gravity)
    }

    fun setDrawerShadow(@DrawableRes Int i, Int i2) {
        setDrawerShadow(ContextCompat.getDrawable(getContext(), i), i2)
    }

    fun setDrawerShadow(Drawable drawable, Int i) {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return
        }
        if ((i & GravityCompat.START) == 8388611) {
            this.mShadowStart = drawable
        } else if ((i & GravityCompat.END) == 8388613) {
            this.mShadowEnd = drawable
        } else if ((i & 3) == 3) {
            this.mShadowLeft = drawable
        } else if ((i & 5) != 5) {
            return
        } else {
            this.mShadowRight = drawable
        }
        resolveShadowDrawables()
        invalidate()
    }

    fun setDrawerTitle(Int i, @Nullable CharSequence charSequence) {
        Int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this))
        if (absoluteGravity == 3) {
            this.mTitleLeft = charSequence
        } else if (absoluteGravity == 5) {
            this.mTitleRight = charSequence
        }
    }

    Unit setDrawerViewOffset(View view, Float f) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
        if (f == layoutParams.onScreen) {
            return
        }
        layoutParams.onScreen = f
        dispatchOnDrawerSlide(view, f)
    }

    fun setScrimColor(@ColorInt Int i) {
        this.mScrimColor = i
        invalidate()
    }

    fun setStatusBarBackground(Int i) {
        this.mStatusBarBackground = i != 0 ? ContextCompat.getDrawable(getContext(), i) : null
        invalidate()
    }

    fun setStatusBarBackground(@Nullable Drawable drawable) {
        this.mStatusBarBackground = drawable
        invalidate()
    }

    fun setStatusBarBackgroundColor(@ColorInt Int i) {
        this.mStatusBarBackground = ColorDrawable(i)
        invalidate()
    }

    Unit updateDrawerState(Int i, Int i2, View view) {
        Int viewDragState = this.mLeftDragger.getViewDragState()
        Int viewDragState2 = this.mRightDragger.getViewDragState()
        Int i3 = (viewDragState == 1 || viewDragState2 == 1) ? 1 : (viewDragState == 2 || viewDragState2 == 2) ? 2 : 0
        if (view != null && i2 == 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams()
            if (layoutParams.onScreen == 0.0f) {
                dispatchOnDrawerClosed(view)
            } else if (layoutParams.onScreen == TOUCH_SLOP_SENSITIVITY) {
                dispatchOnDrawerOpened(view)
            }
        }
        if (i3 != this.mDrawerState) {
            this.mDrawerState = i3
            if (this.mListeners != null) {
                for (Int size = this.mListeners.size() - 1; size >= 0; size--) {
                    ((DrawerListener) this.mListeners.get(size)).onDrawerStateChanged(i3)
                }
            }
        }
    }
}
