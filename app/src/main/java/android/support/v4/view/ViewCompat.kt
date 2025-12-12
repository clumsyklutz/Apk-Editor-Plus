package android.support.v4.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.res.ColorStateList
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.FloatRange
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.annotation.UiThread
import android.support.compat.R
import android.support.v4.util.ArrayMap
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
import android.util.Log
import android.util.SparseArray
import android.view.Display
import android.view.KeyEvent
import android.view.PointerIcon
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.WindowInsets
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeProvider
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Collection
import java.util.Iterator
import java.util.Map
import java.util.WeakHashMap
import java.util.concurrent.atomic.AtomicInteger

class ViewCompat {
    public static val ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2
    public static val ACCESSIBILITY_LIVE_REGION_NONE = 0
    public static val ACCESSIBILITY_LIVE_REGION_POLITE = 1
    public static val IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0
    public static val IMPORTANT_FOR_ACCESSIBILITY_NO = 2
    public static val IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4
    public static val IMPORTANT_FOR_ACCESSIBILITY_YES = 1

    @Deprecated
    public static val LAYER_TYPE_HARDWARE = 2

    @Deprecated
    public static val LAYER_TYPE_NONE = 0

    @Deprecated
    public static val LAYER_TYPE_SOFTWARE = 1
    public static val LAYOUT_DIRECTION_INHERIT = 2
    public static val LAYOUT_DIRECTION_LOCALE = 3
    public static val LAYOUT_DIRECTION_LTR = 0
    public static val LAYOUT_DIRECTION_RTL = 1

    @Deprecated
    public static val MEASURED_HEIGHT_STATE_SHIFT = 16

    @Deprecated
    public static val MEASURED_SIZE_MASK = 16777215

    @Deprecated
    public static val MEASURED_STATE_MASK = -16777216

    @Deprecated
    public static val MEASURED_STATE_TOO_SMALL = 16777216

    @Deprecated
    public static val OVER_SCROLL_ALWAYS = 0

    @Deprecated
    public static val OVER_SCROLL_IF_CONTENT_SCROLLS = 1

    @Deprecated
    public static val OVER_SCROLL_NEVER = 2
    public static val SCROLL_AXIS_HORIZONTAL = 1
    public static val SCROLL_AXIS_NONE = 0
    public static val SCROLL_AXIS_VERTICAL = 2
    public static val SCROLL_INDICATOR_BOTTOM = 2
    public static val SCROLL_INDICATOR_END = 32
    public static val SCROLL_INDICATOR_LEFT = 4
    public static val SCROLL_INDICATOR_RIGHT = 8
    public static val SCROLL_INDICATOR_START = 16
    public static val SCROLL_INDICATOR_TOP = 1
    private static val TAG = "ViewCompat"
    public static val TYPE_NON_TOUCH = 1
    public static val TYPE_TOUCH = 0
    private static Field sAccessibilityDelegateField
    private static Method sChildrenDrawingOrderMethod
    private static Method sDispatchFinishTemporaryDetach
    private static Method sDispatchStartTemporaryDetach
    private static Field sMinHeightField
    private static Boolean sMinHeightFieldFetched
    private static Field sMinWidthField
    private static Boolean sMinWidthFieldFetched
    private static Boolean sTempDetachBound
    private static ThreadLocal sThreadLocalRect
    private static WeakHashMap sTransitionNameMap
    private static val sNextGeneratedId = AtomicInteger(1)
    private static WeakHashMap sViewPropertyAnimatorMap = null
    private static Boolean sAccessibilityDelegateCheckFailed = false

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface FocusDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface FocusRealDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface FocusRelativeDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface NestedScrollType {
    }

