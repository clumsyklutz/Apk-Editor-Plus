package android.arch.lifecycle

class MutableLiveData extends LiveData {
    @Override // android.arch.lifecycle.LiveData
    fun postValue(Object obj) {
        super.postValue(obj)
    }

    @Override // android.arch.lifecycle.LiveData
    fun setValue(Object obj) {
        super.setValue(obj)
    }
}
