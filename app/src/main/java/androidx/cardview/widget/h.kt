package androidx.cardview.widget

import android.content.Context
import android.content.res.ColorStateList

interface h {
    ColorStateList getBackgroundColor(g gVar)

    Float getElevation(g gVar)

    Float getMaxElevation(g gVar)

    Float getMinHeight(g gVar)

    Float getMinWidth(g gVar)

    Float getRadius(g gVar)

    Unit initStatic()

    Unit initialize(g gVar, Context context, ColorStateList colorStateList, Float f, Float f2, Float f3)

    Unit onCompatPaddingChanged(g gVar)

    Unit onPreventCornerOverlapChanged(g gVar)

    Unit setBackgroundColor(g gVar, ColorStateList colorStateList)

    Unit setElevation(g gVar, Float f)

    Unit setMaxElevation(g gVar, Float f)

    Unit setRadius(g gVar, Float f)

    Unit updatePadding(g gVar)
}
