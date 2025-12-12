package jadx.core.utils.files

import com.a.b.b.a
import com.a.b.b.a.c
import jadx.core.utils.exceptions.JadxException
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.io.UnsupportedEncodingException

class JavaToDex {
    private static val CHARSET_NAME = "UTF-8"
    private String dxErrors

    class DxArgs extends c {
        constructor(String str, Array<String> strArr) {
            this.outName = str
            this.fileNames = strArr
            this.jarOutput = false
            this.optimize = true
            this.localInfo = true
            this.coreLibrary = true
        }
    }

    public Array<Byte> convert(String str) throws JadxException {
        JadxException jadxException
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        try {
            a.f254b = PrintStream((OutputStream) byteArrayOutputStream, true, CHARSET_NAME)
            PrintStream printStream = System.out
            ByteArrayOutputStream byteArrayOutputStream2 = ByteArrayOutputStream()
            try {
                try {
                    System.setOut(PrintStream((OutputStream) byteArrayOutputStream2, true, CHARSET_NAME))
                    com.a.b.b.a.a.a(DxArgs("-", new Array<String>{str}))
                    byteArrayOutputStream2.close()
                    try {
                        this.dxErrors = byteArrayOutputStream.toString(CHARSET_NAME)
                        return byteArrayOutputStream2.toByteArray()
                    } catch (UnsupportedEncodingException e) {
                        throw JadxException("Can't save error output", e)
                    }
                } finally {
                }
            } finally {
                System.setOut(printStream)
            }
        } catch (UnsupportedEncodingException e2) {
            throw JadxException(e2.getMessage(), e2)
        }
    }

    fun getDxErrors() {
        return this.dxErrors
    }

    fun isError() {
        return this.dxErrors != null && this.dxErrors.length() > 0
    }
}
