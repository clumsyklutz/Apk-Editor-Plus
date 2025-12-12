package android.arch.lifecycle

import android.arch.core.internal.SafeIterableMap
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import java.util.Iterator
import java.util.Map

class MediatorLiveData extends MutableLiveData {
    private SafeIterableMap mSources = SafeIterableMap()

    class Source implements Observer {
        final LiveData mLiveData
        final Observer mObserver
        Int mVersion = -1

        Source(LiveData liveData, Observer observer) {
            this.mLiveData = liveData
            this.mObserver = observer
        }

        @Override // android.arch.lifecycle.Observer
        fun onChanged(@Nullable Object obj) {
            if (this.mVersion != this.mLiveData.getVersion()) {
                this.mVersion = this.mLiveData.getVersion()
                this.mObserver.onChanged(obj)
            }
        }

        Unit plug() {
            this.mLiveData.observeForever(this)
        }

        Unit unplug() {
            this.mLiveData.removeObserver(this)
        }
    }

    @MainThread
    fun addSource(@NonNull LiveData liveData, @NonNull Observer observer) {
        Source source = Source(liveData, observer)
        Source source2 = (Source) this.mSources.putIfAbsent(liveData, source)
        if (source2 != null && source2.mObserver != observer) {
            throw IllegalArgumentException("This source was already added with the different observer")
        }
        if (source2 == null && hasActiveObservers()) {
            source.plug()
        }
    }

    @Override // android.arch.lifecycle.LiveData
    @CallSuper
    protected fun onActive() {
        Iterator it = this.mSources.iterator()
        while (it.hasNext()) {
            ((Source) ((Map.Entry) it.next()).getValue()).plug()
        }
    }

    @Override // android.arch.lifecycle.LiveData
    @CallSuper
    protected fun onInactive() {
        Iterator it = this.mSources.iterator()
        while (it.hasNext()) {
            ((Source) ((Map.Entry) it.next()).getValue()).unplug()
        }
    }

    @MainThread
    fun removeSource(@NonNull LiveData liveData) {
        Source source = (Source) this.mSources.remove(liveData)
        if (source != null) {
            source.unplug()
        }
    }
}
