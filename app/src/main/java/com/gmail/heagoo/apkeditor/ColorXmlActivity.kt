package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.util.ArrayList

class ColorXmlActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private String f761a

    /* renamed from: b, reason: collision with root package name */
    private ArrayList f762b
    private com.b.a.h c
    private RelativeLayout d

    /* JADX WARN: Removed duplicated region for block: B:21:0x0062 A[LOOP:1: B:19:0x005a->B:21:0x0062, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0084 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a() throws java.lang.Throwable {
        /*
            r8 = this
            r1 = 0
            r6 = -1
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r8.f762b = r0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            java.io.File r3 = new java.io.File     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            java.lang.String r4 = r8.f761a     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            r3.<init>(r4)     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            r2.<init>(r3)     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            r0.<init>(r2)     // Catch: java.lang.Exception -> L73 java.lang.Throwable -> L7b
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
        L1e:
            if (r2 == 0) goto L55
            java.lang.String r3 = "<color name=\""
            Int r3 = r2.indexOf(r3)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            if (r3 == r6) goto L8c
            Int r3 = r3 + 13
            java.lang.String r4 = "\">"
            Int r4 = r2.indexOf(r4, r3)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            if (r4 == r6) goto L8c
            java.lang.String r3 = r2.substring(r3, r4)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            Int r4 = r4 + 2
            java.lang.String r5 = "</color>"
            Int r5 = r2.indexOf(r5, r4)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            if (r5 == r6) goto L8c
            java.lang.String r4 = r2.substring(r4, r5)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            com.b.a.g r2 = new com.b.a.g     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
        L49:
            if (r2 == 0) goto L50
            java.util.ArrayList r3 = r8.f762b     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            r3.add(r2)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
        L50:
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L8a
            goto L1e
        L55:
            r0.close()     // Catch: java.io.IOException -> L80
        L58:
            r0 = 0
            r1 = r0
        L5a:
            java.util.ArrayList r0 = r8.f762b
            Int r0 = r0.size()
            if (r1 >= r0) goto L84
            java.util.ArrayList r0 = r8.f762b
            java.lang.Object r0 = r0.get(r1)
            com.b.a.g r0 = (com.b.a.g) r0
            java.util.ArrayList r2 = r8.f762b
            r0.a(r8, r2)
            Int r0 = r1 + 1
            r1 = r0
            goto L5a
        L73:
            r0 = move-exception
            r0 = r1
        L75:
            r0.close()     // Catch: java.io.IOException -> L79
            goto L58
        L79:
            r0 = move-exception
            goto L58
        L7b:
            r0 = move-exception
        L7c:
            r1.close()     // Catch: java.io.IOException -> L82
        L7f:
            throw r0
        L80:
            r0 = move-exception
            goto L58
        L82:
            r1 = move-exception
            goto L7f
        L84:
            return
        L85:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L7c
        L8a:
            r1 = move-exception
            goto L75
        L8c:
            r2 = r1
            goto L49
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.ColorXmlActivity.a():Unit")
    }

    private fun b() {
        Intent intent = Intent()
        intent.putExtra("xmlPath", this.f761a)
        intent.putExtra("extraString", "res/values/colors.xml")
        setResult(1, intent)
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [android.widget.Button, android.widget.RelativeLayout] */
    protected final Unit a(Int i, Int i2) {
        if (i < this.f762b.size()) {
            com.b.a.g gVar = (com.b.a.g) this.f762b.get(i)
            gVar.c = i2
            gVar.f691b = "#" + Integer.toHexString(i2)
            this.d.setVisibility(0)
            this.c.notifyDataSetChanged()
        }
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, Intent intent) throws Throwable {
        switch (i) {
            case 0:
                if (i2 != 0) {
                    a()
                    this.c.a(this.f762b)
                    b()
                    break
                }
                break
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [Int] */
    /* JADX WARN: Type inference failed for: r1v3 */
    @Override // android.view.View.OnClickListener
    fun onClick(View view) throws Throwable {
        BufferedWriter bufferedWriter
        BufferedWriter id = view.getId()
        if (id == R.id.btn_close) {
            finish()
            return
        }
        try {
            if (id != R.id.btn_save) {
                if (id == R.id.btn_openeditor) {
                    Intent intentA = a.a(this, this.f761a, (String) null)
                    a.a(intentA, "syntaxFileName", "xml.xml")
                    a.a(intentA, "displayFileName", "colors.xml")
                    a.a(intentA, "extraString", "res/values/colors.xml")
                    startActivityForResult(intentA, 0)
                    return
                }
                return
            }
            try {
                bufferedWriter = BufferedWriter(FileWriter(this.f761a))
            } catch (Exception e) {
                e = e
                bufferedWriter = null
            } catch (Throwable th) {
                th = th
                id = 0
                if (id != 0) {
                    try {
                        id.close()
                    } catch (IOException e2) {
                    }
                }
                throw th
            }
            try {
                bufferedWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                bufferedWriter.write("<resources>\n")
                for (Int i = 0; i < this.f762b.size(); i++) {
                    bufferedWriter.write(((com.b.a.g) this.f762b.get(i)).toString())
                    bufferedWriter.write("\n")
                }
                bufferedWriter.write("</resources>")
                b()
                try {
                    bufferedWriter.close()
                } catch (IOException e3) {
                }
            } catch (Exception e4) {
                e = e4
                Toast.makeText(this, String.format(getString(R.string.general_error), e.getMessage()), 1).show()
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close()
                    } catch (IOException e5) {
                    }
                }
                finish()
            }
            finish()
        } catch (Throwable th2) {
            th = th2
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) throws Throwable {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_colors_xml)
        this.f761a = a.a(getIntent(), "xmlPath")
        a()
        ((TextView) findViewById(R.id.filename)).setText(this.f761a.substring(this.f761a.lastIndexOf(47) + 1))
        this.c = new com.b.a.h(this, this.f762b)
        ListView listView = (ListView) findViewById(R.id.color_list)
        listView.setAdapter((ListAdapter) this.c)
        listView.setOnItemClickListener(this)
        this.d = (RelativeLayout) findViewById(R.id.btn_save)
        this.d.setOnClickListener(this)
        ((RelativeLayout) findViewById(R.id.btn_close)).setOnClickListener(this)
        ((RelativeLayout) findViewById(R.id.btn_openeditor)).setOnClickListener(this)
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        new com.b.a.f(this, ((com.b.a.g) this.f762b.get(i)).c, bw(this, i)).show()
    }
}
