package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R

class AboutModActivity extends Activity implements View.OnClickListener {
    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        switch (view.getId()) {
            case R.id.about_card_1 /* 2131559013 */:
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://4pda.ru/forum/index.php?showuser=6304095")))
                break
            case R.id.about_card_2 /* 2131559014 */:
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://4pda.ru/forum/index.php?showuser=6390713")))
                break
            case R.id.about_card_3 /* 2131559015 */:
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://4pda.ru/forum/index.php?showuser=2359960")))
                break
            case R.id.about_card_4 /* 2131559016 */:
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://4pda.ru/forum/index.php?showtopic=575450&view=findpost&p=87900679")))
                break
            case R.id.about_card_5 /* 2131559017 */:
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=io.materialdesign.catalog")))
                break
            case R.id.about_card_6 /* 2131559018 */:
                startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://fonts.google.com/icons")))
                break
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_mod)
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        ((TextView) viewInflate.findViewById(android.R.id.title)).setText(R.string.action_mod)
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
        findViewById(R.id.about_card_1).setOnClickListener(this)
        findViewById(R.id.about_card_2).setOnClickListener(this)
        findViewById(R.id.about_card_3).setOnClickListener(this)
        findViewById(R.id.about_card_4).setOnClickListener(this)
        findViewById(R.id.about_card_5).setOnClickListener(this)
        findViewById(R.id.about_card_6).setOnClickListener(this)
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
