package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.ArrayList
import java.util.List

class cn extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /* renamed from: a, reason: collision with root package name */
    private ff f938a

    /* renamed from: b, reason: collision with root package name */
    private String f939b
    private Boolean c
    private Boolean d
    private String e
    private TextView f
    private TextView g
    private CheckBox h
    private cu i
    private Context j

    constructor(Context context, cu cuVar, String str, String str2, String str3) {
        this(context, cuVar, str, str2, str3, false, false, false, null)
    }

    constructor(Context context, cu cuVar, String str, String str2, String str3, Boolean z, Boolean z2, Boolean z3, String str4) {
        this(context, cuVar, str, str2, str3, z, z2, z3, str4, null)
    }

    @SuppressLint({"InflateParams"})
    constructor(Context context, cu cuVar, String str, String str2, String str3, Boolean z, Boolean z2, Boolean z3, String str4, String str5) {
        super(context)
        super.requestWindowFeature(1)
        this.j = context
        this.i = cuVar
        this.f939b = str2
        this.c = z
        this.d = z2
        this.e = str4
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dlg_fileselect, (ViewGroup) null, false)
        Button button = (Button) viewInflate.findViewById(R.id.confirm)
        if (z) {
            button.setVisibility(0)
            button.setOnClickListener(this)
        } else {
            button.setVisibility(8)
        }
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(str4 != null ? str4 + "_lastDirectory" : "lastDirectory", "")
        string = File(string).exists() ? string : str5 == null ? Environment.getExternalStorageDirectory().getPath() : str5
        this.f = (TextView) viewInflate.findViewById(R.id.tv_title)
        this.g = (TextView) viewInflate.findViewById(R.id.tv_subtitle)
        this.f.setText(str3 == null ? str != null ? context.getString(R.string.select_file_replace) + " (" + str + ")" : context.getString(R.string.select_file_replace) : str3)
        this.g.setText(string)
        ListView listView = (ListView) viewInflate.findViewById(R.id.file_list)
        this.f938a = ff(context, null, string, "/", cs(this))
        listView.setAdapter((ListAdapter) this.f938a)
        listView.setOnItemClickListener(this)
        listView.setOnItemLongClickListener(this)
        this.h = (CheckBox) viewInflate.findViewById(R.id.cb_edit_before_replace)
        if (z3) {
            this.h.setText(String.format(context.getString(R.string.edit_before_replace), a.l(str2)))
            this.h.setChecked(PreferenceManager.getDefaultSharedPreferences(this.j).getBoolean(this.e != null ? this.e + "_editBeforeReplace" : "editBeforeReplace", false))
            this.h.setVisibility(0)
        } else {
            this.h.setVisibility(8)
        }
        ((Button) viewInflate.findViewById(R.id.close)).setOnClickListener(this)
        setContentView(viewInflate)
    }

    static /* synthetic */ Unit a(cn cnVar) {
        String strA = cnVar.f938a.a((List) null)
        Float f = cnVar.j.getResources().getDisplayMetrics().density
        Int i = (Int) ((20.0f * f) + 0.5f)
        AlertDialog.Builder builder = new AlertDialog.Builder(cnVar.j)
        builder.setTitle(R.string.new_folder)
        LinearLayout linearLayout = LinearLayout(cnVar.j)
        linearLayout.setOrientation(1)
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2))
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2)
        layoutParams.setMargins(i, (Int) ((f * 8.0f) + 0.5f), i, 0)
        EditText editText = EditText(cnVar.j)
        editText.setFilters(new Array<InputFilter>{com.gmail.heagoo.common.a.a()})
        editText.setHint(R.string.pls_input_foldername)
        editText.setTextColor(ContextCompat.getColor(cnVar.j, a.a.b.a.k.mdTextMedium(a.a.b.a.k.a(cnVar.j))))
        editText.setTextSize(1, 14)
        editText.setLayoutParams(layoutParams)
        linearLayout.addView(editText)
        builder.setView(linearLayout)
        builder.setPositiveButton(android.R.string.ok, cq(cnVar, editText, strA))
        builder.setNegativeButton(android.R.string.cancel, cr(cnVar))
        builder.show()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(String str) {
        return this.i.a(str, this.f939b)
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(String str) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.j).edit()
        editorEdit.putString(this.e != null ? this.e + "_lastDirectory" : "lastDirectory", str)
        editorEdit.apply()
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.close) {
            dismiss()
            return
        }
        if (id == R.id.confirm) {
            String strA = this.f938a.a((List) null)
            if (this.d) {
                new AlertDialog.Builder(this.j).setTitle(R.string.confirm_dir_replace).setMessage(this.i.b(strA, this.f939b)).setPositiveButton(R.string.yes, ct(this, strA)).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).show()
                return
            }
            this.i.a(strA, this.f939b, this.h.isChecked())
            b(strA)
            dismiss()
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final Unit onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        ArrayList arrayList = ArrayList()
        String strA = this.f938a.a(arrayList)
        com.gmail.heagoo.b.a aVar = (com.gmail.heagoo.b.a) arrayList.get(i)
        if (aVar == null) {
            return
        }
        if (aVar.f1425b) {
            this.f938a.c(aVar.f1424a.equals("..") ? strA.substring(0, strA.lastIndexOf(47)) : strA + "/" + aVar.f1424a)
            this.g.setText(this.f938a.a((List) null))
            return
        }
        if (this.c || !a(aVar.f1424a)) {
            return
        }
        String str = strA + "/" + aVar.f1424a
        Boolean zIsChecked = this.h.isChecked()
        this.i.a(str, this.f939b, zIsChecked)
        b(strA)
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.j).edit()
        editorEdit.putBoolean(this.e != null ? this.e + "_editBeforeReplace" : "editBeforeReplace", zIsChecked)
        editorEdit.apply()
        dismiss()
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final Boolean onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        adapterView.setOnCreateContextMenuListener(co(this))
        return false
    }
}
