package b.a.a

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.logging.Level

class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final InputStream f87a

    /* renamed from: b, reason: collision with root package name */
    private final Socket f88b
    private /* synthetic */ a c

    private constructor(a aVar, InputStream inputStream, Socket socket) {
        this.c = aVar
        this.f87a = inputStream
        this.f88b = socket
    }

    /* synthetic */ c(a aVar, InputStream inputStream, Socket socket, Byte b2) {
        this(aVar, inputStream, socket)
    }

    public final Unit a() throws IOException {
        a.b(this.f87a)
        a.b(this.f88b)
    }

    @Override // java.lang.Runnable
    public final Unit run() throws IOException {
        OutputStream outputStream = null
        try {
            outputStream = this.f88b.getOutputStream()
            g gVar = g(this.c, this.c.m.a(), this.f87a, outputStream, this.f88b.getInetAddress())
            while (!this.f88b.isClosed()) {
                gVar.a()
            }
        } catch (Exception e) {
            if ((!(e is SocketException) || !"NanoHttpd Shutdown".equals(e.getMessage())) && !(e is SocketTimeoutException)) {
                a.g.log(Level.FINE, "Communication with the client broken", (Throwable) e)
            }
        } finally {
            a.b(outputStream)
            a.b(this.f87a)
            a.b(this.f88b)
            this.c.f84a.a(this)
        }
    }
}
