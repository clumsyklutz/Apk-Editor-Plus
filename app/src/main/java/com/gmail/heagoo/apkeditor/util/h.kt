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

class h extends f {
    private Int e
    private List f
    private Map g

    constructor(String str, String str2) throws Throwable {
        super(str, str2)
        this.e = 0
        this.f = ArrayList()
        this.g = HashMap()
        try {
            e()
        } catch (IOException e) {
        }
    }

    private fun a(String str) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.f.size()) {
                return null
            }
            k kVar = (k) this.f.get(i2)
            if (str.equals(kVar.f1321a)) {
                return kVar
            }
            i = i2 + 1
        }
    }

    private fun e() throws Throwable {
        BufferedReader bufferedReader = null
        try {
            Pattern patternCompile = Pattern.compile("^(.+):([0-9]+): Tag (.+) attribute (.+) has invalid character '")
            BufferedReader bufferedReader2 = BufferedReader(StringReader(this.f1314b))
            try {
                String line = bufferedReader2.readLine()
                while (line != null) {
                    Matcher matcher = patternCompile.matcher(line)
                    if (matcher.find()) {
                        String strSubstring = line.substring(matcher.start(1), matcher.end(1))
                        String strSubstring2 = line.substring(matcher.start(2), matcher.end(2))
                        String strSubstring3 = line.substring(matcher.start(3), matcher.end(3))
                        String strSubstring4 = line.substring(matcher.start(4), matcher.end(4))
                        try {
                            Int iIntValue = Integer.valueOf(strSubstring2).intValue()
                            k kVarA = a(strSubstring)
                            if (kVarA == null) {
                                this.f.add(k(this.f1313a, strSubstring, iIntValue, strSubstring3, strSubstring4, (Byte) 0))
                            } else {
                                kVarA.f1322b.add(j(iIntValue > 0 ? iIntValue - 1 : 0, strSubstring3, strSubstring4, (Byte) 0))
                            }
                        } catch (Exception e) {
                        }
                    }
                    line = bufferedReader2.readLine()
                }
                bufferedReader2.close()
                for (Int i = 0; i < this.f.size(); i++) {
                    ((k) this.f.get(i)).a()
                }
            } catch (Throwable th) {
                th = th
                bufferedReader = bufferedReader2
                if (bufferedReader != null) {
                    bufferedReader.close()
                }
                throw th
            }
        } catch (Throwable th2) {
            th = th2
        }
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final String a(Context context) {
        return String.format(context.getString(R.string.str_num_modified_file), Integer.valueOf(this.e))
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean a() {
        return this.f.size() > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Unit b() throws Throwable {
        BufferedWriter bufferedWriter
        List listA
        Boolean z
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= this.f.size()) {
                return
            }
            k kVar = (k) this.f.get(i2)
            BufferedWriter bufferedWriter2 = null
            try {
                listA = new com.gmail.heagoo.common.w(kVar.f1321a).a()
                for (Int i3 = 0; i3 < listA.size(); i3++) {
                    i iVarA = kVar.a(i3)
                    if (iVarA != null) {
                        String str = kVar.f1321a
                        String str2 = (String) listA.get(i3)
                        List list = iVarA.f1318b
                        Array<String> strArrSplit = str2.split(" ")
                        for (Int i4 = 0; i4 < strArrSplit.length; i4++) {
                            Array<String> strArrSplit2 = strArrSplit[i4].split("=")
                            if (strArrSplit2.length == 2) {
                                String str3 = strArrSplit2[0]
                                Iterator it = list.iterator()
                                while (true) {
                                    if (!it.hasNext()) {
                                        z = false
                                        break
                                    }
                                    String str4 = (String) it.next()
                                    if (!str3.equals(str4)) {
                                        if (str3.endsWith(":" + str4)) {
                                            z = true
                                            break
                                        }
                                    } else {
                                        z = true
                                        break
                                    }
                                }
                                if (z) {
                                    Array<String> strArr = new String[2]
                                    StringBuilder sbAppend = StringBuilder().append(strArrSplit2[0]).append("=")
                                    String str5 = strArrSplit2[1]
                                    Int i5 = 0
                                    String str6 = ""
                                    if (str5.startsWith("\"")) {
                                        i5 = 1
                                        str6 = "\""
                                    }
                                    Int iLastIndexOf = str5.lastIndexOf("\"")
                                    if (iLastIndexOf != -1) {
                                        String strSubstring = str5.substring(iLastIndexOf)
                                        if (iLastIndexOf > i5) {
                                            String strSubstring2 = str5.substring(i5, iLastIndexOf)
                                            String strB = d.b(strSubstring2)
                                            strArr[0] = strSubstring2
                                            strArr[1] = strB
                                            str5 = str6 + strB + strSubstring
                                        }
                                    }
                                    strArrSplit[i4] = sbAppend.append(str5).toString()
                                    if (strArr[1] != null) {
                                        String str7 = strArr[0]
                                        String str8 = strArr[1]
                                        Map map = (Map) this.g.get(str)
                                        if (map == null) {
                                            map = HashMap()
                                            this.g.put(str, map)
                                        }
                                        map.put(str7, str8)
                                    }
                                }
                            }
                        }
                        StringBuilder sb = StringBuilder()
                        for (String str9 : strArrSplit) {
                            sb.append(str9)
                            sb.append(" ")
                        }
                        sb.deleteCharAt(sb.length() - 1)
                        listA.set(i3, sb.toString())
                    }
                }
                bufferedWriter = BufferedWriter(FileWriter(kVar.f1321a))
            } catch (IOException e) {
                e = e
                bufferedWriter = null
            } catch (Throwable th) {
                th = th
            }
            try {
                try {
                    Iterator it2 = listA.iterator()
                    while (it2.hasNext()) {
                        bufferedWriter.write((String) it2.next())
                        bufferedWriter.write("\n")
                    }
                    this.e++
                    d.a(bufferedWriter)
                } catch (Throwable th2) {
                    th = th2
                    bufferedWriter2 = bufferedWriter
                    d.a(bufferedWriter2)
                    throw th
                }
            } catch (IOException e2) {
                e = e2
                e.printStackTrace()
                d.a(bufferedWriter)
                i = i2 + 1
            }
            i = i2 + 1
        }
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean c() {
        return this.e > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Map d() {
        return this.g
    }
}
