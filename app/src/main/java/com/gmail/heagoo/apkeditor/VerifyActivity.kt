package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import com.gmail.heagoo.a.c.Verify
import com.gmail.heagoo.apkeditor.FileDialog
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File

class VerifyActivity extends Activity {
    String response = "response"

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_verify)
        val textView = (TextView) findViewById(R.id.verify_text_view)
        if (bundle == null) {
            FileDialog fileDialog = FileDialog(this, File(Environment.getExternalStorageDirectory().getPath()), ".apk")
            fileDialog.addFileListener(new FileDialog.FileSelectedListener() { // from class: com.gmail.heagoo.apkeditor.VerifyActivity.1
                @Override // com.gmail.heagoo.apkeditor.FileDialog.FileSelectedListener
                fun fileSelected(File file) {
                    try {
                        VerifyActivity.this.response = Verify.verify(file.getAbsolutePath())
                        textView.setText(VerifyActivity.this.response)
                    } catch (Exception e) {
                        e.printStackTrace()
                    }
                }
            })
            fileDialog.showDialog()
        }
    }
}
