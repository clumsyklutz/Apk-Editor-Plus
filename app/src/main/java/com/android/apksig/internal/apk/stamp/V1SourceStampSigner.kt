package com.android.apksig.internal.apk.stamp

import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.util.Pair
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Map

abstract class V1SourceStampSigner {
    public static val V1_SOURCE_STAMP_BLOCK_ID = 722016414

    public static final class SourceStampBlock {
        public List<Pair<Integer, Array<Byte>>> signedDigests
        public Array<Byte> stampCertificate

        constructor() {
        }
    }

    public static Pair<Array<Byte>, Integer> generateSourceStampBlock(ApkSigningBlockUtils.SignerConfig signerConfig, Map<ContentDigestAlgorithm, Array<Byte>> map) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (signerConfig.certificates.isEmpty()) {
            throw SignatureException("No certificates configured for signer")
        }
        ArrayList arrayList = ArrayList()
        for (Map.Entry<ContentDigestAlgorithm, Array<Byte>> entry : map.entrySet()) {
            arrayList.add(Pair.of(Integer.valueOf(entry.getKey().getId()), entry.getValue()))
        }
        Collections.sort(arrayList, new Comparator<Pair<Integer, Array<Byte>>>() { // from class: com.android.apksig.internal.apk.stamp.V1SourceStampSigner.1
            @Override // java.util.Comparator
            fun compare(Pair<Integer, Array<Byte>> pair, Pair<Integer, Array<Byte>> pair2) {
                return pair.getFirst().compareTo(pair2.getFirst())
            }
        })
        SourceStampBlock sourceStampBlock = SourceStampBlock()
        try {
            sourceStampBlock.stampCertificate = signerConfig.certificates.get(0).getEncoded()
            List<Pair<Integer, Array<Byte>>> listGenerateSignaturesOverData = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(arrayList))
            sourceStampBlock.signedDigests = listGenerateSignaturesOverData
            return Pair.of(ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{sourceStampBlock.stampCertificate, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(listGenerateSignaturesOverData)})), 722016414)
        } catch (CertificateEncodingException e) {
            throw SignatureException("Retrieving the encoded form of the stamp certificate failed", e)
        }
    }
}
