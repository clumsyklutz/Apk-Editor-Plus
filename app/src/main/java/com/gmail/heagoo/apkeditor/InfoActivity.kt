package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R

class InfoActivity extends Activity {
    private fun AB() {
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        ((TextView) viewInflate.findViewById(android.R.id.title)).setText(R.string.action_info)
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_info)
        AB()
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
