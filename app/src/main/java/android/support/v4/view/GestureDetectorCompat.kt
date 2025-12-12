package android.support.v4.view

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration

class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl

    interface GestureDetectorCompatImpl {
        Boolean isLongpressEnabled()

        Boolean onTouchEvent(MotionEvent motionEvent)

        Unit setIsLongpressEnabled(Boolean z)

        Unit setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener)
    }

    class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static val LONG_PRESS = 2
        private static val SHOW_PRESS = 1
        private static val TAP = 3
        private Boolean mAlwaysInBiggerTapRegion
        private Boolean mAlwaysInTapRegion
        MotionEvent mCurrentDownEvent
        Boolean mDeferConfirmSingleTap
        GestureDetector.OnDoubleTapListener mDoubleTapListener
        private Int mDoubleTapSlopSquare
        private Float mDownFocusX
        private Float mDownFocusY
        private final Handler mHandler
        private Boolean mInLongPress
        private Boolean mIsDoubleTapping
        private Boolean mIsLongpressEnabled
        private Float mLastFocusX
        private Float mLastFocusY
        final GestureDetector.OnGestureListener mListener
        private Int mMaximumFlingVelocity
        private Int mMinimumFlingVelocity
        private MotionEvent mPreviousUpEvent
        Boolean mStillDown
        private Int mTouchSlopSquare
        private VelocityTracker mVelocityTracker
        private static val LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout()
        private static val TAP_TIMEOUT = ViewConfiguration.getTapTimeout()
        private static val DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout()

        class GestureHandler extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper())
            }

            @Override // android.os.Handler
            fun handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent)
                        return
                    case 2:
                        GestureDetectorCompatImplBase.this.dispatchLongPress()
                        return
                    case 3:
                        if (GestureDetectorCompatImplBase.this.mDoubleTapListener != null) {
                            if (GestureDetectorCompatImplBase.this.mStillDown) {
                                GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true
                                return
                            } else {
                                GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent)
                                return
                            }
                        }
                        return
                    default:
                        throw RuntimeException("Unknown message " + message)
                }
            }
        }

        GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.mHandler = GestureHandler(handler)
            } else {
                this.mHandler = GestureHandler()
            }
            this.mListener = onGestureListener
            if (onGestureListener is GestureDetector.OnDoubleTapListener) {
                setOnDoubleTapListener((GestureDetector.OnDoubleTapListener) onGestureListener)
            }
            init(context)
        }

        private fun cancel() {
            this.mHandler.removeMessages(1)
            this.mHandler.removeMessages(2)
            this.mHandler.removeMessages(3)
            this.mVelocityTracker.recycle()
            this.mVelocityTracker = null
            this.mIsDoubleTapping = false
            this.mStillDown = false
            this.mAlwaysInTapRegion = false
            this.mAlwaysInBiggerTapRegion = false
            this.mDeferConfirmSingleTap = false
            if (this.mInLongPress) {
                this.mInLongPress = false
            }
        }

        private fun cancelTaps() {
            this.mHandler.removeMessages(1)
            this.mHandler.removeMessages(2)
            this.mHandler.removeMessages(3)
            this.mIsDoubleTapping = false
            this.mAlwaysInTapRegion = false
            this.mAlwaysInBiggerTapRegion = false
            this.mDeferConfirmSingleTap = false
            if (this.mInLongPress) {
                this.mInLongPress = false
            }
        }

        private fun init(Context context) {
            if (context == null) {
                throw IllegalArgumentException("Context must not be null")
            }
            if (this.mListener == null) {
                throw IllegalArgumentException("OnGestureListener must not be null")
            }
            this.mIsLongpressEnabled = true
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context)
            Int scaledTouchSlop = viewConfiguration.getScaledTouchSlop()
            Int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop()
            this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity()
            this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity()
            this.mTouchSlopSquare = scaledTouchSlop * scaledTouchSlop
            this.mDoubleTapSlopSquare = scaledDoubleTapSlop * scaledDoubleTapSlop
        }

        private fun isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            if (!this.mAlwaysInBiggerTapRegion || motionEvent3.getEventTime() - motionEvent2.getEventTime() > DOUBLE_TAP_TIMEOUT) {
                return false
            }
            Int x = ((Int) motionEvent.getX()) - ((Int) motionEvent3.getX())
            Int y = ((Int) motionEvent.getY()) - ((Int) motionEvent3.getY())
            return (x * x) + (y * y) < this.mDoubleTapSlopSquare
        }

        Unit dispatchLongPress() {
            this.mHandler.removeMessages(3)
            this.mDeferConfirmSingleTap = false
            this.mInLongPress = true
            this.mListener.onLongPress(this.mCurrentDownEvent)
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun isLongpressEnabled() {
            return this.mIsLongpressEnabled
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun onTouchEvent(MotionEvent motionEvent) {
            Boolean zOnFling
            Boolean zOnScroll
            Boolean zOnDoubleTap
            Int action = motionEvent.getAction()
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain()
            }
            this.mVelocityTracker.addMovement(motionEvent)
            Boolean z = (action & 255) == 6
            Int actionIndex = z ? motionEvent.getActionIndex() : -1
            Int pointerCount = motionEvent.getPointerCount()
            Float y = 0.0f
            Float x = 0.0f
            for (Int i = 0; i < pointerCount; i++) {
                if (actionIndex != i) {
                    x += motionEvent.getX(i)
                    y += motionEvent.getY(i)
                }
            }
            Int i2 = z ? pointerCount - 1 : pointerCount
            Float f = x / i2
            Float f2 = y / i2
            switch (action & 255) {
                case 0:
                    if (this.mDoubleTapListener == null) {
                        zOnDoubleTap = false
                    } else {
                        Boolean zHasMessages = this.mHandler.hasMessages(3)
                        if (zHasMessages) {
                            this.mHandler.removeMessages(3)
                        }
                        if (this.mCurrentDownEvent == null || this.mPreviousUpEvent == null || !zHasMessages || !isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, motionEvent)) {
                            this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT)
                            zOnDoubleTap = false
                        } else {
                            this.mIsDoubleTapping = true
                            zOnDoubleTap = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(motionEvent)
                        }
                    }
                    this.mLastFocusX = f
                    this.mDownFocusX = f
                    this.mLastFocusY = f2
                    this.mDownFocusY = f2
                    if (this.mCurrentDownEvent != null) {
                        this.mCurrentDownEvent.recycle()
                    }
                    this.mCurrentDownEvent = MotionEvent.obtain(motionEvent)
                    this.mAlwaysInTapRegion = true
                    this.mAlwaysInBiggerTapRegion = true
                    this.mStillDown = true
                    this.mInLongPress = false
                    this.mDeferConfirmSingleTap = false
                    if (this.mIsLongpressEnabled) {
                        this.mHandler.removeMessages(2)
                        this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + LONGPRESS_TIMEOUT)
                    }
                    this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT)
                    break
                case 1:
                    this.mStillDown = false
                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent)
                    if (this.mIsDoubleTapping) {
                        zOnFling = this.mDoubleTapListener.onDoubleTapEvent(motionEvent) | false
                    } else if (this.mInLongPress) {
                        this.mHandler.removeMessages(3)
                        this.mInLongPress = false
                        zOnFling = false
                    } else if (this.mAlwaysInTapRegion) {
                        zOnFling = this.mListener.onSingleTapUp(motionEvent)
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            this.mDoubleTapListener.onSingleTapConfirmed(motionEvent)
                        }
                    } else {
                        VelocityTracker velocityTracker = this.mVelocityTracker
                        Int pointerId = motionEvent.getPointerId(0)
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity)
                        Float yVelocity = velocityTracker.getYVelocity(pointerId)
                        Float xVelocity = velocityTracker.getXVelocity(pointerId)
                        zOnFling = (Math.abs(yVelocity) > ((Float) this.mMinimumFlingVelocity) || Math.abs(xVelocity) > ((Float) this.mMinimumFlingVelocity)) ? this.mListener.onFling(this.mCurrentDownEvent, motionEvent, xVelocity, yVelocity) : false
                    }
                    if (this.mPreviousUpEvent != null) {
                        this.mPreviousUpEvent.recycle()
                    }
                    this.mPreviousUpEvent = motionEventObtain
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle()
                        this.mVelocityTracker = null
                    }
                    this.mIsDoubleTapping = false
                    this.mDeferConfirmSingleTap = false
                    this.mHandler.removeMessages(1)
                    this.mHandler.removeMessages(2)
                    break
                case 2:
                    if (!this.mInLongPress) {
                        Float f3 = this.mLastFocusX - f
                        Float f4 = this.mLastFocusY - f2
                        if (this.mIsDoubleTapping) {
                            break
                        } else if (this.mAlwaysInTapRegion) {
                            Int i3 = (Int) (f - this.mDownFocusX)
                            Int i4 = (Int) (f2 - this.mDownFocusY)
                            Int i5 = (i3 * i3) + (i4 * i4)
                            if (i5 > this.mTouchSlopSquare) {
                                zOnScroll = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f3, f4)
                                this.mLastFocusX = f
                                this.mLastFocusY = f2
                                this.mAlwaysInTapRegion = false
                                this.mHandler.removeMessages(3)
                                this.mHandler.removeMessages(1)
                                this.mHandler.removeMessages(2)
                            } else {
                                zOnScroll = false
                            }
                            if (i5 > this.mTouchSlopSquare) {
                                this.mAlwaysInBiggerTapRegion = false
                            }
                            break
                        } else if (Math.abs(f3) >= 1.0f || Math.abs(f4) >= 1.0f) {
                            Boolean zOnScroll2 = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f3, f4)
                            this.mLastFocusX = f
                            this.mLastFocusY = f2
                            break
                        }
                    }
                    break
                case 3:
                    cancel()
                    break
                case 5:
                    this.mLastFocusX = f
                    this.mDownFocusX = f
                    this.mLastFocusY = f2
                    this.mDownFocusY = f2
                    cancelTaps()
                    break
                case 6:
                    this.mLastFocusX = f
                    this.mDownFocusX = f
                    this.mLastFocusY = f2
                    this.mDownFocusY = f2
                    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity)
                    Int actionIndex2 = motionEvent.getActionIndex()
                    Int pointerId2 = motionEvent.getPointerId(actionIndex2)
                    Float xVelocity2 = this.mVelocityTracker.getXVelocity(pointerId2)
                    Float yVelocity2 = this.mVelocityTracker.getYVelocity(pointerId2)
                    for (Int i6 = 0; i6 < pointerCount; i6++) {
                        if (i6 != actionIndex2) {
                            Int pointerId3 = motionEvent.getPointerId(i6)
                            if ((this.mVelocityTracker.getYVelocity(pointerId3) * yVelocity2) + (this.mVelocityTracker.getXVelocity(pointerId3) * xVelocity2) < 0.0f) {
                                this.mVelocityTracker.clear()
                                break
                            }
                        }
                    }
                    break
            }
            return false
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun setIsLongpressEnabled(Boolean z) {
            this.mIsLongpressEnabled = z
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener
        }
    }

    class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector

        GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mDetector = GestureDetector(context, onGestureListener, handler)
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled()
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun onTouchEvent(MotionEvent motionEvent) {
            return this.mDetector.onTouchEvent(motionEvent)
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun setIsLongpressEnabled(Boolean z) {
            this.mDetector.setIsLongpressEnabled(z)
        }

        @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
        fun setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDetector.setOnDoubleTapListener(onDoubleTapListener)
        }
    }

    constructor(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null)
    }

    constructor(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.mImpl = GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler)
        } else {
            this.mImpl = GestureDetectorCompatImplBase(context, onGestureListener, handler)
        }
    }

    public final Boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled()
    }

    public final Boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.onTouchEvent(motionEvent)
    }

    public final Unit setIsLongpressEnabled(Boolean z) {
        this.mImpl.setIsLongpressEnabled(z)
    }

    public final Unit setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.setOnDoubleTapListener(onDoubleTapListener)
    }
}
