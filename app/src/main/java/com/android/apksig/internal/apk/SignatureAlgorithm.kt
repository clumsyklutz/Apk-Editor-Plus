package com.android.apksig.internal.apk

import androidx.core.view.InputDeviceCompat
import com.android.apksig.internal.util.Pair
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'RSA_PSS_WITH_SHA256' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
class SignatureAlgorithm {
    public static final /* synthetic */ Array<SignatureAlgorithm> $VALUES
    public static final SignatureAlgorithm DSA_WITH_SHA256
    public static final SignatureAlgorithm ECDSA_WITH_SHA256
    public static final SignatureAlgorithm ECDSA_WITH_SHA512
    public static final SignatureAlgorithm RSA_PKCS1_V1_5_WITH_SHA256
    public static final SignatureAlgorithm RSA_PKCS1_V1_5_WITH_SHA512
    public static final SignatureAlgorithm RSA_PSS_WITH_SHA256
    public static final SignatureAlgorithm RSA_PSS_WITH_SHA512
    public static final SignatureAlgorithm VERITY_DSA_WITH_SHA256
    public static final SignatureAlgorithm VERITY_ECDSA_WITH_SHA256
    public static final SignatureAlgorithm VERITY_RSA_PKCS1_V1_5_WITH_SHA256
    public final ContentDigestAlgorithm mContentDigestAlgorithm
    public final Int mId
    public final String mJcaKeyAlgorithm
    public final Int mJcaSigAlgMinSdkVersion
    public final Pair<String, ? extends AlgorithmParameterSpec> mJcaSignatureAlgAndParams
    public final Int mMinSdkVersion

    static {
        ContentDigestAlgorithm contentDigestAlgorithm = ContentDigestAlgorithm.CHUNKED_SHA256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm("RSA_PSS_WITH_SHA256", 0, InputDeviceCompat.SOURCE_KEYBOARD, contentDigestAlgorithm, "RSA", Pair.of("SHA256withRSA/PSS", PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1)), 24, 23)
        RSA_PSS_WITH_SHA256 = signatureAlgorithm
        ContentDigestAlgorithm contentDigestAlgorithm2 = ContentDigestAlgorithm.CHUNKED_SHA512
        SignatureAlgorithm signatureAlgorithm2 = SignatureAlgorithm("RSA_PSS_WITH_SHA512", 1, 258, contentDigestAlgorithm2, "RSA", Pair.of("SHA512withRSA/PSS", PSSParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, 64, 1)), 24, 23)
        RSA_PSS_WITH_SHA512 = signatureAlgorithm2
        SignatureAlgorithm signatureAlgorithm3 = SignatureAlgorithm("RSA_PKCS1_V1_5_WITH_SHA256", 2, 259, contentDigestAlgorithm, "RSA", Pair.of("SHA256withRSA", null), 24, 1)
        RSA_PKCS1_V1_5_WITH_SHA256 = signatureAlgorithm3
        SignatureAlgorithm signatureAlgorithm4 = SignatureAlgorithm("RSA_PKCS1_V1_5_WITH_SHA512", 3, 260, contentDigestAlgorithm2, "RSA", Pair.of("SHA512withRSA", null), 24, 1)
        RSA_PKCS1_V1_5_WITH_SHA512 = signatureAlgorithm4
        SignatureAlgorithm signatureAlgorithm5 = SignatureAlgorithm("ECDSA_WITH_SHA256", 4, InputDeviceCompat.SOURCE_DPAD, contentDigestAlgorithm, "EC", Pair.of("SHA256withECDSA", null), 24, 11)
        ECDSA_WITH_SHA256 = signatureAlgorithm5
        SignatureAlgorithm signatureAlgorithm6 = SignatureAlgorithm("ECDSA_WITH_SHA512", 5, 514, contentDigestAlgorithm2, "EC", Pair.of("SHA512withECDSA", null), 24, 11)
        ECDSA_WITH_SHA512 = signatureAlgorithm6
        SignatureAlgorithm signatureAlgorithm7 = SignatureAlgorithm("DSA_WITH_SHA256", 6, 769, contentDigestAlgorithm, "DSA", Pair.of("SHA256withDSA", null), 24, 1)
        DSA_WITH_SHA256 = signatureAlgorithm7
        ContentDigestAlgorithm contentDigestAlgorithm3 = ContentDigestAlgorithm.VERITY_CHUNKED_SHA256
        SignatureAlgorithm signatureAlgorithm8 = SignatureAlgorithm("VERITY_RSA_PKCS1_V1_5_WITH_SHA256", 7, 1057, contentDigestAlgorithm3, "RSA", Pair.of("SHA256withRSA", null), 28, 1)
        VERITY_RSA_PKCS1_V1_5_WITH_SHA256 = signatureAlgorithm8
        SignatureAlgorithm signatureAlgorithm9 = SignatureAlgorithm("VERITY_ECDSA_WITH_SHA256", 8, 1059, contentDigestAlgorithm3, "EC", Pair.of("SHA256withECDSA", null), 28, 11)
        VERITY_ECDSA_WITH_SHA256 = signatureAlgorithm9
        SignatureAlgorithm signatureAlgorithm10 = SignatureAlgorithm("VERITY_DSA_WITH_SHA256", 9, 1061, contentDigestAlgorithm3, "DSA", Pair.of("SHA256withDSA", null), 28, 1)
        VERITY_DSA_WITH_SHA256 = signatureAlgorithm10
        $VALUES = new Array<SignatureAlgorithm>{signatureAlgorithm, signatureAlgorithm2, signatureAlgorithm3, signatureAlgorithm4, signatureAlgorithm5, signatureAlgorithm6, signatureAlgorithm7, signatureAlgorithm8, signatureAlgorithm9, signatureAlgorithm10}
    }

    constructor(String str, Int i, Int i2, ContentDigestAlgorithm contentDigestAlgorithm, String str2, Pair pair, Int i3, Int i4) {
        this.mId = i2
        this.mContentDigestAlgorithm = contentDigestAlgorithm
        this.mJcaKeyAlgorithm = str2
        this.mJcaSignatureAlgAndParams = pair
        this.mMinSdkVersion = i3
        this.mJcaSigAlgMinSdkVersion = i4
    }

    fun findById(Int i) {
        for (SignatureAlgorithm signatureAlgorithm : values()) {
            if (signatureAlgorithm.getId() == i) {
                return signatureAlgorithm
            }
        }
        return null
    }

    fun valueOf(String str) {
        return (SignatureAlgorithm) Enum.valueOf(SignatureAlgorithm.class, str)
    }

    public static Array<SignatureAlgorithm> values() {
        return (Array<SignatureAlgorithm>) $VALUES.clone()
    }

    fun getContentDigestAlgorithm() {
        return this.mContentDigestAlgorithm
    }

    fun getId() {
        return this.mId
    }

    fun getJcaKeyAlgorithm() {
        return this.mJcaKeyAlgorithm
    }

    fun getJcaSigAlgMinSdkVersion() {
        return this.mJcaSigAlgMinSdkVersion
    }

    public Pair<String, ? extends AlgorithmParameterSpec> getJcaSignatureAlgorithmAndParams() {
        return this.mJcaSignatureAlgAndParams
    }

    fun getMinSdkVersion() {
        return this.mMinSdkVersion
    }
}
