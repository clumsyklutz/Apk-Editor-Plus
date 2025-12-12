package com.gmail.heagoo.appdm

import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

final class u implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Boolean f1406a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ PrefOverallActivity f1407b

    u(PrefOverallActivity prefOverallActivity, Boolean z) {
        this.f1407b = prefOverallActivity
        this.f1406a = z
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f1407b.findViewById(R.id.layout_scanning).setVisibility(4)
        if (!this.f1406a) {
            Toast.makeText(this.f1407b, this.f1407b.f1355a, 0).show()
            return
        }
        String strA = this.f1407b.d.a()
        if (strA != null) {
            Array<String> strArrSplit = strA.split("\n")
            for (String str : strArrSplit) {
                if (str.endsWith(".xml")) {
                    this.f1407b.q.add(new com.gmail.heagoo.appdm.util.j(str.substring(str.lastIndexOf(47) + 1).substring(0, r5.length() - 4), str))
                }
            }
        }
        String strB = this.f1407b.d.b()
        if (strB != null) {
            Array<String> strArrSplit2 = strB.split("\n")
            for (String str2 : strArrSplit2) {
                if (str2.endsWith(".db")) {
                    this.f1407b.r.add(new com.gmail.heagoo.appdm.util.j(str2.substring(str2.lastIndexOf(47) + 1).substring(0, r5.length() - 3), str2))
                }
            }
        }
        PrefOverallActivity.d(this.f1407b)
        PrefOverallActivity.e(this.f1407b)
        this.f1407b.b()
    }
}
