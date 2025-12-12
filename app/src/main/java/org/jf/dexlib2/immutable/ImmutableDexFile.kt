package org.jf.dexlib2.immutable

import com.google.common.collect.ImmutableSet
import java.util.Collection
import org.jf.dexlib2.Opcodes
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.DexFile

class ImmutableDexFile implements DexFile {
    public final ImmutableSet<? extends ImmutableClassDef> classes
    public final Opcodes opcodes

    constructor(Opcodes opcodes, Collection<? extends ClassDef> collection) {
        this.classes = ImmutableClassDef.immutableSetOf(collection)
        this.opcodes = opcodes
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public ImmutableSet<? extends ImmutableClassDef> getClasses() {
        return this.classes
    }

    @Override // org.jf.dexlib2.iface.DexFile
    fun getOpcodes() {
        return this.opcodes
    }
}
