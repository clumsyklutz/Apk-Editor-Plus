package org.jf.baksmali.Adaptors

import org.jf.baksmali.formatter.BaksmaliWriter

class BlankMethodItem extends MethodItem {
    constructor(Int i) {
        super(i)
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 2.147483647E9d
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) {
        return true
    }
}
