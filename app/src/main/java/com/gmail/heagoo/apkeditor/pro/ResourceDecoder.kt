package com.gmail.heagoo.apkeditor.pro

import java.io.File
import java.util.List
import org.jf.baksmali.BaksmaliOptions
import org.jf.baksmali.Baksmali_r
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.dexbacked.DexBackedDexFile

class ResourceDecoder {
    fun decodeResources(String str, String str2) throws Exception {
        DexBackedDexFile dexBackedDexFileLoadDexFile = loadDexFile(str)
        File file = File(str2)
        if (!file.exists() && !file.mkdirs()) {
            throw Exception("Can't create the output directory " + str2)
        }
        if (!Baksmali_r.disassembleDexResource(dexBackedDexFileLoadDexFile, file, Runtime.getRuntime().availableProcessors(), getOptions(), (List) null)) {
            throw Exception("Baksmali.disassembleDexFile failed.")
        }
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

    protected static DexBackedDexFile loadDexFile(String str) throws Exception {
        File file = File(str)
        if (!file.exists() || file.isDirectory()) {
            throw Exception("Can't find file: " + str)
        }
        return DexFileFactory.loadDexFile(file, Opcodes.forApi(15))
    }
}
