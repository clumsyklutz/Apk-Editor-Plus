package a.a.b.b

import java.io.InputStream
import java.io.OutputStream
import java.util.HashMap
import java.util.Map

class l {

    /* renamed from: a, reason: collision with root package name */
    private val f57a = HashMap()

    public final Unit a(InputStream inputStream, OutputStream outputStream, String str) throws a.a.ExceptionA {
        k kVar = (k) this.f57a.get(str)
        if (kVar == null) {
            throw new a.a.ExceptionA("Unkown DER: " + str)
        }
        kVar.a(inputStream, outputStream)
    }

    public final Unit a(String str, k kVar) {
        this.f57a.put(str, kVar)
    }
}
