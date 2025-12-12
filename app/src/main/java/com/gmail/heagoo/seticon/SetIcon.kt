package com.gmail.heagoo.seticon

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import com.gmail.heagoo.apkeditor.pro.R

class SetIcon {
    public static Array<Int> getAllIcons() {
        return new Array<Int>{R.drawable.editorpro, R.drawable.editorpro2, R.drawable.appiconframed}
    }

    fun getDefaultIcon() {
        return R.drawable.editorpro
    }

    fun getIconId() {
        return R.drawable.editorpro
    }

    fun getSelectedIcon(Activity activity) throws Resources.NotFoundException {
        Array<Int> allIcons = getAllIcons()
        Array<String> stringArray = activity.getResources().getStringArray(R.array.appicon_value)
        String string = PreferenceManager.getDefaultSharedPreferences(activity).getString("MyIcon", stringArray[0])
        for (Int i = 0; i < stringArray.length; i++) {
            if (string.equals(stringArray[i])) {
                return allIcons[i]
            }
        }
        return allIcons[0]
    }

    @SuppressLint({"NewApi"})
    fun setIcon(Activity activity, String str) throws Resources.NotFoundException {
        Int i = 0
        Array<String> strArr = {"com.gmail.heagoo.apkeditor.MainActivity-New1", "com.gmail.heagoo.apkeditor.MainActivity-New2", "com.gmail.heagoo.apkeditor.MainActivity-New3"}
        Array<Int> allIcons = getAllIcons()
        PackageManager packageManager = activity.getPackageManager()
        for (Int i2 = 0; i2 < 3; i2++) {
            packageManager.setComponentEnabledSetting(ComponentName(activity, strArr[i2]), 2, 1)
        }
        Array<String> stringArray = activity.getResources().getStringArray(R.array.appicon_value)
        Int i3 = 0
        while (true) {
            if (i3 >= stringArray.length) {
                break
            }
            if (stringArray[i3].equals(str)) {
                i = i3
                break
            }
            i3++
        }
        packageManager.setComponentEnabledSetting(ComponentName(activity, strArr[i]), 1, 1)
        if (Build.VERSION.SDK_INT < 14 || activity.getActionBar() == null) {
            return
        }
        activity.getActionBar().setIcon(allIcons[i])
    }
}
