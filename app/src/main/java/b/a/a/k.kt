package b.a.a

import java.io.FilterOutputStream
import java.io.IOException
import java.io.OutputStream

final class k extends FilterOutputStream {
    constructor(OutputStream outputStream) {
        super(outputStream)
    }

    public final Unit a() throws IOException {
        this.out.write("0\r\n\r\n".getBytes())
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final Unit write(Int i) throws IOException {
        write(new Array<Byte>{(Byte) i}, 0, 1)
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final Unit write(Array<Byte> bArr) throws IOException {
        write(bArr, 0, bArr.length)
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public final Unit write(Array<Byte> bArr, Int i, Int i2) throws IOException {
        if (i2 == 0) {
            return
        }
        this.out.write(String.format("%x\r\n", Integer.valueOf(i2)).getBytes())
        this.out.write(bArr, i, i2)
        this.out.write("\r\n".getBytes())
    }
}
