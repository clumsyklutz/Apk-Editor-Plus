package androidx.cardview.widget

import android.graphics.drawable.Drawable
import android.view.View

class a implements g {
    private Drawable mCardBackground
    final /* synthetic */ CardView this$0

    a(CardView cardView) {
        this.this$0 = cardView
    }

    @Override // androidx.cardview.widget.g
    fun getCardBackground() {
        return this.mCardBackground
    }

    @Override // androidx.cardview.widget.g
    fun getCardView() {
        return this.this$0
    }

    @Override // androidx.cardview.widget.g
    fun getPreventCornerOverlap() {
        return this.this$0.getPreventCornerOverlap()
    }

    @Override // androidx.cardview.widget.g
    fun getUseCompatPadding() {
        return this.this$0.getUseCompatPadding()
    }

    @Override // androidx.cardview.widget.g
    fun setCardBackground(Drawable drawable) {
        this.mCardBackground = drawable
        this.this$0.setBackgroundDrawable(drawable)
    }

    @Override // androidx.cardview.widget.g
    fun setMinWidthHeightInternal(Int i, Int i2) {
        if (i > this.this$0.mUserSetMinWidth) {
            super/*android.widget.FrameLayout*/.setMinimumWidth(i)
        }
        if (i2 > this.this$0.mUserSetMinHeight) {
            super/*android.widget.FrameLayout*/.setMinimumHeight(i2)
        }
    }

    @Override // androidx.cardview.widget.g
    fun setShadowPadding(Int i, Int i2, Int i3, Int i4) {
        this.this$0.mShadowBounds.set(i, i2, i3, i4)
        CardView cardView = this.this$0
        super/*android.widget.FrameLayout*/.setPadding(i + cardView.mContentPadding.left, i2 + this.this$0.mContentPadding.top, i3 + this.this$0.mContentPadding.right, i4 + this.this$0.mContentPadding.bottom)
    }
}
