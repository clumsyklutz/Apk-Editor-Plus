package android.support.v4.os

import android.os.Build

class BuildCompat {
    private constructor() {
    }

    @Deprecated
    fun isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24
    }

    @Deprecated
    fun isAtLeastNMR1() {
        return Build.VERSION.SDK_INT >= 25
    }

    @Deprecated
    fun isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26
    }

    @Deprecated
    fun isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27
    }

    @Deprecated
    fun isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28
    }

    fun isAtLeastQ() {
        return Build.VERSION.CODENAME.length() == 1 && Build.VERSION.CODENAME.charAt(0) >= 'Q' && Build.VERSION.CODENAME.charAt(0) <= 'Z'
    }
}
