package org.jf.dexlib2.iface.instruction.formats

import java.util.List
import org.jf.dexlib2.iface.instruction.SwitchElement
import org.jf.dexlib2.iface.instruction.SwitchPayload

public interface PackedSwitchPayload extends SwitchPayload {
    List<? extends SwitchElement> getSwitchElements()
}
