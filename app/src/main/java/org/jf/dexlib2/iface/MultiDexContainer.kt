package org.jf.dexlib2.iface

import java.io.IOException
import java.util.List
import org.jf.dexlib2.iface.DexFile

public interface MultiDexContainer<T extends DexFile> {

    public interface DexEntry<T extends DexFile> {
        T getDexFile()
    }

    List<String> getDexEntryNames() throws IOException

    DexEntry<T> getEntry(String str) throws IOException
}
