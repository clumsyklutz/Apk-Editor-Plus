package b.a.a

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class q {

    /* renamed from: a, reason: collision with root package name */
    private final File f104a

    /* renamed from: b, reason: collision with root package name */
    private final OutputStream f105b

    constructor(File file) {
        this.f104a = File.createTempFile("NanoHTTPD-", "", file)
        this.f105b = FileOutputStream(this.f104a)
    }

    fun a() throws Exception {
        a.b(this.f105b)
        if (!this.f104a.delete()) {
            throw Exception("could not delete temporary file")
        }
    }

    fun b() {
        return this.f104a.getAbsolutePath()
    }
}
