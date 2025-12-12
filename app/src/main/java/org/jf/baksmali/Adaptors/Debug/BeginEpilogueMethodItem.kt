package org.jf.baksmali.Adaptors.Debug

import java.io.IOException
import org.jf.baksmali.formatter.BaksmaliWriter

class BeginEpilogueMethodItem extends DebugMethodItem {
    constructor(Int i, Int i2) {
        super(i, i2)
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".prologue")
        return true
    }
}
