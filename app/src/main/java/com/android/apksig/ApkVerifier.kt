package com.android.apksig

import android.support.v7.widget.ActivityChooserView
import com.android.apksig.apk.ApkFormatException
import com.android.apksig.apk.ApkUtils
import com.android.apksig.internal.apk.ApkSigResult
import com.android.apksig.internal.apk.ApkSignerInfo
import com.android.apksig.internal.apk.ApkSigningBlockUtils
import com.android.apksig.internal.apk.ContentDigestAlgorithm
import com.android.apksig.internal.apk.SignatureAlgorithm
import com.android.apksig.internal.apk.SignatureInfo
import com.android.apksig.internal.apk.SignatureNotFoundException
import com.android.apksig.internal.apk.stamp.V2SourceStampVerifier
import com.android.apksig.internal.apk.v1.V1SchemeVerifier
import com.android.apksig.internal.apk.v2.V2SchemeVerifier
import com.android.apksig.internal.apk.v3.V3SchemeVerifier
import com.android.apksig.internal.apk.v4.V4SchemeVerifier
import com.android.apksig.internal.zip.CentralDirectoryRecord
import com.android.apksig.internal.zip.LocalFileRecord
import com.android.apksig.util.DataSource
import com.android.apksig.util.DataSources
import com.android.apksig.util.RunnablesExecutor
import com.android.apksig.zip.ZipFormatException
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateEncodingException
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.EnumMap
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Map
import java.util.Set

class ApkVerifier {
    public static final Map<Integer, String> SUPPORTED_APK_SIG_SCHEME_NAMES = loadSupportedApkSigSchemeNames()
    public final DataSource mApkDataSource
    public final File mApkFile
    public final Int mMaxSdkVersion
    public final Integer mMinSdkVersion
    public final File mV4SignatureFile

    public static class ApkVerificationIssueAdapter {
        public static final Map<Integer, Issue> sVerificationIssueIdToIssue

        static {
            HashMap map = HashMap()
            sVerificationIssueIdToIssue = map
            map.put(1, Issue.V2_SIG_MALFORMED_SIGNERS)
            map.put(2, Issue.V2_SIG_NO_SIGNERS)
            map.put(3, Issue.V2_SIG_MALFORMED_SIGNER)
            map.put(4, Issue.V2_SIG_MALFORMED_SIGNATURE)
            map.put(5, Issue.V2_SIG_NO_SIGNATURES)
            map.put(6, Issue.V2_SIG_MALFORMED_CERTIFICATE)
            map.put(7, Issue.V2_SIG_NO_CERTIFICATES)
            map.put(8, Issue.V2_SIG_MALFORMED_DIGEST)
            map.put(9, Issue.V3_SIG_MALFORMED_SIGNERS)
            map.put(10, Issue.V3_SIG_NO_SIGNERS)
            map.put(11, Issue.V3_SIG_MALFORMED_SIGNER)
            map.put(12, Issue.V3_SIG_MALFORMED_SIGNATURE)
            map.put(13, Issue.V3_SIG_NO_SIGNATURES)
            map.put(14, Issue.V3_SIG_MALFORMED_CERTIFICATE)
            map.put(15, Issue.V3_SIG_NO_CERTIFICATES)
            map.put(16, Issue.V3_SIG_MALFORMED_DIGEST)
            map.put(17, Issue.SOURCE_STAMP_NO_SIGNATURE)
            map.put(18, Issue.SOURCE_STAMP_MALFORMED_CERTIFICATE)
            map.put(19, Issue.SOURCE_STAMP_UNKNOWN_SIG_ALGORITHM)
            map.put(20, Issue.SOURCE_STAMP_MALFORMED_SIGNATURE)
            map.put(21, Issue.SOURCE_STAMP_DID_NOT_VERIFY)
            map.put(22, Issue.SOURCE_STAMP_VERIFY_EXCEPTION)
            map.put(23, Issue.SOURCE_STAMP_EXPECTED_DIGEST_MISMATCH)
            map.put(24, Issue.SOURCE_STAMP_SIGNATURE_BLOCK_WITHOUT_CERT_DIGEST)
            map.put(25, Issue.SOURCE_STAMP_CERT_DIGEST_AND_SIG_BLOCK_MISSING)
            map.put(26, Issue.SOURCE_STAMP_NO_SUPPORTED_SIGNATURE)
            map.put(27, Issue.SOURCE_STAMP_CERTIFICATE_MISMATCH_BETWEEN_SIGNATURE_BLOCK_AND_APK)
            map.put(28, Issue.MALFORMED_APK)
            map.put(29, Issue.UNEXPECTED_EXCEPTION)
            map.put(30, Issue.SOURCE_STAMP_SIG_MISSING)
            map.put(31, Issue.SOURCE_STAMP_MALFORMED_ATTRIBUTE)
            map.put(32, Issue.SOURCE_STAMP_UNKNOWN_ATTRIBUTE)
            map.put(33, Issue.SOURCE_STAMP_MALFORMED_LINEAGE)
            map.put(34, Issue.SOURCE_STAMP_POR_CERT_MISMATCH)
            map.put(35, Issue.SOURCE_STAMP_POR_DID_NOT_VERIFY)
            map.put(36, Issue.JAR_SIG_NO_SIGNATURES)
            map.put(37, Issue.JAR_SIG_PARSE_EXCEPTION)
        }

        public static List<IssueWithParams> getIssuesFromVerificationIssues(List<? extends ApkVerificationIssue> list) {
            ArrayList arrayList = ArrayList(list.size())
            for (ApkVerificationIssue apkVerificationIssue : list) {
                if (apkVerificationIssue is IssueWithParams) {
                    arrayList.add((IssueWithParams) apkVerificationIssue)
                } else {
                    arrayList.add(IssueWithParams(sVerificationIssueIdToIssue.get(Integer.valueOf(apkVerificationIssue.getIssueId())), apkVerificationIssue.getParams()))
                }
            }
            return arrayList
        }
    }

    public static class Builder {
        public final DataSource mApkDataSource
        public final File mApkFile
        public Int mMaxSdkVersion = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED
        public Integer mMinSdkVersion
        public File mV4SignatureFile

        constructor(DataSource dataSource) {
            if (dataSource == null) {
                throw NullPointerException("apk == null")
            }
            this.mApkDataSource = dataSource
            this.mApkFile = null
        }

        constructor(File file) {
            if (file == null) {
                throw NullPointerException("apk == null")
            }
            this.mApkFile = file
            this.mApkDataSource = null
        }

        fun build() {
            return ApkVerifier(this.mApkFile, this.mApkDataSource, this.mV4SignatureFile, this.mMinSdkVersion, this.mMaxSdkVersion)
        }

        fun setMaxCheckedPlatformVersion(Int i) {
            this.mMaxSdkVersion = i
            return this
        }

        fun setMinCheckedPlatformVersion(Int i) {
            this.mMinSdkVersion = Integer.valueOf(i)
            return this
        }

        fun setV4SignatureFile(File file) {
            this.mV4SignatureFile = file
            return this
        }
    }

    public static class ByteArray {
        public final Array<Byte> mArray
        public final Int mHashCode

        constructor(Array<Byte> bArr) {
            this.mArray = bArr
            this.mHashCode = Arrays.hashCode(bArr)
        }

        fun equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (!(obj is ByteArray)) {
                return false
            }
            ByteArray byteArray = (ByteArray) obj
            return hashCode() == byteArray.hashCode() && Arrays.equals(this.mArray, byteArray.mArray)
        }

