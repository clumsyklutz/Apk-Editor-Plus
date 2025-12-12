package android.support.v4.internal.view

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.annotation.RestrictTo
import android.support.v4.view.ActionProvider
import android.view.MenuItem
import android.view.View

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface SupportMenuItem extends MenuItem {
    public static val SHOW_AS_ACTION_ALWAYS = 2
    public static val SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8
    public static val SHOW_AS_ACTION_IF_ROOM = 1
    public static val SHOW_AS_ACTION_NEVER = 0
    public static val SHOW_AS_ACTION_WITH_TEXT = 4

    @Override // android.view.MenuItem
    Boolean collapseActionView()

    @Override // android.view.MenuItem
    Boolean expandActionView()

    @Override // android.view.MenuItem
    View getActionView()

    @Override // android.view.MenuItem
    Int getAlphabeticModifiers()

    @Override // android.view.MenuItem
    CharSequence getContentDescription()

    @Override // android.view.MenuItem
    ColorStateList getIconTintList()

    @Override // android.view.MenuItem
    PorterDuff.Mode getIconTintMode()

    @Override // android.view.MenuItem
    Int getNumericModifiers()

    ActionProvider getSupportActionProvider()

    @Override // android.view.MenuItem
    CharSequence getTooltipText()

    @Override // android.view.MenuItem
    Boolean isActionViewExpanded()

    @Override // android.view.MenuItem
    MenuItem setActionView(Int i)

    @Override // android.view.MenuItem
    MenuItem setActionView(View view)

    @Override // android.view.MenuItem
    MenuItem setAlphabeticShortcut(Char c, Int i)

    @Override // android.view.MenuItem
    SupportMenuItem setContentDescription(CharSequence charSequence)

    @Override // android.view.MenuItem
    MenuItem setIconTintList(ColorStateList colorStateList)

    @Override // android.view.MenuItem
    MenuItem setIconTintMode(PorterDuff.Mode mode)

    @Override // android.view.MenuItem
    MenuItem setNumericShortcut(Char c, Int i)

    @Override // android.view.MenuItem
    MenuItem setShortcut(Char c, Char c2, Int i, Int i2)

    @Override // android.view.MenuItem
    Unit setShowAsAction(Int i)

    @Override // android.view.MenuItem
    MenuItem setShowAsActionFlags(Int i)

    SupportMenuItem setSupportActionProvider(ActionProvider actionProvider)

    @Override // android.view.MenuItem
    SupportMenuItem setTooltipText(CharSequence charSequence)
}
