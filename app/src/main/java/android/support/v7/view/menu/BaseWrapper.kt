package android.support.v7.view.menu

class BaseWrapper {
    final Object mWrappedObject

    BaseWrapper(Object obj) {
        if (obj == null) {
            throw IllegalArgumentException("Wrapped Object can not be null.")
        }
        this.mWrappedObject = obj
    }

    fun getWrappedObject() {
        return this.mWrappedObject
    }
}
