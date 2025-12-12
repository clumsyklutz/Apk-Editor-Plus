package com.gmail.heagoo.apkeditor.prj

import a.a.b.a.k
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.ey
import com.gmail.heagoo.apkeditor.pro.R
import common.types.ProjectInfo_V1
import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List

class ProjectListActivity extends Activity implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private d f1230a

    /* renamed from: b, reason: collision with root package name */
    private String f1231b
    private List c
    private b d
    private c e = c(this)

    private fun a(Array<File> fileArr) {
        if (fileArr == null) {
            return null
        }
        for (File file : fileArr) {
            if (file.isFile() && file.getName().equals("ae.prj")) {
                return file
            }
        }
        return null
    }

    private fun a(String str) {
        File fileA
        ProjectInfo_V1 projectInfo_V1A
        File file = File(str)
        ArrayList arrayList = ArrayList()
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (!file2.isFile() && (fileA = a(file2.listFiles())) != null && (projectInfo_V1A = ApkInfoActivity.a(file2.getPath())) != null) {
                    arrayList.add(f(file2.getName(), projectInfo_V1A.apkPath, projectInfo_V1A.decodeRootPath, fileA.lastModified()))
                }
            }
        }
        if (!arrayList.isEmpty()) {
            Collections.sort(arrayList, a(this))
        }
        return arrayList
    }

    final Unit a() {
        this.c = a(this.f1231b)
        this.f1230a.a(this.c)
        this.f1230a.notifyDataSetChanged()
        ((TextView) findViewById(R.id.tv_title)).setText(String.format(getString(R.string.project_num), Integer.valueOf(this.f1230a.a())))
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int iIntValue
        Int id = view.getId()
        if (id == R.id.btn_close) {
            finish()
        } else {
            if (id != R.id.menu_delete || (iIntValue = ((Integer) view.getTag()).intValue()) >= this.c.size()) {
                return
            }
            ey(this, g(this, (f) this.c.get(iIntValue)), -1).show()
        }
    }

    @Override // android.app.Activity
    fun onCreate(Bundle bundle) {
        setTheme(k.mdNoActionBar(k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_projectlist)
        ListView listView = (ListView) findViewById(R.id.project_list)
        try {
            this.f1231b = com.gmail.heagoo.a.c.a.d(this, ".projects")
            this.c = a(this.f1231b)
            this.f1230a = d(this, this.c)
            listView.setAdapter((ListAdapter) this.f1230a)
            listView.setOnItemClickListener(this.f1230a)
            this.d = b(this)
            this.d.start()
            ((TextView) findViewById(R.id.tv_title)).setText(String.format(getString(R.string.project_num), Integer.valueOf(this.f1230a.a())))
            findViewById(R.id.btn_close).setOnClickListener(this)
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 1).show()
        }
    }

    @Override // android.app.Activity
    fun onDestroy() {
        if (this.d != null && this.d.isAlive()) {
            this.d.a()
        }
        super.onDestroy()
    }
}
