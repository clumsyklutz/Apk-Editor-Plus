package com.gmail.heagoo.apkeditor.prj

import android.content.Context
import android.widget.Toast
import com.gmail.heagoo.apkeditor.fa
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.h
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference

final class g implements fa {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1241a

    /* renamed from: b, reason: collision with root package name */
    private f f1242b
    private Boolean c = false
    private String d

    g(ProjectListActivity projectListActivity, f fVar) {
        this.f1241a = WeakReference(projectListActivity)
        this.f1242b = fVar
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        try {
            h.a(File(this.f1242b.c))
        } catch (IOException e) {
        }
        try {
            h.a(File(com.gmail.heagoo.a.c.a.d((Context) this.f1241a.get(), ".projects") + this.f1242b.f1239a))
            this.c = true
        } catch (Exception e2) {
            this.d = e2.getMessage()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.c) {
            Context context = (Context) this.f1241a.get()
            Toast.makeText(context, String.format(context.getString(R.string.project_removed), this.f1242b.f1239a), 1).show()
            ((ProjectListActivity) this.f1241a.get()).a()
        } else if (this.d != null) {
            Toast.makeText((Context) this.f1241a.get(), String.format(((ProjectListActivity) this.f1241a.get()).getString(R.string.general_error), this.d), 1).show()
        }
    }
}
