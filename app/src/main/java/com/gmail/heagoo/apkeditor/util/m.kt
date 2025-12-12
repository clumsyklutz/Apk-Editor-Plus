package com.gmail.heagoo.apkeditor.util

import android.content.Context
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList
import java.util.List
import java.util.Map

class m extends f {
    private Int e
    private Int f
    private List g

    constructor(String str, String str2) throws Throwable {
        super(str, str2)
        this.e = 0
        this.f = 0
        this.g = ArrayList()
        try {
            e()
        } catch (IOException e) {
        }
    }

    private fun a(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            try {
                bufferedReader.close()
            } catch (IOException e) {
            }
        }
    }

    private fun a(String str) {
        Array<String> strArrSplit = str.split("/")
        Int length = strArrSplit.length
        String str2 = strArrSplit[length - 1]
        String str3 = strArrSplit[length - 2]
        Int iIndexOf = str3.indexOf(45)
        String strSubstring = iIndexOf != -1 ? str3.substring(0, iIndexOf) : str3
        for (Int i = 0; i < this.g.size(); i++) {
            n nVar = (n) this.g.get(i)
            if (str2.equals(nVar.c) && strSubstring.equals(nVar.f1323a)) {
                return true
            }
        }
        return false
    }

    private fun e() throws Throwable {
        BufferedReader bufferedReader
        try {
            bufferedReader = BufferedReader(StringReader(this.f1314b))
        } catch (Throwable th) {
            th = th
            bufferedReader = null
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                Int iIndexOf = line.indexOf(": Invalid file name: must contain only ")
                if (iIndexOf > 0) {
                    String strSubstring = line.substring(0, iIndexOf)
                    if (!a(strSubstring)) {
                        this.g.add(n(strSubstring, (Byte) 0))
                    }
                }
            }
            a(bufferedReader)
        } catch (Throwable th2) {
            th = th2
            a(bufferedReader)
            throw th
        }
    }

    private fun f() {
        ArrayList arrayList = ArrayList()
        for (n nVar : this.g) {
            arrayList.add(y(nVar.f1323a, nVar.f1324b, nVar.e))
        }
        a(arrayList)
    }

    private fun g() throws IOException {
        String str = this.f1313a + "res/values/public.xml"
        try {
            String strB = new com.gmail.heagoo.common.w(str).b()
            for (Int i = 0; i < this.g.size(); i++) {
                n nVar = (n) this.g.get(i)
                strB = strB.replace("\"" + nVar.f1324b + "\"", "\"" + nVar.e + "\"")
            }
            FileOutputStream fileOutputStream = FileOutputStream(str)
            fileOutputStream.write(strB.getBytes())
            fileOutputStream.close()
            this.f++
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final String a(Context context) {
        StringBuilder sb = StringBuilder()
        if (this.e > 0) {
            sb.append(String.format(context.getString(R.string.str_num_renamed_file), Integer.valueOf(this.e)))
            sb.append("\n")
        }
        if (this.f > 0) {
            sb.append(String.format(context.getString(R.string.str_num_modified_file), Integer.valueOf(this.f)))
            sb.append("\n")
        }
        sb.deleteCharAt(sb.length() - 1)
        return sb.toString()
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean a() {
        return this.g.size() > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Unit b() throws IOException {
        Array<File> fileArrListFiles
        Array<File> fileArrListFiles2 = File(this.f1313a + "res").listFiles()
        for (Int i = 0; i < this.g.size(); i++) {
            n nVar = (n) this.g.get(i)
            n.a(nVar, i)
            if (fileArrListFiles2 != null) {
                for (File file : fileArrListFiles2) {
                    if (!file.isFile() && file.getName().startsWith(nVar.f1323a) && (fileArrListFiles = file.listFiles()) != null) {
                        for (File file2 : fileArrListFiles) {
                            if (!file2.isDirectory() && file2.getName().equals(nVar.c)) {
                                file2.renameTo(File(file, nVar.d))
                                this.e++
                            }
                        }
                    }
                }
            }
        }
        g()
        f()
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Boolean c() {
        return this.e > 0 || this.f > 0
    }

    @Override // com.gmail.heagoo.apkeditor.util.f
    public final Map d() {
        return null
    }
}
