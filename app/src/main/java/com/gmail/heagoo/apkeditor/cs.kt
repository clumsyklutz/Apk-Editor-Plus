package com.gmail.heagoo.apkeditor

import java.io.File
import java.io.FilenameFilter

final class cs implements FilenameFilter {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ cn f944a

    cs(cn cnVar) {
        this.f944a = cnVar
    }

    @Override // java.io.FilenameFilter
    public final Boolean accept(File file, String str) {
        return File(file, str).isDirectory() || this.f944a.a(str)
    }
}
