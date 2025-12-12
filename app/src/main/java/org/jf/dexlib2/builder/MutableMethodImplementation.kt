package org.jf.dexlib2.builder

import com.google.common.base.Function
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import java.util.AbstractList
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.HashSet
import java.util.Iterator
import java.util.List
import org.jf.dexlib2.Format
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.debug.BuilderEndLocal
import org.jf.dexlib2.builder.debug.BuilderEpilogueBegin
import org.jf.dexlib2.builder.debug.BuilderLineNumber
import org.jf.dexlib2.builder.debug.BuilderPrologueEnd
import org.jf.dexlib2.builder.debug.BuilderRestartLocal
import org.jf.dexlib2.builder.debug.BuilderSetSourceFile
import org.jf.dexlib2.builder.debug.BuilderStartLocal
import org.jf.dexlib2.builder.instruction.BuilderArrayPayload
import org.jf.dexlib2.builder.instruction.BuilderInstruction10t
import org.jf.dexlib2.builder.instruction.BuilderInstruction10x
import org.jf.dexlib2.builder.instruction.BuilderInstruction11n
import org.jf.dexlib2.builder.instruction.BuilderInstruction11x
import org.jf.dexlib2.builder.instruction.BuilderInstruction12x
import org.jf.dexlib2.builder.instruction.BuilderInstruction20bc
import org.jf.dexlib2.builder.instruction.BuilderInstruction20t
import org.jf.dexlib2.builder.instruction.BuilderInstruction21c
import org.jf.dexlib2.builder.instruction.BuilderInstruction21ih
import org.jf.dexlib2.builder.instruction.BuilderInstruction21lh
import org.jf.dexlib2.builder.instruction.BuilderInstruction21s
import org.jf.dexlib2.builder.instruction.BuilderInstruction21t
import org.jf.dexlib2.builder.instruction.BuilderInstruction22b
import org.jf.dexlib2.builder.instruction.BuilderInstruction22c
import org.jf.dexlib2.builder.instruction.BuilderInstruction22cs
import org.jf.dexlib2.builder.instruction.BuilderInstruction22s
import org.jf.dexlib2.builder.instruction.BuilderInstruction22t
import org.jf.dexlib2.builder.instruction.BuilderInstruction22x
import org.jf.dexlib2.builder.instruction.BuilderInstruction23x
import org.jf.dexlib2.builder.instruction.BuilderInstruction30t
import org.jf.dexlib2.builder.instruction.BuilderInstruction31c
import org.jf.dexlib2.builder.instruction.BuilderInstruction31i
import org.jf.dexlib2.builder.instruction.BuilderInstruction31t
import org.jf.dexlib2.builder.instruction.BuilderInstruction32x
import org.jf.dexlib2.builder.instruction.BuilderInstruction35c
import org.jf.dexlib2.builder.instruction.BuilderInstruction35mi
import org.jf.dexlib2.builder.instruction.BuilderInstruction35ms
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rmi
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rms
import org.jf.dexlib2.builder.instruction.BuilderInstruction51l
import org.jf.dexlib2.builder.instruction.BuilderPackedSwitchPayload
import org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload
import org.jf.dexlib2.iface.ExceptionHandler
import org.jf.dexlib2.iface.MethodImplementation
import org.jf.dexlib2.iface.TryBlock
import org.jf.dexlib2.iface.debug.DebugItem
import org.jf.dexlib2.iface.debug.EndLocal
import org.jf.dexlib2.iface.debug.LineNumber
import org.jf.dexlib2.iface.debug.RestartLocal
import org.jf.dexlib2.iface.debug.SetSourceFile
import org.jf.dexlib2.iface.debug.StartLocal
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload
import org.jf.dexlib2.iface.instruction.formats.Instruction10t
import org.jf.dexlib2.iface.instruction.formats.Instruction10x
import org.jf.dexlib2.iface.instruction.formats.Instruction11n
import org.jf.dexlib2.iface.instruction.formats.Instruction11x
import org.jf.dexlib2.iface.instruction.formats.Instruction12x
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc
import org.jf.dexlib2.iface.instruction.formats.Instruction20t
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.instruction.formats.Instruction21ih
import org.jf.dexlib2.iface.instruction.formats.Instruction21lh
import org.jf.dexlib2.iface.instruction.formats.Instruction21s
import org.jf.dexlib2.iface.instruction.formats.Instruction21t
import org.jf.dexlib2.iface.instruction.formats.Instruction22b
import org.jf.dexlib2.iface.instruction.formats.Instruction22c
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs
import org.jf.dexlib2.iface.instruction.formats.Instruction22s
import org.jf.dexlib2.iface.instruction.formats.Instruction22t
import org.jf.dexlib2.iface.instruction.formats.Instruction22x
import org.jf.dexlib2.iface.instruction.formats.Instruction23x
import org.jf.dexlib2.iface.instruction.formats.Instruction30t
import org.jf.dexlib2.iface.instruction.formats.Instruction31c
import org.jf.dexlib2.iface.instruction.formats.Instruction31i
import org.jf.dexlib2.iface.instruction.formats.Instruction31t
import org.jf.dexlib2.iface.instruction.formats.Instruction32x
import org.jf.dexlib2.iface.instruction.formats.Instruction35c
import org.jf.dexlib2.iface.instruction.formats.Instruction35mi
import org.jf.dexlib2.iface.instruction.formats.Instruction35ms
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc
import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi
import org.jf.dexlib2.iface.instruction.formats.Instruction3rms
import org.jf.dexlib2.iface.instruction.formats.Instruction51l
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.util.ExceptionWithContext

