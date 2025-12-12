package android.support.v7.view.menu

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.RestrictTo
import android.support.v4.internal.view.SupportMenuItem
import android.support.v4.view.ActionProvider
import android.support.v7.view.CollapsibleActionView
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.widget.FrameLayout
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuItemWrapperICS extends BaseMenuWrapper implements MenuItem {
    static val LOG_TAG = "MenuItemWrapper"
    private Method mSetExclusiveCheckableMethod

    class ActionProviderWrapper extends ActionProvider {
        final android.view.ActionProvider mInner

        constructor(Context context, android.view.ActionProvider actionProvider) {
            super(context)
            this.mInner = actionProvider
        }

        @Override // android.support.v4.view.ActionProvider
        fun hasSubMenu() {
            return this.mInner.hasSubMenu()
        }

        @Override // android.support.v4.view.ActionProvider
        fun onCreateActionView() {
            return this.mInner.onCreateActionView()
        }

        @Override // android.support.v4.view.ActionProvider
        fun onPerformDefaultAction() {
            return this.mInner.onPerformDefaultAction()
        }

        @Override // android.support.v4.view.ActionProvider
        fun onPrepareSubMenu(SubMenu subMenu) {
            this.mInner.onPrepareSubMenu(MenuItemWrapperICS.this.getSubMenuWrapper(subMenu))
        }
    }

    class CollapsibleActionViewWrapper extends FrameLayout implements CollapsibleActionView {
        final android.view.CollapsibleActionView mWrappedView

        /* JADX WARN: Multi-variable type inference failed */
        CollapsibleActionViewWrapper(View view) {
            super(view.getContext())
            this.mWrappedView = (android.view.CollapsibleActionView) view
            addView(view)
        }

        View getWrappedView() {
            return (View) this.mWrappedView
        }

        @Override // android.support.v7.view.CollapsibleActionView
        fun onActionViewCollapsed() {
            this.mWrappedView.onActionViewCollapsed()
        }

        @Override // android.support.v7.view.CollapsibleActionView
        fun onActionViewExpanded() {
            this.mWrappedView.onActionViewExpanded()
        }
    }

    class OnActionExpandListenerWrapper extends BaseWrapper implements MenuItem.OnActionExpandListener {
        OnActionExpandListenerWrapper(MenuItem.OnActionExpandListener onActionExpandListener) {
            super(onActionExpandListener)
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        fun onMenuItemActionCollapse(MenuItem menuItem) {
            return ((MenuItem.OnActionExpandListener) this.mWrappedObject).onMenuItemActionCollapse(MenuItemWrapperICS.this.getMenuItemWrapper(menuItem))
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        fun onMenuItemActionExpand(MenuItem menuItem) {
            return ((MenuItem.OnActionExpandListener) this.mWrappedObject).onMenuItemActionExpand(MenuItemWrapperICS.this.getMenuItemWrapper(menuItem))
        }
    }

    class OnMenuItemClickListenerWrapper extends BaseWrapper implements MenuItem.OnMenuItemClickListener {
        OnMenuItemClickListenerWrapper(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            super(onMenuItemClickListener)
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        fun onMenuItemClick(MenuItem menuItem) {
            return ((MenuItem.OnMenuItemClickListener) this.mWrappedObject).onMenuItemClick(MenuItemWrapperICS.this.getMenuItemWrapper(menuItem))
        }
    }

    MenuItemWrapperICS(Context context, SupportMenuItem supportMenuItem) {
        super(context, supportMenuItem)
    }

    @Override // android.view.MenuItem
    fun collapseActionView() {
        return ((SupportMenuItem) this.mWrappedObject).collapseActionView()
    }

    ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider actionProvider) {
        return ActionProviderWrapper(this.mContext, actionProvider)
    }

    @Override // android.view.MenuItem
    fun expandActionView() {
        return ((SupportMenuItem) this.mWrappedObject).expandActionView()
    }

    @Override // android.view.MenuItem
    public android.view.ActionProvider getActionProvider() {
        ActionProvider supportActionProvider = ((SupportMenuItem) this.mWrappedObject).getSupportActionProvider()
        if (supportActionProvider is ActionProviderWrapper) {
            return ((ActionProviderWrapper) supportActionProvider).mInner
        }
        return null
    }

    @Override // android.view.MenuItem
    fun getActionView() {
        View actionView = ((SupportMenuItem) this.mWrappedObject).getActionView()
        return actionView is CollapsibleActionViewWrapper ? ((CollapsibleActionViewWrapper) actionView).getWrappedView() : actionView
    }

    @Override // android.view.MenuItem
    fun getAlphabeticModifiers() {
        return ((SupportMenuItem) this.mWrappedObject).getAlphabeticModifiers()
    }

    @Override // android.view.MenuItem
    fun getAlphabeticShortcut() {
        return ((SupportMenuItem) this.mWrappedObject).getAlphabeticShortcut()
    }

    @Override // android.view.MenuItem
    fun getContentDescription() {
        return ((SupportMenuItem) this.mWrappedObject).getContentDescription()
    }

    @Override // android.view.MenuItem
    fun getGroupId() {
        return ((SupportMenuItem) this.mWrappedObject).getGroupId()
    }

    @Override // android.view.MenuItem
    fun getIcon() {
        return ((SupportMenuItem) this.mWrappedObject).getIcon()
    }

    @Override // android.view.MenuItem
    fun getIconTintList() {
        return ((SupportMenuItem) this.mWrappedObject).getIconTintList()
    }

    @Override // android.view.MenuItem
    public PorterDuff.Mode getIconTintMode() {
        return ((SupportMenuItem) this.mWrappedObject).getIconTintMode()
    }

    @Override // android.view.MenuItem
    fun getIntent() {
        return ((SupportMenuItem) this.mWrappedObject).getIntent()
    }

    @Override // android.view.MenuItem
    fun getItemId() {
        return ((SupportMenuItem) this.mWrappedObject).getItemId()
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return ((SupportMenuItem) this.mWrappedObject).getMenuInfo()
    }

    @Override // android.view.MenuItem
    fun getNumericModifiers() {
        return ((SupportMenuItem) this.mWrappedObject).getNumericModifiers()
    }

    @Override // android.view.MenuItem
    fun getNumericShortcut() {
        return ((SupportMenuItem) this.mWrappedObject).getNumericShortcut()
    }

    @Override // android.view.MenuItem
    fun getOrder() {
        return ((SupportMenuItem) this.mWrappedObject).getOrder()
    }

    @Override // android.view.MenuItem
    fun getSubMenu() {
        return getSubMenuWrapper(((SupportMenuItem) this.mWrappedObject).getSubMenu())
    }

    @Override // android.view.MenuItem
    fun getTitle() {
        return ((SupportMenuItem) this.mWrappedObject).getTitle()
    }

    @Override // android.view.MenuItem
    fun getTitleCondensed() {
        return ((SupportMenuItem) this.mWrappedObject).getTitleCondensed()
    }

    @Override // android.view.MenuItem
    fun getTooltipText() {
        return ((SupportMenuItem) this.mWrappedObject).getTooltipText()
    }

    @Override // android.view.MenuItem
    fun hasSubMenu() {
        return ((SupportMenuItem) this.mWrappedObject).hasSubMenu()
    }

    @Override // android.view.MenuItem
    fun isActionViewExpanded() {
        return ((SupportMenuItem) this.mWrappedObject).isActionViewExpanded()
    }

    @Override // android.view.MenuItem
    fun isCheckable() {
        return ((SupportMenuItem) this.mWrappedObject).isCheckable()
    }

    @Override // android.view.MenuItem
    fun isChecked() {
        return ((SupportMenuItem) this.mWrappedObject).isChecked()
    }

    @Override // android.view.MenuItem
    fun isEnabled() {
        return ((SupportMenuItem) this.mWrappedObject).isEnabled()
    }

    @Override // android.view.MenuItem
    fun isVisible() {
        return ((SupportMenuItem) this.mWrappedObject).isVisible()
    }

    @Override // android.view.MenuItem
    fun setActionProvider(android.view.ActionProvider actionProvider) {
        ((SupportMenuItem) this.mWrappedObject).setSupportActionProvider(actionProvider != null ? createActionProviderWrapper(actionProvider) : null)
        return this
    }

    @Override // android.view.MenuItem
    fun setActionView(Int i) {
        ((SupportMenuItem) this.mWrappedObject).setActionView(i)
        View actionView = ((SupportMenuItem) this.mWrappedObject).getActionView()
        if (actionView is android.view.CollapsibleActionView) {
            ((SupportMenuItem) this.mWrappedObject).setActionView(CollapsibleActionViewWrapper(actionView))
        }
        return this
    }

    @Override // android.view.MenuItem
    fun setActionView(View view) {
        if (view is android.view.CollapsibleActionView) {
            view = CollapsibleActionViewWrapper(view)
        }
        ((SupportMenuItem) this.mWrappedObject).setActionView(view)
        return this
    }

    @Override // android.view.MenuItem
    fun setAlphabeticShortcut(Char c) {
        ((SupportMenuItem) this.mWrappedObject).setAlphabeticShortcut(c)
        return this
    }

    @Override // android.view.MenuItem
    fun setAlphabeticShortcut(Char c, Int i) {
        ((SupportMenuItem) this.mWrappedObject).setAlphabeticShortcut(c, i)
        return this
    }

    @Override // android.view.MenuItem
    fun setCheckable(Boolean z) {
        ((SupportMenuItem) this.mWrappedObject).setCheckable(z)
        return this
    }

    @Override // android.view.MenuItem
    fun setChecked(Boolean z) {
        ((SupportMenuItem) this.mWrappedObject).setChecked(z)
        return this
    }

    @Override // android.view.MenuItem
    fun setContentDescription(CharSequence charSequence) {
        ((SupportMenuItem) this.mWrappedObject).setContentDescription(charSequence)
        return this
    }

    @Override // android.view.MenuItem
    fun setEnabled(Boolean z) {
        ((SupportMenuItem) this.mWrappedObject).setEnabled(z)
        return this
    }

    fun setExclusiveCheckable(Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            if (this.mSetExclusiveCheckableMethod == null) {
                this.mSetExclusiveCheckableMethod = ((SupportMenuItem) this.mWrappedObject).getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE)
            }
            this.mSetExclusiveCheckableMethod.invoke(this.mWrappedObject, Boolean.valueOf(z))
        } catch (Exception e) {
            Log.w(LOG_TAG, "Error while calling setExclusiveCheckable", e)
        }
    }

    @Override // android.view.MenuItem
    fun setIcon(Int i) {
        ((SupportMenuItem) this.mWrappedObject).setIcon(i)
        return this
    }

    @Override // android.view.MenuItem
    fun setIcon(Drawable drawable) {
        ((SupportMenuItem) this.mWrappedObject).setIcon(drawable)
        return this
    }

    @Override // android.view.MenuItem
    fun setIconTintList(ColorStateList colorStateList) {
        ((SupportMenuItem) this.mWrappedObject).setIconTintList(colorStateList)
        return this
    }

    @Override // android.view.MenuItem
    fun setIconTintMode(PorterDuff.Mode mode) {
        ((SupportMenuItem) this.mWrappedObject).setIconTintMode(mode)
        return this
    }

    @Override // android.view.MenuItem
    fun setIntent(Intent intent) {
        ((SupportMenuItem) this.mWrappedObject).setIntent(intent)
        return this
    }

    @Override // android.view.MenuItem
    fun setNumericShortcut(Char c) {
        ((SupportMenuItem) this.mWrappedObject).setNumericShortcut(c)
        return this
    }

    @Override // android.view.MenuItem
    fun setNumericShortcut(Char c, Int i) {
        ((SupportMenuItem) this.mWrappedObject).setNumericShortcut(c, i)
        return this
    }

    @Override // android.view.MenuItem
    fun setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        ((SupportMenuItem) this.mWrappedObject).setOnActionExpandListener(onActionExpandListener != null ? OnActionExpandListenerWrapper(onActionExpandListener) : null)
        return this
    }

    @Override // android.view.MenuItem
    fun setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        ((SupportMenuItem) this.mWrappedObject).setOnMenuItemClickListener(onMenuItemClickListener != null ? OnMenuItemClickListenerWrapper(onMenuItemClickListener) : null)
        return this
    }

    @Override // android.view.MenuItem
    fun setShortcut(Char c, Char c2) {
        ((SupportMenuItem) this.mWrappedObject).setShortcut(c, c2)
        return this
    }

    @Override // android.view.MenuItem
    fun setShortcut(Char c, Char c2, Int i, Int i2) {
        ((SupportMenuItem) this.mWrappedObject).setShortcut(c, c2, i, i2)
        return this
    }

    @Override // android.view.MenuItem
    fun setShowAsAction(Int i) {
        ((SupportMenuItem) this.mWrappedObject).setShowAsAction(i)
    }

    @Override // android.view.MenuItem
    fun setShowAsActionFlags(Int i) {
        ((SupportMenuItem) this.mWrappedObject).setShowAsActionFlags(i)
        return this
    }

    @Override // android.view.MenuItem
    fun setTitle(Int i) {
        ((SupportMenuItem) this.mWrappedObject).setTitle(i)
        return this
    }

    @Override // android.view.MenuItem
    fun setTitle(CharSequence charSequence) {
        ((SupportMenuItem) this.mWrappedObject).setTitle(charSequence)
        return this
    }

    @Override // android.view.MenuItem
    fun setTitleCondensed(CharSequence charSequence) {
        ((SupportMenuItem) this.mWrappedObject).setTitleCondensed(charSequence)
        return this
    }

    @Override // android.view.MenuItem
    fun setTooltipText(CharSequence charSequence) {
        ((SupportMenuItem) this.mWrappedObject).setTooltipText(charSequence)
        return this
    }

    @Override // android.view.MenuItem
    fun setVisible(Boolean z) {
        return ((SupportMenuItem) this.mWrappedObject).setVisible(z)
    }
}
