package com.gmail.heagoo.sqliteutil

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TableLayout
import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List

class SqliteTableViewActivity extends Activity implements com.gmail.heagoo.sqliteutil.a.c {

    /* renamed from: a, reason: collision with root package name */
    private String f1559a

    /* renamed from: b, reason: collision with root package name */
    private String f1560b
    private String c
    private ArrayList d
    private ArrayList e
    private ArrayList f
    private List g
    private TableLayout h
    private com.gmail.heagoo.sqliteutil.a.b i
    private Int j = 0
    private Int k
    private Button l
    private Button m
    private Int n

    private fun a(Cursor cursor, Int i) {
        try {
            String str = (String) this.e.get(i)
            if (f(str)) {
                return cursor.getString(i)
            }
            if (c(str)) {
                return StringBuilder().append(cursor.getLong(i)).toString()
            }
            if (str.equalsIgnoreCase("DATE") || str.equalsIgnoreCase("DATETIME")) {
                return cursor.getString(i)
            }
            if (b(str)) {
                return StringBuilder().append(cursor.getFloat(i)).toString()
            }
            if (a(str)) {
                return StringBuilder().append(cursor.getDouble(i)).toString()
            }
            if (str.startsWith("BLOB")) {
                Array<Byte> blob = cursor.getBlob(i)
                return blob.length > 64 ? "(Too big, first 64 Byte): \n" + ax.a_014(blob, 0, 64) : ax.a_014(blob, 0, blob.length)
            }
            try {
                return cursor.getString(i)
            } catch (Exception e) {
                return "(un-supported type)"
            }
        } catch (Exception e2) {
            return "(error to parse)"
        }
    }

    private fun a(SQLiteDatabase sQLiteDatabase, Int i, Int i2) {
        ArrayList arrayList = ArrayList()
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM " + this.c + " limit 30 offset " + i, null)
        while (cursorRawQuery.moveToNext()) {
            ArrayList arrayList2 = ArrayList()
            for (Int i3 = 0; i3 < this.d.size(); i3++) {
                arrayList2.add(a(cursorRawQuery, i3))
            }
            arrayList.add(arrayList2)
        }
        cursorRawQuery.close()
        return arrayList
    }

    private fun a() {
        this.i.a(this.g)
        this.i.a()
        this.i.b()
    }

    static /* synthetic */ Unit a(SqliteTableViewActivity sqliteTableViewActivity) {
        sqliteTableViewActivity.j -= 30
        if (sqliteTableViewActivity.j < 0) {
            sqliteTableViewActivity.j = 0
        }
        sqliteTableViewActivity.b()
        if (sqliteTableViewActivity.j > 0) {
            sqliteTableViewActivity.l.setVisibility(0)
        } else {
            sqliteTableViewActivity.l.setVisibility(8)
        }
        if (sqliteTableViewActivity.j + sqliteTableViewActivity.g.size() < sqliteTableViewActivity.k) {
            sqliteTableViewActivity.m.setVisibility(0)
        } else {
            sqliteTableViewActivity.m.setVisibility(8)
        }
        sqliteTableViewActivity.a()
    }

    protected static Boolean a(String str) {
        return str.equalsIgnoreCase("DOUBLE") || str.equalsIgnoreCase("DOUBLE PRECISION")
    }

    private fun b() {
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(this.f1560b, null, 1)
        this.g = a(sQLiteDatabaseOpenDatabase, this.j, 30)
        sQLiteDatabaseOpenDatabase.close()
    }

    static /* synthetic */ Unit b(SqliteTableViewActivity sqliteTableViewActivity) {
        sqliteTableViewActivity.j += 30
        sqliteTableViewActivity.b()
        if (sqliteTableViewActivity.j > 0) {
            sqliteTableViewActivity.l.setVisibility(0)
        } else {
            sqliteTableViewActivity.l.setVisibility(8)
        }
        if (sqliteTableViewActivity.j + sqliteTableViewActivity.g.size() < sqliteTableViewActivity.k) {
            sqliteTableViewActivity.m.setVisibility(0)
        } else {
            sqliteTableViewActivity.m.setVisibility(8)
        }
        sqliteTableViewActivity.a()
    }

    protected static Boolean b(String str) {
        return str.equalsIgnoreCase("REAL") || str.equalsIgnoreCase("FLOAT")
    }

