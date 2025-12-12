package com.gmail.heagoo.a.c

import java.io.ByteArrayOutputStream
import java.io.Console
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.PushbackInputStream
import java.lang.reflect.Method
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

class PasswordRetriever implements AutoCloseable {
    public static val SPEC_STDIN = "stdin"
    private Boolean mClosed
    private final Map<File, InputStream> mFileInputStreams = HashMap()
    private val mConsoleEncoding = getConsoleEncoding()

    private fun addPassword(List<Array<Char>> list, Array<Char> cArr) {
        Iterator<Array<Char>> it = list.iterator()
        while (it.hasNext()) {
            if (Arrays.equals(cArr, it.next())) {
                return
            }
        }
        list.add(cArr)
    }

    private fun addPasswords(List<Array<Char>> list, Array<Char> cArr, Charset... charsetArr) {
        if (charsetArr != null && charsetArr.length > 0) {
            for (Charset charset : charsetArr) {
                try {
                    addPassword(list, castBytesToChars(encodePassword(cArr, charset)))
                } catch (IOException e) {
                }
            }
        }
        addPassword(list, cArr)
        Charset charset2 = this.mConsoleEncoding
        if (charset2 != null) {
            try {
                addPassword(list, castBytesToChars(encodePassword(cArr, charset2)))
            } catch (IOException e2) {
            }
        }
        try {
            addPassword(list, castBytesToChars(encodePassword(cArr, Charset.defaultCharset())))
        } catch (IOException e3) {
        }
    }

    private fun assertNotClosed() {
        if (this.mClosed) {
            throw IllegalStateException("Closed")
        }
    }

    private static Array<Char> castBytesToChars(Array<Byte> bArr) {
        if (bArr == null) {
            return null
        }
        Array<Char> cArr = new Char[bArr.length]
        for (Int i = 0; i < bArr.length; i++) {
            cArr[i] = (Char) (bArr[i] & 255)
        }
        return cArr
    }

    private static Array<Char> decodePassword(Array<Byte> bArr, Charset charset) throws IOException {
        CharBuffer charBufferDecode = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).decode(ByteBuffer.wrap(bArr))
        Array<Char> cArr = new Char[charBufferDecode.remaining()]
        charBufferDecode.get(cArr)
        return cArr
    }

    private static Array<Byte> encodePassword(Array<Char> cArr, Charset charset) throws IOException {
        ByteBuffer byteBufferEncode = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).encode(CharBuffer.wrap(cArr))
        Array<Byte> bArr = new Byte[byteBufferEncode.remaining()]
        byteBufferEncode.get(bArr)
        return bArr
    }

    fun getCharsetByName(String str) throws IllegalArgumentException {
        return "cp65001".equalsIgnoreCase(str) ? StandardCharsets.UTF_8 : Charset.forName(str)
    }

    private fun getConsoleEncoding() throws NoSuchMethodException, SecurityException {
        if (isJava9OrHigherErrOnTheSideOfCaution()) {
            return null
        }
        try {
            Method declaredMethod = Console.class.getDeclaredMethod("encoding", new Class[0])
            declaredMethod.setAccessible(true)
            String str = (String) declaredMethod.invoke(null, new Object[0])
            if (str == null) {
                return Charset.defaultCharset()
            }
            try {
                return getCharsetByName(str)
            } catch (IllegalArgumentException e) {
                return null
            }
        } catch (ReflectiveOperationException e2) {
            return null
        }
    }

    private List<Array<Char>> getPasswords(Array<Byte> bArr, Charset charset, Charset... charsetArr) {
        ArrayList arrayList = ArrayList(4)
        try {
            addPasswords(arrayList, decodePassword(bArr, charset), charsetArr)
        } catch (IOException e) {
        }
        addPassword(arrayList, castBytesToChars(bArr))
        return arrayList
    }

    private List<Array<Char>> getPasswords(Array<Char> cArr, Charset... charsetArr) {
        ArrayList arrayList = ArrayList(3)
        addPasswords(arrayList, cArr, charsetArr)
        return arrayList
    }

    private fun isJava9OrHigherErrOnTheSideOfCaution() {
        String property = System.getProperty("java.specification.version")
        if (property == null) {
            return true
        }
        return true ^ property.startsWith("1.")
    }

    private static Array<Byte> readEncodedPassword(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        while (true) {
            Int i = inputStream.read()
            if (i == -1 || i == 10) {
                break
            }
            if (i == 13) {
                Int i2 = inputStream.read()
                if (i2 == -1 || i2 == 10) {
                    break
                }
                if (!(inputStream is PushbackInputStream)) {
                    inputStream = PushbackInputStream(inputStream)
                }
                ((PushbackInputStream) inputStream).unread(i2)
            }
            byteArrayOutputStream.write(i)
        }
        return byteArrayOutputStream.toByteArray()
    }

    @Override // java.lang.AutoCloseable
    fun close() throws IOException {
        Iterator<InputStream> it = this.mFileInputStreams.values().iterator()
        while (it.hasNext()) {
            try {
                it.next().close()
            } catch (IOException e) {
            }
        }
        this.mFileInputStreams.clear()
        this.mClosed = true
    }

    public List<Array<Char>> getPasswords(String str, String str2, Charset... charsetArr) throws IOException {
        assertNotClosed()
        if (str.startsWith("pass:")) {
            return getPasswords(str.substring("pass:".length()).toCharArray(), charsetArr)
        }
        if (SPEC_STDIN.equals(str)) {
            Console console = System.console()
            if (console != null) {
                Array<Char> password = console.readPassword(str2 + ": ", new Object[0])
                if (password != null) {
                    return getPasswords(password, charsetArr)
                }
                throw IOException("Failed to read " + str2 + ": console closed")
            }
            System.out.println(str2 + ": ")
            Array<Byte> encodedPassword = readEncodedPassword(System.in)
            if (encodedPassword.length != 0) {
                return getPasswords(encodedPassword, Charset.defaultCharset(), charsetArr)
            }
            throw IOException("Failed to read " + str2 + ": standard input closed")
        }
        if (str.startsWith("file:")) {
            File canonicalFile = File(str.substring("file:".length())).getCanonicalFile()
            InputStream fileInputStream = this.mFileInputStreams.get(canonicalFile)
            if (fileInputStream == null) {
                fileInputStream = FileInputStream(canonicalFile)
                this.mFileInputStreams.put(canonicalFile, fileInputStream)
            }
            Array<Byte> encodedPassword2 = readEncodedPassword(fileInputStream)
            if (encodedPassword2.length != 0) {
                return getPasswords(encodedPassword2, Charset.defaultCharset(), charsetArr)
            }
            throw IOException("Failed to read " + str2 + " : end of file reached in " + canonicalFile)
        }
        if (!str.startsWith("env:")) {
            throw IOException("Unsupported password spec for " + str2 + ": " + str)
        }
        String str3 = System.getenv(str.substring("env:".length()))
        if (str3 != null) {
            return getPasswords(str3.toCharArray(), charsetArr)
        }
        throw IOException("Failed to read " + str2 + ": environment variable " + str3 + " not specified")
    }
}
