package org.jf.dexlib2.builder

class SwitchLabelElement {
    public final Int key
    public final Label target

    constructor(Int i, Label label) {
        this.key = i
        this.target = label
    }
}
