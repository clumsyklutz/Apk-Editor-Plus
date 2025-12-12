package org.jf.dexlib2.builder

import java.util.HashMap
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference

class MethodImplementationBuilder {
    public MethodLocation currentLocation
    public final MutableMethodImplementation impl
    public final HashMap<String, Label> labels = new HashMap<>()

    constructor(Int i) {
        MutableMethodImplementation mutableMethodImplementation = MutableMethodImplementation(i)
        this.impl = mutableMethodImplementation
        this.currentLocation = mutableMethodImplementation.instructionList.get(0)
    }

    fun addCatch(Label label, Label label2, Label label3) {
        this.impl.addCatch(label, label2, label3)
    }

    fun addCatch(TypeReference typeReference, Label label, Label label2, Label label3) {
        this.impl.addCatch(typeReference, label, label2, label3)
    }

    fun addEndLocal(Int i) {
        this.currentLocation.addEndLocal(i)
    }

    fun addEpilogue() {
        this.currentLocation.addEpilogue()
    }

    fun addInstruction(BuilderInstruction builderInstruction) {
        this.impl.addInstruction(builderInstruction)
        this.currentLocation = this.impl.instructionList.get(r2.size() - 1)
    }

    fun addLabel(String str) {
        Label label = this.labels.get(str)
        if (label == null) {
            Label labelAddNewLabel = this.currentLocation.addNewLabel()
            this.labels.put(str, labelAddNewLabel)
            return labelAddNewLabel
        }
        if (label.isPlaced()) {
            throw IllegalArgumentException("There is already a label with that name.")
        }
        this.currentLocation.getLabels().add(label)
        return label
    }

    fun addLineNumber(Int i) {
        this.currentLocation.addLineNumber(i)
    }

    fun addPrologue() {
        this.currentLocation.addPrologue()
    }

    fun addRestartLocal(Int i) {
        this.currentLocation.addRestartLocal(i)
    }

    fun addSetSourceFile(StringReference stringReference) {
        this.currentLocation.addSetSourceFile(stringReference)
    }

    fun addStartLocal(Int i, StringReference stringReference, TypeReference typeReference, StringReference stringReference2) {
        this.currentLocation.addStartLocal(i, stringReference, typeReference, stringReference2)
    }

    fun getLabel(String str) {
        Label label = this.labels.get(str)
        if (label != null) {
            return label
        }
        Label label2 = Label()
        this.labels.put(str, label2)
        return label2
    }

    fun getMethodImplementation() {
        return this.impl
    }
}
