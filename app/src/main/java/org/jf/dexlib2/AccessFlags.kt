package org.jf.dexlib2

import java.util.HashMap

public enum AccessFlags {
    PUBLIC(1, "public", true, true, true),
    PRIVATE(2, "private", true, true, true),
    PROTECTED(4, "protected", true, true, true),
    STATIC(8, "static", true, true, true),
    FINAL(16, "final", true, true, true),
    SYNCHRONIZED(32, "synchronized", false, true, false),
    VOLATILE(64, "volatile", false, false, true),
    BRIDGE(64, "bridge", false, true, false),
    TRANSIENT(128, "transient", false, false, true),
    VARARGS(128, "varargs", false, true, false),
    NATIVE(256, "native", false, true, false),
    INTERFACE(512, "interface", true, false, false),
    ABSTRACT(1024, "abstract", true, true, false),
    STRICTFP(2048, "strictfp", false, true, false),
    SYNTHETIC(4096, "synthetic", true, true, true),
    ANNOTATION(8192, "annotation", true, false, false),
    ENUM(16384, "enum", true, false, true),
    CONSTRUCTOR(65536, "constructor", false, true, false),
    DECLARED_SYNCHRONIZED(131072, "declared-synchronized", false, true, false)

    public static HashMap<String, AccessFlags> accessFlagsByName
    public static final Array<AccessFlags> allFlags
    public String accessFlagName
    public Boolean validForClass
    public Boolean validForField
    public Boolean validForMethod
    public Int value

    static {
        Array<AccessFlags> accessFlagsArrValues = values()
        allFlags = accessFlagsArrValues
        accessFlagsByName = new HashMap<>()
        for (AccessFlags accessFlags : accessFlagsArrValues) {
            accessFlagsByName.put(accessFlags.accessFlagName, accessFlags)
        }
    }

    AccessFlags(Int i, String str, Boolean z, Boolean z2, Boolean z3) {
        this.value = i
        this.accessFlagName = str
        this.validForClass = z
        this.validForMethod = z2
        this.validForField = z3
    }

    fun getAccessFlag(String str) {
        return accessFlagsByName.get(str)
    }

    public static Array<AccessFlags> getAccessFlagsForClass(Int i) {
        Int i2 = 0
        for (AccessFlags accessFlags : allFlags) {
            if (accessFlags.validForClass && (accessFlags.value & i) != 0) {
                i2++
            }
        }
        Array<AccessFlags> accessFlagsArr = new AccessFlags[i2]
        Int i3 = 0
        for (AccessFlags accessFlags2 : allFlags) {
            if (accessFlags2.validForClass && (accessFlags2.value & i) != 0) {
                accessFlagsArr[i3] = accessFlags2
                i3++
            }
        }
        return accessFlagsArr
    }

    public static Array<AccessFlags> getAccessFlagsForField(Int i) {
        Int i2 = 0
        for (AccessFlags accessFlags : allFlags) {
            if (accessFlags.validForField && (accessFlags.value & i) != 0) {
                i2++
            }
        }
        Array<AccessFlags> accessFlagsArr = new AccessFlags[i2]
        Int i3 = 0
        for (AccessFlags accessFlags2 : allFlags) {
            if (accessFlags2.validForField && (accessFlags2.value & i) != 0) {
                accessFlagsArr[i3] = accessFlags2
                i3++
            }
        }
        return accessFlagsArr
    }

    public static Array<AccessFlags> getAccessFlagsForMethod(Int i) {
        Int i2 = 0
        for (AccessFlags accessFlags : allFlags) {
            if (accessFlags.validForMethod && (accessFlags.value & i) != 0) {
                i2++
            }
        }
        Array<AccessFlags> accessFlagsArr = new AccessFlags[i2]
        Int i3 = 0
        for (AccessFlags accessFlags2 : allFlags) {
            if (accessFlags2.validForMethod && (accessFlags2.value & i) != 0) {
                accessFlagsArr[i3] = accessFlags2
                i3++
            }
        }
        return accessFlagsArr
    }

    fun getValue() {
        return this.value
    }

    fun isSet(Int i) {
        return (i & this.value) != 0
    }

    @Override // java.lang.Enum
    fun toString() {
        return this.accessFlagName
    }
}
