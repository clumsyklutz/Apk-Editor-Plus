package android.arch.lifecycle

import android.annotation.SuppressLint
import android.app.Application
import android.support.annotation.NonNull

class AndroidViewModel extends ViewModel {

    @SuppressLint({"StaticFieldLeak"})
    private Application mApplication

    constructor(@NonNull Application application) {
        this.mApplication = application
    }

    @NonNull
    fun getApplication() {
        return this.mApplication
    }
}
