package org.jf.baksmali.Adaptors.Format

import java.io.IOException
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import org.jf.baksmali.Adaptors.LabelMethodItem
import org.jf.baksmali.Adaptors.MethodDefinition
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue

class PackedSwitchMethodItem extends InstructionMethodItem<PackedSwitchPayload> {
    public Boolean commentedOut
    public final Int firstKey
    public final List<PackedSwitchTarget> targets

    public static class PackedSwitchLabelTarget extends PackedSwitchTarget {
        public final LabelMethodItem target

        constructor(LabelMethodItem labelMethodItem) {
            super()
            this.target = labelMethodItem
        }

        @Override // org.jf.baksmali.Adaptors.Format.PackedSwitchMethodItem.PackedSwitchTarget
        fun writeTargetTo(BaksmaliWriter baksmaliWriter) throws IOException {
            this.target.writeTo(baksmaliWriter)
        }
    }

    public static class PackedSwitchOffsetTarget extends PackedSwitchTarget {
        public final Int target

        constructor(Int i) {
            super()
            this.target = i
        }

        @Override // org.jf.baksmali.Adaptors.Format.PackedSwitchMethodItem.PackedSwitchTarget
        fun writeTargetTo(BaksmaliWriter baksmaliWriter) throws IOException {
            if (this.target >= 0) {
                baksmaliWriter.write(43)
            }
            baksmaliWriter.writeSignedIntAsDec(this.target)
        }
    }

    public static abstract class PackedSwitchTarget {
        constructor() {
        }

        public abstract Unit writeTargetTo(BaksmaliWriter baksmaliWriter) throws IOException
    }

    constructor(MethodDefinition methodDefinition, Int i, PackedSwitchPayload packedSwitchPayload) {
        Int key
        super(methodDefinition, i, packedSwitchPayload)
        Int packedSwitchBaseAddress = methodDefinition.getPackedSwitchBaseAddress(i)
        this.targets = ArrayList()
        Boolean z = true
        if (packedSwitchBaseAddress >= 0) {
            key = 0
            for (SwitchElement switchElement : packedSwitchPayload.getSwitchElements()) {
                if (z) {
                    key = switchElement.getKey()
                    z = false
                }
                this.targets.add(PackedSwitchLabelTarget(methodDefinition.getLabelCache().internLabel(LabelMethodItem(methodDefinition.classDef.options, switchElement.getOffset() + packedSwitchBaseAddress, "pswitch_"))))
            }
        } else {
            this.commentedOut = true
            key = 0
            for (SwitchElement switchElement2 : packedSwitchPayload.getSwitchElements()) {
                if (z) {
                    key = switchElement2.getKey()
                    z = false
                }
                this.targets.add(PackedSwitchOffsetTarget(switchElement2.getOffset()))
            }
        }
        this.firstKey = key
    }

    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem, org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        if (this.commentedOut) {
            baksmaliWriter = this.methodDef.classDef.getCommentingWriter(baksmaliWriter)
        }
        baksmaliWriter.write(".packed-switch ")
        baksmaliWriter.writeEncodedValue(ImmutableIntEncodedValue(this.firstKey))
        baksmaliWriter.indent(4)
        baksmaliWriter.write(10)
        Int i = this.firstKey
        Iterator<PackedSwitchTarget> it = this.targets.iterator()
        while (it.hasNext()) {
            it.next().writeTargetTo(baksmaliWriter)
            writeCommentIfResourceId(baksmaliWriter, i)
            baksmaliWriter.write(10)
            i++
        }
        baksmaliWriter.deindent(4)
        baksmaliWriter.write(".end packed-switch")
        return true
    }
}
