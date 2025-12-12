package android.support.v4.os

import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException

class EnvironmentCompat {
    public static val MEDIA_UNKNOWN = "unknown"
    private static val TAG = "EnvironmentCompat"

    private constructor() {
    }

    fun getStorageState(File file) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Environment.getStorageState(file)
        }
        try {
            if (file.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                return Environment.getExternalStorageState()
            }
        } catch (IOException e) {
            Log.w(TAG, "Failed to resolve canonical path: " + e)
        }
        return MEDIA_UNKNOWN
    }
}
