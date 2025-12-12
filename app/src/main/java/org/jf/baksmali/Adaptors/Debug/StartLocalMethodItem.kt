package org.jf.baksmali.Adaptors.Debug

import java.io.IOException
import org.jf.baksmali.Adaptors.ClassDefinition
import org.jf.baksmali.Adaptors.RegisterFormatter
import org.jf.baksmali.formatter.BaksmaliWriter
import org.jf.dexlib2.iface.debug.StartLocal

class StartLocalMethodItem extends DebugMethodItem {
    public final RegisterFormatter registerFormatter
    public final StartLocal startLocal

    constructor(ClassDefinition classDefinition, Int i, Int i2, RegisterFormatter registerFormatter, StartLocal startLocal) {
        super(i, i2)
        this.startLocal = startLocal
        this.registerFormatter = registerFormatter
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    fun writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".local ")
        this.registerFormatter.writeTo(baksmaliWriter, this.startLocal.getRegister())
        String name = this.startLocal.getName()
        String type = this.startLocal.getType()
        String signature = this.startLocal.getSignature()
        if (name == null && type == null && signature == null) {
            return true
        }
        baksmaliWriter.write(", ")
        LocalFormatter.writeLocal(baksmaliWriter, name, type, signature)
        return true
    }
}
