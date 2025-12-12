package com.gmail.heagoo.apkeditor.se

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.DynamicExpandListView
import java.util.ArrayList
import java.util.List
import java.util.Map

class SimpleEditActivity extends Activity implements View.OnClickListener, h {
    private z B

    /* renamed from: b, reason: collision with root package name */
    private String f1249b
    private com.gmail.heagoo.common.b c
    private Int d
    private u e
    private i f
    private c g
    private p h
    private r i
    private ListView j
    private DynamicExpandListView k
    private ListView l
    private LinearLayout m
    private Int o
    private ViewPager q
    private List r
    private View s
    private View t
    private View u
    private TextView v
    private TextView w
    private TextView x
    private Button y
    private TextView z
    private Int n = 0
    private Boolean A = false

    static /* synthetic */ Unit a(SimpleEditActivity simpleEditActivity) throws Throwable {
        com.gmail.heagoo.a.c.a.d(simpleEditActivity, "tmp")
        simpleEditActivity.B = z(simpleEditActivity.f1249b)
        simpleEditActivity.B.a()
    }

    static /* synthetic */ Unit f(SimpleEditActivity simpleEditActivity) {
        if (simpleEditActivity.d == 2) {
            simpleEditActivity.v.setTextColor(-1)
            simpleEditActivity.w.setTextColor(-1)
            simpleEditActivity.x.setTextColor(-1)
        } else {
            simpleEditActivity.v.setTextColor(simpleEditActivity.getResources().getColor(a.a.b.a.k.mdNavNormal(a.a.b.a.k.a(simpleEditActivity))))
            simpleEditActivity.v.setBackgroundResource(R.drawable.mtrl_nav_bg_unchecked)
            simpleEditActivity.w.setTextColor(simpleEditActivity.getResources().getColor(a.a.b.a.k.mdNavNormal(a.a.b.a.k.a(simpleEditActivity))))
            simpleEditActivity.w.setBackgroundResource(R.drawable.mtrl_nav_bg_unchecked)
            simpleEditActivity.x.setTextColor(simpleEditActivity.getResources().getColor(a.a.b.a.k.mdNavNormal(a.a.b.a.k.a(simpleEditActivity))))
            simpleEditActivity.x.setBackgroundResource(R.drawable.mtrl_nav_bg_unchecked)
        }
        switch (simpleEditActivity.n) {
            case 0:
                simpleEditActivity.v.setTextColor(simpleEditActivity.getResources().getColor(a.a.b.a.k.mdNavActivated(a.a.b.a.k.a(simpleEditActivity))))
                simpleEditActivity.v.setBackgroundResource(R.drawable.mtrl_nav_bg_checked)
                simpleEditActivity.z.setText(simpleEditActivity.e.c())
                break
            case 1:
                simpleEditActivity.w.setTextColor(simpleEditActivity.getResources().getColor(a.a.b.a.k.mdNavActivated(a.a.b.a.k.a(simpleEditActivity))))
                simpleEditActivity.w.setBackgroundResource(R.drawable.mtrl_nav_bg_checked)
                if (simpleEditActivity.B != null) {
                    simpleEditActivity.z.setText(String.format((String) simpleEditActivity.getResources().getText(R.string.image_summary), Integer.valueOf(simpleEditActivity.B.f1286a.size())))
                    break
                }
                break
            case 2:
                simpleEditActivity.x.setTextColor(simpleEditActivity.getResources().getColor(a.a.b.a.k.mdNavActivated(a.a.b.a.k.a(simpleEditActivity))))
                simpleEditActivity.x.setBackgroundResource(R.drawable.mtrl_nav_bg_checked)
                if (simpleEditActivity.B != null) {
                    simpleEditActivity.z.setText(String.format((String) simpleEditActivity.getResources().getText(R.string.audio_summary), Integer.valueOf(simpleEditActivity.B.d.size())))
                    break
                }
                break
        }
    }

    public final Unit a() {
        if (this.A) {
            return
        }
        this.y.setText(R.string.save)
        this.y.setTextColor(getResources().getColor(a.a.b.a.k.mdGoogleBtnText(a.a.b.a.k.a(this))))
        this.y.setBackgroundResource(R.drawable.mtrl_google_btn)
        this.A = true
    }

    @Override // com.gmail.heagoo.apkeditor.se.h
    public final Unit a(String str) {
        this.z.setText(str)
    }

