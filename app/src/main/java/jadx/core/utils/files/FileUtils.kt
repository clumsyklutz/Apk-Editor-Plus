package jadx.core.utils.files

import jadx.core.utils.exceptions.JadxRuntimeException
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream

class FileUtils {
    private constructor() {
    }

    fun addFileToJar(JarOutputStream jarOutputStream, File file, String str) throws Throwable {
        BufferedInputStream bufferedInputStream
        try {
            JarEntry jarEntry = JarEntry(str)
            jarEntry.setTime(file.lastModified())
            jarOutputStream.putNextEntry(jarEntry)
            bufferedInputStream = BufferedInputStream(FileInputStream(file))
            try {
                Array<Byte> bArr = new Byte[8192]
                while (true) {
                    Int i = bufferedInputStream.read(bArr)
                    if (i == -1) {
                        jarOutputStream.closeEntry()
                        bufferedInputStream.close()
                        return
                    }
                    jarOutputStream.write(bArr, 0, i)
                }
            } catch (Throwable th) {
                th = th
                if (bufferedInputStream != null) {
                    bufferedInputStream.close()
                }
                throw th
            }
        } catch (Throwable th2) {
            th = th2
            bufferedInputStream = null
        }
    }

    fun makeDirsForFile(File file) {
        File parentFile = file.getParentFile()
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs() && !parentFile.exists()) {
            throw JadxRuntimeException("Can't create directory " + parentFile)
        }
    }
}
