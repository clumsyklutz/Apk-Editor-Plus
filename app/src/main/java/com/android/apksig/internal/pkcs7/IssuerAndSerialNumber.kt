package com.android.apksig.internal.pkcs7

import com.android.apksig.internal.asn1.Asn1Class
import com.android.apksig.internal.asn1.Asn1Field
import com.android.apksig.internal.asn1.Asn1OpaqueObject
import com.android.apksig.internal.asn1.Asn1Type
import java.math.BigInteger

@Asn1Class(type = Asn1Type.SEQUENCE)
class IssuerAndSerialNumber {

    @Asn1Field(index = 1, type = Asn1Type.INTEGER)
    public BigInteger certificateSerialNumber

    @Asn1Field(index = 0, type = Asn1Type.ANY)
    public Asn1OpaqueObject issuer

    constructor() {
    }

    constructor(Asn1OpaqueObject asn1OpaqueObject, BigInteger bigInteger) {
        this.issuer = asn1OpaqueObject
        this.certificateSerialNumber = bigInteger
    }
}
