package b.a.a

import android.support.v4.media.session.PlaybackStateCompat
import com.gmail.heagoo.apkeditor.gzd
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.RandomAccessFile
import java.net.InetAddress
import java.net.SocketException
import java.net.SocketTimeoutException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.util.HashMap
import java.util.Locale
import java.util.Map
import java.util.StringTokenizer
import java.util.logging.Level
import java.util.regex.Matcher
import java.util.regex.Pattern

class g implements h {

    /* renamed from: a, reason: collision with root package name */
    private final r f93a

    /* renamed from: b, reason: collision with root package name */
    private final OutputStream f94b
    private final BufferedInputStream c
    private Int d
    private Int e
    private String f
    private i g
    private Map h
    private Map i
    private e j
    private String k
    private String l
    private String m
    private /* synthetic */ a n

    constructor(a aVar, r rVar, InputStream inputStream, OutputStream outputStream, InetAddress inetAddress) {
        this.n = aVar
        this.f93a = rVar
        this.c = BufferedInputStream(inputStream, 8192)
        this.f94b = outputStream
        this.l = (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) ? "127.0.0.1" : inetAddress.getHostAddress().toString()
        this.i = HashMap()
    }

    private fun a(Array<Byte> bArr, Int i) {
        while (bArr[i] != 10) {
            i++
        }
        return i + 1
    }

    private fun a(String str, Pattern pattern, String str2) {
        Matcher matcher = pattern.matcher(str)
        return matcher.find() ? matcher.group(2) : str2
    }

    private fun a(ByteBuffer byteBuffer, Int i, Int i2, String str) throws Throwable {
        FileOutputStream fileOutputStream
        q qVarB
        ByteBuffer byteBufferDuplicate
        String strB = ""
        if (i2 > 0) {
            try {
                qVarB = this.f93a.b()
                byteBufferDuplicate = byteBuffer.duplicate()
                fileOutputStream = FileOutputStream(qVarB.b())
            } catch (Exception e) {
                e = e
                fileOutputStream = null
            } catch (Throwable th) {
                th = th
                fileOutputStream = null
                a.b(fileOutputStream)
                throw th
            }
            try {
                try {
                    FileChannel channel = fileOutputStream.getChannel()
                    byteBufferDuplicate.position(i).limit(i + i2)
                    channel.write(byteBufferDuplicate.slice())
                    strB = qVarB.b()
                    a.b(fileOutputStream)
                } catch (Throwable th2) {
                    th = th2
                    a.b(fileOutputStream)
                    throw th
                }
            } catch (Exception e2) {
                e = e2
                throw Error(e)
            }
        }
        return strB
    }

    private fun a(BufferedReader bufferedReader, Map map, Map map2, Map map3) throws n, IOException {
        String strB
        try {
            String line = bufferedReader.readLine()
            if (line == null) {
                return
            }
            StringTokenizer stringTokenizer = StringTokenizer(line)
            if (!stringTokenizer.hasMoreTokens()) {
                throw n(m.f100b, "BAD REQUEST: Syntax error. Usage: GET /example/file.html")
            }
            map.put("method", stringTokenizer.nextToken())
            if (!stringTokenizer.hasMoreTokens()) {
                throw n(m.f100b, "BAD REQUEST: Missing URI. Usage: GET /example/file.html")
            }
            String strNextToken = stringTokenizer.nextToken()
            Int iIndexOf = strNextToken.indexOf(63)
            if (iIndexOf >= 0) {
                a(strNextToken.substring(iIndexOf + 1), map2)
                strB = a.b(strNextToken.substring(0, iIndexOf))
            } else {
                strB = a.b(strNextToken)
            }
            if (stringTokenizer.hasMoreTokens()) {
                this.m = stringTokenizer.nextToken()
            } else {
                this.m = "HTTP/1.1"
                a.g.log(Level.FINE, "no protocol version specified, strange. Assuming HTTP/1.1.")
            }
            String line2 = bufferedReader.readLine()
            while (line2 != null && line2.trim().length() > 0) {
                Int iIndexOf2 = line2.indexOf(58)
                if (iIndexOf2 >= 0) {
                    map3.put(line2.substring(0, iIndexOf2).trim().toLowerCase(Locale.US), line2.substring(iIndexOf2 + 1).trim())
                }
                line2 = bufferedReader.readLine()
            }
            map.put("uri", strB)
        } catch (IOException e) {
            throw n(m.d, "SERVER INTERNAL ERROR: IOException: " + e.getMessage(), e)
        }
    }

