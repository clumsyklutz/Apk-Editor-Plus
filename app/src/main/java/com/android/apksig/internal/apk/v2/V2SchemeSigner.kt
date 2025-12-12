package com.android.apksig.internal.apk.v2

import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.util.Pair
import com.android.apksig.util.DataSource
import com.android.apksig.util.RunnablesExecutor
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.InvalidAlgorithmParameterException
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

abstract class V2SchemeSigner {
    public static val APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818

    public static Array<Byte> generateAdditionalAttributes(Boolean z) {
        if (!z) {
            return new Byte[0]
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(12)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.putInt(8)
        byteBufferAllocate.putInt(V2SchemeConstants.STRIPPING_PROTECTION_ATTR_ID)
        byteBufferAllocate.putInt(3)
        return byteBufferAllocate.array()
    }

    public static ApkSigningBlockUtils.SigningSchemeBlockAndDigests generateApkSignatureSchemeV2Block(RunnablesExecutor runnablesExecutor, DataSource dataSource, DataSource dataSource2, DataSource dataSource3, List<ApkSigningBlockUtils.SignerConfig> list, Boolean z) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        Pair<List<ApkSigningBlockUtils.SignerConfig>, Map<ContentDigestAlgorithm, Array<Byte>>> pairComputeContentDigests = ApkSigningBlockUtils.computeContentDigests(runnablesExecutor, dataSource, dataSource2, dataSource3, list)
        return new ApkSigningBlockUtils.SigningSchemeBlockAndDigests(generateApkSignatureSchemeV2Block(pairComputeContentDigests.getFirst(), pairComputeContentDigests.getSecond(), z), pairComputeContentDigests.getSecond())
    }

    public static Pair<Array<Byte>, Integer> generateApkSignatureSchemeV2Block(List<ApkSigningBlockUtils.SignerConfig> list, Map<ContentDigestAlgorithm, Array<Byte>> map, Boolean z) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        ArrayList arrayList = ArrayList(list.size())
        Iterator<ApkSigningBlockUtils.SignerConfig> it = list.iterator()
        Int i = 0
        while (it.hasNext()) {
            i++
            try {
                arrayList.add(generateSignerBlock(it.next(), map, z))
            } catch (InvalidKeyException e) {
                throw InvalidKeyException("Signer #" + i + " failed", e)
            } catch (SignatureException e2) {
                throw SignatureException("Signer #" + i + " failed", e2)
            }
        }
        return Pair.of(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(arrayList)}), 1896449818)
    }

    public static Array<Byte> generateSignerBlock(ApkSigningBlockUtils.SignerConfig signerConfig, Map<ContentDigestAlgorithm, Array<Byte>> map, Boolean z) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (signerConfig.certificates.isEmpty()) {
            throw SignatureException("No certificates configured for signer")
        }
        Array<Byte> bArrEncodePublicKey = ApkSigningBlockUtils.encodePublicKey(signerConfig.certificates.get(0).getPublicKey())
        V2SchemeSigner$V2SignatureSchemeBlock$SignedData v2SchemeSigner$V2SignatureSchemeBlock$SignedData = new V2SchemeSigner$V2SignatureSchemeBlock$SignedData()
        try {
            v2SchemeSigner$V2SignatureSchemeBlock$SignedData.certificates = ApkSigningBlockUtils.encodeCertificates(signerConfig.certificates)
            ArrayList arrayList = ArrayList(signerConfig.signatureAlgorithms.size())
            for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
                ContentDigestAlgorithm contentDigestAlgorithm = signatureAlgorithm.getContentDigestAlgorithm()
                Array<Byte> bArr = map.get(contentDigestAlgorithm)
                if (bArr == null) {
                    throw RuntimeException(contentDigestAlgorithm + " content digest for " + signatureAlgorithm + " not computed")
                }
                arrayList.add(Pair.of(Integer.valueOf(signatureAlgorithm.getId()), bArr))
            }
            v2SchemeSigner$V2SignatureSchemeBlock$SignedData.digests = arrayList
            v2SchemeSigner$V2SignatureSchemeBlock$SignedData.additionalAttributes = generateAdditionalAttributes(z)
            V2SchemeSigner$V2SignatureSchemeBlock$Signer v2SchemeSigner$V2SignatureSchemeBlock$Signer = new V2SchemeSigner$V2SignatureSchemeBlock$Signer()
            v2SchemeSigner$V2SignatureSchemeBlock$Signer.signedData = ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(v2SchemeSigner$V2SignatureSchemeBlock$SignedData.digests), ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(v2SchemeSigner$V2SignatureSchemeBlock$SignedData.certificates), v2SchemeSigner$V2SignatureSchemeBlock$SignedData.additionalAttributes, new Byte[0]})
            v2SchemeSigner$V2SignatureSchemeBlock$Signer.publicKey = bArrEncodePublicKey
            v2SchemeSigner$V2SignatureSchemeBlock$Signer.signatures = ArrayList()
            List<Pair<Integer, Array<Byte>>> listGenerateSignaturesOverData = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, v2SchemeSigner$V2SignatureSchemeBlock$Signer.signedData)
            v2SchemeSigner$V2SignatureSchemeBlock$Signer.signatures = listGenerateSignaturesOverData
            return ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{v2SchemeSigner$V2SignatureSchemeBlock$Signer.signedData, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(listGenerateSignaturesOverData), v2SchemeSigner$V2SignatureSchemeBlock$Signer.publicKey})
        } catch (CertificateEncodingException e) {
            throw SignatureException("Failed to encode certificates", e)
        }
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
