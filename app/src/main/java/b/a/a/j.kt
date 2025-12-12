package b.a.a

import android.support.v4.media.session.PlaybackStateCompat
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import java.util.Iterator
import java.util.Locale
import java.util.Map
import java.util.TimeZone
import java.util.logging.Level
import java.util.zip.GZIPOutputStream

class j implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    private l f97a

    /* renamed from: b, reason: collision with root package name */
    private String f98b
    private InputStream c
    private Long d
    private val e = HashMap()
    private i f
    private Boolean g
    private Boolean h
    private Boolean i

    protected constructor(l lVar, String str, InputStream inputStream, Long j) {
        this.f97a = lVar
        this.f98b = str
        if (inputStream == null) {
            this.c = ByteArrayInputStream(new Byte[0])
            this.d = 0L
        } else {
            this.c = inputStream
            this.d = j
        }
        this.g = this.d < 0
        this.i = true
    }

    private fun a(PrintWriter printWriter, Map map, Long j) {
        for (String str : map.keySet()) {
            if (str.equalsIgnoreCase("content-length")) {
                try {
                    return Long.parseLong((String) map.get(str))
                } catch (NumberFormatException e) {
                    return j
                }
            }
        }
        printWriter.print("Content-Length: " + j + "\r\n")
        return j
    }

    private fun a(OutputStream outputStream, Long j) throws IOException {
        if (!this.h) {
            b(outputStream, j)
            return
        }
        GZIPOutputStream gZIPOutputStream = GZIPOutputStream(outputStream)
        b(gZIPOutputStream, -1L)
        gZIPOutputStream.finish()
    }

    private fun a(Map map, String str) {
        Boolean zEqualsIgnoreCase = false
        Iterator it = map.keySet().iterator()
        while (true) {
            Boolean z = zEqualsIgnoreCase
            if (!it.hasNext()) {
                return z
            }
            zEqualsIgnoreCase = ((String) it.next()).equalsIgnoreCase(str) | z
        }
    }

    private fun b(OutputStream outputStream, Long j) throws IOException {
        Array<Byte> bArr = new Byte[16384]
        Boolean z = j == -1
        Long j2 = j
        while (true) {
            if (j2 <= 0 && !z) {
                return
            }
            Int i = this.c.read(bArr, 0, (Int) (z ? 16384L : Math.min(j2, PlaybackStateCompat.ACTION_PREPARE)))
            if (i <= 0) {
                return
            }
            outputStream.write(bArr, 0, i)
            j2 = !z ? j2 - i : j2
        }
    }

    public final String a() {
        return this.f98b
    }

    public final String a(String str) {
        for (String str2 : this.e.keySet()) {
            if (str2.equalsIgnoreCase(str)) {
                return (String) this.e.get(str2)
            }
        }
        return null
    }

    public final Unit a(i iVar) {
        this.f = iVar
    }

    protected final Unit a(OutputStream outputStream) throws IOException {
        String str = this.f98b
        SimpleDateFormat simpleDateFormat = SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US)
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
        try {
            if (this.f97a == null) {
                throw Error("sendResponse(): Status can't be null.")
            }
            PrintWriter printWriter = PrintWriter((Writer) BufferedWriter(OutputStreamWriter(outputStream, "UTF-8")), false)
            printWriter.print("HTTP/1.1 " + this.f97a.a() + " \r\n")
            if (str != null) {
                printWriter.print("Content-Type: " + str + "\r\n")
            }
            if (this.e.get("Date") == null) {
                printWriter.print("Date: " + simpleDateFormat.format(Date()) + "\r\n")
            }
            for (String str2 : this.e.keySet()) {
                printWriter.print(str2 + ": " + ((String) this.e.get(str2)) + "\r\n")
            }
            if (!a(this.e, "connection")) {
                printWriter.print("Connection: " + (this.i ? "keep-alive" : "close") + "\r\n")
            }
            if (a(this.e, "content-length")) {
                this.h = false
            }
            if (this.h) {
                printWriter.print("Content-Encoding: gzip\r\n")
                this.g = true
            }
            Long jA = this.c != null ? this.d : 0L
            if (this.f != i.HEAD && this.g) {
                printWriter.print("Transfer-Encoding: chunked\r\n")
            } else if (!this.h) {
                jA = a(printWriter, this.e, jA)
            }
            printWriter.print("\r\n")
            printWriter.flush()
            if (this.f == i.HEAD || !this.g) {
                a(outputStream, jA)
            } else {
                k kVar = k(outputStream)
                a(kVar, -1L)
                kVar.a()
            }
            outputStream.flush()
            a.b(this.c)
        } catch (IOException e) {
            a.g.log(Level.SEVERE, "Could not send response to the client", (Throwable) e)
        }
    }

    public final Unit a(String str, String str2) {
        this.e.put(str, str2)
    }

    public final Unit a(Boolean z) {
        this.h = z
    }

    public final Unit b(Boolean z) {
        this.i = z
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final Unit close() throws IOException {
        if (this.c != null) {
            this.c.close()
        }
    }
}