    private fun a(String str, String str2, ByteBuffer byteBuffer, Map map, Map map2) throws Throwable {
        Array<Int> iArr
        Array<Int> iArr2
        String strGroup
        String strGroup2
        try {
            Array<Byte> bytes = str.getBytes()
            Array<Int> iArr3 = new Int[0]
            if (byteBuffer.remaining() < bytes.length) {
                iArr = iArr3
            } else {
                Int i = 0
                Array<Byte> bArr = new Byte[bytes.length + 4096]
                Int iRemaining = byteBuffer.remaining() < bArr.length ? byteBuffer.remaining() : bArr.length
                byteBuffer.get(bArr, 0, iRemaining)
                Int length = iRemaining - bytes.length
                do {
                    Int i2 = 0
                    while (i2 < length) {
                        Int i3 = 0
                        Array<Int> iArr4 = iArr3
                        while (i3 < bytes.length && bArr[i2 + i3] == bytes[i3]) {
                            if (i3 == bytes.length - 1) {
                                iArr2 = new Int[iArr4.length + 1]
                                System.arraycopy(iArr4, 0, iArr2, 0, iArr4.length)
                                iArr2[iArr4.length] = i + i2
                            } else {
                                iArr2 = iArr4
                            }
                            i3++
                            iArr4 = iArr2
                        }
                        i2++
                        iArr3 = iArr4
                    }
                    i += length
                    System.arraycopy(bArr, bArr.length - bytes.length, bArr, 0, bytes.length)
                    length = bArr.length - bytes.length
                    if (byteBuffer.remaining() < length) {
                        length = byteBuffer.remaining()
                    }
                    byteBuffer.get(bArr, bytes.length, length)
                } while (length > 0);
                iArr = iArr3
            }
            if (iArr.length < 2) {
                throw n(m.f100b, "BAD REQUEST: Content type is multipart/form-data but contains less than two boundary strings.")
            }
            Array<Byte> bArr2 = new Byte[1024]
            for (Int i4 = 0; i4 < iArr.length - 1; i4++) {
                byteBuffer.position(iArr[i4])
                Int iRemaining2 = byteBuffer.remaining() < 1024 ? byteBuffer.remaining() : 1024
                byteBuffer.get(bArr2, 0, iRemaining2)
                BufferedReader bufferedReader = BufferedReader(InputStreamReader(ByteArrayInputStream(bArr2, 0, iRemaining2), Charset.forName(str2)), iRemaining2)
                if (!bufferedReader.readLine().contains(str)) {
                    throw n(m.f100b, "BAD REQUEST: Content type is multipart/form-data but chunk does not start with boundary.")
                }
                String str3 = null
                String str4 = null
                String strTrim = null
                String line = bufferedReader.readLine()
                Int i5 = 2
                while (line != null && line.trim().length() > 0) {
                    Matcher matcher = a.d.matcher(line)
                    if (matcher.matches()) {
                        Matcher matcher2 = a.f.matcher(matcher.group(2))
                        strGroup = str4
                        strGroup2 = str3
                        while (matcher2.find()) {
                            String strGroup3 = matcher2.group(1)
                            if (strGroup3.equalsIgnoreCase("name")) {
                                strGroup2 = matcher2.group(2)
                            } else if (strGroup3.equalsIgnoreCase("filename")) {
                                strGroup = matcher2.group(2)
                            }
                        }
                    } else {
                        strGroup = str4
                        strGroup2 = str3
                    }
                    Matcher matcher3 = a.e.matcher(line)
                    i5++
                    strTrim = matcher3.matches() ? matcher3.group(2).trim() : strTrim
                    line = bufferedReader.readLine()
                    str3 = strGroup2
                    str4 = strGroup
                }
                Int iA = 0
                while (true) {
                    Int i6 = i5 - 1
                    if (i5 <= 0) {
                        break
                    }
                    iA = a(bArr2, iA)
                    i5 = i6
                }
                if (iA >= iRemaining2 - 4) {
                    throw n(m.d, "Multipart header size exceeds MAX_HEADER_SIZE.")
                }
                Int i7 = iA + iArr[i4]
                Int i8 = iArr[i4 + 1] - 4
                byteBuffer.position(i7)
                if (strTrim == null) {
                    Array<Byte> bArr3 = new Byte[i8 - i7]
                    byteBuffer.get(bArr3)
                    map.put(str3, String(bArr3, str2))
                } else {
                    String strA = a(byteBuffer, i7, i8 - i7, str4)
                    if (map2.containsKey(str3)) {
                        Int i9 = 2
                        while (map2.containsKey(str3 + i9)) {
                            i9++
                        }
                        map2.put(str3 + i9, strA)
                    } else {
                        map2.put(str3, strA)
                    }
                    map.put(str3, str4)
                }
            }
        } catch (n e) {
            throw e
        } catch (Exception e2) {
            throw n(m.d, e2.toString())
        }
    }

