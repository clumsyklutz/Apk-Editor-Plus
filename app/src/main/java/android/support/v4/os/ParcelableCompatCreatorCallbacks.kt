package android.support.v4.os

import android.os.Parcel

@Deprecated
public interface ParcelableCompatCreatorCallbacks {
    Object createFromParcel(Parcel parcel, ClassLoader classLoader)

    Array<Object> newArray(Int i)
}