    public final Unit a(Boolean z) {
        findViewById(R.id.progress_bar).setVisibility(8)
        if (!z) {
            Toast.makeText(this, this.i.f1275a, 0).show()
            finish()
            return
        }
        this.m.setVisibility(0)
        this.j = (ListView) this.s.findViewById(R.id.files_list)
        this.k = (DynamicExpandListView) this.t.findViewById(R.id.images_list)
        this.l = (ListView) this.u.findViewById(R.id.audios_list)
        this.e = u(this, this, this.B)
        this.j.setAdapter((ListAdapter) this.e)
        this.j.setOnItemClickListener(this.e)
        this.j.setOnItemLongClickListener(this.e)
        this.f = i(this.k, this, this.B)
        this.k.setAdapter((ListAdapter) this.f)
        this.k.setOnItemClickListener(this.f)
        this.k.setOnItemLongClickListener(this.f)
        this.g = c(this, this.B)
        this.l.setAdapter((ListAdapter) this.g)
        this.l.setOnItemClickListener(this.g)
        this.l.setOnItemLongClickListener(this.g)
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, Intent intent) {
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Int id = view.getId()
        if (id == R.id.files_label) {
            this.n = 0
            this.q.setCurrentItem(this.n)
            return
        }
        if (id == R.id.audio_label) {
            this.n = 2
            this.q.setCurrentItem(this.n)
            return
        }
        if (id == R.id.images_label) {
            this.n = 1
            this.q.setCurrentItem(this.n)
            return
        }
        if (id == R.id.btn_close) {
            if (this.A) {
                Map mapA = this.f.a()
                Map mapD = this.e.d()
                Map mapB = this.g.b()
                Intent intent = Intent(this, (Class<?>) ApkCreateActivity.class)
                com.gmail.heagoo.a.c.a.a(intent, "apkPath", this.f1249b)
                com.gmail.heagoo.a.c.a.a(intent, "packageName", this.c.f1448b)
                com.gmail.heagoo.a.c.a.a(intent, "imageReplaces", mapA)
                if (!mapD.isEmpty() || !mapB.isEmpty()) {
                    mapD.putAll(mapB)
                    com.gmail.heagoo.a.c.a.a(intent, "otherReplaces", mapD)
                }
                startActivity(intent)
            }
            finish()
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) throws Resources.NotFoundException {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_simpleedit)
        this.f1249b = com.gmail.heagoo.a.c.a.a(getIntent(), "apkPath")
        try {
            new com.gmail.heagoo.common.a()
            this.c = com.gmail.heagoo.common.a.a(this, this.f1249b)
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.cannot_parse_apk) + ": " + e.getMessage(), 1).show()
        }
        if (this.c == null) {
            finish()
            return
        }
        this.h = p(this)
        this.i = r(this)
        this.i.start()
        Int width = BitmapFactory.decodeResource(getResources(), R.drawable.pager_focus).getWidth()
        DisplayMetrics displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        this.o = displayMetrics.widthPixels
        Matrix().postTranslate(((this.o / 3) - width) / 2, 0.0f)
        this.m = (LinearLayout) findViewById(R.id.center_layout)
        this.z = (TextView) findViewById(R.id.tv_summary)
        this.v = (TextView) findViewById(R.id.files_label)
        this.w = (TextView) findViewById(R.id.images_label)
        this.x = (TextView) findViewById(R.id.audio_label)
        this.y = (Button) findViewById(R.id.btn_close)
        this.m.setVisibility(4)
        this.v.setOnClickListener(this)
        this.w.setOnClickListener(this)
        this.x.setOnClickListener(this)
        this.y.setOnClickListener(this)
        if (this.c != null) {
            ((ImageView) findViewById(R.id.apk_icon)).setImageDrawable(this.c.c)
            ((TextView) findViewById(R.id.apk_label)).setText(this.c.f1447a)
        }
        this.q = (ViewPager) findViewById(R.id.pagerView)
        this.r = ArrayList()
        LayoutInflater layoutInflater = getLayoutInflater()
        this.s = layoutInflater.inflate(R.layout.pageitem_files, (ViewGroup) null)
        this.t = layoutInflater.inflate(R.layout.pageitem_images, (ViewGroup) null)
        this.u = layoutInflater.inflate(R.layout.pageitem_audios, (ViewGroup) null)
        this.r.add(this.s)
        this.r.add(this.t)
        this.r.add(this.u)
        this.q.setAdapter(s(this, this.r))
        this.q.setCurrentItem(0)
        this.q.setOnPageChangeListener(q(this))
    }

    @Override // android.app.Activity
    fun onDestroy() {
        if (this.e != null) {
            this.e.e()
        }
        if (this.g != null) {
            this.g.a()
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
        bundle.putString("apkPath", this.f1249b)
        super.onSaveInstanceState(bundle)
    }
}
