package android.arch.lifecycle

import android.arch.core.internal.FastSafeIterableMap
import android.arch.core.internal.SafeIterableMap
import android.arch.lifecycle.Lifecycle
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.Log
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Iterator
import java.util.Map

class LifecycleRegistry extends Lifecycle {
    private static val LOG_TAG = "LifecycleRegistry"
    private final WeakReference mLifecycleOwner
    private FastSafeIterableMap mObserverMap = FastSafeIterableMap()
    private Int mAddingObserverCounter = 0
    private Boolean mHandlingEvent = false
    private Boolean mNewEventOccurred = false
    private ArrayList mParentStates = ArrayList()
    private Lifecycle.State mState = Lifecycle.State.INITIALIZED

    class ObserverWithState {
        GenericLifecycleObserver mLifecycleObserver
        Lifecycle.State mState

        ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            this.mLifecycleObserver = Lifecycling.getCallback(lifecycleObserver)
            this.mState = state
        }

        Unit dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State stateAfter = LifecycleRegistry.getStateAfter(event)
            this.mState = LifecycleRegistry.min(this.mState, stateAfter)
            this.mLifecycleObserver.onStateChanged(lifecycleOwner, event)
            this.mState = stateAfter
        }
    }

    constructor(@NonNull LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = WeakReference(lifecycleOwner)
    }

    private fun backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator itDescendingIterator = this.mObserverMap.descendingIterator()
        while (itDescendingIterator.hasNext() && !this.mNewEventOccurred) {
            Map.Entry entry = (Map.Entry) itDescendingIterator.next()
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue()
            while (observerWithState.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
                Lifecycle.Event eventDownEvent = downEvent(observerWithState.mState)
                pushParentState(getStateAfter(eventDownEvent))
                observerWithState.dispatchEvent(lifecycleOwner, eventDownEvent)
                popParentState()
            }
        }
    }

    private Lifecycle.State calculateTargetState(LifecycleObserver lifecycleObserver) {
        Map.Entry entryCeil = this.mObserverMap.ceil(lifecycleObserver)
        return min(min(this.mState, entryCeil != null ? ((ObserverWithState) entryCeil.getValue()).mState : null), !this.mParentStates.isEmpty() ? (Lifecycle.State) this.mParentStates.get(this.mParentStates.size() - 1) : null)
    }

    private static Lifecycle.Event downEvent(Lifecycle.State state) {
        switch (state) {
            case INITIALIZED:
                throw IllegalArgumentException()
            case CREATED:
                return Lifecycle.Event.ON_DESTROY
            case STARTED:
                return Lifecycle.Event.ON_STOP
            case RESUMED:
                return Lifecycle.Event.ON_PAUSE
            case DESTROYED:
                throw IllegalArgumentException()
            default:
                throw IllegalArgumentException("Unexpected state value " + state)
        }
    }

    private fun forwardPass(LifecycleOwner lifecycleOwner) {
        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mObserverMap.iteratorWithAdditions()
        while (iteratorWithAdditions.hasNext() && !this.mNewEventOccurred) {
            Map.Entry entry = (Map.Entry) iteratorWithAdditions.next()
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue()
            while (observerWithState.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred && this.mObserverMap.contains(entry.getKey())) {
                pushParentState(observerWithState.mState)
                observerWithState.dispatchEvent(lifecycleOwner, upEvent(observerWithState.mState))
                popParentState()
            }
        }
    }

    static Lifecycle.State getStateAfter(Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_STOP:
                return Lifecycle.State.CREATED
            case ON_START:
            case ON_PAUSE:
                return Lifecycle.State.STARTED
            case ON_RESUME:
                return Lifecycle.State.RESUMED
            case ON_DESTROY:
                return Lifecycle.State.DESTROYED
            default:
                throw IllegalArgumentException("Unexpected event value " + event)
        }
    }

    private fun isSynced() {
        if (this.mObserverMap.size() == 0) {
            return true
        }
        Lifecycle.State state = ((ObserverWithState) this.mObserverMap.eldest().getValue()).mState
        Lifecycle.State state2 = ((ObserverWithState) this.mObserverMap.newest().getValue()).mState
        return state == state2 && this.mState == state2
    }

    static Lifecycle.State min(@NonNull Lifecycle.State state, @Nullable Lifecycle.State state2) {
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2
    }

    private fun moveToState(Lifecycle.State state) {
        if (this.mState == state) {
            return
        }
        this.mState = state
        if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
            this.mNewEventOccurred = true
            return
        }
        this.mHandlingEvent = true
        sync()
        this.mHandlingEvent = false
    }

    private fun popParentState() {
        this.mParentStates.remove(this.mParentStates.size() - 1)
    }

    private fun pushParentState(Lifecycle.State state) {
        this.mParentStates.add(state)
    }

    private fun sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get()
        if (lifecycleOwner == null) {
            Log.w(LOG_TAG, "LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.")
            return
        }
        while (!isSynced()) {
            this.mNewEventOccurred = false
            if (this.mState.compareTo(((ObserverWithState) this.mObserverMap.eldest().getValue()).mState) < 0) {
                backwardPass(lifecycleOwner)
            }
            Map.Entry entryNewest = this.mObserverMap.newest()
            if (!this.mNewEventOccurred && entryNewest != null && this.mState.compareTo(((ObserverWithState) entryNewest.getValue()).mState) > 0) {
                forwardPass(lifecycleOwner)
            }
        }
        this.mNewEventOccurred = false
    }

    private static Lifecycle.Event upEvent(Lifecycle.State state) {
        switch (state) {
            case INITIALIZED:
            case DESTROYED:
                return Lifecycle.Event.ON_CREATE
            case CREATED:
                return Lifecycle.Event.ON_START
            case STARTED:
                return Lifecycle.Event.ON_RESUME
            case RESUMED:
                throw IllegalArgumentException()
            default:
                throw IllegalArgumentException("Unexpected state value " + state)
        }
    }

    @Override // android.arch.lifecycle.Lifecycle
    fun addObserver(@NonNull LifecycleObserver lifecycleObserver) {
        LifecycleOwner lifecycleOwner
        ObserverWithState observerWithState = ObserverWithState(lifecycleObserver, this.mState == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED)
        if (((ObserverWithState) this.mObserverMap.putIfAbsent(lifecycleObserver, observerWithState)) == null && (lifecycleOwner = (LifecycleOwner) this.mLifecycleOwner.get()) != null) {
            Boolean z = this.mAddingObserverCounter != 0 || this.mHandlingEvent
            Lifecycle.State stateCalculateTargetState = calculateTargetState(lifecycleObserver)
            this.mAddingObserverCounter++
            while (observerWithState.mState.compareTo(stateCalculateTargetState) < 0 && this.mObserverMap.contains(lifecycleObserver)) {
                pushParentState(observerWithState.mState)
                observerWithState.dispatchEvent(lifecycleOwner, upEvent(observerWithState.mState))
                popParentState()
                stateCalculateTargetState = calculateTargetState(lifecycleObserver)
            }
            if (!z) {
                sync()
            }
            this.mAddingObserverCounter--
        }
    }

    @Override // android.arch.lifecycle.Lifecycle
    @NonNull
    public Lifecycle.State getCurrentState() {
        return this.mState
    }

    fun getObserverCount() {
        return this.mObserverMap.size()
    }

    fun handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        moveToState(getStateAfter(event))
    }

    @MainThread
    fun markState(@NonNull Lifecycle.State state) {
        moveToState(state)
    }

    @Override // android.arch.lifecycle.Lifecycle
    fun removeObserver(@NonNull LifecycleObserver lifecycleObserver) {
        this.mObserverMap.remove(lifecycleObserver)
    }
}
