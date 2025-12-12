package android.support.v4.app

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ReportFragment
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.util.SimpleArrayMap
import android.support.v4.view.KeyEventDispatcher
import android.view.KeyEvent
import android.view.View

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SupportActivity extends Activity implements LifecycleOwner, KeyEventDispatcher.Component {
    private SimpleArrayMap mExtraDataMap = SimpleArrayMap()
    private LifecycleRegistry mLifecycleRegistry = LifecycleRegistry(this)

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class ExtraData {
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun dispatchKeyEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView()
        if (decorView == null || !KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return KeyEventDispatcher.dispatchKeyEvent(this, decorView, this, keyEvent)
        }
        return true
    }

    @Override // android.app.Activity, android.view.Window.Callback
    fun dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView()
        if (decorView == null || !KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return super.dispatchKeyShortcutEvent(keyEvent)
        }
        return true
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun getExtraData(Class cls) {
        return (ExtraData) this.mExtraDataMap.get(cls)
    }

    fun getLifecycle() {
        return this.mLifecycleRegistry
    }

    @Override // android.app.Activity
    protected fun onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle)
        ReportFragment.injectIfNeededIn(this)
    }

    @Override // android.app.Activity
    @CallSuper
    protected fun onSaveInstanceState(Bundle bundle) {
        this.mLifecycleRegistry.markState(Lifecycle.State.CREATED)
        super.onSaveInstanceState(bundle)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun putExtraData(ExtraData extraData) {
        this.mExtraDataMap.put(extraData.getClass(), extraData)
    }

    @Override // android.support.v4.view.KeyEventDispatcher.Component
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun superDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent)
    }
}
