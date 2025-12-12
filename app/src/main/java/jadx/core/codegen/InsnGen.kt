package jadx.core.codegen

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.FieldReplaceAttr
import jadx.core.dex.attributes.nodes.LoopLabelAttr
import jadx.core.dex.attributes.nodes.MethodInlineAttr
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.ArithNode
import jadx.core.dex.instructions.ArithOp
import jadx.core.dex.instructions.ConstClassNode
import jadx.core.dex.instructions.ConstStringNode
import jadx.core.dex.instructions.FillArrayNode
import jadx.core.dex.instructions.FilledNewArrayNode
import jadx.core.dex.instructions.GotoNode
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.IndexInsnNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.NewArrayNode
import jadx.core.dex.instructions.SwitchNode
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.FieldArg
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.Named
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.instructions.mods.TernaryInsn
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.FieldNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.RootNode
import jadx.core.utils.ErrorsCounter
import jadx.core.utils.RegionUtils
import jadx.core.utils.StringUtils
import jadx.core.utils.exceptions.CodegenException
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.Arrays
import java.util.EnumSet
import java.util.Iterator
import java.util.List
import java.util.Set

class InsnGen {
    protected final Boolean fallback
    protected final MethodGen mgen
    protected final MethodNode mth
    protected final RootNode root

    public enum Flags {
        BODY_ONLY,
        BODY_ONLY_NOWRAP,
        INLINE
    }

    constructor(MethodGen methodGen, Boolean z) {
        this.mgen = methodGen
        this.mth = methodGen.getMethodNode()
        this.root = this.mth.dex().root()
        this.fallback = z
    }

    private fun fallbackOnlyInsn(InsnNode insnNode) throws CodegenException {
        if (!this.fallback) {
            throw CodegenException(insnNode.getType() + " can be used only in fallback mode")
        }
    }

    private fun filledNewArray(FilledNewArrayNode filledNewArrayNode, CodeWriter codeWriter) throws CodegenException {
        codeWriter.add("new ")
        useType(codeWriter, filledNewArrayNode.getArrayType())
        codeWriter.add('{')
        Int argsCount = filledNewArrayNode.getArgsCount()
        for (Int i = 0; i < argsCount; i++) {
            addArg(codeWriter, filledNewArrayNode.getArg(i), false)
            if (i + 1 < argsCount) {
                codeWriter.add(", ")
            }
        }
        codeWriter.add('}')
    }

    private fun getCallMthArg(MethodNode methodNode, Int i) {
        List arguments
        if (methodNode == null || (arguments = methodNode.getArguments(false)) == null || i >= arguments.size()) {
            return null
        }
        return (RegisterArg) arguments.get(i)
    }

    private fun inlineAnonymousConstr(CodeWriter codeWriter, ClassNode classNode, ConstructorInsn constructorInsn) {
        if (classNode.contains(AFlag.DONT_GENERATE)) {
            codeWriter.add("/* anonymous class already generated */")
            ErrorsCounter.methodError(this.mth, "Anonymous class already generated: " + classNode)
            return
        }
        ArgType superClass = classNode.getInterfaces().size() == 1 ? (ArgType) classNode.getInterfaces().get(0) : classNode.getSuperClass()
        classNode.add(AFlag.DONT_GENERATE)
        MethodNode defaultConstructor = classNode.getDefaultConstructor()
        if (defaultConstructor != null) {
            if (RegionUtils.notEmpty(defaultConstructor.getRegion())) {
                defaultConstructor.add(AFlag.ANONYMOUS_CONSTRUCTOR)
            } else {
                defaultConstructor.add(AFlag.DONT_GENERATE)
            }
        }
        codeWriter.add("new ")
        if (superClass == null) {
            codeWriter.add("Object")
        } else {
            useClass(codeWriter, superClass)
        }
        generateMethodArguments(codeWriter, constructorInsn, 0, this.mth.dex().resolveMethod(constructorInsn.getCallMth()))
        codeWriter.add(' ')
        ClassGen(classNode, this.mgen.getClassGen().getParentGen()).addClassBody(codeWriter)
    }

