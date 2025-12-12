package jadx.core.dex.nodes

import com.a.a.d
import com.a.a.f
import com.a.a.g
import com.a.a.h
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.JumpInfo
import jadx.core.dex.attributes.nodes.LineAttrNode
import jadx.core.dex.attributes.nodes.LoopInfo
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.GotoNode
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.InsnDecoder
import jadx.core.dex.instructions.SwitchNode
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.instructions.args.TypeImmutableArg
import jadx.core.dex.nodes.parser.SignatureParser
import jadx.core.dex.regions.Region
import jadx.core.dex.trycatch.ExcHandlerAttr
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.TryCatchBlock
import jadx.core.utils.Utils
import jadx.core.utils.exceptions.DecodeException
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import org.d.b
import org.d.c

class MethodNode extends LineAttrNode implements ILoadable {
    private static val LOG = c.a(MethodNode.class)
    private final AccessInfo accFlags
    private List argsList
    private List blocks
    private Int codeSize
    private Int debugInfoOffset
    private BlockNode enterBlock
    private List exitBlocks
    private Map genericMap
    private Array<InsnNode> instructions
    private final d methodData
    private Boolean methodIsVirtual
    private final MethodInfo mthInfo
    private Boolean noCode
    private final ClassNode parentClass
    private Region region
    private Int regsCount
    private ArgType retType
    private RegisterArg thisArg
    private List sVars = Collections.emptyList()
    private List exceptionHandlers = Collections.emptyList()
    private List loops = Collections.emptyList()

    constructor(ClassNode classNode, d dVar, Boolean z) {
        this.mthInfo = MethodInfo.fromDex(classNode.dex(), dVar.a())
        this.parentClass = classNode
        this.accFlags = AccessInfo(dVar.b(), AccessInfo.AFType.METHOD)
        this.noCode = dVar.c() == 0
        this.methodData = this.noCode ? null : dVar
        this.methodIsVirtual = z
    }

    private fun addJump(Array<InsnNode> insnNodeArr, Int i, Int i2) {
        insnNodeArr[i2].addAttr(AType.JUMP, JumpInfo(i, i2))
    }

    private fun initArguments(List list) {
        Int regCount
        if (!this.noCode) {
            Int regCount2 = this.regsCount
            Iterator it = list.iterator()
            while (true) {
                regCount = regCount2
                if (!it.hasNext()) {
                    break
                } else {
                    regCount2 = regCount - ((ArgType) it.next()).getRegCount()
                }
            }
        } else {
            regCount = 1
        }
        if (this.accFlags.isStatic()) {
            this.thisArg = null
        } else {
            TypeImmutableArg typeImmutableArgTypeImmutableReg = InsnArg.typeImmutableReg(regCount - 1, this.parentClass.getClassInfo().getType())
            typeImmutableArgTypeImmutableReg.markAsThis()
            this.thisArg = typeImmutableArgTypeImmutableReg
        }
        if (list.isEmpty()) {
            this.argsList = Collections.emptyList()
            return
        }
        this.argsList = ArrayList(list.size())
        Iterator it2 = list.iterator()
        while (it2.hasNext()) {
            ArgType argType = (ArgType) it2.next()
            this.argsList.add(InsnArg.typeImmutableReg(regCount, argType))
            regCount += argType.getRegCount()
        }
    }

    private fun initJumps() {
        Array<InsnNode> insnNodeArr = this.instructions
        for (Int i = 0; i < insnNodeArr.length; i++) {
            InsnNode insnNode = insnNodeArr[i]
            if (insnNode != null) {
                switch (insnNode.getType()) {
                    case SWITCH:
                        Array<Int> targets = ((SwitchNode) insnNode).getTargets()
                        for (Int i2 : targets) {
                            addJump(insnNodeArr, i, i2)
                        }
                        Int nextInsnOffset = InsnDecoder.getNextInsnOffset(insnNodeArr, i)
                        if (nextInsnOffset != -1) {
                            addJump(insnNodeArr, i, nextInsnOffset)
                            break
                        } else {
                            break
                        }
                    case IF:
                        Int nextInsnOffset2 = InsnDecoder.getNextInsnOffset(insnNodeArr, i)
                        if (nextInsnOffset2 != -1) {
                            addJump(insnNodeArr, i, nextInsnOffset2)
                        }
                        addJump(insnNodeArr, i, ((IfNode) insnNode).getTarget())
                        break
                    case GOTO:
                        addJump(insnNodeArr, i, ((GotoNode) insnNode).getTarget())
                        break
                }
            }
        }
    }

    private fun initMethodTypes() {
        if (parseSignature()) {
            return
        }
        this.retType = this.mthInfo.getReturnType()
        initArguments(this.mthInfo.getArgumentsTypes())
    }

