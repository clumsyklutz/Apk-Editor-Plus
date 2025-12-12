package org.jf.dexlib2.analysis

import com.google.common.base.Objects
import com.google.common.collect.Lists
import java.util.ArrayList
import java.util.BitSet
import java.util.Collections
import java.util.Iterator
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.SortedSet
import java.util.TreeSet
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.ReferenceInstruction
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.Reference
import org.jf.util.ExceptionWithContext

class AnalyzedInstruction implements Comparable<AnalyzedInstruction> {
    public Instruction instruction
    public final Int instructionIndex
    public final MethodAnalyzer methodAnalyzer
    public final Instruction originalInstruction
    public final Array<RegisterType> postRegisterMap
    public final Array<RegisterType> preRegisterMap
    public final TreeSet<AnalyzedInstruction> predecessors = new TreeSet<>()
    public final LinkedList<AnalyzedInstruction> successors = new LinkedList<>()
    public Map<PredecessorOverrideKey, RegisterType> predecessorRegisterOverrides = null

    public static class PredecessorOverrideKey {
        public final AnalyzedInstruction analyzedInstruction
        public final Int registerNumber

        constructor(AnalyzedInstruction analyzedInstruction, Int i) {
            this.analyzedInstruction = analyzedInstruction
            this.registerNumber = i
        }

        fun equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (obj == null || PredecessorOverrideKey.class != obj.getClass()) {
                return false
            }
            PredecessorOverrideKey predecessorOverrideKey = (PredecessorOverrideKey) obj
            return Objects.equal(Integer.valueOf(this.registerNumber), Integer.valueOf(predecessorOverrideKey.registerNumber)) && Objects.equal(this.analyzedInstruction, predecessorOverrideKey.analyzedInstruction)
        }

