package com.gmail.heagoo.neweditor

import android.content.Context
import com.gmail.heagoo.apkeditor.pro.R
import java.io.Serializable
import java.util.Map

class a implements com.gmail.heagoo.apkeditor.a.d, Serializable {

    /* renamed from: a, reason: collision with root package name */
    private String f1493a

    /* renamed from: b, reason: collision with root package name */
    private String f1494b
    private Int c
    private Int d

    constructor() {
    }

    constructor(Int i, String str, String str2) {
        this.d = b.f1506a
        this.c = i
        this.f1494b = str
        this.f1493a = str2
        if (str.length() == 0 && this.f1493a.length() > 0) {
            this.d = b.f1507b
        }
        if (str.length() > 0 && this.f1493a.length() == 0) {
            this.d = b.c
        }
        if (str.length() <= 0 || this.f1493a.length() <= 0) {
            return
        }
        this.d = b.d
    }

    @Override // com.gmail.heagoo.apkeditor.a.d
    fun a(Context context, String str, Map map, com.gmail.heagoo.apkeditor.a.f fVar) throws Throwable {
        if (fVar != null) {
            fVar.a(context.getString(R.string.decode_dex_file))
        }
        String str2 = context.getFilesDir().getAbsolutePath() + "/decoded"
        new com.gmail.heagoo.common.c().a("rm -rf " + str2 + "/smali", (Array<String>) null, (Integer) 10000)
        new com.gmail.heagoo.apkeditor.f.a(context, str, str2, null).a()
        if (fVar != null) {
            fVar.a("")
        }
    }

    public final Boolean a(a aVar) {
        if (this.d == b.f1507b && aVar.d == b.f1507b && aVar.f1493a.length() == 1 && !Character.isWhitespace(aVar.f1493a.charAt(0)) && this.c + this.f1493a.length() == aVar.c) {
            return true
        }
        return this.d == b.c && aVar.d == b.c && aVar.f1494b.length() == 1 && !Character.isWhitespace(aVar.f1494b.charAt(0)) && aVar.c + aVar.f1494b.length() == this.c
    }

    public final Unit b(a aVar) {
        if (this.d == b.f1507b && aVar.d == b.f1507b) {
            this.f1493a += aVar.f1493a
        }
        if (this.d == b.c && aVar.d == b.c) {
            this.f1494b = aVar.f1494b + this.f1494b
            this.c = aVar.c
        }
    }
}
