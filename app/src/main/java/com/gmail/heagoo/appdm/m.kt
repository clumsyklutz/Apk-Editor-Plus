package com.gmail.heagoo.appdm

import com.gmail.heagoo.a.c.ax
import com.gmail.heagoo.common.ccc
import java.io.File
import java.io.FileInputStream
import java.lang.ref.WeakReference
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map

final class m extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1394a

    constructor(PrefDetailActivity prefDetailActivity) {
        this.f1394a = WeakReference(prefDetailActivity)
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws Exception {
        HashMap mapA_004
        PrefDetailActivity prefDetailActivity = (PrefDetailActivity) this.f1394a.get()
        if (prefDetailActivity != null) {
            try {
                if (!com.gmail.heagoo.appdm.util.i.a()) {
                    throw Exception("Can not find SD Card!")
                }
                File file = File(com.gmail.heagoo.appdm.util.i.b(prefDetailActivity))
                if (!file.exists()) {
                    file.mkdirs()
                }
                if (prefDetailActivity.l) {
                    String path = File(prefDetailActivity.getFilesDir(), "work.xml").getPath()
                    prefDetailActivity.f1354b = path
                    ccc cccVarA = PrefDetailActivity.a(prefDetailActivity.l)
                    File file2 = File(prefDetailActivity.getFilesDir(), "mycp")
                    Boolean zA = cccVarA.a(String.format((file2.exists() ? file2.getPath() : "cp") + " \"%s\" %s", prefDetailActivity.f1353a, path, path), (Array<String>) null, (Integer) 5000)
                    cccVarA.b()
                    if (!zA) {
                        path = prefDetailActivity.f1353a
                    }
                    FileInputStream fileInputStream = FileInputStream(path)
                    mapA_004 = ax.a_004(fileInputStream)
                    fileInputStream.close()
                } else {
                    FileInputStream fileInputStream2 = FileInputStream(prefDetailActivity.f1353a)
                    mapA_004 = ax.a_004(fileInputStream2)
                    fileInputStream2.close()
                }
                LinkedHashMap linkedHashMap = LinkedHashMap()
                for (Map.Entry entry : mapA_004.entrySet()) {
                    linkedHashMap.put(entry.getKey(), entry.getValue())
                }
                PrefDetailActivity.a(prefDetailActivity, linkedHashMap)
                prefDetailActivity.a((String) null)
            } catch (Exception e) {
                prefDetailActivity.a(e.getMessage() + ": " + ((String) null))
            }
        }
    }
}
