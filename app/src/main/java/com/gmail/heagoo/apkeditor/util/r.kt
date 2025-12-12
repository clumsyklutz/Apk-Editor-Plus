package com.gmail.heagoo.apkeditor.util

import android.content.Context
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map

class r extends f {
    private Int e
    private Map f
    private List g

    constructor(String str, String str2) throws Throwable {
        super(str, str2)
        this.e = 0
        this.f = HashMap()
        this.g = ArrayList()
        try {
            e()
        } catch (IOException e) {
        }
    }

    private fun a(String str) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.g.size()) {
                return null
            }
            s sVar = (s) this.g.get(i2)
            if (str.equals(sVar.f1329a)) {
                return sVar
            }
            i = i2 + 1
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(java.lang.String r12, java.util.Map r13) throws java.lang.Throwable {
        /*
            r11 = this
            r1 = 1
            r2 = 0
            r3 = 0
            java.util.HashMap r7 = new java.util.HashMap     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r7.<init>()     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            com.gmail.heagoo.common.w r0 = new com.gmail.heagoo.common.w     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r0.<init>(r12)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.util.List r8 = r0.a()     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r6 = r1
        L12:
            Int r0 = r8.size()     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            if (r6 >= r0) goto L91
            java.lang.Object r0 = r8.get(r6)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r4 = 60
            Int r5 = r0.indexOf(r4)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r4 = -1
            if (r5 == r4) goto Ldb
            java.lang.String r4 = ">"
            Boolean r4 = r0.endsWith(r4)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            if (r4 == 0) goto Ldb
            Int r4 = r5 + 1
            Char r4 = r0.charAt(r4)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r9 = 47
            if (r4 != r9) goto L88
            Int r5 = r5 + 2
            Int r4 = r0.length()     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            Int r4 = r4 + (-1)
        L41:
            if (r4 <= r5) goto Ldb
            java.lang.String r9 = r0.substring(r5, r4)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.String r10 = "^[a-zA-Z0-9._]+$"
            Boolean r10 = r9.matches(r10)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            if (r10 != 0) goto Ldb
            r2 = 0
            java.lang.String r2 = r0.substring(r2, r5)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.String r4 = r0.substring(r4)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.Object r0 = r13.get(r9)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            if (r0 != 0) goto L67
            java.lang.String r0 = com.gmail.heagoo.apkeditor.util.d.b(r9)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r13.put(r9, r0)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
        L67:
            r7.put(r9, r0)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r5.<init>()     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r8.set(r6, r0)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r0 = r1
        L83:
            Int r2 = r6 + 1
            r6 = r2
            r2 = r0
            goto L12
        L88:
            r4 = 32
            Int r4 = r0.indexOf(r4, r5)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            Int r5 = r5 + 1
            goto L41
        L91:
            if (r2 == 0) goto Ld9
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.io.FileWriter r0 = new java.io.FileWriter     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r0.<init>(r12)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            r1.<init>(r0)     // Catch: java.lang.Throwable -> Lcf java.io.IOException -> Ld7
            java.util.Iterator r2 = r8.iterator()     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
        La1:
            Boolean r0 = r2.hasNext()     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            if (r0 == 0) goto Lbf
            java.lang.Object r0 = r2.next()     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            r1.write(r0)     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            java.lang.String r0 = "\n"
            r1.write(r0)     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            goto La1
        Lb6:
            r0 = move-exception
            r3 = r1
        Lb8:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Lcf
            com.gmail.heagoo.apkeditor.util.d.a(r3)
        Lbe:
            return
        Lbf:
            java.util.Map r0 = r11.f     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            r0.put(r12, r7)     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            Int r0 = r11.e     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            Int r0 = r0 + 1
            r11.e = r0     // Catch: java.io.IOException -> Lb6 java.lang.Throwable -> Ld4
            r0 = r1
        Lcb:
            com.gmail.heagoo.apkeditor.util.d.a(r0)
            goto Lbe
        Lcf:
            r0 = move-exception
        Ld0:
            com.gmail.heagoo.apkeditor.util.d.a(r3)
            throw r0
        Ld4:
            r0 = move-exception
            r3 = r1
            goto Ld0
        Ld7:
            r0 = move-exception
            goto Lb8
        Ld9:
            r0 = r3
            goto Lcb
        Ldb:
            r0 = r2
            goto L83
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.util.r.a(java.lang.String, java.util.Map):Unit")
    }

    private fun e() throws Throwable {
        BufferedReader bufferedReader
        try {
            bufferedReader = BufferedReader(StringReader(this.f1314b))
            try {
                String line = bufferedReader.readLine()
                while (line != null) {
                    Int iIndexOf = line.indexOf(": error: Error parsing XML: not well-formed (invalid token)")
                    if (iIndexOf > 0) {
                        String strSubstring = line.substring(0, iIndexOf)
                        Int iLastIndexOf = strSubstring.lastIndexOf(58)
                        if (iLastIndexOf != -1) {
                            String strSubstring2 = strSubstring.substring(0, iLastIndexOf)
                            try {
                                Int iIntValue = Integer.valueOf(strSubstring.substring(iLastIndexOf + 1)).intValue()
                                if (a(strSubstring2) == null) {
                                    this.g.add(s(strSubstring2, iIntValue))
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    line = bufferedReader.readLine()
                }
                bufferedReader.close()
            } catch (Throwable th) {
                th = th
                if (bufferedReader != null) {
                    bufferedReader.close()
                }
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            bufferedReader = null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final String a(Context context) {
        return String.format(context.getString(R.string.str_num_modified_file), Integer.valueOf(this.e))
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean a() {
        return this.g.size() > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Unit b() throws Throwable {
        HashMap map = HashMap()
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.g.size()) {
                return
            }
            a(((s) this.g.get(i2)).f1329a, map)
            i = i2 + 1
        }
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean c() {
        return this.e > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Map d() {
        return this.f
    }
}
