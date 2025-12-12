package com.gmail.heagoo.apkeditor

import android.preference.Preference
import android.preference.SwitchPreference
import com.gmail.heagoo.apkeditor.pro.R

final class gh implements Preference.OnPreferenceChangeListener {
    gh(SettingActivity settingActivity) {
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public final Boolean onPreferenceChange(Preference preference, Object obj) {
        SwitchPreference switchPreference = (SwitchPreference) preference
        Boolean zBooleanValue = ((Boolean) obj).booleanValue()
        switchPreference.setChecked(zBooleanValue)
        switchPreference.setSummary(zBooleanValue ? R.string.smali_edit_summary : R.string.smali_edit_disabled_summary)
        return true
    }
}
