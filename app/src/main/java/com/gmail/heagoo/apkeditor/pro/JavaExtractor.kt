package com.gmail.heagoo.apkeditor.pro

import com.gmail.heagoo.apkeditor.App
import com.gmail.heagoo.apkeditor.SettingActivity
import com.gmail.heagoo.apkeditor.d.a
import jadx.api.JadxDecompiler
import java.io.File
import java.util.ArrayList
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.DexFile
import org.jf.dexlib2.immutable.ImmutableDexFile

class JavaExtractor implements a {
    private final String apkPath
    private final String className
    private final String dexName
    private String errorMessage = null
    private String interestedName
    private final String workingDirectory

    constructor(String str, String str2, String str3, String str4) {
        this.apkPath = str
        this.dexName = str2
        this.className = str3
        this.workingDirectory = str4
        this.interestedName = str3
        Int iLastIndexOf = str3.lastIndexOf(36)
        if (iLastIndexOf != -1) {
            this.interestedName = str3.substring(0, iLastIndexOf)
        }
    }

    private fun decompile(String str, String str2) {
        try {
            File file = File(str)
            File file2 = File(str2)
            JadxDecompiler jadxDecompiler = JadxDecompiler()
            jadxDecompiler.setOutputDir(file2)
            jadxDecompiler.loadFile(file)
            jadxDecompiler.saveSources()
            return true
        } catch (Exception | StackOverflowError e) {
            this.errorMessage = "Cannot decompile java code: " + e.getMessage()
            return false
        }
    }

    private fun extractDex() {
        try {
            DexFile dexFile = DexFileFactory.loadDexEntry(File(this.apkPath), this.dexName, true, Opcodes.forApi(SettingActivity.smaliApiLevel(App.getContext()))).getDexFile()
            ArrayList arrayList = ArrayList()
            for (ClassDef classDef : dexFile.getClasses()) {
                if (classDef.getType().startsWith(this.interestedName)) {
                    arrayList.add(classDef)
                }
            }
            File file = File(this.workingDirectory)
            if (!file.exists()) {
                file.mkdirs()
            }
            try {
                DexFileFactory.writeDexFile(this.workingDirectory + "/extracted.dex", ImmutableDexFile(Opcodes.forApi(SettingActivity.smaliApiLevel(App.getContext())), arrayList))
                return true
            } catch (Exception e) {
                this.errorMessage = "Cannot extract " + this.className + " as dex extract failed: " + e.getMessage()
                return false
            }
        } catch (Exception unused) {
            this.errorMessage = "The dex file cannot be decompiled."
            return false
        }
    }

    @Override // com.gmail.heagoo.apkeditor.d.a
    fun extract() {
        if (!extractDex()) {
            return false
        }
        return decompile(this.workingDirectory + "/extracted.dex", this.workingDirectory)
    }

    @Override // com.gmail.heagoo.apkeditor.d.a
    fun getErrorMessage() {
        return this.errorMessage
    }
}
