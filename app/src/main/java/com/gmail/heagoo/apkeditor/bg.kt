package com.gmail.heagoo.apkeditor

import android.app.Activity
import android.content.Context
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.a.c.a
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

class bg extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f888a = false

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f889b
    private WeakReference c
    private String d
    private String e
    private a.a.b.a.e f
    private a g
    private String h
    private ApkDecoderMine i
    private com.gmail.heagoo.apkeditor.f.d j
    private Boolean k
    private Boolean l

    constructor(Activity activity, bf bfVar, String str, String str2, Boolean z) {
        this.f889b = WeakReference(activity)
        this.c = WeakReference(bfVar)
        this.d = str
        this.e = str2
        this.l = z
    }

    private fun a(Context context, String str, Array<Byte> bArr) throws IOException {
        Int i
        InputStream inputStreamOpen = context.getAssets().open(str)
        Int i2 = inputStreamOpen.read(bArr)
        while (i2 < bArr.length && (i = inputStreamOpen.read(bArr, i2, bArr.length - i2)) != -1) {
            i2 += i
        }
        inputStreamOpen.close()
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private a.a.b.a.Array<e> a(a.a.b.c.a r9, com.gmail.heagoo.a.c.a r10, Boolean r11) throws java.lang.Throwable {
        /*
            r8 = this
            r1 = 0
            java.util.zip.ZipFile r6 = new java.util.zip.ZipFile     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L69
            r6.<init>(r9)     // Catch: java.io.IOException -> L3d java.lang.Throwable -> L69
            java.lang.String r0 = "resources.arsc"
            java.util.zip.ZipEntry r0 = r6.getEntry(r0)     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            if (r0 == 0) goto L38
            Long r2 = r0.getSize()     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            Int r2 = (Int) r2     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            Array<Byte> r2 = new Byte[r2]     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            java.io.InputStream r0 = r6.getInputStream(r0)     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            com.gmail.heagoo.a.c.a.a(r0, r2)     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L6c java.io.IOException -> L73
            r1 = 0
            com.gmail.heagoo.apkeditor.f.d r4 = r8.j     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L76
            Boolean r5 = r8.k     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L76
            r2 = r11
            r3 = r10
            a.a.b.b.b r1 = a.a.b.b.a.a(r0, r1, r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L76
            a.a.b.a.Array<e> r1 = r1.a()     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L76
            r6.close()     // Catch: java.io.IOException -> L5f
        L33:
            r0.close()     // Catch: java.io.IOException -> L61
        L36:
            r0 = r1
        L37:
            return r0
        L38:
            r6.close()     // Catch: java.io.IOException -> L63
        L3b:
            r0 = r1
            goto L37
        L3d:
            r0 = move-exception
            r2 = r1
        L3f:
            a.a.a r3 = new a.a.a     // Catch: java.lang.Throwable -> L54
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L54
            java.lang.String r5 = "Could not read resources.arsc from file: "
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L54
            java.lang.StringBuilder r4 = r4.append(r9)     // Catch: java.lang.Throwable -> L54
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L54
            r3.<init>(r4, r0)     // Catch: java.lang.Throwable -> L54
            throw r3     // Catch: java.lang.Throwable -> L54
        L54:
            r0 = move-exception
            r6 = r2
        L56:
            r6.close()     // Catch: java.io.IOException -> L65
        L59:
            if (r1 == 0) goto L5e
            r1.close()     // Catch: java.io.IOException -> L67
        L5e:
            throw r0
        L5f:
            r2 = move-exception
            goto L33
        L61:
            r0 = move-exception
            goto L36
        L63:
            r0 = move-exception
            goto L3b
        L65:
            r2 = move-exception
            goto L59
        L67:
            r1 = move-exception
            goto L5e
        L69:
            r0 = move-exception
            r6 = r1
            goto L56
        L6c:
            r0 = move-exception
            goto L56
        L6e:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L56
        L73:
            r0 = move-exception
            r2 = r6
            goto L3f
        L76:
            r1 = move-exception
            r2 = r6
            r7 = r0
            r0 = r1
            r1 = r7
            goto L3f
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.bg.a(a.a.b.c.a, com.gmail.heagoo.a.c.a, Boolean):a.a.b.a.Array<e>")
    }

    private fun c() throws Throwable {
        Array<Byte> bArr
        Array<Byte> bArr2
        Array<Byte> bArr3
        try {
            Activity activity = (Activity) this.f889b.get()
            if (this.l) {
                bArr = null
                bArr2 = null
                bArr3 = null
            } else {
                Array<Byte> bArr4 = new Byte[68]
                a(activity, "carlos_png.png", bArr4)
                Array<Byte> bArr5 = new Byte[667]
                a(activity, "carlos_jpg.jpg", bArr5)
                Array<Byte> bArr6 = new Byte[97]
                a(activity, "carlos.9.png", bArr6)
                bArr3 = bArr4
                bArr2 = bArr5
                bArr = bArr6
            }
            a.a.b.c.a aVar = new a.a.b.c.a(File(this.d))
            a.c.d dVarA = aVar.a()
            this.k = !dVarA.a("res")
            if (this.k && (dVarA.a("r") || dVarA.a("R"))) {
                try {
                    InputStream inputStreamB = dVarA.b(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)
                    com.gmail.heagoo.a.b bVar = new com.gmail.heagoo.a.b()
                    bVar.a(inputStreamB, "manifest", "package")
                    String strA = bVar.a()
                    inputStreamB.close()
                    InputStream inputStreamB2 = dVarA.b("classes.dex")
                    String strD = a.d(activity, "tmp")
                    String str = strD + "classes.dex"
                    FileOutputStream fileOutputStream = FileOutputStream(str)
                    a.b(inputStreamB2, fileOutputStream)
                    inputStreamB2.close()
                    fileOutputStream.close()
                    a.a("com.gmail.heagoo.apkeditor.pro.ResourceDecoder", "decodeResources", new Array<Class>{String.class, String.class}, new Array<Object>{str, strD})
                    this.j = new com.gmail.heagoo.apkeditor.f.d(strD, strA)
                } catch (Exception e) {
                }
            }
            a aVar2 = a(((Activity) this.f889b.get()).getApplicationContext(), this.k)
            a.a.b.a.Array<e> eVarArrA = a(aVar, aVar2, false)
            if (eVarArrA != null) {
                switch (eVarArrA.length) {
                    case 1:
                        this.f = eVarArrA[0]
                        break
                    case 2:
                        if (eVarArrA[0].f().equals("android") || eVarArrA[0].f().equals("com.htc")) {
                            this.f = eVarArrA[1]
                            break
                        }
                        break
                    default:
                        this.f = selectPkgWithMostResSpecs(eVarArrA)
                        break
                }
                if (this.f == null) {
                    throw new a.a.ExceptionA("Arsc files with zero packages")
                }
                aVar2.a(this.f, true)
                a.a.b.a.e eVar = this.f
            }
            this.g = aVar2
            if (this.c.get() != null) {
                ((bf) this.c.get()).c(true)
            }
            this.i = ApkDecoderMine(this.g, bArr3, bArr2, bArr)
            com.gmail.heagoo.common.h.a(File(this.e))
            m.a((Context) this.f889b.get())
            File file = File(this.e)
            if (!file.exists()) {
                file.mkdirs()
            }
            this.i.a(aVar, file)
            if (this.c.get() != null) {
                ((bf) this.c.get()).a(this.i.a())
            }
            return true
        } catch (Exception e2) {
            this.h = e2.getMessage()
            e2.printStackTrace()
            return false
        }
    }

    private a.a.b.a.e selectPkgWithMostResSpecs(a.a.b.a.Array<e> eVarArr) {
        Int iE = 0
        Int size = 0
        Int i = 0
        for (Int i2 = 0; i2 < eVarArr.length; i2++) {
            a.a.b.a.e eVar = eVarArr[i2]
            if (eVar.getSize() > size) {
                size = eVar.getSize()
                iE = eVar.e()
                i = i2
            }
        }
        return iE == 0 ? eVarArr[0] : eVarArr[i]
    }

    public final a.a.b.a.e a() {
        return this.f
    }

    public final Unit b() {
        if (this.i != null) {
            this.i.b()
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        m mVar = m((Context) this.f889b.get(), null, null, null)
        if (!mVar.a()) {
            ((bf) this.c.get()).e(mVar.c())
        } else {
            if (c() || this.c.get() == null) {
                return
            }
            ((bf) this.c.get()).e(this.h)
        }
    }
}
