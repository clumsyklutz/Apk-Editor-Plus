package org.apache.commons.io

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.io.Reader
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.apache.commons.io.output.StringBuilderWriter

class IOUtils {
    public static final String LINE_SEPARATOR

    static {
        Char c = File.separatorChar
        StringBuilderWriter stringBuilderWriter = StringBuilderWriter(4)
        try {
            try {
                PrintWriter(stringBuilderWriter).println()
                LINE_SEPARATOR = stringBuilderWriter.toString()
                stringBuilderWriter.close()
            } finally {
            }
        } catch (Throwable th) {
            try {
                throw th
            } catch (Throwable th2) {
                try {
                    stringBuilderWriter.close()
                } catch (Throwable unused) {
                }
                throw th2
            }
        }
    }

    fun copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        Long jCopyLarge = copyLarge(inputStream, outputStream)
        if (jCopyLarge > 2147483647L) {
            return -1
        }
        return (Int) jCopyLarge
    }

    fun copy(InputStream inputStream, OutputStream outputStream, Int i) throws IOException {
        return copyLarge(inputStream, outputStream, new Byte[i])
    }

    fun copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, 4096)
    }

    fun copyLarge(InputStream inputStream, OutputStream outputStream, Array<Byte> bArr) throws IOException {
        Long j = 0
        while (true) {
            Int i = inputStream.read(bArr)
            if (-1 == i) {
                return j
            }
            outputStream.write(bArr, 0, i)
            j += i
        }
    }

    public static List<String> readLines(InputStream inputStream, Charset charset) throws IOException {
        return readLines(InputStreamReader(inputStream, Charsets.toCharset(charset)))
    }

    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = toBufferedReader(reader)
        ArrayList arrayList = ArrayList()
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            arrayList.add(line)
        }
        return arrayList
    }

    fun toBufferedReader(Reader reader) {
        return reader is BufferedReader ? (BufferedReader) reader : BufferedReader(reader)
    }

    fun writeLines(Collection<?> collection, String str, OutputStream outputStream, String str2) throws IOException {
        writeLines(collection, str, outputStream, Charsets.toCharset(str2))
    }

    fun writeLines(Collection<?> collection, String str, OutputStream outputStream, Charset charset) throws IOException {
        if (collection == null) {
            return
        }
        if (str == null) {
            str = LINE_SEPARATOR
        }
        Charset charset2 = Charsets.toCharset(charset)
        for (Object obj : collection) {
            if (obj != null) {
                outputStream.write(obj.toString().getBytes(charset2))
            }
            outputStream.write(str.getBytes(charset2))
        }
    }
}
