package android.support.v4.widget

import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.Px
import androidx.core.view.ViewCompat
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.OverScroller
import java.util.Arrays

class ViewDragHelper {
    private static val BASE_SETTLE_DURATION = 256
    public static val DIRECTION_ALL = 3
    public static val DIRECTION_HORIZONTAL = 1
    public static val DIRECTION_VERTICAL = 2
    public static val EDGE_ALL = 15
    public static val EDGE_BOTTOM = 8
    public static val EDGE_LEFT = 1
    public static val EDGE_RIGHT = 2
    private static val EDGE_SIZE = 20
    public static val EDGE_TOP = 4
    public static val INVALID_POINTER = -1
    private static val MAX_SETTLE_DURATION = 600
    public static val STATE_DRAGGING = 1
    public static val STATE_IDLE = 0
    public static val STATE_SETTLING = 2
    private static val TAG = "ViewDragHelper"
    private static val sInterpolator = Interpolator() { // from class: android.support.v4.widget.ViewDragHelper.1
        @Override // android.animation.TimeInterpolator
        public final Float getInterpolation(Float f) {
            Float f2 = f - 1.0f
            return (f2 * f2 * f2 * f2 * f2) + 1.0f
        }
    }
    private final Callback mCallback
    private View mCapturedView
    private Int mDragState
    private Array<Int> mEdgeDragsInProgress
    private Array<Int> mEdgeDragsLocked
    private Int mEdgeSize
    private Array<Int> mInitialEdgesTouched
    private Array<Float> mInitialMotionX
    private Array<Float> mInitialMotionY
    private Array<Float> mLastMotionX
    private Array<Float> mLastMotionY
    private Float mMaxVelocity
    private Float mMinVelocity
    private final ViewGroup mParentView
    private Int mPointersDown
    private Boolean mReleaseInProgress
    private OverScroller mScroller
    private Int mTouchSlop
    private Int mTrackingEdges
    private VelocityTracker mVelocityTracker
    private Int mActivePointerId = -1
    private val mSetIdleRunnable = Runnable() { // from class: android.support.v4.widget.ViewDragHelper.2
        @Override // java.lang.Runnable
        fun run() {
            ViewDragHelper.this.setDragState(0)
        }
    }

    abstract class Callback {
        fun clampViewPositionHorizontal(@NonNull View view, Int i, Int i2) {
            return 0
        }

        fun clampViewPositionVertical(@NonNull View view, Int i, Int i2) {
            return 0
        }

        fun getOrderedChildIndex(Int i) {
            return i
        }

        fun getViewHorizontalDragRange(@NonNull View view) {
            return 0
        }

        fun getViewVerticalDragRange(@NonNull View view) {
            return 0
        }

        fun onEdgeDragStarted(Int i, Int i2) {
        }

        fun onEdgeLock(Int i) {
            return false
        }

        fun onEdgeTouched(Int i, Int i2) {
        }

        fun onViewCaptured(@NonNull View view, Int i) {
        }

        fun onViewDragStateChanged(Int i) {
        }

        fun onViewPositionChanged(@NonNull View view, Int i, Int i2, @Px Int i3, @Px Int i4) {
        }

        fun onViewReleased(@NonNull View view, Float f, Float f2) {
        }

        public abstract Boolean tryCaptureView(@NonNull View view, Int i)
    }

