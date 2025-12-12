package com.android.apksig

import com.android.apksig.ApkSignerEngine
import com.android.apksig.Hints
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkUtils
import com.android.apksig.apk.MinSdkVersionException
import com.android.apksig.internal.zip.CentralDirectoryRecord
import com.android.apksig.internal.zip.LocalFileRecord
import com.android.apksig.internal.zip.ZipUtils
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSinks
import com.android.apksig.util.DataSource
import com.android.apksig.util.DataSources
import com.android.apksig.util.ReadableDataSink
import com.android.apksig.zip.ZipFormatException
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List

class ApkSigner {
    public final String mCreatedBy
    public final Boolean mDebuggableApkPermitted
    public final Boolean mForceSourceStampOverwrite
    public final DataSource mInputApkDataSource
    public final File mInputApkFile
    public final Integer mMinSdkVersion
    public final Boolean mOtherSignersSignaturesPreserved
    public final DataSink mOutputApkDataSink
    public final DataSource mOutputApkDataSource
    public final File mOutputApkFile
    public final File mOutputV4File
    public final List<SignerConfig> mSignerConfigs
    public final ApkSignerEngine mSignerEngine
    public final SigningCertificateLineage mSigningCertificateLineage
    public final SignerConfig mSourceStampSignerConfig
    public final SigningCertificateLineage mSourceStampSigningCertificateLineage
    public final Boolean mV1SigningEnabled
    public final Boolean mV2SigningEnabled
    public final Boolean mV3SigningEnabled
    public final Boolean mV4ErrorReportingEnabled
    public final Boolean mV4SigningEnabled
    public final Boolean mVerityEnabled

