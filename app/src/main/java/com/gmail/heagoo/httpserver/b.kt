package com.gmail.heagoo.httpserver

import b.a.a.h
import b.a.a.j
import b.a.a.m
import com.gmail.heagoo.apkeditor.gzd
import com.gmail.heagoo.common.w
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.reflect.Field
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map

class b extends b.a.a.a {

    /* renamed from: b, reason: collision with root package name */
    private static final Array<String> f1479b = {"/listFiles", "/readFile", "/readImage", "/saveFile"}
    private String c
    private String d
    private String e

    constructor(String str, String str2) {
        super(8000)
        this.e = null
        this.c = str
        this.d = str2
    }

    private fun a(Map map) throws Throwable {
        FileOutputStream fileOutputStream
        FileOutputStream fileOutputStream2
        String str = (String) map.get("path")
        String str2 = (String) map.get(gzd.COLUMN_CONTENT)
        if (str != null && str2 != null) {
            File file = File(this.d + "/" + str)
            if (file.isFile() && file.exists()) {
                try {
                    fileOutputStream = FileOutputStream(file)
                } catch (IOException e) {
                    e = e
                    fileOutputStream2 = null
                } catch (Throwable th) {
                    th = th
                    fileOutputStream = null
                }
                try {
                    fileOutputStream.write(str2.getBytes())
                    fileOutputStream.close()
                    j jVarA = a(m.f99a, "text/html", "OK")
                    com.gmail.heagoo.a.c.a.a(fileOutputStream)
                    return jVarA
                } catch (IOException e2) {
                    e = e2
                    fileOutputStream2 = fileOutputStream
                    try {
                        e.printStackTrace()
                        com.gmail.heagoo.a.c.a.a(fileOutputStream2)
                        return l()
                    } catch (Throwable th2) {
                        th = th2
                        fileOutputStream = fileOutputStream2
                        com.gmail.heagoo.a.c.a.a(fileOutputStream)
                        throw th
                    }
                } catch (Throwable th3) {
                    th = th3
                    com.gmail.heagoo.a.c.a.a(fileOutputStream)
                    throw th
                }
            }
        }
        return l()
    }

    private fun b(Map map) throws IOException {
        String str = (String) map.get("path")
        File file = File(this.d + "/" + str)
        if (!file.exists() || !file.isFile()) {
            return l()
        }
        try {
            return a(m.f99a, c(str), FileInputStream(file), r2.available())
        } catch (IOException e) {
            return l()
        }
    }

