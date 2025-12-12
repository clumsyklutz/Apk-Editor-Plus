package androidx.cardview.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.RectF

class e implements h {
    val mCornerRect = RectF()

    e() {
    }

    private fun createBackground(Context context, ColorStateList colorStateList, Float f, Float f2, Float f3) {
        return j(context.getResources(), colorStateList, f, f2, f3)
    }

    private fun getShadowBackground(g gVar) {
        return (j) gVar.getCardBackground()
    }

    @Override // androidx.cardview.widget.h
    fun getBackgroundColor(g gVar) {
        return getShadowBackground(gVar).getColor()
    }

    @Override // androidx.cardview.widget.h
    fun getElevation(g gVar) {
        return getShadowBackground(gVar).getShadowSize()
    }

    @Override // androidx.cardview.widget.h
    fun getMaxElevation(g gVar) {
        return getShadowBackground(gVar).getMaxShadowSize()
    }

    @Override // androidx.cardview.widget.h
    fun getMinHeight(g gVar) {
        return getShadowBackground(gVar).getMinHeight()
    }

    @Override // androidx.cardview.widget.h
    fun getMinWidth(g gVar) {
        return getShadowBackground(gVar).getMinWidth()
    }

    @Override // androidx.cardview.widget.h
    fun getRadius(g gVar) {
        return getShadowBackground(gVar).getCornerRadius()
    }

    @Override // androidx.cardview.widget.h
    fun initStatic() {
        j.sRoundRectHelper = f(this)
    }

    @Override // androidx.cardview.widget.h
    fun initialize(g gVar, Context context, ColorStateList colorStateList, Float f, Float f2, Float f3) {
        j jVarCreateBackground = createBackground(context, colorStateList, f, f2, f3)
        jVarCreateBackground.setAddPaddingForCorners(gVar.getPreventCornerOverlap())
        gVar.setCardBackground(jVarCreateBackground)
        updatePadding(gVar)
    }

    @Override // androidx.cardview.widget.h
    fun onCompatPaddingChanged(g gVar) {
    }

    @Override // androidx.cardview.widget.h
    fun onPreventCornerOverlapChanged(g gVar) {
        getShadowBackground(gVar).setAddPaddingForCorners(gVar.getPreventCornerOverlap())
        updatePadding(gVar)
    }

    @Override // androidx.cardview.widget.h
    fun setBackgroundColor(g gVar, ColorStateList colorStateList) {
        getShadowBackground(gVar).setColor(colorStateList)
    }

    @Override // androidx.cardview.widget.h
    fun setElevation(g gVar, Float f) {
        getShadowBackground(gVar).setShadowSize(f)
    }

    @Override // androidx.cardview.widget.h
    fun setMaxElevation(g gVar, Float f) {
        getShadowBackground(gVar).setMaxShadowSize(f)
        updatePadding(gVar)
    }

    @Override // androidx.cardview.widget.h
    fun setRadius(g gVar, Float f) {
        getShadowBackground(gVar).setCornerRadius(f)
        updatePadding(gVar)
    }

    @Override // androidx.cardview.widget.h
    fun updatePadding(g gVar) {
        Rect rect = Rect()
        getShadowBackground(gVar).getMaxShadowAndCornerPadding(rect)
        gVar.setMinWidthHeightInternal((Int) Math.ceil(getMinWidth(gVar)), (Int) Math.ceil(getMinHeight(gVar)))
        gVar.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom)
    }
}
