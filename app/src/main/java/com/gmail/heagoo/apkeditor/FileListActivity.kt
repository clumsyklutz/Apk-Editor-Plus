package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.LruCache
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.se.SimpleEditActivity
import com.gmail.heagoo.b.a
import java.io.File
import java.util.List

class FileListActivity extends Activity implements View.OnClickListener, cd, com.gmail.heagoo.b.n, com.gmail.heagoo.b.o {

    /* renamed from: a, reason: collision with root package name */
    private TextView f766a

    /* renamed from: b, reason: collision with root package name */
    private ListView f767b
    private com.gmail.heagoo.b.e d
    private ImageView e
    private Boolean g = false
    private cm i = null
    private LruCache k = LruCache(64)
    private Int l = 1
    private Handler m = cl(this)

    private fun a() {
        String path = Environment.getExternalStorageDirectory().getPath()
        String string = getSharedPreferences("config", 0).getString("apkDirectory", path)
        if (!File(string).exists()) {
            string = path
        }
        this.f766a = (TextView) findViewById(R.id.dirPath)
        this.f766a.setText(string)
        this.f767b = (ListView) findViewById(R.id.file_list)
        this.d = new com.gmail.heagoo.b.e(this, this.f767b, string, "/", this, this)
        this.e = (ImageView) findViewById(R.id.imageView_extsdcard)
        findViewById(R.id.menu_switch_card).setOnClickListener(ci(this))
        findViewById(R.id.search_button).setOnClickListener(this)
        findViewById(R.id.menu_home).setOnClickListener(ne(this))
        findViewById(R.id.files_list).setOnClickListener(nf(this))
        findViewById(R.id.btn_close).setOnClickListener(ng(this))
    }

