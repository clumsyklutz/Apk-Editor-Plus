package com.gmail.heagoo.apkeditor

import java.io.File
import java.io.FilenameFilter

final class ac implements FilenameFilter {
    ac(ApkInfoActivity apkInfoActivity) {
    }

    @Override // java.io.FilenameFilter
    public final Boolean accept(File file, String str) {
        return true
    }
}