    private fun inlineMethod(MethodNode methodNode, InvokeNode invokeNode, CodeWriter codeWriter) {
        InsnArg insnArg
        MethodInlineAttr methodInlineAttr = (MethodInlineAttr) methodNode.get(AType.METHOD_INLINE)
        if (methodInlineAttr == null) {
            return false
        }
        InsnNode insn = methodInlineAttr.getInsn()
        if (methodNode.getMethodInfo().getArgumentsTypes().isEmpty()) {
            makeInsn(insn, codeWriter, Flags.BODY_ONLY)
        } else {
            Array<InsnArg> insnArgArr = new InsnArg[methodNode.getRegsCount()]
            List arguments = methodNode.getArguments(true)
            for (Int i = 0; i < arguments.size(); i++) {
                insnArgArr[((RegisterArg) arguments.get(i)).getRegNum()] = invokeNode.getArg(i)
            }
            InsnNode insnNodeCopy = insn.copy()
            ArrayList<RegisterArg> arrayList = ArrayList()
            insnNodeCopy.getRegisterArgs(arrayList)
            for (RegisterArg registerArg : arrayList) {
                Int regNum = registerArg.getRegNum()
                if (regNum < insnArgArr.length && (insnArg = insnArgArr[regNum]) != null) {
                    insnNodeCopy.replaceArg(registerArg, insnArg)
                }
            }
            makeInsn(insnNodeCopy, codeWriter, Flags.BODY_ONLY)
        }
        return true
    }

    private fun instanceField(CodeWriter codeWriter, FieldInfo fieldInfo, InsnArg insnArg) {
        FieldReplaceAttr fieldReplaceAttr
        ClassNode parentClass = this.mth.getParentClass()
        ClassNode classNode = parentClass
        FieldNode fieldNodeSearchField = parentClass.searchField(fieldInfo)
        while (fieldNodeSearchField == null && classNode.getParentClass() != classNode && classNode.getParentClass() != null) {
            ClassNode parentClass2 = classNode.getParentClass()
            classNode = parentClass2
            fieldNodeSearchField = parentClass2.searchField(fieldInfo)
        }
        if (fieldNodeSearchField == null || (fieldReplaceAttr = (FieldReplaceAttr) fieldNodeSearchField.get(AType.FIELD_REPLACE)) == null) {
            addArgDot(codeWriter, insnArg)
            if (fieldNodeSearchField != null) {
                codeWriter.attachAnnotation(fieldNodeSearchField)
            }
            codeWriter.add(fieldInfo.getAlias())
            return
        }
        switch (fieldReplaceAttr.getReplaceType()) {
            case CLASS_INSTANCE:
                useClass(codeWriter, fieldReplaceAttr.getClsRef())
                codeWriter.add(".this")
                break
            case VAR:
                addArg(codeWriter, fieldReplaceAttr.getVarRef())
                break
        }
    }

    private fun isFallback() {
        return this.fallback
    }

    private fun lit(LiteralArg literalArg) {
        return TypeGen.literalToString(literalArg.getLiteral(), literalArg.getType())
    }

    private fun makeArith(ArithNode arithNode, CodeWriter codeWriter, Set set) throws CodegenException {
        if (arithNode.contains(AFlag.ARITH_ONEARG)) {
            makeArithOneArg(arithNode, codeWriter)
            return
        }
        Boolean z = set.contains(Flags.BODY_ONLY) && !arithNode.contains(AFlag.DONT_WRAP)
        if (z) {
            codeWriter.add('(')
        }
        addArg(codeWriter, arithNode.getArg(0))
        codeWriter.add(' ')
        codeWriter.add(arithNode.getOp().getSymbol())
        codeWriter.add(' ')
        addArg(codeWriter, arithNode.getArg(1))
        if (z) {
            codeWriter.add(')')
        }
    }

