package android.support.v4.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.core.internal.view.SupportMenu
import android.view.Menu
import android.view.MenuItem

class MenuCompat {
    private constructor() {
    }

    @SuppressLint({"NewApi"})
    fun setGroupDividerEnabled(Menu menu, Boolean z) {
        if (menu is SupportMenu) {
            ((SupportMenu) menu).setGroupDividerEnabled(z)
        } else if (Build.VERSION.SDK_INT >= 28) {
            menu.setGroupDividerEnabled(z)
        }
    }

    @Deprecated
    fun setShowAsAction(MenuItem menuItem, Int i) {
        menuItem.setShowAsAction(i)
    }
}
