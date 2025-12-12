package com.gmail.heagoo.apkeditor.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.preference.ListPreference
import android.preference.PreferenceManager
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.util.ArrayList
import java.util.List

class IconPickerPreference extends ListPreference {

    /* renamed from: a, reason: collision with root package name */
    private Context f1299a

    /* renamed from: b, reason: collision with root package name */
    private Array<Int> f1300b
    private Array<CharSequence> c
    private Array<CharSequence> d
    private List e
    private SharedPreferences f
    private Resources g
    private String h
    private String i
    private TextView j

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f1299a = context
        this.g = context.getResources()
        this.f = PreferenceManager.getDefaultSharedPreferences(context)
        this.c = this.g.getStringArray(R.array.appicon_key)
        this.d = this.g.getStringArray(R.array.appicon_value)
        this.i = this.d[0].toString()
        this.h = this.f.getString("MyIcon", this.i)
        this.f1300b = (Array<Int>) com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.seticon.SetIcon", "getAllIcons", (Array<Class>) null, (Array<Object>) null)
    }

    @Override // android.preference.Preference
    protected fun onBindView(View view) {
        super.onBindView(view)
        if (Build.VERSION.SDK_INT < 21) {
            view.setPadding((Int) ((view.getPaddingLeft() / 16.0f) * 6.0f), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom())
        }
        ((TextView) view.findViewById(R.id.title)).setText(R.string.launcher_icon)
        this.j = (TextView) view.findViewById(R.id.summary)
        for (Int i = 0; i < this.d.length; i++) {
            if (this.h.equals(this.d[i])) {
                this.j.setText(this.c[i])
                return
            }
        }
    }

    @Override // android.preference.ListPreference, android.preference.DialogPreference
    protected fun onDialogClosed(Boolean z) {
        super.onDialogClosed(z)
        if (this.e != null) {
            for (Int i = 0; i < this.c.length; i++) {
                v vVar = (v) this.e.get(i)
                if (vVar.f1336b) {
                    SharedPreferences.Editor editorEdit = this.f.edit()
                    editorEdit.putString("MyIcon", vVar.d)
                    editorEdit.commit()
                    this.j.setText(vVar.c)
                    String str = vVar.d
                    if (this.h.equals(str)) {
                        return
                    }
                    this.h = str
                    com.gmail.heagoo.a.c.a.a("com.gmail.heagoo.seticon.SetIcon", "setIcon", new Array<Class>{Activity.class, String.class}, new Array<Object>{(Activity) this.f1299a, str})
                    Toast.makeText(this.f1299a, R.string.icon_changed_tip, 1).show()
                    return
                }
            }
        }
    }

    @Override // android.preference.ListPreference, android.preference.DialogPreference
    protected fun onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null)
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null)
        this.c = getEntries()
        this.d = getEntryValues()
        if (this.c == null || this.d == null || this.c.length != this.d.length) {
            throw IllegalStateException("Invalid arguments.")
        }
        this.e = ArrayList()
        for (Int i = 0; i < this.c.length; i++) {
            this.e.add(v(this.c[i], this.d[i], this.f1300b[i], this.h.equals(this.d[i])))
        }
        builder.setAdapter(t(this, this.f1299a, R.layout.item_iconpicker, this.e), null)
    }
}
