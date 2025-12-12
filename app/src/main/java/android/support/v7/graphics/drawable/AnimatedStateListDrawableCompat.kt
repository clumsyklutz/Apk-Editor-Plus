package android.support.v7.graphics.drawable

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.res.TypedArrayUtils
import android.support.v4.util.LongSparseArray
import androidx.collection.SparseArrayCompat
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.graphics.drawable.DrawableContainer
import android.support.v7.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.StateSet
import android.util.Xml
import java.io.IOException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

class AnimatedStateListDrawableCompat extends StateListDrawable {
    private static val ELEMENT_ITEM = "item"
    private static val ELEMENT_TRANSITION = "transition"
    private static val ITEM_MISSING_DRAWABLE_ERROR = ": <item> tag requires a 'drawable' attribute or child tag defining a drawable"
    private static val LOGTAG = AnimatedStateListDrawableCompat.class.getSimpleName()
    private static val TRANSITION_MISSING_DRAWABLE_ERROR = ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable"
    private static val TRANSITION_MISSING_FROM_TO_ID = ": <transition> tag requires 'fromId' & 'toId' attributes"
    private Boolean mMutated
    private AnimatedStateListState mState
    private Transition mTransition
    private Int mTransitionFromIndex
    private Int mTransitionToIndex

    class AnimatableTransition extends Transition {
        private final Animatable mA

        AnimatableTransition(Animatable animatable) {
            super()
            this.mA = animatable
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun start() {
            this.mA.start()
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun stop() {
            this.mA.stop()
        }
    }

    class AnimatedStateListState extends StateListDrawable.StateListState {
        private static val REVERSED_BIT = 4294967296L
        private static val REVERSIBLE_FLAG_BIT = 8589934592L
        SparseArrayCompat mStateIds
        LongSparseArray mTransitions

        AnimatedStateListState(@Nullable AnimatedStateListState animatedStateListState, @NonNull AnimatedStateListDrawableCompat animatedStateListDrawableCompat, @Nullable Resources resources) {
            super(animatedStateListState, animatedStateListDrawableCompat, resources)
            if (animatedStateListState != null) {
                this.mTransitions = animatedStateListState.mTransitions
                this.mStateIds = animatedStateListState.mStateIds
            } else {
                this.mTransitions = LongSparseArray()
                this.mStateIds = SparseArrayCompat()
            }
        }

        private fun generateTransitionKey(Int i, Int i2) {
            return (i << 32) | i2
        }

        Int addStateSet(@NonNull Array<Int> iArr, @NonNull Drawable drawable, Int i) {
            Int iAddStateSet = super.addStateSet(iArr, drawable)
            this.mStateIds.put(iAddStateSet, Integer.valueOf(i))
            return iAddStateSet
        }

        Int addTransition(Int i, Int i2, @NonNull Drawable drawable, Boolean z) {
            Int iAddChild = super.addChild(drawable)
            Long jGenerateTransitionKey = generateTransitionKey(i, i2)
            Long j = z ? REVERSIBLE_FLAG_BIT : 0L
            this.mTransitions.append(jGenerateTransitionKey, Long.valueOf(iAddChild | j))
            if (z) {
                this.mTransitions.append(generateTransitionKey(i2, i), Long.valueOf(j | iAddChild | REVERSED_BIT))
            }
            return iAddChild
        }

        Int getKeyframeIdAt(Int i) {
            if (i < 0) {
                return 0
            }
            return ((Integer) this.mStateIds.get(i, 0)).intValue()
        }

        Int indexOfKeyframe(@NonNull Array<Int> iArr) {
            Int iIndexOfStateSet = super.indexOfStateSet(iArr)
            return iIndexOfStateSet >= 0 ? iIndexOfStateSet : super.indexOfStateSet(StateSet.WILD_CARD)
        }

        Int indexOfTransition(Int i, Int i2) {
            return (Int) ((Long) this.mTransitions.get(generateTransitionKey(i, i2), -1L)).longValue()
        }

        Boolean isTransitionReversed(Int i, Int i2) {
            return (((Long) this.mTransitions.get(generateTransitionKey(i, i2), -1L)).longValue() & REVERSED_BIT) != 0
        }

        @Override // android.support.v7.graphics.drawable.StateListDrawable.StateListState, android.support.v7.graphics.drawable.DrawableContainer.DrawableContainerState
        Unit mutate() {
            this.mTransitions = this.mTransitions.m2clone()
            this.mStateIds = this.mStateIds.m3clone()
        }

        @Override // android.support.v7.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable() {
            return AnimatedStateListDrawableCompat(this, null)
        }

        @Override // android.support.v7.graphics.drawable.StateListDrawable.StateListState, android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable(Resources resources) {
            return AnimatedStateListDrawableCompat(this, resources)
        }

