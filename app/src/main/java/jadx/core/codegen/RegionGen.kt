package jadx.core.codegen

import jadx.core.codegen.InsnGen
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.DeclareVariablesAttr
import jadx.core.dex.attributes.nodes.ForceReturnAttr
import jadx.core.dex.attributes.nodes.LoopLabelAttr
import jadx.core.dex.instructions.SwitchNode
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.NamedArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.parser.FieldInitAttr
import jadx.core.dex.regions.Region
import jadx.core.dex.regions.SwitchRegion
import jadx.core.dex.regions.SynchronizedRegion
import jadx.core.dex.regions.TryCatchRegion
import jadx.core.dex.regions.conditions.IfCondition
import jadx.core.dex.regions.conditions.IfRegion
import jadx.core.dex.regions.loops.ForEachLoop
import jadx.core.dex.regions.loops.ForLoop
import jadx.core.dex.regions.loops.LoopRegion
import jadx.core.dex.regions.loops.LoopType
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.utils.ErrorsCounter
import jadx.core.utils.RegionUtils
import jadx.core.utils.exceptions.CodegenException
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Iterator
import java.util.List
import java.util.Map

class RegionGen extends InsnGen {
    constructor(MethodGen methodGen) {
        super(methodGen, false)
    }

    private fun connectElseIf(CodeWriter codeWriter, IContainer iContainer) throws CodegenException {
        if (iContainer.contains(AFlag.ELSE_IF_CHAIN) && (iContainer is Region)) {
            List subBlocks = ((Region) iContainer).getSubBlocks()
            if (subBlocks.size() != 1 || !(subBlocks.get(0) instanceof IfRegion)) {
                return false
            }
            makeIf((IfRegion) subBlocks.get(0), codeWriter, false)
            return true
        }
        return false
    }

    private fun declareVars(CodeWriter codeWriter, IContainer iContainer) {
        DeclareVariablesAttr declareVariablesAttr = (DeclareVariablesAttr) iContainer.get(AType.DECLARE_VARIABLES)
        if (declareVariablesAttr != null) {
            for (RegisterArg registerArg : declareVariablesAttr.getVars()) {
                codeWriter.startLine()
                declareVar(codeWriter, registerArg)
                codeWriter.add(';')
            }
        }
    }

    private fun makeCatchBlock(CodeWriter codeWriter, ExceptionHandler exceptionHandler) {
        IContainer handlerRegion = exceptionHandler.getHandlerRegion()
        if (handlerRegion == null) {
            return
        }
        codeWriter.startLine("} catch (")
        InsnArg arg = exceptionHandler.getArg()
        if (arg is RegisterArg) {
            declareVar(codeWriter, (RegisterArg) arg)
        } else if (arg is NamedArg) {
            if (exceptionHandler.isCatchAll()) {
                codeWriter.add("Throwable")
            } else {
                useClass(codeWriter, exceptionHandler.getCatchType())
            }
            codeWriter.add(' ')
            codeWriter.add(this.mgen.getNameGen().assignNamedArg((NamedArg) arg))
        }
        codeWriter.add(") {")
        makeRegionIndent(codeWriter, handlerRegion)
    }

    private fun makeIf(IfRegion ifRegion, CodeWriter codeWriter, Boolean z) throws CodegenException {
        if (z) {
            codeWriter.startLineWithNum(ifRegion.getSourceLine())
        } else {
            codeWriter.attachSourceLine(ifRegion.getSourceLine())
        }
        codeWriter.add("if (")
        ConditionGen(this).add(codeWriter, ifRegion.getCondition())
        codeWriter.add(") {")
        makeRegionIndent(codeWriter, ifRegion.getThenRegion())
        codeWriter.startLine('}')
        IContainer elseRegion = ifRegion.getElseRegion()
        if (elseRegion == null || !RegionUtils.notEmpty(elseRegion)) {
            return
        }
        codeWriter.add(" else ")
        if (connectElseIf(codeWriter, elseRegion)) {
            return
        }
        codeWriter.add('{')
        makeRegionIndent(codeWriter, elseRegion)
        codeWriter.startLine('}')
    }

