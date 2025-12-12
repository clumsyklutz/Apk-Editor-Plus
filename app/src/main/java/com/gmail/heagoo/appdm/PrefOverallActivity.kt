package com.gmail.heagoo.appdm

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.ccc
import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.Calendar
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class PrefOverallActivity extends Activity implements View.OnClickListener {
    private Drawable A
    private Drawable B
    private Drawable C
    private Drawable D
    private Boolean G
    private Boolean H
    private Long K
    private Long L
    private Long M
    private Int O
    private Boolean P

    /* renamed from: a, reason: collision with root package name */
    String f1355a
    private String c
    private ac d
    private ListView e
    private ListView f
    private ListView g
    private ListView h
    private LinearLayout i
    private ProgressBar j
    private TextView k
    private PackageManager l
    private ApplicationInfo m
    private PackageInfo n
    private String o
    private ad p
    private RadioButton s
    private RadioButton t
    private RadioButton u
    private RadioButton v
    private Drawable w
    private Drawable x
    private Drawable y
    private Drawable z
    private List q = ArrayList()
    private List r = ArrayList()
    private Int E = -1

    /* renamed from: b, reason: collision with root package name */
    Int f1356b = 0
    private Boolean J = false
    private Int N = 0

    static /* synthetic */ String a(PrefOverallActivity prefOverallActivity, String str) {
        String strSubstring
        Int iLastIndexOf = str.lastIndexOf(46)
        if (iLastIndexOf != -1) {
            strSubstring = str.substring(iLastIndexOf)
            if (strSubstring.contains("/")) {
                strSubstring = null
            }
        } else {
            strSubstring = null
        }
        StringBuilder sbAppend = StringBuilder().append(com.gmail.heagoo.appdm.util.i.b(prefOverallActivity)).append("/_work")
        if (strSubstring == null) {
            strSubstring = ""
        }
        String string = sbAppend.append(strSubstring).toString()
        ccc cccVarG = prefOverallActivity.g()
        File file = File(prefOverallActivity.getFilesDir(), "mycp")
        if (cccVarG.a(String.format((file.exists() ? file.getPath() : "cp") + " \"%s\" %s", str, string), null, 5000, false)) {
            return string
        }
        return null
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(String str, String str2) {
        new com.gmail.heagoo.common.p(this, s(this, str, str2), -1).show()
    }

    private fun a(List list) throws IOException {
        Array<String> strArr
        Resources resources = getResources()
        this.o = this.m.loadLabel(this.l).toString()
        list.add(g(resources.getString(R.string.appdm_app_name), this.o))
        list.add(g(resources.getString(R.string.appdm_package_name), this.c))
        File file = File(this.m.sourceDir)
        String string = ""
        Long length = 0
        if (Build.VERSION.SDK_INT >= 21 && (strArr = this.m.splitSourceDirs) != null && strArr.length > 0) {
            StringBuilder sb = StringBuilder()
            for (Int i = 0; i < strArr.length; i++) {
                sb.append("\n" + strArr[i])
                length += File(strArr[i]).length()
            }
            string = sb.toString()
        }
        list.add(g(resources.getString(R.string.appdm_app_size), com.gmail.heagoo.appdm.util.i.a(length + file.length())))
        list.add(g(resources.getString(R.string.appdm_version), resources.getString(R.string.appdm_version_code) + ": " + this.n.versionCode + "\n" + resources.getString(R.string.appdm_version_name) + ": " + this.n.versionName))
        list.add(g(resources.getString(R.string.appdm_apk_file_path), this.m.sourceDir + string, resources.getString(R.string.save), n(this)))
        try {
            ZipFile zipFile = ZipFile(this.m.sourceDir)
            ZipEntry entry = zipFile.getEntry(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)
            ZipEntry entry2 = zipFile.getEntry("classes.dex")
            Long time = entry != null ? entry.getTime() : Long.MIN_VALUE
            Long time2 = entry2 != null ? entry2.getTime() : Long.MIN_VALUE
            if (time2 < time) {
                time2 = time
            }
            Calendar calendar = Calendar.getInstance()
            calendar.setTimeInMillis(time2)
            list.add(g(resources.getString(R.string.appdm_apk_build_time), calendar.getTime().toString()))
            zipFile.close()
        } catch (IOException e) {
        }
        Calendar calendar2 = Calendar.getInstance()
        calendar2.setTimeInMillis(file.lastModified())
        list.add(g(resources.getString(R.string.appdm_install_time), calendar2.getTime().toString()))
        list.add(g(resources.getString(R.string.appdm_signature), ax.i_009(this.m.sourceDir)))
    }

    static /* synthetic */ Unit d(PrefOverallActivity prefOverallActivity) {
        prefOverallActivity.e.setAdapter((ListAdapter) j(prefOverallActivity, prefOverallActivity.q, prefOverallActivity.P))
        prefOverallActivity.e.setOnItemClickListener(z(prefOverallActivity))
    }

    static /* synthetic */ Unit e(PrefOverallActivity prefOverallActivity) {
        prefOverallActivity.f.setAdapter((ListAdapter) j(prefOverallActivity, prefOverallActivity.r, prefOverallActivity.P))
        prefOverallActivity.f.setOnItemClickListener(aa(prefOverallActivity))
    }

    protected final Unit a() {
        Array<String> strArr
        a(this, this.m.sourceDir, this.o).a()
        if (Build.VERSION.SDK_INT < 21 || (strArr = this.m.splitSourceDirs) == null || strArr.length <= 0) {
            return
        }
        for (Int i = 0; i < strArr.length; i++) {
            String name = File(strArr[i]).getName()
            a(this, strArr[i], this.o + "_" + name.substring(0, name.lastIndexOf(".apk"))).a()
        }
    }

    protected final Unit a(Int i) {
        String strSubstring
        ArrayList arrayList = ArrayList()
        String strA = this.p.a(arrayList)
        if (i < arrayList.size()) {
            com.gmail.heagoo.appdm.util.e eVar = (com.gmail.heagoo.appdm.util.e) arrayList.get(i)
            if (eVar.c) {
                String str = eVar.f1413a
                if ("..".equals(str)) {
                    Int iLastIndexOf = strA.lastIndexOf(47)
                    strSubstring = iLastIndexOf != -1 ? strA.substring(0, iLastIndexOf) : strA
                } else {
                    strSubstring = strA + "/" + str
                }
                new com.gmail.heagoo.common.p(this, r(this, strSubstring), -1).show()
                return
            }
            String str2 = strA + "/" + eVar.f1413a
            String str3 = eVar.f1413a
            String str4 = str3.endsWith(".xml") ? "xml.xml" : (str3.endsWith(".html") || str3.endsWith(".htm")) ? "html.xml" : str3.endsWith(".css") ? "css.xml" : str3.endsWith(".java") ? "java.xml" : str3.endsWith(".json") ? "json.xml" : str3.endsWith(".txt") ? "txt.xml" : str3.endsWith(".js") ? "js.xml" : null
            if (str4 != null) {
                a(str2, str4)
            } else {
                new com.gmail.heagoo.common.p(this, t(this, str2), -1).show()
            }
        }
    }

    public final Unit a(Boolean z) {
        synchronized (this) {
            this.E = z ? 1 : 0
            this.f1355a = this.d.c()
        }
        runOnUiThread(u(this, z))
    }

    protected final Unit b() {
        switch (this.f1356b) {
            case 0:
                c()
                break
            case 1:
                d()
                break
            case 2:
                e()
                break
        }
    }

    protected final Unit c() {
        this.g.setVisibility(0)
        this.e.setVisibility(4)
        this.f.setVisibility(4)
        this.h.setVisibility(4)
        this.i.setVisibility(4)
    }

    protected final Unit d() {
        synchronized (this) {
            switch (this.E) {
                case -1:
                    this.e.setVisibility(4)
                    this.i.setVisibility(0)
                    break
                case 0:
                    this.e.setVisibility(4)
                    this.i.setVisibility(0)
                    this.j.setVisibility(8)
                    this.k.setText(this.f1355a)
                    break
                case 1:
                    this.i.setVisibility(4)
                    this.e.setVisibility(0)
                    break
            }
        }
        this.g.setVisibility(4)
        this.f.setVisibility(4)
        this.h.setVisibility(4)
    }

    protected final Unit e() {
        synchronized (this) {
            switch (this.E) {
                case -1:
                    this.f.setVisibility(4)
                    this.i.setVisibility(0)
                    break
                case 0:
                    this.f.setVisibility(4)
                    this.i.setVisibility(0)
                    this.j.setVisibility(8)
                    this.k.setText(this.f1355a)
                    break
                case 1:
                    this.i.setVisibility(4)
                    this.f.setVisibility(0)
                    break
            }
        }
        this.g.setVisibility(4)
        this.e.setVisibility(4)
        this.h.setVisibility(4)
    }

    protected final Unit f() {
        this.i.setVisibility(4)
        this.g.setVisibility(4)
        this.e.setVisibility(4)
        this.f.setVisibility(4)
        this.h.setVisibility(0)
    }

    protected final ccc g() {
        return this.G ? new com.gmail.heagoo.sqliteutil.c() : new com.gmail.heagoo.common.c()
    }

    @Override // android.app.Activity
    fun onActivityResult(Int i, Int i2, Intent intent) {
        if (i == 1001) {
            this.L = System.currentTimeMillis()
            if (i2 == 1) {
                this.J = true
            }
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        if (view.getId() == R.id.button_backup) {
            ax.a_010("com.gmail.heagoo.appdm.free.a", "s", new Array<Class>{Activity.class, com.gmail.heagoo.b.a.class}, new Array<Object>{this, com.gmail.heagoo.b.a.a(this.l, this.m)})
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) throws IOException {
        super.onCreate(bundle)
        requestWindowFeature(1)
        Intent intent = getIntent()
        this.O = ax.c_006(intent, "themeId")
        this.P = this.O != 0
        setContentView(R.layout.appdm_activity_dataoverview)
        this.c = ax.a_008(intent, "packagePath")
        this.H = ax.b_007(intent, "backup")
        try {
            this.l = getPackageManager()
            this.m = this.l.getApplicationInfo(this.c, 0)
            this.n = this.l.getPackageInfo(this.c, 0)
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace()
        }
        this.M = System.currentTimeMillis()
        this.G = true
        try {
            if (this.n.sharedUserId != null && this.n.sharedUserId.equals(this.l.getPackageInfo(getPackageName(), 0).sharedUserId)) {
                this.G = false
            }
        } catch (PackageManager.NameNotFoundException e2) {
        }
        ((ImageView) findViewById(R.id.app_icon)).setImageDrawable(this.m.loadIcon(this.l))
        ((TextView) findViewById(R.id.app_name)).setText(this.m.loadLabel(this.l))
        ((TextView) findViewById(R.id.app_pkgpath)).setText(this.m.packageName)
        Button button = (Button) findViewById(R.id.button_backup)
        if (this.H) {
            button.setOnClickListener(this)
        } else {
            button.setVisibility(8)
        }
        this.g = (ListView) findViewById(R.id.appInfo_list)
        this.e = (ListView) findViewById(R.id.preference_list)
        this.f = (ListView) findViewById(R.id.database_list)
        this.h = (ListView) findViewById(R.id.files_list)
        this.i = (LinearLayout) findViewById(R.id.layout_scanning)
        this.j = (ProgressBar) findViewById(R.id.progress_bar)
        this.k = (TextView) findViewById(R.id.tv_tip)
        ArrayList arrayList = ArrayList()
        a(arrayList)
        this.g.setAdapter((ListAdapter) e(this, arrayList, this.P))
        String path = getFilesDir().getPath()
        this.p = ad(this, path.substring(0, path.indexOf(getPackageName())) + this.c + "/files", this.G, this.P)
        this.h.setAdapter((ListAdapter) this.p)
        this.h.setOnItemClickListener(ab(this))
        this.h.setOnItemLongClickListener(o(this))
        Resources resources = getResources()
        this.w = resources.getDrawable(R.drawable.appdm_info)
        this.x = resources.getDrawable(R.drawable.appdm_info_blue)
        this.y = resources.getDrawable(R.drawable.appdm_config)
        this.z = resources.getDrawable(R.drawable.appdm_config_blue)
        this.A = resources.getDrawable(R.drawable.appdm_db)
        this.B = resources.getDrawable(R.drawable.appdm_db_blue)
        this.C = resources.getDrawable(R.drawable.appdm_files)
        this.D = resources.getDrawable(R.drawable.appdm_files_blue)
        this.s = (RadioButton) findViewById(R.id.tab_appinfo)
        this.t = (RadioButton) findViewById(R.id.tab_preference)
        this.u = (RadioButton) findViewById(R.id.tab_database)
        this.v = (RadioButton) findViewById(R.id.tab_files)
        this.s.setOnClickListener(v(this))
        this.t.setOnClickListener(w(this))
        this.u.setOnClickListener(x(this))
        this.v.setOnClickListener(y(this))
        this.d = ac(this)
        this.d.start()
    }

    @Override // android.app.Activity
    fun onDestroy() {
        super.onDestroy()
    }

    @Override // android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
    }
}
