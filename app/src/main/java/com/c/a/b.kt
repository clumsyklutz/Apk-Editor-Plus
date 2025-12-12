package com.c.a

class b extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private f f696a

    /* renamed from: b, reason: collision with root package name */
    private a f697b
    private Boolean c
    private Boolean d
    private Long e

    constructor(f fVar, String str) {
        super(str)
        this.c = false
        this.d = false
        this.e = -1L
        this.f696a = fVar
    }

    private synchronized Unit c() {
        this.e = System.currentTimeMillis()
        this.d = true
        notifyAll()
    }

    public final synchronized Unit a() {
        this.c = false
        this.d = false
        notifyAll()
    }

    public final Unit a(a aVar) {
        if (this.d) {
            this.d = false
        }
        this.f697b = aVar
        c()
    }

    public final Unit b() {
        this.d = false
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        this.c = true
        while (this.c) {
            while (this.d && this.f697b != null) {
                Long jCurrentTimeMillis = System.currentTimeMillis()
                this.d = this.f697b.a(this.f696a, jCurrentTimeMillis - this.e)
                this.f696a.postInvalidate()
                this.e = jCurrentTimeMillis
                while (this.d) {
                    try {
                    } catch (InterruptedException e) {
                        this.d = false
                    }
                    if (this.f696a.a(32L)) {
                        break
                    }
                }
            }
            synchronized (this) {
                if (this.c) {
                    try {
                        wait()
                    } catch (InterruptedException e2) {
                    }
                }
            }
        }
    }
}
