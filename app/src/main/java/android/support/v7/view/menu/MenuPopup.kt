package android.support.v7.view.menu

import android.content.Context
import android.graphics.Rect
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.HeaderViewListAdapter
import android.widget.ListAdapter
import android.widget.PopupWindow

/* JADX INFO: Access modifiers changed from: package-private */
abstract class MenuPopup implements MenuPresenter, ShowableListMenu, AdapterView.OnItemClickListener {
    private Rect mEpicenterBounds

    MenuPopup() {
    }

    protected static Int measureIndividualMenuWidth(ListAdapter listAdapter, ViewGroup viewGroup, Context context, Int i) {
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0)
        Int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0)
        Int count = listAdapter.getCount()
        Int i2 = 0
        Int i3 = 0
        View view = null
        Int i4 = 0
        ViewGroup viewGroup2 = viewGroup
        while (i2 < count) {
            Int itemViewType = listAdapter.getItemViewType(i2)
            if (itemViewType != i3) {
                i3 = itemViewType
                view = null
            }
            ViewGroup frameLayout = viewGroup2 == null ? FrameLayout(context) : viewGroup2
            view = listAdapter.getView(i2, view, frameLayout)
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2)
            Int measuredWidth = view.getMeasuredWidth()
            if (measuredWidth >= i) {
                return i
            }
            if (measuredWidth <= i4) {
                measuredWidth = i4
            }
            i2++
            i4 = measuredWidth
            viewGroup2 = frameLayout
        }
        return i4
    }

    protected static Boolean shouldPreserveIconSpacing(MenuBuilder menuBuilder) {
        Int size = menuBuilder.size()
        for (Int i = 0; i < size; i++) {
            MenuItem item = menuBuilder.getItem(i)
            if (item.isVisible() && item.getIcon() != null) {
                return true
            }
        }
        return false
    }

    protected static MenuAdapter toMenuAdapter(ListAdapter listAdapter) {
        return listAdapter is HeaderViewListAdapter ? (MenuAdapter) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (MenuAdapter) listAdapter
    }

    public abstract Unit addMenu(MenuBuilder menuBuilder)

    protected fun closeMenuOnSubMenuOpened() {
        return true
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false
    }

    fun getEpicenterBounds() {
        return this.mEpicenterBounds
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun getId() {
        return 0
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun getMenuView(ViewGroup viewGroup) {
        throw UnsupportedOperationException("MenuPopups manage their own views")
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun initForMenu(@NonNull Context context, @Nullable MenuBuilder menuBuilder) {
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter()
        toMenuAdapter(listAdapter).mAdapterMenu.performItemAction((MenuItem) listAdapter.getItem(i), this, closeMenuOnSubMenuOpened() ? 0 : 4)
    }

    public abstract Unit setAnchorView(View view)

    fun setEpicenterBounds(Rect rect) {
        this.mEpicenterBounds = rect
    }

    public abstract Unit setForceShowIcon(Boolean z)

    public abstract Unit setGravity(Int i)

    public abstract Unit setHorizontalOffset(Int i)

    public abstract Unit setOnDismissListener(PopupWindow.OnDismissListener onDismissListener)

    public abstract Unit setShowTitle(Boolean z)

    public abstract Unit setVerticalOffset(Int i)
}
