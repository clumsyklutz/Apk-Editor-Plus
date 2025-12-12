package com.gmail.heagoo.apkeditor

import android.preference.Preference
import android.preference.SwitchPreference
import com.gmail.heagoo.apkeditor.pro.R

final class gi implements Preference.OnPreferenceChangeListener {
    gi(SettingActivity settingActivity) {
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public final Boolean onPreferenceChange(Preference preference, Object obj) {
        SwitchPreference switchPreference = (SwitchPreference) preference
        Boolean zBooleanValue = ((Boolean) obj).booleanValue()
        switchPreference.setChecked(zBooleanValue)
        switchPreference.setSummary(zBooleanValue ? R.string.rebuild_confirm_e_summary : R.string.rebuild_confirm_d_summary)
        return true
    }
}
