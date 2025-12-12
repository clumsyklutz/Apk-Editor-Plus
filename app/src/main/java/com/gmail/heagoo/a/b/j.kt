package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList
import java.util.Iterator

class j extends b {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList f742a = ArrayList()

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        Int iB = 0
        Iterator it = this.f742a.iterator()
        while (true) {
            Int i = iB
            if (!it.hasNext()) {
                return i
            }
            iB = ((b) it.next()).b() + i
        }
    }

    public final j a(b bVar) {
        this.f742a.add(bVar)
        return this
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(49)
        a(outputStream, a())
        Iterator it = this.f742a.iterator()
        while (it.hasNext()) {
            ((b) it.next()).a(outputStream)
        }
    }
}
