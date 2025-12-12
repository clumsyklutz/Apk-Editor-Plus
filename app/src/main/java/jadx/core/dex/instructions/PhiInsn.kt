package jadx.core.dex.instructions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InstructionRemover
import jadx.core.utils.Utils
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.IdentityHashMap
import java.util.Map

class PhiInsn extends InsnNode {
    private final Map blockBinds

    constructor(Int i, Int i2) {
        super(InsnType.PHI, i2)
        this.blockBinds = IdentityHashMap(i2)
        setResult(InsnArg.reg(i, ArgType.UNKNOWN))
        add(AFlag.DONT_INLINE)
    }

    public final RegisterArg bindArg(BlockNode blockNode) {
        RegisterArg registerArgReg = InsnArg.reg(getResult().getRegNum(), getResult().getType())
        bindArg(registerArgReg, blockNode)
        return registerArgReg
    }

    public final Unit bindArg(RegisterArg registerArg, BlockNode blockNode) {
        if (this.blockBinds.containsValue(blockNode)) {
            throw JadxRuntimeException("Duplicate predecessors in PHI insn: " + blockNode + ", " + this)
        }
        addArg(registerArg)
        this.blockBinds.put(registerArg, blockNode)
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final RegisterArg getArg(Int i) {
        return (RegisterArg) super.getArg(i)
    }

    public final Map getBlockBinds() {
        return this.blockBinds
    }

    public final BlockNode getBlockByArg(RegisterArg registerArg) {
        return (BlockNode) this.blockBinds.get(registerArg)
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Boolean removeArg(InsnArg insnArg) {
        if (!(insnArg is RegisterArg)) {
            return false
        }
        RegisterArg registerArg = (RegisterArg) insnArg
        if (!super.removeArg(registerArg)) {
            return false
        }
        this.blockBinds.remove(registerArg)
        InstructionRemover.fixUsedInPhiFlag(registerArg)
        return true
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Boolean replaceArg(InsnArg insnArg, InsnArg insnArg2) {
        if (!(insnArg is RegisterArg) || !(insnArg2 is RegisterArg)) {
            return false
        }
        BlockNode blockByArg = getBlockByArg((RegisterArg) insnArg)
        if (blockByArg == null) {
            throw JadxRuntimeException("Unknown predecessor block by arg " + insnArg + " in PHI: " + this)
        }
        if (removeArg(insnArg)) {
            bindArg((RegisterArg) insnArg2, blockByArg)
        }
        return true
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Unit setArg(Int i, InsnArg insnArg) {
        throw JadxRuntimeException("Unsupported operation for PHI node")
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final String toString() {
        return "PHI: " + getResult() + " = " + Utils.listToString(getArguments()) + " binds: " + this.blockBinds
    }
}
