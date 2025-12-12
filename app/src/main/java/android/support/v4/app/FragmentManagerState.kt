package android.support.v4.app

import android.os.Parcel
import android.os.Parcelable

final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.app.FragmentManagerState.1
        @Override // android.os.Parcelable.Creator
        public final FragmentManagerState createFromParcel(Parcel parcel) {
            return FragmentManagerState(parcel)
        }

        @Override // android.os.Parcelable.Creator
        public final Array<FragmentManagerState> newArray(Int i) {
            return new FragmentManagerState[i]
        }
    }
    Array<FragmentState> mActive
    Array<Int> mAdded
    Array<BackStackState> mBackStack
    Int mNextFragmentIndex
    Int mPrimaryNavActiveIndex

    constructor() {
        this.mPrimaryNavActiveIndex = -1
    }

    constructor(Parcel parcel) {
        this.mPrimaryNavActiveIndex = -1
        this.mActive = (Array<FragmentState>) parcel.createTypedArray(FragmentState.CREATOR)
        this.mAdded = parcel.createIntArray()
        this.mBackStack = (Array<BackStackState>) parcel.createTypedArray(BackStackState.CREATOR)
        this.mPrimaryNavActiveIndex = parcel.readInt()
        this.mNextFragmentIndex = parcel.readInt()
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return 0
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        parcel.writeTypedArray(this.mActive, i)
        parcel.writeIntArray(this.mAdded)
        parcel.writeTypedArray(this.mBackStack, i)
        parcel.writeInt(this.mPrimaryNavActiveIndex)
        parcel.writeInt(this.mNextFragmentIndex)
    }
}
