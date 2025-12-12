package com.android.apksig.internal.util

import android.util.Base64
import com.android.apksig.internal.asn1.Asn1BerParser
import com.android.apksig.internal.asn1.Asn1DecodingException
import com.android.apksig.internal.asn1.Asn1DerEncoder
import com.android.apksig.internal.asn1.Asn1EncodingException
import com.android.apksig.internal.x509.Certificate
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Collection

class X509CertificateUtils {
    public static final Array<Byte> BEGIN_CERT_HEADER = "-----BEGIN CERTIFICATE-----".getBytes()
    public static final Array<Byte> END_CERT_FOOTER = "-----END CERTIFICATE-----".getBytes()
    public static CertificateFactory sCertFactory

    fun buildCertFactory() {
        if (sCertFactory != null) {
            return
        }
        try {
            sCertFactory = CertificateFactory.getInstance("X.509")
        } catch (CertificateException e) {
            throw RuntimeException("Failed to create X.509 CertificateFactory", e)
        }
    }

    fun generateCertificate(InputStream inputStream) throws CertificateException {
        try {
            return generateCertificate(ByteStreams.toByteArray(inputStream))
        } catch (IOException e) {
            throw CertificateException("Failed to parse certificate", e)
        }
    }

    fun generateCertificate(Array<Byte> bArr) throws CertificateException {
        if (sCertFactory == null) {
            buildCertFactory()
        }
        return generateCertificate(bArr, sCertFactory)
    }

    fun generateCertificate(Array<Byte> bArr, CertificateFactory certificateFactory) throws CertificateException {
        try {
            try {
                return (X509Certificate) certificateFactory.generateCertificate(ByteArrayInputStream(bArr))
            } catch (Asn1DecodingException | Asn1EncodingException | CertificateException e) {
                throw CertificateException("Failed to parse certificate", e)
            }
        } catch (CertificateException unused) {
            ByteBuffer nextDEREncodedCertificateBlock = getNextDEREncodedCertificateBlock(ByteBuffer.wrap(bArr))
            Int iPosition = nextDEREncodedCertificateBlock.position()
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(ByteArrayInputStream(Asn1DerEncoder.encode((Certificate) Asn1BerParser.parse(nextDEREncodedCertificateBlock, Certificate.class))))
            Array<Byte> bArr2 = new Byte[nextDEREncodedCertificateBlock.position() - iPosition]
            nextDEREncodedCertificateBlock.position(iPosition)
            nextDEREncodedCertificateBlock.get(bArr2)
            return GuaranteedEncodedFormX509Certificate(x509Certificate, bArr2)
        }
    }

    public static Collection<? extends java.security.cert.Certificate> generateCertificates(InputStream inputStream) throws CertificateException {
        if (sCertFactory == null) {
            buildCertFactory()
        }
        return generateCertificates(inputStream, sCertFactory)
    }

    public static Collection<? extends java.security.cert.Certificate> generateCertificates(InputStream inputStream, CertificateFactory certificateFactory) throws CertificateException {
        try {
            Array<Byte> byteArray = ByteStreams.toByteArray(inputStream)
            try {
                try {
                    return certificateFactory.generateCertificates(ByteArrayInputStream(byteArray))
                } catch (Asn1DecodingException | Asn1EncodingException e) {
                    throw CertificateException("Failed to parse certificates", e)
                }
            } catch (CertificateException unused) {
                ArrayList arrayList = ArrayList(1)
                ByteBuffer byteBufferWrap = ByteBuffer.wrap(byteArray)
                while (byteBufferWrap.hasRemaining()) {
                    ByteBuffer nextDEREncodedCertificateBlock = getNextDEREncodedCertificateBlock(byteBufferWrap)
                    Int iPosition = nextDEREncodedCertificateBlock.position()
                    X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(ByteArrayInputStream(Asn1DerEncoder.encode((Certificate) Asn1BerParser.parse(nextDEREncodedCertificateBlock, Certificate.class))))
                    Array<Byte> bArr = new Byte[nextDEREncodedCertificateBlock.position() - iPosition]
                    nextDEREncodedCertificateBlock.position(iPosition)
                    nextDEREncodedCertificateBlock.get(bArr)
                    arrayList.add(GuaranteedEncodedFormX509Certificate(x509Certificate, bArr))
                }
                return arrayList
            }
        } catch (IOException e2) {
            throw CertificateException("Failed to read the input stream", e2)
        }
    }

    fun getNextDEREncodedCertificateBlock(ByteBuffer byteBuffer) throws CertificateException {
        Char c
        if (byteBuffer == null) {
            throw NullPointerException("The certificateBuffer cannot be null")
        }
        if (byteBuffer.remaining() < BEGIN_CERT_HEADER.length) {
            return byteBuffer
        }
        byteBuffer.mark()
        Int i = 0
        while (true) {
            Array<Byte> bArr = BEGIN_CERT_HEADER
            if (i >= bArr.length) {
                StringBuilder sb = StringBuilder()
                while (byteBuffer.hasRemaining() && (c = (Char) byteBuffer.get()) != '-') {
                    if (!Character.isWhitespace(c)) {
                        sb.append(c)
                    }
                }
                Int i2 = 1
                while (true) {
                    Array<Byte> bArr2 = END_CERT_FOOTER
                    if (i2 >= bArr2.length) {
                        Array<Byte> bArrDecode = Base64.decode(sb.toString(), 2)
                        Int iPosition = byteBuffer.position()
                        while (byteBuffer.hasRemaining() && Character.isWhitespace((Char) byteBuffer.get())) {
                            iPosition++
                        }
                        byteBuffer.position(iPosition)
                        return ByteBuffer.wrap(bArrDecode)
                    }
                    if (!byteBuffer.hasRemaining()) {
                        throw CertificateException("The provided input contains the PEM certificate header but does not contain sufficient data for the footer")
                    }
                    if (byteBuffer.get() != bArr2[i2]) {
                        throw CertificateException("The provided input contains the PEM certificate header without a valid certificate footer")
                    }
                    i2++
                }
            } else {
                if (byteBuffer.get() != bArr[i]) {
                    byteBuffer.reset()
                    return byteBuffer
                }
                i++
            }
        }
    }
}
