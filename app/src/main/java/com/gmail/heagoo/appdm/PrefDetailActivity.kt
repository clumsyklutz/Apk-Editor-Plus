package com.gmail.heagoo.appdm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.appdm.util.FileCopyUtil
import com.gmail.heagoo.common.ccc
import com.gmail.heagoo.neweditor.EditorActivity
import java.io.FileOutputStream
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.List
import java.util.Map

class PrefDetailActivity extends Activity implements View.OnClickListener, com.gmail.heagoo.sqliteutil.a.c {

    /* renamed from: a, reason: collision with root package name */
    protected String f1353a

    /* renamed from: b, reason: collision with root package name */
    protected String f1354b
    private String c
    private m d
    private ScrollView e
    private ProgressBar f
    private TableLayout g
    private LinkedHashMap h
    private LinkedHashMap i
    private ArrayList j
    private Boolean l
    private com.gmail.heagoo.sqliteutil.a.b m
    private l n = l(this)
    private Button o
    private Int p
    private Boolean q

    static /* synthetic */ ccc a(Boolean z) {
        return z ? new com.gmail.heagoo.sqliteutil.c() : new com.gmail.heagoo.common.c()
    }

    static /* synthetic */ Unit a(PrefDetailActivity prefDetailActivity) {
        prefDetailActivity.m.b()
        prefDetailActivity.e.setVisibility(0)
        prefDetailActivity.o.setVisibility(0)
        prefDetailActivity.f.setVisibility(8)
    }

    static /* synthetic */ Unit a(PrefDetailActivity prefDetailActivity, LinkedHashMap linkedHashMap) {
        if (linkedHashMap != null) {
            prefDetailActivity.h = linkedHashMap
            prefDetailActivity.j = ArrayList()
            for (String str : linkedHashMap.keySet()) {
                ArrayList arrayList = ArrayList()
                Object obj = linkedHashMap.get(str)
                String string = ""
                if (obj != null) {
                    string = obj.toString()
                }
                arrayList.add(str)
                arrayList.add(string)
                prefDetailActivity.j.add(arrayList)
            }
            if (prefDetailActivity.m == null) {
                prefDetailActivity.m = new com.gmail.heagoo.sqliteutil.a.b(prefDetailActivity, null, prefDetailActivity.g, prefDetailActivity, prefDetailActivity.q)
            }
            ArrayList arrayList2 = ArrayList()
            arrayList2.add("Key")
            arrayList2.add("Value")
            prefDetailActivity.m.a(arrayList2)
            prefDetailActivity.m.a((List) prefDetailActivity.j)
            prefDetailActivity.m.a()
        }
    }

    public final Unit a() {
        this.d = m(this)
        this.d.start()
    }

    @Override // com.gmail.heagoo.sqliteutil.a.c
    public final Unit a(Int i, Boolean z) {
        if (z) {
            h(this, this.h, i, true, this.p).show()
        } else {
            h(this, this.i, i, true, this.p).show()
        }
    }

    public final Unit a(String str) {
        if (str == null) {
            this.n.sendEmptyMessage(0)
        } else {
            this.n.a(str)
            this.n.sendEmptyMessage(1)
        }
    }

    public final Unit a(String str, Object obj) {
        this.h.put(str, obj)
        if (this.l) {
            FileOutputStream fileOutputStream = FileOutputStream(this.f1354b)
            ax.a_005(this.h, fileOutputStream)
            fileOutputStream.close()
            FileCopyUtil.copyBack(this, this.f1354b, this.f1353a, this.l)
        } else {
            FileOutputStream fileOutputStream2 = FileOutputStream(this.f1353a)
            ax.a_005(this.h, fileOutputStream2)
            fileOutputStream2.close()
        }
        setResult(1)
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, Intent intent) {
        switch (i) {
            case 1000:
                if (i2 != 0) {
                    setResult(1)
                    a()
                    break
                }
                break
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id == R.id.button_close) {
            finish()
            return
        }
        if (id != R.id.button_search) {
            if (id == R.id.btn_raw_file) {
                Intent intent = Intent(this, (Class<?>) EditorActivity.class)
                Bundle bundle = Bundle()
                bundle.putString("filePath", this.f1354b)
                bundle.putString("realFilePath", this.f1353a)
                bundle.putBoolean("isRootMode", this.l)
                bundle.putIntArray("resourceIds", new Array<Int>{R.string.appdm_apk_file_path, R.string.appdm_apk_build_time, R.string.appdm_basic_info})
                intent.putExtras(bundle)
                startActivityForResult(intent, 1000)
                return
            }
            return
        }
        String strTrim = ((EditText) findViewById(R.id.tv_keyword)).getText().toString().trim()
        if (strTrim.equals("")) {
            Toast.makeText(this, R.string.warning_keyword_empty, 0).show()
            return
        }
        LinkedHashMap linkedHashMap = LinkedHashMap()
        ArrayList arrayList = ArrayList()
        for (Map.Entry entry : this.h.entrySet()) {
            String str = (String) entry.getKey()
            Object value = entry.getValue()
            Boolean z = str.toLowerCase().contains(strTrim)
            if (!z && value != null && value.toString().toLowerCase().contains(strTrim)) {
                z = true
            }
            if (z) {
                linkedHashMap.put(str, value)
                ArrayList arrayList2 = ArrayList()
                arrayList2.add(str)
                if (value != null) {
                    arrayList2.add(value.toString())
                } else {
                    arrayList2.add("")
                }
                arrayList.add(arrayList2)
            }
        }
        if (linkedHashMap.isEmpty()) {
            Toast.makeText(this, "No record found.", 0).show()
        } else {
            this.i = linkedHashMap
            this.m.b(arrayList)
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        super.onCreate(bundle)
        requestWindowFeature(1)
        this.p = ax.c_006(getIntent(), "themeId")
        this.q = this.p != 0
        setContentView(R.layout.appdm_activity_prefdetail)
        try {
            "gplay".equals(getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("com.gmail.heagoo.publish_channel"))
        } catch (Exception e) {
            e.printStackTrace()
        }
        this.l = ax.b_007(getIntent(), "isRootMode")
        this.c = ax.a_008(getIntent(), "appName")
        this.f1353a = ax.a_008(getIntent(), "xmlFilePath")
        this.e = (ScrollView) findViewById(R.id.scroll_view)
        this.f = (ProgressBar) findViewById(R.id.progress_bar)
        this.g = (TableLayout) findViewById(R.id.valueTable)
        ((TextView) findViewById(R.id.tv_appname)).setText(this.c)
        TextView textView = (TextView) findViewById(R.id.tv_prefname)
        String str = this.f1353a
        textView.setText(str.substring(str.lastIndexOf("/") + 1).substring(0, r2.length() - 4))
        findViewById(R.id.button_close).setOnClickListener(this)
        this.o = (Button) findViewById(R.id.button_search)
        this.o.setVisibility(8)
        this.o.setOnClickListener(this)
        findViewById(R.id.btn_raw_file).setOnClickListener(this)
        a()
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