        Boolean transitionHasReversibleFlag(Int i, Int i2) {
            return (((Long) this.mTransitions.get(generateTransitionKey(i, i2), -1L)).longValue() & REVERSIBLE_FLAG_BIT) != 0
        }
    }

    class AnimatedVectorDrawableTransition extends Transition {
        private final AnimatedVectorDrawableCompat mAvd

        AnimatedVectorDrawableTransition(AnimatedVectorDrawableCompat animatedVectorDrawableCompat) {
            super()
            this.mAvd = animatedVectorDrawableCompat
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun start() {
            this.mAvd.start()
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun stop() {
            this.mAvd.stop()
        }
    }

    class AnimationDrawableTransition extends Transition {
        private final ObjectAnimator mAnim
        private final Boolean mHasReversibleFlag

        AnimationDrawableTransition(AnimationDrawable animationDrawable, Boolean z, Boolean z2) {
            super()
            Int numberOfFrames = animationDrawable.getNumberOfFrames()
            Int i = z ? numberOfFrames - 1 : 0
            Int i2 = z ? 0 : numberOfFrames - 1
            FrameInterpolator frameInterpolator = FrameInterpolator(animationDrawable, z)
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", i, i2)
            if (Build.VERSION.SDK_INT >= 18) {
                objectAnimatorOfInt.setAutoCancel(true)
            }
            objectAnimatorOfInt.setDuration(frameInterpolator.getTotalDuration())
            objectAnimatorOfInt.setInterpolator(frameInterpolator)
            this.mHasReversibleFlag = z2
            this.mAnim = objectAnimatorOfInt
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun canReverse() {
            return this.mHasReversibleFlag
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun reverse() {
            this.mAnim.reverse()
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun start() {
            this.mAnim.start()
        }

        @Override // android.support.v7.graphics.drawable.AnimatedStateListDrawableCompat.Transition
        fun stop() {
            this.mAnim.cancel()
        }
    }

    class FrameInterpolator implements TimeInterpolator {
        private Array<Int> mFrameTimes
        private Int mFrames
        private Int mTotalDuration

        FrameInterpolator(AnimationDrawable animationDrawable, Boolean z) {
            updateFrames(animationDrawable, z)
        }

        @Override // android.animation.TimeInterpolator
        fun getInterpolation(Float f) {
            Int i = this.mFrames
            Array<Int> iArr = this.mFrameTimes
            Int i2 = (Int) ((this.mTotalDuration * f) + 0.5f)
            Int i3 = 0
            while (i3 < i && i2 >= iArr[i3]) {
                Int i4 = i2 - iArr[i3]
                i3++
                i2 = i4
            }
            return (i3 < i ? i2 / this.mTotalDuration : 0.0f) + (i3 / i)
        }

        Int getTotalDuration() {
            return this.mTotalDuration
        }

        Int updateFrames(AnimationDrawable animationDrawable, Boolean z) {
            Int i = 0
            Int numberOfFrames = animationDrawable.getNumberOfFrames()
            this.mFrames = numberOfFrames
            if (this.mFrameTimes == null || this.mFrameTimes.length < numberOfFrames) {
                this.mFrameTimes = new Int[numberOfFrames]
            }
            Array<Int> iArr = this.mFrameTimes
            Int i2 = 0
            while (i < numberOfFrames) {
                Int duration = animationDrawable.getDuration(z ? (numberOfFrames - i) - 1 : i)
                iArr[i] = duration
                i++
                i2 = duration + i2
            }
            this.mTotalDuration = i2
            return i2
        }
    }

    abstract class Transition {
        private constructor() {
        }

        fun canReverse() {
            return false
        }

        fun reverse() {
        }

        public abstract Unit start()

        public abstract Unit stop()
    }

    constructor() {
        this(null, null)
    }

    AnimatedStateListDrawableCompat(@Nullable AnimatedStateListState animatedStateListState, @Nullable Resources resources) {
        super(null)
        this.mTransitionToIndex = -1
        this.mTransitionFromIndex = -1
        setConstantState(AnimatedStateListState(animatedStateListState, this, resources))
        onStateChange(getState())
        jumpToCurrentState()
    }

    @Nullable
    fun create(@NonNull Context context, @DrawableRes Int i, @Nullable Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Int next
        try {
            Resources resources = context.getResources()
            XmlResourceParser xml = resources.getXml(i)
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml)
            do {
                next = xml.next()
                if (next == 2) {
                    break
                }
            } while (next != 1);
            if (next != 2) {
                throw XmlPullParserException("No start tag found")
            }
            return createFromXmlInner(context, resources, xml, attributeSetAsAttributeSet, theme)
        } catch (IOException e) {
            Log.e(LOGTAG, "parser error", e)
            return null
        } catch (XmlPullParserException e2) {
            Log.e(LOGTAG, "parser error", e2)
            return null
        }
    }

    fun createFromXmlInner(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = xmlPullParser.getName()
        if (!name.equals("animated-selector")) {
            throw XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + name)
        }
        AnimatedStateListDrawableCompat animatedStateListDrawableCompat = AnimatedStateListDrawableCompat()
        animatedStateListDrawableCompat.inflate(context, resources, xmlPullParser, attributeSet, theme)
        return animatedStateListDrawableCompat
    }

    private fun inflateChildElements(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        Int depth = xmlPullParser.getDepth() + 1
        while (true) {
            Int next = xmlPullParser.next()
            if (next == 1) {
                return
            }
            Int depth2 = xmlPullParser.getDepth()
            if (depth2 < depth && next == 3) {
                return
            }
            if (next == 2 && depth2 <= depth) {
                if (xmlPullParser.getName().equals(ELEMENT_ITEM)) {
                    parseItem(context, resources, xmlPullParser, attributeSet, theme)
                } else if (xmlPullParser.getName().equals(ELEMENT_TRANSITION)) {
                    parseTransition(context, resources, xmlPullParser, attributeSet, theme)
                }
            }
        }
    }

    private fun init() {
        onStateChange(getState())
    }

    private fun parseItem(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        Int next
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableItem)
        Int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_id, 0)
        Int resourceId2 = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableItem_android_drawable, -1)
        Drawable drawable = resourceId2 > 0 ? AppCompatResources.getDrawable(context, resourceId2) : null
        typedArrayObtainAttributes.recycle()
        Array<Int> iArrExtractStateSet = extractStateSet(attributeSet)
        if (drawable == null) {
            do {
                next = xmlPullParser.next()
            } while (next == 4);
            if (next != 2) {
                throw XmlPullParserException(xmlPullParser.getPositionDescription() + ITEM_MISSING_DRAWABLE_ERROR)
            }
            drawable = xmlPullParser.getName().equals("vector") ? VectorDrawableCompat.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet)
        }
        if (drawable == null) {
            throw XmlPullParserException(xmlPullParser.getPositionDescription() + ITEM_MISSING_DRAWABLE_ERROR)
        }
        return this.mState.addStateSet(iArrExtractStateSet, drawable, resourceId)
    }

    private fun parseTransition(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        Int next
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableTransition)
        Int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_fromId, -1)
        Int resourceId2 = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_toId, -1)
        Int resourceId3 = typedArrayObtainAttributes.getResourceId(R.styleable.AnimatedStateListDrawableTransition_android_drawable, -1)
        Drawable drawable = resourceId3 > 0 ? AppCompatResources.getDrawable(context, resourceId3) : null
        Boolean z = typedArrayObtainAttributes.getBoolean(R.styleable.AnimatedStateListDrawableTransition_android_reversible, false)
        typedArrayObtainAttributes.recycle()
        if (drawable == null) {
            do {
                next = xmlPullParser.next()
            } while (next == 4);
            if (next != 2) {
                throw XmlPullParserException(xmlPullParser.getPositionDescription() + TRANSITION_MISSING_DRAWABLE_ERROR)
            }
            drawable = xmlPullParser.getName().equals("animated-vector") ? AnimatedVectorDrawableCompat.createFromXmlInner(context, resources, xmlPullParser, attributeSet, theme) : Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet)
        }
        if (drawable == null) {
            throw XmlPullParserException(xmlPullParser.getPositionDescription() + TRANSITION_MISSING_DRAWABLE_ERROR)
        }
        if (resourceId == -1 || resourceId2 == -1) {
            throw XmlPullParserException(xmlPullParser.getPositionDescription() + TRANSITION_MISSING_FROM_TO_ID)
        }
        return this.mState.addTransition(resourceId, resourceId2, drawable, z)
    }

    private fun selectTransition(Int i) {
        Int currentIndex
        Transition animatableTransition
        Transition transition = this.mTransition
        if (transition == null) {
            currentIndex = getCurrentIndex()
        } else {
            if (i == this.mTransitionToIndex) {
                return true
            }
            if (i == this.mTransitionFromIndex && transition.canReverse()) {
                transition.reverse()
                this.mTransitionToIndex = this.mTransitionFromIndex
                this.mTransitionFromIndex = i
                return true
            }
            Int i2 = this.mTransitionToIndex
            transition.stop()
            currentIndex = i2
        }
        this.mTransition = null
        this.mTransitionFromIndex = -1
        this.mTransitionToIndex = -1
        AnimatedStateListState animatedStateListState = this.mState
        Int keyframeIdAt = animatedStateListState.getKeyframeIdAt(currentIndex)
        Int keyframeIdAt2 = animatedStateListState.getKeyframeIdAt(i)
        if (keyframeIdAt2 == 0 || keyframeIdAt == 0) {
            return false
        }
        Int iIndexOfTransition = animatedStateListState.indexOfTransition(keyframeIdAt, keyframeIdAt2)
        if (iIndexOfTransition < 0) {
            return false
        }
        Boolean zTransitionHasReversibleFlag = animatedStateListState.transitionHasReversibleFlag(keyframeIdAt, keyframeIdAt2)
        selectDrawable(iIndexOfTransition)
        Object current = getCurrent()
        if (current is AnimationDrawable) {
            animatableTransition = AnimationDrawableTransition((AnimationDrawable) current, animatedStateListState.isTransitionReversed(keyframeIdAt, keyframeIdAt2), zTransitionHasReversibleFlag)
        } else if (current is AnimatedVectorDrawableCompat) {
            animatableTransition = AnimatedVectorDrawableTransition((AnimatedVectorDrawableCompat) current)
        } else {
            if (!(current is Animatable)) {
                return false
            }
            animatableTransition = AnimatableTransition((Animatable) current)
        }
        animatableTransition.start()
        this.mTransition = animatableTransition
        this.mTransitionFromIndex = currentIndex
        this.mTransitionToIndex = i
        return true
    }

    private fun updateStateFromTypedArray(TypedArray typedArray) {
        AnimatedStateListState animatedStateListState = this.mState
        if (Build.VERSION.SDK_INT >= 21) {
            animatedStateListState.mChangingConfigurations |= typedArray.getChangingConfigurations()
        }
        animatedStateListState.setVariablePadding(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_variablePadding, animatedStateListState.mVariablePadding))
        animatedStateListState.setConstantSize(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_constantSize, animatedStateListState.mConstantSize))
        animatedStateListState.setEnterFadeDuration(typedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, animatedStateListState.mEnterFadeDuration))
        animatedStateListState.setExitFadeDuration(typedArray.getInt(R.styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, animatedStateListState.mExitFadeDuration))
        setDither(typedArray.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_dither, animatedStateListState.mDither))
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable
    public /* bridge */ /* synthetic */ Unit addState(Array<Int> iArr, Drawable drawable) {
        super.addState(iArr, drawable)
    }

    fun addState(@NonNull Array<Int> iArr, @NonNull Drawable drawable, Int i) {
        if (drawable == null) {
            throw IllegalArgumentException("Drawable must not be null")
        }
        this.mState.addStateSet(iArr, drawable, i)
        onStateChange(getState())
    }

    fun addTransition(Int i, Int i2, @NonNull Drawable drawable, Boolean z) {
        if (drawable == null) {
            throw IllegalArgumentException("Transition drawable must not be null")
        }
        this.mState.addTransition(i, i2, drawable, z)
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public /* bridge */ /* synthetic */ Unit applyTheme(@NonNull Resources.Theme theme) {
        super.applyTheme(theme)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public /* bridge */ /* synthetic */ Boolean canApplyTheme() {
        return super.canApplyTheme()
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer
    Unit clearMutated() {
        super.clearMutated()
        this.mMutated = false
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer
    fun cloneConstantState() {
        return AnimatedStateListState(this.mState, this, null)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit draw(@NonNull Canvas canvas) {
        super.draw(canvas)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getAlpha() {
        return super.getAlpha()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getChangingConfigurations() {
        return super.getChangingConfigurations()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @NonNull
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit getHotspotBounds(@NonNull Rect rect) {
        super.getHotspotBounds(rect)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getIntrinsicHeight() {
        return super.getIntrinsicHeight()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getIntrinsicWidth() {
        return super.getIntrinsicWidth()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getMinimumHeight() {
        return super.getMinimumHeight()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getMinimumWidth() {
        return super.getMinimumWidth()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Int getOpacity() {
        return super.getOpacity()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    public /* bridge */ /* synthetic */ Unit getOutline(@NonNull Outline outline) {
        super.getOutline(outline)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Boolean getPadding(@NonNull Rect rect) {
        return super.getPadding(rect)
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable
    fun inflate(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedStateListDrawableCompat)
        setVisible(typedArrayObtainAttributes.getBoolean(R.styleable.AnimatedStateListDrawableCompat_android_visible, true), true)
        updateStateFromTypedArray(typedArrayObtainAttributes)
        updateDensity(resources)
        typedArrayObtainAttributes.recycle()
        inflateChildElements(context, resources, xmlPullParser, attributeSet, theme)
        init()
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable.Callback
    public /* bridge */ /* synthetic */ Unit invalidateDrawable(@NonNull Drawable drawable) {
        super.invalidateDrawable(drawable)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Boolean isAutoMirrored() {
        return super.isAutoMirrored()
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    fun isStateful() {
        return true
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    fun jumpToCurrentState() {
        super.jumpToCurrentState()
        if (this.mTransition != null) {
            this.mTransition.stop()
            this.mTransition = null
            selectDrawable(this.mTransitionToIndex)
            this.mTransitionToIndex = -1
            this.mTransitionFromIndex = -1
        }
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    fun mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState.mutate()
            this.mMutated = true
        }
        return this
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Boolean onLayoutDirectionChanged(Int i) {
        return super.onLayoutDirectionChanged(i)
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected fun onStateChange(Array<Int> iArr) {
        Int iIndexOfKeyframe = this.mState.indexOfKeyframe(iArr)
        Boolean z = iIndexOfKeyframe != getCurrentIndex() && (selectTransition(iIndexOfKeyframe) || selectDrawable(iIndexOfKeyframe))
        Drawable current = getCurrent()
        return current != null ? z | current.setState(iArr) : z
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable.Callback
    public /* bridge */ /* synthetic */ Unit scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, Long j) {
        super.scheduleDrawable(drawable, runnable, j)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setAlpha(Int i) {
        super.setAlpha(i)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setAutoMirrored(Boolean z) {
        super.setAutoMirrored(z)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter)
    }

    @Override // android.support.v7.graphics.drawable.StateListDrawable, android.support.v7.graphics.drawable.DrawableContainer
    protected fun setConstantState(@NonNull DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState)
        if (drawableContainerState is AnimatedStateListState) {
            this.mState = (AnimatedStateListState) drawableContainerState
        }
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setDither(Boolean z) {
        super.setDither(z)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer
    public /* bridge */ /* synthetic */ Unit setEnterFadeDuration(Int i) {
        super.setEnterFadeDuration(i)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer
    public /* bridge */ /* synthetic */ Unit setExitFadeDuration(Int i) {
        super.setExitFadeDuration(i)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setHotspot(Float f, Float f2) {
        super.setHotspot(f, f2)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setHotspotBounds(Int i, Int i2, Int i3, Int i4) {
        super.setHotspotBounds(i, i2, i3, i4)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Unit setTintMode(@NonNull PorterDuff.Mode mode) {
        super.setTintMode(mode)
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    fun setVisible(Boolean z, Boolean z2) {
        Boolean visible = super.setVisible(z, z2)
        if (this.mTransition != null && (visible || z2)) {
            if (z) {
                this.mTransition.start()
            } else {
                jumpToCurrentState()
            }
        }
        return visible
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable.Callback
    public /* bridge */ /* synthetic */ Unit unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        super.unscheduleDrawable(drawable, runnable)
    }
}
