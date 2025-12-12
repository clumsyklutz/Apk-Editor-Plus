package android.support.v4.graphics.drawable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Outline
import android.graphics.Rect
import android.support.annotation.RequiresApi
import android.view.Gravity

@RequiresApi(21)
class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    protected constructor(Resources resources, Bitmap bitmap) {
        super(resources, bitmap)
    }

    @Override // android.graphics.drawable.Drawable
    fun getOutline(Outline outline) {
        updateDstRect()
        outline.setRoundRect(this.mDstRect, getCornerRadius())
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    Unit gravityCompatApply(Int i, Int i2, Int i3, Rect rect, Rect rect2) {
        Gravity.apply(i, i2, i3, rect, rect2, 0)
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    fun hasMipMap() {
        return this.mBitmap != null && this.mBitmap.hasMipMap()
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    fun setMipMap(Boolean z) {
        if (this.mBitmap != null) {
            this.mBitmap.setHasMipMap(z)
            invalidateSelf()
        }
    }
}
