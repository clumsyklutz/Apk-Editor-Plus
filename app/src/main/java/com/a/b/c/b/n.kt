package com.a.b.c.b

import androidx.appcompat.R

class n {

    /* renamed from: a, reason: collision with root package name */
    private final Int f329a

    /* renamed from: b, reason: collision with root package name */
    private final Int f330b
    private final Int c
    private final r d
    private final Boolean e

    constructor(Int i, Int i2, Int i3, r rVar, Boolean z) {
        if (!com.gmail.heagoo.a.c.a.e(i)) {
            throw IllegalArgumentException("bogus opcode")
        }
        if (!com.gmail.heagoo.a.c.a.e(i2)) {
            throw IllegalArgumentException("bogus family")
        }
        if (!com.gmail.heagoo.a.c.a.e(i3)) {
            throw IllegalArgumentException("bogus nextOpcode")
        }
        if (rVar == null) {
            throw NullPointerException("format == null")
        }
        this.f329a = i
        this.f330b = i2
        this.c = i3
        this.d = rVar
        this.e = z
    }

    public final Int a() {
        return this.f329a
    }

    public final Int b() {
        return this.f330b
    }

    public final r c() {
        return this.d
    }

    public final Boolean d() {
        return this.e
    }

    public final String e() {
        return com.a.b.d.e.a(this.f329a)
    }

    public final Int f() {
        return this.c
    }

    public final n g() {
        switch (this.f329a) {
            case 50:
                return o.Q
            case R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
                return o.P
            case R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
                return o.S
            case R.styleable.AppCompatTheme_buttonBarButtonStyle /* 53 */:
                return o.R
            case R.styleable.AppCompatTheme_selectableItemBackground /* 54 */:
                return o.U
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
                return o.T
            case R.styleable.AppCompatTheme_borderlessButtonStyle /* 56 */:
                return o.W
            case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
                return o.V
            case R.styleable.AppCompatTheme_dividerHorizontal /* 58 */:
                return o.Y
            case R.styleable.AppCompatTheme_activityChooserViewStyle /* 59 */:
                return o.X
            case R.styleable.AppCompatTheme_toolbarStyle /* 60 */:
                return o.aa
            case R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /* 61 */:
                return o.Z
            default:
                throw IllegalArgumentException("bogus opcode: " + this)
        }
    }

    public final String toString() {
        return com.a.b.d.e.a(this.f329a)
    }
}
