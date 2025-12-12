package android.arch.lifecycle

import android.support.annotation.MainThread
import android.support.annotation.NonNull

abstract class Lifecycle {

    public enum Event {
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        ON_ANY
    }

    public enum State {
        DESTROYED,
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED

        public final Boolean isAtLeast(@NonNull State state) {
            return compareTo(state) >= 0
        }
    }

    @MainThread
    public abstract Unit addObserver(@NonNull LifecycleObserver lifecycleObserver)

    @NonNull
    @MainThread
    public abstract State getCurrentState()

    @MainThread
    public abstract Unit removeObserver(@NonNull LifecycleObserver lifecycleObserver)
}
