package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List

abstract class gy extends Activity implements com.b.a.e, dn, gr {
    public static Boolean z = false

    /* renamed from: a, reason: collision with root package name */
    protected String f1129a

    /* renamed from: b, reason: collision with root package name */
    protected List f1130b
    protected List c
    protected List d
    protected String h
    protected Int i
    protected com.gmail.heagoo.neweditor.e j
    private Boolean k
    private Boolean l
    private String m
    private Boolean n
    private String o
    private List p
    private String q
    private Boolean s
    private View w
    private LinearLayout x
    private a y
    protected Int e = 0
    protected Boolean f = true
    protected Boolean g = false
    private ArrayList r = ArrayList()
    private gp t = gp(this)
    private Array<String> u = {",.?!:;~-_=\"'/@*+()<>{}[]%&$|\\#^", "<>\":=/@+.-?#_()[]{}\\;!$%^&*|~',", "(){};.=\"'|&![]@<>+-*/?:,_\\^%#~$"}
    private Int v = -1

    constructor(Boolean z2, Boolean z3) {
        this.k = z2
        this.l = z3
    }

    protected static Boolean a(Array<String> strArr, Int i, Int i2, String str) {
        while (i <= i2) {
            if (!strArr[i - 1].trim().startsWith(str)) {
                return false
            }
            i++
        }
        return true
    }

    static /* synthetic */ Unit b(gy gyVar, Boolean z2) {
        if (SettingEditorActivity.c(gyVar)) {
            if (z2) {
                gyVar.w.setVisibility(0)
                gyVar.x.setVisibility(4)
            } else {
                gyVar.x.setVisibility(0)
                gyVar.w.setVisibility(4)
            }
        }
    }

    fun c(String str) {
        return str != null && str.endsWith(".smali")
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun d() {
        if (this.m == null) {
            Toast.makeText(this, "Internal error: cannot find apk path to decode java code, please contact the author.", 1).show()
            return
        }
        try {
            String strD = com.gmail.heagoo.a.c.a.d(this, "tmp")
            String str = "classes.dex"
            Array<String> strArrSplit = this.q.split("/")
            Int i = 0
            while (true) {
                if (i >= strArrSplit.length || "smali".equals(strArrSplit[i])) {
                    break
                }
                if (strArrSplit[i].startsWith("smali_")) {
                    str = strArrSplit[i].substring(6) + ".dex"
                    break
                }
                i++
            }
            StringBuilder sb = StringBuilder()
            sb.append('L')
            while (true) {
                i++
                if (i >= strArrSplit.length) {
                    break
                }
                String str2 = strArrSplit[i]
                if (i != strArrSplit.length - 1) {
                    sb.append(str2)
                    sb.append('/')
                } else if (str2.length() > 6 && str2.endsWith(".smali")) {
                    sb.append(str2.substring(0, str2.length() - 6))
                }
            }
            Array<String> strArr = sb.length() == 0 ? null : new Array<String>{str, sb.toString()}
            String str3 = strArr[0]
            String str4 = strArr[1]
            if (str4 == null) {
                Toast.makeText(this, "Internal error: Cannot get class name, please contact the author.", 1).show()
            } else {
                ey(this, he(this, str3, str4, strD), -1).show()
            }
        } catch (Exception e) {
            Toast.makeText(this, "Cannot make working directory.", 0).show()
        }
    }

    static /* synthetic */ Unit d(gy gyVar) {
        if (gyVar.y == null) {
            gyVar.y = a(gyVar)
        }
        gyVar.y.a(gyVar.q, gyVar.f(gyVar.q))
    }

    fun d(String str) {
        return str != null && str.endsWith(".java")
    }

    fun e(String str) {
        return str.endsWith(".xml")
    }

    static /* synthetic */ Unit f(gy gyVar) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(gyVar)
        if (defaultSharedPreferences.getBoolean("java_edit_tip_shown", false)) {
            gyVar.d()
            return
        }
        new AlertDialog.Builder(gyVar).setTitle(R.string.please_note).setMessage(R.string.java_code_edit_tip).setPositiveButton(android.R.string.ok, hd(gyVar)).show()
        SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit()
        editorEdit.putBoolean("java_edit_tip_shown", true)
        editorEdit.commit()
    }

