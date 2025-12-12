package jadx.core.dex.nodes.parser

import com.a.a.o
import com.gmail.heagoo.a.c.a
import jadx.core.dex.attributes.nodes.SourceFileAttr
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.exceptions.DecodeException
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

class DebugInfoParser {
    private static val DBG_ADVANCE_LINE = 2
    private static val DBG_ADVANCE_PC = 1
    private static val DBG_END_LOCAL = 5
    private static val DBG_END_SEQUENCE = 0
    private static val DBG_FIRST_SPECIAL = 10
    private static val DBG_LINE_BASE = -4
    private static val DBG_LINE_RANGE = 15
    private static val DBG_RESTART_LOCAL = 6
    private static val DBG_SET_EPILOGUE_BEGIN = 8
    private static val DBG_SET_FILE = 9
    private static val DBG_SET_PROLOGUE_END = 7
    private static val DBG_START_LOCAL = 3
    private static val DBG_START_LOCAL_EXTENDED = 4
    private static val LOG = c.a(DebugInfoParser.class)
    private final Array<InsnArg> activeRegisters
    private final DexNode dex
    private final Array<InsnNode> insnByOffset
    private final Array<LocalVar> locals
    private final MethodNode mth
    private final o section

    constructor(MethodNode methodNode, Int i, Array<InsnNode> insnNodeArr) {
        this.mth = methodNode
        this.dex = methodNode.dex()
        this.section = this.dex.openSection(i)
        Int regsCount = methodNode.getRegsCount()
        this.locals = new LocalVar[regsCount]
        this.activeRegisters = new InsnArg[regsCount]
        this.insnByOffset = insnNodeArr
    }

    private fun addrChange(Int i, Int i2, Int i3) {
        Int iMin = Math.min(i + i2, this.insnByOffset.length - 1)
        for (Int i4 = i + 1; i4 <= iMin; i4++) {
            InsnNode insnNode = this.insnByOffset[i4]
            if (insnNode != null) {
                for (InsnArg insnArg : insnNode.getArguments()) {
                    if (insnArg.isRegister()) {
                        this.activeRegisters[((RegisterArg) insnArg).getRegNum()] = insnArg
                    }
                }
                RegisterArg result = insnNode.getResult()
                if (result != null) {
                    this.activeRegisters[result.getRegNum()] = result
                }
            }
        }
        setSourceLines(i, iMin, i3)
        return iMin
    }

    private fun fillLocals(InsnNode insnNode, LocalVar localVar) {
        merge(insnNode.getResult(), localVar)
        Iterator it = insnNode.getArguments().iterator()
        while (it.hasNext()) {
            merge((InsnArg) it.next(), localVar)
        }
    }

    private fun merge(InsnArg insnArg, LocalVar localVar) {
        Boolean z = true
        if (insnArg == null || !insnArg.isRegister()) {
            return
        }
        RegisterArg registerArg = (RegisterArg) insnArg
        if (localVar.getRegNum() == registerArg.getRegNum()) {
            SSAVar sVar = registerArg.getSVar()
            if (sVar != null) {
                Int endAddr = sVar.getEndAddr()
                Int startAddr = sVar.getStartAddr()
                Int startAddr2 = localVar.getStartAddr()
                Int endAddr2 = localVar.getEndAddr()
                if (!(endAddr2 >= startAddr && endAddr >= startAddr2) || endAddr > endAddr2) {
                    z = false
                }
            }
            if (z) {
                registerArg.mergeDebugInfo(localVar.getType(), localVar.getName())
            }
        }
    }

    private fun setLine(Int i, Int i2) {
        InsnNode insnNode = this.insnByOffset[i]
        if (insnNode != null) {
            insnNode.setSourceLine(i2)
        }
    }

    private fun setSourceLines(Int i, Int i2, Int i3) {
        for (Int i4 = i + 1; i4 < i2; i4++) {
            setLine(i4, i3)
        }
    }

    private fun setVar(LocalVar localVar) {
        Int endAddr = localVar.getEndAddr()
        for (Int startAddr = localVar.getStartAddr(); startAddr <= endAddr; startAddr++) {
            InsnNode insnNode = this.insnByOffset[startAddr]
            if (insnNode != null) {
                fillLocals(insnNode, localVar)
            }
        }
        merge(this.activeRegisters[localVar.getRegNum()], localVar)
    }

