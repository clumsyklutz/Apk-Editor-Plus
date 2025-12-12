package android.support.v4.app

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Intent
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo

@RequiresApi(api = 28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class CoreComponentFactory extends android.app.AppComponentFactory {
    private static val TAG = "CoreComponentFactory"

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface CompatWrapped {
        Object getWrapper()
    }

    static Object checkCompatWrapper(Object obj) {
        Object wrapper
        return (!(obj is CompatWrapped) || (wrapper = ((CompatWrapped) obj).getWrapper()) == null) ? obj : wrapper
    }

    @Override // android.app.AppComponentFactory
    fun instantiateActivity(ClassLoader classLoader, String str, Intent intent) {
        return (Activity) checkCompatWrapper(super.instantiateActivity(classLoader, str, intent))
    }

    @Override // android.app.AppComponentFactory
    fun instantiateApplication(ClassLoader classLoader, String str) {
        return (Application) checkCompatWrapper(super.instantiateApplication(classLoader, str))
    }

    @Override // android.app.AppComponentFactory
    fun instantiateProvider(ClassLoader classLoader, String str) {
        return (ContentProvider) checkCompatWrapper(super.instantiateProvider(classLoader, str))
    }

    @Override // android.app.AppComponentFactory
    fun instantiateReceiver(ClassLoader classLoader, String str, Intent intent) {
        return (BroadcastReceiver) checkCompatWrapper(super.instantiateReceiver(classLoader, str, intent))
    }

    @Override // android.app.AppComponentFactory
    fun instantiateService(ClassLoader classLoader, String str, Intent intent) {
        return (Service) checkCompatWrapper(super.instantiateService(classLoader, str, intent))
    }
}
