package com.gmail.heagoo.appdm

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.ccc
import java.io.File
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.List

class ad extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1363a

    /* renamed from: b, reason: collision with root package name */
    private String f1364b
    private String c
    private Boolean d
    private Boolean e
    private List f = ArrayList()
    private String g

    constructor(PrefOverallActivity prefOverallActivity, String str, Boolean z, Boolean z2) {
        this.f1363a = WeakReference(prefOverallActivity)
        this.f1364b = str
        this.d = z
        this.e = z2
        this.c = str
        this.g = prefOverallActivity.getString(R.string.appdm_file_size) + " "
        ag(this, this.c).start()
    }

    protected static com.gmail.heagoo.appdm.util.e a() {
        com.gmail.heagoo.appdm.util.e eVar = new com.gmail.heagoo.appdm.util.e()
        eVar.f1413a = ".."
        eVar.c = true
        return eVar
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0076 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List b(java.lang.String r6) throws java.lang.Throwable {
        /*
            r2 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L80
            java.io.StringReader r0 = new java.io.StringReader     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L80
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L80
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L72 java.io.IOException -> L80
            java.lang.String r0 = r1.readLine()     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
        L14:
            if (r0 == 0) goto L6c
            java.lang.String r4 = "\\s+"
            java.lang.Array<String> r4 = r0.split(r4)     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            Int r0 = r4.length     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 5
            if (r0 < r5) goto L4d
            r0 = 0
            r0 = r4[r0]     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 0
            Char r0 = r0.charAt(r5)     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 45
            if (r0 != r5) goto L52
            com.gmail.heagoo.appdm.util.e r0 = new com.gmail.heagoo.appdm.util.e     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r0.<init>()     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 0
            r0.c = r5     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 3
            r5 = r4[r5]     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e java.lang.Throwable -> L83
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e java.lang.Throwable -> L83
            Int r5 = r5.intValue()     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e java.lang.Throwable -> L83
            r0.f1414b = r5     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e java.lang.Throwable -> L83
        L41:
            if (r0 == 0) goto L4d
            Int r5 = r4.length     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            Int r5 = r5 + (-1)
            r4 = r4[r5]     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r0.f1413a = r4     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r3.add(r0)     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
        L4d:
            java.lang.String r0 = r1.readLine()     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            goto L14
        L52:
            r5 = 100
            if (r0 != r5) goto L85
            com.gmail.heagoo.appdm.util.e r0 = new com.gmail.heagoo.appdm.util.e     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r0.<init>()     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 1
            r0.c = r5     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            r5 = 0
            r0.f1414b = r5     // Catch: java.io.IOException -> L62 java.lang.Throwable -> L7e
            goto L41
        L62:
            r0 = move-exception
        L63:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L7e
            if (r1 == 0) goto L6b
            r1.close()     // Catch: java.io.IOException -> L7a
        L6b:
            return r3
        L6c:
            r1.close()     // Catch: java.io.IOException -> L70
            goto L6b
        L70:
            r0 = move-exception
            goto L6b
        L72:
            r0 = move-exception
            r1 = r2
        L74:
            if (r1 == 0) goto L79
            r1.close()     // Catch: java.io.IOException -> L7c
        L79:
            throw r0
        L7a:
            r0 = move-exception
            goto L6b
        L7c:
            r1 = move-exception
            goto L79
        L7e:
            r0 = move-exception
            goto L74
        L80:
            r0 = move-exception
            r1 = r2
            goto L63
        L83:
            r5 = move-exception
            goto L41
        L85:
            r0 = r2
            goto L41
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.appdm.ad.b(java.lang.String):java.util.List")
    }

    public final String a(List list) {
        String str
        synchronized (this.f) {
            list.addAll(this.f)
            str = this.c
        }
        return str
    }

    protected final List a(String str) {
        return a(str, false)
    }

    protected final List a(String str, Boolean z) {
        String strA
        if (this.d) {
            ccc cccVarG = ((PrefOverallActivity) this.f1363a.get()).g()
            if (cccVarG.a(String.format("ls -l %s", str), null, 5000, z) && (strA = cccVarG.a()) != null) {
                return b(strA)
            }
            ((PrefOverallActivity) this.f1363a.get()).runOnUiThread(ae(this, "Read error, please try again."))
            return null
        }
        ArrayList arrayList = ArrayList()
        Array<File> fileArrListFiles = File(str).listFiles()
        if (fileArrListFiles == null) {
            return arrayList
        }
        for (File file : fileArrListFiles) {
            com.gmail.heagoo.appdm.util.e eVar = new com.gmail.heagoo.appdm.util.e()
            eVar.f1413a = file.getName()
            eVar.c = file.isDirectory()
            eVar.f1414b = (Int) file.length()
            arrayList.add(eVar)
        }
        return arrayList
    }

    public final Unit a(String str, List list) {
        Activity activity = (Activity) this.f1363a.get()
        if (activity == null) {
            return
        }
        activity.runOnUiThread(af(this, str, list))
    }

    @Override // android.widget.Adapter
    public final Int getCount() {
        return this.f.size()
    }

    @Override // android.widget.Adapter
    public final Object getItem(Int i) {
        return this.f.get(i)
    }

    @Override // android.widget.Adapter
    public final Long getItemId(Int i) {
        return i
    }

    @Override // android.widget.Adapter
    public final View getView(Int i, View view, ViewGroup viewGroup) {
        ah ahVar
        com.gmail.heagoo.appdm.util.e eVar = (com.gmail.heagoo.appdm.util.e) getItem(i)
        if (eVar == null) {
            return null
        }
        if (view == null) {
            view = LayoutInflater.from((Context) this.f1363a.get()).inflate(R.layout.appdm_item_file, (ViewGroup) null)
            ah ahVar2 = ah()
            ahVar2.f1371a = (ImageView) view.findViewById(R.id.file_icon)
            ahVar2.f1372b = (TextView) view.findViewById(R.id.filename)
            ahVar2.c = (TextView) view.findViewById(R.id.detail1)
            view.setTag(ahVar2)
            ahVar = ahVar2
        } else {
            ahVar = (ah) view.getTag()
        }
        if (eVar.c) {
            ahVar.f1371a.setImageResource(R.drawable.ic_file_folder)
            ahVar.c.setVisibility(8)
        } else {
            ahVar.f1371a.setImageResource(R.drawable.ic_file_unknown)
            ahVar.c.setText(this.g + eVar.f1414b)
            ahVar.c.setVisibility(0)
        }
        ahVar.f1372b.setText(eVar.f1413a)
        return view
    }
}
