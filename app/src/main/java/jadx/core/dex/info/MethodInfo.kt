package jadx.core.dex.info

import com.a.a.x
import com.a.a.y
import jadx.core.codegen.TypeGen
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.DexNode
import jadx.core.utils.Utils
import java.util.Iterator
import java.util.List

class MethodInfo {
    private String alias
    private Boolean aliasFromPreset
    private final List args
    private final ClassInfo declClass
    private final String name
    private final ArgType retType
    private final String shortId

    private constructor(DexNode dexNode, Int i) {
        x methodId = dexNode.getMethodId(i)
        this.name = dexNode.getString(methodId.c())
        this.alias = this.name
        this.aliasFromPreset = false
        this.declClass = ClassInfo.fromDex(dexNode, methodId.a())
        y protoId = dexNode.getProtoId(methodId.b())
        this.retType = dexNode.getType(protoId.b())
        this.args = dexNode.readParamList(protoId.c())
        this.shortId = makeSignature(true)
    }

    fun fromDex(DexNode dexNode, Int i) {
        MethodInfo method = dexNode.getInfoStorage().getMethod(i)
        if (method != null) {
            return method
        }
        return dexNode.getInfoStorage().putMethod(i, MethodInfo(dexNode, i))
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is MethodInfo)) {
            return false
        }
        MethodInfo methodInfo = (MethodInfo) obj
        return this.shortId.equals(methodInfo.shortId) && this.retType.equals(methodInfo.retType) && this.declClass.equals(methodInfo.declClass)
    }

    public final String getAlias() {
        return this.alias
    }

    public final Int getArgsCount() {
        return this.args.size()
    }

    public final List getArgumentsTypes() {
        return this.args
    }

    public final ClassInfo getDeclClass() {
        return this.declClass
    }

    public final String getFullId() {
        return this.declClass.getFullName() + Deobfuscator.CLASS_NAME_SEPARATOR + this.shortId
    }

    public final String getFullName() {
        return this.declClass.getFullName() + Deobfuscator.CLASS_NAME_SEPARATOR + this.name
    }

    public final String getName() {
        return this.name
    }

    public final ArgType getReturnType() {
        return this.retType
    }

    public final String getShortId() {
        return this.shortId
    }

    public final Int hashCode() {
        return (((this.declClass.hashCode() * 31) + this.retType.hashCode()) * 31) + this.shortId.hashCode()
    }

    public final Boolean isAliasFromPreset() {
        return this.aliasFromPreset
    }

    public final Boolean isClassInit() {
        return this.name.equals("<clinit>")
    }

    public final Boolean isConstructor() {
        return this.name.equals("<init>")
    }

    public final Boolean isRenamed() {
        return !this.name.equals(this.alias)
    }

    public final String makeSignature(Boolean z) {
        StringBuilder sb = StringBuilder()
        sb.append(this.name)
        sb.append('(')
        Iterator it = this.args.iterator()
        while (it.hasNext()) {
            sb.append(TypeGen.signature((ArgType) it.next()))
        }
        sb.append(')')
        if (z) {
            sb.append(TypeGen.signature(this.retType))
        }
        return sb.toString()
    }

    public final Unit setAlias(String str) {
        this.alias = str
    }

    public final Unit setAliasFromPreset(Boolean z) {
        this.aliasFromPreset = z
    }

    public final String toString() {
        return this.declClass.getFullName() + Deobfuscator.CLASS_NAME_SEPARATOR + this.name + "(" + Utils.listToString(this.args) + "):" + this.retType
    }
}