    private fun makeLoop(LoopRegion loopRegion, CodeWriter codeWriter) {
        BlockNode header = loopRegion.getHeader()
        if (header != null) {
            List instructions = header.getInstructions()
            if (instructions.size() > 1) {
                ErrorsCounter.methodError(this.mth, "Found not inlined instructions from loop header")
                Int size = instructions.size() - 1
                for (Int i = 0; i < size; i++) {
                    makeInsn((InsnNode) instructions.get(i), codeWriter)
                }
            }
        }
        LoopLabelAttr loopLabelAttr = (LoopLabelAttr) loopRegion.getInfo().getStart().get(AType.LOOP_LABEL)
        if (loopLabelAttr != null) {
            codeWriter.startLine(this.mgen.getNameGen().getLoopLabel(loopLabelAttr)).add(':')
        }
        IfCondition condition = loopRegion.getCondition()
        if (condition == null) {
            codeWriter.startLine("while (true) {");
            makeRegionIndent(codeWriter, loopRegion.getBody())
            codeWriter.startLine('}')
        } else {
            ConditionGen conditionGen = ConditionGen(this)
            LoopType type = loopRegion.getType()
            if (type != null) {
                if (type is ForLoop) {
                    ForLoop forLoop = (ForLoop) type
                    codeWriter.startLine("for (");
                    makeInsn(forLoop.getInitInsn(), codeWriter, InsnGen.Flags.INLINE)
                    codeWriter.add("; ")
                    conditionGen.add(codeWriter, condition)
                    codeWriter.add("; ")
                    makeInsn(forLoop.getIncrInsn(), codeWriter, InsnGen.Flags.INLINE)
                    codeWriter.add(") {")
                    makeRegionIndent(codeWriter, loopRegion.getBody())
                    codeWriter.startLine('}')
                } else {
                    if (!(type is ForEachLoop)) {
                        throw JadxRuntimeException("Unknown loop type: " + type.getClass())
                    }
                    ForEachLoop forEachLoop = (ForEachLoop) type
                    codeWriter.startLine("for (");
                    declareVar(codeWriter, forEachLoop.getVarArg())
                    codeWriter.add(" : ")
                    addArg(codeWriter, forEachLoop.getIterableArg(), false)
                    codeWriter.add(") {")
                    makeRegionIndent(codeWriter, loopRegion.getBody())
                    codeWriter.startLine('}')
                }
            } else if (loopRegion.isConditionAtEnd()) {
                codeWriter.startLine("do {")
                makeRegionIndent(codeWriter, loopRegion.getBody())
                codeWriter.startLine("} while (");
                conditionGen.add(codeWriter, condition)
                codeWriter.add(");")
            } else {
                codeWriter.startLine("while (");
                conditionGen.add(codeWriter, condition)
                codeWriter.add(") {")
                makeRegionIndent(codeWriter, loopRegion.getBody())
                codeWriter.startLine('}')
            }
        }
        return codeWriter
    }

    private fun makeSimpleBlock(IBlock iBlock, CodeWriter codeWriter) {
        for (InsnNode insnNode : iBlock.getInstructions()) {
            if (!insnNode.contains(AFlag.SKIP)) {
                makeInsn(insnNode, codeWriter)
            }
        }
        ForceReturnAttr forceReturnAttr = (ForceReturnAttr) iBlock.get(AType.FORCE_RETURN)
        if (forceReturnAttr != null) {
            makeInsn(forceReturnAttr.getReturnInsn(), codeWriter)
        }
    }

    private fun makeSimpleRegion(CodeWriter codeWriter, Region region) {
        declareVars(codeWriter, region)
        Iterator it = region.getSubBlocks().iterator()
        while (it.hasNext()) {
            makeRegion(codeWriter, (IContainer) it.next())
        }
    }

