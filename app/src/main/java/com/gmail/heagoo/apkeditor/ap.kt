package com.gmail.heagoo.apkeditor

import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import common.types.ActivityState_V1
import common.types.ProjectInfo_V1
import java.io.File

final class ap implements fa {

    /* renamed from: a, reason: collision with root package name */
    private ActivityState_V1 f853a

    /* renamed from: b, reason: collision with root package name */
    private File f854b
    private File c
    private String d = null
    private /* synthetic */ ApkInfoActivity e

    ap(ApkInfoActivity apkInfoActivity) {
        this.e = apkInfoActivity
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        String str = this.e.c != null ? this.e.c.f1447a : "UNKNOWN"
        try {
            String str2 = a.d(this.e, ".projects") + str
            this.c = File(str2)
            if (this.c.exists()) {
                this.c = ce.a(str2, true)
            }
            if (!this.c.mkdirs()) {
                this.d = this.e.getString(R.string.cannot_save_project)
                return
            }
            String str3 = this.e.f758b
            String str4 = str3 != null ? str3.substring(0, str3.lastIndexOf(47) + 1) + str : null
            this.f854b = File(str4)
            if (this.f854b.exists()) {
                this.f854b = ce.a(str4, true)
            }
            if (!File(this.e.f758b).renameTo(this.f854b)) {
                this.d = this.e.getString(R.string.cannot_rename_decode_folder)
                return
            }
            this.e.e.f(this.e.f758b + "/", this.f854b.getPath() + "/")
            this.f853a = this.e.a(this.c.getPath() + "/", true, this.c)
            if (this.f853a == null) {
                this.d = "Cannot save project state."
            }
        } catch (Exception e) {
            this.d = String.format(this.e.getString(R.string.general_error), e.getMessage())
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.d != null) {
            Toast.makeText(this.e, this.d, 1).show()
            return
        }
        ProjectInfo_V1 projectInfo_V1 = ProjectInfo_V1()
        projectInfo_V1.state = this.f853a
        projectInfo_V1.apkPath = this.e.f757a
        projectInfo_V1.decodeRootPath = this.f854b.getPath()
        ApkInfoActivity apkInfoActivity = this.e
        if (ApkInfoActivity.a(this.c.getPath(), projectInfo_V1)) {
            this.e.finish()
        } else {
            Toast.makeText(this.e, R.string.cannot_save_project, 1).show()
        }
    }
}
