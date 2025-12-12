package android.support.v7.app

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggleHoneycomb
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View

class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    private final Delegate mActivityImpl
    private final Int mCloseDrawerContentDescRes
    Boolean mDrawerIndicatorEnabled
    private final DrawerLayout mDrawerLayout
    private Boolean mDrawerSlideAnimationEnabled
    private Boolean mHasCustomUpIndicator
    private Drawable mHomeAsUpIndicator
    private final Int mOpenDrawerContentDescRes
    private DrawerArrowDrawable mSlider
    View.OnClickListener mToolbarNavigationClickListener
    private Boolean mWarnedForDisplayHomeAsUp

    public interface Delegate {
        Context getActionBarThemedContext()

        Drawable getThemeUpIndicator()

        Boolean isNavigationVisible()

        Unit setActionBarDescription(@StringRes Int i)

        Unit setActionBarUpIndicator(Drawable drawable, @StringRes Int i)
    }

    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate()
    }

    class FrameworkActionBarDelegate implements Delegate {
        private final Activity mActivity
        private ActionBarDrawerToggleHoneycomb.SetIndicatorInfo mSetIndicatorInfo

        FrameworkActionBarDelegate(Activity activity) {
            this.mActivity = activity
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun getActionBarThemedContext() {
            android.app.ActionBar actionBar = this.mActivity.getActionBar()
            return actionBar != null ? actionBar.getThemedContext() : this.mActivity
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun getThemeUpIndicator() {
            if (Build.VERSION.SDK_INT < 18) {
                return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity)
            }
            TypedArray typedArrayObtainStyledAttributes = getActionBarThemedContext().obtainStyledAttributes(null, new Array<Int>{R.attr.homeAsUpIndicator}, R.attr.actionBarStyle, 0)
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0)
            typedArrayObtainStyledAttributes.recycle()
            return drawable
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun isNavigationVisible() {
            android.app.ActionBar actionBar = this.mActivity.getActionBar()
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun setActionBarDescription(Int i) {
            if (Build.VERSION.SDK_INT < 18) {
                this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, i)
                return
            }
            android.app.ActionBar actionBar = this.mActivity.getActionBar()
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i)
            }
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun setActionBarUpIndicator(Drawable drawable, Int i) {
            android.app.ActionBar actionBar = this.mActivity.getActionBar()
            if (actionBar != null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    actionBar.setHomeAsUpIndicator(drawable)
                    actionBar.setHomeActionContentDescription(i)
                } else {
                    actionBar.setDisplayShowHomeEnabled(true)
                    this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, drawable, i)
                    actionBar.setDisplayShowHomeEnabled(false)
                }
            }
        }
    }

    class ToolbarCompatDelegate implements Delegate {
        final CharSequence mDefaultContentDescription
        final Drawable mDefaultUpIndicator
        final Toolbar mToolbar

        ToolbarCompatDelegate(Toolbar toolbar) {
            this.mToolbar = toolbar
            this.mDefaultUpIndicator = toolbar.getNavigationIcon()
            this.mDefaultContentDescription = toolbar.getNavigationContentDescription()
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun getActionBarThemedContext() {
            return this.mToolbar.getContext()
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun getThemeUpIndicator() {
            return this.mDefaultUpIndicator
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun isNavigationVisible() {
            return true
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun setActionBarDescription(@StringRes Int i) {
            if (i == 0) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription)
            } else {
                this.mToolbar.setNavigationContentDescription(i)
            }
        }

        @Override // android.support.v7.app.ActionBarDrawerToggle.Delegate
        fun setActionBarUpIndicator(Drawable drawable, @StringRes Int i) {
            this.mToolbar.setNavigationIcon(drawable)
            setActionBarDescription(i)
        }
    }

    constructor(Activity activity, DrawerLayout drawerLayout, @StringRes Int i, @StringRes Int i2) {
        this(activity, null, drawerLayout, null, i, i2)
    }

    constructor(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes Int i, @StringRes Int i2) {
        this(activity, toolbar, drawerLayout, null, i, i2)
    }

    /* JADX WARN: Multi-variable type inference failed */
    ActionBarDrawerToggle(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable drawerArrowDrawable, @StringRes Int i, @StringRes Int i2) {
        this.mDrawerSlideAnimationEnabled = true
        this.mDrawerIndicatorEnabled = true
        this.mWarnedForDisplayHomeAsUp = false
        if (toolbar != null) {
            this.mActivityImpl = ToolbarCompatDelegate(toolbar)
            toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: android.support.v7.app.ActionBarDrawerToggle.1
                @Override // android.view.View.OnClickListener
                fun onClick(View view) {
                    if (ActionBarDrawerToggle.this.mDrawerIndicatorEnabled) {
                        ActionBarDrawerToggle.this.toggle()
                    } else if (ActionBarDrawerToggle.this.mToolbarNavigationClickListener != null) {
                        ActionBarDrawerToggle.this.mToolbarNavigationClickListener.onClick(view)
                    }
                }
            })
        } else if (activity is DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) activity).getDrawerToggleDelegate()
        } else {
            this.mActivityImpl = FrameworkActionBarDelegate(activity)
        }
        this.mDrawerLayout = drawerLayout
        this.mOpenDrawerContentDescRes = i
        this.mCloseDrawerContentDescRes = i2
        if (drawerArrowDrawable == null) {
            this.mSlider = DrawerArrowDrawable(this.mActivityImpl.getActionBarThemedContext())
        } else {
            this.mSlider = drawerArrowDrawable
        }
        this.mHomeAsUpIndicator = getThemeUpIndicator()
    }

    private fun setPosition(Float f) {
        if (f == 1.0f) {
            this.mSlider.setVerticalMirror(true)
        } else if (f == 0.0f) {
            this.mSlider.setVerticalMirror(false)
        }
        this.mSlider.setProgress(f)
    }

    @NonNull
    fun getDrawerArrowDrawable() {
        return this.mSlider
    }

    Drawable getThemeUpIndicator() {
        return this.mActivityImpl.getThemeUpIndicator()
    }

    public View.OnClickListener getToolbarNavigationClickListener() {
        return this.mToolbarNavigationClickListener
    }

    fun isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled
    }

    fun isDrawerSlideAnimationEnabled() {
        return this.mDrawerSlideAnimationEnabled
    }

    fun onConfigurationChanged(Configuration configuration) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator()
        }
        syncState()
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerClosed(View view) {
        setPosition(0.0f)
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes)
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerOpened(View view) {
        setPosition(1.0f)
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes)
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerSlide(View view, Float f) {
        if (this.mDrawerSlideAnimationEnabled) {
            setPosition(Math.min(1.0f, Math.max(0.0f, f)))
        } else {
            setPosition(0.0f)
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerStateChanged(Int i) {
    }

    fun onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.mDrawerIndicatorEnabled) {
            return false
        }
        toggle()
        return true
    }

    Unit setActionBarDescription(Int i) {
        this.mActivityImpl.setActionBarDescription(i)
    }

    Unit setActionBarUpIndicator(Drawable drawable, Int i) {
        if (!this.mWarnedForDisplayHomeAsUp && !this.mActivityImpl.isNavigationVisible()) {
            Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);")
            this.mWarnedForDisplayHomeAsUp = true
        }
        this.mActivityImpl.setActionBarUpIndicator(drawable, i)
    }

    fun setDrawerArrowDrawable(@NonNull DrawerArrowDrawable drawerArrowDrawable) {
        this.mSlider = drawerArrowDrawable
        syncState()
    }

    fun setDrawerIndicatorEnabled(Boolean z) {
        if (z != this.mDrawerIndicatorEnabled) {
            if (z) {
                setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes)
            } else {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0)
            }
            this.mDrawerIndicatorEnabled = z
        }
    }

    fun setDrawerSlideAnimationEnabled(Boolean z) {
        this.mDrawerSlideAnimationEnabled = z
        if (z) {
            return
        }
        setPosition(0.0f)
    }

    fun setHomeAsUpIndicator(Int i) {
        setHomeAsUpIndicator(i != 0 ? this.mDrawerLayout.getResources().getDrawable(i) : null)
    }

    fun setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.mHomeAsUpIndicator = getThemeUpIndicator()
            this.mHasCustomUpIndicator = false
        } else {
            this.mHomeAsUpIndicator = drawable
            this.mHasCustomUpIndicator = true
        }
        if (this.mDrawerIndicatorEnabled) {
            return
        }
        setActionBarUpIndicator(this.mHomeAsUpIndicator, 0)
    }

    fun setToolbarNavigationClickListener(View.OnClickListener onClickListener) {
        this.mToolbarNavigationClickListener = onClickListener
    }

    fun syncState() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            setPosition(1.0f)
        } else {
            setPosition(0.0f)
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes)
        }
    }

    Unit toggle() {
        Int drawerLockMode = this.mDrawerLayout.getDrawerLockMode(GravityCompat.START)
        if (this.mDrawerLayout.isDrawerVisible(GravityCompat.START) && drawerLockMode != 2) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START)
        } else if (drawerLockMode != 1) {
            this.mDrawerLayout.openDrawer(GravityCompat.START)
        }
    }
}
