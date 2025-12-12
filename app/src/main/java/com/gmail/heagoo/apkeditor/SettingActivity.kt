package com.gmail.heagoo.apkeditor

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.os.Environment
import android.preference.EditTextPreference
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import android.preference.SwitchPreference
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.Arrays
import java.util.HashSet
import java.util.Set

class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener, View.OnLongClickListener {
    private fun AB() {
        ActionBar actionBar = getActionBar()
        View viewInflate = getLayoutInflater().inflate(R.layout.mtrl_toolbar, (ViewGroup) null)
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(-2, -1, 17)
        TextView textView = (TextView) viewInflate.findViewById(android.R.id.title)
        textView.setText(R.string.action_sett)
        textView.setOnLongClickListener(this)
        actionBar.setCustomView(viewInflate, layoutParams)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun D1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_title)
        builder.setMessage(R.string.confirm_message_str)
        builder.setPositiveButton(R.string.yes, na(this))
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null)
        builder.show()
    }

    private fun D2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_title)
        builder.setMessage(R.string.confirm_message_res)
        builder.setPositiveButton(R.string.yes, nb(this))
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null)
        builder.show()
    }

    private fun D3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_title)
        builder.setMessage(R.string.confirm_message_mf)
        builder.setPositiveButton(R.string.yes, nc(this))
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null)
        builder.show()
    }

    private fun D4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_title)
        builder.setMessage(R.string.confirm_message_clean)
        builder.setPositiveButton(R.string.yes, nd(this))
        builder.setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null)
        builder.show()
    }

    private fun a(String str) {
        return "0".equals(str) ? R.string.summary_decode_all_files : "1".equals(str) ? R.string.summary_decode_partial_files : R.string.summary_decide_at_decoding
    }

    fun a(Context context) throws Resources.NotFoundException {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("AppListOrder", "")
        Array<String> stringArray = context.getResources().getStringArray(R.array.order_value)
        for (String str : stringArray) {
            if (string.equals(str)) {
                return string
            }
        }
        return stringArray[0]
    }

    private fun a(File file) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    a(file2)
                    file2.delete()
                } else {
                    file2.delete()
                }
            }
        }
    }

    fun b(Context context) {
        Int iIntValue
        try {
            iIntValue = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString("CompressionLevel", "9")).intValue()
        } catch (Exception e) {
            iIntValue = 9
        }
        if (iIntValue < 0) {
            return 0
        }
        if (iIntValue <= 9) {
            return iIntValue
        }
        return 9
    }

    private fun b(String str) {
        File file = File(str)
        if (!file.exists() || !file.isDirectory()) {
            return false
        }
        File file2 = File(file, com.gmail.heagoo.common.s.a(8))
        Boolean zMkdir = file2.mkdir()
        if (!zMkdir) {
            return zMkdir
        }
        file2.delete()
        return zMkdir
    }

    fun c(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("SignApkWith", "testkey")
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private fun changeSignatureVersion(Set<String> set) {
        Array<Boolean> zArr = new Boolean[4]
        if (set != null) {
            for (String str : set) {
                Char c = 65535
                switch (str.hashCode()) {
                    case android.support.v7.appcompat.R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
                        if (str.equals("1")) {
                            c = 0
                            break
                        }
                        break
                    case 50:
                        if (str.equals("2")) {
                            c = 1
                            break
                        }
                        break
                    case android.support.v7.appcompat.R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
                        if (str.equals("3")) {
                            c = 2
                            break
                        }
                        break
                    case android.support.v7.appcompat.R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
                        if (str.equals("4")) {
                            c = 3
                            break
                        }
                        break
                }
                if (c == 0) {
                    zArr[0] = true
                } else if (c == 1) {
                    zArr[1] = true
                } else if (c == 2) {
                    zArr[2] = true
                } else if (c == 3) {
                    zArr[3] = true
                }
            }
        }
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < 4; i++) {
            if (zArr[i]) {
                sb.append("v")
                sb.append(i + 1)
                sb.append(" ")
            }
        }
        return sb.toString()
    }

    fun d(Context context) {
        try {
            return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString("OutputApkName", "2")).intValue()
        } catch (Exception e) {
            return 2
        }
    }

    fun e(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("SmaliEditingEnabled", true)
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static Array<Boolean> enabledSignatureVersions(Context context) {
        Array<Boolean> zArr = new Boolean[4]
        for (String str : PreferenceManager.getDefaultSharedPreferences(context).getStringSet("SignatureVersion", HashSet(Arrays.asList("1", "2")))) {
            Char c = 65535
            switch (str.hashCode()) {
                case android.support.v7.appcompat.R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 49 */:
                    if (str.equals("1")) {
                        c = 0
                        break
                    }
                    break
                case 50:
                    if (str.equals("2")) {
                        c = 1
                        break
                    }
                    break
                case android.support.v7.appcompat.R.styleable.AppCompatTheme_actionButtonStyle /* 51 */:
                    if (str.equals("3")) {
                        c = 2
                        break
                    }
                    break
                case android.support.v7.appcompat.R.styleable.AppCompatTheme_buttonBarStyle /* 52 */:
                    if (str.equals("4")) {
                        c = 3
                        break
                    }
                    break
            }
            if (c == 0) {
                zArr[0] = true
            } else if (c == 1) {
                zArr[1] = true
            } else if (c == 2) {
                zArr[2] = true
            } else if (c == 3) {
                zArr[3] = true
            }
        }
        if (zArr[3] && (!zArr[2] || !zArr[1])) {
            zArr[2] = true
        }
        return zArr
    }

    fun extEditor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("ExternalEditor", false)
    }

    fun f(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("RebuildConfirmation", false)
    }

    fun g(Context context) {
        try {
            return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString("FileRenameOption", "1")).intValue()
        } catch (Exception e) {
            return -1
        }
    }

    fun h(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("DecodeDirectory", null)
        if (string != null) {
            if (string.endsWith("/")) {
                string = string.substring(0, string.length() - 1)
            }
            if (b(string)) {
                return string
            }
        }
        return null
    }

    fun i(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("DecodeMode", "2")
        return (string.equals("0") || string.equals("1") || string.equals("2")) ? string : "2"
    }

    fun lang(SettingActivity settingActivity) {
        com.gmail.heagoo.common.e.l(settingActivity)
    }

    fun smaliApiLevel(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("SmaliApiLevel", "15")
        return (string == null || "".equals(string.trim())) ? Integer.parseInt("15") : Integer.parseInt(string)
    }

    protected final Unit a() {
        Boolean z
        File file = File(Environment.getExternalStorageDirectory().getPath() + "/ApkEditor")
        if (file.exists() && file.isDirectory()) {
            Array<String> strArr = {"backups", ".projects"}
            Array<File> fileArrListFiles = file.listFiles()
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.isDirectory()) {
                        String name = file2.getName()
                        Int i = 0
                        while (true) {
                            if (i >= 2) {
                                z = false
                                break
                            } else {
                                if (name.equals(strArr[i])) {
                                    z = true
                                    break
                                }
                                i++
                            }
                        }
                        if (!z) {
                            a(file2)
                            file2.delete()
                        }
                    } else {
                        file2.delete()
                    }
                }
            }
        }
    }

    fun d1() {
        SharedPreferences.Editor editorEdit = getSharedPreferences("com.gmail.heagoo.apkeditor.pro_preferences", 0).edit()
        editorEdit.putString("string_keywords", "")
        editorEdit.apply()
        Toast.makeText(this, R.string.toast_clean_str, 0).show()
    }

    fun d2() {
        SharedPreferences.Editor editorEdit = getSharedPreferences("com.gmail.heagoo.apkeditor.pro_preferences", 0).edit()
        editorEdit.putString("res_keywords", "")
        editorEdit.apply()
        Toast.makeText(this, R.string.toast_clean_res, 0).show()
    }

    fun d3() {
        SharedPreferences.Editor editorEdit = getSharedPreferences("com.gmail.heagoo.apkeditor.pro_preferences", 0).edit()
        editorEdit.putString("mf_keywords", "")
        editorEdit.apply()
        Toast.makeText(this, R.string.toast_clean_mf, 0).show()
    }

    fun d4() {
        ey(this, gj(this, getFilesDir().getAbsolutePath()), R.string.temp_file_cleaned).show()
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected fun onCreate(Bundle bundle) {
        setTheme(a.a.b.a.k.md(a.a.b.a.k.a(this)))
        super.onCreate(bundle)
        lang(this)
        addPreferencesFromResource(R.xml.settings)
        AB()
        PreferenceManager preferenceManager = getPreferenceManager()
        ListPreference listPreference = (ListPreference) preferenceManager.findPreference("DecodeMode")
        listPreference.setSummary(a(i(this)))
        listPreference.setOnPreferenceChangeListener(this)
        ListPreference listPreference2 = (ListPreference) preferenceManager.findPreference("AppListOrder")
        String string = PreferenceManager.getDefaultSharedPreferences(this).getString("AppListOrder", getResources().getStringArray(R.array.order_value)[0])
        listPreference2.setValue(string)
        listPreference2.setSummary(string)
        listPreference2.setOnPreferenceChangeListener(this)
        ListPreference listPreference3 = (ListPreference) preferenceManager.findPreference("SignApkWith")
        listPreference3.setValue(PreferenceManager.getDefaultSharedPreferences(this).getString("SignApkWith", "testkey"))
        listPreference3.setOnPreferenceChangeListener(this)
        ListPreference listPreference4 = (ListPreference) preferenceManager.findPreference("OutputApkName")
        listPreference4.setValue(PreferenceManager.getDefaultSharedPreferences(this).getString("OutputApkName", "2"))
        listPreference4.setOnPreferenceChangeListener(this)
        ListPreference listPreference5 = (ListPreference) findPreference("md_lang")
        listPreference5.setValue(PreferenceManager.getDefaultSharedPreferences(this).getString("md_lang", ""))
        listPreference5.setOnPreferenceChangeListener(this)
        SwitchPreference switchPreference = (SwitchPreference) findPreference("SmaliEditingEnabled")
        Boolean zE = e(this)
        switchPreference.setChecked(zE)
        switchPreference.setSummary(zE ? R.string.smali_edit_summary : R.string.smali_edit_disabled_summary)
        switchPreference.setOnPreferenceChangeListener(gh(this))
        SwitchPreference switchPreference2 = (SwitchPreference) findPreference("RebuildConfirmation")
        Boolean zF = f(this)
        switchPreference2.setChecked(zF)
        switchPreference2.setSummary(zF ? R.string.rebuild_confirm_e_summary : R.string.rebuild_confirm_d_summary)
        switchPreference2.setOnPreferenceChangeListener(gi(this))
        findPreference("clean_str").setOnPreferenceClickListener(this)
        findPreference("clean_res").setOnPreferenceClickListener(this)
        findPreference("clean_mf").setOnPreferenceClickListener(this)
        findPreference("CleanGarbage").setOnPreferenceClickListener(this)
        Preference preferenceFindPreference = findPreference("md_theme")
        preferenceFindPreference.setSummary(a.a.b.a.k.mdNames(a.a.b.a.k.a(this)))
        preferenceFindPreference.setOnPreferenceClickListener(this)
        ((ListPreference) preferenceManager.findPreference("FileRenameOption")).setValue(StringBuilder().append(g(this)).toString())
        EditTextPreference editTextPreference = (EditTextPreference) preferenceManager.findPreference("DecodeDirectory")
        editTextPreference.setOnPreferenceChangeListener(this)
        String strH = h(this)
        if (strH != null) {
            editTextPreference.setSummary(strH)
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        Preference preferenceFindPreference2 = findPreference("SignatureVersion")
        preferenceFindPreference2.setSummary(changeSignatureVersion(defaultSharedPreferences.getStringSet("SignatureVersion", null)))
        preferenceFindPreference2.setOnPreferenceChangeListener(this)
        Preference preferenceFindPreference3 = findPreference("SmaliApiLevel")
        String string2 = getString(R.string.smali_api_summary)
        String string3 = defaultSharedPreferences.getString("SmaliApiLevel", string2)
        if (string3 != null && !"".equals(string3)) {
            string2 = string3
        }
        preferenceFindPreference3.setSummary(string2)
        preferenceFindPreference3.setOnPreferenceChangeListener(this)
        SwitchPreference switchPreference3 = (SwitchPreference) findPreference("ExternalEditor")
        Boolean zExtEditor = extEditor(this)
        switchPreference3.setChecked(zExtEditor)
        switchPreference3.setSummary(zExtEditor ? R.string.external_editor_e_summary : R.string.external_editor_d_summary)
        switchPreference3.setOnPreferenceChangeListener(ExtEditor(this))
    }

    @Override // android.view.View.OnLongClickListener
    public final Boolean onLongClick(View view) {
        switch (view.getId()) {
            case android.R.id.title:
                startActivity(Intent(this, (Class<?>) SettingHideActivity.class))
                return true
            default:
                return false
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    fun onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish()
                return true
            default:
                return false
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    fun onPreferenceChange(Preference preference, Object obj) {
        Boolean z = false
        String key = preference.getKey()
        if ("AppListOrder".equals(key)) {
            String str = (String) obj
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this).edit()
            editorEdit.putString("AppListOrder", str)
            editorEdit.apply()
            preference.setSummary(str)
        } else if ("md_lang".equals(key)) {
            a.a.b.a.k.md = true
            finish()
            overridePendingTransition(R.anim.abc_fade_in_out, R.anim.abc_fade_out)
            startActivity(Intent(this, (Class<?>) SettingActivity.class))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_in_out)
        } else if ("DecodeDirectory".equals(key)) {
            String str2 = (String) obj
            if (str2 != null && !"".equals(str2.trim())) {
                if (b(str2)) {
                    preference.setSummary(str2)
                    z = true
                } else {
                    Toast.makeText(this, String.format(getString(R.string.invalid_directory), str2), 1).show()
                }
            }
            if (!z) {
                preference.setSummary(R.string.decode_dir_summary_may_change)
            }
        } else if ("DecodeMode".equals(key)) {
            preference.setSummary(a((String) obj))
        } else if ("SignatureVersion".equals(key)) {
            preference.setSummary(changeSignatureVersion((Set) obj))
        } else if ("SmaliApiLevel".equals(key)) {
            String str3 = (String) obj
            if (str3 == null || "".equals(str3.trim())) {
                preference.setSummary(R.string.smali_api_summary)
            } else {
                preference.setSummary(String.valueOf(obj))
            }
        }
        return true
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    fun onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("clean_str")) {
            D1()
        } else if (preference.getKey().equals("clean_res")) {
            D2()
        } else if (preference.getKey().equals("clean_mf")) {
            D3()
        } else if (preference.getKey().equals("CleanGarbage")) {
            D4()
        } else if (preference.getKey().equals("md_theme")) {
            a.a.b.a.k.md = true
            new a.a.b.a.k().f(this, R.string.theme_title, jc(this))
        }
        return false
    }

    @Override // android.app.Activity
    protected fun onResume() {
        super.onResume()
        ImageView imageView = (ImageView) findViewById(R.id.title_icon)
        if (imageView != null) {
            imageView.setImageResource(((Integer) a.a("com.gmail.heagoo.seticon.SetIcon", "getSelectedIcon", new Array<Class>{Activity.class}, new Array<Object>{this})).intValue())
        }
    }
}
