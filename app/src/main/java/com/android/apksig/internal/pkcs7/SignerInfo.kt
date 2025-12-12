package com.android.apksig.internal.pkcs7

import com.android.apksig.internal.asn1.Asn1Class
import com.android.apksig.internal.asn1.Asn1Field
import com.android.apksig.internal.asn1.Asn1OpaqueObject
import com.android.apksig.internal.asn1.Asn1Tagging
import com.android.apksig.internal.asn1.Asn1Type
import java.nio.ByteBuffer
import java.util.List

@Asn1Class(type = Asn1Type.SEQUENCE)
class SignerInfo {

    @Asn1Field(index = 2, type = Asn1Type.SEQUENCE)
    public AlgorithmIdentifier digestAlgorithm

    @Asn1Field(index = 1, type = Asn1Type.CHOICE)
    public SignerIdentifier sid

    @Asn1Field(index = 5, type = Asn1Type.OCTET_STRING)
    public ByteBuffer signature

    @Asn1Field(index = 4, type = Asn1Type.SEQUENCE)
    public AlgorithmIdentifier signatureAlgorithm

    @Asn1Field(index = 3, optional = true, tagNumber = 0, tagging = Asn1Tagging.IMPLICIT, type = Asn1Type.SET_OF)
    public Asn1OpaqueObject signedAttrs

    @Asn1Field(index = 6, optional = true, tagNumber = 1, tagging = Asn1Tagging.IMPLICIT, type = Asn1Type.SET_OF)
    public List<Attribute> unsignedAttrs

    @Asn1Field(index = 0, type = Asn1Type.INTEGER)
    public Int version
}
