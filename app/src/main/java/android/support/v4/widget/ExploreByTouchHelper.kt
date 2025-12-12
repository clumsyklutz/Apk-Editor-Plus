package android.support.v4.widget

import android.graphics.Rect
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.collection.SparseArrayCompat
import android.support.v4.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewParentCompat
import androidx.core.view.accessibility.AccessibilityEventCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
import android.support.v4.view.accessibility.AccessibilityRecordCompat
import android.support.v4.widget.FocusStrategy
import androidx.appcompat.R
import android.support.v7.widget.ActivityChooserView
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewParent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import java.util.ArrayList
import java.util.List

abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    private static val DEFAULT_CLASS_NAME = "android.view.View"
    public static val HOST_ID = -1
    public static val INVALID_ID = Integer.MIN_VALUE
    private static val INVALID_PARENT_BOUNDS = Rect(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Integer.MIN_VALUE, Integer.MIN_VALUE)
    private static final FocusStrategy.BoundsAdapter NODE_ADAPTER = new FocusStrategy.BoundsAdapter() { // from class: android.support.v4.widget.ExploreByTouchHelper.1
        @Override // android.support.v4.widget.FocusStrategy.BoundsAdapter
        public final Unit obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            accessibilityNodeInfoCompat.getBoundsInParent(rect)
        }
    }
    private static final FocusStrategy.CollectionAdapter SPARSE_VALUES_ADAPTER = new FocusStrategy.CollectionAdapter() { // from class: android.support.v4.widget.ExploreByTouchHelper.2
        @Override // android.support.v4.widget.FocusStrategy.CollectionAdapter
        public final AccessibilityNodeInfoCompat get(SparseArrayCompat sparseArrayCompat, Int i) {
            return (AccessibilityNodeInfoCompat) sparseArrayCompat.valueAt(i)
        }

        @Override // android.support.v4.widget.FocusStrategy.CollectionAdapter
        public final Int size(SparseArrayCompat sparseArrayCompat) {
            return sparseArrayCompat.size()
        }
    }
    private final View mHost
    private final AccessibilityManager mManager
    private MyNodeProvider mNodeProvider
    private val mTempScreenRect = Rect()
    private val mTempParentRect = Rect()
    private val mTempVisibleRect = Rect()
    private final Array<Int> mTempGlobalRect = new Int[2]
    Int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE
    Int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE
    private Int mHoveredVirtualViewId = Integer.MIN_VALUE

    class MyNodeProvider extends AccessibilityNodeProviderCompat {
        MyNodeProvider() {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
        fun createAccessibilityNodeInfo(Int i) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(i))
        }

        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
        fun findFocus(Int i) {
            Int i2 = i == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId
            if (i2 == Integer.MIN_VALUE) {
                return null
            }
            return createAccessibilityNodeInfo(i2)
        }

        @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
        fun performAction(Int i, Int i2, Bundle bundle) {
            return ExploreByTouchHelper.this.performAction(i, i2, bundle)
        }
    }

    constructor(@NonNull View view) {
        if (view == null) {
            throw IllegalArgumentException("View may not be null")
        }
        this.mHost = view
        this.mManager = (AccessibilityManager) view.getContext().getSystemService("accessibility")
        view.setFocusable(true)
        if (ViewCompat.getImportantForAccessibility(view) == 0) {
            ViewCompat.setImportantForAccessibility(view, 1)
        }
    }

    private fun clearAccessibilityFocus(Int i) {
        if (this.mAccessibilityFocusedVirtualViewId != i) {
            return false
        }
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE
        this.mHost.invalidate()
        sendEventForVirtualView(i, 65536)
        return true
    }

    private fun clickKeyboardFocusedVirtualView() {
        return this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE && onPerformActionForVirtualView(this.mKeyboardFocusedVirtualViewId, 16, null)
    }

    private fun createEvent(Int i, Int i2) {
        switch (i) {
            case -1:
                return createEventForHost(i2)
            default:
                return createEventForChild(i, i2)
        }
    }

    private fun createEventForChild(Int i, Int i2) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2)
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(i)
        accessibilityEventObtain.getText().add(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getText())
        accessibilityEventObtain.setContentDescription(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getContentDescription())
        accessibilityEventObtain.setScrollable(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isScrollable())
        accessibilityEventObtain.setPassword(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isPassword())
        accessibilityEventObtain.setEnabled(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isEnabled())
        accessibilityEventObtain.setChecked(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isChecked())
        onPopulateEventForVirtualView(i, accessibilityEventObtain)
        if (accessibilityEventObtain.getText().isEmpty() && accessibilityEventObtain.getContentDescription() == null) {
            throw RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()")
        }
        accessibilityEventObtain.setClassName(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getClassName())
        AccessibilityRecordCompat.setSource(accessibilityEventObtain, this.mHost, i)
        accessibilityEventObtain.setPackageName(this.mHost.getContext().getPackageName())
        return accessibilityEventObtain
    }

    private fun createEventForHost(Int i) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i)
        this.mHost.onInitializeAccessibilityEvent(accessibilityEventObtain)
        return accessibilityEventObtain
    }

    @NonNull
    private fun createNodeForChild(Int i) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain()
        accessibilityNodeInfoCompatObtain.setEnabled(true)
        accessibilityNodeInfoCompatObtain.setFocusable(true)
        accessibilityNodeInfoCompatObtain.setClassName(DEFAULT_CLASS_NAME)
        accessibilityNodeInfoCompatObtain.setBoundsInParent(INVALID_PARENT_BOUNDS)
        accessibilityNodeInfoCompatObtain.setBoundsInScreen(INVALID_PARENT_BOUNDS)
        accessibilityNodeInfoCompatObtain.setParent(this.mHost)
        onPopulateNodeForVirtualView(i, accessibilityNodeInfoCompatObtain)
        if (accessibilityNodeInfoCompatObtain.getText() == null && accessibilityNodeInfoCompatObtain.getContentDescription() == null) {
            throw RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()")
        }
        accessibilityNodeInfoCompatObtain.getBoundsInParent(this.mTempParentRect)
        if (this.mTempParentRect.equals(INVALID_PARENT_BOUNDS)) {
            throw RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()")
        }
        Int actions = accessibilityNodeInfoCompatObtain.getActions()
        if ((actions & 64) != 0) {
            throw RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()")
        }
        if ((actions & 128) != 0) {
            throw RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()")
        }
        accessibilityNodeInfoCompatObtain.setPackageName(this.mHost.getContext().getPackageName())
        accessibilityNodeInfoCompatObtain.setSource(this.mHost, i)
        if (this.mAccessibilityFocusedVirtualViewId == i) {
            accessibilityNodeInfoCompatObtain.setAccessibilityFocused(true)
            accessibilityNodeInfoCompatObtain.addAction(128)
        } else {
            accessibilityNodeInfoCompatObtain.setAccessibilityFocused(false)
            accessibilityNodeInfoCompatObtain.addAction(64)
        }
        Boolean z = this.mKeyboardFocusedVirtualViewId == i
        if (z) {
            accessibilityNodeInfoCompatObtain.addAction(2)
        } else if (accessibilityNodeInfoCompatObtain.isFocusable()) {
            accessibilityNodeInfoCompatObtain.addAction(1)
        }
        accessibilityNodeInfoCompatObtain.setFocused(z)
        this.mHost.getLocationOnScreen(this.mTempGlobalRect)
        accessibilityNodeInfoCompatObtain.getBoundsInScreen(this.mTempScreenRect)
        if (this.mTempScreenRect.equals(INVALID_PARENT_BOUNDS)) {
            accessibilityNodeInfoCompatObtain.getBoundsInParent(this.mTempScreenRect)
            if (accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain2 = AccessibilityNodeInfoCompat.obtain()
                for (Int i2 = accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId; i2 != -1; i2 = accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId) {
                    accessibilityNodeInfoCompatObtain2.setParent(this.mHost, -1)
                    accessibilityNodeInfoCompatObtain2.setBoundsInParent(INVALID_PARENT_BOUNDS)
                    onPopulateNodeForVirtualView(i2, accessibilityNodeInfoCompatObtain2)
                    accessibilityNodeInfoCompatObtain2.getBoundsInParent(this.mTempParentRect)
                    this.mTempScreenRect.offset(this.mTempParentRect.left, this.mTempParentRect.top)
                }
                accessibilityNodeInfoCompatObtain2.recycle()
            }
            this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY())
        }
        if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
            this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY())
            if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                accessibilityNodeInfoCompatObtain.setBoundsInScreen(this.mTempScreenRect)
                if (isVisibleToUser(this.mTempScreenRect)) {
                    accessibilityNodeInfoCompatObtain.setVisibleToUser(true)
                }
            }
        }
        return accessibilityNodeInfoCompatObtain
    }

    @NonNull
    private fun createNodeForHost() {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(this.mHost)
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, accessibilityNodeInfoCompatObtain)
        ArrayList arrayList = ArrayList()
        getVisibleVirtualViews(arrayList)
        if (accessibilityNodeInfoCompatObtain.getChildCount() > 0 && arrayList.size() > 0) {
            throw RuntimeException("Views cannot have both real and virtual children")
        }
        Int size = arrayList.size()
        for (Int i = 0; i < size; i++) {
            accessibilityNodeInfoCompatObtain.addChild(this.mHost, ((Integer) arrayList.get(i)).intValue())
        }
        return accessibilityNodeInfoCompatObtain
    }

    private fun getAllNodes() {
        ArrayList arrayList = ArrayList()
        getVisibleVirtualViews(arrayList)
        SparseArrayCompat sparseArrayCompat = SparseArrayCompat()
        for (Int i = 0; i < arrayList.size(); i++) {
            sparseArrayCompat.put(i, createNodeForChild(i))
        }
        return sparseArrayCompat
    }

    private fun getBoundsInParent(Int i, Rect rect) {
        obtainAccessibilityNodeInfo(i).getBoundsInParent(rect)
    }

    private fun guessPreviouslyFocusedRect(@NonNull View view, Int i, @NonNull Rect rect) {
        Int width = view.getWidth()
        Int height = view.getHeight()
        switch (i) {
            case 17:
                rect.set(width, 0, width, height)
                return rect
            case 33:
                rect.set(0, height, width, height)
                return rect
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                rect.set(-1, 0, -1, height)
                return rect
            case 130:
                rect.set(0, -1, width, -1)
                return rect
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
    }

    private fun isVisibleToUser(Rect rect) {
        if (rect == null || rect.isEmpty()) {
            return false
        }
        if (this.mHost.getWindowVisibility() != 0) {
            return false
        }
        Object parent = this.mHost.getParent()
        while (parent is View) {
            View view = (View) parent
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false
            }
            parent = view.getParent()
        }
        return parent != null
    }

    private fun keyToDirection(Int i) {
        switch (i) {
            case 19:
                return 33
            case 20:
            default:
                return 130
            case 21:
                return 17
            case 22:
                return 66
        }
    }

    private fun moveFocus(Int i, @Nullable Rect rect) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat
        SparseArrayCompat allNodes = getAllNodes()
        Int i2 = this.mKeyboardFocusedVirtualViewId
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = i2 == Integer.MIN_VALUE ? null : (AccessibilityNodeInfoCompat) allNodes.get(i2)
        switch (i) {
            case 1:
            case 2:
                accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInRelativeDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, accessibilityNodeInfoCompat2, i, ViewCompat.getLayoutDirection(this.mHost) == 1, false)
                break
            case 17:
            case 33:
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
            case 130:
                Rect rect2 = Rect()
                if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
                    getBoundsInParent(this.mKeyboardFocusedVirtualViewId, rect2)
                } else if (rect != null) {
                    rect2.set(rect)
                } else {
                    guessPreviouslyFocusedRect(this.mHost, i, rect2)
                }
                accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInAbsoluteDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, accessibilityNodeInfoCompat2, rect2, i)
                break
            default:
                throw IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.")
        }
        return requestKeyboardFocusForVirtualView(accessibilityNodeInfoCompat == null ? Integer.MIN_VALUE : allNodes.keyAt(allNodes.indexOfValue(accessibilityNodeInfoCompat)))
    }

    private fun performActionForChild(Int i, Int i2, Bundle bundle) {
        switch (i2) {
            case 1:
                return requestKeyboardFocusForVirtualView(i)
            case 2:
                return clearKeyboardFocusForVirtualView(i)
            case 64:
                return requestAccessibilityFocus(i)
            case 128:
                return clearAccessibilityFocus(i)
            default:
                return onPerformActionForVirtualView(i, i2, bundle)
        }
    }

    private fun performActionForHost(Int i, Bundle bundle) {
        return ViewCompat.performAccessibilityAction(this.mHost, i, bundle)
    }

    private fun requestAccessibilityFocus(Int i) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled() || this.mAccessibilityFocusedVirtualViewId == i) {
            return false
        }
        if (this.mAccessibilityFocusedVirtualViewId != Integer.MIN_VALUE) {
            clearAccessibilityFocus(this.mAccessibilityFocusedVirtualViewId)
        }
        this.mAccessibilityFocusedVirtualViewId = i
        this.mHost.invalidate()
        sendEventForVirtualView(i, 32768)
        return true
    }

    private fun updateHoveredVirtualView(Int i) {
        if (this.mHoveredVirtualViewId == i) {
            return
        }
        Int i2 = this.mHoveredVirtualViewId
        this.mHoveredVirtualViewId = i
        sendEventForVirtualView(i, 128)
        sendEventForVirtualView(i2, 256)
    }

    public final Boolean clearKeyboardFocusForVirtualView(Int i) {
        if (this.mKeyboardFocusedVirtualViewId != i) {
            return false
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE
        onVirtualViewKeyboardFocusChanged(i, false)
        sendEventForVirtualView(i, 8)
        return true
    }

    public final Boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
            return false
        }
        switch (motionEvent.getAction()) {
            case 7:
            case 9:
                Int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY())
                updateHoveredVirtualView(virtualViewAt)
                if (virtualViewAt == Integer.MIN_VALUE) {
                    break
                }
                break
            case 10:
                if (this.mHoveredVirtualViewId == Integer.MIN_VALUE) {
                    break
                } else {
                    updateHoveredVirtualView(Integer.MIN_VALUE)
                    break
                }
        }
        return false
    }

    public final Boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        Boolean z = false
        if (keyEvent.getAction() == 1) {
            return false
        }
        Int keyCode = keyEvent.getKeyCode()
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
                if (keyEvent.hasNoModifiers()) {
                    Int iKeyToDirection = keyToDirection(keyCode)
                    Int repeatCount = keyEvent.getRepeatCount() + 1
                    Int i = 0
                    while (i < repeatCount && moveFocus(iKeyToDirection, null)) {
                        i++
                        z = true
                    }
                }
                break
            case 23:
            case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                if (keyEvent.hasNoModifiers() && keyEvent.getRepeatCount() == 0) {
                    clickKeyboardFocusedVirtualView()
                    break
                }
                break
            case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
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

    public final Int getAccessibilityFocusedVirtualViewId() {
        return this.mAccessibilityFocusedVirtualViewId
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    fun getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = MyNodeProvider()
        }
        return this.mNodeProvider
    }

    @Deprecated
    fun getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId()
    }

    public final Int getKeyboardFocusedVirtualViewId() {
        return this.mKeyboardFocusedVirtualViewId
    }

    protected abstract Int getVirtualViewAt(Float f, Float f2)

    protected abstract Unit getVisibleVirtualViews(List list)

    public final Unit invalidateRoot() {
        invalidateVirtualView(-1, 1)
    }

    public final Unit invalidateVirtualView(Int i) {
        invalidateVirtualView(i, 0)
    }

    public final Unit invalidateVirtualView(Int i, Int i2) {
        ViewParent parent
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return
        }
        AccessibilityEvent accessibilityEventCreateEvent = createEvent(i, 2048)
        AccessibilityEventCompat.setContentChangeTypes(accessibilityEventCreateEvent, i2)
        ViewParentCompat.requestSendAccessibilityEvent(parent, this.mHost, accessibilityEventCreateEvent)
    }

    @NonNull
    AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(Int i) {
        return i == -1 ? createNodeForHost() : createNodeForChild(i)
    }

    public final Unit onFocusChanged(Boolean z, Int i, @Nullable Rect rect) {
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId)
        }
        if (z) {
            moveFocus(i, rect)
        }
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    fun onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent)
        onPopulateEventForHost(accessibilityEvent)
    }

    @Override // android.support.v4.view.AccessibilityDelegateCompat
    fun onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat)
        onPopulateNodeForHost(accessibilityNodeInfoCompat)
    }

    protected abstract Boolean onPerformActionForVirtualView(Int i, Int i2, @Nullable Bundle bundle)

    protected fun onPopulateEventForHost(@NonNull AccessibilityEvent accessibilityEvent) {
    }

    protected fun onPopulateEventForVirtualView(Int i, @NonNull AccessibilityEvent accessibilityEvent) {
    }

    protected fun onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    protected abstract Unit onPopulateNodeForVirtualView(Int i, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat)

    protected fun onVirtualViewKeyboardFocusChanged(Int i, Boolean z) {
    }

    Boolean performAction(Int i, Int i2, Bundle bundle) {
        switch (i) {
            case -1:
                return performActionForHost(i2, bundle)
            default:
                return performActionForChild(i, i2, bundle)
        }
    }

    public final Boolean requestKeyboardFocusForVirtualView(Int i) {
        if ((!this.mHost.isFocused() && !this.mHost.requestFocus()) || this.mKeyboardFocusedVirtualViewId == i) {
            return false
        }
        if (this.mKeyboardFocusedVirtualViewId != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(this.mKeyboardFocusedVirtualViewId)
        }
        this.mKeyboardFocusedVirtualViewId = i
        onVirtualViewKeyboardFocusChanged(i, true)
        sendEventForVirtualView(i, 8)
        return true
    }

    public final Boolean sendEventForVirtualView(Int i, Int i2) {
        ViewParent parent
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return false
        }
        return ViewParentCompat.requestSendAccessibilityEvent(parent, this.mHost, createEvent(i, i2))
    }
}