    private fun makeArithOneArg(ArithNode arithNode, CodeWriter codeWriter) throws CodegenException {
        ArithOp op = arithNode.getOp()
        InsnArg arg = arithNode.getArg(1)
        if (arg.isLiteral() && (op == ArithOp.ADD || op == ArithOp.SUB)) {
            LiteralArg literalArg = (LiteralArg) arg
            if (literalArg.isInteger() && literalArg.getLiteral() == 1) {
                assignVar(codeWriter, arithNode)
                String symbol = op.getSymbol()
                codeWriter.add(symbol).add(symbol)
                return
            }
        }
        assignVar(codeWriter, arithNode)
        codeWriter.add(' ').add(op.getSymbol()).add("= ")
        addArg(codeWriter, arg, false)
    }

    private fun makeConstructor(ConstructorInsn constructorInsn, CodeWriter codeWriter) {
        ClassNode classNodeResolveClass = this.mth.dex().resolveClass(constructorInsn.getClassType())
        if (classNodeResolveClass != null && classNodeResolveClass.contains(AFlag.ANONYMOUS_CLASS) && !this.fallback) {
            inlineAnonymousConstr(codeWriter, classNodeResolveClass, constructorInsn)
            return
        }
        if (constructorInsn.isSelf()) {
            throw JadxRuntimeException("Constructor 'self' invoke must be removed!")
        }
        if (constructorInsn.isSuper()) {
            codeWriter.add("super")
        } else if (constructorInsn.isThis()) {
            codeWriter.add("this")
        } else {
            codeWriter.add("new ")
            useClass(codeWriter, constructorInsn.getClassType())
        }
        generateMethodArguments(codeWriter, constructorInsn, 0, this.mth.dex().resolveMethod(constructorInsn.getCallMth()))
    }

