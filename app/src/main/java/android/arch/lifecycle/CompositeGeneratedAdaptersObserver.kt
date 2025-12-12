package android.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class CompositeGeneratedAdaptersObserver implements GenericLifecycleObserver {
    private final Array<GeneratedAdapter> mGeneratedAdapters

    CompositeGeneratedAdaptersObserver(Array<GeneratedAdapter> generatedAdapterArr) {
        this.mGeneratedAdapters = generatedAdapterArr
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    fun onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        MethodCallsLogger methodCallsLogger = MethodCallsLogger()
        for (GeneratedAdapter generatedAdapter : this.mGeneratedAdapters) {
            generatedAdapter.callMethods(lifecycleOwner, event, false, methodCallsLogger)
        }
        for (GeneratedAdapter generatedAdapter2 : this.mGeneratedAdapters) {
            generatedAdapter2.callMethods(lifecycleOwner, event, true, methodCallsLogger)
        }
    }
}
