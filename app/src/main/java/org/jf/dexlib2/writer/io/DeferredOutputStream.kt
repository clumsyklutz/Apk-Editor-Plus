package org.jf.dexlib2.writer.io

import java.io.IOException
import java.io.OutputStream

abstract class DeferredOutputStream extends OutputStream {
    public abstract Unit writeTo(OutputStream outputStream) throws IOException
}
