package android.support.v7.view

import android.content.Context
import android.support.annotation.RestrictTo
import androidx.core.internal.view.SupportMenu
import android.support.v4.internal.view.SupportMenuItem
import android.support.v4.util.SimpleArrayMap
import android.support.v7.view.ActionMode
import android.support.v7.view.menu.MenuWrapperFactory
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import java.util.ArrayList

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SupportActionModeWrapper extends android.view.ActionMode {
    final Context mContext
    final ActionMode mWrappedObject

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class CallbackWrapper implements ActionMode.Callback {
        final Context mContext
        final ActionMode.Callback mWrappedCallback
        val mActionModes = ArrayList()
        val mMenus = SimpleArrayMap()

        constructor(Context context, ActionMode.Callback callback) {
            this.mContext = context
            this.mWrappedCallback = callback
        }

        private fun getMenuWrapper(Menu menu) {
            Menu menu2 = (Menu) this.mMenus.get(menu)
            if (menu2 != null) {
                return menu2
            }
            Menu menuWrapSupportMenu = MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu) menu)
            this.mMenus.put(menu, menuWrapSupportMenu)
            return menuWrapSupportMenu
        }

        public android.view.ActionMode getActionModeWrapper(ActionMode actionMode) {
            Int size = this.mActionModes.size()
            for (Int i = 0; i < size; i++) {
                SupportActionModeWrapper supportActionModeWrapper = (SupportActionModeWrapper) this.mActionModes.get(i)
                if (supportActionModeWrapper != null && supportActionModeWrapper.mWrappedObject == actionMode) {
                    return supportActionModeWrapper
                }
            }
            SupportActionModeWrapper supportActionModeWrapper2 = SupportActionModeWrapper(this.mContext, actionMode)
            this.mActionModes.add(supportActionModeWrapper2)
            return supportActionModeWrapper2
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(actionMode), MenuWrapperFactory.wrapSupportMenuItem(this.mContext, (SupportMenuItem) menuItem))
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(actionMode), getMenuWrapper(menu))
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onDestroyActionMode(ActionMode actionMode) {
            this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(actionMode))
        }

        @Override // android.support.v7.view.ActionMode.Callback
        fun onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(actionMode), getMenuWrapper(menu))
        }
    }

    constructor(Context context, ActionMode actionMode) {
        this.mContext = context
        this.mWrappedObject = actionMode
    }

    @Override // android.view.ActionMode
    fun finish() {
        this.mWrappedObject.finish()
    }

    @Override // android.view.ActionMode
    fun getCustomView() {
        return this.mWrappedObject.getCustomView()
    }

    @Override // android.view.ActionMode
    fun getMenu() {
        return MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu) this.mWrappedObject.getMenu())
    }

    @Override // android.view.ActionMode
    fun getMenuInflater() {
        return this.mWrappedObject.getMenuInflater()
    }

    @Override // android.view.ActionMode
    fun getSubtitle() {
        return this.mWrappedObject.getSubtitle()
    }

    @Override // android.view.ActionMode
    fun getTag() {
        return this.mWrappedObject.getTag()
    }

    @Override // android.view.ActionMode
    fun getTitle() {
        return this.mWrappedObject.getTitle()
    }

    @Override // android.view.ActionMode
    fun getTitleOptionalHint() {
        return this.mWrappedObject.getTitleOptionalHint()
    }

    @Override // android.view.ActionMode
    fun invalidate() {
        this.mWrappedObject.invalidate()
    }

    @Override // android.view.ActionMode
    fun isTitleOptional() {
        return this.mWrappedObject.isTitleOptional()
    }

    @Override // android.view.ActionMode
    fun setCustomView(View view) {
        this.mWrappedObject.setCustomView(view)
    }

    @Override // android.view.ActionMode
    fun setSubtitle(Int i) {
        this.mWrappedObject.setSubtitle(i)
    }

    @Override // android.view.ActionMode
    fun setSubtitle(CharSequence charSequence) {
        this.mWrappedObject.setSubtitle(charSequence)
    }

    @Override // android.view.ActionMode
    fun setTag(Object obj) {
        this.mWrappedObject.setTag(obj)
    }

    @Override // android.view.ActionMode
    fun setTitle(Int i) {
        this.mWrappedObject.setTitle(i)
    }

    @Override // android.view.ActionMode
    fun setTitle(CharSequence charSequence) {
        this.mWrappedObject.setTitle(charSequence)
    }

    @Override // android.view.ActionMode
    fun setTitleOptionalHint(Boolean z) {
        this.mWrappedObject.setTitleOptionalHint(z)
    }
}