    private fun makeInsnBody(CodeWriter codeWriter, InsnNode insnNode, Set set) throws CodegenException {
        Int i = 0
        switch (insnNode.getType()) {
            case CONST_STR:
                codeWriter.add(StringUtils.unescapeString(((ConstStringNode) insnNode).getString()))
                return
            case CONST_CLASS:
                useType(codeWriter, ((ConstClassNode) insnNode).getClsType())
                codeWriter.add(".class")
                return
            case CONST:
                codeWriter.add(lit((LiteralArg) insnNode.getArg(0)))
                return
            case MOVE:
                addArg(codeWriter, insnNode.getArg(0), false)
                return
            case CHECK_CAST:
            case CAST:
                Boolean zContains = set.contains(Flags.BODY_ONLY)
                if (zContains) {
                    codeWriter.add('(')
                }
                codeWriter.add('(')
                useType(codeWriter, (ArgType) ((IndexInsnNode) insnNode).getIndex())
                codeWriter.add(") ")
                addArg(codeWriter, insnNode.getArg(0), true)
                if (zContains) {
                    codeWriter.add(')')
                    return
                }
                return
            case ARITH:
                makeArith((ArithNode) insnNode, codeWriter, set)
                return
            case NEG:
                Boolean zContains2 = set.contains(Flags.BODY_ONLY)
                if (zContains2) {
                    codeWriter.add('(')
                }
                codeWriter.add('-')
                addArg(codeWriter, insnNode.getArg(0))
                if (zContains2) {
                    codeWriter.add(')')
                    return
                }
                return
            case RETURN:
                if (insnNode.getArgsCount() == 0) {
                    codeWriter.add("return")
                    return
                } else {
                    codeWriter.add("return ")
                    addArg(codeWriter, insnNode.getArg(0), false)
                    return
                }
            case BREAK:
                codeWriter.add("break")
                LoopLabelAttr loopLabelAttr = (LoopLabelAttr) insnNode.get(AType.LOOP_LABEL)
                if (loopLabelAttr != null) {
                    codeWriter.add(' ').add(this.mgen.getNameGen().getLoopLabel(loopLabelAttr))
                    return
                }
                return
            case CONTINUE:
                codeWriter.add("continue")
                return
            case THROW:
                codeWriter.add("throw ")
                addArg(codeWriter, insnNode.getArg(0), true)
                return
            case CMP_L:
            case CMP_G:
                codeWriter.add('(')
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(" > ")
                addArg(codeWriter, insnNode.getArg(1))
                codeWriter.add(" ? 1 : (")
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(" == ")
                addArg(codeWriter, insnNode.getArg(1))
                codeWriter.add(" ? 0 : -1))")
                return
            case INSTANCE_OF:
                Boolean zContains3 = set.contains(Flags.BODY_ONLY)
                if (zContains3) {
                    codeWriter.add('(')
                }
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(" instanceof ")
                useType(codeWriter, (ArgType) ((IndexInsnNode) insnNode).getIndex())
                if (zContains3) {
                    codeWriter.add(')')
                    return
                }
                return
            case CONSTRUCTOR:
                makeConstructor((ConstructorInsn) insnNode, codeWriter)
                return
            case INVOKE:
                makeInvoke((InvokeNode) insnNode, codeWriter)
                return
            case NEW_ARRAY:
                ArgType arrayType = ((NewArrayNode) insnNode).getArrayType()
                codeWriter.add("new ")
                useType(codeWriter, arrayType.getArrayRootElement())
                codeWriter.add('[')
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(']')
                Int arrayDimension = arrayType.getArrayDimension()
                while (i < arrayDimension - 1) {
                    codeWriter.add("[]")
                    i++
                }
                return
            case ARRAY_LENGTH:
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(".length")
                return
            case FILLED_NEW_ARRAY:
                filledNewArray((FilledNewArrayNode) insnNode, codeWriter)
                return
            case AGET:
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add('[')
                addArg(codeWriter, insnNode.getArg(1), false)
                codeWriter.add(']')
                return
            case APUT:
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add('[')
                addArg(codeWriter, insnNode.getArg(1), false)
                codeWriter.add("] = ")
                addArg(codeWriter, insnNode.getArg(2), false)
                return
            case IGET:
                instanceField(codeWriter, (FieldInfo) ((IndexInsnNode) insnNode).getIndex(), insnNode.getArg(0))
                return
            case IPUT:
                instanceField(codeWriter, (FieldInfo) ((IndexInsnNode) insnNode).getIndex(), insnNode.getArg(1))
                codeWriter.add(" = ")
                addArg(codeWriter, insnNode.getArg(0), false)
                return
            case SGET:
                staticField(codeWriter, (FieldInfo) ((IndexInsnNode) insnNode).getIndex())
                return
            case SPUT:
                staticField(codeWriter, (FieldInfo) ((IndexInsnNode) insnNode).getIndex())
                codeWriter.add(" = ")
                addArg(codeWriter, insnNode.getArg(0), false)
                return
            case STR_CONCAT:
                Boolean zContains4 = set.contains(Flags.BODY_ONLY)
                if (zContains4) {
                    codeWriter.add('(')
                }
                Iterator it = insnNode.getArguments().iterator()
                while (it.hasNext()) {
                    addArg(codeWriter, (InsnArg) it.next())
                    if (it.hasNext()) {
                        codeWriter.add(" + ")
                    }
                }
                if (zContains4) {
                    codeWriter.add(')')
                    return
                }
                return
            case MONITOR_ENTER:
                if (isFallback()) {
                    codeWriter.add("monitor-enter(")
                    addArg(codeWriter, insnNode.getArg(0))
                    codeWriter.add(')')
                    return
                }
                return
            case MONITOR_EXIT:
                if (isFallback()) {
                    codeWriter.add("monitor-exit(")
                    addArg(codeWriter, insnNode.getArg(0))
                    codeWriter.add(')')
                    return
                }
                return
            case TERNARY:
                makeTernary((TernaryInsn) insnNode, codeWriter, set)
                return
            case ONE_ARG:
                addArg(codeWriter, insnNode.getArg(0))
                return
            case IF:
                fallbackOnlyInsn(insnNode)
                IfNode ifNode = (IfNode) insnNode
                codeWriter.add("if (")
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(' ')
                codeWriter.add(ifNode.getOp().getSymbol()).add(' ')
                addArg(codeWriter, insnNode.getArg(1))
                codeWriter.add(") goto ").add(MethodGen.getLabelName(ifNode.getTarget()))
                return
            case GOTO:
                fallbackOnlyInsn(insnNode)
                codeWriter.add("goto ").add(MethodGen.getLabelName(((GotoNode) insnNode).getTarget()))
                return
            case MOVE_EXCEPTION:
                fallbackOnlyInsn(insnNode)
                codeWriter.add("move-exception")
                return
            case SWITCH:
                fallbackOnlyInsn(insnNode)
                SwitchNode switchNode = (SwitchNode) insnNode
                codeWriter.add("switch(")
                addArg(codeWriter, insnNode.getArg(0))
                codeWriter.add(") {")
                codeWriter.incIndent()
                while (i < switchNode.getCasesCount()) {
                    codeWriter.startLine("case ").add(switchNode.getKeys()[i].toString()).add(": goto ")
                    codeWriter.add(MethodGen.getLabelName(switchNode.getTargets()[i])).add(';')
                    i++
                }
                codeWriter.startLine("default: goto ")
                codeWriter.add(MethodGen.getLabelName(switchNode.getDefaultCaseOffset())).add(';')
                codeWriter.decIndent()
                codeWriter.startLine('}')
                return
            case FILL_ARRAY:
                fallbackOnlyInsn(insnNode)
                Object data = ((FillArrayNode) insnNode).getData()
                codeWriter.add('{').add((data is Array<Int> ? Arrays.toString((Array<Int>) data) : data is Array<Short> ? Arrays.toString((Array<Short>) data) : data is Array<Byte> ? Arrays.toString((Array<Byte>) data) : data is Array<Long> ? Arrays.toString((Array<Long>) data) : "?").substring(1, r0.length() - 1)).add('}')
                return
            case NEW_INSTANCE:
                fallbackOnlyInsn(insnNode)
                codeWriter.add("new ").add(insnNode.getResult().getType().toString())
                return
            case PHI:
            case MERGE:
                fallbackOnlyInsn(insnNode)
                codeWriter.add(insnNode.getType().toString()).add("(")
                Iterator it2 = insnNode.getArguments().iterator()
                while (it2.hasNext()) {
                    addArg(codeWriter, (InsnArg) it2.next())
                    codeWriter.add(' ')
                }
                codeWriter.add(")")
                return
            default:
                throw CodegenException(this.mth, "Unknown instruction: " + insnNode.getType())
        }
    }

