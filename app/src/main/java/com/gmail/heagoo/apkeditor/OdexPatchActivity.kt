package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R

class OdexPatchActivity extends Activity implements View.OnClickListener, cu {

    /* renamed from: a, reason: collision with root package name */
    private EditText f773a

    /* renamed from: b, reason: collision with root package name */
    private String f774b

    private fun AB() {
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        ((TextView) viewInflate.findViewById(android.R.id.title)).setText(R.string.action_odex)
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        this.f773a.setText(str)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return str.endsWith(".apk")
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_select_apkpath) {
            cn(this, this, ".apk", "", null).show()
        } else if (id == R.id.btn_apply_patch) {
            this.f774b = this.f773a.getText().toString()
            ey(this, et(this), -1).show()
        }
    }

    @Override // android.app.Activity
    fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        AB()
        setContentView(R.layout.activity_odex_patch)
        this.f773a = (EditText) findViewById(R.id.et_apkpath)
        ((Button) findViewById(R.id.btn_select_apkpath)).setOnClickListener(this)
        ((Button) findViewById(R.id.btn_apply_patch)).setOnClickListener(this)
    }

    @Override // android.app.Activity
    fun onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish()
                return true
            default:
                return false
        }
    }
}
