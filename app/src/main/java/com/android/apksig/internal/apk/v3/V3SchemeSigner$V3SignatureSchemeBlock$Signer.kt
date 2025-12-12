package com.android.apksig.internal.apk.v3

import com.android.apksig.internal.util.Pair
import java.util.List

class V3SchemeSigner$V3SignatureSchemeBlock$Signer {
    public Int maxSdkVersion
    public Int minSdkVersion
    public Array<Byte> publicKey
    public List<Pair<Integer, Array<Byte>>> signatures
    public Array<Byte> signedData

    public V3SchemeSigner$V3SignatureSchemeBlock$Signer() {
    }
}
