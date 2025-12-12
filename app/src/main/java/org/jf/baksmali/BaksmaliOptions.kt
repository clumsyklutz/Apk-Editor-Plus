package org.jf.baksmali

import java.util.HashMap
import java.util.Map
import org.jf.dexlib2.analysis.ClassPath
import org.jf.dexlib2.analysis.InlineMethodResolver
import org.jf.dexlib2.util.SyntheticAccessorResolver

class BaksmaliOptions {
    public ClassPath classPath
    public InlineMethodResolver inlineResolver
    public SyntheticAccessorResolver syntheticAccessorResolver
    public Int apiLevel = 15
    public Boolean parameterRegisters = true
    public Boolean localsDirective = false
    public Boolean sequentialLabels = false
    public Boolean debugInfo = true
    public Boolean codeOffsets = false
    public Boolean accessorComments = true
    public Boolean allowOdex = false
    public Boolean deodex = false
    public Boolean implicitReferences = false
    public Boolean normalizeVirtualMethods = false
    public Int registerInfo = 0
    public Map<Integer, String> resourceIds = HashMap()
}
