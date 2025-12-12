package android.support.v7.view.menu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.internal.view.SupportMenuItem
import android.support.v4.view.ActionProvider
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.view.menu.MenuView
import android.util.Log
import android.view.ContextMenu
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewDebug
import android.view.ViewGroup
import android.widget.LinearLayout

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuItemImpl implements SupportMenuItem {
    private static val CHECKABLE = 1
    private static val CHECKED = 2
    private static val ENABLED = 16
    private static val EXCLUSIVE = 4
    private static val HIDDEN = 8
    private static val IS_ACTION = 32
    static val NO_ICON = 0
    private static val SHOW_AS_ACTION_MASK = 3
    private static val TAG = "MenuItemImpl"
    private ActionProvider mActionProvider
    private View mActionView
    private final Int mCategoryOrder
    private MenuItem.OnMenuItemClickListener mClickListener
    private CharSequence mContentDescription
    private final Int mGroup
    private Drawable mIconDrawable
    private final Int mId
    private Intent mIntent
    private Runnable mItemCallback
    MenuBuilder mMenu
    private ContextMenu.ContextMenuInfo mMenuInfo
    private MenuItem.OnActionExpandListener mOnActionExpandListener
    private final Int mOrdering
    private Char mShortcutAlphabeticChar
    private Char mShortcutNumericChar
    private Int mShowAsAction
    private SubMenuBuilder mSubMenu
    private CharSequence mTitle
    private CharSequence mTitleCondensed
    private CharSequence mTooltipText
    private Int mShortcutNumericModifiers = 4096
    private Int mShortcutAlphabeticModifiers = 4096
    private Int mIconResId = 0
    private ColorStateList mIconTintList = null
    private PorterDuff.Mode mIconTintMode = null
    private Boolean mHasIconTint = false
    private Boolean mHasIconTintMode = false
    private Boolean mNeedToApplyIconTint = false
    private Int mFlags = 16
    private Boolean mIsActionViewExpanded = false

    MenuItemImpl(MenuBuilder menuBuilder, Int i, Int i2, Int i3, Int i4, CharSequence charSequence, Int i5) {
        this.mShowAsAction = 0
        this.mMenu = menuBuilder
        this.mId = i2
        this.mGroup = i
        this.mCategoryOrder = i3
        this.mOrdering = i4
        this.mTitle = charSequence
        this.mShowAsAction = i5
    }

    private fun appendModifier(StringBuilder sb, Int i, Int i2, String str) {
        if ((i & i2) == i2) {
            sb.append(str)
        }
    }

    private fun applyIconTintIfNecessary(Drawable drawable) {
        if (drawable != null && this.mNeedToApplyIconTint && (this.mHasIconTint || this.mHasIconTintMode)) {
            drawable = DrawableCompat.wrap(drawable).mutate()
            if (this.mHasIconTint) {
                DrawableCompat.setTintList(drawable, this.mIconTintList)
            }
            if (this.mHasIconTintMode) {
                DrawableCompat.setTintMode(drawable, this.mIconTintMode)
            }
            this.mNeedToApplyIconTint = false
        }
        return drawable
    }

    public final Unit actionFormatChanged() {
        this.mMenu.onItemActionRequestChanged(this)
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final Boolean collapseActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false
        }
        if (this.mActionView == null) {
            return true
        }
        if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionCollapse(this)) {
            return this.mMenu.collapseItemActionView(this)
        }
        return false
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final Boolean expandActionView() {
        if (!hasCollapsibleActionView()) {
            return false
        }
        if (this.mOnActionExpandListener == null || this.mOnActionExpandListener.onMenuItemActionExpand(this)) {
            return this.mMenu.expandItemActionView(this)
        }
        return false
    }

    @Override // android.view.MenuItem
    public final android.view.ActionProvider getActionProvider() {
        throw UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()")
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final View getActionView() {
        if (this.mActionView != null) {
            return this.mActionView
        }
        if (this.mActionProvider == null) {
            return null
        }
        this.mActionView = this.mActionProvider.onCreateActionView(this)
        return this.mActionView
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final Int getAlphabeticModifiers() {
        return this.mShortcutAlphabeticModifiers
    }

    @Override // android.view.MenuItem
    public final Char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar
    }

    final Runnable getCallback() {
        return this.mItemCallback
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.mContentDescription
    }

    @Override // android.view.MenuItem
    public final Int getGroupId() {
        return this.mGroup
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        if (this.mIconDrawable != null) {
            return applyIconTintIfNecessary(this.mIconDrawable)
        }
        if (this.mIconResId == 0) {
            return null
        }
        Drawable drawable = AppCompatResources.getDrawable(this.mMenu.getContext(), this.mIconResId)
        this.mIconResId = 0
        this.mIconDrawable = drawable
        return applyIconTintIfNecessary(drawable)
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.mIconTintList
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.mIconTintMode
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.mIntent
    }

    @Override // android.view.MenuItem
    @ViewDebug.CapturedViewProperty
    public final Int getItemId() {
        return this.mId
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.mMenuInfo
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final Int getNumericModifiers() {
        return this.mShortcutNumericModifiers
    }

    @Override // android.view.MenuItem
    public final Char getNumericShortcut() {
        return this.mShortcutNumericChar
    }

    @Override // android.view.MenuItem
    public final Int getOrder() {
        return this.mCategoryOrder
    }

    public final Int getOrdering() {
        return this.mOrdering
    }

    final Char getShortcut() {
        return this.mMenu.isQwertyMode() ? this.mShortcutAlphabeticChar : this.mShortcutNumericChar
    }

    final String getShortcutLabel() {
        Char shortcut = getShortcut()
        if (shortcut == 0) {
            return ""
        }
        Resources resources = this.mMenu.getContext().getResources()
        StringBuilder sb = StringBuilder()
        if (ViewConfiguration.get(this.mMenu.getContext()).hasPermanentMenuKey()) {
            sb.append(resources.getString(R.string.abc_prepend_shortcut_label))
        }
        Int i = this.mMenu.isQwertyMode() ? this.mShortcutAlphabeticModifiers : this.mShortcutNumericModifiers
        appendModifier(sb, i, 65536, resources.getString(R.string.abc_menu_meta_shortcut_label))
        appendModifier(sb, i, 4096, resources.getString(R.string.abc_menu_ctrl_shortcut_label))
        appendModifier(sb, i, 2, resources.getString(R.string.abc_menu_alt_shortcut_label))
        appendModifier(sb, i, 1, resources.getString(R.string.abc_menu_shift_shortcut_label))
        appendModifier(sb, i, 4, resources.getString(R.string.abc_menu_sym_shortcut_label))
        appendModifier(sb, i, 8, resources.getString(R.string.abc_menu_function_shortcut_label))
        switch (shortcut) {
            case '\b':
                sb.append(resources.getString(R.string.abc_menu_delete_shortcut_label))
                break
            case '\n':
                sb.append(resources.getString(R.string.abc_menu_enter_shortcut_label))
                break
            case ' ':
                sb.append(resources.getString(R.string.abc_menu_space_shortcut_label))
                break
            default:
                sb.append(shortcut)
                break
        }
        return sb.toString()
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return this.mSubMenu
    }

    @Override // android.support.v4.internal.view.SupportMenuItem
    public final ActionProvider getSupportActionProvider() {
        return this.mActionProvider
    }

    @Override // android.view.MenuItem
    @ViewDebug.CapturedViewProperty
    public final CharSequence getTitle() {
        return this.mTitle
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle
        return (Build.VERSION.SDK_INT >= 18 || charSequence == null || (charSequence is String)) ? charSequence : charSequence.toString()
    }

    final CharSequence getTitleForItemView(MenuView.ItemView itemView) {
        return (itemView == null || !itemView.prefersCondensedTitle()) ? getTitle() : getTitleCondensed()
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.mTooltipText
    }

    public final Boolean hasCollapsibleActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false
        }
        if (this.mActionView == null && this.mActionProvider != null) {
            this.mActionView = this.mActionProvider.onCreateActionView(this)
        }
        return this.mActionView != null
    }

    @Override // android.view.MenuItem
    public final Boolean hasSubMenu() {
        return this.mSubMenu != null
    }

    public final Boolean invoke() {
        if ((this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) || this.mMenu.dispatchMenuItemSelected(this.mMenu, this)) {
            return true
        }
        if (this.mItemCallback != null) {
            this.mItemCallback.run()
            return true
        }
        if (this.mIntent != null) {
            try {
                this.mMenu.getContext().startActivity(this.mIntent)
                return true
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Can't find activity to handle intent; ignoring", e)
            }
        }
        return this.mActionProvider != null && this.mActionProvider.onPerformDefaultAction()
    }

    public final Boolean isActionButton() {
        return (this.mFlags & 32) == 32
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final Boolean isActionViewExpanded() {
        return this.mIsActionViewExpanded
    }

    @Override // android.view.MenuItem
    public final Boolean isCheckable() {
        return (this.mFlags & 1) == 1
    }

    @Override // android.view.MenuItem
    public final Boolean isChecked() {
        return (this.mFlags & 2) == 2
    }

    @Override // android.view.MenuItem
    public final Boolean isEnabled() {
        return (this.mFlags & 16) != 0
    }

    public final Boolean isExclusiveCheckable() {
        return (this.mFlags & 4) != 0
    }

    @Override // android.view.MenuItem
    public final Boolean isVisible() {
        return (this.mActionProvider == null || !this.mActionProvider.overridesItemVisibility()) ? (this.mFlags & 8) == 0 : (this.mFlags & 8) == 0 && this.mActionProvider.isVisible()
    }

    public final Boolean requestsActionButton() {
        return (this.mShowAsAction & 1) == 1
    }

    public final Boolean requiresActionButton() {
        return (this.mShowAsAction & 2) == 2
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        throw UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()")
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final SupportMenuItem setActionView(Int i) {
        Context context = this.mMenu.getContext()
        setActionView(LayoutInflater.from(context).inflate(i, (ViewGroup) LinearLayout(context), false))
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final SupportMenuItem setActionView(View view) {
        this.mActionView = view
        this.mActionProvider = null
        if (view != null && view.getId() == -1 && this.mId > 0) {
            view.setId(this.mId)
        }
        this.mMenu.onItemActionRequestChanged(this)
        return this
    }

    public final Unit setActionViewExpanded(Boolean z) {
        this.mIsActionViewExpanded = z
        this.mMenu.onItemsChanged(false)
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(Char c) {
        if (this.mShortcutAlphabeticChar != c) {
            this.mShortcutAlphabeticChar = Character.toLowerCase(c)
            this.mMenu.onItemsChanged(false)
        }
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(Char c, Int i) {
        if (this.mShortcutAlphabeticChar != c || this.mShortcutAlphabeticModifiers != i) {
            this.mShortcutAlphabeticChar = Character.toLowerCase(c)
            this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(i)
            this.mMenu.onItemsChanged(false)
        }
        return this
    }

    public final MenuItem setCallback(Runnable runnable) {
        this.mItemCallback = runnable
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(Boolean z) {
        Int i = this.mFlags
        this.mFlags = (z ? 1 : 0) | (this.mFlags & (-2))
        if (i != this.mFlags) {
            this.mMenu.onItemsChanged(false)
        }
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(Boolean z) {
        if ((this.mFlags & 4) != 0) {
            this.mMenu.setExclusiveItemChecked(this)
        } else {
            setCheckedInt(z)
        }
        return this
    }

    final Unit setCheckedInt(Boolean z) {
        Int i = this.mFlags
        this.mFlags = (z ? 2 : 0) | (this.mFlags & (-3))
        if (i != this.mFlags) {
            this.mMenu.onItemsChanged(false)
        }
    }

    @Override // android.view.MenuItem
    public final SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(Boolean z) {
        if (z) {
            this.mFlags |= 16
        } else {
            this.mFlags &= -17
        }
        this.mMenu.onItemsChanged(false)
        return this
    }

    public final Unit setExclusiveCheckable(Boolean z) {
        this.mFlags = (z ? 4 : 0) | (this.mFlags & (-5))
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Int i) {
        this.mIconDrawable = null
        this.mIconResId = i
        this.mNeedToApplyIconTint = true
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.mIconResId = 0
        this.mIconDrawable = drawable
        this.mNeedToApplyIconTint = true
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setIconTintList(@Nullable ColorStateList colorStateList) {
        this.mIconTintList = colorStateList
        this.mHasIconTint = true
        this.mNeedToApplyIconTint = true
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode
        this.mHasIconTintMode = true
        this.mNeedToApplyIconTint = true
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.mIntent = intent
        return this
    }

    public final Unit setIsActionButton(Boolean z) {
        if (z) {
            this.mFlags |= 32
        } else {
            this.mFlags &= -33
        }
    }

    final Unit setMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mMenuInfo = contextMenuInfo
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(Char c) {
        if (this.mShortcutNumericChar != c) {
            this.mShortcutNumericChar = c
            this.mMenu.onItemsChanged(false)
        }
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setNumericShortcut(Char c, Int i) {
        if (this.mShortcutNumericChar != c || this.mShortcutNumericModifiers != i) {
            this.mShortcutNumericChar = c
            this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(i)
            this.mMenu.onItemsChanged(false)
        }
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(Char c, Char c2) {
        this.mShortcutNumericChar = c
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2)
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setShortcut(Char c, Char c2, Int i, Int i2) {
        this.mShortcutNumericChar = c
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(i)
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2)
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(i2)
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final Unit setShowAsAction(Int i) {
        switch (i & 3) {
            case 0:
            case 1:
            case 2:
                this.mShowAsAction = i
                this.mMenu.onItemActionRequestChanged(this)
                return
            default:
                throw IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.")
        }
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public final SupportMenuItem setShowAsActionFlags(Int i) {
        setShowAsAction(i)
        return this
    }

    public final Unit setSubMenu(SubMenuBuilder subMenuBuilder) {
        this.mSubMenu = subMenuBuilder
        subMenuBuilder.setHeaderTitle(getTitle())
    }

    @Override // android.support.v4.internal.view.SupportMenuItem
    public final SupportMenuItem setSupportActionProvider(ActionProvider actionProvider) {
        if (this.mActionProvider != null) {
            this.mActionProvider.reset()
        }
        this.mActionView = null
        this.mActionProvider = actionProvider
        this.mMenu.onItemsChanged(true)
        if (this.mActionProvider != null) {
            this.mActionProvider.setVisibilityListener(new ActionProvider.VisibilityListener() { // from class: android.support.v7.view.menu.MenuItemImpl.1
                @Override // android.support.v4.view.ActionProvider.VisibilityListener
                fun onActionProviderVisibilityChanged(Boolean z) {
                    MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this)
                }
            })
        }
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(Int i) {
        return setTitle(this.mMenu.getContext().getString(i))
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence
        this.mMenu.onItemsChanged(false)
        if (this.mSubMenu != null) {
            this.mSubMenu.setHeaderTitle(charSequence)
        }
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.view.MenuItem
    public final SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence
        this.mMenu.onItemsChanged(false)
        return this
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(Boolean z) {
        if (setVisibleInt(z)) {
            this.mMenu.onItemVisibleChanged(this)
        }
        return this
    }

    final Boolean setVisibleInt(Boolean z) {
        Int i = this.mFlags
        this.mFlags = (z ? 0 : 8) | (this.mFlags & (-9))
        return i != this.mFlags
    }

    public final Boolean shouldShowIcon() {
        return this.mMenu.getOptionalIconsVisible()
    }

    final Boolean shouldShowShortcut() {
        return this.mMenu.isShortcutsVisible() && getShortcut() != 0
    }

    public final Boolean showsTextAsAction() {
        return (this.mShowAsAction & 4) == 4
    }

    public final String toString() {
        if (this.mTitle != null) {
            return this.mTitle.toString()
        }
        return null
    }
}