    private fun makeSwitch(SwitchRegion switchRegion, CodeWriter codeWriter) {
        InsnArg arg = ((SwitchNode) switchRegion.getHeader().getInstructions().get(0)).getArg(0)
        codeWriter.startLine("switch (")
        addArg(codeWriter, arg, false)
        codeWriter.add(") {")
        codeWriter.incIndent()
        Int size = switchRegion.getKeys().size()
        for (Int i = 0; i < size; i++) {
            List list = (List) switchRegion.getKeys().get(i)
            IContainer iContainer = (IContainer) switchRegion.getCases().get(i)
            Iterator it = list.iterator()
            while (it.hasNext()) {
                Object next = it.next()
                codeWriter.startLine("case ")
                if (next is FieldNode) {
                    FieldNode fieldNode = (FieldNode) next
                    if (fieldNode.getParentClass().isEnum()) {
                        codeWriter.add(fieldNode.getAlias())
                    } else {
                        staticField(codeWriter, fieldNode.getFieldInfo())
                        FieldInitAttr fieldInitAttr = (FieldInitAttr) fieldNode.get(AType.FIELD_INIT)
                        if (fieldInitAttr != null && fieldInitAttr.getValue() != null) {
                            codeWriter.add(" /*").add(fieldInitAttr.getValue().toString()).add("*/")
                        }
                    }
                } else {
                    if (!(next is Integer)) {
                        throw JadxRuntimeException("Unexpected key in switch: " + (next != null ? next.getClass() : null))
                    }
                    codeWriter.add(TypeGen.literalToString(((Integer) next).intValue(), arg.getType()))
                }
                codeWriter.add(':')
            }
            makeRegionIndent(codeWriter, iContainer)
        }
        if (switchRegion.getDefaultCase() != null) {
            codeWriter.startLine("default:")
            makeRegionIndent(codeWriter, switchRegion.getDefaultCase())
        }
        codeWriter.decIndent()
        codeWriter.startLine('}')
        return codeWriter
    }

    private fun makeSynchronizedRegion(SynchronizedRegion synchronizedRegion, CodeWriter codeWriter) {
        codeWriter.startLine("synchronized (")
        addArg(codeWriter, synchronizedRegion.getEnterInsn().getArg(0))
        codeWriter.add(") {")
        makeRegionIndent(codeWriter, synchronizedRegion.getRegion())
        codeWriter.startLine('}')
    }

    private fun makeTryCatch(TryCatchRegion tryCatchRegion, CodeWriter codeWriter) {
        codeWriter.startLine("try {")
        makeRegionIndent(codeWriter, tryCatchRegion.getTryRegion())
        Iterator it = tryCatchRegion.getCatchRegions().entrySet().iterator()
        ExceptionHandler exceptionHandler = null
        while (it.hasNext()) {
            ExceptionHandler exceptionHandler2 = (ExceptionHandler) ((Map.Entry) it.next()).getKey()
            if (exceptionHandler2.isCatchAll()) {
                exceptionHandler = exceptionHandler2
            } else {
                makeCatchBlock(codeWriter, exceptionHandler2)
            }
        }
        if (exceptionHandler != null) {
            makeCatchBlock(codeWriter, exceptionHandler)
        }
        IContainer finallyRegion = tryCatchRegion.getFinallyRegion()
        if (finallyRegion != null) {
            codeWriter.startLine("} finally {")
            makeRegionIndent(codeWriter, finallyRegion)
        }
        codeWriter.startLine('}')
    }

    fun makeRegion(CodeWriter codeWriter, IContainer iContainer) throws CodegenException {
        if (iContainer is IBlock) {
            makeSimpleBlock((IBlock) iContainer, codeWriter)
            return
        }
        if (!(iContainer is IRegion)) {
            throw CodegenException("Not processed container: " + iContainer)
        }
        if (iContainer is Region) {
            makeSimpleRegion(codeWriter, (Region) iContainer)
            return
        }
        declareVars(codeWriter, iContainer)
        if (iContainer is IfRegion) {
            makeIf((IfRegion) iContainer, codeWriter, true)
            return
        }
        if (iContainer is SwitchRegion) {
            makeSwitch((SwitchRegion) iContainer, codeWriter)
            return
        }
        if (iContainer is LoopRegion) {
            makeLoop((LoopRegion) iContainer, codeWriter)
        } else if (iContainer is TryCatchRegion) {
            makeTryCatch((TryCatchRegion) iContainer, codeWriter)
        } else if (iContainer is SynchronizedRegion) {
            makeSynchronizedRegion((SynchronizedRegion) iContainer, codeWriter)
        }
    }

    fun makeRegionIndent(CodeWriter codeWriter, IContainer iContainer) throws CodegenException {
        codeWriter.incIndent()
        makeRegion(codeWriter, iContainer)
        codeWriter.decIndent()
    }
}
