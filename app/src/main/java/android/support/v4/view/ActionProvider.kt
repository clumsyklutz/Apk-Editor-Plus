package android.support.v4.view

import android.content.Context
import android.support.annotation.RestrictTo
import android.util.Log
import android.view.MenuItem
import android.view.SubMenu
import android.view.View

abstract class ActionProvider {
    private static val TAG = "ActionProvider(support)"
    private final Context mContext
    private SubUiVisibilityListener mSubUiVisibilityListener
    private VisibilityListener mVisibilityListener

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface SubUiVisibilityListener {
        Unit onSubUiVisibilityChanged(Boolean z)
    }

    public interface VisibilityListener {
        Unit onActionProviderVisibilityChanged(Boolean z)
    }

    constructor(Context context) {
        this.mContext = context
    }

    fun getContext() {
        return this.mContext
    }

    fun hasSubMenu() {
        return false
    }

    fun isVisible() {
        return true
    }

    public abstract View onCreateActionView()

    fun onCreateActionView(MenuItem menuItem) {
        return onCreateActionView()
    }

    fun onPerformDefaultAction() {
        return false
    }

    fun onPrepareSubMenu(SubMenu subMenu) {
    }

    fun overridesItemVisibility() {
        return false
    }

    fun refreshVisibility() {
        if (this.mVisibilityListener == null || !overridesItemVisibility()) {
            return
        }
        this.mVisibilityListener.onActionProviderVisibilityChanged(isVisible())
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun reset() {
        this.mVisibilityListener = null
        this.mSubUiVisibilityListener = null
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setSubUiVisibilityListener(SubUiVisibilityListener subUiVisibilityListener) {
        this.mSubUiVisibilityListener = subUiVisibilityListener
    }

    fun setVisibilityListener(VisibilityListener visibilityListener) {
        if (this.mVisibilityListener != null && visibilityListener != null) {
            Log.w(TAG, "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?")
        }
        this.mVisibilityListener = visibilityListener
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun subUiVisibilityChanged(Boolean z) {
        if (this.mSubUiVisibilityListener != null) {
            this.mSubUiVisibilityListener.onSubUiVisibilityChanged(z)
        }
    }
}
