package android.support.v7.view.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v4.internal.view.SupportSubMenu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SubMenuWrapperICS extends MenuWrapperICS implements SubMenu {
    SubMenuWrapperICS(Context context, SupportSubMenu supportSubMenu) {
        super(context, supportSubMenu)
    }

    @Override // android.view.SubMenu
    fun clearHeader() {
        getWrappedObject().clearHeader()
    }

    @Override // android.view.SubMenu
    fun getItem() {
        return getMenuItemWrapper(getWrappedObject().getItem())
    }

    @Override // android.support.v7.view.menu.BaseWrapper
    fun getWrappedObject() {
        return (SupportSubMenu) this.mWrappedObject
    }

    @Override // android.view.SubMenu
    fun setHeaderIcon(Int i) {
        getWrappedObject().setHeaderIcon(i)
        return this
    }

    @Override // android.view.SubMenu
    fun setHeaderIcon(Drawable drawable) {
        getWrappedObject().setHeaderIcon(drawable)
        return this
    }

    @Override // android.view.SubMenu
    fun setHeaderTitle(Int i) {
        getWrappedObject().setHeaderTitle(i)
        return this
    }

    @Override // android.view.SubMenu
    fun setHeaderTitle(CharSequence charSequence) {
        getWrappedObject().setHeaderTitle(charSequence)
        return this
    }

    @Override // android.view.SubMenu
    fun setHeaderView(View view) {
        getWrappedObject().setHeaderView(view)
        return this
    }

    @Override // android.view.SubMenu
    fun setIcon(Int i) {
        getWrappedObject().setIcon(i)
        return this
    }

    @Override // android.view.SubMenu
    fun setIcon(Drawable drawable) {
        getWrappedObject().setIcon(drawable)
        return this
    }
}
