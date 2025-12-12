package org.jf.baksmali.Adaptors.Debug

import java.io.IOException
import org.jf.baksmali.Adaptors.ClassDefinition
import org.jf.baksmali.Adaptors.RegisterFormatter
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.debug.RestartLocal

class RestartLocalMethodItem extends DebugMethodItem {
    public final RegisterFormatter registerFormatter
    public final RestartLocal restartLocal

    constructor(ClassDefinition classDefinition, Int i, Int i2, RegisterFormatter registerFormatter, RestartLocal restartLocal) {
        super(i, i2)
        this.restartLocal = restartLocal
        this.registerFormatter = registerFormatter
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".restart local ")
        this.registerFormatter.writeTo(baksmaliWriter, this.restartLocal.getRegister())
        String name = this.restartLocal.getName()
        String type = this.restartLocal.getType()
        String signature = this.restartLocal.getSignature()
        if (name == null && type == null && signature == null) {
            return true
        }
        baksmaliWriter.write("    # ")
        LocalFormatter.writeLocal(baksmaliWriter, name, type, signature)
        return true
    }
}
