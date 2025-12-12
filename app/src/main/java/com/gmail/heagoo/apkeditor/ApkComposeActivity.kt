package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.common.a
import java.io.File
import java.io.IOException
import java.util.Map
import javax.xml.xpath.XPathExpressionException

class ApkComposeActivity extends Activity implements View.OnClickListener, com.gmail.heagoo.common.j {
    private k A
    private com.gmail.heagoo.apkeditor.util.d B
    private Boolean C
    private String E

    /* renamed from: a, reason: collision with root package name */
    protected String f751a

    /* renamed from: b, reason: collision with root package name */
    private LinearLayout f752b
    private LinearLayout c
    private TextView d
    private TextView e
    private ListView f
    private ImageView g
    private Button h
    private Button i
    private LinearLayout j
    private TextView k
    private TextView l
    private Button m
    private Button n
    private Button o
    private Button p
    private f r
    private com.gmail.heagoo.common.k s
    private String t
    private Long u
    private String v
    private String w
    private String x
    private Boolean y
    private Boolean z
    private Boolean q = false
    private ServiceConnection D = c(this)

    private fun b(String str) throws PackageManager.NameNotFoundException {
        try {
            getPackageManager().getPackageInfo(str, 0)
            return true
        } catch (Exception e) {
            return false
        }
    }

    static /* synthetic */ Boolean c(ApkComposeActivity apkComposeActivity, Boolean z) {
        apkComposeActivity.q = true
        return true
    }

    private fun d() {
        try {
            a()
            com.gmail.heagoo.common.b bVarA = a.a(this, this.v)
            if (bVarA != null) {
                return bVarA.f1448b
            }
        } catch (Exception e) {
        }
        return null
    }

    static /* synthetic */ com.a.b.a.e.j e(ApkComposeActivity apkComposeActivity) {
        return null
    }

    @Override // com.gmail.heagoo.common.j
    public final Unit a() {
        this.r.sendEmptyMessage(2)
    }

    @Override // com.gmail.heagoo.common.j
    public final Unit a(com.gmail.heagoo.common.k kVar) {
        this.s = kVar
        this.r.sendEmptyMessage(1)
    }

    @Override // com.gmail.heagoo.common.j
    public final Unit a(String str) {
        this.t = str
        this.r.sendEmptyMessage(3)
    }

