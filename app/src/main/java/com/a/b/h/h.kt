package com.a.b.h

import android.support.v7.widget.ActivityChooserView
import java.io.FilterWriter
import java.io.Writer

class h extends FilterWriter {

    /* renamed from: a, reason: collision with root package name */
    private final String f671a

    /* renamed from: b, reason: collision with root package name */
    private final Int f672b
    private final Int c
    private Int d
    private Boolean e
    private Int f

    constructor(Writer writer, Int i) {
        this(writer, i, "")
    }

    constructor(Writer writer, Int i, String str) {
        super(writer)
        if (writer == null) {
            throw NullPointerException("out == null")
        }
        if (i < 0) {
            throw IllegalArgumentException("width < 0")
        }
        if (str == null) {
            throw NullPointerException("prefix == null")
        }
        this.f672b = i != 0 ? i : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        this.c = i >> 1
        this.f671a = str.length() == 0 ? null : str
        a()
    }

    private fun a() {
        this.d = 0
        this.e = this.c != 0
        this.f = 0
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public final Unit write(Int i) {
        synchronized (this.lock) {
            if (this.e) {
                if (i == 32) {
                    this.f++
                    if (this.f >= this.c) {
                        this.f = this.c
                        this.e = false
                    }
                } else {
                    this.e = false
                }
            }
            if (this.d == this.f672b && i != 10) {
                this.out.write(10)
                this.d = 0
            }
            if (this.d == 0) {
                if (this.f671a != null) {
                    this.out.write(this.f671a)
                }
                if (!this.e) {
                    for (Int i2 = 0; i2 < this.f; i2++) {
                        this.out.write(32)
                    }
                    this.d = this.f
                }
            }
            this.out.write(i)
            if (i == 10) {
                a()
            } else {
                this.d++
            }
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public final Unit write(String str, Int i, Int i2) {
        synchronized (this.lock) {
            while (i2 > 0) {
                write(str.charAt(i))
                i++
                i2--
            }
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public final Unit write(Array<Char> cArr, Int i, Int i2) {
        synchronized (this.lock) {
            while (i2 > 0) {
                write(cArr[i])
                i++
                i2--
            }
        }
    }
}
