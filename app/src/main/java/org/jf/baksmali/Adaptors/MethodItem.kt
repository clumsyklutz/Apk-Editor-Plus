package org.jf.baksmali.Adaptors

import java.io.IOException
import org.jf.baksmali.formatter.BaksmaliWriter

abstract class MethodItem implements Comparable<MethodItem> {
    public final Int codeAddress

    constructor(Int i) {
        this.codeAddress = i
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    fun compareTo(MethodItem methodItem) {
        Int iCompareTo = Integer.valueOf(this.codeAddress).compareTo(Integer.valueOf(methodItem.codeAddress))
        return iCompareTo == 0 ? Double.valueOf(getSortOrder()).compareTo(Double.valueOf(methodItem.getSortOrder())) : iCompareTo
    }

    fun getCodeAddress() {
        return this.codeAddress
    }

    public abstract Double getSortOrder()

    public abstract Boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException
}
