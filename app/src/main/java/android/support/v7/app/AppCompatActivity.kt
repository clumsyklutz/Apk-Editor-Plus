package android.support.v7.app

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.StyleRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import android.support.v4.app.NavUtils
import androidx.core.app.TaskStackBuilder
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.view.ActionMode
import androidx.appcompat.widget.Toolbar
import android.support.v7.widget.VectorEnabledTintResources
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window

class AppCompatActivity extends FragmentActivity implements TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider, AppCompatCallback {
    private AppCompatDelegate mDelegate
    private Resources mResources
    private Int mThemeId = 0

    private fun performMenuItemShortcut(Int i, KeyEvent keyEvent) {
        Window window
        return (Build.VERSION.SDK_INT >= 26 || keyEvent.isCtrlPressed() || KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState()) || keyEvent.getRepeatCount() != 0 || KeyEvent.isModifierKey(keyEvent.getKeyCode()) || (window = getWindow()) == null || window.getDecorView() == null || !window.getDecorView().dispatchKeyShortcutEvent(keyEvent)) ? false : true
    }

    @Override // android.app.Activity
    fun addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getDelegate().addContentView(view, layoutParams)
    }

    @Override // android.app.Activity
    fun closeOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar()
        if (getWindow().hasFeature(0)) {
            if (supportActionBar == null || !supportActionBar.closeOptionsMenu()) {
                super.closeOptionsMenu()
            }
        }
    }

    @Override // android.support.v4.app.SupportActivity, android.app.Activity, android.view.Window.Callback
    fun dispatchKeyEvent(KeyEvent keyEvent) {
        Int keyCode = keyEvent.getKeyCode()
        ActionBar supportActionBar = getSupportActionBar()
        if (keyCode == 82 && supportActionBar != null && supportActionBar.onMenuKeyEvent(keyEvent)) {
            return true
        }
        return super.dispatchKeyEvent(keyEvent)
    }

    @Override // android.app.Activity
    fun findViewById(@IdRes Int i) {
        return getDelegate().findViewById(i)
    }

    @NonNull
    fun getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create(this, this)
        }
        return this.mDelegate
    }

    @Override // android.support.v7.app.ActionBarDrawerToggle.DelegateProvider
    @Nullable
    public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return getDelegate().getDrawerToggleDelegate()
    }

    @Override // android.app.Activity
    fun getMenuInflater() {
        return getDelegate().getMenuInflater()
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    fun getResources() {
        if (this.mResources == null && VectorEnabledTintResources.shouldBeUsed()) {
            this.mResources = VectorEnabledTintResources(this, super.getResources())
        }
        return this.mResources == null ? super.getResources() : this.mResources
    }

    @Nullable
    fun getSupportActionBar() {
        return getDelegate().getSupportActionBar()
    }

    @Override // android.support.v4.app.TaskStackBuilder.SupportParentable
    @Nullable
    fun getSupportParentActivityIntent() {
        return NavUtils.getParentActivityIntent(this)
    }

    @Override // android.app.Activity
    fun invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu()
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    fun onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration)
        getDelegate().onConfigurationChanged(configuration)
        if (this.mResources != null) {
            this.mResources.updateConfiguration(configuration, super.getResources().getDisplayMetrics())
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun onContentChanged() {
        onSupportContentChanged()
    }

    @Override // android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected fun onCreate(@Nullable Bundle bundle) {
        AppCompatDelegate delegate = getDelegate()
        delegate.installViewFactory()
        delegate.onCreate(bundle)
        if (delegate.applyDayNight() && this.mThemeId != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                onApplyThemeResource(getTheme(), this.mThemeId, false)
            } else {
                setTheme(this.mThemeId)
            }
        }
        super.onCreate(bundle)
    }

    fun onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder taskStackBuilder) {
        taskStackBuilder.addParentStack(this)
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected fun onDestroy() {
        super.onDestroy()
        getDelegate().onDestroy()
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    fun onKeyDown(Int i, KeyEvent keyEvent) {
        if (performMenuItemShortcut(i, keyEvent)) {
            return true
        }
        return super.onKeyDown(i, keyEvent)
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
    public final Boolean onMenuItemSelected(Int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true
        }
        ActionBar supportActionBar = getSupportActionBar()
        if (menuItem.getItemId() != 16908332 || supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) {
            return false
        }
        return onSupportNavigateUp()
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun onMenuOpened(Int i, Menu menu) {
        return super.onMenuOpened(i, menu)
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
    fun onPanelClosed(Int i, Menu menu) {
        super.onPanelClosed(i, menu)
    }

    @Override // android.app.Activity
    protected fun onPostCreate(@Nullable Bundle bundle) {
        super.onPostCreate(bundle)
        getDelegate().onPostCreate(bundle)
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected fun onPostResume() {
        super.onPostResume()
        getDelegate().onPostResume()
    }

    fun onPrepareSupportNavigateUpTaskStack(@NonNull TaskStackBuilder taskStackBuilder) {
    }

    @Override // android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected fun onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle)
        getDelegate().onSaveInstanceState(bundle)
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected fun onStart() {
        super.onStart()
        getDelegate().onStart()
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected fun onStop() {
        super.onStop()
        getDelegate().onStop()
    }

    @Override // android.support.v7.app.AppCompatCallback
    @CallSuper
    fun onSupportActionModeFinished(@NonNull ActionMode actionMode) {
    }

    @Override // android.support.v7.app.AppCompatCallback
    @CallSuper
    fun onSupportActionModeStarted(@NonNull ActionMode actionMode) {
    }

    @Deprecated
    fun onSupportContentChanged() {
    }

    fun onSupportNavigateUp() {
        Intent supportParentActivityIntent = getSupportParentActivityIntent()
        if (supportParentActivityIntent == null) {
            return false
        }
        if (supportShouldUpRecreateTask(supportParentActivityIntent)) {
            TaskStackBuilder taskStackBuilderCreate = TaskStackBuilder.create(this)
            onCreateSupportNavigateUpTaskStack(taskStackBuilderCreate)
            onPrepareSupportNavigateUpTaskStack(taskStackBuilderCreate)
            taskStackBuilderCreate.startActivities()
            try {
                ActivityCompat.finishAffinity(this)
            } catch (IllegalStateException e) {
                finish()
            }
        } else {
            supportNavigateUpTo(supportParentActivityIntent)
        }
        return true
    }

    @Override // android.app.Activity
    protected fun onTitleChanged(CharSequence charSequence, Int i) {
        super.onTitleChanged(charSequence, i)
        getDelegate().setTitle(charSequence)
    }

    @Override // android.support.v7.app.AppCompatCallback
    @Nullable
    fun onWindowStartingSupportActionMode(@NonNull ActionMode.Callback callback) {
        return null
    }

    @Override // android.app.Activity
    fun openOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar()
        if (getWindow().hasFeature(0)) {
            if (supportActionBar == null || !supportActionBar.openOptionsMenu()) {
                super.openOptionsMenu()
            }
        }
    }

    @Override // android.app.Activity
    fun setContentView(@LayoutRes Int i) {
        getDelegate().setContentView(i)
    }

    @Override // android.app.Activity
    fun setContentView(View view) {
        getDelegate().setContentView(view)
    }

    @Override // android.app.Activity
    fun setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getDelegate().setContentView(view, layoutParams)
    }

    fun setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar)
    }

    @Deprecated
    fun setSupportProgress(Int i) {
    }

    @Deprecated
    fun setSupportProgressBarIndeterminate(Boolean z) {
    }

    @Deprecated
    fun setSupportProgressBarIndeterminateVisibility(Boolean z) {
    }

    @Deprecated
    fun setSupportProgressBarVisibility(Boolean z) {
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    fun setTheme(@StyleRes Int i) {
        super.setTheme(i)
        this.mThemeId = i
    }

    @Nullable
    fun startSupportActionMode(@NonNull ActionMode.Callback callback) {
        return getDelegate().startSupportActionMode(callback)
    }

    @Override // android.support.v4.app.FragmentActivity
    fun supportInvalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu()
    }

    fun supportNavigateUpTo(@NonNull Intent intent) {
        NavUtils.navigateUpTo(this, intent)
    }

    fun supportRequestWindowFeature(Int i) {
        return getDelegate().requestWindowFeature(i)
    }

    fun supportShouldUpRecreateTask(@NonNull Intent intent) {
        return NavUtils.shouldUpRecreateTask(this, intent)
    }
}
