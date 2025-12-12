package android.arch.lifecycle

import android.arch.lifecycle.ClassesInfoCache
import android.arch.lifecycle.Lifecycle
import java.lang.reflect.InvocationTargetException

class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    private final ClassesInfoCache.CallbackInfo mInfo
    private final Object mWrapped

    ReflectiveGenericLifecycleObserver(Object obj) {
        this.mWrapped = obj
        this.mInfo = ClassesInfoCache.sInstance.getInfo(this.mWrapped.getClass())
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    fun onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mInfo.invokeCallbacks(lifecycleOwner, event, this.mWrapped)
    }
}