    private fun initTryCatches(f fVar) {
        Array<InsnNode> insnNodeArr = this.instructions
        Array<g> gVarArrG = fVar.g()
        Array<h> hVarArrF = fVar.f()
        if (gVarArrG.length == 0 && hVarArrF.length == 0) {
            return
        }
        Int i = 0
        HashSet hashSet = HashSet()
        ArrayList<TryCatchBlock> arrayList = ArrayList(gVarArrG.length)
        for (g gVar : gVarArrG) {
            TryCatchBlock tryCatchBlock = TryCatchBlock()
            arrayList.add(tryCatchBlock)
            for (Int i2 = 0; i2 < gVar.b().length; i2++) {
                Int i3 = gVar.b()[i2]
                tryCatchBlock.addHandler(this, i3, ClassInfo.fromDex(this.parentClass.dex(), gVar.a()[i2]))
                hashSet.add(Integer.valueOf(i3))
                i++
            }
            Int iC = gVar.c()
            if (iC >= 0) {
                tryCatchBlock.addHandler(this, iC, null)
                hashSet.add(Integer.valueOf(iC))
                i++
            }
        }
        if (i > 0 && i != hashSet.size()) {
            for (TryCatchBlock tryCatchBlock2 : arrayList) {
                for (TryCatchBlock tryCatchBlock3 : arrayList) {
                    if (tryCatchBlock2 != tryCatchBlock3 && tryCatchBlock3.containsAllHandlers(tryCatchBlock2)) {
                        for (ExceptionHandler exceptionHandler : tryCatchBlock2.getHandlers()) {
                            tryCatchBlock3.removeHandler(this, exceptionHandler)
                            exceptionHandler.setTryBlock(tryCatchBlock2)
                        }
                    }
                }
            }
        }
        hashSet.clear()
        for (TryCatchBlock tryCatchBlock4 : arrayList) {
            for (ExceptionHandler exceptionHandler2 : tryCatchBlock4.getHandlers()) {
                insnNodeArr[exceptionHandler2.getHandleOffset()].addAttr(ExcHandlerAttr(tryCatchBlock4, exceptionHandler2))
            }
        }
        for (h hVar : hVarArrF) {
            TryCatchBlock tryCatchBlock5 = (TryCatchBlock) arrayList.get(hVar.c())
            Int iA = hVar.a()
            Int iB = (hVar.b() + iA) - 1
            InsnNode insnNode = insnNodeArr[iA]
            insnNode.add(AFlag.TRY_ENTER)
            while (iA <= iB && iA >= 0) {
                insnNode = insnNodeArr[iA]
                tryCatchBlock5.addInsn(insnNode)
                iA = InsnDecoder.getNextInsnOffset(insnNodeArr, iA)
            }
            if (insnNodeArr[iB] != null) {
                insnNodeArr[iB].add(AFlag.TRY_LEAVE)
            } else {
                insnNode.add(AFlag.TRY_LEAVE)
            }
        }
    }

    private fun parseSignature() {
        SignatureParser signatureParserFromNode = SignatureParser.fromNode(this)
        if (signatureParserFromNode == null) {
            return false
        }
        try {
            this.genericMap = signatureParserFromNode.consumeGenericMap()
            List listConsumeMethodArgs = signatureParserFromNode.consumeMethodArgs()
            this.retType = signatureParserFromNode.consumeType()
            List argumentsTypes = this.mthInfo.getArgumentsTypes()
            if (listConsumeMethodArgs.size() != argumentsTypes.size()) {
                if (listConsumeMethodArgs.isEmpty()) {
                    return false
                }
                if (!this.mthInfo.isConstructor()) {
                    LOG.b("Wrong signature parse result: {} -> {}, not generic version: {}", signatureParserFromNode, listConsumeMethodArgs, argumentsTypes)
                    return false
                }
                if (getParentClass().getAccessFlags().isEnum()) {
                    listConsumeMethodArgs.add(0, argumentsTypes.get(0))
                    listConsumeMethodArgs.add(1, argumentsTypes.get(1))
                } else {
                    listConsumeMethodArgs.add(0, argumentsTypes.get(0))
                }
                if (listConsumeMethodArgs.size() != argumentsTypes.size()) {
                    return false
                }
            }
            initArguments(listConsumeMethodArgs)
            return true
        } catch (JadxRuntimeException e) {
            LOG.c("Method signature parse error: {}", this, e)
            return false
        }
    }

    fun addExceptionHandler(ExceptionHandler exceptionHandler) {
        if (this.exceptionHandlers.isEmpty()) {
            this.exceptionHandlers = ArrayList(2)
        } else {
            for (ExceptionHandler exceptionHandler2 : this.exceptionHandlers) {
                if (exceptionHandler2 == exceptionHandler || exceptionHandler2.getHandleOffset() == exceptionHandler.getHandleOffset()) {
                    return exceptionHandler2
                }
            }
        }
        this.exceptionHandlers.add(exceptionHandler)
        return exceptionHandler
    }

