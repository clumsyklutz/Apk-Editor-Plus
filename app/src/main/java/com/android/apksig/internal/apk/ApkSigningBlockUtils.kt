package com.android.apksig.internal.apk

import android.support.v4.media.session.PlaybackStateCompat
import com.android.apksig.ApkVerifier
import com.android.apksig.SigningCertificateLineage
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.asn1.Asn1BerParser
import com.android.apksig.internal.asn1.Asn1DecodingException
import com.android.apksig.internal.asn1.Asn1DerEncoder
import com.android.apksig.internal.asn1.Asn1EncodingException
import com.android.apksig.internal.asn1.Asn1OpaqueObject
import com.android.apksig.internal.pkcs7.AlgorithmIdentifier
import com.android.apksig.internal.pkcs7.ContentInfo
import com.android.apksig.internal.pkcs7.EncapsulatedContentInfo
import com.android.apksig.internal.pkcs7.IssuerAndSerialNumber
import com.android.apksig.internal.pkcs7.Pkcs7Constants
import com.android.apksig.internal.pkcs7.SignedData
import com.android.apksig.internal.pkcs7.SignerIdentifier
import com.android.apksig.internal.pkcs7.SignerInfo
import com.android.apksig.internal.util.ByteBufferDataSource
import com.android.apksig.internal.util.ChainedDataSource
import com.android.apksig.internal.util.Pair
import com.android.apksig.internal.util.SupplierCompat
import com.android.apksig.internal.util.VerityTreeBuilder
import com.android.apksig.internal.x509.RSAPublicKey
import com.android.apksig.internal.x509.SubjectPublicKeyInfo
import com.android.apksig.internal.zip.ZipUtils
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSinks
import com.android.apksig.util.DataSource
import com.android.apksig.util.DataSources
import com.android.apksig.util.RunnablesExecutor
import com.android.apksig.util.RunnablesProvider
import java.io.IOException
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.DigestException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.SignatureException
import java.security.cert.CertificateEncodingException
import java.security.cert.X509Certificate
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set
import java.util.concurrent.atomic.AtomicInteger

class ApkSigningBlockUtils {
    public static val ANDROID_COMMON_PAGE_ALIGNMENT_BYTES = 4096
    public static final Array<Byte> APK_SIGNING_BLOCK_MAGIC = {65, 80, 75, 32, 83, 105, 103, 32, 66, 108, 111, 99, 107, 32, 52, 50}
    public static final Array<ContentDigestAlgorithm> V4_CONTENT_DIGEST_ALGORITHMS = {ContentDigestAlgorithm.CHUNKED_SHA512, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, ContentDigestAlgorithm.CHUNKED_SHA256}
    public static val VERSION_APK_SIGNATURE_SCHEME_V2 = 2
    public static val VERSION_APK_SIGNATURE_SCHEME_V3 = 3
    public static val VERSION_APK_SIGNATURE_SCHEME_V4 = 4
    public static val VERSION_JAR_SIGNATURE_SCHEME = 1
    public static val VERSION_SOURCE_STAMP = 0

    public static class ChunkDigester implements Runnable {
        public final List<ChunkDigests> chunkDigests
        public final ChunkSupplier dataSupplier
        public final DataSink mdSink
        public final List<MessageDigest> messageDigests

        constructor(ChunkSupplier chunkSupplier, List<ChunkDigests> list) {
            this.dataSupplier = chunkSupplier
            this.chunkDigests = list
            this.messageDigests = ArrayList(list.size())
            Iterator<ChunkDigests> it = list.iterator()
            while (it.hasNext()) {
                try {
                    this.messageDigests.add(it.next().createMessageDigest())
                } catch (NoSuchAlgorithmException e) {
                    throw RuntimeException(e)
                }
            }
            this.mdSink = DataSinks.asDataSink((Array<MessageDigest>) this.messageDigests.toArray(new MessageDigest[0]))
        }

        @Override // java.lang.Runnable
        fun run() throws DigestException {
            Array<Byte> bArr = new Byte[5]
            bArr[0] = -91
            try {
                ChunkSupplier.Chunk chunk = this.dataSupplier.get()
                while (chunk != null) {
                    Int i = chunk.size
                    if (i > PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
                        throw RuntimeException("Chunk size greater than expected: " + i)
                    }
                    ApkSigningBlockUtils.setUnsignedInt32LittleEndian(i, bArr, 1)
                    this.mdSink.consume(bArr, 0, 5)
                    this.mdSink.consume(chunk.data)
                    for (Int i2 = 0; i2 < this.chunkDigests.size(); i2++) {
                        ChunkDigests chunkDigests = this.chunkDigests.get(i2)
                        Int iDigest = this.messageDigests.get(i2).digest(chunkDigests.concatOfDigestsOfChunks, chunkDigests.getOffset(chunk.chunkIndex), chunkDigests.digestOutputSize)
                        if (iDigest != chunkDigests.digestOutputSize) {
                            throw RuntimeException("Unexpected output size of " + chunkDigests.algorithm + " digest: " + iDigest)
                        }
                    }
                    chunk = this.dataSupplier.get()
                }
            } catch (IOException | DigestException e) {
                throw RuntimeException(e)
            }
        }
    }

