package org.jf.baksmali.Adaptors.Debug

import java.io.IOException
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.debug.SetSourceFile

class SetSourceFileMethodItem extends DebugMethodItem {
    public final String sourceFile

    constructor(Int i, Int i2, SetSourceFile setSourceFile) {
        super(i, i2)
        this.sourceFile = setSourceFile.getSourceFile()
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".source")
        if (this.sourceFile == null) {
            return true
        }
        baksmaliWriter.write(" ")
        baksmaliWriter.writeQuotedString(this.sourceFile)
        return true
    }
}
