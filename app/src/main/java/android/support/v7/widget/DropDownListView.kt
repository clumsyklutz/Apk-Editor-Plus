package android.support.v7.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v4.widget.ListViewAutoScrollHelper
import androidx.appcompat.R
import android.support.v7.graphics.drawable.DrawableWrapper
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ListAdapter
import android.widget.ListView
import java.lang.reflect.Field

class DropDownListView extends ListView {
    public static val INVALID_POSITION = -1
    public static val NO_POSITION = -1
    private ViewPropertyAnimatorCompat mClickAnimation
    private Boolean mDrawsInPressedState
    private Boolean mHijackFocus
    private Field mIsChildViewEnabled
    private Boolean mListSelectionHidden
    private Int mMotionPosition
    ResolveHoverRunnable mResolveHoverRunnable
    private ListViewAutoScrollHelper mScrollHelper
    private Int mSelectionBottomPadding
    private Int mSelectionLeftPadding
    private Int mSelectionRightPadding
    private Int mSelectionTopPadding
    private GateKeeperDrawable mSelector
    private final Rect mSelectorRect

    class GateKeeperDrawable extends DrawableWrapper {
        private Boolean mEnabled

        GateKeeperDrawable(Drawable drawable) {
            super(drawable)
            this.mEnabled = true
        }

        @Override // android.support.v7.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        fun draw(Canvas canvas) {
            if (this.mEnabled) {
                super.draw(canvas)
            }
        }

        Unit setEnabled(Boolean z) {
            this.mEnabled = z
        }

