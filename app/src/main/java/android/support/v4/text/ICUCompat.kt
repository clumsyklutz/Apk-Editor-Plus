package android.support.v4.text

import android.os.Build
import android.support.annotation.Nullable
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Locale

class ICUCompat {
    private static val TAG = "ICUCompat"
    private static Method sAddLikelySubtagsMethod
    private static Method sGetScriptMethod

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", Locale.class)
                return
            } catch (Exception e) {
                throw IllegalStateException(e)
            }
        }
        try {
            Class<?> cls = Class.forName("libcore.icu.ICU")
            if (cls != null) {
                sGetScriptMethod = cls.getMethod("getScript", String.class)
                sAddLikelySubtagsMethod = cls.getMethod("addLikelySubtags", String.class)
            }
        } catch (Exception e2) {
            sGetScriptMethod = null
            sAddLikelySubtagsMethod = null
            Log.w(TAG, e2)
        }
    }

    private constructor() {
    }

    private fun addLikelySubtags(Locale locale) {
        String string = locale.toString()
        try {
            if (sAddLikelySubtagsMethod != null) {
                return (String) sAddLikelySubtagsMethod.invoke(null, string)
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e)
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2)
        }
        return string
    }

    private fun getScript(String str) {
        try {
            if (sGetScriptMethod != null) {
                return (String) sGetScriptMethod.invoke(null, str)
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e)
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2)
        }
        return null
    }

    @Nullable
    fun maximizeAndGetScript(Locale locale) {
        if (Build.VERSION.SDK_INT < 21) {
            String strAddLikelySubtags = addLikelySubtags(locale)
            if (strAddLikelySubtags != null) {
                return getScript(strAddLikelySubtags)
            }
            return null
        }
        try {
            return ((Locale) sAddLikelySubtagsMethod.invoke(null, locale)).getScript()
        } catch (IllegalAccessException e) {
            Log.w(TAG, e)
            return locale.getScript()
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2)
            return locale.getScript()
        }
    }
}
