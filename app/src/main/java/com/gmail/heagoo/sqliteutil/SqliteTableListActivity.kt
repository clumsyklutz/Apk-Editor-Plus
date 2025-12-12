package com.gmail.heagoo.sqliteutil

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.ArrayList

class SqliteTableListActivity extends Activity implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private String f1557a

    /* renamed from: b, reason: collision with root package name */
    private String f1558b
    private ArrayList c
    private Boolean d
    private Int e = 0
    private Int f = -13421773

    private fun a() {
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(this.f1558b, null, 1)
        this.c = ArrayList()
        Cursor cursorRawQuery = sQLiteDatabaseOpenDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        if (cursorRawQuery.moveToFirst()) {
            while (!cursorRawQuery.isAfterLast()) {
                this.c.add(cursorRawQuery.getString(cursorRawQuery.getColumnIndex("name")))
                cursorRawQuery.moveToNext()
            }
        }
        sQLiteDatabaseOpenDatabase.close()
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
    protected fun onCreate(Bundle bundle) throws Exception {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        requestWindowFeature(1)
        setContentView(R.layout.sql_activity_tablelist)
        findViewById(R.id.btn_close).setOnClickListener(this)
        Intent intent = getIntent()
        this.f1557a = ax.a_008(intent, "dbFilePath")
        if ("false".equalsIgnoreCase(ax.a_008(intent, "isRootMode"))) {
            this.d = false
        } else {
            this.d = true
        }
        try {
            if (this.d) {
                if (!(Environment.getExternalStorageState().equals("mounted"))) {
                    throw Exception("Can not find SD Card!")
                }
                String path = Environment.getExternalStorageDirectory().getPath()
                if (!path.endsWith("/")) {
                    StringBuilder().append(path).append("/")
                }
                this.f1558b = File(getFilesDir(), "work.db").getPath()
                c cVar = c()
                File file = File(getFilesDir(), "mycp")
                if (!cVar.b(String.format((file.exists() ? file.getPath() : "cp") + " \"%s\" %s", this.f1557a, this.f1558b), null, 5000)) {
                    this.f1558b = this.f1557a
                }
            } else {
                this.f1558b = this.f1557a
            }
            a()
            TextView textView = (TextView) findViewById(R.id.title)
            StringBuilder sbAppend = StringBuilder().append(getResources().getString(R.string.tables_of)).append(" ")
            String str = this.f1557a
            textView.setText(sbAppend.append(str.substring(str.lastIndexOf(47) + 1)).toString())
            ListView listView = (ListView) findViewById(R.id.tableList)
            listView.setAdapter((ListAdapter) g(this, this, R.layout.item_sql_table, this.c))
            listView.setOnItemClickListener(h(this))
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), 0).show()
            finish()
        }
    }
}
