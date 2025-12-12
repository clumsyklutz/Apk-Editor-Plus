package android.support.v4.app

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.Log

class NavUtils {
    public static val PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY"
    private static val TAG = "NavUtils"

    private constructor() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v8, types: [android.content.Intent] */
    @Nullable
    fun getParentActivityIntent(@NonNull Activity activity) {
        Intent parentActivityIntent
        if (Build.VERSION.SDK_INT >= 16 && (parentActivityIntent = activity.getParentActivityIntent()) != null) {
            return parentActivityIntent
        }
        String parentActivityName = getParentActivityName(activity)
        if (parentActivityName == 0) {
            return null
        }
        ComponentName componentName = ComponentName(activity, (String) parentActivityName)
        try {
            parentActivityName = getParentActivityName(activity, componentName) == null ? Intent.makeMainActivity(componentName) : Intent().setComponent(componentName)
            return parentActivityName
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getParentActivityIntent: bad parentActivityName '" + parentActivityName + "' in manifest")
            return null
        }
    }

    @Nullable
    fun getParentActivityIntent(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, componentName)
        if (parentActivityName == null) {
            return null
        }
        ComponentName componentName2 = ComponentName(componentName.getPackageName(), parentActivityName)
        return getParentActivityName(context, componentName2) == null ? Intent.makeMainActivity(componentName2) : Intent().setComponent(componentName2)
    }

    @Nullable
    fun getParentActivityIntent(@NonNull Context context, @NonNull Class cls) throws PackageManager.NameNotFoundException {
        String parentActivityName = getParentActivityName(context, ComponentName(context, (Class<?>) cls))
        if (parentActivityName == null) {
            return null
        }
        ComponentName componentName = ComponentName(context, parentActivityName)
        return getParentActivityName(context, componentName) == null ? Intent.makeMainActivity(componentName) : Intent().setComponent(componentName)
    }

    @Nullable
    fun getParentActivityName(@NonNull Activity activity) {
        try {
            return getParentActivityName(activity, activity.getComponentName())
        } catch (PackageManager.NameNotFoundException e) {
            throw IllegalArgumentException(e)
        }
    }

    @Nullable
    fun getParentActivityName(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string
        String str
        ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, 128)
        if (Build.VERSION.SDK_INT >= 16 && (str = activityInfo.parentActivityName) != null) {
            return str
        }
        if (activityInfo.metaData != null && (string = activityInfo.metaData.getString(PARENT_ACTIVITY)) != null) {
            return string.charAt(0) == '.' ? context.getPackageName() + string : string
        }
        return null
    }

    fun navigateUpFromSameTask(@NonNull Activity activity) {
        Intent parentActivityIntent = getParentActivityIntent(activity)
        if (parentActivityIntent == null) {
            throw IllegalArgumentException("Activity " + activity.getClass().getSimpleName() + " does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)")
        }
        navigateUpTo(activity, parentActivityIntent)
    }

    fun navigateUpTo(@NonNull Activity activity, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.navigateUpTo(intent)
            return
        }
        intent.addFlags(67108864)
        activity.startActivity(intent)
        activity.finish()
    }

    fun shouldUpRecreateTask(@NonNull Activity activity, @NonNull Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return activity.shouldUpRecreateTask(intent)
        }
        String action = activity.getIntent().getAction()
        return (action == null || action.equals("android.intent.action.MAIN")) ? false : true
    }
}
