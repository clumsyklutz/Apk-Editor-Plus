package android.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY})
public interface GenericLifecycleObserver extends LifecycleObserver {
    Unit onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event)
}
