package com.android.apksig.internal.asn1.ber

import com.android.apksig.internal.asn1.Asn1TagClass
import com.android.apksig.internal.asn1.Asn1Type

abstract class BerEncoding {
    public static val ID_FLAG_CONSTRUCTED_ENCODING = 32
    public static val TAG_CLASS_APPLICATION = 1
    public static val TAG_CLASS_CONTEXT_SPECIFIC = 2
    public static val TAG_CLASS_PRIVATE = 3
    public static val TAG_CLASS_UNIVERSAL = 0
    public static val TAG_NUMBER_BIT_STRING = 3
    public static val TAG_NUMBER_BOOLEAN = 1
    public static val TAG_NUMBER_GENERALIZED_TIME = 24
    public static val TAG_NUMBER_INTEGER = 2
    public static val TAG_NUMBER_NULL = 5
    public static val TAG_NUMBER_OBJECT_IDENTIFIER = 6
    public static val TAG_NUMBER_OCTET_STRING = 4
    public static val TAG_NUMBER_SEQUENCE = 16
    public static val TAG_NUMBER_SET = 17
    public static val TAG_NUMBER_UTC_TIME = 23

    /* renamed from: com.android.apksig.internal.asn1.ber.BerEncoding$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$asn1$Asn1TagClass
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$asn1$Asn1Type

        static {
            Array<Int> iArr = new Int[Asn1TagClass.values().length]
            $SwitchMap$com$android$apksig$internal$asn1$Asn1TagClass = iArr
            try {
                iArr[Asn1TagClass.APPLICATION.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1TagClass[Asn1TagClass.CONTEXT_SPECIFIC.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1TagClass[Asn1TagClass.PRIVATE.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1TagClass[Asn1TagClass.UNIVERSAL.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            Array<Int> iArr2 = new Int[Asn1Type.values().length]
            $SwitchMap$com$android$apksig$internal$asn1$Asn1Type = iArr2
            try {
                iArr2[Asn1Type.INTEGER.ordinal()] = 1
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.OBJECT_IDENTIFIER.ordinal()] = 2
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.OCTET_STRING.ordinal()] = 3
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.BIT_STRING.ordinal()] = 4
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SET_OF.ordinal()] = 5
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SEQUENCE.ordinal()] = 6
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SEQUENCE_OF.ordinal()] = 7
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.UTC_TIME.ordinal()] = 8
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.GENERALIZED_TIME.ordinal()] = 9
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.BOOLEAN.ordinal()] = 10
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    fun getTagClass(Byte b2) {
        return (b2 & 255) >> 6
    }

    fun getTagClass(Asn1TagClass asn1TagClass) {
        Int i = AnonymousClass1.$SwitchMap$com$android$apksig$internal$asn1$Asn1TagClass[asn1TagClass.ordinal()]
        Int i2 = 1
        if (i != 1) {
            i2 = 2
            if (i != 2) {
                i2 = 3
                if (i != 3) {
                    if (i == 4) {
                        return 0
                    }
                    throw IllegalArgumentException("Unsupported tag class: " + asn1TagClass)
                }
            }
        }
        return i2
    }

    fun getTagNumber(Byte b2) {
        return b2 & 31
    }

    fun getTagNumber(Asn1Type asn1Type) {
        switch (AnonymousClass1.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[asn1Type.ordinal()]) {
            case 1:
                return 2
            case 2:
                return 6
            case 3:
                return 4
            case 4:
                return 3
            case 5:
                return 17
            case 6:
            case 7:
                return 16
            case 8:
                return 23
            case 9:
                return 24
            case 10:
                return 1
            default:
                throw IllegalArgumentException("Unsupported data type: " + asn1Type)
        }
    }

    fun isConstructed(Byte b2) {
        return (b2 & 32) != 0
    }

    fun setTagClass(Byte b2, Int i) {
        return (Byte) ((b2 & 63) | (i << 6))
    }

    fun setTagNumber(Byte b2, Int i) {
        return (Byte) ((b2 & (-32)) | i)
    }

    fun tagClassAndNumberToString(Int i, Int i2) {
        String strTagClassToString = tagClassToString(i)
        String strTagNumberToString = tagNumberToString(i2)
        if (strTagClassToString.isEmpty()) {
            return strTagNumberToString
        }
        return strTagClassToString + " " + strTagNumberToString
    }

    fun tagClassToString(Int i) {
        if (i == 0) {
            return "UNIVERSAL"
        }
        if (i == 1) {
            return "APPLICATION"
        }
        if (i == 2) {
            return ""
        }
        if (i == 3) {
            return "PRIVATE"
        }
        throw IllegalArgumentException("Unsupported type class: " + i)
    }

    fun tagNumberToString(Int i) {
        if (i == 16) {
            return "SEQUENCE"
        }
        if (i == 17) {
            return "SET"
        }
        if (i == 23) {
            return "UTC TIME"
        }
        if (i == 24) {
            return "GENERALIZED TIME"
        }
        switch (i) {
            case 1:
                return "BOOLEAN"
            case 2:
                return "INTEGER"
            case 3:
                return "BIT STRING"
            case 4:
                return "OCTET STRING"
            case 5:
                return "NULL"
            case 6:
                return "OBJECT IDENTIFIER"
            default:
                return "0x" + Integer.toHexString(i)
        }
    }
}
