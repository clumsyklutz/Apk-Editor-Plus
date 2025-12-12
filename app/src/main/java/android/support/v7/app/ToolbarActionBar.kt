package android.support.v7.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import androidx.core.view.ViewCompat
import android.support.v7.app.ActionBar
import android.support.v7.view.WindowCallbackWrapper
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPresenter
import android.support.v7.widget.DecorToolbar
import androidx.appcompat.widget.Toolbar
import android.support.v7.widget.ToolbarWidgetWrapper
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SpinnerAdapter
import java.util.ArrayList

class ToolbarActionBar extends ActionBar {
    DecorToolbar mDecorToolbar
    private Boolean mLastMenuVisibility
    private Boolean mMenuCallbackSet
    Boolean mToolbarMenuPrepared
    Window.Callback mWindowCallback
    private ArrayList mMenuVisibilityListeners = ArrayList()
    private val mMenuInvalidator = Runnable() { // from class: android.support.v7.app.ToolbarActionBar.1
        @Override // java.lang.Runnable
        fun run() {
            ToolbarActionBar.this.populateOptionsMenu()
        }
    }
    private final Toolbar.OnMenuItemClickListener mMenuClicker = new Toolbar.OnMenuItemClickListener() { // from class: android.support.v7.app.ToolbarActionBar.2
        @Override // android.support.v7.widget.Toolbar.OnMenuItemClickListener
        fun onMenuItemClick(MenuItem menuItem) {
            return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, menuItem)
        }
    }

    final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private Boolean mClosingActionMenu

        ActionMenuPresenterCallback() {
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public final Unit onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
            if (this.mClosingActionMenu) {
                return
            }
            this.mClosingActionMenu = true
            ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus()
            if (ToolbarActionBar.this.mWindowCallback != null) {
                ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, menuBuilder)
            }
            this.mClosingActionMenu = false
        }

        @Override // android.support.v7.view.menu.MenuPresenter.Callback
        public final Boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.mWindowCallback == null) {
                return false
            }
            ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, menuBuilder)
            return true
        }
    }

    final class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public final Boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return false
        }

        @Override // android.support.v7.view.menu.MenuBuilder.Callback
        public final Unit onMenuModeChange(MenuBuilder menuBuilder) {
            if (ToolbarActionBar.this.mWindowCallback != null) {
                if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
                    ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, menuBuilder)
                } else if (ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, menuBuilder)) {
                    ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, menuBuilder)
                }
            }
        }
    }

    class ToolbarCallbackWrapper extends WindowCallbackWrapper {
        constructor(Window.Callback callback) {
            super(callback)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onCreatePanelView(Int i) {
            return i == 0 ? View(ToolbarActionBar.this.mDecorToolbar.getContext()) : super.onCreatePanelView(i)
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        fun onPreparePanel(Int i, View view, Menu menu) {
            Boolean zOnPreparePanel = super.onPreparePanel(i, view, menu)
            if (zOnPreparePanel && !ToolbarActionBar.this.mToolbarMenuPrepared) {
                ToolbarActionBar.this.mDecorToolbar.setMenuPrepared()
                ToolbarActionBar.this.mToolbarMenuPrepared = true
            }
            return zOnPreparePanel
        }
    }

    ToolbarActionBar(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        this.mDecorToolbar = ToolbarWidgetWrapper(toolbar, false)
        this.mWindowCallback = ToolbarCallbackWrapper(callback)
        this.mDecorToolbar.setWindowCallback(this.mWindowCallback)
        toolbar.setOnMenuItemClickListener(this.mMenuClicker)
        this.mDecorToolbar.setWindowTitle(charSequence)
    }

    private fun getMenu() {
        if (!this.mMenuCallbackSet) {
            this.mDecorToolbar.setMenuCallbacks(ActionMenuPresenterCallback(), MenuBuilderCallback())
            this.mMenuCallbackSet = true
        }
        return this.mDecorToolbar.getMenu()
    }

    @Override // android.support.v7.app.ActionBar
    fun addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener)
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab, Int i) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab, Int i, Boolean z) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun addTab(ActionBar.Tab tab, Boolean z) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun closeOptionsMenu() {
        return this.mDecorToolbar.hideOverflowMenu()
    }

    @Override // android.support.v7.app.ActionBar
    fun collapseActionView() {
        if (!this.mDecorToolbar.hasExpandedActionView()) {
            return false
        }
        this.mDecorToolbar.collapseActionView()
        return true
    }

    @Override // android.support.v7.app.ActionBar
    fun dispatchMenuVisibilityChanged(Boolean z) {
        if (z == this.mLastMenuVisibility) {
            return
        }
        this.mLastMenuVisibility = z
        Int size = this.mMenuVisibilityListeners.size()
        for (Int i = 0; i < size; i++) {
            ((ActionBar.OnMenuVisibilityListener) this.mMenuVisibilityListeners.get(i)).onMenuVisibilityChanged(z)
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun getCustomView() {
        return this.mDecorToolbar.getCustomView()
    }

    @Override // android.support.v7.app.ActionBar
    fun getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions()
    }

    @Override // android.support.v7.app.ActionBar
    fun getElevation() {
        return ViewCompat.getElevation(this.mDecorToolbar.getViewGroup())
    }

    @Override // android.support.v7.app.ActionBar
    fun getHeight() {
        return this.mDecorToolbar.getHeight()
    }

    @Override // android.support.v7.app.ActionBar
    fun getNavigationItemCount() {
        return 0
    }

    @Override // android.support.v7.app.ActionBar
    fun getNavigationMode() {
        return 0
    }

    @Override // android.support.v7.app.ActionBar
    fun getSelectedNavigationIndex() {
        return -1
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun getSubtitle() {
        return this.mDecorToolbar.getSubtitle()
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab getTabAt(Int i) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun getTabCount() {
        return 0
    }

    @Override // android.support.v7.app.ActionBar
    fun getThemedContext() {
        return this.mDecorToolbar.getContext()
    }

    @Override // android.support.v7.app.ActionBar
    fun getTitle() {
        return this.mDecorToolbar.getTitle()
    }

    public Window.Callback getWrappedWindowCallback() {
        return this.mWindowCallback
    }

    @Override // android.support.v7.app.ActionBar
    fun hide() {
        this.mDecorToolbar.setVisibility(8)
    }

    @Override // android.support.v7.app.ActionBar
    fun invalidateOptionsMenu() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator)
        ViewCompat.postOnAnimation(this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator)
        return true
    }

    @Override // android.support.v7.app.ActionBar
    fun isShowing() {
        return this.mDecorToolbar.getVisibility() == 0
    }

    @Override // android.support.v7.app.ActionBar
    fun isTitleTruncated() {
        return super.isTitleTruncated()
    }

    @Override // android.support.v7.app.ActionBar
    public ActionBar.Tab newTab() {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
    }

    @Override // android.support.v7.app.ActionBar
    Unit onDestroy() {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator)
    }

    @Override // android.support.v7.app.ActionBar
    fun onKeyShortcut(Int i, KeyEvent keyEvent) {
        Menu menu = getMenu()
        if (menu == null) {
            return false
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1)
        return menu.performShortcut(i, keyEvent, 0)
    }

    @Override // android.support.v7.app.ActionBar
    fun onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            openOptionsMenu()
        }
        return true
    }

    @Override // android.support.v7.app.ActionBar
    fun openOptionsMenu() {
        return this.mDecorToolbar.showOverflowMenu()
    }

    Unit populateOptionsMenu() {
        Menu menu = getMenu()
        MenuBuilder menuBuilder = menu is MenuBuilder ? (MenuBuilder) menu : null
        if (menuBuilder != null) {
            menuBuilder.stopDispatchingItemsChanged()
        }
        try {
            menu.clear()
            if (!this.mWindowCallback.onCreatePanelMenu(0, menu) || !this.mWindowCallback.onPreparePanel(0, null, menu)) {
                menu.clear()
            }
        } finally {
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged()
            }
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun removeAllTabs() {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener)
    }

    @Override // android.support.v7.app.ActionBar
    fun removeTab(ActionBar.Tab tab) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun removeTabAt(Int i) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup()
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false
        }
        viewGroup.requestFocus()
        return true
    }

    @Override // android.support.v7.app.ActionBar
    fun selectTab(ActionBar.Tab tab) {
        throw UnsupportedOperationException("Tabs are not supported in toolbar action bars")
    }

    @Override // android.support.v7.app.ActionBar
    fun setBackgroundDrawable(@Nullable Drawable drawable) {
        this.mDecorToolbar.setBackgroundDrawable(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setCustomView(Int i) {
        setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(i, this.mDecorToolbar.getViewGroup(), false))
    }

    @Override // android.support.v7.app.ActionBar
    fun setCustomView(View view) {
        setCustomView(view, new ActionBar.LayoutParams(-2, -2))
    }

    @Override // android.support.v7.app.ActionBar
    fun setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        if (view != null) {
            view.setLayoutParams(layoutParams)
        }
        this.mDecorToolbar.setCustomView(view)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDefaultDisplayHomeAsUpEnabled(Boolean z) {
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayHomeAsUpEnabled(Boolean z) {
        setDisplayOptions(z ? 4 : 0, 4)
    }

    @Override // android.support.v7.app.ActionBar
    @SuppressLint({"WrongConstant"})
    fun setDisplayOptions(Int i) {
        setDisplayOptions(i, -1)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayOptions(Int i, Int i2) {
        this.mDecorToolbar.setDisplayOptions((this.mDecorToolbar.getDisplayOptions() & (i2 ^ (-1))) | (i & i2))
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayShowCustomEnabled(Boolean z) {
        setDisplayOptions(z ? 16 : 0, 16)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayShowHomeEnabled(Boolean z) {
        setDisplayOptions(z ? 2 : 0, 2)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayShowTitleEnabled(Boolean z) {
        setDisplayOptions(z ? 8 : 0, 8)
    }

    @Override // android.support.v7.app.ActionBar
    fun setDisplayUseLogoEnabled(Boolean z) {
        setDisplayOptions(z ? 1 : 0, 1)
    }

    @Override // android.support.v7.app.ActionBar
    fun setElevation(Float f) {
        ViewCompat.setElevation(this.mDecorToolbar.getViewGroup(), f)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeActionContentDescription(Int i) {
        this.mDecorToolbar.setNavigationContentDescription(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeActionContentDescription(CharSequence charSequence) {
        this.mDecorToolbar.setNavigationContentDescription(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeAsUpIndicator(Int i) {
        this.mDecorToolbar.setNavigationIcon(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeAsUpIndicator(Drawable drawable) {
        this.mDecorToolbar.setNavigationIcon(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setHomeButtonEnabled(Boolean z) {
    }

    @Override // android.support.v7.app.ActionBar
    fun setIcon(Int i) {
        this.mDecorToolbar.setIcon(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setIcon(Drawable drawable) {
        this.mDecorToolbar.setIcon(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, NavItemSelectedListener(onNavigationListener))
    }

    @Override // android.support.v7.app.ActionBar
    fun setLogo(Int i) {
        this.mDecorToolbar.setLogo(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setLogo(Drawable drawable) {
        this.mDecorToolbar.setLogo(drawable)
    }

    @Override // android.support.v7.app.ActionBar
    fun setNavigationMode(Int i) {
        if (i == 2) {
            throw IllegalArgumentException("Tabs not supported in this configuration")
        }
        this.mDecorToolbar.setNavigationMode(i)
    }

    @Override // android.support.v7.app.ActionBar
    fun setSelectedNavigationItem(Int i) {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(i)
                return
            default:
                throw IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode")
        }
    }

    @Override // android.support.v7.app.ActionBar
    fun setShowHideAnimationEnabled(Boolean z) {
    }

    @Override // android.support.v7.app.ActionBar
    fun setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.support.v7.app.ActionBar
    fun setStackedBackgroundDrawable(Drawable drawable) {
    }

    @Override // android.support.v7.app.ActionBar
    fun setSubtitle(Int i) {
        this.mDecorToolbar.setSubtitle(i != 0 ? this.mDecorToolbar.getContext().getText(i) : null)
    }

    @Override // android.support.v7.app.ActionBar
    fun setSubtitle(CharSequence charSequence) {
        this.mDecorToolbar.setSubtitle(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun setTitle(Int i) {
        this.mDecorToolbar.setTitle(i != 0 ? this.mDecorToolbar.getContext().getText(i) : null)
    }

    @Override // android.support.v7.app.ActionBar
    fun setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence)
    }

    @Override // android.support.v7.app.ActionBar
    fun show() {
        this.mDecorToolbar.setVisibility(0)
    }
}