    private fun makeInvoke(InvokeNode invokeNode, CodeWriter codeWriter) {
        Int i = 1
        MethodInfo callMth = invokeNode.getCallMth()
        MethodNode methodNodeDeepResolveMethod = this.mth.dex().deepResolveMethod(callMth)
        if (methodNodeDeepResolveMethod != null) {
            if (inlineMethod(methodNodeDeepResolveMethod, invokeNode, codeWriter)) {
                return
            } else {
                callMth = methodNodeDeepResolveMethod.getMethodInfo()
            }
        }
        switch (invokeNode.getInvokeType()) {
            case DIRECT:
            case VIRTUAL:
            case INTERFACE:
                InsnArg arg = invokeNode.getArg(0)
                if (!arg.isThis()) {
                    addArgDot(codeWriter, arg)
                    break
                }
                break
            case SUPER:
                codeWriter.add("super").add('.')
                break
            case STATIC:
                ClassInfo alias = this.mth.getParentClass().getAlias()
                ClassInfo declClass = callMth.getDeclClass()
                if (!alias.equals(declClass)) {
                    useClass(codeWriter, declClass)
                    codeWriter.add('.')
                }
            default:
                i = 0
                break
        }
        if (methodNodeDeepResolveMethod != null) {
            codeWriter.attachAnnotation(methodNodeDeepResolveMethod)
        }
        codeWriter.add(callMth.getAlias())
        generateMethodArguments(codeWriter, invokeNode, i, methodNodeDeepResolveMethod)
    }