    public interface OnUnhandledKeyEventListenerCompat {
        Boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent)
    }

    @RequiresApi(28)
    class OnUnhandledKeyEventListenerWrapper implements View.OnUnhandledKeyEventListener {
        private OnUnhandledKeyEventListenerCompat mCompatListener

        OnUnhandledKeyEventListenerWrapper(OnUnhandledKeyEventListenerCompat onUnhandledKeyEventListenerCompat) {
            this.mCompatListener = onUnhandledKeyEventListenerCompat
        }

        @Override // android.view.View.OnUnhandledKeyEventListener
        fun onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
            return this.mCompatListener.onUnhandledKeyEvent(view, keyEvent)
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollAxis {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollIndicators {
    }

    class UnhandledKeyEventManager {
        private static val sViewsWithListeners = ArrayList()

        @Nullable
        private WeakHashMap mViewsContainingListeners = null
        private SparseArray mCapturedKeys = null
        private WeakReference mLastDispatchedPreViewKeyEvent = null

        UnhandledKeyEventManager() {
        }

        static UnhandledKeyEventManager at(View view) {
            UnhandledKeyEventManager unhandledKeyEventManager = (UnhandledKeyEventManager) view.getTag(R.id.tag_unhandled_key_event_manager)
            if (unhandledKeyEventManager != null) {
                return unhandledKeyEventManager
            }
            UnhandledKeyEventManager unhandledKeyEventManager2 = UnhandledKeyEventManager()
            view.setTag(R.id.tag_unhandled_key_event_manager, unhandledKeyEventManager2)
            return unhandledKeyEventManager2
        }

        @Nullable
        private fun dispatchInOrder(View view, KeyEvent keyEvent) {
            if (this.mViewsContainingListeners == null || !this.mViewsContainingListeners.containsKey(view)) {
                return null
            }
            if (view is ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view
                for (Int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                    View viewDispatchInOrder = dispatchInOrder(viewGroup.getChildAt(childCount), keyEvent)
                    if (viewDispatchInOrder != null) {
                        return viewDispatchInOrder
                    }
                }
            }
            if (onUnhandledKeyEvent(view, keyEvent)) {
                return view
            }
            return null
        }

        private fun getCapturedKeys() {
            if (this.mCapturedKeys == null) {
                this.mCapturedKeys = SparseArray()
            }
            return this.mCapturedKeys
        }

        private fun onUnhandledKeyEvent(@NonNull View view, @NonNull KeyEvent keyEvent) {
            ArrayList arrayList = (ArrayList) view.getTag(R.id.tag_unhandled_key_listeners)
            if (arrayList != null) {
                for (Int size = arrayList.size() - 1; size >= 0; size--) {
                    if (((OnUnhandledKeyEventListenerCompat) arrayList.get(size)).onUnhandledKeyEvent(view, keyEvent)) {
                        return true
                    }
                }
            }
            return false
        }

        private fun recalcViewsWithUnhandled() {
            if (this.mViewsContainingListeners != null) {
                this.mViewsContainingListeners.clear()
            }
            if (sViewsWithListeners.isEmpty()) {
                return
            }
            synchronized (sViewsWithListeners) {
                if (this.mViewsContainingListeners == null) {
                    this.mViewsContainingListeners = WeakHashMap()
                }
                for (Int size = sViewsWithListeners.size() - 1; size >= 0; size--) {
                    View view = (View) ((WeakReference) sViewsWithListeners.get(size)).get()
                    if (view == null) {
                        sViewsWithListeners.remove(size)
                    } else {
                        this.mViewsContainingListeners.put(view, Boolean.TRUE)
                        for (ViewParent parent = view.getParent(); parent is View; parent = parent.getParent()) {
                            this.mViewsContainingListeners.put((View) parent, Boolean.TRUE)
                        }
                    }
                }
            }
        }

        static Unit registerListeningView(View view) {
            synchronized (sViewsWithListeners) {
                Iterator it = sViewsWithListeners.iterator()
                while (it.hasNext()) {
                    if (((WeakReference) it.next()).get() == view) {
                        return
                    }
                }
                sViewsWithListeners.add(WeakReference(view))
            }
        }

        static Unit unregisterListeningView(View view) {
            synchronized (sViewsWithListeners) {
                for (Int i = 0; i < sViewsWithListeners.size(); i++) {
                    if (((WeakReference) sViewsWithListeners.get(i)).get() == view) {
                        sViewsWithListeners.remove(i)
                        return
                    }
                }
            }
        }

        Boolean dispatch(View view, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0) {
                recalcViewsWithUnhandled()
            }
            View viewDispatchInOrder = dispatchInOrder(view, keyEvent)
            if (keyEvent.getAction() == 0) {
                Int keyCode = keyEvent.getKeyCode()
                if (viewDispatchInOrder != null && !KeyEvent.isModifierKey(keyCode)) {
                    getCapturedKeys().put(keyCode, WeakReference(viewDispatchInOrder))
                }
            }
            return viewDispatchInOrder != null
        }

        Boolean preDispatch(KeyEvent keyEvent) {
            Int iIndexOfKey
            if (this.mLastDispatchedPreViewKeyEvent != null && this.mLastDispatchedPreViewKeyEvent.get() == keyEvent) {
                return false
            }
            this.mLastDispatchedPreViewKeyEvent = WeakReference(keyEvent)
            WeakReference weakReference = null
            SparseArray capturedKeys = getCapturedKeys()
            if (keyEvent.getAction() == 1 && (iIndexOfKey = capturedKeys.indexOfKey(keyEvent.getKeyCode())) >= 0) {
                weakReference = (WeakReference) capturedKeys.valueAt(iIndexOfKey)
                capturedKeys.removeAt(iIndexOfKey)
            }
            if (weakReference == null) {
                weakReference = (WeakReference) capturedKeys.get(keyEvent.getKeyCode())
            }
            if (weakReference == null) {
                return false
            }
            View view = (View) weakReference.get()
            if (view != null && ViewCompat.isAttachedToWindow(view)) {
                onUnhandledKeyEvent(view, keyEvent)
            }
            return true
        }
    }

    protected constructor() {
    }

    fun addKeyboardNavigationClusters(@NonNull View view, @NonNull Collection collection, Int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.addKeyboardNavigationClusters(collection, i)
        }
    }

    fun addOnUnhandledKeyEventListener(@NonNull View view, @NonNull OnUnhandledKeyEventListenerCompat onUnhandledKeyEventListenerCompat) {
        if (Build.VERSION.SDK_INT >= 28) {
            Map arrayMap = (Map) view.getTag(R.id.tag_unhandled_key_listeners)
            if (arrayMap == null) {
                arrayMap = ArrayMap()
                view.setTag(R.id.tag_unhandled_key_listeners, arrayMap)
            }
            OnUnhandledKeyEventListenerWrapper onUnhandledKeyEventListenerWrapper = OnUnhandledKeyEventListenerWrapper(onUnhandledKeyEventListenerCompat)
            arrayMap.put(onUnhandledKeyEventListenerCompat, onUnhandledKeyEventListenerWrapper)
            view.addOnUnhandledKeyEventListener(onUnhandledKeyEventListenerWrapper)
            return
        }
        ArrayList arrayList = (ArrayList) view.getTag(R.id.tag_unhandled_key_listeners)
        if (arrayList == null) {
            arrayList = ArrayList()
            view.setTag(R.id.tag_unhandled_key_listeners, arrayList)
        }
        arrayList.add(onUnhandledKeyEventListenerCompat)
        if (arrayList.size() == 1) {
            UnhandledKeyEventManager.registerListeningView(view)
        }
    }

    @NonNull
    fun animate(@NonNull View view) {
        if (sViewPropertyAnimatorMap == null) {
            sViewPropertyAnimatorMap = WeakHashMap()
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) sViewPropertyAnimatorMap.get(view)
        if (viewPropertyAnimatorCompat != null) {
            return viewPropertyAnimatorCompat
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = ViewPropertyAnimatorCompat(view)
        sViewPropertyAnimatorMap.put(view, viewPropertyAnimatorCompat2)
        return viewPropertyAnimatorCompat2
    }

    private fun bindTempDetach() {
        try {
            sDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0])
            sDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0])
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Couldn't find method", e)
        }
        sTempDetachBound = true
    }

    @Deprecated
    fun canScrollHorizontally(View view, Int i) {
        return view.canScrollHorizontally(i)
    }

    @Deprecated
    fun canScrollVertically(View view, Int i) {
        return view.canScrollVertically(i)
    }

    fun cancelDragAndDrop(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 24) {
            view.cancelDragAndDrop()
        }
    }

    @Deprecated
    fun combineMeasuredStates(Int i, Int i2) {
        return View.combineMeasuredStates(i, i2)
    }

    private fun compatOffsetLeftAndRight(View view, Int i) {
        view.offsetLeftAndRight(i)
        if (view.getVisibility() == 0) {
            tickleInvalidationFlag(view)
            Object parent = view.getParent()
            if (parent is View) {
                tickleInvalidationFlag((View) parent)
            }
        }
    }

    private fun compatOffsetTopAndBottom(View view, Int i) {
        view.offsetTopAndBottom(i)
        if (view.getVisibility() == 0) {
            tickleInvalidationFlag(view)
            Object parent = view.getParent()
            if (parent is View) {
                tickleInvalidationFlag((View) parent)
            }
        }
    }

    fun dispatchApplyWindowInsets(@NonNull View view, WindowInsetsCompat windowInsetsCompat) {
        if (Build.VERSION.SDK_INT < 21) {
            return windowInsetsCompat
        }
        WindowInsets windowInsets = (WindowInsets) WindowInsetsCompat.unwrap(windowInsetsCompat)
        WindowInsets windowInsetsDispatchApplyWindowInsets = view.dispatchApplyWindowInsets(windowInsets)
        if (windowInsetsDispatchApplyWindowInsets != windowInsets) {
            windowInsets = WindowInsets(windowInsetsDispatchApplyWindowInsets)
        }
        return WindowInsetsCompat.wrap(windowInsets)
    }

    fun dispatchFinishTemporaryDetach(@NonNull View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 24) {
            view.dispatchFinishTemporaryDetach()
            return
        }
        if (!sTempDetachBound) {
            bindTempDetach()
        }
        if (sDispatchFinishTemporaryDetach == null) {
            view.onFinishTemporaryDetach()
            return
        }
        try {
            sDispatchFinishTemporaryDetach.invoke(view, new Object[0])
        } catch (Exception e) {
            Log.d(TAG, "Error calling dispatchFinishTemporaryDetach", e)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun dispatchNestedFling(@NonNull View view, Float f, Float f2, Boolean z) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.dispatchNestedFling(f, f2, z)
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).dispatchNestedFling(f, f2, z)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun dispatchNestedPreFling(@NonNull View view, Float f, Float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.dispatchNestedPreFling(f, f2)
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).dispatchNestedPreFling(f, f2)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun dispatchNestedPreScroll(@NonNull View view, Int i, Int i2, @Nullable Array<Int> iArr, @Nullable Array<Int> iArr2) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.dispatchNestedPreScroll(i, i2, iArr, iArr2)
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).dispatchNestedPreScroll(i, i2, iArr, iArr2)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun dispatchNestedPreScroll(@NonNull View view, Int i, Int i2, @Nullable Array<Int> iArr, @Nullable Array<Int> iArr2, Int i3) {
        if (view is NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).dispatchNestedPreScroll(i, i2, iArr, iArr2, i3)
        }
        if (i3 == 0) {
            return dispatchNestedPreScroll(view, i, i2, iArr, iArr2)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun dispatchNestedScroll(@NonNull View view, Int i, Int i2, Int i3, Int i4, @Nullable Array<Int> iArr) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.dispatchNestedScroll(i, i2, i3, i4, iArr)
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).dispatchNestedScroll(i, i2, i3, i4, iArr)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun dispatchNestedScroll(@NonNull View view, Int i, Int i2, Int i3, Int i4, @Nullable Array<Int> iArr, Int i5) {
        if (view is NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).dispatchNestedScroll(i, i2, i3, i4, iArr, i5)
        }
        if (i5 == 0) {
            return dispatchNestedScroll(view, i, i2, i3, i4, iArr)
        }
        return false
    }

    fun dispatchStartTemporaryDetach(@NonNull View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 24) {
            view.dispatchStartTemporaryDetach()
            return
        }
        if (!sTempDetachBound) {
            bindTempDetach()
        }
        if (sDispatchStartTemporaryDetach == null) {
            view.onStartTemporaryDetach()
            return
        }
        try {
            sDispatchStartTemporaryDetach.invoke(view, new Object[0])
        } catch (Exception e) {
            Log.d(TAG, "Error calling dispatchStartTemporaryDetach", e)
        }
    }

    @UiThread
    static Boolean dispatchUnhandledKeyEventBeforeCallback(View view, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false
        }
        return UnhandledKeyEventManager.at(view).dispatch(view, keyEvent)
    }

    @UiThread
    static Boolean dispatchUnhandledKeyEventBeforeHierarchy(View view, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false
        }
        return UnhandledKeyEventManager.at(view).preDispatch(keyEvent)
    }

    fun generateViewId() {
        Int i
        Int i2
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId()
        }
        do {
            i = sNextGeneratedId.get()
            i2 = i + 1
            if (i2 > 16777215) {
                i2 = 1
            }
        } while (!sNextGeneratedId.compareAndSet(i, i2));
        return i
    }

    fun getAccessibilityLiveRegion(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return view.getAccessibilityLiveRegion()
        }
        return 0
    }

    fun getAccessibilityNodeProvider(@NonNull View view) {
        AccessibilityNodeProvider accessibilityNodeProvider
        if (Build.VERSION.SDK_INT < 16 || (accessibilityNodeProvider = view.getAccessibilityNodeProvider()) == null) {
            return null
        }
        return AccessibilityNodeProviderCompat(accessibilityNodeProvider)
    }

    @Deprecated
    fun getAlpha(View view) {
        return view.getAlpha()
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getBackgroundTintList(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getBackgroundTintList()
        }
        if (view is TintableBackgroundView) {
            return ((TintableBackgroundView) view).getSupportBackgroundTintList()
        }
        return null
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PorterDuff.Mode getBackgroundTintMode(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getBackgroundTintMode()
        }
        if (view is TintableBackgroundView) {
            return ((TintableBackgroundView) view).getSupportBackgroundTintMode()
        }
        return null
    }

    @Nullable
    fun getClipBounds(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return view.getClipBounds()
        }
        return null
    }

    @Nullable
    fun getDisplay(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            return view.getDisplay()
        }
        if (isAttachedToWindow(view)) {
            return ((WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay()
        }
        return null
    }

    fun getElevation(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getElevation()
        }
        return 0.0f
    }

    private fun getEmptyTempRect() {
        if (sThreadLocalRect == null) {
            sThreadLocalRect = ThreadLocal()
        }
        Rect rect = (Rect) sThreadLocalRect.get()
        if (rect == null) {
            rect = Rect()
            sThreadLocalRect.set(rect)
        }
        rect.setEmpty()
        return rect
    }

    fun getFitsSystemWindows(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.getFitsSystemWindows()
        }
        return false
    }

    fun getImportantForAccessibility(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.getImportantForAccessibility()
        }
        return 0
    }

    @SuppressLint({"InlinedApi"})
    fun getImportantForAutofill(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.getImportantForAutofill()
        }
        return 0
    }

    fun getLabelFor(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            return view.getLabelFor()
        }
        return 0
    }

    @Deprecated
    fun getLayerType(View view) {
        return view.getLayerType()
    }

    fun getLayoutDirection(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            return view.getLayoutDirection()
        }
        return 0
    }

    @Nullable
    @Deprecated
    fun getMatrix(View view) {
        return view.getMatrix()
    }

    @Deprecated
    fun getMeasuredHeightAndState(View view) {
        return view.getMeasuredHeightAndState()
    }

    @Deprecated
    fun getMeasuredState(View view) {
        return view.getMeasuredState()
    }

    @Deprecated
    fun getMeasuredWidthAndState(View view) {
        return view.getMeasuredWidthAndState()
    }

    fun getMinimumHeight(@NonNull View view) throws NoSuchFieldException {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.getMinimumHeight()
        }
        if (!sMinHeightFieldFetched) {
            try {
                Field declaredField = View.class.getDeclaredField("mMinHeight")
                sMinHeightField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
            }
            sMinHeightFieldFetched = true
        }
        if (sMinHeightField != null) {
            try {
                return ((Integer) sMinHeightField.get(view)).intValue()
            } catch (Exception e2) {
            }
        }
        return 0
    }

    fun getMinimumWidth(@NonNull View view) throws NoSuchFieldException {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.getMinimumWidth()
        }
        if (!sMinWidthFieldFetched) {
            try {
                Field declaredField = View.class.getDeclaredField("mMinWidth")
                sMinWidthField = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
            }
            sMinWidthFieldFetched = true
        }
        if (sMinWidthField != null) {
            try {
                return ((Integer) sMinWidthField.get(view)).intValue()
            } catch (Exception e2) {
            }
        }
        return 0
    }

    fun getNextClusterForwardId(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.getNextClusterForwardId()
        }
        return -1
    }

    @Deprecated
    fun getOverScrollMode(View view) {
        return view.getOverScrollMode()
    }

    @Px
    fun getPaddingEnd(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 17 ? view.getPaddingEnd() : view.getPaddingRight()
    }

    @Px
    fun getPaddingStart(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 17 ? view.getPaddingStart() : view.getPaddingLeft()
    }

    fun getParentForAccessibility(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 16 ? view.getParentForAccessibility() : view.getParent()
    }

    @Deprecated
    fun getPivotX(View view) {
        return view.getPivotX()
    }

    @Deprecated
    fun getPivotY(View view) {
        return view.getPivotY()
    }

    @Deprecated
    fun getRotation(View view) {
        return view.getRotation()
    }

    @Deprecated
    fun getRotationX(View view) {
        return view.getRotationX()
    }

    @Deprecated
    fun getRotationY(View view) {
        return view.getRotationY()
    }

    @Deprecated
    fun getScaleX(View view) {
        return view.getScaleX()
    }

    @Deprecated
    fun getScaleY(View view) {
        return view.getScaleY()
    }

    fun getScrollIndicators(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            return view.getScrollIndicators()
        }
        return 0
    }

    @Nullable
    fun getTransitionName(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getTransitionName()
        }
        if (sTransitionNameMap == null) {
            return null
        }
        return (String) sTransitionNameMap.get(view)
    }

    @Deprecated
    fun getTranslationX(View view) {
        return view.getTranslationX()
    }

    @Deprecated
    fun getTranslationY(View view) {
        return view.getTranslationY()
    }

    fun getTranslationZ(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getTranslationZ()
        }
        return 0.0f
    }

    fun getWindowSystemUiVisibility(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.getWindowSystemUiVisibility()
        }
        return 0
    }

    @Deprecated
    fun getX(View view) {
        return view.getX()
    }

    @Deprecated
    fun getY(View view) {
        return view.getY()
    }

    fun getZ(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getZ()
        }
        return 0.0f
    }

    fun hasAccessibilityDelegate(@NonNull View view) {
        if (sAccessibilityDelegateCheckFailed) {
            return false
        }
        if (sAccessibilityDelegateField == null) {
            try {
                Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate")
                sAccessibilityDelegateField = declaredField
                declaredField.setAccessible(true)
            } catch (Throwable th) {
                sAccessibilityDelegateCheckFailed = true
                return false
            }
        }
        try {
            return sAccessibilityDelegateField.get(view) != null
        } catch (Throwable th2) {
            sAccessibilityDelegateCheckFailed = true
            return false
        }
    }

    fun hasExplicitFocusable(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 26 ? view.hasExplicitFocusable() : view.hasFocusable()
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun hasNestedScrollingParent(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.hasNestedScrollingParent()
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).hasNestedScrollingParent()
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun hasNestedScrollingParent(@NonNull View view, Int i) {
        if (view is NestedScrollingChild2) {
            ((NestedScrollingChild2) view).hasNestedScrollingParent(i)
        } else if (i == 0) {
            return hasNestedScrollingParent(view)
        }
        return false
    }

    fun hasOnClickListeners(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 15) {
            return view.hasOnClickListeners()
        }
        return false
    }

    fun hasOverlappingRendering(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.hasOverlappingRendering()
        }
        return true
    }

    fun hasTransientState(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.hasTransientState()
        }
        return false
    }

    fun isAttachedToWindow(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 19 ? view.isAttachedToWindow() : view.getWindowToken() != null
    }

    fun isFocusedByDefault(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.isFocusedByDefault()
        }
        return false
    }

    fun isImportantForAccessibility(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.isImportantForAccessibility()
        }
        return true
    }

    fun isImportantForAutofill(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.isImportantForAutofill()
        }
        return true
    }

    fun isInLayout(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return view.isInLayout()
        }
        return false
    }

    fun isKeyboardNavigationCluster(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.isKeyboardNavigationCluster()
        }
        return false
    }

    fun isLaidOut(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 19 ? view.isLaidOut() : view.getWidth() > 0 && view.getHeight() > 0
    }

    fun isLayoutDirectionResolved(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return view.isLayoutDirectionResolved()
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun isNestedScrollingEnabled(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.isNestedScrollingEnabled()
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).isNestedScrollingEnabled()
        }
        return false
    }

    @Deprecated
    fun isOpaque(View view) {
        return view.isOpaque()
    }

    fun isPaddingRelative(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            return view.isPaddingRelative()
        }
        return false
    }

    @Deprecated
    fun jumpDrawablesToCurrentState(View view) {
        view.jumpDrawablesToCurrentState()
    }

    fun keyboardNavigationClusterSearch(@NonNull View view, View view2, Int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            return view.keyboardNavigationClusterSearch(view2, i)
        }
        return null
    }

    fun offsetLeftAndRight(@NonNull View view, Int i) {
        Boolean z
        if (Build.VERSION.SDK_INT >= 23) {
            view.offsetLeftAndRight(i)
            return
        }
        if (Build.VERSION.SDK_INT < 21) {
            compatOffsetLeftAndRight(view, i)
            return
        }
        Rect emptyTempRect = getEmptyTempRect()
        Object parent = view.getParent()
        if (parent is View) {
            View view2 = (View) parent
            emptyTempRect.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom())
            z = !emptyTempRect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())
        } else {
            z = false
        }
        compatOffsetLeftAndRight(view, i)
        if (z && emptyTempRect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
            ((View) parent).invalidate(emptyTempRect)
        }
    }

    fun offsetTopAndBottom(@NonNull View view, Int i) {
        Boolean z
        if (Build.VERSION.SDK_INT >= 23) {
            view.offsetTopAndBottom(i)
            return
        }
        if (Build.VERSION.SDK_INT < 21) {
            compatOffsetTopAndBottom(view, i)
            return
        }
        Rect emptyTempRect = getEmptyTempRect()
        Object parent = view.getParent()
        if (parent is View) {
            View view2 = (View) parent
            emptyTempRect.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom())
            z = !emptyTempRect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())
        } else {
            z = false
        }
        compatOffsetTopAndBottom(view, i)
        if (z && emptyTempRect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
            ((View) parent).invalidate(emptyTempRect)
        }
    }

    fun onApplyWindowInsets(@NonNull View view, WindowInsetsCompat windowInsetsCompat) {
        if (Build.VERSION.SDK_INT < 21) {
            return windowInsetsCompat
        }
        WindowInsets windowInsets = (WindowInsets) WindowInsetsCompat.unwrap(windowInsetsCompat)
        WindowInsets windowInsetsOnApplyWindowInsets = view.onApplyWindowInsets(windowInsets)
        if (windowInsetsOnApplyWindowInsets != windowInsets) {
            windowInsets = WindowInsets(windowInsetsOnApplyWindowInsets)
        }
        return WindowInsetsCompat.wrap(windowInsets)
    }

    @Deprecated
    fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        view.onInitializeAccessibilityEvent(accessibilityEvent)
    }

    fun onInitializeAccessibilityNodeInfo(@NonNull View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat.unwrap())
    }

    @Deprecated
    fun onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        view.onPopulateAccessibilityEvent(accessibilityEvent)
    }

    fun performAccessibilityAction(@NonNull View view, Int i, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return view.performAccessibilityAction(i, bundle)
        }
        return false
    }

    fun postInvalidateOnAnimation(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation()
        } else {
            view.postInvalidate()
        }
    }

    fun postInvalidateOnAnimation(@NonNull View view, Int i, Int i2, Int i3, Int i4) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postInvalidateOnAnimation(i, i2, i3, i4)
        } else {
            view.postInvalidate(i, i2, i3, i4)
        }
    }

    fun postOnAnimation(@NonNull View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable)
        } else {
            view.postDelayed(runnable, ValueAnimator.getFrameDelay())
        }
    }

    fun postOnAnimationDelayed(@NonNull View view, Runnable runnable, Long j) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postOnAnimationDelayed(runnable, j)
        } else {
            view.postDelayed(runnable, ValueAnimator.getFrameDelay() + j)
        }
    }

    fun removeOnUnhandledKeyEventListener(@NonNull View view, @NonNull OnUnhandledKeyEventListenerCompat onUnhandledKeyEventListenerCompat) {
        View.OnUnhandledKeyEventListener onUnhandledKeyEventListener
        if (Build.VERSION.SDK_INT >= 28) {
            Map map = (Map) view.getTag(R.id.tag_unhandled_key_listeners)
            if (map == null || (onUnhandledKeyEventListener = (View.OnUnhandledKeyEventListener) map.get(onUnhandledKeyEventListenerCompat)) == null) {
                return
            }
            view.removeOnUnhandledKeyEventListener(onUnhandledKeyEventListener)
            return
        }
        ArrayList arrayList = (ArrayList) view.getTag(R.id.tag_unhandled_key_listeners)
        if (arrayList != null) {
            arrayList.remove(onUnhandledKeyEventListenerCompat)
            if (arrayList.size() == 0) {
                UnhandledKeyEventManager.unregisterListeningView(view)
            }
        }
    }

    fun requestApplyInsets(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 20) {
            view.requestApplyInsets()
        } else if (Build.VERSION.SDK_INT >= 16) {
            view.requestFitSystemWindows()
        }
    }

    @NonNull
    fun requireViewById(@NonNull View view, @IdRes Int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            return view.requireViewById(i)
        }
        View viewFindViewById = view.findViewById(i)
        if (viewFindViewById == null) {
            throw IllegalArgumentException("ID does not reference a View inside this View")
        }
        return viewFindViewById
    }

    @Deprecated
    fun resolveSizeAndState(Int i, Int i2, Int i3) {
        return View.resolveSizeAndState(i, i2, i3)
    }

    fun restoreDefaultFocus(@NonNull View view) {
        return Build.VERSION.SDK_INT >= 26 ? view.restoreDefaultFocus() : view.requestFocus()
    }

    fun setAccessibilityDelegate(@NonNull View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        view.setAccessibilityDelegate(accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.getBridge())
    }

    fun setAccessibilityLiveRegion(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            view.setAccessibilityLiveRegion(i)
        }
    }

    @Deprecated
    fun setActivated(View view, Boolean z) {
        view.setActivated(z)
    }

    @Deprecated
    fun setAlpha(View view, @FloatRange(from = 0.0d, to = 1.0d) Float f) {
        view.setAlpha(f)
    }

    fun setAutofillHints(@NonNull View view, @Nullable String... strArr) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setAutofillHints(strArr)
        }
    }

    fun setBackground(@NonNull View view, @Nullable Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable)
        } else {
            view.setBackgroundDrawable(drawable)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setBackgroundTintList(@NonNull View view, ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT < 21) {
            if (view is TintableBackgroundView) {
                ((TintableBackgroundView) view).setSupportBackgroundTintList(colorStateList)
                return
            }
            return
        }
        view.setBackgroundTintList(colorStateList)
        if (Build.VERSION.SDK_INT == 21) {
            Drawable background = view.getBackground()
            Boolean z = (view.getBackgroundTintList() == null && view.getBackgroundTintMode() == null) ? false : true
            if (background == null || !z) {
                return
            }
            if (background.isStateful()) {
                background.setState(view.getDrawableState())
            }
            view.setBackground(background)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setBackgroundTintMode(@NonNull View view, PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT < 21) {
            if (view is TintableBackgroundView) {
                ((TintableBackgroundView) view).setSupportBackgroundTintMode(mode)
                return
            }
            return
        }
        view.setBackgroundTintMode(mode)
        if (Build.VERSION.SDK_INT == 21) {
            Drawable background = view.getBackground()
            Boolean z = (view.getBackgroundTintList() == null && view.getBackgroundTintMode() == null) ? false : true
            if (background == null || !z) {
                return
            }
            if (background.isStateful()) {
                background.setState(view.getDrawableState())
            }
            view.setBackground(background)
        }
    }

    @Deprecated
    fun setChildrenDrawingOrderEnabled(ViewGroup viewGroup, Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (sChildrenDrawingOrderMethod == null) {
            try {
                sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE)
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "Unable to find childrenDrawingOrderEnabled", e)
            }
            sChildrenDrawingOrderMethod.setAccessible(true)
        }
        try {
            sChildrenDrawingOrderMethod.invoke(viewGroup, Boolean.valueOf(z))
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", e2)
        } catch (IllegalArgumentException e3) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", e3)
        } catch (InvocationTargetException e4) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", e4)
        }
    }

    fun setClipBounds(@NonNull View view, Rect rect) {
        if (Build.VERSION.SDK_INT >= 18) {
            view.setClipBounds(rect)
        }
    }

    fun setElevation(@NonNull View view, Float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setElevation(f)
        }
    }

    @Deprecated
    fun setFitsSystemWindows(View view, Boolean z) {
        view.setFitsSystemWindows(z)
    }

    fun setFocusedByDefault(@NonNull View view, Boolean z) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setFocusedByDefault(z)
        }
    }

    fun setHasTransientState(@NonNull View view, Boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setHasTransientState(z)
        }
    }

    fun setImportantForAccessibility(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT < 19) {
            if (Build.VERSION.SDK_INT < 16) {
                return
            }
            if (i == 4) {
                i = 2
            }
        }
        view.setImportantForAccessibility(i)
    }

    fun setImportantForAutofill(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setImportantForAutofill(i)
        }
    }

    fun setKeyboardNavigationCluster(@NonNull View view, Boolean z) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setKeyboardNavigationCluster(z)
        }
    }

    fun setLabelFor(@NonNull View view, @IdRes Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            view.setLabelFor(i)
        }
    }

    fun setLayerPaint(@NonNull View view, Paint paint) {
        if (Build.VERSION.SDK_INT >= 17) {
            view.setLayerPaint(paint)
        } else {
            view.setLayerType(view.getLayerType(), paint)
            view.invalidate()
        }
    }

    @Deprecated
    fun setLayerType(View view, Int i, Paint paint) {
        view.setLayerType(i, paint)
    }

    fun setLayoutDirection(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            view.setLayoutDirection(i)
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun setNestedScrollingEnabled(@NonNull View view, Boolean z) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setNestedScrollingEnabled(z)
        } else if (view is NestedScrollingChild) {
            ((NestedScrollingChild) view).setNestedScrollingEnabled(z)
        }
    }

    fun setNextClusterForwardId(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setNextClusterForwardId(i)
        }
    }

    fun setOnApplyWindowInsetsListener(@NonNull View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (onApplyWindowInsetsListener == null) {
                view.setOnApplyWindowInsetsListener(null)
            } else {
                view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: android.support.v4.view.ViewCompat.1
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public final WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                        return (WindowInsets) WindowInsetsCompat.unwrap(onApplyWindowInsetsListener.onApplyWindowInsets(view2, WindowInsetsCompat.wrap(windowInsets)))
                    }
                })
            }
        }
    }

    @Deprecated
    fun setOverScrollMode(View view, Int i) {
        view.setOverScrollMode(i)
    }

    fun setPaddingRelative(@NonNull View view, @Px Int i, @Px Int i2, @Px Int i3, @Px Int i4) {
        if (Build.VERSION.SDK_INT >= 17) {
            view.setPaddingRelative(i, i2, i3, i4)
        } else {
            view.setPadding(i, i2, i3, i4)
        }
    }

    @Deprecated
    fun setPivotX(View view, Float f) {
        view.setPivotX(f)
    }

    @Deprecated
    fun setPivotY(View view, Float f) {
        view.setPivotY(f)
    }

    fun setPointerIcon(@NonNull View view, PointerIconCompat pointerIconCompat) {
        if (Build.VERSION.SDK_INT >= 24) {
            view.setPointerIcon((PointerIcon) (pointerIconCompat != null ? pointerIconCompat.getPointerIcon() : null))
        }
    }

    @Deprecated
    fun setRotation(View view, Float f) {
        view.setRotation(f)
    }

    @Deprecated
    fun setRotationX(View view, Float f) {
        view.setRotationX(f)
    }

    @Deprecated
    fun setRotationY(View view, Float f) {
        view.setRotationY(f)
    }

    @Deprecated
    fun setSaveFromParentEnabled(View view, Boolean z) {
        view.setSaveFromParentEnabled(z)
    }

    @Deprecated
    fun setScaleX(View view, Float f) {
        view.setScaleX(f)
    }

    @Deprecated
    fun setScaleY(View view, Float f) {
        view.setScaleY(f)
    }

    fun setScrollIndicators(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            view.setScrollIndicators(i)
        }
    }

    fun setScrollIndicators(@NonNull View view, Int i, Int i2) {
        if (Build.VERSION.SDK_INT >= 23) {
            view.setScrollIndicators(i, i2)
        }
    }

    fun setTooltipText(@NonNull View view, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(charSequence)
        }
    }

    fun setTransitionName(@NonNull View view, String str) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setTransitionName(str)
            return
        }
        if (sTransitionNameMap == null) {
            sTransitionNameMap = WeakHashMap()
        }
        sTransitionNameMap.put(view, str)
    }

    @Deprecated
    fun setTranslationX(View view, Float f) {
        view.setTranslationX(f)
    }

    @Deprecated
    fun setTranslationY(View view, Float f) {
        view.setTranslationY(f)
    }

    fun setTranslationZ(@NonNull View view, Float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setTranslationZ(f)
        }
    }

    @Deprecated
    fun setX(View view, Float f) {
        view.setX(f)
    }

    @Deprecated
    fun setY(View view, Float f) {
        view.setY(f)
    }

    fun setZ(@NonNull View view, Float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setZ(f)
        }
    }

    fun startDragAndDrop(@NonNull View view, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object obj, Int i) {
        return Build.VERSION.SDK_INT >= 24 ? view.startDragAndDrop(clipData, dragShadowBuilder, obj, i) : view.startDrag(clipData, dragShadowBuilder, obj, i)
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun startNestedScroll(@NonNull View view, Int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.startNestedScroll(i)
        }
        if (view is NestedScrollingChild) {
            return ((NestedScrollingChild) view).startNestedScroll(i)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun startNestedScroll(@NonNull View view, Int i, Int i2) {
        if (view is NestedScrollingChild2) {
            return ((NestedScrollingChild2) view).startNestedScroll(i, i2)
        }
        if (i2 == 0) {
            return startNestedScroll(view, i)
        }
        return false
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun stopNestedScroll(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.stopNestedScroll()
        } else if (view is NestedScrollingChild) {
            ((NestedScrollingChild) view).stopNestedScroll()
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun stopNestedScroll(@NonNull View view, Int i) {
        if (view is NestedScrollingChild2) {
            ((NestedScrollingChild2) view).stopNestedScroll(i)
        } else if (i == 0) {
            stopNestedScroll(view)
        }
    }

    private fun tickleInvalidationFlag(View view) {
        Float translationY = view.getTranslationY()
        view.setTranslationY(1.0f + translationY)
        view.setTranslationY(translationY)
    }

    fun updateDragShadow(@NonNull View view, View.DragShadowBuilder dragShadowBuilder) {
        if (Build.VERSION.SDK_INT >= 24) {
            view.updateDragShadow(dragShadowBuilder)
        }
    }
}