    protected final String a() {
        return this.q
    }

    @Override // com.b.a.e
    public final Unit a(Int i) {
        String str = String.format("#%08x", Integer.valueOf(i))
        com.gmail.heagoo.a.c.a.c(this, str)
        Toast.makeText(this, String.format(getString(R.string.copied_to_clipboard), str), 0).show()
    }

    fun a(Int i, Int i2) {
        Toast.makeText(this, "Not implemented.", 0).show()
    }

    protected final Unit a(View view) {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.popup_list, (ViewGroup) null)
        ListView listView = (ListView) viewInflate.findViewById(R.id.lvGroup)
        er erVar = er(this, this.q)
        listView.setAdapter((ListAdapter) erVar)
        PopupWindow popupWindow = PopupWindow(viewInflate, com.gmail.heagoo.common.f.a(this) / 2, com.gmail.heagoo.common.f.a(this, (erVar.a() * 48) + 32))
        listView.setOnItemClickListener(hc(this, erVar, popupWindow))
        popupWindow.setFocusable(true)
        popupWindow.setOutsideTouchable(true)
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.showAsDropDown(view, (((WindowManager) getSystemService("window")).getDefaultDisplay().getWidth() / 2) - (popupWindow.getWidth() / 2), 0)
    }

    protected final Unit a(com.gmail.heagoo.neweditor.s sVar) {
        View viewFindViewById = findViewById(android.R.id.content)
        viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(hb(this, viewFindViewById, sVar))
    }

    protected final Unit a(String str) {
        this.q = str
        Int i = str == null ? this.v : str.endsWith(".xml") ? 1 : str.endsWith(".java") ? 2 : 0
        if (i != this.v) {
            this.v = i
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.special_char_container)
            linearLayout.removeAllViews()
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(com.gmail.heagoo.common.f.a(this) / 11, -1)
            String str2 = this.u[this.v]
            for (Int i2 = 0; i2 < str2.length(); i2++) {
                TextView textView = TextView(this)
                textView.setTextSize(2, 24.0f)
                textView.setBackgroundResource(R.drawable.mtrl_selectable)
                textView.setText(StringBuilder().append(str2.charAt(i2)).toString())
                textView.setTextColor(ContextCompat.getColor(this, a.a.b.a.k.mdTextMedium(a.a.b.a.k.a(this))))
                textView.setGravity(17)
                textView.setTag(Integer.valueOf(i2))
                textView.setOnClickListener(ha(this))
                linearLayout.addView(textView, layoutParams)
            }
        }
    }

    protected final String b(String str) {
        return this.v >= 0 ? this.u[this.v] : ""
    }

    protected final Unit b() {
        Intent intent = Intent()
        if (this.n) {
            if (!this.r.contains(this.q)) {
                this.r.add(this.q)
            }
            intent.putStringArrayListExtra("modifiedFiles", this.r)
        } else {
            intent.putExtra("xmlPath", this.q)
            intent.putExtra("extraString", (String) this.p.get(0))
        }
        setResult(1, intent)
    }

    protected final Unit b(View view) {
        if (this.t == null || !this.q.equals(this.t.a())) {
            this.t.a(this, this.q, this.j.a(), view)
        } else {
            this.t.a(view)
        }
    }

    protected abstract Unit c(Int i)

    protected final Boolean c() {
        return this.s
    }

    public final String f(String str) {
        if (!this.n && this.o != null) {
            return this.o
        }
        Int iLastIndexOf = str.lastIndexOf(47)
        return iLastIndexOf != -1 ? str.substring(iLastIndexOf + 1) : str
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0041  */
    @Override // android.app.Activity
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onCreate(android.os.Bundle r9) {
        /*
            Method dump skipped, instructions count: 473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.gy.onCreate(android.os.Bundle):Unit")
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        if (z) {
            z = false
            recreate()
        }
    }
}
