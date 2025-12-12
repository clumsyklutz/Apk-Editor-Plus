package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.support.v4.view.PointerIconCompat
import android.support.v7.widget.ActivityChooserView
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.apkeditor.ac.a
import com.gmail.heagoo.apkeditor.pro.R
import com.gmail.heagoo.apkeditor.translate.PossibleLanguages
import com.gmail.heagoo.apkeditor.translate.TranslateItem
import com.gmail.heagoo.pngeditor.PngEditActivity
import common.types.ActivityState_V1
import common.types.ProjectInfo_V1
import common.types.StringItem
import jadx.core.deobf.Deobfuscator
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Locale
import java.util.Map
import java.util.Set
import java.util.Stack

class ApkInfoActivity extends Activity implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, bf, cz, com.gmail.heagoo.apkeditor.f.b, fn, com.gmail.heagoo.apkeditor.ui.b {
    private ListView A
    private gv B
    private Map C
    private ArrayList E
    private LinearLayout F
    private ListView G
    private View I
    private View J
    private HorizontalScrollView K
    private LinearLayout L
    private View M
    private TextView N
    private LinearLayout O
    private ListView P
    private dt Q
    private LinearLayout R
    private View S
    private View U
    private View V
    private bg W

    /* renamed from: a, reason: collision with root package name */
    protected String f757a
    private String aA
    private gu aB
    private String aC
    private String aD
    private String aE
    private Long aF
    private View ad
    private Button ae
    private ProgressBar af
    private View ag
    private TextView ah
    private TextView ai
    private HashMap ak
    private HashMap al
    private ArrayList am
    private ArrayList an
    private Boolean ao
    private String ar
    private Map as
    private Map at
    private Set au
    private a av
    private a aw
    private a ax
    private cy ay
    private String az

    /* renamed from: b, reason: collision with root package name */
    protected String f758b
    com.gmail.heagoo.common.b c
    HashMap d
    ff e
    protected fl f
    protected ImageView g
    protected ImageView h
    protected Map k
    protected Int l
    protected Boolean m
    private ImageView n
    private TextView o
    private TextView p
    private RadioButton q
    private RadioButton r
    private RadioButton s
    private LinearLayout z
    private String D = null
    private Stack H = Stack()
    private Boolean X = false
    private Boolean Y = false
    protected Boolean i = true
    protected Boolean j = true
    private Boolean Z = false
    private Boolean aa = false
    private Int ab = 0
    private Boolean aj = false
    private Boolean ap = false
    private Int aq = 0

    public static a.a.b.a.c a(Set set) {
        HashSet hashSet = HashSet()
        Iterator it = set.iterator()
        while (it.hasNext()) {
            hashSet.add(((a.a.b.a.c) it.next()).a())
        }
        String strC = c(hashSet)
        Iterator it2 = set.iterator()
        while (it2.hasNext()) {
            a.a.b.a.c cVar = (a.a.b.a.c) it2.next()
            if (cVar.a().equals(strC)) {
                return cVar
            }
        }
        return null
    }