    /* renamed from: com.android.apksig.ApkSigner$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy

        static {
            Array<Int> iArr = new Int[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.values().length]
            $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy = iArr
            try {
                iArr[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT_BY_ENGINE.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.SKIP.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static class Builder {
        public String mCreatedBy
        public Boolean mDebuggableApkPermitted
        public Boolean mForceSourceStampOverwrite
        public DataSource mInputApkDataSource
        public File mInputApkFile
        public Integer mMinSdkVersion
        public Boolean mOtherSignersSignaturesPreserved
        public DataSink mOutputApkDataSink
        public DataSource mOutputApkDataSource
        public File mOutputApkFile
        public File mOutputV4File
        public final List<SignerConfig> mSignerConfigs
        public final ApkSignerEngine mSignerEngine
        public SigningCertificateLineage mSigningCertificateLineage
        public SignerConfig mSourceStampSignerConfig
        public SigningCertificateLineage mSourceStampSigningCertificateLineage
        public Boolean mV1SigningEnabled
        public Boolean mV2SigningEnabled
        public Boolean mV3SigningEnabled
        public Boolean mV3SigningExplicitlyDisabled
        public Boolean mV3SigningExplicitlyEnabled
        public Boolean mV4ErrorReportingEnabled
        public Boolean mV4SigningEnabled
        public Boolean mVerityEnabled

        constructor(ApkSignerEngine apkSignerEngine) {
            this.mForceSourceStampOverwrite = false
            this.mV1SigningEnabled = true
            this.mV2SigningEnabled = true
            this.mV3SigningEnabled = true
            this.mV4SigningEnabled = true
            this.mVerityEnabled = false
            this.mV4ErrorReportingEnabled = false
            this.mDebuggableApkPermitted = true
            this.mV3SigningExplicitlyDisabled = false
            this.mV3SigningExplicitlyEnabled = false
            if (apkSignerEngine == null) {
                throw NullPointerException("signerEngine == null")
            }
            this.mSignerEngine = apkSignerEngine
            this.mSignerConfigs = null
        }

        constructor(List<SignerConfig> list) {
            this.mForceSourceStampOverwrite = false
            this.mV1SigningEnabled = true
            this.mV2SigningEnabled = true
            this.mV3SigningEnabled = true
            this.mV4SigningEnabled = true
            this.mVerityEnabled = false
            this.mV4ErrorReportingEnabled = false
            this.mDebuggableApkPermitted = true
            this.mV3SigningExplicitlyDisabled = false
            this.mV3SigningExplicitlyEnabled = false
            if (list.isEmpty()) {
                throw IllegalArgumentException("At least one signer config must be provided")
            }
            if (list.size() > 1) {
                this.mV3SigningEnabled = false
            }
            this.mSignerConfigs = ArrayList(list)
            this.mSignerEngine = null
        }

        fun build() {
            Boolean z = this.mV3SigningExplicitlyDisabled
            if (z && this.mV3SigningExplicitlyEnabled) {
                throw IllegalStateException("Builder configured to both enable and disable APK Signature Scheme v3 signing")
            }
            if (z) {
                this.mV3SigningEnabled = false
            }
            if (this.mV3SigningExplicitlyEnabled) {
                this.mV3SigningEnabled = true
            }
            if (this.mV4SigningEnabled && !this.mV2SigningEnabled && !this.mV3SigningEnabled) {
                if (this.mV4ErrorReportingEnabled) {
                    throw IllegalStateException("APK Signature Scheme v4 signing requires at least v2 or v3 signing to be enabled")
                }
                this.mV4SigningEnabled = false
            }
            return ApkSigner(this.mSignerConfigs, this.mSourceStampSignerConfig, this.mSourceStampSigningCertificateLineage, this.mForceSourceStampOverwrite, this.mMinSdkVersion, this.mV1SigningEnabled, this.mV2SigningEnabled, this.mV3SigningEnabled, this.mV4SigningEnabled, this.mVerityEnabled, this.mV4ErrorReportingEnabled, this.mDebuggableApkPermitted, this.mOtherSignersSignaturesPreserved, this.mCreatedBy, this.mSignerEngine, this.mInputApkFile, this.mInputApkDataSource, this.mOutputApkFile, this.mOutputApkDataSink, this.mOutputApkDataSource, this.mOutputV4File, this.mSigningCertificateLineage, null)
        }

        public final Unit checkInitializedWithoutEngine() {
            if (this.mSignerEngine != null) {
                throw IllegalStateException("Operation is not available when builder initialized with an engine")
            }
        }

        fun setCreatedBy(String str) {
            checkInitializedWithoutEngine()
            str.getClass()
            this.mCreatedBy = str
            return this
        }

        fun setDebuggableApkPermitted(Boolean z) {
            checkInitializedWithoutEngine()
            this.mDebuggableApkPermitted = z
            return this
        }

        fun setForceSourceStampOverwrite(Boolean z) {
            this.mForceSourceStampOverwrite = z
            return this
        }

        fun setInputApk(File file) {
            if (file == null) {
                throw NullPointerException("inputApk == null")
            }
            this.mInputApkFile = file
            this.mInputApkDataSource = null
            return this
        }

        fun setMinSdkVersion(Int i) {
            checkInitializedWithoutEngine()
            this.mMinSdkVersion = Integer.valueOf(i)
            return this
        }

        fun setOtherSignersSignaturesPreserved(Boolean z) {
            checkInitializedWithoutEngine()
            this.mOtherSignersSignaturesPreserved = z
            return this
        }

        fun setOutputApk(DataSink dataSink, DataSource dataSource) {
            if (dataSink == null) {
                throw NullPointerException("outputApkOut == null")
            }
            if (dataSource == null) {
                throw NullPointerException("outputApkIn == null")
            }
            this.mOutputApkFile = null
            this.mOutputApkDataSink = dataSink
            this.mOutputApkDataSource = dataSource
            return this
        }

        fun setOutputApk(ReadableDataSink readableDataSink) {
            if (readableDataSink != null) {
                return setOutputApk(readableDataSink, readableDataSink)
            }
            throw NullPointerException("outputApk == null")
        }

        fun setOutputApk(File file) {
            if (file == null) {
                throw NullPointerException("outputApk == null")
            }
            this.mOutputApkFile = file
            this.mOutputApkDataSink = null
            this.mOutputApkDataSource = null
            return this
        }

        fun setSigningCertificateLineage(SigningCertificateLineage signingCertificateLineage) {
            if (signingCertificateLineage != null) {
                this.mV3SigningEnabled = true
                this.mSigningCertificateLineage = signingCertificateLineage
            }
            return this
        }

        fun setSourceStampSignerConfig(SignerConfig signerConfig) {
            this.mSourceStampSignerConfig = signerConfig
            return this
        }

        fun setSourceStampSigningCertificateLineage(SigningCertificateLineage signingCertificateLineage) {
            this.mSourceStampSigningCertificateLineage = signingCertificateLineage
            return this
        }

        fun setV1SigningEnabled(Boolean z) {
            checkInitializedWithoutEngine()
            this.mV1SigningEnabled = z
            return this
        }

        fun setV2SigningEnabled(Boolean z) {
            checkInitializedWithoutEngine()
            this.mV2SigningEnabled = z
            return this
        }

        fun setV3SigningEnabled(Boolean z) {
            checkInitializedWithoutEngine()
            this.mV3SigningEnabled = z
            if (z) {
                this.mV3SigningExplicitlyEnabled = true
            } else {
                this.mV3SigningExplicitlyDisabled = true
            }
            return this
        }

        fun setV4ErrorReportingEnabled(Boolean z) {
            checkInitializedWithoutEngine()
            this.mV4ErrorReportingEnabled = z
            return this
        }

        fun setV4SignatureOutputFile(File file) {
            if (file == null) {
                throw NullPointerException("v4HashRootOutputFile == null")
            }
            this.mOutputV4File = file
            return this
        }

        fun setV4SigningEnabled(Boolean z) {
            checkInitializedWithoutEngine()
            this.mV4SigningEnabled = z
            this.mV4ErrorReportingEnabled = z
            return this
        }

        fun setVerityEnabled(Boolean z) {
            checkInitializedWithoutEngine()
            this.mVerityEnabled = z
            return this
        }
    }

    public static class OutputSizeAndDataOffset {
        public Long dataOffsetBytes
        public Long outputBytes

        constructor(Long j, Long j2) {
            this.outputBytes = j
            this.dataOffsetBytes = j2
        }
    }

    public static class SignerConfig {
        public final List<X509Certificate> mCertificates
        public final String mName
        public final PrivateKey mPrivateKey

        public static class Builder {
            public final List<X509Certificate> mCertificates
            public final String mName
            public final PrivateKey mPrivateKey

            constructor(String str, PrivateKey privateKey, List<X509Certificate> list) {
                if (str.isEmpty()) {
                    throw IllegalArgumentException("Empty name")
                }
                this.mName = str
                this.mPrivateKey = privateKey
                this.mCertificates = ArrayList(list)
            }

            fun build() {
                return SignerConfig(this.mName, this.mPrivateKey, this.mCertificates, null)
            }
        }

        constructor(String str, PrivateKey privateKey, List<X509Certificate> list) {
            this.mName = str
            this.mPrivateKey = privateKey
            this.mCertificates = Collections.unmodifiableList(ArrayList(list))
        }

        public /* synthetic */ SignerConfig(String str, PrivateKey privateKey, List list, AnonymousClass1 anonymousClass1) {
            this(str, privateKey, list)
        }

