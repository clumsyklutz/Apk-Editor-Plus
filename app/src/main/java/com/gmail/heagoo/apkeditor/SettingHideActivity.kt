package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R

class SettingHideActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        addPreferencesFromResource(R.xml.pref_hide)
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        ((TextView) viewInflate.findViewById(android.R.id.title)).setText(R.string.settings_hidden)
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
        Preference preferenceFindPreference = findPreference("style_buttons")
        preferenceFindPreference.setSummary(a.a.b.a.n.c(a.a.b.a.n.d(this)))
        preferenceFindPreference.setOnPreferenceClickListener(this)
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    fun onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish()
                return true
            default:
                return false
        }
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    fun onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("style_buttons")) {
            a.a.b.a.k.md = true
            new a.a.b.a.n().f(this, R.string.layout_title, jb(this))
        }
        return false
    }
}
