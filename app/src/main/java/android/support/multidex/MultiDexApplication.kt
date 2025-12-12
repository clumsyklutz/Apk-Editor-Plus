package android.support.multidex

import android.app.Application
import android.content.Context

class MultiDexApplication extends Application {
    @Override // android.content.ContextWrapper
    protected fun attachBaseContext(Context context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}