    fun makeStaticFieldAccess(CodeWriter codeWriter, FieldInfo fieldInfo, ClassGen classGen) {
        ClassInfo declClass = fieldInfo.getDeclClass()
        if (!classGen.getClassNode().getClassInfo().equals(declClass)) {
            ClassInfo parentClass = declClass.getParentClass()
            if (parentClass == null || !parentClass.getShortName().equals("R")) {
                classGen.useClass(codeWriter, declClass)
            } else {
                classGen.useClass(codeWriter, parentClass)
                codeWriter.add('.')
                codeWriter.add(declClass.getAlias().getShortName())
            }
            codeWriter.add('.')
        }
        FieldNode fieldNodeResolveField = classGen.getClassNode().dex().resolveField(fieldInfo)
        if (fieldNodeResolveField != null) {
            codeWriter.attachAnnotation(fieldNodeResolveField)
        }
        codeWriter.add(fieldInfo.getAlias())
    }

    private fun makeTernary(TernaryInsn ternaryInsn, CodeWriter codeWriter, Set set) throws CodegenException {
        Boolean zContains = set.contains(Flags.BODY_ONLY)
        if (zContains) {
            codeWriter.add('(')
        }
        InsnArg arg = ternaryInsn.getArg(0)
        InsnArg arg2 = ternaryInsn.getArg(1)
        ConditionGen conditionGen = ConditionGen(this)
        if (arg.equals(LiteralArg.TRUE) && arg2.equals(LiteralArg.FALSE)) {
            conditionGen.add(codeWriter, ternaryInsn.getCondition())
        } else {
            conditionGen.wrap(codeWriter, ternaryInsn.getCondition())
            codeWriter.add(" ? ")
            addArg(codeWriter, arg, false)
            codeWriter.add(" : ")
            addArg(codeWriter, arg2, false)
        }
        if (zContains) {
            codeWriter.add(')')
        }
    }

    private fun processOverloadedArg(CodeWriter codeWriter, MethodNode methodNode, InsnArg insnArg, Int i) {
        ArgType argType = (ArgType) methodNode.getMethodInfo().getArgumentsTypes().get(i)
        if (insnArg.getType().equals(argType)) {
            return false
        }
        codeWriter.add('(')
        useType(codeWriter, argType)
        codeWriter.add(") ")
        return true
    }

    private fun processVarArg(CodeWriter codeWriter, MethodNode methodNode, InsnArg insnArg) throws CodegenException {
        if (methodNode == null || !methodNode.getAccessFlags().isVarArgs() || !insnArg.getType().isArray() || !insnArg.isInsnWrap()) {
            return false
        }
        InsnNode wrapInsn = ((InsnWrapArg) insnArg).getWrapInsn()
        if (wrapInsn.getType() != InsnType.FILLED_NEW_ARRAY) {
            return false
        }
        Int argsCount = wrapInsn.getArgsCount()
        for (Int i = 0; i < argsCount; i++) {
            addArg(codeWriter, wrapInsn.getArg(i), false)
            if (i < argsCount - 1) {
                codeWriter.add(", ")
            }
        }
        return true
    }

