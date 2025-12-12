package com.gmail.heagoo.neweditor

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

class c {

    /* renamed from: a, reason: collision with root package name */
    private Int f1508a

    /* renamed from: b, reason: collision with root package name */
    private Array<Int> f1509b = new Int[16]
    private SharedPreferences c

    constructor(Context context) {
        this.c = context.getSharedPreferences("editor_colors", 0)
        this.f1508a = d(context, "editor_bg", 0)
        this.f1509b[0] = d(context, "editor_numbers", -8355840)
        this.f1509b[1] = d(context, "syntax_1", ContextCompat.getColor(context, a.a.b.a.k.mdSyntax1(a.a.b.a.k.a(context))))
        this.f1509b[2] = d(context, "syntax_2", ContextCompat.getColor(context, a.a.b.a.k.mdSyntax2(a.a.b.a.k.a(context))))
        this.f1509b[3] = d(context, "syntax_3", ContextCompat.getColor(context, a.a.b.a.k.mdSyntax3(a.a.b.a.k.a(context))))
        this.f1509b[4] = d(context, "syntax_4", ContextCompat.getColor(context, a.a.b.a.k.mdSyntax4(a.a.b.a.k.a(context))))
        this.f1509b[5] = d(context, "syntax_5", ContextCompat.getColor(context, a.a.b.a.k.mdSyntax5(a.a.b.a.k.a(context))))
        this.f1509b[6] = d(context, "syntax_6", -8355712)
        this.f1509b[7] = d(context, "syntax_7", -2346449)
        this.f1509b[8] = d(context, "syntax_8", -47992)
        this.f1509b[9] = d(context, "syntax_9", ContextCompat.getColor(context, a.a.b.a.k.mdSyntax9(a.a.b.a.k.a(context))))
    }

    private final Int d(Context context, String str, Int i) {
        return this.c.getBoolean("custom_colors", false) ? this.c.getInt(str, i) : i
    }

    public final Int a() {
        return this.f1508a
    }

    public final Int a(Token token) {
        switch (token.id) {
            case 1:
            case 2:
            case 3:
            case 4:
                return this.f1509b[6]
            case 5:
                return this.f1509b[2]
            case 6:
                return this.f1509b[8]
            case 7:
                return this.f1509b[7]
            case 8:
            case 9:
            case 10:
            case 11:
                return this.f1509b[1]
            case 12:
                return this.f1509b[4]
            case 13:
            case 14:
            case 15:
            case 16:
                return this.f1509b[3]
            case 17:
                return this.f1509b[5]
            case 18:
                return this.f1509b[9]
            default:
                return ViewCompat.MEASURED_STATE_MASK
        }
    }

    public final Int b() {
        return this.f1509b[0]
    }
}
