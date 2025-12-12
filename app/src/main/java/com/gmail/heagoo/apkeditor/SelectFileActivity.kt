package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.FileDialog
import java.io.File
import java.util.Map
import java.util.Set

class SelectFileActivity extends Activity {
    @Override // android.app.Activity
    fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        FileDialog fileDialog = FileDialog(this, File(Environment.getExternalStorageDirectory().getPath()), ".apk")
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() { // from class: com.gmail.heagoo.apkeditor.SelectFileActivity.1
            @Override // com.gmail.heagoo.apkeditor.FileDialog.FileSelectedListener
            fun fileSelected(File file) {
                String str = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(46)) + "_sign.apk"
                a.a(SelectFileActivity.this.getApplicationContext(), file.getAbsolutePath(), str, (Map<String, String>) null, (Map<String, String>) null, (Set<String>) null)
                Toast.makeText(SelectFileActivity.this.getApplicationContext(), "Signed apk -> " + str, 1).show()
                SelectFileActivity.this.finish()
            }
        })
        fileDialog.showDialog()
    }
}
