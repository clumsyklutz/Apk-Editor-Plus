package com.android.apksig.internal.pkcs7

import com.android.apksig.internal.asn1.Asn1Class
import com.android.apksig.internal.asn1.Asn1Field
import com.android.apksig.internal.asn1.Asn1Tagging
import com.android.apksig.internal.asn1.Asn1Type
import java.nio.ByteBuffer

@Asn1Class(type = Asn1Type.CHOICE)
class SignerIdentifier {

    @Asn1Field(type = Asn1Type.SEQUENCE)
    public IssuerAndSerialNumber issuerAndSerialNumber

    @Asn1Field(tagNumber = 0, tagging = Asn1Tagging.IMPLICIT, type = Asn1Type.OCTET_STRING)
    public ByteBuffer subjectKeyIdentifier

    constructor() {
    }

    constructor(IssuerAndSerialNumber issuerAndSerialNumber) {
        this.issuerAndSerialNumber = issuerAndSerialNumber
    }
}
