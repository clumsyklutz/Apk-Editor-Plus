package jadx.core.codegen

import jadx.core.Consts
import jadx.core.deobf.NameMapper
import jadx.core.dex.attributes.nodes.LoopLabelAttr
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.NamedArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.utils.StringUtils
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set

class NameGen {
    private static final Map OBJ_ALIAS
    private final Boolean fallback
    private final MethodNode mth
    private val varNames = HashSet()

    static {
        HashMap map = HashMap()
        OBJ_ALIAS = map
        map.put(Consts.CLASS_STRING, "str")
        OBJ_ALIAS.put(Consts.CLASS_CLASS, "cls")
        OBJ_ALIAS.put(Consts.CLASS_THROWABLE, "th")
        OBJ_ALIAS.put(Consts.CLASS_OBJECT, "obj")
        OBJ_ALIAS.put("java.util.Iterator", "it")
        OBJ_ALIAS.put("java.lang.Boolean", "bool")
        OBJ_ALIAS.put("java.lang.Short", "sh")
        OBJ_ALIAS.put("java.lang.Integer", "num")
        OBJ_ALIAS.put("java.lang.Character", "ch")
        OBJ_ALIAS.put("java.lang.Byte", "b")
        OBJ_ALIAS.put("java.lang.Float", "f")
        OBJ_ALIAS.put("java.lang.Long", "l")
        OBJ_ALIAS.put("java.lang.Double", "d")
    }

    constructor(MethodNode methodNode, Boolean z) {
        this.mth = methodNode
        this.fallback = z
    }

    private fun fromName(String str) {
        if (str == null || str.isEmpty()) {
            return null
        }
        if (str.toUpperCase().equals(str)) {
            return str.toLowerCase()
        }
        String str2 = Character.toLowerCase(str.charAt(0)) + str.substring(1)
        if (!str2.equals(str)) {
            return str2
        }
        if (str.length() < 3) {
            return str + "Var"
        }
        return null
    }

    private fun getAliasForObject(String str) {
        return (String) OBJ_ALIAS.get(str)
    }

    private fun getFallbackName(RegisterArg registerArg) {
        return "r" + registerArg.getRegNum()
    }

    private fun getUniqueVarName(String str) {
        Int i = 2
        String str2 = str
        while (this.varNames.contains(str2)) {
            str2 = str + i
            i++
        }
        this.varNames.add(str2)
        return str2
    }

    private fun guessName(RegisterArg registerArg) {
        RegisterArg assign
        InsnNode parentInsn
        String strMakeNameFromInsn
        SSAVar sVar = registerArg.getSVar()
        if (sVar == null || sVar.getName() != null || (parentInsn = (assign = sVar.getAssign()).getParentInsn()) == null || (strMakeNameFromInsn = makeNameFromInsn(parentInsn)) == null || NameMapper.isReserved(strMakeNameFromInsn)) {
            return makeNameForType(registerArg.getType())
        }
        assign.setName(strMakeNameFromInsn)
        return strMakeNameFromInsn
    }

    private fun makeArgName(RegisterArg registerArg) {
        if (this.fallback) {
            return getFallbackName(registerArg)
        }
        String name = registerArg.getName()
        if (name == null) {
            name = guessName(registerArg)
        } else if ("this".equals(name)) {
            return name
        }
        return NameMapper.isReserved(name) ? name + "R" : name
    }

    private fun makeNameForObject(ArgType argType) {
        if (argType.isObject()) {
            String aliasForObject = getAliasForObject(argType.getObject())
            if (aliasForObject != null) {
                return aliasForObject
            }
            String strFromName = fromName(ClassInfo.extCls(this.mth.dex(), argType).getShortName())
            if (strFromName != null) {
                return strFromName
            }
        }
        return StringUtils.escape(argType.toString())
    }

    private fun makeNameForPrimitive(ArgType argType) {
        return argType.getPrimitiveType().getShortName().toLowerCase()
    }

    private fun makeNameForType(ArgType argType) {
        return argType.isPrimitive() ? makeNameForPrimitive(argType) : argType.isArray() ? makeNameForType(argType.getArrayRootElement()) + "Arr" : makeNameForObject(argType)
    }

    private fun makeNameFromInsn(InsnNode insnNode) {
        String strMakeNameFromInsn
        switch (insnNode.getType()) {
            case INVOKE:
                return makeNameFromInvoke(((InvokeNode) insnNode).getCallMth())
            case CONSTRUCTOR:
                return makeNameForObject(((ConstructorInsn) insnNode).getClassType().getType())
            case ARRAY_LENGTH:
                return "length"
            case ARITH:
            case TERNARY:
            case CAST:
                for (InsnArg insnArg : insnNode.getArguments()) {
                    if (insnArg.isInsnWrap() && (strMakeNameFromInsn = makeNameFromInsn(((InsnWrapArg) insnArg).getWrapInsn())) != null) {
                        return strMakeNameFromInsn
                    }
                }
                break
            default:
                return null
        }
    }

    private fun makeNameFromInvoke(MethodInfo methodInfo) {
        String name = methodInfo.getName()
        if (name.startsWith("get") || name.startsWith("set")) {
            return fromName(name.substring(3))
        }
        ArgType type = methodInfo.getDeclClass().getAlias().getType()
        return "iterator".equals(name) ? "it" : "toString".equals(name) ? makeNameForType(type) : ("forName".equals(name) && type.equals(ArgType.CLASS)) ? (String) OBJ_ALIAS.get(Consts.CLASS_CLASS) : name
    }

    fun assignArg(RegisterArg registerArg) {
        String strMakeArgName = makeArgName(registerArg)
        if (this.fallback) {
            return strMakeArgName
        }
        String uniqueVarName = getUniqueVarName(strMakeArgName)
        registerArg.setName(uniqueVarName)
        return uniqueVarName
    }

    fun assignNamedArg(NamedArg namedArg) {
        String name = namedArg.getName()
        if (this.fallback) {
            return name
        }
        String uniqueVarName = getUniqueVarName(name)
        namedArg.setName(uniqueVarName)
        return uniqueVarName
    }

    fun getLoopLabel(LoopLabelAttr loopLabelAttr) {
        String str = "loop" + loopLabelAttr.getLoop().getId()
        this.varNames.add(str)
        return str
    }

    fun useArg(RegisterArg registerArg) {
        String name = registerArg.getName()
        return (name == null || this.fallback) ? getFallbackName(registerArg) : name
    }
}
