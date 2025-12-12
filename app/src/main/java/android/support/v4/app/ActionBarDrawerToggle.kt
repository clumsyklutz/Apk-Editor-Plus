package android.support.v4.app

import android.R
import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

@Deprecated
class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    private static val ID_HOME = 16908332
    private static val TAG = "ActionBarDrawerToggle"
    private static final Array<Int> THEME_ATTRS = {R.attr.homeAsUpIndicator}
    private static val TOGGLE_DRAWABLE_OFFSET = 0.33333334f
    final Activity mActivity
    private final Delegate mActivityImpl
    private final Int mCloseDrawerContentDescRes
    private Drawable mDrawerImage
    private final Int mDrawerImageResource
    private Boolean mDrawerIndicatorEnabled
    private final DrawerLayout mDrawerLayout
    private Boolean mHasCustomUpIndicator
    private Drawable mHomeAsUpIndicator
    private final Int mOpenDrawerContentDescRes
    private SetIndicatorInfo mSetIndicatorInfo
    private SlideDrawable mSlider

    @Deprecated
    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator()

        Unit setActionBarDescription(@StringRes Int i)

        Unit setActionBarUpIndicator(Drawable drawable, @StringRes Int i)
    }

    @Deprecated
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate()
    }

    class SetIndicatorInfo {
        Method mSetHomeActionContentDescription
        Method mSetHomeAsUpIndicator
        ImageView mUpIndicatorView

        SetIndicatorInfo(Activity activity) {
            try {
                this.mSetHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class)
                this.mSetHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE)
            } catch (NoSuchMethodException e) {
                View viewFindViewById = activity.findViewById(16908332)
                if (viewFindViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent()
                    if (viewGroup.getChildCount() == 2) {
                        View childAt = viewGroup.getChildAt(0)
                        View childAt2 = childAt.getId() != 16908332 ? childAt : viewGroup.getChildAt(1)
                        if (childAt2 is ImageView) {
                            this.mUpIndicatorView = (ImageView) childAt2
                        }
                    }
                }
            }
        }
    }

    class SlideDrawable extends InsetDrawable implements Drawable.Callback {
        private final Boolean mHasMirroring
        private Float mOffset
        private Float mPosition
        private final Rect mTmpRect

        SlideDrawable(Drawable drawable) {
            super(drawable, 0)
            this.mHasMirroring = Build.VERSION.SDK_INT > 18
            this.mTmpRect = Rect()
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        fun draw(@NonNull Canvas canvas) {
            copyBounds(this.mTmpRect)
            canvas.save()
            Boolean z = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.mActivity.getWindow().getDecorView()) == 1
            Int i = z ? -1 : 1
            Int iWidth = this.mTmpRect.width()
            canvas.translate(i * (-this.mOffset) * iWidth * this.mPosition, 0.0f)
            if (z && !this.mHasMirroring) {
                canvas.translate(iWidth, 0.0f)
                canvas.scale(-1.0f, 1.0f)
            }
            super.draw(canvas)
            canvas.restore()
        }

        fun getPosition() {
            return this.mPosition
        }

        fun setOffset(Float f) {
            this.mOffset = f
            invalidateSelf()
        }

        fun setPosition(Float f) {
            this.mPosition = f
            invalidateSelf()
        }
    }

    constructor(Activity activity, DrawerLayout drawerLayout, @DrawableRes Int i, @StringRes Int i2, @StringRes Int i3) {
        this(activity, drawerLayout, !assumeMaterial(activity), i, i2, i3)
    }

    /* JADX WARN: Multi-variable type inference failed */
    constructor(Activity activity, DrawerLayout drawerLayout, Boolean z, @DrawableRes Int i, @StringRes Int i2, @StringRes Int i3) {
        this.mDrawerIndicatorEnabled = true
        this.mActivity = activity
        if (activity is DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) activity).getDrawerToggleDelegate()
        } else {
            this.mActivityImpl = null
        }
        this.mDrawerLayout = drawerLayout
        this.mDrawerImageResource = i
        this.mOpenDrawerContentDescRes = i2
        this.mCloseDrawerContentDescRes = i3
        this.mHomeAsUpIndicator = getThemeUpIndicator()
        this.mDrawerImage = ContextCompat.getDrawable(activity, i)
        this.mSlider = SlideDrawable(this.mDrawerImage)
        this.mSlider.setOffset(z ? TOGGLE_DRAWABLE_OFFSET : 0.0f)
    }

    private fun assumeMaterial(Context context) {
        return context.getApplicationInfo().targetSdkVersion >= 21 && Build.VERSION.SDK_INT >= 21
    }

    private fun getThemeUpIndicator() {
        if (this.mActivityImpl != null) {
            return this.mActivityImpl.getThemeUpIndicator()
        }
        if (Build.VERSION.SDK_INT < 18) {
            TypedArray typedArrayObtainStyledAttributes = this.mActivity.obtainStyledAttributes(THEME_ATTRS)
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0)
            typedArrayObtainStyledAttributes.recycle()
            return drawable
        }
        ActionBar actionBar = this.mActivity.getActionBar()
        TypedArray typedArrayObtainStyledAttributes2 = (actionBar != null ? actionBar.getThemedContext() : this.mActivity).obtainStyledAttributes(null, THEME_ATTRS, R.attr.actionBarStyle, 0)
        Drawable drawable2 = typedArrayObtainStyledAttributes2.getDrawable(0)
        typedArrayObtainStyledAttributes2.recycle()
        return drawable2
    }

    private fun setActionBarDescription(Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarDescription(i)
            return
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar()
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i)
                return
            }
            return
        }
        if (this.mSetIndicatorInfo == null) {
            this.mSetIndicatorInfo = SetIndicatorInfo(this.mActivity)
        }
        if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar2 = this.mActivity.getActionBar()
                this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(actionBar2, Integer.valueOf(i))
                actionBar2.setSubtitle(actionBar2.getSubtitle())
            } catch (Exception e) {
                Log.w(TAG, "Couldn't set content description via JB-MR2 API", e)
            }
        }
    }

    private fun setActionBarUpIndicator(Drawable drawable, Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarUpIndicator(drawable, i)
            return
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.mActivity.getActionBar()
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable)
                actionBar.setHomeActionContentDescription(i)
                return
            }
            return
        }
        if (this.mSetIndicatorInfo == null) {
            this.mSetIndicatorInfo = SetIndicatorInfo(this.mActivity)
        }
        if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator == null) {
            if (this.mSetIndicatorInfo.mUpIndicatorView != null) {
                this.mSetIndicatorInfo.mUpIndicatorView.setImageDrawable(drawable)
                return
            } else {
                Log.w(TAG, "Couldn't set home-as-up indicator")
                return
            }
        }
        try {
            ActionBar actionBar2 = this.mActivity.getActionBar()
            this.mSetIndicatorInfo.mSetHomeAsUpIndicator.invoke(actionBar2, drawable)
            this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(actionBar2, Integer.valueOf(i))
        } catch (Exception e) {
            Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e)
        }
    }

    fun isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled
    }

    fun onConfigurationChanged(Configuration configuration) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator()
        }
        this.mDrawerImage = ContextCompat.getDrawable(this.mActivity, this.mDrawerImageResource)
        syncState()
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerClosed(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mSlider.setPosition(0.0f)
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes)
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerOpened(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mSlider.setPosition(1.0f)
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes)
        }
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerSlide(View view, Float f) {
        Float position = this.mSlider.getPosition()
        this.mSlider.setPosition(f > 0.5f ? Math.max(position, Math.max(0.0f, f - 0.5f) * 2.0f) : Math.min(position, f * 2.0f))
    }

    @Override // android.support.v4.widget.DrawerLayout.DrawerListener
    fun onDrawerStateChanged(Int i) {
    }

    fun onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.mDrawerIndicatorEnabled) {
            return false
        }
        if (this.mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            this.mDrawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    fun setDrawerIndicatorEnabled(Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (z != this.mDrawerIndicatorEnabled) {
            if (z) {
                setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes)
            } else {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0)
            }
            this.mDrawerIndicatorEnabled = z
        }
    }

    fun setHomeAsUpIndicator(Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setHomeAsUpIndicator(i != 0 ? ContextCompat.getDrawable(this.mActivity, i) : null)
    }

    fun setHomeAsUpIndicator(Drawable drawable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

    fun syncState() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mSlider.setPosition(1.0f)
        } else {
            this.mSlider.setPosition(0.0f)
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes)
        }
    }
}
