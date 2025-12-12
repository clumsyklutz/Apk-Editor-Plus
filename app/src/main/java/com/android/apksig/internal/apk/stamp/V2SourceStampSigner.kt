package com.android.apksig.internal.apk.stamp

import com.android.apksig.SigningCertificateLineage
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.util.Pair
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.HashMap
import java.util.Iterator
import java.util.List
import java.util.Map

abstract class V2SourceStampSigner {
    public static val V2_SOURCE_STAMP_BLOCK_ID = 1845461005

    public static final class SourceStampBlock {
        public List<Pair<Integer, Array<Byte>>> signedDigests
        public List<Pair<Integer, Array<Byte>>> signedStampAttributes
        public Array<Byte> stampAttributes
        public Array<Byte> stampCertificate

        constructor() {
        }
    }

    public static Array<Byte> encodeStampAttributes(Map<Integer, Array<Byte>> map) {
        Iterator<Array<Byte>> it = map.values().iterator()
        Int length = 0
        while (it.hasNext()) {
            length += it.next().length + 8
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length + 4)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.putInt(length)
        for (Map.Entry<Integer, Array<Byte>> entry : map.entrySet()) {
            byteBufferAllocate.putInt(entry.getValue().length + 4)
            byteBufferAllocate.putInt(entry.getKey().intValue())
            byteBufferAllocate.put(entry.getValue())
        }
        return byteBufferAllocate.array()
    }

    public static Pair<Array<Byte>, Integer> generateSourceStampBlock(ApkSigningBlockUtils.SignerConfig signerConfig, Map<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> map) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (signerConfig.certificates.isEmpty()) {
            throw SignatureException("No certificates configured for signer")
        }
        ArrayList arrayList = ArrayList()
        getSignedDigestsFor(3, map, signerConfig, arrayList)
        getSignedDigestsFor(2, map, signerConfig, arrayList)
        getSignedDigestsFor(1, map, signerConfig, arrayList)
        Collections.sort(arrayList, new Comparator<Pair<Integer, Array<Byte>>>() { // from class: com.android.apksig.internal.apk.stamp.V2SourceStampSigner.1
            @Override // java.util.Comparator
            fun compare(Pair<Integer, Array<Byte>> pair, Pair<Integer, Array<Byte>> pair2) {
                return pair.getFirst().compareTo(pair2.getFirst())
            }
        })
        SourceStampBlock sourceStampBlock = SourceStampBlock()
        try {
            sourceStampBlock.stampCertificate = signerConfig.certificates.get(0).getEncoded()
            sourceStampBlock.signedDigests = arrayList
            Array<Byte> bArrEncodeStampAttributes = encodeStampAttributes(generateStampAttributes(signerConfig.mSigningCertificateLineage))
            sourceStampBlock.stampAttributes = bArrEncodeStampAttributes
            sourceStampBlock.signedStampAttributes = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, bArrEncodeStampAttributes)
            return Pair.of(ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{sourceStampBlock.stampCertificate, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(sourceStampBlock.signedDigests), sourceStampBlock.stampAttributes, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(sourceStampBlock.signedStampAttributes)})), 1845461005)
        } catch (CertificateEncodingException e) {
            throw SignatureException("Retrieving the encoded form of the stamp certificate failed", e)
        }
    }

    public static Map<Integer, Array<Byte>> generateStampAttributes(SigningCertificateLineage signingCertificateLineage) {
        HashMap map = HashMap()
        if (signingCertificateLineage != null) {
            map.put(Integer.valueOf(SourceStampConstants.PROOF_OF_ROTATION_ATTR_ID), signingCertificateLineage.encodeSigningCertificateLineage())
        }
        return map
    }

    fun getSignedDigestsFor(Int i, Map<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> map, ApkSigningBlockUtils.SignerConfig signerConfig, List<Pair<Integer, Array<Byte>>> list) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (map.containsKey(Integer.valueOf(i))) {
            Map<ContentDigestAlgorithm, Array<Byte>> map2 = map.get(Integer.valueOf(i))
            ArrayList arrayList = ArrayList()
            for (Map.Entry<ContentDigestAlgorithm, Array<Byte>> entry : map2.entrySet()) {
                arrayList.add(Pair.of(Integer.valueOf(entry.getKey().getId()), entry.getValue()))
            }
            Collections.sort(arrayList, new Comparator<Pair<Integer, Array<Byte>>>() { // from class: com.android.apksig.internal.apk.stamp.V2SourceStampSigner.2
                @Override // java.util.Comparator
                fun compare(Pair<Integer, Array<Byte>> pair, Pair<Integer, Array<Byte>> pair2) {
                    return pair.getFirst().compareTo(pair2.getFirst())
                }
            })
            list.add(Pair.of(Integer.valueOf(i), ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(arrayList)))))
        }
    }
}
