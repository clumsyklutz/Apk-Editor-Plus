package jadx.core.utils.files

import com.a.a.i
import jadx.core.utils.AsmUtils
import jadx.core.utils.exceptions.DecodeException
import jadx.core.utils.exceptions.JadxException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.d.b
import org.d.c

class InputFile {
    private static val LOG = c.a(InputFile.class)
    private final i dexBuf
    private final Int dexIndex
    private final File file
    public Int nextDexIndex

    constructor(File file) {
        this(file, 0)
    }

    constructor(File file, Int i) throws IOException {
        this.nextDexIndex = -1
        if (!file.exists()) {
            throw IOException("File not found: " + file.getAbsolutePath())
        }
        this.dexIndex = i
        this.file = file
        this.dexBuf = loadDexBuffer()
    }

    private fun loadDexBuffer() throws IOException, DecodeException {
        String name = this.file.getName()
        if (name.endsWith(".dex")) {
            return i(this.file)
        }
        if (name.endsWith(".class")) {
            return loadFromClassFile(this.file)
        }
        if (name.endsWith(".apk") || name.endsWith(".zip")) {
            i iVarLoadFromZip = loadFromZip(this, this.file)
            if (iVarLoadFromZip == null) {
                throw IOException("File 'classes.dex' not found in file: " + this.file)
            }
            return iVarLoadFromZip
        }
        if (!name.endsWith(".jar")) {
            throw DecodeException("Unsupported input file format: " + this.file)
        }
        i iVarLoadFromZip2 = loadFromZip(this, this.file)
        return iVarLoadFromZip2 == null ? loadFromJar(this.file) : iVarLoadFromZip2
    }

    private fun loadFromClassFile(File file) throws Throwable {
        JarOutputStream jarOutputStream
        FileOutputStream fileOutputStream = null
        File fileCreateTempFile = File.createTempFile("jadx-tmp-", System.nanoTime() + ".jar")
        fileCreateTempFile.deleteOnExit()
        try {
            FileOutputStream fileOutputStream2 = FileOutputStream(fileCreateTempFile)
            try {
                jarOutputStream = JarOutputStream(fileOutputStream2)
            } catch (Throwable th) {
                th = th
                jarOutputStream = null
                fileOutputStream = fileOutputStream2
            }
            try {
                String nameFromClassFile = AsmUtils.getNameFromClassFile(file)
                if (nameFromClassFile == null) {
                    throw IOException("Can't read class name from file: " + file)
                }
                FileUtils.addFileToJar(jarOutputStream, file, nameFromClassFile + ".class")
                jarOutputStream.close()
                fileOutputStream2.close()
                return loadFromJar(fileCreateTempFile)
            } catch (Throwable th2) {
                th = th2
                fileOutputStream = fileOutputStream2
                if (jarOutputStream != null) {
                    jarOutputStream.close()
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close()
                }
                throw th
            }
        } catch (Throwable th3) {
            th = th3
            jarOutputStream = null
        }
    }

    private fun loadFromJar(File file) throws DecodeException {
        try {
            LOG.b("converting to dex: {} ...", file.getName())
            JavaToDex javaToDex = JavaToDex()
            Array<Byte> bArrConvert = javaToDex.convert(file.getAbsolutePath())
            if (bArrConvert.length == 0) {
                throw JadxException(javaToDex.isError() ? javaToDex.getDxErrors() : "Empty dx output")
            }
            if (javaToDex.isError()) {
                LOG.c("dx message: {}", javaToDex.getDxErrors())
            }
            return i(bArrConvert)
        } catch (Throwable th) {
            throw DecodeException("java class to dex conversion error:\n " + th.getMessage(), th)
        }
    }

    /* JADX WARN: Finally extract failed */
    private fun loadFromZip(InputFile inputFile, File file) throws IOException {
        InputStream inputStream = null
        ZipFile zipFile = ZipFile(file)
        String str = "classes.dex"
        String str2 = "classes2.dex"
        if (inputFile.dexIndex != 0) {
            str = "classes" + inputFile.dexIndex + ".dex"
            str2 = "classes" + (inputFile.dexIndex + 1) + ".dex"
        }
        ZipEntry entry = zipFile.getEntry(str)
        if (entry == null) {
            zipFile.close()
            return null
        }
        try {
            if (zipFile.getEntry(str2) != null) {
                inputFile.nextDexIndex = inputFile.dexIndex == 0 ? 2 : inputFile.dexIndex + 1
            }
        } catch (Exception e) {
        }
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        try {
            inputStream = zipFile.getInputStream(entry)
            Array<Byte> bArr = new Byte[8192]
            while (true) {
                Int i = inputStream.read(bArr)
                if (i == -1) {
                    break
                }
                byteArrayOutputStream.write(bArr, 0, i)
            }
            if (inputStream != null) {
                inputStream.close()
            }
            zipFile.close()
            return i(byteArrayOutputStream.toByteArray())
        } catch (Throwable th) {
            if (inputStream != null) {
                inputStream.close()
            }
            zipFile.close()
            throw th
        }
    }

    fun getDexBuffer() {
        return this.dexBuf
    }

    fun getFile() {
        return this.file
    }

    fun toString() {
        return this.file.toString()
    }
}
