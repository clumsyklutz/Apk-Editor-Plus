package android.support.v7.graphics.drawable

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.SystemClock
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import androidx.core.graphics.drawable.DrawableCompat
import android.util.SparseArray

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class DrawableContainer extends Drawable implements Drawable.Callback {
    private static val DEBUG = false
    private static val DEFAULT_DITHER = true
    private static val TAG = "DrawableContainer"
    private Runnable mAnimationRunnable
    private BlockInvalidateCallback mBlockInvalidateCallback
    private Drawable mCurrDrawable
    private DrawableContainerState mDrawableContainerState
    private Long mEnterAnimationEnd
    private Long mExitAnimationEnd
    private Boolean mHasAlpha
    private Rect mHotspotBounds
    private Drawable mLastDrawable
    private Boolean mMutated
    private Int mAlpha = 255
    private Int mCurIndex = -1
    private Int mLastIndex = -1

    class BlockInvalidateCallback implements Drawable.Callback {
        private Drawable.Callback mCallback

        BlockInvalidateCallback() {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        fun invalidateDrawable(@NonNull Drawable drawable) {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        fun scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, Long j) {
            if (this.mCallback != null) {
                this.mCallback.scheduleDrawable(drawable, runnable, j)
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        fun unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
            if (this.mCallback != null) {
                this.mCallback.unscheduleDrawable(drawable, runnable)
            }
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback
            this.mCallback = null
            return callback
        }

        fun wrap(Drawable.Callback callback) {
            this.mCallback = callback
            return this
        }
    }

    abstract class DrawableContainerState extends Drawable.ConstantState {
        Boolean mAutoMirrored
        Boolean mCanConstantState
        Int mChangingConfigurations
        Boolean mCheckedConstantSize
        Boolean mCheckedConstantState
        Boolean mCheckedOpacity
        Boolean mCheckedPadding
        Boolean mCheckedStateful
        Int mChildrenChangingConfigurations
        ColorFilter mColorFilter
        Int mConstantHeight
        Int mConstantMinimumHeight
        Int mConstantMinimumWidth
        Rect mConstantPadding
        Boolean mConstantSize
        Int mConstantWidth
        Int mDensity
        Boolean mDither
        SparseArray mDrawableFutures
        Array<Drawable> mDrawables
        Int mEnterFadeDuration
        Int mExitFadeDuration
        Boolean mHasColorFilter
        Boolean mHasTintList
        Boolean mHasTintMode
        Int mLayoutDirection
        Boolean mMutated
        Int mNumChildren
        Int mOpacity
        final DrawableContainer mOwner
        Resources mSourceRes
        Boolean mStateful
        ColorStateList mTintList
        PorterDuff.Mode mTintMode
        Boolean mVariablePadding

        DrawableContainerState(DrawableContainerState drawableContainerState, DrawableContainer drawableContainer, Resources resources) {
            this.mDensity = 160
            this.mVariablePadding = false
            this.mConstantSize = false
            this.mDither = DrawableContainer.DEFAULT_DITHER
            this.mEnterFadeDuration = 0
            this.mExitFadeDuration = 0
            this.mOwner = drawableContainer
            this.mSourceRes = resources != null ? resources : drawableContainerState != null ? drawableContainerState.mSourceRes : null
            this.mDensity = DrawableContainer.resolveDensity(resources, drawableContainerState != null ? drawableContainerState.mDensity : 0)
            if (drawableContainerState == null) {
                this.mDrawables = new Drawable[10]
                this.mNumChildren = 0
                return
            }
            this.mChangingConfigurations = drawableContainerState.mChangingConfigurations
            this.mChildrenChangingConfigurations = drawableContainerState.mChildrenChangingConfigurations
            this.mCheckedConstantState = DrawableContainer.DEFAULT_DITHER
            this.mCanConstantState = DrawableContainer.DEFAULT_DITHER
            this.mVariablePadding = drawableContainerState.mVariablePadding
            this.mConstantSize = drawableContainerState.mConstantSize
            this.mDither = drawableContainerState.mDither
            this.mMutated = drawableContainerState.mMutated
            this.mLayoutDirection = drawableContainerState.mLayoutDirection
            this.mEnterFadeDuration = drawableContainerState.mEnterFadeDuration
            this.mExitFadeDuration = drawableContainerState.mExitFadeDuration
            this.mAutoMirrored = drawableContainerState.mAutoMirrored
            this.mColorFilter = drawableContainerState.mColorFilter
            this.mHasColorFilter = drawableContainerState.mHasColorFilter
            this.mTintList = drawableContainerState.mTintList
            this.mTintMode = drawableContainerState.mTintMode
            this.mHasTintList = drawableContainerState.mHasTintList
            this.mHasTintMode = drawableContainerState.mHasTintMode
            if (drawableContainerState.mDensity == this.mDensity) {
                if (drawableContainerState.mCheckedPadding) {
                    this.mConstantPadding = Rect(drawableContainerState.mConstantPadding)
                    this.mCheckedPadding = DrawableContainer.DEFAULT_DITHER
                }
                if (drawableContainerState.mCheckedConstantSize) {
                    this.mConstantWidth = drawableContainerState.mConstantWidth
                    this.mConstantHeight = drawableContainerState.mConstantHeight
                    this.mConstantMinimumWidth = drawableContainerState.mConstantMinimumWidth
                    this.mConstantMinimumHeight = drawableContainerState.mConstantMinimumHeight
                    this.mCheckedConstantSize = DrawableContainer.DEFAULT_DITHER
                }
            }
            if (drawableContainerState.mCheckedOpacity) {
                this.mOpacity = drawableContainerState.mOpacity
                this.mCheckedOpacity = DrawableContainer.DEFAULT_DITHER
            }
            if (drawableContainerState.mCheckedStateful) {
                this.mStateful = drawableContainerState.mStateful
                this.mCheckedStateful = DrawableContainer.DEFAULT_DITHER
            }
            Array<Drawable> drawableArr = drawableContainerState.mDrawables
            this.mDrawables = new Drawable[drawableArr.length]
            this.mNumChildren = drawableContainerState.mNumChildren
            SparseArray sparseArray = drawableContainerState.mDrawableFutures
            if (sparseArray != null) {
                this.mDrawableFutures = sparseArray.clone()
            } else {
                this.mDrawableFutures = SparseArray(this.mNumChildren)
            }
            Int i = this.mNumChildren
            for (Int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2] != null) {
                    Drawable.ConstantState constantState = drawableArr[i2].getConstantState()
                    if (constantState != null) {
                        this.mDrawableFutures.put(i2, constantState)
                    } else {
                        this.mDrawables[i2] = drawableArr[i2]
                    }
                }
            }
        }

        private fun createAllFutures() {
            if (this.mDrawableFutures != null) {
                Int size = this.mDrawableFutures.size()
                for (Int i = 0; i < size; i++) {
                    this.mDrawables[this.mDrawableFutures.keyAt(i)] = prepareDrawable(((Drawable.ConstantState) this.mDrawableFutures.valueAt(i)).newDrawable(this.mSourceRes))
                }
                this.mDrawableFutures = null
            }
        }

        private fun prepareDrawable(Drawable drawable) {
            if (Build.VERSION.SDK_INT >= 23) {
                drawable.setLayoutDirection(this.mLayoutDirection)
            }
            Drawable drawableMutate = drawable.mutate()
            drawableMutate.setCallback(this.mOwner)
            return drawableMutate
        }

        public final Int addChild(Drawable drawable) {
            Int i = this.mNumChildren
            if (i >= this.mDrawables.length) {
                growArray(i, i + 10)
            }
            drawable.mutate()
            drawable.setVisible(false, DrawableContainer.DEFAULT_DITHER)
            drawable.setCallback(this.mOwner)
            this.mDrawables[i] = drawable
            this.mNumChildren++
            this.mChildrenChangingConfigurations |= drawable.getChangingConfigurations()
            invalidateCache()
            this.mConstantPadding = null
            this.mCheckedPadding = false
            this.mCheckedConstantSize = false
            this.mCheckedConstantState = false
            return i
        }

        @RequiresApi(21)
        final Unit applyTheme(Resources.Theme theme) {
            if (theme != null) {
                createAllFutures()
                Int i = this.mNumChildren
                Array<Drawable> drawableArr = this.mDrawables
                for (Int i2 = 0; i2 < i; i2++) {
                    if (drawableArr[i2] != null && drawableArr[i2].canApplyTheme()) {
                        drawableArr[i2].applyTheme(theme)
                        this.mChildrenChangingConfigurations |= drawableArr[i2].getChangingConfigurations()
                    }
                }
                updateDensity(theme.getResources())
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @RequiresApi(21)
        fun canApplyTheme() {
            Int i = this.mNumChildren
            Array<Drawable> drawableArr = this.mDrawables
            for (Int i2 = 0; i2 < i; i2++) {
                Drawable drawable = drawableArr[i2]
                if (drawable == null) {
                    Drawable.ConstantState constantState = (Drawable.ConstantState) this.mDrawableFutures.get(i2)
                    if (constantState != null && constantState.canApplyTheme()) {
                        return DrawableContainer.DEFAULT_DITHER
                    }
                } else if (drawable.canApplyTheme()) {
                    return DrawableContainer.DEFAULT_DITHER
                }
            }
            return false
        }

        public synchronized Boolean canConstantState() {
            Boolean z = false
            synchronized (this) {
                if (!this.mCheckedConstantState) {
                    createAllFutures()
                    this.mCheckedConstantState = DrawableContainer.DEFAULT_DITHER
                    Int i = this.mNumChildren
                    Array<Drawable> drawableArr = this.mDrawables
                    Int i2 = 0
                    while (true) {
                        if (i2 >= i) {
                            this.mCanConstantState = DrawableContainer.DEFAULT_DITHER
                            z = true
                            break
                        }
                        if (drawableArr[i2].getConstantState() == null) {
                            this.mCanConstantState = false
                            break
                        }
                        i2++
                    }
                } else {
                    z = this.mCanConstantState
                }
            }
            return z
        }

        final Unit clearMutated() {
            this.mMutated = false
        }

        protected fun computeConstantSize() {
            this.mCheckedConstantSize = DrawableContainer.DEFAULT_DITHER
            createAllFutures()
            Int i = this.mNumChildren
            Array<Drawable> drawableArr = this.mDrawables
            this.mConstantHeight = -1
            this.mConstantWidth = -1
            this.mConstantMinimumHeight = 0
            this.mConstantMinimumWidth = 0
            for (Int i2 = 0; i2 < i; i2++) {
                Drawable drawable = drawableArr[i2]
                Int intrinsicWidth = drawable.getIntrinsicWidth()
                if (intrinsicWidth > this.mConstantWidth) {
                    this.mConstantWidth = intrinsicWidth
                }
                Int intrinsicHeight = drawable.getIntrinsicHeight()
                if (intrinsicHeight > this.mConstantHeight) {
                    this.mConstantHeight = intrinsicHeight
                }
                Int minimumWidth = drawable.getMinimumWidth()
                if (minimumWidth > this.mConstantMinimumWidth) {
                    this.mConstantMinimumWidth = minimumWidth
                }
                Int minimumHeight = drawable.getMinimumHeight()
                if (minimumHeight > this.mConstantMinimumHeight) {
                    this.mConstantMinimumHeight = minimumHeight
                }
            }
        }

        final Int getCapacity() {
            return this.mDrawables.length
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        fun getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations
        }

        public final Drawable getChild(Int i) {
            Int iIndexOfKey
            Drawable drawable = this.mDrawables[i]
            if (drawable != null) {
                return drawable
            }
            if (this.mDrawableFutures == null || (iIndexOfKey = this.mDrawableFutures.indexOfKey(i)) < 0) {
                return null
            }
            Drawable drawablePrepareDrawable = prepareDrawable(((Drawable.ConstantState) this.mDrawableFutures.valueAt(iIndexOfKey)).newDrawable(this.mSourceRes))
            this.mDrawables[i] = drawablePrepareDrawable
            this.mDrawableFutures.removeAt(iIndexOfKey)
            if (this.mDrawableFutures.size() != 0) {
                return drawablePrepareDrawable
            }
            this.mDrawableFutures = null
            return drawablePrepareDrawable
        }

        public final Int getChildCount() {
            return this.mNumChildren
        }

        public final Int getConstantHeight() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize()
            }
            return this.mConstantHeight
        }

        public final Int getConstantMinimumHeight() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize()
            }
            return this.mConstantMinimumHeight
        }

        public final Int getConstantMinimumWidth() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize()
            }
            return this.mConstantMinimumWidth
        }

        public final Rect getConstantPadding() {
            Rect rect = null
            if (this.mVariablePadding) {
                return null
            }
            if (this.mConstantPadding != null || this.mCheckedPadding) {
                return this.mConstantPadding
            }
            createAllFutures()
            Rect rect2 = Rect()
            Int i = this.mNumChildren
            Array<Drawable> drawableArr = this.mDrawables
            for (Int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2].getPadding(rect2)) {
                    if (rect == null) {
                        rect = Rect(0, 0, 0, 0)
                    }
                    if (rect2.left > rect.left) {
                        rect.left = rect2.left
                    }
                    if (rect2.top > rect.top) {
                        rect.top = rect2.top
                    }
                    if (rect2.right > rect.right) {
                        rect.right = rect2.right
                    }
                    if (rect2.bottom > rect.bottom) {
                        rect.bottom = rect2.bottom
                    }
                }
            }
            this.mCheckedPadding = DrawableContainer.DEFAULT_DITHER
            this.mConstantPadding = rect
            return rect
        }

        public final Int getConstantWidth() {
            if (!this.mCheckedConstantSize) {
                computeConstantSize()
            }
            return this.mConstantWidth
        }

        public final Int getEnterFadeDuration() {
            return this.mEnterFadeDuration
        }

        public final Int getExitFadeDuration() {
            return this.mExitFadeDuration
        }

        public final Int getOpacity() {
            if (this.mCheckedOpacity) {
                return this.mOpacity
            }
            createAllFutures()
            Int i = this.mNumChildren
            Array<Drawable> drawableArr = this.mDrawables
            Int opacity = i > 0 ? drawableArr[0].getOpacity() : -2
            Int i2 = 1
            while (i2 < i) {
                Int iResolveOpacity = Drawable.resolveOpacity(opacity, drawableArr[i2].getOpacity())
                i2++
                opacity = iResolveOpacity
            }
            this.mOpacity = opacity
            this.mCheckedOpacity = DrawableContainer.DEFAULT_DITHER
            return opacity
        }

        fun growArray(Int i, Int i2) {
            Array<Drawable> drawableArr = new Drawable[i2]
            System.arraycopy(this.mDrawables, 0, drawableArr, 0, i)
            this.mDrawables = drawableArr
        }

        Unit invalidateCache() {
            this.mCheckedOpacity = false
            this.mCheckedStateful = false
        }

        public final Boolean isConstantSize() {
            return this.mConstantSize
        }

        public final Boolean isStateful() {
            Boolean z = false
            if (this.mCheckedStateful) {
                return this.mStateful
            }
            createAllFutures()
            Int i = this.mNumChildren
            Array<Drawable> drawableArr = this.mDrawables
            Int i2 = 0
            while (true) {
                if (i2 >= i) {
                    break
                }
                if (drawableArr[i2].isStateful()) {
                    z = true
                    break
                }
                i2++
            }
            this.mStateful = z
            this.mCheckedStateful = DrawableContainer.DEFAULT_DITHER
            return z
        }

        Unit mutate() {
            Int i = this.mNumChildren
            Array<Drawable> drawableArr = this.mDrawables
            for (Int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2] != null) {
                    drawableArr[i2].mutate()
                }
            }
            this.mMutated = DrawableContainer.DEFAULT_DITHER
        }

        public final Unit setConstantSize(Boolean z) {
            this.mConstantSize = z
        }

        public final Unit setEnterFadeDuration(Int i) {
            this.mEnterFadeDuration = i
        }

        public final Unit setExitFadeDuration(Int i) {
            this.mExitFadeDuration = i
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final Boolean setLayoutDirection(Int r8, Int r9) {
            /*
                r7 = this
                r2 = 0
                Int r4 = r7.mNumChildren
                android.graphics.drawable.Array<Drawable> r5 = r7.mDrawables
                r3 = r2
                r1 = r2
            L7:
                if (r3 >= r4) goto L20
                r0 = r5[r3]
                if (r0 == 0) goto L23
                Int r0 = android.os.Build.VERSION.SDK_INT
                r6 = 23
                if (r0 < r6) goto L25
                r0 = r5[r3]
                Boolean r0 = r0.setLayoutDirection(r8)
            L19:
                if (r3 != r9) goto L23
            L1b:
                Int r1 = r3 + 1
                r3 = r1
                r1 = r0
                goto L7
            L20:
                r7.mLayoutDirection = r8
                return r1
            L23:
                r0 = r1
                goto L1b
            L25:
                r0 = r2
                goto L19
            */
            throw UnsupportedOperationException("Method not decompiled: android.support.v7.graphics.drawable.DrawableContainer.DrawableContainerState.setLayoutDirection(Int, Int):Boolean")
        }

        public final Unit setVariablePadding(Boolean z) {
            this.mVariablePadding = z
        }

        final Unit updateDensity(Resources resources) {
            if (resources != null) {
                this.mSourceRes = resources
                Int iResolveDensity = DrawableContainer.resolveDensity(resources, this.mDensity)
                Int i = this.mDensity
                this.mDensity = iResolveDensity
                if (i != iResolveDensity) {
                    this.mCheckedConstantSize = false
                    this.mCheckedPadding = false
                }
            }
        }
    }

    DrawableContainer() {
    }

    private fun initializeDrawableForDisplay(Drawable drawable) {
        if (this.mBlockInvalidateCallback == null) {
            this.mBlockInvalidateCallback = BlockInvalidateCallback()
        }
        drawable.setCallback(this.mBlockInvalidateCallback.wrap(drawable.getCallback()))
        try {
            if (this.mDrawableContainerState.mEnterFadeDuration <= 0 && this.mHasAlpha) {
                drawable.setAlpha(this.mAlpha)
            }
            if (this.mDrawableContainerState.mHasColorFilter) {
                drawable.setColorFilter(this.mDrawableContainerState.mColorFilter)
            } else {
                if (this.mDrawableContainerState.mHasTintList) {
                    DrawableCompat.setTintList(drawable, this.mDrawableContainerState.mTintList)
                }
                if (this.mDrawableContainerState.mHasTintMode) {
                    DrawableCompat.setTintMode(drawable, this.mDrawableContainerState.mTintMode)
                }
            }
            drawable.setVisible(isVisible(), DEFAULT_DITHER)
            drawable.setDither(this.mDrawableContainerState.mDither)
            drawable.setState(getState())
            drawable.setLevel(getLevel())
            drawable.setBounds(getBounds())
            if (Build.VERSION.SDK_INT >= 23) {
                drawable.setLayoutDirection(getLayoutDirection())
            }
            if (Build.VERSION.SDK_INT >= 19) {
                drawable.setAutoMirrored(this.mDrawableContainerState.mAutoMirrored)
            }
            Rect rect = this.mHotspotBounds
            if (Build.VERSION.SDK_INT >= 21 && rect != null) {
                drawable.setHotspotBounds(rect.left, rect.top, rect.right, rect.bottom)
            }
        } finally {
            drawable.setCallback(this.mBlockInvalidateCallback.unwrap())
        }
    }

    @SuppressLint({"WrongConstant"})
    @TargetApi(23)
    private fun needsMirroring() {
        if (isAutoMirrored() && getLayoutDirection() == 1) {
            return DEFAULT_DITHER
        }
        return false
    }

    static Int resolveDensity(@Nullable Resources resources, Int i) {
        Int i2 = resources == null ? i : resources.getDisplayMetrics().densityDpi
        if (i2 == 0) {
            return 160
        }
        return i2
    }

    Unit animate(Boolean z) {
        Boolean z2
        this.mHasAlpha = DEFAULT_DITHER
        Long jUptimeMillis = SystemClock.uptimeMillis()
        if (this.mCurrDrawable == null) {
            this.mEnterAnimationEnd = 0L
            z2 = false
        } else if (this.mEnterAnimationEnd == 0) {
            z2 = false
        } else if (this.mEnterAnimationEnd <= jUptimeMillis) {
            this.mCurrDrawable.setAlpha(this.mAlpha)
            this.mEnterAnimationEnd = 0L
            z2 = false
        } else {
            this.mCurrDrawable.setAlpha(((255 - (((Int) ((this.mEnterAnimationEnd - jUptimeMillis) * 255)) / this.mDrawableContainerState.mEnterFadeDuration)) * this.mAlpha) / 255)
            z2 = true
        }
        if (this.mLastDrawable == null) {
            this.mExitAnimationEnd = 0L
        } else if (this.mExitAnimationEnd != 0) {
            if (this.mExitAnimationEnd <= jUptimeMillis) {
                this.mLastDrawable.setVisible(false, false)
                this.mLastDrawable = null
                this.mLastIndex = -1
                this.mExitAnimationEnd = 0L
            } else {
                this.mLastDrawable.setAlpha(((((Int) ((this.mExitAnimationEnd - jUptimeMillis) * 255)) / this.mDrawableContainerState.mExitFadeDuration) * this.mAlpha) / 255)
                z2 = true
            }
        }
        if (z && z2) {
            scheduleSelf(this.mAnimationRunnable, 16 + jUptimeMillis)
        }
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    fun applyTheme(@NonNull Resources.Theme theme) {
        this.mDrawableContainerState.applyTheme(theme)
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    fun canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme()
    }

    Unit clearMutated() {
        this.mDrawableContainerState.clearMutated()
        this.mMutated = false
    }

    DrawableContainerState cloneConstantState() {
        return this.mDrawableContainerState
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(@NonNull Canvas canvas) {
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.draw(canvas)
        }
        if (this.mLastDrawable != null) {
            this.mLastDrawable.draw(canvas)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun getAlpha() {
        return this.mAlpha
    }

    @Override // android.graphics.drawable.Drawable
    fun getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations()
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (!this.mDrawableContainerState.canConstantState()) {
            return null
        }
        this.mDrawableContainerState.mChangingConfigurations = getChangingConfigurations()
        return this.mDrawableContainerState
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    fun getCurrent() {
        return this.mCurrDrawable
    }

    Int getCurrentIndex() {
        return this.mCurIndex
    }

    @Override // android.graphics.drawable.Drawable
    fun getHotspotBounds(@NonNull Rect rect) {
        if (this.mHotspotBounds != null) {
            rect.set(this.mHotspotBounds)
        } else {
            super.getHotspotBounds(rect)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight()
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getIntrinsicHeight()
        }
        return -1
    }

    @Override // android.graphics.drawable.Drawable
    fun getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth()
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getIntrinsicWidth()
        }
        return -1
    }

    @Override // android.graphics.drawable.Drawable
    fun getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight()
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getMinimumHeight()
        }
        return 0
    }

    @Override // android.graphics.drawable.Drawable
    fun getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth()
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getMinimumWidth()
        }
        return 0
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        if (this.mCurrDrawable == null || !this.mCurrDrawable.isVisible()) {
            return -2
        }
        return this.mDrawableContainerState.getOpacity()
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    fun getOutline(@NonNull Outline outline) {
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.getOutline(outline)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun getPadding(@NonNull Rect rect) {
        Boolean padding
        Rect constantPadding = this.mDrawableContainerState.getConstantPadding()
        if (constantPadding != null) {
            rect.set(constantPadding)
            padding = (constantPadding.right | ((constantPadding.left | constantPadding.top) | constantPadding.bottom)) != 0 ? DEFAULT_DITHER : false
        } else {
            padding = this.mCurrDrawable != null ? this.mCurrDrawable.getPadding(rect) : super.getPadding(rect)
        }
        if (needsMirroring()) {
            Int i = rect.left
            rect.left = rect.right
            rect.right = i
        }
        return padding
    }

    fun invalidateDrawable(@NonNull Drawable drawable) {
        if (this.mDrawableContainerState != null) {
            this.mDrawableContainerState.invalidateCache()
        }
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return
        }
        getCallback().invalidateDrawable(this)
    }

    @Override // android.graphics.drawable.Drawable
    fun isAutoMirrored() {
        return this.mDrawableContainerState.mAutoMirrored
    }

    @Override // android.graphics.drawable.Drawable
    fun isStateful() {
        return this.mDrawableContainerState.isStateful()
    }

    @Override // android.graphics.drawable.Drawable
    fun jumpToCurrentState() {
        Boolean z = DEFAULT_DITHER
        Boolean z2 = false
        if (this.mLastDrawable != null) {
            this.mLastDrawable.jumpToCurrentState()
            this.mLastDrawable = null
            this.mLastIndex = -1
            z2 = true
        }
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.jumpToCurrentState()
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha)
            }
        }
        if (this.mExitAnimationEnd != 0) {
            this.mExitAnimationEnd = 0L
            z2 = true
        }
        if (this.mEnterAnimationEnd != 0) {
            this.mEnterAnimationEnd = 0L
        } else {
            z = z2
        }
        if (z) {
            invalidateSelf()
        }
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    fun mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableContainerState drawableContainerStateCloneConstantState = cloneConstantState()
            drawableContainerStateCloneConstantState.mutate()
            setConstantState(drawableContainerStateCloneConstantState)
            this.mMutated = DEFAULT_DITHER
        }
        return this
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onBoundsChange(Rect rect) {
        if (this.mLastDrawable != null) {
            this.mLastDrawable.setBounds(rect)
        }
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.setBounds(rect)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun onLayoutDirectionChanged(Int i) {
        return this.mDrawableContainerState.setLayoutDirection(i, getCurrentIndex())
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onLevelChange(Int i) {
        if (this.mLastDrawable != null) {
            return this.mLastDrawable.setLevel(i)
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.setLevel(i)
        }
        return false
    }

    @Override // android.graphics.drawable.Drawable
    protected fun onStateChange(Array<Int> iArr) {
        if (this.mLastDrawable != null) {
            return this.mLastDrawable.setState(iArr)
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.setState(iArr)
        }
        return false
    }

    fun scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, Long j) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return
        }
        getCallback().scheduleDrawable(this, runnable, j)
    }

    Boolean selectDrawable(Int i) {
        if (i == this.mCurIndex) {
            return false
        }
        Long jUptimeMillis = SystemClock.uptimeMillis()
        if (this.mDrawableContainerState.mExitFadeDuration > 0) {
            if (this.mLastDrawable != null) {
                this.mLastDrawable.setVisible(false, false)
            }
            if (this.mCurrDrawable != null) {
                this.mLastDrawable = this.mCurrDrawable
                this.mLastIndex = this.mCurIndex
                this.mExitAnimationEnd = this.mDrawableContainerState.mExitFadeDuration + jUptimeMillis
            } else {
                this.mLastDrawable = null
                this.mLastIndex = -1
                this.mExitAnimationEnd = 0L
            }
        } else if (this.mCurrDrawable != null) {
            this.mCurrDrawable.setVisible(false, false)
        }
        if (i < 0 || i >= this.mDrawableContainerState.mNumChildren) {
            this.mCurrDrawable = null
            this.mCurIndex = -1
        } else {
            Drawable child = this.mDrawableContainerState.getChild(i)
            this.mCurrDrawable = child
            this.mCurIndex = i
            if (child != null) {
                if (this.mDrawableContainerState.mEnterFadeDuration > 0) {
                    this.mEnterAnimationEnd = jUptimeMillis + this.mDrawableContainerState.mEnterFadeDuration
                }
                initializeDrawableForDisplay(child)
            }
        }
        if (this.mEnterAnimationEnd != 0 || this.mExitAnimationEnd != 0) {
            if (this.mAnimationRunnable == null) {
                this.mAnimationRunnable = Runnable() { // from class: android.support.v7.graphics.drawable.DrawableContainer.1
                    @Override // java.lang.Runnable
                    fun run() {
                        DrawableContainer.this.animate(DrawableContainer.DEFAULT_DITHER)
                        DrawableContainer.this.invalidateSelf()
                    }
                }
            } else {
                unscheduleSelf(this.mAnimationRunnable)
            }
            animate(DEFAULT_DITHER)
        }
        invalidateSelf()
        return DEFAULT_DITHER
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
        if (this.mHasAlpha && this.mAlpha == i) {
            return
        }
        this.mHasAlpha = DEFAULT_DITHER
        this.mAlpha = i
        if (this.mCurrDrawable != null) {
            if (this.mEnterAnimationEnd == 0) {
                this.mCurrDrawable.setAlpha(i)
            } else {
                animate(false)
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setAutoMirrored(Boolean z) {
        if (this.mDrawableContainerState.mAutoMirrored != z) {
            this.mDrawableContainerState.mAutoMirrored = z
            if (this.mCurrDrawable != null) {
                DrawableCompat.setAutoMirrored(this.mCurrDrawable, this.mDrawableContainerState.mAutoMirrored)
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
        this.mDrawableContainerState.mHasColorFilter = DEFAULT_DITHER
        if (this.mDrawableContainerState.mColorFilter != colorFilter) {
            this.mDrawableContainerState.mColorFilter = colorFilter
            if (this.mCurrDrawable != null) {
                this.mCurrDrawable.setColorFilter(colorFilter)
            }
        }
    }

    protected fun setConstantState(DrawableContainerState drawableContainerState) {
        this.mDrawableContainerState = drawableContainerState
        if (this.mCurIndex >= 0) {
            this.mCurrDrawable = drawableContainerState.getChild(this.mCurIndex)
            if (this.mCurrDrawable != null) {
                initializeDrawableForDisplay(this.mCurrDrawable)
            }
        }
        this.mLastIndex = -1
        this.mLastDrawable = null
    }

    Unit setCurrentIndex(Int i) {
        selectDrawable(i)
    }

    @Override // android.graphics.drawable.Drawable
    fun setDither(Boolean z) {
        if (this.mDrawableContainerState.mDither != z) {
            this.mDrawableContainerState.mDither = z
            if (this.mCurrDrawable != null) {
                this.mCurrDrawable.setDither(this.mDrawableContainerState.mDither)
            }
        }
    }

    fun setEnterFadeDuration(Int i) {
        this.mDrawableContainerState.mEnterFadeDuration = i
    }

    fun setExitFadeDuration(Int i) {
        this.mDrawableContainerState.mExitFadeDuration = i
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspot(Float f, Float f2) {
        if (this.mCurrDrawable != null) {
            DrawableCompat.setHotspot(this.mCurrDrawable, f, f2)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
        if (this.mHotspotBounds == null) {
            this.mHotspotBounds = Rect(i, i2, i3, i4)
        } else {
            this.mHotspotBounds.set(i, i2, i3, i4)
        }
        if (this.mCurrDrawable != null) {
            DrawableCompat.setHotspotBounds(this.mCurrDrawable, i, i2, i3, i4)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setTintList(ColorStateList colorStateList) {
        this.mDrawableContainerState.mHasTintList = DEFAULT_DITHER
        if (this.mDrawableContainerState.mTintList != colorStateList) {
            this.mDrawableContainerState.mTintList = colorStateList
            DrawableCompat.setTintList(this.mCurrDrawable, colorStateList)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setTintMode(@NonNull PorterDuff.Mode mode) {
        this.mDrawableContainerState.mHasTintMode = DEFAULT_DITHER
        if (this.mDrawableContainerState.mTintMode != mode) {
            this.mDrawableContainerState.mTintMode = mode
            DrawableCompat.setTintMode(this.mCurrDrawable, mode)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setVisible(Boolean z, Boolean z2) {
        Boolean visible = super.setVisible(z, z2)
        if (this.mLastDrawable != null) {
            this.mLastDrawable.setVisible(z, z2)
        }
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.setVisible(z, z2)
        }
        return visible
    }

    fun unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return
        }
        getCallback().unscheduleDrawable(this, runnable)
    }

    final Unit updateDensity(Resources resources) {
        this.mDrawableContainerState.updateDensity(resources)
    }
}
