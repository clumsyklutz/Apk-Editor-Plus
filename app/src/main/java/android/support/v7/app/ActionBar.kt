package android.support.v7.app

import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.R
import android.support.v7.view.ActionMode
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

abstract class ActionBar {
    public static val DISPLAY_HOME_AS_UP = 4
    public static val DISPLAY_SHOW_CUSTOM = 16
    public static val DISPLAY_SHOW_HOME = 2
    public static val DISPLAY_SHOW_TITLE = 8
    public static val DISPLAY_USE_LOGO = 1

    @Deprecated
    public static val NAVIGATION_MODE_LIST = 1

    @Deprecated
    public static val NAVIGATION_MODE_STANDARD = 0

    @Deprecated
    public static val NAVIGATION_MODE_TABS = 2

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface DisplayOptions {
    }

    class LayoutParams extends ViewGroup.MarginLayoutParams {
        public Int gravity

        constructor(Int i) {
            this(-2, -1, i)
        }

        constructor(Int i, Int i2) {
            super(i, i2)
            this.gravity = 0
            this.gravity = 8388627
        }

        constructor(Int i, Int i2, Int i3) {
            super(i, i2)
            this.gravity = 0
            this.gravity = i3
        }

        constructor(@NonNull Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
            this.gravity = 0
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBarLayout)
            this.gravity = typedArrayObtainStyledAttributes.getInt(R.styleable.ActionBarLayout_android_layout_gravity, 0)
            typedArrayObtainStyledAttributes.recycle()
        }

