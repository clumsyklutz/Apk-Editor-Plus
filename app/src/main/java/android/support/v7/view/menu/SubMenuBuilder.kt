package android.support.v7.view.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.MenuBuilder
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SubMenuBuilder extends MenuBuilder implements SubMenu {
    private MenuItemImpl mItem
    private MenuBuilder mParentMenu

    constructor(Context context, MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        super(context)
        this.mParentMenu = menuBuilder
        this.mItem = menuItemImpl
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun collapseItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.collapseItemActionView(menuItemImpl)
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    Boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return super.dispatchMenuItemSelected(menuBuilder, menuItem) || this.mParentMenu.dispatchMenuItemSelected(menuBuilder, menuItem)
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun expandItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.expandItemActionView(menuItemImpl)
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun getActionViewStatesKey() {
        Int itemId = this.mItem != null ? this.mItem.getItemId() : 0
        if (itemId == 0) {
            return null
        }
        return super.getActionViewStatesKey() + ":" + itemId
    }

    @Override // android.view.SubMenu
    fun getItem() {
        return this.mItem
    }

    fun getParentMenu() {
        return this.mParentMenu
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun getRootMenu() {
        return this.mParentMenu.getRootMenu()
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun isGroupDividerEnabled() {
        return this.mParentMenu.isGroupDividerEnabled()
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun isQwertyMode() {
        return this.mParentMenu.isQwertyMode()
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun isShortcutsVisible() {
        return this.mParentMenu.isShortcutsVisible()
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun setCallback(MenuBuilder.Callback callback) {
        this.mParentMenu.setCallback(callback)
    }

    @Override // android.support.v7.view.menu.MenuBuilder, android.support.v4.internal.view.SupportMenu, android.view.Menu
    fun setGroupDividerEnabled(Boolean z) {
        this.mParentMenu.setGroupDividerEnabled(z)
    }

    @Override // android.view.SubMenu
    fun setHeaderIcon(Int i) {
        return (SubMenu) super.setHeaderIconInt(i)
    }

    @Override // android.view.SubMenu
    fun setHeaderIcon(Drawable drawable) {
        return (SubMenu) super.setHeaderIconInt(drawable)
    }

    @Override // android.view.SubMenu
    fun setHeaderTitle(Int i) {
        return (SubMenu) super.setHeaderTitleInt(i)
    }

    @Override // android.view.SubMenu
    fun setHeaderTitle(CharSequence charSequence) {
        return (SubMenu) super.setHeaderTitleInt(charSequence)
    }

    @Override // android.view.SubMenu
    fun setHeaderView(View view) {
        return (SubMenu) super.setHeaderViewInt(view)
    }

    @Override // android.view.SubMenu
    fun setIcon(Int i) {
        this.mItem.setIcon(i)
        return this
    }

    @Override // android.view.SubMenu
    fun setIcon(Drawable drawable) {
        this.mItem.setIcon(drawable)
        return this
    }

    @Override // android.support.v7.view.menu.MenuBuilder, android.view.Menu
    fun setQwertyMode(Boolean z) {
        this.mParentMenu.setQwertyMode(z)
    }

    @Override // android.support.v7.view.menu.MenuBuilder
    fun setShortcutsVisible(Boolean z) {
        this.mParentMenu.setShortcutsVisible(z)
    }
}
