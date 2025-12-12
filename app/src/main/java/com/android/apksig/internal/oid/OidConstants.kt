package com.android.apksig.internal.oid

import com.android.apksig.internal.util.InclusiveIntRange
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map

class OidConstants {
    public static val OID_DIGEST_MD5 = "1.2.840.113549.2.5"
    public static val OID_DIGEST_SHA1 = "1.3.14.3.2.26"
    public static val OID_DIGEST_SHA224 = "2.16.840.1.101.3.4.2.4"
    public static val OID_DIGEST_SHA256 = "2.16.840.1.101.3.4.2.1"
    public static val OID_DIGEST_SHA384 = "2.16.840.1.101.3.4.2.2"
    public static val OID_DIGEST_SHA512 = "2.16.840.1.101.3.4.2.3"
    public static val OID_SIG_DSA = "1.2.840.10040.4.1"
    public static val OID_SIG_EC_PUBLIC_KEY = "1.2.840.10045.2.1"
    public static val OID_SIG_MD5_WITH_RSA = "1.2.840.113549.1.1.4"
    public static val OID_SIG_RSA = "1.2.840.113549.1.1.1"
    public static val OID_SIG_SHA1_WITH_DSA = "1.2.840.10040.4.3"
    public static val OID_SIG_SHA1_WITH_ECDSA = "1.2.840.10045.4.1"
    public static val OID_SIG_SHA1_WITH_RSA = "1.2.840.113549.1.1.5"
    public static val OID_SIG_SHA224_WITH_DSA = "2.16.840.1.101.3.4.3.1"
    public static val OID_SIG_SHA224_WITH_ECDSA = "1.2.840.10045.4.3.1"
    public static val OID_SIG_SHA224_WITH_RSA = "1.2.840.113549.1.1.14"
    public static val OID_SIG_SHA256_WITH_DSA = "2.16.840.1.101.3.4.3.2"
    public static val OID_SIG_SHA256_WITH_ECDSA = "1.2.840.10045.4.3.2"
    public static val OID_SIG_SHA256_WITH_RSA = "1.2.840.113549.1.1.11"
    public static val OID_SIG_SHA384_WITH_DSA = "2.16.840.1.101.3.4.3.3"
    public static val OID_SIG_SHA384_WITH_ECDSA = "1.2.840.10045.4.3.3"
    public static val OID_SIG_SHA384_WITH_RSA = "1.2.840.113549.1.1.12"
    public static val OID_SIG_SHA512_WITH_DSA = "2.16.840.1.101.3.4.3.4"
    public static val OID_SIG_SHA512_WITH_ECDSA = "1.2.840.10045.4.3.4"
    public static val OID_SIG_SHA512_WITH_RSA = "1.2.840.113549.1.1.13"
    public static final Map<String, String> OID_TO_JCA_DIGEST_ALG
    public static final Map<String, String> OID_TO_JCA_SIGNATURE_ALG
    public static final Map<String, List<InclusiveIntRange>> SUPPORTED_SIG_ALG_OIDS = HashMap()

    public static class OidToUserFriendlyNameMapper {
        public static final Map<String, String> OID_TO_USER_FRIENDLY_NAME

