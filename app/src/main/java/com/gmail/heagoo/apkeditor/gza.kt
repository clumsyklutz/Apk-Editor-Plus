package com.gmail.heagoo.apkeditor

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList
import java.util.List

class gza extends SQLiteOpenHelper {
    private static val DATABASE_NAME = "templates_db"
    private static val DATABASE_VERSION = 1
    private String LOG_TAG

    constructor(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1)
        this.LOG_TAG = "DatabaseHelper"
    }

    fun deleteImages(gzd gzdVar) {
        SQLiteDatabase readableDatabase = getReadableDatabase()
        Cursor cursorRawQuery = readableDatabase.rawQuery("SELECT id, name, images, content FROM news", (Array<String>) null)
        while (cursorRawQuery.moveToNext()) {
            cursorRawQuery.getInt(0)
            cursorRawQuery.getString(1)
            String string = cursorRawQuery.getString(2)
            cursorRawQuery.getString(3)
            Log.d(this.LOG_TAG, StringBuffer().append("images delete :").append(string).toString())
        }
        cursorRawQuery.close()
        readableDatabase.close()
    }

    fun deleteTabble() {
        SQLiteDatabase writableDatabase = getWritableDatabase()
        writableDatabase.delete(gzd.TABLE_NAME, (String) null, (Array<String>) null)
        writableDatabase.close()
    }

    fun deleteTemplates(gzd gzdVar) {
        SQLiteDatabase writableDatabase = getWritableDatabase()
        writableDatabase.delete(gzd.TABLE_NAME, StringBuffer().append(gzd.COLUMN_ID).append(" = ?").toString(), new Array<String>{String.valueOf(gzdVar.getId())})
        writableDatabase.close()
    }

    fun getAllTemplates() {
        ArrayList arrayList = ArrayList()
        String string = StringBuffer().append("SELECT  * FROM ").append(gzd.TABLE_NAME).toString()
        SQLiteDatabase writableDatabase = getWritableDatabase()
        Cursor cursorRawQuery = writableDatabase.rawQuery(string, (Array<String>) null)
        if (cursorRawQuery.moveToFirst()) {
            do {
                arrayList.add(gzd(cursorRawQuery.getInt(cursorRawQuery.getColumnIndex(gzd.COLUMN_ID)), cursorRawQuery.getString(cursorRawQuery.getColumnIndex(gzd.COLUMN_TITLE)), cursorRawQuery.getString(cursorRawQuery.getColumnIndex(gzd.COLUMN_CONTENT))))
            } while (cursorRawQuery.moveToNext());
        }
        writableDatabase.close()
        return arrayList
    }

    fun getTemplatesCount() {
        Cursor cursorRawQuery = getReadableDatabase().rawQuery(StringBuffer().append("SELECT  * FROM ").append(gzd.TABLE_NAME).toString(), (Array<String>) null)
        Int count = cursorRawQuery.getCount()
        cursorRawQuery.close()
        return count
    }

    fun getText(Long j) {
        Cursor cursorQuery = getReadableDatabase().query(gzd.TABLE_NAME, new Array<String>{gzd.COLUMN_ID, gzd.COLUMN_TITLE, gzd.COLUMN_CONTENT}, StringBuffer().append(gzd.COLUMN_ID).append("=?").toString(), new Array<String>{String.valueOf(j)}, (String) null, (String) null, (String) null, (String) null)
        if (cursorQuery != null) {
            cursorQuery.moveToFirst()
        }
        gzd gzdVar = gzd(cursorQuery.getInt(cursorQuery.getColumnIndex(gzd.COLUMN_ID)), cursorQuery.getString(cursorQuery.getColumnIndex(gzd.COLUMN_TITLE)), cursorQuery.getString(cursorQuery.getColumnIndex(gzd.COLUMN_CONTENT)))
        Log.d(this.LOG_TAG, StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append(StringBuffer().append("ID = ").append(cursorQuery.getInt(cursorQuery.getColumnIndex(gzd.COLUMN_ID))).toString()).append(", name = ").toString()).append(cursorQuery.getString(cursorQuery.getColumnIndex(gzd.COLUMN_TITLE))).toString()).append(", content = ").toString()).append(cursorQuery.getString(cursorQuery.getColumnIndex(gzd.COLUMN_CONTENT))).toString())
        cursorQuery.close()
        return gzdVar
    }

    fun insertTemplates(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase()
        ContentValues contentValues = ContentValues()
        contentValues.put(gzd.COLUMN_TITLE, str)
        contentValues.put(gzd.COLUMN_CONTENT, str2)
        Long jInsert = writableDatabase.insert(gzd.TABLE_NAME, (String) null, contentValues)
        Log.d(this.LOG_TAG, StringBuffer().append(" ID = ").append(jInsert).toString())
        writableDatabase.close()
        return jInsert
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    fun onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(gzd.CREATE_TABLE)
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    fun onUpgrade(SQLiteDatabase sQLiteDatabase, Int i, Int i2) throws SQLException {
        sQLiteDatabase.execSQL(StringBuffer().append("DROP TABLE IF EXISTS ").append(gzd.TABLE_NAME).toString())
        onCreate(sQLiteDatabase)
    }

    fun updateTemplates(gzd gzdVar) {
        SQLiteDatabase writableDatabase = getWritableDatabase()
        ContentValues contentValues = ContentValues()
        contentValues.put(gzd.COLUMN_TITLE, gzdVar.getTitle())
        contentValues.put(gzd.COLUMN_CONTENT, gzdVar.getContent())
        return writableDatabase.update(gzd.TABLE_NAME, contentValues, StringBuffer().append(gzd.COLUMN_ID).append(" = ?").toString(), new Array<String>{String.valueOf(gzdVar.getId())})
    }
}
