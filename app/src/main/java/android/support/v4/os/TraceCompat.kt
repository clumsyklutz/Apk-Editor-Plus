package android.support.v4.os

import android.os.Build
import android.os.Trace

class TraceCompat {
    private constructor() {
    }

    fun beginSection(String str) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.beginSection(str)
        }
    }

    fun endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.endSection()
        }
    }
}
