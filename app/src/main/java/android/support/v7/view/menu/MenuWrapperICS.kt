package android.support.v7.view.menu

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.internal.view.SupportMenu
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu

class MenuWrapperICS extends BaseMenuWrapper implements Menu {
    MenuWrapperICS(Context context, SupportMenu supportMenu) {
        super(context, supportMenu)
    }

    @Override // android.view.Menu
    fun add(Int i) {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(i))
    }

    @Override // android.view.Menu
    fun add(Int i, Int i2, Int i3, Int i4) {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(i, i2, i3, i4))
    }

    @Override // android.view.Menu
    fun add(Int i, Int i2, Int i3, CharSequence charSequence) {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(i, i2, i3, charSequence))
    }

    @Override // android.view.Menu
    fun add(CharSequence charSequence) {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).add(charSequence))
    }

    @Override // android.view.Menu
    fun addIntentOptions(Int i, Int i2, Int i3, ComponentName componentName, Array<Intent> intentArr, Intent intent, Int i4, Array<MenuItem> menuItemArr) {
        Array<MenuItem> menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null
        Int iAddIntentOptions = ((SupportMenu) this.mWrappedObject).addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2)
        if (menuItemArr2 != null) {
            Int length = menuItemArr2.length
            for (Int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = getMenuItemWrapper(menuItemArr2[i5])
            }
        }
        return iAddIntentOptions
    }

    @Override // android.view.Menu
    fun addSubMenu(Int i) {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(i))
    }

    @Override // android.view.Menu
    fun addSubMenu(Int i, Int i2, Int i3, Int i4) {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(i, i2, i3, i4))
    }

    @Override // android.view.Menu
    fun addSubMenu(Int i, Int i2, Int i3, CharSequence charSequence) {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(i, i2, i3, charSequence))
    }

    @Override // android.view.Menu
    fun addSubMenu(CharSequence charSequence) {
        return getSubMenuWrapper(((SupportMenu) this.mWrappedObject).addSubMenu(charSequence))
    }

    @Override // android.view.Menu
    fun clear() {
        internalClear()
        ((SupportMenu) this.mWrappedObject).clear()
    }

    @Override // android.view.Menu
    fun close() {
        ((SupportMenu) this.mWrappedObject).close()
    }

    @Override // android.view.Menu
    fun findItem(Int i) {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).findItem(i))
    }

    @Override // android.view.Menu
    fun getItem(Int i) {
        return getMenuItemWrapper(((SupportMenu) this.mWrappedObject).getItem(i))
    }

    @Override // android.view.Menu
    fun hasVisibleItems() {
        return ((SupportMenu) this.mWrappedObject).hasVisibleItems()
    }

    @Override // android.view.Menu
    fun isShortcutKey(Int i, KeyEvent keyEvent) {
        return ((SupportMenu) this.mWrappedObject).isShortcutKey(i, keyEvent)
    }

    @Override // android.view.Menu
    fun performIdentifierAction(Int i, Int i2) {
        return ((SupportMenu) this.mWrappedObject).performIdentifierAction(i, i2)
    }

    @Override // android.view.Menu
    fun performShortcut(Int i, KeyEvent keyEvent, Int i2) {
        return ((SupportMenu) this.mWrappedObject).performShortcut(i, keyEvent, i2)
    }

    @Override // android.view.Menu
    fun removeGroup(Int i) {
        internalRemoveGroup(i)
        ((SupportMenu) this.mWrappedObject).removeGroup(i)
    }

    @Override // android.view.Menu
    fun removeItem(Int i) {
        internalRemoveItem(i)
        ((SupportMenu) this.mWrappedObject).removeItem(i)
    }

    @Override // android.view.Menu
    fun setGroupCheckable(Int i, Boolean z, Boolean z2) {
        ((SupportMenu) this.mWrappedObject).setGroupCheckable(i, z, z2)
    }

    @Override // android.view.Menu
    fun setGroupEnabled(Int i, Boolean z) {
        ((SupportMenu) this.mWrappedObject).setGroupEnabled(i, z)
    }

    @Override // android.view.Menu
    fun setGroupVisible(Int i, Boolean z) {
        ((SupportMenu) this.mWrappedObject).setGroupVisible(i, z)
    }

    @Override // android.view.Menu
    fun setQwertyMode(Boolean z) {
        ((SupportMenu) this.mWrappedObject).setQwertyMode(z)
    }

    @Override // android.view.Menu
    fun size() {
        return ((SupportMenu) this.mWrappedObject).size()
    }
}
