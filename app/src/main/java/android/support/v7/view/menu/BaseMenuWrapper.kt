package android.support.v7.view.menu

import android.content.Context
import android.support.v4.internal.view.SupportMenuItem
import android.support.v4.internal.view.SupportSubMenu
import android.support.v4.util.ArrayMap
import android.view.MenuItem
import android.view.SubMenu
import java.util.Iterator
import java.util.Map

abstract class BaseMenuWrapper extends BaseWrapper {
    final Context mContext
    private Map mMenuItems
    private Map mSubMenus

    BaseMenuWrapper(Context context, Object obj) {
        super(obj)
        this.mContext = context
    }

    final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (!(menuItem is SupportMenuItem)) {
            return menuItem
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem
        if (this.mMenuItems == null) {
            this.mMenuItems = ArrayMap()
        }
        MenuItem menuItem2 = (MenuItem) this.mMenuItems.get(menuItem)
        if (menuItem2 != null) {
            return menuItem2
        }
        MenuItem menuItemWrapSupportMenuItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, supportMenuItem)
        this.mMenuItems.put(supportMenuItem, menuItemWrapSupportMenuItem)
        return menuItemWrapSupportMenuItem
    }

    final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        if (!(subMenu is SupportSubMenu)) {
            return subMenu
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu
        if (this.mSubMenus == null) {
            this.mSubMenus = ArrayMap()
        }
        SubMenu subMenu2 = (SubMenu) this.mSubMenus.get(supportSubMenu)
        if (subMenu2 != null) {
            return subMenu2
        }
        SubMenu subMenuWrapSupportSubMenu = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, supportSubMenu)
        this.mSubMenus.put(supportSubMenu, subMenuWrapSupportSubMenu)
        return subMenuWrapSupportSubMenu
    }

    final Unit internalClear() {
        if (this.mMenuItems != null) {
            this.mMenuItems.clear()
        }
        if (this.mSubMenus != null) {
            this.mSubMenus.clear()
        }
    }

    final Unit internalRemoveGroup(Int i) {
        if (this.mMenuItems == null) {
            return
        }
        Iterator it = this.mMenuItems.keySet().iterator()
        while (it.hasNext()) {
            if (i == ((MenuItem) it.next()).getGroupId()) {
                it.remove()
            }
        }
    }

    final Unit internalRemoveItem(Int i) {
        if (this.mMenuItems == null) {
            return
        }
        Iterator it = this.mMenuItems.keySet().iterator()
        while (it.hasNext()) {
            if (i == ((MenuItem) it.next()).getItemId()) {
                it.remove()
                return
            }
        }
    }
}
