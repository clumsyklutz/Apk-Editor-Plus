package ru.zeratul.preference

import android.graphics.Color
import java.util.ArrayList
import java.util.List

class c {

    /* renamed from: b, reason: collision with root package name */
    private Int f1623b

    /* renamed from: a, reason: collision with root package name */
    private Array<Float> f1622a = {0.0f, 0.0f, 0.0f}
    private List c = ArrayList()

    c(Int i) {
        Color.colorToHSV(i, this.f1622a)
        this.f1623b = Color.alpha(i)
    }

    fun a(b bVar) {
        this.c.add(bVar)
    }

    fun b(Int i, b bVar) {
        Color.colorToHSV(i, this.f1622a)
        this.f1623b = Color.alpha(i)
        for (b bVar2 : this.c) {
            if (bVar2 != bVar) {
                bVar2.a(this)
            }
        }
    }

    fun c() {
        return Color.HSVToColor(this.f1623b, this.f1622a)
    }
}
