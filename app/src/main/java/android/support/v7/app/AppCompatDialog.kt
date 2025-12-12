package android.support.v7.app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.KeyEventDispatcher
import androidx.appcompat.R
import android.support.v7.view.ActionMode
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup

class AppCompatDialog extends Dialog implements AppCompatCallback {
    private AppCompatDelegate mDelegate
    private final KeyEventDispatcher.Component mKeyDispatcher

    constructor(Context context) {
        this(context, 0)
    }

    constructor(Context context, Int i) {
        super(context, getThemeResId(context, i))
        this.mKeyDispatcher = new KeyEventDispatcher.Component() { // from class: android.support.v7.app.AppCompatDialog.1
            @Override // android.support.v4.view.KeyEventDispatcher.Component
            fun superDispatchKeyEvent(KeyEvent keyEvent) {
                return AppCompatDialog.this.superDispatchKeyEvent(keyEvent)
            }
        }
        getDelegate().onCreate(null)
        getDelegate().applyDayNight()
    }

    protected constructor(Context context, Boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener)
        this.mKeyDispatcher = new KeyEventDispatcher.Component() { // from class: android.support.v7.app.AppCompatDialog.1
            @Override // android.support.v4.view.KeyEventDispatcher.Component
            fun superDispatchKeyEvent(KeyEvent keyEvent) {
                return AppCompatDialog.this.superDispatchKeyEvent(keyEvent)
            }
        }
    }

    private fun getThemeResId(Context context, Int i) {
        if (i != 0) {
            return i
        }
        TypedValue typedValue = TypedValue()
        context.getTheme().resolveAttribute(R.attr.dialogTheme, typedValue, true)
        return typedValue.resourceId
    }

    @Override // android.app.Dialog
    fun addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getDelegate().addContentView(view, layoutParams)
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    fun dispatchKeyEvent(KeyEvent keyEvent) {
        return KeyEventDispatcher.dispatchKeyEvent(this.mKeyDispatcher, getWindow().getDecorView(), this, keyEvent)
    }

    @Override // android.app.Dialog
    @Nullable
    fun findViewById(@IdRes Int i) {
        return getDelegate().findViewById(i)
    }

    fun getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create(this, this)
        }
        return this.mDelegate
    }

    fun getSupportActionBar() {
        return getDelegate().getSupportActionBar()
    }

    @Override // android.app.Dialog
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu()
    }

    @Override // android.app.Dialog
    protected fun onCreate(Bundle bundle) {
        getDelegate().installViewFactory()
        super.onCreate(bundle)
        getDelegate().onCreate(bundle)
    }

    @Override // android.app.Dialog
    protected fun onStop() {
        super.onStop()
        getDelegate().onStop()
    }

    @Override // android.support.v7.app.AppCompatCallback
    fun onSupportActionModeFinished(ActionMode actionMode) {
    }

    @Override // android.support.v7.app.AppCompatCallback
    fun onSupportActionModeStarted(ActionMode actionMode) {
    }

    @Override // android.support.v7.app.AppCompatCallback
    @Nullable
    fun onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null
    }

    @Override // android.app.Dialog
    fun setContentView(@LayoutRes Int i) {
        getDelegate().setContentView(i)
    }

    @Override // android.app.Dialog
    fun setContentView(View view) {
        getDelegate().setContentView(view)
    }

    @Override // android.app.Dialog
    fun setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        getDelegate().setContentView(view, layoutParams)
    }

    @Override // android.app.Dialog
    fun setTitle(Int i) {
        super.setTitle(i)
        getDelegate().setTitle(getContext().getString(i))
    }

    @Override // android.app.Dialog
    fun setTitle(CharSequence charSequence) {
        super.setTitle(charSequence)
        getDelegate().setTitle(charSequence)
    }

    Boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent)
    }

    fun supportRequestWindowFeature(Int i) {
        return getDelegate().requestWindowFeature(i)
    }
}
