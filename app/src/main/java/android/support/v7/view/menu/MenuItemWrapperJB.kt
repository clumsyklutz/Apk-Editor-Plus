package android.support.v7.view.menu

import android.content.Context
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.internal.view.SupportMenuItem
import android.support.v4.view.ActionProvider
import android.support.v7.view.menu.MenuItemWrapperICS
import android.view.ActionProvider
import android.view.MenuItem
import android.view.View

@RequiresApi(16)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class MenuItemWrapperJB extends MenuItemWrapperICS {

    class ActionProviderWrapperJB extends MenuItemWrapperICS.ActionProviderWrapper implements ActionProvider.VisibilityListener {
        ActionProvider.VisibilityListener mListener

        constructor(Context context, android.view.ActionProvider actionProvider) {
            super(context, actionProvider)
        }

        @Override // android.support.v4.view.ActionProvider
        fun isVisible() {
            return this.mInner.isVisible()
        }

        @Override // android.view.ActionProvider.VisibilityListener
        fun onActionProviderVisibilityChanged(Boolean z) {
            if (this.mListener != null) {
                this.mListener.onActionProviderVisibilityChanged(z)
            }
        }

        @Override // android.support.v4.view.ActionProvider
        fun onCreateActionView(MenuItem menuItem) {
            return this.mInner.onCreateActionView(menuItem)
        }

        @Override // android.support.v4.view.ActionProvider
        fun overridesItemVisibility() {
            return this.mInner.overridesItemVisibility()
        }

        @Override // android.support.v4.view.ActionProvider
        fun refreshVisibility() {
            this.mInner.refreshVisibility()
        }

        @Override // android.support.v4.view.ActionProvider
        fun setVisibilityListener(ActionProvider.VisibilityListener visibilityListener) {
            this.mListener = visibilityListener
            android.view.ActionProvider actionProvider = this.mInner
            if (visibilityListener == null) {
                this = null
            }
            actionProvider.setVisibilityListener(this)
        }
    }

    MenuItemWrapperJB(Context context, SupportMenuItem supportMenuItem) {
        super(context, supportMenuItem)
    }

    @Override // android.support.v7.view.menu.MenuItemWrapperICS
    MenuItemWrapperICS.ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider actionProvider) {
        return ActionProviderWrapperJB(this.mContext, actionProvider)
    }
}
