package org.jf.baksmali.Adaptors

import java.io.IOException
import org.jf.baksmali.BaksmaliOptions
import org.jf.baksmali.formatter.BaksmaliWriter

class LabelMethodItem extends MethodItem {
    public final String labelPrefix
    public Int labelSequence
    public final BaksmaliOptions options

    constructor(BaksmaliOptions baksmaliOptions, Int i, String str) {
        super(i)
        this.options = baksmaliOptions
        this.labelPrefix = str
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem, java.lang.Comparable
    fun compareTo(MethodItem methodItem) {
        Int iCompareTo = super.compareTo(methodItem)
        return (iCompareTo == 0 && (methodItem is LabelMethodItem)) ? this.labelPrefix.compareTo(((LabelMethodItem) methodItem).labelPrefix) : iCompareTo
    }

    fun equals(Object obj) {
        return (obj is LabelMethodItem) && compareTo((MethodItem) obj) == 0
    }

    fun getLabelAddress() {
        return getCodeAddress()
    }

    fun getLabelPrefix() {
        return this.labelPrefix
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 0.0d
    }

    fun hashCode() {
        return getCodeAddress()
    }

    fun setLabelSequence(Int i) {
        this.labelSequence = i
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(58)
        baksmaliWriter.write(this.labelPrefix)
        if (this.options.sequentialLabels) {
            baksmaliWriter.writeUnsignedLongAsHex(this.labelSequence)
            return true
        }
        baksmaliWriter.writeUnsignedLongAsHex(getLabelAddress())
        return true
    }
}
