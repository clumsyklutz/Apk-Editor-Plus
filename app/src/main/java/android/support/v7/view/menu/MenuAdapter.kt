package android.support.v7.view.menu

import android.support.annotation.RestrictTo
import android.support.v7.view.menu.MenuView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuAdapter extends BaseAdapter {
    MenuBuilder mAdapterMenu
    private Int mExpandedIndex = -1
    private Boolean mForceShowIcon
    private final LayoutInflater mInflater
    private final Int mItemLayoutRes
    private final Boolean mOverflowOnly

    constructor(MenuBuilder menuBuilder, LayoutInflater layoutInflater, Boolean z, Int i) {
        this.mOverflowOnly = z
        this.mInflater = layoutInflater
        this.mAdapterMenu = menuBuilder
        this.mItemLayoutRes = i
        findExpandedIndex()
    }

    Unit findExpandedIndex() {
        MenuItemImpl expandedItem = this.mAdapterMenu.getExpandedItem()
        if (expandedItem != null) {
            ArrayList nonActionItems = this.mAdapterMenu.getNonActionItems()
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

    fun getAdapterMenu() {
        return this.mAdapterMenu
    }

    @Override // android.widget.Adapter
    fun getCount() {
        return this.mExpandedIndex < 0 ? (this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems()).size() : r0.size() - 1
    }

    fun getForceShowIcon() {
        return this.mForceShowIcon
    }

    @Override // android.widget.Adapter
    fun getItem(Int i) {
        ArrayList nonActionItems = this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems()
        if (this.mExpandedIndex >= 0 && i >= this.mExpandedIndex) {
            i++
        }
        return (MenuItemImpl) nonActionItems.get(i)
    }

    @Override // android.widget.Adapter
    fun getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    fun getView(Int i, View view, ViewGroup viewGroup) {
        View viewInflate = view == null ? this.mInflater.inflate(this.mItemLayoutRes, viewGroup, false) : view
        Int groupId = getItem(i).getGroupId()
        ((ListMenuItemView) viewInflate).setGroupDividerEnabled(this.mAdapterMenu.isGroupDividerEnabled() && groupId != (i + (-1) >= 0 ? getItem(i + (-1)).getGroupId() : groupId))
        MenuView.ItemView itemView = (MenuView.ItemView) viewInflate
        if (this.mForceShowIcon) {
            ((ListMenuItemView) viewInflate).setForceShowIcon(true)
        }
        itemView.initialize(getItem(i), 0)
        return viewInflate
    }

    @Override // android.widget.BaseAdapter
    fun notifyDataSetChanged() {
        findExpandedIndex()
        super.notifyDataSetChanged()
    }

    fun setForceShowIcon(Boolean z) {
        this.mForceShowIcon = z
    }
}
