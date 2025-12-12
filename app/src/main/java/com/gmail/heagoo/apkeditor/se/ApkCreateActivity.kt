package com.gmail.heagoo.apkeditor.se

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.zip.ZipFile

class ApkCreateActivity extends Activity implements View.OnClickListener, com.gmail.heagoo.apkeditor.a.f {

    /* renamed from: a, reason: collision with root package name */
    b f1247a

    /* renamed from: b, reason: collision with root package name */
    private String f1248b
    private String c
    private Map d
    private Map e
    private Map f
    private String g
    private String h
    private String i
    private LinearLayout j
    private LinearLayout k
    private ImageView l
    private TextView m
    private Button n
    private Button o
    private TextView p
    private a q
    private String r
    private String s
    private Boolean t = false
    private Boolean u
    private com.gmail.heagoo.common.b v
    private String w
    private com.a.b.a.e.j x
    private Long y
    private ArrayList z

    private fun a() throws PackageManager.NameNotFoundException {
        try {
            new com.gmail.heagoo.common.a()
            com.gmail.heagoo.common.b bVarA = com.gmail.heagoo.common.a.a(this, this.r)
            if (bVarA == null) {
                return false
            }
            getPackageManager().getPackageInfo(bVarA.f1448b, 0)
            return true
        } catch (Exception e) {
            return false
        }
    }

