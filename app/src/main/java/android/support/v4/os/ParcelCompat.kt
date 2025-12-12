package android.support.v4.os

import android.os.Parcel

class ParcelCompat {
    private constructor() {
    }

    fun readBoolean(Parcel parcel) {
        return parcel.readInt() != 0
    }

    fun writeBoolean(Parcel parcel, Boolean z) {
        parcel.writeInt(z ? 1 : 0)
    }
}
