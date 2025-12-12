package android.support.v7.widget

import android.os.SystemClock
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.ShowableListMenu
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewParent

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class ForwardingListener implements View.OnAttachStateChangeListener, View.OnTouchListener {
    private Int mActivePointerId
    private Runnable mDisallowIntercept
    private Boolean mForwarding
    private final Int mLongPressTimeout
    private final Float mScaledTouchSlop
    final View mSrc
    private final Int mTapTimeout
    private final Array<Int> mTmpLocation = new Int[2]
    private Runnable mTriggerLongPress

    class DisallowIntercept implements Runnable {
        DisallowIntercept() {
        }

        @Override // java.lang.Runnable
        fun run() {
            ViewParent parent = ForwardingListener.this.mSrc.getParent()
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
    }

    class TriggerLongPress implements Runnable {
        TriggerLongPress() {
        }

        @Override // java.lang.Runnable
        fun run() {
            ForwardingListener.this.onLongPress()
        }
    }

    constructor(View view) {
        this.mSrc = view
        view.setLongClickable(true)
        view.addOnAttachStateChangeListener(this)
        this.mScaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop()
        this.mTapTimeout = ViewConfiguration.getTapTimeout()
        this.mLongPressTimeout = (this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2
    }

    private fun clearCallbacks() {
        if (this.mTriggerLongPress != null) {
            this.mSrc.removeCallbacks(this.mTriggerLongPress)
        }
        if (this.mDisallowIntercept != null) {
            this.mSrc.removeCallbacks(this.mDisallowIntercept)
        }
    }

    private fun onTouchForwarded(MotionEvent motionEvent) throws IllegalAccessException, IllegalArgumentException {
        View view = this.mSrc
        ShowableListMenu popup = getPopup()
        if (popup == null || !popup.isShowing()) {
            return false
        }
        DropDownListView dropDownListView = (DropDownListView) popup.getListView()
        if (dropDownListView == null || !dropDownListView.isShown()) {
            return false
        }
        MotionEvent motionEventObtainNoHistory = MotionEvent.obtainNoHistory(motionEvent)
        toGlobalMotionEvent(view, motionEventObtainNoHistory)
        toLocalMotionEvent(dropDownListView, motionEventObtainNoHistory)
        Boolean zOnForwardedEvent = dropDownListView.onForwardedEvent(motionEventObtainNoHistory, this.mActivePointerId)
        motionEventObtainNoHistory.recycle()
        Int actionMasked = motionEvent.getActionMasked()
        return zOnForwardedEvent && (actionMasked != 1 && actionMasked != 3)
    }

    private fun onTouchObserved(MotionEvent motionEvent) {
        View view = this.mSrc
        if (!view.isEnabled()) {
            return false
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mActivePointerId = motionEvent.getPointerId(0)
                if (this.mDisallowIntercept == null) {
                    this.mDisallowIntercept = DisallowIntercept()
                }
                view.postDelayed(this.mDisallowIntercept, this.mTapTimeout)
                if (this.mTriggerLongPress == null) {
                    this.mTriggerLongPress = TriggerLongPress()
                }
                view.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout)
                break
            case 1:
            case 3:
                clearCallbacks()
                break
            case 2:
                Int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId)
                if (iFindPointerIndex >= 0 && !pointInView(view, motionEvent.getX(iFindPointerIndex), motionEvent.getY(iFindPointerIndex), this.mScaledTouchSlop)) {
                    clearCallbacks()
                    view.getParent().requestDisallowInterceptTouchEvent(true)
                    break
                }
                break
        }
        return false
    }

    private fun pointInView(View view, Float f, Float f2, Float f3) {
        return f >= (-f3) && f2 >= (-f3) && f < ((Float) (view.getRight() - view.getLeft())) + f3 && f2 < ((Float) (view.getBottom() - view.getTop())) + f3
    }

    private fun toGlobalMotionEvent(View view, MotionEvent motionEvent) {
        view.getLocationOnScreen(this.mTmpLocation)
        motionEvent.offsetLocation(r0[0], r0[1])
        return true
    }

    private fun toLocalMotionEvent(View view, MotionEvent motionEvent) {
        view.getLocationOnScreen(this.mTmpLocation)
        motionEvent.offsetLocation(-r0[0], -r0[1])
        return true
    }

    public abstract ShowableListMenu getPopup()

    protected fun onForwardingStarted() {
        ShowableListMenu popup = getPopup()
        if (popup == null || popup.isShowing()) {
            return true
        }
        popup.show()
        return true
    }

    protected fun onForwardingStopped() {
        ShowableListMenu popup = getPopup()
        if (popup == null || !popup.isShowing()) {
            return true
        }
        popup.dismiss()
        return true
    }

    Unit onLongPress() {
        clearCallbacks()
        View view = this.mSrc
        if (view.isEnabled() && !view.isLongClickable() && onForwardingStarted()) {
            view.getParent().requestDisallowInterceptTouchEvent(true)
            Long jUptimeMillis = SystemClock.uptimeMillis()
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0)
            view.onTouchEvent(motionEventObtain)
            motionEventObtain.recycle()
            this.mForwarding = true
        }
    }

    @Override // android.view.View.OnTouchListener
    fun onTouch(View view, MotionEvent motionEvent) {
        Boolean z
        Boolean z2 = this.mForwarding
        if (z2) {
            z = onTouchForwarded(motionEvent) || !onForwardingStopped()
        } else {
            Boolean z3 = onTouchObserved(motionEvent) && onForwardingStarted()
            if (z3) {
                Long jUptimeMillis = SystemClock.uptimeMillis()
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0)
                this.mSrc.onTouchEvent(motionEventObtain)
                motionEventObtain.recycle()
            }
            z = z3
        }
        this.mForwarding = z
        return z || z2
    }

    @Override // android.view.View.OnAttachStateChangeListener
    fun onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    fun onViewDetachedFromWindow(View view) {
        this.mForwarding = false
        this.mActivePointerId = -1
        if (this.mDisallowIntercept != null) {
            this.mSrc.removeCallbacks(this.mDisallowIntercept)
        }
    }
}