    private fun b() {
        if (!this.u) {
            this.l.setImageResource(R.drawable.ic_close)
            this.m.setText(this.s)
            this.n.setVisibility(8)
            this.o.setVisibility(8)
            return
        }
        String str = getResources().getString(R.string.carlos) + "" + String.format(getResources().getString(R.string.apk_savedas_1), this.r) + "\n\n"
        if (a()) {
            String str2 = str + getResources().getString(R.string.remove_tip)
            SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder(str2)
            AbsoluteSizeSpan absoluteSizeSpan = AbsoluteSizeSpan(12, true)
            Int length = str.length()
            Int length2 = str2.length()
            spannableStringBuilder.setSpan(absoluteSizeSpan, length, length2, 33)
            spannableStringBuilder.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, a.a.b.a.k.mdTextSmall(a.a.b.a.k.a(this)))), length, length2, 33)
            this.m.setText(spannableStringBuilder)
            this.n.setVisibility(0)
        } else {
            this.m.setText(str)
            this.n.setVisibility(8)
        }
        this.l.setImageResource(R.drawable.ic_select)
    }

    private fun b(Boolean z) {
        this.l = (ImageView) findViewById(R.id.result_image)
        this.m = (TextView) findViewById(R.id.result)
        this.p = (TextView) findViewById(R.id.tv_detail)
        this.n = (Button) findViewById(R.id.button_uninstall)
        this.o = (Button) findViewById(R.id.button_reinstall)
        Button button = (Button) findViewById(R.id.button_close)
        this.n.setOnClickListener(this)
        this.o.setOnClickListener(this)
        button.setOnClickListener(this)
        this.j = (LinearLayout) findViewById(R.id.layout_apk_generating)
        this.k = (LinearLayout) findViewById(R.id.layout_apk_reinstall)
        if (z) {
            this.j.setVisibility(4)
            this.k.setVisibility(0)
        } else {
            this.j.setVisibility(0)
            this.k.setVisibility(4)
        }
        if (this.t) {
            b()
        }
    }

    private fun c() {
        Long j = PreferenceManager.getDefaultSharedPreferences(this).getLong("LastPopAdTime", 0L)
        if (j < System.currentTimeMillis()) {
            return j
        }
        return 0L
    }

    static /* synthetic */ Unit g(ApkCreateActivity apkCreateActivity) throws IOException {
        if (apkCreateActivity.d == null || apkCreateActivity.d.isEmpty()) {
            return
        }
        ZipFile zipFile = ZipFile(apkCreateActivity.f1248b)
        HashMap map = HashMap()
        for (Map.Entry entry : apkCreateActivity.d.entrySet()) {
            String str = (String) entry.getKey()
            String str2 = (String) entry.getValue()
            List arrayList = (List) map.get(str2)
            if (arrayList == null) {
                arrayList = ArrayList()
                map.put(str2, arrayList)
            }
            arrayList.add(str)
        }
        for (String str3 : map.keySet()) {
            List<String> list = (List) map.get(str3)
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str3)
            for (String str4 : list) {
                InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(str4))
                BitmapFactory.Options options = new BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeStream(inputStream, null, options)
                Int i = options.outWidth
                Int i2 = options.outHeight
                Int width = bitmapDecodeFile.getWidth()
                Int height = bitmapDecodeFile.getHeight()
                Matrix matrix = Matrix()
                matrix.postScale(i / width, i2 / height)
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeFile, 0, 0, width, height, matrix, true)
                String str5 = apkCreateActivity.w + str4.replaceAll("/", "_")
                FileOutputStream fileOutputStream = FileOutputStream(str5)
                if (str5.endsWith(".png")) {
                    bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream)
                } else {
                    bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
                }
                fileOutputStream.close()
                apkCreateActivity.e.put(str4, str5)
            }
        }
        zipFile.close()
    }

    @Override // com.gmail.heagoo.apkeditor.a.f
    public final Unit a(String str) {
        this.f1247a.a(str)
    }

    public final Unit a(Boolean z) {
        this.u = z
        if (z) {
            this.r = this.q.f1251b
            if (this.x != null && this.x.b()) {
                Toast.makeText(this, R.string.show_ad_tip, 0).show()
            }
        } else {
            this.s = this.q.a()
            System.currentTimeMillis()
            c()
        }
        b()
        this.j.setVisibility(4)
        this.k.setVisibility(0)
        this.t = true
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) throws Throwable {
        Int id = view.getId()
        if (id == R.id.button_uninstall) {
            if (this.v != null) {
                startActivity(Intent("android.intent.action.DELETE", Uri.parse("package:" + this.v.f1448b)))
            }
        } else if (id == R.id.button_reinstall) {
            com.gmail.heagoo.a.c.a.a(this, this.r)
        } else if (id == R.id.button_close) {
            finish()
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) throws Resources.NotFoundException {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        getWindow().setFlags(128, 128)
        setContentView(R.layout.activity_simpleedit_making)
        if (bundle != null) {
            this.f1248b = bundle.getString("apkPath")
            this.c = bundle.getString("packageName")
            this.t = bundle.getBoolean("modifyFinished")
            this.u = bundle.getBoolean("succeed")
            this.r = bundle.getString("outApkPath")
            this.s = bundle.getString("errorMessage")
        } else {
            Intent intent = getIntent()
            this.f1248b = com.gmail.heagoo.a.c.a.a(intent, "apkPath")
            this.c = com.gmail.heagoo.a.c.a.a(intent, "packageName")
            this.d = com.gmail.heagoo.a.c.a.d(intent, "imageReplaces")
            Map mapD = com.gmail.heagoo.a.c.a.d(intent, "otherReplaces")
            this.z = (ArrayList) intent.getExtras().getSerializable("interfaces")
            this.e = HashMap()
            if (mapD != null) {
                this.e.putAll(mapD)
            }
            try {
                this.w = com.gmail.heagoo.a.c.a.d(this, "tmp")
            } catch (Exception e) {
            }
            this.h = com.gmail.heagoo.a.c.a.a(intent, "oldAppNameInArsc")
            this.i = com.gmail.heagoo.a.c.a.a(intent, "newAppNameInArsc")
            this.g = com.gmail.heagoo.a.c.a.a(intent, "newPackageNameInArsc")
            this.f = com.gmail.heagoo.a.c.a.d(intent, "classRenames")
        }
        try {
            new com.gmail.heagoo.common.a()
            this.v = com.gmail.heagoo.common.a.a(this, this.f1248b)
        } catch (Exception e2) {
            Toast.makeText(this, getResources().getString(R.string.cannot_parse_apk) + ": " + e2.getMessage(), 1).show()
        }
        this.f1247a = b(this)
        if (this.t) {
            b(true)
            return
        }
        this.q = a(this)
        this.q.start()
        b(false)
    }

    @Override // android.app.Activity
    protected fun onDestroy() {
        super.onDestroy()
    }

    @Override // android.app.Activity
    fun onSaveInstanceState(Bundle bundle) {
        bundle.putString("apkPath", this.f1248b)
        bundle.putString("packageName", this.c)
        bundle.putString("outApkPath", this.r)
        bundle.putString("errorMessage", this.s)
        bundle.putBoolean("modifyFinished", this.t)
        bundle.putBoolean("succeed", this.u)
        super.onSaveInstanceState(bundle)
    }
}
