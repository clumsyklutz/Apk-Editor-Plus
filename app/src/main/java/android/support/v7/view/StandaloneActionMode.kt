package android.support.v7.view

import android.content.Context
import android.support.annotation.RestrictTo
import android.support.v7.view.ActionMode
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.view.menu.SubMenuBuilder
import android.support.v7.widget.ActionBarContextView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import java.lang.ref.WeakReference

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    private ActionMode.Callback mCallback
    private Context mContext
    private ActionBarContextView mContextView
    private WeakReference mCustomView
    private Boolean mFinished
    private Boolean mFocusable
    private MenuBuilder mMenu

    constructor(Context context, ActionBarContextView actionBarContextView, ActionMode.Callback callback, Boolean z) {
        this.mContext = context
        this.mContextView = actionBarContextView
        this.mCallback = callback
        this.mMenu = MenuBuilder(actionBarContextView.getContext()).setDefaultShowAsAction(1)
        this.mMenu.setCallback(this)
        this.mFocusable = z
    }

    @Override // android.support.v7.view.ActionMode
    fun finish() {
        if (this.mFinished) {
            return
        }
        this.mFinished = true
        this.mContextView.sendAccessibilityEvent(32)
        this.mCallback.onDestroyActionMode(this)
    }

    @Override // android.support.v7.view.ActionMode
    fun getCustomView() {
        if (this.mCustomView != null) {
            return (View) this.mCustomView.get()
        }
        return null
    }

    @Override // android.support.v7.view.ActionMode
    fun getMenu() {
        return this.mMenu
    }

    @Override // android.support.v7.view.ActionMode
    fun getMenuInflater() {
        return SupportMenuInflater(this.mContextView.getContext())
    }

    @Override // android.support.v7.view.ActionMode
    fun getSubtitle() {
        return this.mContextView.getSubtitle()
    }

    @Override // android.support.v7.view.ActionMode
    fun getTitle() {
        return this.mContextView.getTitle()
    }

    @Override // android.support.v7.view.ActionMode
    fun invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu)
    }

    @Override // android.support.v7.view.ActionMode
    fun isTitleOptional() {
        return this.mContextView.isTitleOptional()
    }

    @Override // android.support.v7.view.ActionMode
    fun isUiFocusable() {
        return this.mFocusable
    }

    fun onCloseMenu(MenuBuilder menuBuilder, Boolean z) {
    }

    fun onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    fun onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return this.mCallback.onActionItemClicked(this, menuItem)
    }

    @Override // android.support.v7.view.menu.MenuBuilder.Callback
    fun onMenuModeChange(MenuBuilder menuBuilder) {
        invalidate()
        this.mContextView.showOverflowMenu()
    }

    fun onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (subMenuBuilder.hasVisibleItems()) {
            MenuPopupHelper(this.mContextView.getContext(), subMenuBuilder).show()
        }
        return true
    }

    @Override // android.support.v7.view.ActionMode
    fun setCustomView(View view) {
        this.mContextView.setCustomView(view)
        this.mCustomView = view != null ? WeakReference(view) : null
    }

    @Override // android.support.v7.view.ActionMode
    fun setSubtitle(Int i) {
        setSubtitle(this.mContext.getString(i))
    }

    @Override // android.support.v7.view.ActionMode
    fun setSubtitle(CharSequence charSequence) {
        this.mContextView.setSubtitle(charSequence)
    }

    @Override // android.support.v7.view.ActionMode
    fun setTitle(Int i) {
        setTitle(this.mContext.getString(i))
    }

    @Override // android.support.v7.view.ActionMode
    fun setTitle(CharSequence charSequence) {
        this.mContextView.setTitle(charSequence)
    }

    @Override // android.support.v7.view.ActionMode
    fun setTitleOptionalHint(Boolean z) {
        super.setTitleOptionalHint(z)
        this.mContextView.setTitleOptional(z)
    }
}
