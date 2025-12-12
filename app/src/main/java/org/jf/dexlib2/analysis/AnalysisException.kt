package org.jf.dexlib2.analysis

import org.jf.util.ExceptionWithContext

class AnalysisException extends ExceptionWithContext {
    public Int codeAddress

    constructor(String str, Object... objArr) {
        super(str, objArr)
    }
}
