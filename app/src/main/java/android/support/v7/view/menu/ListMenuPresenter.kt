package android.support.v7.view.menu

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RestrictTo
import androidx.appcompat.R
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.view.menu.MenuView
import android.util.SparseArray
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListAdapter
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ListMenuPresenter implements MenuPresenter, AdapterView.OnItemClickListener {
    private static val TAG = "ListMenuPresenter"
    public static val VIEWS_TAG = "android:menu:list"
    MenuAdapter mAdapter
    private MenuPresenter.Callback mCallback
    Context mContext
    private Int mId
    LayoutInflater mInflater
    Int mItemIndexOffset
    Int mItemLayoutRes
    MenuBuilder mMenu
    ExpandedMenuView mMenuView
    Int mThemeRes

    class MenuAdapter extends BaseAdapter {
        private Int mExpandedIndex = -1

        constructor() {
            findExpandedIndex()
        }

        Unit findExpandedIndex() {
            MenuItemImpl expandedItem = ListMenuPresenter.this.mMenu.getExpandedItem()
            if (expandedItem != null) {
                ArrayList nonActionItems = ListMenuPresenter.this.mMenu.getNonActionItems()
                Int size = nonActionItems.size()
                for (Int i = 0; i < size; i++) {
                    if (((MenuItemImpl) nonActionItems.get(i)) == expandedItem) {
                        this.mExpandedIndex = i
                        return
                    }
                }
            }
            this.mExpandedIndex = -1
        }

        @Override // android.widget.Adapter
        fun getCount() {
            Int size = ListMenuPresenter.this.mMenu.getNonActionItems().size() - ListMenuPresenter.this.mItemIndexOffset
            return this.mExpandedIndex < 0 ? size : size - 1
        }

        @Override // android.widget.Adapter
        fun getItem(Int i) {
            ArrayList nonActionItems = ListMenuPresenter.this.mMenu.getNonActionItems()
            Int i2 = ListMenuPresenter.this.mItemIndexOffset + i
            if (this.mExpandedIndex >= 0 && i2 >= this.mExpandedIndex) {
                i2++
            }
            return (MenuItemImpl) nonActionItems.get(i2)
        }

        @Override // android.widget.Adapter
        fun getItemId(Int i) {
            return i
        }

        @Override // android.widget.Adapter
        fun getView(Int i, View view, ViewGroup viewGroup) {
            View viewInflate = view == null ? ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, viewGroup, false) : view
            ((MenuView.ItemView) viewInflate).initialize(getItem(i), 0)
            return viewInflate
        }

        @Override // android.widget.BaseAdapter
        fun notifyDataSetChanged() {
            findExpandedIndex()
            super.notifyDataSetChanged()
        }
    }

    constructor(Int i, Int i2) {
        this.mItemLayoutRes = i
        this.mThemeRes = i2
    }

    constructor(Context context, Int i) {
        this(i, 0)
        this.mContext = context
        this.mInflater = LayoutInflater.from(this.mContext)
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun flagActionItems() {
        return false
    }

    fun getAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = MenuAdapter()
        }
        return this.mAdapter
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun getId() {
        return this.mId
    }

    Int getItemIndexOffset() {
        return this.mItemIndexOffset
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (ExpandedMenuView) this.mInflater.inflate(R.layout.abc_expanded_menu_layout, viewGroup, false)
            if (this.mAdapter == null) {
                this.mAdapter = MenuAdapter()
            }
            this.mMenuView.setAdapter((ListAdapter) this.mAdapter)
            this.mMenuView.setOnItemClickListener(this)
        }
        return this.mMenuView
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun initForMenu(Context context, MenuBuilder menuBuilder) {
        if (this.mThemeRes != 0) {
            this.mContext = ContextThemeWrapper(context, this.mThemeRes)
            this.mInflater = LayoutInflater.from(this.mContext)
        } else if (this.mContext != null) {
            this.mContext = context
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(this.mContext)
            }
        }
        this.mMenu = menuBuilder
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged()
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, z)
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        this.mMenu.performItemAction(this.mAdapter.getItem(i), this, 0)
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onRestoreInstanceState(Parcelable parcelable) {
        restoreHierarchyState((Bundle) parcelable)
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null
        }
        Bundle bundle = Bundle()
        saveHierarchyState(bundle)
        return bundle
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false
        }
        MenuDialogHelper(subMenuBuilder).show(null)
        if (this.mCallback != null) {
            this.mCallback.onOpenSubMenu(subMenuBuilder)
        }
        return true
    }

    fun restoreHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG)
        if (sparseParcelableArray != null) {
            this.mMenuView.restoreHierarchyState(sparseParcelableArray)
        }
    }

    fun saveHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = new SparseArray<>()
        if (this.mMenuView != null) {
            this.mMenuView.saveHierarchyState(sparseArray)
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray)
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback
    }

    fun setId(Int i) {
        this.mId = i
    }

    fun setItemIndexOffset(Int i) {
        this.mItemIndexOffset = i
        if (this.mMenuView != null) {
            updateMenuView(false)
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun updateMenuView(Boolean z) {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged()
        }
    }
}