    fun addExitBlock(BlockNode blockNode) {
        this.exitBlocks.add(blockNode)
    }

    fun checkInstructions() {
        ArrayList arrayList = ArrayList()
        for (InsnNode insnNode : this.instructions) {
            if (insnNode != null) {
                arrayList.clear()
                RegisterArg result = insnNode.getResult()
                if (result != null) {
                    arrayList.add(result)
                }
                insnNode.getRegisterArgs(arrayList)
                Int size = arrayList.size()
                for (Int i = 0; i < size; i++) {
                    if (((RegisterArg) arrayList.get(i)).getRegNum() >= this.regsCount) {
                        throw JadxRuntimeException("Incorrect register number in instruction: " + insnNode + ", expected to be less than " + this.regsCount)
                    }
                }
            }
        }
    }

    fun dex() {
        return this.parentClass.dex()
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return this.mthInfo.equals(((MethodNode) obj).mthInfo)
    }

    fun finishBasicBlocks() {
        ((ArrayList) this.blocks).trimToSize()
        ((ArrayList) this.exitBlocks).trimToSize()
        this.blocks = Collections.unmodifiableList(this.blocks)
        this.exitBlocks = Collections.unmodifiableList(this.exitBlocks)
        Iterator it = this.blocks.iterator()
        while (it.hasNext()) {
            ((BlockNode) it.next()).lock()
        }
    }

    fun getAccessFlags() {
        return this.accFlags
    }

    fun getAlias() {
        return this.mthInfo.getAlias()
    }

    fun getAllLoopsForBlock(BlockNode blockNode) {
        if (this.loops.isEmpty()) {
            return Collections.emptyList()
        }
        ArrayList arrayList = ArrayList(this.loops.size())
        for (LoopInfo loopInfo : this.loops) {
            if (loopInfo.getLoopBlocks().contains(blockNode)) {
                arrayList.add(loopInfo)
            }
        }
        return arrayList
    }

    fun getArguments(Boolean z) {
        if (!z || this.thisArg == null) {
            return this.argsList
        }
        ArrayList arrayList = ArrayList(this.argsList.size() + 1)
        arrayList.add(this.thisArg)
        arrayList.addAll(this.argsList)
        return arrayList
    }

    fun getBasicBlocks() {
        return this.blocks
    }

    fun getCodeSize() {
        return this.codeSize
    }

    fun getDebugInfoOffset() {
        return this.debugInfoOffset
    }

    fun getEnterBlock() {
        return this.enterBlock
    }

    fun getExceptionHandlers() {
        return this.exceptionHandlers
    }

    fun getExceptionHandlersCount() {
        return this.exceptionHandlers.size()
    }

    fun getExitBlocks() {
        return this.exitBlocks
    }

    fun getGenericMap() {
        return this.genericMap
    }

    public Array<InsnNode> getInstructions() {
        return this.instructions
    }

    fun getLoopForBlock(BlockNode blockNode) {
        if (this.loops.isEmpty()) {
            return null
        }
        for (LoopInfo loopInfo : this.loops) {
            if (loopInfo.getLoopBlocks().contains(blockNode)) {
                return loopInfo
            }
        }
        return null
    }

    fun getLoops() {
        return this.loops
    }

    fun getLoopsCount() {
        return this.loops.size()
    }

    fun getMethodInfo() {
        return this.mthInfo
    }

    fun getName() {
        return this.mthInfo.getName()
    }

    fun getNextSVarVersion(Int i) {
        Int iMax = -1
        Iterator it = this.sVars.iterator()
        while (true) {
            Int i2 = iMax
            if (!it.hasNext()) {
                return i2 + 1
            }
            SSAVar sSAVar = (SSAVar) it.next()
            iMax = sSAVar.getRegNum() == i ? Math.max(i2, sSAVar.getVersion()) : i2
        }
    }

    fun getParentClass() {
        return this.parentClass
    }

    fun getRegion() {
        return this.region
    }

    fun getRegsCount() {
        return this.regsCount
    }

    fun getReturnType() {
        return this.retType
    }

    fun getSVars() {
        return this.sVars
    }

    fun getThisArg() {
        return this.thisArg
    }

    fun hashCode() {
        return this.mthInfo.hashCode()
    }

    fun initBasicBlocks() {
        this.blocks = ArrayList()
        this.exitBlocks = ArrayList(1)
    }

