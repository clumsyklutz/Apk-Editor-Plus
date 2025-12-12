package android.support.v7.widget

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.ListMenuItemView
import android.support.v7.view.menu.MenuAdapter
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.transition.Transition
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.HeaderViewListAdapter
import android.widget.ListAdapter
import android.widget.PopupWindow
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuPopupWindow extends ListPopupWindow implements MenuItemHoverListener {
    private static val TAG = "MenuPopupWindow"
    private static Method sSetTouchModalMethod
    private MenuItemHoverListener mHoverListener

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class MenuDropDownListView extends DropDownListView {
        final Int mAdvanceKey
        private MenuItemHoverListener mHoverListener
        private MenuItem mHoveredMenuItem
        final Int mRetreatKey

        constructor(Context context, Boolean z) {
            super(context, z)
            Configuration configuration = context.getResources().getConfiguration()
            if (Build.VERSION.SDK_INT < 17 || 1 != configuration.getLayoutDirection()) {
                this.mAdvanceKey = 22
                this.mRetreatKey = 21
            } else {
                this.mAdvanceKey = 21
                this.mRetreatKey = 22
            }
        }

        fun clearSelection() {
            setSelection(-1)
        }

        @Override // android.support.v7.widget.DropDownListView, android.view.ViewGroup, android.view.View
        public /* bridge */ /* synthetic */ Boolean hasFocus() {
            return super.hasFocus()
        }

        @Override // android.support.v7.widget.DropDownListView, android.view.View
        public /* bridge */ /* synthetic */ Boolean hasWindowFocus() {
            return super.hasWindowFocus()
        }

        @Override // android.support.v7.widget.DropDownListView, android.view.View
        public /* bridge */ /* synthetic */ Boolean isFocused() {
            return super.isFocused()
        }

        @Override // android.support.v7.widget.DropDownListView, android.view.View
        public /* bridge */ /* synthetic */ Boolean isInTouchMode() {
            return super.isInTouchMode()
        }

        @Override // android.support.v7.widget.DropDownListView
        public /* bridge */ /* synthetic */ Int lookForSelectablePosition(Int i, Boolean z) {
            return super.lookForSelectablePosition(i, z)
        }

        @Override // android.support.v7.widget.DropDownListView
        public /* bridge */ /* synthetic */ Int measureHeightOfChildrenCompat(Int i, Int i2, Int i3, Int i4, Int i5) {
            return super.measureHeightOfChildrenCompat(i, i2, i3, i4, i5)
        }

        @Override // android.support.v7.widget.DropDownListView
        public /* bridge */ /* synthetic */ Boolean onForwardedEvent(MotionEvent motionEvent, Int i) {
            return super.onForwardedEvent(motionEvent, i)
        }

        @Override // android.support.v7.widget.DropDownListView, android.view.View
        fun onHoverEvent(MotionEvent motionEvent) {
            Int headersCount
            MenuAdapter menuAdapter
            Int iPointToPosition
            Int i
            if (this.mHoverListener != null) {
                ListAdapter adapter = getAdapter()
                if (adapter is HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter
                    headersCount = headerViewListAdapter.getHeadersCount()
                    menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter()
                } else {
                    headersCount = 0
                    menuAdapter = (MenuAdapter) adapter
                }
                MenuItemImpl item = (motionEvent.getAction() == 10 || (iPointToPosition = pointToPosition((Int) motionEvent.getX(), (Int) motionEvent.getY())) == -1 || (i = iPointToPosition - headersCount) < 0 || i >= menuAdapter.getCount()) ? null : menuAdapter.getItem(i)
                MenuItem menuItem = this.mHoveredMenuItem
                if (menuItem != item) {
                    MenuBuilder adapterMenu = menuAdapter.getAdapterMenu()
                    if (menuItem != null) {
                        this.mHoverListener.onItemHoverExit(adapterMenu, menuItem)
                    }
                    this.mHoveredMenuItem = item
                    if (item != null) {
                        this.mHoverListener.onItemHoverEnter(adapterMenu, item)
                    }
                }
            }
            return super.onHoverEvent(motionEvent)
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
        fun onKeyDown(Int i, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView()
            if (listMenuItemView != null && i == this.mAdvanceKey) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId())
                }
                return true
            }
            if (listMenuItemView == null || i != this.mRetreatKey) {
                return super.onKeyDown(i, keyEvent)
            }
            setSelection(-1)
            ((MenuAdapter) getAdapter()).getAdapterMenu().close(false)
            return true
        }

        @Override // android.support.v7.widget.DropDownListView, android.widget.AbsListView, android.view.View
        public /* bridge */ /* synthetic */ Boolean onTouchEvent(MotionEvent motionEvent) {
            return super.onTouchEvent(motionEvent)
        }

        fun setHoverListener(MenuItemHoverListener menuItemHoverListener) {
            this.mHoverListener = menuItemHoverListener
        }

        @Override // android.support.v7.widget.DropDownListView, android.widget.AbsListView
        public /* bridge */ /* synthetic */ Unit setSelector(Drawable drawable) {
            super.setSelector(drawable)
        }
    }

    static {
        try {
            sSetTouchModalMethod = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE)
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Could not find method setTouchModal() on PopupWindow. Oh well.")
        }
    }

    constructor(Context context, AttributeSet attributeSet, Int i, Int i2) {
        super(context, attributeSet, i, i2)
    }

    @Override // android.support.v7.widget.ListPopupWindow
    DropDownListView createDropDownListView(Context context, Boolean z) {
        MenuDropDownListView menuDropDownListView = MenuDropDownListView(context, z)
        menuDropDownListView.setHoverListener(this)
        return menuDropDownListView
    }

    @Override // android.support.v7.widget.MenuItemHoverListener
    fun onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverEnter(menuBuilder, menuItem)
        }
    }

    @Override // android.support.v7.widget.MenuItemHoverListener
    fun onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        if (this.mHoverListener != null) {
            this.mHoverListener.onItemHoverExit(menuBuilder, menuItem)
        }
    }

    fun setEnterTransition(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mPopup.setEnterTransition((Transition) obj)
        }
    }

    fun setExitTransition(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mPopup.setExitTransition((Transition) obj)
        }
    }

    fun setHoverListener(MenuItemHoverListener menuItemHoverListener) {
        this.mHoverListener = menuItemHoverListener
    }

    fun setTouchModal(Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (sSetTouchModalMethod != null) {
            try {
                sSetTouchModalMethod.invoke(this.mPopup, Boolean.valueOf(z))
            } catch (Exception e) {
                Log.i(TAG, "Could not invoke setTouchModal() on PopupWindow. Oh well.")
            }
        }
    }
}
