package org.jf.dexlib2.iface.instruction

import java.util.List

public interface SwitchPayload extends Instruction {
    List<? extends SwitchElement> getSwitchElements()
}
