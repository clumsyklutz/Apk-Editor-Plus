package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList

final class ge extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    private String f1101a

    /* renamed from: b, reason: collision with root package name */
    private String f1102b
    private Int c
    private /* synthetic */ fv d

    constructor(fv fvVar, String str, String str2, Int i) {
        this.d = fvVar
        this.f1101a = str
        this.f1102b = str2
        this.c = i
    }

    @SuppressLint({"DefaultLocale"})
    private fun a() throws Throwable {
        BufferedReader bufferedReader
        ir irVar = ir()
        irVar.f1199a = this.f1101a
        String lowerCase = this.f1102b.toLowerCase()
        BufferedReader bufferedReader2 = null
        try {
            ArrayList arrayList = ArrayList()
            bufferedReader = BufferedReader(InputStreamReader(FileInputStream(this.f1101a)))
            try {
                Int i = 1
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    Int iIndexOf = this.d.j ? line.indexOf(this.f1102b) : line.toLowerCase().indexOf(lowerCase)
                    if (iIndexOf != -1) {
                        arrayList.add(eh(i, iIndexOf, line))
                    }
                    i++
                }
                irVar.f1200b = arrayList
                try {
                    bufferedReader.close()
                } catch (IOException e) {
                }
            } catch (Exception e2) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close()
                    } catch (IOException e3) {
                    }
                }
                return irVar
            } catch (Throwable th) {
                bufferedReader2 = bufferedReader
                th = th
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close()
                    } catch (IOException e4) {
                    }
                }
                throw th
            }
        } catch (Exception e5) {
            bufferedReader = null
        } catch (Throwable th2) {
            th = th2
        }
        return irVar
    }

    @Override // android.os.AsyncTask
    @SuppressLint({"DefaultLocale"})
    protected final /* synthetic */ Object doInBackground(Array<Object> objArr) {
        return a()
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ Unit onPostExecute(Object obj) {
        ir irVar = (ir) obj
        if (irVar.f1200b != null) {
            this.d.d.a(irVar.f1199a, irVar.f1200b)
        }
        this.d.c.expandGroup(this.c)
    }
}
