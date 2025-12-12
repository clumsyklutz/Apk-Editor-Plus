package b.a.a

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.logging.Level

class o implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final Int f102a

    /* renamed from: b, reason: collision with root package name */
    private IOException f103b
    private Boolean c
    private /* synthetic */ a d

    private constructor(a aVar, Int i) {
        this.d = aVar
        this.c = false
        this.f102a = i
    }

    /* synthetic */ o(a aVar, Int i, Byte b2) {
        this(aVar, i)
    }

    @Override // java.lang.Runnable
    public final Unit run() throws IOException {
        try {
            this.d.j.bind(this.d.h != null ? InetSocketAddress(this.d.h, this.d.i) : InetSocketAddress(this.d.i))
            this.c = true
            do {
                try {
                    Socket socketAccept = this.d.j.accept()
                    if (this.f102a > 0) {
                        socketAccept.setSoTimeout(this.f102a)
                    }
                    this.d.f84a.b(c(this.d, socketAccept.getInputStream(), socketAccept, (Byte) 0))
                } catch (IOException e) {
                    a.g.log(Level.FINE, "Communication with the client broken", (Throwable) e)
                }
            } while (!this.d.j.isClosed());
        } catch (IOException e2) {
            this.f103b = e2
        }
    }
}
