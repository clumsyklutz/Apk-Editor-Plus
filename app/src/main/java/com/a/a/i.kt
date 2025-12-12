package com.a.a

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.List
import java.util.zip.Adler32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class i {

    /* renamed from: a, reason: collision with root package name */
    static final Array<Short> f131a = new Short[0]

    /* renamed from: b, reason: collision with root package name */
    private ByteBuffer f132b
    private final z c
    private Int d
    private final p e
    private final q f
    private final r g
    private final n h
    private final l i
    private final m j

    constructor(Int i) {
        Byte b2 = 0
        this.c = z()
        this.d = 0
        this.e = p(this, b2)
        this.f = q(this, b2)
        this.g = r(this, b2)
        this.h = n(this, b2)
        this.i = l(this, b2)
        this.j = m(this, b2)
        this.f132b = ByteBuffer.wrap(new Byte[i])
        this.f132b.order(ByteOrder.LITTLE_ENDIAN)
    }

    constructor(File file) throws IOException {
        Byte b2 = 0
        this.c = z()
        this.d = 0
        this.e = p(this, b2)
        this.f = q(this, b2)
        this.g = r(this, b2)
        this.h = n(this, b2)
        this.i = l(this, b2)
        this.j = m(this, b2)
        if (!com.gmail.heagoo.a.c.a.g(file.getName())) {
            if (!file.getName().endsWith(".dex")) {
                throw s("unknown output extension: " + file)
            }
            a(FileInputStream(file))
        } else {
            ZipFile zipFile = ZipFile(file)
            ZipEntry entry = zipFile.getEntry("classes.dex")
            if (entry == null) {
                throw s("Expected classes.dex in " + file)
            }
            a(zipFile.getInputStream(entry))
            zipFile.close()
        }
    }

    private constructor(ByteBuffer byteBuffer) {
        Byte b2 = 0
        this.c = z()
        this.d = 0
        this.e = p(this, b2)
        this.f = q(this, b2)
        this.g = r(this, b2)
        this.h = n(this, b2)
        this.i = l(this, b2)
        this.j = m(this, b2)
        this.f132b = byteBuffer
        this.f132b.order(ByteOrder.LITTLE_ENDIAN)
        this.c.a(this)
    }

    constructor(Array<Byte> bArr) {
        this(ByteBuffer.wrap(bArr))
    }

    private fun a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        Array<Byte> bArr = new Byte[8192]
        while (true) {
            Int i = inputStream.read(bArr)
            if (i == -1) {
                inputStream.close()
                this.f132b = ByteBuffer.wrap(byteArrayOutputStream.toByteArray())
                this.f132b.order(ByteOrder.LITTLE_ENDIAN)
                this.c.a(this)
                return
            }
            byteArrayOutputStream.write(bArr, 0, i)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Int i, Int i2) {
        if (i < 0 || i >= i2) {
            throw IndexOutOfBoundsException("index:" + i + ", length=" + i2)
        }
    }

    private Array<Byte> m() throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1")
            Array<Byte> bArr = new Byte[8192]
            ByteBuffer byteBufferDuplicate = this.f132b.duplicate()
            byteBufferDuplicate.limit(byteBufferDuplicate.capacity())
            byteBufferDuplicate.position(32)
            while (byteBufferDuplicate.hasRemaining()) {
                Int iMin = Math.min(8192, byteBufferDuplicate.remaining())
                byteBufferDuplicate.get(bArr, 0, iMin)
                messageDigest.update(bArr, 0, iMin)
            }
            return messageDigest.digest()
        } catch (NoSuchAlgorithmException e) {
            throw AssertionError()
        }
    }

    public final b a(e eVar) {
        Int i = eVar.i()
        if (i == 0) {
            throw IllegalArgumentException("offset == 0")
        }
        return o.a(a(i))
    }

    public final f a(d dVar) {
        Int iC = dVar.c()
        if (iC == 0) {
            throw IllegalArgumentException("offset == 0")
        }
        return o.b(a(iC))
    }

    public final o a(Int i) {
        if (i < 0 || i >= this.f132b.capacity()) {
            throw IllegalArgumentException("position=" + i + " length=" + this.f132b.capacity())
        }
        ByteBuffer byteBufferDuplicate = this.f132b.duplicate()
        byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferDuplicate.position(i)
        byteBufferDuplicate.limit(this.f132b.capacity())
        return o(this, "section", byteBufferDuplicate, (Byte) 0)
    }

    public final o a(Int i, String str) {
        if ((i & 3) != 0) {
            throw IllegalStateException("Not four Byte aligned!")
        }
        Int i2 = this.d + i
        ByteBuffer byteBufferDuplicate = this.f132b.duplicate()
        byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferDuplicate.position(this.d)
        byteBufferDuplicate.limit(i2)
        o oVar = o(this, str, byteBufferDuplicate, (Byte) 0)
        this.d = i2
        return oVar
    }

    public final z a() {
        return this.c
    }

    public final Unit a(OutputStream outputStream) throws IOException {
        Array<Byte> bArr = new Byte[8192]
        ByteBuffer byteBufferDuplicate = this.f132b.duplicate()
        byteBufferDuplicate.clear()
        while (byteBufferDuplicate.hasRemaining()) {
            Int iMin = Math.min(8192, byteBufferDuplicate.remaining())
            byteBufferDuplicate.get(bArr, 0, iMin)
            outputStream.write(bArr, 0, iMin)
        }
    }

    public final Int b() {
        return this.f132b.capacity()
    }

    public final ab b(Int i) {
        return i == 0 ? ab.f115a : a(i).e()
    }

    public final Int c() {
        return this.d
    }

    public final Int c(Int i) {
        b(i, this.c.c.f114b)
        return this.f132b.getInt(this.c.c.c + (i * 4))
    }

    public final Array<Byte> d() {
        ByteBuffer byteBufferDuplicate = this.f132b.duplicate()
        Array<Byte> bArr = new Byte[byteBufferDuplicate.capacity()]
        byteBufferDuplicate.position(0)
        byteBufferDuplicate.get(bArr)
        return bArr
    }

    public final List e() {
        return this.e
    }

    public final List f() {
        return this.f
    }

    public final List g() {
        return this.g
    }

    public final List h() {
        return this.h
    }

    public final List i() {
        return this.i
    }

    public final List j() {
        return this.j
    }

    public final Iterable k() {
        return j(this, (Byte) 0)
    }

    public final Unit l() {
        a(12).a(m())
        o oVarA = a(8)
        Adler32 adler32 = Adler32()
        Array<Byte> bArr = new Byte[8192]
        ByteBuffer byteBufferDuplicate = this.f132b.duplicate()
        byteBufferDuplicate.limit(byteBufferDuplicate.capacity())
        byteBufferDuplicate.position(12)
        while (byteBufferDuplicate.hasRemaining()) {
            Int iMin = Math.min(8192, byteBufferDuplicate.remaining())
            byteBufferDuplicate.get(bArr, 0, iMin)
            adler32.update(bArr, 0, iMin)
        }
        oVarA.f((Int) adler32.getValue())
    }
}
