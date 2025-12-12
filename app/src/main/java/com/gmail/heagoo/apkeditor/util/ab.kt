package com.gmail.heagoo.apkeditor.util

import jadx.core.codegen.CodeWriter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.List

class ab {

    /* renamed from: a, reason: collision with root package name */
    private Int f1305a

    private fun a(BufferedWriter bufferedWriter, Int i, String str, String str2) {
        bufferedWriter.write("\n")
        for (Int i2 = 0; i2 < i; i2++) {
            bufferedWriter.write(CodeWriter.INDENT)
        }
        bufferedWriter.write("<span class=\"attribute-name\">" + str + "</span>=<a class=\"attribute-value\">" + str2 + "</a>")
    }

    private fun a(BufferedWriter bufferedWriter, Int i, String str, Boolean z) {
        if (z) {
            for (Int i2 = 0; i2 < i; i2++) {
                bufferedWriter.write(CodeWriter.INDENT)
            }
        }
        bufferedWriter.write("&lt;/<span class=\"end-tag\">" + str + "</span>&gt;")
    }

    private fun a(BufferedWriter bufferedWriter, String str) {
        bufferedWriter.write(str.replaceAll("<", "&lt;").replace(">", "&gt;"))
    }

    private fun a(BufferedWriter bufferedWriter, String str, Boolean z) {
        Int i
        Boolean z2
        Int iIndexOf = str.indexOf(60)
        if (iIndexOf > 0) {
            a(bufferedWriter, str.substring(0, iIndexOf))
        } else if (iIndexOf == -1) {
            a(bufferedWriter, str)
            return
        }
        if (str.charAt(iIndexOf + 1) == '/') {
            this.f1305a--
            Int iIndexOf2 = str.indexOf(62, iIndexOf + 2)
            if (iIndexOf2 == -1) {
                a(bufferedWriter, this.f1305a, str.substring(iIndexOf + 2), z)
                return
            }
            a(bufferedWriter, this.f1305a, str.substring(iIndexOf + 2, iIndexOf2), z)
            if (iIndexOf2 + 1 < str.length()) {
                a(bufferedWriter, str.substring(iIndexOf2 + 1), false)
                return
            }
            return
        }
        Int iIndexOf3 = str.indexOf(62, iIndexOf + 2)
        if (iIndexOf3 == -1) {
            a(bufferedWriter, str)
            return
        }
        if (str.charAt(iIndexOf3 - 1) == '/') {
            i = iIndexOf3 - 1
            z2 = true
        } else {
            i = iIndexOf3
            z2 = false
        }
        Array<String> strArrSplit = str.substring(iIndexOf + 1, i).split(" ")
        String str2 = strArrSplit[0]
        Int i2 = this.f1305a
        if (z) {
            for (Int i3 = 0; i3 < i2; i3++) {
                bufferedWriter.write(CodeWriter.INDENT)
            }
        }
        bufferedWriter.write("&lt;<span class=\"start-tag\">" + str2 + "</span>")
        for (Int i4 = 1; i4 < strArrSplit.length; i4++) {
            Int iIndexOf4 = strArrSplit[i4].indexOf("=")
            if (iIndexOf4 != -1) {
                a(bufferedWriter, this.f1305a + 1, strArrSplit[i4].substring(0, iIndexOf4), strArrSplit[i4].substring(iIndexOf4 + 1))
            } else {
                a(bufferedWriter, strArrSplit[i4])
            }
        }
        if (z2) {
            a(bufferedWriter, "/>")
        } else {
            this.f1305a++
            a(bufferedWriter, ">")
        }
        if (iIndexOf3 + 1 < str.length()) {
            a(bufferedWriter, str.substring(iIndexOf3 + 1), false)
        }
    }

    public final Unit a(List list, String str) throws IOException {
        BufferedWriter bufferedWriter = BufferedWriter(FileWriter(File(str)))
        bufferedWriter.write("<html><head>")
        bufferedWriter.write("<title>1.xml</title>")
        bufferedWriter.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"viewsource.css\">")
        bufferedWriter.write("</head>")
        bufferedWriter.write("<body id=\"viewsource\" class=\"wrap\" style=\"-moz-tab-size: 4\">")
        bufferedWriter.write("<pre id=\"line1\">")
        String str2 = (String) list.get(0)
        if (str2 != null) {
            bufferedWriter.write(str2.replaceAll("<", "&lt;").replace(">", "&gt;"))
        }
        Int i = 2
        for (Int i2 = 1; i2 < list.size(); i2++) {
            String strTrim = ((String) list.get(i2)).trim()
            bufferedWriter.write("\n<span id=\"line" + i + "\"></span>")
            a(bufferedWriter, strTrim, true)
            i++
        }
        bufferedWriter.close()
    }
}
