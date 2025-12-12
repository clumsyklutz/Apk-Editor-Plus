package com.a.b.f.a

import com.a.b.h.s

public enum b implements s {
    RUNTIME("runtime"),
    BUILD("build"),
    SYSTEM("system"),
    EMBEDDED("embedded")

    private final String e

    b(String str) {
        this.e = str
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.e
    }
}
