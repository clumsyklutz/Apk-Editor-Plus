package com.gmail.heagoo.httpserver

import a.a.b.a.a.x
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class e {

    /* renamed from: a, reason: collision with root package name */
    private static e f1482a = null

    /* renamed from: b, reason: collision with root package name */
    private val f1483b = ArrayList()

    private constructor() {
    }

    fun a() {
        if (f1482a == null) {
            f1482a = e()
        }
        return f1482a
    }

    fun b(Activity activity) {
        activity.stopService(Intent(activity, (Class<?>) HttpService.class))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Activity activity, String str) {
        new AlertDialog.Builder(activity).setTitle(R.string.web_server).setMessage(String.format(activity.getString(R.string.web_server_started), str)).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show()
    }

    private fun c(Activity activity) {
        synchronized (this.f1483b) {
            for (f fVar : this.f1483b) {
                if (fVar.f1484a.get() == activity) {
                    return fVar.f1485b
                }
            }
            return null
        }
    }

    public final Unit a(Activity activity) {
        f fVar
        synchronized (this.f1483b) {
            Iterator it = this.f1483b.iterator()
            while (true) {
                if (!it.hasNext()) {
                    fVar = null
                    break
                } else {
                    fVar = (f) it.next()
                    if (fVar.f1484a.get() == activity) {
                        break
                    }
                }
            }
        }
        if (fVar != null) {
            activity.unbindService(fVar)
            synchronized (this.f1483b) {
                this.f1483b.remove(fVar)
            }
        }
    }

    public final Unit a(Activity activity, String str) {
        FileOutputStream fileOutputStream
        InputStream inputStreamOpen
        File file
        InputStream inputStream = null
        d dVarC = c(activity)
        if (dVarC != null) {
            dVarC.f1481a.f1478b = str
            if (dVarC.f1481a.c != null) {
                dVarC.f1481a.c.a(str)
            }
            b(activity, dVarC.a())
            return
        }
        File file2 = File(activity.getFilesDir(), "http_server")
        if (!file2.exists()) {
            try {
                inputStreamOpen = activity.getAssets().open("http.zip")
                try {
                    file = File(activity.getFilesDir(), "http.zip")
                    fileOutputStream = FileOutputStream(file)
                } catch (Exception e) {
                    e = e
                    fileOutputStream = null
                    inputStream = inputStreamOpen
                } catch (Throwable th) {
                    th = th
                    fileOutputStream = null
                }
                try {
                    com.gmail.heagoo.a.c.a.b(inputStreamOpen, fileOutputStream)
                    file2.mkdir()
                    x.a(file.getPath(), file2.getPath())
                    file.delete()
                    com.gmail.heagoo.a.c.a.a((Closeable) inputStreamOpen)
                    com.gmail.heagoo.a.c.a.a(fileOutputStream)
                } catch (Exception e2) {
                    e = e2
                    inputStream = inputStreamOpen
                    try {
                        Toast.makeText(activity, "Init Error: " + e.getMessage(), 1).show()
                        com.gmail.heagoo.a.c.a.a((Closeable) inputStream)
                        com.gmail.heagoo.a.c.a.a(fileOutputStream)
                        return
                    } catch (Throwable th2) {
                        th = th2
                        inputStreamOpen = inputStream
                        com.gmail.heagoo.a.c.a.a((Closeable) inputStreamOpen)
                        com.gmail.heagoo.a.c.a.a(fileOutputStream)
                        throw th
                    }
                } catch (Throwable th3) {
                    th = th3
                    com.gmail.heagoo.a.c.a.a((Closeable) inputStreamOpen)
                    com.gmail.heagoo.a.c.a.a(fileOutputStream)
                    throw th
                }
            } catch (Exception e3) {
                e = e3
                fileOutputStream = null
            } catch (Throwable th4) {
                th = th4
                fileOutputStream = null
                inputStreamOpen = null
            }
        }
        Intent intent = Intent(activity, (Class<?>) HttpService.class)
        com.gmail.heagoo.a.c.a.a(intent, "httpDirectory", file2.getPath())
        com.gmail.heagoo.a.c.a.a(intent, "projectDirectory", str)
        activity.startService(intent)
        activity.bindService(intent, f(this, activity), 1)
    }
}
