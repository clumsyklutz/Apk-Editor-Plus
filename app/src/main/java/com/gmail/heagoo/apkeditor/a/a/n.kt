package com.gmail.heagoo.apkeditor.a.a

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.io.Serializable
import java.security.DigestException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Map
import java.util.zip.Adler32
import java.util.zip.ZipFile

class n implements com.gmail.heagoo.apkeditor.a.d, Serializable {

    /* renamed from: a, reason: collision with root package name */
    private String f797a

    constructor(String str) {
        this.f797a = str
    }

    @Override // com.gmail.heagoo.apkeditor.a.d
    public final Unit a(Context context, String str, Map map, com.gmail.heagoo.apkeditor.a.f fVar) throws NoSuchAlgorithmException, DigestException, IOException {
        String str2 = com.gmail.heagoo.a.c.a.d(context, "tmp") + "_dex"
        RandomAccessFile randomAccessFile = RandomAccessFile(str2, "rw")
        randomAccessFile.setLength(0L)
        ZipFile zipFile = ZipFile(str)
        InputStream inputStream = zipFile.getInputStream(zipFile.getEntry("classes.dex"))
        Array<Byte> bArr = new Byte[559000]
        Int i = 0
        while (true) {
            Int i2 = inputStream.read(bArr, i, 559000 - i)
            if (i2 <= 0) {
                System.arraycopy(this.f797a.getBytes(), 0, bArr, 401438, this.f797a.length())
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1")
                    messageDigest.update(bArr, 32, i - 32)
                    try {
                        messageDigest.digest(bArr, 12, 20)
                        Adler32 adler32 = Adler32()
                        adler32.update(bArr, 12, i - 12)
                        Int value = (Int) adler32.getValue()
                        bArr[8] = (Byte) value
                        bArr[9] = (Byte) (value >> 8)
                        bArr[10] = (Byte) (value >> 16)
                        bArr[11] = (Byte) (value >>> 24)
                        randomAccessFile.write(bArr, 0, i)
                        inputStream.close()
                        zipFile.close()
                        randomAccessFile.close()
                        map.put("classes.dex", str2)
                        return
                    } catch (DigestException e) {
                        throw RuntimeException(e)
                    }
                } catch (NoSuchAlgorithmException e2) {
                    throw RuntimeException(e2)
                }
            }
            i += i2
        }
    }
}
