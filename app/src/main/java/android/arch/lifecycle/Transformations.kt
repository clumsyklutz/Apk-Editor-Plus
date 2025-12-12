package android.arch.lifecycle

import android.arch.core.util.Function
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable

class Transformations {
    private constructor() {
    }

    @MainThread
    fun map(@NonNull LiveData liveData, @NonNull final Function function) {
        val mediatorLiveData = MediatorLiveData()
        mediatorLiveData.addSource(liveData, Observer() { // from class: android.arch.lifecycle.Transformations.1
            @Override // android.arch.lifecycle.Observer
            public final Unit onChanged(@Nullable Object obj) {
                mediatorLiveData.setValue(function.apply(obj))
            }
        })
        return mediatorLiveData
    }

    @MainThread
    fun switchMap(@NonNull LiveData liveData, @NonNull final Function function) {
        val mediatorLiveData = MediatorLiveData()
        mediatorLiveData.addSource(liveData, Observer() { // from class: android.arch.lifecycle.Transformations.2
            LiveData mSource

            @Override // android.arch.lifecycle.Observer
            public final Unit onChanged(@Nullable Object obj) {
                LiveData liveData2 = (LiveData) function.apply(obj)
                if (this.mSource == liveData2) {
                    return
                }
                if (this.mSource != null) {
                    mediatorLiveData.removeSource(this.mSource)
                }
                this.mSource = liveData2
                if (this.mSource != null) {
                    mediatorLiveData.addSource(this.mSource, Observer() { // from class: android.arch.lifecycle.Transformations.2.1
                        @Override // android.arch.lifecycle.Observer
                        fun onChanged(@Nullable Object obj2) {
                            mediatorLiveData.setValue(obj2)
                        }
                    })
                }
            }
        })
        return mediatorLiveData
    }
}
