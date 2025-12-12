package org.jf.dexlib2.iface.debug

import org.jf.dexlib2.iface.reference.StringReference

public interface SetSourceFile extends DebugItem {
    String getSourceFile()

    StringReference getSourceFileReference()
}
