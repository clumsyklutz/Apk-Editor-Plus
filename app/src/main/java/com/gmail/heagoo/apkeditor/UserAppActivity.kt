package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.se.SimpleEditActivity
import java.util.ArrayList
import java.util.List

class UserAppActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, cd {
    private ListView c
    private PopupWindow g
    private cw h
    private EditText j
    private View k
    private ProgressBar l

    /* renamed from: a, reason: collision with root package name */
    private Int f775a = 0

    /* renamed from: b, reason: collision with root package name */
    private Int f776b = 0
    private iw d = iw(this)
    private List e = ArrayList()
    private val f = ArrayList()

    private fun a(Int i) {
        bk bkVar
        synchronized (this.f) {
            try {
                bkVar = (bk) this.f.get(i)
            } catch (Throwable th) {
                bkVar = null
            }
        }
        return bkVar
    }

    private fun a() {
        is(this).start()
    }

    static /* synthetic */ Unit a(UserAppActivity userAppActivity, Int i, Int i2) {
        userAppActivity.f775a = i
        userAppActivity.f776b = i2
        userAppActivity.a()
    }

    fun a(Context context, String str) {
        String strI = SettingActivity.i(context)
        if ("2".equals(strI)) {
            ca(context, it(context, str), str).show()
            return false
        }
        b(context, str, strI)
        return true
    }

    @SuppressLint({"DefaultLocale"})
    private fun b() throws Resources.NotFoundException {
        String lowerCase = this.j.getText().toString().toLowerCase()
        ArrayList arrayList = ArrayList()
        for (bk bkVar : this.e) {
            if (bkVar.c.toLowerCase().contains(lowerCase)) {
                arrayList.add(bkVar)
            }
        }
        a(arrayList)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Context context, String str, String str2) {
        Intent intent = Intent(context, (Class<?>) ApkInfoExActivity.class)
        a.a(intent, "apkPath", str)
        a.b(intent, "isFullDecoding", "0".equals(str2))
        context.startActivity(intent)
    }

