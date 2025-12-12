package com.gmail.heagoo.apkeditor.c

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.TextEditNormalActivity
import com.gmail.heagoo.apkeditor.gp
import com.gmail.heagoo.apkeditor.gr
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.util.ab
import com.gmail.heagoo.apkeditor.util.z
import com.gmail.heagoo.common.w
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.ArrayList

class a extends Dialog implements View.OnClickListener, gr {

    /* renamed from: a, reason: collision with root package name */
    private TextView f918a

    /* renamed from: b, reason: collision with root package name */
    private View f919b
    private WebView c
    private String d
    private File e
    private WeakReference f
    private c g
    private gp h

    constructor(Activity activity) {
        super(activity, R.style.Dialog_FullWindow)
        this.g = c(this)
        this.h = gp(this)
        this.f = WeakReference(activity)
        setContentView(R.layout.dlg_htmlview)
        this.f918a = (TextView) findViewById(R.id.filename)
        this.f919b = findViewById(R.id.menu_methods)
        this.c = (WebView) findViewById(R.id.webView)
        View viewFindViewById = findViewById(R.id.editorBtn)
        this.f919b.setOnClickListener(this)
        viewFindViewById.setOnClickListener(this)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Int i) {
        if (this.e != null) {
            this.c.loadUrl("file://" + this.e.getAbsolutePath() + (i > 0 ? "#line" + i : ""))
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(String str) throws IOException {
        ArrayList arrayList = ArrayList()
        try {
            BufferedReader bufferedReader = BufferedReader(FileReader(str))
            while (true) {
                String line = bufferedReader.readLine()
                if (line == null) {
                    break
                }
                arrayList.add(line)
            }
            bufferedReader.close()
            try {
                File file = File(((Activity) this.f.get()).getFilesDir(), "viewsource.css")
                if (!file.exists()) {
                    InputStream inputStreamOpen = ((Activity) this.f.get()).getAssets().open("viewsource.css")
                    FileOutputStream fileOutputStream = FileOutputStream(file)
                    com.gmail.heagoo.a.c.a.b(inputStreamOpen, fileOutputStream)
                    fileOutputStream.close()
                }
            } catch (IOException e) {
                e.printStackTrace()
            }
            File file2 = File(((Activity) this.f.get()).getFilesDir(), ".html")
            try {
                if (TextEditNormalActivity.g(str)) {
                    ab().a(arrayList, file2.getAbsolutePath())
                } else {
                    com.gmail.heagoo.a.c.a.a(arrayList, file2.getAbsolutePath())
                }
                this.e = file2
                return true
            } catch (IOException e2) {
                e2.printStackTrace()
                return false
            }
        } catch (Exception e3) {
            e3.printStackTrace()
            return false
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(String str) {
        z zVar = z(str)
        File file = File(((Activity) this.f.get()).getFilesDir(), ".html")
        try {
            zVar.a(file.getAbsolutePath())
            this.e = file
            return true
        } catch (IOException e) {
            e.printStackTrace()
            return false
        }
    }

    public final Unit a(String str, String str2) {
        if (!str.equals(this.d)) {
            this.d = str
            this.f918a.setText(str2)
            if (TextEditNormalActivity.c(str)) {
                this.f919b.setVisibility(0)
            } else {
                this.f919b.setVisibility(8)
            }
            b(this, str).start()
        }
        show()
    }

    @Override // com.gmail.heagoo.apkeditor.gr
    public final Unit b(Int i) {
        a(i)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id != R.id.menu_methods) {
            if (id == R.id.editorBtn) {
                dismiss()
            }
        } else {
            if (this.h != null && this.h.a() != null) {
                this.h.a(view)
                return
            }
            try {
                this.h.a((Activity) this.f.get(), this.d, w(this.d).b(), view)
            } catch (Exception e) {
            }
        }
    }
}
