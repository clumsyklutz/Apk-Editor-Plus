package com.android.apksig.internal.x509

import com.android.apksig.internal.asn1.Asn1Class
import com.android.apksig.internal.asn1.Asn1Field
import com.android.apksig.internal.asn1.Asn1OpaqueObject
import com.android.apksig.internal.asn1.Asn1Type
import com.android.apksig.internal.pkcs7.AlgorithmIdentifier
import com.android.apksig.internal.pkcs7.IssuerAndSerialNumber
import com.android.apksig.internal.pkcs7.SignerIdentifier
import com.android.apksig.internal.util.ByteBufferUtils
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate
import com.android.apksig.internal.util.X509CertificateUtils
import java.nio.ByteBuffer
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.List
import javax.security.auth.x500.X500Principal

@Asn1Class(type = Asn1Type.SEQUENCE)
class Certificate {

    @Asn1Field(index = 0, type = Asn1Type.SEQUENCE)
    public TBSCertificate certificate

    @Asn1Field(index = 2, type = Asn1Type.BIT_STRING)
    public ByteBuffer signature

    @Asn1Field(index = 1, type = Asn1Type.SEQUENCE)
    public AlgorithmIdentifier signatureAlgorithm

    fun findCertificate(Collection<X509Certificate> collection, SignerIdentifier signerIdentifier) {
        for (X509Certificate x509Certificate : collection) {
            if (isMatchingCerticicate(x509Certificate, signerIdentifier)) {
                return x509Certificate
            }
        }
        return null
    }

    fun isMatchingCerticicate(X509Certificate x509Certificate, SignerIdentifier signerIdentifier) {
        IssuerAndSerialNumber issuerAndSerialNumber = signerIdentifier.issuerAndSerialNumber
        if (issuerAndSerialNumber == null) {
            return false
        }
        return issuerAndSerialNumber.certificateSerialNumber.equals(x509Certificate.getSerialNumber()) && X500Principal(ByteBufferUtils.toByteArray(issuerAndSerialNumber.issuer.getEncoded())).equals(x509Certificate.getIssuerX500Principal())
    }

    public static List<X509Certificate> parseCertificates(List<Asn1OpaqueObject> list) throws CertificateException {
        if (list.isEmpty()) {
            return Collections.emptyList()
        }
        ArrayList arrayList = ArrayList(list.size())
        for (Int i = 0; i < list.size(); i++) {
            Array<Byte> byteArray = ByteBufferUtils.toByteArray(list.get(i).getEncoded())
            try {
                arrayList.add(GuaranteedEncodedFormX509Certificate(X509CertificateUtils.generateCertificate(byteArray), byteArray))
            } catch (CertificateException e) {
                throw CertificateException("Failed to parse certificate #" + (i + 1), e)
            }
        }
        return arrayList
    }
}