    fun isArgsOverload() {
        Int size = this.mthInfo.getArgumentsTypes().size()
        if (size == 0) {
            return false
        }
        String name = getName()
        for (MethodNode methodNode : this.parentClass.getMethods()) {
            MethodInfo methodInfo = methodNode.mthInfo
            if (this != methodNode && methodInfo.getArgumentsTypes().size() == size && methodInfo.getName().equals(name)) {
                return true
            }
        }
        return false
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun isDefaultConstructor() {
        /*
            r4 = this
            r1 = 1
            r2 = 0
            jadx.core.dex.info.AccessInfo r0 = r4.accFlags
            Boolean r0 = r0.isConstructor()
            if (r0 == 0) goto L64
            jadx.core.dex.info.MethodInfo r0 = r4.mthInfo
            Boolean r0 = r0.isConstructor()
            if (r0 == 0) goto L64
            jadx.core.dex.nodes.ClassNode r0 = r4.parentClass
            jadx.core.dex.info.ClassInfo r0 = r0.getClassInfo()
            Boolean r0 = r0.isInner()
            if (r0 == 0) goto L65
            jadx.core.dex.nodes.ClassNode r0 = r4.parentClass
            jadx.core.dex.info.AccessInfo r0 = r0.getAccessFlags()
            Boolean r0 = r0.isStatic()
            if (r0 != 0) goto L65
            jadx.core.dex.nodes.ClassNode r0 = r4.parentClass
            jadx.core.dex.nodes.ClassNode r3 = r0.getParentClass()
            java.util.List r0 = r4.argsList
            if (r0 == 0) goto L65
            java.util.List r0 = r4.argsList
            Boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L65
            java.util.List r0 = r4.argsList
            java.lang.Object r0 = r0.get(r2)
            jadx.core.dex.instructions.args.RegisterArg r0 = (jadx.core.dex.instructions.args.RegisterArg) r0
            jadx.core.dex.instructions.args.ArgType r0 = r0.getType()
            jadx.core.dex.info.ClassInfo r3 = r3.getClassInfo()
            jadx.core.dex.instructions.args.ArgType r3 = r3.getType()
            Boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L65
            r0 = r1
        L57:
            java.util.List r3 = r4.argsList
            if (r3 == 0) goto L63
            java.util.List r3 = r4.argsList
            Int r3 = r3.size()
            if (r3 != r0) goto L64
        L63:
            r2 = r1
        L64:
            return r2
        L65:
            r0 = r2
            goto L57
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.core.dex.nodes.MethodNode.isDefaultConstructor():Boolean")
    }

    fun isNoCode() {
        return this.noCode
    }

    fun isNoExceptionHandlers() {
        return this.exceptionHandlers.isEmpty()
    }

    fun isVirtual() {
        return this.methodIsVirtual
    }

    @Override // jadx.core.dex.nodes.ILoadable
    fun load() {
        try {
            if (this.noCode) {
                this.regsCount = 0
                this.codeSize = 0
                initMethodTypes()
                return
            }
            f code = this.parentClass.dex().readCode(this.methodData)
            this.regsCount = code.a()
            initMethodTypes()
            InsnDecoder insnDecoder = InsnDecoder(this)
            insnDecoder.decodeInsns(code)
            this.instructions = insnDecoder.process()
            this.codeSize = this.instructions.length
            initTryCatches(code)
            initJumps()
            this.debugInfoOffset = code.d()
        } catch (Exception e) {
            if (!this.noCode) {
                this.noCode = true
                load()
                this.noCode = false
            }
            throw DecodeException(this, "Load method exception", e)
        }
    }

    fun makeNewSVar(Int i, Int i2, RegisterArg registerArg) {
        SSAVar sSAVar = SSAVar(i, i2, registerArg)
        if (this.sVars.isEmpty()) {
            this.sVars = ArrayList()
        }
        this.sVars.add(sSAVar)
        return sSAVar
    }

    fun registerLoop(LoopInfo loopInfo) {
        if (this.loops.isEmpty()) {
            this.loops = ArrayList(5)
        }
        loopInfo.setId(this.loops.size())
        this.loops.add(loopInfo)
    }

    fun removeFirstArgument() {
        add(AFlag.SKIP_FIRST_ARG)
        return (RegisterArg) this.argsList.remove(0)
    }

    fun removeSVar(SSAVar sSAVar) {
        this.sVars.remove(sSAVar)
    }

    fun setEnterBlock(BlockNode blockNode) {
        this.enterBlock = blockNode
    }

    fun setRegion(Region region) {
        this.region = region
    }

    fun toString() {
        return this.parentClass + Deobfuscator.CLASS_NAME_SEPARATOR + this.mthInfo.getName() + "(" + Utils.listToString(this.mthInfo.getArgumentsTypes()) + "):" + this.retType
    }

    @Override // jadx.core.dex.nodes.ILoadable
    fun unload() {
        if (this.noCode) {
            return
        }
        this.instructions = null
        this.blocks = null
        this.exitBlocks = null
        this.exceptionHandlers.clear()
    }

    fun unloadInsnArr() {
        this.instructions = null
    }
}
