package com.gmail.heagoo.apkeditor

import android.os.Environment
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

final class am implements cu {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f850a

    am(ApkInfoActivity apkInfoActivity) {
        this.f850a = apkInfoActivity
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        String str3 = Environment.getExternalStorageDirectory().getPath() + "/ApkEditor/tmp"
        if (str3.startsWith(str)) {
            Toast.makeText(this.f850a, R.string.select_folder_err2, 1).show()
        } else if (str.startsWith(str3)) {
            Toast.makeText(this.f850a, R.string.select_folder_err1, 1).show()
        } else {
            this.f850a.e.e(str2, str)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return String.format(this.f850a.getString(R.string.folder_replace_tip), str2.substring(this.f850a.f758b.length() + 1), str)
    }
}