        static {
            HashMap map = HashMap()
            OID_TO_USER_FRIENDLY_NAME = map
            map.put(OidConstants.OID_DIGEST_MD5, "MD5")
            map.put(OidConstants.OID_DIGEST_SHA1, "SHA-1")
            map.put(OidConstants.OID_DIGEST_SHA224, "SHA-224")
            map.put(OidConstants.OID_DIGEST_SHA256, "SHA-256")
            map.put(OidConstants.OID_DIGEST_SHA384, "SHA-384")
            map.put(OidConstants.OID_DIGEST_SHA512, "SHA-512")
            map.put(OidConstants.OID_SIG_RSA, "RSA")
            map.put(OidConstants.OID_SIG_MD5_WITH_RSA, "MD5 with RSA")
            map.put(OidConstants.OID_SIG_SHA1_WITH_RSA, "SHA-1 with RSA")
            map.put(OidConstants.OID_SIG_SHA224_WITH_RSA, "SHA-224 with RSA")
            map.put(OidConstants.OID_SIG_SHA256_WITH_RSA, "SHA-256 with RSA")
            map.put(OidConstants.OID_SIG_SHA384_WITH_RSA, "SHA-384 with RSA")
            map.put(OidConstants.OID_SIG_SHA512_WITH_RSA, "SHA-512 with RSA")
            map.put(OidConstants.OID_SIG_DSA, "DSA")
            map.put(OidConstants.OID_SIG_SHA1_WITH_DSA, "SHA-1 with DSA")
            map.put(OidConstants.OID_SIG_SHA224_WITH_DSA, "SHA-224 with DSA")
            map.put(OidConstants.OID_SIG_SHA256_WITH_DSA, "SHA-256 with DSA")
            map.put(OidConstants.OID_SIG_SHA384_WITH_DSA, "SHA-384 with DSA")
            map.put(OidConstants.OID_SIG_SHA512_WITH_DSA, "SHA-512 with DSA")
            map.put(OidConstants.OID_SIG_EC_PUBLIC_KEY, "ECDSA")
            map.put(OidConstants.OID_SIG_SHA1_WITH_ECDSA, "SHA-1 with ECDSA")
            map.put(OidConstants.OID_SIG_SHA224_WITH_ECDSA, "SHA-224 with ECDSA")
            map.put(OidConstants.OID_SIG_SHA256_WITH_ECDSA, "SHA-256 with ECDSA")
            map.put(OidConstants.OID_SIG_SHA384_WITH_ECDSA, "SHA-384 with ECDSA")
            map.put(OidConstants.OID_SIG_SHA512_WITH_ECDSA, "SHA-512 with ECDSA")
        }

        fun getUserFriendlyNameForOid(String str) {
            return OID_TO_USER_FRIENDLY_NAME.get(str)
        }
    }

    static {
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_RSA, InclusiveIntRange.from(0))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_RSA, InclusiveIntRange.from(0))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.from(0))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 21))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 21))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_RSA, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_RSA, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 21))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_DSA, InclusiveIntRange.from(0))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.from(9))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_DSA, InclusiveIntRange.from(22))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_DSA, InclusiveIntRange.from(22))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.from(18))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.from(21))
        addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23))
        addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.from(21))
        HashMap map = HashMap()
        OID_TO_JCA_DIGEST_ALG = map
        map.put(OID_DIGEST_MD5, "MD5")
        map.put(OID_DIGEST_SHA1, "SHA-1")
        map.put(OID_DIGEST_SHA224, "SHA-224")
        map.put(OID_DIGEST_SHA256, "SHA-256")
        map.put(OID_DIGEST_SHA384, "SHA-384")
        map.put(OID_DIGEST_SHA512, "SHA-512")
        HashMap map2 = HashMap()
        OID_TO_JCA_SIGNATURE_ALG = map2
        map2.put(OID_SIG_MD5_WITH_RSA, "MD5withRSA")
        map2.put(OID_SIG_SHA1_WITH_RSA, "SHA1withRSA")
        map2.put(OID_SIG_SHA224_WITH_RSA, "SHA224withRSA")
        map2.put(OID_SIG_SHA256_WITH_RSA, "SHA256withRSA")
        map2.put(OID_SIG_SHA384_WITH_RSA, "SHA384withRSA")
        map2.put(OID_SIG_SHA512_WITH_RSA, "SHA512withRSA")
        map2.put(OID_SIG_SHA1_WITH_DSA, "SHA1withDSA")
        map2.put(OID_SIG_SHA224_WITH_DSA, "SHA224withDSA")
        map2.put(OID_SIG_SHA256_WITH_DSA, "SHA256withDSA")
        map2.put(OID_SIG_SHA1_WITH_ECDSA, "SHA1withECDSA")
        map2.put(OID_SIG_SHA224_WITH_ECDSA, "SHA224withECDSA")
        map2.put(OID_SIG_SHA256_WITH_ECDSA, "SHA256withECDSA")
        map2.put(OID_SIG_SHA384_WITH_ECDSA, "SHA384withECDSA")
        map2.put(OID_SIG_SHA512_WITH_ECDSA, "SHA512withECDSA")
    }

    fun addSupportedSigAlg(String str, String str2, InclusiveIntRange... inclusiveIntRangeArr) {
        SUPPORTED_SIG_ALG_OIDS.put(str + "with" + str2, Arrays.asList(inclusiveIntRangeArr))
    }

    public static List<InclusiveIntRange> getSigAlgSupportedApiLevels(String str, String str2) {
        List<InclusiveIntRange> list = SUPPORTED_SIG_ALG_OIDS.get(str + "with" + str2)
        return list != null ? list : Collections.emptyList()
    }
}
