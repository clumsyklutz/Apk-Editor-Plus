package com.gmail.heagoo.b

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.List

class e implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /* renamed from: a, reason: collision with root package name */
    private Context f1430a

    /* renamed from: b, reason: collision with root package name */
    private ListView f1431b
    private String c
    private String d
    private c e
    private n f

    constructor(Context context, ListView listView, String str, String str2, n nVar, o oVar) {
        this.f1430a = context
        this.f1431b = listView
        this.d = str
        this.c = str2
        this.f = nVar
        this.e = c(this.f1430a, this.c, this.d, oVar)
        this.f1431b.setAdapter((ListAdapter) this.e)
        this.f1431b.setOnItemClickListener(this)
        this.f1431b.setOnItemLongClickListener(this)
    }

    static /* synthetic */ Unit a(e eVar) {
        String strA = eVar.e.a((List) null)
        Float f = eVar.f1430a.getResources().getDisplayMetrics().density
        Int i = (Int) ((8.0f * f) + 0.5f)
        Int i2 = (Int) ((f * 20.0f) + 0.5f)
        AlertDialog.Builder builder = new AlertDialog.Builder(eVar.f1430a)
        builder.setTitle(R.string.new_file)
        LinearLayout linearLayout = LinearLayout(eVar.f1430a)
        linearLayout.setOrientation(1)
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2))
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2)
        layoutParams.setMargins(i2, i, i2, 0)
        EditText editText = EditText(eVar.f1430a)
        editText.setFilters(new Array<InputFilter>{com.gmail.heagoo.common.a.a()})
        editText.setHint(R.string.pls_input_filename)
        editText.setTextColor(ContextCompat.getColor(eVar.f1430a, a.a.b.a.k.mdTextMedium(a.a.b.a.k.a(eVar.f1430a))))
        editText.setTextSize(1, 14)
        editText.setLayoutParams(layoutParams)
        linearLayout.addView(editText)
        builder.setView(linearLayout)
        builder.setPositiveButton(android.R.string.ok, l(eVar, editText, strA))
        builder.setNegativeButton(android.R.string.cancel, m(eVar))
        builder.show()
    }

    static /* synthetic */ Unit a(e eVar, Int i) {
        ArrayList arrayList = ArrayList()
        String strA = eVar.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar != null) {
            String str = aVar.f1424a
            if (b(strA + "/" + aVar.f1424a)) {
                eVar.e.b(str)
            }
        }
    }

    static /* synthetic */ Unit b(e eVar, Int i) {
        Float f = eVar.f1430a.getResources().getDisplayMetrics().density
        Int i2 = (Int) ((8.0f * f) + 0.5f)
        Int i3 = (Int) ((f * 20.0f) + 0.5f)
        AlertDialog.Builder builder = new AlertDialog.Builder(eVar.f1430a)
        builder.setTitle(R.string.rename)
        LinearLayout linearLayout = LinearLayout(eVar.f1430a)
        linearLayout.setOrientation(1)
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2))
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2)
        layoutParams.setMargins(i3, i2, i3, 0)
        EditText editText = EditText(eVar.f1430a)
        editText.setHint(R.string.pls_input_filename)
        editText.setTextColor(ContextCompat.getColor(eVar.f1430a, a.a.b.a.k.mdTextMedium(a.a.b.a.k.a(eVar.f1430a))))
        editText.setTextSize(1, 14)
        editText.setLayoutParams(layoutParams)
        linearLayout.addView(editText)
        ArrayList arrayList = ArrayList()
        String strA = eVar.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar != null) {
            String str = aVar.f1424a
            editText.setText(str)
            builder.setView(linearLayout)
            builder.setPositiveButton(android.R.string.ok, j(eVar, editText, strA, str))
            builder.setNegativeButton(android.R.string.cancel, k(eVar))
            builder.show()
        }
    }

    private fun b(String str) {
        File file = File(str)
        if (!file.exists()) {
            return false
        }
        if (file.isFile()) {
            return file.delete()
        }
        try {
            com.gmail.heagoo.common.h.a(file)
            return true
        } catch (IOException e) {
            return false
        }
    }

    static /* synthetic */ Unit d(e eVar, Int i) throws Throwable {
        ArrayList arrayList = ArrayList()
        String strA = eVar.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar != null) {
            String str = aVar.f1424a
            com.gmail.heagoo.a.c.a.a(eVar.f1430a, strA + "/" + aVar.f1424a)
        }
    }

    static /* synthetic */ Unit e(e eVar, Int i) {
        ArrayList arrayList = ArrayList()
        String strA = eVar.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar != null) {
            String str = aVar.f1424a
            com.gmail.heagoo.a.c.a.aa(eVar.f1430a, strA + "/" + aVar.f1424a)
        }
    }

    static /* synthetic */ Unit f(e eVar, Int i) {
        ArrayList arrayList = ArrayList()
        String strA = eVar.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar != null) {
            String str = aVar.f1424a
            com.gmail.heagoo.a.c.b.sign(eVar.f1430a, strA + "/" + aVar.f1424a)
        }
    }

    static /* synthetic */ Unit g(e eVar, Int i) {
        ArrayList arrayList = ArrayList()
        String strA = eVar.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar != null) {
            String str = aVar.f1424a
            com.gmail.heagoo.a.c.b.checkSign(eVar.f1430a, strA + "/" + aVar.f1424a)
        }
    }

    public final c a() {
        return this.e
    }

    public final Unit a(String str) {
        String strA = this.e.a((List) null)
        this.e.a(str)
        String strA2 = this.e.a((List) null)
        if (strA2.equals(strA)) {
            return
        }
        this.f.a(strA2)
    }

    protected final Boolean a(String str, String str2, String str3) {
        File file = File(str + "/" + str3)
        if (file.exists()) {
            Toast.makeText(this.f1430a, String.format(this.f1430a.getResources().getString(R.string.file_already_exist), str3), 0).show()
            return false
        }
        Boolean zRenameTo = File(str + "/" + str2).renameTo(file)
        Toast.makeText(this.f1430a, this.f1430a.getResources().getString(R.string.rename) + " " + this.f1430a.getResources().getString(zRenameTo ? R.string.succeed : R.string.failed), 0).show()
        return zRenameTo
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        Context context
        Intent intentE
        ArrayList arrayList = ArrayList()
        String strA = this.e.a(arrayList)
        a aVar = (a) arrayList.get(i)
        if (aVar == null) {
            return
        }
        if (aVar.f1425b) {
            this.e.a(aVar.f1424a.equals("..") ? strA.substring(0, strA.lastIndexOf(47)) : strA + "/" + aVar.f1424a)
        } else {
            String str = strA + "/" + aVar.f1424a
            if (!this.f.b(str) && (intentE = com.gmail.heagoo.a.c.a.e((context = this.f1430a), str)) != null) {
                context.startActivity(intentE)
            }
        }
        String strA2 = this.e.a((List) null)
        if (strA2.equals(strA)) {
            return
        }
        this.f.a(strA2)
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        if (i == 0) {
            return true
        }
        adapterView.setOnCreateContextMenuListener(f(this, i))
        return false
    }
}