        fun hashCode() {
            return Objects.hashCode(this.analyzedInstruction, Integer.valueOf(this.registerNumber))
        }
    }

    constructor(MethodAnalyzer methodAnalyzer, Instruction instruction, Int i, Int i2) {
        this.methodAnalyzer = methodAnalyzer
        this.instruction = instruction
        this.originalInstruction = instruction
        this.instructionIndex = i
        this.postRegisterMap = new RegisterType[i2]
        this.preRegisterMap = new RegisterType[i2]
        RegisterType registerType = RegisterType.getRegisterType((Byte) 0, (TypeProto) null)
        for (Int i3 = 0; i3 < i2; i3++) {
            this.preRegisterMap[i3] = registerType
            this.postRegisterMap[i3] = registerType
        }
    }

    fun addPredecessor(AnalyzedInstruction analyzedInstruction) {
        return this.predecessors.add(analyzedInstruction)
    }

    fun addSuccessor(AnalyzedInstruction analyzedInstruction) {
        this.successors.add(analyzedInstruction)
    }

    @Override // java.lang.Comparable
    fun compareTo(AnalyzedInstruction analyzedInstruction) {
        Int i = this.instructionIndex
        Int i2 = analyzedInstruction.instructionIndex
        if (i < i2) {
            return -1
        }
        return i == i2 ? 0 : 1
    }

    fun getDestinationRegister() {
        if (this.instruction.getOpcode().setsRegister()) {
            return ((OneRegisterInstruction) this.instruction).getRegisterA()
        }
        throw ExceptionWithContext("Cannot call getDestinationRegister() for an instruction that doesn't store a value", new Object[0])
    }

    fun getInstruction() {
        return this.instruction
    }

    fun getInstructionIndex() {
        return this.instructionIndex
    }

    fun getMergedPreRegisterTypeFromPredecessors(Int i) {
        Iterator<AnalyzedInstruction> it = this.predecessors.iterator()
        RegisterType registerTypeMerge = null
        while (it.hasNext()) {
            RegisterType predecessorRegisterType = getPredecessorRegisterType(it.next(), i)
            if (predecessorRegisterType != null) {
                registerTypeMerge = registerTypeMerge == null ? predecessorRegisterType : predecessorRegisterType.merge(registerTypeMerge)
            }
        }
        if (registerTypeMerge != null) {
            return registerTypeMerge
        }
        throw IllegalStateException()
    }

    fun getOriginalInstruction() {
        return this.originalInstruction
    }

    fun getPostInstructionRegisterType(Int i) {
        return this.postRegisterMap[i]
    }

    fun getPreInstructionRegisterType(Int i) {
        return this.preRegisterMap[i]
    }

    fun getPredecessorCount() {
        return this.predecessors.size()
    }

    fun getPredecessorRegisterType(AnalyzedInstruction analyzedInstruction, Int i) {
        RegisterType registerType
        Map<PredecessorOverrideKey, RegisterType> map = this.predecessorRegisterOverrides
        return (map == null || (registerType = map.get(PredecessorOverrideKey(analyzedInstruction, i))) == null) ? analyzedInstruction.postRegisterMap[i] : registerType
    }

    public SortedSet<AnalyzedInstruction> getPredecessors() {
        return Collections.unmodifiableSortedSet(this.predecessors)
    }

    fun getRegisterCount() {
        return this.postRegisterMap.length
    }

    public List<Integer> getSetRegisters() {
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        if (this.instruction.getOpcode().setsRegister()) {
            arrayListNewArrayList.add(Integer.valueOf(getDestinationRegister()))
        }
        if (this.instruction.getOpcode().setsWideRegister()) {
            arrayListNewArrayList.add(Integer.valueOf(getDestinationRegister() + 1))
        }
        if (isInvokeInit()) {
            Instruction instruction = this.instruction
            Int registerC = instruction is FiveRegisterInstruction ? ((FiveRegisterInstruction) instruction).getRegisterC() : ((RegisterRangeInstruction) instruction).getStartRegister()
            Byte b2 = getPreInstructionRegisterType(registerC).category
            Int i = 0
            if (b2 == 16 || b2 == 17) {
                arrayListNewArrayList.add(Integer.valueOf(registerC))
                RegisterType registerType = this.preRegisterMap[registerC]
                while (true) {
                    Array<RegisterType> registerTypeArr = this.preRegisterMap
                    if (i >= registerTypeArr.length) {
                        break
                    }
                    if (i != registerC) {
                        RegisterType registerType2 = registerTypeArr[i]
                        if (registerType2.equals(registerType)) {
                            arrayListNewArrayList.add(Integer.valueOf(i))
                        } else {
                            Byte b3 = registerType2.category
                            if ((b3 == 16 || b3 == 17) && this.postRegisterMap[i].category == 0) {
                                arrayListNewArrayList.add(Integer.valueOf(i))
                            }
                        }
                    }
                    i++
                }
            } else if (b2 == 0) {
                while (true) {
                    Array<RegisterType> registerTypeArr2 = this.preRegisterMap
                    if (i >= registerTypeArr2.length) {
                        break
                    }
                    Byte b4 = registerTypeArr2[i].category
                    if (b4 == 16 || b4 == 17) {
                        arrayListNewArrayList.add(Integer.valueOf(i))
                    }
                    i++
                }
            }
        }
        if (this.instructionIndex <= 0) {
            return arrayListNewArrayList
        }
        this.methodAnalyzer.getClassPath()
        throw null
    }

    fun isBeginningInstruction() {
        return this.predecessors.size() != 0 && this.predecessors.first().instructionIndex == -1
    }

    fun isInvokeInit() {
        if (!this.instruction.getOpcode().canInitializeReference()) {
            return false
        }
        Reference reference = ((ReferenceInstruction) this.instruction).getReference()
        if (reference is MethodReference) {
            return ((MethodReference) reference).getName().equals("<init>")
        }
        return false
    }

    fun mergeRegister(Int i, RegisterType registerType, BitSet bitSet, Boolean z) {
        RegisterType registerType2 = this.preRegisterMap[i]
        RegisterType mergedPreRegisterTypeFromPredecessors = z ? getMergedPreRegisterTypeFromPredecessors(i) : registerType2.merge(registerType)
        if (mergedPreRegisterTypeFromPredecessors.equals(registerType2)) {
            return false
        }
        this.preRegisterMap[i] = mergedPreRegisterTypeFromPredecessors
        bitSet.clear(this.instructionIndex)
        if (setsRegister(i)) {
            return false
        }
        this.postRegisterMap[i] = mergedPreRegisterTypeFromPredecessors
        return true
    }

    fun restoreOdexedInstruction() {
        this.instruction = this.originalInstruction
    }

    fun setDeodexedInstruction(Instruction instruction) {
        this.instruction = instruction
    }

    fun setPostRegisterType(Int i, RegisterType registerType) {
        if (this.postRegisterMap[i].equals(registerType)) {
            return false
        }
        this.postRegisterMap[i] = registerType
        return true
    }

    fun setsRegister(Int i) {
        Byte b2
        if (!isInvokeInit()) {
            if (this.instructionIndex > 0) {
                this.methodAnalyzer.getClassPath()
                throw null
            }
            if (!this.instruction.getOpcode().setsRegister()) {
                return false
            }
            Int destinationRegister = getDestinationRegister()
            if (i == destinationRegister) {
                return true
            }
            return this.instruction.getOpcode().setsWideRegister() && i == destinationRegister + 1
        }
        Instruction instruction = this.instruction
        Int registerC = instruction is FiveRegisterInstruction ? ((FiveRegisterInstruction) instruction).getRegisterC() : ((RegisterRangeInstruction) instruction).getStartRegister()
        RegisterType preInstructionRegisterType = getPreInstructionRegisterType(registerC)
        if (preInstructionRegisterType.category == 0 && ((b2 = getPreInstructionRegisterType(i).category) == 16 || b2 == 17)) {
            return true
        }
        Byte b3 = preInstructionRegisterType.category
        if (b3 != 16 && b3 != 17) {
            return false
        }
        if (i == registerC) {
            return true
        }
        return preInstructionRegisterType.equals(getPreInstructionRegisterType(i))
    }
}
