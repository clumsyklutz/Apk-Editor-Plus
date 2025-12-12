package android.support.v4.os

import android.os.Parcel
import android.os.Parcelable

@Deprecated
class ParcelableCompat {

    class ParcelableCompatCreatorHoneycombMR2 implements Parcelable.ClassLoaderCreator {
        private final ParcelableCompatCreatorCallbacks mCallbacks

        ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks parcelableCompatCreatorCallbacks) {
            this.mCallbacks = parcelableCompatCreatorCallbacks
        }

        @Override // android.os.Parcelable.Creator
        fun createFromParcel(Parcel parcel) {
            return this.mCallbacks.createFromParcel(parcel, null)
        }

        @Override // android.os.Parcelable.ClassLoaderCreator
        fun createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return this.mCallbacks.createFromParcel(parcel, classLoader)
        }

        @Override // android.os.Parcelable.Creator
        public Array<Object> newArray(Int i) {
            return this.mCallbacks.newArray(i)
        }
    }

    private constructor() {
    }

    @Deprecated
    public static Parcelable.Creator newCreator(ParcelableCompatCreatorCallbacks parcelableCompatCreatorCallbacks) {
        return ParcelableCompatCreatorHoneycombMR2(parcelableCompatCreatorCallbacks)
    }
}
