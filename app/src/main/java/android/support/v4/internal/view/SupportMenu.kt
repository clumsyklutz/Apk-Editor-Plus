package android.support.v4.internal.view

import android.support.annotation.RestrictTo
import android.view.Menu

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface SupportMenu extends Menu {
    public static val CATEGORY_MASK = -65536
    public static val CATEGORY_SHIFT = 16
    public static val FLAG_KEEP_OPEN_ON_SUBMENU_OPENED = 4
    public static val SUPPORTED_MODIFIERS_MASK = 69647
    public static val USER_MASK = 65535
    public static val USER_SHIFT = 0

    @Override // android.view.Menu
    Unit setGroupDividerEnabled(Boolean z)
}
