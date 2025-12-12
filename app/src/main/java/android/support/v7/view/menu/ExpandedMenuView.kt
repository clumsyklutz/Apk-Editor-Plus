package android.support.v7.view.menu

import android.R
import android.content.Context
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.TintTypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ExpandedMenuView extends ListView implements MenuBuilder.ItemInvoker, MenuView, AdapterView.OnItemClickListener {
    private static final Array<Int> TINT_ATTRS = {R.attr.background, R.attr.divider}
    private Int mAnimations
    private MenuBuilder mMenu

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listViewStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet)
        setOnItemClickListener(this)
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, TINT_ATTRS, i, 0)
        if (tintTypedArrayObtainStyledAttributes.hasValue(0)) {
            setBackgroundDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(0))
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(1)) {
            setDivider(tintTypedArrayObtainStyledAttributes.getDrawable(1))
        }
        tintTypedArrayObtainStyledAttributes.recycle()
    }

    @Override // android.support.v7.view.menu.MenuView
    public final Int getWindowAnimations() {
        return this.mAnimations
    }

    @Override // android.support.v7.view.menu.MenuView
    public final Unit initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder
    }

    @Override // android.support.v7.view.menu.MenuBuilder.ItemInvoker
    public final Boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0)
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected final Unit onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setChildrenDrawingCacheEnabled(false)
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        invokeItem((MenuItemImpl) getAdapter().getItem(i))
    }
}