    private fun startVar(LocalVar localVar, Int i, Int i2) {
        SSAVar sVar
        InsnNode parentInsn
        Int regNum = localVar.getRegNum()
        LocalVar localVar2 = this.locals[regNum]
        if (localVar2 != null && !localVar2.isEnd()) {
            localVar2.end(i, i2)
            setVar(localVar2)
        }
        InsnArg insnArg = this.activeRegisters[localVar.getRegNum()]
        if ((insnArg is RegisterArg) && (sVar = ((RegisterArg) insnArg).getSVar()) != null && sVar.getStartAddr() != -1 && (parentInsn = sVar.getAssign().getParentInsn()) != null && parentInsn.getOffset() >= 0) {
            i = parentInsn.getOffset()
        }
        localVar.start(i, i2)
        this.locals[regNum] = localVar
    }

    fun process() throws DecodeException {
        Int iA
        Int iAddrChange
        Int iB = a.b(this.section)
        Int iB2 = a.b(this.section)
        List<RegisterArg> arguments = this.mth.getArguments(false)
        for (Int i = 0; i < iB2; i++) {
            Int iB3 = a.b(this.section) - 1
            if (iB3 != -1) {
                String string = this.dex.getString(iB3)
                if (i < arguments.size()) {
                    ((RegisterArg) arguments.get(i)).setName(string)
                }
            }
        }
        for (RegisterArg registerArg : arguments) {
            Int regNum = registerArg.getRegNum()
            this.locals[regNum] = LocalVar(registerArg)
            this.activeRegisters[regNum] = registerArg
        }
        addrChange(-1, 1, iB)
        setLine(0, iB)
        Int iD = this.section.d() & 255
        Int i2 = 0
        while (iD != 0) {
            switch (iD) {
                case 1:
                    Int iAddrChange2 = addrChange(i2, a.b(this.section), iB)
                    setLine(iAddrChange2, iB)
                    iAddrChange = iAddrChange2
                    iA = iB
                    continue
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                case 2:
                    iA = a.a(this.section) + iB
                    iAddrChange = i2
                    continue
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                case 3:
                    startVar(LocalVar(this.dex, a.b(this.section), a.b(this.section) - 1, a.b(this.section) - 1, -1), i2, iB)
                    iA = iB
                    iAddrChange = i2
                    continue
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                case 4:
                    startVar(LocalVar(this.dex, a.b(this.section), a.b(this.section) - 1, a.b(this.section) - 1, a.b(this.section) - 1), i2, iB)
                    iA = iB
                    iAddrChange = i2
                    continue
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                case 5:
                    LocalVar localVar = this.locals[a.b(this.section)]
                    if (localVar != null) {
                        localVar.end(i2, iB)
                        setVar(localVar)
                        iA = iB
                        iAddrChange = i2
                    }
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                    break
                case 6:
                    LocalVar localVar2 = this.locals[a.b(this.section)]
                    if (localVar2 != null) {
                        if (localVar2.end(i2, iB)) {
                            setVar(localVar2)
                        }
                        localVar2.start(i2, iB)
                        iA = iB
                        iAddrChange = i2
                    }
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                    break
                case 7:
                case 8:
                    iA = iB
                    iAddrChange = i2
                    continue
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                case 9:
                    Int iB4 = a.b(this.section) - 1
                    if (iB4 != -1) {
                        this.mth.addAttr(SourceFileAttr(this.dex.getString(iB4)))
                        iA = iB
                        iAddrChange = i2
                    }
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
                    break
                default:
                    if (iD < 10) {
                        throw DecodeException("Unknown debug insn code: " + iD)
                    }
                    iAddrChange = addrChange(i2, (iD - 10) / 15, iB)
                    iA = ((r0 % 15) - 4) + iB
                    setLine(iAddrChange, iA)
                    continue
                    iB = iA
                    i2 = iAddrChange
                    iD = this.section.d() & 255
            }
            iA = iB
            iAddrChange = i2
            iB = iA
            i2 = iAddrChange
            iD = this.section.d() & 255
        }
        for (LocalVar localVar3 : this.locals) {
            if (localVar3 != null && !localVar3.isEnd()) {
                localVar3.end(this.mth.getCodeSize() - 1, iB)
                setVar(localVar3)
            }
        }
        setSourceLines(i2, this.insnByOffset.length, iB)
    }
}
