package com.gmail.heagoo.apkeditor.util

import android.content.Context
import com.gmail.heagoo.apkeditor.e.ad
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.regex.Matcher
import java.util.regex.Pattern

class l extends f {
    private Map e
    private Map f
    private Map g

    constructor(String str, String str2) throws Throwable {
        super(str, str2)
        this.e = HashMap()
        this.f = HashMap()
        this.g = HashMap()
        try {
            e()
        } catch (IOException e) {
        }
    }

    private fun e() throws Throwable {
        BufferedReader bufferedReader
        Pattern patternCompile
        try {
            patternCompile = Pattern.compile("^(.+): error: File is case-insensitive equivalent to: (.+)")
            bufferedReader = BufferedReader(StringReader(this.f1314b))
        } catch (Throwable th) {
            th = th
            bufferedReader = null
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                Matcher matcher = patternCompile.matcher(line)
                if (matcher.find()) {
                    this.e.put(matcher.group(1), matcher.group(2))
                }
            }
            bufferedReader.close()
        } catch (Throwable th2) {
            th = th2
            if (bufferedReader != null) {
                bufferedReader.close()
            }
            throw th
        }
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
        return !this.e.isEmpty()
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Unit b() {
        Boolean z
        String str = this.f1313a + "res/values/public.xml"
        try {
            List listA = new com.gmail.heagoo.common.w(str).a()
            for (Int i = 0; i < listA.size(); i++) {
                ad adVarA = ad.a((String) listA.get(i))
                if (adVarA != null) {
                    String str2 = adVarA.f987a
                    String str3 = adVarA.f988b
                    String lowerCase = str3.toLowerCase()
                    Set hashSet = (Set) this.f.get(str2)
                    if (hashSet == null) {
                        hashSet = HashSet()
                        this.f.put(str2, hashSet)
                    }
                    if (hashSet.contains(lowerCase)) {
                        z = false
                    } else {
                        hashSet.add(lowerCase)
                        z = true
                    }
                    if (!z) {
                        String str4 = str3 + "_" + com.gmail.heagoo.common.s.a(6)
                        List arrayList = (List) this.g.get(str2)
                        if (arrayList == null) {
                            arrayList = ArrayList()
                            this.g.put(str2, arrayList)
                        }
                        arrayList.add(y(str2, str3, str4))
                        adVarA.f988b = str4
                        listA.set(i, adVarA.toString())
                    }
                }
            }
            BufferedOutputStream bufferedOutputStream = BufferedOutputStream(FileOutputStream(str))
            StringBuilder sb = StringBuilder()
            Iterator it = listA.iterator()
            while (it.hasNext()) {
                sb.append((String) it.next())
                sb.append("\n")
            }
            bufferedOutputStream.write(sb.toString().getBytes())
            bufferedOutputStream.close()
            this.c.add(str)
        } catch (IOException e) {
            e.printStackTrace()
        }
        for (Map.Entry entry : this.g.entrySet()) {
            a((String) entry.getKey(), (List) entry.getValue())
        }
        if (this.g.isEmpty()) {
            return
        }
        ArrayList arrayList2 = ArrayList()
        Iterator it2 = this.g.values().iterator()
        while (it2.hasNext()) {
            arrayList2.addAll((List) it2.next())
        }
        a(arrayList2)
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
