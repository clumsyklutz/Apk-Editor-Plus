package android.support.v7.view

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.RestrictTo
import androidx.appcompat.R
import android.view.ViewConfiguration

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class ActionBarPolicy {
    private Context mContext

    private constructor(Context context) {
        this.mContext = context
    }

    fun get(Context context) {
        return ActionBarPolicy(context)
    }

    fun enableHomeButtonByDefault() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 14
    }

    fun getEmbeddedMenuWidthLimit() {
        return this.mContext.getResources().getDisplayMetrics().widthPixels / 2
    }

    fun getMaxActionButtons() {
        Configuration configuration = this.mContext.getResources().getConfiguration()
        Int i = configuration.screenWidthDp
        Int i2 = configuration.screenHeightDp
        if (configuration.smallestScreenWidthDp > 600 || i > 600 || ((i > 960 && i2 > 720) || (i > 720 && i2 > 960))) {
            return 5
        }
        if (i >= 500 || ((i > 640 && i2 > 480) || (i > 480 && i2 > 640))) {
            return 4
        }
        return i >= 360 ? 3 : 2
    }

    fun getStackedTabMaxWidth() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_stacked_tab_max_width)
    }

    fun getTabContainerHeight() {
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0)
        Int layoutDimension = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionBar_height, 0)
        Resources resources = this.mContext.getResources()
        if (!hasEmbeddedTabs()) {
            layoutDimension = Math.min(layoutDimension, resources.getDimensionPixelSize(R.dimen.abc_action_bar_stacked_max_height))
        }
        typedArrayObtainStyledAttributes.recycle()
        return layoutDimension
    }

    fun hasEmbeddedTabs() {
        return this.mContext.getResources().getBoolean(R.bool.abc_action_bar_embed_tabs)
    }

    fun showsOverflowMenuButton() {
        return Build.VERSION.SDK_INT >= 19 || !ViewConfiguration.get(this.mContext).hasPermanentMenuKey()
    }
}
