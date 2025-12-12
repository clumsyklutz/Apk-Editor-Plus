package org.jf.baksmali.Adaptors.Debug

import java.io.IOException
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.debug.LineNumber

class LineNumberMethodItem extends DebugMethodItem {
    public final Int lineNumber

    constructor(Int i, Int i2, LineNumber lineNumber) {
        super(i, i2)
        this.lineNumber = lineNumber.getLineNumber()
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".line ")
        baksmaliWriter.writeUnsignedIntAsDec(this.lineNumber)
        return true
    }
}
