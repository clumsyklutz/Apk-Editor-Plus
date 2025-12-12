package com.gmail.heagoo.apkeditor

import android.preference.Preference
import android.preference.SwitchPreference
import com.gmail.heagoo.apkeditor.pro.R

class ExtEditor implements Preference.OnPreferenceChangeListener {
    constructor(SettingActivity settingActivity) {
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public final Boolean onPreferenceChange(Preference preference, Object obj) {
        SwitchPreference switchPreference = (SwitchPreference) preference
        Boolean zBooleanValue = ((Boolean) obj).booleanValue()
        switchPreference.setChecked(zBooleanValue)
        switchPreference.setSummary(zBooleanValue ? R.string.external_editor_e_summary : R.string.external_editor_d_summary)
        return true
    }
}
