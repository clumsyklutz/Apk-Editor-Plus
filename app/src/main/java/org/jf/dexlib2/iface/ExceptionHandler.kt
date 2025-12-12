package org.jf.dexlib2.iface

import org.jf.dexlib2.iface.reference.TypeReference

public interface ExceptionHandler extends Comparable<ExceptionHandler> {
    String getExceptionType()

    TypeReference getExceptionTypeReference()

    Int getHandlerCodeAddress()
}
