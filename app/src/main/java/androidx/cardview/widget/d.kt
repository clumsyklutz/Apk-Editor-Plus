package androidx.cardview.widget

import android.content.Context
import android.content.res.ColorStateList
import android.view.View

class d implements h {
    d() {
    }

    private fun getCardBackground(g gVar) {
        return (i) gVar.getCardBackground()
    }

    @Override // androidx.cardview.widget.h
    fun getBackgroundColor(g gVar) {
        return getCardBackground(gVar).getColor()
    }

    @Override // androidx.cardview.widget.h
    fun getElevation(g gVar) {
        return gVar.getCardView().getElevation()
    }

    @Override // androidx.cardview.widget.h
    fun getMaxElevation(g gVar) {
        return getCardBackground(gVar).getPadding()
    }

    @Override // androidx.cardview.widget.h
    fun getMinHeight(g gVar) {
        return getRadius(gVar) * 2.0f
    }

    @Override // androidx.cardview.widget.h
    fun getMinWidth(g gVar) {
        return getRadius(gVar) * 2.0f
    }

    @Override // androidx.cardview.widget.h
    fun getRadius(g gVar) {
        return getCardBackground(gVar).getRadius()
    }

    @Override // androidx.cardview.widget.h
    fun initStatic() {
    }

    @Override // androidx.cardview.widget.h
    fun initialize(g gVar, Context context, ColorStateList colorStateList, Float f, Float f2, Float f3) {
        gVar.setCardBackground(i(colorStateList, f))
        View cardView = gVar.getCardView()
        cardView.setClipToOutline(true)
        cardView.setElevation(f2)
        setMaxElevation(gVar, f3)
    }

    @Override // androidx.cardview.widget.h
    fun onCompatPaddingChanged(g gVar) {
        setMaxElevation(gVar, getMaxElevation(gVar))
    }

    @Override // androidx.cardview.widget.h
    fun onPreventCornerOverlapChanged(g gVar) {
        setMaxElevation(gVar, getMaxElevation(gVar))
    }

    @Override // androidx.cardview.widget.h
    fun setBackgroundColor(g gVar, ColorStateList colorStateList) {
        getCardBackground(gVar).setColor(colorStateList)
    }

    @Override // androidx.cardview.widget.h
    fun setElevation(g gVar, Float f) {
        gVar.getCardView().setElevation(f)
    }

    @Override // androidx.cardview.widget.h
    fun setMaxElevation(g gVar, Float f) {
        getCardBackground(gVar).setPadding(f, gVar.getUseCompatPadding(), gVar.getPreventCornerOverlap())
        updatePadding(gVar)
    }

    @Override // androidx.cardview.widget.h
    fun setRadius(g gVar, Float f) {
        getCardBackground(gVar).setRadius(f)
    }

    @Override // androidx.cardview.widget.h
    fun updatePadding(g gVar) {
        if (!gVar.getUseCompatPadding()) {
            gVar.setShadowPadding(0, 0, 0, 0)
            return
        }
        Float maxElevation = getMaxElevation(gVar)
        Float radius = getRadius(gVar)
        Int iCeil = (Int) Math.ceil(j.calculateHorizontalPadding(maxElevation, radius, gVar.getPreventCornerOverlap()))
        Int iCeil2 = (Int) Math.ceil(j.calculateVerticalPadding(maxElevation, radius, gVar.getPreventCornerOverlap()))
        gVar.setShadowPadding(iCeil, iCeil2, iCeil, iCeil2)
    }
}
