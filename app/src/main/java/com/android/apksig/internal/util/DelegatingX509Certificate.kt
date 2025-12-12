package com.android.apksig.internal.util

import android.os.Build
import java.math.BigInteger
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.Principal
import java.security.Provider
import java.security.PublicKey
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.security.cert.CertificateException
import java.security.cert.CertificateExpiredException
import java.security.cert.CertificateNotYetValidException
import java.security.cert.CertificateParsingException
import java.security.cert.X509Certificate
import java.util.Collection
import java.util.Date
import java.util.List
import java.util.Set
import javax.security.auth.x500.X500Principal

class DelegatingX509Certificate extends X509Certificate {
    public final X509Certificate mDelegate

    constructor(X509Certificate x509Certificate) {
        this.mDelegate = x509Certificate
    }

    @Override // java.security.cert.X509Certificate
    fun checkValidity() throws CertificateNotYetValidException, CertificateExpiredException {
        this.mDelegate.checkValidity()
    }

    @Override // java.security.cert.X509Certificate
    fun checkValidity(Date date) throws CertificateNotYetValidException, CertificateExpiredException {
        this.mDelegate.checkValidity(date)
    }

    @Override // java.security.cert.X509Certificate
    fun getBasicConstraints() {
        return this.mDelegate.getBasicConstraints()
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getCriticalExtensionOIDs() {
        return this.mDelegate.getCriticalExtensionOIDs()
    }

    @Override // java.security.cert.Certificate
    public Array<Byte> getEncoded() throws CertificateEncodingException {
        return this.mDelegate.getEncoded()
    }

    @Override // java.security.cert.X509Certificate
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return this.mDelegate.getExtendedKeyUsage()
    }

    @Override // java.security.cert.X509Extension
    public Array<Byte> getExtensionValue(String str) {
        return this.mDelegate.getExtensionValue(str)
    }

    @Override // java.security.cert.X509Certificate
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return this.mDelegate.getIssuerAlternativeNames()
    }

    @Override // java.security.cert.X509Certificate
    fun getIssuerDN() {
        return this.mDelegate.getIssuerDN()
    }

    @Override // java.security.cert.X509Certificate
    public Array<Boolean> getIssuerUniqueID() {
        return this.mDelegate.getIssuerUniqueID()
    }

    @Override // java.security.cert.X509Certificate
    fun getIssuerX500Principal() {
        return this.mDelegate.getIssuerX500Principal()
    }

    @Override // java.security.cert.X509Certificate
    public Array<Boolean> getKeyUsage() {
        return this.mDelegate.getKeyUsage()
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getNonCriticalExtensionOIDs() {
        return this.mDelegate.getNonCriticalExtensionOIDs()
    }

    @Override // java.security.cert.X509Certificate
    fun getNotAfter() {
        return this.mDelegate.getNotAfter()
    }

    @Override // java.security.cert.X509Certificate
    fun getNotBefore() {
        return this.mDelegate.getNotBefore()
    }

    @Override // java.security.cert.Certificate
    fun getPublicKey() {
        return this.mDelegate.getPublicKey()
    }

    @Override // java.security.cert.X509Certificate
    fun getSerialNumber() {
        return this.mDelegate.getSerialNumber()
    }

    @Override // java.security.cert.X509Certificate
    fun getSigAlgName() {
        return this.mDelegate.getSigAlgName()
    }

    @Override // java.security.cert.X509Certificate
    fun getSigAlgOID() {
        return this.mDelegate.getSigAlgOID()
    }

    @Override // java.security.cert.X509Certificate
    public Array<Byte> getSigAlgParams() {
        return this.mDelegate.getSigAlgParams()
    }

    @Override // java.security.cert.X509Certificate
    public Array<Byte> getSignature() {
        return this.mDelegate.getSignature()
    }

    @Override // java.security.cert.X509Certificate
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return this.mDelegate.getSubjectAlternativeNames()
    }

    @Override // java.security.cert.X509Certificate
    fun getSubjectDN() {
        return this.mDelegate.getSubjectDN()
    }

    @Override // java.security.cert.X509Certificate
    public Array<Boolean> getSubjectUniqueID() {
        return this.mDelegate.getSubjectUniqueID()
    }

    @Override // java.security.cert.X509Certificate
    fun getSubjectX500Principal() {
        return this.mDelegate.getSubjectX500Principal()
    }

    @Override // java.security.cert.X509Certificate
    public Array<Byte> getTBSCertificate() throws CertificateEncodingException {
        return this.mDelegate.getTBSCertificate()
    }

    @Override // java.security.cert.X509Certificate
    fun getVersion() {
        return this.mDelegate.getVersion()
    }

    @Override // java.security.cert.X509Extension
    fun hasUnsupportedCriticalExtension() {
        return this.mDelegate.hasUnsupportedCriticalExtension()
    }

    @Override // java.security.cert.Certificate
    fun toString() {
        return this.mDelegate.toString()
    }

    @Override // java.security.cert.Certificate
    fun verify(PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        this.mDelegate.verify(publicKey)
    }

    @Override // java.security.cert.Certificate
    fun verify(PublicKey publicKey, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        this.mDelegate.verify(publicKey, str)
    }

    @Override // java.security.cert.X509Certificate, java.security.cert.Certificate
    fun verify(PublicKey publicKey, Provider provider) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException {
        if (Build.VERSION.SDK_INT < 24) {
            throw UnsupportedOperationException("Not supported before API 24")
        }
        this.mDelegate.verify(publicKey, provider)
    }
}
