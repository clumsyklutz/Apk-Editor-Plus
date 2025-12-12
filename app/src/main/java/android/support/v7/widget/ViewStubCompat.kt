package android.support.v7.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.support.annotation.RestrictTo
import androidx.appcompat.R
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import java.lang.ref.WeakReference

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ViewStubCompat extends View {
    private OnInflateListener mInflateListener
    private Int mInflatedId
    private WeakReference mInflatedViewRef
    private LayoutInflater mInflater
    private Int mLayoutResource

    public interface OnInflateListener {
        Unit onInflate(ViewStubCompat viewStubCompat, View view)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mLayoutResource = 0
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewStubCompat, i, 0)
        this.mInflatedId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ViewStubCompat_android_inflatedId, -1)
        this.mLayoutResource = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ViewStubCompat_android_layout, 0)
        setId(typedArrayObtainStyledAttributes.getResourceId(R.styleable.ViewStubCompat_android_id, -1))
        typedArrayObtainStyledAttributes.recycle()
        setVisibility(8)
        setWillNotDraw(true)
    }

    @Override // android.view.View
    protected final Unit dispatchDraw(Canvas canvas) {
    }

    @Override // android.view.View
    @SuppressLint({"MissingSuperCall"})
    public final Unit draw(Canvas canvas) {
    }

    public final Int getInflatedId() {
        return this.mInflatedId
    }

    public final LayoutInflater getLayoutInflater() {
        return this.mInflater
    }

    public final Int getLayoutResource() {
        return this.mLayoutResource
    }

    public final View inflate() {
        ViewParent parent = getParent()
        if (parent == null || !(parent is ViewGroup)) {
            throw IllegalStateException("ViewStub must have a non-null ViewGroup viewParent")
        }
        if (this.mLayoutResource == 0) {
            throw IllegalArgumentException("ViewStub must have a valid layoutResource")
        }
        ViewGroup viewGroup = (ViewGroup) parent
        View viewInflate = (this.mInflater != null ? this.mInflater : LayoutInflater.from(getContext())).inflate(this.mLayoutResource, viewGroup, false)
        if (this.mInflatedId != -1) {
            viewInflate.setId(this.mInflatedId)
        }
        Int iIndexOfChild = viewGroup.indexOfChild(this)
        viewGroup.removeViewInLayout(this)
        ViewGroup.LayoutParams layoutParams = getLayoutParams()
        if (layoutParams != null) {
            viewGroup.addView(viewInflate, iIndexOfChild, layoutParams)
        } else {
            viewGroup.addView(viewInflate, iIndexOfChild)
        }
        this.mInflatedViewRef = WeakReference(viewInflate)
        if (this.mInflateListener != null) {
            this.mInflateListener.onInflate(this, viewInflate)
        }
        return viewInflate
    }

    @Override // android.view.View
    protected final Unit onMeasure(Int i, Int i2) {
        setMeasuredDimension(0, 0)
    }

    public final Unit setInflatedId(Int i) {
        this.mInflatedId = i
    }

    public final Unit setLayoutInflater(LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater
    }

    public final Unit setLayoutResource(Int i) {
        this.mLayoutResource = i
    }

    public final Unit setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener
    }

    @Override // android.view.View
    public final Unit setVisibility(Int i) {
        if (this.mInflatedViewRef != null) {
            View view = (View) this.mInflatedViewRef.get()
            if (view == null) {
                throw IllegalStateException("setVisibility called on un-referenced view")
            }
            view.setVisibility(i)
            return
        }
        super.setVisibility(i)
        if (i == 0 || i == 4) {
            inflate()
        }
    }
}
