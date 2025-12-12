package android.support.v7.view.menu

import android.content.Context
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.view.menu.MenuView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
abstract class BaseMenuPresenter implements MenuPresenter {
    private MenuPresenter.Callback mCallback
    protected Context mContext
    private Int mId
    protected LayoutInflater mInflater
    private Int mItemLayoutRes
    protected MenuBuilder mMenu
    private Int mMenuLayoutRes
    protected MenuView mMenuView
    protected Context mSystemContext
    protected LayoutInflater mSystemInflater

    constructor(Context context, Int i, Int i2) {
        this.mSystemContext = context
        this.mSystemInflater = LayoutInflater.from(context)
        this.mMenuLayoutRes = i
        this.mItemLayoutRes = i2
    }

    protected fun addItemView(View view, Int i) {
        ViewGroup viewGroup = (ViewGroup) view.getParent()
        if (viewGroup != null) {
            viewGroup.removeView(view)
        }
        ((ViewGroup) this.mMenuView).addView(view, i)
    }

    public abstract Unit bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView)

    @Override // android.support.v7.view.menu.MenuPresenter
    fun collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false
    }

    public MenuView.ItemView createItemView(ViewGroup viewGroup) {
        return (MenuView.ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, viewGroup, false)
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false
    }

    protected fun filterLeftoverView(ViewGroup viewGroup, Int i) {
        viewGroup.removeViewAt(i)
        return true
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun flagActionItems() {
        return false
    }

    public MenuPresenter.Callback getCallback() {
        return this.mCallback
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun getId() {
        return this.mId
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        MenuView.ItemView itemViewCreateItemView = view is MenuView.ItemView ? (MenuView.ItemView) view : createItemView(viewGroup)
        bindItemView(menuItemImpl, itemViewCreateItemView)
        return (View) itemViewCreateItemView
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, viewGroup, false)
            this.mMenuView.initialize(this.mMenu)
            updateMenuView(true)
        }
        return this.mMenuView
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun initForMenu(Context context, MenuBuilder menuBuilder) {
        this.mContext = context
        this.mInflater = LayoutInflater.from(this.mContext)
        this.mMenu = menuBuilder
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, z)
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (this.mCallback != null) {
            return this.mCallback.onOpenSubMenu(subMenuBuilder)
        }
        return false
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback
    }

    fun setId(Int i) {
        this.mId = i
    }

    fun shouldIncludeItem(Int i, MenuItemImpl menuItemImpl) {
        return true
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v7.view.menu.MenuPresenter
    fun updateMenuView(Boolean z) {
        Int i
        Int i2
        ViewGroup viewGroup = (ViewGroup) this.mMenuView
        if (viewGroup == null) {
            return
        }
        if (this.mMenu != null) {
            this.mMenu.flagActionItems()
            ArrayList visibleItems = this.mMenu.getVisibleItems()
            Int size = visibleItems.size()
            Int i3 = 0
            i = 0
            while (i3 < size) {
                MenuItemImpl menuItemImpl = (MenuItemImpl) visibleItems.get(i3)
                if (shouldIncludeItem(i, menuItemImpl)) {
                    View childAt = viewGroup.getChildAt(i)
                    MenuItemImpl itemData = childAt is MenuView.ItemView ? ((MenuView.ItemView) childAt).getItemData() : null
                    View itemView = getItemView(menuItemImpl, childAt, viewGroup)
                    if (menuItemImpl != itemData) {
                        itemView.setPressed(false)
                        itemView.jumpDrawablesToCurrentState()
                    }
                    if (itemView != childAt) {
                        addItemView(itemView, i)
                    }
                    i2 = i + 1
                } else {
                    i2 = i
                }
                i3++
                i = i2
            }
        } else {
            i = 0
        }
        while (i < viewGroup.getChildCount()) {
            if (!filterLeftoverView(viewGroup, i)) {
                i++
            }
        }
    }
}
