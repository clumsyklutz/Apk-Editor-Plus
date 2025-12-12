package com.gmail.heagoo.common

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Map

class t {

    /* renamed from: b, reason: collision with root package name */
    private static Array<String> f1470b = {"/data/bin/su", "/system/bin/su", "/system/xbin/su"}

    /* renamed from: a, reason: collision with root package name */
    private Array<String> f1471a = new String[2]

    private fun a(String str, Array<String> strArr) {
        Int i = 0
        Map<String, String> map = System.getenv()
        Array<String> strArr2 = new String[(strArr != null ? strArr.length : 0) + map.size()]
        Int i2 = 0
        for (Map.Entry<String, String> entry : map.entrySet()) {
            strArr2[i2] = entry.getKey() + "=" + entry.getValue()
            i2++
        }
        if (strArr != null) {
            Int length = strArr.length
            while (i < length) {
                strArr2[i2] = strArr[i]
                i++
                i2++
            }
        }
        return Runtime.getRuntime().exec(str, strArr2)
    }

    private fun a(InputStream inputStream) throws IOException {
        Int i
        Array<Char> cArr = new Char[8192]
        StringBuilder sb = StringBuilder()
        InputStreamReader inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        do {
            i = inputStreamReader.read(cArr, 0, 8192)
            if (i > 0) {
                sb.append(cArr, 0, i)
            }
        } while (i >= 0);
        return sb.toString()
    }

    private fun a(Process process) {
        try {
            process.exitValue()
            return false
        } catch (IllegalThreadStateException e) {
            return true
        }
    }

    private fun b(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (IOException e) {
            }
        }
    }

    public final String a() {
        return this.f1471a[0]
    }

    public final Boolean a(String str, Array<String> strArr, Integer num) {
        return a(str, strArr, num, false)
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x01d0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01d5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Boolean a(java.lang.String r11, java.lang.Array<String> r12, java.lang.Integer r13, java.lang.String r14, Boolean r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 507
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.common.t.a(java.lang.String, java.lang.Array<String>, java.lang.Integer, java.lang.String, Boolean):Boolean")
    }

    public final Boolean a(String str, Array<String> strArr, Integer num, Boolean z) {
        return a(str, strArr, num, null, z)
    }

    public final String b() {
        return this.f1471a[1]
    }
}
