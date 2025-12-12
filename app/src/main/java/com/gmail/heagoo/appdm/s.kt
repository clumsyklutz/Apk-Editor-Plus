package com.gmail.heagoo.appdm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.gmail.heagoo.apkeditor.fa
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.neweditor.EditorActivity

final class s implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f1402a = null

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1403b
    private /* synthetic */ String c
    private /* synthetic */ PrefOverallActivity d

    s(PrefOverallActivity prefOverallActivity, String str, String str2) {
        this.d = prefOverallActivity
        this.f1403b = str
        this.c = str2
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        this.f1402a = PrefOverallActivity.a(this.d, this.f1403b)
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.f1402a == null) {
            Toast.makeText(this.d, "Failed to open the file.", 0).show()
            return
        }
        Intent intent = Intent(this.d, (Class<?>) EditorActivity.class)
        Bundle bundle = Bundle()
        bundle.putString("filePath", this.f1402a)
        bundle.putString("realFilePath", this.f1403b)
        if (this.c != null) {
            bundle.putString("syntaxFileName", this.c)
        }
        bundle.putBoolean("isRootMode", this.d.G)
        bundle.putIntArray("resourceIds", new Array<Int>{R.string.appdm_apk_file_path, R.string.appdm_apk_build_time, R.string.appdm_basic_info})
        intent.putExtras(bundle)
        this.d.startActivityForResult(intent, 1000)
    }
}
