package com.gmail.heagoo.apkeditor.util

import jadx.core.codegen.CodeWriter
import jadx.core.deobf.Deobfuscator
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.ArrayList
import java.util.List

class z {

    /* renamed from: a, reason: collision with root package name */
    private BufferedReader f1343a

    /* renamed from: b, reason: collision with root package name */
    private BufferedWriter f1344b
    private List c = ArrayList()
    private Boolean d = false

    constructor(String str) throws IOException {
        try {
            this.f1343a = BufferedReader(FileReader(str))
            String line = this.f1343a.readLine()
            while (line != null) {
                this.c.add(line)
                line = this.f1343a.readLine()
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    private fun b(String str) {
        Int iIndexOf
        Int i = 0
        if (str.startsWith("\"")) {
            if (str.endsWith("\"")) {
                return "<font color=\"blue\">" + c(str) + "</font>"
            }
            this.d = true
            return "<font color=\"blue\">" + c(str)
        }
        if (str.endsWith("\"")) {
            this.d = false
            return c(str) + "</font>"
        }
        if (this.d) {
            return c(str)
        }
        if (str.startsWith("L") && (iIndexOf = str.indexOf(59)) != -1) {
            return "L<font color=\"red\">" + str.substring(1, iIndexOf) + "</font>;" + str.substring(iIndexOf + 1)
        }
        StringBuilder sb = StringBuilder()
        while (i < str.length()) {
            Char cCharAt = str.charAt(i)
            if (cCharAt == '>') {
                sb.append("&gt;")
            } else if (cCharAt == '<') {
                sb.append("&lt;")
            } else if ((cCharAt == 'v' || cCharAt == 'p') && i + 1 < str.length() && Character.isDigit(str.charAt(i + 1))) {
                sb.append("<font color=\"red\">")
                sb.append(cCharAt)
                sb.append(str.charAt(i + 1))
                i++
                if (i + 1 < str.length() && Character.isDigit(str.charAt(i + 1))) {
                    sb.append(str.charAt(i + 1))
                    i++
                }
                sb.append("</font>")
            } else {
                sb.append(cCharAt)
            }
            i++
        }
        return sb.toString()
    }

    private fun c(String str) {
        return str.replaceAll("<", "&lt;").replace(">", "&gt;")
    }

    public final Unit a(String str) throws IOException {
        Int i
        this.f1344b = BufferedWriter(FileWriter(str))
        this.f1344b.write("<html>")
        this.f1344b.write("<head>")
        this.f1344b.write("<title>1.xml</title>")
        this.f1344b.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"viewsource.css\">")
        this.f1344b.write("</head>")
        this.f1344b.write("<body id=\"viewsource\" class=\"wrap\" style=\"-moz-tab-size: 4\">")
        this.f1344b.write("<pre id=\"line1\">" + ((String) this.c.get(0)) + "\n")
        for (Int i2 = 1; i2 < this.c.size(); i2++) {
            String str2 = (String) this.c.get(i2)
            if (str2.trim().isEmpty()) {
                this.f1344b.write("<span id=\"line" + (i2 + 1) + "\"></span>\n")
            } else {
                this.f1344b.write("<span id=\"line" + (i2 + 1) + "\">")
                Array<String> strArrSplit = str2.split("\\s+")
                if (strArrSplit[0].equals("")) {
                    this.f1344b.write(CodeWriter.INDENT)
                    i = 1
                } else {
                    i = 0
                }
                String str3 = strArrSplit[i]
                this.f1344b.write("<font color=\"" + (str3.startsWith(Deobfuscator.CLASS_NAME_SEPARATOR) ? "#FF3399" : str3.startsWith(":") ? "brown" : "green") + "\">")
                this.f1344b.write(strArrSplit[i])
                this.f1344b.write("</font>")
                while (true) {
                    i++
                    if (i >= strArrSplit.length) {
                        break
                    }
                    this.f1344b.write(" ")
                    this.f1344b.write(b(strArrSplit[i]))
                }
                this.f1344b.write("</span>\n")
            }
        }
        this.f1344b.write("</body>")
        this.f1344b.write("</html>")
        this.f1344b.close()
    }
}
