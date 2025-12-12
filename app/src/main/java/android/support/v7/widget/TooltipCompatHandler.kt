package android.support.v7.widget

import android.content.res.Resources
import android.support.annotation.RestrictTo
import androidx.core.view.ViewCompat
import android.support.v4.view.ViewConfigurationCompat
import android.support.v7.widget.ActivityChooserView
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.accessibility.AccessibilityManager

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TooltipCompatHandler implements View.OnAttachStateChangeListener, View.OnHoverListener, View.OnLongClickListener {
    private static val HOVER_HIDE_TIMEOUT_MS = 15000
    private static val HOVER_HIDE_TIMEOUT_SHORT_MS = 3000
    private static val LONG_CLICK_HIDE_TIMEOUT_MS = 2500
    private static val TAG = "TooltipCompatHandler"
    private static TooltipCompatHandler sActiveHandler
    private static TooltipCompatHandler sPendingHandler
    private final View mAnchor
    private Int mAnchorX
    private Int mAnchorY
    private Boolean mFromTouch
    private final Int mHoverSlop
    private TooltipPopup mPopup
    private final CharSequence mTooltipText
    private val mShowRunnable = Runnable() { // from class: android.support.v7.widget.TooltipCompatHandler.1
        @Override // java.lang.Runnable
        fun run() throws Resources.NotFoundException {
            TooltipCompatHandler.this.show(false)
        }
    }
    private val mHideRunnable = Runnable() { // from class: android.support.v7.widget.TooltipCompatHandler.2
        @Override // java.lang.Runnable
        fun run() {
            TooltipCompatHandler.this.hide()
        }
    }

    private constructor(View view, CharSequence charSequence) {
        this.mAnchor = view
        this.mTooltipText = charSequence
        this.mHoverSlop = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get(this.mAnchor.getContext()))
        clearAnchorPos()
        this.mAnchor.setOnLongClickListener(this)
        this.mAnchor.setOnHoverListener(this)
    }

    private fun cancelPendingShow() {
        this.mAnchor.removeCallbacks(this.mShowRunnable)
    }

    private fun clearAnchorPos() {
        this.mAnchorX = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        this.mAnchorY = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
    }

    private fun scheduleShow() {
        this.mAnchor.postDelayed(this.mShowRunnable, ViewConfiguration.getLongPressTimeout())
    }

    private fun setPendingHandler(TooltipCompatHandler tooltipCompatHandler) {
        if (sPendingHandler != null) {
            sPendingHandler.cancelPendingShow()
        }
        sPendingHandler = tooltipCompatHandler
        if (tooltipCompatHandler != null) {
            sPendingHandler.scheduleShow()
        }
    }

    fun setTooltipText(View view, CharSequence charSequence) {
        if (sPendingHandler != null && sPendingHandler.mAnchor == view) {
            setPendingHandler(null)
        }
        if (!TextUtils.isEmpty(charSequence)) {
            TooltipCompatHandler(view, charSequence)
            return
        }
        if (sActiveHandler != null && sActiveHandler.mAnchor == view) {
            sActiveHandler.hide()
        }
        view.setOnLongClickListener(null)
        view.setLongClickable(false)
        view.setOnHoverListener(null)
    }

    private fun updateAnchorPos(MotionEvent motionEvent) {
        Int x = (Int) motionEvent.getX()
        Int y = (Int) motionEvent.getY()
        if (Math.abs(x - this.mAnchorX) <= this.mHoverSlop && Math.abs(y - this.mAnchorY) <= this.mHoverSlop) {
            return false
        }
        this.mAnchorX = x
        this.mAnchorY = y
        return true
    }

    Unit hide() {
        if (sActiveHandler == this) {
            sActiveHandler = null
            if (this.mPopup != null) {
                this.mPopup.hide()
                this.mPopup = null
                clearAnchorPos()
                this.mAnchor.removeOnAttachStateChangeListener(this)
            } else {
                Log.e(TAG, "sActiveHandler.mPopup == null")
            }
        }
        if (sPendingHandler == this) {
            setPendingHandler(null)
        }
        this.mAnchor.removeCallbacks(this.mHideRunnable)
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnHoverListener
    fun onHover(View view, MotionEvent motionEvent) {
        if (this.mPopup == null || !this.mFromTouch) {
            AccessibilityManager accessibilityManager = (AccessibilityManager) this.mAnchor.getContext().getSystemService("accessibility")
            if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
                switch (motionEvent.getAction()) {
                    case 7:
                        if (this.mAnchor.isEnabled() && this.mPopup == null && updateAnchorPos(motionEvent)) {
                            setPendingHandler(this)
                            break
                        }
                        break
                    case 10:
                        clearAnchorPos()
                        hide()
                        break
                }
            }
        }
        return false
    }

    @Override // android.view.View.OnLongClickListener
    fun onLongClick(View view) throws Resources.NotFoundException {
        this.mAnchorX = view.getWidth() / 2
        this.mAnchorY = view.getHeight() / 2
        show(true)
        return true
    }

    @Override // android.view.View.OnAttachStateChangeListener
    fun onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    fun onViewDetachedFromWindow(View view) {
        hide()
    }

    Unit show(Boolean z) throws Resources.NotFoundException {
        if (ViewCompat.isAttachedToWindow(this.mAnchor)) {
            setPendingHandler(null)
            if (sActiveHandler != null) {
                sActiveHandler.hide()
            }
            sActiveHandler = this
            this.mFromTouch = z
            this.mPopup = TooltipPopup(this.mAnchor.getContext())
            this.mPopup.show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText)
            this.mAnchor.addOnAttachStateChangeListener(this)
            Long longPressTimeout = this.mFromTouch ? LONG_CLICK_HIDE_TIMEOUT_MS : (ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1 ? HOVER_HIDE_TIMEOUT_SHORT_MS - ViewConfiguration.getLongPressTimeout() : HOVER_HIDE_TIMEOUT_MS - ViewConfiguration.getLongPressTimeout()
            this.mAnchor.removeCallbacks(this.mHideRunnable)
            this.mAnchor.postDelayed(this.mHideRunnable, longPressTimeout)
        }
    }
}