    fun addArg(CodeWriter codeWriter, InsnArg insnArg) {
        addArg(codeWriter, insnArg, true)
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun addArg(CodeWriter codeWriter, InsnArg insnArg, Boolean z) throws CodegenException {
        if (insnArg.isRegister()) {
            codeWriter.add(this.mgen.getNameGen().useArg((RegisterArg) insnArg))
            return
        }
        if (insnArg.isLiteral()) {
            codeWriter.add(lit((LiteralArg) insnArg))
            return
        }
        if (insnArg.isInsnWrap()) {
            makeInsn(((InsnWrapArg) insnArg).getWrapInsn(), codeWriter, z ? Flags.BODY_ONLY : Flags.BODY_ONLY_NOWRAP)
            return
        }
        if (insnArg.isNamed()) {
            codeWriter.add(((Named) insnArg).getName())
            return
        }
        if (!insnArg.isField()) {
            throw CodegenException("Unknown arg type " + insnArg)
        }
        FieldArg fieldArg = (FieldArg) insnArg
        if (fieldArg.isStatic()) {
            staticField(codeWriter, fieldArg.getField())
        } else {
            instanceField(codeWriter, fieldArg.getField(), fieldArg.getInstanceArg())
        }
    }

    fun addArgDot(CodeWriter codeWriter, InsnArg insnArg) {
        Int iBufLength = codeWriter.bufLength()
        addArg(codeWriter, insnArg, true)
        if (iBufLength != codeWriter.bufLength()) {
            codeWriter.add('.')
        }
    }

    fun assignVar(CodeWriter codeWriter, InsnNode insnNode) throws CodegenException {
        RegisterArg result = insnNode.getResult()
        if (insnNode.contains(AFlag.DECLARE_VAR)) {
            declareVar(codeWriter, result)
        } else {
            addArg(codeWriter, result, false)
        }
    }

    fun declareVar(CodeWriter codeWriter, RegisterArg registerArg) {
        if (registerArg.getSVar().contains(AFlag.FINAL)) {
            codeWriter.add("final ")
        }
        useType(codeWriter, registerArg.getType())
        codeWriter.add(' ')
        codeWriter.add(this.mgen.getNameGen().assignArg(registerArg))
    }

    Unit generateMethodArguments(CodeWriter codeWriter, InsnNode insnNode, Int i, MethodNode methodNode) {
        RegisterArg callMthArg
        Int i2 = (methodNode == null || !methodNode.contains(AFlag.SKIP_FIRST_ARG)) ? i : i + 1
        Int argsCount = insnNode.getArgsCount()
        codeWriter.add('(')
        if (i2 < argsCount) {
            Boolean z = methodNode != null && methodNode.isArgsOverload()
            Boolean z2 = true
            for (Int i3 = i2; i3 < argsCount; i3++) {
                InsnArg arg = insnNode.getArg(i3)
                if (!arg.contains(AFlag.SKIP_ARG) && ((callMthArg = getCallMthArg(methodNode, i3 - i)) == null || !callMthArg.contains(AFlag.SKIP_ARG))) {
                    if (!z2) {
                        codeWriter.add(", ")
                    }
                    if ((z && processOverloadedArg(codeWriter, methodNode, arg, i3 - i)) || i3 != argsCount - 1 || !processVarArg(codeWriter, methodNode, arg)) {
                        addArg(codeWriter, arg, false)
                        z2 = false
                    }
                }
            }
        }
        codeWriter.add(')')
    }

    fun makeInsn(InsnNode insnNode, CodeWriter codeWriter) {
        return makeInsn(insnNode, codeWriter, null)
    }

    protected fun makeInsn(InsnNode insnNode, CodeWriter codeWriter, Flags flags) {
        try {
            EnumSet enumSetNoneOf = EnumSet.noneOf(Flags.class)
            if (flags == Flags.BODY_ONLY || flags == Flags.BODY_ONLY_NOWRAP) {
                enumSetNoneOf.add(flags)
                makeInsnBody(codeWriter, insnNode, enumSetNoneOf)
                return true
            }
            if (flags != Flags.INLINE) {
                codeWriter.startLineWithNum(insnNode.getSourceLine())
            }
            if (insnNode.getResult() != null && !insnNode.contains(AFlag.ARITH_ONEARG)) {
                assignVar(codeWriter, insnNode)
                codeWriter.add(" = ")
            }
            makeInsnBody(codeWriter, insnNode, enumSetNoneOf)
            if (flags == Flags.INLINE) {
                return true
            }
            codeWriter.add(';')
            return true
        } catch (Throwable th) {
            throw CodegenException(this.mth, "Error generate insn: " + insnNode, th)
        }
    }

    protected fun staticField(CodeWriter codeWriter, FieldInfo fieldInfo) {
        makeStaticFieldAccess(codeWriter, fieldInfo, this.mgen.getClassGen())
    }

    fun useClass(CodeWriter codeWriter, ClassInfo classInfo) {
        this.mgen.getClassGen().useClass(codeWriter, classInfo)
    }

    fun useClass(CodeWriter codeWriter, ArgType argType) {
        this.mgen.getClassGen().useClass(codeWriter, argType)
    }

    protected fun useType(CodeWriter codeWriter, ArgType argType) {
        this.mgen.getClassGen().useType(codeWriter, argType)
    }
}