    private fun b(Context context, String str) {
        try {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str)
            if (launchIntentForPackage == null) {
                return false
            }
            launchIntentForPackage.addCategory("android.intent.category.LAUNCHER")
            context.startActivity(launchIntentForPackage)
            return true
        } catch (ActivityNotFoundException e) {
            return false
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cd
    public final Unit a(Int i, String str) {
        Intent intent = null
        switch (i) {
            case 0:
                if (a(this, str)) {
                    finish()
                    return
                }
                return
            case 1:
                intent = Intent(this, (Class<?>) SimpleEditActivity.class)
                break
            case 2:
                intent = Intent(this, (Class<?>) CommonEditActivity.class)
                break
            case 3:
                try {
                    a.a("com.gmail.heagoo.apkeditor.pro.appdm", "de", new Array<Class>{Context.class, String.class}, new Array<Object>{this, str})
                    break
                } catch (Exception e) {
                    break
                }
            case 4:
                intent = Intent(this, (Class<?>) AxmlEditActivity.class)
                break
        }
        if (intent != null) {
            a.a(intent, "apkPath", str)
            startActivity(intent)
            finish()
        }
    }

    public final Unit a(List list) throws Resources.NotFoundException {
        this.l.setVisibility(8)
        this.c.setVisibility(0)
        bl blVar = (bl) this.c.getAdapter()
        Array<String> stringArray = getResources().getStringArray(R.array.order_value)
        blVar.a(list, this.f776b < stringArray.length ? stringArray[this.f776b] : "")
        blVar.notifyDataSetChanged()
        synchronized (this.f) {
            this.f.clear()
            this.f.addAll(list)
        }
        this.j.setEnabled(true)
        this.k.setVisibility(0)
    }

    protected final Unit b(List list) {
        PackageManager packageManager = getPackageManager()
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0)
        if (this.f775a == 0) {
            for (ApplicationInfo applicationInfo : installedApplications) {
                if ((applicationInfo.flags & 1) == 0) {
                    list.add(bk.a(packageManager, applicationInfo))
                }
            }
            return
        }
        for (ApplicationInfo applicationInfo2 : installedApplications) {
            if ((applicationInfo2.flags & 1) != 0) {
                list.add(bk.a(packageManager, applicationInfo2))
            }
        }
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) throws Resources.NotFoundException {
        Int id = view.getId()
        if (id != R.id.menu_more) {
            if (id == R.id.btn_close) {
                finish()
                return
            } else {
                if (id == R.id.btn_search) {
                    b()
                    return
                }
                return
            }
        }
        if (this.g == null) {
            View viewInflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popup_list, (ViewGroup) null)
            ListView listView = (ListView) viewInflate.findViewById(R.id.lvGroup)
            ArrayList arrayList = ArrayList()
            Resources resources = getResources()
            arrayList.add(resources.getString(R.string.user_apps))
            arrayList.add(resources.getString(R.string.system_apps))
            Array<String> stringArray = resources.getStringArray(R.array.order_value)
            for (String str : stringArray) {
                arrayList.add(str)
            }
            this.h = cw(this, arrayList)
            listView.setAdapter((ListAdapter) this.h)
            this.g = PopupWindow(viewInflate, com.gmail.heagoo.common.f.a(this) / 2, com.gmail.heagoo.common.f.a(this, 224.0f))
            listView.setOnItemClickListener(iu(this))
        }
        ArrayList arrayList2 = ArrayList()
        arrayList2.add(Integer.valueOf(this.f775a))
        arrayList2.add(Integer.valueOf(this.f776b + 2))
        this.h.a(arrayList2)
        this.g.setFocusable(true)
        this.g.setOutsideTouchable(true)
        this.g.setBackgroundDrawable(BitmapDrawable())
        this.g.showAsDropDown(view, (((WindowManager) getSystemService("window")).getDefaultDisplay().getWidth() / 2) - (this.g.getWidth() / 2), 0)
    }

    @Override // android.app.Activity
    fun onContextItemSelected(MenuItem menuItem) {
        Int i = ((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position
        switch (menuItem.getItemId()) {
            case 2:
                try {
                    bk bkVarA = a(i)
                    if (bkVarA != null) {
                        String str = bkVarA.f896b
                        try {
                            Intent intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
                            intent.setData(Uri.parse("package:" + str))
                            startActivity(intent)
                        } catch (ActivityNotFoundException e) {
                            startActivity(Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS"))
                        }
                    }
                } catch (Exception e2) {
                }
                return true
            case 3:
                try {
                    bk bkVarA2 = a(i)
                    if (bkVarA2 != null) {
                        ey(this, iv(this, bkVarA2.c, getPackageManager().getApplicationInfo(bkVarA2.f896b, 0).publicSourceDir), -1).show()
                    }
                } catch (Exception e3) {
                }
                return true
            case 4:
                try {
                    bk bkVarA3 = a(i)
                    if (bkVarA3 != null && !b(this, bkVarA3.f896b)) {
                        Toast.makeText(this, String.format(getString(R.string.cannot_launch), bkVarA3.c), 0).show()
                    }
                } catch (Exception e4) {
                }
                return true
            default:
                return super.onOptionsItemSelected(menuItem)
        }
    }

    @Override // android.app.Activity
    fun onCreate(Bundle bundle) throws Resources.NotFoundException {
        setTheme(a.a.b.a.k.mdNoActionBar(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        setContentView(R.layout.activity_applist)
        String strA = SettingActivity.a(this)
        Array<String> stringArray = getResources().getStringArray(R.array.order_value)
        Int i = 0
        while (true) {
            if (i >= stringArray.length) {
                i = 0
                break
            } else if (strA.equals(stringArray[i])) {
                break
            } else {
                i++
            }
        }
        this.f776b = i
        this.l = (ProgressBar) findViewById(R.id.progress_bar)
        ((TextView) findViewById(R.id.apptype)).setText(R.string.select_apk_from_app)
        findViewById(R.id.menu_more).setOnClickListener(this)
        this.c = (ListView) findViewById(R.id.application_list)
        bl blVar = bl(this)
        blVar.a(this.f, SettingActivity.a(this))
        this.c.setAdapter((ListAdapter) blVar)
        this.c.setOnItemClickListener(this)
        registerForContextMenu(this.c)
        this.j = (EditText) findViewById(R.id.et_keyword)
        this.k = findViewById(R.id.btn_search)
        this.k.setOnClickListener(this)
        findViewById(R.id.btn_close).setOnClickListener(this)
    }

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    fun onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        bk bkVarA
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo)
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) contextMenuInfo
        if (adapterContextMenuInfo == null || (bkVarA = a(adapterContextMenuInfo.position)) == null) {
            return
        }
        contextMenu.setHeaderTitle(bkVarA.c)
        contextMenu.add(0, 2, 0, R.string.app_info)
        contextMenu.add(0, 3, 0, R.string.backup)
        contextMenu.add(0, 4, 0, R.string.launch)
    }

    @Override // android.app.Activity
    fun onDestroy() {
        super.onDestroy()
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) throws PackageManager.NameNotFoundException {
        bk bkVarA = a(i)
        if (bkVarA != null) {
            try {
                ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(bkVarA.f896b, 0)
                String str = applicationInfo.sourceDir
                if (MainActivity.a(this)) {
                    cc(this, this, str, applicationInfo.packageName).show()
                } else {
                    a(this, str)
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace()
            }
        }
    }

    @Override // android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // android.app.Activity
    fun onResume() {
        super.onResume()
        a()
    }
}
