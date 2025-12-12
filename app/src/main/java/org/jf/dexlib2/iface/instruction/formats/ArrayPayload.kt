package org.jf.dexlib2.iface.instruction.formats

import java.util.List
import org.jf.dexlib2.iface.instruction.Instruction

public interface ArrayPayload extends Instruction {
    List<Number> getArrayElements()

    Int getElementWidth()
}
