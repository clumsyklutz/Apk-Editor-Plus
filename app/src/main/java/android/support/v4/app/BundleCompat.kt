package android.support.v4.app

import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class BundleCompat {

    class BundleCompatBaseImpl {
        private static val TAG = "BundleCompatBaseImpl"
        private static Method sGetIBinderMethod
        private static Boolean sGetIBinderMethodFetched
        private static Method sPutIBinderMethod
        private static Boolean sPutIBinderMethodFetched

        private constructor() {
        }

        fun getBinder(Bundle bundle, String str) throws NoSuchMethodException, SecurityException {
            if (!sGetIBinderMethodFetched) {
                try {
                    Method method = Bundle.class.getMethod("getIBinder", String.class)
                    sGetIBinderMethod = method
                    method.setAccessible(true)
                } catch (NoSuchMethodException e) {
                    Log.i(TAG, "Failed to retrieve getIBinder method", e)
                }
                sGetIBinderMethodFetched = true
            }
            if (sGetIBinderMethod != null) {
                try {
                    return (IBinder) sGetIBinderMethod.invoke(bundle, str)
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i(TAG, "Failed to invoke getIBinder via reflection", e2)
                    sGetIBinderMethod = null
                }
            }
            return null
        }

        fun putBinder(Bundle bundle, String str, IBinder iBinder) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (!sPutIBinderMethodFetched) {
                try {
                    Method method = Bundle.class.getMethod("putIBinder", String.class, IBinder.class)
                    sPutIBinderMethod = method
                    method.setAccessible(true)
                } catch (NoSuchMethodException e) {
                    Log.i(TAG, "Failed to retrieve putIBinder method", e)
                }
                sPutIBinderMethodFetched = true
            }
            if (sPutIBinderMethod != null) {
                try {
                    sPutIBinderMethod.invoke(bundle, str, iBinder)
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i(TAG, "Failed to invoke putIBinder via reflection", e2)
                    sPutIBinderMethod = null
                }
            }
        }
    }

    private constructor() {
    }

    @Nullable
    fun getBinder(@NonNull Bundle bundle, @Nullable String str) {
        return Build.VERSION.SDK_INT >= 18 ? bundle.getBinder(str) : BundleCompatBaseImpl.getBinder(bundle, str)
    }

    fun putBinder(@NonNull Bundle bundle, @Nullable String str, @Nullable IBinder iBinder) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 18) {
            bundle.putBinder(str, iBinder)
        } else {
            BundleCompatBaseImpl.putBinder(bundle, str, iBinder)
        }
    }
}