    public static class ChunkDigests {
        public final ContentDigestAlgorithm algorithm
        public final Array<Byte> concatOfDigestsOfChunks
        public final Int digestOutputSize

        constructor(ContentDigestAlgorithm contentDigestAlgorithm, Int i) {
            this.algorithm = contentDigestAlgorithm
            Int chunkDigestOutputSizeBytes = contentDigestAlgorithm.getChunkDigestOutputSizeBytes()
            this.digestOutputSize = chunkDigestOutputSizeBytes
            Array<Byte> bArr = new Byte[(chunkDigestOutputSizeBytes * i) + 5]
            this.concatOfDigestsOfChunks = bArr
            bArr[0] = 90
            ApkSigningBlockUtils.setUnsignedInt32LittleEndian(i, bArr, 1)
        }

        public final MessageDigest createMessageDigest() throws NoSuchAlgorithmException {
            return MessageDigest.getInstance(this.algorithm.getJcaMessageDigestAlgorithm())
        }

        public final Int getOffset(Int i) {
            return (i * this.digestOutputSize) + 5
        }
    }

    public static class ChunkSupplier implements SupplierCompat<Chunk> {
        public final Array<Int> chunkCounts
        public final Array<DataSource> dataSources
        public final AtomicInteger nextIndex
        public final Int totalChunkCount

        public static class Chunk {
            public final Int chunkIndex
            public final ByteBuffer data
            public final Int size

            constructor(Int i, ByteBuffer byteBuffer, Int i2) {
                this.chunkIndex = i
                this.data = byteBuffer
                this.size = i2
            }
        }

        constructor(Array<DataSource> dataSourceArr) {
            this.dataSources = dataSourceArr
            this.chunkCounts = new Int[dataSourceArr.length]
            Int i = 0
            for (Int i2 = 0; i2 < dataSourceArr.length; i2++) {
                Long chunkCount = ApkSigningBlockUtils.getChunkCount(dataSourceArr[i2].size(), PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED)
                if (chunkCount > 2147483647L) {
                    throw RuntimeException(String.format("Number of chunks in dataSource[%d] is greater than max Int.", Integer.valueOf(i2)))
                }
                this.chunkCounts[i2] = (Int) chunkCount
                i = (Int) (i + chunkCount)
            }
            this.totalChunkCount = i
            this.nextIndex = AtomicInteger(0)
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.apksig.internal.util.SupplierCompat
        fun get() {
            Array<DataSource> dataSourceArr
            Int andIncrement = this.nextIndex.getAndIncrement()
            if (andIncrement < 0 || andIncrement >= this.totalChunkCount) {
                return null
            }
            Int i = 0
            Long j = andIncrement
            while (true) {
                dataSourceArr = this.dataSources
                if (i >= dataSourceArr.length) {
                    break
                }
                Array<Int> iArr = this.chunkCounts
                if (j < iArr[i]) {
                    break
                }
                j -= iArr[i]
                i++
            }
            Long size = dataSourceArr[i].size()
            Long j2 = j * PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED
            Int iMin = (Int) Math.min(size - j2, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED)
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iMin)
            try {
                this.dataSources[i].copyTo(j2, iMin, byteBufferAllocate)
                byteBufferAllocate.rewind()
                return Chunk(andIncrement, byteBufferAllocate, iMin)
            } catch (IOException e) {
                throw IllegalStateException("Failed to read chunk", e)
            }
        }
    }

    public static class NoSupportedSignaturesException extends NoApkSupportedSignaturesException {
        constructor(String str) {
            super(str)
        }
    }

    public static class Result extends ApkSigResult {
        public final List<ApkVerifier.IssueWithParams> mErrors
        public final List<ApkVerifier.IssueWithParams> mWarnings
        public final List<SignerInfo> signers
        public SigningCertificateLineage signingCertificateLineage

        public static class SignerInfo extends ApkSignerInfo {
            public Int maxSdkVersion
            public Int minSdkVersion
            public Array<Byte> signedData
            public SigningCertificateLineage signingCertificateLineage
            public List<ContentDigest> contentDigests = ArrayList()
            public Map<ContentDigestAlgorithm, Array<Byte>> verifiedContentDigests = HashMap()
            public List<Signature> signatures = ArrayList()
            public Map<SignatureAlgorithm, Array<Byte>> verifiedSignatures = HashMap()
            public List<AdditionalAttribute> additionalAttributes = ArrayList()
            public final List<ApkVerifier.IssueWithParams> mWarnings = ArrayList()
            public final List<ApkVerifier.IssueWithParams> mErrors = ArrayList()

