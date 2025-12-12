package jadx.core.clsp

import jadx.api.JadxArgs
import jadx.core.dex.nodes.RootNode
import jadx.core.utils.files.InputFile
import java.io.File
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

class ConvertToClsSet {
    private static val LOG = c.a(ConvertToClsSet.class)

    private fun addFilesFromDirectory(File file, List list) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles == null) {
            return
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isDirectory()) {
                addFilesFromDirectory(file2, list)
            }
            String name = file2.getName()
            if (name.endsWith(".dex") || name.endsWith(".jar") || name.endsWith(".apk")) {
                InputFile inputFile = InputFile(file2)
                list.add(inputFile)
                while (inputFile.nextDexIndex != -1) {
                    InputFile inputFile2 = InputFile(file2, inputFile.nextDexIndex)
                    list.add(inputFile2)
                    inputFile = inputFile2
                }
            }
        }
    }

    fun main(Array<String> strArr) {
        if (strArr.length < 2) {
            usage()
            System.exit(1)
        }
        File file = File(strArr[0])
        ArrayList arrayList = ArrayList(strArr.length - 1)
        for (Int i = 1; i < strArr.length; i++) {
            File file2 = File(strArr[i])
            if (file2.isDirectory()) {
                addFilesFromDirectory(file2, arrayList)
            } else {
                InputFile inputFile = InputFile(file2)
                arrayList.add(inputFile)
                while (inputFile.nextDexIndex != -1) {
                    InputFile inputFile2 = InputFile(file2, inputFile.nextDexIndex)
                    arrayList.add(inputFile2)
                    inputFile = inputFile2
                }
            }
        }
        Iterator it = arrayList.iterator()
        while (it.hasNext()) {
            LOG.b("Loaded: {}", ((InputFile) it.next()).getFile())
        }
        RootNode rootNode = RootNode(JadxArgs())
        rootNode.load(arrayList)
        ClsSet clsSet = ClsSet()
        clsSet.load(rootNode)
        clsSet.save(file)
        LOG.b("Output: {}", file)
        LOG.a("done")
    }

    fun usage() {
        LOG.a("<output .jcst or .jar file> <several input dex or jar files> ")
    }
}