    static /* synthetic */ Unit a(FileListActivity fileListActivity) {
        Boolean z
        if (fileListActivity.g) {
            fileListActivity.c()
            fileListActivity.e.setImageResource(R.drawable.ic_sdcard_ext)
            z = true
        } else if (fileListActivity.b()) {
            fileListActivity.e.setImageResource(R.drawable.ic_sdcard_int)
            z = true
        } else {
            z = false
        }
        if (z) {
            fileListActivity.g = !fileListActivity.g
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun b() {
        /*
            r9 = this
            r2 = 0
            r1 = 0
            Int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 19
            if (r0 < r3) goto L64
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r4 = r0.getPath()
            java.io.Array<File> r5 = r9.getExternalFilesDirs(r2)
            if (r5 == 0) goto L64
            Int r3 = r5.length
            r0 = r1
        L18:
            if (r0 >= r3) goto L90
            r6 = r5[r0]
            java.lang.String r6 = r6.getPath()
            Boolean r7 = r6.startsWith(r4)
            if (r7 == 0) goto L5e
            Int r0 = r6.length()
            Int r3 = r4.length()
            Int r0 = r0 - r3
        L2f:
            Int r6 = r5.length
            r3 = r1
        L31:
            if (r3 >= r6) goto L64
            r7 = r5[r3]
            java.lang.String r7 = r7.getPath()
            Boolean r8 = r7.startsWith(r4)
            if (r8 != 0) goto L61
            Int r2 = r7.length()
            Int r0 = r2 - r0
            java.lang.String r0 = r7.substring(r1, r0)
        L49:
            if (r0 == 0) goto L83
            java.lang.String r2 = ""
            Boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L83
            com.gmail.heagoo.b.e r2 = r9.d
            if (r2 == 0) goto L5d
            com.gmail.heagoo.b.e r1 = r9.d
            r1.a(r0)
            r1 = 1
        L5d:
            return r1
        L5e:
            Int r0 = r0 + 1
            goto L18
        L61:
            Int r3 = r3 + 1
            goto L31
        L64:
            java.util.List r0 = com.gmail.heagoo.common.a.b()
            java.util.Iterator r3 = r0.iterator()
        L6c:
            Boolean r0 = r3.hasNext()
            if (r0 == 0) goto L8e
            java.lang.Object r0 = r3.next()
            com.gmail.heagoo.common.v r0 = (com.gmail.heagoo.common.v) r0
            Boolean r4 = r0.f1475b
            if (r4 != 0) goto L6c
            Boolean r4 = r0.c
            if (r4 != 0) goto L6c
            java.lang.String r0 = r0.f1474a
            goto L49
        L83:
            r0 = 2131165256(0x7f070048, Float:1.7944724E38)
            android.widget.Toast r0 = android.widget.Toast.makeText(r9, r0, r1)
            r0.show()
            goto L5d
        L8e:
            r0 = r2
            goto L49
        L90:
            r0 = r1
            goto L2f
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.FileListActivity.b():Boolean")
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c() {
        String path = Environment.getExternalStorageDirectory().getPath()
        if (path == null || path.equals("") || this.d == null) {
            return
        }
        this.d.a(path)
    }

    static /* synthetic */ Boolean d(FileListActivity fileListActivity) {
        File filesDir = fileListActivity.getFilesDir()
        if (fileListActivity.d == null) {
            return false
        }
        fileListActivity.d.a(filesDir.getPath())
        return true
    }

    static /* synthetic */ Unit g(FileListActivity fileListActivity) {
        fileListActivity.m.removeMessages(0)
        fileListActivity.m.sendEmptyMessageDelayed(0, 300L)
    }

    @Override // com.gmail.heagoo.b.o
    public final Drawable a(String str, a aVar) {
        if (aVar == null || aVar.f1425b || !aVar.f1424a.endsWith(".apk")) {
            return null
        }
        String str2 = str + "/" + aVar.f1424a
        com.gmail.heagoo.common.b bVar = (com.gmail.heagoo.common.b) this.k.get(str2)
        if (bVar != null) {
            return bVar.c
        }
        this.i.a(str2)
        return getResources().getDrawable(R.drawable.apk_icon)
    }

    @Override // com.gmail.heagoo.apkeditor.cd
    public final Unit a(Int i, String str) {
        Intent intent = null
        switch (i) {
            case 0:
                UserAppActivity.a(this, str)
                return
            case 1:
                intent = Intent(this, (Class<?>) SimpleEditActivity.class)
                break
            case 2:
                intent = Intent(this, (Class<?>) CommonEditActivity.class)
                break
            case 4:
                intent = Intent(this, (Class<?>) AxmlEditActivity.class)
                break
        }
        if (intent != null) {
            com.gmail.heagoo.a.c.a.a(intent, "apkPath", str)
            startActivity(intent)
        }
    }

    @Override // com.gmail.heagoo.b.n
    public final Unit a(String str) {
        if (this.f766a != null) {
            this.f766a.setText(str)
        }
    }

    @Override // com.gmail.heagoo.b.o
    public final String b(String str, a aVar) {
        if (aVar.f1425b || !aVar.f1424a.endsWith(".apk")) {
            return null
        }
        com.gmail.heagoo.common.b bVar = (com.gmail.heagoo.common.b) this.k.get(str + "/" + aVar.f1424a)
        return bVar != null ? bVar.f1447a : ""
    }

    @Override // com.gmail.heagoo.b.n
    public final Boolean b(String str) {
        if (!str.endsWith(".apk")) {
            return false
        }
        String strSubstring = str.substring(0, str.lastIndexOf(47))
        SharedPreferences.Editor editorEdit = getSharedPreferences("config", 0).edit()
        editorEdit.putString("apkDirectory", strSubstring)
        editorEdit.commit()
        if (MainActivity.a(this)) {
            cc(this, this, str).show()
        } else {
            UserAppActivity.a(this, str)
        }
        return true
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        if (view.getId() != R.id.search_button || this.d == null) {
            return
        }
        String string = ((EditText) findViewById(R.id.keyword_edit)).getText().toString()
        String strA = this.d.a().a((List) null)
        Intent intent = Intent(this, (Class<?>) ApkSearchActivity.class)
        com.gmail.heagoo.a.c.a.a(intent, "Keyword", string)
        com.gmail.heagoo.a.c.a.a(intent, "Path", strA)
        startActivity(intent)
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_listfile)
        if (this.i == null) {
            this.i = cm(this)
            this.i.start()
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new Array<String>{"android.permission.WRITE_EXTERNAL_STORAGE"}, this.l)
        } else {
            a()
        }
    }

    @Override // android.app.Activity
    fun onDestroy() {
        this.i.a()
        super.onDestroy()
    }

    @Override // android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // android.app.Activity
    fun onRequestPermissionsResult(Int i, Array<String> strArr, Array<Int> iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr)
        if (iArr.length <= 0 || iArr[0] != 0) {
            return
        }
        a()
    }

    @Override // android.app.Activity
    fun onResume() {
        super.onResume()
    }
}
