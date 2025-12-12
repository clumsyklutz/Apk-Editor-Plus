package org.jf.dexlib2.iface

import java.util.Set
import org.jf.dexlib2.Opcodes

public interface DexFile {
    Set<? extends ClassDef> getClasses()

    Opcodes getOpcodes()
}
