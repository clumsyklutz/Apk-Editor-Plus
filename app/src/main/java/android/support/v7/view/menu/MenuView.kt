package android.support.v7.view.menu

import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface MenuView {

    public interface ItemView {
        MenuItemImpl getItemData()

        Unit initialize(MenuItemImpl menuItemImpl, Int i)

        Boolean prefersCondensedTitle()

        Unit setCheckable(Boolean z)

        Unit setChecked(Boolean z)

        Unit setEnabled(Boolean z)

        Unit setIcon(Drawable drawable)

        Unit setShortcut(Boolean z, Char c)

        Unit setTitle(CharSequence charSequence)

        Boolean showsIcon()
    }

    Int getWindowAnimations()

    Unit initialize(MenuBuilder menuBuilder)
}