            public static class AdditionalAttribute {
                public final Int mId
                public final Array<Byte> mValue

                constructor(Int i, Array<Byte> bArr) {
                    this.mId = i
                    this.mValue = (Array<Byte>) bArr.clone()
                }

                fun getId() {
                    return this.mId
                }

                public Array<Byte> getValue() {
                    return (Array<Byte>) this.mValue.clone()
                }
            }

            public static class ContentDigest {
                public final Int mSignatureAlgorithmId
                public final Array<Byte> mValue

                constructor(Int i, Array<Byte> bArr) {
                    this.mSignatureAlgorithmId = i
                    this.mValue = bArr
                }

                fun getSignatureAlgorithmId() {
                    return this.mSignatureAlgorithmId
                }

                public Array<Byte> getValue() {
                    return this.mValue
                }
            }

            public static class Signature {
                public final Int mAlgorithmId
                public final Array<Byte> mValue

                constructor(Int i, Array<Byte> bArr) {
                    this.mAlgorithmId = i
                    this.mValue = bArr
                }

                fun getAlgorithmId() {
                    return this.mAlgorithmId
                }

                public Array<Byte> getValue() {
                    return this.mValue
                }
            }

            fun addError(ApkVerifier.Issue issue, Object... objArr) {
                this.mErrors.add(new ApkVerifier.IssueWithParams(issue, objArr))
            }

            fun addWarning(ApkVerifier.Issue issue, Object... objArr) {
                this.mWarnings.add(new ApkVerifier.IssueWithParams(issue, objArr))
            }

            @Override // com.android.apksig.internal.apk.ApkSignerInfo
            fun containsErrors() {
                return !this.mErrors.isEmpty()
            }

            @Override // com.android.apksig.internal.apk.ApkSignerInfo
            fun containsWarnings() {
                return !this.mWarnings.isEmpty()
            }

            @Override // com.android.apksig.internal.apk.ApkSignerInfo
            public List<ApkVerifier.IssueWithParams> getErrors() {
                return this.mErrors
            }

            @Override // com.android.apksig.internal.apk.ApkSignerInfo
            public List<ApkVerifier.IssueWithParams> getWarnings() {
                return this.mWarnings
            }
        }

        constructor(Int i) {
            super(i)
            this.signingCertificateLineage = null
            this.signers = ArrayList()
            this.mWarnings = ArrayList()
            this.mErrors = ArrayList()
        }

        fun addError(ApkVerifier.Issue issue, Object... objArr) {
            this.mErrors.add(new ApkVerifier.IssueWithParams(issue, objArr))
        }

        fun addWarning(ApkVerifier.Issue issue, Object... objArr) {
            this.mWarnings.add(new ApkVerifier.IssueWithParams(issue, objArr))
        }

