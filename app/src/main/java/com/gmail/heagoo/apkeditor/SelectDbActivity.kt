package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import com.gmail.heagoo.apkeditor.FileDialog
import com.gmail.heagoo.sqliteutil.SqliteTableListActivity
import java.io.File

class SelectDbActivity extends Activity {
    @Override // android.app.Activity
    fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        FileDialog fileDialog = FileDialog(this, File(Environment.getExternalStorageDirectory().getPath()), ".db")
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() { // from class: com.gmail.heagoo.apkeditor.SelectDbActivity.1
            @Override // com.gmail.heagoo.apkeditor.FileDialog.FileSelectedListener
            fun fileSelected(File file) {
                Intent intent = Intent(SelectDbActivity.this.getApplicationContext(), (Class<?>) SqliteTableListActivity.class)
                com.gmail.heagoo.a.c.ax.a_001(intent, "dbFilePath", file.getAbsolutePath())
                com.gmail.heagoo.a.c.ax.a_001(intent, "isRootMode", "false")
                intent.setFlags(268435456)
                SelectDbActivity.this.getApplicationContext().startActivity(intent)
                SelectDbActivity.this.finish()
            }
        })
        fileDialog.showDialog()
    }
}
