package android.support.v4.app

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Intent
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import java.lang.reflect.InvocationTargetException

@RequiresApi(28)
class AppComponentFactory extends android.app.AppComponentFactory {
    @Override // android.app.AppComponentFactory
    public final Activity instantiateActivity(ClassLoader classLoader, String str, Intent intent) {
        return (Activity) CoreComponentFactory.checkCompatWrapper(instantiateActivityCompat(classLoader, str, intent))
    }

    @NonNull
    fun instantiateActivityCompat(@NonNull ClassLoader classLoader, @NonNull String str, @Nullable Intent intent) {
        try {
            return (Activity) classLoader.loadClass(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw RuntimeException("Couldn't call constructor", e)
        }
    }

    @Override // android.app.AppComponentFactory
    public final Application instantiateApplication(ClassLoader classLoader, String str) {
        return (Application) CoreComponentFactory.checkCompatWrapper(instantiateApplicationCompat(classLoader, str))
    }

    @NonNull
    fun instantiateApplicationCompat(@NonNull ClassLoader classLoader, @NonNull String str) {
        try {
            return (Application) classLoader.loadClass(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw RuntimeException("Couldn't call constructor", e)
        }
    }

    @Override // android.app.AppComponentFactory
    public final ContentProvider instantiateProvider(ClassLoader classLoader, String str) {
        return (ContentProvider) CoreComponentFactory.checkCompatWrapper(instantiateProviderCompat(classLoader, str))
    }

    @NonNull
    fun instantiateProviderCompat(@NonNull ClassLoader classLoader, @NonNull String str) {
        try {
            return (ContentProvider) classLoader.loadClass(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw RuntimeException("Couldn't call constructor", e)
        }
    }

    @Override // android.app.AppComponentFactory
    public final BroadcastReceiver instantiateReceiver(ClassLoader classLoader, String str, Intent intent) {
        return (BroadcastReceiver) CoreComponentFactory.checkCompatWrapper(instantiateReceiverCompat(classLoader, str, intent))
    }

    @NonNull
    fun instantiateReceiverCompat(@NonNull ClassLoader classLoader, @NonNull String str, @Nullable Intent intent) {
        try {
            return (BroadcastReceiver) classLoader.loadClass(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw RuntimeException("Couldn't call constructor", e)
        }
    }

    @Override // android.app.AppComponentFactory
    public final Service instantiateService(ClassLoader classLoader, String str, Intent intent) {
        return (Service) CoreComponentFactory.checkCompatWrapper(instantiateServiceCompat(classLoader, str, intent))
    }

    @NonNull
    fun instantiateServiceCompat(@NonNull ClassLoader classLoader, @NonNull String str, @Nullable Intent intent) {
        try {
            return (Service) classLoader.loadClass(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0])
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw RuntimeException("Couldn't call constructor", e)
        }
    }
}
