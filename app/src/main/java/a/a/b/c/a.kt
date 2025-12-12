package a.a.b.c

import a.c.d
import a.c.f
import a.c.i
import java.io.File

class a extends File {

    /* renamed from: a, reason: collision with root package name */
    private d f60a

    constructor(File file) {
        super(file.getPath())
    }

    public final d a() {
        if (this.f60a == null) {
            if (isDirectory()) {
                this.f60a = f(this)
            } else {
                this.f60a = i(this)
            }
        }
        return this.f60a
    }
}
