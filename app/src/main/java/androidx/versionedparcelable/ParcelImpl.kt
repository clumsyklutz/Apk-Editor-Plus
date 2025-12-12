package androidx.versionedparcelable

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RestrictTo

@RestrictTo({RestrictTo.Scope.LIBRARY})
class ParcelImpl implements Parcelable {

    /* renamed from: a, reason: collision with root package name */
    private final d f80a

    static {
        a()
    }

    protected constructor(Parcel parcel) {
        this.f80a = c(parcel).g()
    }

    @Override // android.os.Parcelable
    fun describeContents() {
        return 0
    }

    @Override // android.os.Parcelable
    fun writeToParcel(Parcel parcel, Int i) {
        c(parcel).a(this.f80a)
    }
}
