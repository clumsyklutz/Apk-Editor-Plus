package android.support.v4.media.session

import android.os.Parcel
import android.os.Parcelable

class ParcelableVolumeInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.ParcelableVolumeInfo.1
        @Override // android.os.Parcelable.Creator
        public final ParcelableVolumeInfo createFromParcel(Parcel parcel) {
            return ParcelableVolumeInfo(parcel)
        }

        @Override // android.os.Parcelable.Creator
        public final Array<ParcelableVolumeInfo> newArray(Int i) {
            return new ParcelableVolumeInfo[i]
        }
    }
    public Int audioStream
    public Int controlType
    public Int currentVolume
    public Int maxVolume
    public Int volumeType

    constructor(Int i, Int i2, Int i3, Int i4, Int i5) {
        this.volumeType = i
        this.audioStream = i2
        this.controlType = i3
        this.maxVolume = i4
        this.currentVolume = i5
    }

    constructor(Parcel parcel) {
        this.volumeType = parcel.readInt()
        this.controlType = parcel.readInt()
        this.maxVolume = parcel.readInt()
        this.currentVolume = parcel.readInt()
        this.audioStream = parcel.readInt()
    }

    @Override // android.os.Parcelable
    fun describeContents() {
        return 0
    }

    @Override // android.os.Parcelable
    fun writeToParcel(Parcel parcel, Int i) {
        parcel.writeInt(this.volumeType)
        parcel.writeInt(this.controlType)
        parcel.writeInt(this.maxVolume)
        parcel.writeInt(this.currentVolume)
        parcel.writeInt(this.audioStream)
    }
}
