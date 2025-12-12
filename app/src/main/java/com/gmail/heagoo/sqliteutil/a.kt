package com.gmail.heagoo.sqliteutil

import android.graphics.Rect
import android.os.Handler
import android.view.FocusFinder
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.Scroller

class a extends FrameLayout {

    /* renamed from: a, reason: collision with root package name */
    private Long f1561a

    /* renamed from: b, reason: collision with root package name */
    private final Rect f1562b
    private Boolean c
    private Float d
    private Float e
    private Boolean f
    private View g
    private Boolean h
    private VelocityTracker i
    private Int j
    private Long k
    private View l

    private fun a(Int i, Int i2, Int i3) {
        if (i2 >= i3 || i < 0) {
            return 0
        }
        return i2 + i > i3 ? i3 - i2 : i
    }

    private fun a(Rect rect) {
        Int iMax
        if (getChildCount() == 0) {
            return 0
        }
        Int height = getHeight()
        Int scrollY = getScrollY()
        Int i = scrollY + height
        Int verticalFadingEdgeLength = getVerticalFadingEdgeLength()
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i -= verticalFadingEdgeLength
        }
        if (rect.bottom > i && rect.top > scrollY) {
            iMax = Math.min(rect.height() > height ? (rect.top - scrollY) + 0 : (rect.bottom - i) + 0, getChildAt(0).getBottom() - i)
        } else if (rect.top >= scrollY || rect.bottom >= i) {
            iMax = 0
        } else {
            iMax = Math.max(rect.height() > height ? 0 - (i - rect.bottom) : 0 - (scrollY - rect.top), -getScrollY())
        }
        return iMax
    }

    private fun a(MotionEvent motionEvent) {
        Int action = (motionEvent.getAction() >> 8) & 255
        if (motionEvent.getPointerId(action) == this.j) {
            Int i = action == 0 ? 1 : 0
            this.e = motionEvent.getX(i)
            this.d = motionEvent.getY(i)
            this.j = motionEvent.getPointerId(i)
            if (this.i != null) {
                this.i.clear()
            }
        }
    }

    private fun a(View view) {
        view.getDrawingRect(this.f1562b)
        offsetDescendantRectToMyCoords(view, this.f1562b)
        Int iA = a(this.f1562b)
        Int iB = b(this.f1562b)
        if (iB == 0 && iA == 0) {
            return
        }
        scrollBy(iB, iA)
    }

    private fun a() {
        View childAt = getChildAt(0)
        if (childAt != null) {
            return getHeight() < (childAt.getHeight() + getPaddingTop()) + getPaddingBottom()
        }
        return false
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(Int r19) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.sqliteutil.a.a(Int):Boolean")
    }

    private fun a(Int i, Int i2) {
        if (getChildCount() <= 0) {
            return false
        }
        Int scrollX = getScrollX()
        Int scrollY = getScrollY()
        View childAt = getChildAt(0)
        return i2 >= childAt.getTop() - scrollY && i2 < childAt.getBottom() - scrollY && i >= childAt.getLeft() - scrollX && i < childAt.getRight() - scrollX
    }

    private fun a(View view, Int i) {
        view.getDrawingRect(this.f1562b)
        offsetDescendantRectToMyCoords(view, this.f1562b)
        return this.f1562b.right + i >= getScrollX() && this.f1562b.left - i <= getScrollX() + getWidth()
    }

    private fun a(View view, Int i, Int i2) {
        view.getDrawingRect(this.f1562b)
        offsetDescendantRectToMyCoords(view, this.f1562b)
        return this.f1562b.bottom + i >= getScrollY() && this.f1562b.top - i <= getScrollY() + i2
    }

    private fun a(View view, View view2) {
        if (view == view2) {
            return true
        }
        Object parent = view.getParent()
        return (parent is ViewGroup) && a((View) parent, view2)
    }

    private fun b(Rect rect) {
        Int iMax
        if (getChildCount() == 0) {
            return 0
        }
        Int width = getWidth()
        Int scrollX = getScrollX()
        Int i = scrollX + width
        Int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength()
        if (rect.left > 0) {
            scrollX += horizontalFadingEdgeLength
        }
        if (rect.right < getChildAt(0).getWidth()) {
            i -= horizontalFadingEdgeLength
        }
        if (rect.right > i && rect.left > scrollX) {
            iMax = Math.min(rect.width() > width ? (rect.left - scrollX) + 0 : (rect.right - i) + 0, getChildAt(0).getRight() - i)
        } else if (rect.left >= scrollX || rect.right >= i) {
            iMax = 0
        } else {
            iMax = Math.max(rect.width() > width ? 0 - (i - rect.right) : 0 - (scrollX - rect.left), -getScrollX())
        }
        return iMax
    }

    static /* synthetic */ Rect b(a aVar) {
        return null
    }

    private fun b() {
        View childAt = getChildAt(0)
        if (childAt != null) {
            return getWidth() < (childAt.getWidth() + getPaddingLeft()) + getPaddingRight()
        }
        return false
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun b(Int r19) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.sqliteutil.a.b(Int):Boolean")
    }

    private fun c(Int i) {
        View viewFindFocus = findFocus()
        if (viewFindFocus == this) {
            viewFindFocus = null
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i)
        Int bottom = (Int) (0.5f * (getBottom() - getTop()))
        if (viewFindNextFocus == null || !a(viewFindNextFocus, bottom, getHeight())) {
            if (i == 33 && getScrollY() < bottom) {
                bottom = getScrollY()
            } else if (i == 130 && getChildCount() > 0) {
                Int bottom2 = getChildAt(0).getBottom()
                Int scrollY = getScrollY() + getHeight()
                if (bottom2 - scrollY < bottom) {
                    bottom = bottom2 - scrollY
                }
            }
            if (bottom == 0) {
                return false
            }
            if (i != 130) {
                bottom = -bottom
            }
            e(bottom)
        } else {
            viewFindNextFocus.getDrawingRect(this.f1562b)
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.f1562b)
            e(a(this.f1562b))
            viewFindNextFocus.requestFocus(i)
        }
        if (viewFindFocus != null && viewFindFocus.isFocused()) {
            if (a(viewFindFocus, 0, getHeight()) ? false : true) {
                Int descendantFocusability = getDescendantFocusability()
                setDescendantFocusability(131072)
                requestFocus()
                setDescendantFocusability(descendantFocusability)
            }
        }
        return true
    }

    private fun d(Int i) {
        View viewFindFocus = findFocus()
        if (viewFindFocus == this) {
            viewFindFocus = null
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i)
        Int right = (Int) (0.5f * (getRight() - getLeft()))
        if (viewFindNextFocus == null || !a(viewFindNextFocus, right)) {
            if (i == 17 && getScrollX() < right) {
                right = getScrollX()
            } else if (i == 66 && getChildCount() > 0) {
                Int right2 = getChildAt(0).getRight()
                Int scrollX = getScrollX() + getWidth()
                if (right2 - scrollX < right) {
                    right = right2 - scrollX
                }
            }
            if (right == 0) {
                return false
            }
            if (i != 66) {
                right = -right
            }
            f(right)
        } else {
            viewFindNextFocus.getDrawingRect(this.f1562b)
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.f1562b)
            f(b(this.f1562b))
            viewFindNextFocus.requestFocus(i)
        }
        if (viewFindFocus != null && viewFindFocus.isFocused()) {
            if (a(viewFindFocus, 0) ? false : true) {
                Int descendantFocusability = getDescendantFocusability()
                setDescendantFocusability(131072)
                requestFocus()
                setDescendantFocusability(descendantFocusability)
            }
        }
        return true
    }

    private fun e(Int i) {
        if (i != 0) {
            scrollBy(0, i)
        }
    }

    private fun f(Int i) {
        if (i != 0) {
            scrollBy(i, 0)
        }
    }

    @Override // android.view.ViewGroup
    public final Unit addView(View view) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view)
    }

    @Override // android.view.ViewGroup
    public final Unit addView(View view, Int i) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view, i)
    }

    @Override // android.view.ViewGroup
    public final Unit addView(View view, Int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view, i, layoutParams)
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final Unit addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw IllegalStateException("ScrollView can host only one direct child")
        }
        super.addView(view, layoutParams)
    }

    @Override // android.view.View
    protected final Int computeHorizontalScrollOffset() {
        return Math.max(0, super.computeHorizontalScrollOffset())
    }

    @Override // android.view.View
    protected final Int computeHorizontalScrollRange() {
        return getChildCount() == 0 ? (getWidth() - getPaddingLeft()) - getPaddingRight() : getChildAt(0).getRight()
    }

    @Override // android.view.View
    public final Unit computeScroll() {
        Scroller scroller = null
        if (scroller.computeScrollOffset()) {
            Int currX = scroller.getCurrX()
            Int currY = scroller.getCurrY()
            if (getChildCount() > 0) {
                View childAt = getChildAt(0)
                super.scrollTo(a(currX, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth()), a(currY, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight()))
            }
            awakenScrollBars()
            postInvalidate()
        }
    }

    @Override // android.view.View
    protected final Int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset())
    }

    @Override // android.view.View
    protected final Int computeVerticalScrollRange() {
        return getChildCount() == 0 ? (getHeight() - getPaddingBottom()) - getPaddingTop() : getChildAt(0).getBottom()
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Boolean dispatchKeyEvent(android.view.KeyEvent r7) {
        /*
            r6 = this
            r5 = 130(0x82, Float:1.82E-43)
            r4 = 66
            r3 = 33
            r2 = 17
            r0 = 0
            Boolean r1 = super.dispatchKeyEvent(r7)
            if (r1 != 0) goto L24
            android.graphics.Rect r1 = r6.f1562b
            r1.setEmpty()
            Int r1 = r7.getAction()
            if (r1 != 0) goto L21
            Int r1 = r7.getKeyCode()
            switch(r1) {
                case 19: goto L52
                case 20: goto L68
                case 21: goto L26
                case 22: goto L3c
                default: goto L21
            }
        L21:
            r1 = r0
        L22:
            if (r1 == 0) goto L25
        L24:
            r0 = 1
        L25:
            return r0
        L26:
            Boolean r1 = r6.b()
            if (r1 == 0) goto L21
            Boolean r1 = r7.isAltPressed()
            if (r1 != 0) goto L37
            Boolean r1 = r6.d(r2)
            goto L22
        L37:
            Boolean r1 = r6.b(r2)
            goto L22
        L3c:
            Boolean r1 = r6.b()
            if (r1 == 0) goto L21
            Boolean r1 = r7.isAltPressed()
            if (r1 != 0) goto L4d
            Boolean r1 = r6.d(r4)
            goto L22
        L4d:
            Boolean r1 = r6.b(r4)
            goto L22
        L52:
            Boolean r1 = r6.a()
            if (r1 == 0) goto L21
            Boolean r1 = r7.isAltPressed()
            if (r1 != 0) goto L63
            Boolean r1 = r6.c(r3)
            goto L22
        L63:
            Boolean r1 = r6.a(r3)
            goto L22
        L68:
            Boolean r1 = r6.a()
            if (r1 == 0) goto L21
            Boolean r1 = r7.isAltPressed()
            if (r1 != 0) goto L79
            Boolean r1 = r6.c(r5)
            goto L22
        L79:
            Boolean r1 = r6.a(r5)
            goto L22
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.sqliteutil.a.dispatchKeyEvent(android.view.KeyEvent):Boolean")
    }

    @Override // android.view.View
    protected final Float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f
        }
        Int verticalFadingEdgeLength = getVerticalFadingEdgeLength()
        Int bottom = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom())
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength
        }
        return 1.0f
    }

    @Override // android.view.View
    protected final Float getLeftFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f
        }
        Int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength()
        if (getScrollX() < horizontalFadingEdgeLength) {
            return getScrollX() / horizontalFadingEdgeLength
        }
        return 1.0f
    }

    @Override // android.view.View
    protected final Float getRightFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f
        }
        Int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength()
        Int right = (getChildAt(0).getRight() - getScrollX()) - (getWidth() - getPaddingRight())
        if (right < horizontalFadingEdgeLength) {
            return right / horizontalFadingEdgeLength
        }
        return 1.0f
    }

    @Override // android.view.View
    protected final Float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f
        }
        Int verticalFadingEdgeLength = getVerticalFadingEdgeLength()
        if (getScrollY() < verticalFadingEdgeLength) {
            return getScrollY() / verticalFadingEdgeLength
        }
        return 1.0f
    }

    @Override // android.view.ViewGroup
    protected final Unit measureChild(View view, Int i, Int i2) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0))
    }

    @Override // android.view.ViewGroup
    protected final Unit measureChildWithMargins(View view, Int i, Int i2, Int i3, Int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams()
        view.measure(View.MeasureSpec.makeMeasureSpec(marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, 0), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.bottomMargin + marginLayoutParams.topMargin, 0))
    }

    @Override // android.view.View
    protected final Unit onFinishInflate() {
        if (getChildCount() > 0) {
            this.l = getChildAt(0)
        }
    }

    @Override // android.view.ViewGroup
    public final Boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Int action = motionEvent.getAction()
        if (action == 2 && this.h) {
            return true
        }
        switch (action & 255) {
            case 0:
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                if (!a((Int) x, (Int) y)) {
                    this.h = false
                    break
                } else {
                    this.d = y
                    this.e = x
                    this.j = motionEvent.getPointerId(0)
                    Scroller scroller = null
                    this.h = scroller.isFinished() ? false : true
                    break
                }
            case 1:
            case 3:
                this.h = false
                this.j = -1
                break
            case 2:
                Int i = this.j
                if (i != -1) {
                    Int iFindPointerIndex = motionEvent.findPointerIndex(i)
                    Float y2 = motionEvent.getY(iFindPointerIndex)
                    if (((Int) Math.abs(y2 - this.d)) > 0) {
                        this.h = true
                        this.d = y2
                    }
                    Float x2 = motionEvent.getX(iFindPointerIndex)
                    if (((Int) Math.abs(x2 - this.e)) > 0) {
                        this.h = true
                        this.e = x2
                        break
                    }
                }
                break
            case 6:
                a(motionEvent)
                break
        }
        return this.h
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected final Unit onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        super.onLayout(z, i, i2, i3, i4)
        this.f = false
        if (this.g != null && a(this.g, this)) {
            a(this.g)
        }
        this.g = null
        scrollTo(getScrollX(), getScrollY())
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected final Unit onMeasure(Int i, Int i2) {
        super.onMeasure(i, i2)
    }

    @Override // android.view.ViewGroup
    protected final Boolean onRequestFocusInDescendants(Int i, Rect rect) {
        View viewFindNextFocus = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, i) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, i)
        if (viewFindNextFocus == null) {
            return false
        }
        return viewFindNextFocus.requestFocus(i, rect)
    }

    @Override // android.view.View
    protected final Unit onSizeChanged(Int i, Int i2, Int i3, Int i4) {
        super.onSizeChanged(i, i2, i3, i4)
        View viewFindFocus = findFocus()
        if (viewFindFocus == null || this == viewFindFocus) {
            return
        }
        if (a(viewFindFocus, 0, i4)) {
            viewFindFocus.getDrawingRect(this.f1562b)
            offsetDescendantRectToMyCoords(viewFindFocus, this.f1562b)
            e(a(this.f1562b))
        }
        if (a(viewFindFocus, getRight() - getLeft())) {
            viewFindFocus.getDrawingRect(this.f1562b)
            offsetDescendantRectToMyCoords(viewFindFocus, this.f1562b)
            f(b(this.f1562b))
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public final Boolean onTouchEvent(MotionEvent motionEvent) {
        Rect rect = null
        Byte b2 = 0
        Byte b3 = 0
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false
        }
        if (this.i == null) {
            this.i = VelocityTracker.obtain()
        }
        this.i.addMovement(motionEvent)
        switch (motionEvent.getAction() & 255) {
            case 0:
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                Boolean zA = a((Int) x, (Int) y)
                this.h = zA
                if (!zA) {
                    return false
                }
                if (System.currentTimeMillis() - this.k < 200) {
                    motionEvent.setAction(3)
                }
                this.k = System.currentTimeMillis()
                if (!(b3 == true ? 1 : 0).isFinished()) {
                    (b2 == true ? 1 : 0).abortAnimation()
                }
                this.d = y
                this.e = x
                this.j = motionEvent.getPointerId(0)
                return true
            case 1:
                if (this.h) {
                    if (!rect.isEmpty()) {
                        TranslateAnimation translateAnimation = TranslateAnimation(0.0f, -this.l.getLeft(), 0.0f, -this.l.getTop())
                        translateAnimation.setDuration(200L)
                        this.l.startAnimation(translateAnimation)
                        Handler().postDelayed(b(this), 200L)
                    }
                    this.j = -1
                    this.h = false
                    if (this.i != null) {
                        this.i.recycle()
                        this.i = null
                    }
                }
                return true
            case 2:
                if (this.h) {
                    Int iFindPointerIndex = motionEvent.findPointerIndex(this.j)
                    Float y2 = motionEvent.getY(iFindPointerIndex)
                    Int i = (Int) (this.d - y2)
                    this.d = y2
                    Float x2 = motionEvent.getX(iFindPointerIndex)
                    Int i2 = (Int) (this.e - x2)
                    this.e = x2
                    scrollBy(i2, i)
                    Int measuredWidth = this.l.getMeasuredWidth() - getWidth()
                    Int scrollX = getScrollX()
                    if (scrollX != 0 && scrollX != measuredWidth) {
                        Int measuredHeight = this.l.getMeasuredHeight() - getHeight()
                        Int scrollY = getScrollY()
                        if (scrollY == 0 || scrollY == measuredHeight) {
                        }
                    }
                }
                return true
            case 3:
                if (this.h && getChildCount() > 0) {
                    this.j = -1
                    this.h = false
                    if (this.i != null) {
                        this.i.recycle()
                        this.i = null
                    }
                }
                return true
            case 4:
            case 5:
            default:
                return true
            case 6:
                a(motionEvent)
                return true
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final Unit requestChildFocus(View view, View view2) {
        if (!this.c) {
            if (this.f) {
                this.g = view2
            } else {
                a(view2)
            }
        }
        super.requestChildFocus(view, view2)
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final Boolean requestChildRectangleOnScreen(View view, Rect rect, Boolean z) {
        Scroller scroller = null
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY())
        Int iA = a(rect)
        Int iB = b(rect)
        Boolean z2 = (iB == 0 && iA == 0) ? false : true
        if (z2) {
            if (z) {
                scrollBy(iB, iA)
            } else if (getChildCount() != 0) {
                if (AnimationUtils.currentAnimationTimeMillis() - this.f1561a > 250) {
                    Int iMax = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()))
                    Int scrollY = getScrollY()
                    Int iMax2 = Math.max(0, Math.min(iA + scrollY, iMax)) - scrollY
                    Int iMax3 = Math.max(0, getChildAt(0).getWidth() - ((getWidth() - getPaddingRight()) - getPaddingLeft()))
                    Int scrollX = getScrollX()
                    scroller.startScroll(scrollX, scrollY, Math.max(0, Math.min(iB + scrollX, iMax3)) - scrollX, iMax2)
                    invalidate()
                } else {
                    if (!scroller.isFinished()) {
                        scroller.abortAnimation()
                    }
                    scrollBy(iB, iA)
                }
                this.f1561a = AnimationUtils.currentAnimationTimeMillis()
            }
        }
        return z2
    }

    @Override // android.view.View, android.view.ViewParent
    public final Unit requestLayout() {
        this.f = true
        super.requestLayout()
    }

    @Override // android.view.View
    public final Unit scrollTo(Int i, Int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0)
            Int iA = a(i, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth())
            Int iA2 = a(i2, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight())
            if (iA == getScrollX() && iA2 == getScrollY()) {
                return
            }
            super.scrollTo(iA, iA2)
        }
    }
}
