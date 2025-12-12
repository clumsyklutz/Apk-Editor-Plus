package android.support.v7.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPresenter
import android.util.SparseArray
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.SpinnerAdapter

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface DecorToolbar {
    Unit animateToVisibility(Int i)

    Boolean canShowOverflowMenu()

    Unit collapseActionView()

    Unit dismissPopupMenus()

    Context getContext()

    View getCustomView()

    Int getDisplayOptions()

    Int getDropdownItemCount()

    Int getDropdownSelectedPosition()

    Int getHeight()

    Menu getMenu()

    Int getNavigationMode()

    CharSequence getSubtitle()

    CharSequence getTitle()

    ViewGroup getViewGroup()

    Int getVisibility()

    Boolean hasEmbeddedTabs()

    Boolean hasExpandedActionView()

    Boolean hasIcon()

    Boolean hasLogo()

    Boolean hideOverflowMenu()

    Unit initIndeterminateProgress()

    Unit initProgress()

    Boolean isOverflowMenuShowPending()

    Boolean isOverflowMenuShowing()

    Boolean isTitleTruncated()

    Unit restoreHierarchyState(SparseArray sparseArray)

    Unit saveHierarchyState(SparseArray sparseArray)

    Unit setBackgroundDrawable(Drawable drawable)

    Unit setCollapsible(Boolean z)

    Unit setCustomView(View view)

    Unit setDefaultNavigationContentDescription(Int i)

    Unit setDefaultNavigationIcon(Drawable drawable)

    Unit setDisplayOptions(Int i)

    Unit setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener)

    Unit setDropdownSelectedPosition(Int i)

    Unit setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView)

    Unit setHomeButtonEnabled(Boolean z)

    Unit setIcon(Int i)

    Unit setIcon(Drawable drawable)

    Unit setLogo(Int i)

    Unit setLogo(Drawable drawable)

    Unit setMenu(Menu menu, MenuPresenter.Callback callback)

    Unit setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2)

    Unit setMenuPrepared()

    Unit setNavigationContentDescription(Int i)

    Unit setNavigationContentDescription(CharSequence charSequence)

    Unit setNavigationIcon(Int i)

    Unit setNavigationIcon(Drawable drawable)

    Unit setNavigationMode(Int i)

    Unit setSubtitle(CharSequence charSequence)

    Unit setTitle(CharSequence charSequence)

    Unit setVisibility(Int i)

    Unit setWindowCallback(Window.Callback callback)

    Unit setWindowTitle(CharSequence charSequence)

    ViewPropertyAnimatorCompat setupAnimatorToVisibility(Int i, Long j)

    Boolean showOverflowMenu()
}
