package android.support.v7.widget

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi

class ActionBarBackgroundDrawable extends Drawable {
    final ActionBarContainer mContainer

    constructor(ActionBarContainer actionBarContainer) {
        this.mContainer = actionBarContainer
    }

    @Override // android.graphics.drawable.Drawable
    fun draw(Canvas canvas) {
        if (this.mContainer.mIsSplit) {
            if (this.mContainer.mSplitBackground != null) {
                this.mContainer.mSplitBackground.draw(canvas)
            }
        } else {
            if (this.mContainer.mBackground != null) {
                this.mContainer.mBackground.draw(canvas)
            }
            if (this.mContainer.mStackedBackground == null || !this.mContainer.mIsStacked) {
                return
            }
            this.mContainer.mStackedBackground.draw(canvas)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun getOpacity() {
        return 0
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    fun getOutline(@NonNull Outline outline) {
        if (this.mContainer.mIsSplit) {
            if (this.mContainer.mSplitBackground != null) {
                this.mContainer.mSplitBackground.getOutline(outline)
            }
        } else if (this.mContainer.mBackground != null) {
            this.mContainer.mBackground.getOutline(outline)
        }
    }

    @Override // android.graphics.drawable.Drawable
    fun setAlpha(Int i) {
    }

    @Override // android.graphics.drawable.Drawable
    fun setColorFilter(ColorFilter colorFilter) {
    }
}
