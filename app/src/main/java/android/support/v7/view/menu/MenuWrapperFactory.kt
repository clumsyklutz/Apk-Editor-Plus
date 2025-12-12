package android.support.v7.view.menu

import android.content.Context
import android.os.Build
import android.support.annotation.RestrictTo
import androidx.core.internal.view.SupportMenu
import android.support.v4.internal.view.SupportMenuItem
import android.support.v4.internal.view.SupportSubMenu
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuWrapperFactory {
    private constructor() {
    }

    fun wrapSupportMenu(Context context, SupportMenu supportMenu) {
        return MenuWrapperICS(context, supportMenu)
    }

    fun wrapSupportMenuItem(Context context, SupportMenuItem supportMenuItem) {
        return Build.VERSION.SDK_INT >= 16 ? MenuItemWrapperJB(context, supportMenuItem) : MenuItemWrapperICS(context, supportMenuItem)
    }

    fun wrapSupportSubMenu(Context context, SupportSubMenu supportSubMenu) {
        return SubMenuWrapperICS(context, supportSubMenu)
    }
}
