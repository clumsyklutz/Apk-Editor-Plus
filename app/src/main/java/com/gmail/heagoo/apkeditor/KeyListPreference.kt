package com.gmail.heagoo.apkeditor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.preference.ListPreference
import android.preference.Preference
import android.util.AttributeSet
import com.gmail.heagoo.apkeditor.pro.R

class KeyListPreference extends ListPreference implements Preference.OnPreferenceClickListener {

    /* renamed from: a, reason: collision with root package name */
    private Context f768a

    constructor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet)
        this.f768a = context
    }

    @Override // android.preference.ListPreference, android.preference.DialogPreference
    protected fun onDialogClosed(Boolean z) {
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    fun onPreferenceClick(Preference preference) {
        return false
    }

    @Override // android.preference.ListPreference, android.preference.DialogPreference
    protected fun onPrepareDialogBuilder(AlertDialog.Builder builder) {
        String value = getValue()
        Int length = getEntryValues().length
        Int i = 0
        while (true) {
            if (i >= length) {
                i = 2
                break
            } else if (value.equals(getEntryValues()[i])) {
                break
            } else {
                i++
            }
        }
        builder.setSingleChoiceItems(R.array.signer_key, i, de(this))
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null)
    }
}
