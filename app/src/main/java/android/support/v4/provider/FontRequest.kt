package android.support.v4.provider

import android.support.annotation.ArrayRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.util.Preconditions
import android.util.Base64
import java.util.List

class FontRequest {
    private final List mCertificates
    private final Int mCertificatesArray
    private final String mIdentifier
    private final String mProviderAuthority
    private final String mProviderPackage
    private final String mQuery

    constructor(@NonNull String str, @NonNull String str2, @NonNull String str3, @ArrayRes Int i) {
        this.mProviderAuthority = (String) Preconditions.checkNotNull(str)
        this.mProviderPackage = (String) Preconditions.checkNotNull(str2)
        this.mQuery = (String) Preconditions.checkNotNull(str3)
        this.mCertificates = null
        Preconditions.checkArgument(i != 0)
        this.mCertificatesArray = i
        this.mIdentifier = this.mProviderAuthority + "-" + this.mProviderPackage + "-" + this.mQuery
    }

    constructor(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull List list) {
        this.mProviderAuthority = (String) Preconditions.checkNotNull(str)
        this.mProviderPackage = (String) Preconditions.checkNotNull(str2)
        this.mQuery = (String) Preconditions.checkNotNull(str3)
        this.mCertificates = (List) Preconditions.checkNotNull(list)
        this.mCertificatesArray = 0
        this.mIdentifier = this.mProviderAuthority + "-" + this.mProviderPackage + "-" + this.mQuery
    }

    @Nullable
    public final List getCertificates() {
        return this.mCertificates
    }

    @ArrayRes
    public final Int getCertificatesArrayResId() {
        return this.mCertificatesArray
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final String getIdentifier() {
        return this.mIdentifier
    }

    @NonNull
    public final String getProviderAuthority() {
        return this.mProviderAuthority
    }

    @NonNull
    public final String getProviderPackage() {
        return this.mProviderPackage
    }

    @NonNull
    public final String getQuery() {
        return this.mQuery
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:")
        for (Int i = 0; i < this.mCertificates.size(); i++) {
            sb.append(" [")
            List list = (List) this.mCertificates.get(i)
            for (Int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"")
                sb.append(Base64.encodeToString((Array<Byte>) list.get(i2), 0))
                sb.append("\"")
            }
            sb.append(" ]")
        }
        sb.append("}")
        sb.append("mCertificatesArray: " + this.mCertificatesArray)
        return sb.toString()
    }
}
