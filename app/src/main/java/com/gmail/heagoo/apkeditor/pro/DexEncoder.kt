package com.gmail.heagoo.apkeditor.pro

import com.gmail.heagoo.apkeditor.App
import com.gmail.heagoo.apkeditor.SettingActivity
import com.gmail.heagoo.apkeditor.f.c
import com.google.common.collect.Lists
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Iterator
import java.util.LinkedHashSet
import java.util.Set
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import org.antlr.runtime.CommonTokenStream
import org.antlr.runtime.tree.CommonTreeNodeStream
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.writer.builder.DexBuilder
import org.jf.dexlib2.writer.io.FileDataStore
import org.jf.smali.SmaliOptions
import org.jf.smali.smaliFlexLexer
import org.jf.smali.smaliParser
import org.jf.smali.smaliTreeWalker

class DexEncoder {
    /* JADX INFO: Access modifiers changed from: private */
    fun assembleSmaliFile(File file, DexBuilder dexBuilder, SmaliOptions smaliOptions) throws Exception {
        String str
        FileInputStream fileInputStream = FileInputStream(file)
        try {
            smaliFlexLexer smaliflexlexer = smaliFlexLexer(InputStreamReader(fileInputStream, "UTF-8"), smaliOptions.apiLevel)
            smaliflexlexer.setSourceFile(file)
            CommonTokenStream commonTokenStream = CommonTokenStream(smaliflexlexer)
            smaliParser smaliparser = smaliParser(commonTokenStream)
            smaliparser.setVerboseErrors(smaliOptions.verboseErrors)
            smaliparser.setAllowOdex(smaliOptions.allowOdexOpcodes)
            smaliparser.setApiLevel(smaliOptions.apiLevel)
            smaliParser.smali_file_return smali_file_returnVarSmali_file = smaliparser.smali_file()
            if (smaliparser.getNumberOfSyntaxErrors() <= 0 && smaliflexlexer.getNumberOfSyntaxErrors() <= 0) {
                CommonTreeNodeStream commonTreeNodeStream = CommonTreeNodeStream(smali_file_returnVarSmali_file.getTree())
                commonTreeNodeStream.setTokenStream(commonTokenStream)
                smaliTreeWalker smalitreewalker = smaliTreeWalker(commonTreeNodeStream)
                smalitreewalker.setApiLevel(smaliOptions.apiLevel)
                smalitreewalker.setVerboseErrors(smaliOptions.verboseErrors)
                smalitreewalker.setDexBuilder(dexBuilder)
                smalitreewalker.smali_file()
                if (smalitreewalker.getNumberOfSyntaxErrors() <= 0) {
                    fileInputStream.close()
                    fileInputStream.close()
                    return true
                }
                String errorMessages = smalitreewalker.getErrorMessages()
                if (!errorMessages.equals("")) {
                    throw Exception(errorMessages)
                }
                throw Exception("Error occurred while compiling2 " + file.getName())
            }
            if (smaliflexlexer.getNumberOfSyntaxErrors() > 0) {
                str = "" + smaliflexlexer.getErrorMessages()
            } else {
                str = ""
            }
            if (smaliparser.getNumberOfSyntaxErrors() > 0) {
                str = str + smaliparser.getErrorMessages()
            }
            if (!str.equals("")) {
                throw Exception(str)
            }
            throw Exception("Error occurred while compiling1 " + file.getName())
        } catch (Throwable th) {
            try {
                throw th
            } catch (Throwable th2) {
                try {
                    fileInputStream.close()
                } catch (Throwable unused) {
                }
                throw th2
            }
        }
    }

    private fun getSmaliFilesInDir(File file, Set<File> set) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    getSmaliFilesInDir(file2, set)
                } else if (file2.getName().endsWith(".smali")) {
                    set.add(file2)
                }
            }
        }
    }

    fun smali2Dex(String str, String str2, c cVar) throws Exception {
        val smaliOptions = SmaliOptions()
        smaliOptions.jobs = Runtime.getRuntime().availableProcessors()
        smaliOptions.apiLevel = SettingActivity.smaliApiLevel(App.getContext())
        smaliOptions.outputDexFile = str2
        smaliOptions.allowOdexOpcodes = false
        smaliOptions.verboseErrors = false
        System.currentTimeMillis()
        LinkedHashSet linkedHashSet = LinkedHashSet()
        getSmaliFilesInDir(File(str), linkedHashSet)
        val dexBuilder = DexBuilder(Opcodes.forApi(smaliOptions.apiLevel))
        ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(smaliOptions.jobs)
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        Iterator it = linkedHashSet.iterator()
        while (it.hasNext()) {
            val file = (File) it.next()
            arrayListNewArrayList.add(executorServiceNewFixedThreadPool.submit(new Callable<Boolean>() { // from class: com.gmail.heagoo.apkeditor.pro.DexEncoder.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                fun call() throws Exception {
                    try {
                        return Boolean.valueOf(DexEncoder.assembleSmaliFile(file, dexBuilder, smaliOptions))
                    } catch (RuntimeException e) {
                        throw RuntimeException(e.getMessage())
                    } catch (Exception e2) {
                        throw Exception(e2.getMessage())
                    }
                }
            }))
        }
        Int size = arrayListNewArrayList.size()
        Boolean z = false
        for (Int i = 0; i < size; i++) {
            if (!((Boolean) ((Future) arrayListNewArrayList.get(i)).get()).booleanValue()) {
                z = true
            }
            cVar.a(i, size)
        }
        executorServiceNewFixedThreadPool.shutdown()
        if (z) {
            throw Exception("Encountered errors while compiling smali files.")
        }
        dexBuilder.writeTo(FileDataStore(File(smaliOptions.outputDexFile)))
        System.currentTimeMillis()
    }
}
