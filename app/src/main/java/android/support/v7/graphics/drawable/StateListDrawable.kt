package android.support.v7.graphics.drawable

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.content.res.TypedArrayUtils
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.graphics.drawable.DrawableContainer
import android.util.AttributeSet
import android.util.StateSet
import java.io.IOException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class StateListDrawable extends DrawableContainer {
    private static val DEBUG = false
    private static val TAG = "StateListDrawable"
    private Boolean mMutated
    private StateListState mStateListState

    class StateListState extends DrawableContainer.DrawableContainerState {
        Array<Int>[] mStateSets

        StateListState(StateListState stateListState, StateListDrawable stateListDrawable, Resources resources) {
            super(stateListState, stateListDrawable, resources)
            if (stateListState != null) {
                this.mStateSets = stateListState.mStateSets
            } else {
                this.mStateSets = new Int[getCapacity()][]
            }
        }

        Int addStateSet(Array<Int> iArr, Drawable drawable) {
            Int iAddChild = addChild(drawable)
            this.mStateSets[iAddChild] = iArr
            return iAddChild
        }

        @Override // android.support.v7.graphics.drawable.DrawableContainer.DrawableContainerState
        fun growArray(Int i, Int i2) {
            super.growArray(i, i2)
            Array<Int>[] iArr = new Int[i2][]
            System.arraycopy(this.mStateSets, 0, iArr, 0, i)
            this.mStateSets = iArr
        }

        Int indexOfStateSet(Array<Int> iArr) {
            Array<Int>[] iArr2 = this.mStateSets
            Int childCount = getChildCount()
            for (Int i = 0; i < childCount; i++) {
                if (StateSet.stateSetMatches(iArr2[i], iArr)) {
                    return i
                }
            }
            return -1
        }

        @Override // android.support.v7.graphics.drawable.DrawableContainer.DrawableContainerState
        Unit mutate() {
            Array<Int>[] iArr = new Int[this.mStateSets.length][]
            for (Int length = this.mStateSets.length - 1; length >= 0; length--) {
                iArr[length] = this.mStateSets[length] != null ? (Array<Int>) this.mStateSets[length].clone() : null
            }
            this.mStateSets = iArr
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable() {
            return StateListDrawable(this, null)
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        fun newDrawable(Resources resources) {
            return StateListDrawable(this, resources)
        }
    }

    StateListDrawable() {
        this(null, null)
    }

    StateListDrawable(@Nullable StateListState stateListState) {
        if (stateListState != null) {
            setConstantState(stateListState)
        }
    }

    StateListDrawable(StateListState stateListState, Resources resources) {
        setConstantState(StateListState(stateListState, this, resources))
        onStateChange(getState())
    }

    private fun inflateChildElements(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Int next
        StateListState stateListState = this.mStateListState
        Int depth = xmlPullParser.getDepth() + 1
        while (true) {
            Int next2 = xmlPullParser.next()
            if (next2 == 1) {
                return
            }
            Int depth2 = xmlPullParser.getDepth()
            if (depth2 < depth && next2 == 3) {
                return
            }
            if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals("item")) {
                TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawableItem)
                Int resourceId = typedArrayObtainAttributes.getResourceId(R.styleable.StateListDrawableItem_android_drawable, -1)
                Drawable drawable = resourceId > 0 ? AppCompatResources.getDrawable(context, resourceId) : null
                typedArrayObtainAttributes.recycle()
                Array<Int> iArrExtractStateSet = extractStateSet(attributeSet)
                if (drawable == null) {
                    do {
                        next = xmlPullParser.next()
                    } while (next == 4);
                    if (next != 2) {
                        throw XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable")
                    }
                    drawable = Build.VERSION.SDK_INT >= 21 ? Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme) : Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet)
                }
                stateListState.addStateSet(iArrExtractStateSet, drawable)
            }
        }
    }

    private fun updateStateFromTypedArray(TypedArray typedArray) {
        StateListState stateListState = this.mStateListState
        if (Build.VERSION.SDK_INT >= 21) {
            stateListState.mChangingConfigurations |= typedArray.getChangingConfigurations()
        }
        stateListState.mVariablePadding = typedArray.getBoolean(R.styleable.StateListDrawable_android_variablePadding, stateListState.mVariablePadding)
        stateListState.mConstantSize = typedArray.getBoolean(R.styleable.StateListDrawable_android_constantSize, stateListState.mConstantSize)
        stateListState.mEnterFadeDuration = typedArray.getInt(R.styleable.StateListDrawable_android_enterFadeDuration, stateListState.mEnterFadeDuration)
        stateListState.mExitFadeDuration = typedArray.getInt(R.styleable.StateListDrawable_android_exitFadeDuration, stateListState.mExitFadeDuration)
        stateListState.mDither = typedArray.getBoolean(R.styleable.StateListDrawable_android_dither, stateListState.mDither)
    }

    fun addState(Array<Int> iArr, Drawable drawable) {
        if (drawable != null) {
            this.mStateListState.addStateSet(iArr, drawable)
            onStateChange(getState())
        }
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @RequiresApi(21)
    fun applyTheme(@NonNull Resources.Theme theme) {
        super.applyTheme(theme)
        onStateChange(getState())
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer
    Unit clearMutated() {
        super.clearMutated()
        this.mMutated = false
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.support.v7.graphics.drawable.DrawableContainer
    fun cloneConstantState() {
        return StateListState(this.mStateListState, this, null)
    }

    Array<Int> extractStateSet(AttributeSet attributeSet) {
        Int i
        Int attributeCount = attributeSet.getAttributeCount()
        Array<Int> iArr = new Int[attributeCount]
        Int i2 = 0
        Int i3 = 0
        while (i2 < attributeCount) {
            Int attributeNameResource = attributeSet.getAttributeNameResource(i2)
            switch (attributeNameResource) {
                case 0:
                    i = i3
                    break
                case android.R.attr.id:
                case android.R.attr.drawable:
                    i = i3
                    break
                default:
                    Int i4 = i3 + 1
                    if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                        attributeNameResource = -attributeNameResource
                    }
                    iArr[i3] = attributeNameResource
                    i = i4
                    break
            }
            i2++
            i3 = i
        }
        return StateSet.trimStateSet(iArr, i3)
    }

    Int getStateCount() {
        return this.mStateListState.getChildCount()
    }

    Drawable getStateDrawable(Int i) {
        return this.mStateListState.getChild(i)
    }

    Int getStateDrawableIndex(Array<Int> iArr) {
        return this.mStateListState.indexOfStateSet(iArr)
    }

    StateListState getStateListState() {
        return this.mStateListState
    }

    Array<Int> getStateSet(Int i) {
        return this.mStateListState.mStateSets[i]
    }

    fun inflate(@NonNull Context context, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawable)
        setVisible(typedArrayObtainAttributes.getBoolean(R.styleable.StateListDrawable_android_visible, true), true)
        updateStateFromTypedArray(typedArrayObtainAttributes)
        updateDensity(resources)
        typedArrayObtainAttributes.recycle()
        inflateChildElements(context, resources, xmlPullParser, attributeSet, theme)
        onStateChange(getState())
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    fun isStateful() {
        return true
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    @NonNull
    fun mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mStateListState.mutate()
            this.mMutated = true
        }
        return this
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected fun onStateChange(Array<Int> iArr) {
        Boolean zOnStateChange = super.onStateChange(iArr)
        Int iIndexOfStateSet = this.mStateListState.indexOfStateSet(iArr)
        if (iIndexOfStateSet < 0) {
            iIndexOfStateSet = this.mStateListState.indexOfStateSet(StateSet.WILD_CARD)
        }
        return selectDrawable(iIndexOfStateSet) || zOnStateChange
    }

    @Override // android.support.v7.graphics.drawable.DrawableContainer
    protected fun setConstantState(@NonNull DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState)
        if (drawableContainerState is StateListState) {
            this.mStateListState = (StateListState) drawableContainerState
        }
    }
}
