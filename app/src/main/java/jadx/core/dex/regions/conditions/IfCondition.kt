package jadx.core.dex.regions.conditions

import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.IfOp
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Iterator
import java.util.LinkedList
import java.util.List

class IfCondition {
    private final List args
    private final Compare compare
    private final Mode mode

    public enum Mode {
        COMPARE,
        TERNARY,
        NOT,
        AND,
        OR
    }

    private constructor(Compare compare) {
        this.mode = Mode.COMPARE
        this.compare = compare
        this.args = Collections.emptyList()
    }

    private constructor(Mode mode, List list) {
        this.mode = mode
        this.args = list
        this.compare = null
    }

    private constructor(IfCondition ifCondition) {
        this.mode = ifCondition.mode
        this.compare = ifCondition.compare
        if (ifCondition.mode == Mode.COMPARE) {
            this.args = Collections.emptyList()
        } else {
            this.args = ArrayList(ifCondition.args)
        }
    }

    fun fromIfBlock(BlockNode blockNode) {
        if (blockNode == null) {
            return null
        }
        return fromIfNode((IfNode) blockNode.getInstructions().get(0))
    }

    fun fromIfNode(IfNode ifNode) {
        return IfCondition(Compare(ifNode))
    }

    fun invert(IfCondition ifCondition) {
        Mode mode = ifCondition.getMode()
        switch (mode) {
            case COMPARE:
                return IfCondition(ifCondition.getCompare().invert())
            case TERNARY:
                return ternary(not(ifCondition.first()), ifCondition.third(), ifCondition.second())
            case NOT:
                return ifCondition.first()
            case AND:
            case OR:
                List args = ifCondition.getArgs()
                ArrayList arrayList = ArrayList(args.size())
                Iterator it = args.iterator()
                while (it.hasNext()) {
                    arrayList.add(invert((IfCondition) it.next()))
                }
                return IfCondition(mode == Mode.AND ? Mode.OR : Mode.AND, arrayList)
            default:
                throw JadxRuntimeException("Unknown mode for invert: " + mode)
        }
    }

    fun merge(Mode mode, IfCondition ifCondition, IfCondition ifCondition2) {
        if (ifCondition.getMode() != mode) {
            return IfCondition(mode, Arrays.asList(ifCondition, ifCondition2))
        }
        IfCondition ifCondition3 = IfCondition(ifCondition)
        ifCondition3.addArg(ifCondition2)
        return ifCondition3
    }

    fun not(IfCondition ifCondition) {
        return ifCondition.getMode() == Mode.NOT ? ifCondition.first() : IfCondition(Mode.NOT, Collections.singletonList(ifCondition))
    }

    fun simplify(IfCondition ifCondition) {
        Int size
        ArrayList arrayList
        if (ifCondition.isCompare()) {
            Compare compare = ifCondition.getCompare()
            simplifyCmpOp(compare)
            if (compare.getOp() == IfOp.EQ && compare.getB().isLiteral() && compare.getB().equals(LiteralArg.FALSE)) {
                return not(IfCondition(compare.invert()))
            }
            compare.normalize()
            return ifCondition
        }
        ArrayList arrayList2 = null
        Int i = 0
        while (i < ifCondition.getArgs().size()) {
            IfCondition ifCondition2 = (IfCondition) ifCondition.getArgs().get(i)
            IfCondition ifConditionSimplify = simplify(ifCondition2)
            if (ifConditionSimplify != ifCondition2) {
                arrayList = arrayList2 == null ? ArrayList(ifCondition.getArgs()) : arrayList2
                arrayList.set(i, ifConditionSimplify)
            } else {
                arrayList = arrayList2
            }
            i++
            arrayList2 = arrayList
        }
        if (arrayList2 != null) {
            ifCondition = IfCondition(ifCondition.getMode(), arrayList2)
        }
        if (ifCondition.getMode() == Mode.NOT && ifCondition.first().getMode() == Mode.NOT) {
            ifCondition = invert(ifCondition.first())
        }
        if (ifCondition.getMode() == Mode.TERNARY && ifCondition.first().getMode() == Mode.NOT) {
            ifCondition = invert(ifCondition)
        }
        if ((ifCondition.getMode() != Mode.OR && ifCondition.getMode() != Mode.AND) || (size = ifCondition.getArgs().size()) <= 1) {
            return ifCondition
        }
        Int i2 = 0
        for (IfCondition ifCondition3 : ifCondition.getArgs()) {
            i2 = (ifCondition3.getMode() == Mode.NOT || (ifCondition3.isCompare() && ifCondition3.getCompare().getOp() == IfOp.NE)) ? i2 + 1 : i2
        }
        return i2 > size / 2 ? not(invert(ifCondition)) : ifCondition
    }

    private fun simplifyCmpOp(Compare compare) {
        if (compare.getA().isInsnWrap() && compare.getB().isLiteral() && ((LiteralArg) compare.getB()).getLiteral() == 0) {
            InsnNode wrapInsn = ((InsnWrapArg) compare.getA()).getWrapInsn()
            InsnType type = wrapInsn.getType()
            if (type == InsnType.CMP_L || type == InsnType.CMP_G) {
                IfNode insn = compare.getInsn()
                insn.changeCondition(insn.getOp(), wrapInsn.getArg(0), wrapInsn.getArg(1))
            }
        }
    }

    fun ternary(IfCondition ifCondition, IfCondition ifCondition2, IfCondition ifCondition3) {
        return IfCondition(Mode.TERNARY, Arrays.asList(ifCondition, ifCondition2, ifCondition3))
    }

    public final Unit addArg(IfCondition ifCondition) {
        this.args.add(ifCondition)
    }

    public final IfCondition first() {
        return (IfCondition) this.args.get(0)
    }

    public final List getArgs() {
        return this.args
    }

    public final Compare getCompare() {
        return this.compare
    }

    public final Mode getMode() {
        return this.mode
    }

    public final List getRegisterArgs() {
        LinkedList linkedList = LinkedList()
        if (this.mode == Mode.COMPARE) {
            this.compare.getInsn().getRegisterArgs(linkedList)
        } else {
            Iterator it = this.args.iterator()
            while (it.hasNext()) {
                linkedList.addAll(((IfCondition) it.next()).getRegisterArgs())
            }
        }
        return linkedList
    }

    public final Boolean isCompare() {
        return this.mode == Mode.COMPARE
    }

    public final IfCondition second() {
        return (IfCondition) this.args.get(1)
    }

    public final IfCondition third() {
        return (IfCondition) this.args.get(2)
    }

    public final String toString() {
        switch (this.mode) {
            case COMPARE:
                return this.compare.toString()
            case TERNARY:
                return first() + " ? " + second() + " : " + third()
            case NOT:
                return "!" + first()
            case AND:
            case OR:
                String str = this.mode == Mode.OR ? " || " : " && "
                StringBuilder sb = StringBuilder()
                sb.append('(')
                Iterator it = this.args.iterator()
                while (it.hasNext()) {
                    sb.append((IfCondition) it.next())
                    if (it.hasNext()) {
                        sb.append(str)
                    }
                }
                sb.append(')')
                return sb.toString()
            default:
                return "??"
        }
    }
}
