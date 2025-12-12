package android.support.v7.view.menu

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.support.v4.internal.view.SupportMenuItem
import android.view.ActionProvider
import android.view.ContextMenu
import android.view.KeyEvent
import android.view.MenuItem
import android.view.SubMenu
import android.view.View

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActionMenuItem implements SupportMenuItem {
    private static val CHECKABLE = 1
    private static val CHECKED = 2
    private static val ENABLED = 16
    private static val EXCLUSIVE = 4
    private static val HIDDEN = 8
    private static val NO_ICON = 0
    private final Int mCategoryOrder
    private MenuItem.OnMenuItemClickListener mClickListener
    private CharSequence mContentDescription
    private Context mContext
    private final Int mGroup
    private Drawable mIconDrawable
    private final Int mId
    private Intent mIntent
    private final Int mOrdering
    private Char mShortcutAlphabeticChar
    private Char mShortcutNumericChar
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
    private Int mFlags = 16

    constructor(Context context, Int i, Int i2, Int i3, Int i4, CharSequence charSequence) {
        this.mContext = context
        this.mId = i2
        this.mGroup = i
        this.mCategoryOrder = i3
        this.mOrdering = i4
        this.mTitle = charSequence
    }

    private fun applyIconTint() {
        if (this.mIconDrawable != null) {
            if (this.mHasIconTint || this.mHasIconTintMode) {
                this.mIconDrawable = DrawableCompat.wrap(this.mIconDrawable)
                this.mIconDrawable = this.mIconDrawable.mutate()
                if (this.mHasIconTint) {
                    DrawableCompat.setTintList(this.mIconDrawable, this.mIconTintList)
                }
                if (this.mHasIconTintMode) {
                    DrawableCompat.setTintMode(this.mIconDrawable, this.mIconTintMode)
                }
            }
        }
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun collapseActionView() {
        return false
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun expandActionView() {
        return false
    }

    @Override // android.view.MenuItem
    fun getActionProvider() {
        throw UnsupportedOperationException()
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun getActionView() {
        return null
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun getAlphabeticModifiers() {
        return this.mShortcutAlphabeticModifiers
    }

    @Override // android.view.MenuItem
    fun getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun getContentDescription() {
        return this.mContentDescription
    }

    @Override // android.view.MenuItem
    fun getGroupId() {
        return this.mGroup
    }

    @Override // android.view.MenuItem
    fun getIcon() {
        return this.mIconDrawable
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun getIconTintList() {
        return this.mIconTintList
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    public PorterDuff.Mode getIconTintMode() {
        return this.mIconTintMode
    }

    @Override // android.view.MenuItem
    fun getIntent() {
        return this.mIntent
    }

    @Override // android.view.MenuItem
    fun getItemId() {
        return this.mId
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun getNumericModifiers() {
        return this.mShortcutNumericModifiers
    }

    @Override // android.view.MenuItem
    fun getNumericShortcut() {
        return this.mShortcutNumericChar
    }

    @Override // android.view.MenuItem
    fun getOrder() {
        return this.mOrdering
    }

    @Override // android.view.MenuItem
    fun getSubMenu() {
        return null
    }

    @Override // android.support.v4.internal.view.SupportMenuItem
    public android.support.v4.view.ActionProvider getSupportActionProvider() {
        return null
    }

    @Override // android.view.MenuItem
    fun getTitle() {
        return this.mTitle
    }

    @Override // android.view.MenuItem
    fun getTitleCondensed() {
        return this.mTitleCondensed != null ? this.mTitleCondensed : this.mTitle
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun getTooltipText() {
        return this.mTooltipText
    }

    @Override // android.view.MenuItem
    fun hasSubMenu() {
        return false
    }

    fun invoke() {
        if (this.mClickListener != null && this.mClickListener.onMenuItemClick(this)) {
            return true
        }
        if (this.mIntent == null) {
            return false
        }
        this.mContext.startActivity(this.mIntent)
        return true
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun isActionViewExpanded() {
        return false
    }

    @Override // android.view.MenuItem
    fun isCheckable() {
        return (this.mFlags & 1) != 0
    }

    @Override // android.view.MenuItem
    fun isChecked() {
        return (this.mFlags & 2) != 0
    }

    @Override // android.view.MenuItem
    fun isEnabled() {
        return (this.mFlags & 16) != 0
    }

    @Override // android.view.MenuItem
    fun isVisible() {
        return (this.mFlags & 8) == 0
    }

    @Override // android.view.MenuItem
    fun setActionProvider(ActionProvider actionProvider) {
        throw UnsupportedOperationException()
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setActionView(Int i) {
        throw UnsupportedOperationException()
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setActionView(View view) {
        throw UnsupportedOperationException()
    }

    @Override // android.view.MenuItem
    fun setAlphabeticShortcut(Char c) {
        this.mShortcutAlphabeticChar = Character.toLowerCase(c)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setAlphabeticShortcut(Char c, Int i) {
        this.mShortcutAlphabeticChar = Character.toLowerCase(c)
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(i)
        return this
    }

    @Override // android.view.MenuItem
    fun setCheckable(Boolean z) {
        this.mFlags = (z ? 1 : 0) | (this.mFlags & (-2))
        return this
    }

    @Override // android.view.MenuItem
    fun setChecked(Boolean z) {
        this.mFlags = (z ? 2 : 0) | (this.mFlags & (-3))
        return this
    }

    @Override // android.view.MenuItem
    fun setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence
        return this
    }

    @Override // android.view.MenuItem
    fun setEnabled(Boolean z) {
        this.mFlags = (z ? 16 : 0) | (this.mFlags & (-17))
        return this
    }

    fun setExclusiveCheckable(Boolean z) {
        this.mFlags = (z ? 4 : 0) | (this.mFlags & (-5))
        return this
    }

    @Override // android.view.MenuItem
    fun setIcon(Int i) {
        this.mIconResId = i
        this.mIconDrawable = ContextCompat.getDrawable(this.mContext, i)
        applyIconTint()
        return this
    }

    @Override // android.view.MenuItem
    fun setIcon(Drawable drawable) {
        this.mIconDrawable = drawable
        this.mIconResId = 0
        applyIconTint()
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setIconTintList(@Nullable ColorStateList colorStateList) {
        this.mIconTintList = colorStateList
        this.mHasIconTint = true
        applyIconTint()
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode
        this.mHasIconTintMode = true
        applyIconTint()
        return this
    }

    @Override // android.view.MenuItem
    fun setIntent(Intent intent) {
        this.mIntent = intent
        return this
    }

    @Override // android.view.MenuItem
    fun setNumericShortcut(Char c) {
        this.mShortcutNumericChar = c
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setNumericShortcut(Char c, Int i) {
        this.mShortcutNumericChar = c
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(i)
        return this
    }

    @Override // android.view.MenuItem
    fun setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw UnsupportedOperationException()
    }

    @Override // android.view.MenuItem
    fun setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener
        return this
    }

    @Override // android.view.MenuItem
    fun setShortcut(Char c, Char c2) {
        this.mShortcutNumericChar = c
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setShortcut(Char c, Char c2, Int i, Int i2) {
        this.mShortcutNumericChar = c
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(i)
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2)
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(i2)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setShowAsAction(Int i) {
    }

    @Override // android.support.v4.internal.view.SupportMenuItem, android.view.MenuItem
    fun setShowAsActionFlags(Int i) {
        setShowAsAction(i)
        return this
    }

    @Override // android.support.v4.internal.view.SupportMenuItem
    fun setSupportActionProvider(android.support.v4.view.ActionProvider actionProvider) {
        throw UnsupportedOperationException()
    }

    @Override // android.view.MenuItem
    fun setTitle(Int i) {
        this.mTitle = this.mContext.getResources().getString(i)
        return this
    }

    @Override // android.view.MenuItem
    fun setTitle(CharSequence charSequence) {
        this.mTitle = charSequence
        return this
    }

    @Override // android.view.MenuItem
    fun setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence
        return this
    }

    @Override // android.view.MenuItem
    fun setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence
        return this
    }

    @Override // android.view.MenuItem
    fun setVisible(Boolean z) {
        this.mFlags = (z ? 0 : 8) | (this.mFlags & 8)
        return this
    }
}
