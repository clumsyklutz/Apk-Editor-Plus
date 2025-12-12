package com.gmail.heagoo.apkeditor

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.ArrayList
import java.util.List
import java.util.Map

class ce extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private List f927a

    /* renamed from: b, reason: collision with root package name */
    private String f928b
    private String c
    private String d
    private Map e
    private ch f
    private View g
    private String h
    private String i
    private Int j
    private String k

    constructor(Context context, String str, String str2, String str3, String str4, Map map) {
        super(context)
        this.f = ch(this)
        requestWindowFeature(1)
        cg cgVar = cg()
        cgVar.f930a = str
        cgVar.c = false
        cgVar.f931b = File(str).isDirectory()
        this.f927a = ArrayList()
        this.f927a.add(cgVar)
        this.f928b = null
        this.d = null
        this.e = null
        if (str2 != null) {
            this.c = str2
        } else {
            this.c = Environment.getExternalStorageDirectory().getPath() + "/ApkEditor"
        }
        a(context)
    }

    constructor(Context context, String str, String str2, Map map, List list, String str3) {
        super(context)
        this.f = ch(this)
        requestWindowFeature(1)
        this.f928b = str
        this.d = str2
        this.e = map
        this.f927a = list
        this.c = str3
        a(context)
    }

    fun a(String str, Boolean z) {
        String str2
        String strSubstring
        String strSubstring2
        Int i = 1
        if (z) {
            str2 = null
            strSubstring = null
            strSubstring2 = null
        } else {
            Int iLastIndexOf = str.lastIndexOf(47)
            strSubstring2 = str.substring(0, iLastIndexOf + 1)
            strSubstring = str.substring(iLastIndexOf + 1)
            Int iLastIndexOf2 = strSubstring.lastIndexOf(46)
            if (iLastIndexOf2 != -1) {
                String strSubstring3 = strSubstring.substring(0, iLastIndexOf2)
                String strSubstring4 = strSubstring.substring(iLastIndexOf2)
                strSubstring = strSubstring3
                str2 = strSubstring4
            } else {
                str2 = ""
            }
        }
        while (true) {
            File file = File(z ? str + "(" + i + ")" : strSubstring2 + strSubstring + "(" + i + ")" + str2)
            if (!file.exists()) {
                return file
            }
            i++
        }
    }

    private fun a(Context context) {
        this.j = SettingActivity.g(context)
        Resources resources = context.getResources()
        this.h = resources.getString(R.string.save_succeed_1)
        this.i = resources.getString(R.string.failed_1)
        this.g = LayoutInflater.from(context).inflate(R.layout.dlg_extractres, (ViewGroup) null)
        setContentView(this.g)
        ((Button) this.g.findViewById(R.id.close_button)).setOnClickListener(this)
    }

    private fun a(File file, File file2) throws Throwable {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file3 : fileArrListFiles) {
                if (file3.isFile()) {
                    b(file3, File(file2, file3.getName()))
                } else {
                    File file4 = File(file2, file3.getName())
                    file4.mkdir()
                    a(file3, file4)
                }
            }
        }
    }

    private fun b(String str) {
        return str.substring(str.lastIndexOf(47) + 1)
    }

    private fun b(File file, File file2) throws Throwable {
        String name = file.getName()
        if (this.f928b == null || (!name.endsWith(".jpg") && (!name.endsWith(".png") || name.endsWith(".9.png")))) {
            com.gmail.heagoo.common.h.a(file, file2)
            return
        }
        String strSubstring = file.getPath().substring(this.d.length() + 1)
        String str = (String) this.e.get(strSubstring)
        if (str == null) {
            str = strSubstring
        }
        a.a.b.a.a.x.b(this.f928b, str, file2.getPath())
    }

    public final Unit a() {
        TextView textView = (TextView) this.g.findViewById(R.id.result_tv)
        if (this.f927a.size() == 1) {
            textView.setText(String.format(this.h, this.k))
        } else {
            textView.setText(String.format(this.h, this.c))
        }
        this.g.findViewById(R.id.layout_done).setVisibility(0)
    }

    protected final Unit a(cg cgVar) throws Throwable {
        String path = this.c + "/" + b(cgVar.f930a)
        if (File(path).exists() && this.j == 0) {
            path = a(path, cgVar.f931b).getPath()
        }
        if (cgVar.f931b) {
            a.a.b.a.a.x.a(this.f928b, cgVar.f930a, path)
        } else {
            a.a.b.a.a.x.b(this.f928b, cgVar.f930a, path)
        }
        this.k = path
    }

    public final Unit a(String str) {
        ((TextView) this.g.findViewById(R.id.result_tv)).setText(String.format(this.i, str))
        this.g.findViewById(R.id.layout_done).setVisibility(0)
    }

    protected final Unit b(cg cgVar) throws Throwable {
        String str = this.c + "/" + b(cgVar.f930a)
        File file = File(cgVar.f930a)
        if (!file.isDirectory()) {
            File file2 = File(str)
            if (file2.exists() && this.j == 0) {
                file2 = a(str, false)
            }
            b(File(cgVar.f930a), file2)
            this.k = file2.getPath()
            return
        }
        File file3 = File(str)
        if (!file3.exists()) {
            file3.mkdirs()
        } else if (this.j == 0) {
            file3 = a(str, true)
            file3.mkdirs()
        }
        a(file, file3)
        this.k = file3.getPath()
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (view.getId() == R.id.close_button) {
            dismiss()
        }
    }

    @Override // android.app.Dialog
    public final Unit show() {
        cf(this).start()
        super.show()
    }
}
