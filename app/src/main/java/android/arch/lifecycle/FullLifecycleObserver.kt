package android.arch.lifecycle

interface FullLifecycleObserver extends LifecycleObserver {
    Unit onCreate(LifecycleOwner lifecycleOwner)

    Unit onDestroy(LifecycleOwner lifecycleOwner)

    Unit onPause(LifecycleOwner lifecycleOwner)

    Unit onResume(LifecycleOwner lifecycleOwner)

    Unit onStart(LifecycleOwner lifecycleOwner)

    Unit onStop(LifecycleOwner lifecycleOwner)
}
