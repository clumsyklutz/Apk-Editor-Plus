package org.jf.dexlib2.iface.debug

import org.jf.dexlib2.iface.reference.StringReference
import org.jf.dexlib2.iface.reference.TypeReference

public interface StartLocal extends DebugItem, LocalInfo {
    StringReference getNameReference()

    Int getRegister()

    StringReference getSignatureReference()

    TypeReference getTypeReference()
}