class MutableMethodImplementation implements MethodImplementation {
    public Boolean fixInstructions
    public final ArrayList<MethodLocation> instructionList
    public final Int registerCount
    public final ArrayList<BuilderTryBlock> tryBlocks

    /* renamed from: org.jf.dexlib2.builder.MutableMethodImplementation$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Format
        public static final /* synthetic */ Array<Int> $SwitchMap$org$jf$dexlib2$Opcode

        static {
            Array<Int> iArr = new Int[Format.values().length]
            $SwitchMap$org$jf$dexlib2$Format = iArr
            try {
                iArr[Format.Format10t.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format10x.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11n.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11x.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format12x.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20bc.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20t.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21c.ordinal()] = 8
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21ih.ordinal()] = 9
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21lh.ordinal()] = 10
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21s.ordinal()] = 11
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21t.ordinal()] = 12
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22b.ordinal()] = 13
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22c.ordinal()] = 14
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22cs.ordinal()] = 15
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22s.ordinal()] = 16
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22t.ordinal()] = 17
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22x.ordinal()] = 18
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format23x.ordinal()] = 19
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format30t.ordinal()] = 20
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31c.ordinal()] = 21
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31i.ordinal()] = 22
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31t.ordinal()] = 23
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format32x.ordinal()] = 24
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35c.ordinal()] = 25
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 26
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 27
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rc.ordinal()] = 28
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rmi.ordinal()] = 29
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rms.ordinal()] = 30
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format51l.ordinal()] = 31
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.PackedSwitchPayload.ordinal()] = 32
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.SparseSwitchPayload.ordinal()] = 33
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.ArrayPayload.ordinal()] = 34
            } catch (NoSuchFieldError unused34) {
            }
            Array<Int> iArr2 = new Int[Opcode.values().length]
            $SwitchMap$org$jf$dexlib2$Opcode = iArr2
            try {
                iArr2[Opcode.SPARSE_SWITCH.ordinal()] = 1
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.PACKED_SWITCH.ordinal()] = 2
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.GOTO.ordinal()] = 3
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.GOTO_16.ordinal()] = 4
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPARSE_SWITCH_PAYLOAD.ordinal()] = 5
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.PACKED_SWITCH_PAYLOAD.ordinal()] = 6
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.ARRAY_PAYLOAD.ordinal()] = 7
            } catch (NoSuchFieldError unused41) {
            }
        }
    }

    public static class SwitchPayloadReferenceLabel extends Label {
        public MethodLocation switchLocation

        constructor() {
        }
    }

    public interface Task {
        Unit perform()
    }

    constructor(Int i) {
        this.instructionList = Lists.newArrayList(MethodLocation(null, 0, 0))
        this.tryBlocks = Lists.newArrayList()
        this.fixInstructions = true
        this.registerCount = i
    }

    constructor(MethodImplementation methodImplementation) {
        Int i = 0
        this.instructionList = Lists.newArrayList(MethodLocation(null, 0, 0))
        this.tryBlocks = Lists.newArrayList()
        this.fixInstructions = true
        this.registerCount = methodImplementation.getRegisterCount()
        Iterator<? extends Instruction> it = methodImplementation.getInstructions().iterator()
        Int codeUnits = 0
        Int i2 = 0
        while (it.hasNext()) {
            codeUnits += it.next().getCodeUnits()
            i2++
            this.instructionList.add(MethodLocation(null, codeUnits, i2))
        }
        final Array<Int> iArr = new Int[codeUnits + 1]
        Arrays.fill(iArr, -1)
        for (Int i3 = 0; i3 < this.instructionList.size(); i3++) {
            iArr[this.instructionList.get(i3).codeAddress] = i3
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        for (final Instruction instruction : methodImplementation.getInstructions()) {
            val methodLocation = this.instructionList.get(i)
            Opcode opcode = instruction.getOpcode()
            if (opcode == Opcode.PACKED_SWITCH_PAYLOAD || opcode == Opcode.SPARSE_SWITCH_PAYLOAD) {
                arrayListNewArrayList.add(Task() { // from class: org.jf.dexlib2.builder.MutableMethodImplementation.1
                    @Override // org.jf.dexlib2.builder.MutableMethodImplementation.Task
                    fun perform() {
                        MutableMethodImplementation.this.convertAndSetInstruction(methodLocation, iArr, instruction)
                    }
                })
            } else {
                convertAndSetInstruction(methodLocation, iArr, instruction)
            }
            i++
        }
        Iterator it2 = arrayListNewArrayList.iterator()
        while (it2.hasNext()) {
            ((Task) it2.next()).perform()
        }
        for (DebugItem debugItem : methodImplementation.getDebugItems()) {
            MethodLocation methodLocation2 = this.instructionList.get(mapCodeAddressToIndex(iArr, debugItem.getCodeAddress()))
            BuilderDebugItem builderDebugItemConvertDebugItem = convertDebugItem(debugItem)
            methodLocation2.getDebugItems().add(builderDebugItemConvertDebugItem)
            builderDebugItemConvertDebugItem.location = methodLocation2
        }
        for (TryBlock<? extends ExceptionHandler> tryBlock : methodImplementation.getTryBlocks()) {
            Label labelNewLabel = newLabel(iArr, tryBlock.getStartCodeAddress())
            Label labelNewLabel2 = newLabel(iArr, tryBlock.getStartCodeAddress() + tryBlock.getCodeUnitCount())
            Iterator it3 = tryBlock.getExceptionHandlers().iterator()
            while (it3.hasNext()) {
                ExceptionHandler exceptionHandler = (ExceptionHandler) it3.next()
                this.tryBlocks.add(BuilderTryBlock(labelNewLabel, labelNewLabel2, exceptionHandler.getExceptionTypeReference(), newLabel(iArr, exceptionHandler.getHandlerCodeAddress())))
            }
        }
    }

    fun addCatch(Label label, Label label2, Label label3) {
        this.tryBlocks.add(BuilderTryBlock(label, label2, label3))
    }

    fun addCatch(TypeReference typeReference, Label label, Label label2, Label label3) {
        this.tryBlocks.add(BuilderTryBlock(label, label2, typeReference, label3))
    }

    fun addInstruction(Int i, BuilderInstruction builderInstruction) {
        if (i >= this.instructionList.size()) {
            throw IndexOutOfBoundsException()
        }
        if (i == this.instructionList.size() - 1) {
            addInstruction(builderInstruction)
            return
        }
        Int codeAddress = this.instructionList.get(i).getCodeAddress()
        MethodLocation methodLocation = MethodLocation(builderInstruction, codeAddress, i)
        this.instructionList.add(i, methodLocation)
        builderInstruction.location = methodLocation
        Int codeUnits = codeAddress + builderInstruction.getCodeUnits()
        for (Int i2 = i + 1; i2 < this.instructionList.size(); i2++) {
            MethodLocation methodLocation2 = this.instructionList.get(i2)
            methodLocation2.index++
            methodLocation2.codeAddress = codeUnits
            BuilderInstruction builderInstruction2 = methodLocation2.instruction
            if (builderInstruction2 != null) {
                codeUnits += builderInstruction2.getCodeUnits()
            }
        }
        this.fixInstructions = true
    }

    fun addInstruction(BuilderInstruction builderInstruction) {
        ArrayList<MethodLocation> arrayList = this.instructionList
        MethodLocation methodLocation = arrayList.get(arrayList.size() - 1)
        methodLocation.instruction = builderInstruction
        builderInstruction.location = methodLocation
        Int codeUnits = methodLocation.codeAddress + builderInstruction.getCodeUnits()
        ArrayList<MethodLocation> arrayList2 = this.instructionList
        arrayList2.add(MethodLocation(null, codeUnits, arrayList2.size()))
        this.fixInstructions = true
    }

    public final Unit convertAndSetInstruction(MethodLocation methodLocation, Array<Int> iArr, Instruction instruction) {
        switch (AnonymousClass4.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
            case 1:
                setInstruction(methodLocation, newBuilderInstruction10t(methodLocation.codeAddress, iArr, (Instruction10t) instruction))
                return
            case 2:
                setInstruction(methodLocation, newBuilderInstruction10x((Instruction10x) instruction))
                return
            case 3:
                setInstruction(methodLocation, newBuilderInstruction11n((Instruction11n) instruction))
                return
            case 4:
                setInstruction(methodLocation, newBuilderInstruction11x((Instruction11x) instruction))
                return
            case 5:
                setInstruction(methodLocation, newBuilderInstruction12x((Instruction12x) instruction))
                return
            case 6:
                setInstruction(methodLocation, newBuilderInstruction20bc((Instruction20bc) instruction))
                return
            case 7:
                setInstruction(methodLocation, newBuilderInstruction20t(methodLocation.codeAddress, iArr, (Instruction20t) instruction))
                return
            case 8:
                setInstruction(methodLocation, newBuilderInstruction21c((Instruction21c) instruction))
                return
            case 9:
                setInstruction(methodLocation, newBuilderInstruction21ih((Instruction21ih) instruction))
                return
            case 10:
                setInstruction(methodLocation, newBuilderInstruction21lh((Instruction21lh) instruction))
                return
            case 11:
                setInstruction(methodLocation, newBuilderInstruction21s((Instruction21s) instruction))
                return
            case 12:
                setInstruction(methodLocation, newBuilderInstruction21t(methodLocation.codeAddress, iArr, (Instruction21t) instruction))
                return
            case 13:
                setInstruction(methodLocation, newBuilderInstruction22b((Instruction22b) instruction))
                return
            case 14:
                setInstruction(methodLocation, newBuilderInstruction22c((Instruction22c) instruction))
                return
            case 15:
                setInstruction(methodLocation, newBuilderInstruction22cs((Instruction22cs) instruction))
                return
            case 16:
                setInstruction(methodLocation, newBuilderInstruction22s((Instruction22s) instruction))
                return
            case 17:
                setInstruction(methodLocation, newBuilderInstruction22t(methodLocation.codeAddress, iArr, (Instruction22t) instruction))
                return
            case 18:
                setInstruction(methodLocation, newBuilderInstruction22x((Instruction22x) instruction))
                return
            case 19:
                setInstruction(methodLocation, newBuilderInstruction23x((Instruction23x) instruction))
                return
            case 20:
                setInstruction(methodLocation, newBuilderInstruction30t(methodLocation.codeAddress, iArr, (Instruction30t) instruction))
                return
            case 21:
                setInstruction(methodLocation, newBuilderInstruction31c((Instruction31c) instruction))
                return
            case 22:
                setInstruction(methodLocation, newBuilderInstruction31i((Instruction31i) instruction))
                return
            case 23:
                setInstruction(methodLocation, newBuilderInstruction31t(methodLocation, iArr, (Instruction31t) instruction))
                return
            case 24:
                setInstruction(methodLocation, newBuilderInstruction32x((Instruction32x) instruction))
                return
            case 25:
                setInstruction(methodLocation, newBuilderInstruction35c((Instruction35c) instruction))
                return
            case 26:
                setInstruction(methodLocation, newBuilderInstruction35mi((Instruction35mi) instruction))
                return
            case 27:
                setInstruction(methodLocation, newBuilderInstruction35ms((Instruction35ms) instruction))
                return
            case 28:
                setInstruction(methodLocation, newBuilderInstruction3rc((Instruction3rc) instruction))
                return
            case 29:
                setInstruction(methodLocation, newBuilderInstruction3rmi((Instruction3rmi) instruction))
                return
            case 30:
                setInstruction(methodLocation, newBuilderInstruction3rms((Instruction3rms) instruction))
                return
            case 31:
                setInstruction(methodLocation, newBuilderInstruction51l((Instruction51l) instruction))
                return
            case 32:
                setInstruction(methodLocation, newBuilderPackedSwitchPayload(methodLocation, iArr, (PackedSwitchPayload) instruction))
                return
            case 33:
                setInstruction(methodLocation, newBuilderSparseSwitchPayload(methodLocation, iArr, (SparseSwitchPayload) instruction))
                return
            case 34:
                setInstruction(methodLocation, newBuilderArrayPayload((ArrayPayload) instruction))
                return
            default:
                throw ExceptionWithContext("Instruction format %s not supported", instruction.getOpcode().format)
        }
    }

    public final BuilderDebugItem convertDebugItem(DebugItem debugItem) {
        switch (debugItem.getDebugItemType()) {
            case 3:
                StartLocal startLocal = (StartLocal) debugItem
                return BuilderStartLocal(startLocal.getRegister(), startLocal.getNameReference(), startLocal.getTypeReference(), startLocal.getSignatureReference())
            case 4:
            default:
                throw ExceptionWithContext("Invalid debug item type: " + debugItem.getDebugItemType(), new Object[0])
            case 5:
                return BuilderEndLocal(((EndLocal) debugItem).getRegister())
            case 6:
                return BuilderRestartLocal(((RestartLocal) debugItem).getRegister())
            case 7:
                return BuilderPrologueEnd()
            case 8:
                return BuilderEpilogueBegin()
            case 9:
                return BuilderSetSourceFile(((SetSourceFile) debugItem).getSourceFileReference())
            case 10:
                return BuilderLineNumber(((LineNumber) debugItem).getLineNumber())
        }
    }

    public final MethodLocation findSwitchForPayload(MethodLocation methodLocation) {
        BuilderInstruction builderInstruction
        MethodLocation methodLocation2 = null
        do {
            for (Label label : methodLocation.getLabels()) {
                if (label is SwitchPayloadReferenceLabel) {
                    if (methodLocation2 != null) {
                        throw IllegalStateException("Multiple switch instructions refer to the same payload. This is not currently supported. Please file a bug :)")
                    }
                    methodLocation2 = ((SwitchPayloadReferenceLabel) label).switchLocation
                }
            }
            Int i = methodLocation.index
            if (i == 0 || (builderInstruction = (methodLocation = this.instructionList.get(i - 1)).instruction) == null) {
                break
            }
        } while (builderInstruction.getOpcode() == Opcode.NOP);
        return methodLocation2
    }

    public final Unit fixInstructions() {
        Boolean z
        Int i
        HashSet hashSetNewHashSet = Sets.newHashSet()
        Iterator<MethodLocation> it = this.instructionList.iterator()
        while (it.hasNext()) {
            MethodLocation next = it.next()
            BuilderInstruction builderInstruction = next.instruction
            if (builderInstruction != null && ((i = AnonymousClass4.$SwitchMap$org$jf$dexlib2$Opcode[builderInstruction.getOpcode().ordinal()]) == 1 || i == 2)) {
                MethodLocation location = ((BuilderOffsetInstruction) builderInstruction).getTarget().getLocation()
                BuilderInstruction firstNonNop = location.instruction
                if (firstNonNop == null) {
                    throw IllegalStateException(String.format("Switch instruction at address/index 0x%x/%d points to the end of the method.", Integer.valueOf(next.codeAddress), Integer.valueOf(next.index)))
                }
                if (firstNonNop.getOpcode() == Opcode.NOP) {
                    firstNonNop = getFirstNonNop(location.index + 1)
                }
                if (firstNonNop == null || !(firstNonNop is BuilderSwitchPayload)) {
                    throw IllegalStateException(String.format("Switch instruction at address/index 0x%x/%d does not refer to a payload instruction.", Integer.valueOf(next.codeAddress), Integer.valueOf(next.index)))
                }
                if ((builderInstruction.opcode == Opcode.PACKED_SWITCH && firstNonNop.getOpcode() != Opcode.PACKED_SWITCH_PAYLOAD) || (builderInstruction.opcode == Opcode.SPARSE_SWITCH && firstNonNop.getOpcode() != Opcode.SPARSE_SWITCH_PAYLOAD)) {
                    throw IllegalStateException(String.format("Switch instruction at address/index 0x%x/%d refers to the wrong type of payload instruction.", Integer.valueOf(next.codeAddress), Integer.valueOf(next.index)))
                }
                if (!hashSetNewHashSet.add(location)) {
                    throw IllegalStateException("Multiple switch instructions refer to the same payload. This is not currently supported. Please file a bug :)")
                }
                ((BuilderSwitchPayload) firstNonNop).referrer = next
            }
        }
        do {
            Int i2 = 0
            z = false
            while (i2 < this.instructionList.size()) {
                MethodLocation methodLocation = this.instructionList.get(i2)
                BuilderInstruction builderInstruction2 = methodLocation.instruction
                if (builderInstruction2 != null) {
                    Int i3 = AnonymousClass4.$SwitchMap$org$jf$dexlib2$Opcode[builderInstruction2.getOpcode().ordinal()]
                    if (i3 == 3) {
                        BuilderOffsetInstruction builderOffsetInstruction = (BuilderOffsetInstruction) builderInstruction2
                        Int iInternalGetCodeOffset = builderOffsetInstruction.internalGetCodeOffset()
                        if (iInternalGetCodeOffset < -128 || iInternalGetCodeOffset > 127) {
                            replaceInstruction(methodLocation.index, (iInternalGetCodeOffset < -32768 || iInternalGetCodeOffset > 32767) ? BuilderInstruction30t(Opcode.GOTO_32, builderOffsetInstruction.getTarget()) : BuilderInstruction20t(Opcode.GOTO_16, builderOffsetInstruction.getTarget()))
                            z = true
                        }
                    } else if (i3 != 4) {
                        if (i3 == 5 || i3 == 6) {
                            if (((BuilderSwitchPayload) builderInstruction2).referrer == null) {
                                removeInstruction(i2)
                            }
                            i2--
                            z = true
                        } else if (i3 != 7) {
                        }
                        if ((methodLocation.codeAddress & 1) != 0) {
                            Int i4 = methodLocation.index - 1
                            Opcode opcode = this.instructionList.get(i4).instruction.getOpcode()
                            Opcode opcode2 = Opcode.NOP
                            if (opcode == opcode2) {
                                removeInstruction(i4)
                                i2--
                                z = true
                            } else {
                                addInstruction(methodLocation.index, BuilderInstruction10x(opcode2))
                                i2++
                                z = true
                            }
                        }
                    } else {
                        BuilderOffsetInstruction builderOffsetInstruction2 = (BuilderOffsetInstruction) builderInstruction2
                        Int iInternalGetCodeOffset2 = builderOffsetInstruction2.internalGetCodeOffset()
                        if (iInternalGetCodeOffset2 < -32768 || iInternalGetCodeOffset2 > 32767) {
                            replaceInstruction(methodLocation.index, BuilderInstruction30t(Opcode.GOTO_32, builderOffsetInstruction2.getTarget()))
                            z = true
                        }
                    }
                }
                i2++
            }
        } while (z);
        this.fixInstructions = false
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public Iterable<? extends DebugItem> getDebugItems() {
        if (this.fixInstructions) {
            fixInstructions()
        }
        return Iterables.concat(Iterables.transform(this.instructionList, new Function<MethodLocation, Iterable<? extends DebugItem>>() { // from class: org.jf.dexlib2.builder.MutableMethodImplementation.3
            @Override // com.google.common.base.Function
            public Iterable<? extends DebugItem> apply(MethodLocation methodLocation) {
                if (MutableMethodImplementation.this.fixInstructions) {
                    throw IllegalStateException("This iterator was invalidated by a change to this MutableMethodImplementation.")
                }
                return methodLocation.getDebugItems()
            }
        }))
    }

    public final BuilderInstruction getFirstNonNop(Int i) {
        while (i < this.instructionList.size() - 1) {
            BuilderInstruction builderInstruction = this.instructionList.get(i).instruction
            if (builderInstruction.getOpcode() != Opcode.NOP) {
                return builderInstruction
            }
            i++
        }
        return null
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public List<BuilderInstruction> getInstructions() {
        if (this.fixInstructions) {
            fixInstructions()
        }
        return new AbstractList<BuilderInstruction>() { // from class: org.jf.dexlib2.builder.MutableMethodImplementation.2
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i) {
                if (i >= size()) {
                    throw IndexOutOfBoundsException()
                }
                if (MutableMethodImplementation.this.fixInstructions) {
                    MutableMethodImplementation.this.fixInstructions()
                }
                return MutableMethodImplementation.this.instructionList.get(i).instruction
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                if (MutableMethodImplementation.this.fixInstructions) {
                    MutableMethodImplementation.this.fixInstructions()
                }
                return MutableMethodImplementation.this.instructionList.size() - 1
            }
        }
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    fun getRegisterCount() {
        return this.registerCount
    }

    @Override // org.jf.dexlib2.iface.MethodImplementation
    public List<BuilderTryBlock> getTryBlocks() {
        if (this.fixInstructions) {
            fixInstructions()
        }
        return Collections.unmodifiableList(this.tryBlocks)
    }

    public final Int mapCodeAddressToIndex(Array<Int> iArr, Int i) {
        while (true) {
            if (i >= iArr.length) {
                i = iArr.length - 1
            }
            Int i2 = iArr[i]
            if (i2 >= 0) {
                return i2
            }
            i--
        }
    }

    public final BuilderArrayPayload newBuilderArrayPayload(ArrayPayload arrayPayload) {
        return BuilderArrayPayload(arrayPayload.getElementWidth(), arrayPayload.getArrayElements())
    }

    public final BuilderInstruction10t newBuilderInstruction10t(Int i, Array<Int> iArr, Instruction10t instruction10t) {
        return BuilderInstruction10t(instruction10t.getOpcode(), newLabel(iArr, i + instruction10t.getCodeOffset()))
    }

    public final BuilderInstruction10x newBuilderInstruction10x(Instruction10x instruction10x) {
        return BuilderInstruction10x(instruction10x.getOpcode())
    }

    public final BuilderInstruction11n newBuilderInstruction11n(Instruction11n instruction11n) {
        return BuilderInstruction11n(instruction11n.getOpcode(), instruction11n.getRegisterA(), instruction11n.getNarrowLiteral())
    }

    public final BuilderInstruction11x newBuilderInstruction11x(Instruction11x instruction11x) {
        return BuilderInstruction11x(instruction11x.getOpcode(), instruction11x.getRegisterA())
    }

    public final BuilderInstruction12x newBuilderInstruction12x(Instruction12x instruction12x) {
        return BuilderInstruction12x(instruction12x.getOpcode(), instruction12x.getRegisterA(), instruction12x.getRegisterB())
    }

    public final BuilderInstruction20bc newBuilderInstruction20bc(Instruction20bc instruction20bc) {
        return BuilderInstruction20bc(instruction20bc.getOpcode(), instruction20bc.getVerificationError(), instruction20bc.getReference())
    }

    public final BuilderInstruction20t newBuilderInstruction20t(Int i, Array<Int> iArr, Instruction20t instruction20t) {
        return BuilderInstruction20t(instruction20t.getOpcode(), newLabel(iArr, i + instruction20t.getCodeOffset()))
    }

    public final BuilderInstruction21c newBuilderInstruction21c(Instruction21c instruction21c) {
        return BuilderInstruction21c(instruction21c.getOpcode(), instruction21c.getRegisterA(), instruction21c.getReference())
    }

    public final BuilderInstruction21ih newBuilderInstruction21ih(Instruction21ih instruction21ih) {
        return BuilderInstruction21ih(instruction21ih.getOpcode(), instruction21ih.getRegisterA(), instruction21ih.getNarrowLiteral())
    }

    public final BuilderInstruction21lh newBuilderInstruction21lh(Instruction21lh instruction21lh) {
        return BuilderInstruction21lh(instruction21lh.getOpcode(), instruction21lh.getRegisterA(), instruction21lh.getWideLiteral())
    }

    public final BuilderInstruction21s newBuilderInstruction21s(Instruction21s instruction21s) {
        return BuilderInstruction21s(instruction21s.getOpcode(), instruction21s.getRegisterA(), instruction21s.getNarrowLiteral())
    }

    public final BuilderInstruction21t newBuilderInstruction21t(Int i, Array<Int> iArr, Instruction21t instruction21t) {
        return BuilderInstruction21t(instruction21t.getOpcode(), instruction21t.getRegisterA(), newLabel(iArr, i + instruction21t.getCodeOffset()))
    }

    public final BuilderInstruction22b newBuilderInstruction22b(Instruction22b instruction22b) {
        return BuilderInstruction22b(instruction22b.getOpcode(), instruction22b.getRegisterA(), instruction22b.getRegisterB(), instruction22b.getNarrowLiteral())
    }

    public final BuilderInstruction22c newBuilderInstruction22c(Instruction22c instruction22c) {
        return BuilderInstruction22c(instruction22c.getOpcode(), instruction22c.getRegisterA(), instruction22c.getRegisterB(), instruction22c.getReference())
    }

    public final BuilderInstruction22cs newBuilderInstruction22cs(Instruction22cs instruction22cs) {
        return BuilderInstruction22cs(instruction22cs.getOpcode(), instruction22cs.getRegisterA(), instruction22cs.getRegisterB(), instruction22cs.getFieldOffset())
    }

    public final BuilderInstruction22s newBuilderInstruction22s(Instruction22s instruction22s) {
        return BuilderInstruction22s(instruction22s.getOpcode(), instruction22s.getRegisterA(), instruction22s.getRegisterB(), instruction22s.getNarrowLiteral())
    }

    public final BuilderInstruction22t newBuilderInstruction22t(Int i, Array<Int> iArr, Instruction22t instruction22t) {
        return BuilderInstruction22t(instruction22t.getOpcode(), instruction22t.getRegisterA(), instruction22t.getRegisterB(), newLabel(iArr, i + instruction22t.getCodeOffset()))
    }

    public final BuilderInstruction22x newBuilderInstruction22x(Instruction22x instruction22x) {
        return BuilderInstruction22x(instruction22x.getOpcode(), instruction22x.getRegisterA(), instruction22x.getRegisterB())
    }

    public final BuilderInstruction23x newBuilderInstruction23x(Instruction23x instruction23x) {
        return BuilderInstruction23x(instruction23x.getOpcode(), instruction23x.getRegisterA(), instruction23x.getRegisterB(), instruction23x.getRegisterC())
    }

    public final BuilderInstruction30t newBuilderInstruction30t(Int i, Array<Int> iArr, Instruction30t instruction30t) {
        return BuilderInstruction30t(instruction30t.getOpcode(), newLabel(iArr, i + instruction30t.getCodeOffset()))
    }

    public final BuilderInstruction31c newBuilderInstruction31c(Instruction31c instruction31c) {
        return BuilderInstruction31c(instruction31c.getOpcode(), instruction31c.getRegisterA(), instruction31c.getReference())
    }

    public final BuilderInstruction31i newBuilderInstruction31i(Instruction31i instruction31i) {
        return BuilderInstruction31i(instruction31i.getOpcode(), instruction31i.getRegisterA(), instruction31i.getNarrowLiteral())
    }

    public final BuilderInstruction31t newBuilderInstruction31t(MethodLocation methodLocation, Array<Int> iArr, Instruction31t instruction31t) {
        Int codeAddress = methodLocation.getCodeAddress()
        return BuilderInstruction31t(instruction31t.getOpcode(), instruction31t.getRegisterA(), instruction31t.getOpcode() != Opcode.FILL_ARRAY_DATA ? newSwitchPayloadReferenceLabel(methodLocation, iArr, codeAddress + instruction31t.getCodeOffset()) : newLabel(iArr, codeAddress + instruction31t.getCodeOffset()))
    }

    public final BuilderInstruction32x newBuilderInstruction32x(Instruction32x instruction32x) {
        return BuilderInstruction32x(instruction32x.getOpcode(), instruction32x.getRegisterA(), instruction32x.getRegisterB())
    }

    public final BuilderInstruction35c newBuilderInstruction35c(Instruction35c instruction35c) {
        return BuilderInstruction35c(instruction35c.getOpcode(), instruction35c.getRegisterCount(), instruction35c.getRegisterC(), instruction35c.getRegisterD(), instruction35c.getRegisterE(), instruction35c.getRegisterF(), instruction35c.getRegisterG(), instruction35c.getReference())
    }

    public final BuilderInstruction35mi newBuilderInstruction35mi(Instruction35mi instruction35mi) {
        return BuilderInstruction35mi(instruction35mi.getOpcode(), instruction35mi.getRegisterCount(), instruction35mi.getRegisterC(), instruction35mi.getRegisterD(), instruction35mi.getRegisterE(), instruction35mi.getRegisterF(), instruction35mi.getRegisterG(), instruction35mi.getInlineIndex())
    }

    public final BuilderInstruction35ms newBuilderInstruction35ms(Instruction35ms instruction35ms) {
        return BuilderInstruction35ms(instruction35ms.getOpcode(), instruction35ms.getRegisterCount(), instruction35ms.getRegisterC(), instruction35ms.getRegisterD(), instruction35ms.getRegisterE(), instruction35ms.getRegisterF(), instruction35ms.getRegisterG(), instruction35ms.getVtableIndex())
    }

    public final BuilderInstruction3rc newBuilderInstruction3rc(Instruction3rc instruction3rc) {
        return BuilderInstruction3rc(instruction3rc.getOpcode(), instruction3rc.getStartRegister(), instruction3rc.getRegisterCount(), instruction3rc.getReference())
    }

    public final BuilderInstruction3rmi newBuilderInstruction3rmi(Instruction3rmi instruction3rmi) {
        return BuilderInstruction3rmi(instruction3rmi.getOpcode(), instruction3rmi.getStartRegister(), instruction3rmi.getRegisterCount(), instruction3rmi.getInlineIndex())
    }

    public final BuilderInstruction3rms newBuilderInstruction3rms(Instruction3rms instruction3rms) {
        return BuilderInstruction3rms(instruction3rms.getOpcode(), instruction3rms.getStartRegister(), instruction3rms.getRegisterCount(), instruction3rms.getVtableIndex())
    }

    public final BuilderInstruction51l newBuilderInstruction51l(Instruction51l instruction51l) {
        return BuilderInstruction51l(instruction51l.getOpcode(), instruction51l.getRegisterA(), instruction51l.getWideLiteral())
    }

    public final BuilderPackedSwitchPayload newBuilderPackedSwitchPayload(MethodLocation methodLocation, Array<Int> iArr, PackedSwitchPayload packedSwitchPayload) {
        List<? extends SwitchElement> switchElements = packedSwitchPayload.getSwitchElements()
        if (switchElements.size() == 0) {
            return BuilderPackedSwitchPayload(0, null)
        }
        MethodLocation methodLocationFindSwitchForPayload = findSwitchForPayload(methodLocation)
        Int i = methodLocationFindSwitchForPayload == null ? 0 : methodLocationFindSwitchForPayload.codeAddress
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        Iterator<? extends SwitchElement> it = switchElements.iterator()
        while (it.hasNext()) {
            arrayListNewArrayList.add(newLabel(iArr, it.next().getOffset() + i))
        }
        return BuilderPackedSwitchPayload(switchElements.get(0).getKey(), arrayListNewArrayList)
    }

    public final BuilderSparseSwitchPayload newBuilderSparseSwitchPayload(MethodLocation methodLocation, Array<Int> iArr, SparseSwitchPayload sparseSwitchPayload) {
        List<? extends SwitchElement> switchElements = sparseSwitchPayload.getSwitchElements()
        if (switchElements.size() == 0) {
            return BuilderSparseSwitchPayload(null)
        }
        MethodLocation methodLocationFindSwitchForPayload = findSwitchForPayload(methodLocation)
        Int i = methodLocationFindSwitchForPayload == null ? 0 : methodLocationFindSwitchForPayload.codeAddress
        ArrayList arrayListNewArrayList = Lists.newArrayList()
        for (SwitchElement switchElement : switchElements) {
            arrayListNewArrayList.add(SwitchLabelElement(switchElement.getKey(), newLabel(iArr, switchElement.getOffset() + i)))
        }
        return BuilderSparseSwitchPayload(arrayListNewArrayList)
    }

    public final Label newLabel(Array<Int> iArr, Int i) {
        return this.instructionList.get(mapCodeAddressToIndex(iArr, i)).addNewLabel()
    }

    fun newSwitchPayloadReferenceLabel(MethodLocation methodLocation, Array<Int> iArr, Int i) {
        MethodLocation methodLocation2 = this.instructionList.get(mapCodeAddressToIndex(iArr, i))
        SwitchPayloadReferenceLabel switchPayloadReferenceLabel = SwitchPayloadReferenceLabel()
        switchPayloadReferenceLabel.switchLocation = methodLocation
        methodLocation2.getLabels().add(switchPayloadReferenceLabel)
        return switchPayloadReferenceLabel
    }

    fun removeInstruction(Int i) {
        if (i >= this.instructionList.size() - 1) {
            throw IndexOutOfBoundsException()
        }
        MethodLocation methodLocation = this.instructionList.get(i)
        methodLocation.instruction = null
        methodLocation.mergeInto(this.instructionList.get(i + 1))
        this.instructionList.remove(i)
        Int codeUnits = methodLocation.codeAddress
        while (i < this.instructionList.size()) {
            MethodLocation methodLocation2 = this.instructionList.get(i)
            methodLocation2.index = i
            methodLocation2.codeAddress = codeUnits
            Instruction instruction = methodLocation2.getInstruction()
            if (instruction != null) {
                codeUnits += instruction.getCodeUnits()
            }
            i++
        }
        this.fixInstructions = true
    }

    fun replaceInstruction(Int i, BuilderInstruction builderInstruction) {
        if (i >= this.instructionList.size() - 1) {
            throw IndexOutOfBoundsException()
        }
        MethodLocation methodLocation = this.instructionList.get(i)
        builderInstruction.location = methodLocation
        methodLocation.instruction.location = null
        methodLocation.instruction = builderInstruction
        Int codeUnits = methodLocation.codeAddress + builderInstruction.getCodeUnits()
        for (Int i2 = i + 1; i2 < this.instructionList.size(); i2++) {
            MethodLocation methodLocation2 = this.instructionList.get(i2)
            methodLocation2.codeAddress = codeUnits
            Instruction instruction = methodLocation2.getInstruction()
            if (instruction != null) {
                codeUnits += instruction.getCodeUnits()
            }
        }
        this.fixInstructions = true
    }

    public final Unit setInstruction(MethodLocation methodLocation, BuilderInstruction builderInstruction) {
        methodLocation.instruction = builderInstruction
        builderInstruction.location = methodLocation
    }
}