    private fun a(String str, Map map) {
        if (str == null) {
            this.k = ""
            return
        }
        this.k = str
        StringTokenizer stringTokenizer = StringTokenizer(str, "&")
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken()
            Int iIndexOf = strNextToken.indexOf(61)
            if (iIndexOf >= 0) {
                map.put(a.b(strNextToken.substring(0, iIndexOf)).trim(), a.b(strNextToken.substring(iIndexOf + 1)))
            } else {
                map.put(a.b(strNextToken).trim(), "")
            }
        }
    }

    private fun b(Array<Byte> bArr, Int i) {
        for (Int i2 = 0; i2 + 1 < i; i2++) {
            if (bArr[i2] == 13 && bArr[i2 + 1] == 10 && i2 + 3 < i && bArr[i2 + 2] == 13 && bArr[i2 + 3] == 10) {
                return i2 + 4
            }
            if (bArr[i2] == 10 && bArr[i2 + 1] == 10) {
                return i2 + 2
            }
        }
        return 0
    }

    private fun f() {
        try {
            return RandomAccessFile(this.f93a.b().b(), "rw")
        } catch (Exception e) {
            throw Error(e)
        }
    }

    public final Unit a() throws IOException {
        try {
            try {
                try {
                    try {
                        Array<Byte> bArr = new Byte[8192]
                        this.d = 0
                        this.e = 0
                        this.c.mark(8192)
                        try {
                            Int i = this.c.read(bArr, 0, 8192)
                            if (i == -1) {
                                a.b(this.c)
                                a.b(this.f94b)
                                throw SocketException("NanoHttpd Shutdown")
                            }
                            while (i > 0) {
                                this.e = i + this.e
                                this.d = b(bArr, this.e)
                                if (this.d > 0) {
                                    break
                                } else {
                                    i = this.c.read(bArr, this.e, 8192 - this.e)
                                }
                            }
                            if (this.d < this.e) {
                                this.c.reset()
                                this.c.skip(this.d)
                            }
                            this.h = HashMap()
                            if (this.i == null) {
                                this.i = HashMap()
                            } else {
                                this.i.clear()
                            }
                            BufferedReader bufferedReader = BufferedReader(InputStreamReader(ByteArrayInputStream(bArr, 0, this.e)))
                            HashMap map = HashMap()
                            a(bufferedReader, map, this.h, this.i)
                            if (this.l != null) {
                                this.i.put("remote-addr", this.l)
                                this.i.put("http-client-ip", this.l)
                            }
                            this.g = i.a((String) map.get("method"))
                            if (this.g == null) {
                                throw n(m.f100b, "BAD REQUEST: Syntax error.")
                            }
                            this.f = (String) map.get("uri")
                            this.j = e(this.n, this.i)
                            String str = (String) this.i.get("connection")
                            Boolean z = this.m.equals("HTTP/1.1") && (str == null || !str.matches("(?i).*close.*"))
                            j jVarA = this.n.a((h) this)
                            if (jVarA == null) {
                                throw n(m.d, "SERVER INTERNAL ERROR: Serve() returned a null response.")
                            }
                            String str2 = (String) this.i.get("accept-encoding")
                            this.j.a(jVarA)
                            jVarA.a(this.g)
                            jVarA.a(a.a(jVarA) && str2 != null && str2.contains("gzip"))
                            jVarA.b(z)
                            jVarA.a(this.f94b)
                            if (!z || "close".equalsIgnoreCase(jVarA.a("connection"))) {
                                throw SocketException("NanoHttpd Shutdown")
                            }
                            a.b(jVarA)
                            this.f93a.a()
                        } catch (Exception e) {
                            a.b(this.c)
                            a.b(this.f94b)
                            throw SocketException("NanoHttpd Shutdown")
                        }
                    } catch (n e2) {
                        a.a(e2.a(), "text/plain", e2.getMessage()).a(this.f94b)
                        a.b(this.f94b)
                        a.b((Object) null)
                        this.f93a.a()
                    } catch (IOException e3) {
                        a.a(m.d, "text/plain", "SERVER INTERNAL ERROR: IOException: " + e3.getMessage()).a(this.f94b)
                        a.b(this.f94b)
                        a.b((Object) null)
                        this.f93a.a()
                    }
                } catch (SocketException e4) {
                    throw e4
                }
            } catch (SocketTimeoutException e5) {
                throw e5
            }
        } catch (Throwable th) {
            a.b((Object) null)
            this.f93a.a()
            throw th
        }
    }

    @Override // b.a.a.h
    public final Unit a(Map map) throws Throwable {
        RandomAccessFile randomAccessFile
        Long j
        ByteArrayOutputStream byteArrayOutputStream
        RandomAccessFile randomAccessFile2
        DataOutput dataOutputStream
        ByteBuffer map2
        StringTokenizer stringTokenizer = null
        try {
            j = this.i.containsKey("content-length") ? Long.parseLong((String) this.i.get("content-length")) : this.d < this.e ? this.e - this.d : 0L
            if (j < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                byteArrayOutputStream = ByteArrayOutputStream()
                randomAccessFile2 = null
                dataOutputStream = DataOutputStream(byteArrayOutputStream)
            } else {
                RandomAccessFile randomAccessFileF = f()
                byteArrayOutputStream = null
                randomAccessFile2 = randomAccessFileF
                dataOutputStream = randomAccessFileF
            }
        } catch (Throwable th) {
            th = th
            randomAccessFile = null
        }
        try {
            Array<Byte> bArr = new Byte[512]
            while (this.e >= 0 && j > 0) {
                this.e = this.c.read(bArr, 0, (Int) Math.min(j, 512L))
                j -= this.e
                if (this.e > 0) {
                    dataOutputStream.write(bArr, 0, this.e)
                }
            }
            if (byteArrayOutputStream != null) {
                map2 = ByteBuffer.wrap(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size())
            } else {
                map2 = randomAccessFile2.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, randomAccessFile2.length())
                randomAccessFile2.seek(0L)
            }
            if (i.POST.equals(this.g)) {
                String strNextToken = ""
                String str = (String) this.i.get("content-type")
                if (str != null) {
                    stringTokenizer = StringTokenizer(str, ",; ")
                    if (stringTokenizer.hasMoreTokens()) {
                        strNextToken = stringTokenizer.nextToken()
                    }
                }
                if (!"multipart/form-data".equalsIgnoreCase(strNextToken)) {
                    Array<Byte> bArr2 = new Byte[map2.remaining()]
                    map2.get(bArr2)
                    String strTrim = String(bArr2).trim()
                    if ("application/x-www-form-urlencoded".equalsIgnoreCase(strNextToken)) {
                        a(strTrim, this.h)
                    } else if (strTrim.length() != 0) {
                        map.put("postData", strTrim)
                    }
                } else {
                    if (!stringTokenizer.hasMoreTokens()) {
                        throw n(m.f100b, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html")
                    }
                    a(a(str, a.c, null), a(str, a.f83b, "US-ASCII"), map2, this.h, map)
                }
            } else if (i.PUT.equals(this.g)) {
                map.put(gzd.COLUMN_CONTENT, a(map2, 0, map2.limit(), (String) null))
            }
            a.b(randomAccessFile2)
        } catch (Throwable th2) {
            th = th2
            randomAccessFile = randomAccessFile2
            a.b(randomAccessFile)
            throw th
        }
    }

    @Override // b.a.a.h
    public final i b() {
        return this.g
    }

    @Override // b.a.a.h
    public final Map c() {
        return this.h
    }

    @Override // b.a.a.h
    public final String d() {
        return this.k
    }

    @Override // b.a.a.h
    public final String e() {
        return this.f
    }
}
