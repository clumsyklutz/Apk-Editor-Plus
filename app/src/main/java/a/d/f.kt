package a.d

import java.io.DataInputStream
import java.io.IOException
import java.io.InputStream

class f extends d {
    constructor(a.a.b.a.b bVar) {
        super(bVar)
    }

    constructor(InputStream inputStream) {
        super(DataInputStream(inputStream))
    }

    public final String a(Int i, Boolean z) {
        Int i2
        Short s
        StringBuilder sb = StringBuilder(16)
        while (true) {
            i2 = i - 1
            if (i == 0 || (s = readShort()) == 0) {
                break
            }
            sb.append((Char) s)
            i = i2
        }
        skipBytes(i2 << 1)
        return sb.toString()
    }

    public final Unit a(Int i, Int i2) throws IOException {
        Int i3 = readInt()
        if (i3 == i2 || i3 < i) {
            a(i, -1)
        } else if (i3 != i) {
            throw IOException(String.format("Expected: 0x%08x, actual: 0x%08x", Integer.valueOf(i), Integer.valueOf(i3)))
        }
    }
}
