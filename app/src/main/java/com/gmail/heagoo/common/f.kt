package com.gmail.heagoo.common

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

class f {

    /* renamed from: a, reason: collision with root package name */
    private static Int f1452a = 0

    /* renamed from: b, reason: collision with root package name */
    private static Int f1453b = 0

    fun a(Activity activity) {
        if (f1452a <= 0) {
            c(activity)
        }
        return f1452a
    }

    fun a(Context context, Float f) {
        return (Int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f)
    }

    fun b(Activity activity) {
        if (f1453b <= 0) {
            c(activity)
        }
        return f1453b
    }

    private fun c(Activity activity) {
        DisplayMetrics displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        f1452a = displayMetrics.widthPixels
        f1453b = displayMetrics.heightPixels
    }
}
