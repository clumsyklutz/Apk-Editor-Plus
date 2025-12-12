package android.support.v7.widget

import android.R
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Region
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.Nullable
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.text.AllCapsTransformationMethod
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.util.Property
import android.view.ActionMode
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.CompoundButton
import java.lang.reflect.InvocationTargetException

class SwitchCompat extends CompoundButton {
    private static val ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch"
    private static val MONOSPACE = 3
    private static val SANS = 1
    private static val SERIF = 2
    private static val THUMB_ANIMATION_DURATION = 250
    private static val TOUCH_MODE_DOWN = 1
    private static val TOUCH_MODE_DRAGGING = 2
    private static val TOUCH_MODE_IDLE = 0
    private Boolean mHasThumbTint
    private Boolean mHasThumbTintMode
    private Boolean mHasTrackTint
    private Boolean mHasTrackTintMode
    private Int mMinFlingVelocity
    private Layout mOffLayout
    private Layout mOnLayout
    ObjectAnimator mPositionAnimator
    private Boolean mShowText
    private Boolean mSplitTrack
    private Int mSwitchBottom
    private Int mSwitchHeight
    private Int mSwitchLeft
    private Int mSwitchMinWidth
    private Int mSwitchPadding
    private Int mSwitchRight
    private Int mSwitchTop
    private TransformationMethod mSwitchTransformationMethod
    private Int mSwitchWidth
    private final Rect mTempRect
    private ColorStateList mTextColors
    private CharSequence mTextOff
    private CharSequence mTextOn
    private final TextPaint mTextPaint
    private Drawable mThumbDrawable
    Float mThumbPosition
    private Int mThumbTextPadding
    private ColorStateList mThumbTintList
    private PorterDuff.Mode mThumbTintMode
    private Int mThumbWidth
    private Int mTouchMode
    private Int mTouchSlop
    private Float mTouchX
    private Float mTouchY
    private Drawable mTrackDrawable
    private ColorStateList mTrackTintList
    private PorterDuff.Mode mTrackTintMode
    private VelocityTracker mVelocityTracker
    private static val THUMB_POS = Property(Float.class, "thumbPos") { // from class: android.support.v7.widget.SwitchCompat.1
        @Override // android.util.Property
        public final Float get(SwitchCompat switchCompat) {
            return Float.valueOf(switchCompat.mThumbPosition)
        }

        @Override // android.util.Property
        public final Unit set(SwitchCompat switchCompat, Float f) {
            switchCompat.setThumbPosition(f.floatValue())
        }
    }
    private static final Array<Int> CHECKED_STATE_SET = {R.attr.state_checked}

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.support.v7.appcompat.R.attr.switchStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mThumbTintList = null
        this.mThumbTintMode = null
        this.mHasThumbTint = false
        this.mHasThumbTintMode = false
        this.mTrackTintList = null
        this.mTrackTintMode = null
        this.mHasTrackTint = false
        this.mHasTrackTintMode = false
        this.mVelocityTracker = VelocityTracker.obtain()
        this.mTempRect = Rect()
        this.mTextPaint = TextPaint(1)
        Resources resources = getResources()
        this.mTextPaint.density = resources.getDisplayMetrics().density
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, android.support.v7.appcompat.R.styleable.SwitchCompat, i, 0)
        this.mThumbDrawable = tintTypedArrayObtainStyledAttributes.getDrawable(android.support.v7.appcompat.R.styleable.SwitchCompat_android_thumb)
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(this)
        }
        this.mTrackDrawable = tintTypedArrayObtainStyledAttributes.getDrawable(android.support.v7.appcompat.R.styleable.SwitchCompat_track)
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this)
        }
        this.mTextOn = tintTypedArrayObtainStyledAttributes.getText(android.support.v7.appcompat.R.styleable.SwitchCompat_android_textOn)
        this.mTextOff = tintTypedArrayObtainStyledAttributes.getText(android.support.v7.appcompat.R.styleable.SwitchCompat_android_textOff)
        this.mShowText = tintTypedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.SwitchCompat_showText, true)
        this.mThumbTextPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.SwitchCompat_thumbTextPadding, 0)
        this.mSwitchMinWidth = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.SwitchCompat_switchMinWidth, 0)
        this.mSwitchPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.SwitchCompat_switchPadding, 0)
        this.mSplitTrack = tintTypedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.SwitchCompat_splitTrack, false)
        ColorStateList colorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.v7.appcompat.R.styleable.SwitchCompat_thumbTint)
        if (colorStateList != null) {
            this.mThumbTintList = colorStateList
            this.mHasThumbTint = true
        }
        PorterDuff.Mode tintMode = DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.SwitchCompat_thumbTintMode, -1), null)
        if (this.mThumbTintMode != tintMode) {
            this.mThumbTintMode = tintMode
            this.mHasThumbTintMode = true
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            applyThumbTint()
        }
        ColorStateList colorStateList2 = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.v7.appcompat.R.styleable.SwitchCompat_trackTint)
        if (colorStateList2 != null) {
            this.mTrackTintList = colorStateList2
            this.mHasTrackTint = true
        }
        PorterDuff.Mode tintMode2 = DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.SwitchCompat_trackTintMode, -1), null)
        if (this.mTrackTintMode != tintMode2) {
            this.mTrackTintMode = tintMode2
            this.mHasTrackTintMode = true
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            applyTrackTint()
        }
        Int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.SwitchCompat_switchTextAppearance, 0)
        if (resourceId != 0) {
            setSwitchTextAppearance(context, resourceId)
        }
        tintTypedArrayObtainStyledAttributes.recycle()
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context)
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop()
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity()
        refreshDrawableState()
        setChecked(isChecked())
    }

    private fun animateThumbToCheckedState(Boolean z) {
        this.mPositionAnimator = ObjectAnimator.ofFloat(this, (Property<SwitchCompat, Float>) THUMB_POS, z ? 1.0f : 0.0f)
        this.mPositionAnimator.setDuration(250L)
        if (Build.VERSION.SDK_INT >= 18) {
            this.mPositionAnimator.setAutoCancel(true)
        }
        this.mPositionAnimator.start()
    }

    private fun applyThumbTint() {
        if (this.mThumbDrawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                this.mThumbDrawable = this.mThumbDrawable.mutate()
                if (this.mHasThumbTint) {
                    DrawableCompat.setTintList(this.mThumbDrawable, this.mThumbTintList)
                }
                if (this.mHasThumbTintMode) {
                    DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode)
                }
                if (this.mThumbDrawable.isStateful()) {
                    this.mThumbDrawable.setState(getDrawableState())
                }
            }
        }
    }

    private fun applyTrackTint() {
        if (this.mTrackDrawable != null) {
            if (this.mHasTrackTint || this.mHasTrackTintMode) {
                this.mTrackDrawable = this.mTrackDrawable.mutate()
                if (this.mHasTrackTint) {
                    DrawableCompat.setTintList(this.mTrackDrawable, this.mTrackTintList)
                }
                if (this.mHasTrackTintMode) {
                    DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode)
                }
                if (this.mTrackDrawable.isStateful()) {
                    this.mTrackDrawable.setState(getDrawableState())
                }
            }
        }
    }

    private fun cancelPositionAnimator() {
        if (this.mPositionAnimator != null) {
            this.mPositionAnimator.cancel()
        }
    }

    private fun cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent)
        motionEventObtain.setAction(3)
        super.onTouchEvent(motionEventObtain)
        motionEventObtain.recycle()
    }

    private fun constrain(Float f, Float f2, Float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f
    }

    private fun getTargetCheckedState() {
        return this.mThumbPosition > 0.5f
    }

    private fun getThumbOffset() {
        return (Int) (((ViewUtils.isLayoutRtl(this) ? 1.0f - this.mThumbPosition : this.mThumbPosition) * getThumbScrollRange()) + 0.5f)
    }

    private fun getThumbScrollRange() {
        if (this.mTrackDrawable == null) {
            return 0
        }
        Rect rect = this.mTempRect
        this.mTrackDrawable.getPadding(rect)
        Rect opticalBounds = this.mThumbDrawable != null ? DrawableUtils.getOpticalBounds(this.mThumbDrawable) : DrawableUtils.INSETS_NONE
        return ((((this.mSwitchWidth - this.mThumbWidth) - rect.left) - rect.right) - opticalBounds.left) - opticalBounds.right
    }

    private fun hitThumb(Float f, Float f2) {
        if (this.mThumbDrawable == null) {
            return false
        }
        Int thumbOffset = getThumbOffset()
        this.mThumbDrawable.getPadding(this.mTempRect)
        Int i = this.mSwitchTop - this.mTouchSlop
        Int i2 = (thumbOffset + this.mSwitchLeft) - this.mTouchSlop
        return f > ((Float) i2) && f < ((Float) ((((this.mThumbWidth + i2) + this.mTempRect.left) + this.mTempRect.right) + this.mTouchSlop)) && f2 > ((Float) i) && f2 < ((Float) (this.mSwitchBottom + this.mTouchSlop))
    }

    private fun makeLayout(CharSequence charSequence) {
        CharSequence transformation = this.mSwitchTransformationMethod != null ? this.mSwitchTransformationMethod.getTransformation(charSequence, this) : charSequence
        return StaticLayout(transformation, this.mTextPaint, transformation != null ? (Int) Math.ceil(Layout.getDesiredWidth(transformation, this.mTextPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true)
    }

    private fun setSwitchTypefaceByIndex(Int i, Int i2) {
        Typeface typeface = null
        switch (i) {
            case 1:
                typeface = Typeface.SANS_SERIF
                break
            case 2:
                typeface = Typeface.SERIF
                break
            case 3:
                typeface = Typeface.MONOSPACE
                break
        }
        setSwitchTypeface(typeface, i2)
    }

    private fun stopDrag(MotionEvent motionEvent) {
        Boolean targetCheckedState = true
        this.mTouchMode = 0
        Boolean z = motionEvent.getAction() == 1 && isEnabled()
        Boolean zIsChecked = isChecked()
        if (z) {
            this.mVelocityTracker.computeCurrentVelocity(1000)
            Float xVelocity = this.mVelocityTracker.getXVelocity()
            if (Math.abs(xVelocity) <= this.mMinFlingVelocity) {
                targetCheckedState = getTargetCheckedState()
            } else if (ViewUtils.isLayoutRtl(this)) {
                if (xVelocity >= 0.0f) {
                    targetCheckedState = false
                }
            } else if (xVelocity <= 0.0f) {
                targetCheckedState = false
            }
        } else {
            targetCheckedState = zIsChecked
        }
        if (targetCheckedState != zIsChecked) {
            playSoundEffect(0)
        }
        setChecked(targetCheckedState)
        cancelSuperTouch(motionEvent)
    }

    @Override // android.view.View
    fun draw(Canvas canvas) {
        Int i
        Int i2
        Int i3
        Rect rect = this.mTempRect
        Int i4 = this.mSwitchLeft
        Int i5 = this.mSwitchTop
        Int i6 = this.mSwitchRight
        Int i7 = this.mSwitchBottom
        Int thumbOffset = i4 + getThumbOffset()
        Rect opticalBounds = this.mThumbDrawable != null ? DrawableUtils.getOpticalBounds(this.mThumbDrawable) : DrawableUtils.INSETS_NONE
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect)
            Int i8 = rect.left + thumbOffset
            if (opticalBounds != null) {
                if (opticalBounds.left > rect.left) {
                    i4 += opticalBounds.left - rect.left
                }
                i3 = opticalBounds.top > rect.top ? (opticalBounds.top - rect.top) + i5 : i5
                if (opticalBounds.right > rect.right) {
                    i6 -= opticalBounds.right - rect.right
                }
                i2 = opticalBounds.bottom > rect.bottom ? i7 - (opticalBounds.bottom - rect.bottom) : i7
            } else {
                i2 = i7
                i3 = i5
            }
            this.mTrackDrawable.setBounds(i4, i3, i6, i2)
            i = i8
        } else {
            i = thumbOffset
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect)
            Int i9 = i - rect.left
            Int i10 = i + this.mThumbWidth + rect.right
            this.mThumbDrawable.setBounds(i9, i5, i10, i7)
            Drawable background = getBackground()
            if (background != null) {
                DrawableCompat.setHotspotBounds(background, i9, i5, i10, i7)
            }
        }
        super.draw(canvas)
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    fun drawableHotspotChanged(Float f, Float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(f, f2)
        }
        if (this.mThumbDrawable != null) {
            DrawableCompat.setHotspot(this.mThumbDrawable, f, f2)
        }
        if (this.mTrackDrawable != null) {
            DrawableCompat.setHotspot(this.mTrackDrawable, f, f2)
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected fun drawableStateChanged() {
        super.drawableStateChanged()
        Array<Int> drawableState = getDrawableState()
        Boolean state = false
        Drawable drawable = this.mThumbDrawable
        if (drawable != null && drawable.isStateful()) {
            state = drawable.setState(drawableState) | false
        }
        Drawable drawable2 = this.mTrackDrawable
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState)
        }
        if (state) {
            invalidate()
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    fun getCompoundPaddingLeft() {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft()
        }
        Int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.mSwitchWidth
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.mSwitchPadding : compoundPaddingLeft
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    fun getCompoundPaddingRight() {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight()
        }
        Int compoundPaddingRight = super.getCompoundPaddingRight() + this.mSwitchWidth
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.mSwitchPadding : compoundPaddingRight
    }

    fun getShowText() {
        return this.mShowText
    }

    fun getSplitTrack() {
        return this.mSplitTrack
    }

    fun getSwitchMinWidth() {
        return this.mSwitchMinWidth
    }

    fun getSwitchPadding() {
        return this.mSwitchPadding
    }

    fun getTextOff() {
        return this.mTextOff
    }

    fun getTextOn() {
        return this.mTextOn
    }

    fun getThumbDrawable() {
        return this.mThumbDrawable
    }

    fun getThumbTextPadding() {
        return this.mThumbTextPadding
    }

    @Nullable
    fun getThumbTintList() {
        return this.mThumbTintList
    }

    @Nullable
    public PorterDuff.Mode getThumbTintMode() {
        return this.mThumbTintMode
    }

    fun getTrackDrawable() {
        return this.mTrackDrawable
    }

    @Nullable
    fun getTrackTintList() {
        return this.mTrackTintList
    }

    @Nullable
    public PorterDuff.Mode getTrackTintMode() {
        return this.mTrackTintMode
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.jumpToCurrentState()
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.jumpToCurrentState()
        }
        if (this.mPositionAnimator == null || !this.mPositionAnimator.isStarted()) {
            return
        }
        this.mPositionAnimator.end()
        this.mPositionAnimator = null
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected Array<Int> onCreateDrawableState(Int i) {
        Array<Int> iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1)
        if (isChecked()) {
            mergeDrawableStates(iArrOnCreateDrawableState, CHECKED_STATE_SET)
        }
        return iArrOnCreateDrawableState
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected fun onDraw(Canvas canvas) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Int width
        super.onDraw(canvas)
        Rect rect = this.mTempRect
        Drawable drawable = this.mTrackDrawable
        if (drawable != null) {
            drawable.getPadding(rect)
        } else {
            rect.setEmpty()
        }
        Int i = this.mSwitchTop
        Int i2 = this.mSwitchBottom
        Int i3 = i + rect.top
        Int i4 = i2 - rect.bottom
        Drawable drawable2 = this.mThumbDrawable
        if (drawable != null) {
            if (!this.mSplitTrack || drawable2 == null) {
                drawable.draw(canvas)
            } else {
                Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable2)
                drawable2.copyBounds(rect)
                rect.left += opticalBounds.left
                rect.right -= opticalBounds.right
                Int iSave = canvas.save()
                canvas.clipRect(rect, Region.Op.DIFFERENCE)
                drawable.draw(canvas)
                canvas.restoreToCount(iSave)
            }
        }
        Int iSave2 = canvas.save()
        if (drawable2 != null) {
            drawable2.draw(canvas)
        }
        Layout layout = getTargetCheckedState() ? this.mOnLayout : this.mOffLayout
        if (layout != null) {
            Array<Int> drawableState = getDrawableState()
            if (this.mTextColors != null) {
                this.mTextPaint.setColor(this.mTextColors.getColorForState(drawableState, 0))
            }
            this.mTextPaint.drawableState = drawableState
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds()
                width = bounds.right + bounds.left
            } else {
                width = getWidth()
            }
            canvas.translate((width / 2) - (layout.getWidth() / 2), ((i3 + i4) / 2) - (layout.getHeight() / 2))
            layout.draw(canvas)
        }
        canvas.restoreToCount(iSave2)
    }

    @Override // android.view.View
    fun onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent)
        accessibilityEvent.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME)
    }

    @Override // android.view.View
    fun onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo)
        accessibilityNodeInfo.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME)
        CharSequence charSequence = isChecked() ? this.mTextOn : this.mTextOff
        if (TextUtils.isEmpty(charSequence)) {
            return
        }
        CharSequence text = accessibilityNodeInfo.getText()
        if (TextUtils.isEmpty(text)) {
            accessibilityNodeInfo.setText(charSequence)
            return
        }
        StringBuilder sb = StringBuilder()
        sb.append(text).append(' ').append(charSequence)
        accessibilityNodeInfo.setText(sb)
    }

    @Override // android.widget.TextView, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Int iMax
        Int i5
        Int i6
        Int height
        Int paddingTop
        Int iMax2 = 0
        super.onLayout(z, i, i2, i3, i4)
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding(rect)
            } else {
                rect.setEmpty()
            }
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.mThumbDrawable)
            iMax = Math.max(0, opticalBounds.left - rect.left)
            iMax2 = Math.max(0, opticalBounds.right - rect.right)
        } else {
            iMax = 0
        }
        if (ViewUtils.isLayoutRtl(this)) {
            Int paddingLeft = getPaddingLeft() + iMax
            i6 = ((this.mSwitchWidth + paddingLeft) - iMax) - iMax2
            i5 = paddingLeft
        } else {
            Int width = (getWidth() - getPaddingRight()) - iMax2
            i5 = iMax2 + iMax + (width - this.mSwitchWidth)
            i6 = width
        }
        switch (getGravity() & android.support.v7.appcompat.R.styleable.AppCompatTheme_ratingBarStyleSmall) {
            case 16:
                paddingTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2)
                height = this.mSwitchHeight + paddingTop
                break
            case android.support.v7.appcompat.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 80 */:
                height = getHeight() - getPaddingBottom()
                paddingTop = height - this.mSwitchHeight
                break
            default:
                paddingTop = getPaddingTop()
                height = this.mSwitchHeight + paddingTop
                break
        }
        this.mSwitchLeft = i5
        this.mSwitchTop = paddingTop
        this.mSwitchBottom = height
        this.mSwitchRight = i6
    }

    @Override // android.widget.TextView, android.view.View
    fun onMeasure(Int i, Int i2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Int intrinsicHeight
        Int intrinsicWidth
        Int intrinsicHeight2 = 0
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOn)
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOff)
            }
        }
        Rect rect = this.mTempRect
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect)
            intrinsicWidth = (this.mThumbDrawable.getIntrinsicWidth() - rect.left) - rect.right
            intrinsicHeight = this.mThumbDrawable.getIntrinsicHeight()
        } else {
            intrinsicHeight = 0
            intrinsicWidth = 0
        }
        this.mThumbWidth = Math.max(this.mShowText ? Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + (this.mThumbTextPadding << 1) : 0, intrinsicWidth)
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect)
            intrinsicHeight2 = this.mTrackDrawable.getIntrinsicHeight()
        } else {
            rect.setEmpty()
        }
        Int iMax = rect.left
        Int iMax2 = rect.right
        if (this.mThumbDrawable != null) {
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.mThumbDrawable)
            iMax = Math.max(iMax, opticalBounds.left)
            iMax2 = Math.max(iMax2, opticalBounds.right)
        }
        Int iMax3 = Math.max(this.mSwitchMinWidth, iMax + (this.mThumbWidth * 2) + iMax2)
        Int iMax4 = Math.max(intrinsicHeight2, intrinsicHeight)
        this.mSwitchWidth = iMax3
        this.mSwitchHeight = iMax4
        super.onMeasure(i, i2)
        if (getMeasuredHeight() < iMax4) {
            setMeasuredDimension(getMeasuredWidthAndState(), iMax4)
        }
    }

    @Override // android.view.View
    fun onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent)
        CharSequence charSequence = isChecked() ? this.mTextOn : this.mTextOff
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence)
        }
    }

    @Override // android.widget.TextView, android.view.View
    fun onTouchEvent(MotionEvent motionEvent) {
        this.mVelocityTracker.addMovement(motionEvent)
        switch (motionEvent.getActionMasked()) {
            case 0:
                Float x = motionEvent.getX()
                Float y = motionEvent.getY()
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1
                    this.mTouchX = x
                    this.mTouchY = y
                    break
                }
                break
            case 1:
            case 3:
                if (this.mTouchMode != 2) {
                    this.mTouchMode = 0
                    this.mVelocityTracker.clear()
                    break
                } else {
                    stopDrag(motionEvent)
                    super.onTouchEvent(motionEvent)
                    return true
                }
            case 2:
                switch (this.mTouchMode) {
                    case 1:
                        Float x2 = motionEvent.getX()
                        Float y2 = motionEvent.getY()
                        if (Math.abs(x2 - this.mTouchX) > this.mTouchSlop || Math.abs(y2 - this.mTouchY) > this.mTouchSlop) {
                            this.mTouchMode = 2
                            getParent().requestDisallowInterceptTouchEvent(true)
                            this.mTouchX = x2
                            this.mTouchY = y2
                            return true
                        }
                        break
                    case 2:
                        Float x3 = motionEvent.getX()
                        Int thumbScrollRange = getThumbScrollRange()
                        Float f = x3 - this.mTouchX
                        Float f2 = thumbScrollRange != 0 ? f / thumbScrollRange : f > 0.0f ? 1.0f : -1.0f
                        if (ViewUtils.isLayoutRtl(this)) {
                            f2 = -f2
                        }
                        Float fConstrain = constrain(f2 + this.mThumbPosition, 0.0f, 1.0f)
                        if (fConstrain != this.mThumbPosition) {
                            this.mTouchX = x3
                            setThumbPosition(fConstrain)
                        }
                        return true
                }
        }
        return super.onTouchEvent(motionEvent)
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    fun setChecked(Boolean z) {
        super.setChecked(z)
        Boolean zIsChecked = isChecked()
        if (getWindowToken() != null && ViewCompat.isLaidOut(this)) {
            animateThumbToCheckedState(zIsChecked)
        } else {
            cancelPositionAnimator()
            setThumbPosition(zIsChecked ? 1.0f : 0.0f)
        }
    }

    @Override // android.widget.TextView
    fun setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback))
    }

    fun setShowText(Boolean z) {
        if (this.mShowText != z) {
            this.mShowText = z
            requestLayout()
        }
    }

    fun setSplitTrack(Boolean z) {
        this.mSplitTrack = z
        invalidate()
    }

    fun setSwitchMinWidth(Int i) {
        this.mSwitchMinWidth = i
        requestLayout()
    }

    fun setSwitchPadding(Int i) {
        this.mSwitchPadding = i
        requestLayout()
    }

    fun setSwitchTextAppearance(Context context, Int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, android.support.v7.appcompat.R.styleable.TextAppearance)
        ColorStateList colorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor)
        if (colorStateList != null) {
            this.mTextColors = colorStateList
        } else {
            this.mTextColors = getTextColors()
        }
        Int dimensionPixelSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, 0)
        if (dimensionPixelSize != 0 && dimensionPixelSize != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(dimensionPixelSize)
            requestLayout()
        }
        setSwitchTypefaceByIndex(tintTypedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.TextAppearance_android_typeface, -1), tintTypedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.TextAppearance_android_textStyle, -1))
        if (tintTypedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.TextAppearance_textAllCaps, false)) {
            this.mSwitchTransformationMethod = AllCapsTransformationMethod(getContext())
        } else {
            this.mSwitchTransformationMethod = null
        }
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    fun setSwitchTypeface(Typeface typeface) {
        if ((this.mTextPaint.getTypeface() == null || this.mTextPaint.getTypeface().equals(typeface)) && (this.mTextPaint.getTypeface() != null || typeface == null)) {
            return
        }
        this.mTextPaint.setTypeface(typeface)
        requestLayout()
        invalidate()
    }

    fun setSwitchTypeface(Typeface typeface, Int i) {
        if (i <= 0) {
            this.mTextPaint.setFakeBoldText(false)
            this.mTextPaint.setTextSkewX(0.0f)
            setSwitchTypeface(typeface)
        } else {
            Typeface typefaceDefaultFromStyle = typeface == null ? Typeface.defaultFromStyle(i) : Typeface.create(typeface, i)
            setSwitchTypeface(typefaceDefaultFromStyle)
            Int style = ((typefaceDefaultFromStyle != null ? typefaceDefaultFromStyle.getStyle() : 0) ^ (-1)) & i
            this.mTextPaint.setFakeBoldText((style & 1) != 0)
            this.mTextPaint.setTextSkewX((style & 2) != 0 ? -0.25f : 0.0f)
        }
    }

    fun setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence
        requestLayout()
    }

    fun setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence
        requestLayout()
    }

    fun setThumbDrawable(Drawable drawable) {
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(null)
        }
        this.mThumbDrawable = drawable
        if (drawable != null) {
            drawable.setCallback(this)
        }
        requestLayout()
    }

    Unit setThumbPosition(Float f) {
        this.mThumbPosition = f
        invalidate()
    }

    fun setThumbResource(Int i) {
        setThumbDrawable(AppCompatResources.getDrawable(getContext(), i))
    }

    fun setThumbTextPadding(Int i) {
        this.mThumbTextPadding = i
        requestLayout()
    }

    fun setThumbTintList(@Nullable ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList
        this.mHasThumbTint = true
        applyThumbTint()
    }

    fun setThumbTintMode(@Nullable PorterDuff.Mode mode) {
        this.mThumbTintMode = mode
        this.mHasThumbTintMode = true
        applyThumbTint()
    }

    fun setTrackDrawable(Drawable drawable) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null)
        }
        this.mTrackDrawable = drawable
        if (drawable != null) {
            drawable.setCallback(this)
        }
        requestLayout()
    }

    fun setTrackResource(Int i) {
        setTrackDrawable(AppCompatResources.getDrawable(getContext(), i))
    }

    fun setTrackTintList(@Nullable ColorStateList colorStateList) {
        this.mTrackTintList = colorStateList
        this.mHasTrackTint = true
        applyTrackTint()
    }

    fun setTrackTintMode(@Nullable PorterDuff.Mode mode) {
        this.mTrackTintMode = mode
        this.mHasTrackTintMode = true
        applyTrackTint()
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    fun toggle() {
        setChecked(!isChecked())
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected fun verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable
    }
}
