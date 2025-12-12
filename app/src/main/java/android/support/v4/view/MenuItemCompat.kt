package android.support.v4.view

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.internal.view.SupportMenuItem
import android.util.Log
import android.view.MenuItem
import android.view.View

class MenuItemCompat {

    @Deprecated
    public static val SHOW_AS_ACTION_ALWAYS = 2

    @Deprecated
    public static val SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8

    @Deprecated
    public static val SHOW_AS_ACTION_IF_ROOM = 1

    @Deprecated
    public static val SHOW_AS_ACTION_NEVER = 0

    @Deprecated
    public static val SHOW_AS_ACTION_WITH_TEXT = 4
    private static val TAG = "MenuItemCompat"

    @Deprecated
    public interface OnActionExpandListener {
        Boolean onMenuItemActionCollapse(MenuItem menuItem)

        Boolean onMenuItemActionExpand(MenuItem menuItem)
    }

    private constructor() {
    }

    @Deprecated
    fun collapseActionView(MenuItem menuItem) {
        return menuItem.collapseActionView()
    }

    @Deprecated
    fun expandActionView(MenuItem menuItem) {
        return menuItem.expandActionView()
    }

    fun getActionProvider(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getSupportActionProvider()
        }
        Log.w(TAG, "getActionProvider: item does not implement SupportMenuItem; returning null")
        return null
    }

    @Deprecated
    fun getActionView(MenuItem menuItem) {
        return menuItem.getActionView()
    }

    fun getAlphabeticModifiers(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getAlphabeticModifiers()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return menuItem.getAlphabeticModifiers()
        }
        return 0
    }

    fun getContentDescription(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getContentDescription()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return menuItem.getContentDescription()
        }
        return null
    }

    fun getIconTintList(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getIconTintList()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return menuItem.getIconTintList()
        }
        return null
    }

    public static PorterDuff.Mode getIconTintMode(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getIconTintMode()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return menuItem.getIconTintMode()
        }
        return null
    }

    fun getNumericModifiers(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getNumericModifiers()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return menuItem.getNumericModifiers()
        }
        return 0
    }

    fun getTooltipText(MenuItem menuItem) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getTooltipText()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return menuItem.getTooltipText()
        }
        return null
    }

    @Deprecated
    fun isActionViewExpanded(MenuItem menuItem) {
        return menuItem.isActionViewExpanded()
    }

    fun setActionProvider(MenuItem menuItem, ActionProvider actionProvider) {
        if (menuItem is SupportMenuItem) {
            return ((SupportMenuItem) menuItem).setSupportActionProvider(actionProvider)
        }
        Log.w(TAG, "setActionProvider: item does not implement SupportMenuItem; ignoring")
        return menuItem
    }

    @Deprecated
    fun setActionView(MenuItem menuItem, Int i) {
        return menuItem.setActionView(i)
    }

    @Deprecated
    fun setActionView(MenuItem menuItem, View view) {
        return menuItem.setActionView(view)
    }

    fun setAlphabeticShortcut(MenuItem menuItem, Char c, Int i) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setAlphabeticShortcut(c, i)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setAlphabeticShortcut(c, i)
        }
    }

    fun setContentDescription(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setContentDescription(charSequence)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setContentDescription(charSequence)
        }
    }

    fun setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setIconTintList(colorStateList)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setIconTintList(colorStateList)
        }
    }

    fun setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setIconTintMode(mode)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setIconTintMode(mode)
        }
    }

    fun setNumericShortcut(MenuItem menuItem, Char c, Int i) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setNumericShortcut(c, i)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setNumericShortcut(c, i)
        }
    }

    @Deprecated
    fun setOnActionExpandListener(MenuItem menuItem, final OnActionExpandListener onActionExpandListener) {
        return menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() { // from class: android.support.v4.view.MenuItemCompat.1
            @Override // android.view.MenuItem.OnActionExpandListener
            public final Boolean onMenuItemActionCollapse(MenuItem menuItem2) {
                return onActionExpandListener.onMenuItemActionCollapse(menuItem2)
            }

            @Override // android.view.MenuItem.OnActionExpandListener
            public final Boolean onMenuItemActionExpand(MenuItem menuItem2) {
                return onActionExpandListener.onMenuItemActionExpand(menuItem2)
            }
        })
    }

    fun setShortcut(MenuItem menuItem, Char c, Char c2, Int i, Int i2) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setShortcut(c, c2, i, i2)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setShortcut(c, c2, i, i2)
        }
    }

    @Deprecated
    fun setShowAsAction(MenuItem menuItem, Int i) {
        menuItem.setShowAsAction(i)
    }

    fun setTooltipText(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem is SupportMenuItem) {
            ((SupportMenuItem) menuItem).setTooltipText(charSequence)
        } else if (Build.VERSION.SDK_INT >= 26) {
            menuItem.setTooltipText(charSequence)
        }
    }
}
