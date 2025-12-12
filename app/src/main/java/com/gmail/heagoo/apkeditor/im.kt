package com.gmail.heagoo.apkeditor

import android.graphics.Rect
import com.gmail.heagoo.neweditor.ObEditText
import java.util.TimerTask

final class im extends TimerTask {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TextEditNormalActivity f1192a

    im(TextEditNormalActivity textEditNormalActivity) {
        this.f1192a = textEditNormalActivity
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(com.gmail.heagoo.neweditor.e eVar, Rect rect) {
        Int i
        Int i2 = 0
        if (this.f1192a.ah) {
            try {
                Int lineHeight = this.f1192a.l.getLineHeight()
                Int i3 = rect.top / lineHeight
                Int i4 = rect.bottom / lineHeight
                if (this.f1192a.f) {
                    Array<String> strArrSplit = this.f1192a.l.getText().toString().split("\\n")
                    Int length = 0
                    Int i5 = 0
                    Int i6 = 0
                    while (i6 < strArrSplit.length) {
                        Int iB = this.f1192a.l.b(length)
                        length += strArrSplit[i6].length() + 1
                        if (iB <= i3) {
                            i2 = i6
                        }
                        if (iB > i4) {
                            break
                        }
                        Int i7 = i6
                        i6++
                        i5 = i7
                    }
                    i = i5
                } else {
                    i2 = i3
                    i = i4
                }
                Int i8 = i2 - this.f1192a.w
                Int i9 = i + this.f1192a.w
                Long jCurrentTimeMillis = System.currentTimeMillis()
                ObEditText obEditText = this.f1192a.l
                Int i10 = this.f1192a.u
                Int i11 = this.f1192a.t
                Boolean z = this.f1192a.s
                this.f1192a.getApplicationContext()
                eVar.a(obEditText, i10, i11, i8, i9, z)
                if (System.currentTimeMillis() - jCurrentTimeMillis > 2000) {
                    TextEditNormalActivity.b(this.f1192a, false)
                }
            } catch (Exception e) {
            }
        }
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final Unit run() {
        if (this.f1192a.ah) {
            com.gmail.heagoo.neweditor.e eVar = this.f1192a.j
            Rect rect = Rect()
            if (this.f1192a.l.getLocalVisibleRect(rect)) {
                a(eVar, rect)
            } else if (this.f1192a.ah) {
                ObEditText obEditText = this.f1192a.l
                Int i = this.f1192a.u
                Int i2 = this.f1192a.t
                Boolean z = this.f1192a.s
                this.f1192a.getApplicationContext()
                eVar.a(obEditText, i, i2, -1, -1, z)
                this.f1192a.l.getViewTreeObserver().addOnGlobalLayoutListener(in(this, rect, eVar))
            }
            TextEditNormalActivity.a(this.f1192a, -1)
            TextEditNormalActivity.b(this.f1192a, -1)
            TextEditNormalActivity.a(this.f1192a, false)
        }
    }
}