    private constructor(@NonNull Context context, @NonNull ViewGroup viewGroup, @NonNull Callback callback) {
        if (viewGroup == null) {
            throw IllegalArgumentException("Parent view may not be null")
        }
        if (callback == null) {
            throw IllegalArgumentException("Callback may not be null")
        }
        this.mParentView = viewGroup
        this.mCallback = callback
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context)
        this.mEdgeSize = (Int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f)
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop()
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity()
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity()
        this.mScroller = OverScroller(context, sInterpolator)
    }

    private fun checkNewEdgeDrag(Float f, Float f2, Int i, Int i2) {
        Float fAbs = Math.abs(f)
        Float fAbs2 = Math.abs(f2)
        if ((this.mInitialEdgesTouched[i] & i2) != i2 || (this.mTrackingEdges & i2) == 0 || (this.mEdgeDragsLocked[i] & i2) == i2 || (this.mEdgeDragsInProgress[i] & i2) == i2) {
            return false
        }
        if (fAbs <= this.mTouchSlop && fAbs2 <= this.mTouchSlop) {
            return false
        }
        if (fAbs >= fAbs2 * 0.5f || !this.mCallback.onEdgeLock(i2)) {
            return (this.mEdgeDragsInProgress[i] & i2) == 0 && fAbs > ((Float) this.mTouchSlop)
        }
        Array<Int> iArr = this.mEdgeDragsLocked
        iArr[i] = iArr[i] | i2
        return false
    }

    private fun checkTouchSlop(View view, Float f, Float f2) {
        if (view == null) {
            return false
        }
        Boolean z = this.mCallback.getViewHorizontalDragRange(view) > 0
        Boolean z2 = this.mCallback.getViewVerticalDragRange(view) > 0
        return (z && z2) ? (f * f) + (f2 * f2) > ((Float) (this.mTouchSlop * this.mTouchSlop)) : z ? Math.abs(f) > ((Float) this.mTouchSlop) : z2 && Math.abs(f2) > ((Float) this.mTouchSlop)
    }

    private fun clampMag(Float f, Float f2, Float f3) {
        Float fAbs = Math.abs(f)
        if (fAbs < f2) {
            return 0.0f
        }
        return fAbs > f3 ? f <= 0.0f ? -f3 : f3 : f
    }

    private fun clampMag(Int i, Int i2, Int i3) {
        Int iAbs = Math.abs(i)
        if (iAbs < i2) {
            return 0
        }
        return iAbs > i3 ? i <= 0 ? -i3 : i3 : i
    }

    private fun clearMotionHistory() {
        if (this.mInitialMotionX == null) {
            return
        }
        Arrays.fill(this.mInitialMotionX, 0.0f)
        Arrays.fill(this.mInitialMotionY, 0.0f)
        Arrays.fill(this.mLastMotionX, 0.0f)
        Arrays.fill(this.mLastMotionY, 0.0f)
        Arrays.fill(this.mInitialEdgesTouched, 0)
        Arrays.fill(this.mEdgeDragsInProgress, 0)
        Arrays.fill(this.mEdgeDragsLocked, 0)
        this.mPointersDown = 0
    }

    private fun clearMotionHistory(Int i) {
        if (this.mInitialMotionX == null || !isPointerDown(i)) {
            return
        }
        this.mInitialMotionX[i] = 0.0f
        this.mInitialMotionY[i] = 0.0f
        this.mLastMotionX[i] = 0.0f
        this.mLastMotionY[i] = 0.0f
        this.mInitialEdgesTouched[i] = 0
        this.mEdgeDragsInProgress[i] = 0
        this.mEdgeDragsLocked[i] = 0
        this.mPointersDown &= (1 << i) ^ (-1)
    }

    private fun computeAxisDuration(Int i, Int i2, Int i3) {
        if (i == 0) {
            return 0
        }
        Int width = this.mParentView.getWidth()
        Int i4 = width / 2
        Float fDistanceInfluenceForSnapDuration = (distanceInfluenceForSnapDuration(Math.min(1.0f, Math.abs(i) / width)) * i4) + i4
        Int iAbs = Math.abs(i2)
        return Math.min(iAbs > 0 ? Math.round(Math.abs(fDistanceInfluenceForSnapDuration / iAbs) * 1000.0f) * 4 : (Int) (((Math.abs(i) / i3) + 1.0f) * 256.0f), MAX_SETTLE_DURATION)
    }

    private fun computeSettleDuration(View view, Int i, Int i2, Int i3, Int i4) {
        Int iClampMag = clampMag(i3, (Int) this.mMinVelocity, (Int) this.mMaxVelocity)
        Int iClampMag2 = clampMag(i4, (Int) this.mMinVelocity, (Int) this.mMaxVelocity)
        Int iAbs = Math.abs(i)
        Int iAbs2 = Math.abs(i2)
        Int iAbs3 = Math.abs(iClampMag)
        Int iAbs4 = Math.abs(iClampMag2)
        Int i5 = iAbs3 + iAbs4
        Int i6 = iAbs + iAbs2
        return (Int) (((iClampMag2 != 0 ? iAbs4 / i5 : iAbs2 / i6) * computeAxisDuration(i2, iClampMag2, this.mCallback.getViewVerticalDragRange(view))) + ((iClampMag != 0 ? iAbs3 / i5 : iAbs / i6) * computeAxisDuration(i, iClampMag, this.mCallback.getViewHorizontalDragRange(view))))
    }

    fun create(@NonNull ViewGroup viewGroup, Float f, @NonNull Callback callback) {
        ViewDragHelper viewDragHelperCreate = create(viewGroup, callback)
        viewDragHelperCreate.mTouchSlop = (Int) (viewDragHelperCreate.mTouchSlop * (1.0f / f))
        return viewDragHelperCreate
    }

    fun create(@NonNull ViewGroup viewGroup, @NonNull Callback callback) {
        return ViewDragHelper(viewGroup.getContext(), viewGroup, callback)
    }

    private fun dispatchViewReleased(Float f, Float f2) {
        this.mReleaseInProgress = true
        this.mCallback.onViewReleased(this.mCapturedView, f, f2)
        this.mReleaseInProgress = false
        if (this.mDragState == 1) {
            setDragState(0)
        }
    }

    private fun distanceInfluenceForSnapDuration(Float f) {
        return (Float) Math.sin((f - 0.5f) * 0.47123894f)
    }

    private fun dragTo(Int i, Int i2, Int i3, Int i4) {
        Int iClampViewPositionHorizontal
        Int iClampViewPositionVertical
        Int left = this.mCapturedView.getLeft()
        Int top = this.mCapturedView.getTop()
        if (i3 != 0) {
            iClampViewPositionHorizontal = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, i, i3)
            ViewCompat.offsetLeftAndRight(this.mCapturedView, iClampViewPositionHorizontal - left)
        } else {
            iClampViewPositionHorizontal = i
        }
        if (i4 != 0) {
            iClampViewPositionVertical = this.mCallback.clampViewPositionVertical(this.mCapturedView, i2, i4)
            ViewCompat.offsetTopAndBottom(this.mCapturedView, iClampViewPositionVertical - top)
        } else {
            iClampViewPositionVertical = i2
        }
        if (i3 == 0 && i4 == 0) {
            return
        }
        this.mCallback.onViewPositionChanged(this.mCapturedView, iClampViewPositionHorizontal, iClampViewPositionVertical, iClampViewPositionHorizontal - left, iClampViewPositionVertical - top)
    }

    private fun ensureMotionHistorySizeForId(Int i) {
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= i) {
            Array<Float> fArr = new Float[i + 1]
            Array<Float> fArr2 = new Float[i + 1]
            Array<Float> fArr3 = new Float[i + 1]
            Array<Float> fArr4 = new Float[i + 1]
            Array<Int> iArr = new Int[i + 1]
            Array<Int> iArr2 = new Int[i + 1]
            Array<Int> iArr3 = new Int[i + 1]
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, fArr, 0, this.mInitialMotionX.length)
                System.arraycopy(this.mInitialMotionY, 0, fArr2, 0, this.mInitialMotionY.length)
                System.arraycopy(this.mLastMotionX, 0, fArr3, 0, this.mLastMotionX.length)
                System.arraycopy(this.mLastMotionY, 0, fArr4, 0, this.mLastMotionY.length)
                System.arraycopy(this.mInitialEdgesTouched, 0, iArr, 0, this.mInitialEdgesTouched.length)
                System.arraycopy(this.mEdgeDragsInProgress, 0, iArr2, 0, this.mEdgeDragsInProgress.length)
                System.arraycopy(this.mEdgeDragsLocked, 0, iArr3, 0, this.mEdgeDragsLocked.length)
            }
            this.mInitialMotionX = fArr
            this.mInitialMotionY = fArr2
            this.mLastMotionX = fArr3
            this.mLastMotionY = fArr4
            this.mInitialEdgesTouched = iArr
            this.mEdgeDragsInProgress = iArr2
            this.mEdgeDragsLocked = iArr3
        }
    }

    private fun forceSettleCapturedViewAt(Int i, Int i2, Int i3, Int i4) {
        Int left = this.mCapturedView.getLeft()
        Int top = this.mCapturedView.getTop()
        Int i5 = i - left
        Int i6 = i2 - top
        if (i5 == 0 && i6 == 0) {
            this.mScroller.abortAnimation()
            setDragState(0)
            return false
        }
        this.mScroller.startScroll(left, top, i5, i6, computeSettleDuration(this.mCapturedView, i5, i6, i3, i4))
        setDragState(2)
        return true
    }

    private fun getEdgesTouched(Int i, Int i2) {
        Int i3 = i < this.mParentView.getLeft() + this.mEdgeSize ? 1 : 0
        if (i2 < this.mParentView.getTop() + this.mEdgeSize) {
            i3 |= 4
        }
        if (i > this.mParentView.getRight() - this.mEdgeSize) {
            i3 |= 2
        }
        return i2 > this.mParentView.getBottom() - this.mEdgeSize ? i3 | 8 : i3
    }

    private fun isValidPointerForActionMove(Int i) {
        if (isPointerDown(i)) {
            return true
        }
        Log.e(TAG, "Ignoring pointerId=" + i + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.")
        return false
    }

    private fun releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity)
        dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity))
    }

    private fun reportNewEdgeDrags(Float f, Float f2, Int i) {
        Int i2 = checkNewEdgeDrag(f, f2, i, 1) ? 1 : 0
        if (checkNewEdgeDrag(f2, f, i, 4)) {
            i2 |= 4
        }
        if (checkNewEdgeDrag(f, f2, i, 2)) {
            i2 |= 2
        }
        if (checkNewEdgeDrag(f2, f, i, 8)) {
            i2 |= 8
        }
        if (i2 != 0) {
            Array<Int> iArr = this.mEdgeDragsInProgress
            iArr[i] = iArr[i] | i2
            this.mCallback.onEdgeDragStarted(i2, i)
        }
    }

    private fun saveInitialMotion(Float f, Float f2, Int i) {
        ensureMotionHistorySizeForId(i)
        Array<Float> fArr = this.mInitialMotionX
        this.mLastMotionX[i] = f
        fArr[i] = f
        Array<Float> fArr2 = this.mInitialMotionY
        this.mLastMotionY[i] = f2
        fArr2[i] = f2
        this.mInitialEdgesTouched[i] = getEdgesTouched((Int) f, (Int) f2)
        this.mPointersDown |= 1 << i
    }

    private fun saveLastMotion(MotionEvent motionEvent) {
        Int pointerCount = motionEvent.getPointerCount()
        for (Int i = 0; i < pointerCount; i++) {
            Int pointerId = motionEvent.getPointerId(i)
            if (isValidPointerForActionMove(pointerId)) {
                Float x = motionEvent.getX(i)
                Float y = motionEvent.getY(i)
                this.mLastMotionX[pointerId] = x
                this.mLastMotionY[pointerId] = y
            }
        }
    }

    fun abort() {
        cancel()
        if (this.mDragState == 2) {
            Int currX = this.mScroller.getCurrX()
            Int currY = this.mScroller.getCurrY()
            this.mScroller.abortAnimation()
            Int currX2 = this.mScroller.getCurrX()
            Int currY2 = this.mScroller.getCurrY()
            this.mCallback.onViewPositionChanged(this.mCapturedView, currX2, currY2, currX2 - currX, currY2 - currY)
        }
        setDragState(0)
    }

    protected fun canScroll(@NonNull View view, Boolean z, Int i, Int i2, Int i3, Int i4) {
        if (view is ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view
            Int scrollX = view.getScrollX()
            Int scrollY = view.getScrollY()
            for (Int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount)
                if (i3 + scrollX >= childAt.getLeft() && i3 + scrollX < childAt.getRight() && i4 + scrollY >= childAt.getTop() && i4 + scrollY < childAt.getBottom() && canScroll(childAt, true, i, i2, (i3 + scrollX) - childAt.getLeft(), (i4 + scrollY) - childAt.getTop())) {
                    return true
                }
            }
        }
        return z && (view.canScrollHorizontally(-i) || view.canScrollVertically(-i2))
    }

    fun cancel() {
        this.mActivePointerId = -1
        clearMotionHistory()
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle()
            this.mVelocityTracker = null
        }
    }

    fun captureChildView(@NonNull View view, Int i) {
        if (view.getParent() != this.mParentView) {
            throw IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")")
        }
        this.mCapturedView = view
        this.mActivePointerId = i
        this.mCallback.onViewCaptured(view, i)
        setDragState(1)
    }

    fun checkTouchSlop(Int i) {
        Int length = this.mInitialMotionX.length
        for (Int i2 = 0; i2 < length; i2++) {
            if (checkTouchSlop(i, i2)) {
                return true
            }
        }
        return false
    }

    fun checkTouchSlop(Int i, Int i2) {
        if (!isPointerDown(i2)) {
            return false
        }
        Boolean z = (i & 1) == 1
        Boolean z2 = (i & 2) == 2
        Float f = this.mLastMotionX[i2] - this.mInitialMotionX[i2]
        Float f2 = this.mLastMotionY[i2] - this.mInitialMotionY[i2]
        return (z && z2) ? (f * f) + (f2 * f2) > ((Float) (this.mTouchSlop * this.mTouchSlop)) : z ? Math.abs(f) > ((Float) this.mTouchSlop) : z2 && Math.abs(f2) > ((Float) this.mTouchSlop)
    }

    fun continueSettling(Boolean z) {
        Boolean z2
        if (this.mDragState == 2) {
            Boolean zComputeScrollOffset = this.mScroller.computeScrollOffset()
            Int currX = this.mScroller.getCurrX()
            Int currY = this.mScroller.getCurrY()
            Int left = currX - this.mCapturedView.getLeft()
            Int top = currY - this.mCapturedView.getTop()
            if (left != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, left)
            }
            if (top != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, top)
            }
            if (left != 0 || top != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, currX, currY, left, top)
            }
            if (zComputeScrollOffset && currX == this.mScroller.getFinalX() && currY == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation()
                z2 = false
            } else {
                z2 = zComputeScrollOffset
            }
            if (!z2) {
                if (z) {
                    this.mParentView.post(this.mSetIdleRunnable)
                } else {
                    setDragState(0)
                }
            }
        }
        return this.mDragState == 2
    }

    @Nullable
    fun findTopChildUnder(Int i, Int i2) {
        for (Int childCount = this.mParentView.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(childCount))
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt
            }
        }
        return null
    }

    fun flingCapturedView(Int i, Int i2, Int i3, Int i4) {
        if (!this.mReleaseInProgress) {
            throw IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased")
        }
        this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (Int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (Int) this.mVelocityTracker.getYVelocity(this.mActivePointerId), i, i3, i2, i4)
        setDragState(2)
    }

    fun getActivePointerId() {
        return this.mActivePointerId
    }

    @Nullable
    fun getCapturedView() {
        return this.mCapturedView
    }

    @Px
    fun getEdgeSize() {
        return this.mEdgeSize
    }

    fun getMinVelocity() {
        return this.mMinVelocity
    }

    @Px
    fun getTouchSlop() {
        return this.mTouchSlop
    }

    fun getViewDragState() {
        return this.mDragState
    }

    fun isCapturedViewUnder(Int i, Int i2) {
        return isViewUnder(this.mCapturedView, i, i2)
    }

    fun isEdgeTouched(Int i) {
        Int length = this.mInitialEdgesTouched.length
        for (Int i2 = 0; i2 < length; i2++) {
            if (isEdgeTouched(i, i2)) {
                return true
            }
        }
        return false
    }

    fun isEdgeTouched(Int i, Int i2) {
        return isPointerDown(i2) && (this.mInitialEdgesTouched[i2] & i) != 0
    }

    fun isPointerDown(Int i) {
        return (this.mPointersDown & (1 << i)) != 0
    }

    fun isViewUnder(@Nullable View view, Int i, Int i2) {
        return view != null && i >= view.getLeft() && i < view.getRight() && i2 >= view.getTop() && i2 < view.getBottom()
    }

    fun processTouchEvent(@NonNull MotionEvent motionEvent) {
        Int i
        Int i2 = 0
        Int actionMasked = motionEvent.getActionMasked()
        Int actionIndex = motionEvent.getActionIndex()
        if (actionMasked == 0) {
            cancel()
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain()
        }
        this.mVelocityTracker.addMovement(motionEvent)
        switch (actionMasked) {
            case 0:
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                Int pointerId = motionEvent.getPointerId(0)
                View viewFindTopChildUnder = findTopChildUnder((Int) x, (Int) y)
                saveInitialMotion(x, y, pointerId)
                tryCaptureViewForDrag(viewFindTopChildUnder, pointerId)
                Int i3 = this.mInitialEdgesTouched[pointerId]
                if ((this.mTrackingEdges & i3) != 0) {
                    this.mCallback.onEdgeTouched(i3 & this.mTrackingEdges, pointerId)
                    break
                }
                break
            case 1:
                if (this.mDragState == 1) {
                    releaseViewForPointerUp()
                }
                cancel()
                break
            case 2:
                if (this.mDragState != 1) {
                    Int pointerCount = motionEvent.getPointerCount()
                    while (i2 < pointerCount) {
                        Int pointerId2 = motionEvent.getPointerId(i2)
                        if (isValidPointerForActionMove(pointerId2)) {
                            Float x2 = motionEvent.getX(i2)
                            Float y2 = motionEvent.getY(i2)
                            Float f = x2 - this.mInitialMotionX[pointerId2]
                            Float f2 = y2 - this.mInitialMotionY[pointerId2]
                            reportNewEdgeDrags(f, f2, pointerId2)
                            if (this.mDragState != 1) {
                                View viewFindTopChildUnder2 = findTopChildUnder((Int) x2, (Int) y2)
                                if (!checkTouchSlop(viewFindTopChildUnder2, f, f2) || !tryCaptureViewForDrag(viewFindTopChildUnder2, pointerId2)) {
                                }
                            }
                            saveLastMotion(motionEvent)
                            break
                        }
                        i2++
                    }
                    saveLastMotion(motionEvent)
                } else if (isValidPointerForActionMove(this.mActivePointerId)) {
                    Int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId)
                    Float x3 = motionEvent.getX(iFindPointerIndex)
                    Float y3 = motionEvent.getY(iFindPointerIndex)
                    Int i4 = (Int) (x3 - this.mLastMotionX[this.mActivePointerId])
                    Int i5 = (Int) (y3 - this.mLastMotionY[this.mActivePointerId])
                    dragTo(this.mCapturedView.getLeft() + i4, this.mCapturedView.getTop() + i5, i4, i5)
                    saveLastMotion(motionEvent)
                    break
                }
                break
            case 3:
                if (this.mDragState == 1) {
                    dispatchViewReleased(0.0f, 0.0f)
                }
                cancel()
                break
            case 5:
                Int pointerId3 = motionEvent.getPointerId(actionIndex)
                Float x4 = motionEvent.getX(actionIndex)
                Float y4 = motionEvent.getY(actionIndex)
                saveInitialMotion(x4, y4, pointerId3)
                if (this.mDragState != 0) {
                    if (isCapturedViewUnder((Int) x4, (Int) y4)) {
                        tryCaptureViewForDrag(this.mCapturedView, pointerId3)
                        break
                    }
                } else {
                    tryCaptureViewForDrag(findTopChildUnder((Int) x4, (Int) y4), pointerId3)
                    Int i6 = this.mInitialEdgesTouched[pointerId3]
                    if ((this.mTrackingEdges & i6) != 0) {
                        this.mCallback.onEdgeTouched(i6 & this.mTrackingEdges, pointerId3)
                        break
                    }
                }
                break
            case 6:
                Int pointerId4 = motionEvent.getPointerId(actionIndex)
                if (this.mDragState == 1 && pointerId4 == this.mActivePointerId) {
                    Int pointerCount2 = motionEvent.getPointerCount()
                    while (true) {
                        if (i2 >= pointerCount2) {
                            i = -1
                        } else {
                            Int pointerId5 = motionEvent.getPointerId(i2)
                            if (pointerId5 != this.mActivePointerId) {
                                if (findTopChildUnder((Int) motionEvent.getX(i2), (Int) motionEvent.getY(i2)) == this.mCapturedView && tryCaptureViewForDrag(this.mCapturedView, pointerId5)) {
                                    i = this.mActivePointerId
                                }
                            }
                            i2++
                        }
                    }
                    if (i == -1) {
                        releaseViewForPointerUp()
                    }
                }
                clearMotionHistory(pointerId4)
                break
        }
    }

    Unit setDragState(Int i) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable)
        if (this.mDragState != i) {
            this.mDragState = i
            this.mCallback.onViewDragStateChanged(i)
            if (this.mDragState == 0) {
                this.mCapturedView = null
            }
        }
    }

    fun setEdgeTrackingEnabled(Int i) {
        this.mTrackingEdges = i
    }

    fun setMinVelocity(Float f) {
        this.mMinVelocity = f
    }

    fun settleCapturedViewAt(Int i, Int i2) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(i, i2, (Int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (Int) this.mVelocityTracker.getYVelocity(this.mActivePointerId))
        }
        throw IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased")
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0103  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun shouldInterceptTouchEvent(@android.support.annotation.NonNull android.view.MotionEvent r14) {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: android.support.v4.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):Boolean")
    }

    fun smoothSlideViewTo(@NonNull View view, Int i, Int i2) {
        this.mCapturedView = view
        this.mActivePointerId = -1
        Boolean zForceSettleCapturedViewAt = forceSettleCapturedViewAt(i, i2, 0, 0)
        if (!zForceSettleCapturedViewAt && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null
        }
        return zForceSettleCapturedViewAt
    }

    Boolean tryCaptureViewForDrag(View view, Int i) {
        if (view == this.mCapturedView && this.mActivePointerId == i) {
            return true
        }
        if (view == null || !this.mCallback.tryCaptureView(view, i)) {
            return false
        }
        this.mActivePointerId = i
        captureChildView(view, i)
        return true
    }
}
