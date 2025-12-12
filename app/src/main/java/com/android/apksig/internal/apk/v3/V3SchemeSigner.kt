package com.android.apksig.internal.apk.v3

import com.android.apksig.SigningCertificateLineage
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import com.android.apksig.util.RunnablesExecutor
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.security.interfaces.ECKey
import java.security.interfaces.RSAKey
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.Map

abstract class V3SchemeSigner {
    public static val APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152
    public static val PROOF_OF_ROTATION_ATTR_ID = 1000370060

    public static Array<Byte> encodeSignedData(V3SchemeSigner$V3SignatureSchemeBlock$SignedData v3SchemeSigner$V3SignatureSchemeBlock$SignedData) {
        Array<Byte> bArrEncodeAsLengthPrefixedElement = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(v3SchemeSigner$V3SignatureSchemeBlock$SignedData.digests))
        Array<Byte> bArrEncodeAsLengthPrefixedElement2 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(v3SchemeSigner$V3SignatureSchemeBlock$SignedData.certificates))
        Array<Byte> bArrEncodeAsLengthPrefixedElement3 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(v3SchemeSigner$V3SignatureSchemeBlock$SignedData.additionalAttributes)
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeAsLengthPrefixedElement.length + bArrEncodeAsLengthPrefixedElement2.length + 4 + 4 + bArrEncodeAsLengthPrefixedElement3.length)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement)
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement2)
        byteBufferAllocate.putInt(v3SchemeSigner$V3SignatureSchemeBlock$SignedData.minSdkVersion)
        byteBufferAllocate.putInt(v3SchemeSigner$V3SignatureSchemeBlock$SignedData.maxSdkVersion)
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement3)
        return byteBufferAllocate.array()
    }

    public static Array<Byte> encodeSigner(V3SchemeSigner$V3SignatureSchemeBlock$Signer v3SchemeSigner$V3SignatureSchemeBlock$Signer) {
        Array<Byte> bArrEncodeAsLengthPrefixedElement = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(v3SchemeSigner$V3SignatureSchemeBlock$Signer.signedData)
        Array<Byte> bArrEncodeAsLengthPrefixedElement2 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(v3SchemeSigner$V3SignatureSchemeBlock$Signer.signatures))
        Array<Byte> bArrEncodeAsLengthPrefixedElement3 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(v3SchemeSigner$V3SignatureSchemeBlock$Signer.publicKey)
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeAsLengthPrefixedElement.length + 4 + 4 + bArrEncodeAsLengthPrefixedElement2.length + bArrEncodeAsLengthPrefixedElement3.length)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement)
        byteBufferAllocate.putInt(v3SchemeSigner$V3SignatureSchemeBlock$Signer.minSdkVersion)
        byteBufferAllocate.putInt(v3SchemeSigner$V3SignatureSchemeBlock$Signer.maxSdkVersion)
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement2)
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement3)
        return byteBufferAllocate.array()
    }

    public static Array<Byte> generateAdditionalAttributes(ApkSigningBlockUtils.SignerConfig signerConfig) {
        SigningCertificateLineage signingCertificateLineage = signerConfig.mSigningCertificateLineage
        return signingCertificateLineage == null ? new Byte[0] : generateV3SignerAttribute(signingCertificateLineage)
    }

    public static ApkSigningBlockUtils.SigningSchemeBlockAndDigests generateApkSignatureSchemeV3Block(RunnablesExecutor runnablesExecutor, DataSource dataSource, DataSource dataSource2, DataSource dataSource3, List<ApkSigningBlockUtils.SignerConfig> list) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        Pair<List<ApkSigningBlockUtils.SignerConfig>, Map<ContentDigestAlgorithm, Array<Byte>>> pairComputeContentDigests = ApkSigningBlockUtils.computeContentDigests(runnablesExecutor, dataSource, dataSource2, dataSource3, list)
        return new ApkSigningBlockUtils.SigningSchemeBlockAndDigests(generateApkSignatureSchemeV3Block(pairComputeContentDigests.getFirst(), pairComputeContentDigests.getSecond()), pairComputeContentDigests.getSecond())
    }

    public static Pair<Array<Byte>, Integer> generateApkSignatureSchemeV3Block(List<ApkSigningBlockUtils.SignerConfig> list, Map<ContentDigestAlgorithm, Array<Byte>> map) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        ArrayList arrayList = ArrayList(list.size())
        Iterator<ApkSigningBlockUtils.SignerConfig> it = list.iterator()
        Int i = 0
        while (it.hasNext()) {
            i++
            try {
                arrayList.add(generateSignerBlock(it.next(), map))
            } catch (InvalidKeyException e) {
                throw InvalidKeyException("Signer #" + i + " failed", e)
            } catch (SignatureException e2) {
                throw SignatureException("Signer #" + i + " failed", e2)
            }
        }
        return Pair.of(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(arrayList)}), -262969152)
    }

    public static Array<Byte> generateSignerBlock(ApkSigningBlockUtils.SignerConfig signerConfig, Map<ContentDigestAlgorithm, Array<Byte>> map) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (signerConfig.certificates.isEmpty()) {
            throw SignatureException("No certificates configured for signer")
        }
        Array<Byte> bArrEncodePublicKey = ApkSigningBlockUtils.encodePublicKey(signerConfig.certificates.get(0).getPublicKey())
        V3SchemeSigner$V3SignatureSchemeBlock$SignedData v3SchemeSigner$V3SignatureSchemeBlock$SignedData = new V3SchemeSigner$V3SignatureSchemeBlock$SignedData()
        try {
            v3SchemeSigner$V3SignatureSchemeBlock$SignedData.certificates = ApkSigningBlockUtils.encodeCertificates(signerConfig.certificates)
            ArrayList arrayList = ArrayList(signerConfig.signatureAlgorithms.size())
            for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
                ContentDigestAlgorithm contentDigestAlgorithm = signatureAlgorithm.getContentDigestAlgorithm()
                Array<Byte> bArr = map.get(contentDigestAlgorithm)
                if (bArr == null) {
                    throw RuntimeException(contentDigestAlgorithm + " content digest for " + signatureAlgorithm + " not computed")
                }
                arrayList.add(Pair.of(Integer.valueOf(signatureAlgorithm.getId()), bArr))
            }
            v3SchemeSigner$V3SignatureSchemeBlock$SignedData.digests = arrayList
            v3SchemeSigner$V3SignatureSchemeBlock$SignedData.minSdkVersion = signerConfig.minSdkVersion
            v3SchemeSigner$V3SignatureSchemeBlock$SignedData.maxSdkVersion = signerConfig.maxSdkVersion
            v3SchemeSigner$V3SignatureSchemeBlock$SignedData.additionalAttributes = generateAdditionalAttributes(signerConfig)
            V3SchemeSigner$V3SignatureSchemeBlock$Signer v3SchemeSigner$V3SignatureSchemeBlock$Signer = new V3SchemeSigner$V3SignatureSchemeBlock$Signer()
            Array<Byte> bArrEncodeSignedData = encodeSignedData(v3SchemeSigner$V3SignatureSchemeBlock$SignedData)
            v3SchemeSigner$V3SignatureSchemeBlock$Signer.signedData = bArrEncodeSignedData
            v3SchemeSigner$V3SignatureSchemeBlock$Signer.minSdkVersion = signerConfig.minSdkVersion
            v3SchemeSigner$V3SignatureSchemeBlock$Signer.maxSdkVersion = signerConfig.maxSdkVersion
            v3SchemeSigner$V3SignatureSchemeBlock$Signer.publicKey = bArrEncodePublicKey
            v3SchemeSigner$V3SignatureSchemeBlock$Signer.signatures = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, bArrEncodeSignedData)
            return encodeSigner(v3SchemeSigner$V3SignatureSchemeBlock$Signer)
        } catch (CertificateEncodingException e) {
            throw SignatureException("Failed to encode certificates", e)
        }
    }

    public static Array<Byte> generateV3SignerAttribute(SigningCertificateLineage signingCertificateLineage) {
        Array<Byte> bArrEncodeSigningCertificateLineage = signingCertificateLineage.encodeSigningCertificateLineage()
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeSigningCertificateLineage.length + 8)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.putInt(bArrEncodeSigningCertificateLineage.length + 4)
        byteBufferAllocate.putInt(1000370060)
        byteBufferAllocate.put(bArrEncodeSigningCertificateLineage)
        return byteBufferAllocate.array()
    }

    public static List<SignatureAlgorithm> getSuggestedSignatureAlgorithms(PublicKey publicKey, Int i, Boolean z) throws InvalidKeyException {
        String algorithm = publicKey.getAlgorithm()
        if ("RSA".equalsIgnoreCase(algorithm)) {
            if (((RSAKey) publicKey).getModulus().bitLength() > 3072) {
                return Collections.singletonList(SignatureAlgorithm.RSA_PKCS1_V1_5_WITH_SHA512)
            }
            ArrayList arrayList = ArrayList()
            arrayList.add(SignatureAlgorithm.RSA_PKCS1_V1_5_WITH_SHA256)
            if (z) {
                arrayList.add(SignatureAlgorithm.VERITY_RSA_PKCS1_V1_5_WITH_SHA256)
            }
            return arrayList
        }
        if ("DSA".equalsIgnoreCase(algorithm)) {
            ArrayList arrayList2 = ArrayList()
            arrayList2.add(SignatureAlgorithm.DSA_WITH_SHA256)
            if (z) {
                arrayList2.add(SignatureAlgorithm.VERITY_DSA_WITH_SHA256)
            }
            return arrayList2
        }
        if (!"EC".equalsIgnoreCase(algorithm)) {
            throw InvalidKeyException("Unsupported key algorithm: " + algorithm)
        }
        if (((ECKey) publicKey).getParams().getOrder().bitLength() > 256) {
            return Collections.singletonList(SignatureAlgorithm.ECDSA_WITH_SHA512)
        }
        ArrayList arrayList3 = ArrayList()
        arrayList3.add(SignatureAlgorithm.ECDSA_WITH_SHA256)
        if (z) {
            arrayList3.add(SignatureAlgorithm.VERITY_ECDSA_WITH_SHA256)
        }
        return arrayList3
    }
}
