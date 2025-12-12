package androidx.cardview.widget

import android.graphics.drawable.Drawable
import android.view.View

interface g {
    Drawable getCardBackground()

    View getCardView()

    Boolean getPreventCornerOverlap()

    Boolean getUseCompatPadding()

    Unit setCardBackground(Drawable drawable)

    Unit setMinWidthHeightInternal(Int i, Int i2)

    Unit setShadowPadding(Int i, Int i2, Int i3, Int i4)
}
