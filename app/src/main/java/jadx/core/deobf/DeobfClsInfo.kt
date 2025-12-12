package jadx.core.deobf

import jadx.core.dex.nodes.ClassNode

class DeobfClsInfo {
    private final String alias
    private final ClassNode cls
    private final Deobfuscator deobfuscator
    private final PackageNode pkg

    constructor(Deobfuscator deobfuscator, ClassNode classNode, PackageNode packageNode, String str) {
        this.deobfuscator = deobfuscator
        this.cls = classNode
        this.pkg = packageNode
        this.alias = str
    }

    fun getAlias() {
        return this.alias
    }

    fun getCls() {
        return this.cls
    }

    fun getFullName() {
        return this.pkg.getFullAlias() + Deobfuscator.CLASS_NAME_SEPARATOR + makeNameWithoutPkg()
    }

    fun getPkg() {
        return this.pkg
    }

    fun makeNameWithoutPkg() {
        String str
        ClassNode parentClass = this.cls.getParentClass()
        if (parentClass != this.cls) {
            DeobfClsInfo deobfClsInfo = (DeobfClsInfo) this.deobfuscator.getClsMap().get(parentClass.getClassInfo())
            str = (deobfClsInfo != null ? deobfClsInfo.makeNameWithoutPkg() : this.deobfuscator.getNameWithoutPackage(parentClass.getClassInfo())) + Deobfuscator.INNER_CLASS_SEPARATOR
        } else {
            str = ""
        }
        return str + (this.alias != null ? this.alias : this.cls.getShortName())
    }
}
