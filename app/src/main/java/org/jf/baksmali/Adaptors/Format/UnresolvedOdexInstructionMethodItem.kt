package org.jf.baksmali.Adaptors.Format

import java.io.IOException
import org.jf.baksmali.Adaptors.MethodDefinition
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.analysis.UnresolvedOdexInstruction

class UnresolvedOdexInstructionMethodItem extends InstructionMethodItem<UnresolvedOdexInstruction> {
    constructor(MethodDefinition methodDefinition, Int i, UnresolvedOdexInstruction unresolvedOdexInstruction) {
        super(methodDefinition, i, unresolvedOdexInstruction)
    }

    public final Unit writeThrowTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("#Replaced unresolvable odex instruction with a throw\n")
        baksmaliWriter.write("throw ")
        writeRegister(baksmaliWriter, ((UnresolvedOdexInstruction) this.instruction).objectRegisterNum)
    }

    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem, org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        writeThrowTo(baksmaliWriter)
        return true
    }
}
