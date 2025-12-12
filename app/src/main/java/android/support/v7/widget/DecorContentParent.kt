package android.support.v7.widget

import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.MenuPresenter
import android.util.SparseArray
import android.view.Menu
import android.view.Window

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface DecorContentParent {
    Boolean canShowOverflowMenu()

    Unit dismissPopups()

    CharSequence getTitle()

    Boolean hasIcon()

    Boolean hasLogo()

    Boolean hideOverflowMenu()

    Unit initFeature(Int i)

    Boolean isOverflowMenuShowPending()

    Boolean isOverflowMenuShowing()

    Unit restoreToolbarHierarchyState(SparseArray sparseArray)

    Unit saveToolbarHierarchyState(SparseArray sparseArray)

    Unit setIcon(Int i)

    Unit setIcon(Drawable drawable)

    Unit setLogo(Int i)

    Unit setMenu(Menu menu, MenuPresenter.Callback callback)

    Unit setMenuPrepared()

    Unit setUiOptions(Int i)

    Unit setWindowCallback(Window.Callback callback)

    Unit setWindowTitle(CharSequence charSequence)

    Boolean showOverflowMenu()
}