        @Override // android.support.v7.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        fun setHotspot(Float f, Float f2) {
            if (this.mEnabled) {
                super.setHotspot(f, f2)
            }
        }

        @Override // android.support.v7.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        fun setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
            if (this.mEnabled) {
                super.setHotspotBounds(i, i2, i3, i4)
            }
        }

        @Override // android.support.v7.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        fun setState(Array<Int> iArr) {
            if (this.mEnabled) {
                return super.setState(iArr)
            }
            return false
        }

        @Override // android.support.v7.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        fun setVisible(Boolean z, Boolean z2) {
            if (this.mEnabled) {
                return super.setVisible(z, z2)
            }
            return false
        }
    }

    class ResolveHoverRunnable implements Runnable {
        ResolveHoverRunnable() {
        }

        fun cancel() {
            DropDownListView.this.mResolveHoverRunnable = null
            DropDownListView.this.removeCallbacks(this)
        }

        fun post() {
            DropDownListView.this.post(this)
        }

        @Override // java.lang.Runnable
        fun run() {
            DropDownListView.this.mResolveHoverRunnable = null
            DropDownListView.this.drawableStateChanged()
        }
    }

    DropDownListView(Context context, Boolean z) {
        super(context, null, R.attr.dropDownListViewStyle)
        this.mSelectorRect = Rect()
        this.mSelectionLeftPadding = 0
        this.mSelectionTopPadding = 0
        this.mSelectionRightPadding = 0
        this.mSelectionBottomPadding = 0
        this.mHijackFocus = z
        setCacheColorHint(0)
        try {
            this.mIsChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled")
            this.mIsChildViewEnabled.setAccessible(true)
        } catch (NoSuchFieldException e) {
            e.printStackTrace()
        }
    }

    private fun clearPressedItem() {
        this.mDrawsInPressedState = false
        setPressed(false)
        drawableStateChanged()
        View childAt = getChildAt(this.mMotionPosition - getFirstVisiblePosition())
        if (childAt != null) {
            childAt.setPressed(false)
        }
        if (this.mClickAnimation != null) {
            this.mClickAnimation.cancel()
            this.mClickAnimation = null
        }
    }

    private fun clickPressedItem(View view, Int i) {
        performItemClick(view, i, getItemIdAtPosition(i))
    }

    private fun drawSelectorCompat(Canvas canvas) {
        Drawable selector
        if (this.mSelectorRect.isEmpty() || (selector = getSelector()) == null) {
            return
        }
        selector.setBounds(this.mSelectorRect)
        selector.draw(canvas)
    }

    private fun positionSelectorCompat(Int i, View view) throws IllegalAccessException, IllegalArgumentException {
        Rect rect = this.mSelectorRect
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())
        rect.left -= this.mSelectionLeftPadding
        rect.top -= this.mSelectionTopPadding
        rect.right += this.mSelectionRightPadding
        rect.bottom += this.mSelectionBottomPadding
        try {
            Boolean z = this.mIsChildViewEnabled.getBoolean(this)
            if (view.isEnabled() != z) {
                this.mIsChildViewEnabled.set(this, Boolean.valueOf(!z))
                if (i != -1) {
                    refreshDrawableState()
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace()
        }
    }

    private fun positionSelectorLikeFocusCompat(Int i, View view) throws IllegalAccessException, IllegalArgumentException {
        Drawable selector = getSelector()
        Boolean z = (selector == null || i == -1) ? false : true
        if (z) {
            selector.setVisible(false, false)
        }
        positionSelectorCompat(i, view)
        if (z) {
            Rect rect = this.mSelectorRect
            Float fExactCenterX = rect.exactCenterX()
            Float fExactCenterY = rect.exactCenterY()
            selector.setVisible(getVisibility() == 0, false)
            DrawableCompat.setHotspot(selector, fExactCenterX, fExactCenterY)
        }
    }

    private fun positionSelectorLikeTouchCompat(Int i, View view, Float f, Float f2) throws IllegalAccessException, IllegalArgumentException {
        positionSelectorLikeFocusCompat(i, view)
        Drawable selector = getSelector()
        if (selector == null || i == -1) {
            return
        }
        DrawableCompat.setHotspot(selector, f, f2)
    }

    private fun setPressedItem(View view, Int i, Float f, Float f2) throws IllegalAccessException, IllegalArgumentException {
        View childAt
        this.mDrawsInPressedState = true
        if (Build.VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(f, f2)
        }
        if (!isPressed()) {
            setPressed(true)
        }
        layoutChildren()
        if (this.mMotionPosition != -1 && (childAt = getChildAt(this.mMotionPosition - getFirstVisiblePosition())) != null && childAt != view && childAt.isPressed()) {
            childAt.setPressed(false)
        }
        this.mMotionPosition = i
        Float left = f - view.getLeft()
        Float top = f2 - view.getTop()
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(left, top)
        }
        if (!view.isPressed()) {
            view.setPressed(true)
        }
        positionSelectorLikeTouchCompat(i, view, f, f2)
        setSelectorEnabled(false)
        refreshDrawableState()
    }

    private fun setSelectorEnabled(Boolean z) {
        if (this.mSelector != null) {
            this.mSelector.setEnabled(z)
        }
    }

    private fun touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState
    }

    private fun updateSelectorStateCompat() {
        Drawable selector = getSelector()
        if (selector != null && touchModeDrawsInPressedStateCompat() && isPressed()) {
            selector.setState(getDrawableState())
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected fun dispatchDraw(Canvas canvas) {
        drawSelectorCompat(canvas)
        super.dispatchDraw(canvas)
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected fun drawableStateChanged() {
        if (this.mResolveHoverRunnable != null) {
            return
        }
        super.drawableStateChanged()
        setSelectorEnabled(true)
        updateSelectorStateCompat()
    }

    @Override // android.view.ViewGroup, android.view.View
    fun hasFocus() {
        return this.mHijackFocus || super.hasFocus()
    }

    @Override // android.view.View
    fun hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus()
    }

    @Override // android.view.View
    fun isFocused() {
        return this.mHijackFocus || super.isFocused()
    }

    @Override // android.view.View
    fun isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode()
    }

    fun lookForSelectablePosition(Int i, Boolean z) {
        Int iMin
        ListAdapter adapter = getAdapter()
        if (adapter == null || isInTouchMode()) {
            return -1
        }
        Int count = adapter.getCount()
        if (getAdapter().areAllItemsEnabled()) {
            if (i < 0 || i >= count) {
                return -1
            }
            return i
        }
        if (z) {
            iMin = Math.max(0, i)
            while (iMin < count && !adapter.isEnabled(iMin)) {
                iMin++
            }
        } else {
            iMin = Math.min(i, count - 1)
            while (iMin >= 0 && !adapter.isEnabled(iMin)) {
                iMin--
            }
        }
        if (iMin < 0 || iMin >= count) {
            return -1
        }
        return iMin
    }

    fun measureHeightOfChildrenCompat(Int i, Int i2, Int i3, Int i4, Int i5) {
        View view
        Int listPaddingTop = getListPaddingTop()
        Int listPaddingBottom = getListPaddingBottom()
        getListPaddingLeft()
        getListPaddingRight()
        Int dividerHeight = getDividerHeight()
        Drawable divider = getDivider()
        ListAdapter adapter = getAdapter()
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom
        }
        Int measuredHeight = listPaddingBottom + listPaddingTop
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0
        }
        Int i6 = 0
        View view2 = null
        Int i7 = 0
        Int count = adapter.getCount()
        Int i8 = 0
        while (i8 < count) {
            Int itemViewType = adapter.getItemViewType(i8)
            if (itemViewType != i7) {
                view = null
                i7 = itemViewType
            } else {
                view = view2
            }
            view2 = adapter.getView(i8, view, this)
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams()
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams()
                view2.setLayoutParams(layoutParams)
            }
            view2.measure(i, layoutParams.height > 0 ? View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0))
            view2.forceLayout()
            measuredHeight = view2.getMeasuredHeight() + (i8 > 0 ? measuredHeight + dividerHeight : measuredHeight)
            if (measuredHeight >= i4) {
                return (i5 < 0 || i8 <= i5 || i6 <= 0 || measuredHeight == i4) ? i4 : i6
            }
            Int i9 = (i5 < 0 || i8 < i5) ? i6 : measuredHeight
            i8++
            i6 = i9
        }
        return measuredHeight
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        this.mResolveHoverRunnable = null
        super.onDetachedFromWindow()
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onForwardedEvent(android.view.MotionEvent r9, Int r10) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException {
        /*
            r8 = this
            r2 = 1
            r1 = 0
            Int r3 = r9.getActionMasked()
            switch(r3) {
                case 1: goto L2d
                case 2: goto L6a
                case 3: goto L2a
                default: goto L9
            }
        L9:
            r0 = r1
            r3 = r2
        Lb:
            if (r3 == 0) goto Lf
            if (r0 == 0) goto L12
        Lf:
            r8.clearPressedItem()
        L12:
            if (r3 == 0) goto L60
            android.support.v4.widget.ListViewAutoScrollHelper r0 = r8.mScrollHelper
            if (r0 != 0) goto L1f
            android.support.v4.widget.ListViewAutoScrollHelper r0 = new android.support.v4.widget.ListViewAutoScrollHelper
            r0.<init>(r8)
            r8.mScrollHelper = r0
        L1f:
            android.support.v4.widget.ListViewAutoScrollHelper r0 = r8.mScrollHelper
            r0.setEnabled(r2)
            android.support.v4.widget.ListViewAutoScrollHelper r0 = r8.mScrollHelper
            r0.onTouch(r8, r9)
        L29:
            return r3
        L2a:
            r0 = r1
            r3 = r1
            goto Lb
        L2d:
            r0 = r1
        L2e:
            Int r4 = r9.findPointerIndex(r10)
            if (r4 >= 0) goto L37
            r0 = r1
            r3 = r1
            goto Lb
        L37:
            Float r5 = r9.getX(r4)
            Int r5 = (Int) r5
            Float r4 = r9.getY(r4)
            Int r4 = (Int) r4
            Int r6 = r8.pointToPosition(r5, r4)
            r7 = -1
            if (r6 != r7) goto L4b
            r3 = r0
            r0 = r2
            goto Lb
        L4b:
            Int r0 = r8.getFirstVisiblePosition()
            Int r0 = r6 - r0
            android.view.View r0 = r8.getChildAt(r0)
            Float r5 = (Float) r5
            Float r4 = (Float) r4
            r8.setPressedItem(r0, r6, r5, r4)
            if (r3 != r2) goto L9
            r8.clickPressedItem(r0, r6)
            goto L9
        L60:
            android.support.v4.widget.ListViewAutoScrollHelper r0 = r8.mScrollHelper
            if (r0 == 0) goto L29
            android.support.v4.widget.ListViewAutoScrollHelper r0 = r8.mScrollHelper
            r0.setEnabled(r1)
            goto L29
        L6a:
            r0 = r2
            goto L2e
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v7.widget.DropDownListView.onForwardedEvent(android.view.MotionEvent, Int):Boolean")
    }

    @Override // android.view.View
    fun onHoverEvent(@NonNull MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(motionEvent)
        }
        Int actionMasked = motionEvent.getActionMasked()
        if (actionMasked == 10 && this.mResolveHoverRunnable == null) {
            this.mResolveHoverRunnable = ResolveHoverRunnable()
            this.mResolveHoverRunnable.post()
        }
        Boolean zOnHoverEvent = super.onHoverEvent(motionEvent)
        if (actionMasked != 9 && actionMasked != 7) {
            setSelection(-1)
            return zOnHoverEvent
        }
        Int iPointToPosition = pointToPosition((Int) motionEvent.getX(), (Int) motionEvent.getY())
        if (iPointToPosition == -1 || iPointToPosition == getSelectedItemPosition()) {
            return zOnHoverEvent
        }
        View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition())
        if (childAt.isEnabled()) {
            setSelectionFromTop(iPointToPosition, childAt.getTop() - getTop())
        }
        updateSelectorStateCompat()
        return zOnHoverEvent
    }

    @Override // android.widget.AbsListView, android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.mMotionPosition = pointToPosition((Int) motionEvent.getX(), (Int) motionEvent.getY())
                break
        }
        if (this.mResolveHoverRunnable != null) {
            this.mResolveHoverRunnable.cancel()
        }
        return super.onTouchEvent(motionEvent)
    }

    Unit setListSelectionHidden(Boolean z) {
        this.mListSelectionHidden = z
    }

    @Override // android.widget.AbsListView
    fun setSelector(Drawable drawable) {
        this.mSelector = drawable != null ? GateKeeperDrawable(drawable) : null
        super.setSelector(this.mSelector)
        Rect rect = Rect()
        if (drawable != null) {
            drawable.getPadding(rect)
        }
        this.mSelectionLeftPadding = rect.left
        this.mSelectionTopPadding = rect.top
        this.mSelectionRightPadding = rect.right
        this.mSelectionBottomPadding = rect.bottom
    }
}
