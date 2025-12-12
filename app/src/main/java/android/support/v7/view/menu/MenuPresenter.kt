package android.support.v7.view.menu

import android.content.Context
import android.os.Parcelable
import android.support.annotation.RestrictTo
import android.view.ViewGroup

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface MenuPresenter {

    public interface Callback {
        Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z)

        Boolean onOpenSubMenu(MenuBuilder menuBuilder)
    }

    Boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl)

    Boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl)

    Boolean flagActionItems()

    Int getId()

    MenuView getMenuView(ViewGroup viewGroup)

    Unit initForMenu(Context context, MenuBuilder menuBuilder)

    Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z)

    Unit onRestoreInstanceState(Parcelable parcelable)

    Parcelable onSaveInstanceState()

    Boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder)

    Unit setCallback(Callback callback)

    Unit updateMenuView(Boolean z)
}
