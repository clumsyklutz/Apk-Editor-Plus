package android.support.v7.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.view.ActionMode
import androidx.appcompat.widget.Toolbar
import android.support.v7.widget.VectorEnabledTintResources
import android.util.AttributeSet
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

abstract class AppCompatDelegate {
    public static val FEATURE_ACTION_MODE_OVERLAY = 10
    public static val FEATURE_SUPPORT_ACTION_BAR = 108
    public static val FEATURE_SUPPORT_ACTION_BAR_OVERLAY = 109
    public static val MODE_NIGHT_AUTO = 0
    public static val MODE_NIGHT_FOLLOW_SYSTEM = -1
    public static val MODE_NIGHT_NO = 1
    static val MODE_NIGHT_UNSPECIFIED = -100
    public static val MODE_NIGHT_YES = 2
    static val TAG = "AppCompatDelegate"
    private static Int sDefaultNightMode = -1

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface NightMode {
    }

    AppCompatDelegate() {
    }

    fun create(Activity activity, AppCompatCallback appCompatCallback) {
        return AppCompatDelegateImpl(activity, activity.getWindow(), appCompatCallback)
    }

    fun create(Dialog dialog, AppCompatCallback appCompatCallback) {
        return AppCompatDelegateImpl(dialog.getContext(), dialog.getWindow(), appCompatCallback)
    }

    fun create(Context context, Window window, AppCompatCallback appCompatCallback) {
        return AppCompatDelegateImpl(context, window, appCompatCallback)
    }

    fun getDefaultNightMode() {
        return sDefaultNightMode
    }

    fun isCompatVectorFromResourcesEnabled() {
        return VectorEnabledTintResources.isCompatVectorFromResourcesEnabled()
    }

    fun setCompatVectorFromResourcesEnabled(Boolean z) {
        VectorEnabledTintResources.setCompatVectorFromResourcesEnabled(z)
    }

    fun setDefaultNightMode(Int i) {
        switch (i) {
            case -1:
            case 0:
            case 1:
            case 2:
                sDefaultNightMode = i
                break
            default:
                Log.d(TAG, "setDefaultNightMode() called with an unknown mode")
                break
        }
    }

    public abstract Unit addContentView(View view, ViewGroup.LayoutParams layoutParams)

    public abstract Boolean applyDayNight()

    public abstract View createView(@Nullable View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet)

    @Nullable
    public abstract View findViewById(@IdRes Int i)

    @Nullable
    public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()

    public abstract MenuInflater getMenuInflater()

    @Nullable
    public abstract ActionBar getSupportActionBar()

    public abstract Boolean hasWindowFeature(Int i)

    public abstract Unit installViewFactory()

    public abstract Unit invalidateOptionsMenu()

    public abstract Boolean isHandleNativeActionModesEnabled()

    public abstract Unit onConfigurationChanged(Configuration configuration)

    public abstract Unit onCreate(Bundle bundle)

    public abstract Unit onDestroy()

    public abstract Unit onPostCreate(Bundle bundle)

    public abstract Unit onPostResume()

    public abstract Unit onSaveInstanceState(Bundle bundle)

    public abstract Unit onStart()

    public abstract Unit onStop()

    public abstract Boolean requestWindowFeature(Int i)

    public abstract Unit setContentView(@LayoutRes Int i)

    public abstract Unit setContentView(View view)

    public abstract Unit setContentView(View view, ViewGroup.LayoutParams layoutParams)

    public abstract Unit setHandleNativeActionModesEnabled(Boolean z)

    public abstract Unit setLocalNightMode(Int i)

    public abstract Unit setSupportActionBar(@Nullable Toolbar toolbar)

    public abstract Unit setTitle(@Nullable CharSequence charSequence)

    @Nullable
    public abstract ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback)
}
