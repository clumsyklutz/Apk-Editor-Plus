package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.se.ApkCreateActivity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.Map
import java.util.zip.ZipFile

class CommonEditActivity extends Activity implements TextWatcher, View.OnClickListener, AdapterView.OnItemSelectedListener, cu {
    private String A
    private String B
    private Int C
    private Int D
    private String E

    /* renamed from: a, reason: collision with root package name */
    private String f763a

    /* renamed from: b, reason: collision with root package name */
    private com.gmail.heagoo.common.b f764b
    private ImageView c
    private ImageView d
    private TextView e
    private TextView f
    private TextView g
    private TextView h
    private EditText i
    private EditText j
    private EditText k
    private EditText l
    private EditText m
    private EditText n
    private EditText o
    private CheckBox p
    private CheckBox q
    private View r
    private com.gmail.heagoo.apkeditor.a.i s
    private com.gmail.heagoo.apkeditor.a.g t
    private by u = by(this)
    private Spinner v
    private Spinner w
    private String x
    private Map y
    private String z

    private fun a() throws Throwable {
        String absolutePath = getFilesDir().getAbsolutePath()
        String str = absolutePath + "/bin/_wrapper.apk"
        File file = File(absolutePath + "/bin")
        if (!file.exists() && !file.mkdirs()) {
            Toast.makeText(this, "Cannot create bin directory", 1).show()
            return
        }
        String strA = com.gmail.heagoo.common.s.a(4)
        try {
            this.x = a.d(this, "tmp") + ".xml"
            String str2 = null
            try {
                str2 = getPackageManager().getPackageInfo(getPackageName(), 0).versionName
            } catch (PackageManager.NameNotFoundException e) {
            }
            SharedPreferences sharedPreferences = getSharedPreferences("info", 0)
            String string = sharedPreferences.getString("wrapper_ver", "")
            if (!File(str).exists() || !string.equals(str2)) {
                try {
                    InputStream inputStreamOpen = getAssets().open("_wrapper")
                    FileOutputStream fileOutputStream = FileOutputStream(str)
                    a.b(inputStreamOpen, fileOutputStream)
                    inputStreamOpen.close()
                    fileOutputStream.close()
                    a.i(str)
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit()
                    editorEdit.putString("wrapper_ver", str2)
                    editorEdit.apply()
                } catch (Exception e2) {
                }
            }
            ZipFile zipFile = ZipFile(str)
            InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME))
            com.gmail.heagoo.apkeditor.a.a.o oVar = new com.gmail.heagoo.apkeditor.a.a.o(inputStream, this.x)
            oVar.a("com.example.droidpluginwrapper", this.z)
            oVar.a("DroidPluginWrapper", this.A)
            oVar.a("1.x.y", this.B)
            for (Int i = 0; i < 9; i++) {
                Char c = (Char) (i + 48)
                oVar.a("com.morgoo.droidplugin_stub_P0" + c, "com.morgoo.droidplugin_" + strA + "_P0" + c)
            }
            oVar.b(this.C)
            oVar.a(this.D >= 0 ? this.D : 0)
            if (!this.t.v.isEmpty()) {
                ArrayList arrayList = ArrayList()
                Iterator it = this.t.v.iterator()
                while (it.hasNext()) {
                    arrayList.add((String) it.next())
                }
                oVar.a(arrayList)
            }
            oVar.a()
            inputStream.close()
            zipFile.close()
            HashMap map = HashMap()
            map.put(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME, this.x)
            map.put("assets/1.apk", this.f763a)
            if (this.E != null) {
                map.put("res/drawable/ic_launcher.png", this.E)
            } else {
                String strB = b()
                if (strB != null) {
                    map.put("res/drawable/ic_launcher.png", strB)
                }
            }
            Intent intent = Intent(this, (Class<?>) ApkCreateActivity.class)
            a.a(intent, "apkPath", str)
            a.a(intent, "packageName", this.t.e)
            a.a(intent, "otherReplaces", map)
            ArrayList arrayList2 = ArrayList()
            arrayList2.add(new com.gmail.heagoo.apkeditor.a.a.n(strA))
            intent.putExtra("interfaces", arrayList2)
            startActivity(intent)
        } catch (Exception e3) {
            Toast.makeText(this, e3.getMessage(), 1).show()
            e3.printStackTrace()
        }
    }

    private fun a(TextView textView, EditText editText, Int i) {
        if (i == -1) {
            textView.setVisibility(8)
            editText.setVisibility(8)
        } else {
            editText.setText(String.valueOf(i))
            textView.setVisibility(0)
            editText.setVisibility(0)
        }
    }

    private fun a(Boolean z, Boolean z2) {
        HashMap map = HashMap()
        map.put(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME, this.x)
        Intent intent = Intent(this, (Class<?>) ApkCreateActivity.class)
        a.a(intent, "apkPath", this.f763a)
        a.a(intent, "packageName", this.t.e)
        a.a(intent, "otherReplaces", map)
        if (!this.t.e.equals(this.z) && this.p.isChecked()) {
            a.a(intent, "newPackageNameInArsc", this.z)
        }
        if (this.t.j < 0 && !this.A.equals(this.t.c)) {
            a.a(intent, "oldAppNameInArsc", this.t.c)
            a.a(intent, "newAppNameInArsc", this.A)
        }
        if (!this.y.isEmpty()) {
            HashMap map2 = HashMap()
            for (Map.Entry entry : this.y.entrySet()) {
                map2.put("L" + ((String) entry.getKey()).replaceAll("\\.", "/"), "L" + ((String) entry.getValue()).replaceAll("\\.", "/"))
            }
            a.a(intent, "classRenames", map2)
        }
        ArrayList arrayList = ArrayList()
        if (this.t.m != -1 && this.E != null) {
            arrayList.add(new com.gmail.heagoo.apkeditor.a.j(this.t.m, this.E))
        }
        if (z2) {
            arrayList.add(new com.gmail.heagoo.apkeditor.a.a.p(this.t.e, this.z))
            arrayList.add(new com.gmail.heagoo.apkeditor.a.a.q(this.t.e, this.z))
        }
        if (!arrayList.isEmpty()) {
            intent.putExtra("interfaces", arrayList)
        }
        startActivity(intent)
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0049 A[Catch: Exception -> 0x0073, TryCatch #0 {Exception -> 0x0073, blocks: (B:2:0x0000, B:4:0x0006, B:6:0x0027, B:8:0x0031, B:9:0x0035, B:11:0x0049, B:13:0x004f, B:21:0x0079, B:16:0x005d, B:15:0x0055), top: B:23:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b() throws java.io.IOException {
        /*
            r9 = this
            com.gmail.heagoo.common.b r1 = r9.f764b     // Catch: java.lang.Exception -> L73
            android.graphics.drawable.Drawable r1 = r1.c     // Catch: java.lang.Exception -> L73
            if (r1 == 0) goto L77
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L73
            r1.<init>()     // Catch: java.lang.Exception -> L73
            java.lang.String r2 = "tmp"
            java.lang.String r2 = com.gmail.heagoo.a.c.a.d(r9, r2)     // Catch: java.lang.Exception -> L73
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Exception -> L73
            java.lang.String r2 = "_launcher"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Exception -> L73
            java.lang.String r3 = r1.toString()     // Catch: java.lang.Exception -> L73
            com.gmail.heagoo.common.b r1 = r9.f764b     // Catch: java.lang.Exception -> L73
            android.graphics.drawable.Drawable r2 = r1.c     // Catch: java.lang.Exception -> L73
            Boolean r1 = r2 is android.graphics.drawable.BitmapDrawable     // Catch: java.lang.Exception -> L73
            if (r1 == 0) goto L49
            r0 = r2
            android.graphics.drawable.BitmapDrawable r0 = (android.graphics.drawable.BitmapDrawable) r0     // Catch: java.lang.Exception -> L73
            r1 = r0
            android.graphics.Bitmap r4 = r1.getBitmap()     // Catch: java.lang.Exception -> L73
            if (r4 == 0) goto L49
            android.graphics.Bitmap r1 = r1.getBitmap()     // Catch: java.lang.Exception -> L73
        L35:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Exception -> L73
            r2.<init>(r3)     // Catch: java.lang.Exception -> L73
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch: java.lang.Exception -> L73
            r5 = 100
            r1.compress(r4, r5, r2)     // Catch: java.lang.Exception -> L73
            r2.flush()     // Catch: java.lang.Exception -> L73
            r2.close()     // Catch: java.lang.Exception -> L73
            r1 = r3
        L48:
            return r1
        L49:
            Int r1 = r2.getIntrinsicWidth()     // Catch: java.lang.Exception -> L73
            if (r1 <= 0) goto L55
            Int r1 = r2.getIntrinsicHeight()     // Catch: java.lang.Exception -> L73
            if (r1 > 0) goto L79
        L55:
            r1 = 1
            r4 = 1
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Exception -> L73
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r1, r4, r5)     // Catch: java.lang.Exception -> L73
        L5d:
            android.graphics.Canvas r4 = new android.graphics.Canvas     // Catch: java.lang.Exception -> L73
            r4.<init>(r1)     // Catch: java.lang.Exception -> L73
            r5 = 0
            r6 = 0
            Int r7 = r4.getWidth()     // Catch: java.lang.Exception -> L73
            Int r8 = r4.getHeight()     // Catch: java.lang.Exception -> L73
            r2.setBounds(r5, r6, r7, r8)     // Catch: java.lang.Exception -> L73
            r2.draw(r4)     // Catch: java.lang.Exception -> L73
            goto L35
        L73:
            r1 = move-exception
            r1.printStackTrace()
        L77:
            r1 = 0
            goto L48
        L79:
            Int r1 = r2.getIntrinsicWidth()     // Catch: java.lang.Exception -> L73
            Int r4 = r2.getIntrinsicHeight()     // Catch: java.lang.Exception -> L73
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Exception -> L73
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r1, r4, r5)     // Catch: java.lang.Exception -> L73
            goto L5d
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.CommonEditActivity.b():java.lang.String")
    }

    public final Unit a(String str) throws Resources.NotFoundException {
        if (str != null) {
            Toast.makeText(this, getResources().getString(R.string.cannot_parse_apk) + ": " + str, 1).show()
            finish()
            return
        }
        this.t = this.s.a()
        this.t.c = this.f764b.f1447a
        this.c.setImageDrawable(this.f764b.c)
        this.e.setText(this.f764b.f1447a)
        this.i.setText(this.f764b.f1447a)
        this.j.setText(this.t.e)
        if (this.t.k == -1) {
            this.l.setVisibility(8)
            findViewById(R.id.tv_vername).setVisibility(8)
        } else {
            this.l.setText(this.t.d)
        }
        this.k.setText(String.valueOf(this.t.f822a))
        Int i = this.t.f823b
        if (i >= -1 && i < 3) {
            this.v.setSelection(i + 1)
        }
        this.d.setImageDrawable(this.f764b.c)
        if (this.t.m != -1) {
            this.d.setOnClickListener(this)
        }
        a(this.f, this.m, this.t.g)
        a(this.g, this.n, this.t.h)
        a(this.h, this.o, this.t.i)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        this.E = str
        try {
            this.d.setImageBitmap(BitmapFactory.decodeFile(this.E))
        } catch (Exception e) {
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return str.endsWith(".png")
    }

    @Override // android.text.TextWatcher
    fun afterTextChanged(Editable editable) {
        String string = editable.toString()
        if (this.t == null || !string.equals(this.t.e)) {
            this.r.setVisibility(0)
        } else {
            this.r.setVisibility(8)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    @Override // android.text.TextWatcher
    fun beforeTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }

    /* JADX WARN: Removed duplicated region for block: B:113:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0145  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onClick(android.view.View r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.CommonEditActivity.onClick(android.view.View):Unit")
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_commonedit)
        this.f763a = a.a(getIntent(), "apkPath")
        bx(this).start()
        this.c = (ImageView) findViewById(R.id.apk_icon)
        this.d = (ImageView) findViewById(R.id.launcher_icon)
        this.e = (TextView) findViewById(R.id.apk_label)
        this.f = (TextView) findViewById(R.id.tv_minSdkVersion)
        this.g = (TextView) findViewById(R.id.tv_targetSdkVersion)
        this.h = (TextView) findViewById(R.id.tv_maxSdkVersion)
        this.i = (EditText) findViewById(R.id.et_appname)
        this.j = (EditText) findViewById(R.id.et_pkgname)
        this.k = (EditText) findViewById(R.id.et_vercode)
        this.l = (EditText) findViewById(R.id.et_vername)
        this.m = (EditText) findViewById(R.id.et_minSdkVersion)
        this.n = (EditText) findViewById(R.id.et_targetSdkVersion)
        this.o = (EditText) findViewById(R.id.et_maxSdkVersion)
        this.p = (CheckBox) findViewById(R.id.cb_rename_resource)
        this.q = (CheckBox) findViewById(R.id.cb_rename_dex)
        this.r = findViewById(R.id.layout_rename_extra)
        this.w = (Spinner) findViewById(R.id.rename_method_spinner)
        if (getPackageName().endsWith("pro")) {
            this.j.addTextChangedListener(this)
        } else {
            this.j.setEnabled(false)
            this.w.setEnabled(false)
            findViewById(R.id.cb_rename_resource).setEnabled(false)
        }
        this.v = (Spinner) findViewById(R.id.location_spinner)
        Resources resources = getResources()
        ArrayAdapter arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, new Array<String>{resources.getString(R.string.location_0), resources.getString(R.string.location_1), resources.getString(R.string.location_2), resources.getString(R.string.location_3)})
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.v.setAdapter((SpinnerAdapter) arrayAdapter)
        ArrayAdapter arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, new Array<String>{resources.getString(R.string.pkg_rename_direct), resources.getString(R.string.pkg_rename_as_plugin)})
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.w.setAdapter((SpinnerAdapter) arrayAdapter2)
        this.w.setOnItemSelectedListener(this)
        findViewById(R.id.btn_close).setOnClickListener(this)
        findViewById(R.id.btn_save).setOnClickListener(this)
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    fun onItemSelected(AdapterView adapterView, View view, Int i, Long j) {
        if (i == 1) {
            this.p.setVisibility(8)
            this.q.setVisibility(8)
        } else {
            this.p.setVisibility(0)
            this.q.setVisibility(0)
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    fun onNothingSelected(AdapterView adapterView) {
    }

    @Override // android.text.TextWatcher
    fun onTextChanged(CharSequence charSequence, Int i, Int i2, Int i3) {
    }
}