    private fun c(Map map) {
        String str = (String) map.get("path")
        File file = File(this.d + "/" + str)
        if (!file.exists() || !file.isFile()) {
            return l()
        }
        String str2 = str.endsWith(".xml") ? "xml" : str.endsWith(".java") ? "java" : str.endsWith(".kt") ? "kotlin" : str.endsWith(".css") ? "css" : str.endsWith(".js") ? "javascript" : (str.endsWith(".htm") || str.endsWith(".html")) ? "html" : (str.endsWith(".txt") || str.endsWith(".md") || str.endsWith(".project") || str.endsWith(".gradle") || str.endsWith(".smali")) ? "text" : (str.endsWith(".c") || str.endsWith(".cpp")) ? "c_cpp" : str.endsWith(".py") ? "python" : null
        if (str2 == null) {
            if (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".gif") || str.endsWith(".bmp")) {
                return a(m.f99a, "text/html", "<html>\n<head>\n<style type=\"text/css\">\nimg {\n    position: absolute;\n    margin: auto;\n    top: 0;\n    left: 0;\n    right: 0;\n    bottom: 0;\n}\n</style>\n</head>\n<body>\n<img src=\"readImage?path=" + str + "\">\n</body>\n</html>")
            }
            return a(m.f99a, "text/html", "<html>\n<head>\n<style type=\"text/css\">\n#tip {\n    position: absolute;\n    margin: auto;\n    top: 50%;\n    width: 100%;\n    height: 100px;\n    margin-top: -50px;\n}\n</style>\n</head>\n<body>\n<div id=\"tip\"><center>Cannot open " + str + "</center></div>\n</body>\n</html>")
        }
        if (this.e == null) {
            try {
                this.e = w(this.c + "/editor.htm").b()
            } catch (IOException e) {
                this.e = "<html lang=\"en\">\n<head>\n  <meta charset=\"UTF-8\">\n  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n  <title>Editor</title>\n  <style type=\"text/css\" media=\"screen\">\n    body {\n        overflow: hidden;\n    }\n    #editor {\n        margin: 0;\n        position: absolute;\n        top: 0;\n        bottom: 0;\n        left: 0;\n        right: 0;\n    }\n  </style>\n</head>\n<body>\n<pre id=\"editor\">__CONTENT__</pre>\n<script src=\"src-min-noconflict/ace.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\n<script>\n    var editor = ace.edit(\"editor\");\n    editor.setTheme(\"ace/theme/dreamweaver\");\n    editor.session.setMode(\"ace/mode/__MODE__\");\n</script>\n</body>\n</html>"
            }
        }
        try {
            return a(m.f99a, "text/html", this.e.replace("__MODE__", str2).replace("__PATH__", str).replace("__CONTENT__", w(file.getPath()).b().replace("<", "&lt;")))
        } catch (IOException e2) {
            return l()
        }
    }

    private fun c(String str) {
        return (str.endsWith(".jpg") || str.endsWith(".jpeg")) ? "image/jpeg" : str.endsWith(".png") ? "image/png" : str.endsWith(".gif") ? "image/gif" : str.endsWith(".bmp") ? "image/bmp" : (str.endsWith(".htm") || str.endsWith(".html")) ? "text/html" : str.endsWith(".css") ? "text/css" : str.endsWith(".js") ? "text/javascript" : "text/plain"
    }

    private fun d(Map map) {
        String str = (String) map.get("node")
        String str2 = str == null ? "" : str + "/"
        File file = File(this.d + "/" + str2)
        if (!file.exists() || !file.isDirectory()) {
            return l()
        }
        List listAsList = Arrays.asList(file.listFiles())
        Collections.sort(listAsList, a())
        StringBuilder sb = StringBuilder()
        sb.append("[")
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= listAsList.size()) {
                sb.append("]")
                return a(m.f99a, "application/json", sb.toString())
            }
            File file2 = (File) listAsList.get(i2)
            sb.append("{")
            sb.append("\"name\": \"" + file2.getName() + "\",")
            sb.append("\"id\": \"" + str2 + file2.getName() + "\",")
            if (file2.isDirectory()) {
                sb.append("\"load_on_demand\": true")
            } else {
                sb.append("\"load_on_demand\": false")
            }
            sb.append("}")
            if (i2 != listAsList.size() - 1) {
                sb.append(",")
            }
            i = i2 + 1
        }
    }

    private fun d(String str) {
        Array<String> strArr = f1479b
        for (Int i = 0; i < 4; i++) {
            if (strArr[i].equals(str)) {
                return true
            }
        }
        return false
    }

    private fun l() {
        return a(m.c, "text/html", "Not Found!")
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.net.InetAddress m() throws java.net.SocketException, java.net.UnknownHostException {
        /*
            r1 = 0
            java.util.Enumeration r2 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch: java.lang.Exception -> L44
        L5:
            Boolean r0 = r2.hasMoreElements()     // Catch: java.lang.Exception -> L44
            if (r0 == 0) goto L32
            java.lang.Object r0 = r2.nextElement()     // Catch: java.lang.Exception -> L44
            java.net.NetworkInterface r0 = (java.net.NetworkInterface) r0     // Catch: java.lang.Exception -> L44
            java.util.Enumeration r3 = r0.getInetAddresses()     // Catch: java.lang.Exception -> L44
        L15:
            Boolean r0 = r3.hasMoreElements()     // Catch: java.lang.Exception -> L44
            if (r0 == 0) goto L5
            java.lang.Object r0 = r3.nextElement()     // Catch: java.lang.Exception -> L44
            java.net.InetAddress r0 = (java.net.InetAddress) r0     // Catch: java.lang.Exception -> L44
            Boolean r4 = r0.isLoopbackAddress()     // Catch: java.lang.Exception -> L44
            if (r4 != 0) goto L5d
            Boolean r4 = r0.isSiteLocalAddress()     // Catch: java.lang.Exception -> L44
            if (r4 == 0) goto L2e
        L2d:
            return r0
        L2e:
            if (r1 != 0) goto L5d
        L30:
            r1 = r0
            goto L15
        L32:
            if (r1 == 0) goto L36
            r0 = r1
            goto L2d
        L36:
            java.net.InetAddress r0 = java.net.InetAddress.getLocalHost()     // Catch: java.lang.Exception -> L44
            if (r0 != 0) goto L2d
            java.net.UnknownHostException r0 = new java.net.UnknownHostException     // Catch: java.lang.Exception -> L44
            java.lang.String r1 = "The JDK InetAddress.getLocalHost() method unexpectedly returned null."
            r0.<init>(r1)     // Catch: java.lang.Exception -> L44
            throw r0     // Catch: java.lang.Exception -> L44
        L44:
            r0 = move-exception
            java.net.UnknownHostException r1 = new java.net.UnknownHostException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to determine LAN address: "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            r1.initCause(r0)
            throw r1
        L5d:
            r0 = r1
            goto L30
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.httpserver.b.m():java.net.InetAddress")
    }

    @Override // b.a.a.a
    public final j a(h hVar) {
        String strE = hVar.e()
        try {
            hVar.a(HashMap())
        } catch (Exception e) {
        }
        Map mapC = hVar.c()
        try {
            if (d(strE)) {
                switch (strE) {
                    case "/listFiles":
                        return d(mapC)
                    case "/readFile":
                        return c(mapC)
                    case "/readImage":
                        return b(mapC)
                    case "/saveFile":
                        return a(mapC)
                }
            }
            String str = "/".equals(strE) ? this.c + "/index.htm" : this.c + strE
            if (str == null) {
                return super.a(hVar)
            }
            File file = File(str)
            if (file.exists()) {
                StringBuilder("file path = ").append(file.getAbsolutePath())
                return a(m.f99a, c(str), FileInputStream(file), r2.available())
            }
            StringBuilder("Cannot find").append(file.getAbsolutePath())
        } catch (Exception e2) {
            e2.printStackTrace()
        }
        return l()
    }

    public final String a() {
        Array<String> strArr = {""}
        c cVar = c(this, strArr)
        try {
            synchronized (cVar) {
                cVar.start()
                cVar.wait()
            }
        } catch (InterruptedException e) {
        }
        return "http://" + strArr[0] + ":" + String.valueOf(c())
    }

    public final Unit a(Int i) {
        try {
            Field declaredField = b.a.a.a.class.getDeclaredField("myPort")
            declaredField.setAccessible(true)
            declaredField.set(this, Integer.valueOf(i + 8000))
        } catch (Exception e) {
            e.printStackTrace()
        }
        a(5000, true)
    }

    public final Unit a(String str) {
        this.d = str
    }
}
