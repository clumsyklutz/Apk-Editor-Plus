package org.jf.dexlib2.immutable.instruction

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.util.ImmutableConverter

class ImmutableSwitchElement implements SwitchElement {
    public static final ImmutableConverter<ImmutableSwitchElement, SwitchElement> CONVERTER = new ImmutableConverter<ImmutableSwitchElement, SwitchElement>() { // from class: org.jf.dexlib2.immutable.instruction.ImmutableSwitchElement.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(SwitchElement switchElement) {
            return switchElement is ImmutableSwitchElement
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(SwitchElement switchElement) {
            return ImmutableSwitchElement.of(switchElement)
        }
    }
    public final Int key
    public final Int offset

    constructor(Int i, Int i2) {
        this.key = i
        this.offset = i2
    }

    public static ImmutableList<ImmutableSwitchElement> immutableListOf(List<? extends SwitchElement> list) {
        return CONVERTER.toList(list)
    }

    fun of(SwitchElement switchElement) {
        return switchElement is ImmutableSwitchElement ? (ImmutableSwitchElement) switchElement : ImmutableSwitchElement(switchElement.getKey(), switchElement.getOffset())
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    fun getKey() {
        return this.key
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    fun getOffset() {
        return this.offset
    }
}
