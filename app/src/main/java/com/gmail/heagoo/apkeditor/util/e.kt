package com.gmail.heagoo.apkeditor.util

import android.app.Activity
import android.widget.Toast
import com.gmail.heagoo.apkeditor.ApkComposeActivity
import com.gmail.heagoo.apkeditor.fa
import com.gmail.heagoo.apkeditor.pro.R
import java.io.IOException
import java.util.Map
import javax.xml.xpath.XPathExpressionException

final class e implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Activity f1311a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ d f1312b

    e(d dVar, Activity activity) {
        this.f1312b = dVar
        this.f1311a = activity
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        this.f1312b.d.b()
        d dVar = this.f1312b
        Map mapD = this.f1312b.d.d()
        if (mapD != null) {
            for (Map.Entry entry : mapD.entrySet()) {
                String str = (String) entry.getKey()
                Map map = (Map) dVar.f1309a.get(str)
                if (map == null) {
                    dVar.f1309a.put(str, entry.getValue())
                } else {
                    map.putAll((Map) entry.getValue())
                }
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() throws XPathExpressionException, IOException {
        if (!this.f1312b.d.c()) {
            Toast.makeText(this.f1311a, R.string.str_fix_failed, 0).show()
            return
        }
        Toast.makeText(this.f1311a, this.f1312b.d.a(this.f1311a), 0).show()
        ((ApkComposeActivity) this.f1311a).c()
    }
}
