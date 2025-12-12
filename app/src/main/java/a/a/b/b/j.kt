package a.a.b.b

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class j implements k {
    @Override // a.a.b.b.k
    public final Unit a(InputStream inputStream, OutputStream outputStream) throws IOException, a.a.ExceptionA {
        try {
            Array<Byte> bArr = new Byte[4096]
            while (true) {
                Int i = inputStream.read(bArr)
                if (i == -1) {
                    return
                } else {
                    outputStream.write(bArr, 0, i)
                }
            }
        } catch (IOException e) {
            throw new a.a.ExceptionA("Cannot decode raw stream", e)
        }
    }
}
