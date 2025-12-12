package jadx.core.codegen

import jadx.core.dex.instructions.ArithNode
import jadx.core.dex.instructions.IfOp
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.regions.conditions.Compare
import jadx.core.dex.regions.conditions.IfCondition
import jadx.core.utils.ErrorsCounter
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Iterator
import java.util.LinkedList
import java.util.Queue
import org.d.b
import org.d.c

class ConditionGen extends InsnGen {
    private static val LOG = c.a(ConditionGen.class)

    class CondStack {
        private final Queue stack

        private constructor() {
            this.stack = LinkedList()
        }

        fun getStack() {
            return this.stack
        }

        fun pop() {
            return (IfCondition) this.stack.poll()
        }

        fun push(IfCondition ifCondition) {
            this.stack.add(ifCondition)
        }
    }

    constructor(InsnGen insnGen) {
        super(insnGen.mgen, insnGen.fallback)
    }

    private fun add(CodeWriter codeWriter, CondStack condStack, IfCondition ifCondition) {
        condStack.push(ifCondition)
        switch (ifCondition.getMode()) {
            case COMPARE:
                addCompare(codeWriter, condStack, ifCondition.getCompare())
                break
            case TERNARY:
                addTernary(codeWriter, condStack, ifCondition)
                break
            case NOT:
                addNot(codeWriter, condStack, ifCondition)
                break
            case AND:
            case OR:
                addAndOr(codeWriter, condStack, ifCondition)
                break
            default:
                throw JadxRuntimeException("Unknown condition mode: " + ifCondition.getMode())
        }
        condStack.pop()
    }

    private fun addAndOr(CodeWriter codeWriter, CondStack condStack, IfCondition ifCondition) {
        String str = ifCondition.getMode() == IfCondition.Mode.AND ? " && " : " || "
        Iterator it = ifCondition.getArgs().iterator()
        while (it.hasNext()) {
            wrap(codeWriter, condStack, (IfCondition) it.next())
            if (it.hasNext()) {
                codeWriter.add(str)
            }
        }
    }

    private fun addCompare(CodeWriter codeWriter, CondStack condStack, Compare compare) {
        IfOp op = compare.getOp()
        InsnArg a2 = compare.getA()
        InsnArg b2 = compare.getB()
        if (a2.getType().equals(ArgType.BOOLEAN) && b2.isLiteral() && b2.getType().equals(ArgType.BOOLEAN)) {
            IfOp ifOpInvert = ((LiteralArg) b2).getLiteral() == 0 ? op.invert() : op
            if (ifOpInvert == IfOp.EQ) {
                if (condStack.getStack().size() == 1) {
                    addArg(codeWriter, a2, false)
                    return
                } else {
                    wrap(codeWriter, a2)
                    return
                }
            }
            if (ifOpInvert == IfOp.NE) {
                codeWriter.add('!')
                wrap(codeWriter, a2)
                return
            } else {
                LOG.b(ErrorsCounter.formatErrorMsg(this.mth, "Unsupported Boolean condition " + ifOpInvert.getSymbol()))
                op = ifOpInvert
            }
        }
        addArg(codeWriter, a2, isArgWrapNeeded(a2))
        codeWriter.add(' ').add(op.getSymbol()).add(' ')
        addArg(codeWriter, b2, isArgWrapNeeded(b2))
    }

    private fun addNot(CodeWriter codeWriter, CondStack condStack, IfCondition ifCondition) {
        codeWriter.add('!')
        wrap(codeWriter, condStack, (IfCondition) ifCondition.getArgs().get(0))
    }

    private fun addTernary(CodeWriter codeWriter, CondStack condStack, IfCondition ifCondition) {
        add(codeWriter, condStack, ifCondition.first())
        codeWriter.add(" ? ")
        add(codeWriter, condStack, ifCondition.second())
        codeWriter.add(" : ")
        add(codeWriter, condStack, ifCondition.third())
    }

    private fun isArgWrapNeeded(InsnArg insnArg) {
        if (!insnArg.isInsnWrap()) {
            return false
        }
        InsnType type = ((InsnWrapArg) insnArg).getWrapInsn().getType()
        if (type == InsnType.ARITH) {
            switch (((ArithNode) r0).getOp()) {
                case ADD:
                case SUB:
                case MUL:
                case DIV:
                case REM:
                    return false
                default:
                    return true
            }
        }
        switch (type) {
            case INVOKE:
            case SGET:
            case IGET:
            case AGET:
            case CONST:
            case ARRAY_LENGTH:
                return false
            default:
                return true
        }
    }

    private fun isWrapNeeded(IfCondition ifCondition) {
        return (ifCondition.isCompare() || ifCondition.getMode() == IfCondition.Mode.NOT) ? false : true
    }

    private fun wrap(CodeWriter codeWriter, CondStack condStack, IfCondition ifCondition) {
        Boolean zIsWrapNeeded = isWrapNeeded(ifCondition)
        if (zIsWrapNeeded) {
            codeWriter.add('(')
        }
        add(codeWriter, condStack, ifCondition)
        if (zIsWrapNeeded) {
            codeWriter.add(')')
        }
    }

    private fun wrap(CodeWriter codeWriter, InsnArg insnArg) {
        Boolean zIsArgWrapNeeded = isArgWrapNeeded(insnArg)
        if (zIsArgWrapNeeded) {
            codeWriter.add('(')
        }
        addArg(codeWriter, insnArg, false)
        if (zIsArgWrapNeeded) {
            codeWriter.add(')')
        }
    }

    Unit add(CodeWriter codeWriter, IfCondition ifCondition) {
        add(codeWriter, CondStack(), ifCondition)
    }

    Unit wrap(CodeWriter codeWriter, IfCondition ifCondition) {
        wrap(codeWriter, CondStack(), ifCondition)
    }
}
