package com.gmail.heagoo.neweditor

import android.graphics.Rect
import java.util.TimerTask

final class p extends TimerTask {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ EditorActivity f1525a

    p(EditorActivity editorActivity) {
        this.f1525a = editorActivity
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(e eVar, Rect rect) {
        Int i
        Int i2
        try {
            Int lineHeight = rect.top / this.f1525a.f1488a.getLineHeight()
            Int height = (rect.top + this.f1525a.getWindowManager().getDefaultDisplay().getHeight()) / this.f1525a.f1488a.getLineHeight()
            if (this.f1525a.l) {
                Array<String> strArrSplit = this.f1525a.f1488a.getText().toString().split("\\n")
                Int length = 0
                Int i3 = 0
                Int i4 = 0
                for (Int i5 = 0; i5 < strArrSplit.length; i5++) {
                    Int iB = this.f1525a.f1488a.b(length)
                    length += strArrSplit[i5].length() + 1
                    if (iB <= lineHeight) {
                        i4 = i5
                    }
                    if (iB <= height) {
                        i3 = i5
                    }
                }
                i2 = i3
                i = i4
            } else {
                i = lineHeight
                i2 = height
            }
            Int i6 = i - this.f1525a.n
            Int i7 = i2 + this.f1525a.n
            ObEditText obEditText = this.f1525a.f1488a
            Int i8 = this.f1525a.k
            Int i9 = this.f1525a.j
            Boolean z = this.f1525a.i
            this.f1525a.getApplicationContext()
            eVar.a(obEditText, i8, i9, i6, i7, z)
        } catch (Exception e) {
        }
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final Unit run() {
        e eVar = this.f1525a.P
        Rect rect = Rect()
        if (this.f1525a.f1488a.getLocalVisibleRect(rect)) {
            a(eVar, rect)
        } else {
            ObEditText obEditText = this.f1525a.f1488a
            Int i = this.f1525a.k
            Int i2 = this.f1525a.j
            Boolean z = this.f1525a.i
            this.f1525a.getApplicationContext()
            eVar.a(obEditText, i, i2, -1, -1, z)
            this.f1525a.f1488a.getViewTreeObserver().addOnGlobalLayoutListener(q(this, rect, eVar))
        }
        EditorActivity.a(this.f1525a, -1)
        EditorActivity.b(this.f1525a, -1)
        EditorActivity.a(this.f1525a, false)
    }
}
