package com.android.apksig.internal.apk.v1

import java.util.Comparator

public enum DigestAlgorithm {
    SHA1("SHA-1"),
    SHA256("SHA-256")

    public static Comparator<DigestAlgorithm> BY_STRENGTH_COMPARATOR = StrengthComparator(null)
    public final String mJcaMessageDigestAlgorithm

    /* renamed from: com.android.apksig.internal.apk.v1.DigestAlgorithm$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm

        static {
            Array<Int> iArr = new Int[DigestAlgorithm.values().length]
            $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm = iArr
            try {
                iArr[DigestAlgorithm.SHA1.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[DigestAlgorithm.SHA256.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class StrengthComparator implements Comparator<DigestAlgorithm> {
        constructor() {
        }

        public /* synthetic */ StrengthComparator(AnonymousClass1 anonymousClass1) {
            this()
        }

        @Override // java.util.Comparator
        fun compare(DigestAlgorithm digestAlgorithm, DigestAlgorithm digestAlgorithm2) {
            Array<Int> iArr = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm
            Int i = iArr[digestAlgorithm.ordinal()]
            if (i == 1) {
                Int i2 = iArr[digestAlgorithm2.ordinal()]
                if (i2 == 1) {
                    return 0
                }
                if (i2 == 2) {
                    return -1
                }
                throw RuntimeException("Unsupported algorithm: " + digestAlgorithm2)
            }
            if (i != 2) {
                throw RuntimeException("Unsupported algorithm: " + digestAlgorithm)
            }
            Int i3 = iArr[digestAlgorithm2.ordinal()]
            if (i3 == 1) {
                return 1
            }
            if (i3 == 2) {
                return 0
            }
            throw RuntimeException("Unsupported algorithm: " + digestAlgorithm2)
        }
    }

    DigestAlgorithm(String str) {
        this.mJcaMessageDigestAlgorithm = str
    }

    fun getJcaMessageDigestAlgorithm() {
        return this.mJcaMessageDigestAlgorithm
    }
}
