package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList
import java.util.Iterator

class i extends b {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList f741a = ArrayList()

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        Int iB = 0
        Iterator it = this.f741a.iterator()
        while (true) {
            Int i = iB
            if (!it.hasNext()) {
                return i
            }
            iB = ((b) it.next()).b() + i
        }
    }

    public final i a(b bVar) {
        this.f741a.add(bVar)
        return this
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(48)
        a(outputStream, a())
        Iterator it = this.f741a.iterator()
        while (it.hasNext()) {
            ((b) it.next()).a(outputStream)
        }
    }
}
