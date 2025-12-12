package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.prj.ProjectListActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipFile

class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener, DialogInterface.OnClickListener, fa {

    /* renamed from: a, reason: collision with root package name */
    private static Int f769a

    /* renamed from: b, reason: collision with root package name */
    private static Int f770b
    SharedPreferences d
    View e
    View f
    View g
    View h
    View j
    View k
    View l
    private Int i = 1
    private MainActivity c = this

    static {
        System.loadLibrary("syscheck")
    }

    private fun AB() {
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        TextView textView = (TextView) viewInflate.findViewById(android.R.id.title)
        textView.setText(R.string.app_name)
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.gmail.heagoo.apkeditor.la
            @Override // android.view.View.OnClickListener
            public final Unit onClick(View view) {
                MainActivity.lc(view)
            }
        })
        textView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.gmail.heagoo.apkeditor.lb
            @Override // android.view.View.OnLongClickListener
            public final Boolean onLongClick(View view) {
                return this.f1222a.llc(view)
            }
        })
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_theme)
    }

    fun a(Context context) {
        return true
    }

    private fun c() throws IOException {
        try {
            File file = File(getFilesDir(), "work.xml")
            if (!file.exists()) {
                file.createNewFile()
                file.setWritable(true)
            }
            File file2 = File(getFilesDir(), "work.db")
            if (!file2.exists()) {
                file2.createNewFile()
                file2.setWritable(true)
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
        try {
            File file3 = File(getFilesDir(), "mycp")
            if (!file3.exists() && Build.VERSION.SDK_INT >= 20) {
                InputStream inputStreamOpen = getAssets().open("mycp")
                FileOutputStream fileOutputStream = FileOutputStream(file3)
                a.a(inputStreamOpen, fileOutputStream)
                inputStreamOpen.close()
                fileOutputStream.close()
                file3.setExecutable(true)
            }
            File file4 = File(getFilesDir().getAbsolutePath() + "/bin", "aaptz")
            File parentFile = file4.getParentFile()
            if (!parentFile.exists()) {
                parentFile.mkdirs()
            }
            if (file4.exists()) {
                return
            }
            InputStream inputStreamOpen2 = getAssets().open("aaptz")
            FileOutputStream fileOutputStream2 = FileOutputStream(file4)
            a.a(inputStreamOpen2, fileOutputStream2)
            inputStreamOpen2.close()
            fileOutputStream2.close()
            file4.setExecutable(true)
        } catch (Exception e2) {
            e2.printStackTrace()
        }
    }

    private fun clean() {
        cleanStorage()
        String absolutePath = getFilesDir().getAbsolutePath()
        new com.gmail.heagoo.common.c().a("rm -rf " + absolutePath + "/decoded\nrm -rf " + absolutePath + "/temp", (Array<String>) null, (Integer) 8000)
        Toast.makeText(this, R.string.temp_file_cleaned, 0).show()
    }

    private fun cleanStorage() {
        Boolean z
        File file = File(Environment.getExternalStorageDirectory().getPath() + "/ApkEditor")
        if (file.exists() && file.isDirectory()) {
            Array<String> strArr = {"backups", ".projects"}
            Array<File> fileArrListFiles = file.listFiles()
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.isDirectory()) {
                        String name = file2.getName()
                        Int i = 0
                        while (true) {
                            if (i >= 2) {
                                z = false
                                break
                            } else {
                                if (name.equals(strArr[i])) {
                                    z = true
                                    break
                                }
                                i++
                            }
                        }
                        if (!z) {
                            cleanWork(file2)
                            file2.delete()
                        }
                    } else {
                        file2.delete()
                    }
                }
            }
        }
    }

    private fun cleanWork(File file) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    cleanWork(file2)
                    file2.delete()
                } else {
                    file2.delete()
                }
            }
        }
    }

    private fun fd() {
        SharedPreferences.Editor editorEdit = getSharedPreferences("fd", 0).edit()
        editorEdit.putBoolean("FD", false)
        editorEdit.apply()
        Float f = getResources().getDisplayMetrics().density
        Int i = (Int) ((6.0f * f) + 0.5f)
        Int i2 = (Int) ((f * 24.0f) + 0.5f)
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.action_changelogs)
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2)
        LinearLayout linearLayout = LinearLayout(this)
        linearLayout.setOrientation(1)
        linearLayout.setLayoutParams(layoutParams)
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2)
        ScrollView scrollView = ScrollView(this)
        scrollView.setVerticalScrollBarEnabled(false)
        layoutParams2.setMargins(0, i, 0, 0)
        scrollView.setLayoutParams(layoutParams2)
        linearLayout.addView(scrollView)
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2)
        TextView textView = TextView(this)
        textView.setText(R.string.changelogs)
        textView.setTextColor(ContextCompat.getColor(this, a.a.b.a.k.mdTextSmall(a.a.b.a.k.a(this))))
        textView.setTextSize(1, 14.0f)
        textView.setPadding(0, i, 0, i)
        layoutParams3.setMargins(i2, 0, i2, 0)
        textView.setLayoutParams(layoutParams3)
        scrollView.addView(textView)
        builder.setView(linearLayout)
        builder.setPositiveButton(R.string.got_it, (DialogInterface.OnClickListener) null)
        builder.setNeutralButton("by Zeratul", (DialogInterface.OnClickListener) null)
        builder.setCancelable(false)
        builder.show().getButton(-3).setEnabled(false)
    }

    public static native Int isX86()

    public static native Unit it(Object obj, String str, String str2, String str3)

    fun lang(MainActivity mainActivity) {
        com.gmail.heagoo.common.e.l(mainActivity)
    }

    static /* synthetic */ Unit lc(View view) {
        f769a++
    }

    public static native Unit md(String str, String str2, String str3, Int i, String str4, Int i2, String str5, Int i3)

    public static native Unit mg(String str, String str2, String str3, Int i, String str4, Int i2)

    private fun resetTools() {
        SharedPreferences.Editor editorEdit = getSharedPreferences("info", 0).edit()
        editorEdit.putBoolean("initialized", false)
        editorEdit.commit()
        Toast.makeText(this, R.string.toast_reset_tools, 1).show()
    }

    private fun showDialogClean() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_title)
        builder.setMessage(R.string.confirm_message_clean)
        builder.setNegativeButton(R.string.no, this)
        builder.setPositiveButton(R.string.yes, this)
        builder.create().show()
    }

    public static native Int vc(Object obj, Int i)

    private fun visibilityButtons() {
        if (Boolean(this.d.getBoolean("hide_prj", false)).equals(Boolean(true))) {
            this.e.setVisibility(8)
        } else {
            this.e.setVisibility(0)
        }
        if (Boolean(this.d.getBoolean("show_odex", false)).equals(Boolean(true))) {
            this.f.setVisibility(0)
        } else {
            this.f.setVisibility(8)
        }
        if (Boolean(this.d.getBoolean("show_info", false)).equals(Boolean(true))) {
            this.g.setVisibility(0)
        } else {
            this.g.setVisibility(8)
        }
        if (Boolean(this.d.getBoolean("hide_exit", false)).equals(Boolean(true))) {
            this.h.setVisibility(8)
        } else {
            this.h.setVisibility(0)
        }
        if (Boolean(this.d.getBoolean("show_verify", false)).equals(Boolean(true))) {
            this.j.setVisibility(0)
        } else {
            this.j.setVisibility(8)
        }
        if (Boolean(this.d.getBoolean("show_sign", false)).equals(Boolean(true))) {
            this.k.setVisibility(0)
        } else {
            this.k.setVisibility(8)
        }
        if (Boolean(this.d.getBoolean("show_db", false)).equals(Boolean(true))) {
            this.l.setVisibility(0)
        } else {
            this.l.setVisibility(8)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        try {
            stopService(Intent(this, (Class<?>) ApkComposeService.class))
            com.gmail.heagoo.httpserver.e.a()
            com.gmail.heagoo.httpserver.e.b(this)
            com.gmail.heagoo.common.h.a(File(getFilesDir().getAbsolutePath() + "/decoded"))
        } catch (Throwable th) {
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        Process.killProcess(Process.myPid())
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit checkingInstruments() {
        /*
            r9 = this
            java.lang.String r0 = "info"
            r1 = 0
            android.content.SharedPreferences r0 = r9.getSharedPreferences(r0, r1)
            java.lang.String r2 = "initialized"
            Boolean r0 = r0.getBoolean(r2, r1)
            if (r0 == 0) goto L6a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.File r2 = r9.getFilesDir()
            java.lang.String r2 = r2.getAbsolutePath()
            r0.append(r2)
            java.lang.String r2 = "/bin"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "aapt"
            r2.<init>(r0, r3)
            Long r3 = r9.fileSize(r2)
            r5 = 819960(0xc82f8, Double:4.05114E-318)
            r7 = 1
            Int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 == 0) goto L3d
        L3b:
            r2 = 1
            goto L54
        L3d:
            r3 = 0
            Long r3 = org.apache.commons.io.FileUtils.checksumCRC32(r2)     // Catch: java.io.IOException -> L44
            goto L48
        L44:
            r2 = move-exception
            r2.printStackTrace()
        L48:
            java.lang.String r2 = "assets/aapt"
            Long r5 = r9.getCrc(r2)
            Int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 == 0) goto L53
            goto L3b
        L53:
            r2 = 0
        L54:
            java.io.File r3 = new java.io.File
            java.lang.String r4 = "android.jar"
            r3.<init>(r0, r4)
            Long r3 = r9.fileSize(r3)
            r5 = 2269471(0x22a11f, Double:1.1212677E-317)
            Int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 == 0) goto L67
            goto L68
        L67:
            r7 = r2
        L68:
            if (r7 == 0) goto L6a
        L6a:
            return
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.MainActivity.checkingInstruments():Unit")
    }

    public final Long fileSize(File file) {
        if (file.exists()) {
            return file.length()
        }
        return 0L
    }

    public final Long getCrc(String str) throws IOException {
        Long crc = 0
        try {
            ZipFile zipFile = ZipFile(getPackageCodePath())
            crc = zipFile.getEntry(str).getCrc()
            zipFile.close()
            return crc
        } catch (IOException e) {
            e.printStackTrace()
            return crc
        }
    }

    fun llc(View view) {
        if (f769a >= 5) {
            Toast.makeText(this, "by Zeratul", 0).show()
        }
        return f769a >= 5
    }

    @Override // android.app.Activity
    fun onBackPressed() {
        finish()
    }

    @Override // android.content.DialogInterface.OnClickListener
    fun onClick(DialogInterface dialogInterface, Int i) {
        switch (i) {
            case -2:
                dialogInterface.dismiss()
                break
            case -1:
                clean()
                break
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apk /* 2131558984 */:
                startActivity(Intent(this, (Class<?>) FileListActivity.class))
                break
            case R.id.btn_app /* 2131558985 */:
                startActivity(Intent(this, (Class<?>) UserAppActivity.class))
                break
            case R.id.btn_prj /* 2131558986 */:
                startActivity(Intent(this, (Class<?>) ProjectListActivity.class))
                break
            case R.id.btn_odex /* 2131558987 */:
                startActivity(Intent(this, (Class<?>) OdexPatchActivity.class))
                break
            case R.id.btn_info /* 2131558988 */:
                startActivity(Intent(this, (Class<?>) InfoActivity.class))
                break
            case R.id.btn_settings /* 2131558989 */:
                startActivity(Intent(this, (Class<?>) SettingActivity.class))
                break
            case R.id.btn_exit /* 2131558990 */:
                ey(this.c, this.c, -1).show()
                break
            case R.id.btn_sign /* 2131559001 */:
                startActivity(Intent(this, (Class<?>) SelectFileActivity.class))
                break
            case R.id.btn_verify /* 2131559003 */:
                startActivity(Intent(this, (Class<?>) VerifyActivity.class))
                break
            case R.id.btn_db /* 2131559020 */:
                startActivity(Intent(this, (Class<?>) SelectDbActivity.class))
                break
        }
    }

    @Override // android.app.Activity
    protected fun onCreate(Bundle bundle) throws IOException {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        lang(this)
        App.setContext(this)
        this.d = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(a.a.b.a.n.b(a.a.b.a.n.d(this)))
        if (getSharedPreferences("fd", 0).getBoolean("FD", true)) {
            fd()
        }
        AB()
        findViewById(R.id.btn_apk).setOnClickListener(this)
        findViewById(R.id.btn_app).setOnClickListener(this)
        this.e = findViewById(R.id.btn_prj)
        this.e.setOnClickListener(this)
        this.f = findViewById(R.id.btn_odex)
        this.f.setOnClickListener(this)
        this.g = findViewById(R.id.btn_info)
        this.g.setOnClickListener(this)
        findViewById(R.id.btn_settings).setOnClickListener(this)
        this.h = findViewById(R.id.btn_exit)
        this.h.setOnClickListener(this)
        this.j = findViewById(R.id.btn_verify)
        this.j.setOnClickListener(this)
        this.k = findViewById(R.id.btn_sign)
        this.k.setOnClickListener(this)
        this.l = findViewById(R.id.btn_db)
        this.l.setOnClickListener(this)
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new Array<String>{"android.permission.WRITE_EXTERNAL_STORAGE"}, this.i)
        } else {
            c()
            checkingInstruments()
        }
    }

    @Override // android.app.Activity
    fun onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    @Override // android.app.Activity
    fun onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                new a.a.b.a.k().f(this, R.string.theme_title, ja(this))
                break
            case R.id.action_prj /* 2131558991 */:
                startActivity(Intent(this, (Class<?>) ProjectListActivity.class))
                break
            case R.id.action_odex /* 2131558992 */:
                startActivity(Intent(this, (Class<?>) OdexPatchActivity.class))
                break
            case R.id.action_edit /* 2131558993 */:
                startActivity(Intent(this, (Class<?>) SettingEditorActivity.class))
                break
            case R.id.action_info /* 2131558994 */:
                startActivity(Intent(this, (Class<?>) InfoActivity.class))
                break
            case R.id.action_mod /* 2131558995 */:
                startActivity(Intent(this, (Class<?>) AboutModActivity.class))
                break
            case R.id.action_exit /* 2131558996 */:
                ey(this.c, this.c, -1).show()
                break
            case R.id.action_db /* 2131558997 */:
                startActivity(Intent(this, (Class<?>) SelectDbActivity.class))
                break
            case R.id.action_tools /* 2131558998 */:
                resetTools()
                break
            case R.id.action_changelogs /* 2131558999 */:
                fd()
                break
            case R.id.action_sign /* 2131559000 */:
                startActivity(Intent(this, (Class<?>) SelectFileActivity.class))
                break
            case R.id.action_verify /* 2131559002 */:
                startActivity(Intent(this, (Class<?>) VerifyActivity.class))
                break
            case R.id.action_clean /* 2131559021 */:
                showDialogClean()
                break
        }
        return true
    }

    @Override // android.app.Activity
    fun onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemFindItem = menu.findItem(R.id.action_prj)
        if (Boolean(this.d.getBoolean("hide_prj", false)).equals(Boolean(true))) {
            menuItemFindItem.setVisible(true)
        } else {
            menuItemFindItem.setVisible(false)
        }
        MenuItem menuItemFindItem2 = menu.findItem(R.id.action_odex)
        if (Boolean(this.d.getBoolean("show_odex", false)).equals(Boolean(true))) {
            menuItemFindItem2.setVisible(false)
        } else {
            menuItemFindItem2.setVisible(true)
        }
        menu.findItem(R.id.action_edit).setVisible(true)
        MenuItem menuItemFindItem3 = menu.findItem(R.id.action_info)
        if (Boolean(this.d.getBoolean("show_info", false)).equals(Boolean(true))) {
            menuItemFindItem3.setVisible(false)
        } else {
            menuItemFindItem3.setVisible(true)
        }
        MenuItem menuItemFindItem4 = menu.findItem(R.id.action_mod)
        if (Boolean(this.d.getBoolean("hide_mod", false)).equals(Boolean(true))) {
            menuItemFindItem4.setVisible(false)
        } else {
            menuItemFindItem4.setVisible(true)
        }
        MenuItem menuItemFindItem5 = menu.findItem(R.id.action_exit)
        if (Boolean(this.d.getBoolean("hide_exit", false)).equals(Boolean(true))) {
            menuItemFindItem5.setVisible(true)
        } else {
            menuItemFindItem5.setVisible(false)
        }
        MenuItem menuItemFindItem6 = menu.findItem(R.id.action_verify)
        if (Boolean(this.d.getBoolean("show_verify", false)).equals(Boolean(true))) {
            menuItemFindItem6.setVisible(false)
        } else {
            menuItemFindItem6.setVisible(true)
        }
        MenuItem menuItemFindItem7 = menu.findItem(R.id.action_sign)
        if (Boolean(this.d.getBoolean("show_sign", false)).equals(Boolean(true))) {
            menuItemFindItem7.setVisible(false)
        } else {
            menuItemFindItem7.setVisible(true)
        }
        MenuItem menuItemFindItem8 = menu.findItem(R.id.action_changelogs)
        if (Boolean(this.d.getBoolean("hide_changelogs", false)).equals(Boolean(true))) {
            menuItemFindItem8.setVisible(false)
        } else {
            menuItemFindItem8.setVisible(true)
        }
        MenuItem menuItemFindItem9 = menu.findItem(R.id.action_tools)
        if (Boolean(this.d.getBoolean("hide_tools", false)).equals(Boolean(true))) {
            menuItemFindItem9.setVisible(false)
        } else {
            menuItemFindItem9.setVisible(true)
        }
        MenuItem menuItemFindItem10 = menu.findItem(R.id.action_db)
        if (Boolean(this.d.getBoolean("show_db", false)).equals(Boolean(true))) {
            menuItemFindItem10.setVisible(false)
        } else {
            menuItemFindItem10.setVisible(true)
        }
        return true
    }

    @Override // android.app.Activity
    fun onRequestPermissionsResult(Int i, Array<String> strArr, Array<Int> iArr) throws IOException {
        super.onRequestPermissionsResult(i, strArr, iArr)
        c()
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        if (a.a.b.a.k.md) {
            a.a.b.a.k.md = false
            recreate()
        }
        visibilityButtons()
    }
}