        @Override // com.android.apksig.internal.apk.ApkSigResult
        fun containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true
            }
            if (this.signers.isEmpty()) {
                return false
            }
            Iterator<SignerInfo> it = this.signers.iterator()
            while (it.hasNext()) {
                if (it.next().containsErrors()) {
                    return true
                }
            }
            return false
        }

        @Override // com.android.apksig.internal.apk.ApkSigResult
        fun containsWarnings() {
            if (!this.mWarnings.isEmpty()) {
                return true
            }
            if (this.signers.isEmpty()) {
                return false
            }
            Iterator<SignerInfo> it = this.signers.iterator()
            while (it.hasNext()) {
                if (it.next().containsWarnings()) {
                    return true
                }
            }
            return false
        }

        @Override // com.android.apksig.internal.apk.ApkSigResult
        public List<ApkVerifier.IssueWithParams> getErrors() {
            return this.mErrors
        }

        @Override // com.android.apksig.internal.apk.ApkSigResult
        public List<ApkVerifier.IssueWithParams> getWarnings() {
            return this.mWarnings
        }
    }

    public static class SignatureNotFoundException extends Exception {
        constructor(String str) {
            super(str)
        }

        constructor(String str, Throwable th) {
            super(str, th)
        }
    }

    public static class SignerConfig {
        public List<X509Certificate> certificates
        public SigningCertificateLineage mSigningCertificateLineage
        public Int maxSdkVersion
        public Int minSdkVersion
        public PrivateKey privateKey
        public List<SignatureAlgorithm> signatureAlgorithms
    }

    public static class SigningSchemeBlockAndDigests {
        public final Map<ContentDigestAlgorithm, Array<Byte>> digestInfo
        public final Pair<Array<Byte>, Integer> signingSchemeBlock

        constructor(Pair<Array<Byte>, Integer> pair, Map<ContentDigestAlgorithm, Array<Byte>> map) {
            this.signingSchemeBlock = pair
            this.digestInfo = map
        }
    }

    public static class SupportedSignature extends ApkSupportedSignature {
        constructor(SignatureAlgorithm signatureAlgorithm, Array<Byte> bArr) {
            super(signatureAlgorithm, bArr)
        }
    }

    public static class VerityTreeAndDigest {
        public final ContentDigestAlgorithm contentDigestAlgorithm
        public final Array<Byte> rootHash
        public final Array<Byte> tree

        constructor(ContentDigestAlgorithm contentDigestAlgorithm, Array<Byte> bArr, Array<Byte> bArr2) {
            this.contentDigestAlgorithm = contentDigestAlgorithm
            this.rootHash = bArr
            this.tree = bArr2
        }
    }

    fun checkByteOrderLittleEndian(ByteBuffer byteBuffer) {
        ApkSigningBlockUtilsLite.checkByteOrderLittleEndian(byteBuffer)
    }

    fun compareSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm, SignatureAlgorithm signatureAlgorithm2) {
        return ApkSigningBlockUtilsLite.compareSignatureAlgorithm(signatureAlgorithm, signatureAlgorithm2)
    }

    fun computeApkVerityDigest(DataSource dataSource, DataSource dataSource2, DataSource dataSource3, Map<ContentDigestAlgorithm, Array<Byte>> map) throws NoSuchAlgorithmException, IOException {
        ByteBuffer byteBufferCreateVerityDigestBuffer = createVerityDigestBuffer(true)
        VerityTreeBuilder verityTreeBuilder = VerityTreeBuilder(new Byte[8])
        try {
            byteBufferCreateVerityDigestBuffer.put(verityTreeBuilder.generateVerityTreeRootHash(dataSource, dataSource2, dataSource3))
            byteBufferCreateVerityDigestBuffer.putLong(dataSource.size() + dataSource2.size() + dataSource3.size())
            map.put(ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, byteBufferCreateVerityDigestBuffer.array())
            verityTreeBuilder.close()
        } catch (Throwable th) {
            try {
                verityTreeBuilder.close()
            } catch (Throwable unused) {
            }
            throw th
        }
    }

    fun computeChunkVerityTreeAndDigest(DataSource dataSource) throws NoSuchAlgorithmException, IOException {
        ByteBuffer byteBufferCreateVerityDigestBuffer = createVerityDigestBuffer(false)
        VerityTreeBuilder verityTreeBuilder = VerityTreeBuilder(null)
        try {
            ByteBuffer byteBufferGenerateVerityTree = verityTreeBuilder.generateVerityTree(dataSource)
            byteBufferCreateVerityDigestBuffer.put(verityTreeBuilder.getRootHashFromTree(byteBufferGenerateVerityTree))
            VerityTreeAndDigest verityTreeAndDigest = VerityTreeAndDigest(ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, byteBufferCreateVerityDigestBuffer.array(), byteBufferGenerateVerityTree.array())
            verityTreeBuilder.close()
            return verityTreeAndDigest
        } catch (Throwable th) {
            try {
                verityTreeBuilder.close()
            } catch (Throwable unused) {
            }
            throw th
        }
    }

    public static Pair<List<SignerConfig>, Map<ContentDigestAlgorithm, Array<Byte>>> computeContentDigests(RunnablesExecutor runnablesExecutor, DataSource dataSource, DataSource dataSource2, DataSource dataSource3, List<SignerConfig> list) throws SignatureException, NoSuchAlgorithmException, IOException {
        if (list.isEmpty()) {
            throw IllegalArgumentException("No signer configs provided. At least one is required")
        }
        HashSet hashSet = HashSet(1)
        Iterator<SignerConfig> it = list.iterator()
        while (it.hasNext()) {
            Iterator<SignatureAlgorithm> it2 = it.next().signatureAlgorithms.iterator()
            while (it2.hasNext()) {
                hashSet.add(it2.next().getContentDigestAlgorithm())
            }
        }
        try {
            return Pair.of(list, computeContentDigests(runnablesExecutor, hashSet, dataSource, dataSource2, dataSource3))
        } catch (IOException e) {
            throw IOException("Failed to read APK being signed", e)
        } catch (DigestException e2) {
            throw SignatureException("Failed to compute digests of APK", e2)
        }
    }

    public static Map<ContentDigestAlgorithm, Array<Byte>> computeContentDigests(RunnablesExecutor runnablesExecutor, Set<ContentDigestAlgorithm> set, DataSource dataSource, DataSource dataSource2, DataSource dataSource3) throws NoSuchAlgorithmException, DigestException, IOException {
        HashMap map = HashMap()
        HashSet hashSet = HashSet()
        for (ContentDigestAlgorithm contentDigestAlgorithm : set) {
            if (contentDigestAlgorithm == ContentDigestAlgorithm.CHUNKED_SHA256 || contentDigestAlgorithm == ContentDigestAlgorithm.CHUNKED_SHA512) {
                hashSet.add(contentDigestAlgorithm)
            }
        }
        computeOneMbChunkContentDigests(runnablesExecutor, hashSet, new Array<DataSource>{dataSource, dataSource2, dataSource3}, map)
        if (set.contains(ContentDigestAlgorithm.VERITY_CHUNKED_SHA256)) {
            computeApkVerityDigest(dataSource, dataSource2, dataSource3, map)
        }
        return map
    }

    fun computeOneMbChunkContentDigests(RunnablesExecutor runnablesExecutor, Set<ContentDigestAlgorithm> set, Array<DataSource> dataSourceArr, Map<ContentDigestAlgorithm, Array<Byte>> map) throws NoSuchAlgorithmException, DigestException {
        Long chunkCount = 0
        for (DataSource dataSource : dataSourceArr) {
            chunkCount += getChunkCount(dataSource.size(), PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED)
        }
        if (chunkCount > 2147483647L) {
            throw DigestException("Input too Long: " + chunkCount + " chunks")
        }
        Int i = (Int) chunkCount
        final ArrayList<ChunkDigests> arrayList = ArrayList(set.size())
        Iterator<ContentDigestAlgorithm> it = set.iterator()
        while (true) {
            if (!it.hasNext()) {
                break
            } else {
                arrayList.add(ChunkDigests(it.next(), i))
            }
        }
        val chunkSupplier = ChunkSupplier(dataSourceArr)
        runnablesExecutor.execute(RunnablesProvider() { // from class: com.android.apksig.internal.apk.ApkSigningBlockUtils.1
            @Override // com.android.apksig.util.RunnablesProvider
            fun createRunnable() {
                return ChunkDigester(chunkSupplier, arrayList)
            }
        })
        for (ChunkDigests chunkDigests : arrayList) {
            map.put(chunkDigests.algorithm, chunkDigests.createMessageDigest().digest(chunkDigests.concatOfDigestsOfChunks))
        }
    }

    fun copyWithModifiedCDOffset(DataSource dataSource, DataSource dataSource2) throws IOException {
        Long size = dataSource.size()
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((Int) dataSource2.size())
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        dataSource2.copyTo(0L, (Int) dataSource2.size(), byteBufferAllocate)
        byteBufferAllocate.flip()
        ZipUtils.setZipEocdCentralDirectoryOffset(byteBufferAllocate, size)
        return DataSources.asDataSource(byteBufferAllocate)
    }

    fun createVerityDigestBuffer(Boolean z) {
        Int chunkDigestOutputSizeBytes = ContentDigestAlgorithm.VERITY_CHUNKED_SHA256.getChunkDigestOutputSizeBytes()
        if (z) {
            chunkDigestOutputSizeBytes += 8
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(chunkDigestOutputSizeBytes)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        return byteBufferAllocate
    }

    public static Array<Byte> encodeAsLengthPrefixedElement(Array<Byte> bArr) {
        return encodeAsSequenceOfLengthPrefixedElements(new Array<Byte>[]{bArr})
    }

    public static Array<Byte> encodeAsSequenceOfLengthPrefixedElements(List<Array<Byte>> list) {
        return encodeAsSequenceOfLengthPrefixedElements((Array<Byte>[]) list.toArray(new Byte[list.size()][]))
    }

    public static Array<Byte> encodeAsSequenceOfLengthPrefixedElements(Array<Byte>[] bArr) {
        Int length = 0
        for (Array<Byte> bArr2 : bArr) {
            length += bArr2.length + 4
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        for (Array<Byte> bArr3 : bArr) {
            byteBufferAllocate.putInt(bArr3.length)
            byteBufferAllocate.put(bArr3)
        }
        return byteBufferAllocate.array()
    }

    public static Array<Byte> encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(List<Pair<Integer, Array<Byte>>> list) {
        return ApkSigningBlockUtilsLite.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(list)
    }

    public static List<Array<Byte>> encodeCertificates(List<X509Certificate> list) throws CertificateEncodingException {
        ArrayList arrayList = ArrayList(list.size())
        Iterator<X509Certificate> it = list.iterator()
        while (it.hasNext()) {
            arrayList.add(it.next().getEncoded())
        }
        return arrayList
    }

    public static Array<Byte> encodePublicKey(PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Array<Byte> encoded = null
        if ("X.509".equals(publicKey.getFormat())) {
            Array<Byte> encoded2 = publicKey.getEncoded()
            if ("RSA".equals(publicKey.getAlgorithm())) {
                try {
                    SubjectPublicKeyInfo subjectPublicKeyInfo = (SubjectPublicKeyInfo) Asn1BerParser.parse(ByteBuffer.wrap(encoded2), SubjectPublicKeyInfo.class)
                    ByteBuffer byteBuffer = subjectPublicKeyInfo.subjectPublicKey
                    Byte b2 = byteBuffer.get()
                    RSAPublicKey rSAPublicKey = (RSAPublicKey) Asn1BerParser.parse(byteBuffer, RSAPublicKey.class)
                    if (rSAPublicKey.modulus.compareTo(BigInteger.ZERO) < 0) {
                        Array<Byte> byteArray = rSAPublicKey.modulus.toByteArray()
                        Array<Byte> bArr = new Byte[byteArray.length + 1]
                        bArr[0] = 0
                        System.arraycopy(byteArray, 0, bArr, 1, byteArray.length)
                        rSAPublicKey.modulus = BigInteger(bArr)
                        Array<Byte> bArrEncode = Asn1DerEncoder.encode(rSAPublicKey)
                        Array<Byte> bArr2 = new Byte[bArrEncode.length + 1]
                        bArr2[0] = b2
                        System.arraycopy(bArrEncode, 0, bArr2, 1, bArrEncode.length)
                        subjectPublicKeyInfo.subjectPublicKey = ByteBuffer.wrap(bArr2)
                        encoded2 = Asn1DerEncoder.encode(subjectPublicKeyInfo)
                    }
                    encoded = encoded2
                } catch (Asn1DecodingException | Asn1EncodingException e) {
                    System.out.println("Caught a exception encoding the public key: " + e)
                    e.printStackTrace()
                }
            } else {
                encoded = encoded2
            }
        }
        if (encoded == null) {
            try {
                encoded = ((X509EncodedKeySpec) KeyFactory.getInstance(publicKey.getAlgorithm()).getKeySpec(publicKey, X509EncodedKeySpec.class)).getEncoded()
            } catch (InvalidKeySpecException e2) {
                throw InvalidKeyException("Failed to obtain X.509 encoded form of public key " + publicKey + " of class " + publicKey.getClass().getName(), e2)
            }
        }
        if (encoded != null && encoded.length != 0) {
            return encoded
        }
        throw InvalidKeyException("Failed to obtain X.509 encoded form of public key " + publicKey + " of class " + publicKey.getClass().getName())
    }

    fun findApkSignatureSchemeBlock(ByteBuffer byteBuffer, Int i, Result result) throws SignatureNotFoundException {
        try {
            return ApkSigningBlockUtilsLite.findApkSignatureSchemeBlock(byteBuffer, i)
        } catch (com.android.apksig.internal.apk.SignatureNotFoundException e) {
            throw SignatureNotFoundException(e.getMessage())
        }
    }

    fun findSignature(DataSource dataSource, ApkUtils.ZipSections zipSections, Int i, Result result) throws SignatureNotFoundException, IOException {
        try {
            return ApkSigningBlockUtilsLite.findSignature(dataSource, zipSections, i)
        } catch (com.android.apksig.internal.apk.SignatureNotFoundException e) {
            throw SignatureNotFoundException(e.getMessage())
        }
    }

    public static Array<Byte> generateApkSigningBlock(List<Pair<Array<Byte>, Integer>> list) {
        Iterator<Pair<Array<Byte>, Integer>> it = list.iterator()
        Int length = 0
        while (it.hasNext()) {
            length += it.next().getFirst().length + 12
        }
        Int i = length + 8 + 8 + 16
        ByteBuffer byteBuffer = null
        Int i2 = i % 4096
        if (i2 != 0) {
            Int i3 = 4096 - i2
            if (i3 < 12) {
                i3 += 4096
            }
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(i3).order(ByteOrder.LITTLE_ENDIAN)
            byteBufferOrder.putLong(i3 - 8)
            byteBufferOrder.putInt(1114793335)
            byteBufferOrder.rewind()
            i += i3
            byteBuffer = byteBufferOrder
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        Long j = i - 8
        byteBufferAllocate.putLong(j)
        for (Pair<Array<Byte>, Integer> pair : list) {
            Array<Byte> first = pair.getFirst()
            Int iIntValue = pair.getSecond().intValue()
            byteBufferAllocate.putLong(first.length + 4)
            byteBufferAllocate.putInt(iIntValue)
            byteBufferAllocate.put(first)
        }
        if (byteBuffer != null) {
            byteBufferAllocate.put(byteBuffer)
        }
        byteBufferAllocate.putLong(j)
        byteBufferAllocate.put(APK_SIGNING_BLOCK_MAGIC)
        return byteBufferAllocate.array()
    }

    public static Pair<DataSource, Integer> generateApkSigningBlockPadding(DataSource dataSource, Boolean z) {
        Int i = 0
        if (z && dataSource.size() % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM != 0) {
            Int size = (Int) (PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM - (dataSource.size() % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM))
            i = size
            dataSource = ChainedDataSource(dataSource, DataSources.asDataSource(ByteBuffer.allocate(size)))
        }
        return Pair.of(dataSource, Integer.valueOf(i))
    }

    public static Array<Byte> generatePkcs7DerEncodedMessage(Array<Byte> bArr, ByteBuffer byteBuffer, List<X509Certificate> list, AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) throws Asn1EncodingException, CertificateEncodingException {
        SignerInfo signerInfo = SignerInfo()
        signerInfo.version = 1
        X509Certificate x509Certificate = list.get(0)
        signerInfo.sid = SignerIdentifier(IssuerAndSerialNumber(Asn1OpaqueObject(x509Certificate.getIssuerX500Principal().getEncoded()), x509Certificate.getSerialNumber()))
        signerInfo.digestAlgorithm = algorithmIdentifier
        signerInfo.signatureAlgorithm = algorithmIdentifier2
        signerInfo.signature = ByteBuffer.wrap(bArr)
        SignedData signedData = SignedData()
        signedData.certificates = ArrayList(list.size())
        Iterator<X509Certificate> it = list.iterator()
        while (it.hasNext()) {
            signedData.certificates.add(Asn1OpaqueObject(it.next().getEncoded()))
        }
        signedData.version = 1
        signedData.digestAlgorithms = Collections.singletonList(algorithmIdentifier)
        EncapsulatedContentInfo encapsulatedContentInfo = EncapsulatedContentInfo(Pkcs7Constants.OID_DATA)
        signedData.encapContentInfo = encapsulatedContentInfo
        encapsulatedContentInfo.content = byteBuffer
        signedData.signerInfos = Collections.singletonList(signerInfo)
        ContentInfo contentInfo = ContentInfo()
        contentInfo.contentType = Pkcs7Constants.OID_SIGNED_DATA
        contentInfo.content = Asn1OpaqueObject(Asn1DerEncoder.encode(signedData))
        return Asn1DerEncoder.encode(contentInfo)
    }

    public static List<Pair<Integer, Array<Byte>>> generateSignaturesOverData(SignerConfig signerConfig, Array<Byte> bArr) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        ArrayList arrayList = ArrayList(signerConfig.signatureAlgorithms.size())
        PublicKey publicKey = signerConfig.certificates.get(0).getPublicKey()
        for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
            Pair<String, ? extends AlgorithmParameterSpec> jcaSignatureAlgorithmAndParams = signatureAlgorithm.getJcaSignatureAlgorithmAndParams()
            String first = jcaSignatureAlgorithmAndParams.getFirst()
            AlgorithmParameterSpec second = jcaSignatureAlgorithmAndParams.getSecond()
            try {
                Signature signature = Signature.getInstance(first)
                signature.initSign(signerConfig.privateKey)
                if (second != null) {
                    signature.setParameter(second)
                }
                signature.update(bArr)
                Array<Byte> bArrSign = signature.sign()
                try {
                    Signature signature2 = Signature.getInstance(first)
                    signature2.initVerify(publicKey)
                    if (second != null) {
                        signature2.setParameter(second)
                    }
                    signature2.update(bArr)
                    if (!signature2.verify(bArrSign)) {
                        throw SignatureException("Failed to verify generated " + first + " signature using public key from certificate")
                    }
                    arrayList.add(Pair.of(Integer.valueOf(signatureAlgorithm.getId()), bArrSign))
                } catch (InvalidAlgorithmParameterException e) {
                    e = e
                    throw SignatureException("Failed to verify generated " + first + " signature using public key from certificate", e)
                } catch (InvalidKeyException e2) {
                    throw InvalidKeyException("Failed to verify generated " + first + " signature using public key from certificate", e2)
                } catch (SignatureException e3) {
                    e = e3
                    throw SignatureException("Failed to verify generated " + first + " signature using public key from certificate", e)
                }
            } catch (InvalidAlgorithmParameterException e4) {
                e = e4
                throw SignatureException("Failed to sign using " + first, e)
            } catch (InvalidKeyException e5) {
                throw InvalidKeyException("Failed to sign using " + first, e5)
            } catch (SignatureException e6) {
                e = e6
                throw SignatureException("Failed to sign using " + first, e)
            }
        }
        return arrayList
    }

    fun getChunkCount(Long j, Long j2) {
        return ((j + j2) - 1) / j2
    }

    fun getLengthPrefixedSlice(ByteBuffer byteBuffer) throws ApkFormatException {
        return ApkSigningBlockUtilsLite.getLengthPrefixedSlice(byteBuffer)
    }

    public static <T extends ApkSupportedSignature> List<T> getSignaturesToVerify(List<T> list, Int i, Int i2) throws NoSupportedSignaturesException {
        return getSignaturesToVerify(list, i, i2, false)
    }

    public static <T extends ApkSupportedSignature> List<T> getSignaturesToVerify(List<T> list, Int i, Int i2, Boolean z) throws NoSupportedSignaturesException {
        try {
            return ApkSigningBlockUtilsLite.getSignaturesToVerify(list, i, i2, z)
        } catch (NoApkSupportedSignaturesException e) {
            throw NoSupportedSignaturesException(e.getMessage())
        }
    }

    public static Array<Byte> pickBestDigestForV4(Map<ContentDigestAlgorithm, Array<Byte>> map) {
        for (ContentDigestAlgorithm contentDigestAlgorithm : V4_CONTENT_DIGEST_ALGORITHMS) {
            if (map.containsKey(contentDigestAlgorithm)) {
                return map.get(contentDigestAlgorithm)
            }
        }
        return null
    }

    public static Array<Byte> readLengthPrefixedByteArray(ByteBuffer byteBuffer) throws ApkFormatException {
        return ApkSigningBlockUtilsLite.readLengthPrefixedByteArray(byteBuffer)
    }

    fun setUnsignedInt32LittleEndian(Int i, Array<Byte> bArr, Int i2) {
        bArr[i2] = (Byte) (i & 255)
        bArr[i2 + 1] = (Byte) ((i >> 8) & 255)
        bArr[i2 + 2] = (Byte) ((i >> 16) & 255)
        bArr[i2 + 3] = (Byte) ((i >> 24) & 255)
    }

    fun toHex(Array<Byte> bArr) {
        return ApkSigningBlockUtilsLite.toHex(bArr)
    }

    fun verifyIntegrity(RunnablesExecutor runnablesExecutor, DataSource dataSource, DataSource dataSource2, ByteBuffer byteBuffer, Set<ContentDigestAlgorithm> set, Result result) throws NoSuchAlgorithmException, IOException {
        if (set.isEmpty()) {
            throw RuntimeException("No content digests found")
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBuffer.remaining())
        Int iPosition = byteBuffer.position()
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        byteBufferAllocate.put(byteBuffer)
        byteBufferAllocate.flip()
        byteBuffer.position(iPosition)
        ZipUtils.setZipEocdCentralDirectoryOffset(byteBufferAllocate, dataSource.size())
        try {
            Map<ContentDigestAlgorithm, Array<Byte>> mapComputeContentDigests = computeContentDigests(runnablesExecutor, set, dataSource, dataSource2, ByteBufferDataSource(byteBufferAllocate))
            if (mapComputeContentDigests.containsKey(ContentDigestAlgorithm.VERITY_CHUNKED_SHA256)) {
                if (dataSource.size() % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM != 0) {
                    throw RuntimeException("APK Signing Block is not aligned on 4k boundary: " + dataSource.size())
                }
                Long zipEocdCentralDirectoryOffset = ZipUtils.getZipEocdCentralDirectoryOffset(byteBuffer) - dataSource.size()
                if (zipEocdCentralDirectoryOffset % PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM != 0) {
                    throw RuntimeException("APK Signing Block size is not multiple of page size: " + zipEocdCentralDirectoryOffset)
                }
            }
            if (!set.equals(mapComputeContentDigests.keySet())) {
                throw RuntimeException("Mismatch between sets of requested and computed content digests . Requested: " + set + ", computed: " + mapComputeContentDigests.keySet())
            }
            for (Result.SignerInfo signerInfo : result.signers) {
                for (Result.SignerInfo.ContentDigest contentDigest : signerInfo.contentDigests) {
                    SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId())
                    if (signatureAlgorithmFindById != null) {
                        ContentDigestAlgorithm contentDigestAlgorithm = signatureAlgorithmFindById.getContentDigestAlgorithm()
                        if (set.contains(contentDigestAlgorithm)) {
                            Array<Byte> value = contentDigest.getValue()
                            Array<Byte> bArr = mapComputeContentDigests.get(contentDigestAlgorithm)
                            if (Arrays.equals(value, bArr)) {
                                signerInfo.verifiedContentDigests.put(contentDigestAlgorithm, bArr)
                            } else {
                                Int i = result.signatureSchemeVersion
                                if (i == 2) {
                                    signerInfo.addError(ApkVerifier.Issue.V2_SIG_APK_DIGEST_DID_NOT_VERIFY, contentDigestAlgorithm, toHex(value), toHex(bArr))
                                } else if (i == 3) {
                                    signerInfo.addError(ApkVerifier.Issue.V3_SIG_APK_DIGEST_DID_NOT_VERIFY, contentDigestAlgorithm, toHex(value), toHex(bArr))
                                }
                            }
                        }
                    }
                }
            }
        } catch (DigestException e) {
            throw RuntimeException("Failed to compute content digests", e)
        }
    }
}
