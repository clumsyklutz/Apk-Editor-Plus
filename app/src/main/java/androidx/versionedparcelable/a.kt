package androidx.versionedparcelable

import android.os.Parcel
import android.os.Parcelable

final class a implements Parcelable.Creator {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return ParcelImpl(parcel)
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Array<Object> newArray(Int i) {
        return new ParcelImpl[i]
    }
}
