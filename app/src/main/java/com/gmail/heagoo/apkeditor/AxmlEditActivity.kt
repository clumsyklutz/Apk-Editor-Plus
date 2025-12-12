package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.se.ApkCreateActivity
import java.util.Map

class AxmlEditActivity extends Activity implements View.OnClickListener, com.gmail.heagoo.apkeditor.se.h {

    /* renamed from: b, reason: collision with root package name */
    private String f760b
    private com.gmail.heagoo.common.b c
    private com.gmail.heagoo.apkeditor.se.u e
    private bs f
    private bt g
    private ListView h
    private Button i
    private TextView j
    private com.gmail.heagoo.apkeditor.se.z k

    /* JADX INFO: Access modifiers changed from: private */
    fun a() {
        try {
            return getPackageManager().getApplicationInfo("com.gmail.heagoo.apkeditor.pro", 0).publicSourceDir
        } catch (Throwable th) {
            return null
        }
    }

    static /* synthetic */ Unit a(AxmlEditActivity axmlEditActivity) throws Throwable {
        axmlEditActivity.k = new com.gmail.heagoo.apkeditor.se.z(axmlEditActivity.f760b)
        axmlEditActivity.k.a()
    }

    static /* synthetic */ String c(AxmlEditActivity axmlEditActivity) {
        m(axmlEditActivity, null, null, null).a()
        return axmlEditActivity.getFilesDir().getAbsolutePath() + "/bin/"
    }

    @Override // com.gmail.heagoo.apkeditor.se.h
    public final Unit a(String str) {
        this.j.setText(str)
    }

    public final Unit a(Boolean z) {
        findViewById(R.id.progress_bar).setVisibility(8)
        if (!z) {
            Toast.makeText(this, this.g.f906a, 0).show()
            finish()
            return
        }
        this.h.setVisibility(0)
        this.e = new com.gmail.heagoo.apkeditor.se.u(this, this, this.k, true)
        this.h.setAdapter((ListAdapter) this.e)
        this.h.setOnItemClickListener(this.e)
        this.h.setOnItemLongClickListener(this.e)
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, Intent intent) {
        if (i == 0 && i2 == 1) {
            ey(this, bu(this, intent.getStringExtra("xmlPath"), intent.getStringExtra("extraString")), -1).show()
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_close) {
            finish()
            return
        }
        if (id == R.id.btn_save) {
            Map mapD = this.e.d()
            Intent intent = Intent(this, (Class<?>) ApkCreateActivity.class)
            a.a(intent, "apkPath", this.f760b)
            a.a(intent, "packageName", this.c.f1448b)
            if (!mapD.isEmpty()) {
                a.a(intent, "otherReplaces", mapD)
            }
            startActivity(intent)
            finish()
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) throws Resources.NotFoundException {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_axmledit)
        this.f760b = a.a(getIntent(), "apkPath")
        try {
            new com.gmail.heagoo.common.a()
            this.c = com.gmail.heagoo.common.a.a(this, this.f760b)
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.cannot_parse_apk) + ": " + e.getMessage(), 1).show()
        }
        if (this.c == null) {
            finish()
            return
        }
        this.f = bs(this)
        this.g = bt(this)
        this.g.start()
        this.h = (ListView) findViewById(R.id.files_list)
        this.j = (TextView) findViewById(R.id.tv_summary)
        this.i = (Button) findViewById(R.id.btn_save)
        Button button = (Button) findViewById(R.id.btn_close)
        this.h.setVisibility(4)
        this.i.setOnClickListener(this)
        button.setOnClickListener(this)
        if (this.c != null) {
            ((ImageView) findViewById(R.id.apk_icon)).setImageDrawable(this.c.c)
            ((TextView) findViewById(R.id.apk_label)).setText(this.c.f1447a)
        }
    }

    @Override // android.app.Activity
    fun onDestroy() {
        if (this.e != null) {
            this.e.e()
        }
        super.onDestroy()
    }

    @Override // android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // android.app.Activity
    fun onResume() {
        super.onResume()
    }

    @Override // android.app.Activity
    fun onSaveInstanceState(Bundle bundle) {
        bundle.putString("apkPath", this.f760b)
        super.onSaveInstanceState(bundle)
    }
}
