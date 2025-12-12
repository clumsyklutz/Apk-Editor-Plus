package android.support.v4.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.annotation.NonNull
import android.support.v4.content.IntentCompat

class AppLaunchChecker {
    private static val KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher"
    private static val SHARED_PREFS_NAME = "android.support.AppLaunchChecker"

    @Deprecated
    constructor() {
    }

    fun hasStartedFromLauncher(@NonNull Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, 0).getBoolean(KEY_STARTED_FROM_LAUNCHER, false)
    }

    fun onActivityCreate(@NonNull Activity activity) {
        Intent intent
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFS_NAME, 0)
        if (sharedPreferences.getBoolean(KEY_STARTED_FROM_LAUNCHER, false) || (intent = activity.getIntent()) == null || !"android.intent.action.MAIN".equals(intent.getAction())) {
            return
        }
        if (intent.hasCategory("android.intent.category.LAUNCHER") || intent.hasCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER)) {
            sharedPreferences.edit().putBoolean(KEY_STARTED_FROM_LAUNCHER, true).apply()
        }
    }
}
