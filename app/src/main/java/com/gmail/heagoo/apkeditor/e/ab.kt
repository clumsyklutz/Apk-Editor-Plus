package com.gmail.heagoo.apkeditor.e

import jadx.core.deobf.Deobfuscator
import java.io.File
import java.util.ArrayList
import java.util.LinkedList
import java.util.List

class ab extends w {

    /* renamed from: a, reason: collision with root package name */
    private String f984a

    /* renamed from: b, reason: collision with root package name */
    private String f985b
    private String c
    private Boolean d = false
    private List e = LinkedList()
    private List f = ArrayList()
    private Int g = 0

    constructor(b bVar, String str) {
        this.f984a = str
        this.f985b = "^" + str.replace("*", ".*") + Deobfuscator.INNER_CLASS_SEPARATOR
        this.c = bVar.b()
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final String a() {
        if (!this.d) {
            Array<File> fileArrListFiles = File(this.c).listFiles()
            if (fileArrListFiles != null) {
                for (File file : fileArrListFiles) {
                    if (file.isDirectory()) {
                        this.e.add(file.getName())
                    } else {
                        String name = file.getName()
                        if (a(name)) {
                            this.f.add(name)
                        }
                    }
                }
            }
            this.d = true
        }
        if (this.g < this.f.size()) {
            String str = (String) this.f.get(this.g)
            this.g++
            return str
        }
        if (!this.e.isEmpty()) {
            this.g = 0
            this.f.clear()
            while (!this.e.isEmpty()) {
                String str2 = (String) this.e.remove(0)
                Array<File> fileArrListFiles2 = File(this.c + "/" + str2).listFiles()
                if (fileArrListFiles2 != null) {
                    for (File file2 : fileArrListFiles2) {
                        String str3 = str2 + "/" + file2.getName()
                        if (file2.isDirectory()) {
                            this.e.add(str3)
                        } else if (a(str3)) {
                            this.f.add(str3)
                        }
                    }
                }
                if (!this.f.isEmpty()) {
                    break
                }
            }
            if (!this.f.isEmpty()) {
                this.g = 1
                return (String) this.f.get(0)
            }
        }
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean a(String str) {
        return str.matches(this.f985b)
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean b() {
        return this.f984a.startsWith("smali") || this.f984a.endsWith(".smali")
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean c() {
        return true
    }
}
