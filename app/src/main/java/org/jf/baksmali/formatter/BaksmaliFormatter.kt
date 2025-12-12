package org.jf.baksmali.formatter

import java.io.Writer
import org.jf.dexlib2.formatter.DexFormatter

class BaksmaliFormatter extends DexFormatter {
    public final String classContext

    constructor(String str) {
        this.classContext = str
    }

    @Override // org.jf.dexlib2.formatter.DexFormatter
    fun getWriter(Writer writer) {
        return BaksmaliWriter(writer, this.classContext)
    }
}
