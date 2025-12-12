package com.gmail.heagoo.apkeditor

import androidx.core.internal.view.SupportMenu
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

final class ew implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Boolean f1049a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1050b
    private /* synthetic */ Boolean c
    private /* synthetic */ eu d

    ew(eu euVar, Boolean z, String str, Boolean z2) {
        this.d = euVar
        this.f1049a = z
        this.f1050b = str
        this.c = z2
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        if (this.f1049a) {
            SpannableString spannableString = SpannableString(this.f1050b)
            spannableString.setSpan(ForegroundColorSpan(SupportMenu.CATEGORY_MASK), 0, this.f1050b.length(), 33)
            this.d.j.append(spannableString)
        } else {
            if (!this.c) {
                this.d.j.append(this.f1050b)
                return
            }
            SpannableString spannableString2 = SpannableString(this.f1050b)
            spannableString2.setSpan(StyleSpan(1), 0, this.f1050b.length(), 33)
            this.d.j.append(spannableString2)
        }
    }
}
