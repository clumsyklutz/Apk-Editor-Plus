package org.jf.dexlib2.iface

import java.util.List
import org.jf.dexlib2.iface.ExceptionHandler

public interface TryBlock<EH extends ExceptionHandler> {
    Int getCodeUnitCount()

    List<? extends EH> getExceptionHandlers()

    Int getStartCodeAddress()
}