    static /* synthetic */ bg a(ApkInfoActivity apkInfoActivity, bg bgVar) {
        apkInfoActivity.W = null
        return null
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(String str, Boolean z, File file) {
        ActivityState_V1 activityState_V1 = ActivityState_V1()
        Map mapA = this.B.a()
        if (mapA != null && !mapA.isEmpty()) {
            this.X = true
            String str2 = str + "changedStringValues"
            com.gmail.heagoo.a.c.a.a(str2, mapA)
            activityState_V1.putString("changedStringValues_file", str2)
        }
        if (!this.m && this.e != null) {
            activityState_V1.putString("res_current_dir", this.e.a((List) null))
            Map mapB = this.e.b()
            Map mapC = this.e.c()
            Set setD = this.e.d()
            if (file != null) {
                a(mapB, mapC, file)
            }
            if (mapB != null && !mapB.isEmpty()) {
                String str3 = str + "res_added"
                com.gmail.heagoo.a.c.a.a(str3, mapB)
                activityState_V1.putString("res_added_file", str3)
            }
            if (mapC != null && !mapC.isEmpty()) {
                String str4 = str + "res_replaced"
                com.gmail.heagoo.a.c.a.a(str4, mapC)
                activityState_V1.putString("res_replaced_file", str4)
            }
            if (setD != null && !setD.isEmpty()) {
                String str5 = str + "res_deleted"
                com.gmail.heagoo.a.c.a.a(str5, setD)
                activityState_V1.putString("res_deleted_file", str5)
            }
        }
        String str6 = str + "allStringValues"
        if (z) {
            com.gmail.heagoo.a.c.a.a(str6, this.d)
        }
        activityState_V1.putString("allStringValues_file", str6)
        String str7 = str + "fileEntry2ZipEntry"
        if (z && !this.m) {
            com.gmail.heagoo.a.c.a.a(str7, this.k)
        }
        activityState_V1.putString("fileEntry2ZipEntry_file", str7)
        activityState_V1.putString("curConfig", this.D.toString())
        activityState_V1.putSerializable("langConfigList", this.E)
        activityState_V1.putBoolean("stringModified", this.X)
        activityState_V1.putBoolean("manifestModified", this.Y)
        activityState_V1.putBoolean("stringParsed", this.Z)
        activityState_V1.putBoolean("resourceParsed", this.aa)
        activityState_V1.putBoolean("bStringPrepared", this.ap)
        activityState_V1.putBoolean("searchTextContent", this.i)
        activityState_V1.putBoolean("searchResSensitive", this.j)
        activityState_V1.putInt("curSelectedRadio", this.ab)
        activityState_V1.putInt("rotateClickedTimes", this.aq)
        activityState_V1.putBoolean("dex2smaliClicked", this.aj)
        activityState_V1.putString("savedParam_extraStr", this.aA)
        activityState_V1.putString("savedParam_filePath", this.az)
        activityState_V1.putBoolean("isFullDecoding", this.m)
        return activityState_V1
    }

    fun a(String str) {
        String str2 = str + "/.prj_version"
        String str3 = str + "/ae.prj"
        File file = File(str2)
        File file2 = File(str3)
        if (file.exists() && file2.exists()) {
            try {
                switch (Integer.valueOf(new com.gmail.heagoo.common.w(str2).b()).intValue()) {
                    case 1:
                        return (ProjectInfo_V1) com.gmail.heagoo.a.c.a.j(str3)
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
            e.printStackTrace()
        }
        return null
    }

    fun a(String str, String str2, String str3) {
        if (str2.endsWith("/")) {
            str2 = str2.substring(0, str2.length() - 1)
        }
        String str4 = str2 + "/" + str3 + ".apk"
        Int iLastIndexOf = str.lastIndexOf(47)
        String strSubstring = str.substring(0, iLastIndexOf)
        String strSubstring2 = str.substring(iLastIndexOf + 1)
        if (!strSubstring.equals(str2) || !strSubstring2.startsWith(str3)) {
            return str4
        }
        String strSubstring3 = strSubstring2.substring(str3.length())
        if (".apk".equals(strSubstring3)) {
            return str2 + "/" + str3 + "2.apk"
        }
        if (!strSubstring3.matches("[1-9][0-9]*\\.apk")) {
            return str4
        }
        try {
            return str2 + "/" + str3 + (Integer.valueOf(strSubstring3.substring(0, strSubstring3.length() - 4)).intValue() + 1) + ".apk"
        } catch (Exception e) {
            return str4
        }
    }

    fun a(String str, Set set) {
        if ((str.startsWith("smali/") || str.startsWith("smali_")) && str.endsWith(".smali")) {
            Int iIndexOf = str.indexOf(47)
            strSubstring = iIndexOf != -1 ? str.substring(0, iIndexOf) : null
            set.add(strSubstring)
        }
        return strSubstring
    }

    private fun a(Bundle bundle) {
        ey(this, q(this, bundle), -1).show()
    }

    static /* synthetic */ Unit a(ApkInfoActivity apkInfoActivity) {
        apkInfoActivity.s()
        apkInfoActivity.a()
        apkInfoActivity.x()
        if (apkInfoActivity.aj) {
            apkInfoActivity.ad.setVisibility(8)
        }
        apkInfoActivity.n()
        apkInfoActivity.S.setVisibility(0)
        apkInfoActivity.U.setVisibility(0)
        apkInfoActivity.V.setVisibility(0)
    }

    static /* synthetic */ Unit a(ApkInfoActivity apkInfoActivity, Bundle bundle) {
        apkInfoActivity.d = (HashMap) com.gmail.heagoo.a.c.a.j(bundle.getString("allStringValues_file"))
        String string = bundle.getString("changedStringValues_file")
        if (string != null) {
            apkInfoActivity.C = (HashMap) com.gmail.heagoo.a.c.a.j(string)
            HashMap map = apkInfoActivity.d
            for (Map.Entry entry : apkInfoActivity.C.entrySet()) {
                String str = (String) entry.getKey()
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    Iterator it = ((ArrayList) map.get(str)).iterator()
                    while (true) {
                        if (it.hasNext()) {
                            StringItem stringItem = (StringItem) it.next()
                            if (((String) entry2.getKey()).equals(stringItem.name)) {
                                stringItem.value = (String) entry2.getValue()
                                break
                            }
                        }
                    }
                }
            }
        }
        apkInfoActivity.k = (HashMap) com.gmail.heagoo.a.c.a.j(bundle.getString("fileEntry2ZipEntry_file"))
        apkInfoActivity.D = bundle.getString("curConfig")
        apkInfoActivity.E = (ArrayList) bundle.getSerializable("langConfigList")
        apkInfoActivity.X = bundle.getBoolean("stringModified")
        apkInfoActivity.Y = bundle.getBoolean("manifestModified")
        apkInfoActivity.Z = bundle.getBoolean("stringParsed")
        apkInfoActivity.aa = bundle.getBoolean("resourceParsed")
        apkInfoActivity.ap = bundle.getBoolean("bStringPrepared")
        apkInfoActivity.i = bundle.getBoolean("searchTextContent")
        apkInfoActivity.j = bundle.getBoolean("searchResSensitive")
        apkInfoActivity.ab = bundle.getInt("curSelectedRadio")
        apkInfoActivity.aq = bundle.getInt("rotateClickedTimes")
        apkInfoActivity.ar = bundle.getString("res_current_dir")
        String string2 = bundle.getString("res_added_file")
        if (string2 != null) {
            apkInfoActivity.as = (Map) com.gmail.heagoo.a.c.a.j(string2)
        }
        String string3 = bundle.getString("res_replaced_file")
        if (string3 != null) {
            apkInfoActivity.at = (Map) com.gmail.heagoo.a.c.a.j(string3)
        }
        String string4 = bundle.getString("res_deleted_file")
        if (string4 != null) {
            apkInfoActivity.au = (Set) com.gmail.heagoo.a.c.a.j(string4)
        }
        apkInfoActivity.aj = bundle.getBoolean("dex2smaliClicked")
        apkInfoActivity.aA = bundle.getString("savedParam_extraStr")
        apkInfoActivity.az = bundle.getString("savedParam_filePath")
        apkInfoActivity.m = bundle.getBoolean("isFullDecoding")
    }

    private fun a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(String str, List list) throws IllegalStateException, IOException, IllegalArgumentException {
        b(str, list)
        this.X = true
        String str2 = null
        for (Map.Entry entry : this.d.entrySet()) {
            String str3 = (String) entry.getKey()
            if (str3.equals(str)) {
                ArrayList arrayList = (ArrayList) entry.getValue()
                arrayList.clear()
                arrayList.addAll(list)
            } else {
                str3 = str2
            }
            str2 = str3
        }
        if (str2 == null) {
            ArrayList arrayList2 = ArrayList()
            arrayList2.addAll(list)
            this.d.put(str, arrayList2)
        }
        w()
    }

    private fun a(String str, List list, List list2) throws IOException {
        try {
            FileInputStream fileInputStream = FileInputStream(this.f758b + "/AndroidManifest.xml")
            BufferedReader bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
            String line = bufferedReader.readLine()
            Int i = 1
            while (line != null) {
                if (line.contains(str)) {
                    list.add(Integer.valueOf(i))
                    list2.add(line)
                }
                line = bufferedReader.readLine()
                i++
            }
            bufferedReader.close()
            fileInputStream.close()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    private fun a(ArrayList arrayList) {
        this.B.a(this.D, arrayList)
    }

    private fun a(Map map, Map map2, File file) {
        try {
            String strD = com.gmail.heagoo.a.c.a.d(this, "tmp")
            if (map != null && !map.isEmpty()) {
                for (Map.Entry entry : map.entrySet()) {
                    String str = (String) entry.getValue()
                    if (str != null && str.startsWith(strD)) {
                        File file2 = File(str)
                        File file3 = File(file, file2.getName())
                        if (file2.renameTo(file3)) {
                            entry.setValue(file3.getPath())
                        }
                    }
                }
            }
            if (map2 == null || map2.isEmpty()) {
                return
            }
            for (Map.Entry entry2 : map2.entrySet()) {
                String str2 = (String) entry2.getValue()
                if (str2 != null && str2.startsWith(strD)) {
                    File file4 = File(str2)
                    File file5 = File(file, file4.getName())
                    if (file4.renameTo(file5)) {
                        entry2.setValue(file5.getPath())
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private fun a(Context context) {
        Intent intent = Intent("android.intent.action.VIEW")
        intent.addCategory("android.intent.category.DEFAULT")
        intent.setDataAndType(null, "application/com.gmail.heagoo.apkeditor-translate")
        return context.getPackageManager().queryIntentActivities(intent, 0).size() > 0
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(String str, ProjectInfo_V1 projectInfo_V1) {
        String str2 = str + "/ae.prj"
        try {
            com.gmail.heagoo.a.c.a.b(str + "/.prj_version", "1")
            return com.gmail.heagoo.a.c.a.a(str2, projectInfo_V1)
        } catch (IOException e) {
            e.printStackTrace()
            return false
        }
    }

    private fun b(Map map) throws IOException {
        try {
            String str = com.gmail.heagoo.a.c.a.d(this, "tmp") + com.gmail.heagoo.common.s.a(8)
            BufferedOutputStream bufferedOutputStream = BufferedOutputStream(FileOutputStream(str))
            for (Map.Entry entry : map.entrySet()) {
                bufferedOutputStream.write(((String) entry.getKey()).getBytes())
                bufferedOutputStream.write(10)
                bufferedOutputStream.write(((String) entry.getValue()).getBytes())
                bufferedOutputStream.write(10)
            }
            bufferedOutputStream.close()
            return str
        } catch (Exception e) {
            return null
        }
    }

    private fun b(String str, List list) throws IllegalStateException, IOException, IllegalArgumentException {
        a.d.h hVar = new a.d.h()
        String str2 = this.f758b + "/res/values" + str
        File file = File(str2)
        if (!file.exists()) {
            file.mkdirs()
        }
        FileOutputStream fileOutputStream = FileOutputStream(File(str2 + "/strings.xml"))
        OutputStreamWriter outputStreamWriter = OutputStreamWriter(fileOutputStream)
        hVar.setOutput(outputStreamWriter)
        outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        outputStreamWriter.write("<resources>\n")
        Iterator it = list.iterator()
        while (it.hasNext()) {
            StringItem stringItem = (StringItem) it.next()
            hVar.startTag(null, "string")
            hVar.attribute(null, "name", stringItem.name)
            if (com.gmail.heagoo.a.c.a.d(stringItem.value)) {
                hVar.attribute(null, "formatted", "false")
            }
            hVar.ignorableWhitespace((stringItem.value.startsWith("@string/") || stringItem.value.startsWith("@android:string/")) ? stringItem.value : stringItem.styledValue == null ? com.gmail.heagoo.a.c.a.c(com.gmail.heagoo.a.c.a.a(stringItem.value)) : com.gmail.heagoo.a.c.a.c(stringItem.styledValue))
            hVar.endTag(null, "string")
            hVar.flush()
            outputStreamWriter.write("\n")
        }
        outputStreamWriter.write("</resources>\n")
        outputStreamWriter.close()
        fileOutputStream.close()
    }

    private fun b(String str, List list, List list2) {
        ArrayList arrayList = null
        ArrayList arrayList2 = null
        for (Map.Entry entry : this.d.entrySet()) {
            String str2 = (String) entry.getKey()
            if (str2.equals(str)) {
                arrayList = (ArrayList) entry.getValue()
            } else {
                arrayList2 = "".equals(str2) ? (ArrayList) entry.getValue() : arrayList2
            }
        }
        if (arrayList2 == null) {
            if (arrayList != null) {
                Iterator it = arrayList.iterator()
                while (it.hasNext()) {
                    StringItem stringItem = (StringItem) it.next()
                    list.add(TranslateItem(stringItem.name, "", stringItem.value))
                }
                return
            }
            return
        }
        HashMap map = HashMap()
        HashMap map2 = HashMap()
        if (arrayList != null) {
            Iterator it2 = arrayList.iterator()
            while (it2.hasNext()) {
                StringItem stringItem2 = (StringItem) it2.next()
                map.put(stringItem2.name, stringItem2.value)
            }
        }
        Iterator it3 = arrayList2.iterator()
        while (it3.hasNext()) {
            StringItem stringItem3 = (StringItem) it3.next()
            map2.put(stringItem3.name, stringItem3.value)
        }
        if (arrayList != null) {
            Iterator it4 = arrayList.iterator()
            while (it4.hasNext()) {
                StringItem stringItem4 = (StringItem) it4.next()
                String str3 = stringItem4.name
                list.add(TranslateItem(str3, (String) map2.get(str3), stringItem4.value))
            }
        }
        Iterator it5 = arrayList2.iterator()
        while (it5.hasNext()) {
            StringItem stringItem5 = (StringItem) it5.next()
            String str4 = stringItem5.name
            if (((String) map.get(str4)) == null) {
                list2.add(TranslateItem(str4, stringItem5.value, null))
            }
        }
    }

    private fun b(List list) {
        cn(this, ak(this, list), null, null, getString(R.string.select_folder), true, false, false, null).show()
    }

    fun b(String str) {
        return str.endsWith(".jpg") || (str.endsWith(".png") && !str.endsWith(".9.png"))
    }

    private fun c(Set set) {
        String str = null
        Locale locale = Locale.getDefault()
        String str2 = "-" + locale.getLanguage()
        String str3 = str2 + "-r" + locale.getCountry()
        StringBuilder("*****realQualifier=").append(str3)
        Iterator it = set.iterator()
        while (true) {
            String str4 = str
            if (!it.hasNext()) {
                return str4
            }
            str = (String) it.next()
            if (str3.equals(str)) {
                return str
            }
            if (!str2.equals(str)) {
                if (!str.equals("") || str4 != null) {
                    str = str4
                }
            }
        }
    }

    private fun c(String str, String str2) {
        String strSubstring
        Array<String> strArrSplit = str.split("/")
        Int iLastIndexOf = strArrSplit[strArrSplit.length - 1].lastIndexOf(46)
        if (iLastIndexOf != -1) {
            strSubstring = str.substring(0, str.length() - (strArrSplit[strArrSplit.length - 1].length() - iLastIndexOf))
        } else {
            strSubstring = str
        }
        if (strSubstring != null && strSubstring.endsWith("/APKEDITOR.xcrhfvke")) {
            File file = File(str)
            String str3 = strSubstring.substring(0, strSubstring.length() - 18) + com.gmail.heagoo.common.s.a(8)
            if (file.renameTo(File(str3))) {
                str = str3
            } else {
                Log.w("DEBUG", "file rename error.")
            }
        }
        a(str, str2)
    }

    private fun d(Boolean z) {
        Boolean z2
        Iterator<ActivityManager.RunningServiceInfo> it = ((ActivityManager) getSystemService("activity")).getRunningServices(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED).iterator()
        while (true) {
            if (!it.hasNext()) {
                z2 = false
                break
            }
            if (ApkComposeService.class.getName().equals(it.next().service.getClassName())) {
                z2 = true
                break
            }
        }
        if (z2) {
            bindService(Intent(this, (Class<?>) ApkComposeService.class), au(this, z), 1)
        } else {
            e(z)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun e(Boolean z) {
        String str
        String str2 = Environment.getExternalStorageDirectory().getPath() + "/ApkEditor"
        File file = File(str2)
        if (!file.exists()) {
            file.mkdir()
        }
        switch (SettingActivity.d(this)) {
            case 0:
                if (!z) {
                    str = this.c.f1448b + "_unsigned"
                    break
                } else {
                    str = this.c.f1448b + "_signed"
                    break
                }
            case 1:
            default:
                if (!z) {
                    str = "gen_unsigned"
                    break
                } else {
                    str = "gen_signed"
                    break
                }
            case 2:
                if (!z) {
                    str = this.c.f1447a + "_unsigned"
                    break
                } else {
                    str = this.c.f1447a + "_signed"
                    break
                }
        }
        String strA = a(this.f757a, str2, com.gmail.heagoo.common.h.a(str))
        if (File(strA).exists() && SettingActivity.g(this) == 0) {
            strA = ce.a(strA, false).getPath()
        }
        Intent intent = Intent(this, (Class<?>) ApkComposeService.class)
        com.gmail.heagoo.a.c.a.a(intent, "decodeRootPath", this.f758b)
        if (!this.m) {
            com.gmail.heagoo.a.c.a.a(intent, "srcApkPath", this.f757a)
        }
        com.gmail.heagoo.a.c.a.a(intent, "targetApkPath", strA)
        com.gmail.heagoo.a.c.a.a(intent, "stringModified", this.X ? "true" : "false")
        com.gmail.heagoo.a.c.a.a(intent, "manifestModified", this.Y ? "true" : "false")
        com.gmail.heagoo.a.c.a.a(intent, "resFileModified", this.ao ? "true" : "false")
        com.gmail.heagoo.a.c.a.a(intent, "modifiedSmaliFolders", this.an)
        com.gmail.heagoo.a.c.a.a(intent, "addedFiles", this.ak)
        com.gmail.heagoo.a.c.a.a(intent, "deletedFiles", this.am)
        com.gmail.heagoo.a.c.a.a(intent, "replacedFiles", this.al)
        com.gmail.heagoo.a.c.a.b(intent, "signAPK", z)
        com.gmail.heagoo.a.c.a.a(intent, "fileEntry2ZipEntry", b(this.k))
        startService(intent)
        startActivityForResult(Intent(this, (Class<?>) ApkComposeActivity.class), 1)
    }

    private fun extEditor(String str, String str2, Boolean z) {
        String str3
        String strJ
        if (!str2.endsWith(".xml") && !str2.endsWith(".smali") && !str2.endsWith(".html") && !str2.endsWith(".htm") && !str2.endsWith(".css") && !str2.endsWith(".java") && !str2.endsWith(".json") && !str2.endsWith(".txt") && !str2.endsWith(".js")) {
            a(str, str2, z)
            return
        }
        if (str.equals(this.f758b)) {
            str3 = str2
        } else {
            str3 = str.substring(this.f758b.length() + 1) + "/" + str2
        }
        String strD = this.e.d(str3)
        if (strD == null) {
            if (z || ff.b(str2)) {
                strJ = j(str3)
            } else {
                strJ = str + "/" + str2
            }
            strD = strJ
        }
        if (strD != null) {
            this.aD = strD
            this.aE = str3
            this.aF = File(strD).lastModified()
            com.gmail.heagoo.a.c.a.a(this, strD, PointerIconCompat.TYPE_HAND)
        }
    }

    private fun j(String str) {
        Int iLastIndexOf = str.lastIndexOf("/")
        String strSubstring = iLastIndexOf != -1 ? str.substring(iLastIndexOf + 1) : str
        Int iLastIndexOf2 = strSubstring.lastIndexOf(Deobfuscator.CLASS_NAME_SEPARATOR)
        String strSubstring2 = iLastIndexOf2 != -1 ? strSubstring.substring(iLastIndexOf2) : ""
        try {
            String str2 = (String) this.k.get(str)
            String str3 = com.gmail.heagoo.a.c.a.d(this, "tmp") + "APKEDITOR.xcrhfvke" + strSubstring2
            if (str2 == null) {
                str2 = str
            }
            a.a.b.a.a.x.b(this.f757a, str2, str3)
            return str3
        } catch (Exception e) {
            return null
        }
    }

    static /* synthetic */ Boolean j(ApkInfoActivity apkInfoActivity) {
        return true
    }

    private fun n() {
        Int i = this.i ? R.drawable.searchtxt_checked : R.drawable.searchtxt_unchecked
        Int i2 = this.j ? R.drawable.ic_case_sensitive : R.drawable.ic_case_insensitive
        this.g.setImageResource(i)
        this.h.setImageResource(i2)
    }

    private fun o() {
        try {
            return "amazon".equals(getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("com.gmail.heagoo.publish_channel"))
        } catch (Exception e) {
            return false
        }
    }

    private fun p() {
        Int i
        Map mapA = this.B.a()
        if (mapA == null || mapA.isEmpty()) {
            return
        }
        this.X = true
        try {
            for (Map.Entry entry : mapA.entrySet()) {
                String str = (String) entry.getKey()
                Map map = (Map) entry.getValue()
                String str2 = this.f758b + "/res/values" + str
                File file = File(str2)
                if (!file.exists()) {
                    file.mkdirs()
                }
                String str3 = str2 + "/strings.xml"
                List listA = new com.gmail.heagoo.common.w(str3).a()
                ArrayList arrayList = ArrayList()
                HashSet hashSet = HashSet()
                Int i2 = 0
                Int size = -1
                while (i2 < listA.size()) {
                    String str4 = (String) listA.get(i2)
                    Int iIndexOf = str4.indexOf("<string name=\"")
                    if (iIndexOf == -1) {
                        arrayList.add(str4)
                        i = i2
                    } else {
                        Int i3 = iIndexOf + 14
                        Int iIndexOf2 = str4.indexOf(34, i3)
                        if (iIndexOf2 == -1) {
                            arrayList.add(str4)
                            i = i2
                        } else {
                            String strSubstring = str4.substring(i3, iIndexOf2)
                            String str5 = (String) map.get(strSubstring)
                            if (str5 != null) {
                                arrayList.add(StringItem.toString(strSubstring, str5, null))
                                hashSet.add(strSubstring)
                                if (!str4.contains("</string>")) {
                                    do {
                                        i2++
                                        if (i2 < listA.size()) {
                                        }
                                    } while (!((String) listA.get(i2)).contains("</string>"));
                                    i = i2
                                    size = arrayList.size() - 1
                                }
                            } else {
                                arrayList.add(str4)
                            }
                            i = i2
                            size = arrayList.size() - 1
                        }
                    }
                    i2 = i + 1
                }
                for (Map.Entry entry2 : map.entrySet()) {
                    String str6 = (String) entry2.getKey()
                    if (!hashSet.contains(str6)) {
                        size++
                        listA.add(size, StringItem.toString(str6, (String) entry2.getValue(), null))
                    }
                    size = size
                }
                com.gmail.heagoo.common.h.a(str3, arrayList)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    private fun q() {
        this.R.setVisibility(4)
        this.O.setVisibility(4)
        this.z.setVisibility(4)
        this.F.setVisibility(4)
        switch (this.ab) {
            case 0:
                if (!this.Z) {
                    this.R.setVisibility(0)
                    break
                } else {
                    this.z.setVisibility(0)
                    break
                }
            case 1:
                if (!this.aa) {
                    this.R.setVisibility(0)
                    break
                } else {
                    this.F.setVisibility(0)
                    break
                }
            case 2:
                if (!this.aa) {
                    this.R.setVisibility(0)
                    break
                } else {
                    this.O.setVisibility(0)
                    break
                }
        }
    }

    private fun r() {
        String string
        this.d = HashMap()
        a.a.b.a.e eVarA = this.W.a()
        if (eVarA == null) {
            return false
        }
        for (a.a.b.a.f fVar : eVarA.a()) {
            if ("string".equals(fVar.h().a())) {
                String strF = fVar.f()
                for (Map.Entry entry : fVar.b().entrySet()) {
                    a.a.b.a.c cVar = (a.a.b.a.c) entry.getKey()
                    a.a.b.a.a.w wVarD = ((a.a.b.a.g) entry.getValue()).d()
                    if ((wVarD is a.a.b.a.a.s) || !(wVarD is a.a.b.a.a.t)) {
                        string = wVarD.toString()
                    } else {
                        a.a.b.a.a.t tVar = (a.a.b.a.a.t) wVarD
                        strL = tVar.n() ? tVar.l() : null
                        string = tVar.m()
                        if (string == null) {
                            string = wVarD.toString()
                        }
                    }
                    StringItem stringItem = StringItem(strF, string, strL)
                    String strA = cVar.a()
                    ArrayList arrayList = (ArrayList) this.d.get(strA)
                    if (arrayList == null) {
                        arrayList = ArrayList()
                        this.d.put(strA, arrayList)
                    }
                    arrayList.add(stringItem)
                }
            }
        }
        return true
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun s() {
        if (this.B != null && this.C != null) {
            this.B.a(this.C)
        }
        findViewById(R.id.add_language).setOnClickListener(y(this))
        View viewFindViewById = findViewById(R.id.translate)
        if (a((Context) this) || t()) {
            viewFindViewById.setOnClickListener(this)
        } else {
            viewFindViewById.setOnClickListener(z(this))
        }
        v()
        findViewById(R.id.search_button).setOnClickListener(this)
        this.Z = true
        q()
    }

    private fun t() throws PackageManager.NameNotFoundException {
        try {
            getPackageManager().getApplicationInfo("apkeditor.translate", 0)
            return true
        } catch (PackageManager.NameNotFoundException e) {
            return false
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun u() {
        if (this.D == null) {
            this.D = c(this.d.keySet())
        }
        StringBuilder("********BEST*********").append(this.D)
        a((ArrayList) this.d.get(this.D))
    }

    private fun v() {
        Int i = 0
        if (this.d == null) {
            return
        }
        if (this.D == null) {
            this.D = c(this.d.keySet())
        }
        this.E = ArrayList()
        Array<String> strArr = new String[this.d.size()]
        ArrayList<String> arrayList = ArrayList()
        HashMap map = HashMap()
        for (String str : this.d.keySet()) {
            String strA = a.d.i.a(str)
            arrayList.add(strA)
            map.put(strA, str)
        }
        Collections.sort(arrayList)
        Int i2 = 0
        for (String str2 : arrayList) {
            String str3 = (String) map.get(str2)
            strArr[i] = str2
            this.E.add(str3)
            Int i3 = str3.equals(this.D) ? i : i2
            i++
            i2 = i3
        }
        Spinner spinner = (Spinner) findViewById(R.id.language_spinner)
        if (spinner != null) {
            ArrayAdapter arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, strArr)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter((SpinnerAdapter) arrayAdapter)
            spinner.setOnItemSelectedListener(ab(this))
            spinner.setSelection(i2)
        }
    }

    private fun w() {
        v()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun x() {
        this.e = ff(this, this.m ? null : this.f757a, (this.ar == null || !this.ar.startsWith(this.f758b)) ? this.f758b : this.ar, this.f758b, this.k, ac(this), this)
        this.e.a(this.as, this.au, this.at)
        this.G.setAdapter((ListAdapter) this.e)
        this.G.setOnItemClickListener(this)
        this.G.setOnItemLongClickListener(this)
        this.Q = dt(this, this.f758b + "/AndroidManifest.xml", this)
        this.P.setAdapter((ListAdapter) this.Q)
        this.P.setOnItemClickListener(this.Q)
        this.P.setOnItemLongClickListener(this.Q)
        if (this.f != null && this.ar != null) {
            this.f.a(this.ar)
        }
        this.aa = true
        q()
    }

    @Override // com.gmail.heagoo.apkeditor.cz
    public final String a(dj djVar) {
        return null
    }

    protected final Unit a() {
        this.q.setOnClickListener(as(this))
        this.r.setOnClickListener(at(this))
        this.s.setOnClickListener(r(this))
        this.V = findViewById(R.id.save_button)
        this.V.setOnClickListener(s(this))
        this.U = findViewById(R.id.menu_apply_patch)
        this.U.setOnClickListener(this)
        this.S = findViewById(R.id.menu_webserver)
        this.S.setOnClickListener(this)
    }

    protected final Unit a(Int i) {
        ArrayList arrayList = ArrayList()
        a(this.e.a(arrayList) + "/" + ((com.gmail.heagoo.b.a) arrayList.get(i)).f1424a, (gu) null)
    }

    public final Unit a(cy cyVar) {
        this.ay = cyVar
        new com.gmail.heagoo.apkeditor.f.a(this, this.f757a, this.f758b, this).execute(new Void[0])
        this.aj = true
    }

    public final Unit a(String str, Int i) {
        this.e.c(str)
        if (i > 0) {
            a.d.e eVar = null
            Int i2 = 0
            while (i2 < i) {
                try {
                    i2++
                    eVar = (a.d.e) this.H.pop()
                } catch (Exception e) {
                    return
                }
            }
            if (eVar != null) {
                this.G.setSelectionFromTop(((Integer) eVar.f71a).intValue(), ((Integer) eVar.f72b).intValue())
            }
        }
    }

    protected final Unit a(String str, gu guVar) {
        String strSubstring = str.substring(str.lastIndexOf(47) + 1)
        Int iLastIndexOf = strSubstring.lastIndexOf(46)
        String strSubstring2 = iLastIndexOf != -1 ? strSubstring.substring(iLastIndexOf) : null
        cn(this, al(this, guVar, strSubstring2), strSubstring2, str, null, false, false, true, null).show()
    }

    protected final Unit a(String str, String str2) {
        if (str2 == null) {
            str2 = str.substring(this.f758b.length() + 1)
        }
        this.e.c(str2, str)
    }

    public final Unit a(String str, String str2, gu guVar) {
        this.az = str
        this.aA = str2
        this.aB = guVar
    }

    protected final Unit a(String str, String str2, ArrayList arrayList, Boolean z, Boolean z2) {
        if (this.aw != null) {
            this.aw.a(str)
        }
        if (z) {
            fo(this, str2, arrayList, str, z2).show()
        } else {
            fv(this, str2, arrayList, str, z2).show()
        }
    }

    protected final Unit a(String str, String str2, Boolean z) {
        String str3 = str.equals(this.f758b) ? str2 : str.substring(this.f758b.length() + 1) + "/" + str2
        String str4 = str2.endsWith(".xml") ? "xml.xml" : str2.endsWith(".smali") ? "smali.xml" : (str2.endsWith(".html") || str2.endsWith(".htm")) ? "html.xml" : str2.endsWith(".css") ? "css.xml" : str2.endsWith(".java") ? "java.xml" : str2.endsWith(".json") ? "json.xml" : str2.endsWith(".txt") ? "txt.xml" : str2.endsWith(".js") ? "js.xml" : null
        if (str4 == null) {
            String strD = this.e.d(str3)
            if (strD == null) {
                strD = (z || ff.b(str2)) ? j(str3) : str + "/" + str2
            }
            if (strD != null) {
                this.aD = strD
                this.aE = str3
                this.aF = File(strD).lastModified()
                if (!str2.endsWith(".png")) {
                    com.gmail.heagoo.a.c.a.a(this, strD, PointerIconCompat.TYPE_HAND)
                    return
                }
                Intent intent = Intent(this, (Class<?>) PngEditActivity.class)
                com.gmail.heagoo.a.c.a.a(intent, "filePath", strD)
                startActivityForResult(intent, PointerIconCompat.TYPE_HAND)
                return
            }
            return
        }
        String strD2 = this.e.d(str3)
        if (strD2 == null) {
            if (z) {
                strD2 = j(str3)
                if (strD2 == null) {
                    Toast.makeText(this, String.format(getString(R.string.cannot_open_xxx), str3), 0).show()
                    return
                }
            } else {
                strD2 = str + "/" + str2
            }
        }
        if ("res/values/colors.xml".equals(str3)) {
            Intent intent2 = Intent(this, (Class<?>) ColorXmlActivity.class)
            com.gmail.heagoo.a.c.a.a(intent2, "xmlPath", strD2)
            startActivityForResult(intent2, 3)
        } else {
            Intent intentA = com.gmail.heagoo.a.c.a.a(this, strD2, this.f757a)
            com.gmail.heagoo.a.c.a.a(intentA, "syntaxFileName", str4)
            if (str2 != null) {
                com.gmail.heagoo.a.c.a.a(intentA, "displayFileName", str2)
            }
            com.gmail.heagoo.a.c.a.a(intentA, "extraString", str3)
            startActivityForResult(intentA, 0)
        }
    }

    protected final Unit a(List list) {
        ArrayList arrayList = ArrayList()
        ArrayList arrayList2 = ArrayList()
        String strA = this.e.a(arrayList2)
        Iterator it = list.iterator()
        while (it.hasNext()) {
            Int iIntValue = ((Integer) it.next()).intValue()
            if (iIntValue < arrayList2.size()) {
                com.gmail.heagoo.b.a aVar = (com.gmail.heagoo.b.a) arrayList2.get(iIntValue)
                cg cgVar = cg()
                if (aVar.c) {
                    cgVar.f930a = (strA + "/" + aVar.f1424a).substring(this.f758b.length() + 1)
                } else {
                    cgVar.f930a = strA + "/" + aVar.f1424a
                }
                cgVar.c = aVar.c
                cgVar.f931b = aVar.f1425b
                arrayList.add(cgVar)
            }
        }
        b(arrayList)
    }

    @Override // com.gmail.heagoo.apkeditor.bf
    public final Unit a(Map map) {
        this.k = map
        v(this, map).start()
        Boolean z = !this.ap
        if (!this.ap) {
            this.ap = r()
        }
        runOnUiThread(w(this, z))
    }

    public final Unit a(Boolean z) {
        this.Y = true
        if (z) {
            this.Q.a()
        } else {
            runOnUiThread(aq(this))
        }
    }

    @Override // com.gmail.heagoo.apkeditor.f.b
    public final Unit a(Boolean z, String str, String str2) {
        Boolean z2
        if (this.ay != null) {
            this.ay.a()
            this.ay = null
        }
        if (!z) {
            this.ah.setText(R.string.failed)
            this.ai.setVisibility(0)
            if (str != null) {
                this.ai.setText(str)
                z2 = true
            } else {
                this.ai.setText(R.string.unknown_error)
                z2 = true
            }
        } else if (str2 != null) {
            this.ah.setText(R.string.succeed_with_warning)
            this.ai.setText(getString(R.string.warning) + ": " + str2)
            this.ai.setVisibility(0)
            z2 = true
        } else {
            this.ah.setText(R.string.succeed)
            this.ai.setVisibility(8)
            z2 = false
        }
        this.ae.setVisibility(4)
        this.af.setVisibility(4)
        if (z2) {
            this.ag.setVisibility(0)
        } else {
            this.ad.setVisibility(8)
            Toast.makeText(this, R.string.dex_decode_succeed, 1).show()
        }
        String strA = this.e.a((List) null)
        if (strA.endsWith("/decoded")) {
            this.e.c(strA)
        }
    }

    protected final Unit aa(Int i) {
        ArrayList arrayList = ArrayList()
        String strA = this.e.a(arrayList)
        com.gmail.heagoo.b.a aVar = (com.gmail.heagoo.b.a) arrayList.get(i)
        if (aVar != null) {
            extEditor(strA, aVar.f1424a, false)
        }
    }

    protected final Unit b() {
        if (!MainActivity.a(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
            builder.setTitle(R.string.please_note)
            builder.setMessage(R.string.build_not_support_tip)
            builder.show()
            return
        }
        p()
        if (!SettingActivity.f(this)) {
            b(true)
            return
        }
        fc(this, this.X, this.Y, this.e.b(), this.e.c(), this.e.d()).a()
    }

    protected final Unit b(Int i) {
        String string = getString(R.string.select_folder_replace)
        ArrayList arrayList = ArrayList()
        cn(this, am(this), null, this.e.a(arrayList) + "/" + ((com.gmail.heagoo.b.a) arrayList.get(i)).f1424a, string, true, true, false, null).show()
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.Closeable, java.io.FileOutputStream, java.io.OutputStream] */
    protected final Unit b(String str, String str2) throws Throwable {
        FileInputStream fileInputStream
        FileInputStream fileInputStream2
        FileOutputStream fileOutputStream
        FileInputStream fileInputStream3 = null
        try {
            fileInputStream = FileInputStream(str2)
            try {
                fileOutputStream = FileOutputStream(str)
            } catch (IOException e) {
                e = e
                fileInputStream2 = null
                fileInputStream3 = fileInputStream
            } catch (Throwable th) {
                th = th
            }
            try {
                com.gmail.heagoo.a.c.a.b(fileInputStream, (OutputStream) fileOutputStream)
                a(fileInputStream)
                a((Closeable) fileOutputStream)
            } catch (IOException e2) {
                e = e2
                fileInputStream3 = fileInputStream
                fileInputStream2 = fileOutputStream
                try {
                    e.printStackTrace()
                    a(fileInputStream3)
                    a(fileInputStream2)
                } catch (Throwable th2) {
                    th = th2
                    fileInputStream = fileInputStream3
                    fileInputStream3 = fileInputStream2
                    a(fileInputStream)
                    a(fileInputStream3)
                    throw th
                }
            } catch (Throwable th3) {
                th = th3
                fileInputStream3 = fileOutputStream
                a(fileInputStream)
                a(fileInputStream3)
                throw th
            }
        } catch (IOException e3) {
            e = e3
            fileInputStream2 = null
        } catch (Throwable th4) {
            th = th4
            fileInputStream = null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fn
    fun b(Set set) {
        if (!(!set.isEmpty())) {
            this.J.setVisibility(8)
            this.M.setVisibility(8)
            this.I.setVisibility(0)
            this.L.setVisibility(0)
            return
        }
        this.I.setVisibility(8)
        this.L.setVisibility(8)
        this.J.setVisibility(0)
        this.M.setVisibility(0)
        this.N.setText(String.format(getString(R.string.num_items_selected), Integer.valueOf(set.size())))
    }

    protected final Unit b(Boolean z) {
        this.ao = false
        HashSet hashSet = HashSet()
        Log.d("DEBUG", "resModifyTime=" + com.gmail.heagoo.common.h.b(File(this.f758b + "/res")))
        Log.d("DEBUG", "manifestTime=" + com.gmail.heagoo.common.h.b(File(this.f758b + "/AndroidManifest.xml")))
        Map mapB = this.e.b()
        Map mapC = this.e.c()
        Set<String> setD = this.e.d()
        this.ak = HashMap()
        this.al = HashMap()
        this.am = ArrayList()
        for (Map.Entry entry : mapB.entrySet()) {
            String str = (String) entry.getKey()
            if (str.startsWith("res/")) {
                if (!this.ao && !b(str)) {
                    this.ao = true
                }
                this.ak.put(entry.getKey(), entry.getValue())
            } else if (a(str, hashSet) == null) {
                this.ak.put(entry.getKey(), entry.getValue())
            }
        }
        for (Map.Entry entry2 : mapC.entrySet()) {
            String str2 = (String) entry2.getKey()
            if (str2.equals(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME) || str2.startsWith("res/")) {
                if (!this.ao && !b(str2)) {
                    this.ao = true
                }
                this.al.put(entry2.getKey(), entry2.getValue())
            } else if (a(str2, hashSet) == null) {
                this.al.put(entry2.getKey(), entry2.getValue())
            }
        }
        for (String str3 : setD) {
            if (str3.startsWith("res/")) {
                if (!this.ao && !b(str3)) {
                    this.ao = true
                }
                this.am.add(str3)
            } else if (a(str3, hashSet) == null) {
                this.am.add(str3)
            }
        }
        this.an = ArrayList()
        Iterator it = hashSet.iterator()
        while (it.hasNext()) {
            this.an.add((String) it.next())
        }
        d(z)
    }

    protected final Unit c() {
        if (this.ab != 2) {
            this.ab = 2
            q()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.ui.b
    public final Unit c(String str) {
        this.e.b(this.e.a((List) null), str)
    }

    @Override // com.gmail.heagoo.apkeditor.bf
    public final Unit c(Boolean z) {
        this.ap = r()
        if (this.ap) {
            runOnUiThread(u(this))
        }
    }

    protected final Unit d() {
        SharedPreferences defaultSharedPreferences
        Int i
        if (this.ab == 1) {
            return
        }
        this.ab = 1
        q()
        if (this.aj || (i = (defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)).getInt("HideSmaliMsgShown", 0)) > 0 || !SettingActivity.e(this)) {
            return
        }
        Toast.makeText(this, R.string.hide_smali_tip, 1).show()
        SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit()
        editorEdit.putInt("HideSmaliMsgShown", i + 1)
        editorEdit.apply()
    }

    @Override // com.gmail.heagoo.apkeditor.ui.b
    public final Unit d(String str) {
        ey(this, t(this, str, this.e.a((List) null)), -1).show()
    }

    protected final Unit e() {
        if (this.ab != 0) {
            this.ab = 0
            q()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.bf
    public final Unit e(String str) {
        runOnUiThread(x(this, str))
    }

    protected final Unit editorSwitch(String str, String str2, Boolean z) {
        if (!SettingActivity.extEditor(this) || str2.equals("colors.xml")) {
            a(str, str2, z)
        } else {
            extEditor(str, str2, z)
        }
    }

    protected final Unit f() {
        cn(this, an(this), null, this.e.a((List) null), getString(R.string.add_a_file)).show()
    }

    public final Unit f(String str) {
        Intent intent
        ArrayList arrayList = ArrayList()
        ArrayList arrayList2 = ArrayList()
        b(str, arrayList, arrayList2)
        try {
            if (a((Context) this)) {
                intent = Intent("android.intent.action.VIEW")
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setDataAndType(null, "application/com.gmail.heagoo.apkeditor-translate")
            } else {
                ComponentName componentName = ComponentName("apkeditor.translate", "apkeditor.translate.TranslateActivity")
                intent = Intent()
                intent.setComponent(componentName)
            }
            Bundle bundle = Bundle()
            String str2 = com.gmail.heagoo.a.c.a.d(this, "tmp") + "translated"
            com.gmail.heagoo.a.c.a.a(str2, arrayList)
            bundle.putString("translatedList_file", str2)
            String str3 = com.gmail.heagoo.a.c.a.d(this, "tmp") + "untranslatedList"
            com.gmail.heagoo.a.c.a.a(str3, arrayList2)
            bundle.putString("untranslatedList_file", str3)
            bundle.putString("targetLanguageCode", str)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1000)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    public final String g(String str) {
        String str2
        String str3
        Boolean z = true
        Resources resources = getResources()
        if (str.length() < 3) {
            return resources.getString(R.string.invalid_lang_code)
        }
        if (!this.aa) {
            return resources.getString(R.string.wait_for_decoding)
        }
        List<TranslateItem> arrayList = ArrayList()
        List<TranslateItem> arrayList2 = ArrayList()
        b(str, arrayList, arrayList2)
        if (arrayList2.isEmpty()) {
            return resources.getString(R.string.lang_exist)
        }
        ArrayList arrayList3 = ArrayList()
        for (TranslateItem translateItem : arrayList2) {
            arrayList3.add(StringItem(translateItem.name, translateItem.originValue))
        }
        Iterator it = this.d.entrySet().iterator()
        while (true) {
            if (!it.hasNext()) {
                str2 = null
                z = false
                break
            }
            Map.Entry entry = (Map.Entry) it.next()
            String str4 = (String) entry.getKey()
            if (str4.equals(str)) {
                ((ArrayList) entry.getValue()).addAll(arrayList3)
                str2 = str4
                break
            }
        }
        if (str2 == null) {
            this.d.put(str, arrayList3)
            str3 = str
        } else {
            str3 = str2
        }
        try {
            if (z) {
                List arrayList4 = ArrayList()
                for (TranslateItem translateItem2 : arrayList) {
                    arrayList4.add(StringItem(translateItem2.name, translateItem2.translatedValue))
                }
                arrayList4.addAll(arrayList3)
                b(str, arrayList4)
            } else {
                b(str, arrayList3)
            }
            this.X = true
        } catch (Exception e) {
            e.printStackTrace()
        }
        this.D = str3
        w()
        return null
    }

    protected final Unit g() {
        new com.gmail.heagoo.apkeditor.ui.a(this, this, this.m).show()
    }

    @Override // com.gmail.heagoo.apkeditor.f.b
    public final Unit h() {
        this.ae.setVisibility(4)
        this.ag.setVisibility(4)
        this.af.setVisibility(0)
    }

    protected final Unit h(String str) {
        ArrayList arrayList = ArrayList()
        cg cgVar = cg()
        cgVar.c = false
        cgVar.f931b = false
        cgVar.f930a = str
        arrayList.add(cgVar)
        b(arrayList)
    }

    public final String i() {
        return this.f758b
    }

    @Override // com.gmail.heagoo.apkeditor.cz
    public final Unit i(String str) throws Throwable {
        FileOutputStream fileOutputStream
        FileOutputStream fileOutputStream2 = null
        try {
            fileOutputStream = FileOutputStream(this.f758b + "/AndroidManifest.xml")
            try {
                fileOutputStream.write(str.getBytes())
                this.Y = true
                try {
                    fileOutputStream.close()
                } catch (IOException e) {
                }
            } catch (IOException e2) {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close()
                    } catch (IOException e3) {
                    }
                }
            } catch (Throwable th) {
                fileOutputStream2 = fileOutputStream
                th = th
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close()
                    } catch (IOException e4) {
                    }
                }
                throw th
            }
        } catch (IOException e5) {
            fileOutputStream = null
        } catch (Throwable th2) {
            th = th2
        }
    }

    public final ff j() {
        return this.e
    }

    public final Boolean k() {
        return this.aj
    }

    public final String l() {
        return this.f757a
    }

    public final com.gmail.heagoo.common.b m() {
        return this.c
    }

    @Override // android.app.Activity
    protected fun onActivityResult(Int i, Int i2, Intent intent) {
        switch (i) {
            case 0:
            case 3:
                if (i2 != 0) {
                    ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("modifiedFiles")
                    if (stringArrayListExtra != null) {
                        Iterator<String> it = stringArrayListExtra.iterator()
                        while (it.hasNext()) {
                            a(it.next(), (String) null)
                        }
                        break
                    } else {
                        c(intent.getStringExtra("xmlPath"), intent.getStringExtra("extraString"))
                        break
                    }
                }
                break
            case 1:
                if (i2 == 10005) {
                    finish()
                    break
                }
                break
            case 2:
                if (i2 != 0) {
                    a(true)
                    break
                }
                break
            case 1000:
                if (i2 == -1) {
                    Bundle extras = intent.getExtras()
                    String string = extras.getString("targetLanguageCode")
                    List<TranslateItem> list = (List) com.gmail.heagoo.a.c.a.j(extras.getString("translatedList_file"))
                    if (list != null && !list.isEmpty()) {
                        ArrayList arrayList = ArrayList()
                        for (TranslateItem translateItem : list) {
                            arrayList.add(StringItem(translateItem.name, translateItem.translatedValue))
                        }
                        try {
                            a(string, arrayList)
                            Toast.makeText(this, String.format(getString(R.string.save_succeed_tip), Integer.valueOf(arrayList.size())), 0).show()
                            break
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), 1).show()
                            return
                        }
                    }
                }
                break
            case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
                this.e.d(this.aA, this.az)
                if ((this.f758b + "/AnfroidManifest.xml").equals(this.aA)) {
                    a(true)
                }
                if (this.aB != null) {
                    this.aB.a()
                    break
                }
                break
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                if (File(this.aD).lastModified() > this.aF) {
                    c(this.aD, this.aE)
                    break
                }
                break
        }
    }

    @Override // android.app.Activity
    fun onBackPressed() {
        Boolean z = false
        if (this.ab == 1 && this.e != null) {
            if (!this.e.e().isEmpty()) {
                this.e.a(false)
                z = true
            } else if (!this.e.f()) {
                String strA = this.e.a((List) null)
                String strSubstring = strA.substring(0, strA.lastIndexOf(47))
                this.e.c(strSubstring)
                this.f.a(strSubstring)
                try {
                    a.d.e eVar = (a.d.e) this.H.pop()
                    this.G.setSelectionFromTop(((Integer) eVar.f71a).intValue(), ((Integer) eVar.f72b).intValue())
                } catch (Exception e) {
                }
                z = true
            }
        }
        if (z) {
            return
        }
        AlertDialog.Builder negativeButton = new AlertDialog.Builder(this).setMessage(R.string.sure_to_exit_editing).setPositiveButton(R.string.yes, aa(this)).setNegativeButton(R.string.no, (DialogInterface.OnClickListener) null)
        if (this.aC == null && (this.W == null || !this.W.isAlive())) {
            negativeButton.setMessage(R.string.sure_to_exit)
            negativeButton.setNeutralButton(R.string.save_as_project, ao(this))
        }
        negativeButton.show()
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) throws IOException {
        Int id = view.getId()
        if (id == R.id.search_button) {
            String strTrim = ((EditText) findViewById(R.id.keyword_edit)).getText().toString().trim()
            if (strTrim.equals("")) {
                u()
            } else {
                String lowerCase = strTrim.toLowerCase()
                ArrayList arrayList = (ArrayList) this.d.get(this.D)
                if (arrayList != null) {
                    ArrayList arrayList2 = ArrayList()
                    Iterator it = arrayList.iterator()
                    while (it.hasNext()) {
                        StringItem stringItem = (StringItem) it.next()
                        if (stringItem.value.toLowerCase().contains(lowerCase)) {
                            arrayList2.add(stringItem)
                        }
                    }
                    a(arrayList2)
                }
                this.av.a(strTrim)
            }
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2)
            return
        }
        if (id == R.id.btn_search_mf) {
            String strTrim2 = ((EditText) findViewById(R.id.mf_keyword)).getText().toString().trim()
            if (strTrim2.equals("")) {
                Toast.makeText(this, R.string.empty_input_tip, 0).show()
                return
            }
            this.ax.a(strTrim2)
            ArrayList<Integer> arrayList3 = new ArrayList<>()
            ArrayList<String> arrayList4 = new ArrayList<>()
            a(strTrim2, arrayList3, arrayList4)
            if (arrayList3.isEmpty()) {
                Toast.makeText(this, R.string.notfound_in_manifest, 0).show()
                return
            }
            Intent intent = Intent(this, (Class<?>) MfSearchRetActivity.class)
            Bundle bundle = Bundle()
            bundle.putString("xmlPath", this.f758b + "/AndroidManifest.xml")
            bundle.putIntegerArrayList("lineIndexs", arrayList3)
            bundle.putStringArrayList("lineContents", arrayList4)
            intent.putExtras(bundle)
            startActivityForResult(intent, 2)
            return
        }
        if (id == R.id.menu_search_res) {
            String strTrim3 = ((EditText) findViewById(R.id.et_res_keyword)).getText().toString().trim()
            if (strTrim3.equals("")) {
                Toast.makeText(this, R.string.empty_input_tip, 0).show()
                return
            }
            if (this.e != null) {
                ArrayList arrayList5 = ArrayList()
                ArrayList arrayList6 = ArrayList()
                String strA = this.e.a(arrayList6)
                if (!"..".equals(((com.gmail.heagoo.b.a) arrayList6.get(0)).f1424a)) {
                    arrayList5.add(((com.gmail.heagoo.b.a) arrayList6.get(0)).f1424a)
                }
                for (Int i = 1; i < arrayList6.size(); i++) {
                    arrayList5.add(((com.gmail.heagoo.b.a) arrayList6.get(i)).f1424a)
                }
                a(strTrim3, strA, arrayList5, !this.i, this.j)
                return
            }
            return
        }
        if (id == R.id.imageview_dex2smali) {
            if (!com.gmail.heagoo.a.c.a.a((Context) this, "smali_license_showed", false)) {
                gt(this).show()
            }
            a((cy) null)
        } else {
            if (id == R.id.down_arrow_container) {
                this.ad.setVisibility(8)
                return
            }
            if (id == R.id.menu_webserver) {
                com.gmail.heagoo.httpserver.e.a().a(this, this.f758b)
                return
            }
            if (id == R.id.menu_apply_patch) {
                eu(this).show()
            } else if (id == R.id.translate) {
                dh dhVar = dh(this, PossibleLanguages.languages, PossibleLanguages.codes)
                dhVar.setTitle(R.string.select_target_lang)
                dhVar.show()
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x03c0  */
    @Override // android.app.Activity
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected fun onCreate(android.os.Bundle r10) {
        /*
            Method dump skipped, instructions count: 1123
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.ApkInfoActivity.onCreate(android.os.Bundle):Unit")
    }

    @Override // android.app.Activity
    fun onDestroy() {
        com.gmail.heagoo.httpserver.e.a().a(this)
        super.onDestroy()
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    fun onItemClick(AdapterView adapterView, View view, Int i, Long j) {
        String strSubstring
        Boolean z = false
        ArrayList arrayList = ArrayList()
        String strA = this.e.a(arrayList)
        com.gmail.heagoo.b.a aVar = (com.gmail.heagoo.b.a) arrayList.get(i)
        if (aVar == null) {
            return
        }
        if (!this.e.e().isEmpty()) {
            this.e.a(i)
            return
        }
        if (!aVar.f1425b) {
            editorSwitch(strA, aVar.f1424a, aVar.c)
            return
        }
        if (aVar.f1424a.equals("..")) {
            z = true
            strSubstring = strA.substring(0, strA.lastIndexOf(47))
        } else {
            Int firstVisiblePosition = this.G.getFirstVisiblePosition()
            View childAt = this.G.getChildAt(0)
            this.H.push(new a.d.e(Integer.valueOf(firstVisiblePosition), Integer.valueOf(childAt == null ? 0 : childAt.getTop() - this.G.getPaddingTop())))
            strSubstring = strA + "/" + aVar.f1424a
        }
        this.e.c(strSubstring)
        this.f.a(strSubstring)
        if (!z) {
            this.G.setSelectionAfterHeaderView()
            return
        }
        try {
            a.d.e eVar = (a.d.e) this.H.pop()
            this.G.setSelectionFromTop(((Integer) eVar.f71a).intValue(), ((Integer) eVar.f72b).intValue())
        } catch (Exception e) {
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    fun onItemLongClick(AdapterView adapterView, View view, Int i, Long j) {
        adapterView.setOnCreateContextMenuListener(ad(this, i, i == 0))
        return false
    }

    @Override // android.view.View.OnLongClickListener
    fun onLongClick(View view) {
        if (view.getId() != R.id.imageview_dex2smali) {
            return false
        }
        this.ad.setVisibility(8)
        return true
    }

    @Override // android.app.Activity
    fun onPause() {
        super.onPause()
    }

    @Override // android.app.Activity
    fun onResume() {
        super.onResume()
    }

    @Override // android.app.Activity
    fun onSaveInstanceState(Bundle bundle) {
        String strD
        try {
            if (this.aC != null) {
                strD = com.gmail.heagoo.a.c.a.d(this, ".projects") + this.aC + "/"
            } else {
                strD = com.gmail.heagoo.a.c.a.d(this, "tmp")
            }
            a(strD, false, (File) null).toBundle(bundle)
        } catch (Exception e) {
        }
        super.onSaveInstanceState(bundle)
    }

    @Override // android.app.Activity
    fun onStart() {
        super.onStart()
    }

    @Override // android.app.Activity
    fun onStop() {
        super.onStop()
    }
}
