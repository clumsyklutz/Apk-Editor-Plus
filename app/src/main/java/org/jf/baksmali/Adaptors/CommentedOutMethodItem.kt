package org.jf.baksmali.Adaptors

import java.io.IOException
import org.jf.baksmali.formatter.BaksmaliWriter

class CommentedOutMethodItem extends MethodItem {
    public final MethodItem commentedOutMethodItem

    constructor(MethodItem methodItem) {
        super(methodItem.getCodeAddress())
        this.commentedOutMethodItem = methodItem
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return this.commentedOutMethodItem.getSortOrder() + 0.001d
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(35)
        this.commentedOutMethodItem.writeTo(baksmaliWriter)
        return true
    }
}
