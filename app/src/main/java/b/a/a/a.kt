package b.a.a

import java.io.ByteArrayInputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.ServerSocket
import java.net.Socket
import java.net.URLDecoder
import java.util.HashMap
import java.util.logging.Level
import java.util.logging.Logger
import java.util.regex.Pattern

abstract class a {

    /* renamed from: b, reason: collision with root package name */
    private static val f83b = Pattern.compile("[ |\t]*(charset)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;]*)['|\"]?", 2)
    private static val c = Pattern.compile("[ |\t]*(boundary)[ |\t]*=[ |\t]*['|\"]?([^\"^'^;]*)['|\"]?", 2)
    private static val d = Pattern.compile("([ |\t]*Content-Disposition[ |\t]*:)(.*)", 2)
    private static val e = Pattern.compile("([ |\t]*content-type[ |\t]*:)(.*)", 2)
    private static val f = Pattern.compile("[ |\t]*([a-zA-Z]*)[ |\t]*=[ |\t]*['|\"]([^\"^']*)['|\"]")
    private static val g = Logger.getLogger(a.class.getName())

    /* renamed from: a, reason: collision with root package name */
    protected b f84a
    private final String h
    private final Int i
    private volatile ServerSocket j
    private p k
    private Thread l
    private s m

    constructor(Int i) {
        this(null, 8000)
    }

    private constructor(String str, Int i) {
        this.k = p()
        this.h = null
        this.i = i
        this.m = f(this, (Byte) 0)
        this.f84a = b()
    }

    fun a(l lVar, String str, InputStream inputStream, Long j) {
        return j(lVar, str, inputStream, j)
    }

    fun a(l lVar, String str, String str2) throws UnsupportedEncodingException {
        Array<Byte> bytes
        if (str2 == null) {
            return a(lVar, str, ByteArrayInputStream(new Byte[0]), 0L)
        }
        try {
            bytes = str2.getBytes("UTF-8")
        } catch (UnsupportedEncodingException e2) {
            g.log(Level.SEVERE, "encoding problem, responding nothing", (Throwable) e2)
            bytes = new Byte[0]
        }
        return a(lVar, str, ByteArrayInputStream(bytes), bytes.length)
    }

    protected static Boolean a(j jVar) {
        return jVar.a() != null && jVar.a().toLowerCase().contains("text/")
    }

    protected static String b(String str) {
        try {
            return URLDecoder.decode(str, "UTF8")
        } catch (UnsupportedEncodingException e2) {
            g.log(Level.WARNING, "Encoding not supported, ignored", (Throwable) e2)
            return null
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(Object obj) throws IOException {
        if (obj != null) {
            try {
                if (obj is Closeable) {
                    ((Closeable) obj).close()
                } else if (obj is Socket) {
                    ((Socket) obj).close()
                } else {
                    if (!(obj is ServerSocket)) {
                        throw IllegalArgumentException("Unknown object to close")
                    }
                    ((ServerSocket) obj).close()
                }
            } catch (IOException e2) {
                g.log(Level.SEVERE, "Could not close", (Throwable) e2)
            }
        }
    }

    fun a(h hVar) {
        HashMap map = HashMap()
        i iVarB = hVar.b()
        if (i.PUT.equals(iVarB) || i.POST.equals(iVarB)) {
            try {
                hVar.a(map)
            } catch (n e2) {
                return a(e2.a(), "text/plain", e2.getMessage())
            } catch (IOException e3) {
                return a(m.d, "text/plain", "SERVER INTERNAL ERROR: IOException: " + e3.getMessage())
            }
        }
        hVar.c().put("NanoHttpd.QUERY_STRING", hVar.d())
        return a(m.c, "text/plain", "Not Found")
    }

    public final Unit a(Int i, Boolean z) throws IOException {
        this.j = this.k.a()
        this.j.setReuseAddress(true)
        o oVar = o(this, i, (Byte) 0)
        this.l = Thread(oVar)
        this.l.setDaemon(true)
        this.l.setName("NanoHttpd Main Listener")
        this.l.start()
        while (!oVar.c && oVar.f103b == null) {
            try {
                Thread.sleep(10L)
            } catch (Throwable th) {
            }
        }
        if (oVar.f103b != null) {
            throw oVar.f103b
        }
    }

    public final Int c() {
        if (this.j == null) {
            return -1
        }
        return this.j.getLocalPort()
    }

    public final Boolean d() {
        return (this.j != null && this.l != null) && !this.j.isClosed() && this.l.isAlive()
    }

    public final Unit e() throws InterruptedException {
        try {
            b(this.j)
            this.f84a.a()
            if (this.l != null) {
                this.l.join()
            }
        } catch (Exception e2) {
            g.log(Level.SEVERE, "Could not stop all connections", (Throwable) e2)
        }
    }
}
