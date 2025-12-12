package com.android.apksig.internal.util

import java.security.cert.CertificateEncodingException
import java.security.cert.X509Certificate
import java.util.Arrays

class GuaranteedEncodedFormX509Certificate extends DelegatingX509Certificate {
    public final Array<Byte> mEncodedForm
    public Int mHash

    constructor(X509Certificate x509Certificate, Array<Byte> bArr) {
        super(x509Certificate)
        this.mHash = -1
        this.mEncodedForm = bArr != null ? (Array<Byte>) bArr.clone() : null
    }

    @Override // java.security.cert.Certificate
    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is X509Certificate)) {
            return false
        }
        try {
            return Arrays.equals(getEncoded(), ((X509Certificate) obj).getEncoded())
        } catch (CertificateEncodingException unused) {
            return false
        }
    }

    @Override // com.android.apksig.internal.util.DelegatingX509Certificate, java.security.cert.Certificate
    public Array<Byte> getEncoded() throws CertificateEncodingException {
        Array<Byte> bArr = this.mEncodedForm
        if (bArr != null) {
            return (Array<Byte>) bArr.clone()
        }
        return null
    }

    @Override // java.security.cert.Certificate
    fun hashCode() {
        if (this.mHash == -1) {
            try {
                this.mHash = Arrays.hashCode(getEncoded())
            } catch (CertificateEncodingException unused) {
                this.mHash = 0
            }
        }
        return this.mHash
    }
}
