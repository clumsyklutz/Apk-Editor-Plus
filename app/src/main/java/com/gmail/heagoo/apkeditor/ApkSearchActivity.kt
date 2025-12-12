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
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.se.SimpleEditActivity
import java.util.ArrayList
import java.util.List

class ApkSearchActivity extends Activity implements AdapterView.OnItemClickListener, cd, View.OnClickListener {

    /* renamed from: b, reason: collision with root package name */
    private String f759b
    private String c
    private List d = ArrayList()
    private TextView e
    private View f
    private bc g

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Int i) {
        String strSubstring = String.format(getString(R.string.str_files_found), Integer.valueOf(i), this.f759b)
        if ("".equals(this.f759b)) {
            strSubstring = strSubstring.substring(0, strSubstring.length() - 4)
        }
        this.e.setText(strSubstring)
    }

    @Override // com.gmail.heagoo.apkeditor.cd
    public final Unit a(Int i, String str) {
        Intent intent = null
        switch (i) {
            case 0:
                UserAppActivity.a(this, str)
                return
            case 1:
                intent = Intent(this, (Class<?>) SimpleEditActivity.class)
                break
            case 2:
                intent = Intent(this, (Class<?>) CommonEditActivity.class)
                break
            case 4:
                intent = Intent(this, (Class<?>) AxmlEditActivity.class)
                break
        }
        if (intent != null) {
            a.a(intent, "apkPath", str)
            startActivity(intent)
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close /* 2131558539 */:
                finish()
                break
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_apksearch)
        Intent intent = getIntent()
        this.f759b = a.a(intent, "Keyword")
        this.c = a.a(intent, "Path")
        this.e = (TextView) findViewById(R.id.title)
        ((RelativeLayout) findViewById(R.id.btn_close)).setOnClickListener(this)
        this.f = findViewById(R.id.searching_layout)
        ListView listView = (ListView) findViewById(R.id.listview_apkfiles)
        this.g = bc(this)
        listView.setAdapter((ListAdapter) this.g)
        listView.setOnItemClickListener(this)
        a(0)
        bj(this).start()
    }

    @Override // android.app.Activity
    fun onDestroy() {
        super.onDestroy()
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        if (i < this.d.size()) {
            String str = (String) this.d.get(i)
            if (MainActivity.a(this)) {
                cc(this, this, str).show()
            } else {
                UserAppActivity.a(this, str)
            }
        }
    }

    @Override // android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // android.app.Activity
    fun onResume() {
        super.onResume()
    }
}
