package com.gmail.heagoo.apkeditor.util

import android.R
import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import java.lang.ref.WeakReference

class a {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1303a

    /* renamed from: b, reason: collision with root package name */
    private Int f1304b
    private Int c
    private View d
    private Int e

    private constructor(Activity activity) {
        this.f1303a = WeakReference(activity)
        this.f1304b = com.gmail.heagoo.common.f.b(activity)
        this.c = com.gmail.heagoo.common.f.a(activity)
        this.d = ((FrameLayout) activity.findViewById(R.id.content)).getChildAt(0)
        this.d.getViewTreeObserver().addOnGlobalLayoutListener(b(this))
        this.d.getLayoutParams()
    }

    fun a(Activity activity) {
        a(activity)
    }

    static /* synthetic */ Unit a(a aVar) {
        Rect rect = Rect()
        aVar.d.getWindowVisibleDisplayFrame(rect)
        Int i = rect.bottom - rect.top
        if (i != aVar.e) {
            if (i >= ((((Activity) aVar.f1303a.get()).getResources().getConfiguration().orientation == 1 ? aVar.f1304b : aVar.c) * 85) / 100) {
                Window window = ((Activity) aVar.f1303a.get()).getWindow()
                window.addFlags(1024)
                window.clearFlags(2048)
            } else {
                Window window2 = ((Activity) aVar.f1303a.get()).getWindow()
                window2.addFlags(2048)
                window2.clearFlags(1024)
            }
            aVar.e = i
        }
    }
}
