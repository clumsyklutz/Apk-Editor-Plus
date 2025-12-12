package com.gmail.heagoo.apkeditor

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.ResXmlPatcher
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.Map
import java.util.Set
import javax.xml.xpath.XPathExpressionException

class ApkComposeService extends Service implements com.gmail.heagoo.common.j {

    /* renamed from: a, reason: collision with root package name */
    private String f753a

    /* renamed from: b, reason: collision with root package name */
    private String f754b
    private Boolean c
    private Boolean d
    private Boolean e
    private ArrayList f
    private Map g
    private Map h
    private Set i
    private Map j
    private String k
    private bz l
    private WeakReference n
    private NotificationManager p
    private NotificationCompat.Builder q
    private Boolean u
    private j m = j(this)
    private com.gmail.heagoo.apkeditor.a.d o = null
    private Boolean r = false
    private l s = l(this, 0)
    private k t = k(this)
    private Long v = 0

    static /* synthetic */ NotificationManager a(ApkComposeService apkComposeService, NotificationManager notificationManager) {
        apkComposeService.p = null
        return null
    }

    private fun a(Boolean z, String str, String str2) {
        if (this.p != null) {
            this.s.removeMessages(0)
            this.s.a(str, str2)
            if (z || System.currentTimeMillis() - this.v > 1000) {
                this.s.sendEmptyMessage(0)
            } else {
                this.s.sendEmptyMessageDelayed(0, 500L)
            }
        }
    }

    private fun b(String str) throws Throwable {
        BufferedReader bufferedReader
        HashMap map = HashMap()
        BufferedReader bufferedReader2 = null
        try {
            bufferedReader = BufferedReader(InputStreamReader(FileInputStream(str)))
        } catch (Exception e) {
            bufferedReader = null
        } catch (Throwable th) {
            th = th
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                String line2 = bufferedReader.readLine()
                if (line2 != null) {
                    map.put(line, line2)
                }
            }
            try {
                bufferedReader.close()
            } catch (IOException e2) {
            }
        } catch (Exception e3) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (IOException e4) {
                }
            }
            return map
        } catch (Throwable th2) {
            bufferedReader2 = bufferedReader
            th = th2
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close()
                } catch (IOException e5) {
                }
            }
            throw th
        }
        return map
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b() {
        Intent intent = Intent(this, (Class<?>) ApkComposeActivity.class)
        intent.setAction("com.gmail.heagoo.action.apkcompose")
        intent.setFlags(131072)
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0)
        Int iIntValue = ((Integer) a.a("com.gmail.heagoo.seticon.SetIcon", "getIconId", (Array<Class>) null, (Array<Object>) null)).intValue()
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), iIntValue)
        String string = getString(R.string.app_name)
        this.p = (NotificationManager) getSystemService("notification")
        if (Build.VERSION.SDK_INT >= 26) {
            this.q = new NotificationCompat.Builder(this, "default")
        } else {
            this.q = new NotificationCompat.Builder(this)
        }
        this.q.setContentTitle(string).setTicker(string).setContentText(getString(R.string.build_ongoing)).setSmallIcon(iIntValue).setLargeIcon(Bitmap.createScaledBitmap(bitmapDecodeResource, 128, 128, false)).setContentIntent(activity).setOngoing(true)
        startForeground(8001, this.q.build())
        this.r = true
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c() {
        if (this.l != null && this.l.isAlive()) {
            this.l.b()
        }
        j jVar = this.m
        jVar.f1213a = false
        jVar.c = null
        jVar.d = null
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun d() throws XPathExpressionException, IOException {
        ResXmlPatcher.a(File(this.f753a, ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME))
        if (this.f754b != null) {
            this.l = m(this, this.f753a, this.f754b, this.k)
        } else {
            this.l = o(this, this.f753a, this.k)
        }
        if (this.o != null) {
            this.l.a(this.o)
        }
        this.l.a(this.c.booleanValue(), this.d.booleanValue(), this.e.booleanValue(), this.f, this.g, this.h, this.i, this.j, this.u)
        this.l.a(this)
        this.l.start()
    }

    @Override // com.gmail.heagoo.common.j
    public final Unit a() {
        com.gmail.heagoo.common.j jVar
        synchronized (this.m) {
            this.m.f1213a = true
            this.m.f1214b = true
            this.m.c = null
        }
        a(true, getString(R.string.build_finished), getString(R.string.succeed))
        if (this.n == null || (jVar = (com.gmail.heagoo.common.j) this.n.get()) == null) {
            return
        }
        jVar.a()
    }

    @Override // com.gmail.heagoo.common.j
    public final Unit a(com.gmail.heagoo.common.k kVar) {
        com.gmail.heagoo.common.j jVar
        synchronized (this.m) {
            this.m.d = kVar
        }
        if (this.n != null && (jVar = (com.gmail.heagoo.common.j) this.n.get()) != null) {
            jVar.a(kVar)
        }
        a(kVar.f1457a == kVar.f1458b, getString(R.string.build_ongoing), String.format(getResources().getString(R.string.step) + " %d/%d: %s", Integer.valueOf(kVar.f1457a), Integer.valueOf(kVar.f1458b), kVar.c))
    }

    @Override // com.gmail.heagoo.common.j
    public final Unit a(String str) {
        com.gmail.heagoo.common.j jVar
        synchronized (this.m) {
            this.m.f1213a = true
            this.m.f1214b = false
            this.m.c = str
        }
        a(true, getString(R.string.build_finished), getString(R.string.failed))
        if (this.n == null || (jVar = (com.gmail.heagoo.common.j) this.n.get()) == null) {
            return
        }
        jVar.a(str)
    }

    @Override // android.app.Service
    fun onBind(Intent intent) {
        return this.t
    }

    @Override // android.app.Service
    fun onCreate() {
        super.onCreate()
    }

    @Override // android.app.Service
    fun onDestroy() {
        super.onDestroy()
    }

    @Override // android.app.Service
    fun onStartCommand(Intent intent, Int i, Int i2) throws XPathExpressionException, IOException {
        if (intent == null) {
            return super.onStartCommand(intent, i, i2)
        }
        this.f753a = a.a(intent, "decodeRootPath")
        this.f754b = a.a(intent, "srcApkPath")
        this.k = a.a(intent, "targetApkPath")
        this.c = Boolean.valueOf(a.a(intent, "stringModified"))
        this.d = Boolean.valueOf(a.a(intent, "manifestModified"))
        this.e = Boolean.valueOf(a.a(intent, "resFileModified"))
        this.f = a.c(intent, "modifiedSmaliFolders")
        this.u = a.b(intent, "signAPK")
        this.g = a.d(intent, "addedFiles")
        this.h = a.d(intent, "replacedFiles")
        this.i = HashSet()
        Iterator it = a.c(intent, "deletedFiles").iterator()
        while (it.hasNext()) {
            this.i.add((String) it.next())
        }
        String strA = a.a(intent, "fileEntry2ZipEntry")
        if (strA != null) {
            this.j = b(strA)
        }
        c()
        b()
        d()
        return 1
    }
}
