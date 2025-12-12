package org.jf.baksmali.Adaptors.Format

import java.io.IOException
import java.util.ArrayList
import java.util.List
import org.jf.baksmali.Adaptors.LabelMethodItem
import org.jf.baksmali.Adaptors.MethodDefinition
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload
import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue

class SparseSwitchMethodItem extends InstructionMethodItem<SparseSwitchPayload> {
    public Boolean commentedOut
    public final List<SparseSwitchTarget> targets

    public static class SparseSwitchLabelTarget extends SparseSwitchTarget {
        public final LabelMethodItem target

        constructor(Int i, LabelMethodItem labelMethodItem) {
            super(i)
            this.target = labelMethodItem
        }

        @Override // org.jf.baksmali.Adaptors.Format.SparseSwitchMethodItem.SparseSwitchTarget
        fun writeTargetTo(BaksmaliWriter baksmaliWriter) throws IOException {
            this.target.writeTo(baksmaliWriter)
        }
    }

    public static class SparseSwitchOffsetTarget extends SparseSwitchTarget {
        public final Int target

        constructor(Int i, Int i2) {
            super(i)
            this.target = i2
        }

        @Override // org.jf.baksmali.Adaptors.Format.SparseSwitchMethodItem.SparseSwitchTarget
        fun writeTargetTo(BaksmaliWriter baksmaliWriter) throws IOException {
            if (this.target >= 0) {
                baksmaliWriter.write(43)
            }
            baksmaliWriter.writeSignedIntAsDec(this.target)
        }
    }

    public static abstract class SparseSwitchTarget {
        public final Int key

        constructor(Int i) {
            this.key = i
        }

        fun getKey() {
            return this.key
        }

        public abstract Unit writeTargetTo(BaksmaliWriter baksmaliWriter) throws IOException
    }

    constructor(MethodDefinition methodDefinition, Int i, SparseSwitchPayload sparseSwitchPayload) {
        super(methodDefinition, i, sparseSwitchPayload)
        Int sparseSwitchBaseAddress = methodDefinition.getSparseSwitchBaseAddress(i)
        this.targets = ArrayList()
        if (sparseSwitchBaseAddress >= 0) {
            for (SwitchElement switchElement : sparseSwitchPayload.getSwitchElements()) {
                this.targets.add(SparseSwitchLabelTarget(switchElement.getKey(), methodDefinition.getLabelCache().internLabel(LabelMethodItem(methodDefinition.classDef.options, switchElement.getOffset() + sparseSwitchBaseAddress, "sswitch_"))))
            }
            return
        }
        this.commentedOut = true
        for (SwitchElement switchElement2 : sparseSwitchPayload.getSwitchElements()) {
            this.targets.add(SparseSwitchOffsetTarget(switchElement2.getKey(), switchElement2.getOffset()))
        }
    }

    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem, org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        if (this.commentedOut) {
            baksmaliWriter = this.methodDef.classDef.getCommentingWriter(baksmaliWriter)
        }
        baksmaliWriter.write(".sparse-switch\n")
        baksmaliWriter.indent(4)
        for (SparseSwitchTarget sparseSwitchTarget : this.targets) {
            baksmaliWriter.writeEncodedValue(ImmutableIntEncodedValue(sparseSwitchTarget.getKey()))
            baksmaliWriter.write(" -> ")
            sparseSwitchTarget.writeTargetTo(baksmaliWriter)
            writeCommentIfResourceId(baksmaliWriter, sparseSwitchTarget.getKey())
            baksmaliWriter.write(10)
        }
        baksmaliWriter.deindent(4)
        baksmaliWriter.write(".end sparse-switch")
        return true
    }
}
