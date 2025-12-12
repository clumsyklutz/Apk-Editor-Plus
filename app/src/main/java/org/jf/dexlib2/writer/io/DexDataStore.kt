package org.jf.dexlib2.writer.io

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

public interface DexDataStore {
    Unit close() throws IOException

    OutputStream outputAt(Int i)

    InputStream readAt(Int i)
}
