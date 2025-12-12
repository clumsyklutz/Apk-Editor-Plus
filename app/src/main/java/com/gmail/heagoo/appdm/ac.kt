package com.gmail.heagoo.appdm

import com.gmail.heagoo.common.ccc
import java.io.File
import java.lang.ref.WeakReference

final class ac extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1361a

    /* renamed from: b, reason: collision with root package name */
    private String f1362b
    private String c
    private String d
    private String e

    constructor(PrefOverallActivity prefOverallActivity) {
        this.f1362b = prefOverallActivity.c
        this.f1361a = WeakReference(prefOverallActivity)
    }

    public final String a() {
        return this.d
    }

    public final String b() {
        return this.e
    }

    public final String c() {
        return this.c
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        Boolean z = false
        z = false
        z = false
        z = false
        z = false
        PrefOverallActivity prefOverallActivity = (PrefOverallActivity) this.f1361a.get()
        if (prefOverallActivity != null) {
            if (com.gmail.heagoo.appdm.util.i.a()) {
                String path = prefOverallActivity.getFilesDir().getPath()
                Int iIndexOf = path.indexOf(prefOverallActivity.getPackageName())
                if (iIndexOf == -1) {
                    this.c = "Can not find data path!"
                } else {
                    String strSubstring = path.substring(0, iIndexOf)
                    if (prefOverallActivity.G) {
                        String str = strSubstring + this.f1362b + "/shared_prefs/*.xml"
                        String str2 = strSubstring + this.f1362b + "/databases/*.db"
                        ccc cccVarG = prefOverallActivity.g()
                        if (cccVarG.a(String.format("ls %s", str), (Array<String>) null, (Integer) 5000)) {
                            this.d = cccVarG.a()
                            if (cccVarG.a(String.format("ls %s", str2), (Array<String>) null, (Integer) 5000)) {
                                this.e = cccVarG.a()
                            } else {
                                this.c = "Can not get access to read files!"
                            }
                        } else {
                            this.c = "Can not get access to read files!"
                        }
                    } else {
                        Array<File> fileArrListFiles = File(strSubstring + this.f1362b + "/shared_prefs").listFiles()
                        if (fileArrListFiles != null) {
                            StringBuffer stringBuffer = StringBuffer()
                            for (File file : fileArrListFiles) {
                                stringBuffer.append(file.getAbsolutePath())
                                stringBuffer.append("\n")
                            }
                            this.d = stringBuffer.toString()
                        }
                        Array<File> fileArrListFiles2 = File(strSubstring + this.f1362b + "/databases").listFiles()
                        if (fileArrListFiles2 != null) {
                            StringBuffer stringBuffer2 = StringBuffer()
                            for (File file2 : fileArrListFiles2) {
                                stringBuffer2.append(file2.getAbsolutePath())
                                stringBuffer2.append("\n")
                            }
                            this.e = stringBuffer2.toString()
                        }
                    }
                    z = true
                }
            } else {
                this.c = "Can not find SD card!"
            }
        }
        PrefOverallActivity prefOverallActivity2 = (PrefOverallActivity) this.f1361a.get()
        if (prefOverallActivity2 != null) {
            prefOverallActivity2.a(z)
        }
    }
}
