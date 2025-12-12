package android.support.v13.view

import android.graphics.Point
import androidx.core.view.MotionEventCompat
import android.view.MotionEvent
import android.view.View

class DragStartHelper {
    private Boolean mDragging
    private Int mLastTouchX
    private Int mLastTouchY
    private final OnDragStartListener mListener
    private final View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() { // from class: android.support.v13.view.DragStartHelper.1
        @Override // android.view.View.OnLongClickListener
        fun onLongClick(View view) {
            return DragStartHelper.this.onLongClick(view)
        }
    }
    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() { // from class: android.support.v13.view.DragStartHelper.2
        @Override // android.view.View.OnTouchListener
        fun onTouch(View view, MotionEvent motionEvent) {
            return DragStartHelper.this.onTouch(view, motionEvent)
        }
    }
    private final View mView

    public interface OnDragStartListener {
        Boolean onDragStart(View view, DragStartHelper dragStartHelper)
    }

    constructor(View view, OnDragStartListener onDragStartListener) {
        this.mView = view
        this.mListener = onDragStartListener
    }

    fun attach() {
        this.mView.setOnLongClickListener(this.mLongClickListener)
        this.mView.setOnTouchListener(this.mTouchListener)
    }

    fun detach() {
        this.mView.setOnLongClickListener(null)
        this.mView.setOnTouchListener(null)
    }

    fun getTouchPosition(Point point) {
        point.set(this.mLastTouchX, this.mLastTouchY)
    }

    fun onLongClick(View view) {
        return this.mListener.onDragStart(view, this)
    }

    fun onTouch(View view, MotionEvent motionEvent) {
        Int x = (Int) motionEvent.getX()
        Int y = (Int) motionEvent.getY()
        switch (motionEvent.getAction()) {
            case 0:
                this.mLastTouchX = x
                this.mLastTouchY = y
                break
            case 1:
            case 3:
                this.mDragging = false
                break
            case 2:
                if (MotionEventCompat.isFromSource(motionEvent, 8194) && (motionEvent.getButtonState() & 1) != 0 && !this.mDragging) {
                    if (this.mLastTouchX != x || this.mLastTouchY != y) {
                        this.mLastTouchX = x
                        this.mLastTouchY = y
                        this.mDragging = this.mListener.onDragStart(view, this)
                        break
                    }
                }
                break
        }
        return false
    }
}