    public final Unit a(Boolean z) {
        Int i = -1
        this.f752b.setVisibility(4)
        this.c.setVisibility(0)
        if (this.A != null && this.C) {
            this.A.a()
        }
        Button button = (Button) findViewById(R.id.btn_install)
        if (!z) {
            setResult(-1)
            findViewById(R.id.succeeded_view).setVisibility(8)
            findViewById(R.id.failed_view).setVisibility(0)
            this.f.setDivider(null)
            this.f.setAdapter((ListAdapter) g(this, this.t))
            this.g.setImageResource(R.drawable.ic_close)
            if (this.t.contains("warning:")) {
                this.h.setVisibility(0)
            } else {
                this.h.setVisibility(8)
            }
            this.B.a(this.t)
            if (this.B.a()) {
                switch (this.B.b()) {
                    case 0:
                        i = R.string.fix_invalid_name_tip
                        break
                    case 1:
                        i = R.string.fix_invalid_token_tip
                        break
                    case 2:
                        i = R.string.fix_invalid_attr_tip
                        break
                    case 3:
                        i = R.string.fix_invalid_symbol_tip
                        break
                    case 4:
                        i = R.string.fix_error_equivalent
                        break
                }
                this.k.setText(i)
                this.j.setVisibility(0)
            }
            button.setVisibility(8)
            this.i.setVisibility(8)
            this.o.setVisibility(0)
            return
        }
        setResult(10005)
        button.setOnClickListener(this)
        findViewById(R.id.succeeded_view).setVisibility(0)
        findViewById(R.id.failed_view).setVisibility(8)
        this.g.setImageResource(R.drawable.ic_select)
        String str = getResources().getString(R.string.carlos) + "" + String.format(getResources().getString(R.string.apk_savedas_1), this.v) + "\n\n"
        if (this.z) {
            this.w = d()
            if (b(this.w)) {
                String str2 = str + getResources().getString(R.string.remove_tip)
                SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder(str2)
                AbsoluteSizeSpan absoluteSizeSpan = AbsoluteSizeSpan(12, true)
                Int length = str.length()
                Int length2 = str2.length()
                spannableStringBuilder.setSpan(absoluteSizeSpan, length, length2, 33)
                spannableStringBuilder.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, a.a.b.a.k.mdTextSmall(a.a.b.a.k.a(this)))), length, length2, 33)
                this.e.setText(spannableStringBuilder)
                this.i.setVisibility(0)
            } else {
                this.e.setText(str)
                this.i.setVisibility(8)
            }
            this.o.setVisibility(8)
            button.setVisibility(0)
        } else {
            String str3 = str + getResources().getString(R.string.not_signed_tip)
            SpannableStringBuilder spannableStringBuilder2 = SpannableStringBuilder(str3)
            AbsoluteSizeSpan absoluteSizeSpan2 = AbsoluteSizeSpan(12, true)
            Int length3 = str.length()
            Int length4 = str3.length()
            spannableStringBuilder2.setSpan(absoluteSizeSpan2, length3, length4, 18)
            spannableStringBuilder2.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, a.a.b.a.k.mdProgress(a.a.b.a.k.a(this)))), length3, length4, 18)
            this.e.setText(spannableStringBuilder2)
            this.o.setVisibility(8)
            this.i.setVisibility(8)
            button.setVisibility(8)
        }
        if (this.y) {
            String property = System.getProperty("java.vm.version")
            if (property != null && property.charAt(0) > '1') {
                this.l = (TextView) findViewById(R.id.tv_patch_tip)
                findViewById(R.id.patch_dex_layout).setVisibility(0)
                this.m = (Button) findViewById(R.id.btn_patch)
                this.m.setOnClickListener(this)
                return
            }
        }
        findViewById(R.id.patch_dex_layout).setVisibility(8)
    }

    public final Unit b() {
        this.d.setText(String.format(getResources().getString(R.string.step) + " %d/%d: %s", Integer.valueOf(this.s.f1457a), Integer.valueOf(this.s.f1458b), this.s.c))
    }

    public final Unit c() throws XPathExpressionException, IOException {
        if (this.A != null) {
            Map mapC = this.B.c()
            if (this.A != null && !mapC.isEmpty()) {
                this.A.f1218a.o = new com.gmail.heagoo.apkeditor.util.c(this.x, mapC)
            }
            this.d.setText("")
            this.f752b.setVisibility(0)
            this.c.setVisibility(8)
            k kVar = this.A
            kVar.f1218a.c()
            kVar.f1218a.b()
            kVar.f1218a.d()
        }
    }

    @Override // android.app.Activity
    fun onBackPressed() {
        if (this.A == null || !this.A.b()) {
            finish()
            return
        }
        if (com.gmail.heagoo.a.c.a.a((Context) this, "donot_show_compose_tip", false)) {
            finish()
            return
        }
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.dlg_tip, (ViewGroup) null)
        ((TextView) viewInflate.findViewById(R.id.tv_message)).setText(R.string.build_still_running_tip)
        AlertDialog.Builder positiveButton = new AlertDialog.Builder(this).setTitle(R.string.tip).setPositiveButton(android.R.string.ok, e(this, (CheckBox) viewInflate.findViewById(R.id.cb_show_once)))
        positiveButton.setView(viewInflate)
        positiveButton.show()
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) throws Throwable {
        Int iLastIndexOf
        File file
        Int id = view.getId()
        if (id == R.id.btn_close) {
            finish()
            return
        }
        if (id == R.id.btn_install) {
            com.gmail.heagoo.a.c.a.a(this, this.v)
            return
        }
        if (id == R.id.btn_remove) {
            if (this.w != null) {
                startActivity(Intent("android.intent.action.DELETE", Uri.parse("package:" + this.w)))
                return
            }
            return
        }
        if (id == R.id.btn_copy_errmsg) {
            com.gmail.heagoo.a.c.a.c(this, this.t)
            Toast.makeText(this, R.string.errmsg_copied, 0).show()
            return
        }
        if (id == R.id.btn_fix) {
            if (this.B != null) {
                this.j.setVisibility(8)
                this.B.a(this)
                return
            }
            return
        }
        if (id == R.id.btn_patch) {
            if (!this.q) {
                ey(this, d(this), -1).show()
                return
            }
            try {
                Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(this.w)
                if (launchIntentForPackage != null) {
                    startActivity(launchIntentForPackage)
                    return
                }
                return
            } catch (ActivityNotFoundException e) {
                return
            }
        }
        if (id == R.id.btn_hide_warning) {
            StringBuilder sb = StringBuilder()
            for (String str : this.t.split("\n")) {
                if (!str.startsWith("warning:")) {
                    sb.append(str)
                    sb.append("\n")
                }
            }
            g gVar = (g) this.f.getAdapter()
            gVar.a(sb.toString())
            gVar.notifyDataSetChanged()
            this.h.setVisibility(8)
            return
        }
        if (id == R.id.btn_bg) {
            finish()
            return
        }
        if (id != R.id.result || this.v == null) {
            return
        }
        String str2 = this.v
        if (!File(str2).exists() || (iLastIndexOf = str2.lastIndexOf("/")) == -1 || (file = File(str2.substring(0, iLastIndexOf + 1))) == null || !file.exists()) {
            return
        }
        Intent intent = Intent("android.intent.action.GET_CONTENT")
        intent.addCategory("android.intent.category.OPENABLE")
        intent.addFlags(268435456)
        intent.setDataAndType(Uri.fromFile(file), "text/csv")
        try {
            startActivity(intent)
        } catch (ActivityNotFoundException e2) {
            e2.printStackTrace()
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) getSystemService("notification")
            NotificationChannel notificationChannel = NotificationChannel("default", "default", 3)
            notificationChannel.setLightColor(0)
            notificationChannel.enableVibration(false)
            notificationChannel.setVibrationPattern(new Array<Long>{0})
            notificationChannel.setLockscreenVisibility(1)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        this.E = getIntent().getAction()
        getWindow().setFlags(128, 128)
        setContentView(R.layout.activity_apkcompose)
        this.r = f(this)
        this.f752b = (LinearLayout) findViewById(R.id.layout_apk_composing)
        this.c = (LinearLayout) findViewById(R.id.layout_apk_composed)
        this.d = (TextView) findViewById(R.id.progress_tip)
        this.e = (TextView) findViewById(R.id.result)
        this.f = (ListView) findViewById(R.id.failed_view)
        this.g = (ImageView) findViewById(R.id.result_image)
        this.j = (LinearLayout) findViewById(R.id.fix_layout)
        this.k = (TextView) findViewById(R.id.tv_fix_tip)
        this.e.setOnClickListener(this)
        this.c.setVisibility(4)
        ((Button) findViewById(R.id.btn_close)).setOnClickListener(this)
        this.i = (Button) findViewById(R.id.btn_remove)
        this.i.setOnClickListener(this)
        this.n = (Button) findViewById(R.id.btn_fix)
        this.n.setOnClickListener(this)
        this.o = (Button) findViewById(R.id.btn_copy_errmsg)
        this.o.setOnClickListener(this)
        this.h = (Button) findViewById(R.id.btn_hide_warning)
        this.h.setOnClickListener(this)
        this.p = (Button) findViewById(R.id.btn_bg)
        this.p.setOnClickListener(this)
        bindService(Intent(this, (Class<?>) ApkComposeService.class), this.D, 1)
        this.u = System.currentTimeMillis()
    }

    @Override // android.app.Activity
    protected fun onDestroy() {
        if (this.D != null) {
            unbindService(this.D)
            this.D = null
        }
        super.onDestroy()
    }

    @Override // android.app.Activity
    fun onNewIntent(Intent intent) {
        this.E = intent.getAction()
        if (this.E == null || !"com.gmail.heagoo.action.apkcompose".equals(this.E) || this.A == null || this.A.b()) {
            return
        }
        this.A.a()
    }

    @Override // android.app.Activity
    fun onPause() {
        this.C = false
        super.onPause()
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        this.C = true
    }
}
