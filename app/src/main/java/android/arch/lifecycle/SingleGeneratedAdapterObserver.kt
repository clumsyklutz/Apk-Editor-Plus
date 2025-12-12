package android.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {
    private final GeneratedAdapter mGeneratedAdapter

    SingleGeneratedAdapterObserver(GeneratedAdapter generatedAdapter) {
        this.mGeneratedAdapter = generatedAdapter
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    fun onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        this.mGeneratedAdapter.callMethods(lifecycleOwner, event, false, null)
        this.mGeneratedAdapter.callMethods(lifecycleOwner, event, true, null)
    }
}
