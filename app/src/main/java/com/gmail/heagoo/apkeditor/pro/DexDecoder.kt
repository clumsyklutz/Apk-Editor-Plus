package com.gmail.heagoo.apkeditor.pro

import com.gmail.heagoo.apkeditor.App
import com.gmail.heagoo.apkeditor.SettingActivity
import java.io.File
import java.util.List
import org.jf.baksmali.Baksmali
import org.jf.baksmali.BaksmaliOptions
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedDexFile

class DexDecoder {
    private final String dexFilePath
    private String strWarning

    constructor(String str) {
        this.dexFilePath = str
    }

    private fun getOptions() {
        BaksmaliOptions baksmaliOptions = BaksmaliOptions()
        baksmaliOptions.parameterRegisters = true
        baksmaliOptions.localsDirective = false
        baksmaliOptions.sequentialLabels = false
        baksmaliOptions.debugInfo = true
        baksmaliOptions.codeOffsets = false
        baksmaliOptions.accessorComments = true
        baksmaliOptions.implicitReferences = false
        baksmaliOptions.normalizeVirtualMethods = false
        baksmaliOptions.registerInfo = 0
        return baksmaliOptions
    }

    fun dex2smali(String str) throws Exception {
        DexBackedDexFile dexBackedDexFileLoadDexFile = loadDexFile(this.dexFilePath)
        File file = File(str)
        if (file.exists() || file.mkdirs()) {
            if (!Baksmali.disassembleDexFile(dexBackedDexFileLoadDexFile, file, Runtime.getRuntime().availableProcessors(), getOptions(), (List) null)) {
                throw Exception("Baksmali.disassembleDexFile failed.")
            }
        } else {
            throw Exception("Can't create the output directory " + str)
        }
    }

    fun getWarning() {
        return this.strWarning
    }

    protected fun loadDexFile(String str) throws Exception {
        File file = File(str)
        if (file.exists() && !file.isDirectory()) {
            return DexFileFactory.loadDexFile(file, Opcodes.forApi(SettingActivity.smaliApiLevel(App.getContext())))
        }
        throw Exception("Can't find file: " + str)
    }
}
