package org.jf.baksmali.Adaptors

import java.io.IOException
import org.jf.baksmali.formatter.BaksmaliWriter

class CommentMethodItem extends MethodItem {
    public final String comment
    public final Double sortOrder

    constructor(String str, Int i, Double d) {
        super(i)
        this.comment = str
        this.sortOrder = d
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return this.sortOrder
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(35)
        baksmaliWriter.write(this.comment)
        return true
    }
}
