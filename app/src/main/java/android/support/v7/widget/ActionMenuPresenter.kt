package android.support.v7.widget

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.view.ActionProvider
import androidx.core.view.GravityCompat
import androidx.appcompat.R
import android.support.v7.view.ActionBarPolicy
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.view.menu.BaseMenuPresenter
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.view.menu.MenuView
import android.support.v7.view.menu.ShowableListMenu
import android.support.v7.view.menu.SubMenuBuilder
import android.support.v7.widget.ActionMenuView
import android.util.SparseBooleanArray
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
    private static val TAG = "ActionMenuPresenter"
    private final SparseBooleanArray mActionButtonGroups
    ActionButtonSubmenu mActionButtonPopup
    private Int mActionItemWidthLimit
    private Boolean mExpandedActionViewsExclusive
    private Int mMaxItems
    private Boolean mMaxItemsSet
    private Int mMinCellSize
    Int mOpenSubMenuId
    OverflowMenuButton mOverflowButton
    OverflowPopup mOverflowPopup
    private Drawable mPendingOverflowIcon
    private Boolean mPendingOverflowIconSet
    private ActionMenuPopupCallback mPopupCallback
    final PopupPresenterCallback mPopupPresenterCallback
    OpenOverflowRunnable mPostedOpenRunnable
    private Boolean mReserveOverflow
    private Boolean mReserveOverflowSet
    private View mScrapActionButtonView
    private Boolean mStrictWidthLimit
    private Int mWidthLimit
    private Boolean mWidthLimitSet

    class ActionButtonSubmenu extends MenuPopupHelper {
        constructor(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle)
            if (!((MenuItemImpl) subMenuBuilder.getItem()).isActionButton()) {
                setAnchorView(ActionMenuPresenter.this.mOverflowButton == null ? (View) ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mOverflowButton)
            }
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback)
        }

        @Override // android.support.v7.view.menu.MenuPopupHelper
        protected fun onDismiss() {
            ActionMenuPresenter.this.mActionButtonPopup = null
            ActionMenuPresenter.this.mOpenSubMenuId = 0
            super.onDismiss()
        }
    }

    class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
        ActionMenuPopupCallback() {
        }

        @Override // android.support.v7.view.menu.ActionMenuItemView.PopupCallback
        fun getPopup() {
            if (ActionMenuPresenter.this.mActionButtonPopup != null) {
                return ActionMenuPresenter.this.mActionButtonPopup.getPopup()
            }
            return null
        }
    }

    class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup

        constructor(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup
        }

        @Override // java.lang.Runnable
        fun run() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode()
            }
            View view = (View) ActionMenuPresenter.this.mMenuView
            if (view != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null
        }
    }

    class OverflowMenuButton extends AppCompatImageView implements ActionMenuView.ActionMenuChildView {
        private final Array<Float> mTempPts

        constructor(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle)
            this.mTempPts = new Float[2]
            setClickable(true)
            setFocusable(true)
            setVisibility(0)
            setEnabled(true)
            TooltipCompat.setTooltipText(this, getContentDescription())
            setOnTouchListener(ForwardingListener(this) { // from class: android.support.v7.widget.ActionMenuPresenter.OverflowMenuButton.1
                @Override // android.support.v7.widget.ForwardingListener
                fun getPopup() {
                    if (ActionMenuPresenter.this.mOverflowPopup == null) {
                        return null
                    }
                    return ActionMenuPresenter.this.mOverflowPopup.getPopup()
                }

                @Override // android.support.v7.widget.ForwardingListener
                fun onForwardingStarted() {
                    ActionMenuPresenter.this.showOverflowMenu()
                    return true
                }

                @Override // android.support.v7.widget.ForwardingListener
                fun onForwardingStopped() {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false
                    }
                    ActionMenuPresenter.this.hideOverflowMenu()
                    return true
                }
            })
        }

        @Override // android.support.v7.widget.ActionMenuView.ActionMenuChildView
        fun needsDividerAfter() {
            return false
        }

        @Override // android.support.v7.widget.ActionMenuView.ActionMenuChildView
        fun needsDividerBefore() {
            return false
        }

        @Override // android.view.View
        fun performClick() {
            if (!super.performClick()) {
                playSoundEffect(0)
                ActionMenuPresenter.this.showOverflowMenu()
            }
            return true
        }

        @Override // android.widget.ImageView
        protected fun setFrame(Int i, Int i2, Int i3, Int i4) {
            Boolean frame = super.setFrame(i, i2, i3, i4)
            Drawable drawable = getDrawable()
            Drawable background = getBackground()
            if (drawable != null && background != null) {
                Int width = getWidth()
                Int height = getHeight()
                Int iMax = Math.max(width, height) / 2
                Int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2
                Int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2
                DrawableCompat.setHotspotBounds(background, paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax)
            }
            return frame
        }
    }

    class OverflowPopup extends MenuPopupHelper {
        constructor(Context context, MenuBuilder menuBuilder, View view, Boolean z) {
            super(context, menuBuilder, view, z, R.attr.actionOverflowMenuStyle)
            setGravity(GravityCompat.END)
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback)
        }

        @Override // android.support.v7.view.menu.MenuPopupHelper
        protected fun onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close()
            }
            ActionMenuPresenter.this.mOverflowPopup = null
            super.onDismiss()
        }
    }

    class PopupPresenterCallback implements MenuPresenter.Callback {
        PopupPresenterCallback() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
            if (menuBuilder is SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false)
            }
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback()
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z)
            }
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        fun onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) menuBuilder).getItem().getItemId()
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback()
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder)
            }
            return false
        }
    }

    class SavedState implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v7.widget.ActionMenuPresenter.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        public Int openSubMenuId

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt()
        }

        @Override // android.os.Parcelable
        fun describeContents() {
            return 0
        }

        @Override // android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            parcel.writeInt(this.openSubMenuId)
        }
    }

    constructor(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout)
        this.mActionButtonGroups = SparseBooleanArray()
        this.mPopupPresenterCallback = PopupPresenterCallback()
    }

    /* JADX WARN: Multi-variable type inference failed */
    private fun findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView
        if (viewGroup == null) {
            return null
        }
        Int childCount = viewGroup.getChildCount()
        for (Int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i)
            if ((childAt is MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == menuItem) {
                return childAt
            }
        }
        return null
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter
    fun bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl, 0)
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView
        actionMenuItemView.setItemInvoker((ActionMenuView) this.mMenuView)
        if (this.mPopupCallback == null) {
            this.mPopupCallback = ActionMenuPopupCallback()
        }
        actionMenuItemView.setPopupCallback(this.mPopupCallback)
    }

    fun dismissPopupMenus() {
        return hideOverflowMenu() | hideSubMenus()
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter
    fun filterLeftoverView(ViewGroup viewGroup, Int i) {
        if (viewGroup.getChildAt(i) == this.mOverflowButton) {
            return false
        }
        return super.filterLeftoverView(viewGroup, i)
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter, android.support.v7.view.menu.MenuPresenter
    fun flagActionItems() {
        Int size
        ArrayList arrayList
        Int i
        Int i2
        Int iMeasureChildForCells
        Int i3
        Int measuredWidth
        Int i4
        Boolean z
        Int i5
        Int i6
        if (this.mMenu != null) {
            ArrayList visibleItems = this.mMenu.getVisibleItems()
            size = visibleItems.size()
            arrayList = visibleItems
        } else {
            size = 0
            arrayList = null
        }
        Int i7 = this.mMaxItems
        Int i8 = this.mActionItemWidthLimit
        Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0)
        ViewGroup viewGroup = (ViewGroup) this.mMenuView
        Int i9 = 0
        Int i10 = 0
        Boolean z2 = false
        Int i11 = 0
        while (i11 < size) {
            MenuItemImpl menuItemImpl = (MenuItemImpl) arrayList.get(i11)
            if (menuItemImpl.requiresActionButton()) {
                i9++
            } else if (menuItemImpl.requestsActionButton()) {
                i10++
            } else {
                z2 = true
            }
            i11++
            i7 = (this.mExpandedActionViewsExclusive && menuItemImpl.isActionViewExpanded()) ? 0 : i7
        }
        if (this.mReserveOverflow && (z2 || i9 + i10 > i7)) {
            i7--
        }
        Int i12 = i7 - i9
        SparseBooleanArray sparseBooleanArray = this.mActionButtonGroups
        sparseBooleanArray.clear()
        if (this.mStrictWidthLimit) {
            Int i13 = i8 / this.mMinCellSize
            i = ((i8 % this.mMinCellSize) / i13) + this.mMinCellSize
            i2 = i13
        } else {
            i = 0
            i2 = 0
        }
        Int i14 = 0
        Int i15 = 0
        Int i16 = i2
        while (i15 < size) {
            MenuItemImpl menuItemImpl2 = (MenuItemImpl) arrayList.get(i15)
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = getItemView(menuItemImpl2, this.mScrapActionButtonView, viewGroup)
                if (this.mScrapActionButtonView == null) {
                    this.mScrapActionButtonView = itemView
                }
                if (this.mStrictWidthLimit) {
                    iMeasureChildForCells = i16 - ActionMenuView.measureChildForCells(itemView, i, i16, iMakeMeasureSpec, 0)
                } else {
                    itemView.measure(iMakeMeasureSpec, iMakeMeasureSpec)
                    iMeasureChildForCells = i16
                }
                measuredWidth = itemView.getMeasuredWidth()
                Int i17 = i8 - measuredWidth
                if (i14 != 0) {
                    measuredWidth = i14
                }
                Int groupId = menuItemImpl2.getGroupId()
                if (groupId != 0) {
                    sparseBooleanArray.put(groupId, true)
                }
                menuItemImpl2.setIsActionButton(true)
                i3 = i17
                i4 = i12
            } else if (menuItemImpl2.requestsActionButton()) {
                Int groupId2 = menuItemImpl2.getGroupId()
                Boolean z3 = sparseBooleanArray.get(groupId2)
                Boolean z4 = (i12 > 0 || z3) && i8 > 0 && (!this.mStrictWidthLimit || i16 > 0)
                if (z4) {
                    View itemView2 = getItemView(menuItemImpl2, this.mScrapActionButtonView, viewGroup)
                    if (this.mScrapActionButtonView == null) {
                        this.mScrapActionButtonView = itemView2
                    }
                    if (this.mStrictWidthLimit) {
                        Int iMeasureChildForCells2 = ActionMenuView.measureChildForCells(itemView2, i, i16, iMakeMeasureSpec, 0)
                        i16 -= iMeasureChildForCells2
                        if (iMeasureChildForCells2 == 0) {
                            z4 = false
                        }
                    } else {
                        itemView2.measure(iMakeMeasureSpec, iMakeMeasureSpec)
                    }
                    Int measuredWidth2 = itemView2.getMeasuredWidth()
                    i8 -= measuredWidth2
                    if (i14 == 0) {
                        i14 = measuredWidth2
                    }
                    if (this.mStrictWidthLimit) {
                        z = z4 & (i8 >= 0)
                        i5 = i16
                    } else {
                        z = z4 & (i8 + i14 > 0)
                        i5 = i16
                    }
                } else {
                    z = z4
                    i5 = i16
                }
                if (z && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true)
                    i6 = i12
                } else if (z3) {
                    sparseBooleanArray.put(groupId2, false)
                    Int i18 = i12
                    for (Int i19 = 0; i19 < i15; i19++) {
                        MenuItemImpl menuItemImpl3 = (MenuItemImpl) arrayList.get(i19)
                        if (menuItemImpl3.getGroupId() == groupId2) {
                            if (menuItemImpl3.isActionButton()) {
                                i18++
                            }
                            menuItemImpl3.setIsActionButton(false)
                        }
                    }
                    i6 = i18
                } else {
                    i6 = i12
                }
                if (z) {
                    i6--
                }
                menuItemImpl2.setIsActionButton(z)
                measuredWidth = i14
                i3 = i8
                i4 = i6
                iMeasureChildForCells = i5
            } else {
                menuItemImpl2.setIsActionButton(false)
                iMeasureChildForCells = i16
                i3 = i8
                measuredWidth = i14
                i4 = i12
            }
            i15++
            i8 = i3
            i12 = i4
            i14 = measuredWidth
            i16 = iMeasureChildForCells
        }
        return true
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter
    fun getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView()
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup)
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0)
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams()
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams))
        }
        return actionView
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter, android.support.v7.view.menu.MenuPresenter
    fun getMenuView(ViewGroup viewGroup) {
        MenuView menuView = this.mMenuView
        MenuView menuView2 = super.getMenuView(viewGroup)
        if (menuView != menuView2) {
            ((ActionMenuView) menuView2).setPresenter(this)
        }
        return menuView2
    }

    fun getOverflowIcon() {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable()
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon
        }
        return null
    }

    fun hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable)
            this.mPostedOpenRunnable = null
            return true
        }
        OverflowPopup overflowPopup = this.mOverflowPopup
        if (overflowPopup == null) {
            return false
        }
        overflowPopup.dismiss()
        return true
    }

    fun hideSubMenus() {
        if (this.mActionButtonPopup == null) {
            return false
        }
        this.mActionButtonPopup.dismiss()
        return true
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter, android.support.v7.view.menu.MenuPresenter
    fun initForMenu(@NonNull Context context, @Nullable MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder)
        Resources resources = context.getResources()
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context)
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton()
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit()
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = actionBarPolicy.getMaxActionButtons()
        }
        Int measuredWidth = this.mWidthLimit
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = OverflowMenuButton(this.mSystemContext)
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon)
                    this.mPendingOverflowIcon = null
                    this.mPendingOverflowIconSet = false
                }
                Int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0)
                this.mOverflowButton.measure(iMakeMeasureSpec, iMakeMeasureSpec)
            }
            measuredWidth -= this.mOverflowButton.getMeasuredWidth()
        } else {
            this.mOverflowButton = null
        }
        this.mActionItemWidthLimit = measuredWidth
        this.mMinCellSize = (Int) (56.0f * resources.getDisplayMetrics().density)
        this.mScrapActionButtonView = null
    }

    fun isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || isOverflowMenuShowing()
    }

    fun isOverflowMenuShowing() {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing()
    }

    fun isOverflowReserved() {
        return this.mReserveOverflow
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter, android.support.v7.view.menu.MenuPresenter
    fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
        dismissPopupMenus()
        super.onCloseMenu(menuBuilder, z)
    }

    fun onConfigurationChanged(Configuration configuration) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons()
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true)
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem
        if (parcelable is SavedState) {
            SavedState savedState = (SavedState) parcelable
            if (savedState.openSubMenuId <= 0 || (menuItemFindItem = this.mMenu.findItem(savedState.openSubMenuId)) == null) {
                return
            }
            onSubMenuSelected((SubMenuBuilder) menuItemFindItem.getSubMenu())
        }
    }

    @Override // android.support.v7.view.menu.MenuPresenter
    fun onSaveInstanceState() {
        SavedState savedState = SavedState()
        savedState.openSubMenuId = this.mOpenSubMenuId
        return savedState
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter, android.support.v7.view.menu.MenuPresenter
    fun onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        Boolean z
        if (!subMenuBuilder.hasVisibleItems()) {
            return false
        }
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (SubMenuBuilder) subMenuBuilder2.getParentMenu()
        }
        View viewFindViewForItem = findViewForItem(subMenuBuilder2.getItem())
        if (viewFindViewForItem == null) {
            return false
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId()
        Int size = subMenuBuilder.size()
        Int i = 0
        while (true) {
            if (i >= size) {
                z = false
                break
            }
            MenuItem item = subMenuBuilder.getItem(i)
            if (item.isVisible() && item.getIcon() != null) {
                z = true
                break
            }
            i++
        }
        this.mActionButtonPopup = ActionButtonSubmenu(this.mContext, subMenuBuilder, viewFindViewForItem)
        this.mActionButtonPopup.setForceShowIcon(z)
        this.mActionButtonPopup.show()
        super.onSubMenuSelected(subMenuBuilder)
        return true
    }

    @Override // android.support.v4.view.ActionProvider.SubUiVisibilityListener
    fun onSubUiVisibilityChanged(Boolean z) {
        if (z) {
            super.onSubMenuSelected(null)
        } else if (this.mMenu != null) {
            this.mMenu.close(false)
        }
    }

    fun setExpandedActionViewsExclusive(Boolean z) {
        this.mExpandedActionViewsExclusive = z
    }

    fun setItemLimit(Int i) {
        this.mMaxItems = i
        this.mMaxItemsSet = true
    }

    fun setMenuView(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView
        actionMenuView.initialize(this.mMenu)
    }

    fun setOverflowIcon(Drawable drawable) {
        if (this.mOverflowButton != null) {
            this.mOverflowButton.setImageDrawable(drawable)
        } else {
            this.mPendingOverflowIconSet = true
            this.mPendingOverflowIcon = drawable
        }
    }

    fun setReserveOverflow(Boolean z) {
        this.mReserveOverflow = z
        this.mReserveOverflowSet = true
    }

    fun setWidthLimit(Int i, Boolean z) {
        this.mWidthLimit = i
        this.mStrictWidthLimit = z
        this.mWidthLimitSet = true
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter
    fun shouldIncludeItem(Int i, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton()
    }

    fun showOverflowMenu() {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false
        }
        this.mPostedOpenRunnable = OpenOverflowRunnable(OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true))
        ((View) this.mMenuView).post(this.mPostedOpenRunnable)
        super.onSubMenuSelected(null)
        return true
    }

    @Override // android.support.v7.view.menu.BaseMenuPresenter, android.support.v7.view.menu.MenuPresenter
    fun updateMenuView(Boolean z) {
        Boolean z2 = false
        super.updateMenuView(z)
        ((View) this.mMenuView).requestLayout()
        if (this.mMenu != null) {
            ArrayList actionItems = this.mMenu.getActionItems()
            Int size = actionItems.size()
            for (Int i = 0; i < size; i++) {
                ActionProvider supportActionProvider = ((MenuItemImpl) actionItems.get(i)).getSupportActionProvider()
                if (supportActionProvider != null) {
                    supportActionProvider.setSubUiVisibilityListener(this)
                }
            }
        }
        ArrayList nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null
        if (this.mReserveOverflow && nonActionItems != null) {
            Int size2 = nonActionItems.size()
            z2 = size2 == 1 ? !((MenuItemImpl) nonActionItems.get(0)).isActionViewExpanded() : size2 > 0
        }
        if (z2) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = OverflowMenuButton(this.mSystemContext)
            }
            ViewGroup viewGroup = (ViewGroup) this.mOverflowButton.getParent()
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.mOverflowButton)
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView
                actionMenuView.addView(this.mOverflowButton, actionMenuView.generateOverflowButtonLayoutParams())
            }
        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
            ((ViewGroup) this.mMenuView).removeView(this.mOverflowButton)
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow)
    }
}
