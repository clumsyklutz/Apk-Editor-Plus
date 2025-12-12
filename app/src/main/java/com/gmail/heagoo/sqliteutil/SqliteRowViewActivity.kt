package com.gmail.heagoo.sqliteutil

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.HashMap
import java.util.List

class SqliteRowViewActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    /* renamed from: a, reason: collision with root package name */
    private String f1555a

    /* renamed from: b, reason: collision with root package name */
    private String f1556b
    private String c
    private ArrayList d
    private ArrayList e
    private ArrayList f
    private ArrayList g
    private ListView h
    private Boolean i = true
    private Int j

    private fun a(List list) {
        String str = ""
        for (Int i = 0; i < this.f.size(); i++) {
            if ("1".equals(this.f.get(i))) {
                list.add(this.g.get(i))
                str = "".equals(str) ? ((String) this.d.get(i)) + "=?" : str + " AND " + ((String) this.d.get(i)) + "=?"
            }
        }
        if ("".equals(str)) {
            for (Int i2 = 0; i2 < this.d.size(); i2++) {
                list.add(this.g.get(i2))
                str = "".equals(str) ? ((String) this.d.get(i2)) + "=?" : str + " AND " + ((String) this.d.get(i2)) + "=?"
            }
        }
        return str
    }

    private fun a() throws Exception {
        if (this.f1556b.equals(this.f1555a)) {
            return
        }
        String path = Environment.getExternalStorageDirectory().getPath()
        if (!path.endsWith("/")) {
            path = path + "/"
        }
        this.f1556b = (path + "HackAppData/tmp/") + "tmp.db"
        if (!c().b(String.format("cat %s > %s", this.f1556b, this.f1555a), null, 2000)) {
            throw Exception("Can not write to DB file.")
        }
    }

    static /* synthetic */ Unit a(SqliteRowViewActivity sqliteRowViewActivity) throws Exception {
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(sqliteRowViewActivity.f1556b, null, 0)
        try {
            if (sQLiteDatabaseOpenDatabase == null) {
                throw Exception("Can not open database.")
            }
            try {
                ArrayList arrayList = ArrayList()
                sQLiteDatabaseOpenDatabase.delete(sqliteRowViewActivity.c, sqliteRowViewActivity.a(arrayList), (Array<String>) arrayList.toArray(new String[arrayList.size()]))
                sQLiteDatabaseOpenDatabase.close()
                sqliteRowViewActivity.a()
                sqliteRowViewActivity.setResult(1)
            } catch (Exception e) {
                throw e
            }
        } catch (Throwable th) {
            sQLiteDatabaseOpenDatabase.close()
            throw th
        }
    }

    private com.gmail.heagoo.sqliteutil.a.a b() {
        ArrayList arrayList = ArrayList()
        for (Int i = 0; i < this.d.size(); i++) {
            HashMap map = HashMap()
            map.put("NAME", this.d.get(i))
            map.put("VALUE", this.g.get(i))
            arrayList.add(map)
        }
        return new com.gmail.heagoo.sqliteutil.a.a(this, arrayList, R.layout.item_sql_raw, new Array<String>{"NAME", "VALUE"}, new Array<Int>{android.R.id.text1, android.R.id.text2}, this.j != 0)
    }

    public final Unit a(Int i, Object obj) throws Exception {
        String str = (String) this.d.get(i)
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(this.f1556b, null, 0)
        try {
            if (sQLiteDatabaseOpenDatabase == null) {
                throw Exception("Can not open database.")
            }
            try {
                ContentValues contentValues = ContentValues()
                if (obj is String) {
                    contentValues.put(str, (String) obj)
                } else if (obj is Boolean) {
                    contentValues.put(str, (Boolean) obj)
                } else if (obj is Integer) {
                    contentValues.put(str, (Integer) obj)
                } else if (obj is Long) {
                    contentValues.put(str, (Long) obj)
                } else if (obj is Short) {
                    contentValues.put(str, (Short) obj)
                } else if (obj is Double) {
                    contentValues.put(str, (Double) obj)
                } else if (obj is Float) {
                    contentValues.put(str, (Float) obj)
                } else {
                    if (!(obj is Byte)) {
                        throw Exception("Unrecognized value type!")
                    }
                    contentValues.put(str, (Byte) obj)
                }
                ArrayList arrayList = ArrayList()
                if (sQLiteDatabaseOpenDatabase.update(this.c, contentValues, a(arrayList), (Array<String>) arrayList.toArray(new String[arrayList.size()])) <= 0) {
                    throw Exception("Failed or no change detected!")
                }
                sQLiteDatabaseOpenDatabase.close()
                a()
                this.g.set(i, obj.toString())
                this.h.setAdapter((ListAdapter) b())
                setResult(1)
            } catch (Exception e) {
                throw e
            }
        } catch (Throwable th) {
            sQLiteDatabaseOpenDatabase.close()
            throw th
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id != R.id.btn_delete) {
            if (id == R.id.btn_close) {
                finish()
                return
            }
            return
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_title)
        builder.setMessage(R.string.msg_delete_record)
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null)
        builder.setPositiveButton(R.string.yes, f(this))
        builder.create()
        builder.show()
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        requestWindowFeature(1)
        Intent intent = getIntent()
        this.j = ax.c_006(intent, "themeId")
        setContentView(R.layout.sql_activity_rowview)
        this.f1555a = ax.a_008(intent, "originDbFilePath")
        this.f1556b = ax.a_008(intent, "dbFilePath")
        this.c = ax.a_008(intent, "tableName")
        this.d = ax.d_013(intent, "columnNames")
        this.e = ax.d_013(intent, "columnTypes")
        this.f = ax.d_013(intent, "columnIsPKs")
        this.g = ax.d_013(intent, "rowData")
        this.h = (ListView) findViewById(R.id.tableRowList)
        this.h.setAdapter((ListAdapter) b())
        this.h.setCacheColorHint(0)
        this.h.setOnItemClickListener(this)
        View viewFindViewById = findViewById(R.id.btn_delete)
        if (this.i) {
            viewFindViewById.setOnClickListener(this)
        } else {
            viewFindViewById.setVisibility(8)
        }
        findViewById(R.id.btn_close).setOnClickListener(this)
        setResult(0)
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        k(this, this.e, this.d, this.f, this.g, i, this.i, this.j).show()
    }
}
