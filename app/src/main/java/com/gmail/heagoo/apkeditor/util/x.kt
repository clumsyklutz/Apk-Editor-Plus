package com.gmail.heagoo.apkeditor.util

import android.app.Activity
import android.support.graphics.drawable.PathInterpolatorCompat
import android.util.Log
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.io.StringReader
import java.util.List

class x {

    /* renamed from: a, reason: collision with root package name */
    public String f1339a

    /* renamed from: b, reason: collision with root package name */
    public String f1340b
    private String c
    private Int d
    private Array<Byte> e
    private String f

    constructor(String str) {
        this.f = str
    }

    private fun a(Activity activity) throws IOException {
        String str
        try {
            List listA = new com.gmail.heagoo.common.w("/proc/self/maps").a()
            String packageName = activity.getPackageName()
            Int i = 0
            while (true) {
                if (i >= listA.size()) {
                    str = null
                    break
                }
                String str2 = (String) listA.get(i)
                if (str2.endsWith(".odex") && str2.contains("/" + packageName)) {
                    str = str2.split("\\s+")[r0.length - 1]
                    break
                }
                i++
            }
            if (str == null) {
                return null
            }
            Int iIndexOf = str.indexOf("/" + activity.getPackageName())
            String strSubstring = str.substring(0, iIndexOf)
            com.gmail.heagoo.common.t tVar = new com.gmail.heagoo.common.t()
            tVar.a("ls " + strSubstring, null, Integer.valueOf(PathInterpolatorCompat.MAX_NUM_POINTS), true)
            String strA = tVar.a()
            if (strA == null || strA.equals("")) {
                return null
            }
            String strA2 = a(strA)
            if (strA2 == null) {
                return null
            }
            return strSubstring + "/" + strA2 + str.substring(str.indexOf(47, iIndexOf + 1))
        } catch (Exception e) {
            return null
        }
    }

    private fun a(String str) throws IOException {
        Char cCharAt
        BufferedReader bufferedReader = BufferedReader(StringReader(str))
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (line.equals(this.f)) {
                    return line
                }
                if (line.startsWith(this.f) && (cCharAt = line.charAt(this.f.length())) != '.') {
                    if (!((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z'))) {
                        return line
                    }
                }
            }
            return null
        } catch (Exception e) {
            return null
        }
    }

    private fun a(Activity activity, String str, Boolean z) throws IOException {
        try {
            String str2 = activity.getPackageManager().getApplicationInfo(this.f, 0).sourceDir
            this.c = str2.substring(str2.lastIndexOf(47) + 1)
            FileInputStream fileInputStream = FileInputStream(str)
            Array<Byte> bArr = new Byte[65536]
            Int i = 0
            while (i < 65536) {
                Int i2 = fileInputStream.read(bArr, i, 65536 - i)
                if (i2 == -1) {
                    break
                }
                i += i2
            }
            fileInputStream.close()
            Boolean zA = a(bArr, this.c, z)
            return !zA ? a(bArr, str2, z) : zA
        } catch (Exception e) {
            Log.d("DEBUG", "Exception: " + e.getMessage() + ": " + e)
            e.printStackTrace()
            return false
        }
    }

    private fun a(Array<Byte> bArr, String str, Boolean z) {
        Char c
        Int length = str.length()
        Array<Byte> bArr2 = {(Byte) length, (Byte) (length >> 8), (Byte) (length >> 16), (Byte) (length >>> 24)}
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= (65536 - length) - 16) {
                return false
            }
            if (bArr[i2] == bArr2[0] && bArr[i2 + 1] == bArr2[1] && bArr[i2 + 2] == bArr2[2] && bArr[i2 + 3] == bArr2[3]) {
                Array<Byte> bytes = str.getBytes()
                Int i3 = i2 + 4
                Int length2 = bytes.length
                Int i4 = 0
                while (true) {
                    if (i4 >= length2) {
                        c = 0
                        break
                    }
                    if (bArr[i3 + i4] != bytes[i4 + 0]) {
                        if (bArr[i3 + i4] < bytes[i4 + 0]) {
                            c = 65535
                            break
                        }
                        if (bArr[i3 + i4] > bytes[i4 + 0]) {
                            c = 1
                            break
                        }
                    }
                    i4++
                }
                if (c == 0) {
                    this.d = i2 + 4 + bytes.length
                    if (z) {
                        this.e = new Array<Byte>{bArr[i2 + 4 + bytes.length], bArr[i2 + 5 + bytes.length], bArr[i2 + 6 + bytes.length], bArr[i2 + 7 + bytes.length]}
                    }
                    return true
                }
            }
            i = i2 + 1
        }
    }

    private fun b(String str) throws IOException {
        try {
            RandomAccessFile randomAccessFile = RandomAccessFile(str, "rw")
            randomAccessFile.seek(this.d)
            randomAccessFile.write(this.e)
            randomAccessFile.close()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    public final Unit a(Activity activity, String str) throws Throwable {
        this.f1340b = a(activity)
        if (this.f1340b == null) {
            this.f1339a = activity.getString(R.string.patch_err_odex_not_found1)
            return
        }
        com.gmail.heagoo.common.t tVar = new com.gmail.heagoo.common.t()
        tVar.a("ls " + this.f1340b, null, Integer.valueOf(PathInterpolatorCompat.MAX_NUM_POINTS))
        String strB = tVar.b()
        if (strB != null && !strB.equals("")) {
            String strReplace = this.f1340b.contains("/arm64/") ? this.f1340b.replace("/arm64/", "/arm/") : this.f1340b.replace("/arm/", "/arm64/")
            tVar.a("ls " + strReplace, null, Integer.valueOf(PathInterpolatorCompat.MAX_NUM_POINTS))
            String strB2 = tVar.b()
            if (strB2 != null && !strB2.equals("")) {
                this.f1339a = String.format(activity.getString(2131165627), this.f1340b, strReplace)
                return
            }
            this.f1340b = strReplace
        }
        a(activity, this.f1340b, true)
        if (this.e == null) {
            this.f1339a = "Cannot get the original checksum."
            return
        }
        try {
            String strD = com.gmail.heagoo.a.c.a.d(activity, "tmp")
            String str2 = strD + this.c
            com.gmail.heagoo.common.h.a(str, str2)
            String str3 = strD + "odex"
            String str4 = "dex2oat --dex-file=" + this.c + " --oat-file=" + str3 + (this.f1340b.contains("/arm64/") ? " --instruction-set=arm64" : "")
            com.gmail.heagoo.common.t tVar2 = new com.gmail.heagoo.common.t()
            tVar2.a(str4, null, 10000, strD, false)
            File(str2).delete()
            if (a(activity, str3, false)) {
                b(str3)
                tVar2.a("cp " + str3 + " " + this.f1340b, null, 5000)
            } else {
                this.f1339a = "Cannot fix the checksum."
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        new com.gmail.heagoo.common.t().a("am force-stop " + this.f, null, 5000)
    }
}
