package com.gmail.heagoo.apkeditor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.lang.ref.WeakReference

class df implements View.OnClickListener, cu {

    /* renamed from: a, reason: collision with root package name */
    private View f956a

    /* renamed from: b, reason: collision with root package name */
    private Context f957b
    private WeakReference c
    private EditText d
    private EditText e

    constructor(KeyListPreference keyListPreference) {
        this.c = WeakReference(keyListPreference)
    }

    protected final Unit a() {
        String string = this.d.getText().toString()
        String string2 = this.e.getText().toString()
        if ("".equals(string) || "".equals(string2)) {
            Toast.makeText(this.f957b, R.string.error_filepath_empty, 1).show()
            return
        }
        File file = File(string2)
        File file2 = File(string)
        if (!file.exists() || !file2.exists()) {
            Toast.makeText(this.f957b, R.string.error_filepath_notexist, 1).show()
            return
        }
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.f957b).edit()
        editorEdit.putString("PrivateKeyPath", string)
        editorEdit.putString("PublicKeyPath", string2)
        editorEdit.commit()
        KeyListPreference keyListPreference = (KeyListPreference) this.c.get()
        keyListPreference.setValue(StringBuilder().append((Object) keyListPreference.getEntryValues()[2]).toString())
    }

    public final Unit a(Context context) {
        this.f957b = context
        this.f956a = LayoutInflater.from(context).inflate(R.layout.dlg_keyselect, (ViewGroup) null, false)
        this.d = (EditText) this.f956a.findViewById(R.id.et_pk8path)
        this.e = (EditText) this.f956a.findViewById(R.id.et_x509path)
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.f957b)
        this.d.setText(defaultSharedPreferences.getString("PrivateKeyPath", ""))
        this.e.setText(defaultSharedPreferences.getString("PublicKeyPath", ""))
        ((RelativeLayout) this.f956a.findViewById(R.id.btn_select_pk8)).setOnClickListener(this)
        ((RelativeLayout) this.f956a.findViewById(R.id.btn_select_x509)).setOnClickListener(this)
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
        builder.setView(this.f956a)
        builder.setTitle(R.string.custom_key_setting)
        builder.setPositiveButton(android.R.string.ok, dg(this))
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null)
        builder.show()
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Unit a(String str, String str2, Boolean z) {
        if (".pk8".equals(str2)) {
            this.d.setText(str)
        } else {
            this.e.setText(str)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final Boolean a(String str, String str2) {
        return str.endsWith(".pk8") || str.endsWith(str2)
    }

    @Override // com.gmail.heagoo.apkeditor.cu
    public final String b(String str, String str2) {
        return null
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_select_pk8) {
            cn(this.f957b, this, ".pk8", ".pk8", this.f957b.getString(R.string.select_key_file)).show()
        } else if (id == R.id.btn_select_x509) {
            cn(this.f957b, this, ".x509.pem", ".pem", this.f957b.getString(R.string.select_key_file)).show()
        }
    }
}
