package com.gmail.heagoo.apkeditor

import java.util.TimerTask

final class iq extends TimerTask {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1198a

    iq(TextEditNormalActivity textEditNormalActivity) {
        this.f1198a = textEditNormalActivity
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Int i) {
        Int i2 = 1
        while (i >= 10) {
            i /= 10
            i2++
        }
        return Math.max(2, i2)
    }

    private fun a() {
        Int lineCount = this.f1198a.l.getLineCount()
        if (lineCount == 0) {
            lineCount = 1
        }
        TextEditNormalActivity.a(this.f1198a, this.f1198a.Y, a(lineCount))
        StringBuilder sb = StringBuilder()
        for (Int i = 1; i <= lineCount; i++) {
            sb.append(i)
            sb.append('\n')
        }
        this.f1198a.B.setText(sb.toString())
        this.f1198a.B.requestLayout()
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final Unit run() {
        Int i
        Int i2 = 0
        if (!SettingEditorActivity.e(this.f1198a)) {
            this.f1198a.m = 0
            return
        }
        if (this.f1198a.f) {
            Array<String> strArrSplit = this.f1198a.l.getText().toString().split("\n")
            TextEditNormalActivity.a(this.f1198a, this.f1198a.Y, a(strArrSplit.length + 1))
            StringBuilder sb = StringBuilder()
            Int i3 = 0
            Int length = 0
            while (i3 < strArrSplit.length) {
                while (true) {
                    i = i2 + 1
                    if (i2 < this.f1198a.l.b(length)) {
                        sb.append('\n')
                        i2 = i
                    }
                }
                sb.append(i3 + 1)
                sb.append('\n')
                length += strArrSplit[i3].length() + 1
                i3++
                i2 = i
            }
            this.f1198a.B.setText(sb.toString())
        } else {
            a()
        }
        this.f1198a.m = this.f1198a.l.getLineCount()
    }
}
