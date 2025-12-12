package com.gmail.heagoo.apkeditor.util

import android.content.Context
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.regex.Matcher
import java.util.regex.Pattern

final class o extends f {
    private List e

    o(String str, String str2) throws Throwable {
        super(str, str2)
        this.e = ArrayList()
        try {
            e()
        } catch (IOException e) {
        }
    }

    private fun a(String str) {
        if (!str.startsWith("/")) {
            str = this.f1313a + str
        }
        for (p pVar : this.e) {
            if (str.equals(pVar.f1325a)) {
                return pVar
            }
        }
        return null
    }

    private fun a(p pVar) throws IOException {
        Int i
        Int iIndexOf
        try {
            List listA = new com.gmail.heagoo.common.w(pVar.f1325a).a()
            for (q qVar : pVar.f1326b) {
                if (qVar.f1327a > 0) {
                    Int i2 = qVar.f1327a - 1
                    String str = (String) listA.get(i2)
                    Int iIndexOf2 = str.indexOf("type=\"")
                    if (iIndexOf2 != -1 && (iIndexOf = str.indexOf(34, (i = iIndexOf2 + 6))) != -1) {
                        qVar.f1328b = str.substring(i, iIndexOf)
                    }
                    listA.set(i2, str.replace("name=\"" + qVar.c + "\"", "name=\"" + qVar.a() + "\""))
                }
            }
            BufferedWriter bufferedWriter = BufferedWriter(FileWriter(pVar.f1325a))
            Iterator it = listA.iterator()
            while (it.hasNext()) {
                bufferedWriter.write((String) it.next())
                bufferedWriter.write("\n")
            }
            bufferedWriter.close()
            this.c.add(pVar.f1325a)
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    private fun a(List list, String str, String str2) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            y yVar = (y) it.next()
            if (str.equals(yVar.f1341a) && str2.equals(yVar.f1342b)) {
                return true
            }
        }
        return false
    }

    private fun b(List list) {
        HashMap map = HashMap()
        Iterator it = list.iterator()
        while (it.hasNext()) {
            q qVar = (q) it.next()
            List arrayList = (List) map.get(qVar.f1328b)
            if (arrayList == null) {
                arrayList = ArrayList()
                map.put(qVar.f1328b, arrayList)
            }
            arrayList.add(y(qVar.f1328b, qVar.c, qVar.a()))
        }
        return map
    }

    private fun e() throws Throwable {
        Pattern patternCompile
        BufferedReader bufferedReader
        BufferedReader bufferedReader2 = null
        try {
            patternCompile = Pattern.compile("^(.+):([0-9]+): error: invalid symbol: '(.+)'")
            bufferedReader = BufferedReader(StringReader(this.f1314b))
        } catch (Throwable th) {
            th = th
        }
        try {
            String line = bufferedReader.readLine()
            while (line != null) {
                Matcher matcher = patternCompile.matcher(line)
                if (matcher.find()) {
                    String strSubstring = line.substring(matcher.start(1), matcher.end(1))
                    String strSubstring2 = line.substring(matcher.start(2), matcher.end(2))
                    String strSubstring3 = line.substring(matcher.start(3), matcher.end(3))
                    try {
                        Int iIntValue = Integer.valueOf(strSubstring2).intValue()
                        p pVarA = a(strSubstring)
                        if (pVarA == null) {
                            this.e.add(p(this.f1313a, strSubstring, iIntValue, strSubstring3, (Byte) 0))
                        } else {
                            pVarA.f1326b.add(q(pVarA, iIntValue, strSubstring3, (Byte) 0))
                        }
                    } catch (Exception e) {
                    }
                }
                line = bufferedReader.readLine()
            }
            a(bufferedReader)
        } catch (Throwable th2) {
            th = th2
            bufferedReader2 = bufferedReader
            a(bufferedReader2)
            throw th
        }
    }

    private fun f() {
        ArrayList arrayList = ArrayList()
        Iterator it = this.e.iterator()
        while (it.hasNext()) {
            for (q qVar : ((p) it.next()).f1326b) {
                if (q.a(qVar) && !a(arrayList, qVar.f1328b, qVar.c)) {
                    arrayList.add(y(qVar.f1328b, qVar.c, qVar.a()))
                }
            }
        }
        return arrayList
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final String a(Context context) {
        StringBuilder sb = StringBuilder()
        if (this.d > 0) {
            sb.append(String.format(context.getString(R.string.str_num_renamed_file), Integer.valueOf(this.d)))
            sb.append("\n")
        }
        if (!this.c.isEmpty()) {
            sb.append(String.format(context.getString(R.string.str_num_modified_file), Integer.valueOf(this.c.size())))
            sb.append("\n")
        }
        sb.deleteCharAt(sb.length() - 1)
        return sb.toString()
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean a() {
        return this.e.size() > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Unit b() throws IOException {
        p pVarA = a(this.f1313a + "res/values/public.xml")
        if (pVarA != null) {
            a(pVarA)
        }
        if (pVarA != null) {
            for (Map.Entry entry : b(pVarA.f1326b).entrySet()) {
                a((String) entry.getKey(), (List) entry.getValue())
            }
        }
        List listF = f()
        if (listF.isEmpty()) {
            return
        }
        a(listF)
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean c() {
        return this.d > 0 || !this.c.isEmpty()
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Map d() {
        return null
    }
}
