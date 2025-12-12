package org.jf.baksmali.Adaptors

import java.io.IOException
import org.jf.baksmali.Adaptors.MethodDefinition
import org.jf.baksmali.BaksmaliOptions
import org.jf.baksmali.formatter.BaksmaliWriter

class CatchMethodItem extends MethodItem {
    public final String exceptionType
    public final LabelMethodItem handlerLabel
    public final LabelMethodItem tryEndLabel
    public final LabelMethodItem tryStartLabel

    constructor(BaksmaliOptions baksmaliOptions, MethodDefinition.LabelCache labelCache, Int i, String str, Int i2, Int i3, Int i4) {
        super(i)
        this.exceptionType = str
        this.tryStartLabel = labelCache.internLabel(LabelMethodItem(baksmaliOptions, i2, "try_start_"))
        this.tryEndLabel = labelCache.internLabel(EndTryLabelMethodItem(baksmaliOptions, i, i3))
        if (str == null) {
            this.handlerLabel = labelCache.internLabel(LabelMethodItem(baksmaliOptions, i4, "catchall_"))
        } else {
            this.handlerLabel = labelCache.internLabel(LabelMethodItem(baksmaliOptions, i4, "catch_"))
        }
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 102.0d
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        if (this.exceptionType == null) {
            baksmaliWriter.write(".catchall")
        } else {
            baksmaliWriter.write(".catch ")
            baksmaliWriter.write(this.exceptionType)
        }
        baksmaliWriter.write(" {")
        this.tryStartLabel.writeTo(baksmaliWriter)
        baksmaliWriter.write(" .. ")
        this.tryEndLabel.writeTo(baksmaliWriter)
        baksmaliWriter.write("} ")
        this.handlerLabel.writeTo(baksmaliWriter)
        return true
    }
}