        public List<X509Certificate> getCertificates() {
            return this.mCertificates
        }

        fun getName() {
            return this.mName
        }

        fun getPrivateKey() {
            return this.mPrivateKey
        }
    }

    constructor(List<SignerConfig> list, SignerConfig signerConfig, SigningCertificateLineage signingCertificateLineage, Boolean z, Integer num, Boolean z2, Boolean z3, Boolean z4, Boolean z5, Boolean z6, Boolean z7, Boolean z8, Boolean z9, String str, ApkSignerEngine apkSignerEngine, File file, DataSource dataSource, File file2, DataSink dataSink, DataSource dataSource2, File file3, SigningCertificateLineage signingCertificateLineage2) {
        this.mSignerConfigs = list
        this.mSourceStampSignerConfig = signerConfig
        this.mSourceStampSigningCertificateLineage = signingCertificateLineage
        this.mForceSourceStampOverwrite = z
        this.mMinSdkVersion = num
        this.mV1SigningEnabled = z2
        this.mV2SigningEnabled = z3
        this.mV3SigningEnabled = z4
        this.mV4SigningEnabled = z5
        this.mVerityEnabled = z6
        this.mV4ErrorReportingEnabled = z7
        this.mDebuggableApkPermitted = z8
        this.mOtherSignersSignaturesPreserved = z9
        this.mCreatedBy = str
        this.mSignerEngine = apkSignerEngine
        this.mInputApkFile = file
        this.mInputApkDataSource = dataSource
        this.mOutputApkFile = file2
        this.mOutputApkDataSink = dataSink
        this.mOutputApkDataSource = dataSource2
        this.mOutputV4File = file3
        this.mSigningCertificateLineage = signingCertificateLineage2
    }

    public /* synthetic */ ApkSigner(List list, SignerConfig signerConfig, SigningCertificateLineage signingCertificateLineage, Boolean z, Integer num, Boolean z2, Boolean z3, Boolean z4, Boolean z5, Boolean z6, Boolean z7, Boolean z8, Boolean z9, String str, ApkSignerEngine apkSignerEngine, File file, DataSource dataSource, File file2, DataSink dataSink, DataSource dataSource2, File file3, SigningCertificateLineage signingCertificateLineage2, AnonymousClass1 anonymousClass1) {
        this(list, signerConfig, signingCertificateLineage, z, num, z2, z3, z4, z5, z6, z7, z8, z9, str, apkSignerEngine, file, dataSource, file2, dataSink, dataSource2, file3, signingCertificateLineage2)
    }

    fun createExtraFieldToAlignData(ByteBuffer byteBuffer, Long j, Int i) {
        if (i <= 1) {
            return byteBuffer
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBuffer.remaining() + 5 + i)
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN)
        while (byteBuffer.remaining() >= 4) {
            Short s = byteBuffer.getShort()
            Int unsignedInt16 = ZipUtils.getUnsignedInt16(byteBuffer)
            if (unsignedInt16 > byteBuffer.remaining()) {
                break
            }
            if ((s == 0 && unsignedInt16 == 0) || s == -9931) {
                byteBuffer.position(byteBuffer.position() + unsignedInt16)
            } else {
                byteBuffer.position(byteBuffer.position() - 4)
                Int iLimit = byteBuffer.limit()
                byteBuffer.limit(byteBuffer.position() + 4 + unsignedInt16)
                byteBufferAllocate.put(byteBuffer)
                byteBuffer.limit(iLimit)
            }
        }
        Int iPosition = (i - ((Int) (((j + byteBufferAllocate.position()) + 6) % i))) % i
        byteBufferAllocate.putShort((Short) -9931)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, iPosition + 2)
        ZipUtils.putUnsignedInt16(byteBufferAllocate, i)
        byteBufferAllocate.position(byteBufferAllocate.position() + iPosition)
        byteBufferAllocate.flip()
        return byteBufferAllocate
    }

    public static List<Hints.PatternWithRange> extractPinPatterns(List<CentralDirectoryRecord> list, DataSource dataSource) throws IOException, ApkFormatException {
        CentralDirectoryRecord centralDirectoryRecordFindCdRecord = findCdRecord(list, "assets/com.android.hints.pins.txt")
        if (centralDirectoryRecordFindCdRecord == null) {
            return null
        }
        ArrayList()
        try {
            return Hints.parsePinPatterns(LocalFileRecord.getUncompressedData(dataSource, centralDirectoryRecordFindCdRecord, dataSource.size()))
        } catch (ZipFormatException unused) {
            throw ApkFormatException("Bad " + centralDirectoryRecordFindCdRecord)
        }
    }

    fun findCdRecord(List<CentralDirectoryRecord> list, String str) {
        for (CentralDirectoryRecord centralDirectoryRecord : list) {
            if (str.equals(centralDirectoryRecord.getName())) {
                return centralDirectoryRecord
            }
        }
        return null
    }

    fun fulfillInspectInputJarEntryRequest(DataSource dataSource, LocalFileRecord localFileRecord, ApkSignerEngine.InspectJarEntryRequest inspectJarEntryRequest) throws IOException, ApkFormatException {
        try {
            localFileRecord.outputUncompressedData(dataSource, inspectJarEntryRequest.getDataSink())
            inspectJarEntryRequest.done()
        } catch (ZipFormatException e) {
            throw ApkFormatException("Malformed ZIP entry: " + localFileRecord.getName(), e)
        }
    }

    fun getAndroidManifestFromApk(List<CentralDirectoryRecord> list, DataSource dataSource) throws IOException, ApkFormatException, ZipFormatException {
        CentralDirectoryRecord centralDirectoryRecordFindCdRecord = findCdRecord(list, ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME)
        if (centralDirectoryRecordFindCdRecord != null) {
            return ByteBuffer.wrap(LocalFileRecord.getUncompressedData(dataSource, centralDirectoryRecordFindCdRecord, dataSource.size()))
        }
        throw ApkFormatException("Missing AndroidManifest.xml")
    }

    fun getInputJarEntryDataAlignmentMultiple(LocalFileRecord localFileRecord) {
        if (localFileRecord.isDataCompressed()) {
            return 1
        }
        return localFileRecord.getName().endsWith(".so") ? 4096 : 4
    }

    fun getMinSdkVersionFromApk(List<CentralDirectoryRecord> list, DataSource dataSource) throws MinSdkVersionException, IOException {
        try {
            return ApkUtils.getMinSdkVersionFromBinaryAndroidManifest(getAndroidManifestFromApk(list, dataSource))
        } catch (ApkFormatException | ZipFormatException e) {
            throw MinSdkVersionException("Failed to determine APK's minimum supported Android platform version", e)
        }
    }

    fun getZipCentralDirectory(DataSource dataSource, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        Long zipCentralDirectorySizeBytes = zipSections.getZipCentralDirectorySizeBytes()
        if (zipCentralDirectorySizeBytes <= 2147483647L) {
            ByteBuffer byteBuffer = dataSource.getByteBuffer(zipSections.getZipCentralDirectoryOffset(), (Int) zipCentralDirectorySizeBytes)
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            return byteBuffer
        }
        throw ApkFormatException("ZIP Central Directory too large: " + zipCentralDirectorySizeBytes)
    }

    fun outputDataToOutputApk(String str, Array<Byte> bArr, Long j, List<CentralDirectoryRecord> list, Int i, Int i2, DataSink dataSink) throws IOException {
        ZipUtils.DeflateResult deflateResultDeflate = ZipUtils.deflate(ByteBuffer.wrap(bArr))
        Array<Byte> bArr2 = deflateResultDeflate.output
        Long j2 = deflateResultDeflate.inputCrc32
        Long jOutputRecordWithDeflateCompressedData = LocalFileRecord.outputRecordWithDeflateCompressedData(str, i, i2, bArr2, j2, bArr.length, dataSink)
        list.add(CentralDirectoryRecord.createWithDeflateCompressedData(str, i, i2, j2, bArr2.length, bArr.length, j))
        return jOutputRecordWithDeflateCompressedData
    }

    fun outputInputJarEntryLfhRecordPreservingDataAlignment(DataSource dataSource, LocalFileRecord localFileRecord, DataSink dataSink, Long j) throws IOException {
        Int inputJarEntryDataAlignmentMultiple = getInputJarEntryDataAlignmentMultiple(localFileRecord)
        if (inputJarEntryDataAlignmentMultiple <= 1) {
            return OutputSizeAndDataOffset(localFileRecord.outputRecord(dataSource, dataSink), localFileRecord.getDataStartOffsetInRecord())
        }
        return OutputSizeAndDataOffset(localFileRecord.outputRecordWithModifiedExtra(dataSource, createExtraFieldToAlignData(localFileRecord.getExtra(), j + localFileRecord.getExtraFieldStartOffsetInsideRecord(), inputJarEntryDataAlignmentMultiple), dataSink), (localFileRecord.getDataStartOffsetInRecord() + r7.remaining()) - localFileRecord.getExtra().remaining())
    }

    public static List<CentralDirectoryRecord> parseZipCentralDirectory(ByteBuffer byteBuffer, ApkUtils.ZipSections zipSections) throws ApkFormatException {
        Long zipCentralDirectoryOffset = zipSections.getZipCentralDirectoryOffset()
        Int zipCentralDirectoryRecordCount = zipSections.getZipCentralDirectoryRecordCount()
        ArrayList arrayList = ArrayList(zipCentralDirectoryRecordCount)
        HashSet hashSet = HashSet(zipCentralDirectoryRecordCount)
        for (Int i = 0; i < zipCentralDirectoryRecordCount; i++) {
            Int iPosition = byteBuffer.position()
            try {
                CentralDirectoryRecord record = CentralDirectoryRecord.getRecord(byteBuffer)
                String name = record.getName()
                if (!hashSet.add(name)) {
                    throw ApkFormatException("Multiple ZIP entries with the same name: " + name)
                }
                arrayList.add(record)
            } catch (ZipFormatException e) {
                throw ApkFormatException("Malformed ZIP Central Directory record #" + (i + 1) + " at file offset " + (zipCentralDirectoryOffset + iPosition), e)
            }
        }
        if (!byteBuffer.hasRemaining()) {
            return arrayList
        }
        throw ApkFormatException("Unused space at the end of ZIP Central Directory: " + byteBuffer.remaining() + " bytes starting at file offset " + (zipCentralDirectoryOffset + byteBuffer.position()))
    }

    fun sign() throws Throwable {
        RandomAccessFile randomAccessFile
        Throwable th
        DataSource dataSourceAsDataSource
        DataSource dataSourceAsDataSource2
        RandomAccessFile randomAccessFile2 = null
        try {
            DataSource dataSource = this.mInputApkDataSource
            if (dataSource != null) {
                dataSourceAsDataSource = dataSource
                randomAccessFile = null
            } else {
                if (this.mInputApkFile == null) {
                    throw IllegalStateException("Input APK not specified")
                }
                randomAccessFile = RandomAccessFile(this.mInputApkFile, "r")
                try {
                    dataSourceAsDataSource = DataSources.asDataSource(randomAccessFile)
                } catch (Throwable th2) {
                    th = th2
                    if (randomAccessFile != null) {
                        randomAccessFile.close()
                    }
                    throw th
                }
            }
            try {
                DataSink dataSink = this.mOutputApkDataSink
                if (dataSink != null) {
                    dataSourceAsDataSource2 = this.mOutputApkDataSource
                } else {
                    if (this.mOutputApkFile == null) {
                        throw IllegalStateException("Output APK not specified")
                    }
                    RandomAccessFile randomAccessFile3 = RandomAccessFile(this.mOutputApkFile, "rw")
                    try {
                        randomAccessFile3.setLength(0L)
                        DataSink dataSinkAsDataSink = DataSinks.asDataSink(randomAccessFile3)
                        dataSourceAsDataSource2 = DataSources.asDataSource(randomAccessFile3)
                        dataSink = dataSinkAsDataSink
                        randomAccessFile2 = randomAccessFile3
                    } catch (Throwable th3) {
                        th = th3
                        randomAccessFile2 = randomAccessFile3
                        if (randomAccessFile2 != null) {
                            randomAccessFile2.close()
                        }
                        throw th
                    }
                }
                sign(dataSourceAsDataSource, dataSink, dataSourceAsDataSource2)
                if (randomAccessFile2 != null) {
                    randomAccessFile2.close()
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close()
                }
            } catch (Throwable th4) {
                th = th4
            }
        } catch (Throwable th5) {
            randomAccessFile = null
            th = th5
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x02a1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Unit sign(com.android.apksig.util.DataSource r31, com.android.apksig.util.DataSink r32, com.android.apksig.util.DataSource r33) throws java.lang.IllegalStateException, java.security.SignatureException, java.security.NoSuchAlgorithmException, java.io.IOException, java.security.InvalidKeyException, com.android.apksig.apk.ApkFormatException {
        /*
            Method dump skipped, instructions count: 1120
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.android.apksig.ApkSigner.sign(com.android.apksig.util.DataSource, com.android.apksig.util.DataSink, com.android.apksig.util.DataSource):Unit")
    }
}
