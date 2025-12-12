package org.jf.baksmali.Adaptors

import org.jf.baksmali.BaksmaliOptions

class EndTryLabelMethodItem extends LabelMethodItem {
    public Int endTryAddress

    constructor(BaksmaliOptions baksmaliOptions, Int i, Int i2) {
        super(baksmaliOptions, i, "try_end_")
        this.endTryAddress = i2
    }

    @Override // org.jf.baksmali.Adaptors.LabelMethodItem
    fun getLabelAddress() {
        return this.endTryAddress
    }

    @Override // org.jf.baksmali.Adaptors.LabelMethodItem, org.jf.baksmali.Adaptors.MethodItem
    fun getSortOrder() {
        return 101.0d
    }
}
