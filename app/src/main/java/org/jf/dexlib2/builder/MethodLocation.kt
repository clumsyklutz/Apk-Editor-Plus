package org.jf.dexlib2.builder

import java.util.Set
import org.jf.dexlib2.builder.debug.BuilderEndLocal
import org.jf.dexlib2.builder.debug.BuilderEpilogueBegin
import org.jf.dexlib2.builder.debug.BuilderLineNumber
import org.jf.dexlib2.builder.debug.BuilderPrologueEnd
import org.jf.dexlib2.builder.debug.BuilderRestartLocal
import org.jf.dexlib2.builder.debug.BuilderSetSourceFile
import org.jf.dexlib2.builder.debug.BuilderStartLocal
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference

class MethodLocation {
    public Int codeAddress
    public Int index
    public BuilderInstruction instruction
    public final LocatedItems<BuilderDebugItem> debugItems = LocatedDebugItems()
    public final LocatedItems<Label> labels = LocatedLabels()

    constructor(BuilderInstruction builderInstruction, Int i, Int i2) {
        this.instruction = builderInstruction
        this.codeAddress = i
        this.index = i2
    }

    fun addEndLocal(Int i) {
        getDebugItems().add(BuilderEndLocal(i))
    }

    fun addEpilogue() {
        getDebugItems().add(BuilderEpilogueBegin())
    }

    fun addLineNumber(Int i) {
        getDebugItems().add(BuilderLineNumber(i))
    }

    fun addNewLabel() {
        Label label = Label()
        getLabels().add(label)
        return label
    }

    fun addPrologue() {
        getDebugItems().add(BuilderPrologueEnd())
    }

    fun addRestartLocal(Int i) {
        getDebugItems().add(BuilderRestartLocal(i))
    }

    fun addSetSourceFile(StringReference stringReference) {
        getDebugItems().add(BuilderSetSourceFile(stringReference))
    }

    fun addStartLocal(Int i, StringReference stringReference, TypeReference typeReference, StringReference stringReference2) {
        getDebugItems().add(BuilderStartLocal(i, stringReference, typeReference, stringReference2))
    }

    fun getCodeAddress() {
        return this.codeAddress
    }

    public Set<BuilderDebugItem> getDebugItems() {
        return this.debugItems.getModifiableItems(this)
    }

    fun getInstruction() {
        return this.instruction
    }

    public Set<Label> getLabels() {
        return this.labels.getModifiableItems(this)
    }

    fun mergeInto(MethodLocation methodLocation) {
        this.labels.mergeItemsIntoNext(methodLocation, methodLocation.labels)
        this.debugItems.mergeItemsIntoNext(methodLocation, methodLocation.debugItems)
    }
}
