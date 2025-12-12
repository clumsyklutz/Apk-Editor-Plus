package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.os.AsyncTask
import java.io.File
import java.util.List

final class fu extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private String f1085a

    /* renamed from: b, reason: collision with root package name */
    private List f1086b
    private String c
    private String d
    private /* synthetic */ fo e

    @SuppressLint({"DefaultLocale"})
    constructor(fo foVar, String str, List list, String str2) {
        this.e = foVar
        this.f1085a = str
        this.f1086b = list
        this.c = str2
        this.d = str2.toLowerCase()
    }

    private fun a(File file) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    a(file2)
                } else if (a(file2.getName())) {
                    this.e.p.add(file2.getPath())
                }
            }
        }
    }

    private fun a(String str) {
        return this.e.o ? str.contains(this.c) : str.toLowerCase().contains(this.d)
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        File file = File(this.f1085a)
        for (String str : this.f1086b) {
            File file2 = File(file, str)
            if (file2.exists()) {
                if (file2.isDirectory()) {
                    a(file2)
                } else if (a(str)) {
                    this.e.p.add(file2.getPath())
                }
            }
        }
        return this.e.p
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        fo.c(this.e)
    }
}