        constructor(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams)
            this.gravity = 0
            this.gravity = layoutParams.gravity
        }

        constructor(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams)
            this.gravity = 0
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface NavigationMode {
    }

    public interface OnMenuVisibilityListener {
        Unit onMenuVisibilityChanged(Boolean z)
    }

    @Deprecated
    public interface OnNavigationListener {
        Boolean onNavigationItemSelected(Int i, Long j)
    }

    @Deprecated
    abstract class Tab {
        public static val INVALID_POSITION = -1

        public abstract CharSequence getContentDescription()

        public abstract View getCustomView()

        public abstract Drawable getIcon()

        public abstract Int getPosition()

        public abstract Object getTag()

        public abstract CharSequence getText()

        public abstract Unit select()

        public abstract Tab setContentDescription(@StringRes Int i)

        public abstract Tab setContentDescription(CharSequence charSequence)

        public abstract Tab setCustomView(Int i)

        public abstract Tab setCustomView(View view)

        public abstract Tab setIcon(@DrawableRes Int i)

        public abstract Tab setIcon(Drawable drawable)

        public abstract Tab setTabListener(TabListener tabListener)

        public abstract Tab setTag(Object obj)

        public abstract Tab setText(Int i)

        public abstract Tab setText(CharSequence charSequence)
    }

    @Deprecated
    public interface TabListener {
        Unit onTabReselected(Tab tab, FragmentTransaction fragmentTransaction)

        Unit onTabSelected(Tab tab, FragmentTransaction fragmentTransaction)

        Unit onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction)
    }

    public abstract Unit addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener)

    @Deprecated
    public abstract Unit addTab(Tab tab)

    @Deprecated
    public abstract Unit addTab(Tab tab, Int i)

    @Deprecated
    public abstract Unit addTab(Tab tab, Int i, Boolean z)

    @Deprecated
    public abstract Unit addTab(Tab tab, Boolean z)

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun closeOptionsMenu() {
        return false
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun collapseActionView() {
        return false
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun dispatchMenuVisibilityChanged(Boolean z) {
    }

    public abstract View getCustomView()

    public abstract Int getDisplayOptions()

    fun getElevation() {
        return 0.0f
    }

    public abstract Int getHeight()

    fun getHideOffset() {
        return 0
    }

    @Deprecated
    public abstract Int getNavigationItemCount()

    @Deprecated
    public abstract Int getNavigationMode()

    @Deprecated
    public abstract Int getSelectedNavigationIndex()

    @Nullable
    @Deprecated
    public abstract Tab getSelectedTab()

    @Nullable
    public abstract CharSequence getSubtitle()

    @Deprecated
    public abstract Tab getTabAt(Int i)

    @Deprecated
    public abstract Int getTabCount()

    fun getThemedContext() {
        return null
    }

    @Nullable
    public abstract CharSequence getTitle()

    public abstract Unit hide()

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun invalidateOptionsMenu() {
        return false
    }

    fun isHideOnContentScrollEnabled() {
        return false
    }

    public abstract Boolean isShowing()

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun isTitleTruncated() {
        return false
    }

    @Deprecated
    public abstract Tab newTab()

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun onConfigurationChanged(Configuration configuration) {
    }

    Unit onDestroy() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun onKeyShortcut(Int i, KeyEvent keyEvent) {
        return false
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun onMenuKeyEvent(KeyEvent keyEvent) {
        return false
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun openOptionsMenu() {
        return false
    }

    @Deprecated
    public abstract Unit removeAllTabs()

    public abstract Unit removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener)

    @Deprecated
    public abstract Unit removeTab(Tab tab)

    @Deprecated
    public abstract Unit removeTabAt(Int i)

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Boolean requestFocus() {
        return false
    }

    @Deprecated
    public abstract Unit selectTab(Tab tab)

    public abstract Unit setBackgroundDrawable(@Nullable Drawable drawable)

    public abstract Unit setCustomView(Int i)

    public abstract Unit setCustomView(View view)

    public abstract Unit setCustomView(View view, LayoutParams layoutParams)

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setDefaultDisplayHomeAsUpEnabled(Boolean z) {
    }

    public abstract Unit setDisplayHomeAsUpEnabled(Boolean z)

    public abstract Unit setDisplayOptions(Int i)

    public abstract Unit setDisplayOptions(Int i, Int i2)

    public abstract Unit setDisplayShowCustomEnabled(Boolean z)

    public abstract Unit setDisplayShowHomeEnabled(Boolean z)

    public abstract Unit setDisplayShowTitleEnabled(Boolean z)

    public abstract Unit setDisplayUseLogoEnabled(Boolean z)

    fun setElevation(Float f) {
        if (f != 0.0f) {
            throw UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.")
        }
    }

    fun setHideOffset(Int i) {
        if (i != 0) {
            throw UnsupportedOperationException("Setting an explicit action bar hide offset is not supported in this action bar configuration.")
        }
    }

    fun setHideOnContentScrollEnabled(Boolean z) {
        if (z) {
            throw UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.")
        }
    }

    fun setHomeActionContentDescription(@StringRes Int i) {
    }

    fun setHomeActionContentDescription(@Nullable CharSequence charSequence) {
    }

    fun setHomeAsUpIndicator(@DrawableRes Int i) {
    }

    fun setHomeAsUpIndicator(@Nullable Drawable drawable) {
    }

    fun setHomeButtonEnabled(Boolean z) {
    }

    public abstract Unit setIcon(@DrawableRes Int i)

    public abstract Unit setIcon(Drawable drawable)

    @Deprecated
    public abstract Unit setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener)

    public abstract Unit setLogo(@DrawableRes Int i)

    public abstract Unit setLogo(Drawable drawable)

    @Deprecated
    public abstract Unit setNavigationMode(Int i)

    @Deprecated
    public abstract Unit setSelectedNavigationItem(Int i)

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setShowHideAnimationEnabled(Boolean z) {
    }

    fun setSplitBackgroundDrawable(Drawable drawable) {
    }

    fun setStackedBackgroundDrawable(Drawable drawable) {
    }

    public abstract Unit setSubtitle(Int i)

    public abstract Unit setSubtitle(CharSequence charSequence)

    public abstract Unit setTitle(@StringRes Int i)

    public abstract Unit setTitle(CharSequence charSequence)

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setWindowTitle(CharSequence charSequence) {
    }

    public abstract Unit show()

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun startActionMode(ActionMode.Callback callback) {
        return null
    }
}
