package com.gmail.heagoo.neweditor

import java.util.TimerTask

final class r extends TimerTask {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1528a

    r(EditorActivity editorActivity) {
        this.f1528a = editorActivity
    }

    protected static Int a(Int i) {
        Int i2 = 1
        while (i >= 10) {
            i /= 10
            i2++
        }
        return Math.max(2, i2)
    }

    private fun a() {
        Int lineCount = this.f1528a.f1488a.getLineCount()
        if (lineCount == 0) {
            lineCount = 1
        }
        this.f1528a.a(this.f1528a.Q, a(lineCount))
        StringBuilder sb = StringBuilder()
        for (Int i = 1; i <= lineCount; i++) {
            sb.append(i)
            sb.append('\n')
        }
        this.f1528a.u.setText(sb.toString())
        this.f1528a.u.requestLayout()
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final Unit run() {
        Int i
        Int i2 = 0
        try {
            if (this.f1528a.l) {
                Array<String> strArrSplit = (String.valueOf(this.f1528a.f1488a.getText().toString()) + "\nEND").split("\n")
                this.f1528a.a(this.f1528a.Q, a(strArrSplit.length))
                StringBuilder sb = StringBuilder()
                Int i3 = 0
                Int length = 0
                while (i3 < strArrSplit.length - 1) {
                    while (true) {
                        i = i2 + 1
                        if (i2 < this.f1528a.f1488a.b(length)) {
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
                this.f1528a.u.setText(sb.toString())
            } else {
                a()
            }
        } catch (Exception e) {
        }
        this.f1528a.f1489b = this.f1528a.f1488a.getLineCount()
    }
}