        fun hashCode() {
            return this.mHashCode
        }
    }

    public enum Issue {
        JAR_SIG_NO_SIGNATURES("No JAR signatures"),
        JAR_SIG_NO_SIGNED_ZIP_ENTRIES("No JAR entries covered by JAR signatures"),
        JAR_SIG_DUPLICATE_ZIP_ENTRY("Duplicate entry: %1$s"),
        JAR_SIG_DUPLICATE_MANIFEST_SECTION("Duplicate section in META-INF/MANIFEST.MF: %1$s"),
        JAR_SIG_UNNNAMED_MANIFEST_SECTION("Malformed META-INF/MANIFEST.MF: invidual section #%1$d does not have a name"),
        JAR_SIG_UNNNAMED_SIG_FILE_SECTION("Malformed %1$s: invidual section #%2$d does not have a name"),
        JAR_SIG_NO_MANIFEST("Missing META-INF/MANIFEST.MF"),
        JAR_SIG_MISSING_ZIP_ENTRY_REFERENCED_IN_MANIFEST("%1$s entry referenced by META-INF/MANIFEST.MF not found in the APK"),
        JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST("No digest for %1$s in META-INF/MANIFEST.MF"),
        JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE("No digest for %1$s in %2$s"),
        JAR_SIG_ZIP_ENTRY_NOT_SIGNED("%1$s entry not signed"),
        JAR_SIG_ZIP_ENTRY_SIGNERS_MISMATCH("Entries %1$s and %3$s are signed with different sets of signers : <%2$s> vs <%4$s>"),
        JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY("%2$s digest of %1$s does not match the digest specified in %3$s. Expected: <%5$s>, actual: <%4$s>"),
        JAR_SIG_MANIFEST_MAIN_SECTION_DIGEST_DID_NOT_VERIFY("%1$s digest of META-INF/MANIFEST.MF main section does not match the digest specified in %2$s. Expected: <%4$s>, actual: <%3$s>"),
        JAR_SIG_MANIFEST_SECTION_DIGEST_DID_NOT_VERIFY("%2$s digest of META-INF/MANIFEST.MF section for %1$s does not match the digest specified in %3$s. Expected: <%5$s>, actual: <%4$s>"),
        JAR_SIG_NO_MANIFEST_DIGEST_IN_SIG_FILE("%1$s does not specify digest of META-INF/MANIFEST.MF. This slows down verification."),
        JAR_SIG_NO_APK_SIG_STRIP_PROTECTION("APK is signed using APK Signature Scheme v2 but these signatures may be stripped without being detected because %1$s does not contain anti-stripping protections."),
        JAR_SIG_MISSING_FILE("Partial JAR signature. Found: %1$s, missing: %2$s"),
        JAR_SIG_VERIFY_EXCEPTION("Failed to verify JAR signature %1$s against %2$s: %3$s"),
        JAR_SIG_UNSUPPORTED_SIG_ALG("JAR signature %1$s uses digest algorithm %5$s and signature algorithm %6$s which is not supported on API Level(s) %4$s for which this APK is being verified"),
        JAR_SIG_PARSE_EXCEPTION("Failed to parse JAR signature %1$s: %2$s"),
        JAR_SIG_MALFORMED_CERTIFICATE("Malformed certificate in JAR signature %1$s: %2$s"),
        JAR_SIG_DID_NOT_VERIFY("JAR signature %1$s did not verify against %2$s"),
        JAR_SIG_NO_SIGNERS("JAR signature %1$s contains no signers"),
        JAR_SIG_DUPLICATE_SIG_FILE_SECTION("Duplicate section in %1$s: %2$s"),
        JAR_SIG_MISSING_VERSION_ATTR_IN_SIG_FILE("Malformed %1$s: missing Signature-Version attribute"),
        JAR_SIG_UNKNOWN_APK_SIG_SCHEME_ID("JAR signature %1$s references unknown APK signature scheme ID: %2$d"),
        JAR_SIG_MISSING_APK_SIG_REFERENCED("JAR signature %1$s indicates the APK is signed using %3$s but no such signature was found. Signature stripped?"),
        JAR_SIG_UNPROTECTED_ZIP_ENTRY("%1$s not protected by signature. Unauthorized modifications to this JAR entry will not be detected. Delete or move the entry outside of META-INF/."),
        JAR_SIG_MISSING("No JAR signature from this signer"),
        NO_SIG_FOR_TARGET_SANDBOX_VERSION("Missing APK Signature Scheme v2 signature required for target sandbox version %1$d"),
        MIN_SIG_SCHEME_FOR_TARGET_SDK_NOT_MET("Target SDK version %1$d requires a minimum of signature scheme v%2$d; the APK is not signed with this or a later signature scheme"),
        V2_SIG_MISSING("No APK Signature Scheme v2 signature from this signer"),
        V2_SIG_MALFORMED_SIGNERS("Malformed list of signers"),
        V2_SIG_MALFORMED_SIGNER("Malformed signer block"),
        V2_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V2_SIG_MALFORMED_CERTIFICATE("Malformed certificate #%2$d: %3$s"),
        V2_SIG_MALFORMED_SIGNATURE("Malformed APK Signature Scheme v2 signature record #%1$d"),
        V2_SIG_MALFORMED_DIGEST("Malformed APK Signature Scheme v2 digest record #%1$d"),
        V2_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE("Malformed additional attribute #%1$d"),
        V2_SIG_UNKNOWN_APK_SIG_SCHEME_ID("APK Signature Scheme v2 signer: %1$s references unknown APK signature scheme ID: %2$d"),
        V2_SIG_MISSING_APK_SIG_REFERENCED("APK Signature Scheme v2 signature %1$s indicates the APK is signed using %2$s but no such signature was found. Signature stripped?"),
        V2_SIG_NO_SIGNERS("No signers in APK Signature Scheme v2 signature"),
        V2_SIG_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        V2_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE("Unknown additional attribute: ID %1$#x"),
        V2_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V2_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V2_SIG_NO_SIGNATURES("No signatures"),
        V2_SIG_NO_SUPPORTED_SIGNATURES("No supported signatures: %1$s"),
        V2_SIG_NO_CERTIFICATES("No certificates"),
        V2_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("Public key mismatch between certificate and signature record: <%1$s> vs <%2$s>"),
        V2_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS("Signature algorithms mismatch between signatures and digests records: %1$s vs %2$s"),
        V2_SIG_APK_DIGEST_DID_NOT_VERIFY("APK integrity check failed. %1$s digest mismatch. Expected: <%2$s>, actual: <%3$s>"),
        V3_SIG_MALFORMED_SIGNERS("Malformed list of signers"),
        V3_SIG_MALFORMED_SIGNER("Malformed signer block"),
        V3_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V3_SIG_MALFORMED_CERTIFICATE("Malformed certificate #%2$d: %3$s"),
        V3_SIG_MALFORMED_SIGNATURE("Malformed APK Signature Scheme v3 signature record #%1$d"),
        V3_SIG_MALFORMED_DIGEST("Malformed APK Signature Scheme v3 digest record #%1$d"),
        V3_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE("Malformed additional attribute #%1$d"),
        V3_SIG_NO_SIGNERS("No signers in APK Signature Scheme v3 signature"),
        V3_SIG_MULTIPLE_SIGNERS("Multiple APK Signature Scheme v3 signatures found for a single  platform version."),
        V3_SIG_MULTIPLE_PAST_SIGNERS("Multiple signatures found for pre-v3 signing with an APK  Signature Scheme v3 signer.  Only one allowed."),
        V3_SIG_PAST_SIGNERS_MISMATCH("v3 signer differs from v1/v2 signer without proper signing certificate lineage."),
        V3_SIG_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        V3_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE("Unknown additional attribute: ID %1$#x"),
        V3_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V3_SIG_INVALID_SDK_VERSIONS("Invalid SDK Version parameter(s) encountered in APK Signature scheme v3 signature: minSdkVersion %1$s maxSdkVersion: %2$s"),
        V3_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V3_SIG_NO_SIGNATURES("No signatures"),
        V3_SIG_NO_SUPPORTED_SIGNATURES("No supported signatures"),
        V3_SIG_NO_CERTIFICATES("No certificates"),
        V3_MIN_SDK_VERSION_MISMATCH_BETWEEN_SIGNER_AND_SIGNED_DATA_RECORD("minSdkVersion mismatch between signed data and signature record: <%1$s> vs <%2$s>"),
        V3_MAX_SDK_VERSION_MISMATCH_BETWEEN_SIGNER_AND_SIGNED_DATA_RECORD("maxSdkVersion mismatch between signed data and signature record: <%1$s> vs <%2$s>"),
        V3_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("Public key mismatch between certificate and signature record: <%1$s> vs <%2$s>"),
        V3_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS("Signature algorithms mismatch between signatures and digests records: %1$s vs %2$s"),
        V3_SIG_APK_DIGEST_DID_NOT_VERIFY("APK integrity check failed. %1$s digest mismatch. Expected: <%2$s>, actual: <%3$s>"),
        V3_SIG_POR_DID_NOT_VERIFY("SigningCertificateLineage attribute containd a proof-of-rotation record with signature(s) that did not verify."),
        V3_SIG_MALFORMED_LINEAGE("Failed to parse the SigningCertificateLineage structure in the APK Signature Scheme v3 signature's additional attributes section."),
        V3_SIG_POR_CERT_MISMATCH("APK signing certificate differs from the associated certificate found in the signer's SigningCertificateLineage."),
        V3_INCONSISTENT_SDK_VERSIONS("APK Signature Scheme v3 signers supported min/max SDK versions are not continuous."),
        V3_MISSING_SDK_VERSIONS("APK Signature Scheme v3 signers supported min/max SDK versions do not cover the entire desired range.  Found min:  %1$s max %2$s"),
        V3_INCONSISTENT_LINEAGES("SigningCertificateLineages targeting different platform versions using APK Signature Scheme v3 are not all a part of the same overall lineage."),
        APK_SIG_BLOCK_UNKNOWN_ENTRY_ID("APK Signing Block contains unknown entry: ID %1$#x"),
        V4_SIG_MALFORMED_SIGNERS("V4 signature has malformed signer block"),
        V4_SIG_UNKNOWN_SIG_ALGORITHM("V4 signature has unknown signing algorithm: %1$#x"),
        V4_SIG_NO_SIGNATURES("V4 signature has no signature found"),
        V4_SIG_NO_SUPPORTED_SIGNATURES("V4 signature has no supported signature"),
        V4_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V4_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V4_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V4_SIG_MALFORMED_CERTIFICATE("V4 signature has malformed certificate"),
        V4_SIG_NO_CERTIFICATE("V4 signature has no certificate"),
        V4_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("V4 signature has mismatched certificate and signature: <%1$s> vs <%2$s>"),
        V4_SIG_APK_ROOT_DID_NOT_VERIFY("V4 signature's hash tree root (content digest) did not verity"),
        V4_SIG_APK_TREE_DID_NOT_VERIFY("V4 signature's hash tree did not verity"),
        V4_SIG_MULTIPLE_SIGNERS("V4 signature only supports one signer"),
        V4_SIG_V2_V3_SIGNERS_MISMATCH("V4 signature and V2/V3 signature have mismatched certificates"),
        V4_SIG_V2_V3_DIGESTS_MISMATCH("V4 signature and V2/V3 signature have mismatched digests"),
        V4_SIG_VERSION_NOT_CURRENT("V4 signature format version %1$d is different from the tool's current version %2$d"),
        SOURCE_STAMP_CERT_DIGEST_AND_SIG_BLOCK_MISSING("Neither the source stamp certificate digest file nor the signature block are present in the APK"),
        SOURCE_STAMP_SIG_MISSING("No SourceStamp signature"),
        SOURCE_STAMP_MALFORMED_CERTIFICATE("Malformed certificate: %1$s"),
        SOURCE_STAMP_MALFORMED_SIGNATURE("Malformed SourceStamp signature"),
        SOURCE_STAMP_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        SOURCE_STAMP_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        SOURCE_STAMP_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        SOURCE_STAMP_NO_SIGNATURE("No signature"),
        SOURCE_STAMP_NO_SUPPORTED_SIGNATURE("Signature(s) {%1$s} not supported: %2$s"),
        SOURCE_STAMP_CERTIFICATE_MISMATCH_BETWEEN_SIGNATURE_BLOCK_AND_APK("Certificate mismatch between SourceStamp block in APK signing block and SourceStamp file in APK: <%1$s> vs <%2$s>"),
        SOURCE_STAMP_SIGNATURE_BLOCK_WITHOUT_CERT_DIGEST("A source stamp signature block was found without a corresponding certificate digest in the APK"),
        SOURCE_STAMP_EXPECTED_DIGEST_MISMATCH("The source stamp certificate digest in the APK, %1$s, does not match the expected digest, %2$s"),
        SOURCE_STAMP_MALFORMED_ATTRIBUTE("Malformed stamp attribute #%1$d"),
        SOURCE_STAMP_UNKNOWN_ATTRIBUTE("Unknown stamp attribute: ID %1$#x"),
        SOURCE_STAMP_MALFORMED_LINEAGE("Failed to parse the SigningCertificateLineage structure in the source stamp attributes section."),
        SOURCE_STAMP_POR_CERT_MISMATCH("APK signing certificate differs from the associated certificate found in the signer's SigningCertificateLineage."),
        SOURCE_STAMP_POR_DID_NOT_VERIFY("Source stamp SigningCertificateLineage attribute contains a proof-of-rotation record with signature(s) that did not verify."),
        MALFORMED_APK("Malformed APK; the following exception was caught when attempting to parse the APK: %1$s"),
        UNEXPECTED_EXCEPTION("An unexpected exception was caught when verifying the signature: %1$s")

        public final String mFormat

        Issue(String str) {
            this.mFormat = str
        }

        public final String getFormat() {
            return this.mFormat
        }
    }

    public static class IssueWithParams extends ApkVerificationIssue {
        public final Issue mIssue
        public final Array<Object> mParams

        constructor(Issue issue, Array<Object> objArr) {
            super(issue.mFormat, objArr)
            this.mIssue = issue
            this.mParams = objArr
        }

        fun getIssue() {
            return this.mIssue
        }

        @Override // com.android.apksig.ApkVerificationIssue
        public Array<Object> getParams() {
            return (Array<Object>) this.mParams.clone()
        }

        @Override // com.android.apksig.ApkVerificationIssue
        fun toString() {
            return String.format(this.mIssue.getFormat(), this.mParams)
        }
    }

    public static class Result {
        public SigningCertificateLineage mSigningCertificateLineage
        public SourceStampInfo mSourceStampInfo
        public Boolean mSourceStampVerified
        public Boolean mVerified
        public Boolean mVerifiedUsingV1Scheme
        public Boolean mVerifiedUsingV2Scheme
        public Boolean mVerifiedUsingV3Scheme
        public Boolean mVerifiedUsingV4Scheme
        public Boolean mWarningsAsErrors
        public final List<IssueWithParams> mErrors = ArrayList()
        public final List<IssueWithParams> mWarnings = ArrayList()
        public final List<X509Certificate> mSignerCerts = ArrayList()
        public final List<V1SchemeSignerInfo> mV1SchemeSigners = ArrayList()
        public final List<V1SchemeSignerInfo> mV1SchemeIgnoredSigners = ArrayList()
        public final List<V2SchemeSignerInfo> mV2SchemeSigners = ArrayList()
        public final List<V3SchemeSignerInfo> mV3SchemeSigners = ArrayList()
        public final List<V4SchemeSignerInfo> mV4SchemeSigners = ArrayList()

        public static class SourceStampInfo {
            public final List<X509Certificate> mCertificateLineage
            public final List<X509Certificate> mCertificates
            public final List<IssueWithParams> mErrors
            public final SourceStampVerificationStatus mSourceStampVerificationStatus
            public final List<IssueWithParams> mWarnings

            public enum SourceStampVerificationStatus {
                STAMP_VERIFIED,
                STAMP_VERIFICATION_FAILED,
                CERT_DIGEST_MISMATCH,
                STAMP_MISSING,
                STAMP_NOT_VERIFIED,
                VERIFICATION_ERROR
            }

            constructor(SourceStampVerificationStatus sourceStampVerificationStatus) {
                this.mCertificates = Collections.emptyList()
                this.mCertificateLineage = Collections.emptyList()
                this.mErrors = Collections.emptyList()
                this.mWarnings = Collections.emptyList()
                this.mSourceStampVerificationStatus = sourceStampVerificationStatus
            }

            constructor(ApkSignerInfo apkSignerInfo) {
                this.mCertificates = apkSignerInfo.certs
                this.mCertificateLineage = apkSignerInfo.certificateLineage
                List<IssueWithParams> issuesFromVerificationIssues = ApkVerificationIssueAdapter.getIssuesFromVerificationIssues(apkSignerInfo.getErrors())
                this.mErrors = issuesFromVerificationIssues
                List<IssueWithParams> issuesFromVerificationIssues2 = ApkVerificationIssueAdapter.getIssuesFromVerificationIssues(apkSignerInfo.getWarnings())
                this.mWarnings = issuesFromVerificationIssues2
                if (issuesFromVerificationIssues.isEmpty() && issuesFromVerificationIssues2.isEmpty()) {
                    this.mSourceStampVerificationStatus = SourceStampVerificationStatus.STAMP_VERIFIED
                } else {
                    this.mSourceStampVerificationStatus = SourceStampVerificationStatus.STAMP_VERIFICATION_FAILED
                }
            }

            fun containsErrors() {
                return !this.mErrors.isEmpty()
            }

            fun getCertificate() {
                if (this.mCertificates.isEmpty()) {
                    return null
                }
                return this.mCertificates.get(0)
            }

            public List<X509Certificate> getCertificatesInLineage() {
                return this.mCertificateLineage
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors
            }

            fun getSourceStampVerificationStatus() {
                return this.mSourceStampVerificationStatus
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings
            }
        }

        public static class V1SchemeSignerInfo {
            public final List<X509Certificate> mCertChain
            public final List<IssueWithParams> mErrors
            public final String mName
            public final String mSignatureBlockFileName
            public final String mSignatureFileName
            public final List<IssueWithParams> mWarnings

            constructor(V1SchemeVerifier.Result.SignerInfo signerInfo) {
                this.mName = signerInfo.name
                this.mCertChain = signerInfo.certChain
                this.mSignatureBlockFileName = signerInfo.signatureBlockFileName
                this.mSignatureFileName = signerInfo.signatureFileName
                this.mErrors = signerInfo.getErrors()
                this.mWarnings = signerInfo.getWarnings()
            }

            public final Unit addError(Issue issue, Object... objArr) {
                this.mErrors.add(IssueWithParams(issue, objArr))
            }

            fun containsErrors() {
                return !this.mErrors.isEmpty()
            }

            fun getCertificate() {
                if (this.mCertChain.isEmpty()) {
                    return null
                }
                return this.mCertChain.get(0)
            }

            public List<X509Certificate> getCertificateChain() {
                return this.mCertChain
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors
            }

            fun getName() {
                return this.mName
            }

            fun getSignatureBlockFileName() {
                return this.mSignatureBlockFileName
            }

            fun getSignatureFileName() {
                return this.mSignatureFileName
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings
            }
        }

        public static class V2SchemeSignerInfo {
            public final List<X509Certificate> mCerts
            public final List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> mContentDigests
            public final List<IssueWithParams> mErrors
            public final Int mIndex
            public final List<IssueWithParams> mWarnings

            constructor(ApkSigningBlockUtils.Result.SignerInfo signerInfo) {
                this.mIndex = signerInfo.index
                this.mCerts = signerInfo.certs
                this.mErrors = signerInfo.getErrors()
                this.mWarnings = signerInfo.getWarnings()
                this.mContentDigests = signerInfo.contentDigests
            }

            public final Unit addError(Issue issue, Object... objArr) {
                this.mErrors.add(IssueWithParams(issue, objArr))
            }

            fun containsErrors() {
                return !this.mErrors.isEmpty()
            }

            fun getCertificate() {
                if (this.mCerts.isEmpty()) {
                    return null
                }
                return this.mCerts.get(0)
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts
            }

            public List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> getContentDigests() {
                return this.mContentDigests
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors
            }

            fun getIndex() {
                return this.mIndex
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings
            }
        }

        public static class V3SchemeSignerInfo {
            public final List<X509Certificate> mCerts
            public final List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> mContentDigests
            public final List<IssueWithParams> mErrors
            public final Int mIndex
            public final List<IssueWithParams> mWarnings

            constructor(ApkSigningBlockUtils.Result.SignerInfo signerInfo) {
                this.mIndex = signerInfo.index
                this.mCerts = signerInfo.certs
                this.mErrors = signerInfo.getErrors()
                this.mWarnings = signerInfo.getWarnings()
                this.mContentDigests = signerInfo.contentDigests
            }

            fun containsErrors() {
                return !this.mErrors.isEmpty()
            }

            fun getCertificate() {
                if (this.mCerts.isEmpty()) {
                    return null
                }
                return this.mCerts.get(0)
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts
            }

            public List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> getContentDigests() {
                return this.mContentDigests
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors
            }

            fun getIndex() {
                return this.mIndex
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings
            }
        }

        public static class V4SchemeSignerInfo {
            public final List<X509Certificate> mCerts
            public final List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> mContentDigests
            public final List<IssueWithParams> mErrors
            public final Int mIndex
            public final List<IssueWithParams> mWarnings

            constructor(ApkSigningBlockUtils.Result.SignerInfo signerInfo) {
                this.mIndex = signerInfo.index
                this.mCerts = signerInfo.certs
                this.mErrors = signerInfo.getErrors()
                this.mWarnings = signerInfo.getWarnings()
                this.mContentDigests = signerInfo.contentDigests
            }

            fun containsErrors() {
                return !this.mErrors.isEmpty()
            }

            fun getCertificate() {
                if (this.mCerts.isEmpty()) {
                    return null
                }
                return this.mCerts.get(0)
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts
            }

            public List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> getContentDigests() {
                return this.mContentDigests
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors
            }

            fun getIndex() {
                return this.mIndex
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings
            }
        }

        fun addError(Issue issue, Object... objArr) {
            this.mErrors.add(IssueWithParams(issue, objArr))
        }

        public final Unit addSignerCertificate(X509Certificate x509Certificate) {
            this.mSignerCerts.add(x509Certificate)
        }

        fun addWarning(Issue issue, Object... objArr) {
            this.mWarnings.add(IssueWithParams(issue, objArr))
        }

        fun containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true
            }
            if (this.mWarningsAsErrors && !this.mWarnings.isEmpty()) {
                return true
            }
            if (!this.mV1SchemeSigners.isEmpty()) {
                for (V1SchemeSignerInfo v1SchemeSignerInfo : this.mV1SchemeSigners) {
                    if (v1SchemeSignerInfo.containsErrors()) {
                        return true
                    }
                    if (this.mWarningsAsErrors && !v1SchemeSignerInfo.getWarnings().isEmpty()) {
                        return true
                    }
                }
            }
            if (!this.mV2SchemeSigners.isEmpty()) {
                for (V2SchemeSignerInfo v2SchemeSignerInfo : this.mV2SchemeSigners) {
                    if (v2SchemeSignerInfo.containsErrors()) {
                        return true
                    }
                    if (this.mWarningsAsErrors && !v2SchemeSignerInfo.getWarnings().isEmpty()) {
                        return true
                    }
                }
            }
            if (!this.mV3SchemeSigners.isEmpty()) {
                for (V3SchemeSignerInfo v3SchemeSignerInfo : this.mV3SchemeSigners) {
                    if (v3SchemeSignerInfo.containsErrors()) {
                        return true
                    }
                    if (this.mWarningsAsErrors && !v3SchemeSignerInfo.getWarnings().isEmpty()) {
                        return true
                    }
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo
            if (sourceStampInfo == null) {
                return false
            }
            if (sourceStampInfo.containsErrors()) {
                return true
            }
            return this.mWarningsAsErrors && !this.mSourceStampInfo.getWarnings().isEmpty()
        }

        public List<IssueWithParams> getAllErrors() {
            ArrayList arrayList = ArrayList()
            arrayList.addAll(this.mErrors)
            if (this.mWarningsAsErrors) {
                arrayList.addAll(this.mWarnings)
            }
            if (!this.mV1SchemeSigners.isEmpty()) {
                for (V1SchemeSignerInfo v1SchemeSignerInfo : this.mV1SchemeSigners) {
                    arrayList.addAll(v1SchemeSignerInfo.mErrors)
                    if (this.mWarningsAsErrors) {
                        arrayList.addAll(v1SchemeSignerInfo.getWarnings())
                    }
                }
            }
            if (!this.mV2SchemeSigners.isEmpty()) {
                for (V2SchemeSignerInfo v2SchemeSignerInfo : this.mV2SchemeSigners) {
                    arrayList.addAll(v2SchemeSignerInfo.mErrors)
                    if (this.mWarningsAsErrors) {
                        arrayList.addAll(v2SchemeSignerInfo.getWarnings())
                    }
                }
            }
            if (!this.mV3SchemeSigners.isEmpty()) {
                for (V3SchemeSignerInfo v3SchemeSignerInfo : this.mV3SchemeSigners) {
                    arrayList.addAll(v3SchemeSignerInfo.mErrors)
                    if (this.mWarningsAsErrors) {
                        arrayList.addAll(v3SchemeSignerInfo.getWarnings())
                    }
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo
            if (sourceStampInfo != null) {
                arrayList.addAll(sourceStampInfo.getErrors())
                if (this.mWarningsAsErrors) {
                    arrayList.addAll(this.mSourceStampInfo.getWarnings())
                }
            }
            return arrayList
        }

        public List<IssueWithParams> getErrors() {
            if (!this.mWarningsAsErrors) {
                return this.mErrors
            }
            ArrayList arrayList = ArrayList()
            arrayList.addAll(this.mErrors)
            arrayList.addAll(this.mWarnings)
            return arrayList
        }

        public List<X509Certificate> getSignerCertificates() {
            return this.mSignerCerts
        }

        fun getSigningCertificateLineage() {
            return this.mSigningCertificateLineage
        }

        fun getSourceStampInfo() {
            return this.mSourceStampInfo
        }

        public List<V1SchemeSignerInfo> getV1SchemeIgnoredSigners() {
            return this.mV1SchemeIgnoredSigners
        }

        public List<V1SchemeSignerInfo> getV1SchemeSigners() {
            return this.mV1SchemeSigners
        }

        public List<V2SchemeSignerInfo> getV2SchemeSigners() {
            return this.mV2SchemeSigners
        }

        public List<V3SchemeSignerInfo> getV3SchemeSigners() {
            return this.mV3SchemeSigners
        }

        public final List<V4SchemeSignerInfo> getV4SchemeSigners() {
            return this.mV4SchemeSigners
        }

        public List<IssueWithParams> getWarnings() {
            return this.mWarnings
        }

        fun isSourceStampVerified() {
            return this.mSourceStampVerified
        }

        fun isVerified() {
            return this.mVerified
        }

        fun isVerifiedUsingV1Scheme() {
            return this.mVerifiedUsingV1Scheme
        }

        fun isVerifiedUsingV2Scheme() {
            return this.mVerifiedUsingV2Scheme
        }

        fun isVerifiedUsingV3Scheme() {
            return this.mVerifiedUsingV3Scheme
        }

        fun isVerifiedUsingV4Scheme() {
            return this.mVerifiedUsingV4Scheme
        }

        public final Unit mergeFrom(ApkSigResult apkSigResult) {
            if (apkSigResult.signatureSchemeVersion != 0) {
                throw IllegalArgumentException("Unknown ApkSigResult Signing Block Scheme Id " + apkSigResult.signatureSchemeVersion)
            }
            this.mSourceStampVerified = apkSigResult.verified
            if (apkSigResult.mSigners.isEmpty()) {
                return
            }
            this.mSourceStampInfo = SourceStampInfo(apkSigResult.mSigners.get(0))
        }

        public final Unit mergeFrom(ApkSigningBlockUtils.Result result) {
            Int i = result.signatureSchemeVersion
            if (i == 0) {
                this.mSourceStampVerified = result.verified
                if (result.signers.isEmpty()) {
                    return
                }
                this.mSourceStampInfo = SourceStampInfo(result.signers.get(0))
                return
            }
            if (i == 2) {
                this.mVerifiedUsingV2Scheme = result.verified
                Iterator<ApkSigningBlockUtils.Result.SignerInfo> it = result.signers.iterator()
                while (it.hasNext()) {
                    this.mV2SchemeSigners.add(V2SchemeSignerInfo(it.next()))
                }
                return
            }
            if (i == 3) {
                this.mVerifiedUsingV3Scheme = result.verified
                Iterator<ApkSigningBlockUtils.Result.SignerInfo> it2 = result.signers.iterator()
                while (it2.hasNext()) {
                    this.mV3SchemeSigners.add(V3SchemeSignerInfo(it2.next()))
                }
                this.mSigningCertificateLineage = result.signingCertificateLineage
                return
            }
            if (i != 4) {
                throw IllegalArgumentException("Unknown Signing Block Scheme Id")
            }
            this.mVerifiedUsingV4Scheme = result.verified
            Iterator<ApkSigningBlockUtils.Result.SignerInfo> it3 = result.signers.iterator()
            while (it3.hasNext()) {
                this.mV4SchemeSigners.add(V4SchemeSignerInfo(it3.next()))
            }
        }

        public final Unit mergeFrom(V1SchemeVerifier.Result result) {
            this.mVerifiedUsingV1Scheme = result.verified
            this.mErrors.addAll(result.getErrors())
            this.mWarnings.addAll(result.getWarnings())
            Iterator<V1SchemeVerifier.Result.SignerInfo> it = result.signers.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                this.mV1SchemeSigners.add(V1SchemeSignerInfo(it.next()))
            }
            Iterator<V1SchemeVerifier.Result.SignerInfo> it2 = result.ignoredSigners.iterator()
            while (it2.hasNext()) {
                this.mV1SchemeIgnoredSigners.add(V1SchemeSignerInfo(it2.next()))
            }
        }

        public final Unit setVerified() {
            this.mVerified = true
        }

        fun setWarningsAsErrors(Boolean z) {
            this.mWarningsAsErrors = z
        }
    }

    constructor(File file, DataSource dataSource, File file2, Integer num, Int i) {
        this.mApkFile = file
        this.mApkDataSource = dataSource
        this.mV4SignatureFile = file2
        this.mMinSdkVersion = num
        this.mMaxSdkVersion = i
    }

    fun checkV4Certificate(List<X509Certificate> list, List<X509Certificate> list2, Result result) {
        try {
            if (Arrays.equals(list2.get(0).getEncoded(), list.get(0).getEncoded())) {
                return
            }
            result.addError(Issue.V4_SIG_V2_V3_SIGNERS_MISMATCH, new Object[0])
        } catch (CertificateEncodingException e) {
            throw RuntimeException("Failed to encode APK signer cert", e)
        }
    }

    fun collectApkContentDigests(List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> list, Map<ContentDigestAlgorithm, Array<Byte>> map) {
        for (ApkSigningBlockUtils.Result.SignerInfo.ContentDigest contentDigest : list) {
            SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId())
            if (signatureAlgorithmFindById != null) {
                map.put(signatureAlgorithmFindById.getContentDigestAlgorithm(), contentDigest.getValue())
            }
        }
    }

    fun createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus sourceStampVerificationStatus, Issue issue, Object... objArr) {
        Result result = Result()
        result.addError(issue, objArr)
        return mergeSourceStampResult(sourceStampVerificationStatus, result)
    }

    fun getAndroidManifestFromApk(DataSource dataSource, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        try {
            return ApkSigner.getAndroidManifestFromApk(V1SchemeVerifier.parseZipCentralDirectory(dataSource, zipSections), dataSource.slice(0L, zipSections.getZipCentralDirectoryOffset()))
        } catch (ZipFormatException e) {
            throw ApkFormatException("Failed to read AndroidManifest.xml", e)
        }
    }

    public static Map<ContentDigestAlgorithm, Array<Byte>> getApkContentDigestFromV1SigningScheme(List<CentralDirectoryRecord> list, DataSource dataSource, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        CentralDirectoryRecord next
        EnumMap enumMap = EnumMap(ContentDigestAlgorithm.class)
        Iterator<CentralDirectoryRecord> it = list.iterator()
        while (true) {
            if (!it.hasNext()) {
                next = null
                break
            }
            next = it.next()
            if ("META-INF/MANIFEST.MF".equals(next.getName())) {
                break
            }
        }
        if (next == null) {
            return enumMap
        }
        try {
            enumMap.put((EnumMap) ContentDigestAlgorithm.SHA256, (ContentDigestAlgorithm) ApkUtils.computeSha256DigestBytes(LocalFileRecord.getUncompressedData(dataSource, next, zipSections.getZipCentralDirectoryOffset())))
            return enumMap
        } catch (ZipFormatException e) {
            throw ApkFormatException("Failed to read APK", e)
        }
    }

    public static Map<ContentDigestAlgorithm, Array<Byte>> getApkContentDigestsFromSigningSchemeResult(ApkSigningBlockUtils.Result result) {
        HashMap map = HashMap()
        Iterator<ApkSigningBlockUtils.Result.SignerInfo> it = result.signers.iterator()
        while (it.hasNext()) {
            collectApkContentDigests(it.next().contentDigests, map)
        }
        return map
    }

    fun getMinimumSignatureSchemeVersionForTargetSdk(Int i) {
        return i >= 30 ? 2 : 1
    }

    public static Map<Integer, String> getSupportedSchemeNames(Int i) {
        if (i >= 28) {
            return SUPPORTED_APK_SIG_SCHEME_NAMES
        }
        if (i < 24) {
            return Collections.emptyMap()
        }
        HashMap map = HashMap(1)
        map.put(2, SUPPORTED_APK_SIG_SCHEME_NAMES.get(2))
        return map
    }

    public static Map<Integer, String> loadSupportedApkSigSchemeNames() {
        HashMap map = HashMap(2)
        map.put(2, "APK Signature Scheme v2")
        map.put(3, "APK Signature Scheme v3")
        return map
    }

    fun mergeSourceStampResult(Result.SourceStampInfo.SourceStampVerificationStatus sourceStampVerificationStatus, Result result) {
        result.mSourceStampInfo = new Result.SourceStampInfo(sourceStampVerificationStatus)
        return result
    }

    public static Array<Byte> pickBestDigestForV4(List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> list) {
        HashMap map = HashMap()
        collectApkContentDigests(list, map)
        return ApkSigningBlockUtils.pickBestDigestForV4(map)
    }

    public final ApkSigningBlockUtils.Result getApkContentDigests(DataSource dataSource, ApkUtils.ZipSections zipSections, Set<Integer> set, Map<Integer, String> map, Map<Integer, Map<ContentDigestAlgorithm, Array<Byte>>> map2, Int i, Int i2) throws NoSuchAlgorithmException, IOException {
        if (i != 2 && i != 3) {
            return null
        }
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(i)
        try {
            SignatureInfo signatureInfoFindSignature = ApkSigningBlockUtils.findSignature(dataSource, zipSections, i == 3 ? -262969152 : 1896449818, result)
            set.add(Integer.valueOf(i))
            HashSet hashSet = HashSet(1)
            if (i == 2) {
                V2SchemeVerifier.parseSigners(signatureInfoFindSignature.signatureBlock, hashSet, map, set, i2, this.mMaxSdkVersion, result)
            } else {
                V3SchemeVerifier.parseSigners(signatureInfoFindSignature.signatureBlock, hashSet, result)
            }
            EnumMap enumMap = EnumMap(ContentDigestAlgorithm.class)
            Iterator<ApkSigningBlockUtils.Result.SignerInfo> it = result.signers.iterator()
            while (it.hasNext()) {
                for (ApkSigningBlockUtils.Result.SignerInfo.ContentDigest contentDigest : it.next().contentDigests) {
                    SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId())
                    if (signatureAlgorithmFindById != null) {
                        enumMap.put((EnumMap) signatureAlgorithmFindById.getContentDigestAlgorithm(), (ContentDigestAlgorithm) contentDigest.getValue())
                    }
                }
            }
            map2.put(Integer.valueOf(i), enumMap)
            return result
        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
            return null
        }
    }

    fun verify() throws Throwable {
        RandomAccessFile randomAccessFile = null
        try {
            DataSource dataSourceAsDataSource = this.mApkDataSource
            if (dataSourceAsDataSource == null) {
                if (this.mApkFile == null) {
                    throw IllegalStateException("APK not provided")
                }
                RandomAccessFile randomAccessFile2 = RandomAccessFile(this.mApkFile, "r")
                try {
                    dataSourceAsDataSource = DataSources.asDataSource(randomAccessFile2, 0L, randomAccessFile2.length())
                    randomAccessFile = randomAccessFile2
                } catch (Throwable th) {
                    th = th
                    randomAccessFile = randomAccessFile2
                    if (randomAccessFile != null) {
                        randomAccessFile.close()
                    }
                    throw th
                }
            }
            Result resultVerify = verify(dataSourceAsDataSource)
            if (randomAccessFile != null) {
                randomAccessFile.close()
            }
            return resultVerify
        } catch (Throwable th2) {
            th = th2
        }
    }

    public final Result verify(DataSource dataSource) throws NoSuchAlgorithmException, IOException, ApkFormatException {
        Map<Integer, String> map
        Int i
        ByteBuffer androidManifestFromApk
        List<CentralDirectoryRecord> list
        Int i2
        Int i3
        Int targetSdkVersionFromBinaryAndroidManifest
        Int minimumSignatureSchemeVersionForTargetSdk
        X509Certificate x509Certificate
        CentralDirectoryRecord next
        Int i4 = this.mMaxSdkVersion
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(dataSource)
            Int iVerifyAndGetMinSdkVersion = verifyAndGetMinSdkVersion(dataSource, zipSectionsFindZipSections)
            Result result = Result()
            HashMap map2 = HashMap()
            Map<Integer, String> supportedSchemeNames = getSupportedSchemeNames(i4)
            HashSet hashSet = HashSet(2)
            if (i4 >= 24) {
                RunnablesExecutor runnablesExecutor = RunnablesExecutor.SINGLE_THREADED
                if (i4 >= 28) {
                    try {
                        ApkSigningBlockUtils.Result resultVerify = V3SchemeVerifier.verify(runnablesExecutor, dataSource, zipSectionsFindZipSections, Math.max(iVerifyAndGetMinSdkVersion, 28), i4)
                        hashSet.add(3)
                        result.mergeFrom(resultVerify)
                        map2.put(3, getApkContentDigestsFromSigningSchemeResult(resultVerify))
                    } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
                    }
                    if (result.containsErrors()) {
                        return result
                    }
                }
                if (iVerifyAndGetMinSdkVersion < 28 || hashSet.isEmpty()) {
                    try {
                        map = supportedSchemeNames
                        i = 24
                        try {
                            ApkSigningBlockUtils.Result resultVerify2 = V2SchemeVerifier.verify(runnablesExecutor, dataSource, zipSectionsFindZipSections, supportedSchemeNames, hashSet, Math.max(iVerifyAndGetMinSdkVersion, 24), i4)
                            hashSet.add(2)
                            result.mergeFrom(resultVerify2)
                            map2.put(2, getApkContentDigestsFromSigningSchemeResult(resultVerify2))
                        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused2) {
                        }
                    } catch (ApkSigningBlockUtils.SignatureNotFoundException unused3) {
                        map = supportedSchemeNames
                        i = 24
                    }
                    if (result.containsErrors()) {
                        return result
                    }
                } else {
                    map = supportedSchemeNames
                    i = 24
                }
                File file = this.mV4SignatureFile
                if (file != null) {
                    ApkSigningBlockUtils.Result resultVerify3 = V4SchemeVerifier.verify(dataSource, file)
                    hashSet.add(4)
                    result.mergeFrom(resultVerify3)
                    if (result.containsErrors()) {
                        return result
                    }
                }
            } else {
                map = supportedSchemeNames
                i = 24
            }
            if (i4 >= 26) {
                ByteBuffer androidManifestFromApk2 = getAndroidManifestFromApk(dataSource, zipSectionsFindZipSections)
                Int targetSandboxVersionFromBinaryAndroidManifest = ApkUtils.getTargetSandboxVersionFromBinaryAndroidManifest(androidManifestFromApk2.slice())
                if (targetSandboxVersionFromBinaryAndroidManifest > 1 && hashSet.isEmpty()) {
                    result.addError(Issue.NO_SIG_FOR_TARGET_SANDBOX_VERSION, Integer.valueOf(targetSandboxVersionFromBinaryAndroidManifest))
                }
                androidManifestFromApk = androidManifestFromApk2
            } else {
                androidManifestFromApk = null
            }
            List<CentralDirectoryRecord> zipCentralDirectory = V1SchemeVerifier.parseZipCentralDirectory(dataSource, zipSectionsFindZipSections)
            if (iVerifyAndGetMinSdkVersion < i || hashSet.isEmpty()) {
                list = zipCentralDirectory
                i2 = 0
                result.mergeFrom(V1SchemeVerifier.verify(dataSource, zipSectionsFindZipSections, map, hashSet, iVerifyAndGetMinSdkVersion, i4))
                map2.put(1, getApkContentDigestFromV1SigningScheme(list, dataSource, zipSectionsFindZipSections))
            } else {
                list = zipCentralDirectory
                i2 = 0
            }
            if (result.containsErrors()) {
                return result
            }
            try {
                Iterator<CentralDirectoryRecord> it = list.iterator()
                while (true) {
                    if (!it.hasNext()) {
                        next = null
                        break
                    }
                    next = it.next()
                    if ("stamp-cert-sha256".equals(next.getName())) {
                        break
                    }
                }
                if (next != null) {
                    result.mergeFrom(V2SourceStampVerifier.verify(dataSource, zipSectionsFindZipSections, LocalFileRecord.getUncompressedData(dataSource, next, zipSectionsFindZipSections.getZipCentralDirectoryOffset()), map2, Math.max(iVerifyAndGetMinSdkVersion, 30), i4))
                }
            } catch (SignatureNotFoundException unused4) {
                result.addWarning(Issue.SOURCE_STAMP_SIG_MISSING, new Object[i2])
            } catch (ZipFormatException e) {
                throw ApkFormatException("Failed to read APK", e)
            }
            if (result.containsErrors()) {
                return result
            }
            if (result.isVerifiedUsingV1Scheme() && result.isVerifiedUsingV2Scheme()) {
                ArrayList arrayList = ArrayList(result.getV1SchemeSigners())
                ArrayList arrayList2 = ArrayList(result.getV2SchemeSigners())
                ArrayList arrayList3 = ArrayList()
                ArrayList arrayList4 = ArrayList()
                Iterator it2 = arrayList.iterator()
                while (it2.hasNext()) {
                    Result.V1SchemeSignerInfo v1SchemeSignerInfo = (Result.V1SchemeSignerInfo) it2.next()
                    try {
                        arrayList3.add(ByteArray(v1SchemeSignerInfo.getCertificate().getEncoded()))
                    } catch (CertificateEncodingException e2) {
                        throw IllegalStateException("Failed to encode JAR signer " + v1SchemeSignerInfo.getName() + " certs", e2)
                    }
                }
                Iterator it3 = arrayList2.iterator()
                while (it3.hasNext()) {
                    Result.V2SchemeSignerInfo v2SchemeSignerInfo = (Result.V2SchemeSignerInfo) it3.next()
                    try {
                        arrayList4.add(ByteArray(v2SchemeSignerInfo.getCertificate().getEncoded()))
                    } catch (CertificateEncodingException e3) {
                        throw IllegalStateException("Failed to encode APK Signature Scheme v2 signer (index: " + v2SchemeSignerInfo.getIndex() + ") certs", e3)
                    }
                }
                Int i5 = 0
                while (true) {
                    if (i5 >= arrayList3.size()) {
                        break
                    }
                    if (!arrayList4.contains((ByteArray) arrayList3.get(i5))) {
                        ((Result.V1SchemeSignerInfo) arrayList.get(i5)).addError(Issue.V2_SIG_MISSING, new Object[i2])
                        break
                    }
                    i5++
                }
                Int i6 = 0
                while (true) {
                    if (i6 >= arrayList4.size()) {
                        break
                    }
                    if (!arrayList3.contains((ByteArray) arrayList4.get(i6))) {
                        ((Result.V2SchemeSignerInfo) arrayList2.get(i6)).addError(Issue.JAR_SIG_MISSING, new Object[i2])
                        break
                    }
                    i6++
                }
            }
            if (result.isVerifiedUsingV3Scheme() && (result.isVerifiedUsingV1Scheme() || result.isVerifiedUsingV2Scheme())) {
                SigningCertificateLineage signingCertificateLineage = result.getSigningCertificateLineage()
                if (result.isVerifiedUsingV1Scheme()) {
                    List<Result.V1SchemeSignerInfo> v1SchemeSigners = result.getV1SchemeSigners()
                    i3 = 1
                    if (v1SchemeSigners.size() != 1) {
                        result.addError(Issue.V3_SIG_MULTIPLE_PAST_SIGNERS, new Object[i2])
                    }
                    x509Certificate = (X509Certificate) v1SchemeSigners.get(i2).mCertChain.get(i2)
                } else {
                    i3 = 1
                    List<Result.V2SchemeSignerInfo> v2SchemeSigners = result.getV2SchemeSigners()
                    if (v2SchemeSigners.size() != 1) {
                        result.addError(Issue.V3_SIG_MULTIPLE_PAST_SIGNERS, new Object[i2])
                    }
                    x509Certificate = (X509Certificate) v2SchemeSigners.get(i2).mCerts.get(i2)
                }
                if (signingCertificateLineage == null) {
                    List<Result.V3SchemeSignerInfo> v3SchemeSigners = result.getV3SchemeSigners()
                    if (v3SchemeSigners.size() != i3) {
                        result.addError(Issue.V3_SIG_MULTIPLE_SIGNERS, new Object[i2])
                    }
                    try {
                        if (!Arrays.equals(x509Certificate.getEncoded(), ((X509Certificate) v3SchemeSigners.get(i2).mCerts.get(i2)).getEncoded())) {
                            result.addError(Issue.V3_SIG_PAST_SIGNERS_MISMATCH, new Object[i2])
                        }
                    } catch (CertificateEncodingException e4) {
                        throw RuntimeException("Failed to encode APK Signature Scheme v3 signer cert", e4)
                    }
                } else {
                    try {
                        if (signingCertificateLineage.getSubLineage(x509Certificate).size() != i3) {
                            result.addError(Issue.V3_SIG_PAST_SIGNERS_MISMATCH, new Object[i2])
                        }
                    } catch (IllegalArgumentException unused5) {
                        result.addError(Issue.V3_SIG_PAST_SIGNERS_MISMATCH, new Object[i2])
                    }
                }
            } else {
                i3 = 1
            }
            if (result.isVerifiedUsingV4Scheme()) {
                List v4SchemeSigners = result.getV4SchemeSigners()
                if (v4SchemeSigners.size() != i3) {
                    result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[i2])
                }
                List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> contentDigests = ((Result.V4SchemeSignerInfo) v4SchemeSigners.get(i2)).getContentDigests()
                if (contentDigests.size() != i3) {
                    result.addError(Issue.V4_SIG_V2_V3_DIGESTS_MISMATCH, new Object[i2])
                }
                Array<Byte> value = contentDigests.get(i2).getValue()
                if (result.isVerifiedUsingV3Scheme()) {
                    List<Result.V3SchemeSignerInfo> v3SchemeSigners2 = result.getV3SchemeSigners()
                    if (v3SchemeSigners2.size() != i3) {
                        result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[i2])
                    }
                    checkV4Certificate(((Result.V4SchemeSignerInfo) v4SchemeSigners.get(i2)).mCerts, v3SchemeSigners2.get(i2).mCerts, result)
                    if (!Arrays.equals(value, pickBestDigestForV4(v3SchemeSigners2.get(i2).getContentDigests()))) {
                        result.addError(Issue.V4_SIG_V2_V3_DIGESTS_MISMATCH, new Object[i2])
                    }
                } else {
                    if (!result.isVerifiedUsingV2Scheme()) {
                        throw RuntimeException("V4 signature must be also verified with V2/V3")
                    }
                    List<Result.V2SchemeSignerInfo> v2SchemeSigners2 = result.getV2SchemeSigners()
                    if (v2SchemeSigners2.size() != i3) {
                        result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[i2])
                    }
                    checkV4Certificate(((Result.V4SchemeSignerInfo) v4SchemeSigners.get(i2)).mCerts, v2SchemeSigners2.get(i2).mCerts, result)
                    if (!Arrays.equals(value, pickBestDigestForV4(v2SchemeSigners2.get(i2).getContentDigests()))) {
                        result.addError(Issue.V4_SIG_V2_V3_DIGESTS_MISMATCH, new Object[i2])
                    }
                }
            }
            if (androidManifestFromApk == null) {
                try {
                    androidManifestFromApk = getAndroidManifestFromApk(dataSource, zipSectionsFindZipSections)
                } catch (ApkFormatException unused6) {
                }
            }
            if (androidManifestFromApk != null && (minimumSignatureSchemeVersionForTargetSdk = getMinimumSignatureSchemeVersionForTargetSdk((targetSdkVersionFromBinaryAndroidManifest = ApkUtils.getTargetSdkVersionFromBinaryAndroidManifest(androidManifestFromApk.slice())))) > i3 && i4 >= targetSdkVersionFromBinaryAndroidManifest && (minimumSignatureSchemeVersionForTargetSdk == 2 ? !result.isVerifiedUsingV2Scheme() : minimumSignatureSchemeVersionForTargetSdk == 3) && !result.isVerifiedUsingV3Scheme()) {
                Issue issue = Issue.MIN_SIG_SCHEME_FOR_TARGET_SDK_NOT_MET
                Array<Object> objArr = new Object[2]
                objArr[i2] = Integer.valueOf(targetSdkVersionFromBinaryAndroidManifest)
                objArr[i3] = Integer.valueOf(minimumSignatureSchemeVersionForTargetSdk)
                result.addError(issue, objArr)
            }
            if (result.containsErrors()) {
                return result
            }
            result.setVerified()
            if (result.isVerifiedUsingV3Scheme()) {
                List<Result.V3SchemeSignerInfo> v3SchemeSigners3 = result.getV3SchemeSigners()
                result.addSignerCertificate(v3SchemeSigners3.get(v3SchemeSigners3.size() - i3).getCertificate())
            } else if (result.isVerifiedUsingV2Scheme()) {
                Iterator<Result.V2SchemeSignerInfo> it4 = result.getV2SchemeSigners().iterator()
                while (it4.hasNext()) {
                    result.addSignerCertificate(it4.next().getCertificate())
                }
            } else {
                if (!result.isVerifiedUsingV1Scheme()) {
                    throw RuntimeException("APK verified, but has not verified using any of v1, v2 or v3 schemes")
                }
                Iterator<Result.V1SchemeSignerInfo> it5 = result.getV1SchemeSigners().iterator()
                while (it5.hasNext()) {
                    result.addSignerCertificate(it5.next().getCertificate())
                }
            }
            return result
        } catch (ZipFormatException e5) {
            throw ApkFormatException("Malformed APK: not a ZIP archive", e5)
        }
    }

    public final Int verifyAndGetMinSdkVersion(DataSource dataSource, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        Integer num = this.mMinSdkVersion
        if (num == null) {
            Int minSdkVersionFromBinaryAndroidManifest = ApkUtils.getMinSdkVersionFromBinaryAndroidManifest(getAndroidManifestFromApk(dataSource, zipSections).slice())
            if (minSdkVersionFromBinaryAndroidManifest <= this.mMaxSdkVersion) {
                return minSdkVersionFromBinaryAndroidManifest
            }
            throw IllegalArgumentException("minSdkVersion from APK (" + minSdkVersionFromBinaryAndroidManifest + ") > maxSdkVersion (" + this.mMaxSdkVersion + ")")
        }
        if (num.intValue() < 0) {
            throw IllegalArgumentException("minSdkVersion must not be negative: " + this.mMinSdkVersion)
        }
        Integer num2 = this.mMinSdkVersion
        if (num2 == null || num2.intValue() <= this.mMaxSdkVersion) {
            return this.mMinSdkVersion.intValue()
        }
        throw IllegalArgumentException("minSdkVersion (" + this.mMinSdkVersion + ") > maxSdkVersion (" + this.mMaxSdkVersion + ")")
    }

    fun verifySourceStamp() {
        return verifySourceStamp(null)
    }

    public final Result verifySourceStamp(DataSource dataSource, String str) {
        Boolean z
        Result result
        HashSet hashSet
        HashMap map
        Int i
        Array<Byte> bArr
        Int i2
        HashMap map2
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(dataSource)
            Int iVerifyAndGetMinSdkVersion = verifyAndGetMinSdkVersion(dataSource, zipSectionsFindZipSections)
            List<CentralDirectoryRecord> zipCentralDirectory = V1SchemeVerifier.parseZipCentralDirectory(dataSource, zipSectionsFindZipSections)
            CentralDirectoryRecord centralDirectoryRecord = null
            Iterator<CentralDirectoryRecord> it = zipCentralDirectory.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                CentralDirectoryRecord next = it.next()
                if ("stamp-cert-sha256".equals(next.getName())) {
                    centralDirectoryRecord = next
                    break
                }
            }
            if (centralDirectoryRecord == null) {
                try {
                    ApkSigningBlockUtils.findSignature(dataSource, zipSectionsFindZipSections, 1845461005, new ApkSigningBlockUtils.Result(0))
                    z = true
                } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
                    z = false
                }
                return z ? createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.STAMP_NOT_VERIFIED, Issue.SOURCE_STAMP_SIGNATURE_BLOCK_WITHOUT_CERT_DIGEST, new Object[0]) : createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.STAMP_MISSING, Issue.SOURCE_STAMP_CERT_DIGEST_AND_SIG_BLOCK_MISSING, new Object[0])
            }
            Array<Byte> uncompressedData = LocalFileRecord.getUncompressedData(dataSource, centralDirectoryRecord, zipSectionsFindZipSections.getZipCentralDirectoryOffset())
            if (str != null) {
                String hex = ApkSigningBlockUtils.toHex(uncompressedData)
                if (!str.equalsIgnoreCase(hex)) {
                    return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.CERT_DIGEST_MISMATCH, Issue.SOURCE_STAMP_EXPECTED_DIGEST_MISMATCH, hex, str)
                }
            }
            HashMap map3 = HashMap()
            Map<Integer, String> supportedSchemeNames = getSupportedSchemeNames(this.mMaxSdkVersion)
            HashSet hashSet2 = HashSet(2)
            Result result2 = Result()
            if (this.mMaxSdkVersion >= 28) {
                i = 28
                result = result2
                hashSet = hashSet2
                map = map3
                ApkSigningBlockUtils.Result apkContentDigests = getApkContentDigests(dataSource, zipSectionsFindZipSections, hashSet2, supportedSchemeNames, map3, 3, Math.max(iVerifyAndGetMinSdkVersion, 28))
                if (apkContentDigests != null && apkContentDigests.containsErrors()) {
                    result.mergeFrom(apkContentDigests)
                    return mergeSourceStampResult(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, result)
                }
            } else {
                result = result2
                hashSet = hashSet2
                map = map3
                i = 28
            }
            if (this.mMaxSdkVersion < 24 || (iVerifyAndGetMinSdkVersion >= i && !hashSet.isEmpty())) {
                bArr = uncompressedData
                i2 = 24
            } else {
                bArr = uncompressedData
                i2 = 24
                ApkSigningBlockUtils.Result apkContentDigests2 = getApkContentDigests(dataSource, zipSectionsFindZipSections, hashSet, supportedSchemeNames, map, 2, Math.max(iVerifyAndGetMinSdkVersion, 24))
                if (apkContentDigests2 != null && apkContentDigests2.containsErrors()) {
                    result.mergeFrom(apkContentDigests2)
                    return mergeSourceStampResult(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, result)
                }
            }
            if (iVerifyAndGetMinSdkVersion < i2 || hashSet.isEmpty()) {
                map2 = map
                map2.put(1, getApkContentDigestFromV1SigningScheme(zipCentralDirectory, dataSource, zipSectionsFindZipSections))
            } else {
                map2 = map
            }
            ApkSigResult apkSigResultVerify = V2SourceStampVerifier.verify(dataSource, zipSectionsFindZipSections, bArr, map2, iVerifyAndGetMinSdkVersion, this.mMaxSdkVersion)
            result.mergeFrom(apkSigResultVerify)
            if (apkSigResultVerify.verified) {
                result.setVerified()
            } else {
                result.setWarningsAsErrors(true)
            }
            return result
        } catch (ApkFormatException e) {
            e = e
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.MALFORMED_APK, e)
        } catch (SignatureNotFoundException unused2) {
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.STAMP_NOT_VERIFIED, Issue.SOURCE_STAMP_SIG_MISSING, new Object[0])
        } catch (ZipFormatException e2) {
            e = e2
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.MALFORMED_APK, e)
        } catch (IOException e3) {
            e = e3
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.MALFORMED_APK, e)
        } catch (NoSuchAlgorithmException e4) {
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.UNEXPECTED_EXCEPTION, e4)
        }
    }

    fun verifySourceStamp(String str) throws Throwable {
        RandomAccessFile randomAccessFile = null
        try {
            try {
                DataSource dataSourceAsDataSource = this.mApkDataSource
                if (dataSourceAsDataSource == null) {
                    if (this.mApkFile == null) {
                        throw IllegalStateException("APK not provided")
                    }
                    RandomAccessFile randomAccessFile2 = RandomAccessFile(this.mApkFile, "r")
                    try {
                        dataSourceAsDataSource = DataSources.asDataSource(randomAccessFile2, 0L, randomAccessFile2.length())
                        randomAccessFile = randomAccessFile2
                    } catch (IOException e) {
                        e = e
                        randomAccessFile = randomAccessFile2
                        Result resultCreateSourceStampResultWithError = createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.UNEXPECTED_EXCEPTION, e)
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close()
                            } catch (IOException unused) {
                            }
                        }
                        return resultCreateSourceStampResultWithError
                    } catch (Throwable th) {
                        th = th
                        randomAccessFile = randomAccessFile2
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close()
                            } catch (IOException unused2) {
                            }
                        }
                        throw th
                    }
                }
                Result resultVerifySourceStamp = verifySourceStamp(dataSourceAsDataSource, str)
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close()
                    } catch (IOException unused3) {
                    }
                }
                return resultVerifySourceStamp
            } catch (IOException e2) {
                e = e2
            }
        } catch (Throwable th2) {
            th = th2
        }
    }
}
