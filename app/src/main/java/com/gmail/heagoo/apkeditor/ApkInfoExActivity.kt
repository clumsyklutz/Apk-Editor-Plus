package com.gmail.heagoo.apkeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.util.Set

class ApkInfoExActivity extends ApkInfoActivity {
    private av A = av(this)
    private Boolean B
    private LinearLayout n
    private View o
    private View p
    private View q
    private View r
    private View s
    private View t
    private View u
    private View v
    private View w
    private View x
    private View y
    private View z

    private fun a(Int i, Int i2) {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.item_res_menu, (ViewGroup) null)
        ((ImageView) viewInflate.findViewById(R.id.menu_icon)).setImageResource(i)
        ((TextView) viewInflate.findViewById(R.id.menu_title)).setText(i2)
        viewInflate.setId(i)
        viewInflate.setOnClickListener(this.A)
        return viewInflate
    }

    private fun a(View view, Boolean z) {
        ImageView imageView = (ImageView) view.findViewById(R.id.menu_icon)
        TextView textView = (TextView) view.findViewById(R.id.menu_title)
        if (z) {
            imageView.getDrawable().setAlpha(255)
            textView.setEnabled(true)
        } else {
            imageView.getDrawable().setAlpha(127)
            textView.setEnabled(false)
        }
        view.setClickable(z)
        view.setEnabled(z)
    }

    @Override // com.gmail.heagoo.apkeditor.ApkInfoActivity, com.gmail.heagoo.apkeditor.fn
    public final Unit b(Set set) {
        super.b(set)
        if (set.size() == 1) {
            a(this.y, true)
            a(this.z, true)
        } else {
            a(this.y, false)
            a(this.z, false)
        }
    }

    public final Unit n() {
        this.i = !this.i
        this.g.setImageResource(this.B ? this.i ? R.drawable.searchtxt_checked_white : R.drawable.searchtxt_unchecked_white : this.i ? R.drawable.searchtxt_checked : R.drawable.searchtxt_unchecked)
    }

    public final Unit o() {
        this.j = !this.j
        this.h.setImageResource(this.B ? this.j ? R.drawable.ic_case_sensitive_white : R.drawable.ic_case_insensitive_white : this.j ? R.drawable.ic_case_sensitive : R.drawable.ic_case_insensitive)
    }

    @Override // com.gmail.heagoo.apkeditor.ApkInfoActivity, android.app.Activity
    protected fun onCreate(Bundle bundle) {
        super.onCreate(bundle)
        getWindow().setFlags(128, 128)
        this.B = this.l != 0
        this.o = findViewById(R.id.menu_home)
        this.p = findViewById(R.id.menu_done)
        this.q = findViewById(R.id.menu_select)
        this.r = findViewById(R.id.menu_addfile)
        this.s = findViewById(R.id.menu_addfolder)
        this.t = findViewById(R.id.menu_searchoptions)
        this.u = findViewById(R.id.menu_caseinsensitive)
        this.o.setOnClickListener(this.A)
        this.p.setOnClickListener(this.A)
        this.q.setOnClickListener(this.A)
        this.r.setOnClickListener(this.A)
        this.s.setOnClickListener(this.A)
        this.t.setOnClickListener(this.A)
        this.u.setOnClickListener(this.A)
        this.n = (LinearLayout) findViewById(R.id.res_menu_layout)
        this.v = a(R.drawable.ic_res_extract, R.string.extract)
        this.y = a(R.drawable.ic_res_replace, R.string.replace)
        this.x = a(R.drawable.ic_res_search, R.string.search)
        this.w = a(R.drawable.ic_res_delete, R.string.delete)
        this.z = a(R.drawable.ic_res_detail, R.string.detail)
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0f)
        this.n.addView(this.v, layoutParams)
        this.n.addView(this.y, layoutParams)
        this.n.addView(this.x, layoutParams)
        this.n.addView(this.w, layoutParams)
        this.n.addView(this.z, layoutParams)
    }

    @Override // com.gmail.heagoo.apkeditor.ApkInfoActivity, android.app.Activity
    fun onDestroy() {
        super.onDestroy()
    }

    @Override // com.gmail.heagoo.apkeditor.ApkInfoActivity, android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // com.gmail.heagoo.apkeditor.ApkInfoActivity, android.app.Activity
    fun onResume() {
        super.onResume()
    }
}
