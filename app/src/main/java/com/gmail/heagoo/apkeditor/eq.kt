package com.gmail.heagoo.apkeditor

import android.content.Context
import com.gmail.heagoo.apkeditor.pro.R

final class eq {

    /* renamed from: a, reason: collision with root package name */
    public String f1038a

    /* renamed from: b, reason: collision with root package name */
    public Int f1039b
    public Int c
    public Int d

    constructor(String str, Int i, Int i2, Int i3) {
        this.f1038a = str
        this.f1039b = i
        this.c = i2
        this.d = i3
    }

    public final String a(Context context, Int i) {
        if (this.f1039b < 0 && this.c < 0 && this.d < 0) {
            return context.getString(R.string.str_modified)
        }
        if (this.f1039b == 0 && this.c == 0 && this.d == 0) {
            return context.getString(R.string.str_not_modified)
        }
        Int i2 = this.f1039b > 0 ? 0 : -1
        if (i2 == i) {
            return String.format(context.getString(R.string.str_num_added_file), Integer.valueOf(this.f1039b))
        }
        if (this.c > 0) {
            i2++
        }
        if (i2 == i) {
            return String.format(context.getString(R.string.str_num_removed_file), Integer.valueOf(this.c))
        }
        if (this.d > 0) {
            i2++
        }
        if (i2 == i) {
            return String.format(context.getString(R.string.str_num_modified_file), Integer.valueOf(this.d))
        }
        return null
    }
}
