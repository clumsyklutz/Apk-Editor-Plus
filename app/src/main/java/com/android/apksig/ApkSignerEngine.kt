package com.android.apksig

import com.android.apksig.apk.ApkFormatException
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import com.android.apksig.util.RunnablesExecutor
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SignatureException
import java.util.List
import java.util.Set

public interface ApkSignerEngine extends Closeable {

    /* renamed from: com.android.apksig.ApkSignerEngine$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static Array<Byte> $default$generateSourceStampCertificateDigest(ApkSignerEngine apkSignerEngine) throws SignatureException {
            return new Byte[0]
        }

        public static Set $default$initWith(ApkSignerEngine apkSignerEngine, Array<Byte> bArr, Set set) {
            throw UnsupportedOperationException("initWith method is not implemented")
        }

        public static Boolean $default$isEligibleForSourceStamp(ApkSignerEngine apkSignerEngine) {
            return false
        }

        public static Unit $default$setExecutor(ApkSignerEngine apkSignerEngine, RunnablesExecutor runnablesExecutor) {
            throw UnsupportedOperationException("setExecutor method is not implemented")
        }
    }

    public static class InputJarEntryInstructions {
        public final InspectJarEntryRequest mInspectJarEntryRequest
        public final OutputPolicy mOutputPolicy

        public enum OutputPolicy {
            SKIP,
            OUTPUT,
            OUTPUT_BY_ENGINE
        }

        constructor(OutputPolicy outputPolicy) {
            this(outputPolicy, null)
        }

        constructor(OutputPolicy outputPolicy, InspectJarEntryRequest inspectJarEntryRequest) {
            this.mOutputPolicy = outputPolicy
            this.mInspectJarEntryRequest = inspectJarEntryRequest
        }

        fun getInspectJarEntryRequest() {
            return this.mInspectJarEntryRequest
        }

        fun getOutputPolicy() {
            return this.mOutputPolicy
        }
    }

    public interface InspectJarEntryRequest {
        Unit done()

        DataSink getDataSink()

        String getEntryName()
    }

    @Deprecated
    public interface OutputApkSigningBlockRequest {
        Unit done()

        Array<Byte> getApkSigningBlock()
    }

    public interface OutputApkSigningBlockRequest2 {
        Unit done()

        Array<Byte> getApkSigningBlock()

        Int getPaddingSizeBeforeApkSigningBlock()
    }

    public interface OutputJarSignatureRequest {

        public static class JarEntry {
            public final Array<Byte> mData
            public final String mName

            constructor(String str, Array<Byte> bArr) {
                this.mName = str
                this.mData = (Array<Byte>) bArr.clone()
            }

            public Array<Byte> getData() {
                return (Array<Byte>) this.mData.clone()
            }

            fun getName() {
                return this.mName
            }
        }

        Unit done()

        List<JarEntry> getAdditionalJarEntries()
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    Unit close()

    Array<Byte> generateSourceStampCertificateDigest() throws SignatureException

    Set<String> initWith(Array<Byte> bArr, Set<String> set)

    Unit inputApkSigningBlock(DataSource dataSource) throws IllegalStateException, IOException, ApkFormatException

    InputJarEntryInstructions inputJarEntry(String str) throws IllegalStateException

    InputJarEntryInstructions.OutputPolicy inputJarEntryRemoved(String str) throws IllegalStateException

    Boolean isEligibleForSourceStamp()

    Unit outputDone() throws IllegalStateException

    OutputJarSignatureRequest outputJarEntries() throws IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, ApkFormatException

    InspectJarEntryRequest outputJarEntry(String str) throws IllegalStateException

    Unit outputJarEntryRemoved(String str) throws IllegalStateException

    @Deprecated
    OutputApkSigningBlockRequest outputZipSections(DataSource dataSource, DataSource dataSource2, DataSource dataSource3) throws IllegalStateException, NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, ApkFormatException

    OutputApkSigningBlockRequest2 outputZipSections2(DataSource dataSource, DataSource dataSource2, DataSource dataSource3) throws IllegalStateException, NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, ApkFormatException

    Unit setExecutor(RunnablesExecutor runnablesExecutor)

    Unit signV4(DataSource dataSource, File file, Boolean z) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException
}