    protected static Boolean c(String str) {
        return str.equalsIgnoreCase("INTEGER") || str.equalsIgnoreCase("LONG") || str.equalsIgnoreCase("TINYINT") || str.equalsIgnoreCase("SMALLINT") || str.equalsIgnoreCase("MEDIUMINT") || str.equalsIgnoreCase("BIGINT") || str.equalsIgnoreCase("UNSIGNED BIG INT") || str.startsWith("INT") || str.startsWith("BOOL")
    }

    protected static Boolean d(String str) {
        return str.startsWith("BOOL")
    }

    protected static Boolean e(String str) {
        return str.startsWith("BLOB")
    }

    protected static Boolean f(String str) {
        return str.equalsIgnoreCase("TEXT") || str.equalsIgnoreCase("NCHAR") || str.equalsIgnoreCase("CLOB") || str.endsWith("VARCHAR") || str.endsWith("CHARACTER") || str.startsWith("NUMERIC") || str.startsWith("DECIMAL")
    }

    @Override // com.gmail.heagoo.sqliteutil.a.c
    public final Unit a(Int i, Boolean z) {
        Intent intent = Intent(this, (Class<?>) SqliteRowViewActivity.class)
        ax.a_001(intent, "originDbFilePath", this.f1559a)
        ax.a_001(intent, "dbFilePath", this.f1560b)
        ax.a_001(intent, "tableName", this.c)
        ax.a_015(intent, "columnNames", this.d)
        ax.a_015(intent, "columnTypes", this.e)
        ax.a_015(intent, "columnIsPKs", this.f)
        ax.a_015(intent, "rowData", (ArrayList) this.g.get(i))
        ax.a_002(intent, "themeId", this.n)
        startActivityForResult(intent, 0)
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, Intent intent) {
        if (i == 0 && i2 == 1) {
            b()
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        requestWindowFeature(1)
        this.n = ax.c_006(getIntent(), "themeId")
        setContentView(R.layout.sql_activity_tableview)
        this.f1559a = ax.a_008(getIntent(), "originDbFilePath")
        this.f1560b = ax.a_008(getIntent(), "dbFilePath")
        this.c = ax.a_008(getIntent(), "tableName")
        this.j = 0
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(this.f1560b, null, 1)
        if (this.d == null) {
            this.d = ArrayList()
            this.e = ArrayList()
            this.f = ArrayList()
            Cursor cursorRawQuery = sQLiteDatabaseOpenDatabase.rawQuery("PRAGMA table_info(" + this.c + ")", null)
            if (cursorRawQuery.moveToFirst()) {
                Int columnIndex = cursorRawQuery.getColumnIndex("pk")
                do {
                    String string = cursorRawQuery.getString(1)
                    String string2 = cursorRawQuery.getString(2)
                    Int i = cursorRawQuery.getInt(columnIndex)
                    this.d.add(string)
                    if (string2 != null) {
                        string2 = string2.toUpperCase()
                    }
                    this.e.add(string2)
                    this.f.add(StringBuilder().append(i).toString())
                } while (cursorRawQuery.moveToNext());
            }
            cursorRawQuery.close()
        }
        Cursor cursorRawQuery2 = sQLiteDatabaseOpenDatabase.rawQuery("SELECT COUNT(*) FROM " + this.c, null)
        if (cursorRawQuery2.moveToFirst()) {
            this.k = cursorRawQuery2.getInt(0)
        }
        cursorRawQuery2.close()
        this.g = a(sQLiteDatabaseOpenDatabase, this.j, 30)
        sQLiteDatabaseOpenDatabase.close()
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    fun onKeyDown(Int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent)
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        this.h = (TableLayout) findViewById(R.id.valueTable)
        this.i = new com.gmail.heagoo.sqliteutil.a.b(this, null, this.h, this, this.n != 0)
        this.i.a(this.d)
        this.i.a(this.g)
        this.i.a()
        this.i.b()
        this.l = (Button) findViewById(R.id.button_prepage)
        this.m = (Button) findViewById(R.id.button_nextpage)
        if (this.k < 30) {
            this.m.setVisibility(8)
        } else {
            this.l.setOnClickListener(i(this))
            this.m.setOnClickListener(j(this))
        }
    }
}
