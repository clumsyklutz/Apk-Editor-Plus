package com.android.apksig.internal.pkcs7

import com.android.apksig.internal.asn1.Asn1Class
import com.android.apksig.internal.asn1.Asn1Field
import com.android.apksig.internal.asn1.Asn1Tagging
import com.android.apksig.internal.asn1.Asn1Type
import java.nio.ByteBuffer

@Asn1Class(type = Asn1Type.SEQUENCE)
class EncapsulatedContentInfo {

    @Asn1Field(index = 1, optional = true, tagNumber = 0, tagging = Asn1Tagging.EXPLICIT, type = Asn1Type.OCTET_STRING)
    public ByteBuffer content

    @Asn1Field(index = 0, type = Asn1Type.OBJECT_IDENTIFIER)
    public String contentType

    constructor() {
    }

    constructor(String str) {
        this.contentType = str
    }
}
